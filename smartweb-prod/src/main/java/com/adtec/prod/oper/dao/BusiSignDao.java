package com.adtec.prod.oper.dao;

import com.adtec.prod.oper.entity.BusiSignDO;
import com.adtec.framework.common.util.StringUtil;
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
public class BusiSignDao implements IBaseDao<BusiSignDO>{
	
	protected final static Logger logger = LoggerFactory.getLogger(BusiSignDao.class);
	private static final String TABLE_NAME = "T_PIP_BUSI";
	private static final String ENTR_TABLE= "T_PIP_ENTR";
	@Override
	public int insert(BusiSignDO objDO) {
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			rs = session.saveObject(TABLE_NAME, objDO,objDO.getIgnoreFields());
		} catch (SQLException e) {
			logger.error("新增业务信息表异常：" + e.getMessage());
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_MESSAGE, "新增业务信息表异常！");
		}
		return rs;
	}

	@Override
	public int update(BusiSignDO objDO) {
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			rs = session.updateObject(TABLE_NAME, objDO, objDO.getMatchFields(),
					objDO.getIgnoreFields());
		} catch (SQLException e) {
			logger.error("修改业务信息表异常：" + e.getMessage());
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_MESSAGE, "修改业务信息表异常！");
		}
		return rs;
	}
	public int update(String busiNo,String stat) {
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		List<Object> parameters = Lists.newArrayList();
		String sql = "update "+TABLE_NAME+" set stat=? where busi_no=?";
		parameters.add(stat);
		parameters.add(busiNo);
		try {
			rs = session.executeByList(sql, parameters);
		}catch (SQLException e) {
			logger.error("修改业务信息表异常：" + e.getMessage());
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_MESSAGE, "修改业务信息表异常！");
		}
		return rs;
	}
	public int updateByRule(String ruleTableTp,String ruleId,String flgVal) {
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		
		//拼接sql语句
		StringBuffer sql = new StringBuffer("update "+TABLE_NAME+" ");
		StringBuffer sqlSet = new StringBuffer();
		StringBuffer sqlWhere = new StringBuffer("where busi_no in(select busi_no from t_pip_rule_relat where SVC_CODE='0' and SCENE_NO='0' and rule_id = ?)");
		
		logger.info("ruleTableTp["+ruleTableTp+"]");
		if("CHK".equals(ruleTableTp)) {
			sqlSet.append("set CHK_PAT=? ");
		}else if("CLR".equals(ruleTableTp)) {
			sqlSet.append("set CLR_TP=? ");
		}else if("FEE_CLR".equals(ruleTableTp)) {
			sqlSet.append("set FEE_TP=? ");
		}else if("SIGN".equals(ruleTableTp)) {
			sqlSet.append("set SIGN_PAT=? ");
		}else {
			return 0;
		}
		sql.append(sqlSet);
		sql.append(sqlWhere);

		logger.info("sql["+sql.toString()+"]");
		//参数赋值
		List<Object> parameters = Lists.newArrayList();
		parameters.add(flgVal);
		parameters.add(ruleId);
		
		try {
			rs = session.executeByList(sql.toString(), parameters);
		}catch (SQLException e) {
			logger.error("修改业务信息表异常：" + e.getMessage());
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_MESSAGE, "修改业务信息表异常！");
		}
		return rs;
	}
	@Override
	public int delete(BusiSignDO objDO) {
		List<Object> parameters = Lists.newArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("delete from ").append(TABLE_NAME).append(" where busi_no=?");
		parameters.add(objDO.getBusiNo());
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			rs = session.executeByList(sql.toString(), parameters);
		} catch (SQLException e) {
			logger.error("删除业务信息异常：" + e.getMessage());
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_MESSAGE, "删除业务信息异常！");
		}
		return rs;
	}
	
	public int delete(String busiNo){
		BusiSignDO busiSignDO = new BusiSignDO();
		busiSignDO.setBusiNo(busiNo);
		int rs = delete(busiSignDO);
		return rs;
	}
	
	@Override
	public BusiSignDO get(BusiSignDO objDO) {
		List<Object> parameters = Lists.newArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("select * from ").append(TABLE_NAME).append(" where busi_no=?");
		parameters.add(objDO.getBusiNo());
		BusiSignDO rs = null;
		IDBSession session = DBSessionFactory.getSession();
		try {
			rs = session.getObjectByList(sql.toString(), BusiSignDO.class, parameters);
		} catch (SQLException e) {
			logger.error("查询业务信息异常：" + e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "查询业务信息异常！");
		}
		return rs;
	}

	@Override
	public List<BusiSignDO> list(BusiSignDO objDO) {
		return list(objDO,0,0);
	}

	@Override
	public List<BusiSignDO> list(BusiSignDO objDO, int start, int limit) {
		logger.debug("BusiSignDO = "+objDO);
		List<Object> parameters = Lists.newArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("select b.*,e.entr_name from ").append(TABLE_NAME).append(" b left join ").append(ENTR_TABLE)
		.append(" e").append(" on b.entr_no = e.entr_no").append(whereSQL(objDO, parameters));
		logger.debug("sql=" + sql.toString());
		IDBSession session = DBSessionFactory.getSession();
		List<BusiSignDO> list = null;
		try {
			if (limit == 0) {
				list = session.getObjectListByList(sql.toString(), BusiSignDO.class, parameters);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), BusiSignDO.class, start, limit, parameters);
			}
		} catch (SQLException e) {
			logger.error("列表业务表异常：" + e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "列表业务信息失败！");
		}
		return list;
	}

	@Override
	public List<BusiSignDO> list(int start, int limit, Object... param) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private String whereSQL(BusiSignDO objDO, List<Object> parameters){
		StringBuilder sql = new StringBuilder();
		sql.append(" where 1=1 ");
		if(!StringUtil.isEmpty(objDO.getEntrNo())){
			sql.append(" and b.entr_no=?");
			parameters.add(objDO.getEntrNo());
		}
		if(!StringUtil.isEmpty(objDO.getOpenStat())){
			sql.append(" and b.open_stat=?");
			parameters.add(objDO.getOpenStat());
		}
		if(!StringUtil.isEmpty(objDO.getSaleProdCode())){
			sql.append(" and b.sale_prod_code=?");
			parameters.add(objDO.getSaleProdCode());
		}
		return sql.toString();
	}
	
	public int getTotal(BusiSignDO objDO){
		List<Object> parameters = Lists.newArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("select * from ").append(TABLE_NAME).append(" b").append(whereSQL(objDO, parameters));
		String countSql = "select count(1) from (" + sql.toString() + ") ct";
		logger.debug("countSql=" + countSql);
		IDBSession session = DBSessionFactory.getSession();
		int total = 0;
		try {
			total = session.accountByList(countSql, parameters);
		} catch (SQLException e) {
			logger.error("总记录数查询业务表异常：" + e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "总记录数查询业务表失败！");
		}
		return total;
	}
}
