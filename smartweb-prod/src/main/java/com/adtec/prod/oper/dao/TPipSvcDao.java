/**
* 系统名称: SmartWeb平台
* 模块名称: comp.prod.oper持久化模块
* 功能描述: 服务数据库操作
* 类 名 称  : TPipSvcDao.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200108<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.prod.oper.dao;

import com.adtec.prod.oper.definition.atom.entity.TPipAtomProdDO;
import com.adtec.prod.oper.definition.atom.entity.TPipAtomProdSvcDO;
import com.adtec.prod.oper.entity.TPipSvcDO;
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
 * 服务Dao接口
 * @author zh
 * @version 20200108
 */
@Component
public class TPipSvcDao implements IBaseDao<TPipSvcDO>{
	/**
	 * 日志对象
	 */
	protected final static Logger log = LoggerFactory.getLogger(TPipSvcDao.class);
	/*表名称*/
    public static final String TABLE_NAME = "t_pip_svc";
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public TPipSvcDO get(String id) {
		TPipSvcDO obj = new TPipSvcDO();
		obj.setId(id);
		return get(obj);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	@Override
    public TPipSvcDO get(TPipSvcDO obj) {
		List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
		sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        TPipSvcDO rs = null;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObjectByList(sql.toString(), TPipSvcDO.class, parameters);
        } catch (Exception e) {
            log.error("获取服务异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "获取服务失败！");
        }
        return rs;
    }
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	@Override
	public int insert(TPipSvcDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			// Y开通 N关闭
			obj.setOpenStat("N");
			List<String> ignoreFields = obj.getIgnoreFields();
			rs = session.saveObject(TABLE_NAME, obj, ignoreFields);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("新增服务交易异常", e);
            throw new BaseException(SysErr.E_MESSAGE, "新增服务交易失败！");
		}
		return rs;
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	@Override
	public int update(TPipSvcDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			List<String> matchFields = obj.getMatchFields();
			rs = session.updateObject(TABLE_NAME, obj, matchFields, ignoreFields);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("修改服务异常", e);
            throw new BaseException(SysErr.E_MESSAGE, "修改服务失败！");
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
		TPipSvcDO obj = new TPipSvcDO();
		obj.setId(id);
		return delete(obj);
	}
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param obj
	 * @return
	 */
	@Override
	public int delete(TPipSvcDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
//			List<TPipCompSvcParaDO> list = session.getObjectList("select * from t_pip_comp_svc_para where svc_code = ?", TPipCompSvcParaDO.class, obj.getSvcCode());
//			if (list.size() > 0) {
//				throw new BaseException(SysErr.E_MESSAGE, "该服务已关联属性，不能删除");
//			}
			List<TPipAtomProdDO> list = session.getObjectList("select * from t_pip_atom_prod where comp_no = ?", TPipAtomProdDO.class, obj.getCompNo());
			List<TPipAtomProdSvcDO> list2 = session.getObjectList("select * from t_pip_atom_prod_svc where svc_code = ?", TPipAtomProdSvcDO.class, obj.getSvcCode());
			if (list.size() > 0 && list2.size() > 0) {
				throw new BaseException(SysErr.E_MESSAGE, "该服务已关联原子产品，不能删除");
			}
			rs = session.deleteObject(TABLE_NAME, obj, obj.getMatchFields());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("删除交易异常", e);
			throw new BaseException(SysErr.E_MESSAGE, e.getMessage());
		}
		return rs;
	}
	
	/**
	 * 数据库多笔查询，不分页
	 * @param obj 数据对象DO
	 * @return List返回集合
	 */
	@Override
	public List<TPipSvcDO> list(TPipSvcDO obj) {
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
	public List<TPipSvcDO> list(TPipSvcDO obj, int start, int limit) {
		log.debug("TPipSvcDO=" + obj.toString());
		log.debug("start=" + start);
		log.debug("limit=" + limit);
		List<TPipSvcDO> list = null;
		try {
			List<Object> parameters = Lists.newArrayList();
			StringBuilder sql = new StringBuilder();
        	sql.append("select * from ").append(TABLE_NAME).append(getWhereSqlForLike(obj, parameters)).append(" ORDER BY COMP_NO, SVC_CODE");
        	log.debug("sql=" + sql.toString());
        	IDBSession session = DBSessionFactory.getSession();
			if (limit == 0) {
				list = session.getObjectListByList(sql.toString(), TPipSvcDO.class, parameters);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), TPipSvcDO.class, start, limit, parameters);
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
	public List<TPipSvcDO> list(int start, int limit, Object... param) {
		return null;
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(TPipSvcDO obj) {
		log.debug("TPipSvcDO=" + obj);
		List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSqlForLike(obj, parameters));
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        log.debug("countSql=" + countSql);
        IDBSession session = DBSessionFactory.getSession();       
		int total = 0;
        try {
        	total = session.accountByList(countSql, parameters);
        } catch (Exception e) {
        	log.error("总记录数查询t_pip_svc异常：" + e.getMessage());
        	throw new BaseException(SysErr.E_MESSAGE, "总记录数查询t_pip_svc失败！");
        }
        return total;
	}
	
	/**
	 * 根据数据对象产生对应的查询SQL
	 * @param obj 数据对象DO
	 * @return SQL
	 */
	public String getWhereSql(TPipSvcDO obj, List<Object> parameters) {
		StringBuffer sql = new StringBuffer(" where 1=1 ");
		if ( !DataUtil.isNullStr(obj.getCompNo())) {
            sql.append(" AND comp_no = ? ");
            parameters.add(obj.getCompNo());
		}
		if ( !DataUtil.isNullStr(obj.getSvcCode())) {
            sql.append(" AND svc_code = ? ");
            parameters.add(obj.getSvcCode());
		}
		return sql.toString();
	}

	public String getWhereSqlForLike(TPipSvcDO obj, List<Object> parameters) {
		StringBuffer sql = new StringBuffer(" where 1=1 ");
		if ( !DataUtil.isNullStr(obj.getCompNo())) {
			sql.append(" AND comp_no = ? ");
			parameters.add(obj.getCompNo());
		}
		if ( !DataUtil.isNullStr(obj.getSvcCode())) {
			sql.append(" AND svc_code like ? ");
			parameters.add("%"+obj.getSvcCode()+"%");
		}
		return sql.toString();
	}

}