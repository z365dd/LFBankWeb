package com.adtec.sys.modules.flow.dao;

/**
 * 系统名称: SmartWeb平台
 * 模块名称:流程步骤模板数据库操作类
 * 类  名  称: FlowStepTemplateDao.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl
 * 开发时间: 2018-05-07 20:02:25
 * 系统版本: V1.0.0
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================ *
 * ========     ======  ============================================
 */


import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.common.dao.IBaseDao;
import com.adtec.sys.modules.flow.entity.FlowStepTemplateDO;
import com.google.common.collect.Lists;


@Component
public class FlowStepTemplateDao implements IBaseDao<FlowStepTemplateDO>{

    /*日志对象*/
    protected final static Logger logger = LoggerFactory.getLogger(FlowStepTemplateDao.class);
    /*表名称*/
    public static final String TABLE_NAME = "T_SYS_FLOW_STEP_TEMPLATE";


    @Override
    public int insert(FlowStepTemplateDO flowStepTemplateDO) {
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.saveObject(TABLE_NAME, flowStepTemplateDO, flowStepTemplateDO.getIgnoreFields());
        } catch (SQLException e) {
            logger.error("新增流程步骤模板异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "新增流程步骤模板失败！");
        }
        return rs;
    }


    @Override
    public int update(FlowStepTemplateDO flowStepTemplateDO) {
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.updateObject(TABLE_NAME, flowStepTemplateDO, flowStepTemplateDO.getMatchFields(), flowStepTemplateDO.getIgnoreFields());
        } catch (SQLException e) {
            logger.error("修改流程步骤模板异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "修改流程步骤模板失败！");
        }
        return rs;
    }

    /**
     * 根据流程模板ID+步骤号更新下一步骤号
     * @param flowTmplId	流程模板ID
     * @param stepSer		步骤号
     * @param nextStepSer	下一步骤号
     * @return
     */
    public int updateNextStepSer(String flowTmplId, int stepSer, int nextStepSer){
    	StringBuilder sql = new StringBuilder();
        sql.append("update ").append(TABLE_NAME).append(" set next_step_ser=?  where flow_tmpl_id=? and step_ser=?");
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.execute(sql.toString(), nextStepSer, flowTmplId, stepSer);
        } catch (SQLException e) {
            logger.error("更新流程步骤模板下一步骤号异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "更新流程步骤模板下一步骤号失败！");
        }
        return rs;
    }
    
    /**
     * 更新对应流程模板下，大于开始步骤号的所有步骤节点中的步骤号、下一步骤号、上一步骤号，进行moveStep操作,moveStep>0向后移,moveStep<0向前移
     * @param templateId		流程模板ID
     * @param startStepSer		开始步骤号
     * @param moveStep			前后移动的步骤数
     * @return
     */
    public int moveStepSer(String templateId, int startStepSer, int moveStep){
    	StringBuilder sql = new StringBuilder();
        sql.append("update ").append(TABLE_NAME).append(" set step_ser=step_ser+(?), ");
        sql.append("prv_step_ser=prv_step_ser+(?), ");
        sql.append("next_step_ser= (CASE WHEN next_step_ser != 999 THEN next_step_ser+(?)  ");
        sql.append("ELSE next_step_ser END) ");
        sql.append(" where flow_tmpl_id=? and step_ser>?");
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.execute(sql.toString(), moveStep, moveStep, moveStep, templateId, startStepSer);
        } catch (SQLException e) {
            logger.error("更新流程步骤["+startStepSer+"]的后续步骤异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "更新流程步骤["+startStepSer+"]的后续步骤失败！");
        }
        return rs;
    }
    

    @Override
    public int delete(FlowStepTemplateDO flowStepTemplateDO) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from ").append(TABLE_NAME).append(" where id=? ");
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.execute(sql.toString(), flowStepTemplateDO.getId());
        } catch (SQLException e) {
            logger.error("删除流程步骤模板异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "删除流程步骤模板失败！");
        }
        return rs;
    }
    
    /**
     * 根据流程模板id删除对应的流程步骤模板
     * @param flowTmplId
     * @return
     */
    public int deleteByFlowTmplId(String flowTmplId) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from ").append(TABLE_NAME).append(" where flow_tmpl_id=?");
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.execute(sql.toString(), flowTmplId);
        } catch (SQLException e) {
            logger.error("删除流程步骤模板异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "删除流程步骤模板失败！");
        }
        return rs;
    }


    public FlowStepTemplateDO get(String id) {
        FlowStepTemplateDO obj = new FlowStepTemplateDO();
        obj.setId(id);
        return get(obj);
    }


    public FlowStepTemplateDO get(FlowStepTemplateDO flowStepTemplateDO) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where id=?");
        FlowStepTemplateDO rs = new FlowStepTemplateDO();
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObject(sql.toString(), FlowStepTemplateDO.class, flowStepTemplateDO.getId());
        } catch (SQLException e) {
            logger.error("查询流程步骤模板异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "查询流程步骤模板失败！");
        }
        return rs;
    }

    /**
     * 根据流程模板ID+步骤号获取对应的流程步骤模板
     * @param flowTmplId	流程模板ID
     * @param stepSer			步骤号
     * @return
     */
    public FlowStepTemplateDO getByStepSer(String flowTmplId, int stepSer) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where flow_tmpl_id=? and step_ser=?");
        FlowStepTemplateDO rs = new FlowStepTemplateDO();
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObject(sql.toString(), FlowStepTemplateDO.class, flowTmplId, stepSer);
        } catch (Exception e) {
            logger.error("查询流程步骤模板异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "查询流程步骤模板失败！");
        }
        return rs;
    }

    /**
     * 根据流程模板ID获取对应的流程步骤模板
     * @param flowTmplId	流程模板ID
     * @return
     */
    public FlowStepTemplateDO getByTmpl(String flowTmplId) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where flow_tmpl_id=?");
        FlowStepTemplateDO rs;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObject(sql.toString(), FlowStepTemplateDO.class, flowTmplId);
        } catch (Exception e) {
            logger.error("查询流程步骤模板异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "查询流程步骤模板失败！");
        }
        return rs;
    }

    @Override
    public List<FlowStepTemplateDO> list(FlowStepTemplateDO flowStepTemplateDO) {
        return list(flowStepTemplateDO, 0, 0);
    }


    @Override
    public List<FlowStepTemplateDO> list(FlowStepTemplateDO flowStepTemplateDO, int start, int limit) {
        logger.debug("flowStepTemplateDO=" + flowStepTemplateDO);
        logger.debug("start=" + start);
        logger.debug("limit=" + limit);
        StringBuilder sql = new StringBuilder();
        /* 20200117 mod by chenyl for SQL的拼接才成占位符的方式，防止SQL注入 */
        List<Object> parameters = Lists.newArrayList();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(flowStepTemplateDO, parameters));
        sql.append(" order by flow_tmpl_id, step_ser asc");
        logger.debug("sql=" + sql.toString());
        IDBSession session = DBSessionFactory.getSession();
        List<FlowStepTemplateDO> list = null;
        try {
            if (limit == 0) {
                list = session.getObjectListByList(sql.toString(), FlowStepTemplateDO.class, parameters);
            } else {
                list = session.getObjectListByListForPage(sql.toString(), FlowStepTemplateDO.class, start, limit, parameters);
            }
        } catch (Exception e) {
            logger.error("列表查询流程步骤模板异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "列表查询流程步骤模板失败！");
        }
        return list;
    }


    @Override
    public List<FlowStepTemplateDO> list(int start, int limit, Object... param) {
        return null;
    }


    public int getTotal(FlowStepTemplateDO flowStepTemplateDO) {
        logger.debug("flowStepTemplateDO=" + flowStepTemplateDO);
        StringBuilder sql = new StringBuilder();
        /* 20200117 mod by chenyl for SQL的拼接才成占位符的方式，防止SQL注入 */
        List<Object> parameters = Lists.newArrayList();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(flowStepTemplateDO, parameters));
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        logger.debug("countSql=" + countSql);
        IDBSession session = DBSessionFactory.getSession();
        int total = 0;
        try {
            total = session.accountByList(countSql, parameters);
        } catch (Exception e) {
            logger.error("总记录数查询流程步骤模板异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "总记录数查询流程步骤模板失败！");
        }
        return total;
    }


    /*根据传入的数据对象，对应属性值不空时拼接查询条件*/
    private String getWhereSql(FlowStepTemplateDO flowStepTemplateDO, List<Object> parameters) {
        StringBuffer whereSql = new StringBuffer(" where 1=1 ");
        if(null==parameters){
        	parameters = Lists.newArrayList();
        }
        if(!DataUtil.isNullStr(flowStepTemplateDO.getId())){
            /*添加查询条件：编号*/
            whereSql.append(" and id=? ");
            parameters.add(flowStepTemplateDO.getId());
        }
        if(!DataUtil.isNullStr(flowStepTemplateDO.getFlowTmplId())){
            /*添加查询条件：流程模板ID*/
            whereSql.append(" and flow_tmpl_id=? ");
            parameters.add(flowStepTemplateDO.getFlowTmplId());
        }
        // 不参与列表查询
        if(!DataUtil.isNullStr("" + flowStepTemplateDO.getStepSer()) && !FlowStepTemplateDO.MQRY_Y.equals(flowStepTemplateDO.getmQry())){
            /*添加查询条件：步骤号*/
            whereSql.append(" and step_ser=? ");
            parameters.add(flowStepTemplateDO.getStepSer());
        }
        if(!DataUtil.isNullStr(flowStepTemplateDO.getEngName())){
            /*添加查询条件：步骤英文名称*/
            whereSql.append(" and eng_name=? ");
            parameters.add(flowStepTemplateDO.getEngName());
        }
        if(!DataUtil.isNullStr(flowStepTemplateDO.getName())){
            /*添加查询条件：中文名称*/
        	if(FlowStepTemplateDO.MQRY_Y.equals(flowStepTemplateDO.getmQry())){
        		/*中英文模糊查询*/
        		whereSql.append(" and (name like ?").append(" or eng_name like ?)");
        		parameters.add("%"+flowStepTemplateDO.getName()+"%");
        		parameters.add("%"+flowStepTemplateDO.getName()+"%");
        	}else{
        		whereSql.append(" and name like ?");
        		parameters.add("%"+flowStepTemplateDO.getName()+"%");
        	}
        }
        if(!DataUtil.isNullStr(flowStepTemplateDO.getFlowApprFlg())){
            /*添加查询条件：通过标准*/
            whereSql.append(" and FLOW_APPR_FLG=? ");
            parameters.add(flowStepTemplateDO.getFlowApprFlg());
        }
        // 不参与列表查询
        if(!DataUtil.isNullStr("" + flowStepTemplateDO.getSuccNum()) && !FlowStepTemplateDO.MQRY_Y.equals(flowStepTemplateDO.getmQry())){
            /*添加查询条件：通过用户个数*/
            whereSql.append(" and succ_num=? ");
            parameters.add(flowStepTemplateDO.getSuccNum());
        }
        if(!DataUtil.isNullStr(flowStepTemplateDO.getFlowProcClssTp())){
            /*添加查询条件：事件处理类*/
            whereSql.append(" and FLOW_PROC_CLSS_TP=? ");
            parameters.add(flowStepTemplateDO.getFlowProcClssTp());
        }
        // 不参与列表查询
        if(!DataUtil.isNullStr("" + flowStepTemplateDO.getNextStepSer()) && !FlowStepTemplateDO.MQRY_Y.equals(flowStepTemplateDO.getmQry())){
            /*添加查询条件：下一步骤号*/
            whereSql.append(" and next_step_ser=? ");
            parameters.add(flowStepTemplateDO.getNextStepSer());
        }
        // 不参与列表查询
        if(!DataUtil.isNullStr("" + flowStepTemplateDO.getPrvStepSer()) && !FlowStepTemplateDO.MQRY_Y.equals(flowStepTemplateDO.getmQry())){
            /*添加查询条件：上一步骤号*/
            whereSql.append(" and prv_step_ser=? ");
            parameters.add(flowStepTemplateDO.getPrvStepSer());
        }
        // 不参与列表查询
        if(!DataUtil.isNullStr("" + flowStepTemplateDO.getDayNum()) && !FlowStepTemplateDO.MQRY_Y.equals(flowStepTemplateDO.getmQry())){
            /*添加查询条件：最长处理时间*/
            whereSql.append(" and day_num=? ");
            parameters.add(flowStepTemplateDO.getDayNum());
        }
        if(!DataUtil.isNullStr(flowStepTemplateDO.getFlowTimeOutFlg())){
            /*添加查询条件：超时处理*/
            whereSql.append(" and FLOW_TIME_OUT_FLG=? ");
            parameters.add(flowStepTemplateDO.getFlowTimeOutFlg());
        }
        if(!DataUtil.isNullStr(flowStepTemplateDO.getTimeOutFlowUserTp())){
            /*添加查询条件：超时处理用户类型*/
            whereSql.append(" and TIME_OUT_FLOW_USER_TP=? ");
            parameters.add(flowStepTemplateDO.getTimeOutFlowUserTp());
        }
        if(!DataUtil.isNullStr(flowStepTemplateDO.getTimeOutProcUserId())){
            /*添加查询条件：超时处理用户编码*/
            whereSql.append(" and TIME_OUT_PROC_USER_ID=? ");
            parameters.add(flowStepTemplateDO.getTimeOutProcUserId());
        }
        if(!DataUtil.isNullStr(flowStepTemplateDO.getApprFlowUserTp())){
            /*添加查询条件：审批用户类型*/
            whereSql.append(" and APPR_FLOW_USER_TP=? ");
            parameters.add(flowStepTemplateDO.getApprFlowUserTp());
        }
        if(!DataUtil.isNullStr(flowStepTemplateDO.getApprUserId())){
            /*添加查询条件：审批用户编码*/
            whereSql.append(" and appr_user_id=? ");
            parameters.add(flowStepTemplateDO.getApprUserId());
        }
        if(!DataUtil.isNullStr(flowStepTemplateDO.getCrtr())){
            /*添加查询条件：创建者*/
            whereSql.append(" and crtr=? ");
            parameters.add(flowStepTemplateDO.getCrtr());
        }
        if(!DataUtil.isNullStr(flowStepTemplateDO.getCrtTime())){
            /*添加查询条件：创建时间*/
            whereSql.append(" and crt_time=? ");
            parameters.add(flowStepTemplateDO.getCrtTime());
        }
        if(!DataUtil.isNullStr(flowStepTemplateDO.getUptr())){
            /*添加查询条件：更新者*/
            whereSql.append(" and uptr=? ");
            parameters.add(flowStepTemplateDO.getUptr());
        }
        if(!DataUtil.isNullStr(flowStepTemplateDO.getUptTime())){
            /*添加查询条件：更新时间*/
            whereSql.append(" and upt_time=? ");
            parameters.add(flowStepTemplateDO.getUptTime());
        }
        if(!DataUtil.isNullStr(flowStepTemplateDO.getRmrk())){
            /*添加查询条件：备注信息*/
            whereSql.append(" and rmrk=? ");
            parameters.add(flowStepTemplateDO.getRmrk());
        }
        if(!DataUtil.isNullStr(flowStepTemplateDO.getDac())){
            /*添加查询条件：DAC*/
            whereSql.append(" and dac=? ");
            parameters.add(flowStepTemplateDO.getDac());
        }
        return whereSql.toString();
    }


	/**
	 * 获取最后一个步骤模板
	 * @param flowTmplId
	 * @return
	 */
	public FlowStepTemplateDO getLastStep(String flowTmplId) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		sql.append("select * from ").append(TABLE_NAME).append(" where flow_tmpl_id=?");
		sql.append(" order by flow_tmpl_id, step_ser desc");
		logger.debug("sql=" + sql.toString());
		IDBSession session = DBSessionFactory.getSession();
		List<FlowStepTemplateDO> list = null;
		try {
			list = session.getObjectList(sql.toString(), FlowStepTemplateDO.class, flowTmplId);
		} catch (Exception e) {
			logger.error("获取最后一个步骤模板异常：" + e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "获取最后一个步骤模板失败！");
		}
		if(null!=list){
			return list.get(0);
		}else{
			return null;
		}
	}


}
