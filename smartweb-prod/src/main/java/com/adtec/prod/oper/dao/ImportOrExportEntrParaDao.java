package com.adtec.prod.oper.dao;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.adtec.prod.oper.entity.BusiDO;
import com.adtec.framework.impl.share.caseStrategy.MBCCaseStrategy;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.common.dao.IBaseDao;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;

@Component
public class ImportOrExportEntrParaDao  implements IBaseDao<BusiDO>{
	 /**
     * 日志对象
     */
    protected final static Logger logger = LoggerFactory.getLogger(ImportOrExportEntrParaDao.class);
    /*表名称*/
    public static final String TABLE_NAME = "T_PIP_BUSI";

	@Override
	public int insert(BusiDO objDO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(BusiDO objDO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(BusiDO objDO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BusiDO get(BusiDO objDO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BusiDO> list(BusiDO objDO) {
		return list(objDO,0,0);
	}

	@Override
	public List<BusiDO> list(BusiDO obj, int start, int limit) {
		logger.debug("ProdAttrDO=" + obj.toString());
        logger.debug("start=" + start);
        logger.debug("limit=" + limit);
        List<BusiDO> list;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select * from ").append(TABLE_NAME);
            sql.append(" where 1 = 1 ");
            logger.debug("sql=" + sql.toString());
            IDBSession session = DBSessionFactory.getSession();
            if (limit == 0) {
                list = session.getObjectList(sql.toString(), BusiDO.class,new MBCCaseStrategy());
            } else {
                list = session.getObjectListForPage(sql.toString(), BusiDO.class, start, limit,new MBCCaseStrategy());
            }
        }catch (Exception e) {
            logger.error("列表查询异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "列表查询失败！");
        }
        return list;
	}

	@Override
	public List<BusiDO> list(int start, int limit, Object... param) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 批量导入
	 * @param sqlList
	 */
	public void importData(List<String> sqlList) {
		IDBSession session = DBSessionFactory.getSession();
        try {
            session.executeBatch(sqlList);
        } catch (SQLException e) {
            logger.error("导入数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "导入数据失败！");
        }	
	}


}
