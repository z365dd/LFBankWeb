package com.adtec.comp.sign.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.adtec.comp.sign.entity.FSignPipSignRuleDO;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.common.dao.IBaseDao;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;

@Component
public class FSignPipSignRuleDao implements IBaseDao<FSignPipSignRuleDO>{
	 /**
     * 日志对象
     */
    protected final static Logger logger = LoggerFactory.getLogger(FSignPipSignRuleDao.class);
    /*表名称*/
    public static final String TABLE_NAME = "T_PIP_SIGN_RULE";
    
    /**
     * 获取签约类型
     *
     * @param obj String
     * @return
     */
   
    public String getSignTp(FSignPipSignRuleDO obj, String ruleId) {
    	StringBuilder sql = new StringBuilder();
    	sql.append("select * from ").append(TABLE_NAME).append(" where 1 = 1 ").append(" and rule_id = '"+ruleId+"'");
    	FSignPipSignRuleDO rs = null;
    	IDBSession session = DBSessionFactory.getSession();
    	try {
    		rs = session.getObject(sql.toString(), FSignPipSignRuleDO.class);
    	} catch (Exception e) {
    		logger.error("获取权限维度异常：" + e.getMessage());
    		throw new BaseException(SysErr.E_MESSAGE, "获取权限维度失败！");
    	}
    	return rs.getSignTp();
    }
    
    /**
     * 获取签约ID名称
     *
     * @param obj String
     * @return
     */
   
    public String getSignName(FSignPipSignRuleDO obj, String ruleId) {
    	StringBuilder sql = new StringBuilder();
    	sql.append("select * from ").append(TABLE_NAME).append(" where 1 = 1 ").append(" and rule_id = '"+ruleId+"'");
    	FSignPipSignRuleDO rs = null;
    	IDBSession session = DBSessionFactory.getSession();
    	try {
    		rs = session.getObject(sql.toString(), FSignPipSignRuleDO.class);
    	} catch (Exception e) {
    		logger.error("获取权限维度异常：" + e.getMessage());
    		throw new BaseException(SysErr.E_MESSAGE, "获取权限维度失败！");
    	}
    	if(rs == null) {
    		return "";
    	}else {
    		return rs.getRuleDesc();
    	}
    }
    
	 /**
     * 数据库多笔查询，不分页
     *
     * @param obj 数据对象DO
     * @return List返回集合
     */
    @Override
    public List<FSignPipSignRuleDO> list(FSignPipSignRuleDO obj) {
        return list(obj, 0, 0);
    }

    /**
     * 数据库多笔查询，支持分页
     *
     * @param obj   数据对象DO
     * @param start 起始位置
     * @param limit 每页数量
     * @return List返回集合
     */
    @Override
    public List<FSignPipSignRuleDO> list(FSignPipSignRuleDO obj, int start, int limit) {
        logger.debug("FSignPipSignRuleDO=" + obj.toString());
        logger.debug("start=" + start);
        logger.debug("limit=" + limit);
        List<FSignPipSignRuleDO> list = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select * from ").append(TABLE_NAME).append(" where 1 = 1 and sign_flg = 'Y' ");
            if(obj.getRuleId()!=null &&!"".equals(obj.getRuleId())){
            	sql.append(" and rule_id ="+ obj.getRuleId());
            }
            
            if(obj.getRuleDesc()!=null &&!"".equals(obj.getRuleDesc())){
            	sql.append(" and rule_desc ="+ obj.getRuleDesc());
            }
           
            logger.debug("sql=" + sql.toString());
            IDBSession session = DBSessionFactory.getSession();
          
            if (limit == 0) {
                list = session.getObjectList(sql.toString(), FSignPipSignRuleDO.class);
            } else {
                list = session.getObjectListForPage(sql.toString(), FSignPipSignRuleDO.class, start, limit);
            }
        } catch (Exception e) {
            logger.error("列表查询异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "列表查询失败！");
        }
        return list;
    }
    
	@Override
	public int insert(FSignPipSignRuleDO objDO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(FSignPipSignRuleDO objDO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(FSignPipSignRuleDO objDO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public FSignPipSignRuleDO get(FSignPipSignRuleDO objDO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FSignPipSignRuleDO> list(int start, int limit, Object... param) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
