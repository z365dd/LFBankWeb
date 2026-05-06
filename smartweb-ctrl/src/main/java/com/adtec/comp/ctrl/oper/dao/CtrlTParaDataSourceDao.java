/**
* 系统名称: SmartWeb平台
* 模块名称: comp.ctrl.oper持久化模块
* 功能描述: 数据源数据库操作
* 类 名 称  : CtrlTParaDataSourceDao.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200512<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.comp.ctrl.oper.dao;

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
import com.adtec.comp.ctrl.oper.entity.CtrlTParaDataSourceDO;

/**
 * 数据源Dao接口
 * @author zhengjt
 * @version 20200512
 */
@Component
public class CtrlTParaDataSourceDao implements IBaseDao<CtrlTParaDataSourceDO>{
	/**
	 * 日志对象
	 */
	protected final static Logger log = LoggerFactory.getLogger(CtrlTParaDataSourceDao.class);
	/*表名称*/
    public static final String TABLE_NAME = "t_para_data_source";
    
	/**
	 * 获取单条数据
	 * @param dataSrc
	 * @return
	 */
	public CtrlTParaDataSourceDO get(String dataSrc ) {
		CtrlTParaDataSourceDO obj = new CtrlTParaDataSourceDO();
	 	obj.setDataSrc(dataSrc);
		return get(obj);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	@Override
    public CtrlTParaDataSourceDO get(CtrlTParaDataSourceDO obj) {
        StringBuilder sql = new StringBuilder();
        List<Object> parameters = Lists.newArrayList();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        CtrlTParaDataSourceDO rs = null;
        IDBSession session = DBSessionFactory.getSession("fctrl");
        try {
            rs = session.getObjectByList(sql.toString(), CtrlTParaDataSourceDO.class, parameters);
        } catch (Exception e) {
            log.error("获取数据源异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "获取数据源失败！");
        }
        return rs;
    }
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	@Override
	public int insert(CtrlTParaDataSourceDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession("fctrl");
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			ignoreFields.add("shortRmrk");
			ignoreFields.add("midRmrk");
			ignoreFields.add("longRmrk");
			ignoreFields.add("dac");
			rs = session.saveObject(TABLE_NAME, obj, ignoreFields);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("新增数据源交易异常", e);
            throw new BaseException(SysErr.E_MESSAGE, "新增数据源交易失败！");
		}
		return rs;
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	@Override
	public int update(CtrlTParaDataSourceDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession("fctrl");
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			ignoreFields.add("shortRmrk");
			ignoreFields.add("midRmrk");
			ignoreFields.add("longRmrk");
			ignoreFields.add("dac");
			List<String> matchFields = obj.getMatchFields();
			rs = session.updateObject(TABLE_NAME, obj, matchFields, ignoreFields);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("修改数据源异常", e);
            throw new BaseException(SysErr.E_MESSAGE, "修改数据源失败！");
		}
		return rs;
	}
	
	/**
	 * 根据主键删除数据
	 * @param dataSrc
	 * @see public int delete(T entity)
	 * @return
	 */
	public int delete(String dataSrc ){
		CtrlTParaDataSourceDO obj = new CtrlTParaDataSourceDO();
	 	obj.setDataSrc(dataSrc);
		return delete(obj);
	}
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param obj
	 * @return
	 */
	@Override
	public int delete(CtrlTParaDataSourceDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession("fctrl");
		try {
			List<Object> parameters = Lists.newArrayList();
			String sql = "DELETE FROM t_para_data_source "+getWhereSql(obj, parameters);
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
	public List<CtrlTParaDataSourceDO> list(CtrlTParaDataSourceDO obj) {
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
	public List<CtrlTParaDataSourceDO> list(CtrlTParaDataSourceDO obj, int start, int limit) {
		log.debug("CtrlTParaDataSourceDO=" + obj.toString());
		log.debug("start=" + start);
		log.debug("limit=" + limit);
		List<CtrlTParaDataSourceDO> list = null;
		try {
			List<Object> parameters = Lists.newArrayList();
			StringBuilder sql = new StringBuilder();
        	sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        	IDBSession session = DBSessionFactory.getSession("fctrl");
			if (limit <= 0) {
				list = session.getObjectListByList(sql.toString(), CtrlTParaDataSourceDO.class, parameters);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), CtrlTParaDataSourceDO.class, start, limit, parameters);
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
	public List<CtrlTParaDataSourceDO> list(int start, int limit, Object... param) {
		return null;
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(CtrlTParaDataSourceDO obj) {
		log.debug("CtrlTParaDataSourceDO=" + obj);
		List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        IDBSession session = DBSessionFactory.getSession("fctrl");       
		int total = 0;
        try {
        	total = session.accountByList(countSql, parameters);
        } catch (Exception e) {
        	log.error("总记录数查询t_para_data_source异常", e);
        	throw new BaseException(SysErr.E_MESSAGE, "总记录数查询t_para_data_source失败！");
        }
        return total;
	}
	
	/**
	 * 根据数据对象产生对应的查询SQL
	 * @param obj 数据对象DO
	 * @return SQL
	 */
	public String getWhereSql(CtrlTParaDataSourceDO obj, List<Object> parameters) {
		if(null==parameters){
        	parameters = Lists.newArrayList();
        }
		StringBuffer sql = new StringBuffer(" where 1=1 ");
		if ( !DataUtil.isNullStr(obj.getDataSrc())) {
			sql.append(" AND data_src LIKE ? ");
			parameters.add("%"+obj.getDataSrc()+"%");
		}
		if ( !DataUtil.isNullStr(obj.getDataSrcName())) {
			sql.append(" AND data_src_name LIKE ? ");
			parameters.add("%"+obj.getDataSrcName()+"%");
		}
		return sql.toString();
	}
	
}