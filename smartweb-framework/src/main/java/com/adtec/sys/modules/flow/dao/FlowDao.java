package com.adtec.sys.modules.flow.dao;

/**
 * 系统名称: SmartWeb平台
 * 模块名称:流程实例数据库操作类
 * 类  名  称: FlowDao.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl
 * 开发时间: 2018-05-07 20:02:25
 * 系统版本: V1.0.0
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================ *
 * ========     ======  ============================================
 */


import java.util.HashMap;
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
import com.adtec.sys.modules.flow.entity.FlowDO;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


@Component
public class FlowDao implements IBaseDao<FlowDO>{

    /*日志对象*/
    protected final static Logger logger = LoggerFactory.getLogger(FlowDao.class);
    /*表名称*/
    public static final String TABLE_NAME = "T_SYS_FLOW";


    @Override
    public int insert(FlowDO flowDO) {
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.saveObject(TABLE_NAME, flowDO, flowDO.getIgnoreFields());
        } catch (Exception e) {
            logger.error("新增流程实例异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "新增流程实例失败！");
        }
        return rs;
    }


    @Override
    public int update(FlowDO flowDO) {
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.updateObject(TABLE_NAME, flowDO, flowDO.getMatchFields(), flowDO.getIgnoreFields());
        } catch (Exception e) {
            logger.error("修改流程实例异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "修改流程实例失败！");
        }
        return rs;
    }


    @Override
    public int delete(FlowDO flowDO) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from ").append(TABLE_NAME).append(" where id= ? ");
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.execute(sql.toString(), flowDO.getId());
        } catch (Exception e) {
            logger.error("删除流程实例异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "删除流程实例失败！");
        }
        return rs;
    }
    
    public int delByGlobalSeq(String globalSeq) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from ").append(TABLE_NAME).append(" where global_seq= ? ");
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.execute(sql.toString(), globalSeq);
        } catch (Exception e) {
            logger.error("删除流程实例异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "删除流程实例失败！");
        }
        return rs;
    }


    public FlowDO get(String id) {
        FlowDO obj = new FlowDO();
        obj.setId(id);
        return get(obj);
    }


    public FlowDO get(FlowDO flowDO) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where id=? ");
        FlowDO rs = null;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObject(sql.toString(), FlowDO.class, flowDO.getId());
        } catch (Exception e) {
            logger.error("查询流程实例异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "查询流程实例失败！");
        }
        return rs;
    }

    /**
	 * 根据业务流水号获取流程信息
	 * @param globalSeq		业务流水号
	 * @return
	 */
    public FlowDO getByGlobalSeq(String globalSeq) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where global_seq=?");
        FlowDO rs = null;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObject(sql.toString(), FlowDO.class, globalSeq);
        } catch (Exception e) {
            logger.error("查询流程实例异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "查询流程实例失败！");
        }
        return rs;
    }

    public FlowDO getUndoneByTemplateId(String flowTmplId) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where flow_tmpl_id=? and FLOW_STAT not in ('03','02') ");
        FlowDO rs = null;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObject(sql.toString(), FlowDO.class, flowTmplId);
        } catch (Exception e) {
            logger.error("查询流程实例异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "查询流程实例失败！");
        }
        return rs;
    }

    @Override
    public List<FlowDO> list(FlowDO flowDO) {
        return list(flowDO, 0, 0);
    }


    @Override
    public List<FlowDO> list(FlowDO flowDO, int start, int limit) {
        logger.debug("flowDO=" + flowDO);
        logger.debug("start=" + start);
        logger.debug("limit=" + limit);
        StringBuilder sql = new StringBuilder();
        /* 20200117 mod by chenyl for SQL的拼接才成占位符的方式，防止SQL注入 */
        List<Object> parameters = Lists.newArrayList();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(flowDO, parameters));
        sql.append(" order by upt_time desc");
        logger.debug("sql=" + sql.toString());
        IDBSession session = DBSessionFactory.getSession();
        List<FlowDO> list = null;
        try {
            if (limit == 0) {
                list = session.getObjectListByList(sql.toString(), FlowDO.class, parameters);
            } else {
                list = session.getObjectListByListForPage(sql.toString(), FlowDO.class, start, limit, parameters);
            }
        } catch (Exception e) {
            logger.error("列表查询流程实例异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "列表查询流程实例失败！");
        }
        return list;
    }


    @Override
    public List<FlowDO> list(int start, int limit, Object... param) {
        return null;
    }

    /**
     * 查询当前用户有权限访问的流程实例列表
     * @param flowDO
     * @param start
     * @param limit
     * @return	TOT-总记录数，LIST-记录结果List<FlowDO>
     */
    public HashMap<String,Object> listByAuth(FlowDO flowDO, int start, int limit) {
        logger.debug("flowDO=" + flowDO);
        logger.debug("start=" + start);
        logger.debug("limit=" + limit);
        User user = UserUtils.getUser();
        /* 20200117 mod by chenyl for SQL的拼接才成占位符的方式，防止SQL注入 */
        List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from (");
        sql.append(" select t.* from ").append(TABLE_NAME).append(" t where t.snd_user_id = ? ");
        parameters.add(user.getId());
        sql.append(" or t.global_seq in (");
        sql.append(" 	select t2.global_seq from ").append(FlowStepDao.TABLE_NAME).append(" t2 where t2.flow_tmpl_id = t.flow_tmpl_id ");
        sql.append("	and t2.CUR_PROC_USER_ID = ? ");
        parameters.add(user.getId());
        sql.append(")) flow ").append(getWhereSql(flowDO, parameters, "flow"));
        // 统计记录数sql
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        // 添加排序
        sql.append(" order by flow.FLOW_STAT, flow.str_time desc");
        logger.debug("sql=" + sql.toString());
        IDBSession session = DBSessionFactory.getSession();
        HashMap<String,Object> retMap = Maps.newHashMap();
        int total = 0;
        List<FlowDO> list = null;
        try {
            if (limit == 0) {
                list = session.getObjectListByList(sql.toString(), FlowDO.class, parameters);
                total = list.size();
            } else {
                list = session.getObjectListByListForPage(sql.toString(), FlowDO.class, start, limit, parameters);
                logger.debug("countSql=" + countSql);
                total = session.accountByList(countSql, parameters);
            }
            retMap.put("TOT", total); // 设置返回的总记录数
            retMap.put("LIST", list);
        } catch (Exception e) {
            logger.error("列表查询流程实例异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "列表查询流程实例失败！");
        }
        return retMap;
    }
    
    public int getTotal(FlowDO flowDO) {
        logger.debug("flowDO=" + flowDO);
        StringBuilder sql = new StringBuilder();
        /* 20200117 mod by chenyl for SQL的拼接才成占位符的方式，防止SQL注入 */
        List<Object> parameters = Lists.newArrayList();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(flowDO, parameters));
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        logger.debug("countSql=" + countSql);
        IDBSession session = DBSessionFactory.getSession();
        int total = 0;
        try {
            total = session.accountByList(countSql, parameters);
        } catch (Exception e) {
            logger.error("总记录数查询流程实例异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "总记录数查询流程实例失败！");
        }
        return total;
    }
    
    /**
     * 根据流程模板ID检查是否存在未结束的流程实例
	 * @param flowDO
	 * @return
	 */ 
	public boolean chkFlowByTemplate(FlowDO flowDO) {
		// TODO Auto-generated method stub
		logger.debug("flowDO=" + flowDO);
		StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where flow_tmpl_id=? and FLOW_STAT=?");
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        logger.debug("countSql=" + countSql);
        IDBSession session = DBSessionFactory.getSession();
        int total = 0;
        try {
            total = session.account(countSql, flowDO.getFlowTmplId(), FlowDO.STAT_DEAL);
        } catch (Exception e) {
            logger.error("查询未结束流程实例异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "查询未结束流程实例失败！");
        }
        return total>0?true:false;
	}

	/**
	 * 根据传入的数据对象，对应属性值不空时拼接查询条件
	 * @param flowDO	流程数据对象
	 * @return
	 */
    private String getWhereSql(FlowDO flowDO, List<Object> parameters) {
    	if(null==parameters){
        	parameters = Lists.newArrayList();
        }
    	return getWhereSql(flowDO, parameters, "");
    }
    /**
     * 根据传入的数据对象，对应属性值不空时拼接查询条件
     * @param flowDO		流程数据对象
     * @param tableAlias	表别名
     * @return
     */
    private String getWhereSql(FlowDO flowDO, List<Object> parameters, String tableAlias) {
        StringBuffer whereSql = new StringBuffer(" where 1=1 ");
        if(!DataUtil.isNullStr(tableAlias)){
        	tableAlias += ".";
        }
        if(null==parameters){
        	parameters = Lists.newArrayList();
        }
        if(!DataUtil.isNullStr(flowDO.getId())){
            /*添加查询条件：编号*/
            whereSql.append(" and ").append(tableAlias).append("id=? ");
            parameters.add(flowDO.getId());
        }
        if(!DataUtil.isNullStr(flowDO.getGlobalSeq())){
            /*添加查询条件：全局流水号*/
            whereSql.append(" and ").append(tableAlias).append("global_seq=? ");
            parameters.add(flowDO.getGlobalSeq());
        }
        if(!DataUtil.isNullStr(flowDO.getFlowTmplId())){
            /*添加查询条件：流程模板ID*/
            whereSql.append(" and ").append(tableAlias).append("flow_tmpl_id=? ");
            parameters.add(flowDO.getFlowTmplId());
        }
        if(!DataUtil.isNullStr(flowDO.getInfoTitle())){
        	if(FlowDO.MQRY_Y.equals(flowDO.getmQry())){
        		/*标题描述模糊查询*/
        		whereSql.append(" and (").append(tableAlias).append("info_title like ?").append(" or ").append(tableAlias).append("flow_desc like ?)");
        		parameters.add("%"+flowDO.getInfoTitle()+"%");
        		parameters.add("%"+flowDO.getInfoTitle()+"%");
        	}else{
        		/*添加查询条件：流程标题*/
                whereSql.append(" and ").append(tableAlias).append("info_title=? ");
                parameters.add(flowDO.getInfoTitle());
        	}
        }
        if(!DataUtil.isNullStr(flowDO.getFlowDesc())){
            /*添加查询条件：流程描述*/
            whereSql.append(" and ").append(tableAlias).append("flow_desc=? ");
            parameters.add(flowDO.getFlowDesc());
        }
        if(!DataUtil.isNullStr(flowDO.getFlowStat())){
            /*添加查询条件：流程状态*/
            whereSql.append(" and ").append(tableAlias).append("FLOW_STAT=? ");
            parameters.add(flowDO.getFlowStat());
        }
        if(flowDO.getStepSer()>0){
            /*添加查询条件：当前步骤号*/
            whereSql.append(" and ").append(tableAlias).append("step_ser=? ");
            parameters.add(flowDO.getStepSer());
        }
        if(!DataUtil.isNullStr(flowDO.getSndUserId())){
            /*添加查询条件：发起用户ID*/
            whereSql.append(" and ").append(tableAlias).append("snd_user_id=? ");
            parameters.add(flowDO.getSndUserId());
        }
        if(!DataUtil.isNullStr(flowDO.getSndUserName())){
            /*添加查询条件：发起用户名*/
            whereSql.append(" and ").append(tableAlias).append("snd_user_name=? ");
            parameters.add(flowDO.getSndUserName());
        }
        if(!DataUtil.isNullStr(flowDO.getStrTime())){
            /*添加查询条件：开始时间*/
            whereSql.append(" and ").append(tableAlias).append("str_time=? ");
            parameters.add(flowDO.getStrTime());
        }
        if(!DataUtil.isNullStr(flowDO.getEndTime())){
            /*添加查询条件：结束时间*/
            whereSql.append(" and ").append(tableAlias).append("end_time=? ");
            parameters.add(flowDO.getEndTime());
        }
        if(!DataUtil.isNullStr(flowDO.getTimeOutProcUserId())){
            /*添加查询条件：超时处理用户ID*/
            whereSql.append(" and ").append(tableAlias).append("TIME_OUT_PROC_USER_ID=? ");
            parameters.add(flowDO.getTimeOutProcUserId());
        }
        if(!DataUtil.isNullStr(flowDO.getTimeOutProcUserName())){
            /*添加查询条件：超时处理用户名称*/
            whereSql.append(" and ").append(tableAlias).append("TIME_OUT_PROC_USER_NAME=? ");
            parameters.add(flowDO.getTimeOutProcUserName());
        }
        if(!DataUtil.isNullStr(flowDO.getCrtr())){
            /*添加查询条件：创建者*/
            whereSql.append(" and ").append(tableAlias).append("crtr=? ");
            parameters.add(flowDO.getCrtr());
        }
        if(!DataUtil.isNullStr(flowDO.getCrtTime())){
            /*添加查询条件：创建时间*/
            whereSql.append(" and ").append(tableAlias).append("crt_time=? ");
            parameters.add(flowDO.getCrtTime());
        }
        if(!DataUtil.isNullStr(flowDO.getUptr())){
            /*添加查询条件：更新者*/
            whereSql.append(" and ").append(tableAlias).append("uptr=? ");
            parameters.add(flowDO.getUptr());
        }
        if(!DataUtil.isNullStr(flowDO.getUptTime())){
            /*添加查询条件：更新时间*/
            whereSql.append(" and ").append(tableAlias).append("upt_time=? ");
            parameters.add(flowDO.getUptTime());
        }
        if(!DataUtil.isNullStr(flowDO.getRmrk())){
            /*添加查询条件：备注信息*/
            whereSql.append(" and ").append(tableAlias).append("rmrk=? ");
            parameters.add(flowDO.getRmrk());
        }
        if(!DataUtil.isNullStr(flowDO.getDac())){
            /*添加查询条件：DAC*/
            whereSql.append(" and ").append(tableAlias).append("dac=? ");
            parameters.add(flowDO.getDac());
        }
        return whereSql.toString();
    }


	/**
	 * 根据流程模板ID查询关联的流程实例数
	 * @param flowTmplId
	 * @return
	 */
	public int getInstByTemplateId(String flowTmplId) {
		// TODO Auto-generated method stub
		 logger.debug("根据流程模板ID查询关联的流程实例数，flowTmplId=" + flowTmplId);
	        StringBuilder sql = new StringBuilder();
	        sql.append("select * from ").append(TABLE_NAME).append(" where flow_tmpl_id=?");
	        String countSql = "select count(1) from (" + sql.toString() + ") ct";
	        logger.debug("countSql=" + countSql);
	        IDBSession session = DBSessionFactory.getSession();
	        int total = 0;
	        try {
	            total = session.account(countSql, flowTmplId);
	        } catch (Exception e) {
	            logger.error("总记录数查询流程实例异常："+ e.getMessage());
	            throw new BaseException(SysErr.E_MESSAGE, "总记录数查询流程实例失败！");
	        }
	        return total;
	}


	/**
	 * @param globalSeq
	 * @param stepSer
	 */
	public int updateStepSerByGlobalSeq(String globalSeq, int stepSer) {
		// TODO Auto-generated method stub
		int rs = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("update ").append(TABLE_NAME).append(" set step_ser=? ").append(" where global_seq=?");
		IDBSession session = DBSessionFactory.getSession();
		try {
			rs = session.execute(sql.toString(), stepSer, globalSeq);
		} catch (Exception e) {
			logger.error("修改流程实例异常：" + e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "修改流程实例失败！");
		}
		return rs;
	}


	/**
	 * @param obj
	 */
	public int updateTimeOut(FlowDO obj) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
        sql.append("update ").append(TABLE_NAME).append(" set step_ser=? , FLOW_STAT=? , end_time=? , TIME_OUT_PROC_USER_ID=? , TIME_OUT_PROC_USER_NAME=? where global_seq=?");
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.execute(sql.toString(), obj.getStepSer(), obj.getFlowStat(), obj.getEndTime(), obj.getTimeOutProcUserId(), obj.getTimeOutProcUserName(), obj.getGlobalSeq());
        } catch (Exception e) {
            logger.error("更新超时流程实例异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "更新超时流程实例失败！");
        }
        return rs;
	}


	public void appendDesc(String id, String flowDesc) {
		// TODO Auto-generated method stub
		 IDBSession session = DBSessionFactory.getSession();
	      try {
	    	  String sql = "update "+TABLE_NAME+" set flow_desc=? where id=?";
	    	  session.execute(sql.toString(), flowDesc, id);
	      } catch (Exception e) {
	            logger.error("追加备注异常："+ e.getMessage());
	            throw new BaseException(SysErr.E_MESSAGE, "追加备注失败！");
	      }
	}

}
