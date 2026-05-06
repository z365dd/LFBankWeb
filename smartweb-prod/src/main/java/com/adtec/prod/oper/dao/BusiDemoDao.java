package com.adtec.prod.oper.dao;

import com.adtec.prod.oper.entity.BusiDemoDO;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.prod.oper.entity.DictDO;
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
public class BusiDemoDao implements IBaseDao<BusiDemoDO>{
	private final static Logger logger = LoggerFactory.getLogger(BusiDemoDao.class);
	
	private static final String T_PROD_BUSI = "T_PROD_BUSI";
	

	@Override
	public int insert(BusiDemoDO objDO) {
		objDO.preInsert();
		int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.saveObject(T_PROD_BUSI, objDO, objDO.getIgnoreFields());
            if(rs==0){
            	 try {
                     session.rollback();
                 } catch (SQLException e1) {
                     e1.printStackTrace();
                 }
                 throw new BaseException(SysErr.E_MESSAGE, "插入数据条数为0！");
            }
        } catch (SQLException e) {
        	e.printStackTrace();
            logger.error("新增文档类型表异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "插入数据失败！");
        }
        return rs;
	}



	@Override
	public int update(BusiDemoDO objDO) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public int delete(BusiDemoDO objDO) {
		return 0;
		// TODO Auto-generated method stub
	}
	
	public void deleteFirst(String busiNo) {
		// TODO Auto-generated method stub
		List<Object> parameters = Lists.newArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("delete from ")
		   .append(T_PROD_BUSI)
		   .append(" where busi_no=?");
		parameters.add(busiNo);
		IDBSession session = DBSessionFactory.getSession();
		try {
			session.executeByList(sql.toString(), parameters);
		} catch (SQLException e) {
            logger.error("删除文件信息失败："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "删除文件信息失败");
		}
	}



	@Override
	public BusiDemoDO get(BusiDemoDO objDO) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<BusiDemoDO> get(String busiNo) {
		IDBSession session = DBSessionFactory.getSession();
		List<Object> parameters = Lists.newArrayList();
		String sql = "select * from "+T_PROD_BUSI+ " where BUSI_NO=?";
		parameters.add(busiNo);
		List<BusiDemoDO> list = new ArrayList<BusiDemoDO>();
		try {
			list = session.getObjectListByList(sql, BusiDemoDO.class, parameters);
		} catch (SQLException e) {
			e.printStackTrace();
            logger.error("获取文件数据："+ e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "获取数据失败");
		}
		return list;	
	}



	@Override
	public List<BusiDemoDO> list(BusiDemoDO objDO) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<BusiDemoDO> list(BusiDemoDO objDO, int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<BusiDemoDO> list(int start, int limit, Object... param) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<DictDO> getInOutBankFlg() {
		IDBSession session = DBSessionFactory.getSession();
		List<Object> parameters = Lists.newArrayList();
		String sql = "select dict_val,dict_label from t_sys_dict where dict_tp=?";
		parameters.add("ENTR_ACCT_BANK_FLG");
		List<DictDO> list;
		try {
			list = session.getObjectListByList(sql, DictDO.class, parameters);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("获取数据："+ e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "获取数据失败");
		}
		return list;
	}
}
