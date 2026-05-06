package com.adtec.prod.oper.dao;

import com.adtec.prod.oper.entity.BusiSignDocDO;
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
import java.util.List;
@Component
public class BusiSignDocDao implements IBaseDao<BusiSignDocDO>{
	protected final static Logger logger = LoggerFactory.getLogger(BusiSignDocDao.class);
	private static final String TABLE_NAME = "T_PIP_BUSI_DOC";
	@Override
	public int insert(BusiSignDocDO objDO) {
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			rs = session.saveObject(TABLE_NAME, objDO, objDO.getIgnoreFields());
		} catch (SQLException e) {
			logger.error("新增业务文档信息表异常：" + e.getMessage());
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_MESSAGE, "新增业务文档信息表异常！");
		}
		return rs;
	}

	@Override
	public int update(BusiSignDocDO objDO) {
		//TODO
		return 0;
	}
	
	public int delete(String busiNo){
		BusiSignDocDO objDO = new BusiSignDocDO();
		objDO.setBusiNo(busiNo);
		return delete(objDO);
	}
	@Override
	public int delete(BusiSignDocDO objDO) {
		List<Object> parameters = Lists.newArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("delete from ").append(TABLE_NAME).append(" where busi_no=?");
		parameters.add(objDO.getBusiNo());
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			rs = session.executeByList(sql.toString(), parameters);
		} catch (SQLException e) {
			logger.error("删除业务文档信息异常：" + e.getMessage());
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_MESSAGE, "删除业务文档信息异常！");
		}
		return rs;
	}

	@Override
	public BusiSignDocDO get(BusiSignDocDO objDO) {
		//TODO
		return null;
	}
	
	public List<BusiSignDocDO> list(String busiNo){
		BusiSignDocDO busiSignDocDO = new BusiSignDocDO();
		busiSignDocDO.setBusiNo(busiNo);
		return list(busiSignDocDO);
	}
	
	@Override
	public List<BusiSignDocDO> list(BusiSignDocDO objDO) {
		List<Object> parameters = Lists.newArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("select * from ").append(TABLE_NAME)
		.append(" where busi_no=?");
		parameters.add(objDO.getBusiNo());
		logger.debug("sql=" + sql.toString());
		IDBSession session = DBSessionFactory.getSession();
		List<BusiSignDocDO> list = null;
		try {
			list = session.getObjectListByList(sql.toString(), BusiSignDocDO.class, parameters);
			
		} catch (SQLException e) {
			logger.error("列表查询业务文档信息异常：" + e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "列表查询业务文档信息失败！");
		}
		return list;
	}

	@Override
	public List<BusiSignDocDO> list(BusiSignDocDO objDO, int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BusiSignDocDO> list(int start, int limit, Object... param) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public BusiSignDocDO getMaxSer(){
		StringBuilder sql = new StringBuilder();
		sql.append("select * from ").append(TABLE_NAME).append(" where ser = (select MAX(ser) from ").append(TABLE_NAME).append(")");
		IDBSession session = DBSessionFactory.getSession();
		BusiSignDocDO rs = null;
		try {
			rs = session.getObject(sql.toString(),BusiSignDocDO.class);
		} catch (SQLException e) {
			throw new BaseException(SysErr.E_MESSAGE, "列表查询业务文档信息失败！");
		}
		return rs;
	}
}
