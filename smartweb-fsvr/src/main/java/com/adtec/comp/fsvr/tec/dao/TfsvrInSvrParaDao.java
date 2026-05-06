/**
* 系统名称: SmartWeb平台
* 模块名称: comp.fsvr.tec持久化模块
* 功能描述: 内部文件服务器数据库操作
* 类 名 称  : TfsvrInSvrParaDao.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200624<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.comp.fsvr.tec.dao;

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
import com.adtec.comp.fsvr.tec.entity.TfsvrInSvrParaDO;

/**
 * 内部文件服务器Dao接口
 * @author zhengjt
 * @version 20200624
 */
@Component
public class TfsvrInSvrParaDao implements IBaseDao<TfsvrInSvrParaDO>{
	/**
	 * 日志对象
	 */
	protected final static Logger log = LoggerFactory.getLogger(TfsvrInSvrParaDao.class);
	/*表名称*/
    public static final String TABLE_NAME = "t_tfsvr_svr_para";
    
	/**
	 * 获取单条数据
	 * @param fileSvrId
	 * @return
	 */
	public TfsvrInSvrParaDO get(String fileSvrId ) {
		TfsvrInSvrParaDO obj = new TfsvrInSvrParaDO();
	 	obj.setFileSvrId(fileSvrId);
		return get(obj);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	@Override
    public TfsvrInSvrParaDO get(TfsvrInSvrParaDO obj) {
        StringBuilder sql = new StringBuilder();
        List<Object> parameters = Lists.newArrayList();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        TfsvrInSvrParaDO rs = null;
        IDBSession session = DBSessionFactory.getSession("fsvr");
        try {
            rs = session.getObjectByList(sql.toString(), TfsvrInSvrParaDO.class, parameters);
        } catch (Exception e) {
            log.error("获取内部文件服务器异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "获取内部文件服务器失败！");
        }
        return rs;
    }
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	@Override
	public int insert(TfsvrInSvrParaDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession("fsvr");
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			ignoreFields.add("userNo");
			ignoreFields.add("pwd");
			ignoreFields.add("shortRmrk");
			ignoreFields.add("midRmrk");
			ignoreFields.add("longRmrk");
			ignoreFields.add("dac");
			ignoreFields.add("contFlg");
			rs = session.saveObject(TABLE_NAME, obj, ignoreFields);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("新增内部文件服务器交易异常", e);
            throw new BaseException(SysErr.E_MESSAGE, "新增内部文件服务器交易失败！");
		}
		return rs;
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	@Override
	public int update(TfsvrInSvrParaDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession("fsvr");
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			ignoreFields.add("userNo");
			ignoreFields.add("pwd");
			ignoreFields.add("shortRmrk");
			ignoreFields.add("midRmrk");
			ignoreFields.add("longRmrk");
			ignoreFields.add("dac");
			ignoreFields.add("contFlg");
			List<String> matchFields = obj.getMatchFields();
			rs = session.updateObject(TABLE_NAME, obj, matchFields, ignoreFields);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("修改内部文件服务器异常", e);
            throw new BaseException(SysErr.E_MESSAGE, "修改内部文件服务器失败！");
		}
		return rs;
	}
	
	/**
	 * 根据主键删除数据
	 * @param fileSvrId
	 * @see public int delete(T entity)
	 * @return
	 */
	public int delete(String fileSvrId ){
		TfsvrInSvrParaDO obj = new TfsvrInSvrParaDO();
	 	obj.setFileSvrId(fileSvrId);
		return delete(obj);
	}
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param obj
	 * @return
	 */
	@Override
	public int delete(TfsvrInSvrParaDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession("fsvr");
		try {
			List<Object> parameters = Lists.newArrayList();
			String sql = "DELETE FROM t_tfsvr_svr_para "+getWhereSql(obj, parameters);
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
	public List<TfsvrInSvrParaDO> list(TfsvrInSvrParaDO obj) {
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
	public List<TfsvrInSvrParaDO> list(TfsvrInSvrParaDO obj, int start, int limit) {
		log.debug("TfsvrInSvrParaDO=" + obj.toString());
		log.debug("start=" + start);
		log.debug("limit=" + limit);
		List<TfsvrInSvrParaDO> list = null;
		try {
			List<Object> parameters = Lists.newArrayList();
			StringBuilder sql = new StringBuilder();
        	sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
			sql.append(" order by file_svr_id desc");
        	IDBSession session = DBSessionFactory.getSession("fsvr");
			if (limit <= 0) {
				list = session.getObjectListByList(sql.toString(), TfsvrInSvrParaDO.class, parameters);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), TfsvrInSvrParaDO.class, start, limit, parameters);
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
	public List<TfsvrInSvrParaDO> list(int start, int limit, Object... param) {
		return null;
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(TfsvrInSvrParaDO obj) {
		log.debug("TfsvrInSvrParaDO=" + obj);
		List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        IDBSession session = DBSessionFactory.getSession("fsvr");       
		int total = 0;
        try {
        	total = session.accountByList(countSql, parameters);
        } catch (Exception e) {
        	log.error("总记录数查询t_tfsvr_svr_para异常", e);
        	throw new BaseException(SysErr.E_MESSAGE, "总记录数查询t_tfsvr_svr_para失败！");
        }
        return total;
	}
	
	/**
	 * 根据数据对象产生对应的查询SQL
	 * @param obj 数据对象DO
	 * @return SQL
	 */
	public String getWhereSql(TfsvrInSvrParaDO obj, List<Object> parameters) {
		if(null==parameters){
        	parameters = Lists.newArrayList();
        }
		StringBuffer sql = new StringBuffer(" where 1=1 ");
		if ( !DataUtil.isNullStr(obj.getFileSvrId())) {
		    sql.append(" AND file_svr_id = ? ");
		    parameters.add(obj.getFileSvrId());
		}
		if ( !DataUtil.isNullStr(obj.getFileSvrStat())) {
		    sql.append(" AND file_svr_stat = ? ");
		    parameters.add(obj.getFileSvrStat());
		}
		return sql.toString();
	}
	
}