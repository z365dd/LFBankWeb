package com.adtec.sys.modules.sys.dao;

import java.lang.Exception;
import java.util.List;

import com.adtec.sys.modules.sys.entity.Dict;
import com.adtec.sys.modules.sys.utils.DictUtils;
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
import com.adtec.sys.modules.sys.entity.TSysDictDO;

/**
 * 页面参数Dao接口
 * @author zh
 * @version 20200630
 */
@Component
public class TSysDictDao implements IBaseDao<TSysDictDO>{
	/**
	 * 日志对象
	 */
	protected final static Logger log = LoggerFactory.getLogger(TSysDictDao.class);
	/*表名称*/
    public static final String TABLE_NAME = "t_sys_dict";

	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	@Override
    public TSysDictDO get(TSysDictDO obj) {
        StringBuilder sql = new StringBuilder();
        List<Object> parameters = Lists.newArrayList();
        sql.append("select distinct dict_tp, dict_info from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        TSysDictDO rs = null;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObjectByList(sql.toString(), TSysDictDO.class, parameters);
        } catch (Exception e) {
            log.error("获取页面参数异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "获取页面参数失败！");
        }
        return rs;
    }

	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	public TSysDictDO getVal(TSysDictDO obj) {
		StringBuilder sql = new StringBuilder();
		List<Object> parameters = Lists.newArrayList();
		sql.append("select * from ").append(TABLE_NAME).append(getWhereSqlVal(obj, parameters));
		TSysDictDO rs = null;
		IDBSession session = DBSessionFactory.getSession();
		try {
			rs = session.getObjectByList(sql.toString(), TSysDictDO.class, parameters);
		} catch (Exception e) {
			log.error("获取页面参数异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "获取页面参数失败！");
		}
		return rs;
	}

	public TSysDictDO getSort(TSysDictDO obj) {
		StringBuilder sql = new StringBuilder();
		List<Object> parameters = Lists.newArrayList();
		sql.append("select * from ").append(TABLE_NAME).append(" where dict_tp = ? order by sort desc ");
		parameters.add(obj.getDictTp());
		TSysDictDO rs = null;
		IDBSession session = DBSessionFactory.getSession();
		try {
			rs = session.getObjectByList(sql.toString(), TSysDictDO.class, parameters);
		} catch (Exception e) {
			log.error("获取页面参数异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "获取页面参数失败！");
		}
		return rs;
	}

	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	@Override
	public int insert(TSysDictDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			ignoreFields.add("parentId");
			ignoreFields.add("dictVal");
			ignoreFields.add("dictLabel");
			ignoreFields.add("sort");
			rs = session.saveObject(TABLE_NAME, obj, ignoreFields);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("新增页面参数交易异常", e);
            throw new BaseException(SysErr.E_MESSAGE, "新增页面参数交易失败！");
		}
		return rs;
	}

	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	public int insertVal(TSysDictDO obj){
		obj.setDelFlg("0");
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			TSysDictDO temp = new TSysDictDO();
			temp.setDictTp(obj.getDictTp());
			TSysDictDO tSysDictDO = getVal(temp);
			// 查到的记录为空
			if (tSysDictDO == null) {
				insertValJustUpdate(obj);
			} else {
				List<String> ignoreFields = obj.getIgnoreFields();
				ignoreFields.remove("delFlg");
				ignoreFields.add("parentId");
				obj.setDictTp(tSysDictDO.getDictTp());
				obj.setDictInfo(tSysDictDO.getDictInfo());
				rs = session.saveObject(TABLE_NAME, obj, ignoreFields);
			}

			Dict dict = new Dict();
			dict.setDictTp(obj.getDictTp());
			dict.setValue(obj.getDictVal());
			dict.setLabel(obj.getDictLabel());
			dict.setSort(Math.toIntExact(obj.getSort()));
			dict.setId(obj.getId());
			dict.setDictInfo(obj.getDictInfo());
			DictUtils.insertDictInCache(dict);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("新增页面参数交易异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "新增页面参数交易失败！");
		}
		return rs;
	}

	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	public int insertValJustUpdate(TSysDictDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			ignoreFields.remove("delFlg");
			ignoreFields.add("parentId");
			ignoreFields.add("id");
			ignoreFields.add("dictTp");
			ignoreFields.add("dictInfo");
			List<String> matchFields = obj.getMatchFields();
			rs = session.updateObject(TABLE_NAME, obj, matchFields, ignoreFields);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("修改页面参数异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "修改页面参数失败！");
		}
		return rs;
	}

	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	@Override
	public int update(TSysDictDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			ignoreFields.add("parentId");
			ignoreFields.add("id");
			ignoreFields.add("dictVal");
			ignoreFields.add("dictLabel");
			ignoreFields.add("sort");
			List<String> matchFields = obj.getMatchFields();
			rs = session.updateObject(TABLE_NAME, obj, matchFields, ignoreFields);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("修改页面参数异常", e);
            throw new BaseException(SysErr.E_MESSAGE, "修改页面参数失败！");
		}
		return rs;
	}

	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	public int updateVal(TSysDictDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			ignoreFields.add("parentId");
			ignoreFields.add("id");
			ignoreFields.add("dictTp");
			ignoreFields.add("dictInfo");
			List<String> matchFields = obj.getMatchFields();
			matchFields.add("dictVal");
			rs = session.updateObject(TABLE_NAME, obj, matchFields, ignoreFields);

			Dict dict = new Dict();
			dict.setDictTp(obj.getDictTp());
			dict.setValue(obj.getDictVal());
			dict.setLabel(obj.getDictLabel());
			dict.setSort(Math.toIntExact(obj.getSort()));
			dict.setId(obj.getId());
			dict.setDictInfo(obj.getDictInfo());
			DictUtils.updateDictInCache(dict);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("修改页面参数异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "修改页面参数失败！");
		}
		return rs;
	}

	/**
	 * 删除数据（一般为逻辑删除，更新del_flg字段为1）
	 * @param obj
	 * @return
	 */
	@Override
	public int delete(TSysDictDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			List<Object> parameters = Lists.newArrayList();
			String sql = "DELETE FROM t_sys_dict "+getWhereSql(obj, parameters);
			rs = session.executeByList(sql, parameters);

			Dict dict = new Dict();
			dict.setDictTp(obj.getDictTp());
			DictUtils.deleteDictInCache(dict);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("删除交易异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "删除交易失败！");
		}
		return rs;
	}

	/**
	 * 删除数据（一般为逻辑删除，更新del_flg字段为1）
	 * @param obj
	 * @return
	 */
	public int deleteVal(TSysDictDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			List<Object> parameters = Lists.newArrayList();
			String sql = "DELETE FROM t_sys_dict "+getWhereSqlVal(obj, parameters);
			rs = session.executeByList(sql, parameters);

			Dict dict = new Dict();
			dict.setDictTp(obj.getDictTp());
			dict.setValue(obj.getDictVal());
			DictUtils.deleteDictInCache(dict);
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
	public List<TSysDictDO> list(TSysDictDO obj) {
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
	public List<TSysDictDO> list(TSysDictDO obj, int start, int limit) {
		log.debug("TSysDictDO=" + obj.toString());
		log.debug("start=" + start);
		log.debug("limit=" + limit);
		List<TSysDictDO> list = null;
		try {
			List<Object> parameters = Lists.newArrayList();
			StringBuilder sql = new StringBuilder();
        	sql.append("select distinct dict_tp, dict_info from ").append(TABLE_NAME).append(getWhereSql(obj, parameters)).append(" order by dict_tp ");
        	IDBSession session = DBSessionFactory.getSession();
			if (limit <= 0) {
				list = session.getObjectListByList(sql.toString(), TSysDictDO.class, parameters);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), TSysDictDO.class, start, limit, parameters);
			}
		} catch (Exception e) {
			log.error("列表查询异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "列表查询失败！");
		}
		return list;
	}

	/**
	 * 数据库多笔查询，支持分页
	 * @param obj 数据对象DO
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @return List返回集合
	 */
	public List<TSysDictDO> listVal(TSysDictDO obj, int start, int limit) {
		log.debug("TSysDictDO=" + obj.toString());
		log.debug("start=" + start);
		log.debug("limit=" + limit);
		List<TSysDictDO> list = null;
		try {
			List<Object> parameters = Lists.newArrayList();
			StringBuilder sql = new StringBuilder();
			sql.append("select * from ").append(TABLE_NAME).append(getWhereSqlVal(obj, parameters)).append(" order by sort ");
			IDBSession session = DBSessionFactory.getSession();
			if (limit <= 0) {
				list = session.getObjectListByList(sql.toString(), TSysDictDO.class, parameters);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), TSysDictDO.class, start, limit, parameters);
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
	public List<TSysDictDO> list(int start, int limit, Object... param) {
		return null;
	}

	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(TSysDictDO obj) {
		log.debug("TSysDictDO=" + obj);
		List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select distinct dict_tp, dict_info from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        IDBSession session = DBSessionFactory.getSession();
		int total = 0;
        try {
        	total = session.accountByList(countSql, parameters);
        } catch (Exception e) {
        	log.error("总记录数查询t_sys_dict异常", e);
        	throw new BaseException(SysErr.E_MESSAGE, "总记录数查询t_sys_dict失败！");
        }
        return total;
	}

	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotalVal(TSysDictDO obj) {
		log.debug("TSysDictDO=" + obj);
		List<Object> parameters = Lists.newArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("select * from ").append(TABLE_NAME).append(getWhereSqlVal(obj, parameters));
		String countSql = "select count(1) from (" + sql.toString() + ") ct";
		IDBSession session = DBSessionFactory.getSession();
		int total = 0;
		try {
			total = session.accountByList(countSql, parameters);
		} catch (Exception e) {
			log.error("总记录数查询t_sys_dict异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "总记录数查询t_sys_dict失败！");
		}
		return total;
	}

	/**
	 * 根据数据对象产生对应的查询SQL
	 * @param obj 数据对象DO
	 * @return SQL
	 */
	public String getWhereSql(TSysDictDO obj, List<Object> parameters) {
		if(null==parameters){
        	parameters = Lists.newArrayList();
        }
		StringBuffer sql = new StringBuffer(" where 1=1 ");
		if ( !DataUtil.isNullStr(obj.getDictTp())) {
		    sql.append(" AND dict_tp = ? ");
		    parameters.add(obj.getDictTp());
		}
		if ( !DataUtil.isNullStr(obj.getDictInfo())) {
		    sql.append(" AND dict_info = ? ");
		    parameters.add(obj.getDictInfo());
		}
		return sql.toString();
	}

	/**
	 * 根据数据对象产生对应的查询SQL
	 * @param obj 数据对象DO
	 * @return SQL
	 */
	public String getWhereSqlVal(TSysDictDO obj, List<Object> parameters) {
		if(null==parameters){
			parameters = Lists.newArrayList();
		}
		StringBuffer sql = new StringBuffer(" where 1=1 ");
		if ( !DataUtil.isNullStr(obj.getDictTp())) {
			sql.append(" AND dict_tp = ? ");
			parameters.add(obj.getDictTp());
		}
		if ( !DataUtil.isNullStr(obj.getDictVal())) {
			sql.append(" AND dict_val = ? ");
			parameters.add(obj.getDictVal());
		}
		sql.append(" AND dict_val is not null ");
		return sql.toString();
	}

}
