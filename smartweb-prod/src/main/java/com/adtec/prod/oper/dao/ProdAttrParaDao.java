package com.adtec.prod.oper.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.adtec.prod.oper.entity.ProdAttrParaDO;
import com.adtec.framework.impl.share.caseStrategy.MBCCaseStrategy;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.common.dao.IBaseDao;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.common.util.DataUtil;
@Component
public class ProdAttrParaDao implements IBaseDao<ProdAttrParaDO>{
	 /**
     * 日志对象
     */
    protected final static Logger logger = LoggerFactory.getLogger(ProdAttrParaDao.class);
    /*表名称*/
    public static final String TABLE_NAME = "T_PIP_COMP_PARA";
    public static final String TABLE_KEY_TP = "T_PIP_KEY";
	
	 
	public int insert(ProdAttrParaDO objDO,IDBSession session) {
		  int rs = 0;
	       // IDBSession session = DBSessionFactory.getSession();
	 
		 try {
	            List<String> ignoreFields = objDO.getIgnoreFields();
	            rs = session.saveObject(TABLE_NAME, objDO, ignoreFields,new MBCCaseStrategy());
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
	 
	@Override
	public int update(ProdAttrParaDO objDO) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int delete(ProdAttrParaDO objDO,IDBSession session) {
		int rs = 0;
        //IDBSession session = DBSessionFactory.getSession();	
        try {
            String sql = "delete from "+TABLE_NAME+" where COMP_NO = ? ";
            rs = session.execute(sql, objDO.getCOMP_NO());
            if (rs == 0) {
                //throw new BaseException(SysErr.E_MESSAGE, "删除交易失败，影响记录数：" + rs);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error("删除交易异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "删除交易失败！");
        }
        return rs;
	}
	
	@Override
	public ProdAttrParaDO get(ProdAttrParaDO objDO) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 不分页多笔查询
	 */
	@Override
	public List<ProdAttrParaDO> list(ProdAttrParaDO objDO) {
		// TODO Auto-generated method stub
		 return list(objDO, 0, 0);
	}
	/**
	 * 分页多笔查询
	 */
	@Override
	public List<ProdAttrParaDO> list(ProdAttrParaDO objDO, int start, int limit) {
		logger.debug("ProdAttrParaDO=" + objDO.toString());
        logger.debug("start=" + start);
        logger.debug("limit=" + limit);
        List<ProdAttrParaDO> list = null;
        try {
            List<Object> parameters = Lists.newArrayList();
            StringBuilder sql = new StringBuilder();
            sql.append("select a.* ,b.ENTER_TP  from ").append(TABLE_NAME).append(" a LEFT JOIN ").append(TABLE_KEY_TP);
            sql.append(" b on a.KEY_NO = b.KEY_NO");
            sql.append(" where 1 = 1 ");
            if(!DataUtil.isNullStr(objDO.getCOMP_NO())){
            	sql.append("and comp_no = ?");
            	parameters.add(objDO.getCOMP_NO());
            }

            logger.debug("sql=" + sql.toString());
            IDBSession session = DBSessionFactory.getSession();
            if (limit == 0) {
                list = session.getObjectListByList(sql.toString(), ProdAttrParaDO.class, parameters, new MBCCaseStrategy());
            } else {
                list = session.getObjectListByListForPage(sql.toString(), ProdAttrParaDO.class, start, limit, parameters, new MBCCaseStrategy());
            }
        } catch (Exception e) {
            logger.error("列表查询异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "列表查询失败！");
        }
        return list;
	}
	
	@Override
	public List<ProdAttrParaDO> list(int start, int limit, Object... param) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
     * 根据数据对象产生对应的数据查询总记录
     *
     * @param obj 数据对象DO
     * @return total
     */
    public int getTotal(ProdAttrParaDO obj) {
        logger.debug("ProdAttrParaDO=" + obj);
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME);
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        logger.debug("countSql=" + countSql);
        IDBSession session = DBSessionFactory.getSession();
        int total = 0;
        try {
            total = session.account(countSql);
        } catch (Exception e) {
            logger.error("总记录数查询sys_permission_weight异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "总记录数查询sys_permission_weight失败！");
        }
        return total;
    }
    
    
    
    /**
     * 根据 字段 排序方式 无条件查询所有数据
     * @return
     */
    public List<ProdAttrParaDO> getListSerDesc(String colmn,String sort){
    	return getListSerDesc(colmn,sort,"");
    }
    
    /**
     * 根据 字段 排序方式  条件查询所有数据
     * @return
     */
    public List<ProdAttrParaDO> getListSerDesc(String colmn,String sort,String term){
    	 StringBuilder sql = new StringBuilder();
         sql.append("select * from ").append(TABLE_NAME);
         sql.append(" where 1 = 1 ");
         if(!"".equals(term)){
        	 sql.append(term);
         }
         sql.append(" order by "+colmn+" "+sort);
         IDBSession session = DBSessionFactory.getSession();
         List<ProdAttrParaDO> list= new ArrayList<ProdAttrParaDO>();
         try {
        	list = session.getObjectList(sql.toString(), ProdAttrParaDO.class,new MBCCaseStrategy());
         }catch(Exception e){
        	 throw new BaseException(SysErr.E_MESSAGE, "获取"+colmn+"失败！");
         }
         return list;
    }

	@Override
	public int insert(ProdAttrParaDO objDO) {
		  int rs = 0;
	      IDBSession session = DBSessionFactory.getSession();
	 
		 try {
	            List<String> ignoreFields = objDO.getIgnoreFields();
	            rs = session.saveObject(TABLE_NAME, objDO, ignoreFields,new MBCCaseStrategy());
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

	@Override
	public int delete(ProdAttrParaDO objDO) {

		int rs = 0;
        IDBSession session = DBSessionFactory.getSession();	
        try {
            String sql = "delete from "+TABLE_NAME+" where COMP_NO = ? ";
            rs = session.execute(sql, objDO.getCOMP_NO());
            if (rs == 0) {
                //throw new BaseException(SysErr.E_MESSAGE, "删除交易失败，影响记录数：" + rs);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error("删除交易异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "删除交易失败！");
        }
        return rs;
	
	}

}
