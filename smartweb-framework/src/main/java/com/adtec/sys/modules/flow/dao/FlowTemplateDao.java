package com.adtec.sys.modules.flow.dao;

/**
 * 系统名称: SmartWeb平台
 * 模块名称:流程模板数据库操作类
 * 类  名  称: FlowTemplateDao.java
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
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.common.dao.IBaseDao;
import com.adtec.sys.modules.flow.entity.FlowTemplateDO;
import com.adtec.sys.modules.sys.entity.Office;
import com.adtec.sys.modules.sys.entity.Role;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.service.SystemService;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;


@Component
public class FlowTemplateDao implements IBaseDao<FlowTemplateDO>{

    /*日志对象*/
    protected final static Logger logger = LoggerFactory.getLogger(FlowTemplateDao.class);
    /*表名称*/
    public static final String TABLE_NAME = "T_SYS_FLOW_TEMPLATE";

    @Override
    public int insert(FlowTemplateDO flowTemplateDO) {
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.saveObject(TABLE_NAME, flowTemplateDO, flowTemplateDO.getIgnoreFields());
        } catch (Exception e) {
            logger.error("新增流程模板异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "新增流程模板失败！");
        }
        return rs;
    }


    @Override
    public int update(FlowTemplateDO flowTemplateDO) {
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.updateObject(TABLE_NAME, flowTemplateDO, flowTemplateDO.getMatchFields(), flowTemplateDO.getIgnoreFields());
        } catch (Exception e) {
            logger.error("修改流程模板异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "修改流程模板失败！");
        }
        return rs;
    }


    @Override
    public int delete(FlowTemplateDO flowTemplateDO) {
    	/* 20200117 mod by chenyl for SQL的拼接才成占位符的方式，防止SQL注入 */
        StringBuilder sql = new StringBuilder();
        sql.append("delete from ").append(TABLE_NAME).append(" where id= ? ");
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.execute(sql.toString(), flowTemplateDO.getId());
        } catch (Exception e) {
            logger.error("删除流程模板异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "删除流程模板失败！");
        }
        return rs;
    }


    public FlowTemplateDO get(String id) {
        FlowTemplateDO obj = new FlowTemplateDO();
        obj.setId(id);
        return get(obj);
    }


    public FlowTemplateDO get(FlowTemplateDO flowTemplateDO) {
    	/* 20200117 mod by chenyl for SQL的拼接才成占位符的方式，防止SQL注入 */
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where id=? ");
        FlowTemplateDO rs = new FlowTemplateDO();
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObject(sql.toString(), FlowTemplateDO.class, flowTemplateDO.getId());
        } catch (Exception e) {
            logger.error("查询流程模板异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "查询流程模板失败！");
        }
        return rs;
    }

    /**
	 * 根据业务唯一索引：英文名称+流程类型+流程版本，获取流程模板
	 * @param enname		英文名称
	 * @param flowTp		流程类型
	 * @param version		流程模板版本
	 * @return
	 */
	public FlowTemplateDO get(String enname, String flowTp, String version) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where eng_name=? and FLOW_TP=? and ver_no=?");
        FlowTemplateDO rs = null;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObject(sql.toString(), FlowTemplateDO.class, enname, flowTp, version);
        } catch (Exception e) {
            logger.error("根据业务唯一索引查询流程模板异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "根据业务唯一索引查询流程模板失败！");
        }
        return rs;
	}

    @Override
    public List<FlowTemplateDO> list(FlowTemplateDO flowTemplateDO) {
        return list(flowTemplateDO, 0, 0);
    }


    @Override
    public List<FlowTemplateDO> list(FlowTemplateDO flowTemplateDO, int start, int limit) {
        logger.debug("flowTemplateDO=" + flowTemplateDO);
        logger.debug("start=" + start);
        logger.debug("limit=" + limit);
        /* 20200117 mod by chenyl for SQL的拼接才成占位符的方式，防止SQL注入 */
        List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(flowTemplateDO, parameters));
        sql.append(" order by FLOW_TP asc, ver_no desc, eng_name asc");
        logger.debug("sql=" + sql.toString());
        IDBSession session = DBSessionFactory.getSession();
        List<FlowTemplateDO> list = null;
        try {
            if (limit == 0) {
                list = session.getObjectListByList(sql.toString(), FlowTemplateDO.class, parameters);
            } else {
                list = session.getObjectListByListForPage(sql.toString(), FlowTemplateDO.class, start, limit, parameters);
            }
        } catch (Exception e) {
            logger.error("列表查询流程模板异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "列表查询流程模板失败！");
        }
        return list;
    }


    @Override
    public List<FlowTemplateDO> list(int start, int limit, Object... param) {
        return null;
    }


    public int getTotal(FlowTemplateDO flowTemplateDO) {
        logger.debug("flowTemplateDO=" + flowTemplateDO);
        /* 20200117 mod by chenyl for SQL的拼接才成占位符的方式，防止SQL注入 */
        List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(flowTemplateDO, parameters));
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        logger.debug("countSql=" + countSql);
        IDBSession session = DBSessionFactory.getSession();
        int total = 0;
        try {
            total = session.accountByList(countSql, parameters);
        } catch (Exception e) {
            logger.error("总记录数查询流程模板异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "总记录数查询流程模板失败！");
        }
        return total;
    }

    /**
     * 更新流程模板状态：1-启用、2-挂起
     * @param flowStepTmplId
     * @param status
     * @return
     */
    public int updateStatus(String flowStepTmplId, String status) {
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
        	String sql = "update "+TABLE_NAME+" set FLOW_TMPL_STAT=? where id=?";
            rs = session.execute(sql, status, flowStepTmplId);
        } catch (Exception e) {
            logger.error("更新流程模板状态异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "更新流程模板状态失败！");
        }
        return rs;
    }
    
    /*根据传入的数据对象，对应属性值不空时拼接查询条件*/
    private String getWhereSql(FlowTemplateDO flowTemplateDO, List<Object> parameters) {
        StringBuffer whereSql = new StringBuffer(" where 1=1 ");
        if(null==parameters){
        	parameters = Lists.newArrayList();
        }
        if(!DataUtil.isNullStr(flowTemplateDO.getId())){
            /*添加查询条件：编号*/
            whereSql.append(" and id=? ");
            parameters.add(flowTemplateDO.getId());
        }
        if(!DataUtil.isNullStr(flowTemplateDO.getEngName())){
            /*添加查询条件：流程英文名称*/
            whereSql.append(" and eng_name=? ");
            parameters.add(flowTemplateDO.getEngName());
        }
        if(!DataUtil.isNullStr(flowTemplateDO.getName())){
            /*添加查询条件：中文名称*/
        	if(FlowTemplateDO.MQRY_Y.equals(flowTemplateDO.getmQry())){
        		/*中英文模糊查询*/
        		whereSql.append(" and (name like ? ").append(" or eng_name like ?) ");
        		parameters.add("%"+flowTemplateDO.getName()+"%");
        		parameters.add("%"+flowTemplateDO.getName()+"%");
        	}else{
        		whereSql.append(" and name like ? ");
        		parameters.add("%"+flowTemplateDO.getName()+"%");
        	}
        }
        if(!DataUtil.isNullStr(flowTemplateDO.getFlowTp())){
            /*添加查询条件：流程类型*/
            whereSql.append(" and FLOW_TP=? ");
            parameters.add(flowTemplateDO.getFlowTp());
        }
        if(!DataUtil.isNullStr(flowTemplateDO.getVerNo())){
            /*添加查询条件：版本*/
            whereSql.append(" and ver_no=? ");
            parameters.add(flowTemplateDO.getVerNo());
        }
        if(!DataUtil.isNullStr(flowTemplateDO.getFlowTmplStat())){
            /*添加查询条件：状态*/
            whereSql.append(" and FLOW_TMPL_STAT=? ");
            parameters.add(flowTemplateDO.getFlowTmplStat());
        }
        // 不参与者列表查询
        if(!DataUtil.isNullStr("" + flowTemplateDO.getDayNum()) && !FlowTemplateDO.MQRY_Y.equals(flowTemplateDO.getmQry())){
            /*添加查询条件：最长处理时间*/
            whereSql.append(" and day_num=? ");
            parameters.add(flowTemplateDO.getDayNum());
        }
        if(!DataUtil.isNullStr(flowTemplateDO.getFlowTimeOutFlg())){
            /*添加查询条件：超时处理*/
            whereSql.append(" and FLOW_TIME_OUT_FLG=? ");
            parameters.add(flowTemplateDO.getFlowTimeOutFlg());
        }
        if(!DataUtil.isNullStr(flowTemplateDO.getTimeOutFlowUserTp())){
            /*添加查询条件：超时处理用户类型*/
            whereSql.append(" and TIME_OUT_FLOW_USER_TP=? ");
            parameters.add(flowTemplateDO.getTimeOutFlowUserTp());
        }
        if(!DataUtil.isNullStr(flowTemplateDO.getTimeOutProcUserId())){
            /*添加查询条件：超时处理用户编码*/
            whereSql.append(" and TIME_OUT_PROC_USER_ID=? ");
            parameters.add(flowTemplateDO.getTimeOutProcUserId());
        }
        if(!DataUtil.isNullStr(flowTemplateDO.getSndFlowUserTp())){
            /*添加查询条件：发起用户类型*/
            whereSql.append(" and SND_FLOW_USER_TP=? ");
            parameters.add(flowTemplateDO.getSndFlowUserTp());
        }
        if(!DataUtil.isNullStr(flowTemplateDO.getSndUserId())){
            /*添加查询条件：发起用户编码*/
            whereSql.append(" and snd_user_id=? ");
            parameters.add(flowTemplateDO.getSndUserId());
        }
        if(!DataUtil.isNullStr(flowTemplateDO.getFlowProcClssTp())){
            /*添加查询条件：处理类*/
            whereSql.append(" and FLOW_PROC_CLSS_TP=? ");
            parameters.add(flowTemplateDO.getFlowProcClssTp());
        }
        if(!DataUtil.isNullStr(flowTemplateDO.getCrtr())){
            /*添加查询条件：创建者*/
            whereSql.append(" and crtr=? ");
            parameters.add(flowTemplateDO.getCrtr());
        }
        if(!DataUtil.isNullStr(flowTemplateDO.getCrtTime())){
            /*添加查询条件：创建时间*/
            whereSql.append(" and crt_time=? ");
            parameters.add(flowTemplateDO.getCrtTime());
        }
        if(!DataUtil.isNullStr(flowTemplateDO.getUptr())){
            /*添加查询条件：更新者*/
            whereSql.append(" and uptr=? ");
            parameters.add(flowTemplateDO.getUptr());
        }
        if(!DataUtil.isNullStr(flowTemplateDO.getUptTime())){
            /*添加查询条件：更新时间*/
            whereSql.append(" and upt_time=? ");
            parameters.add(flowTemplateDO.getUptTime());
        }
        if(!DataUtil.isNullStr(flowTemplateDO.getRmrk())){
            /*添加查询条件：备注信息*/
            whereSql.append(" and rmrk=? ");
            parameters.add(flowTemplateDO.getRmrk());
        }
        if(!DataUtil.isNullStr(flowTemplateDO.getDac())){
            /*添加查询条件：DAC*/
            whereSql.append(" and dac=? ");
            parameters.add(flowTemplateDO.getDac());
        }
        return whereSql.toString();
    }


	/**
	 * 获取流程模板下拉列表数据(当前用户有权限发起的)
	 * @param qryDO
	 * @return
	 */
	public List<FlowTemplateDO> listBySndUser(FlowTemplateDO qryDO) {
		// TODO Auto-generated method stub
		logger.debug("flowTemplateDO=" + qryDO);
		String sndUserId = qryDO.getSndUserId();
		User user = UserUtils.get(sndUserId);
		/* 20200117 mod by chenyl for SQL的拼接才成占位符的方式，防止SQL注入 */
        List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        // 首先查询出不限制发起用户和限制发起用户类型为用户的
        sql.append("select * from (select * from ").append(TABLE_NAME).append(" where SND_FLOW_USER_TP in (?) or SND_FLOW_USER_TP is null and snd_user_id=? or snd_user_id is null ) t ");
        parameters.add("user");
        parameters.add(sndUserId);
        if(!DataUtil.isNullStr(qryDO.getFlowTmplStat())){
        	sql.append(" where t.FLOW_TMPL_STAT=? ");
        	parameters.add(qryDO.getFlowTmplStat());
        }
        sql.append(" order by t.FLOW_TP asc, t.ver_no desc, t.eng_name asc");
        logger.debug("查询不限制发起用户和发起用户类型为用户的sql=" + sql.toString());
        IDBSession session = DBSessionFactory.getSession();
        List<FlowTemplateDO> list = null;
        try {
        	list = session.getObjectListByList(sql.toString(), FlowTemplateDO.class, parameters);
        	/*20191004 add by chenyl for 新增空判断*/
        	if(null==list){
        		list = Lists.newArrayList();
        	}
           
        	// 获取当前发起用用户所属机构有权限发起的流程
        	Office office = user.getOffice();
        	String offilceList = office.getParentIdList() + office.getId();
        	//offilceList = offilceList.replace(",", "','");
        	/* 20200117 mod by chenyl for SQL的拼接才成占位符的方式，防止SQL注入 */
            List<Object> parameters2 = Lists.newArrayList();
        	sql.delete(0, sql.length());
        	sql.append("select * from ").append(TABLE_NAME).append(" where SND_FLOW_USER_TP=? and snd_user_id in(");
        	parameters2.add("office");
        	if(!DataUtil.isNullStr(offilceList)){
        		String[] officeArr = offilceList.split(",");
        		for(int i=0;i<officeArr.length;i++){
                	if(i!=(officeArr.length-1)){
                		sql.append("?,");
                	}else{
                		sql.append("?");
                	} 
                	parameters2.add(officeArr[i]);
        		}
        	}else{
        		sql.append("?");
        		parameters2.add("");
        	}
        	sql.append(")");
        	if(!DataUtil.isNullStr(qryDO.getFlowTmplStat())){
            	sql.append(" and FLOW_TMPL_STAT=? ");
            	parameters2.add(qryDO.getFlowTmplStat());
            }
        	sql.append(" order by FLOW_TP asc, ver_no desc, eng_name asc");
            logger.debug("查询发起用户类型为机构的sql=" + sql.toString());
            List<FlowTemplateDO> list2 = session.getObjectListByList(sql.toString(), FlowTemplateDO.class, parameters2);
           if(null!=list2){
        	   list.addAll(list2);
           }
            
            // 获取当前发起用户所属角色有权限发起的流程
            SystemService systemService = SpringContextHolder.getBean("systemService");
            Role role = new Role();
            role.setUser(user);
            List<Role> roleList = systemService.findAllRoleWithUser(role);
            if(roleList.size() > 0){
            	/* 20200117 mod by chenyl for SQL的拼接才成占位符的方式，防止SQL注入 */
                List<Object> parameters3 = Lists.newArrayList();
            	sql.delete(0, sql.length());
            	sql.append("select * from ").append(TABLE_NAME).append(" where SND_FLOW_USER_TP=? and snd_user_id in(");
            	parameters3.add("role");
                for(int i=0;i<roleList.size();i++){
                	Role r = roleList.get(i);
                	if(i!=(roleList.size()-1)){
                		sql.append("?,");
                	}else{
                		sql.append("?");
                	}           	
                	parameters3.add(r.getId());
                }
                sql.append(")");
            	if(!DataUtil.isNullStr(qryDO.getFlowTmplStat())){
                	sql.append(" and FLOW_TMPL_STAT=? ");
                	parameters3.add(qryDO.getFlowTmplStat());
                }
            	sql.append(" order by FLOW_TP asc, ver_no desc, eng_name asc");
                logger.debug("查询发起用户类型为角色的sql=" + sql.toString());
                List<FlowTemplateDO> list3 = session.getObjectListByList(sql.toString(), FlowTemplateDO.class, parameters3);
                if(null!=list3){
                	list.addAll(list3);
                }
            }          
        } catch (Exception e) {
            logger.error("查询当前用户有权限发起的流程模板异常", e);
            throw new BaseException(SysErr.E_MESSAGE, "查询当前用户有权限发起的流程模板失败！");
        }
        return list;
	}
}
