package com.adtec.para.center.dao;
/**
 * 系统名称: SmartWeb平台
 * 模块名称: 缓存中心redis节点信息数据库操作类
 * 类  名  称: CenterRedisNodeDao.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl
 * 开发时间: 2018-11-13 11:47:37
 * 系统版本: V1.0.0
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================ *
 * ========     ======  ============================================
 */


import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.para.center.entity.CenterRedisNodeDO;
import com.adtec.para.cfgcenter.entity.ParaRedisNodeData;
import com.adtec.sys.common.dao.IBaseDao;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;


@Component
public class ParaRedisNodeDao implements IBaseDao<CenterRedisNodeDO>{

    /*日志对象*/
    protected final static Logger logger = LoggerFactory.getLogger(ParaRedisNodeDao.class);
    /*表名称*/
    public static final String TABLE_NAME = "t_para_center_redis_node";


    @Override
    public int insert(CenterRedisNodeDO centerRedisNodeDO) {
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.saveObject(TABLE_NAME, centerRedisNodeDO, centerRedisNodeDO.getIgnoreFields());
        } catch (SQLException e) {
            logger.error("新增缓存中心redis节点信息异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "新增缓存中心redis节点信息失败！");
        }
        return rs;
    }


    @Override
    public int update(CenterRedisNodeDO centerRedisNodeDO) {
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.updateObject(TABLE_NAME, centerRedisNodeDO, centerRedisNodeDO.getMatchFields(), centerRedisNodeDO.getIgnoreFields());
        } catch (SQLException e) {
            logger.error("修改缓存中心redis节点信息异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "修改缓存中心redis节点信息失败！");
        }
        return rs;
    }


    @Override
    public int delete(CenterRedisNodeDO centerRedisNodeDO) {
//        StringBuilder sql = new StringBuilder();
//        sql.append("delete from ").append(TABLE_NAME).append(" where id='").append(centerRedisNodeDO.getId()).append("'");
        String sql = "delete from " + TABLE_NAME + " where id = ?";
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.execute(sql.toString(), centerRedisNodeDO.getId());
        } catch (SQLException e) {
            logger.error("删除缓存中心redis节点信息异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "删除缓存中心redis节点信息失败！");
        }
        return rs;
    }


    public CenterRedisNodeDO get(String id) {
        CenterRedisNodeDO obj = new CenterRedisNodeDO();
        obj.setId(id);
        return get(obj);
    }


    @Override
	public CenterRedisNodeDO get(CenterRedisNodeDO centerRedisNodeDO) {
//        StringBuilder sql = new StringBuilder();
//        sql.append("select * from ").append(TABLE_NAME).append(" where id='").append(centerRedisNodeDO.getId()).append("'");
        String sql = "select * from " + TABLE_NAME + " where id = ?";
        CenterRedisNodeDO rs = new CenterRedisNodeDO();
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObject(sql.toString(), CenterRedisNodeDO.class, centerRedisNodeDO.getId());
        } catch (SQLException e) {
            logger.error("查询缓存中心redis节点信息异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "查询缓存中心redis节点信息失败！");
        }
        return rs;
    }


    @Override
    public List<CenterRedisNodeDO> list(CenterRedisNodeDO centerRedisNodeDO) {
        return list(centerRedisNodeDO, 0, 0);
    }


    @Override
    public List<CenterRedisNodeDO> list(CenterRedisNodeDO centerRedisNodeDO, int start, int limit) {
        logger.debug("centerRedisNodeDO=" + centerRedisNodeDO);
        logger.debug("start=" + start);
        logger.debug("limit=" + limit);
        List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(centerRedisNodeDO, parameters));
        sql.append(" order by upt_time desc");
        logger.debug("sql=" + sql.toString());
        IDBSession session = DBSessionFactory.getSession();
        List<CenterRedisNodeDO> list = null;
        try {
            if (limit == 0) {
                list = session.getObjectListByList(sql.toString(), CenterRedisNodeDO.class, parameters);
            } else {
                list = session.getObjectListByListForPage(sql.toString(), CenterRedisNodeDO.class, start, limit, parameters);
            }
        } catch (SQLException e) {
            logger.error("列表查询缓存中心redis节点信息异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "列表查询缓存中心redis节点信息失败！");
        }
        return list;
    }


    @Override
    public List<CenterRedisNodeDO> list(int start, int limit, Object... param) {
        return null;
    }


    public int getTotal(CenterRedisNodeDO centerRedisNodeDO) {
        logger.debug("centerRedisNodeDO=" + centerRedisNodeDO);
        List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(centerRedisNodeDO, parameters));
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        logger.debug("countSql=" + countSql);
        IDBSession session = DBSessionFactory.getSession();
        int total = 0;
        try {
            total = session.accountByList(countSql, parameters);
        } catch (SQLException e) {
            logger.error("总记录数查询缓存中心redis节点信息异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "总记录数查询缓存中心redis节点信息失败！");
        }
        return total;
    }


    /*根据传入的数据对象，对应属性值不空时拼接查询条件*/
    private String getWhereSql(CenterRedisNodeDO centerRedisNodeDO, List<Object> parameters) {
        StringBuffer whereSql = new StringBuffer(" where 1=1 ");
        if(!DataUtil.isNullStr(centerRedisNodeDO.getId())){
            /*添加查询条件：编号*/
            whereSql.append(" and id = ?");
            parameters.add(centerRedisNodeDO.getId());
        }
        if(!DataUtil.isNullStr(centerRedisNodeDO.getCacheCentrId())){
            /*添加查询条件：所属缓存中心id*/
            whereSql.append(" and cache_centr_id = ?");
            parameters.add(centerRedisNodeDO.getCacheCentrId());
        }
        if(!DataUtil.isNullStr(centerRedisNodeDO.getMainNodeId())){
            /*添加查询条件：所属主节点*/
            whereSql.append(" and main_node_id = ?");
            parameters.add(centerRedisNodeDO.getCacheCentrId());
        }
        if(!DataUtil.isNullStr(centerRedisNodeDO.getIp())){
            /*添加查询条件：节点地址*/
            whereSql.append(" and ip = ?");
            parameters.add(centerRedisNodeDO.getIp());
        }
        if(!DataUtil.isNullStr("" + centerRedisNodeDO.getPort())){
            /*添加查询条件：节点端口*/
            whereSql.append(" and port = ?");
            parameters.add(centerRedisNodeDO.getPort());
        }
        return whereSql.toString();
    }

    /**
     * 根据缓存中心id获取redis节点信息 -- 管理台
     * @param id
     * @return
     */
	public List<CenterRedisNodeDO> getRsNodeByCenterId(String cacheCentrId, String mainNodeId) {
//		String sql = "select * from " + TABLE_NAME + " where cache_centr_id = ? and main_node_id = ? order by end_slot_num asc";
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where cache_centr_id = ? ");
        List<Object> parameters = Lists.newArrayList();
        parameters.add(cacheCentrId);
        if (!DataUtil.isNullStr(mainNodeId)) {
            sql.append("and main_node_id = ? ");
            parameters.add(mainNodeId);
        }
        sql.append("order by main_node_id desc, end_slot_num asc");
		logger.debug("sql="+sql.toString());
		List<CenterRedisNodeDO> list = null;
		IDBSession session = DBSessionFactory.getSession();
		try {
			list = session.getObjectListByList(sql.toString(), CenterRedisNodeDO.class, parameters);
		} catch (SQLException e) {
            logger.error("获取redis节点信息异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "获取redis节点信息失败！");
        }
        return list;
	}

	/**
	 * 根据缓存中心id和所属主节点id获取从节点id
	 * @param centerId
	 * @param masterId
	 * @return
	 */
//	public List<CenterRedisNodeDO> getSlaveNodeById(String centerId, String masterId) {
//		IDBSession session = DBSessionFactory.getSession();
//		List<CenterRedisNodeDO> slaveList = null;
//		StringBuffer slaveSql = new StringBuffer();
//		slaveSql.append("select * from ")
//				.append(TABLE_NAME)
//				.append(" where center_id = '")
//				.append(centerId)
//				.append("' and master_id = '")
//				.append(masterId)
//				.append("'");
//		logger.debug("slaveSql="+slaveSql.toString());
//		try {
//			slaveList = session.getObjectList(slaveSql.toString(), CenterRedisNodeDO.class);
//		} catch (SQLException e) {
//            logger.error("获取redis节点信息异常："+ e.getMessage());
//            throw new BaseException(SysErr.E_MESSAGE, "获取redis节点信息失败！");
//        }
//		return slaveList;
//	}

	/**
	 * 根据缓存中心id删除ZooKeeper节点信息
	 * @param centerId
	 */
	public int deleteByCenterId(String centerId) {
//		StringBuilder sql = new StringBuilder();
//		sql.append("delete from ").append(TABLE_NAME).append(" where centerId = ?");
        String sql = "delete from " + TABLE_NAME + " where cache_centr_id = ?";
		logger.debug("sql="+sql.toString());
		IDBSession session = DBSessionFactory.getSession();
		int rs = 0;
		try {
			rs = session.execute(sql.toString(), centerId);
		}catch (SQLException e) {
            logger.error("Redis节点删除异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "Redis节点删除失败！");
        }
		return rs;
	}

	/**
     * 根据缓存中心id获取redis节点信息 -- 同步配置信息
     * @param id
     * @return
     */
	public List<ParaRedisNodeData> getRsDataByCenterId(String cacheCentrId) {
        String sql = "select * from " + TABLE_NAME + " where cache_centr_id = ? ";
        List<Object> parameters = Lists.newArrayList();
        parameters.add(cacheCentrId);
		logger.debug("sql="+sql.toString());
		List<ParaRedisNodeData> list = null;
		IDBSession session = DBSessionFactory.getSession();
		try {
			list = session.getObjectListByList(sql.toString(), ParaRedisNodeData.class, parameters);
		} catch (SQLException e) {
            logger.error("获取redis节点信息异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "获取redis节点信息失败！");
        }
        return list;
	}
	
	/**
	 * 根据缓存中心id和所属主节点id获取从节点id -- 同步配置信息
	 * @param centerId
	 * @param masterId
	 * @return
	 */
//	public List<CacheRedisNodeData> getCfgSlaveDataById(String centerId, String masterId) {
//		IDBSession session = DBSessionFactory.getSession();
//		List<CacheRedisNodeData> slaveList = null;
//		StringBuffer slaveSql = new StringBuffer();
//		slaveSql.append("select * from ")
//				.append(TABLE_NAME)
//				.append(" where center_id = '")
//				.append(centerId)
//				.append("' and master_id = '")
//				.append(masterId)
//				.append("'");
//		logger.debug("slaveSql="+slaveSql.toString());
//		try {
//			slaveList = session.getObjectList(slaveSql.toString(), CacheRedisNodeData.class);
//		} catch (SQLException e) {
//            logger.error("获取redis节点信息异常："+ e.getMessage());
//            throw new BaseException(SysErr.E_MESSAGE, "获取redis节点信息失败！");
//        }
//		return slaveList;
//	}

	/**
	 * 根据缓存中心Id获取Redis节点信息
	 * @param id
	 * @return
	 */
	public List<CenterRedisNodeDO> getNodeByCenterId(String centerId) {
		IDBSession session = DBSessionFactory.getSession();
		List<CenterRedisNodeDO> redisNodeList = null;
		String sql = "select * from " + TABLE_NAME + " where cache_centr_id = ?";
		logger.debug("sql="+sql.toString());
		try {
			redisNodeList = session.getObjectList(sql.toString(), CenterRedisNodeDO.class, centerId);
		} catch (SQLException e) {
            logger.error("获取redis节点信息异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "获取redis节点信息失败！");
        }
		return redisNodeList;
	}

}
