package com.adtec.prod.oper.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.adtec.prod.oper.entity.ChnlDO;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.common.dao.IBaseDao;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
@Component
public class ChnlDao implements IBaseDao<ChnlDO>{
	private final static Logger logger = LoggerFactory.getLogger(ChnlDao.class);
	private static final String TABLE_NAME = "T_PARA_CHNL";
	@Override
	public int insert(ChnlDO objDO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(ChnlDO objDO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(ChnlDO objDO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ChnlDO get(ChnlDO objDO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ChnlDO> list(ChnlDO objDO) {
		IDBSession session = DBSessionFactory.getSession();
		String sql = "select chnl_no,chnl_name from "+TABLE_NAME;
		List<ChnlDO> list = new ArrayList<ChnlDO>();
		try {
			list = session.getObjectList(sql, ChnlDO.class);
		} catch (SQLException e) {
			e.printStackTrace();
            logger.error("获取文件数据："+ e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "获取数据失败");
		}
		return list;
	}

	@Override
	public List<ChnlDO> list(ChnlDO objDO, int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ChnlDO> list(int start, int limit, Object... param) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
