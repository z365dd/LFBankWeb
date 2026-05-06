/**
* 系统名称: SmartWeb平台
* 模块名称: comp.fsvr.tec持久化模块
* 功能描述: 网络信息数据库操作
* 类 名 称  : TfsvrNetInfoMngDao.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200624<br>
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

import com.adtec.comp.fsvr.tec.entity.TfsvrInSvrParaDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrNetInfoMngDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrSvrDeponNetParaDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrSvrDeponParaDO;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.common.dao.IBaseDao;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.common.util.DataUtil;
import com.google.common.collect.Lists;

/**
 * 网络信息Dao接口
 * 
 * @author zhengjt
 * @version 20200624
 */
@Component
public class TfsvrNetInfoMngDao implements IBaseDao<TfsvrNetInfoMngDO> {
	/**
	 * 日志对象
	 */
	protected final static Logger log = LoggerFactory.getLogger(TfsvrNetInfoMngDao.class);
	/* 表名称 */
	public static final String TABLE_NAME = "t_tfsvr_svr_depon_net_para";
	public static final String DEPEN_TABLE_NAME = "t_tfsvr_svr_depon_para";
	public static final String SVR_TABLE_NAME = "t_tfsvr_svr_para";

	@Autowired
	private TfsvrInSvrParaDao tfsvrInSvrParaDao;
	@Autowired
	private TfsvrSvrDeponParaDao tfsvrSvrDeponParaDao;
	@Autowired
	private TfsvrSvrDeponNetParaDao tfsvrSvrDeponNetParaDao;

	/**
	 * 获取单条数据
	 * 
	 * @param netRegion
	 * @return
	 */
	public TfsvrNetInfoMngDO get(String netRegion) {
		TfsvrNetInfoMngDO obj = new TfsvrNetInfoMngDO();
		obj.setNetRegion(netRegion);
		return get(obj);
	}

	/**
	 * 获取单条数据
	 * 
	 * @param obj
	 * @return
	 */
	@Override
	public TfsvrNetInfoMngDO get(TfsvrNetInfoMngDO obj) {
		StringBuilder sql = new StringBuilder();
		List<Object> parameters = Lists.newArrayList();
		TfsvrNetInfoMngDO rs = new TfsvrNetInfoMngDO();
		IDBSession session = DBSessionFactory.getSession("fsvr");
		try {
			sql.append("select * from ").append(SVR_TABLE_NAME).append(" where file_svr_id in ( ");
			sql.append("select file_svr_id from ").append(TABLE_NAME).append(getWhereSql(obj, parameters))
					.append(" ) ");
			rs.setNetRegion(obj.getNetRegion());
			rs.setSvrList(session.getObjectListByList(sql.toString(), TfsvrInSvrParaDO.class, parameters));
			List<TfsvrSvrDeponNetParaDO> list = Lists.newArrayList();
			list = deponQry(rs.getSvrList().get(0).getFileSvrId());
			if (list != null && !list.isEmpty()) {
				String deponNetRegion = list.get(0).getNetRegion();
				for (int i = 0; i < list.size(); i++) {
					deponNetRegion = deponNetRegion + ";" + list.get(i).getNetRegion();
				}
				rs.setDeponNetRegion(deponNetRegion);
			}

		} catch (Exception e) {
			log.error("获取网络信息异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "获取网络信息失败！");
		}
		return rs;
	}

	/**
	 * 插入数据
	 * 
	 * @param obj
	 * @return
	 */
	@Override
	public int insert(TfsvrNetInfoMngDO obj) {
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession("fsvr");
		// 先检查数据是否存在，不存在则插入数据
		if (checkExist(session, obj)) {
			throw new BaseException(SysErr.E_MESSAGE, "该网络区域已存在！");
		}
		try {
			session.beginTransaction();
			// 保存内部服务器信息
			List<TfsvrInSvrParaDO> svrList = obj.getSvrList();
			for (int i = 0; i < svrList.size(); i++) {
				TfsvrSvrDeponNetParaDO deponNetDo = new TfsvrSvrDeponNetParaDO();
				deponNetDo.setNetRegion(obj.getNetRegion());
				deponNetDo.setFileResTp("1");
				deponNetDo.setFileSvrId(svrList.get(i).getFileSvrId());
				tfsvrSvrDeponNetParaDao.insert(deponNetDo);
				tfsvrInSvrParaDao.insert(svrList.get(i));
			}
			// 保存网络依赖关系信息
			List<TfsvrSvrDeponParaDO> deponList = obj.getDeponList();
			if (deponList != null && !deponList.isEmpty()){
				for (int i = 0; i < deponList.size(); i++) {
					tfsvrSvrDeponParaDao.insert(deponList.get(i));
				}
			}
			session.endTransaction();
			rs = 1;
		} catch (Exception e) {
			log.error("新增网络信息交易异常", e);
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_MESSAGE, "新增网络信息交易失败！");
		}
		return rs;
	}

	/**
	 * 检查是否存在
	 * 
	 * @param session
	 * @param obj
	 * @return
	 */
	public boolean checkExist(IDBSession session, TfsvrNetInfoMngDO obj) {
		int total = 0;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select count(1) from ").append(TABLE_NAME).append(" where net_region=?");
			log.debug("sql=" + sql.toString());
			total = session.account(sql.toString(), obj.getNetRegion());
		} catch (Exception e) {
			log.error("条数查询异常：" + e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "条数查询失败！");
		}
		return total > 0 ? true : false;
	}

	/**
	 * 更新数据
	 * 
	 * @param obj
	 * @return
	 */
	@Override
	public int update(TfsvrNetInfoMngDO obj) {
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession("fsvr");
		try {
			session.beginTransaction();
			//删除原有的数据
			List<Object> parameters = Lists.newArrayList();
			StringBuilder sql = new StringBuilder();
			sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
			List<TfsvrSvrDeponNetParaDO> list = null;
			list = session.getObjectListByList(sql.toString(), TfsvrSvrDeponNetParaDO.class, parameters);
			if (list != null && !list.isEmpty()) {
				for (TfsvrSvrDeponNetParaDO tempDO : list) {
					tfsvrInSvrParaDao.delete(tempDO.getFileSvrId());
					tfsvrSvrDeponParaDao.delete(tempDO.getFileSvrId());
				}
			}
			parameters.clear();
			String sqlStr = "DELETE FROM t_tfsvr_svr_depon_net_para " + getWhereSql(obj, parameters);
			rs = session.executeByList(sqlStr, parameters);
			// 保存内部服务器信息
			List<TfsvrInSvrParaDO> svrList = obj.getSvrList();
			for (int i = 0; i < svrList.size(); i++) {
				TfsvrSvrDeponNetParaDO deponNetDo = new TfsvrSvrDeponNetParaDO();
				deponNetDo.setNetRegion(obj.getNetRegion());
				deponNetDo.setFileResTp("1");
				deponNetDo.setFileSvrId(svrList.get(i).getFileSvrId());
				tfsvrSvrDeponNetParaDao.insert(deponNetDo);
				tfsvrInSvrParaDao.insert(svrList.get(i));
			}
			// 保存网络依赖关系信息
			List<TfsvrSvrDeponParaDO> deponList = obj.getDeponList();
			if (deponList != null && !deponList.isEmpty()){
				for (int i = 0; i < deponList.size(); i++) {
					tfsvrSvrDeponParaDao.insert(deponList.get(i));
				}
			}
			session.endTransaction();
			rs = 1;
		} catch (Exception e) {
			log.error("修改网络信息交易异常", e);
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_MESSAGE, "修改网络信息交易失败！");
		}
		return rs;
	}

	/**
	 * 根据主键删除数据
	 * 
	 * @param netRegion
	 * @see public int delete(T entity)
	 * @return
	 */
	public int delete(String netRegion) {
		TfsvrNetInfoMngDO obj = new TfsvrNetInfoMngDO();
		obj.setNetRegion(netRegion);
		return delete(obj);
	}

	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * 
	 * @param obj
	 * @return
	 */
	@Override
	public int delete(TfsvrNetInfoMngDO obj) {
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession("fsvr");
		try {
			session.beginTransaction();
			List<Object> parameters = Lists.newArrayList();
			StringBuilder sql = new StringBuilder();
			sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
			List<TfsvrSvrDeponNetParaDO> list = null;
			list = session.getObjectListByList(sql.toString(), TfsvrSvrDeponNetParaDO.class, parameters);
			if (list != null && !list.isEmpty()) {
				for (TfsvrSvrDeponNetParaDO tempDO : list) {
					tfsvrInSvrParaDao.delete(tempDO.getFileSvrId());
					tfsvrSvrDeponParaDao.delete(tempDO.getFileSvrId());
				}
			}
			List<Object> delParameters = Lists.newArrayList();
			String sqlStr = "DELETE FROM t_tfsvr_svr_depon_net_para " + getWhereSql(obj, delParameters);
			rs = session.executeByList(sqlStr, parameters);
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
	 * 数据库多笔查询，不分页
	 * 
	 * @param obj
	 *            数据对象DO
	 * @return List返回集合
	 */
	@Override
	public List<TfsvrNetInfoMngDO> list(TfsvrNetInfoMngDO obj) {
		// TODO Auto-generated method stub
		return list(obj, 0, 0);
	}

	/**
	 * 数据库多笔查询，支持分页
	 * 
	 * @param obj
	 *            数据对象DO
	 * @param start
	 *            起始位置
	 * @param limit
	 *            每页数量
	 * @return List返回集合
	 */
	@Override
	public List<TfsvrNetInfoMngDO> list(TfsvrNetInfoMngDO obj, int start, int limit) {
		log.debug("TfsvrNetInfoMngDO=" + obj.toString());
		log.debug("start=" + start);
		log.debug("limit=" + limit);
		List<TfsvrNetInfoMngDO> list = null;
		try {
			List<Object> parameters = Lists.newArrayList();
			StringBuilder sql = new StringBuilder();
			sql.append("select distinct net_region from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
			sql.append(" order by net_region desc");
			IDBSession session = DBSessionFactory.getSession("fsvr");
			if (limit <= 0) {
				list = session.getObjectListByList(sql.toString(), TfsvrNetInfoMngDO.class, parameters);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), TfsvrNetInfoMngDO.class, start, limit,
						parameters);
			}
			if (list != null && !list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					String sqlStr = "select * from t_tfsvr_svr_depon_net_para where file_res_tp = '1' and net_region = ? ";
					List<Object> sqlParameters = Lists.newArrayList();
					sqlParameters.add(list.get(i).getNetRegion());
					TfsvrSvrDeponNetParaDO deponNetDO = session.getObjectByList(sqlStr, TfsvrSvrDeponNetParaDO.class,
							sqlParameters);
					List<TfsvrSvrDeponNetParaDO> deponList = deponQry(deponNetDO.getFileSvrId());
					if (deponList != null && !deponList.isEmpty()) {
						String deponNetRegion = deponList.get(0).getNetRegion();
						for (int j = 1; j < deponList.size(); j++) {
							deponNetRegion = deponNetRegion + "," + deponList.get(j).getNetRegion();
						}
						list.get(i).setDeponNetRegion(deponNetRegion);
					}
				}
			}
		} catch (Exception e) {
			log.error("列表查询异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "列表查询失败！");
		}
		return list;
	}

	/**
	 * 依赖网络查询
	 * 
	 * @param obj
	 * @return
	 */
	public List<TfsvrSvrDeponNetParaDO> deponQry(String fileSvrId) {
		List<TfsvrSvrDeponNetParaDO> list = null;
		IDBSession session = DBSessionFactory.getSession("fsvr");
		try {
			session.beginTransaction();
			List<Object> parameters = Lists.newArrayList();
			StringBuilder sql = new StringBuilder();
			sql.append("select distinct net_region from ").append(TABLE_NAME).append(" where file_svr_id in ");
			sql.append("( select depon_file_svr_id from ").append(DEPEN_TABLE_NAME).append(" where file_svr_id =? )");
			parameters.add(fileSvrId);
			list = session.getObjectListByList(sql.toString(), TfsvrSvrDeponNetParaDO.class, parameters);
			session.endTransaction();
		} catch (Exception e) {
			log.error("依赖网络查询异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "依赖网络查询失败！");
		}
		return list;
	}

	/**
	 * 数据库多笔查询，支持分页
	 * 
	 * @param start
	 *            起始位置
	 * @param limit
	 *            每页数量
	 * @param param
	 *            查询参数
	 * @return List返回集合
	 */
	@Override
	public List<TfsvrNetInfoMngDO> list(int start, int limit, Object... param) {
		return null;
	}

	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * 
	 * @param obj
	 *            数据对象DO
	 * @return total
	 */
	public int getTotal(TfsvrNetInfoMngDO obj) {
		log.debug("TfsvrNetInfoMngDO=" + obj);
		List<Object> parameters = Lists.newArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));
		String countSql = "select count(1) from (" + sql.toString() + ") ct";
		IDBSession session = DBSessionFactory.getSession("fsvr");
		int total = 0;
		try {
			total = session.accountByList(countSql, parameters);
		} catch (Exception e) {
			log.error("总记录数查询t_tfsvr_svr_depon_net_para异常", e);
			throw new BaseException(SysErr.E_MESSAGE, "总记录数查询t_tfsvr_svr_depon_net_para失败！");
		}
		return total;
	}

	/**
	 * 根据数据对象产生对应的查询SQL
	 * 
	 * @param obj
	 *            数据对象DO
	 * @return SQL
	 */
	public String getWhereSql(TfsvrNetInfoMngDO obj, List<Object> parameters) {
		if (null == parameters) {
			parameters = Lists.newArrayList();
		}
		StringBuffer sql = new StringBuffer(" where 1=1 AND file_res_tp = '1' ");
		if (!DataUtil.isNullStr(obj.getNetRegion())) {
			sql.append(" AND net_region = ? ");
			parameters.add(obj.getNetRegion());
		}
		return sql.toString();
	}

}