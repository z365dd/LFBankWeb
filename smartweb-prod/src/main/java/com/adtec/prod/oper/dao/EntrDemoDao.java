package com.adtec.prod.oper.dao;

import com.adtec.prod.oper.entity.EntrDemoDO;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.common.dao.IBaseDao;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class EntrDemoDao implements IBaseDao<EntrDemoDO> {
    /**
     * 日志对象
     */
    protected final static Logger logger = LoggerFactory.getLogger(EntrDemoDao.class);
    /*表名称*/
    public static final String TABLE_NAME = "T_PIP_ENTR";

    /**
     * 获取单条数据
     *
     * @return
     */
    public EntrDemoDO get(String entrNo) {
        EntrDemoDO obj = new EntrDemoDO();
        obj.setEntrNo(entrNo);
        return get(obj);
    }

    /**
     * 获取单条数据
     *
     * @param obj
     * @return
     */
    @Override
    public EntrDemoDO get(EntrDemoDO obj) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where entr_no = ?");
        EntrDemoDO rs = null;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObject(sql.toString(), EntrDemoDO.class, obj.getEntrNo());
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
    public int insert(EntrDemoDO obj) {
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            List<String> ignoreFields = obj.getIgnoreFields();
            ignoreFields.add("delFlg");
            ignoreFields.add("rmrk");
            ignoreFields.add("id");
            rs = session.saveObject(TABLE_NAME, obj, ignoreFields);
        } catch (Exception e) {
            logger.error("数据库新增异常：" + e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, e.getMessage());
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
    public int update(EntrDemoDO obj) {
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            List<String> ignoreFields = obj.getIgnoreFields();
            ignoreFields.add("delFlg");
            ignoreFields.add("rmrk");
            ignoreFields.add("id");
            ignoreFields.add("crtr");
            ignoreFields.add("crtTime");
            List<String> matchFields = obj.getMatchFields();
            matchFields.remove(0);
            matchFields.add("entrNo");
            rs = session.updateObject(TABLE_NAME, obj, matchFields, ignoreFields);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("修改权限维度异常：" + e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
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
    public int delete(String id) {
        EntrDemoDO obj = new EntrDemoDO();
        obj.setId(id);
        return delete(obj);
    }

    /**
     * 删除数据（一般为逻辑删除，更新del_flag字段为1）
     *
     * @param obj
     * @return
     */
    @Override
    public int delete(EntrDemoDO obj) {
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            String sql = "delete from "+ TABLE_NAME +" where ENTR_NO = ?";
            rs = session.execute(sql, obj.getEntrNo());
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
    public List<EntrDemoDO> list(EntrDemoDO obj) {
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
    public List<EntrDemoDO> list(EntrDemoDO obj, int start, int limit) {
        logger.debug("EntrDemoDO=" + obj.toString());
        logger.debug("start=" + start);
        logger.debug("limit=" + limit);
        List<EntrDemoDO> list = null;
        try {
            List<Object> parameters = Lists.newArrayList();
            StringBuilder sql = new StringBuilder();
            sql.append("select * from ").append(TABLE_NAME).append(" where 1 = 1 ");
            if(obj.getEntrNo()!=null &&!"".equals(obj.getEntrNo())){
            	sql.append(" and entr_no =?");
            	parameters.add(obj.getEntrNo());
            }
            
            if(obj.getOpenStat()!=null &&!"".equals(obj.getOpenStat())){
            	sql.append(" and open_stat =?");
            	parameters.add(obj.getOpenStat());
            }

            if(obj.getEntrNature()!=null &&!"".equals(obj.getEntrNature())){
                sql.append(" and entr_nature =?");
                parameters.add(obj.getEntrNature());
            }

            if(obj.getEntrName()!=null && !"".equals(obj.getEntrName())){
            	sql.append(" and entr_name like ? ");
            	parameters.add("%"+obj.getEntrName()+"%");
            }
            sql.append(" order by mod_time desc");
            logger.debug("sql=" + sql.toString());
            IDBSession session = DBSessionFactory.getSession();
            if (limit == 0) {
                list = session.getObjectListByList(sql.toString(), EntrDemoDO.class, parameters);
            } else {
                list = session.getObjectListByListForPage(sql.toString(), EntrDemoDO.class, start, limit, parameters);
            }
        } catch (Exception e) {
            logger.error("列表查询异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "列表查询失败！");
        }
        return list;
    }
    
    
    public List<EntrDemoDO> qryName(EntrDemoDO obj) {
    	logger.debug("EntrDemoDO=" + obj.toString());
        List<EntrDemoDO> list = null;
        try {
            List<Object> parameters = Lists.newArrayList();
            StringBuilder sql = new StringBuilder();
            sql.append("select * from ").append(TABLE_NAME).append(" where 1 = 1 ");
            if(obj.getEntrName()!=null && !"".equals(obj.getEntrName())){
            	sql.append(" and entr_name = ?");
            	parameters.add(obj.getEntrName());
            }
            sql.append(" order by mod_time desc");
            logger.debug("sql=" + sql.toString());
            IDBSession session = DBSessionFactory.getSession();
            list= session.getObjectListByList(sql.toString(), EntrDemoDO.class, parameters);
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
    public List<EntrDemoDO> list(int start, int limit, Object... param) {
        return null;
    }

    /**
     * 根据数据对象产生对应的数据查询总记录
     *
     * @param obj 数据对象DO
     * @return total
     */
    public int getTotal(EntrDemoDO obj) {
        logger.debug("EntrDemoDO=" + obj);
        List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where 1 = 1 ");
        
        if(obj.getEntrNo()!=null &&!"".equals(obj.getEntrNo())){
        	sql.append(" and entr_no =?");
        	parameters.add(obj.getEntrNo());
        }
        
        if(obj.getOpenStat()!=null &&!"".equals(obj.getOpenStat())){
        	sql.append(" and open_stat =?");
        	parameters.add(obj.getOpenStat());
        }

        if(obj.getEntrNature()!=null &&!"".equals(obj.getEntrNature())){
            sql.append(" and entr_nature =?");
            parameters.add(obj.getEntrNature());
        }

        if(obj.getEntrName()!=null && !"".equals(obj.getEntrName())){
        	sql.append(" and entr_name like ? ");
        	parameters.add("%"+obj.getEntrName()+"%");
        }
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        logger.debug("countSql=" + countSql);
        IDBSession session = DBSessionFactory.getSession();
        int total = 0;
        try {
            total = session.accountByList(countSql, parameters);
        } catch (Exception e) {
            logger.error("总记录数查询异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "总记录数查询异常：" + e.getMessage());
        }
        return total;
    }
    
    /**
     * 修改状态
     * @param obj
     * @return
     */
    public int updateStat(EntrDemoDO obj){
    	int rs = 0;
    	IDBSession session = DBSessionFactory.getSession();
        try {
            List<String> ignoreFields = obj.getIgnoreFields();
            List<String> matchFields = obj.getMatchFields();
            //rs = session.updateObject(TABLE_NAME, obj, matchFields, ignoreFields);
            rs = session.execute("update "+TABLE_NAME+" set open_stat = ? where entr_no = ?", obj.getOpenStat(),obj.getEntrNo());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("修改权限维度异常：" + e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "修改权限维度失败！");
        }
    	return rs;
    }

	public List<EntrDemoDO> getNewEntrNo(String dateStr) {
		// TODO Auto-generated method stub
    	IDBSession session = DBSessionFactory.getSession();
    	StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME);
        sql.append(" where ENTR_NO like ? ");
        sql.append(" order by ENTR_NO desc ");
        List<EntrDemoDO> list = new ArrayList<EntrDemoDO>();
        try {
        	list = session.getObjectList(sql.toString(), EntrDemoDO.class, dateStr+"%");
         }catch(Exception e){
        	 throw new BaseException(SysErr.E_MESSAGE, "获取ENTR_NO失败！");
         }
		return list;
	}


}