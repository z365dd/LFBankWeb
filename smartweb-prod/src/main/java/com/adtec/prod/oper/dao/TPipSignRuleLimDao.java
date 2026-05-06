/**
* 系统名称: SmartWeb平台
* 模块名称: prod-oper持久化模块
* 功能描述: 签约默认限额表数据库操作
* 类 名 称  : TPipSignRuleLimDao.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200310<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.prod.oper.dao;

import java.lang.Exception;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.common.dao.IBaseDao;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.common.util.DataUtil;
import com.google.common.collect.Lists;
import com.adtec.prod.oper.entity.TPipSignRuleLimDO;

/**
 * 签约默认限额表Dao接口
 * @author linyx
 * @version 20200310
 */
@Component
public class TPipSignRuleLimDao implements IBaseDao<TPipSignRuleLimDO>{
	/**
	 * 日志对象
	 */
	protected final static Logger log = LoggerFactory.getLogger(TPipSignRuleLimDao.class);
	/*表名称*/
    public static final String TABLE_NAME = "t_pip_sign_rule_lim";
    
	/**
	 * 获取单条数据
	 * @param ruleId
	 * @param chnlNo
	 * @param limTp
	 * @return
	 */
	public TPipSignRuleLimDO get(String ruleId, String chnlNo, String limTp ) {
		TPipSignRuleLimDO obj = new TPipSignRuleLimDO();
	 	obj.setRuleId(ruleId);
	 	obj.setChnlNo(chnlNo);
	 	obj.setLimTp(limTp);
		return get(obj);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	@Override
    public TPipSignRuleLimDO get(TPipSignRuleLimDO obj) {
        StringBuilder sql = new StringBuilder();
        List<Object> parameters = Lists.newArrayList();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        TPipSignRuleLimDO rs = null;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObjectByList(sql.toString(), TPipSignRuleLimDO.class, parameters);
        } catch (Exception e) {
            log.error("获取签约默认限额表异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "获取签约默认限额表失败！");
        }
        return rs;
    }
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	@Override
	public int insert(TPipSignRuleLimDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			rs = session.saveObject(TABLE_NAME, obj, ignoreFields);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("新增签约默认限额表交易异常", e);
            throw new BaseException(SysErr.E_MESSAGE, "新增签约默认限额表交易失败！");
		}
		return rs;
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	@Override
	public int update(TPipSignRuleLimDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			List<String> matchFields = obj.getMatchFields();
			rs = session.updateObject(TABLE_NAME, obj, matchFields, ignoreFields);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("修改签约默认限额表异常", e);
            throw new BaseException(SysErr.E_MESSAGE, "修改签约默认限额表失败！");
		}
		return rs;
	}
	
	/**
	 * 根据主键删除数据
	 * @param ruleId
	 * @param chnlNo
	 * @param limTp
	 * @see public int delete(T entity)
	 * @return
	 */
	public int delete(String ruleId, String chnlNo, String limTp ){
		TPipSignRuleLimDO obj = new TPipSignRuleLimDO();
	 	obj.setRuleId(ruleId);
	 	obj.setChnlNo(chnlNo);
	 	obj.setLimTp(limTp);
		return delete(obj);
	}
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param obj
	 * @return
	 */
	@Override
	public int delete(TPipSignRuleLimDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			List<Object> parameters = Lists.newArrayList();
			String sql = "DELETE FROM t_pip_sign_rule_lim "+getWhereSql(obj, parameters);
			rs = session.executeByList(sql, parameters);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("删除交易异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "删除交易失败！");
		}
		return rs;
	}
	
	/**
	 * 数据库多笔查询，不分页
	 * @param obj 数据对象DO
	 * @return List返回集合
	 */
	@Override
	public List<TPipSignRuleLimDO> list(TPipSignRuleLimDO obj) {
		// TODO Auto-generated method stub
		return list(obj, 0, 0);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param obj 数据对象DO
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @return List返回集合
	 */
	@Override
	public List<TPipSignRuleLimDO> list(TPipSignRuleLimDO obj, int start, int limit) {
		log.debug("TPipSignRuleLimDO=" + obj.toString());
		log.debug("start=" + start);
		log.debug("limit=" + limit);
		List<TPipSignRuleLimDO> list = null;
		try {
			List<Object> parameters = Lists.newArrayList();
			StringBuilder sql = new StringBuilder();
        	sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        	log.debug("sql=" + sql.toString());
        	IDBSession session = DBSessionFactory.getSession();
			if (limit <= 0) {
				list = session.getObjectListByList(sql.toString(), TPipSignRuleLimDO.class, parameters);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), TPipSignRuleLimDO.class, start, limit, parameters);
			}
		} catch (Exception e) {
			log.error("列表查询异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "列表查询失败！");
		}
		return list;
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @param param 查询参数
	 * @return List返回集合
	 */
	@Override
	public List<TPipSignRuleLimDO> list(int start, int limit, Object... param) {
		return null;
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(TPipSignRuleLimDO obj) {
		log.debug("TPipSignRuleLimDO=" + obj);
		List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        log.debug("countSql=" + countSql);
        IDBSession session = DBSessionFactory.getSession();       
		int total = 0;
        try {
        	total = session.accountByList(countSql, parameters);
        } catch (Exception e) {
        	log.error("总记录数查询t_pip_sign_rule_lim异常：" + e.getMessage());
        	throw new BaseException(SysErr.E_MESSAGE, "总记录数查询t_pip_sign_rule_lim失败！");
        }
        return total;
	}
	
	/**
	 * 根据数据对象产生对应的查询SQL
	 * @param obj 数据对象DO
	 * @return SQL
	 */
	public String getWhereSql(TPipSignRuleLimDO obj, List<Object> parameters) {
		if(null==parameters){
        	parameters = Lists.newArrayList();
        }
		StringBuffer sql = new StringBuffer(" where 1=1 ");
		if ( !DataUtil.isNullStr(obj.getRuleId())) {
			sql.append(" AND rule_id = ? ");
			parameters.add(obj.getRuleId());
		}
		if ( !DataUtil.isNullStr(obj.getChnlNo())) {
			sql.append(" AND chnl_no = ? ");
			parameters.add(obj.getChnlNo());
		}
		if ( !DataUtil.isNullStr(obj.getLimTp())) {
			sql.append(" AND lim_tp = ? ");
			parameters.add(obj.getLimTp());
		}
		return sql.toString();
	}
	
}