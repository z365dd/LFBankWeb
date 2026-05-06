package com.adtec.para.center.dao;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.para.center.entity.CenterZkNodeDO;
import com.adtec.para.cfgcenter.entity.ParaZkNodeData;
import com.adtec.sys.common.dao.IBaseDao;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;


@Component
public class ParaZkNodeDao implements IBaseDao<CenterZkNodeDO>{

    /*日志对象*/
    protected final static Logger logger = LoggerFactory.getLogger(ParaZkNodeDao.class);
    /*表名称*/
    public static final String TABLE_NAME = "t_para_center_zk_node";


    @Override
    public int insert(CenterZkNodeDO centerZkNodeDO) {
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.saveObject(TABLE_NAME, centerZkNodeDO, centerZkNodeDO.getIgnoreFields());
        } catch (SQLException e) {
            logger.error("新增缓存中心zk节点信息异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "新增缓存中心zk节点信息失败！");
        }
        return rs;
    }


    @Override
    public int update(CenterZkNodeDO centerZkNodeDO) {
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.updateObject(TABLE_NAME, centerZkNodeDO, centerZkNodeDO.getMatchFields(), centerZkNodeDO.getIgnoreFields());
        } catch (SQLException e) {
            logger.error("修改缓存中心zk节点信息异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "修改缓存中心zk节点信息失败！");
        }
        return rs;
    }


    @Override
    public int delete(CenterZkNodeDO centerZkNodeDO) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from ").append(TABLE_NAME).append(" where id = ?");
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.execute(sql.toString(), centerZkNodeDO.getId());
        } catch (SQLException e) {
            logger.error("删除缓存中心zk节点信息异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "删除缓存中心zk节点信息失败！");
        }
        return rs;
    }


    public CenterZkNodeDO get(String id) {
        CenterZkNodeDO obj = new CenterZkNodeDO();
        obj.setId(id);
        return get(obj);
    }


    @Override
	public CenterZkNodeDO get(CenterZkNodeDO centerZkNodeDO) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where id = ?");
        CenterZkNodeDO rs = new CenterZkNodeDO();
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObject(sql.toString(), CenterZkNodeDO.class, centerZkNodeDO.getId());
        } catch (SQLException e) {
            logger.error("查询缓存中心zk节点信息异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "查询缓存中心zk节点信息失败！");
        }
        return rs;
    }


    @Override
    public List<CenterZkNodeDO> list(CenterZkNodeDO centerZkNodeDO) {
        return list(centerZkNodeDO, 0, 0);
    }


    @Override
    public List<CenterZkNodeDO> list(CenterZkNodeDO centerZkNodeDO, int start, int limit) {
        logger.debug("centerZkNodeDO=" + centerZkNodeDO);
        logger.debug("start=" + start);
        logger.debug("limit=" + limit);
        List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(centerZkNodeDO, parameters));
        sql.append(" order by update_date desc");
        logger.debug("sql=" + sql.toString());
        IDBSession session = DBSessionFactory.getSession();
        List<CenterZkNodeDO> list = null;
        try {
            if (limit == 0) {
                list = session.getObjectListByList(sql.toString(), CenterZkNodeDO.class, parameters);
            } else {
                list = session.getObjectListByListForPage(sql.toString(), CenterZkNodeDO.class, start, limit, parameters);
            }
        } catch (SQLException e) {
            logger.error("列表查询缓存中心zk节点信息异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "列表查询缓存中心zk节点信息失败！");
        }
        return list;
    }


    @Override
    public List<CenterZkNodeDO> list(int start, int limit, Object... param) {
        return null;
    }


    public int getTotal(CenterZkNodeDO centerZkNodeDO) {
        logger.debug("centerZkNodeDO=" + centerZkNodeDO);
        List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(centerZkNodeDO, parameters));
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        logger.debug("countSql=" + countSql);
        IDBSession session = DBSessionFactory.getSession();
        int total = 0;
        try {
            total = session.account(countSql);
        } catch (SQLException e) {
            logger.error("总记录数查询缓存中心zk节点信息异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "总记录数查询缓存中心zk节点信息失败！");
        }
        return total;
    }


    /*根据传入的数据对象，对应属性值不空时拼接查询条件*/
    private String getWhereSql(CenterZkNodeDO centerZkNodeDO, List<Object> parameters) {
        StringBuffer whereSql = new StringBuffer(" where 1=1 ");
        if(!DataUtil.isNullStr(centerZkNodeDO.getId())){
            /*添加查询条件：编号*/
            whereSql.append(" and id='").append(centerZkNodeDO.getId()).append("'");
        }
        if(!DataUtil.isNullStr(centerZkNodeDO.getCacheCentrId())){
            /*添加查询条件：所属缓存中心id*/
            whereSql.append(" and CACHE_CENTR_ID = ?");
            parameters.add(centerZkNodeDO.getCacheCentrId());
        }
        if(!DataUtil.isNullStr("" + centerZkNodeDO.getZkId())){
            /*添加查询条件：节点序号*/
            whereSql.append(" and zk_id = ?");
            parameters.add(centerZkNodeDO.getZkId());
        }
        if(!DataUtil.isNullStr(centerZkNodeDO.getIp())){
            /*添加查询条件：节点地址*/
            whereSql.append(" and ip = ?");
            parameters.add(centerZkNodeDO.getIp());
        }
        if(!DataUtil.isNullStr("" + centerZkNodeDO.getPort())){
            /*添加查询条件：节点端口*/
            whereSql.append(" and port = ?");
            parameters.add(centerZkNodeDO.getPort());
        }
        if(!DataUtil.isNullStr("" + centerZkNodeDO.getCommPort())){
            /*添加查询条件：通讯端口*/
            whereSql.append(" and comm_port = ?");
            parameters.add(centerZkNodeDO.getCommPort());
        }
        if(!DataUtil.isNullStr("" + centerZkNodeDO.getElectionPort())){
            /*添加查询条件：选举端口*/
            whereSql.append(" and election_port = ?");
            parameters.add(centerZkNodeDO.getCommPort());
        }
        return whereSql.toString();
    }

    /**
     * 根据缓存中心id获取ZooKeeper节点信息 -管理台页面展示
     * @param id
     * @return
     */
	public List<CenterZkNodeDO> getNodeByCenterId(String cacheCenterId) {
		String sql = "select * from "+ TABLE_NAME + " where CACHE_CENTR_ID = ?";
		logger.debug("sql="+sql);
		IDBSession session = DBSessionFactory.getSession();
		List<CenterZkNodeDO> list = null;
		try {
			list = session.getObjectList(sql, CenterZkNodeDO.class, cacheCenterId);
		}catch (SQLException e) {
            logger.error("ZooKeeper节点信息获取异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "ZooKeeper节点信息获取失败！");
        }
        return list;
	}

	/**
	 * 根据缓存中心id删除ZooKeeper节点信息
	 * @param centerId
	 */
	public int deleteByCenterId(String centerId) {
		StringBuilder sql = new StringBuilder();
		sql.append("delete from ").append(TABLE_NAME).append(" where CACHE_CENTR_ID = ?");
		logger.debug("sql="+sql.toString());
		IDBSession session = DBSessionFactory.getSession();
		int rs = 0;
		try {
			rs = session.execute(sql.toString(), centerId);
		}catch (SQLException e) {
            logger.error("ZooKeeper节点信息获取异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "ZooKeeper节点信息获取失败！");
        }
		return rs;
	}

	/**
	 * 根据缓存中心id获取ZooKeeper节点信息 - 同步配置信息
	 * @param centerId
	 * @return
	 */
	public List<ParaZkNodeData> getZkDataByCenterId(String cacheCentrId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from ")
			.append(TABLE_NAME)
			.append(" where CACHE_CENTR_ID = ?");
		logger.debug("sql="+sql.toString());
		IDBSession session = DBSessionFactory.getSession();
		List<ParaZkNodeData> list = null;
		try {
			list = session.getObjectList(sql.toString(), ParaZkNodeData.class, cacheCentrId);
		}catch (SQLException e) {
            logger.error("ZooKeeper节点信息获取异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "ZooKeeper节点信息获取失败！");
        }
        return list;
	}


}

