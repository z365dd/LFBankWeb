package com.adtec.comp.fsvr.om.dao;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.adtec.comp.fsvr.om.entity.FsvrTPipBusiDO;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.common.dao.IBaseDao;

@Component
public class FsvrTPipBusiDao implements IBaseDao<FsvrTPipBusiDO>{
	 /**
     * 日志对象
     */
    protected final static Logger logger = LoggerFactory.getLogger(FsvrTPipBusiDao.class);
    /*表名称*/
    public static final String TABLE_NAME = "T_PIP_BUSI";
	@Override
	public int insert(FsvrTPipBusiDO objDO) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int update(FsvrTPipBusiDO objDO) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int delete(FsvrTPipBusiDO objDO) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public FsvrTPipBusiDO get(FsvrTPipBusiDO objDO) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<FsvrTPipBusiDO> list(FsvrTPipBusiDO objDO) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<FsvrTPipBusiDO> list(FsvrTPipBusiDO obj, int start, int limit) {
		// TODO Auto-generated method stub
		 logger.debug("EntrDemoDO=" + obj.toString());
	        logger.debug("start=" + start);
	        logger.debug("limit=" + limit);
	        List<FsvrTPipBusiDO> list = null;
	        try {
	            StringBuilder sql = new StringBuilder();
	           /* sql.append("select * from ").append(TABLE_NAME).append(" where 1 = 1 ");
	           
	            if(obj.getBusiNo()!=null &&!"".equals(obj.getBusiNo())){
	            	sql.append(" and entr_no ="+ obj.getBusiNo());
	            }//
	             
	            if(obj.getEntrNo()!=null &&!"".equals(obj.getEntrNo())){
	            	sql.append(" and entr_no ="+ obj.getEntrNo());
	            }
	            
	            if(obj.getBusiName()!=null && !"".equals(obj.getBusiName())){
	            	sql.append(" and busi_name like '%"+obj.getBusiName()+"%' ");
	            }
	            if(obj.getCompNo()!=null && !"".equals(obj.getCompNo())){
	            	sql.append(" and comp_no ="+ obj.getCompNo());
	            }*/
	            sql.append("select * from ").append(TABLE_NAME).append(" where sale_prod_code in ( ")
	            .append("select sale_prod_code from t_pip_sale_prod_atom_prod where 1=1");
	            List<Object> paramList = new ArrayList<>();
	            
	            if(obj.getCompNo()!=null && !"".equals(obj.getCompNo())){
	            	sql.append(" and comp_no = ?");
	            	paramList.add(obj.getCompNo());
	            }
	            sql.append(")");
	            logger.debug("sql=" + sql.toString());
	            IDBSession session = DBSessionFactory.getSession();
	            if (limit == 0) {
	                list = session.getObjectListByList(sql.toString(), FsvrTPipBusiDO.class,paramList);
	            } else {
	                list = session.getObjectListForPage(sql.toString(), FsvrTPipBusiDO.class, start, limit);
	            }
	        } catch (Exception e) {
	            logger.error("列表查询异常：" + e.getMessage());
	            throw new BaseException(SysErr.E_MESSAGE, "列表查询失败！");
	        }
	        return list;
	}
	@Override
	public List<FsvrTPipBusiDO> list(int start, int limit, Object... param) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
