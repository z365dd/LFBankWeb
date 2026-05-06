/**
* 系统名称: SmartWeb平台
* 模块名称: comp-prod/oper/definition/atom持久化模块
* 功能描述: 原子产品数据库操作
* 类 名 称  : TPipAtomProdDao.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200102<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.prod.oper.definition.atom.dao;

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
import com.adtec.prod.oper.definition.atom.entity.TPipAtomProdDO;

/**
 * 原子产品Dao接口
 * @author zengxj
 * @version 20200102
 */
@Component
public class TPipAtomProdDao implements IBaseDao<TPipAtomProdDO>{
	/**
	 * 日志对象
	 */
	protected final static Logger logger = LoggerFactory.getLogger(TPipAtomProdDao.class);
	/*表名称*/
    public static final String TABLE_NAME = "t_pip_atom_prod";
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public TPipAtomProdDO get(String id) {
		TPipAtomProdDO obj = new TPipAtomProdDO();
		obj.setAtomProdCode(id);
		return get(obj);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	@Override
    public TPipAtomProdDO get(TPipAtomProdDO obj) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where ATOM_PROD_CODE =? ");
        TPipAtomProdDO rs = null;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObject(sql.toString(), TPipAtomProdDO.class, obj.getAtomProdCode());
        } catch (Exception e) {
            logger.error("获取原子产品异常："+e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "获取原子产品失败！");
        }
        return rs;
    }
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	@Override
	public int insert(TPipAtomProdDO obj){
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
			logger.error("新增原子产品交易异常："+e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "新增原子产品交易失败！");
		}
		return rs;
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	@Override
	public int update(TPipAtomProdDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			ignoreFields.add("compName");
			ignoreFields.add("shortRmrk");
			ignoreFields.add("midRmrk");
			ignoreFields.add("longRmrk");
			ignoreFields.add("dac");
			List<String> matchFields = obj.getMatchFields();
			rs = session.updateObject(TABLE_NAME, obj, matchFields, ignoreFields);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("修改原子产品异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "修改原子产品失败！");
		}
		return rs;
	}
	
	/**
	 * 根据主键id删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param id
	 * @see public int delete(T entity)
	 * @return
	 */
	public int delete(String atomProdCode){
		TPipAtomProdDO obj = new TPipAtomProdDO();
		obj.setAtomProdCode(atomProdCode);
		return delete(obj);
	}
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param obj
	 * @return
	 */
	@Override
	public int delete(TPipAtomProdDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			String sql = "DELETE FROM t_pip_atom_prod where ATOM_PROD_CODE=?";
			rs = session.execute(sql, obj.getAtomProdCode());
			if(rs==0){
				throw new BaseException(SysErr.E_MESSAGE, "删除交易失败，影响记录数："+rs);
			}
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
	public List<TPipAtomProdDO> list(TPipAtomProdDO obj) {
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
	public List<TPipAtomProdDO> list(TPipAtomProdDO obj, int start, int limit) {
		logger.debug("TPipAtomProdDO=" + obj.toString());
		logger.debug("start=" + start);
		logger.debug("limit=" + limit);
		List<TPipAtomProdDO> list = null;
		try {
			StringBuilder sql = new StringBuilder();
			List<Object> params = Lists.newArrayList();
//        	sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj));
//        	sql.append(" order by update_date desc");
			sql.append("select p.atom_prod_code, c.COMP_NO, c.COMP_NAME, p.atom_prod_desc from T_PIP_ATOM_PROD p LEFT JOIN t_pip_comp c on p.COMP_NO = c.COMP_NO ")
			.append(getWhereSql(obj, params));
        	logger.debug("sql=" + sql.toString());
        	IDBSession session = DBSessionFactory.getSession();
			if (limit == 0) {
				list = session.getObjectListByList(sql.toString(), TPipAtomProdDO.class, params);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), TPipAtomProdDO.class, start, limit, params);
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
	public List<TPipAtomProdDO> list(int start, int limit, Object... param) {
		return null;
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(TPipAtomProdDO obj) {
		logger.debug("TPipAtomProdDO=" + obj);
        StringBuilder sql = new StringBuilder();
		List<Object> params = Lists.newArrayList();
//        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj));
        sql.append("select p.atom_prod_code, c.COMP_NO, c.COMP_NAME, p.atom_prod_desc from T_PIP_ATOM_PROD p LEFT JOIN t_pip_comp c on p.COMP_NO = c.COMP_NO ")
			.append(getWhereSql(obj, params));
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        logger.debug("countSql=" + countSql);
        IDBSession session = DBSessionFactory.getSession();       
		int total = 0;
        try {
        	total = session.accountByList(countSql, params);
        } catch (Exception e) {
        	logger.error("总记录数查询t_pip_atom_prod异常：" + e.getMessage());
        	throw new BaseException(SysErr.E_MESSAGE, "总记录数查询t_pip_atom_prod失败！");
        }
        return total;
	}
	
	/**
	 * 根据数据对象产生对应的查询SQL
	 * @param obj 数据对象DO
	 * @return SQL
	 */
	public String getWhereSql(TPipAtomProdDO obj, List<Object> params) {
		StringBuffer sql = new StringBuffer(" where 1=1 ");
		if (!DataUtil.isNullStr(obj.getAtomProdDesc())) {
            sql.append(" AND p.atom_prod_desc like ? ");
            params.add("%"+obj.getAtomProdDesc()+"%");
		}
		if (!DataUtil.isNullStr(obj.getCompNo())) {
			sql.append(" AND c.comp_no = ? ");
			params.add(obj.getCompNo());
		}
		return sql.toString();
	}
	
	/**
	 * 获取当前原子产品代码最大值
	 * @param compNo
	 * @return
	 */
	public String getMaxAtomProdCode(String compNo) {
		List<TPipAtomProdDO> list = Lists.newArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from ").append(TABLE_NAME).append(" where comp_no = ? order by ATOM_PROD_CODE desc");
		IDBSession session = DBSessionFactory.getSession();       
        try {
        	list = session.getObjectList(sql.toString(), TPipAtomProdDO.class, compNo);
        } catch (Exception e) {
        	logger.error("查询t_pip_atom_prod异常：" + e.getMessage());
        	throw new BaseException(SysErr.E_MESSAGE, "查询t_pip_atom_prod失败！");
        }
        
        String code = "";
        if (list.size() > 0) {
        	TPipAtomProdDO prod = list.get(0);
        	code = prod.getAtomProdCode();
        }
        
        return code;
	}

	/**
	 * 获取可售产品数
	 * @param atomProdCode
	 * @return
	 */
	public int getSaleProdCount(String atomProdCode) {
		int rs = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from T_PIP_SALE_PROD_ATOM_PROD where ATOM_PROD_CODE = ?");
		
		IDBSession session = DBSessionFactory.getSession();
		try {
			rs = session.account(sql.toString(), atomProdCode);
		} catch (Exception e) {
			logger.error("获取可售产品数异常：" + e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "获取可售产品数失败！");
		}
		
		return rs;
	}

	/**
	 * 获取原子产品数
	 * @param obj
	 * @return
	 */
	public int getAtomCount(TPipAtomProdDO obj) {
		int rs = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from ").append(TABLE_NAME).append(" where ATOM_PROD_DESC = ?");
		
		IDBSession session = DBSessionFactory.getSession();
		try {
			rs = session.account(sql.toString(), obj.getAtomProdDesc());
		} catch (Exception e) {
			logger.error("获取原子产品数异常：" + e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "获取原子产品数失败！");
		}
		
		return rs;
	}
	
}