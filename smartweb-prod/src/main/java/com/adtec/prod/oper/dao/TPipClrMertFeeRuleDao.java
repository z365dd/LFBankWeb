/**
* 系统名称: SmartWeb平台
* 模块名称: comp.prod.oper持久化模块
* 功能描述: 单位清算规则表数据库操作
* 类 名 称  : TPipClrRuleDao.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200114<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.prod.oper.dao;

import com.adtec.prod.oper.entity.TPipClrMertFeeRuleDO;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.common.dao.IBaseDao;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.common.util.DataUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 单位清算规则表Dao接口
 * @author zh
 * @version 20200114
 */
@Component
public class TPipClrMertFeeRuleDao implements IBaseDao<TPipClrMertFeeRuleDO>{
	/**
	 * 日志对象
	 */
	protected final static Logger log = LoggerFactory.getLogger(TPipClrMertFeeRuleDao.class);
	/*表名称*/
    public static final String TABLE_NAME = "T_PIP_CLR_MERT_FEE_RULE";

	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	@Override
    public TPipClrMertFeeRuleDO get(TPipClrMertFeeRuleDO obj) {
        StringBuilder sql = new StringBuilder();
		sql.append("select * from ").append(TABLE_NAME).append(" where rule_id=?");
        TPipClrMertFeeRuleDO rs = null;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObject(sql.toString(), TPipClrMertFeeRuleDO.class, obj.getRuleId());
        } catch (Exception e) {
            log.error("获取单位清算规则表异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "获取单位清算规则表失败！");
        }
        return rs;
    }
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	@Override
	public int insert(TPipClrMertFeeRuleDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			rs = session.saveObject(TABLE_NAME, obj, ignoreFields);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("新增商户清算账号表交易异常", e);
            throw new BaseException(SysErr.E_MESSAGE, "新增商户清算账号表交易失败！");
		}
		return rs;
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	@Override
	public int update(TPipClrMertFeeRuleDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			List<String> matchFields = obj.getMatchFields();
			rs = session.updateObject(TABLE_NAME, obj, matchFields, ignoreFields);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("修改单位清算规则表异常", e);
            throw new BaseException(SysErr.E_MESSAGE, "修改单位清算规则表失败！");
		}
		return rs;
	}
	
	/**
	 * 根据主键id删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param id
	 * @see public int delete(T entity)
	 * @return
	 */
	public int delete(String id){
		TPipClrMertFeeRuleDO obj = new TPipClrMertFeeRuleDO();
		obj.setRuleId(id);
		return delete(obj);
	}
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param obj
	 * @return
	 */
	@Override
	public int delete(TPipClrMertFeeRuleDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			String sql = "DELETE FROM "+TABLE_NAME+" where rule_id=?";
			rs = session.execute(sql, obj.getRuleId());
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
	public List<TPipClrMertFeeRuleDO> list(TPipClrMertFeeRuleDO obj) {
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
	public List<TPipClrMertFeeRuleDO> list(TPipClrMertFeeRuleDO obj, int start, int limit) {
		log.debug("TPipClrMertFeeRuleDO=" + obj.toString());
		log.debug("start=" + start);
		log.debug("limit=" + limit);
		List<TPipClrMertFeeRuleDO> list = null;
		try {
			List<Object> parameters = Lists.newArrayList();
			StringBuilder sql = new StringBuilder();
        	sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        	log.debug("sql=" + sql.toString());
        	IDBSession session = DBSessionFactory.getSession();
			if (limit == 0) {
				list = session.getObjectListByList(sql.toString(), TPipClrMertFeeRuleDO.class, parameters);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), TPipClrMertFeeRuleDO.class, start, limit, parameters);
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
	public List<TPipClrMertFeeRuleDO> list(int start, int limit, Object... param) {
		return null;
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(TPipClrMertFeeRuleDO obj) {
		log.debug("TPipClrMertFeeRuleDO=" + obj);
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
        	log.error("总记录数查询t_pip_clr_rule异常：" + e.getMessage());
        	throw new BaseException(SysErr.E_MESSAGE, "总记录数查询t_pip_clr_rule失败！");
        }
        return total;
	}
	
	/**
	 * 根据数据对象产生对应的查询SQL
	 * @param obj 数据对象DO
	 * @return SQL
	 */
	public String getWhereSql(TPipClrMertFeeRuleDO obj, List<Object> parameters) {
		StringBuffer sql = new StringBuffer(" where 1=1 ");
		if ( !DataUtil.isNullStr(obj.getRuleId())) {
			sql.append(" AND rule_id = ? ");
			parameters.add(obj.getRuleId());
		}
		return sql.toString();
	}
	
}