package com.adtec.prod.oper.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.adtec.prod.oper.entity.SquareRulesDO;
import com.adtec.framework.impl.share.caseStrategy.MBCCaseStrategy;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.common.dao.IBaseDao;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.common.util.DataUtil;
@Component
public class SquareRulesDao implements IBaseDao<SquareRulesDO>{
	 /**
     * 日志对象
     */
    protected final static Logger logger = LoggerFactory.getLogger(SquareRulesDao.class);
    /*表名称*/
    public static final String TABLE_NAME = "T_PIP_RULE_RELAT";//规则关系表
    public static final String TABLE_NAME_CLR = "T_PIP_CLR_RULE";//清算关系表

	@Override
	public int insert(SquareRulesDO objDO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(SquareRulesDO objDO) {
		logger.debug("SquareRulesDO=" + objDO);
		StringBuilder sql = new StringBuilder();
		IDBSession session = DBSessionFactory.getSession();
		 List<String> getIgnoreFields = objDO.getIgnoreFields();
		 List<String> matchFields = new ArrayList<String>();
		 matchFields.add("RULE_ID");
		 int rs;
		 try {
			 rs=session.updateObject(TABLE_NAME_CLR, objDO, matchFields, getIgnoreFields, new MBCCaseStrategy());
		} catch (SQLException e) {
			logger.error("配置修改异常" + e.getMessage()); 
			throw new BaseException(SysErr.E_MESSAGE, "配置修改异常！");		}
		return rs;
	}

	@Override
	public int delete(SquareRulesDO objDO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SquareRulesDO get(SquareRulesDO obj) {
		logger.debug("SquareRulesDO=" + obj.toString());
		
		SquareRulesDO resDo = new SquareRulesDO();
		 StringBuilder sql = new StringBuilder();	
		 sql.append("select * from " + TABLE_NAME_CLR);
		 sql .append(" where 1 = 1 ");
		 
		 
		 if(!DataUtil.isNullStr(obj.getRULE_ID())){
			 sql.append(" and RULE_ID = ?");
		 }
		 IDBSession session = DBSessionFactory.getSession();
		 try{
			 if(!DataUtil.isNullStr(obj.getRULE_ID())){
				 resDo	= session.getObject(sql.toString(), SquareRulesDO.class, new MBCCaseStrategy(),obj.getRULE_ID());
			 } else {
				 resDo	= session.getObject(sql.toString(), SquareRulesDO.class, new MBCCaseStrategy());
			 }
		 }catch(Exception e){
			 logger.error("查询异常" + e.getMessage()); 
				throw new BaseException(SysErr.E_MESSAGE, "查询异常！");	
		 }
		 return resDo;
	}

	@Override
	public List<SquareRulesDO> list(SquareRulesDO objDO) {
		// TODO Auto-generated method stub
		return list(objDO,0,0);
	}

	@Override
	public List<SquareRulesDO> list(SquareRulesDO obj, int start, int limit) {
		logger.debug("SquareRulesDO=" + obj.toString());
        logger.debug("start=" + start);
        logger.debug("limit=" + limit);		
        List<SquareRulesDO> list=null;
        try {
        	List<Object> parameters = Lists.newArrayList();
            StringBuilder sql = new StringBuilder();
            sql.append("select a.*,b.BUSI_NAME from ").append(TABLE_NAME);
            sql.append(" a left join T_PIP_BUSI b ");
            sql.append(" on a.BUSI_NO = b.BUSI_NO ");
            sql.append(" where 1 = 1 ");
            sql.append(" and a.RULE_TP in ('201','202') ");
            if(!DataUtil.isNullStr(obj.getBUSI_NO())){
            	sql.append("and a.BUSI_NO = ?");
            	parameters.add(obj.getBUSI_NO());
            }
            IDBSession session = DBSessionFactory.getSession();
            logger.debug("sql=" + sql.toString());
            if (limit == 0) {
                list = session.getObjectListByList(sql.toString(), SquareRulesDO.class, parameters, new MBCCaseStrategy());
            } else {
                list = session.getObjectListByListForPage(sql.toString(), SquareRulesDO.class, start, limit, parameters, new MBCCaseStrategy());
            }
        }catch(Exception e){
        	logger.error("查询异常" + e.getMessage()); 
			throw new BaseException(SysErr.E_MESSAGE, "查询异常！");	
        }
        return list;
	}

	@Override
	public List<SquareRulesDO> list(int start, int limit, Object... param) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 获取总条数
	 * @param obj
	 * @return
	 */
	public int getTotal(SquareRulesDO obj) {
		 int count =0;
		 try {
		 	List<Object> parameters = Lists.newArrayList();
	            StringBuilder sql = new StringBuilder();
	            sql.append("select count(1) from ( ");
	            sql.append("select a.*,b.BUSI_NAME from ").append(TABLE_NAME);
	            sql.append(" a left join T_PIP_BUSI b ");
	            sql.append(" on a.BUSI_NO = b.BUSI_NO ");
	            sql.append(" where 1 = 1 ");
	            sql.append(" and a.RULE_TP in ('201','202') ");
	            if(!DataUtil.isNullStr(obj.getBUSI_NO())){
	            	sql.append("and a.BUSI_NO = ?");
	            	parameters.add(obj.getBUSI_NO());
	            }
	            sql.append(" ) ct");
	            IDBSession session = DBSessionFactory.getSession();
	            logger.debug("sql=" + sql.toString());
	            count =  session.accountByList(sql.toString(), parameters);
	        }catch(Exception e){
	        	logger.error("查询异常" + e.getMessage()); 
				throw new BaseException(SysErr.E_MESSAGE, "查询异常！");	
	        }
		return count;
	}

}
