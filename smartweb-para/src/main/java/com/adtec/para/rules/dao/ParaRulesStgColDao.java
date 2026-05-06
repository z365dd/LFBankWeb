package com.adtec.para.rules.dao;
/**
 * 系统名称: SmartWeb平台
 * 模块名称:存储规则键项表数据库操作类
 * 类  名  称: RulesStgColDao.java
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
import com.adtec.para.cfgcenter.entity.ParaRulesStgColData;
import com.adtec.para.rules.entity.ParaRulesStgColDO;
import com.adtec.sys.common.dao.IBaseDao;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;


@Component
public class ParaRulesStgColDao implements IBaseDao<ParaRulesStgColDO>{

    /*日志对象*/
    protected final static Logger logger = LoggerFactory.getLogger(ParaRulesStgColDao.class);
    /*表名称*/
    public static final String TABLE_NAME = "t_para_rules_stg_col";
    public static final String T_CACHE_RULES_STG = "t_para_rules_stg";
    public static final String T_CAHCE_CENTER = "t_para_center";


    @Override
    public int insert(ParaRulesStgColDO rulesStgColDO) {
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.saveObject(TABLE_NAME, rulesStgColDO, rulesStgColDO.getIgnoreFields());
        } catch (SQLException e) {
            logger.error("新增存储规则键项表异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "新增存储规则键项表失败！");
        }
        return rs;
    }


    @Override
    public int update(ParaRulesStgColDO rulesStgColDO) {
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.updateObject(TABLE_NAME, rulesStgColDO, rulesStgColDO.getMatchFields(), rulesStgColDO.getIgnoreFields());
        } catch (SQLException e) {
            logger.error("修改存储规则键项表异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "修改存储规则键项表失败！");
        }
        return rs;
    }


    @Override
    public int delete(ParaRulesStgColDO rulesStgColDO) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from ").append(TABLE_NAME).append(" where id = ?");
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.execute(sql.toString(), rulesStgColDO.getId());
        } catch (SQLException e) {
            logger.error("删除存储规则键项表异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "删除存储规则键项表失败！");
        }
        return rs;
    }


    public ParaRulesStgColDO get(String id) {
        ParaRulesStgColDO obj = new ParaRulesStgColDO();
        obj.setId(id);
        return get(obj);
    }


    @Override
	public ParaRulesStgColDO get(ParaRulesStgColDO rulesStgColDO) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where id = ?");
        ParaRulesStgColDO rs = new ParaRulesStgColDO();
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObject(sql.toString(), ParaRulesStgColDO.class, rulesStgColDO.getId());
        } catch (SQLException e) {
            logger.error("查询存储规则键项表异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "查询存储规则键项表失败！");
        }
        return rs;
    }


    @Override
    public List<ParaRulesStgColDO> list(ParaRulesStgColDO rulesStgColDO) {
        return list(rulesStgColDO, 0, 0);
    }


    @Override
    public List<ParaRulesStgColDO> list(ParaRulesStgColDO rulesStgColDO, int start, int limit) {
        logger.debug("rulesStgColDO=" + rulesStgColDO);
        logger.debug("start=" + start);
        logger.debug("limit=" + limit);
        List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(rulesStgColDO, parameters));
        sql.append(" order by upt_time desc");
        logger.debug("sql=" + sql.toString());
        IDBSession session = DBSessionFactory.getSession();
        List<ParaRulesStgColDO> list = null;
        try {
            if (limit == 0) {
                list = session.getObjectListByList(sql.toString(), ParaRulesStgColDO.class, parameters);
            } else {
                list = session.getObjectListByListForPage(sql.toString(), ParaRulesStgColDO.class, start, limit, parameters);
            }
        } catch (SQLException e) {
            logger.error("列表查询存储规则键项表异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "列表查询存储规则键项表失败！");
        }
        return list;
    }


    @Override
    public List<ParaRulesStgColDO> list(int start, int limit, Object... param) {
        return null;
    }


    public int getTotal(ParaRulesStgColDO rulesStgColDO) {
        logger.debug("rulesStgColDO=" + rulesStgColDO);
        List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(rulesStgColDO, parameters));
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        logger.debug("countSql=" + countSql);
        IDBSession session = DBSessionFactory.getSession();
        int total = 0;
        try {
            total = session.accountByList(countSql, parameters);
        } catch (SQLException e) {
            logger.error("总记录数查询存储规则键项表异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "总记录数查询存储规则键项表失败！");
        }
        return total;
    }


    /*根据传入的数据对象，对应属性值不空时拼接查询条件*/
    private String getWhereSql(ParaRulesStgColDO rulesStgColDO, List<Object> parameters) {
        StringBuffer whereSql = new StringBuffer(" where 1=1 ");
        if(!DataUtil.isNullStr(rulesStgColDO.getId())){
            /*添加查询条件：ID*/
            whereSql.append(" and id = ?");
            parameters.add(rulesStgColDO.getId());
        }
        if(!DataUtil.isNullStr(rulesStgColDO.getRuleId())){
            /*添加查询条件：所属存储规则ID*/
            whereSql.append(" and rule_id = ?");
            parameters.add(rulesStgColDO.getRuleId());
        }
        if(!DataUtil.isNullStr(rulesStgColDO.getColNo())){
            /*添加查询条件：列名*/
            whereSql.append(" and col_no = ?");
            parameters.add(rulesStgColDO.getColNo());
        }
        if(!DataUtil.isNullStr(rulesStgColDO.getColName())){
            /*添加查询条件：中文描述*/
            whereSql.append(" and col_name = ?");
            parameters.add(rulesStgColDO.getColName());
        }
        if(!DataUtil.isNullStr(rulesStgColDO.getDataTp())){
            /*添加查询条件：数据类型*/
            whereSql.append(" and col_tp = ? ");
            parameters.add(rulesStgColDO.getDataTp());
        }
        if(!DataUtil.isNullStr(rulesStgColDO.getColLen())){
            /*添加查询条件：数据长度*/
            whereSql.append(" and col_len = ? ");
            parameters.add(rulesStgColDO.getColLen());
        }
        if(!DataUtil.isNullStr(rulesStgColDO.getNullFlg())){
            /*添加查询条件：是否非空*/
            whereSql.append(" and null_flg = ?");
            parameters.add(rulesStgColDO.getNullFlg());
        }
        if(!DataUtil.isNullStr(rulesStgColDO.getUniqFlg())){
            /*添加查询条件：是否唯一索引*/
            whereSql.append(" and uniq_flg = ?");
            parameters.add(rulesStgColDO.getUniqFlg());
        }
        return whereSql.toString();
    }

    /**
     * 根据存储规则id获取对应的键项列表
     * @param id
     * @return
     */
	public List<ParaRulesStgColDO> getListByStgId(String id) {
		List<ParaRulesStgColDO> list = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ").append(TABLE_NAME).append(" where 1=1 and rule_id = ? order by col_ser asc");
		logger.debug("sql=" + sql.toString());
        IDBSession session = DBSessionFactory.getSession();
        try {
        	list = session.getObjectList(sql.toString(), ParaRulesStgColDO.class, id);
        } catch (SQLException e) {
            logger.error("查询存储规则键项表异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "查询存储规则键项表失败！");
        }
		return list;
	}

	/**
	 * 根据存储规则id删除对应的键项列表
	 * @param stgId
	 */
	public int deleteByStgId(String stgId) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from ").append(TABLE_NAME).append(" where rule_id = ?");
		logger.debug("sql=" + sql.toString());
		int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
        	rs = session.execute(sql.toString(), stgId);
        } catch (SQLException e) {
            logger.error("查询存储规则键项表异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "查询存储规则键项表失败！");
        }
        return rs;
	}

	/**
	 * 根据存储规则id获取字段信息 -- 同步配置信息
	 * @param id
	 * @return
	 */
	public List<ParaRulesStgColData> getColDataByStgId(String id) {
		List<ParaRulesStgColData> list = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ").append(TABLE_NAME).append(" where 1=1 and rule_id = ?");
		logger.debug("sql=" + sql.toString());
        IDBSession session = DBSessionFactory.getSession();
        try {
        	list = session.getObjectList(sql.toString(), ParaRulesStgColData.class, id);
        } catch (SQLException e) {
            logger.error("查询存储规则键项表异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "查询存储规则键项表失败！");
        }
		return list;
	}

	/**
	 * 根据存储规则英文名获取字段信息
	 * @param enname
	 * @return
	 */
	public List<ParaRulesStgColDO> getColByRulesName(String engName) {
		List<ParaRulesStgColDO> list = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ").append(TABLE_NAME).append(" c left join ")
            .append(T_CACHE_RULES_STG).append(" s on c.rule_id = s.id ")
			.append(" left join ").append(T_CAHCE_CENTER).append(" t on t.id = s.cache_centr_id  ")
			.append(" where s.eng_name = ?  order by c.col_ser asc");
		logger.debug("sql=" + sql.toString());
		IDBSession session = DBSessionFactory.getSession();
        try {
        	list = session.getObjectList(sql.toString(), ParaRulesStgColDO.class, engName);
        } catch (SQLException e) {
            logger.error("查询存储规则键项表异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "查询存储规则键项表失败！");
        }
		return list;
	}
}
