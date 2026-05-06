/**
* 系统名称: SmartWeb平台
* 模块名称: comp-prod/comp持久化模块
* 功能描述: 组件信息数据库操作
* 类 名 称  : TParaCompDao.java
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
package com.adtec.prod.oper.dao;

import com.adtec.prod.oper.entity.TPipCompDO;
import com.adtec.prod.oper.entity.TPipCompParaDO;
import com.adtec.prod.oper.entity.TPipSvcCompScenDO;
import com.adtec.prod.oper.entity.TPipSvcDO;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.common.dao.IBaseDao;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.common.util.DataUtil;
import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

/**
 * 组件信息Dao接口
 * @author zengxj
 * @version 20200102
 */
@Component
public class TPipCompDao implements IBaseDao<TPipCompDO>{
	/**
	 * 日志对象
	 */
	protected final static Logger logger = LoggerFactory.getLogger(TPipCompDao.class);
	/*表名称*/
    public static final String TABLE_NAME = "t_pip_comp";
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public TPipCompDO get(String id) {
		TPipCompDO obj = new TPipCompDO();
		obj.setId(id);
		return get(obj);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	@Override
    public TPipCompDO get(TPipCompDO obj) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where id=? AND del_flag='" + TPipCompDO.DEL_FLAG_NORMAL + "'");
        TPipCompDO rs = null;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObject(sql.toString(), TPipCompDO.class, obj.getId());
        } catch (Exception e) {
            logger.error("获取组件信息异常："+e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "获取组件信息失败！");
        }
        return rs;
    }
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	@Override
	public int insert(TPipCompDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			ignoreFields.add("compNo");
			ignoreFields.add("compName");
			ignoreFields.add("prodCompTp");
			ignoreFields.add("shortRmrk");
			ignoreFields.add("midRmrk");
			ignoreFields.add("longRmrk");
			ignoreFields.add("dac");
			rs = session.saveObject(TABLE_NAME, obj, ignoreFields);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("新增组件信息交易异常："+e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "新增组件信息交易失败！");
		}
		return rs;
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	@Override
	public int update(TPipCompDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			ignoreFields.add("compNo");
			ignoreFields.add("compName");
			ignoreFields.add("prodCompTp");
			ignoreFields.add("shortRmrk");
			ignoreFields.add("midRmrk");
			ignoreFields.add("longRmrk");
			ignoreFields.add("dac");
			List<String> matchFields = obj.getMatchFields();
			rs = session.updateObject(TABLE_NAME, obj, matchFields, ignoreFields);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("修改组件信息异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "修改组件信息失败！");
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
		TPipCompDO obj = new TPipCompDO();
		obj.setId(id);
		return delete(obj);
	}
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param obj
	 * @return
	 */
	@Override
	public int delete(TPipCompDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			String sql = "DELETE FROM t_para_comp where id=?";
			rs = session.execute(sql, obj.getId());
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
	public List<TPipCompDO> list(TPipCompDO obj) {
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
	public List<TPipCompDO> list(TPipCompDO obj, int start, int limit) {
		logger.debug("TParaCompDO=" + obj.toString());
		logger.debug("start=" + start);
		logger.debug("limit=" + limit);
		List<TPipCompDO> list = null;
		try {
			List<Object> parameters = Lists.newArrayList();
			StringBuilder sql = new StringBuilder();
        	sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        	sql.append(" order by comp_no desc");
        	logger.debug("sql=" + sql.toString());
        	IDBSession session = DBSessionFactory.getSession();
			if (limit == 0) {
				list = session.getObjectListByList(sql.toString(), TPipCompDO.class, parameters);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), TPipCompDO.class, start, limit, parameters);
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
	public List<TPipCompDO> list(int start, int limit, Object... param) {
		return null;
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(TPipCompDO obj) {
		logger.debug("TParaCompDO=" + obj);
		List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        logger.debug("countSql=" + countSql);
        IDBSession session = DBSessionFactory.getSession();       
		int total = 0;
        try {
        	total = session.accountByList(countSql, parameters);
        } catch (Exception e) {
        	logger.error("总记录数查询t_para_comp异常：" + e.getMessage());
        	throw new BaseException(SysErr.E_MESSAGE, "总记录数查询t_para_comp失败！");
        }
        return total;
	}
	
	/**
	 * 根据数据对象产生对应的查询SQL
	 * @param obj 数据对象DO
	 * @return SQL
	 */
	public String getWhereSql(TPipCompDO obj, List<Object> parameters) {
		StringBuffer sql = new StringBuffer(" where 1=1 ");
		if (!DataUtil.isNullStr(obj.getCompNo())) {
            sql.append(" AND comp_no = ? ");
            parameters.add(obj.getCompNo());
		}
		if (!DataUtil.isNullStr(obj.getProdCompTp())) {
            sql.append(" AND PROD_COMP_TP = ? ");
			parameters.add(obj.getProdCompTp());
		}
		return sql.toString();
	}
	
	/**
	 * 根据模型号获取服务属性表
	 * @param compNo
	 * @return
	 */
	public List<TPipSvcDO> getSvcList(String compNo) {
		List<TPipSvcDO> list = Lists.newArrayList();
		List<Object> parameters = Lists.newArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from T_PIP_SVC where comp_no = ?");
		parameters.add(compNo);
		IDBSession session = DBSessionFactory.getSession();       
        try {
        	list = session.getObjectListByList(sql.toString(), TPipSvcDO.class, parameters);
        } catch (Exception e) {
        	logger.error("获取服务属性表异常：" + e.getMessage());
        	throw new BaseException(SysErr.E_MESSAGE, "获取服务属性表失败！");
        }
		return list;
	}
	
	/**
	 * 根据模型号，服务码获取场景
	 * @param compNo
	 * @param svcCode
	 * @return
	 */
	public List<TPipSvcCompScenDO> getSubSvcList(String compNo, String svcCode) {
		List<TPipSvcCompScenDO> list = Lists.newArrayList();
		List<Object> parameters = Lists.newArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from T_PIP_SVC_COMP_SCEN where comp_no = ? and SVC_CODE = ?");
		parameters.add(compNo);
		parameters.add(svcCode);
		IDBSession session = DBSessionFactory.getSession();       
        try {
        	list = session.getObjectListByList(sql.toString(), TPipSvcCompScenDO.class, parameters);
        } catch (Exception e) {
        	logger.error("获取服务关联表表异常：" + e.getMessage());
        	throw new BaseException(SysErr.E_MESSAGE, "获取服务关联表失败！");
        }
		return list;
	}
	
	/**
	 * 获取组件属性表
	 * @param tPipCompDO
	 * @return
	 */
	public List<TPipCompParaDO> getCompParaByCompNo(TPipCompDO tPipCompDO) {
		List<TPipCompParaDO> list = Lists.newArrayList();
		List<Object> parameters = Lists.newArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select p.*, k.ENTER_TP as SHORT_RMRK, c.ELEM_KV as MID_RMRK, k.val_len as LONG_RMRK from T_PIP_COMP_PARA p left join T_PIP_KEY k on k.KEY_NO = p.KEY_NO LEFT JOIN T_PIP_KEY_CTRL c on c.KEY_NO = p.key_no ");
		sql.append(" where p.comp_no = ? order by ser desc ");
		parameters.add(tPipCompDO.getCompNo());
		IDBSession session = DBSessionFactory.getSession();       
        try {
        	list = session.getObjectListByList(sql.toString(), TPipCompParaDO.class, parameters);
        } catch (Exception e) {
        	logger.error("获取组件属性表异常：" + e.getMessage());
        	throw new BaseException(SysErr.E_MESSAGE, "获取组件属性表失败！");
        }
		return list;
	}
	
	/**
	 * 保存技术参数
	 * @param tPipCompDO
	 */
	public void saveParaConfInfo(TPipCompParaDO tPipCompDO) {
		List<Object> parameters = Lists.newArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE T_PIP_COMP_PARA set kv = ?, flg = 'Y' where COMP_NO = ? and KEY_NO = ?");
		parameters.add(tPipCompDO.getKv());
		parameters.add(tPipCompDO.getCompNo());
		parameters.add(tPipCompDO.getKeyNo());
		IDBSession session = DBSessionFactory.getSession();       
        try {
        	session.executeByList(sql.toString(), parameters);
        } catch (Exception e) {
        	logger.error("保存技术参数异常：" + e.getMessage());
        	throw new BaseException(SysErr.E_MESSAGE, "保存技术参数失败！");
        }
	}
	
}