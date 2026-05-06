/**
* 系统名称: SmartWeb平台
* 模块名称: comp.fsvr.tec持久化模块
* 功能描述: 调用方信息数据库操作
* 类 名 称  : TfsvrPtcptParaDao.java
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

import java.lang.Exception;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.common.dao.IBaseDao;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.common.util.DataUtil;
import com.google.common.collect.Lists;
import com.adtec.comp.fsvr.tec.entity.TfsvrPtcptParaDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrSvrDeponNetParaDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrSvrParaInfoDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrSvrPortParaDO;

/**
 * 调用方信息Dao接口
 * @author zhengjt
 * @version 20200622
 */
@Component
public class TfsvrPtcptParaDao implements IBaseDao<TfsvrPtcptParaDO>{
	/**
	 * 日志对象
	 */
	protected final static Logger log = LoggerFactory.getLogger(TfsvrPtcptParaDao.class);
	/*表名称*/
    public static final String TABLE_NAME = "t_tfsvr_ptcpt_para";
	
    @Autowired
 	private  TfsvrSvrDeponNetParaDao deponNetParaDao;
    @Autowired
   	private  TfsvrSvrPortParaDao svrPortParaDao;
	/**
	 * 获取单条数据
	 * @param callerId
	 * @return
	 */
	public TfsvrPtcptParaDO get(String callerId ) {
		TfsvrPtcptParaDO obj = new TfsvrPtcptParaDO();
	 	obj.setCallerId(callerId);
		return get(obj);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	@Override
    public TfsvrPtcptParaDO get(TfsvrPtcptParaDO obj) {
        StringBuilder sql = new StringBuilder();
        List<Object> parameters = Lists.newArrayList();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        TfsvrPtcptParaDO rs = null;
        IDBSession session = DBSessionFactory.getSession("fsvr");
        try {
            rs = session.getObjectByList(sql.toString(), TfsvrPtcptParaDO.class, parameters);
            rs.setDeponList(deponNetParaDao.list(obj.getCallerId()));
        } catch (Exception e) {
            log.error("获取调用方信息异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "获取调用方信息失败！");
        }
        return rs;
    }
	
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	@Override
	public int insert(TfsvrPtcptParaDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession("fsvr");
		if(checkExist(session,obj)){
            throw new BaseException(SysErr.E_MESSAGE, "该调用方信息已配置！");
        }
		try {
			session.beginTransaction();
			List<String> ignoreFields = obj.getIgnoreFields();
			ignoreFields.add("shortRmrk");
			ignoreFields.add("midRmrk");
			ignoreFields.add("longRmrk");
			ignoreFields.add("dac");
			// 保存网络依赖关系参数信息
			if (obj.getDeponList() != null) {
				insertDeponPara(obj);
			}
			if ("02".equals(obj.getCallMeth())) {
				TfsvrSvrPortParaDO portDo = new TfsvrSvrPortParaDO();
				portDo.setCompName(obj.getCallerDesc());
				portDo.setCompNo(obj.getCallerId());
				portDo.setPort(obj.getPort());
				svrPortParaDao.insert(portDo);
			}
			rs = session.saveObject(TABLE_NAME, obj, ignoreFields);
			session.endTransaction();
		} catch (Exception e) {
			log.error("新增调用方信息交易异常", e);
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
            throw new BaseException(SysErr.E_MESSAGE, "新增调用方信息交易失败！");
		}
		return rs;
	}
	
	// 保存网络区域关系参数信息
	public void insertDeponPara(TfsvrPtcptParaDO obj) {
		List<TfsvrSvrDeponNetParaDO> list = obj.getDeponList();
		for (int i = 0; i < list.size(); i++){
			if (deponNetParaDao.insert(list.get(i)) == 0) {
				throw new BaseException(SysErr.E_MESSAGE, "保存网络区域关系参数信息失败!");
			}
		}
	}
		
	 /**
     * 检查是否存在
     * @param session
     * @param obj
     * @return
     */
    public boolean checkExist(IDBSession session,TfsvrPtcptParaDO obj) {
        int total = 0;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select count(1) from ").append(TABLE_NAME).append(" where caller_id=?");
            log.debug("sql=" + sql.toString());
            total = session.account(sql.toString(),obj.getCallerId());
        } catch (Exception e) {
            log.error("条数查询异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "条数查询失败！");
        }
        return total>0?true:false;
    }
    
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	@Override
	public int update(TfsvrPtcptParaDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession("fsvr");
		try {
			session.beginTransaction();
			List<String> ignoreFields = obj.getIgnoreFields();
			ignoreFields.add("shortRmrk");
			ignoreFields.add("midRmrk");
			ignoreFields.add("longRmrk");
			ignoreFields.add("dac");
			TfsvrSvrDeponNetParaDO tempDo = new TfsvrSvrDeponNetParaDO(); 
			tempDo.setCompNo(obj.getCallerId());
			tempDo.setFileResTp("2");
			deponNetParaDao.delete(tempDo);
			// 保存网络依赖关系参数信息
			if (obj.getDeponList() != null) {
				insertDeponPara(obj);
			}
			if ("02".equals(obj.getCallMeth())) {
				TfsvrSvrPortParaDO portDo = new TfsvrSvrPortParaDO();
				portDo.setCompName(obj.getCallerDesc());
				portDo.setCompNo(obj.getCallerId());
				portDo.setPort(obj.getPort());
				svrPortParaDao.update(portDo);
			}
			List<String> matchFields = obj.getMatchFields();
			rs = session.updateObject(TABLE_NAME, obj, matchFields, ignoreFields);
			session.endTransaction();
		} catch (Exception e) {
			log.error("修改调用方信息异常", e);
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
            throw new BaseException(SysErr.E_MESSAGE, "修改调用方信息失败！");
		}
		return rs;
	}
	
	/**
	 * 根据主键删除数据
	 * @param callerId
	 * @see public int delete(T entity)
	 * @return
	 */
	public int delete(String callerId ){
		TfsvrPtcptParaDO obj = new TfsvrPtcptParaDO();
	 	obj.setCallerId(callerId);
		return delete(obj);
	}
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param obj
	 * @return
	 */
	@Override
	public int delete(TfsvrPtcptParaDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession("fsvr");
		try {
			session.beginTransaction();
			List<Object> parameters = Lists.newArrayList();
			TfsvrSvrDeponNetParaDO tempDo = new TfsvrSvrDeponNetParaDO(); 
			tempDo.setCompNo(obj.getCallerId());
			tempDo.setFileResTp("2");
			deponNetParaDao.delete(tempDo);
			TfsvrSvrPortParaDO portDo = new TfsvrSvrPortParaDO();
			portDo.setCompNo(obj.getCallerId());
			svrPortParaDao.delete(portDo);
			String sql = "DELETE FROM t_tfsvr_ptcpt_para "+getWhereSql(obj, parameters);
			rs = session.executeByList(sql, parameters);
			session.endTransaction();
		} catch (Exception e) {
			log.error("删除交易异常", e);
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_MESSAGE, "删除交易失败！");
		}
		return rs;
	}
	
	/**
	 * 开通状态修改
	 * @param obj
	 * @return
	 */
	public int statChange(TfsvrPtcptParaDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession("fsvr");
		try {
			session.beginTransaction();
			List<Object> parameters = Lists.newArrayList();
			String sql = "UPDATE t_tfsvr_ptcpt_para set stat = ? where caller_id = ? ";
			parameters.add(obj.getStat());
			parameters.add(obj.getCallerId());
			rs = session.executeByList(sql, parameters);
			session.endTransaction();
		} catch (Exception e) {
			log.error("状态修改异常", e);
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_MESSAGE, "状态修改失败！");
		}
		return rs;
	}
	
	/**
	 * 数据库多笔查询，不分页
	 * @param obj 数据对象DO
	 * @return List返回集合
	 */
	@Override
	public List<TfsvrPtcptParaDO> list(TfsvrPtcptParaDO obj) {
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
	public List<TfsvrPtcptParaDO> list(TfsvrPtcptParaDO obj, int start, int limit) {
		log.debug("TfsvrPtcptParaDO=" + obj.toString());
		log.debug("start=" + start);
		log.debug("limit=" + limit);
		List<TfsvrPtcptParaDO> list = null;
		try {
			List<Object> parameters = Lists.newArrayList();
			StringBuilder sql = new StringBuilder();
        	sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
			//sql.append(" order by update_date desc");
        	IDBSession session = DBSessionFactory.getSession("fsvr");
			if (limit <= 0) {
				list = session.getObjectListByList(sql.toString(), TfsvrPtcptParaDO.class, parameters);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), TfsvrPtcptParaDO.class, start, limit, parameters);
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
	public List<TfsvrPtcptParaDO> list(int start, int limit, Object... param) {
		return null;
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(TfsvrPtcptParaDO obj) {
		log.debug("TfsvrPtcptParaDO=" + obj);
		List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        IDBSession session = DBSessionFactory.getSession("fsvr");       
		int total = 0;
        try {
        	total = session.accountByList(countSql, parameters);
        } catch (Exception e) {
        	log.error("总记录数查询t_tfsvr_ptcpt_para异常", e);
        	throw new BaseException(SysErr.E_MESSAGE, "总记录数查询t_tfsvr_ptcpt_para失败！");
        }
        return total;
	}
	
	/**
	 * 根据数据对象产生对应的查询SQL
	 * @param obj 数据对象DO
	 * @return SQL
	 */
	public String getWhereSql(TfsvrPtcptParaDO obj, List<Object> parameters) {
		if(null==parameters){
        	parameters = Lists.newArrayList();
        }
		StringBuffer sql = new StringBuffer(" where 1=1 ");
		if ( !DataUtil.isNullStr(obj.getCallerId())) {
			sql.append(" AND caller_id LIKE ? ");
			parameters.add("%"+obj.getCallerId()+"%");
		}
		if ( !DataUtil.isNullStr(obj.getStat())) {
		    sql.append(" AND stat = ? ");
		    parameters.add(obj.getStat());
		}
		return sql.toString();
	}
	
}