/**
* 系统名称: SmartWeb平台
* 模块名称: comp.fsvr.tec持久化模块
* 功能描述: 组件文件传输端口数据库操作
* 类 名 称  : TfsvrSvrPortParaDao.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200622<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.comp.fsvr.tec.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adtec.comp.fsvr.tec.entity.TfsvrSvrDeponNetParaDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrSvrPortParaDO;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.common.dao.IBaseDao;
import com.google.common.collect.Lists;

/**
 * 组件文件传输端口Dao接口
 * @author zhengjt
 * @version 20200622
 */
@Component
public class TfsvrSvrPortParaDao implements IBaseDao<TfsvrSvrPortParaDO>{
	/**
	 * 日志对象
	 */
	protected final static Logger log = LoggerFactory.getLogger(TfsvrSvrPortParaDao.class);
	/*表名称*/
    public static final String TABLE_NAME = "t_tfsvr_svr_port_para";
    @Autowired
	private  TfsvrSvrDeponNetParaDao deponNetParaDao;
	/**
	 * 获取单条数据
	 * @param compNo
	 * @return
	 */
	public TfsvrSvrPortParaDO get(String compNo ) {
		TfsvrSvrPortParaDO obj = new TfsvrSvrPortParaDO();
	 	obj.setCompNo(compNo);
	 	TfsvrSvrPortParaDO tfsvrSvrPortParaDO = get(obj);
	 	TfsvrSvrDeponNetParaDO tfsvrSvrDeponNetParaDO = new TfsvrSvrDeponNetParaDO();
	 	tfsvrSvrDeponNetParaDO.setCompNo(compNo);
	 	tfsvrSvrDeponNetParaDO.setFileResTp("2");
	 	List<TfsvrSvrDeponNetParaDO> list = deponNetParaDao.list(tfsvrSvrDeponNetParaDO);
	 	if ( list != null ) {
	 		StringBuilder str = new StringBuilder();
	 		for (int i=0; i<list.size(); i++) {
	 		    str.append(list.get(i).getNetRegion()+";");
	 		}
	 		
	 		tfsvrSvrPortParaDO.setList(list);
	 	}
	 	
		return tfsvrSvrPortParaDO;
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	@Override
    public TfsvrSvrPortParaDO get(TfsvrSvrPortParaDO obj) {
        StringBuilder sql = new StringBuilder();
        List<Object> parameters = Lists.newArrayList();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        TfsvrSvrPortParaDO rs = null;
        IDBSession session = DBSessionFactory.getSession("fsvr");
        try {
            rs = session.getObjectByList(sql.toString(), TfsvrSvrPortParaDO.class, parameters);
        } catch (Exception e) {
            log.error("获取组件文件传输端口异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "获取组件文件传输端口失败！");
        }
        return rs;
    }
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	@Override
	public int insert(TfsvrSvrPortParaDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession("fsvr");
		if(checkExist(session,obj)){
            throw new BaseException(SysErr.E_MESSAGE, "该端口信息已配置！");
        }
		try {
			session.beginTransaction();
			List<String> ignoreFields = obj.getIgnoreFields();
			ignoreFields.add("shortRmrk");
			ignoreFields.add("midRmrk");
			ignoreFields.add("longRmrk");
			ignoreFields.add("dac");
			if ( obj.getList() != null && obj.getList().size() > 0 ) {
				List<TfsvrSvrDeponNetParaDO> list = obj.getList();
				for (int i = 0; i < list.size(); i++){
					if (deponNetParaDao.insert(list.get(i)) == 0) {
						throw new BaseException(SysErr.E_MESSAGE, "保存网络区域关系参数信息失败!");
					}
				}
			}
			rs = session.saveObject(TABLE_NAME, obj, ignoreFields);
			session.endTransaction();
		} catch (Exception e) {
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			log.error("新增组件文件传输端口交易异常", e);
            throw new BaseException(SysErr.E_MESSAGE, "新增组件文件传输端口交易失败！");
		}
		return rs;
	}
	
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	@Override
	public int update(TfsvrSvrPortParaDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession("fsvr");
		try {
			session.beginTransaction();
			List<String> ignoreFields = obj.getIgnoreFields();
			ignoreFields.add("shortRmrk");
			ignoreFields.add("midRmrk");
			ignoreFields.add("longRmrk");
			ignoreFields.add("dac");
			if ( obj.getList() != null && obj.getList().size() > 0 ) {
				List<TfsvrSvrDeponNetParaDO> list = obj.getList();
				TfsvrSvrDeponNetParaDO tfsvrSvrDeponNetParaDO = new TfsvrSvrDeponNetParaDO();
				tfsvrSvrDeponNetParaDO.setCompNo(obj.getCompNo());
				tfsvrSvrDeponNetParaDO.setFileResTp("2");
				deponNetParaDao.delete(tfsvrSvrDeponNetParaDO);
				for (int i = 0; i < list.size(); i++){
					if (deponNetParaDao.insert(list.get(i)) == 0) {
						throw new BaseException(SysErr.E_MESSAGE, "保存网络区域关系参数信息失败!");
					}
				}
			}
			
			List<String> matchFields = obj.getMatchFields();
			rs = session.updateObject(TABLE_NAME, obj, matchFields, ignoreFields);
			session.endTransaction();
		} catch (Exception e) {
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			log.error("修改组件文件传输端口异常", e);
            throw new BaseException(SysErr.E_MESSAGE, "修改组件文件传输端口失败！");
		}
		return rs;
	}
	
	 /**
     * 检查是否存在
     * @param session
     * @param obj
     * @return
     */
    public boolean checkExist(IDBSession session,TfsvrSvrPortParaDO obj) {
        int total = 0;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select count(1) from ").append(TABLE_NAME).append(" where comp_no=?");
            log.debug("sql=" + sql.toString());
            total = session.account(sql.toString(),obj.getCompNo());
        } catch (Exception e) {
            log.error("条数查询异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "条数查询失败！");
        }
        return total>0?true:false;
    }
    
	/**
	 * 根据主键删除数据
	 * @param compNo
	 * @see public int delete(T entity)
	 * @return
	 */
	public int delete(String compNo ){
		TfsvrSvrPortParaDO obj = new TfsvrSvrPortParaDO();
	 	obj.setCompNo(compNo);
		return delete(obj);
	}
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param obj
	 * @return
	 */
	@Override
	public int delete(TfsvrSvrPortParaDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession("fsvr");
		try {
			List<Object> parameters = Lists.newArrayList();
			String sql = "DELETE FROM t_tfsvr_svr_port_para "+getWhereSql(obj, parameters);
			rs = session.executeByList(sql, parameters);
		} catch (Exception e) {
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
	public List<TfsvrSvrPortParaDO> list(TfsvrSvrPortParaDO obj) {
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
	public List<TfsvrSvrPortParaDO> list(TfsvrSvrPortParaDO obj, int start, int limit) {
		log.debug("TfsvrSvrPortParaDO=" + obj.toString());
		log.debug("start=" + start);
		log.debug("limit=" + limit);
		List<TfsvrSvrPortParaDO> list = null;
		try {
			List<Object> parameters = Lists.newArrayList();
			StringBuilder sql = new StringBuilder();
        	sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
			sql.append(" order by comp_no ");
        	IDBSession session = DBSessionFactory.getSession("fsvr");
			if (limit <= 0) {
				list = session.getObjectListByList(sql.toString(), TfsvrSvrPortParaDO.class, parameters);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), TfsvrSvrPortParaDO.class, start, limit, parameters);
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
	public List<TfsvrSvrPortParaDO> list(int start, int limit, Object... param) {
		return null;
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(TfsvrSvrPortParaDO obj) {
		log.debug("TfsvrSvrPortParaDO=" + obj);
		List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        IDBSession session = DBSessionFactory.getSession("fsvr");       
		int total = 0;
        try {
        	total = session.accountByList(countSql, parameters);
        } catch (Exception e) {
        	log.error("总记录数查询t_tfsvr_svr_port_para异常", e);
        	throw new BaseException(SysErr.E_MESSAGE, "总记录数查询t_tfsvr_svr_port_para失败！");
        }
        return total;
	}
	
	/**
	 * 根据数据对象产生对应的查询SQL
	 * @param obj 数据对象DO
	 * @return SQL
	 */
	public String getWhereSql(TfsvrSvrPortParaDO obj, List<Object> parameters) {
		if(null==parameters){
        	parameters = Lists.newArrayList();
        }
		StringBuffer sql = new StringBuffer(" where 1=1 ");
		if ( !DataUtil.isNullStr(obj.getCompNo())) {
		    sql.append(" AND comp_no = ? ");
		    parameters.add(obj.getCompNo());
		}
		if ( !DataUtil.isNullStr(obj.getPort())) {
		    sql.append(" AND port LIKE ? ");
			parameters.add("%"+obj.getPort()+"%");
		}
		return sql.toString();
	}
	
}