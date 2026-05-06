/**
* 系统名称: SmartWeb平台
* 模块名称: comp.fsvr.tec持久化模块
* 功能描述: 文件格式字段转换明细参数表数据库操作
* 类 名 称  : TfsvrFileColChgDtlParaDao.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200630<br>
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
import com.adtec.comp.fsvr.tec.entity.TfsvrFileColChgDtlParaDO;

/**
 * 文件格式字段转换明细参数表Dao接口
 * @author zhengjt
 * @version 20200630
 */
@Component
public class TfsvrFileColChgDtlParaDao implements IBaseDao<TfsvrFileColChgDtlParaDO>{
	/**
	 * 日志对象
	 */
	protected final static Logger log = LoggerFactory.getLogger(TfsvrFileColChgDtlParaDao.class);
	/*表名称*/
    public static final String TABLE_NAME = "t_tfsvr_file_col_chg_dtl_para";
    
	/**
	 * 获取单条数据
	 * @param chgNo
	 * @param flg
	 * @param ser
	 * @return
	 */
	public TfsvrFileColChgDtlParaDO get(String chgNo, String flg, Long ser ) {
		TfsvrFileColChgDtlParaDO obj = new TfsvrFileColChgDtlParaDO();
	 	obj.setChgNo(chgNo);
	 	obj.setFileFlg(flg);
	 	obj.setSer(ser);
		return get(obj);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	@Override
    public TfsvrFileColChgDtlParaDO get(TfsvrFileColChgDtlParaDO obj) {
        StringBuilder sql = new StringBuilder();
        List<Object> parameters = Lists.newArrayList();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        TfsvrFileColChgDtlParaDO rs = null;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObjectByList(sql.toString(), TfsvrFileColChgDtlParaDO.class, parameters);
        } catch (Exception e) {
            log.error("获取文件格式字段转换明细参数表异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "获取文件格式字段转换明细参数表失败！");
        }
        return rs;
    }
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	@Override
	public int insert(TfsvrFileColChgDtlParaDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			rs = session.saveObject(TABLE_NAME, obj, ignoreFields);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("新增文件格式字段转换明细参数表交易异常", e);
            throw new BaseException(SysErr.E_MESSAGE, "新增文件格式字段转换明细参数表交易失败！");
		}
		return rs;
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	@Override
	public int update(TfsvrFileColChgDtlParaDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			List<String> matchFields = obj.getMatchFields();
			rs = session.updateObject(TABLE_NAME, obj, matchFields, ignoreFields);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("修改文件格式字段转换明细参数表异常", e);
            throw new BaseException(SysErr.E_MESSAGE, "修改文件格式字段转换明细参数表失败！");
		}
		return rs;
	}
	
	/**
	 * 根据主键删除数据
	 * @param chgNo
	 * @param flg
	 * @param ser
	 * @see public int delete(T entity)
	 * @return
	 */
	public int delete(String chgNo, String flg, Long ser ){
		TfsvrFileColChgDtlParaDO obj = new TfsvrFileColChgDtlParaDO();
	 	obj.setChgNo(chgNo);
	 	obj.setFileFlg(flg);
	 	obj.setSer(ser);
		return delete(obj);
	}
	/**
	 * 根据主键删除数据
	 * @param chgNo
	 * @param flg
	 * @param ser
	 * @see public int delete(T entity)
	 * @return
	 */
	public int delete(String chgNo){
		TfsvrFileColChgDtlParaDO obj = new TfsvrFileColChgDtlParaDO();
	 	obj.setChgNo(chgNo);
	 	obj.setSer(null);
	 	obj.setColSer(null);
		return delete(obj);
	}
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param obj
	 * @return
	 */
	@Override
	public int delete(TfsvrFileColChgDtlParaDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			List<Object> parameters = Lists.newArrayList();
			String sql = "DELETE FROM t_tfsvr_file_col_chg_dtl_para "+getWhereSql(obj, parameters);
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
	public List<TfsvrFileColChgDtlParaDO> list(TfsvrFileColChgDtlParaDO obj) {
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
	public List<TfsvrFileColChgDtlParaDO> list(TfsvrFileColChgDtlParaDO obj, int start, int limit) {
		log.debug("TfsvrFileColChgDtlParaDO=" + obj.toString());
		log.debug("start=" + start);
		log.debug("limit=" + limit);
		List<TfsvrFileColChgDtlParaDO> list = null;
		try {
			List<Object> parameters = Lists.newArrayList();
			StringBuilder sql = new StringBuilder();
        	sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
			sql.append(" order by ser asc");
        	IDBSession session = DBSessionFactory.getSession();
			if (limit <= 0) {
				list = session.getObjectListByList(sql.toString(), TfsvrFileColChgDtlParaDO.class, parameters);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), TfsvrFileColChgDtlParaDO.class, start, limit, parameters);
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
	public List<TfsvrFileColChgDtlParaDO> list(int start, int limit, Object... param) {
		return null;
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(TfsvrFileColChgDtlParaDO obj) {
		log.debug("TfsvrFileColChgDtlParaDO=" + obj);
		List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        IDBSession session = DBSessionFactory.getSession();       
		int total = 0;
        try {
        	total = session.accountByList(countSql, parameters);
        } catch (Exception e) {
        	log.error("总记录数查询t_tfsvr_file_col_chg_dtl_para异常", e);
        	throw new BaseException(SysErr.E_MESSAGE, "总记录数查询t_tfsvr_file_col_chg_dtl_para失败！");
        }
        return total;
	}
	
	/**
	 * 根据数据对象产生对应的查询SQL
	 * @param obj 数据对象DO
	 * @return SQL
	 */
	public String getWhereSql(TfsvrFileColChgDtlParaDO obj, List<Object> parameters) {
		if(null==parameters){
        	parameters = Lists.newArrayList();
        }
		StringBuffer sql = new StringBuffer(" where 1=1 ");
		if ( !DataUtil.isNullStr(obj.getChgNo())) {
		    sql.append(" AND chg_no = ? ");
		    parameters.add(obj.getChgNo());
		}
		if ( !DataUtil.isNullStr(obj.getFileFlg())) {
		    sql.append(" AND file_flg = ? ");
		    parameters.add(obj.getFileFlg());
		}
		if (null!=obj.getSer() && !DataUtil.isNullStr(""+obj.getSer())) {
		    sql.append(" AND ser = ? ");
		    parameters.add(obj.getSer());
		}
		if (null!=obj.getColSer() && !DataUtil.isNullStr(""+obj.getColSer())) {
		    sql.append(" AND col_ser = ? ");
		    parameters.add(obj.getColSer());
		}
		return sql.toString();
	}
	
}