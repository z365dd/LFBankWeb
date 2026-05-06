package com.adtec.sys.modules.sys.dao;

import java.lang.Exception;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.common.dao.IBaseDao;
import com.adtec.sys.modules.sys.entity.UserAcctDO;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.common.util.DataUtil;
import com.google.common.collect.Lists;

/**
 * 用户账号Dao接口
 * @author lijb
 * @version 20210415
 */
@Component
public class UserAcctDao implements IBaseDao<UserAcctDO>{
	/**
	 * 日志对象
	 */
	protected final static Logger log = LoggerFactory.getLogger(UserAcctDao.class);
	/*表名称*/
    public static final String TABLE_NAME = "t_sys_user_acct";
    
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public UserAcctDO get(String id ) {
		UserAcctDO obj = new UserAcctDO();
	 	obj.setId(id);
		return get(obj);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	@Override
    public UserAcctDO get(UserAcctDO obj) {
        StringBuilder sql = new StringBuilder();
        List<Object> parameters = Lists.newArrayList();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));       
        UserAcctDO rs = null;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObjectByList(sql.toString(), UserAcctDO.class, parameters);
        } catch (Exception e) {
            log.error("获取用户账号异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "获取用户账号失败！");
        }
        return rs;
    }
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	@Override
	public int insert(UserAcctDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		UserAcctDO qry = new UserAcctDO();
		qry.setUserId(obj.getUserId());
		qry.setUserAcctTp(obj.getUserAcctTp());
		if(getTotal(qry) > 0){
            throw new BaseException(SysErr.E_MESSAGE, "此用户账号类型已经存在！");
		}
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			rs = session.saveObject(TABLE_NAME, obj, ignoreFields);
		} catch (Exception e) {
			log.error("新增用户账号交易异常", e);
            throw new BaseException(SysErr.E_MESSAGE, "新增用户账号交易失败！");
		}
		return rs;
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	@Override
	public int update(UserAcctDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			List<String> matchFields = obj.getMatchFields();
			rs = session.updateObject(TABLE_NAME, obj, matchFields, ignoreFields);
		} catch (Exception e) {
			log.error("修改用户账号异常", e);
            throw new BaseException(SysErr.E_MESSAGE, "修改用户账号失败！");
		}
		return rs;
	}
	
	/**
	 * 根据主键删除数据
	 * @param id
	 * @see public int delete(T entity)
	 * @return
	 */
	public int delete(String id ){
		UserAcctDO obj = new UserAcctDO();
	 	obj.setId(id);
		return delete(obj);
	}
	
	public void delByUserId(String userId){
		UserAcctDO obj = new UserAcctDO();
	 	obj.setUserId(userId);;
		delete(obj);		
	}
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flg字段为1）
	 * @param obj
	 * @return
	 */
	@Override
	public int delete(UserAcctDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			List<Object> parameters = Lists.newArrayList();
			String sql = "DELETE FROM t_sys_user_acct "+getWhereSql(obj, parameters);
			rs = session.executeByList(sql, parameters);
		} catch (Exception e) {
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
	public List<UserAcctDO> list(UserAcctDO obj) {
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
	public List<UserAcctDO> list(UserAcctDO obj, int start, int limit) {
		log.debug("SysUserAcctDO=" + obj.toString());
		log.debug("start=" + start);
		log.debug("limit=" + limit);
		List<UserAcctDO> list = null;
		try {
			List<Object> parameters = Lists.newArrayList();
			StringBuilder sql = new StringBuilder();
        	sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        	sql.append(" order by upt_time desc");
        	IDBSession session = DBSessionFactory.getSession();
			if (limit <= 0) {
				list = session.getObjectListByList(sql.toString(), UserAcctDO.class, parameters);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), UserAcctDO.class, start, limit, parameters);
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
	public List<UserAcctDO> list(int start, int limit, Object... param) {
		return null;
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(UserAcctDO obj) {
		log.debug("SysUserAcctDO=" + obj);
		List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        IDBSession session = DBSessionFactory.getSession();       
		int total = 0;
        try {
        	total = session.accountByList(countSql, parameters);
        } catch (Exception e) {
        	log.error("总记录数查询t_sys_user_acct异常", e);
        	throw new BaseException(SysErr.E_MESSAGE, "总记录数查询t_sys_user_acct失败！");
        }
        return total;
	}
	
	/**
	 * 根据数据对象产生对应的查询SQL
	 * @param obj 数据对象DO
	 * @return SQL
	 */
	public String getWhereSql(UserAcctDO obj, List<Object> parameters) {
		if(null==parameters){
        	parameters = Lists.newArrayList();
        }
		StringBuffer sql = new StringBuffer(" where 1=1 ");
		if ( !DataUtil.isNullStr(obj.getId())) {
		    sql.append(" AND id = ? ");
		    parameters.add(obj.getId());
		}
		if ( !DataUtil.isNullStr(obj.getUserId())) {
		    sql.append(" AND user_id = ? ");
		    parameters.add(obj.getUserId());
		}
		if ( !DataUtil.isNullStr(obj.getUserAcctTp())) {
			sql.append(" AND user_acct_tp = ? ");
			parameters.add(obj.getUserAcctTp());
		}
		return sql.toString();
	}
	
}