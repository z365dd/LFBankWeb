/**
* 系统名称: SmartWeb平台
* 模块名称: comp.ctrl.oper持久化模块
* 功能描述: 法人管理数据库操作
* 类 名 称  : CtrlTParaLegaDao.java
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
import com.adtec.comp.ctrl.oper.entity.CtrlTParaLegaAndTntDO;
import com.adtec.comp.ctrl.oper.entity.CtrlTParaLegaDO;

/**
 * 法人管理Dao接口
 * @author zhengjt
 * @version 20200310
 */
@Component
public class CtrlTParaLegaDao implements IBaseDao<CtrlTParaLegaDO>{
	/**
	 * 日志对象
	 */
	protected final static Logger log = LoggerFactory.getLogger(CtrlTParaLegaDao.class);
	/*表名称*/
    public static final String TABLE_NAME = "t_para_lega";
    
	/**
	 * 获取单条数据
	 * @param legaNo
	 * @return
	 */
	public CtrlTParaLegaDO get(String legaNo ) {
		CtrlTParaLegaDO obj = new CtrlTParaLegaDO();
	 	obj.setLegaNo(legaNo);
		return get(obj);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	@Override
    public CtrlTParaLegaDO get(CtrlTParaLegaDO obj) {
        StringBuilder sql = new StringBuilder();
        List<Object> parameters = Lists.newArrayList();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        CtrlTParaLegaDO rs = null;
        IDBSession session = DBSessionFactory.getSession("fctrl");
        try {
            rs = session.getObjectByList(sql.toString(), CtrlTParaLegaDO.class, parameters);
        } catch (Exception e) {
            log.error("获取法人管理异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "获取法人管理失败！");
        }
        return rs;
    }
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	@Override
	public int insert(CtrlTParaLegaDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession("fctrl");
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			ignoreFields.add("lastUptTime");
			ignoreFields.add("shortRmrk");
			ignoreFields.add("midRmrk");
			ignoreFields.add("longRmrk");
			ignoreFields.add("dac");
			rs = session.saveObject(TABLE_NAME, obj, ignoreFields);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("新增法人管理交易异常", e);
            throw new BaseException(SysErr.E_MESSAGE, "新增法人管理交易失败！");
		}
		return rs;
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	@Override
	public int update(CtrlTParaLegaDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession("fctrl");
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			ignoreFields.add("lastUptTime");
			ignoreFields.add("shortRmrk");
			ignoreFields.add("midRmrk");
			ignoreFields.add("longRmrk");
			ignoreFields.add("dac");
			List<String> matchFields = obj.getMatchFields();
			rs = session.updateObject(TABLE_NAME, obj, matchFields, ignoreFields);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("修改法人管理异常", e);
            throw new BaseException(SysErr.E_MESSAGE, "修改法人管理失败！");
		}
		return rs;
	}
	
	/**
	 * 根据主键删除数据
	 * @param legaNo
	 * @see public int delete(T entity)
	 * @return
	 */
	public int delete(String legaNo ){
		CtrlTParaLegaDO obj = new CtrlTParaLegaDO();
	 	obj.setLegaNo(legaNo);
		return delete(obj);
	}
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param obj
	 * @return
	 */
	@Override
	public int delete(CtrlTParaLegaDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession("fctrl");
		try {
			List<Object> parameters = Lists.newArrayList();
			String sql = "DELETE FROM t_para_lega "+getWhereSql(obj, parameters);
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
	public List<CtrlTParaLegaDO> list(CtrlTParaLegaDO obj) {
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
	public List<CtrlTParaLegaDO> list(CtrlTParaLegaDO obj, int start, int limit) {
		log.debug("CtrlTParaLegaDO=" + obj.toString());
		log.debug("start=" + start);
		log.debug("limit=" + limit);
		List<CtrlTParaLegaDO> list = null;
		try {
			List<Object> parameters = Lists.newArrayList();
			StringBuilder sql = new StringBuilder();
        	sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
			//sql.append(" order by update_date desc");
        	log.debug("sql=" + sql.toString());
        	IDBSession session = DBSessionFactory.getSession("fctrl");
			if (limit <= 0) {
				list = session.getObjectListByList(sql.toString(), CtrlTParaLegaDO.class, parameters);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), CtrlTParaLegaDO.class, start, limit, parameters);
			}
		} catch (Exception e) {
			log.error("列表查询异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "列表查询失败！");
		}
		return list;
	}
	
	
	public List<CtrlTParaLegaAndTntDO> listAndTnt(CtrlTParaLegaAndTntDO obj, int start, int limit) {
		log.debug("CtrlTParaLegaAndTntDO=" + obj.toString());
		log.debug("start=" + start);
		log.debug("limit=" + limit);
		List<CtrlTParaLegaAndTntDO> list = null;
		try {
			List<Object> parameters = Lists.newArrayList();
			StringBuilder sql = new StringBuilder();
        	//sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
			sql.append("SELECT T_PARA_TNT.TNT_NAME,T_PARA_LEGA.* FROM T_PARA_LEGA  left join"
	        		+ " T_PARA_TNT  on  T_PARA_LEGA.TNT_NO=T_PARA_TNT.TNT_NO ");
	        sql.append(getWhereSqlAndTnt(obj, parameters));
			//sql.append(" order by update_date desc");
        	log.debug("sql=" + sql.toString());
        	IDBSession session = DBSessionFactory.getSession("fctrl");
			if (limit <= 0) {
				list = session.getObjectListByList(sql.toString(), CtrlTParaLegaAndTntDO.class, parameters);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), CtrlTParaLegaAndTntDO.class, start, limit, parameters);
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
	public List<CtrlTParaLegaDO> list(int start, int limit, Object... param) {
		return null;
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(CtrlTParaLegaDO obj) {
		log.debug("CtrlTParaLegaDO=" + obj);
		List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        log.debug("countSql=" + countSql);
        IDBSession session = DBSessionFactory.getSession("fctrl");       
		int total = 0;
        try {
        	total = session.accountByList(countSql, parameters);
        } catch (Exception e) {
        	log.error("总记录数查询t_para_lega异常：" + e.getMessage());
        	throw new BaseException(SysErr.E_MESSAGE, "总记录数查询t_para_lega失败！");
        }
        return total;
	}
	
	/**
	 * 根据数据对象产生对应的查询SQL
	 * @param obj 数据对象DO
	 * @return SQL
	 */
	public String getWhereSql(CtrlTParaLegaDO obj, List<Object> parameters) {
		if(null==parameters){
        	parameters = Lists.newArrayList();
        }
		StringBuffer sql = new StringBuffer(" where 1=1 ");
		if ( !DataUtil.isNullStr(obj.getLegaName())) {
		    sql.append(" AND lega_name like ? ");
		    parameters.add("%"+obj.getLegaName()+"%");
		}
		if ( !DataUtil.isNullStr(obj.getLegaNo())) {
		    sql.append(" AND lega_no = ? ");
		    parameters.add(obj.getLegaNo());
		}
		if ( !DataUtil.isNullStr(obj.getTntNo())) {
			sql.append(" AND tnt_no = ? ");
			parameters.add(obj.getTntNo());
		}
		return sql.toString();
	}
	public String getWhereSqlAndTnt(CtrlTParaLegaAndTntDO obj, List<Object> parameters) {
		if(null==parameters){
        	parameters = Lists.newArrayList();
        }
		StringBuffer sql = new StringBuffer(" where 1=1 ");
		if ( !DataUtil.isNullStr(obj.getLegaName())) {
		    sql.append(" AND lega_name like ? ");
		    parameters.add("%"+obj.getLegaName()+"%");
		}
		if ( !DataUtil.isNullStr(obj.getLegaNo())) {
		    sql.append(" AND lega_no like ? ");
		    parameters.add("%"+obj.getLegaNo()+"%");
		}
		if ( !DataUtil.isNullStr(obj.getTntNo())) {
			sql.append(" AND  ");
			sql.append(TABLE_NAME);
			sql.append(".tnt_no = ?");
			parameters.add(obj.getTntNo());
		}
		return sql.toString();
	}
}