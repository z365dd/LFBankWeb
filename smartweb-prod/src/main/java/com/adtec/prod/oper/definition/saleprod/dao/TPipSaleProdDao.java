/**
* 系统名称: SmartWeb平台
* 模块名称: comp-prod/oper/definition/saleprod持久化模块
* 功能描述: 可售产品管理数据库操作
* 类 名 称  : TPipSaleProdDao.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200104<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.prod.oper.definition.saleprod.dao;

import java.sql.SQLException;
import java.util.List;

import com.adtec.prod.oper.definition.saleprod.entity.TPipSaleProdDOForMsmall;
import com.adtec.prod.oper.definition.saleprod.entity.TPipSaleProdAtomProdDO;
import com.adtec.prod.oper.definition.saleprod.entity.TPipSaleProdDO;
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

/**
 * 可售产品管理Dao接口
 * @author zengxj
 * @version 20200104
 */
@Component
public class TPipSaleProdDao implements IBaseDao<TPipSaleProdDO>{
	/**
	 * 日志对象
	 */
	protected final static Logger logger = LoggerFactory.getLogger(TPipSaleProdDao.class);
	/*表名称*/
    public static final String TABLE_NAME = "t_pip_sale_prod";
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public TPipSaleProdDO get(String id) {
		TPipSaleProdDO obj = new TPipSaleProdDO();
		obj.setSaleProdCode(id);
		return get(obj);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	@Override
    public TPipSaleProdDO get(TPipSaleProdDO obj) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where sale_prod_code=?");
        TPipSaleProdDO rs = null;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObject(sql.toString(), TPipSaleProdDO.class, obj.getSaleProdCode());
        } catch (Exception e) {
            logger.error("获取可售产品管理异常："+e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "获取可售产品管理失败！");
        }
        return rs;
    }
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	@Override
	public int insert(TPipSaleProdDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			ignoreFields.add("shortRmrk");
			ignoreFields.add("midRmrk");
			ignoreFields.add("dac");
			rs = session.saveObject(TABLE_NAME, obj, ignoreFields);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("新增可售产品管理交易异常："+e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "新增可售产品管理交易失败！");
		}
		return rs;
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	@Override
	public int update(TPipSaleProdDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			ignoreFields.add("saleProdCode");
			ignoreFields.add("shortRmrk");
			ignoreFields.add("midRmrk");
			ignoreFields.add("dac");
			ignoreFields.add("crtr");
			ignoreFields.add("crtTime");
			List<String> matchFields = obj.getMatchFields();
			rs = session.updateObject(TABLE_NAME, obj, matchFields, ignoreFields);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("修改可售产品管理异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "修改可售产品管理失败！");
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
		TPipSaleProdDO obj = new TPipSaleProdDO();
		obj.setSaleProdCode(id);
		return delete(obj);
	}
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param obj
	 * @return
	 */
	@Override
	public int delete(TPipSaleProdDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			String sql = "DELETE FROM t_pip_sale_prod where sale_prod_code=?";
			rs = session.execute(sql, obj.getSaleProdCode());
			String del = "DELETE FROM T_PIP_SALE_PROD_ATOM_PROD where sale_prod_code=?";
			session.execute(del, obj.getSaleProdCode());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				session.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
	public List<TPipSaleProdDO> list(TPipSaleProdDO obj) {
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
	public List<TPipSaleProdDO> list(TPipSaleProdDO obj, int start, int limit) {
		logger.debug("TPipSaleProdDO=" + obj.toString());
		logger.debug("start=" + start);
		logger.debug("limit=" + limit);
		List<TPipSaleProdDO> list = null;
		try {
			StringBuilder sql = new StringBuilder();
			List<Object> params = Lists.newArrayList();
        	sql.append("select t.sale_prod_code, t.sale_prod_desc, t.prod_line_code, t.url, t.brch_id, l.prod_line_name from ").append(TABLE_NAME).append(" t left join t_pip_line_prod l on l.prod_line_code = t.prod_line_code ")
        	.append(getWhereSql(obj, params));
        	sql.append(" order by t.SALE_PROD_CODE desc");
        	logger.debug("sql=" + sql.toString());
        	IDBSession session = DBSessionFactory.getSession();
			if (limit == 0) {
				list = session.getObjectListByList(sql.toString(), TPipSaleProdDO.class, params);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), TPipSaleProdDO.class, start, limit, params);
			}
		} catch (Exception e) {
			logger.error("列表查询异常：" + e.getMessage());
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
	public List<TPipSaleProdDOForMsmall> listForMsmall(TPipSaleProdDOForMsmall obj, int start, int limit) {
		logger.debug("TPipSaleProdDO=" + obj.toString());
		logger.debug("start=" + start);
		logger.debug("limit=" + limit);
		List<TPipSaleProdDOForMsmall> list = null;
		List<Object> parameters = Lists.newArrayList();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select distinct t.sale_prod_code, t.sale_prod_desc, t.prod_line_code, t.url, l.prod_line_name ")
					.append("from t_pip_sale_prod t left join t_pip_line_prod l on l.prod_line_code = t.prod_line_code ")
					.append("LEFT JOIN T_PIP_SALE_PROD_ATOM_PROD m on m.sale_prod_code=t.sale_prod_code ")
					.append(getWhereSqlForMsmall(obj, parameters));
			sql.append(" order by t.SALE_PROD_CODE desc");
			logger.debug("sql=" + sql.toString());
			IDBSession session = DBSessionFactory.getSession();
			if (limit == 0) {
				list = session.getObjectListByList(sql.toString(), TPipSaleProdDOForMsmall.class, parameters);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), TPipSaleProdDOForMsmall.class, start, limit, parameters);
			}
		} catch (Exception e) {
			logger.error("列表查询异常：" + e.getMessage());
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
	public List<TPipSaleProdDO> list(int start, int limit, Object... param) {
		return null;
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(TPipSaleProdDO obj) {
		logger.debug("TPipSaleProdDO=" + obj);
        StringBuilder sql = new StringBuilder();
		List<Object> params = Lists.newArrayList();
        sql.append("select t.sale_prod_code, t.sale_prod_desc, t.prod_line_code, l.prod_line_name from ").append(TABLE_NAME).append(" t left join t_pip_line_prod l on l.prod_line_code = t.prod_line_code ")
    	.append(getWhereSql(obj, params));
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        logger.debug("countSql=" + countSql);
        IDBSession session = DBSessionFactory.getSession();       
		int total = 0;
        try {
        	total = session.accountByList(countSql, params);
        } catch (Exception e) {
        	logger.error("总记录数查询t_pip_sale_prod异常：" + e.getMessage());
        	throw new BaseException(SysErr.E_MESSAGE, "总记录数查询t_pip_sale_prod失败！");
        }
        return total;
	}

	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotalForMsmall(TPipSaleProdDOForMsmall obj) {
		logger.debug("TPipSaleProdDO=" + obj);
		StringBuilder sql = new StringBuilder();
		List<Object> parameters = Lists.newArrayList();
		sql.append("select distinct t.sale_prod_code, t.sale_prod_desc, t.prod_line_code, t.url, l.prod_line_name ")
				.append("from t_pip_sale_prod t left join t_pip_line_prod l on l.prod_line_code = t.prod_line_code ")
				.append("LEFT JOIN T_PIP_SALE_PROD_ATOM_PROD m on m.sale_prod_code=t.sale_prod_code ")
				.append(getWhereSqlForMsmall(obj, parameters));
		String countSql = "select count(1) from (" + sql.toString() + ") ct";
		logger.debug("countSql=" + countSql);
		IDBSession session = DBSessionFactory.getSession();
		int total = 0;
		try {
			total = session.accountByList(countSql, parameters);
		} catch (Exception e) {
			logger.error("总记录数查询t_pip_sale_prod异常：" + e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "总记录数查询t_pip_sale_prod失败！");
		}
		return total;
	}

	/**
	 * 根据数据对象产生对应的查询SQL
	 * @param obj 数据对象DO
	 * @return SQL
	 */
	public String getWhereSql(TPipSaleProdDO obj, List<Object> params) {
		StringBuffer sql = new StringBuffer(" where 1=1 ");
		if (!DataUtil.isNullStr(obj.getSaleProdCode())) {
			sql.append(" AND t.sale_prod_code = ? ");
			params.add(obj.getSaleProdCode());
		}
		if (!DataUtil.isNullStr(obj.getSaleProdDesc())) {
            sql.append(" AND t.sale_prod_desc like ? ");
            params.add("%"+obj.getSaleProdDesc()+"%");
		}
		if (!DataUtil.isNullStr(obj.getProdLineCode())) {
            sql.append(" AND t.prod_line_code = ? ");
            params.add(obj.getProdLineCode());
		}
		return sql.toString();
	}

	/**
	 * 根据数据对象产生对应的查询SQL
	 * @param obj 数据对象DO
	 * @return SQL
	 */
	public String getWhereSqlForMsmall(TPipSaleProdDOForMsmall obj, List<Object> parameters) {
		StringBuffer sql = new StringBuffer(" where 1=1 ");
		if (!DataUtil.isNullStr(obj.getSaleProdCode())) {
			sql.append(" AND t.sale_prod_code = ? ");
			parameters.add(obj.getSaleProdCode());
		}
		if (!DataUtil.isNullStr(obj.getSaleProdDesc())) {
			sql.append(" AND t.sale_prod_desc like ? ");
			parameters.add("%"+obj.getSaleProdDesc()+"%");
		}
		if (!DataUtil.isNullStr(obj.getProdLineCode())) {
			sql.append(" AND t.prod_line_code = ? ");
			parameters.add(obj.getProdLineCode());
		}
		if (!DataUtil.isNullStr(obj.getCompNo())) {
			sql.append(" AND ( ");
			String[] ss = obj.getCompNo().split("\\,");
			for (int i = 0 ; i < ss.length; i++) {
				if (i+1 == ss.length) {
					sql.append(" m.comp_no = ? ");
				} else {
					sql.append(" m.comp_no = ? or ");
				}
				parameters.add(ss[i]);
			}
			sql.append(" ) ");
		}
		return sql.toString();
	}

	public String getWhereSqlAtom(TPipSaleProdAtomProdDO obj, List<Object> parameters) {
		StringBuffer sql = new StringBuffer(" where 1=1 ");
		if ( !DataUtil.isNullStr(obj.getSaleProdCode())) {
			sql.append(" AND sale_prod_code = ? ");
			parameters.add(obj.getSaleProdCode());
		}
		if ( !DataUtil.isNullStr(obj.getCompNo())) {
			sql.append(" AND comp_no = ? ");
			parameters.add(obj.getCompNo());
		}
		if ( !DataUtil.isNullStr(obj.getAtomProdCode())) {
			sql.append(" AND atom_prod_code = ? ");
			parameters.add(obj.getAtomProdCode());
		}
		return sql.toString();
	}

	/**
	 * 获取最大的可售产品code
	 * @return
	 */
	public String getMaxCode(String prodLineCode) {
		String saleProdCode = "";
		List<TPipSaleProdDO> list = Lists.newArrayList();
		StringBuffer sql = new StringBuffer();
		List<Object> params = Lists.newArrayList();
		sql.append("select * from ").append(TABLE_NAME).append(" where prod_line_code = ? order by SALE_PROD_CODE DESC");
		params.add(prodLineCode);
		try{
			IDBSession session = DBSessionFactory.getSession();
			list = session.getObjectListByList(sql.toString(), TPipSaleProdDO.class, params);
		} catch (Exception e) {
			logger.error("可售产品编号最大值获取异常：" + e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "可售产品编号最大值获取失败！");
		}
		if (list.size() > 0) {
			TPipSaleProdDO saleProdDO = list.get(0);
			saleProdCode = saleProdDO.getSaleProdCode();
		}
		
		return saleProdCode;
	}
	
	/**
	 * 根据可售产品编号获取原子产品关联信息
	 * @param saleProdCode
	 * @return
	 */
	public List<TPipSaleProdAtomProdDO> getSaleAtomList(String saleProdCode) {
		List<TPipSaleProdAtomProdDO> list = Lists.newArrayList();
		StringBuffer sql = new StringBuffer();
		List<Object> params = Lists.newArrayList();
		sql.append("select * from T_PIP_SALE_PROD_ATOM_PROD where SALE_PROD_CODE = ?");
		params.add(saleProdCode);
		try{
			IDBSession session = DBSessionFactory.getSession();
			list = session.getObjectListByList(sql.toString(), TPipSaleProdAtomProdDO.class, params);
		} catch (Exception e) {
			logger.error("可售产品与原子产品关联信息获取异常：" + e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "可售产品与原子产品关联信息获取失败！");
		}
		return list;
	}

	/**
	 * 根据可售产品编号获取原子产品关联信息
	 * @param obj
	 * @return
	 */
	public List<TPipSaleProdAtomProdDO> getSaleAtomList(TPipSaleProdAtomProdDO obj, int start, int limit) {
		List<TPipSaleProdAtomProdDO> list = Lists.newArrayList();
		List<Object> parameters = com.google.common.collect.Lists.newArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from T_PIP_SALE_PROD_ATOM_PROD ").append(getWhereSqlAtom(obj, parameters))
				.append("order by sale_prod_code, comp_no");
		try{
			IDBSession session = DBSessionFactory.getSession();
			if (limit == 0) {
				list = session.getObjectListByList(sql.toString(), TPipSaleProdAtomProdDO.class, parameters);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), TPipSaleProdAtomProdDO.class, start, limit, parameters);
			}
		} catch (Exception e) {
			logger.error("可售产品与原子产品关联信息获取异常：" + e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "可售产品与原子产品关联信息获取失败！");
		}
		return list;
	}

	public int getTotalAtom(TPipSaleProdAtomProdDO obj) {
		List<Object> parameters = com.google.common.collect.Lists.newArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("select * from T_PIP_SALE_PROD_ATOM_PROD ").append(getWhereSqlAtom(obj, parameters));
		String countSql = "select count(1) from (" + sql.toString() + ") ct";
		logger.debug("countSql=" + countSql);
		IDBSession session = DBSessionFactory.getSession();
		int total = 0;
		try {
			total = session.accountByList(countSql, parameters);
		} catch (Exception e) {
			logger.error("总记录数查询t_pip_svc异常：" + e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "总记录数查询t_pip_svc失败！");
		}
		return total;
	}

	/**
	 * 根据
	 * @param saleProdCode
	 * @return
	 */
	public TPipSaleProdAtomProdDO getAtomProdBySaleProdCode(String saleProdCode) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 获取可售产品包装数
	 * @param saleProdDO
	 * @return
	 */
	public int getAdapterCount(TPipSaleProdDO saleProdDO) {
		int rs = 0;
		StringBuffer sql = new StringBuffer();
		List<Object> params = Lists.newArrayList();
		sql.append("select count(*) from T_PIP_SALE_PROD_ADAPTER where SALE_PROD_CODE = ?");
		params.add(saleProdDO.getSaleProdCode());
		
		IDBSession session = DBSessionFactory.getSession();
		try {
			rs = session.accountByList(sql.toString(), params);
		} catch (Exception e) {
			logger.error("获取可售产品包装数异常：" + e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "获取可售产品包装数失败！");
		}
		
		return rs;
	}
	
	/**
	 * 获取可售产品数
	 * @param obj
	 * @return
	 */
	public int getSaleProdCount(TPipSaleProdDO obj) {
		int rs = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from ").append(TABLE_NAME).append(" where SALE_PROD_DESC = ?");
		
		IDBSession session = DBSessionFactory.getSession();
		try {
			rs = session.account(sql.toString(), obj.getSaleProdDesc());
		} catch (Exception e) {
			logger.error("获取可售产品数异常：" + e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "获取可售产品数失败！");
		}
		
		return rs;
	}
	
}