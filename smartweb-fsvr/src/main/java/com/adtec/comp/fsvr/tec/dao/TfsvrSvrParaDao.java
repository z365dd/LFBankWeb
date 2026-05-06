/**
* 系统名称: SmartWeb平台
* 模块名称: comp.fsvr.tec持久化模块
* 功能描述: 文件服务器数据库操作
* 类 名 称  : TfsvrSvrParaDao.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200618<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.comp.fsvr.tec.dao;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adtec.comp.fsvr.tec.entity.TfsvrSvrDeponNetParaDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrSvrDeponParaDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrSvrParaInfoDO;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.common.dao.IBaseDao;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.common.util.DataUtil;
import com.google.common.collect.Lists;

/**
 * 文件服务器Dao接口
 * @author zhengjt
 * @version 20200618
 */
@Component
public class TfsvrSvrParaDao implements IBaseDao<TfsvrSvrParaInfoDO>{
	/**
	 * 日志对象
	 */
	protected final static Logger log = LoggerFactory.getLogger(TfsvrSvrParaDao.class);
	/*表名称*/
    public static final String TABLE_NAME = "t_tfsvr_svr_para";
    public static final String DEPEN_TABLE_NAME = "t_tfsvr_svr_depon_para";
    public static final String DEPEN_NET_TABLE_NAME = "t_tfsvr_svr_depon_net_para";
	
	@Autowired
	private TfsvrSvrDeponParaDao deponParaDao;
	/**
	 * 获取单条数据
	 * @param fileSvrId
	 * @return
	 */
	public TfsvrSvrParaInfoDO get(String fileSvrId ) {
		TfsvrSvrParaInfoDO obj = new TfsvrSvrParaInfoDO();
	 	obj.setFileSvrId(fileSvrId);
		return get(obj);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	@Override
    public TfsvrSvrParaInfoDO get(TfsvrSvrParaInfoDO obj) {
        StringBuilder sql = new StringBuilder();
        List<Object> parameters = Lists.newArrayList();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        TfsvrSvrParaInfoDO rs = null;
        IDBSession session = DBSessionFactory.getSession("fsvr");
        try {
            rs = session.getObjectByList(sql.toString(), TfsvrSvrParaInfoDO.class, parameters);
            rs.setDeponList(deponQry(obj.getFileSvrId()));
        } catch (Exception e) {
            log.error("获取文件服务器异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "获取文件服务器失败！");
        }
        return rs;
    }
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	@Override
	public int insert(TfsvrSvrParaInfoDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession("fsvr");
		//先检查数据是否存在，不存在则插入数据
        if(checkExist(session,obj)){
            throw new BaseException(SysErr.E_MESSAGE, "该文件服务器已配置！");
        }
		try {
			session.beginTransaction();
			List<String> ignoreFields = obj.getIgnoreFields();
			ignoreFields.add("shortRmrk");
			ignoreFields.add("midRmrk");
			ignoreFields.add("longRmrk");
			ignoreFields.add("dac");
			// 保存网络依赖关系参数信息
			if (obj.getList() != null) {
				insertDeponPara(obj);
			}
			rs = session.saveObject(TABLE_NAME, obj, ignoreFields);
			session.endTransaction();
		} catch (Exception e) {
			log.error("新增文件服务器交易异常", e);
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
            throw new BaseException(SysErr.E_MESSAGE, "新增文件服务器交易失败！");
		}
		return rs;
	}
	// 保存网络依赖关系参数信息
	public void insertDeponPara(TfsvrSvrParaInfoDO obj) {
		List<TfsvrSvrDeponParaDO> list = obj.getList();
		for (int i = 0; i < list.size(); i++){
			if (deponParaDao.insert(list.get(i)) == 0) {
				throw new BaseException(SysErr.E_MESSAGE, "保存网络依赖关系参数信息失败!");
			}
		}
		
	}
	
	 /**
     * 检查是否存在
     * @param session
     * @param obj
     * @return
     */
    public boolean checkExist(IDBSession session,TfsvrSvrParaInfoDO obj) {
        int total = 0;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select count(1) from ").append(TABLE_NAME).append(" where file_svr_id=?");
            log.debug("sql=" + sql.toString());
            total = session.account(sql.toString(),obj.getFileSvrId());
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
	public int update(TfsvrSvrParaInfoDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession("fsvr");
		try {
			session.beginTransaction();
			List<String> ignoreFields = obj.getIgnoreFields();
			ignoreFields.add("shortRmrk");
			ignoreFields.add("midRmrk");
			ignoreFields.add("longRmrk");
			ignoreFields.add("dac");
		
			// 更新网络依赖关系参数信息
			deponParaDao.delete(obj.getFileSvrId());
			if (obj.getList() != null) {
				insertDeponPara(obj);
			}
			List<String> matchFields = obj.getMatchFields();
			rs = session.updateObject(TABLE_NAME, obj, matchFields, ignoreFields);
			session.endTransaction();
		} catch (Exception e) {
			log.error("修改文件服务器异常", e);
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
            throw new BaseException(SysErr.E_MESSAGE, "修改文件服务器失败！");
		}
		return rs;
	}
	
	/**
	 * 根据主键删除数据
	 * @param fileSvrId
	 * @see public int delete(T entity)
	 * @return
	 */
	public int delete(String fileSvrId ){
		TfsvrSvrParaInfoDO obj = new TfsvrSvrParaInfoDO();
	 	obj.setFileSvrId(fileSvrId);
		return delete(obj);
	}
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param obj
	 * @return
	 */
	@Override
	public int delete(TfsvrSvrParaInfoDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession("fsvr");
		try {
			session.beginTransaction();
			List<Object> parameters = Lists.newArrayList();
			String sql = "DELETE FROM t_tfsvr_svr_para "+getWhereSql(obj, parameters);
			// 删除网络依赖关系参数信息
			deponParaDao.delete(obj.getFileSvrId());
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
	public int statChange(TfsvrSvrParaInfoDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession("fsvr");
		try {
			session.beginTransaction();
			List<Object> parameters = Lists.newArrayList();
			String sql = "UPDATE t_tfsvr_svr_para set file_svr_stat = ? where file_svr_id = ? ";
			parameters.add(obj.getFileSvrStat());
			parameters.add(obj.getFileSvrId());
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
	 * 依赖网络查询
	 * @param obj
	 * @return
	 */
	public List<TfsvrSvrDeponNetParaDO> deponQry(String fileSvrId){
		List<TfsvrSvrDeponNetParaDO> list = null;
		IDBSession session = DBSessionFactory.getSession("fsvr");
		try {
			session.beginTransaction();
			List<Object> parameters = Lists.newArrayList();
			StringBuilder sql = new StringBuilder();
	        sql.append("select distinct net_region from ").append(DEPEN_NET_TABLE_NAME).append(" where file_svr_id in ");
	        sql.append("( select depon_file_svr_id from ").append(DEPEN_TABLE_NAME).append(" where file_svr_id =? )");
			parameters.add(fileSvrId);
			list = session.getObjectListByList(sql.toString(),TfsvrSvrDeponNetParaDO.class, parameters);
			session.endTransaction();
		} catch (Exception e) {
			log.error("依赖网络查询异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "依赖网络查询失败！");
		}
		return list;
	}
	
	/**
	 * 数据库多笔查询，不分页
	 * @param obj 数据对象DO
	 * @return List返回集合
	 */
	@Override
	public List<TfsvrSvrParaInfoDO> list(TfsvrSvrParaInfoDO obj) {
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
	public List<TfsvrSvrParaInfoDO> list(TfsvrSvrParaInfoDO obj, int start, int limit) {
		log.debug("TfsvrSvrParaInfoDO=" + obj.toString());
		log.debug("start=" + start);
		log.debug("limit=" + limit);
		List<TfsvrSvrParaInfoDO> list = null;
		try {
			List<Object> parameters = Lists.newArrayList();
			StringBuilder sql = new StringBuilder();
        	sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
			sql.append(" AND file_svr_tp != 'A1'");
        	sql.append(" order by file_svr_id desc");
        	IDBSession session = DBSessionFactory.getSession("fsvr");
			if (limit <= 0) {
				list = session.getObjectListByList(sql.toString(), TfsvrSvrParaInfoDO.class, parameters);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), TfsvrSvrParaInfoDO.class, start, limit, parameters);
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
	public List<TfsvrSvrParaInfoDO> list(int start, int limit, Object... param) {
		return null;
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(TfsvrSvrParaInfoDO obj) {
		log.debug("TfsvrSvrParaInfoDO=" + obj);
		List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
        sql.append(" AND file_svr_tp != 'A1'");
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        IDBSession session = DBSessionFactory.getSession("fsvr");       
		int total = 0;
        try {
        	total = session.accountByList(countSql, parameters);
        } catch (Exception e) {
        	log.error("总记录数查询t_tfsvr_svr_para异常", e);
        	throw new BaseException(SysErr.E_MESSAGE, "总记录数查询t_tfsvr_svr_para失败！");
        }
        return total;
	}
	
	/**
	 * 根据数据对象产生对应的查询SQL
	 * @param obj 数据对象DO
	 * @return SQL
	 */
	public String getWhereSql(TfsvrSvrParaInfoDO obj, List<Object> parameters) {
		if(null==parameters){
        	parameters = Lists.newArrayList();
        }
		StringBuffer sql = new StringBuffer(" where 1=1 ");
		if ( !DataUtil.isNullStr(obj.getFileSvrId())) {
		    sql.append(" AND file_svr_id = ? ");
		    parameters.add(obj.getFileSvrId());
		}
		if ( !DataUtil.isNullStr(obj.getIp())) {
		    sql.append(" AND ip = ? ");
		    parameters.add(obj.getIp());
		}
		if ( !DataUtil.isNullStr(obj.getFileSvrStat())) {
		    sql.append(" AND file_svr_stat = ? ");
		    parameters.add(obj.getFileSvrStat());
		}
		return sql.toString();
	}
	
	/**
	 * 根据数据对象产生对应的查询SQL--列表查询
	 * @param obj 数据对象DO
	 * @return SQL
	 */
	public String getWhereSqlList(TfsvrSvrParaInfoDO obj, List<Object> parameters) {
		if(null==parameters){
        	parameters = Lists.newArrayList();
        }
		StringBuffer sql = new StringBuffer(" where 1=1 ");
		if ( !DataUtil.isNullStr(obj.getFileSvrId())) {
		    sql.append(" AND file_svr_id LIKE ? ");
			parameters.add("%"+obj.getFileSvrId()+"%");
		}
		if ( !DataUtil.isNullStr(obj.getIp())) {
		    sql.append(" AND ip LIKE ? ");
		    parameters.add("%"+obj.getIp()+"%");
		}
		if ( !DataUtil.isNullStr(obj.getFileSvrStat())) {
		    sql.append(" AND file_svr_stat = ? ");
		    parameters.add(obj.getFileSvrStat());
		}
		return sql.toString();
	}
}