package com.adtec.prod.oper.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.adtec.prod.oper.entity.ProdAttrDO;
import com.adtec.framework.impl.share.caseStrategy.MBCCaseStrategy;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.common.dao.IBaseDao;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.common.util.DataUtil;

@Component
public class ProdAttrDao implements IBaseDao<ProdAttrDO> {
    /**
     * 日志对象
     */
    protected final static Logger logger = LoggerFactory.getLogger(ProdAttrDao.class);
    /*表名称*/
    public static final String TABLE_NAME = "T_PIP_COMP";//组件

    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    public ProdAttrDO get(String id) {
        ProdAttrDO obj = new ProdAttrDO();
        obj.setCOMP_NO(id);
        return get(obj);
    }

    /**
     * 获取单条数据
     *
     * @param obj
     * @return
     */
    @Override
    public ProdAttrDO get(ProdAttrDO obj) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where COMP_NO=?");
        ProdAttrDO rs = null;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObject(sql.toString(), ProdAttrDO.class, new MBCCaseStrategy(), obj.getCOMP_NO());
        } catch (Exception e) {
            logger.error("获取权限维度异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "获取权限维度失败！");
        }
        return rs;
    }

    public ProdAttrDO getByName(ProdAttrDO obj) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where COMP_NAME=?");
        ProdAttrDO rs = null;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObject(sql.toString(), ProdAttrDO.class, new MBCCaseStrategy(), obj.getCOMP_NAME());
        } catch (Exception e) {
            logger.error("获取权限维度异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "获取权限维度失败！");
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
    public int insert(ProdAttrDO obj) {
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            List<String> ignoreFields = obj.getIgnoreFields();
            rs = session.saveObject(TABLE_NAME, obj, ignoreFields,new MBCCaseStrategy());
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("新增权限维度交易异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "新增权限维度交易失败！");
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
    public int update(ProdAttrDO obj) {
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            List<String> ignoreFields = obj.getIgnoreFields();
            List<String> matchFields = obj.getMatchFields();
            rs = session.updateObject(TABLE_NAME, obj, matchFields, ignoreFields,new MBCCaseStrategy());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("修改权限维度异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "修改权限维度失败！");
        }
        return rs;
    }

    /**
     * 根据主键id删除数据（一般为逻辑删除，更新del_flag字段为1）
     *
     * @param id
     * @return
     * @see public int delete(T entity)
     */
 /*   public int delete(String id) {
        ProdAttrDO obj = new ProdAttrDO();
        obj.setId(id);
        return delete(obj);
    }*/

    /**
     * 删除数据（直接删除）
     *
     * @param obj
     * @return
     */
    @Override
    public int delete(ProdAttrDO obj) {
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            String sql = "delete from "+TABLE_NAME+" where COMP_NO = ? ";
            rs = session.execute(sql, obj.getCOMP_NO());
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
    public List<ProdAttrDO> list(ProdAttrDO obj) {
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
    public List<ProdAttrDO> list(ProdAttrDO obj, int start, int limit) {
        logger.debug("ProdAttrDO=" + obj.toString());
        logger.debug("start=" + start);
        logger.debug("limit=" + limit);
        List<ProdAttrDO> list = null;
        try {
            List<Object> parameters = Lists.newArrayList();
            StringBuilder sql = new StringBuilder();
            sql.append("select * from ").append(TABLE_NAME);
            sql.append(" where 1 = 1 ");
            if(!DataUtil.isNullStr(obj.getOPEN_STAT())){
            	sql.append("and OPEN_STAT = ?");
            	parameters.add(obj.getOPEN_STAT());
            }
            if(!DataUtil.isNullStr(obj.getPROD_COMP_TP())){
            	sql.append("and PROD_COMP_TP = ?");
            	parameters.add(obj.getPROD_COMP_TP());
            }
            if(!DataUtil.isNullStr(obj.getCOMP_NO())){
            	sql.append("and comp_no = ?");
            	parameters.add(obj.getCOMP_NO());
            }
            sql.append(" order by comp_no");
            logger.debug("sql=" + sql.toString());
            IDBSession session = DBSessionFactory.getSession();
            if (limit == 0) {
                list = session.getObjectListByList(sql.toString(), ProdAttrDO.class, parameters, new MBCCaseStrategy());
            } else {
                list = session.getObjectListByListForPage(sql.toString(), ProdAttrDO.class, start, limit, parameters, new MBCCaseStrategy());
            }
        } catch (Exception e) {
            logger.error("列表查询异常：" + e.getMessage());
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
    public List<ProdAttrDO> list(int start, int limit, Object... param) {
        return null;
    }

    /**
     * 根据数据对象产生对应的数据查询总记录
     *
     * @param obj 数据对象DO
     * @return total
     */
    public int getTotal(ProdAttrDO obj) {
        List<Object> parameters = Lists.newArrayList();
    	StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME);
        sql.append(" where 1 = 1 ");
        if(!DataUtil.isNullStr(obj.getOPEN_STAT())){
        	sql.append("and OPEN_STAT = ?");
        	parameters.add(obj.getOPEN_STAT());
        }
        if(!DataUtil.isNullStr(obj.getPROD_COMP_TP())){
        	sql.append("and PROD_COMP_TP = ?");
        	parameters.add(obj.getPROD_COMP_TP());
        }
        if(!DataUtil.isNullStr(obj.getCOMP_NO())){
        	sql.append("and comp_no = ?");
        	parameters.add(obj.getCOMP_NO());
        }
        //sql.append(" order by update_date desc");
        logger.debug("sql=" + sql.toString());

        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        logger.debug("countSql=" + countSql);
        IDBSession session = DBSessionFactory.getSession();
        int total = 0;
        try {
            total = session.accountByList(countSql, parameters);
        } catch (Exception e) {
            logger.error("总记录数查询sys_permission_weight异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "总记录数查询失败！");
        }
        return total;
    }

    /**
     * 修改状态
     * @param prodAttrDO
     */
	public void updateStat(ProdAttrDO prodAttrDO) {
		 logger.debug("ProdAttrDO=" + prodAttrDO);
		 StringBuilder sql = new StringBuilder();
		 sql.append("update ").append(TABLE_NAME).append(" set OPEN_STAT = ?  where COMP_NO = ? ");
		 logger.debug("sql=" + sql);
		 IDBSession session = DBSessionFactory.getSession();
		 try {
		 session.execute(sql.toString(), prodAttrDO.getOPEN_STAT(),prodAttrDO.getCOMP_NO());
		 } catch(Exception e){
			 logger.error("修改开通状态异常" + e.getMessage()); 
			 throw new BaseException(SysErr.E_MESSAGE, "修改开通状态异常！");
		 }
	}

    public ResultSet getSelectRelatSysNo() {
        IDBSession session = DBSessionFactory.getSession();
        try {
            return session.getResultSet("select * from T_PARA_RELAT_SYS");
        } catch(Exception e){
            logger.error("查询异常", e);
            throw new BaseException(SysErr.E_MESSAGE, "异常！");
        }
//        finally {
//            try {
//                DBSessionFactory.closeSession(session);
//            } catch (SQLException e) {
//                logger.error("关闭删除数据异常：" + e.getMessage());
//            }
//        }
    }
}