package com.adtec.sys.modules.sys.dao;

/**
 * 系统名称: SmartWeb平台
 * 模块名称:文档类型表数据库操作类
 * 类  名  称: FileDao.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl
 * 开发时间: 2018-05-02 14:56:25
 * 系统版本: V1.0.0
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================ *
 * ========     ======  ============================================
 */


import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.common.dao.IBaseDao;
import com.adtec.sys.modules.sys.entity.FileDO;
import com.google.common.collect.Lists;


@Component
public class FileDao implements IBaseDao<FileDO>{

    /*日志对象*/
    protected final static Logger logger = LoggerFactory.getLogger(FileDao.class);
    /*表名称*/
    public static final String TABLE_NAME = "T_SYS_FILE";


    @Override
    public int insert(FileDO fileDO) {
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.saveObject(TABLE_NAME, fileDO, fileDO.getIgnoreFields());
        } catch (SQLException e) {
            logger.error("新增文档类型表异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "新增文档类型失败！");
        }
        return rs;
    }
    public int insertByNewSession(FileDO fileDO) {
    	int rs = 0;
    	IDBSession session = DBSessionFactory.getNewSession();
    	try {
    		rs = session.saveObject(TABLE_NAME, fileDO, fileDO.getIgnoreFields());
    	} catch (SQLException e) {
    		logger.error("新增文档类型表异常："+ e.getMessage());
    		try {
    			session.rollback();
    		} catch (SQLException e1) {
    			e1.printStackTrace();
    		}
    		throw new BaseException(SysErr.E_MESSAGE, "新增文档类型失败！");
    	}finally {
        	try {
				DBSessionFactory.closeSession(session);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    	return rs;
    }


    @Override
    public int update(FileDO fileDO) {
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.updateObject(TABLE_NAME, fileDO, fileDO.getMatchFields(), fileDO.getIgnoreFields());
        } catch (SQLException e) {
            logger.error("修改文档类型表异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "修改文档类型表失败！");
        }
        return rs;
    }
    public int updateForHandler(FileDO fileDO) {
    	StringBuilder sql = new StringBuilder();
    	List<Object> paras = Lists.newArrayList();
    	sql.append("update ")
    	   .append(TABLE_NAME)
    	   .append(" set prev_path=?")
    	   .append(",save_path=?")
    	   .append(" where id=?");
    	paras.add(fileDO.getPrevPath());
    	paras.add(fileDO.getSavePath());
    	paras.add(fileDO.getId());
        int rs = 0;
        IDBSession session = DBSessionFactory.getNewSession();
        try {
            rs = session.executeByList(sql.toString(),paras);
        } catch (SQLException e) {
            logger.error("修改文档类型表异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "修改文档类型表失败！");
        } finally {
			try {
				DBSessionFactory.closeSession(session);
			} catch (SQLException e) {
				logger.error("关闭session异常：" + e.getMessage());
			}
		}
        return rs;
    }

    @Override
    public int delete(FileDO fileDO) {
        StringBuilder sql = new StringBuilder();
        List<Object> paras = Lists.newArrayList();
        sql.append("delete from ").append(TABLE_NAME).append(" where id=?");
        paras.add(fileDO.getId());
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.executeByList(sql.toString(),paras);
        } catch (SQLException e) {
            logger.error("删除文档类型表异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "删除文档类型表失败！");
        }
        return rs;
    }


    @Override
    public FileDO get(FileDO fileDO) {
        StringBuilder sql = new StringBuilder();
        List<Object> paras = Lists.newArrayList();
        sql.append("select * from ").append(TABLE_NAME).append(" where id=?");
        paras.add(fileDO.getId());
        FileDO rs = new FileDO();
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObjectByList(sql.toString(), FileDO.class,paras);
        } catch (SQLException e) {
            logger.error("查询文件信息异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "查询文件信息失败！");
        }
        return rs;
    }


    @Override
    public List<FileDO> list(FileDO fileDO) {
        return list(fileDO, 0, 0);
    }

   
    @Override
    public List<FileDO> list(FileDO fileDO, int start, int limit) {
        logger.debug("fileDO=" + fileDO);
        logger.debug("start=" + start);
        logger.debug("limit=" + limit);
        StringBuilder sql = new StringBuilder();
        List<Object> paras = Lists.newArrayList();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(fileDO,paras));
        sql.append(" order by crt_time desc");
        logger.debug("sql=" + sql.toString());
        IDBSession session = DBSessionFactory.getSession();
        List<FileDO> list = null;
        try {
            if (limit == 0) {
                list = session.getObjectListByList(sql.toString(), FileDO.class,paras);
            } else {
                list = session.getObjectListByListForPage(sql.toString(), FileDO.class, start, limit,paras);
            }
        } catch (SQLException e) {
            logger.error("列表查询文档类型表异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "列表查询文档类型表失败！");
        }
        return list;
    }
    
    public int getTotal(FileDO fileDO) {
        logger.debug("fileDO=" + fileDO);
        StringBuilder sql = new StringBuilder();
        List<Object> paras = Lists.newArrayList();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(fileDO,paras));
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        logger.debug("countSql=" + countSql);
        IDBSession session = DBSessionFactory.getSession();
        int total = 0;
        try {
            total = session.accountByList(countSql,paras);
        } catch (SQLException e) {
            logger.error("总记录数查询异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "总记录数查询失败！");
        }
        return total;
    }


    @Override
    public List<FileDO> list(int start, int limit, Object... param) {
        return null;
    }


    public int getTotal(FileDO fileDO,String resultSql) {
        logger.debug("fileDO=" + fileDO);
        StringBuilder sql = new StringBuilder();
        sql.append(resultSql);
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        logger.debug("countSql=" + countSql);
        IDBSession session = DBSessionFactory.getSession();
        int total = 0;
        try {
            total = session.account(countSql);
        } catch (SQLException e) {
            logger.error("总记录数查询异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "总记录数查询失败！");
        }
        return total;
    }


    /*根据传入的数据对象，对应属性值不空时拼接查询条件*/
    private String getWhereSql(FileDO fileDO,List<Object> paras) {
        StringBuffer whereSql = new StringBuffer(" where 1=1 ");
        if(!DataUtil.isNullStr(fileDO.getId())){
            /*添加查询条件：ID*/
            whereSql.append(" and id=?");
            paras.add(fileDO.getId());
        }
        if(!DataUtil.isNullStr(fileDO.getFileName())){
            /*添加查询条件：文件名*/
            whereSql.append(" and file_name=?");
            paras.add(fileDO.getFileName());
        }
        if(!DataUtil.isNullStr(fileDO.getSaveName())){
            /*添加查询条件：保存名*/
            whereSql.append(" and save_name=?");
            paras.add(fileDO.getSaveName());
        }
        if(!DataUtil.isNullStr(fileDO.getFileTp())){
            /*添加查询条件：文件类型*/
            whereSql.append(" and file_tp=?");
            paras.add(fileDO.getFileTp());
        }
        if(!DataUtil.isNullStr(fileDO.getSavePath())){
            /*添加查询条件：保存路径*/
            whereSql.append(" and save_path=?");
            paras.add(fileDO.getSavePath());
        }
        if(!DataUtil.isNullStr(fileDO.getPrevPath())){
            /*添加查询条件：预览路径*/
            whereSql.append(" and prev_path=?");
            paras.add(fileDO.getPrevPath());
        }
        /*if(!DataUtil.isNullStr("" + fileDO.getDownloadNum())){
            //添加查询条件：下载次数
            whereSql.append(" and download_num=?");
            paras.add(fileDO.getDownloadNum());
        }*/
        if(!DataUtil.isNullStr(fileDO.getQuoteFlg())){
            /*添加查询条件：是否引用*/
            whereSql.append(" and quote_flg=?");
            paras.add(fileDO.getQuoteFlg());
        }
        if(!DataUtil.isNullStr(fileDO.getCrtr())){
            /*添加查询条件：创建者*/
            whereSql.append(" and crtr=?");
            paras.add(fileDO.getCrtr());
        }
        if(!DataUtil.isNullStr(fileDO.getCrtTime())){
            /*添加查询条件：创建时间*/
            whereSql.append(" and crt_time=?");
            paras.add(fileDO.getCrtTime());
        }
        if(!DataUtil.isNullStr(fileDO.getUptr())){
            /*添加查询条件：更新者*/
            whereSql.append(" and uptr=?");
            paras.add(fileDO.getUptr());
        }
        if(!DataUtil.isNullStr(fileDO.getUptTime())){
            /*添加查询条件：更新时间*/
            whereSql.append(" and upt_time=?");
            paras.add(fileDO.getUptTime());
        }
        if(!DataUtil.isNullStr(fileDO.getRmrk())){
            /*添加查询条件：备注信息*/
            whereSql.append(" and rmrk=?");
            paras.add(fileDO.getRmrk());
        }
        if(!DataUtil.isNullStr(fileDO.getDac())){
            /*添加查询条件：dac校验*/
            whereSql.append(" and dac=?");
            paras.add(fileDO.getDac());
        }
        return whereSql.toString();
    }
    
    /**
     * 判断文件是否存在
     * @param fileDO
     * @return 
     */
    public int isExist(FileDO fileDO){
    	 logger.debug("fileDO=" + fileDO);
         StringBuilder sql = new StringBuilder();
         List<Object> paras = Lists.newArrayList();
         sql.append("select * from ");
 		 sql.append(TABLE_NAME).append(getWhereSql(fileDO,paras));
         String countSql = "select count(1) from (" + sql.toString() + ") ct";
         logger.debug("countSql=" + countSql);
         IDBSession session = DBSessionFactory.getSession();
         int total = 0;
         try {
             total = session.accountByList(countSql,paras);
         } catch (Exception e) {
             logger.error("查询是否存在同名文件时，查询文件表异常："+ e.getMessage());
             throw new BaseException(SysErr.E_MESSAGE, "查询是否存在同名文件时，查询文件表异常！");
         }
         return total;
    }

}
