package com.adtec.comp.sign.dao;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.adtec.comp.sign.entity.FSignTParaChnlDO;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.common.dao.IBaseDao;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;

@Component
public class FSignTParaChnlDao implements IBaseDao<FSignTParaChnlDO>{
	 /**
     * 日志对象
     */
    protected final static Logger logger = LoggerFactory.getLogger(FSignTParaChnlDao.class);
    /*表名称*/
    public static final String TABLE_NAME = "T_PARA_CHNL";
    
 
    
	 /**
     * 数据库多笔查询，不分页
     *
     * @param obj 数据对象DO
     * @return List返回集合
     */
    @Override
    public List<FSignTParaChnlDO> list(FSignTParaChnlDO obj) {
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
    public List<FSignTParaChnlDO> list(FSignTParaChnlDO obj, int start, int limit) {
        logger.debug("FSignTParaChnlDO=" + obj.toString());
        logger.debug("start=" + start);
        logger.debug("limit=" + limit);
        List<FSignTParaChnlDO> list = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select * from ").append(TABLE_NAME).append(" where 1 = 1 ");
            if(obj.getChnlNo()!=null &&!"".equals(obj.getChnlNo())){
            	sql.append(" and chnl_no ="+ obj.getChnlNo());
            }
            logger.debug("sql=" + sql.toString());
            IDBSession session = DBSessionFactory.getSession();
          
            if (limit == 0) {
                list = session.getObjectList(sql.toString(), FSignTParaChnlDO.class);
            } else {
                list = session.getObjectListForPage(sql.toString(), FSignTParaChnlDO.class, start, limit);
            }
        } catch (Exception e) {
            logger.error("列表查询异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "列表查询失败！");
        }
        return list;
    }
    
	@Override
	public int insert(FSignTParaChnlDO objDO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(FSignTParaChnlDO objDO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(FSignTParaChnlDO objDO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public FSignTParaChnlDO get(FSignTParaChnlDO objDO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FSignTParaChnlDO> list(int start, int limit, Object... param) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
