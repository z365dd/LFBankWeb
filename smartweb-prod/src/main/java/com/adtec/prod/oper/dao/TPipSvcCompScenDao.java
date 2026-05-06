/**
* 系统名称: SmartWeb平台
* 模块名称: comp.prod.oper持久化模块
* 功能描述: 服务场景表数据库操作
* 类 名 称  : TPipSvcCompScenDao.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200109<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.prod.oper.dao;

import com.adtec.prod.oper.entity.TPipSvcCompScenDO;
import com.adtec.prod.oper.entity.TPipSvcCompScenDOForMsmall;
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
 * 服务场景表Dao接口
 * @author zh
 * @version 20200109
 */
@Component
public class TPipSvcCompScenDao implements IBaseDao<TPipSvcCompScenDO>{
	/**
	 * 日志对象
	 */
	protected final static Logger log = LoggerFactory.getLogger(TPipSvcCompScenDao.class);
	/*表名称*/
    public static final String TABLE_NAME = "t_pip_svc_comp_scen";
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public TPipSvcCompScenDO get(String id) {
		TPipSvcCompScenDO obj = new TPipSvcCompScenDO();
		obj.setId(id);
		return get(obj);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	@Override
    public TPipSvcCompScenDO get(TPipSvcCompScenDO obj) {
        StringBuilder sql = new StringBuilder();
		sql.append("select * from ").append(TABLE_NAME).append(" where id=? ");
        TPipSvcCompScenDO rs = null;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObject(sql.toString(), TPipSvcCompScenDO.class, obj.getId());
        } catch (Exception e) {
            log.error("获取服务场景表异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "获取服务场景表失败！");
        }
        return rs;
    }
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	@Override
	public int insert(TPipSvcCompScenDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			rs = session.saveObject(TABLE_NAME, obj, ignoreFields);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("新增服务场景表交易异常", e);
            throw new BaseException(SysErr.E_MESSAGE, "新增服务场景表交易失败！");
		}
		return rs;
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	@Override
	public int update(TPipSvcCompScenDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			List<String> matchFields = obj.getMatchFields();
			rs = session.updateObject(TABLE_NAME, obj, matchFields, ignoreFields);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("修改服务场景表异常", e);
            throw new BaseException(SysErr.E_MESSAGE, "修改服务场景表失败！");
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
		TPipSvcCompScenDO obj = new TPipSvcCompScenDO();
		obj.setId(id);
		return delete(obj);
	}
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param obj
	 * @return
	 */
	@Override
	public int delete(TPipSvcCompScenDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
//			String sql = "DELETE FROM t_pip_svc_comp_scen where id=?";
//			rs = session.execute(sql, obj.getId());
			rs = session.deleteObject(TABLE_NAME, obj, obj.getMatchFields());
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
	public List<TPipSvcCompScenDO> list(TPipSvcCompScenDO obj) {
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
	public List<TPipSvcCompScenDO> list(TPipSvcCompScenDO obj, int start, int limit) {
		log.debug("TPipSvcCompScenDO=" + obj.toString());
		log.debug("start=" + start);
		log.debug("limit=" + limit);
		List<TPipSvcCompScenDO> list = null;
		try {
			List<Object> parameters = Lists.newArrayList();
			StringBuilder sql = new StringBuilder();
        	sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        	log.debug("sql=" + sql.toString());
        	IDBSession session = DBSessionFactory.getSession();
			if (limit == 0) {
				list = session.getObjectListByList(sql.toString(), TPipSvcCompScenDO.class, parameters);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), TPipSvcCompScenDO.class, start, limit, parameters);
			}
		} catch (Exception e) {
			log.error("列表查询异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "列表查询失败！");
		}
		return list;
	}

	public List<TPipSvcCompScenDOForMsmall> listForMsmall(TPipSvcCompScenDOForMsmall obj, int start, int limit) {
		log.debug("TPipSvcCompScenDO=" + obj.toString());
		log.debug("start=" + start);
		log.debug("limit=" + limit);
		List<TPipSvcCompScenDOForMsmall> list = null;
		try {
			List<Object> parameters = Lists.newArrayList();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT a.COMP_NO,a.SVC_CODE,a.SCENE_NO,a.SCENE_NAME,a.SCENE_DESC,a.LAST_UPT_TIME,a.SHORT_RMRK,a.MID_RMRK,a.LONG_RMRK,a.DAC,c.sale_prod_code,c.ATOM_PROD_CODE,c.atom_prod_desc ")
					.append("from T_PIP_SVC_COMP_SCEN a ")
					.append("LEFT JOIN T_PIP_SALE_PROD_ATOM_PROD c on a.COMP_NO=c.COMP_NO ")
					.append(getWhereSqlForMsmall(obj, parameters));
			sql.append(" union all ")
					.append(" SELECT COMP_NO,SVC_CODE,'00' as SCENE_NO,'默认场景-'||SVC_NAME as SCENE_NAME,'默认描述-'||SVC_DESC as SCENE_DESC, ")
					.append(" '' as LAST_UPT_TIME,'' as SHORT_RMRK,'' as MID_RMRK,'' as LONG_RMRK,'' as DAC,sale_prod_code,ATOM_PROD_CODE,atom_prod_desc ")
					.append(" from (SELECT s.SVC_CODE,s.SVC_NAME,s.SVC_DESC,c.COMP_NO,c.sale_prod_code,c.ATOM_PROD_CODE,c.atom_prod_desc ")
					.append(" from T_PIP_SVC s,t_pip_atom_prod_svc svc LEFT JOIN T_PIP_SALE_PROD_ATOM_PROD c on c.ATOM_PROD_CODE=SVC.ATOM_PROD_CODE ")
					.append(" where c.sale_prod_code = ? and s.SVC_CODE=SVC.SVC_CODE) temp ")
					.append(" where temp.SVC_CODE not in ")
					.append(" (SELECT s.svc_code from T_PIP_SVC_COMP_SCEN s ")
					.append(" LEFT JOIN T_PIP_SALE_PROD_ATOM_PROD c on c.COMP_NO=s.COMP_NO ")
					.append(" where c.sale_prod_code = ?) ");
			parameters.add(obj.getSaleProdCode());
			parameters.add(obj.getSaleProdCode());
			log.debug("sql=" + sql.toString());
			IDBSession session = DBSessionFactory.getSession();
			if (limit == 0) {
				list = session.getObjectListByList(sql.toString(), TPipSvcCompScenDOForMsmall.class, parameters);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), TPipSvcCompScenDOForMsmall.class, start, limit, parameters);
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
	public List<TPipSvcCompScenDO> list(int start, int limit, Object... param) {
		return null;
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotalForMsmall(TPipSvcCompScenDOForMsmall obj) {
		log.debug("TPipSvcCompScenDO=" + obj);
		List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.* ")
				.append("from T_PIP_SVC_COMP_SCEN a ")
				.append("LEFT JOIN T_PIP_SALE_PROD_ATOM_PROD c on a.COMP_NO=c.COMP_NO ")
				.append(getWhereSqlForMsmall(obj, parameters));
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        log.debug("countSql=" + countSql);
        IDBSession session = DBSessionFactory.getSession();       
		int total = 0;
        try {
        	total = session.accountByList(countSql, parameters);
        } catch (Exception e) {
        	log.error("总记录数查询t_pip_svc_comp_scen异常：" + e.getMessage());
        	throw new BaseException(SysErr.E_MESSAGE, "总记录数查询t_pip_svc_comp_scen失败！");
        }
        return total;
	}
	
	/**
	 * 根据数据对象产生对应的查询SQL
	 * @param obj 数据对象DO
	 * @return SQL
	 */
	public String getWhereSql(TPipSvcCompScenDO obj, List<Object> parameters) {
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

	public String getWhereSqlForMsmall(TPipSvcCompScenDOForMsmall obj, List<Object> parameters) {
		StringBuffer sql = new StringBuffer(" where 1=1 ");
		if ( !DataUtil.isNullStr(obj.getCompNo())) {
			sql.append(" AND a.comp_no = ? ");
			parameters.add(obj.getCompNo());
		}
		if ( !DataUtil.isNullStr(obj.getSvcCode())) {
			sql.append(" AND a.svc_code = ? ");
			parameters.add(obj.getSvcCode());
		}
		if ( !DataUtil.isNullStr(obj.getSaleProdCode())) {
			sql.append(" AND c.sale_prod_code = ? ");
			parameters.add(obj.getSaleProdCode());
		}
		return sql.toString();
	}

}