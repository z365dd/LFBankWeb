/**
* 系统名称: SmartWeb平台
* 模块名称: sys-modules持久化模块
* 功能描述: 请求IP信息表数据库操作
* 类 名 称  : SysReqIpMsgDao.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20211021<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.sys.modules.sys.dao;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.common.dao.IBaseDao;
import com.adtec.sys.modules.sys.entity.SysReqIpMsgDO;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 请求IP信息表Dao接口
 * @author zengxj
 * @version 20211021
 */
@Component
public class SysReqIpMsgDao implements IBaseDao<SysReqIpMsgDO>{
	/**
	 * 日志对象
	 */
	protected final static Logger log = LoggerFactory.getLogger(SysReqIpMsgDao.class);
	/*表名称*/
    public static final String TABLE_NAME = "t_sys_req_ip_msg";
    
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public SysReqIpMsgDO get(String id ) {
		SysReqIpMsgDO obj = new SysReqIpMsgDO();
	 	obj.setId(id);
		return get(obj);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	@Override
    public SysReqIpMsgDO get(SysReqIpMsgDO obj) {
        StringBuilder sql = new StringBuilder();
        List<Object> parameters = Lists.newArrayList();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));       
        SysReqIpMsgDO rs = null;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObjectByList(sql.toString(), SysReqIpMsgDO.class, parameters);
        } catch (Exception e) {
            log.error("获取请求IP信息表异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "获取请求IP信息表失败！");
        }
        return rs;
    }
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	@Override
	public int insert(SysReqIpMsgDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			rs = session.saveObject(TABLE_NAME, obj, ignoreFields);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("新增请求IP信息表交易异常", e);
            throw new BaseException(SysErr.E_MESSAGE, "新增请求IP信息表交易失败！");
		}
		return rs;
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	@Override
	public int update(SysReqIpMsgDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			List<String> matchFields = obj.getMatchFields();
			rs = session.updateObject(TABLE_NAME, obj, matchFields, ignoreFields);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("修改请求IP信息表异常", e);
            throw new BaseException(SysErr.E_MESSAGE, "修改请求IP信息表失败！");
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
		SysReqIpMsgDO obj = new SysReqIpMsgDO();
	 	obj.setId(id);
		return delete(obj);
	}
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flg字段为1）
	 * @param obj
	 * @return
	 */
	@Override
	public int delete(SysReqIpMsgDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			List<Object> parameters = Lists.newArrayList();
			String sql = "DELETE FROM t_sys_req_ip_msg "+getWhereSql(obj, parameters);
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
	public List<SysReqIpMsgDO> list(SysReqIpMsgDO obj) {
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
	public List<SysReqIpMsgDO> list(SysReqIpMsgDO obj, int start, int limit) {
		log.debug("SysReqIpMsgDO=" + obj.toString());
		log.debug("start=" + start);
		log.debug("limit=" + limit);
		List<SysReqIpMsgDO> list = null;
		try {
			List<Object> parameters = Lists.newArrayList();
			StringBuilder sql = new StringBuilder();
        	sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        	IDBSession session = DBSessionFactory.getSession();
			if (limit <= 0) {
				list = session.getObjectListByList(sql.toString(), SysReqIpMsgDO.class, parameters);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), SysReqIpMsgDO.class, start, limit, parameters);
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
	public List<SysReqIpMsgDO> list(int start, int limit, Object... param) {
		return null;
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(SysReqIpMsgDO obj) {
		log.debug("SysReqIpMsgDO=" + obj);
		List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        IDBSession session = DBSessionFactory.getSession();       
		int total = 0;
        try {
        	total = session.accountByList(countSql, parameters);
        } catch (Exception e) {
        	log.error("总记录数查询t_sys_req_ip_msg异常", e);
        	throw new BaseException(SysErr.E_MESSAGE, "总记录数查询t_sys_req_ip_msg失败！");
        }
        return total;
	}
	
	/**
	 * 根据数据对象产生对应的查询SQL
	 * @param obj 数据对象DO
	 * @return SQL
	 */
	public String getWhereSql(SysReqIpMsgDO obj, List<Object> parameters) {
		if(null==parameters){
        	parameters = Lists.newArrayList();
        }
		StringBuffer sql = new StringBuffer(" where 1=1 ");
		if ( !DataUtil.isNullStr(obj.getId())) {
		    sql.append(" AND id = ? ");
		    parameters.add(obj.getId());
		}
		if ( !DataUtil.isNullStr(obj.getTermIp())) {
		    sql.append(" AND term_ip = ? ");
		    parameters.add(obj.getTermIp());
		}
		if ( !DataUtil.isNullStr(obj.getUsrTp())) {
		    sql.append(" AND usr_tp like ? ");
		    parameters.add("%"+obj.getUsrTp()+"%");
		}
		if ( !DataUtil.isNullStr(obj.getUsrName())) {
		    sql.append(" AND usr_name like ? ");
		    parameters.add("%"+obj.getUsrName()+"%");
		}
		return sql.toString();
	}
	
}