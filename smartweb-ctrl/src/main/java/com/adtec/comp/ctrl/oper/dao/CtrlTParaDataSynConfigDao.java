/**
* 系统名称: SmartWeb平台
* 模块名称: comp.ctrl.oper持久化模块
* 功能描述: 数据同步数据库操作
* 类 名 称  : CtrlTParaDataSynConfigDao.java
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
import com.adtec.comp.ctrl.oper.entity.CtrlTParaDataSynConfigDO;

/**
 * 数据同步Dao接口
 * @author zhengjt
 * @version 20200512
 */
@Component
public class CtrlTParaDataSynConfigDao implements IBaseDao<CtrlTParaDataSynConfigDO>{
	/**
	 * 日志对象
	 */
	protected final static Logger log = LoggerFactory.getLogger(CtrlTParaDataSynConfigDao.class);
	/*表名称*/
    public static final String TABLE_NAME = "t_para_data_syn_config";
    
	/**
	 * 获取单条数据
	 * @param stepNo
	 * @return
	 */
	public CtrlTParaDataSynConfigDO get(String stepNo ) {
		CtrlTParaDataSynConfigDO obj = new CtrlTParaDataSynConfigDO();
	 	obj.setStepNo(stepNo);
		return get(obj);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	@Override
    public CtrlTParaDataSynConfigDO get(CtrlTParaDataSynConfigDO obj) {
        StringBuilder sql = new StringBuilder();
        List<Object> parameters = Lists.newArrayList();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        CtrlTParaDataSynConfigDO rs = null;
        IDBSession session = DBSessionFactory.getSession("fctrl");
        try {
            rs = session.getObjectByList(sql.toString(), CtrlTParaDataSynConfigDO.class, parameters);
        } catch (Exception e) {
            log.error("获取数据同步异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "获取数据同步失败！");
        }
        return rs;
    }
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	@Override
	public int insert(CtrlTParaDataSynConfigDO obj){
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
			log.error("新增数据同步交易异常", e);
            throw new BaseException(SysErr.E_MESSAGE, "新增数据同步交易失败！");
		}
		return rs;
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	@Override
	public int update(CtrlTParaDataSynConfigDO obj){
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
			log.error("修改数据同步异常", e);
            throw new BaseException(SysErr.E_MESSAGE, "修改数据同步失败！");
		}
		return rs;
	}
	
	/**
	 * 根据主键删除数据
	 * @param stepNo
	 * @see public int delete(T entity)
	 * @return
	 */
	public int delete(String stepNo ){
		CtrlTParaDataSynConfigDO obj = new CtrlTParaDataSynConfigDO();
	 	obj.setStepNo(stepNo);
		return delete(obj);
	}
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param obj
	 * @return
	 */
	@Override
	public int delete(CtrlTParaDataSynConfigDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession("fctrl");
		try {
			List<Object> parameters = Lists.newArrayList();
			String sql = "DELETE FROM t_para_data_syn_config "+getWhereSql(obj, parameters);
			rs = session.executeByList(sql, parameters);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("删除交易异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "删除交易失败！");
		}
		return rs;
	}
	
	/**
	 * 获取步骤号
	 * @param obj
	 * @return
	 */
    public String getStepNo( ) {
        List<Object> parameters = Lists.newArrayList();
        String sql = "select * from  (SELECT * from t_para_data_syn_config order by step_no desc) where rownum=1";
        CtrlTParaDataSynConfigDO rs = null;
        IDBSession session = DBSessionFactory.getSession("fctrl");
        try {
            rs = session.getObjectByList(sql, CtrlTParaDataSynConfigDO.class, parameters);
        } catch (Exception e) {
            log.error("获取数据同步异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "获取数据同步失败！");
        }
        
        return rs.getStepNo();
    }
	/**
	 * 数据库多笔查询，不分页
	 * @param obj 数据对象DO
	 * @return List返回集合
	 */
	@Override
	public List<CtrlTParaDataSynConfigDO> list(CtrlTParaDataSynConfigDO obj) {
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
	public List<CtrlTParaDataSynConfigDO> list(CtrlTParaDataSynConfigDO obj, int start, int limit) {
		log.debug("CtrlTParaDataSynConfigDO=" + obj.toString());
		log.debug("start=" + start);
		log.debug("limit=" + limit);
		List<CtrlTParaDataSynConfigDO> list = null;
		try {
			List<Object> parameters = Lists.newArrayList();
			StringBuilder sql = new StringBuilder();
        	sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
			sql.append(" order by step_no desc");
        	IDBSession session = DBSessionFactory.getSession("fctrl");
			if (limit <= 0) {
				list = session.getObjectListByList(sql.toString(), CtrlTParaDataSynConfigDO.class, parameters);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), CtrlTParaDataSynConfigDO.class, start, limit, parameters);
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
	public List<CtrlTParaDataSynConfigDO> list(int start, int limit, Object... param) {
		return null;
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(CtrlTParaDataSynConfigDO obj) {
		log.debug("CtrlTParaDataSynConfigDO=" + obj);
		List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        IDBSession session = DBSessionFactory.getSession("fctrl");       
		int total = 0;
        try {
        	total = session.accountByList(countSql, parameters);
        } catch (Exception e) {
        	log.error("总记录数查询t_para_data_syn_config异常", e);
        	throw new BaseException(SysErr.E_MESSAGE, "总记录数查询t_para_data_syn_config失败！");
        }
        return total;
	}
	
	/**
	 * 根据数据对象产生对应的查询SQL
	 * @param obj 数据对象DO
	 * @return SQL
	 */
	public String getWhereSql(CtrlTParaDataSynConfigDO obj, List<Object> parameters) {
		if(null==parameters){
        	parameters = Lists.newArrayList();
        }
		StringBuffer sql = new StringBuffer(" where 1=1 ");
		if ( !DataUtil.isNullStr(obj.getSrcTp())) {
		    sql.append(" AND src_tp = ? ");
		    parameters.add(obj.getSrcTp());
		}
		if ( !DataUtil.isNullStr(obj.getSrcTabName())) {
			sql.append(" AND src_tab_name LIKE ? ");
			parameters.add("%"+obj.getSrcTabName()+"%");
		}
		if ( !DataUtil.isNullStr(obj.getStepNo())) {
		    sql.append(" AND step_no = ? ");
		    parameters.add(obj.getStepNo());
		}
		return sql.toString();
	}
	
}