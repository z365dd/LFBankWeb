/**
* 系统名称: SmartWeb平台
* 模块名称: comp.prod.oper持久化模块
* 功能描述: 业务表数据库操作
* 类 名 称  : TPipBusiDao.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200114<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.prod.oper.dao;

import com.adtec.prod.oper.entity.TPipBusiDO;
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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 业务表Dao接口
 * @author zh
 * @version 20200114
 */
@Component
public class TPipBusiDao implements IBaseDao<TPipBusiDO>{
	/**
	 * 日志对象
	 */
	protected final static Logger log = LoggerFactory.getLogger(TPipBusiDao.class);
	/*表名称*/
    public static final String TABLE_NAME = "t_pip_busi";

	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	@Override
    public TPipBusiDO get(TPipBusiDO obj) {
        StringBuilder sql = new StringBuilder();
		sql.append("select * from ").append(TABLE_NAME).append(" where busi_no=? ");
        TPipBusiDO rs = null;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObject(sql.toString(), TPipBusiDO.class, obj.getBusiNo());
        } catch (Exception e) {
            log.error("获取业务表异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "获取业务表失败！");
        }
        return rs;
    }

    public String getBusiNo(String SALE_PROD_CODE) {
		String busiNo = SALE_PROD_CODE + "0001";
		StringBuilder sql = new StringBuilder();
		sql.append("select MAX(busi_no) busi_no from t_pip_busi where busi_no like ?");
		IDBSession session = DBSessionFactory.getSession();
		ResultSet rs = null;
		try {
			rs = session.getResultSet(sql.toString(), SALE_PROD_CODE+"%");
			if (rs.next()) {
				String val = rs.getString(1);
				if (val == null) {
					return busiNo;
				}
				String temp = String.valueOf(Integer.valueOf(val.substring(SALE_PROD_CODE.length()))+1);
				StringBuffer busiNoBuff = new StringBuffer();
				busiNoBuff.append(SALE_PROD_CODE);
				for (int i=1; i<=4; i++) {
					if (i > temp.length()) {
						busiNoBuff.append("0");
					}
				}
				busiNoBuff.append(temp);
				return busiNoBuff.toString();
			} else {
				return busiNo;
			}
		} catch (SQLException e) {
			throw new BaseException(SysErr.E_MESSAGE, "列表查询业务文档信息失败！");
		} finally {
			try {
				session.closeResultSetAndStatement(rs);
				DBSessionFactory.closeSession(session);
			} catch (SQLException e) {
				System.out.println("sql异常");
			}
		}
    }

	public String getRuleId(String type) {
		String ruleId = type + "0000000001";
		StringBuilder sql = new StringBuilder();
		sql.append("select MAX(rule_id) rule_id from T_PIP_RULE_RELAT where rule_id like ?");
		IDBSession session = DBSessionFactory.getSession();
		ResultSet rs = null;
		try {
			rs = session.getResultSet(sql.toString(), type+"%");
			if (rs.next()) {
				String val = rs.getString(1);
				if (val == null) {
					return ruleId;
				}
				String temp = String.valueOf(Integer.valueOf(val.substring(3))+1);
				StringBuffer ruleIdBuff = new StringBuffer();
				ruleIdBuff.append(type);
				for (int i=1; i<=10; i++) {
					if (i > temp.length()) {
						ruleIdBuff.append("0");
					}
				}
				ruleIdBuff.append(temp);
				return ruleIdBuff.toString();
			} else {
				return ruleId;
			}
		} catch (SQLException e) {
			throw new BaseException(SysErr.E_MESSAGE, "列表查询业务文档信息失败！");
		} finally {
			try {
				session.closeResultSetAndStatement(rs);
				DBSessionFactory.closeSession(session);
			} catch (SQLException e) {
				System.out.println("sql异常");
			}
		}
	}

    /**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	@Override
	public int insert(TPipBusiDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			rs = session.saveObject(TABLE_NAME, obj, ignoreFields);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("新增业务表交易异常", e);
            throw new BaseException(SysErr.E_MESSAGE, "新增业务表交易失败！");
		}
		return rs;
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	@Override
	public int update(TPipBusiDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			List<String> matchFields = obj.getMatchFields();
			rs = session.updateObject(TABLE_NAME, obj, matchFields, ignoreFields);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("修改业务表异常", e);
            throw new BaseException(SysErr.E_MESSAGE, "修改业务表失败！");
		}
		return rs;
	}

	public int update(String busiNo,String openStat) {
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		String sql = "update "+TABLE_NAME+" set open_stat=? where busi_no=?";
		try {
			rs = session.execute(sql, openStat, busiNo);
		}catch (SQLException e) {
			log.error("修改业务信息表异常：" + e.getMessage());
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_MESSAGE, "修改业务信息表异常！");
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
		TPipBusiDO obj = new TPipBusiDO();
		obj.setId(id);
		return delete(obj);
	}
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param obj
	 * @return
	 */
	@Override
	public int delete(TPipBusiDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			String sql = "DELETE FROM t_pip_busi where busi_no=?";
			rs = session.execute(sql, obj.getId());
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
	public List<TPipBusiDO> list(TPipBusiDO obj) {
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
	public List<TPipBusiDO> list(TPipBusiDO obj, int start, int limit) {
		log.debug("TPipBusiDO=" + obj.toString());
		log.debug("start=" + start);
		log.debug("limit=" + limit);
		List<TPipBusiDO> list = null;
		try {
			List<Object> parameters = Lists.newArrayList();
			StringBuilder sql = new StringBuilder();
        	sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        	log.debug("sql=" + sql.toString());
        	IDBSession session = DBSessionFactory.getSession();
			if (limit == 0) {
				list = session.getObjectListByList(sql.toString(), TPipBusiDO.class, parameters);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), TPipBusiDO.class, start, limit, parameters);
			}
		} catch (Exception e) {
			log.error("列表查询异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "列表查询失败！");
		}
		return list;
	}
	
	public List<TPipBusiDO> listHaveFeeClr(TPipBusiDO obj, int start, int limit) {
		log.debug("TPipBusiDO=" + obj.toString());
		log.debug("start=" + start);
		log.debug("limit=" + limit);
		List<TPipBusiDO> list = null;
		try {
			List<Object> parameters = Lists.newArrayList();
			StringBuilder sql = new StringBuilder();
        	sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        	sql.append(" and busi_no in (select busi_no from T_PIP_RULE_RELAT where rule_tp = '301' )");
        	log.debug("sql=" + sql.toString());
        	IDBSession session = DBSessionFactory.getSession();
			if (limit == 0) {
				list = session.getObjectListByList(sql.toString(), TPipBusiDO.class, parameters);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), TPipBusiDO.class, start, limit, parameters);
			}
		} catch (Exception e) {
			log.error("列表查询异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "列表查询失败！");
		}
		return list;
	}
	
	public List<TPipBusiDO> listForMsmall(TPipBusiDO obj, int start, int limit) {
		log.debug("TPipBusiDO=" + obj.toString());
		log.debug("start=" + start);
		log.debug("limit=" + limit);
		List<TPipBusiDO> list = null;
		try {
			List<Object> parameters = Lists.newArrayList();
			StringBuilder sql = new StringBuilder();
			sql.append("select distinct a.* from t_pip_busi a LEFT JOIN T_PIP_SALE_PROD_ATOM_PROD c on a.sale_prod_code=c.sale_prod_code ")
					.append("LEFT JOIN T_PIP_BUSI_DOC d on d.busi_no = a.busi_no ")
					.append(getWhereSqlForMsmall(obj, parameters));
			log.debug("sql=" + sql.toString());
			IDBSession session = DBSessionFactory.getSession();
			if (limit == 0) {
				list = session.getObjectListByList(sql.toString(), TPipBusiDO.class, parameters);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), TPipBusiDO.class, start, limit, parameters);
			}
		} catch (Exception e) {
			log.error("列表查询异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "列表查询失败！");
		}
		return list;
	}

	public List<TPipBusiDO> qryCompSvcForMsmall(TPipBusiDO obj, int start, int limit) {
		log.debug("TPipBusiDO=" + obj.toString());
		log.debug("start=" + start);
		log.debug("limit=" + limit);
		List<TPipBusiDO> list = null;
		try {
			List<Object> parameters = Lists.newArrayList();
			StringBuilder sql = new StringBuilder();
			sql.append("select a.*,b.COMP_NO,c.svc_code from T_PIP_BUSI a  ")
					.append("LEFT JOIN T_PIP_SALE_PROD_ATOM_PROD b on a.SALE_PROD_CODE=b.SALE_PROD_CODE ")
					.append("LEFT JOIN T_PIP_ATOM_PROD_SVC c on c.ATOM_PROD_CODE=b.ATOM_PROD_CODE ")
					.append(getWhereSqlForMsmall(obj, parameters));
			log.debug("sql=" + sql.toString());
			IDBSession session = DBSessionFactory.getSession();
			if (limit == 0) {
				list = session.getObjectListByList(sql.toString(), TPipBusiDO.class, parameters);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), TPipBusiDO.class, start, limit, parameters);
			}
		} catch (Exception e) {
			log.error("列表查询异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "列表查询失败！");
		}
		return list;
	}

	public List<TPipBusiDO> listHasFileForMsmall(TPipBusiDO obj, int start, int limit) {
		log.debug("TPipBusiDO=" + obj.toString());
		log.debug("start=" + start);
		log.debug("limit=" + limit);
		List<TPipBusiDO> list = null;
		try {
			List<Object> parameters = Lists.newArrayList();
			StringBuilder sql = new StringBuilder();
			sql.append("select distinct a.* from t_pip_busi a LEFT JOIN T_PIP_SALE_PROD_ATOM_PROD c on a.sale_prod_code=c.sale_prod_code ")
					.append("LEFT JOIN T_PIP_BUSI_DOC d on d.busi_no = a.busi_no ")
					.append(getWhereSqlHasFileForMsmall(obj, parameters));
			log.debug("sql=" + sql.toString());
			IDBSession session = DBSessionFactory.getSession();
			if (limit == 0) {
				list = session.getObjectListByList(sql.toString(), TPipBusiDO.class, parameters);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), TPipBusiDO.class, start, limit, parameters);
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
	public List<TPipBusiDO> listFile(TPipBusiDO obj, int start, int limit) {
		log.debug("TPipBusiDO=" + obj.toString());
		log.debug("start=" + start);
		log.debug("limit=" + limit);
		List<TPipBusiDO> list = null;
		try {
			List<Object> parameters = Lists.newArrayList();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT a.FILE_TP,a.FILE_NAME,a.URL,b.* from T_PIP_BUSI_DOC a LEFT JOIN T_PIP_BUSI b ON a.BUSI_NO=b.BUSI_NO")
					.append(getWhereSql2(obj, parameters));
			log.debug("sql=" + sql.toString());
			IDBSession session = DBSessionFactory.getSession();
			if (limit == 0) {
				list = session.getObjectListByList(sql.toString(), TPipBusiDO.class, parameters);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), TPipBusiDO.class, start, limit, parameters);
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
	public List<TPipBusiDO> list(int start, int limit, Object... param) {
		return null;
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(TPipBusiDO obj) {
		log.debug("TPipBusiDO=" + obj);
		List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        log.debug("countSql=" + countSql);
        IDBSession session = DBSessionFactory.getSession();       
		int total = 0;
        try {
        	total = session.accountByList(countSql, parameters);
        } catch (Exception e) {
        	log.error("总记录数查询t_pip_busi异常：" + e.getMessage());
        	throw new BaseException(SysErr.E_MESSAGE, "总记录数查询t_pip_busi失败！");
        }
        return total;
	}

	public int getTotalForMsmall(TPipBusiDO obj) {
		log.debug("TPipBusiDO=" + obj);
		List<Object> parameters = Lists.newArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct a.* from t_pip_busi a LEFT JOIN T_PIP_SALE_PROD_ATOM_PROD c on a.sale_prod_code=c.sale_prod_code ")
				.append("LEFT JOIN T_PIP_BUSI_DOC d on d.busi_no = a.busi_no ")
				.append(getWhereSqlForMsmall(obj, parameters));
		String countSql = "select count(1) from (" + sql.toString() + ") ct";
		log.debug("countSql=" + countSql);
		IDBSession session = DBSessionFactory.getSession();
		int total = 0;
		try {
			total = session.accountByList(countSql, parameters);
		} catch (Exception e) {
			log.error("总记录数查询t_pip_busi异常：" + e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "总记录数查询t_pip_busi失败！");
		}
		return total;
	}

	public int getTotalCompSvcForMsmall(TPipBusiDO obj) {
		log.debug("TPipBusiDO=" + obj);
		List<Object> parameters = Lists.newArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("select a.*,b.COMP_NO,c.svc_code from T_PIP_BUSI a  ")
				.append("LEFT JOIN T_PIP_SALE_PROD_ATOM_PROD b on a.SALE_PROD_CODE=b.SALE_PROD_CODE ")
				.append("LEFT JOIN T_PIP_ATOM_PROD_SVC c on c.ATOM_PROD_CODE=b.ATOM_PROD_CODE ")
				.append(getWhereSqlForMsmall(obj, parameters));
		String countSql = "select count(1) from (" + sql.toString() + ") ct";
		log.debug("countSql=" + countSql);
		IDBSession session = DBSessionFactory.getSession();
		int total = 0;
		try {
			total = session.accountByList(countSql, parameters);
		} catch (Exception e) {
			log.error("总记录数查询t_pip_busi异常：" + e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "总记录数查询t_pip_busi失败！");
		}
		return total;
	}

	public int getTotalHasFileForMsmall(TPipBusiDO obj) {
		log.debug("TPipBusiDO=" + obj);
		List<Object> parameters = Lists.newArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct a.* from t_pip_busi a LEFT JOIN T_PIP_SALE_PROD_ATOM_PROD c on a.sale_prod_code=c.sale_prod_code ")
				.append("LEFT JOIN T_PIP_BUSI_DOC d on d.busi_no = a.busi_no ")
				.append(getWhereSqlHasFileForMsmall(obj, parameters));
		String countSql = "select count(1) from (" + sql.toString() + ") ct";
		log.debug("countSql=" + countSql);
		IDBSession session = DBSessionFactory.getSession();
		int total = 0;
		try {
			total = session.accountByList(countSql, parameters);
		} catch (Exception e) {
			log.error("总记录数查询t_pip_busi异常：" + e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "总记录数查询t_pip_busi失败！");
		}
		return total;
	}

	public int getTotalAssetsForMsmall(String areaIds, String type) {
		List<Object> parameters = Lists.newArrayList();
		StringBuilder sql = new StringBuilder();
		String[] ss = areaIds.split("\\$");
		for (int i = 0; i< ss.length; i++) {
			String areaId = ss[i];
			// 04产品 05单位 06业务
			if ("04".equals(type)) {
				sql.append("SELECT b.* from T_PIP_SALE_PROD b ")
						.append("LEFT JOIN T_SYS_USER u on u.id=b.crtr ")
						.append("LEFT JOIN T_SYS_OFFICE o on o.id=u.brch_id ")
						.append("LEFT JOIN T_SYS_AREA a on a.id=o.region_id ")
						.append("where a.id=? or a.parent_id_list like ?");
			} else if ("05".equals(type)) {
				sql.append("SELECT b.* from T_PIP_ENTR b ")
						.append("LEFT JOIN T_SYS_USER u on u.id=b.crtr ")
						.append("LEFT JOIN T_SYS_OFFICE o on o.id=u.brch_id ")
						.append("LEFT JOIN T_SYS_AREA a on a.id=o.region_id ")
						.append("where a.id=? or a.parent_id_list like ?");
			} else if ("06".equals(type)) {
				sql.append("SELECT b.* from T_PIP_BUSI b ")
						.append("LEFT JOIN T_SYS_USER u on u.id=b.crtr ")
						.append("LEFT JOIN T_SYS_OFFICE o on o.id=u.brch_id ")
						.append("LEFT JOIN T_SYS_AREA a on a.id=o.region_id ")
						.append("where a.id=? or a.parent_id_list like ?");
			}
			parameters.add(areaId);
			parameters.add("%,"+areaId+",%");
			if (i < ss.length-1) {
				sql.append(" union all ");
			}
		}
		String countSql = "select count(1) from (" + sql.toString() + ") ct";
		log.debug("countSql=" + countSql);
		IDBSession session = DBSessionFactory.getSession();
		int total = 0;
		try {
			total = session.accountByList(countSql, parameters);
		} catch (Exception e) {
			log.error("总记录数查询t_pip_busi异常：" + e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "总记录数查询t_pip_busi失败！");
		}
		return total;
	}

	public int getFileTotal(TPipBusiDO obj) {
		log.debug("TPipBusiDO=" + obj);
		List<Object> parameters = Lists.newArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.FILE_TP,a.FILE_NAME,a.URL,b.* from T_PIP_BUSI_DOC a LEFT JOIN T_PIP_BUSI b ON a.BUSI_NO=b.BUSI_NO").append(getWhereSql2(obj, parameters));
		String countSql = "select count(1) from (" + sql.toString() + ") ct";
		log.debug("countSql=" + countSql);
		IDBSession session = DBSessionFactory.getSession();
		int total = 0;
		try {
			total = session.accountByList(countSql, parameters);
		} catch (Exception e) {
			log.error("总记录数查询t_pip_busi异常：" + e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "总记录数查询t_pip_busi失败！");
		}
		return total;
	}

	/**
	 * 根据数据对象产生对应的查询SQL
	 * @param obj 数据对象DO
	 * @return SQL
	 */
	public String getWhereSql(TPipBusiDO obj, List<Object> parameters) {
		StringBuffer sql = new StringBuffer(" where 1=1 ");
		if ( !DataUtil.isNullStr(obj.getBusiNo())) {
			sql.append(" AND busi_no = ? ");
			parameters.add(obj.getBusiNo());
		}
		if ( !DataUtil.isNullStr(obj.getEntrNo())) {
			sql.append(" AND entr_no = ? ");
			parameters.add(obj.getEntrNo());
		}
		if ( !DataUtil.isNullStr(obj.getOpenStat())) {
			sql.append(" AND open_stat = ? ");
			parameters.add(obj.getOpenStat());
		}
		if ( !DataUtil.isNullStr(obj.getSaleProdCode())) {
			sql.append(" AND sale_prod_code = ? ");
			parameters.add(obj.getSaleProdCode());
		}
		if ( !DataUtil.isNullStr(obj.getBusiName())) {
			sql.append(" AND busi_name like ? ");
			parameters.add("%"+obj.getBusiName()+"%");
		}
		if ( !DataUtil.isNullStr(obj.getEntrName())) {
			sql.append(" AND entr_no in (select e.entr_no from t_pip_entr e where e.entr_name like ?) ");
			parameters.add("%"+obj.getEntrName()+"%");
		}
		return sql.toString();
	}

	public String getWhereSqlForMsmall(TPipBusiDO obj, List<Object> parameters) {
		StringBuffer sql = new StringBuffer(" where 1=1 ");
		if ( !DataUtil.isNullStr(obj.getBusiNo())) {
			sql.append(" AND a.busi_no = ? ");
			parameters.add(obj.getBusiNo());
		}
		if ( !DataUtil.isNullStr(obj.getBusiName())) {
			sql.append(" AND a.busi_name like ? ");
			parameters.add("%"+obj.getBusiName()+"%");
		}
		if ( !DataUtil.isNullStr(obj.getEntrNo())) {
			sql.append(" AND a.entr_no = ? ");
			parameters.add(obj.getEntrNo());
		}
		if ( !DataUtil.isNullStr(obj.getOpenStat())) {
			sql.append(" AND a.open_stat = ? ");
			parameters.add(obj.getOpenStat());
		}
		if ( !DataUtil.isNullStr(obj.getSaleProdCode())) {
			sql.append(" AND a.sale_prod_code = ? ");
			parameters.add(obj.getSaleProdCode());
		}
		if ( !DataUtil.isNullStr(obj.getBrchTp())) {
			sql.append(" AND a.brch_tp = ? ");
			parameters.add(obj.getBrchTp());
		}
		if ( !DataUtil.isNullStr(obj.getCompNo())) {
			sql.append(" AND c.comp_no = ? ");
			parameters.add(obj.getCompNo());
		}
		if ( !DataUtil.isNullStr(obj.getFileName())) {
			sql.append(" AND d.file_name like ? ");
			parameters.add("%"+obj.getFileName()+"%");
		}
		return sql.toString();
	}

	public String getWhereSqlHasFileForMsmall(TPipBusiDO obj, List<Object> parameters) {
		StringBuffer sql = new StringBuffer(" where 1=1 ");
		if ( !DataUtil.isNullStr(obj.getBusiNo())) {
			sql.append(" AND a.busi_no = ? ");
			parameters.add(obj.getBusiNo());
		}
		if ( !DataUtil.isNullStr(obj.getBusiName())) {
			sql.append(" AND a.busi_name like ? ");
			parameters.add("%"+obj.getBusiName()+"%");
		}
		if ( !DataUtil.isNullStr(obj.getEntrNo())) {
			sql.append(" AND a.entr_no = ? ");
			parameters.add(obj.getEntrNo());
		}
		if ( !DataUtil.isNullStr(obj.getOpenStat())) {
			sql.append(" AND a.open_stat = ? ");
			parameters.add(obj.getOpenStat());
		}
		if ( !DataUtil.isNullStr(obj.getSaleProdCode())) {
			sql.append(" AND a.sale_prod_code = ? ");
			parameters.add(obj.getSaleProdCode());
		}
		if ( !DataUtil.isNullStr(obj.getBrchTp())) {
			sql.append(" AND a.brch_tp = ? ");
			parameters.add(obj.getBrchTp());
		}
		if ( !DataUtil.isNullStr(obj.getCompNo())) {
			sql.append(" AND c.comp_no = ? ");
			parameters.add(obj.getCompNo());
		}
		if ( !DataUtil.isNullStr(obj.getFileName())) {
			sql.append(" AND d.file_name like ? ");
			parameters.add("%"+obj.getFileName()+"%");
		} else {
			sql.append(" AND d.file_name is not null ");
		}
		return sql.toString();
	}

	public String getWhereSql2(TPipBusiDO obj, List<Object> parameters) {
		StringBuffer sql = new StringBuffer(" where 1=1 ");
		if ( !DataUtil.isNullStr(obj.getBusiNo())) {
			sql.append(" AND b.busi_no = ? ");
			parameters.add(obj.getBusiNo());
		}
		if ( !DataUtil.isNullStr(obj.getEntrNo())) {
			sql.append(" AND b.entr_no = ? ");
			parameters.add(obj.getEntrNo());
		}
		if ( !DataUtil.isNullStr(obj.getOpenStat())) {
			sql.append(" AND b.open_stat = ? ");
			parameters.add(obj.getOpenStat());
		}
		if ( !DataUtil.isNullStr(obj.getSaleProdCode())) {
			sql.append(" AND b.sale_prod_code = ? ");
			parameters.add(obj.getSaleProdCode());
		}
		if ( !DataUtil.isNullStr(obj.getFileName())) {
			sql.append(" AND a.file_name like ? ");
			parameters.add("%"+obj.getFileName()+"%");
		}
		return sql.toString();
	}

	public TPipBusiDO get(String busiNo) {
		TPipBusiDO busiDO = new TPipBusiDO();
		busiDO.setBusiNo(busiNo);
		return get(busiDO);
	}
}