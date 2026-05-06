/**
* 系统名称: SmartWeb平台
* 模块名称: comp.tseq.oper持久化模块
* 功能描述: 组件属性表数据库操作
* 类 名 称  : TseqTPipCompParaDao.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200804<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.comp.tseq.oper.dao;

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
import com.adtec.comp.tseq.oper.entity.TseqTPipCompParaDO;

/**
 * 组件属性表Dao接口
 * @author zhengjt
 * @version 20200804
 */
@Component
public class TseqTPipCompParaDao implements IBaseDao<TseqTPipCompParaDO>{
	/**
	 * 日志对象
	 */
	protected final static Logger log = LoggerFactory.getLogger(TseqTPipCompParaDao.class);
	/*表名称*/
    public static final String TABLE_NAME = "t_pip_comp_para";
    
	/**
	 * 获取单条数据
	 * @param compNo
	 * @param keyTp
	 * @param keyNo
	 * @return
	 */
	public TseqTPipCompParaDO get(String compNo, String keyTp, String keyNo ) {
		TseqTPipCompParaDO obj = new TseqTPipCompParaDO();
	 	obj.setCompNo(compNo);
	 	obj.setKeyTp(keyTp);
	 	obj.setKeyNo(keyNo);
		return get(obj);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	@Override
    public TseqTPipCompParaDO get(TseqTPipCompParaDO obj) {
        StringBuilder sql = new StringBuilder();
        List<Object> parameters = Lists.newArrayList();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        TseqTPipCompParaDO rs = null;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObjectByList(sql.toString(), TseqTPipCompParaDO.class, parameters);
        } catch (Exception e) {
            log.error("获取组件属性表异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "获取组件属性表失败！");
        }
        return rs;
    }
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	@Override
	public int insert(TseqTPipCompParaDO obj){
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
			log.error("新增组件属性表交易异常", e);
            throw new BaseException(SysErr.E_MESSAGE, "新增组件属性表交易失败！");
		}
		return rs;
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	@Override
	public int update(TseqTPipCompParaDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
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
			log.error("修改组件属性表异常", e);
            throw new BaseException(SysErr.E_MESSAGE, "修改组件属性表失败！");
		}
		return rs;
	}
	
	/**
	 * 根据主键删除数据
	 * @param compNo
	 * @param keyTp
	 * @param keyNo
	 * @see public int delete(T entity)
	 * @return
	 */
	public int delete(String compNo, String keyTp, String keyNo ){
		TseqTPipCompParaDO obj = new TseqTPipCompParaDO();
	 	obj.setCompNo(compNo);
	 	obj.setKeyTp(keyTp);
	 	obj.setKeyNo(keyNo);
		return delete(obj);
	}
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param obj
	 * @return
	 */
	@Override
	public int delete(TseqTPipCompParaDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			List<Object> parameters = Lists.newArrayList();
			String sql = "DELETE FROM t_pip_comp_para "+getWhereSql(obj, parameters);
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
	public List<TseqTPipCompParaDO> list(TseqTPipCompParaDO obj) {
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
	public List<TseqTPipCompParaDO> list(TseqTPipCompParaDO obj, int start, int limit) {
		log.debug("TseqTPipCompParaDO=" + obj.toString());
		log.debug("start=" + start);
		log.debug("limit=" + limit);
		List<TseqTPipCompParaDO> list = null;
		try {
			List<Object> parameters = Lists.newArrayList();
			StringBuilder sql = new StringBuilder();
        	sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
			sql.append(" order by update_date desc");
        	IDBSession session = DBSessionFactory.getSession();
			if (limit <= 0) {
				list = session.getObjectListByList(sql.toString(), TseqTPipCompParaDO.class, parameters);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), TseqTPipCompParaDO.class, start, limit, parameters);
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
	public List<TseqTPipCompParaDO> list(int start, int limit, Object... param) {
		return null;
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(TseqTPipCompParaDO obj) {
		log.debug("TseqTPipCompParaDO=" + obj);
		List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        IDBSession session = DBSessionFactory.getSession();       
		int total = 0;
        try {
        	total = session.accountByList(countSql, parameters);
        } catch (Exception e) {
        	log.error("总记录数查询t_pip_comp_para异常", e);
        	throw new BaseException(SysErr.E_MESSAGE, "总记录数查询t_pip_comp_para失败！");
        }
        return total;
	}
	
	/**
	 * 根据数据对象产生对应的查询SQL
	 * @param obj 数据对象DO
	 * @return SQL
	 */
	public String getWhereSql(TseqTPipCompParaDO obj, List<Object> parameters) {
		if(null==parameters){
        	parameters = Lists.newArrayList();
        }
		StringBuffer sql = new StringBuffer(" where 1=1 ");
		if ( !DataUtil.isNullStr(obj.getCompNo())) {
		    sql.append(" AND comp_no = ? ");
		    parameters.add(obj.getCompNo());
		}
		if ( !DataUtil.isNullStr(obj.getKeyTp())) {
		    sql.append(" AND key_tp = ? ");
		    parameters.add(obj.getKeyTp());
		}
		if ( !DataUtil.isNullStr(obj.getKeyNo())) {
		    sql.append(" AND key_no = ? ");
		    parameters.add(obj.getKeyNo());
		}
		return sql.toString();
	}
	
}