/**
* 系统名称: SmartWeb平台
* 模块名称: comp-prod/oper/definition/atom持久化模块
* 功能描述: 原子产品服务关联数据库操作
* 类 名 称  : TPipAtomProdSvcDao.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200103<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.prod.oper.definition.atom.dao;

import java.sql.SQLException;
import java.util.List;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.common.dao.IBaseDao;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.prod.oper.definition.atom.entity.TPipAtomProdSvcDO;

/**
 * 原子产品服务关联Dao接口
 * @author zengxj
 * @version 20200103
 */
@Component
public class TPipAtomProdSvcDao implements IBaseDao<TPipAtomProdSvcDO>{
	/**
	 * 日志对象
	 */
	protected final static Logger logger = LoggerFactory.getLogger(TPipAtomProdSvcDao.class);
	/*表名称*/
    public static final String TABLE_NAME = "t_pip_atom_prod_svc";
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public TPipAtomProdSvcDO get(String id) {
		TPipAtomProdSvcDO obj = new TPipAtomProdSvcDO();
		obj.setId(id);
		return get(obj);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	@Override
    public TPipAtomProdSvcDO get(TPipAtomProdSvcDO obj) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where id=? AND del_flag='" + TPipAtomProdSvcDO.DEL_FLAG_NORMAL + "'");
        TPipAtomProdSvcDO rs = null;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObject(sql.toString(), TPipAtomProdSvcDO.class, obj.getId());
        } catch (Exception e) {
            logger.error("获取原子产品服务关联异常："+e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "获取原子产品服务关联失败！");
        }
        return rs;
    }
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	@Override
	public int insert(TPipAtomProdSvcDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			ignoreFields.add("shortRmrk");
			ignoreFields.add("midRmrk");
			ignoreFields.add("longRmrk");
			ignoreFields.add("dac");
			rs = session.saveObject(TABLE_NAME, obj, ignoreFields);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("新增原子产品服务关联交易异常："+e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "新增原子产品服务关联交易失败！");
		}
		return rs;
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	@Override
	public int update(TPipAtomProdSvcDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			ignoreFields.add("atomProdCode");
			ignoreFields.add("svcCode");
			ignoreFields.add("svcDesc");
			ignoreFields.add("subSvcCode");
			ignoreFields.add("subSvcDesc");
			ignoreFields.add("shortRmrk");
			ignoreFields.add("midRmrk");
			ignoreFields.add("longRmrk");
			ignoreFields.add("dac");
			List<String> matchFields = obj.getMatchFields();
			rs = session.updateObject(TABLE_NAME, obj, matchFields, ignoreFields);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("修改原子产品服务关联异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "修改原子产品服务关联失败！");
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
		TPipAtomProdSvcDO obj = new TPipAtomProdSvcDO();
		obj.setAtomProdCode(id);;
		return delete(obj);
	}
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param obj
	 * @return
	 */
	@Override
	public int delete(TPipAtomProdSvcDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			String sql = "DELETE FROM t_pip_atom_prod_svc where atom_prod_code=?";
			rs = session.execute(sql, obj.getAtomProdCode());
//			if(rs==0){
//				throw new BaseException(SysErr.E_MESSAGE, "删除交易失败，影响记录数："+rs);
//			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("删除交易异常："+e.getMessage());
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
	public List<TPipAtomProdSvcDO> list(TPipAtomProdSvcDO obj) {
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
	public List<TPipAtomProdSvcDO> list(TPipAtomProdSvcDO obj, int start, int limit) {
		logger.debug("TPipAtomProdSvcDO=" + obj.toString());
		logger.debug("start=" + start);
		logger.debug("limit=" + limit);
		List<TPipAtomProdSvcDO> list = null;
		try {
			StringBuilder sql = new StringBuilder();
			List<Object> params = Lists.newArrayList();
        	sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, params));
//        	sql.append(" order by update_date desc");
        	logger.debug("sql=" + sql.toString());
        	IDBSession session = DBSessionFactory.getSession();
			if (limit == 0) {
				list = session.getObjectListByList(sql.toString(), TPipAtomProdSvcDO.class, params);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), TPipAtomProdSvcDO.class, start, limit, params);
			}
		} catch (Exception e) {
			logger.error("列表查询异常：" + e.getMessage());
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
	public List<TPipAtomProdSvcDO> list(int start, int limit, Object... param) {
		return null;
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(TPipAtomProdSvcDO obj) {
		logger.debug("TPipAtomProdSvcDO=" + obj);
        StringBuilder sql = new StringBuilder();
		List<Object> params = Lists.newArrayList();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, params));
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        logger.debug("countSql=" + countSql);
        IDBSession session = DBSessionFactory.getSession();       
		int total = 0;
        try {
        	total = session.accountByList(countSql, params);
        } catch (Exception e) {
        	logger.error("总记录数查询t_pip_atom_prod_svc异常：" + e.getMessage());
        	throw new BaseException(SysErr.E_MESSAGE, "总记录数查询t_pip_atom_prod_svc失败！");
        }
        return total;
	}
	
	/**
	 * 根据数据对象产生对应的查询SQL
	 * @param obj 数据对象DO
	 * @return SQL
	 */
	public String getWhereSql(TPipAtomProdSvcDO obj, List<Object> params) {
		StringBuffer sql = new StringBuffer(" where 1=1 ");
		if (!DataUtil.isNullStr(obj.getAtomProdCode())) {
			sql.append(" and atom_prod_code = ?");
			params.add(obj.getAtomProdCode());
		}
		return sql.toString();
	}
	
}