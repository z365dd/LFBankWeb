package com.adtec.comp.sign.dao;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.adtec.comp.sign.entity.FSignTPipBusiDO;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.common.dao.IBaseDao;

@Component
public class FSignTPipBusiDao implements IBaseDao<FSignTPipBusiDO>{
	 /**
     * 日志对象
     */
    protected final static Logger logger = LoggerFactory.getLogger(FSignTPipBusiDao.class);
    /*表名称*/
    public static final String TABLE_NAME = "T_PIP_BUSI";
	@Override
	public int insert(FSignTPipBusiDO objDO) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int update(FSignTPipBusiDO objDO) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int delete(FSignTPipBusiDO objDO) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public FSignTPipBusiDO get(FSignTPipBusiDO objDO) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<FSignTPipBusiDO> list(FSignTPipBusiDO objDO) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<FSignTPipBusiDO> list(FSignTPipBusiDO obj, int start, int limit) {
		// TODO Auto-generated method stub
		 logger.debug("EntrDemoDO=" + obj.toString());
	        logger.debug("start=" + start);
	        logger.debug("limit=" + limit);
	        List<FSignTPipBusiDO> list = null;
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
	                list = session.getObjectListByList(sql.toString(), FSignTPipBusiDO.class,paramList);
	            } else {
	                list = session.getObjectListForPage(sql.toString(), FSignTPipBusiDO.class, start, limit);
	            }
	        } catch (Exception e) {
	            logger.error("列表查询异常：" + e.getMessage());
	            throw new BaseException(SysErr.E_MESSAGE, "列表查询失败！");
	        }
	        return list;
	}
	@Override
	public List<FSignTPipBusiDO> list(int start, int limit, Object... param) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
