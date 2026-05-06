package com.adtec.prod.oper.dao;

import com.adtec.prod.oper.entity.TPipCompParaDO;
import com.adtec.prod.oper.entity.ProdAttrDictDO;
import com.adtec.prod.oper.entity.ProdAttrDictListDO;
import com.adtec.prod.oper.entity.TPipCompSvcParaDO;
import com.adtec.framework.impl.share.caseStrategy.MBCCaseStrategy;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.common.dao.IBaseDao;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.common.util.StringUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProdAttrDictDao implements IBaseDao<ProdAttrDictDO> {
    /**
     * 日志对象
     */
    protected final static Logger logger = LoggerFactory.getLogger(ProdAttrDictDao.class);
    /*表名称*/
    public static final String TABLE_NAME = "T_PIP_KEY";
    public static final String TABLE_NAME_LIST = "T_PIP_KEY_CTRL";

    /**
     * 获取单条数据
     *
     * @param obj
     * @return
     */
    @Override
    public ProdAttrDictDO get(ProdAttrDictDO obj) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where KEY_NO=?");
        ProdAttrDictDO prodAttrDictDO = null;
        ProdAttrDictListDO prodAttrDictListDO = null;
        IDBSession session = DBSessionFactory.getSession();
        try {
            prodAttrDictDO = session.getObject(sql.toString(), ProdAttrDictDO.class, new MBCCaseStrategy(), obj.getKEY_NO());
            if (prodAttrDictDO == null) {
                return prodAttrDictDO;
            }

            sql = new StringBuilder();
            sql.append("select * from ").append(TABLE_NAME_LIST).append(" where KEY_NO=?");
            prodAttrDictListDO = session.getObject(sql.toString(), ProdAttrDictListDO.class, new MBCCaseStrategy(), prodAttrDictDO.getKEY_NO());

            if (prodAttrDictListDO != null) {
                List<ProdAttrDictListDO> list = new ArrayList<>();
                list.add(prodAttrDictListDO);
                prodAttrDictDO.setLIST(list);
                prodAttrDictDO.setELEM_KEY(prodAttrDictListDO.getELEM_KEY());
                prodAttrDictDO.setELEM_NAME(prodAttrDictListDO.getELEM_NAME());
                prodAttrDictDO.setELEM_KV(prodAttrDictListDO.getELEM_KV());
            }
        } catch (Exception e) {
            logger.error("获取属性异常：", e);
            throw new BaseException(SysErr.E_MESSAGE, "获取属性失败！");
        }
        return prodAttrDictDO;
    }

    /**
     * 获取单条数据
     *
     * @param obj
     * @return
     */
    public ProdAttrDictDO getByName(ProdAttrDictDO obj) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where KEY_NAME=?");
        ProdAttrDictDO prodAttrDictDO = null;
        IDBSession session = DBSessionFactory.getSession();
        try {
            prodAttrDictDO = session.getObject(sql.toString(), ProdAttrDictDO.class, new MBCCaseStrategy(), obj.getKEY_NAME());

        } catch (Exception e) {
            logger.error("获取属性异常：", e);
            throw new BaseException(SysErr.E_MESSAGE, "获取属性失败！");
        }
        return prodAttrDictDO;
    }

    /**
     * 插入数据
     *
     * @param obj
     * @return
     */
    @Override
    public int insert(ProdAttrDictDO obj) {
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            List<String> ignoreFields = obj.getIgnoreFields();
            rs = session.saveObject(TABLE_NAME, obj, ignoreFields, new MBCCaseStrategy());
            for (ProdAttrDictListDO prodAttrDictListDO : obj.getLIST()) {
                prodAttrDictListDO.setKEY_NO(obj.getKEY_NO());
                rs = session.saveObject(TABLE_NAME_LIST, prodAttrDictListDO, prodAttrDictListDO.getIgnoreFields(), new MBCCaseStrategy());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("新增属性交易异常：" + e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "新增属性交易失败！");
        }
        return rs;
    }

    /**
     * 更新数据
     *
     * @param obj
     * @return
     */
    @Override
    public int update(ProdAttrDictDO obj) {
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            List<ProdAttrDictListDO> list = obj.getLIST();
            if (list != null && list.size() > 0) {
                ProdAttrDictListDO prodAttrDictListDO = list.get(0);
                prodAttrDictListDO.setKEY_NO(obj.getKEY_NO());
                rs = session.deleteObject(TABLE_NAME_LIST, prodAttrDictListDO, prodAttrDictListDO.getMatchFields(), new MBCCaseStrategy());
                rs = session.saveObject(TABLE_NAME_LIST, prodAttrDictListDO, prodAttrDictListDO.getIgnoreFields(), new MBCCaseStrategy());
            } else {
                ProdAttrDictListDO prodAttrDictListDO = new ProdAttrDictListDO();
                prodAttrDictListDO.setKEY_NO(obj.getKEY_NO());
                rs = session.deleteObject(TABLE_NAME_LIST, prodAttrDictListDO, prodAttrDictListDO.getMatchFields(), new MBCCaseStrategy());
            }

            List<String> ignoreFields = obj.getIgnoreFields();
            List<String> matchFields = obj.getMatchFields();
            rs = session.updateObject(TABLE_NAME, obj, matchFields, ignoreFields, new MBCCaseStrategy());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("修改属性异常：" + e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "修改属性失败！");
        }
        return rs;
    }

    /**
     * 删除数据（一般为逻辑删除，更新del_flag字段为1）
     *
     * @param obj
     * @return
     */
    @Override
    public int delete(ProdAttrDictDO obj) {
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            String KEY_TP = obj.getKEY_TP();
            // 01产品KV属性--服务 02技术KV属性--组件
            if ("01".equals(KEY_TP)) {
                List<TPipCompSvcParaDO> list = session.getObjectList("select * from t_pip_comp_svc_para where key_tp = ? and key_no = ?", TPipCompSvcParaDO.class, obj.getKEY_TP(), obj.getKEY_NO());
                if (list.size() > 0) {
                    throw new BaseException(SysErr.E_MESSAGE, "该属性已使用，不能删除");
                }
            } else if ("02".equals(KEY_TP)) {
                List<TPipCompParaDO> list = session.getObjectList("select * from t_pip_comp_para where key_tp = ? and key_no = ?", TPipCompParaDO.class, obj.getKEY_TP(), obj.getKEY_NO());
                if (list.size() > 0) {
                    throw new BaseException(SysErr.E_MESSAGE, "该属性已使用，不能删除");
                }
            }

            ProdAttrDictListDO prodAttrDictListDO = new ProdAttrDictListDO();
            prodAttrDictListDO.setKEY_NO(obj.getKEY_NO());
            rs = session.deleteObject(TABLE_NAME_LIST, prodAttrDictListDO, prodAttrDictListDO.getMatchFields(), new MBCCaseStrategy());
            rs = session.deleteObject(TABLE_NAME, obj, obj.getMatchFields(), new MBCCaseStrategy());
            if (rs == 0) {
                throw new BaseException(SysErr.E_MESSAGE, "删除交易失败，影响记录数：" + rs);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error("删除交易异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "删除交易失败！");
        }
        return rs;
    }

    /**
     * 数据库多笔查询，不分页
     *
     * @param obj 数据对象DO
     * @return List返回集合
     */
    @Override
    public List<ProdAttrDictDO> list(ProdAttrDictDO obj) {
        return list(obj, 0, 0);
    }

    /**
     * 数据库多笔查询，支持分页
     *
     * @param obj   数据对象DO
     * @param start 起始位置
     * @param limit 每页数量
     * @return List返回集合
     */
    @Override
    public List<ProdAttrDictDO> list(ProdAttrDictDO obj, int start, int limit) {
        logger.debug("ProdAttrDictDO=" + obj.toString());
        logger.debug("start=" + start);
        logger.debug("limit=" + limit);
        List<ProdAttrDictDO> list = null;
        try {
            IDBSession session = DBSessionFactory.getSession();

            List<Object> parameters = Lists.newArrayList();
            /* 20200330 modify by zengxj 优化以下代码 */
            StringBuilder sql = new StringBuilder();
            sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));

            sql.append(" order by KEY_TP, KEY_NO ");
            logger.debug("sql=" + sql.toString());
            if (limit == 0) {
                list = session.getObjectListByList(sql.toString(), ProdAttrDictDO.class, parameters, new MBCCaseStrategy());
            } else {
                list = session.getObjectListByListForPage(sql.toString(), ProdAttrDictDO.class, start, limit, parameters, new MBCCaseStrategy());
            }
        } catch (Exception e) {
            logger.error("列表查询异常", e);
            throw new BaseException(SysErr.E_MESSAGE, "列表查询失败！");
        }
        return list;
    }

    /**
     * 数据库多笔查询，支持分页
     *
     * @param start 起始位置
     * @param limit 每页数量
     * @param param 查询参数
     * @return List返回集合
     */
    @Override
    public List<ProdAttrDictDO> list(int start, int limit, Object... param) {
        return null;
    }

    /**
     * 根据数据对象产生对应的数据查询总记录
     *
     * @param obj 数据对象DO
     * @return total
     */
    public int getTotal(ProdAttrDictDO obj) {
        logger.debug("ProdAttrDictDO=" + obj);

        IDBSession session = DBSessionFactory.getSession();

        List<Object> parameters = Lists.newArrayList();
        /* 20200330 modify by zengxj 优化以下代码 */
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(obj, parameters));

        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        logger.debug("countSql=" + countSql);
        int total = 0;
        try {
            total = session.accountByList(countSql, parameters);
        } catch (Exception e) {
            logger.error("总记录数查询sys_permission_weight异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "总记录数查询sys_permission_weight失败！");
        }
        return total;
    }

    public Map<String, String> getCompParaDistinct() {
        Map<String, String> map = new HashMap<String, String>();
        StringBuilder sql = new StringBuilder();
        sql.append("select DISTINCT a.COMP_NO,b.COMP_NAME,a.KEY_NO from T_PIP_COMP_PARA a left join T_PIP_COMP b on a.comp_no=b.comp_no");
        IDBSession session = DBSessionFactory.getSession();
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString());
            while (rs.next()) {
                String KEY_NO = rs.getString("KEY_NO");
                String COMP_NAME = rs.getString("COMP_NAME");
                if (map.containsKey(KEY_NO)) {
                    String temp = map.get(KEY_NO) + " | " + COMP_NAME;
                    map.put(KEY_NO, temp);
                } else {
                    map.put(KEY_NO, COMP_NAME);
                }
            }
        } catch (Exception e) {
            logger.error("获取属性异常：", e);
            throw new BaseException(SysErr.E_MESSAGE, "获取属性失败！");
        } finally {
            try {
                session.closeResultSetAndStatement(rs);
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return map;
    }

    public Map<String, String> getSvcParaDistinct() {
        Map<String, String> map = new HashMap<String, String>();
        StringBuilder sql = new StringBuilder();
        sql.append("select DISTINCT SVC_CODE,SVC_NAME,KEY_NO from T_PIP_COMP_SVC_PARA");
        IDBSession session = DBSessionFactory.getSession();
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString());
            while (rs.next()) {
                String KEY_NO = rs.getString("KEY_NO");
                String SVC_NAME = rs.getString("SVC_NAME");
                if (map.containsKey(KEY_NO)) {
                    String temp = map.get(KEY_NO) + " | " + SVC_NAME;
                    map.put(KEY_NO, temp);
                } else {
                    map.put(KEY_NO, SVC_NAME);
                }
            }
        } catch (Exception e) {
            logger.error("获取属性异常：", e);
            throw new BaseException(SysErr.E_MESSAGE, "获取属性失败！");
        } finally {
            try {
                session.closeResultSetAndStatement(rs);
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return map;
    }
    
    private String getWhereSql(ProdAttrDictDO obj, List<Object> parameters) {
    	StringBuilder sql = new StringBuilder();
    	sql.append(" where 1=1 ");
    	
    	if (StringUtil.isNotBlank(obj.getKEY_TP())) {
    		sql.append(" and KEY_TP = ? ");
            parameters.add(obj.getKEY_TP());
        }
        if (StringUtil.isNotBlank(obj.getKEY_NO())) {
        	sql.append(" and KEY_NO like ? ");
            parameters.add("%"+obj.getKEY_NO()+"%");
        }
        if (StringUtil.isNotBlank(obj.getKEY_NAME())) {
        	sql.append(" and KEY_NAME like ? ");
            parameters.add("%"+obj.getKEY_NAME()+"%");
        }
    	
    	return sql.toString(); 
    }
}