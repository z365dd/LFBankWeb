/**
* 系统名称: SmartWeb平台
* 模块名称: comp-prod/oper/definition/adapter持久化模块
* 功能描述: 可售产品包装数据库操作
* 类 名 称  : TPipSaleProdAdapterDao.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200106<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.prod.oper.definition.adapter.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.common.dao.IBaseDao;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.prod.oper.definition.adapter.entity.TPipSaleProdAdapterCtrlDO;
import com.adtec.prod.oper.definition.adapter.entity.TPipSaleProdAdapterDO;
import com.adtec.prod.oper.definition.saleprod.entity.TPipSaleProdAtomProdDO;
import com.adtec.prod.oper.definition.saleprod.entity.TPipSaleProdDO;

/**
 * 可售产品包装Dao接口
 * @author zengxj
 * @version 20200106
 */
@Component
public class TPipSaleProdAdapterDao implements IBaseDao<TPipSaleProdAdapterDO>{
	/**
	 * 日志对象
	 */
	protected final static Logger logger = LoggerFactory.getLogger(TPipSaleProdAdapterDao.class);
	/*表名称*/
    public static final String TABLE_NAME = "t_pip_sale_prod_adapter";
    public static final String SALE_PROD = "t_pip_sale_prod";
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public TPipSaleProdAdapterDO get(String id) {
		TPipSaleProdAdapterDO obj = new TPipSaleProdAdapterDO();
		obj.setSaleProdCode(id);
		return get(obj);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	@Override
    public TPipSaleProdAdapterDO get(TPipSaleProdAdapterDO obj) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where sale_prod_code=? ");
        TPipSaleProdAdapterDO rs = null;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObject(sql.toString(), TPipSaleProdAdapterDO.class, obj.getSaleProdCode());
        } catch (Exception e) {
            logger.error("获取可售产品包装异常："+e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "获取可售产品包装失败！");
        }
        return rs;
    }
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	@Override
	public int insert(TPipSaleProdAdapterDO obj){
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
			logger.error("新增可售产品包装交易异常："+e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "新增可售产品包装交易失败！");
		}
		return rs;
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	@Override
	public int update(TPipSaleProdAdapterDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			ignoreFields.add("keyTp");
			ignoreFields.add("shortRmrk");
			ignoreFields.add("midRmrk");
			ignoreFields.add("longRmrk");
			ignoreFields.add("dac");
			List<String> matchFields = obj.getMatchFields();
			rs = session.updateObject(TABLE_NAME, obj, matchFields, ignoreFields);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("修改可售产品包装异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "修改可售产品包装失败！");
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
		TPipSaleProdAdapterDO obj = new TPipSaleProdAdapterDO();
		obj.setSaleProdCode(id);
		return delete(obj);
	}
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param obj
	 * @return
	 */
	@Override
	public int delete(TPipSaleProdAdapterDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			String sql = "DELETE FROM t_pip_sale_prod_adapter where sale_prod_code=?";
			rs = session.execute(sql, obj.getSaleProdCode());
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
	public List<TPipSaleProdAdapterDO> list(TPipSaleProdAdapterDO obj) {
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
	public List<TPipSaleProdAdapterDO> list(TPipSaleProdAdapterDO obj, int start, int limit) {
		logger.debug("TPipSaleProdDO=" + obj.toString());
		logger.debug("start=" + start);
		logger.debug("limit=" + limit);
		List<TPipSaleProdAdapterDO> list = null;
//		try {
//			StringBuilder sql = new StringBuilder();
//        	sql.append("SELECT * from T_PIP_SALE_PROD  ")
//        	.append(getWhereSql(obj));
//        	sql.append(" order by SALE_PROD_CODE desc");
//        	logger.debug("sql=" + sql.toString());
//        	IDBSession session = DBSessionFactory.getSession();
//			if (limit == 0) {
//				list = session.getObjectList(sql.toString(), TPipSaleProdAdapterDO.class);
//			} else {
//				list = session.getObjectListForPage(sql.toString(), TPipSaleProdAdapterDO.class, start, limit);
//			}
//		} catch (Exception e) {
//			logger.error("列表查询异常：" + e.getMessage());
//			throw new BaseException(SysErr.E_MESSAGE, "列表查询失败！");
//		}
		return list;
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param obj 数据对象DO
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @return List返回集合
	 */
	public List<TPipSaleProdDO> saleProdList(TPipSaleProdDO obj, int start, int limit) {
		logger.debug("TPipSaleProdDO=" + obj.toString());
		logger.debug("start=" + start);
		logger.debug("limit=" + limit);
		List<TPipSaleProdDO> list = null;
		try {
			StringBuilder sql = new StringBuilder();
			List<Object> params = Lists.newArrayList();
			sql.append("select t.sale_prod_code, t.sale_prod_desc, t.prod_line_code, t.url, l.prod_line_name from ").append(SALE_PROD).append(" t left join t_pip_line_prod l on l.prod_line_code = t.prod_line_code ")
			.append(getWhereSql(obj, params));
			sql.append(" order by SALE_PROD_CODE desc");
			logger.debug("sql=" + sql.toString());
			IDBSession session = DBSessionFactory.getSession();
			if (limit == 0) {
				list = session.getObjectListByList(sql.toString(), TPipSaleProdDO.class, params);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), TPipSaleProdDO.class, start, limit, params);
			}
		} catch (Exception e) {
			logger.error("列表查询异常：", e);
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
	public List<TPipSaleProdAdapterDO> list(int start, int limit, Object... param) {
		return null;
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(TPipSaleProdDO obj) {
		logger.debug("TPipSaleProdAdapterDO=" + obj);
        StringBuilder sql = new StringBuilder();
		List<Object> params = Lists.newArrayList();
        sql.append("select t.sale_prod_code, t.sale_prod_desc, t.prod_line_code, l.prod_line_name from ").append(SALE_PROD).append(" t left join t_pip_line_prod l on l.prod_line_code = t.prod_line_code ")
        .append(getWhereSql(obj, params));
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        logger.debug("countSql=" + countSql);
        IDBSession session = DBSessionFactory.getSession();       
		int total = 0;
        try {
        	total = session.accountByList(countSql, params);
        } catch (Exception e) {
        	logger.error("总记录数查询t_pip_sale_prod_adapter异常：" + e.getMessage());
        	throw new BaseException(SysErr.E_MESSAGE, "总记录数查询t_pip_sale_prod_adapter失败！");
        }
        return total;
	}
	
	/**
	 * 根据数据对象产生对应的查询SQL
	 * @param obj 数据对象DO
	 * @return SQL
	 */
	public String getWhereSql(TPipSaleProdDO obj, List<Object> params) {
		StringBuilder sql = new StringBuilder(" where 1=1 ");
		sql.append(" and SALE_PROD_CODE in (select SALE_PROD_CODE from T_PIP_SALE_PROD_ADAPTER) ");
		if (!DataUtil.isNullStr(obj.getSaleProdCode())) {
			sql.append(" and SALE_PROD_CODE = ?");
			params.add(obj.getSaleProdCode());
		}
		if (!DataUtil.isNullStr(obj.getProdLineCode())) {
			sql.append(" and t.PROD_LINE_CODE = ?");
			params.add(obj.getProdLineCode());
		}
		return sql.toString();
	}
	
	/**
	 * 获取原子产品对应的属性字段
	 * @param atomProdCode
	 * @param deltailFlag -- 根据页面判断查询不同的表，新增时查key表，修改或详细查询包装表
	 * @return
	 */
	public List<TPipSaleProdAdapterDO> getKeyList(String saleProdCode, String deltailFlag) {
		List<TPipSaleProdAdapterDO> list = Lists.newArrayList();
		String tableName = "t_pip_key";
		if ("1".equals(deltailFlag)){
			tableName = TABLE_NAME;
		}
		StringBuilder sql = new StringBuilder();
		List<Object> params = Lists.newArrayList();
		sql.append("select DISTINCT k.* FROM "+tableName+" K LEFT JOIN T_PIP_COMP_SVC_PARA s ON s.key_no = K .key_no ")
			.append(" LEFT JOIN T_PIP_ATOM_PROD_SVC P ON P .svc_code = s.svc_code where  1 = 1 ");
		if ("1".equals(deltailFlag)){
			sql.append("and k.sale_prod_code = ?");
			sql.append(" order by ser asc");
			params.add(saleProdCode);
		}else {
			sql.append("and p.atom_prod_code in (select atom_prod_code from T_PIP_SALE_PROD_ATOM_PROD where sale_prod_code=?)");
			params.add(saleProdCode);
		}
		IDBSession session = DBSessionFactory.getSession();
//		int total = 0;
		logger.debug("sql=" + sql);
        try {
        	list = session.getObjectListByList(sql.toString(), TPipSaleProdAdapterDO.class, params);
        } catch (Exception e) {
        	logger.error("属性字典表查询异常：" + e.getMessage());
        	throw new BaseException(SysErr.E_MESSAGE, "属性字典表查询失败！");
        }
		return list;
	}
	
	/**
	 * 根据keyNo获取对应的ctrl字段
	 * @param keyNo
	 * @return
	 */
	public List<TPipSaleProdAdapterCtrlDO> getKeyCtrlList(String keyNo) {
		List<TPipSaleProdAdapterCtrlDO> list = Lists.newArrayList();
		StringBuilder sql = new StringBuilder();
		List<Object> params = Lists.newArrayList();
		sql.append("select * from t_pip_key_ctrl where key_no = ?");
		params.add(keyNo);
		IDBSession session = DBSessionFactory.getSession();
//		int total = 0;
		logger.debug("sql=" + sql);
        try {
        	list = session.getObjectListByList(sql.toString(), TPipSaleProdAdapterCtrlDO.class, params);
        } catch (Exception e) {
        	logger.error("属性字典表查询t_pip_key异常：" + e.getMessage());
        	throw new BaseException(SysErr.E_MESSAGE, "属性字典表查询失败！");
        }
		return list;
	}
	
	/**
	 * 根据可售产品编号获取业务总数
	 * @param saleProdCode
	 * @return
	 */
	public boolean getBusiCount(String saleProdCode) {
		StringBuilder sql = new StringBuilder();
		sql.append("select count(1) from t_pip_busi where sale_prod_code = ?");
		IDBSession session = DBSessionFactory.getSession();
		int rs = 0;
//		int total = 0;
		logger.debug("sql=" + sql);
        try {
        	rs = session.account(sql.toString(), saleProdCode);
        } catch (Exception e) {
        	logger.error("业务数查询异常：" + e.getMessage());
        	throw new BaseException(SysErr.E_MESSAGE, "业务表查询失败！");
        }
		return rs>0?true:false;
	}
}