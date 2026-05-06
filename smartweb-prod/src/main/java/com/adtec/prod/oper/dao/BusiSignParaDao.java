package com.adtec.prod.oper.dao;

import com.adtec.prod.oper.entity.BusiSignParaDO;
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
public class BusiSignParaDao implements IBaseDao<BusiSignParaDO>{
	protected final static Logger logger = LoggerFactory.getLogger(BusiSignParaDao.class);
	private static final String TABLE_NAME = "T_PIP_BUSI_PARA";
	@Override
	public int insert(BusiSignParaDO objDO) {
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			rs = session.saveObject(TABLE_NAME, objDO, objDO.getIgnoreFields());
		} catch (SQLException e) {
			logger.error("新增业务参数信息表异常：" + e.getMessage());
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_MESSAGE, "新增业务参数信息表异常！");
		}
		return rs;
	}

	@Override
	public int update(BusiSignParaDO objDO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(BusiSignParaDO objDO) {
		List<Object> parameters = Lists.newArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("delete from ").append(TABLE_NAME).append(" where busi_no=?");
		parameters.add(objDO.getBusiNo());
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			rs = session.executeByList(sql.toString(), parameters);
		} catch (SQLException e) {
			logger.error("删除业务参数信息异常：" + e.getMessage());
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_MESSAGE, "删除业务参数信息异常！");
		}
		return rs;
	}
	public int delete(String  busiNo){
		BusiSignParaDO objDO = new BusiSignParaDO();
		objDO.setBusiNo(busiNo);
		return delete(objDO);
	}
	@Override
	public BusiSignParaDO get(BusiSignParaDO objDO) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<BusiSignParaDO> list(String busiNo){
		BusiSignParaDO busiSignParaDO = new BusiSignParaDO();
		busiSignParaDO.setBusiNo(busiNo);
		return list(busiSignParaDO);
	}
	@Override
	public List<BusiSignParaDO> list(BusiSignParaDO objDO) {
		List<Object> parameters = Lists.newArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("select * from ").append(TABLE_NAME)
		.append(" where busi_no=?");
		parameters.add(objDO.getBusiNo());
		logger.debug("sql=" + sql.toString());
		IDBSession session = DBSessionFactory.getSession();
		List<BusiSignParaDO> list = null;
		try {
			list = session.getObjectListByList(sql.toString(), BusiSignParaDO.class, parameters);
			
		} catch (SQLException e) {
			logger.error("列表查询业务参数信息异常：" + e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "列表查询业务参数信息失败！");
		}
		return list;
	}

	@Override
	public List<BusiSignParaDO> list(BusiSignParaDO objDO, int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BusiSignParaDO> list(int start, int limit, Object... param) {
		// TODO Auto-generated method stub
		return null;
	}

}
