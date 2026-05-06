package com.adtec.sys.modules.flow.dao;

/**
 * 系统名称: SmartWeb平台
 * 模块名称:流程步骤实例数据库操作类
 * 类  名  称: FlowStepDao.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl
 * 开发时间: 2018-05-07 20:02:25
 * 系统版本: V1.0.0
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================ *
 * ========     ======  ============================================
 */

import java.util.ArrayList;
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
import com.adtec.sys.modules.flow.entity.FlowStepDO;
import com.adtec.sys.modules.flow.entity.FlowStepTemplateDO;
import com.google.common.collect.Lists;


@Component
public class FlowStepDao implements IBaseDao<FlowStepDO>{

    /*日志对象*/
    protected final static Logger logger = LoggerFactory.getLogger(FlowStepDao.class);
    /*表名称*/
    public static final String TABLE_NAME = "T_SYS_FLOW_STEP";


    @Override
    public int insert(FlowStepDO flowStepDO) {
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.saveObject(TABLE_NAME, flowStepDO, flowStepDO.getIgnoreFields());
        } catch (Exception e) {
            logger.error("新增流程步骤实例异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "新增流程步骤实例失败！");
        }
        return rs;
    }


    @Override
    public int update(FlowStepDO flowStepDO) {
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.updateObject(TABLE_NAME, flowStepDO, flowStepDO.getMatchFields(), flowStepDO.getIgnoreFields());
        } catch (Exception e) {
            logger.error("修改流程步骤实例异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "修改流程步骤实例失败！");
        }
        return rs;
    }

    /**
     * 根据流程步骤实例ID删除
     * @param id
     * @return
     */
    public int delete(String id){
    	FlowStepDO flowStepDO = new FlowStepDO();
    	flowStepDO.setId(id);
    	return delete(flowStepDO);
    }

    @Override
    public int delete(FlowStepDO flowStepDO) {
        StringBuilder sql = new StringBuilder();
        /* 20200117 mod by chenyl for SQL的拼接才成占位符的方式，防止SQL注入 */
        List<Object> parameters = Lists.newArrayList();
        sql.append("delete from ").append(TABLE_NAME).append(getWhereSql(flowStepDO, parameters));
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.executeByList(sql.toString(), parameters);
        } catch (Exception e) {
            logger.error("删除流程步骤实例异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "删除流程步骤实例失败！");
        }
        return rs;
    }

    public FlowStepDO get(String id) {
        FlowStepDO obj = new FlowStepDO();
        obj.setId(id);
        return get(obj);
    }


    public FlowStepDO get(FlowStepDO flowStepDO) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where id=? ");
        FlowStepDO rs = null;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObject(sql.toString(), FlowStepDO.class, flowStepDO.getId());
        } catch (Exception e) {
            logger.error("查询流程步骤实例异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "查询流程步骤实例失败！");
        }
        return rs;
    }

    @Override
    public List<FlowStepDO> list(FlowStepDO flowStepDO) {
        return list(flowStepDO, 0, 0);
    }


    @Override
    public List<FlowStepDO> list(FlowStepDO flowStepDO, int start, int limit) {
        logger.debug("flowStepDO=" + flowStepDO);
        logger.debug("start=" + start);
        logger.debug("limit=" + limit);
        StringBuilder sql = new StringBuilder();
        /* 20200117 mod by chenyl for SQL的拼接才成占位符的方式，防止SQL注入 */
        List<Object> parameters = Lists.newArrayList();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(flowStepDO, parameters));
        sql.append(" order by upt_time desc");
        logger.debug("sql=" + sql.toString());
        IDBSession session = DBSessionFactory.getSession();
        List<FlowStepDO> list = null;
        try {
            if (limit == 0) {
                list = session.getObjectListByList(sql.toString(), FlowStepDO.class, parameters);
            } else {
                list = session.getObjectListByListForPage(sql.toString(), FlowStepDO.class, start, limit, parameters);
            }
        } catch (Exception e) {
            logger.error("列表查询流程步骤实例异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "列表查询流程步骤实例失败！");
        }
        return list;
    }


    @Override
    public List<FlowStepDO> list(int start, int limit, Object... param) {
        return null;
    }


    public int getTotal(FlowStepDO flowStepDO) {
        logger.debug("flowStepDO=" + flowStepDO);
        StringBuilder sql = new StringBuilder();
        /* 20200117 mod by chenyl for SQL的拼接才成占位符的方式，防止SQL注入 */
        List<Object> parameters = Lists.newArrayList();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(flowStepDO, parameters));
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        logger.debug("countSql=" + countSql);
        IDBSession session = DBSessionFactory.getSession();
        int total = 0;
        try {
            total = session.accountByList(countSql, parameters);
        } catch (Exception e) {
            logger.error("总记录数查询流程步骤实例异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "总记录数查询流程步骤实例失败！");
        }
        return total;
    }


    /*根据传入的数据对象，对应属性值不空时拼接查询条件*/
    private String getWhereSql(FlowStepDO flowStepDO, List<Object> parameters) {
        StringBuffer whereSql = new StringBuffer(" where 1=1 ");
        if(null==parameters){
        	parameters = Lists.newArrayList();
        }
        if(!DataUtil.isNullStr(flowStepDO.getId())){
            /*添加查询条件：编号*/
            whereSql.append(" and id=? ");
            parameters.add(flowStepDO.getId());
        }
        if(!DataUtil.isNullStr(flowStepDO.getGlobalSeq())){
            /*添加查询条件：处理该步骤的全局流水号*/
            whereSql.append(" and global_seq=? ");
            parameters.add(flowStepDO.getGlobalSeq());
        }
        if(!DataUtil.isNullStr(flowStepDO.getFlowTmplId())){
            /*添加查询条件：流程模板ID*/
            whereSql.append(" and flow_tmpl_id=? ");
            parameters.add(flowStepDO.getFlowTmplId());
        }
        if(!DataUtil.isNullStr(flowStepDO.getStepTmplId())){
            /*添加查询条件：流程步骤模板ID*/
            whereSql.append(" and step_tmpl_id=? ");
            parameters.add(flowStepDO.getStepTmplId());
        }
        if(FlowStepTemplateDO.STEP_START!=flowStepDO.getStepSer()){
            /*添加查询条件：步骤号*/
            whereSql.append(" and step_ser=? ");
            parameters.add(flowStepDO.getStepSer());
        }
        if(!DataUtil.isNullStr(flowStepDO.getInfoTitle())){
            /*添加查询条件：步骤标题*/
            whereSql.append(" and info_title=? ");
            parameters.add(flowStepDO.getInfoTitle());
        }
        if(!DataUtil.isNullStr(flowStepDO.getFlowDesc())){
            /*添加查询条件：步骤程描述*/
            whereSql.append(" and flow_desc=? ");
            parameters.add(flowStepDO.getFlowDesc());
        }
        if(!DataUtil.isNullStr(flowStepDO.getFlowStat())){
            /*添加查询条件：流程状态*/
        	if(flowStepDO.getFlowStat().indexOf("','")>-1){
        		// 多状态匹配查询
        	    //edit by zx 20200630 多状态'02','04','06'时不能只用一个占位符，否则字符串当一个参数，查不出数据
        		/*
        		whereSql.append(" and flow_stat in ? ");
        		parameters.add("("+flowStepDO.getFlowStat()+")");
        		*/
        	    String flowStat = flowStepDO.getFlowStat().replaceAll("'", "");
        	    String[] flowStatArr = flowStat.split(",");
        	    for (int i = 0; i < flowStatArr.length; i++) {
                    if(i == 0){
                        whereSql.append(" and flow_stat in (?");
                    }else if (i == flowStatArr.length - 1){
                        whereSql.append(",?)");
                    }else{
                        whereSql.append(",?");
                    }
                    parameters.add(flowStatArr[i]);
                }
        	}else{
        		whereSql.append(" and flow_stat=? ");
        		parameters.add(flowStepDO.getFlowStat());
        	}
        }
        if(!DataUtil.isNullStr(flowStepDO.getAppMsg())){
            /*添加查询条件：审批信息*/
            whereSql.append(" and app_msg=? ");
            parameters.add(flowStepDO.getAppMsg());
        }
        if(!DataUtil.isNullStr(flowStepDO.getCurProcUserId())){
            /*添加查询条件：处理用户ID*/
            whereSql.append(" and CUR_PROC_USER_ID=? ");
            parameters.add(flowStepDO.getCurProcUserId());
        }
        if(!DataUtil.isNullStr(flowStepDO.getCurProcUserName())){
            /*添加查询条件：处理用户名称*/
            whereSql.append(" and CUR_PROC_USER_NAME=? ");
            parameters.add(flowStepDO.getCurProcUserName());
        }
        if(!DataUtil.isNullStr(flowStepDO.getStrTime())){
            /*添加查询条件：开始时间*/
            whereSql.append(" and str_time=? ");
            parameters.add(flowStepDO.getStrTime());
        }
        if(!DataUtil.isNullStr(flowStepDO.getEndTime())){
            /*添加查询条件：结束时间*/
            whereSql.append(" and end_time=? ");
            parameters.add(flowStepDO.getEndTime());
        }
        if(!DataUtil.isNullStr(flowStepDO.getTimeOutProcUserId())){
            /*添加查询条件：超时处理用户ID*/
            whereSql.append(" and TIME_OUT_PROC_USER_ID=? ");
            parameters.add(flowStepDO.getTimeOutProcUserId());
        }
        if(!DataUtil.isNullStr(flowStepDO.getTimeOutProcUserName())){
            /*添加查询条件：超时处理用户名称*/
            whereSql.append(" and TIME_OUT_PROC_USER_NAME=? ");
            parameters.add(flowStepDO.getTimeOutProcUserName());
        }
        if(!DataUtil.isNullStr(flowStepDO.getCrtr())){
            /*添加查询条件：创建者*/
            whereSql.append(" and crtr=? ");
            parameters.add(flowStepDO.getCrtr());
        }
        if(!DataUtil.isNullStr(flowStepDO.getCrtTime())){
            /*添加查询条件：创建时间*/
            whereSql.append(" and crt_time=? ");
            parameters.add(flowStepDO.getCrtTime());
        }
        if(!DataUtil.isNullStr(flowStepDO.getUptr())){
            /*添加查询条件：更新者*/
            whereSql.append(" and uptr=? ");
            parameters.add(flowStepDO.getUptr());
        }
        if(!DataUtil.isNullStr(flowStepDO.getUptTime())){
            /*添加查询条件：更新时间*/
            whereSql.append(" and upt_time=? ");
            parameters.add(flowStepDO.getUptTime());
        }
        if(!DataUtil.isNullStr(flowStepDO.getRmrk())){
            /*添加查询条件：备注信息*/
            whereSql.append(" and rmrk=? ");
            parameters.add(flowStepDO.getRmrk());
        }
        if(!DataUtil.isNullStr(flowStepDO.getDac())){
            /*添加查询条件：DAC*/
            whereSql.append(" and dac=? ");
            parameters.add(flowStepDO.getDac());
        }
        return whereSql.toString();
    }


	/**
	 * 根据全局流水号+流程模板ID+步骤模板ID+步骤号+审批用户ID，检查是否存在对应的步骤实例
	 * @param globalSeq
	 * @param flowTmplId
	 * @param stepTmplId
	 * @param stepSer
	 * @param userId
	 * @return
	 */
	public boolean chkCurUser(String globalSeq, String flowTmplId, String stepTmplId, int stepSer,
			String userId) {
		// TODO Auto-generated method stub
		logger.debug("globalSeq=" + globalSeq+" , flowTmplId="+flowTmplId+" , stepTmplId="+stepTmplId+" , stepSer="+stepSer+" , userId="+userId);
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where global_seq=? and flow_tmpl_id=? and step_tmpl_id=? and step_ser=? and CUR_PROC_USER_ID=?");
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        logger.debug("countSql=" + countSql);
        IDBSession session = DBSessionFactory.getSession();
        int total = 0;
        try {
            total = session.account(countSql, globalSeq, flowTmplId, stepTmplId, stepSer, userId);
        } catch (Exception e) {
            logger.error("检查是否存在对应的步骤实例异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "检查是否存在对应的步骤实例失败！");
        }
		
		return total>0?true:false;
	}


	/**
	 * 获取步骤列表信息
	 * @param globalSeq
	 * @param flowTmplId
	 * @param stepSer
	 * @return
	 */
	public List<FlowStepDO> show(String globalSeq, String flowTmplId, int stepSer) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where global_seq=? and flow_tmpl_id=? and step_ser<=?");
        sql.append(" order by step_ser asc, end_time desc");
        logger.debug("sql=" + sql.toString());
        IDBSession session = DBSessionFactory.getSession();
        List<FlowStepDO> list = null;
        try {
             list = session.getObjectList(sql.toString(), FlowStepDO.class, globalSeq, flowTmplId, stepSer);
        } catch (Exception e) {
            logger.error("获取步骤列表信息异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "获取步骤列表信息失败！");
        }
        return list;
	}


	/**
	 * 获取当前用户的待审批流程步骤实例
	 * @param globalSeq		流程实例全局流水号
	 * @param flowTmplId	流程模板ID
	 * @param stepTmplId	步骤模板ID
	 * @param stepSer			步骤号
	 * @param curProcUserId		审批用户ID
	 * @return
	 */
	public FlowStepDO getByCurUser(String globalSeq, String flowTmplId, String stepTmplId, int stepSer, String curProcUserId) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where global_seq=? and flow_tmpl_id=? and step_tmpl_id=? and step_ser=? and CUR_PROC_USER_ID=?");
        logger.debug("sql=" + sql.toString());
        IDBSession session = DBSessionFactory.getSession();
        FlowStepDO rs =  null;
        try {
        	rs = session.getObject(sql.toString(), FlowStepDO.class, globalSeq, flowTmplId, stepTmplId, stepSer, curProcUserId);
        } catch (Exception e) {
            logger.error("查询流程步骤实例异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "查询流程步骤实例失败！");
        }
        return rs;
	}


	/**
	 * 获取所有设置了超时处理的待处理流程步骤实例列表
	 * @return
	 */
	public List<FlowStepDO> getFlowTimeOutFlgFlow() {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		sql.append("select step.* from ").append(TABLE_NAME).append(" step "); 
		sql.append("left join ").append(FlowDao.TABLE_NAME).append(" f on step.global_seq=f.global_seq ");
		sql.append("left join ").append(FlowTemplateDao.TABLE_NAME).append(" ft on step.flow_tmpl_id=ft.id ");
		sql.append("left join ").append(FlowStepTemplateDao.TABLE_NAME).append(" st on step.step_tmpl_id=st.id ");
		sql.append("where step.FLOW_STAT='01' and f.FLOW_STAT in('00','01') and (ft.day_num>0 or st.day_num>0) order by step.global_seq,step.flow_tmpl_id,step.step_tmpl_id,step.step_ser,step.str_time asc ");
        logger.debug("sql=" + sql.toString());
        IDBSession session = DBSessionFactory.getSession();
        List<FlowStepDO> list = null;
        try {
             list = session.getObjectList(sql.toString(), FlowStepDO.class);
        } catch (Exception e) {
            logger.error("获取步骤列表信息异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "获取所有设置了超时处理的待处理流程步骤实例列表失败！");
        }
        return list;
	}


	/**
	 * 删除待处理且超时处理id为空的步骤实例
	 * @param delObj
	 * @return
	 */
	public int deleteByTimeout(FlowStepDO delObj) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
        sql.append("delete from ").append(TABLE_NAME).append(" where global_seq=? and flow_tmpl_id=? and step_tmpl_id=? and step_ser=? and FLOW_STAT=?");
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.execute(sql.toString(), delObj.getGlobalSeq(), delObj.getFlowTmplId(), delObj.getStepTmplId(), delObj.getStepSer(), delObj.getFlowStat());
        } catch (Exception e) {
            logger.error("删除流程步骤实例异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "删除流程步骤实例失败！");
        }
        return rs;
	}


	/**
	 * @param updObj
	 */
	public int updateTimeOut(FlowStepDO obj) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
        sql.append("update ").append(TABLE_NAME).append(" set step_ser=? , FLOW_STAT=? , app_msg=? , end_time=? , TIME_OUT_PROC_USER_ID=? , TIME_OUT_PROC_USER_NAME=? where global_seq=? and flow_tmpl_id=? and step_tmpl_id=? and step_ser=?");
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.execute(sql.toString(), obj.getStepSer(), obj.getFlowStat(), obj.getAppMsg(), obj.getEndTime(), obj.getTimeOutProcUserId(), obj.getTimeOutProcUserName(), obj.getGlobalSeq(), obj.getFlowTmplId(), obj.getStepTmplId(), obj.getStepSer());
        } catch (Exception e) {
            logger.error("更新超时步骤实例异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "更新超时步骤实例失败！");
        }
        return rs;
	}
	
    public List<FlowStepDO> getFlowStep(FlowStepDO obj) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where global_seq=? and flow_tmpl_id=? and step_ser=?");
        logger.debug("sql=" + sql.toString());
        IDBSession session = DBSessionFactory.getSession();
        List<FlowStepDO> list = new ArrayList<FlowStepDO>();
        try {
             list = session.getObjectList(sql.toString(), FlowStepDO.class, obj.getGlobalSeq(), obj.getFlowTmplId(), obj.getStepSer());
        } catch (Exception e) {
            logger.error("获取步骤列表信息异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "获取步骤列表信息失败！");
        }
        return list;
    }
    
    public List<FlowStepDO> getEndFlowStep(FlowStepDO obj) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where global_seq=? and flow_tmpl_id=?")
                .append(" and step_ser=(select max(t.step_ser) from t_sys_flow_step t where t.global_seq = ? and t.flow_tmpl_id = ? ) ");
        sql.append(" order by step_ser desc");
        logger.debug("sql=" + sql.toString());
        IDBSession session = DBSessionFactory.getSession();
        List<FlowStepDO> list = new ArrayList<FlowStepDO>();
        try {
            list = session.getObjectList(sql.toString(), FlowStepDO.class, obj.getGlobalSeq(), obj.getFlowTmplId(),obj.getGlobalSeq(), obj.getFlowTmplId());
        } catch (Exception e) {
            logger.error("获取步骤列表信息异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "获取步骤列表信息失败！");
        }
        return list;
    }	

}
