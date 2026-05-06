package com.adtec.comp.ctrl.oper.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.adtec.comp.ctrl.oper.entity.FCtrlRelatSysDO;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.common.dao.IBaseDao;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;

@Component
public class FCtrlRelatSysDao  implements IBaseDao<FCtrlRelatSysDO>{
	 /**
     * 日志对象
     */
    protected final static Logger logger = LoggerFactory.getLogger(FCtrlRelatSysDao.class);
    /*表名称*/
    public static final String TABLE_NAME = "T_PARA_RELAT_SYS";

    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    public FCtrlRelatSysDO get(String relatSys) {
        FCtrlRelatSysDO obj = new FCtrlRelatSysDO();
        obj.setRelatSys(relatSys);
        return get(obj);
    }

    /**
     * 获取单条数据
     *
     * @param obj
     * @return
     */
    @Override
    public FCtrlRelatSysDO get(FCtrlRelatSysDO obj) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where relat_sys = ?");
        FCtrlRelatSysDO rs = null;
        IDBSession session = DBSessionFactory.getSession("fctrl");
        try {
            rs = session.getObject(sql.toString(), FCtrlRelatSysDO.class, obj.getRelatSys());
        } catch (Exception e) {
            logger.error("获取权限维度异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "获取权限维度失败！");
        }
        return rs;
    }
    
    /**
     * 获取单条数据
     *
     * @param obj
     * @return
     */
   
    public FCtrlRelatSysDO getSingleData(FCtrlRelatSysDO obj, String relatSys) {
    	StringBuilder sql = new StringBuilder();
    	sql.append("select * from ").append(TABLE_NAME).append(" where 1 = 1 ").append(" and relat_sys = '"+relatSys+"'");
    	FCtrlRelatSysDO rs = null;
    	IDBSession session = DBSessionFactory.getSession("fctrl");
    	try {
    		rs = session.getObject(sql.toString(), FCtrlRelatSysDO.class);
    	} catch (Exception e) {
    		logger.error("获取权限维度异常：" + e.getMessage());
    		throw new BaseException(SysErr.E_MESSAGE, "获取权限维度失败！");
    	}
    	return rs;
    }
    
    /**
     * 根据关联系统号查询记录
     *
     * @param obj 数据对象DO
     * @return list
     */
    /*public List<FCtrlRelatSysDO> getSingleData(FCtrlRelatSysDO obj, String relatSys) {
    	logger.debug("FCtrlRelatSysDO=" + obj.toString());
        List<FCtrlRelatSysDO> list = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select * from ").append(TABLE_NAME).append(" where 1 = 1 ");
            if(obj.getRelatSys()!=null && !"".equals(obj.getRelatSys())){
            	sql.append(" and relat_sys = '"+relatSys+"'");
            }
            logger.debug("sql=" + sql.toString());
            IDBSession session = DBSessionFactory.getSession("fctrl");
            list= session.getObjectList(sql.toString(), FCtrlRelatSysDO.class);
        } catch (Exception e) {
            logger.error("列表查询异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "列表查询失败！");
        }
        return list;
    }*/
   
    /**
     * 插入数据
     *
     * @param obj
     * @return
     */
    @Override
    public int insert(FCtrlRelatSysDO obj) {
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession("fctrl");
        try {
            List<String> ignoreFields = obj.getIgnoreFields();
            ignoreFields.add("delFlag");
            ignoreFields.add("createBy");
            ignoreFields.add("createDate");
            ignoreFields.add("updateBy");
            ignoreFields.add("updateDate");
            ignoreFields.add("remarks");
            ignoreFields.add("id");
            rs = session.saveObject(TABLE_NAME, obj, ignoreFields);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("新增权限维度交易异常：" + e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
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
    public int update(FCtrlRelatSysDO obj) {
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession("fctrl");
        try {
            List<String> ignoreFields = obj.getIgnoreFields();
            ignoreFields.add("delFlag");
            ignoreFields.add("createBy");
            ignoreFields.add("createDate");
            ignoreFields.add("updateBy");
            ignoreFields.add("updateDate");
            ignoreFields.add("remarks");
            ignoreFields.add("id");
            List<String> matchFields = obj.getMatchFields();
            matchFields.remove(0);
            matchFields.add("relatSys");
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
        FCtrlRelatSysDO obj = new FCtrlRelatSysDO();
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
    public int delete(FCtrlRelatSysDO obj) {
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession("fctrl");
        try {
            String sql = "delete from "+ TABLE_NAME +" where RELAT_SYS = ?";
            rs = session.execute(sql, obj.getRelatSys());
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
    public List<FCtrlRelatSysDO> list(FCtrlRelatSysDO obj) {
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
    public List<FCtrlRelatSysDO> list(FCtrlRelatSysDO obj, int start, int limit) {
        logger.debug("FCtrlRelatSysDO=" + obj.toString());
        logger.debug("start=" + start);
        logger.debug("limit=" + limit);
        List<FCtrlRelatSysDO> list = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select * from ").append(TABLE_NAME).append(" where 1 = 1 ");
            if(obj.getRelatSys()!=null &&!"".equals(obj.getRelatSys())){
            	sql.append(" and relat_sys = '"+obj.getRelatSys()+"' ");
            }
            if(obj.getOpenStat()!=null &&!"".equals(obj.getOpenStat())){
            	sql.append(" and open_stat ="+ obj.getOpenStat());
            }
            if(obj.getSysTp()!=null &&!"".equals(obj.getSysTp())){
            	sql.append(" and sys_tp ="+ obj.getSysTp()); 
            }
           
            logger.debug("sql=" + sql.toString());
            IDBSession session = DBSessionFactory.getSession("fctrl");
          
            if (limit == 0) {
                list = session.getObjectList(sql.toString(), FCtrlRelatSysDO.class);
            } else {
                list = session.getObjectListForPage(sql.toString(), FCtrlRelatSysDO.class, start, limit);
            }
        } catch (Exception e) {
            logger.error("列表查询异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "列表查询失败！");
        }
        return list;
    }
    

    /**
     * 根据关联系统号查询记录
     *
     * @param obj 数据对象DO
     * @return list
     */
    public List<FCtrlRelatSysDO> qryRelatSys(FCtrlRelatSysDO obj) {
    	logger.debug("FCtrlRelatSysDO=" + obj.toString());
        List<FCtrlRelatSysDO> list = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select * from ").append(TABLE_NAME).append(" where 1 = 1 ");
            if(obj.getRelatSys()!=null && !"".equals(obj.getRelatSys())){
            	sql.append(" and relat_sys = '"+obj.getRelatSys()+"'");
            	
            }
            logger.debug("sql=" + sql.toString());
            IDBSession session = DBSessionFactory.getSession("fctrl");
            list= session.getObjectList(sql.toString(), FCtrlRelatSysDO.class);
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
    public List<FCtrlRelatSysDO> list(int start, int limit, Object... param) {
        return null;
    }

    /**
     * 根据数据对象产生对应的数据查询总记录
     *
     * @param obj 数据对象DO
     * @return total
     */
    public int getTotal(FCtrlRelatSysDO obj) {
        logger.debug("FCtrlRelatSysDO=" + obj);
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where 1 = 1 ");
        
        if(obj.getRelatSys()!=null &&!"".equals(obj.getRelatSys())){
        	/*sql.append(" and relat_sys ="+ obj.getRelatSys());*/
        	sql.append(" and relat_sys = '"+obj.getRelatSys()+"' ");
        }
        
        if(obj.getSysTp()!=null &&!"".equals(obj.getSysTp())){
        	sql.append(" and sys_tp ="+ obj.getSysTp());
        }
        
       /* if(obj.getEntrName()!=null && !"".equals(obj.getEntrName())){
        	sql.append(" and entr_name like '%"+obj.getEntrName()+"%' ");
        }*/
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        logger.debug("countSql=" + countSql);
        IDBSession session = DBSessionFactory.getSession("fctrl");
        int total = 0;
        try {
            total = session.account(countSql);
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
    public int updateStat(FCtrlRelatSysDO obj){
    	int rs = 0;
    	IDBSession session = DBSessionFactory.getSession("fctrl");
        try {
            List<String> ignoreFields = obj.getIgnoreFields();
            List<String> matchFields = obj.getMatchFields();
            //rs = session.updateObject(TABLE_NAME, obj, matchFields, ignoreFields);
            rs = session.execute("update "+TABLE_NAME+" set sys_tp = ? where relat_sys = ?", obj.getSysTp(),obj.getRelatSys());
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

	public List<FCtrlRelatSysDO> getNewRelatSys(FCtrlRelatSysDO entrDemoDO) {
		// TODO Auto-generated method stub
    	IDBSession session = DBSessionFactory.getSession("fctrl");
    	StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME);
        sql.append(" where 1 = 1 ");
        sql.append(" order by "+"RELAT_SYS desc ");
        List<FCtrlRelatSysDO> list = new ArrayList<FCtrlRelatSysDO>();
        try {
        	list = session.getObjectList(sql.toString(), FCtrlRelatSysDO.class);
         }catch(Exception e){
        	 throw new BaseException(SysErr.E_MESSAGE, "获取RELAT_SYS失败！");
         }
		return list;
	}
}
