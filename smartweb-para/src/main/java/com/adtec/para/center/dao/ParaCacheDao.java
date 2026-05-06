package com.adtec.para.center.dao;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.para.center.entity.ParaCacheDO;
import com.adtec.para.cfgcenter.entity.ParaCenterData;
import com.adtec.sys.common.dao.IBaseDao;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;


@Component
public class ParaCacheDao implements IBaseDao<ParaCacheDO>{

    /*日志对象*/
    protected final static Logger logger = LoggerFactory.getLogger(ParaCacheDao.class);
    /*表名称*/
    public static final String TABLE_NAME = "t_para_center";
    
    public static final String CENTER = "t_para_center";
    

    @Override
    public int insert(ParaCacheDO centerDO) {
        int rs;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.saveObject(TABLE_NAME, centerDO, centerDO.getIgnoreFields());
        } catch (SQLException e) {
            logger.error("新增缓存中心异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "新增缓存中心失败！");
        }
        return rs;
    }


    @Override
    public int update(ParaCacheDO centerDO) {
        int rs;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.updateObject(TABLE_NAME, centerDO, centerDO.getMatchFields(), centerDO.getIgnoreFields());
        } catch (SQLException e) {
            logger.error("修改缓存中心异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "修改缓存中心失败！");
        }
        return rs;
    }


    @Override
    public int delete(ParaCacheDO centerDO) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from ").append(TABLE_NAME).append(" where id = ?");
        int rs;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.execute(sql.toString(), centerDO.getId());
        } catch (SQLException e) {
            logger.error("删除缓存中心异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "删除缓存中心失败！");
        }
        return rs;
    }


    public ParaCacheDO get(String id) {
        ParaCacheDO obj = new ParaCacheDO();
        obj.setId(id);
        return get(obj);
    }


    @Override
	public ParaCacheDO get(ParaCacheDO centerDO) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where id = ?");
        ParaCacheDO rs;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObject(sql.toString(), ParaCacheDO.class, centerDO.getId());
        } catch (SQLException e) {
            logger.error("查询缓存中心异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "查询缓存中心失败！");
        }
        return rs;
    }


    @Override
    public List<ParaCacheDO> list(ParaCacheDO centerDO) {
        return list(centerDO, 0, 0);
    }


    @Override
    public List<ParaCacheDO> list(ParaCacheDO centerDO, int start, int limit) {
        logger.debug("enterDO=" + centerDO);
        logger.debug("start=" + start);
        logger.debug("limit=" + limit);
        List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(centerDO, parameters)).append(" order by UPT_TIME desc");
        return getCenterList(start, limit, sql, parameters);
    }

    private List<ParaCacheDO> getCenterList(int start, int limit, StringBuilder sql, List<Object> parameters) {
        IDBSession session = DBSessionFactory.getSession();
        List<ParaCacheDO> list;
        try {
            if (limit == 0) {
                list = session.getObjectListByList(sql.toString(), ParaCacheDO.class, parameters);
            } else {
                list = session.getObjectListByListForPage(sql.toString(), ParaCacheDO.class, start, limit, parameters);
            }
        } catch (SQLException e) {
            logger.error("列表查询缓存中心异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "列表查询缓存中心失败！");
        }
        return list;
    }


    @Override
    public List<ParaCacheDO> list(int start, int limit, Object... param) {
        return null;
    }


    public int getTotal(ParaCacheDO centerDO) {
        logger.debug("enterDO=" + centerDO);
        List<Object> parameters = Lists.newArrayList();
        String countSql = "select count(1) from (" + "select * from " + TABLE_NAME + getWhereSql(centerDO, parameters) + " and del_flg != 'Y'" + ") ct";
        logger.debug("countSql=" + countSql);
        IDBSession session = DBSessionFactory.getSession();
        int total;
        try {
            total = session.accountByList(countSql, parameters);
        } catch (SQLException e) {
            logger.error("总记录数查询缓存中心异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "总记录数查询缓存中心失败！");
        }
        return total;
    }


    /*根据传入的数据对象，对应属性值不空时拼接查询条件*/
    private String getWhereSql(ParaCacheDO centerDO, List<Object> parameters) {
        StringBuilder whereSql = new StringBuilder(" where 1=1 ");
        if(!DataUtil.isNullStr(centerDO.getId())){
            /*添加查询条件：编号*/
            whereSql.append(" and id = ?");
            parameters.add(centerDO.getId());
        }
        if(!DataUtil.isNullStr(centerDO.getEngName())){
            /*添加查询条件：英文名称*/
            whereSql.append(" and eng_name like ?");
            parameters.add("%"+centerDO.getEngName()+"%");
        }
        if(!DataUtil.isNullStr(centerDO.getChName())){
            /*添加查询条件：中文名称*/
            whereSql.append(" and ch_name like ?");
            parameters.add("%"+centerDO.getChName()+"%");
        }
        if(!DataUtil.isNullStr(centerDO.getRunStat())){
            /*添加查询条件：运行状态*/
            whereSql.append(" and run_stat = ?");
            parameters.add(centerDO.getRunStat());
        }
        if(!DataUtil.isNullStr(centerDO.getCacheMode())){
            /*添加查询条件：缓存模式*/
            whereSql.append(" and cache_mode = ?");
            parameters.add(centerDO.getCacheMode());
        }
        return whereSql.toString();
    }

    /**
     * 缓存中心列表查询
     * @param centerDO 缓存中心DO
     * @param start 开始页
     * @param limit 每页条数
     * @return list 列表
     */
	public List<ParaCacheDO> listByPage(ParaCacheDO centerDO, int start, int limit) {
        logger.debug("enterDO=" + centerDO);
        logger.debug("start=" + start);
        logger.debug("limit=" + limit);
        List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where 1=1 and del_flg != 'Y' ");// 显示未删除的
        	if(!DataUtil.isNullStr(centerDO.getEngName())){
              /*添加查询条件：英文名称*/
              sql.append(" and eng_name like ?");
              parameters.add("%"+centerDO.getEngName()+"%");
          }
          if(!DataUtil.isNullStr(centerDO.getChName())){
              /*添加查询条件：中文名称*/
              sql.append(" and ch_name like ?");
              parameters.add("%"+centerDO.getChName()+"%");
          }
          if(!DataUtil.isNullStr(centerDO.getRunStat())){
              /*添加查询条件：中文名称*/
              sql.append(" and run_stat = ?");
              parameters.add(centerDO.getRunStat());
          }
        
        sql.append(" order by UPT_TIME desc");
        logger.debug("sql=" + sql.toString());
        return getCenterList(start, limit, sql, parameters);
    }

	/**
	 * 根据id获取缓存中心信息
	 * @param centerId
	 * @return
	 */
	public ParaCenterData getCenterDataById(String centerId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from ").append(TABLE_NAME).append(" where id = ?");
		logger.debug("sql=" + sql.toString());
		ParaCenterData rs;
		IDBSession session = DBSessionFactory.getSession();
		try {
		    rs = session.getObject(sql.toString(), ParaCenterData.class, centerId);
		} catch (SQLException e) {
		    logger.error("查询缓存中心异常："+ e.getMessage());
		    throw new BaseException(SysErr.E_MESSAGE, "查询缓存中心失败！");
		}
		return rs;
	}

	/**
	 * 根据id更新缓存中心运行状态
	 * @param centerId
	 */
	public int updateRunStat(String centerId, String runStat) {
        List<Object> parameters = Lists.newArrayList();
		String sql = "update " + TABLE_NAME + " set run_stat = ? where id = ?";
		parameters.add(runStat);
		parameters.add(centerId);
		logger.debug("sql=" + sql.toString());
		int rs;
		IDBSession session = DBSessionFactory.getSession();
		try {
			rs = session.executeByList(sql, parameters);
		} catch (Exception e) {
			logger.error("更新缓存中心状态异常："+ e.getMessage());
		    throw new BaseException(SysErr.E_MESSAGE, "更新缓存中心状态异常！");
		}
		return rs;
	}


    /**
     * 获取缓存中心数量
     * @param centerDO
     * @return
     */
	public int getCenterSum(ParaCacheDO centerDO) {
        List<Object> parameters = Lists.newArrayList();
        StringBuffer sql = new StringBuffer();
        sql.append("select count(*) from ").append(TABLE_NAME).append(getWhereSql(centerDO, parameters));
        logger.debug("sql=" + sql.toString());
        int rs;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.accountByList(sql.toString(), parameters);
        } catch (Exception e) {
            logger.error("获取缓存中心数量异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "获取缓存中心数量异常！");
        }
        return rs;
    }


	/**
	 * 根据存储规则Id获取已启动缓存中心信息
	 * @param stgId
	 * @return
	 */
	public int getCenterSumByStgId(String stgId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from t_para_rules_stg s left join t_para_center c on s.cache_centr_id = c.id where c.run_stat = '01'")
			.append(" and s.id = ?");
		int rs;
		IDBSession session = DBSessionFactory.getSession();
		try {
			rs = session.account(sql.toString(), stgId);
		} catch (Exception e) {
			logger.error("获取缓存中心数量异常："+ e.getMessage());
		    throw new BaseException(SysErr.E_MESSAGE, "获取缓存中心数量异常！");
		}
		return rs;
	}

	/**
	 * 根据id逻辑删除缓存中心
	 * @param id
	 */
	public int delById(String id) {
	    List<Object> parameters = Lists.newArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("update ").append(TABLE_NAME).append(" set del_flg = 'Y' where id = ?");
        parameters.add(id);
		int rs;
		IDBSession session = DBSessionFactory.getSession();
		try {
			rs = session.executeByList(sql.toString(), parameters);
		} catch (Exception e) {
			logger.error("删除缓存中心异常："+ e.getMessage());
		    throw new BaseException(SysErr.E_MESSAGE, "删除缓存中心异常！");
		}
		return rs;
	}


    public int updateStat(String centerId, String runStat, String syncFlg) {
        List<Object> parameters = Lists.newArrayList();
        String sql = "update " + TABLE_NAME + " set run_stat = ?, sync_flg = ? where id = ?";
        parameters.add(runStat);
        parameters.add(syncFlg);
        parameters.add(centerId);
        logger.debug("sql=" + sql.toString());
        int rs;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.executeByList(sql, parameters);
        } catch (Exception e) {
            logger.error("更新缓存中心状态异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "更新缓存中心状态异常！");
        }
        return rs;
    }
}
