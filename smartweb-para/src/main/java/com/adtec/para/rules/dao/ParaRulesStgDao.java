package com.adtec.para.rules.dao;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.para.cfgcenter.entity.ParaRulesStgData;
import com.adtec.para.rules.entity.ParaRulesStgDO;
import com.adtec.sys.common.dao.IBaseDao;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;



@Component
public class ParaRulesStgDao implements IBaseDao<ParaRulesStgDO>{

	/*日志对象*/
	protected final static Logger logger = LoggerFactory.getLogger(ParaRulesStgDao.class);
	/*表名称*/
	public static final String TABLE_NAME = "t_para_rules_stg";
	public static final String T_MS_PART = "t_ms_part";
	public static final String T_CACHE_CENTER = "t_para_center";
	public static final String T_SYS_RENT = "t_sys_rent";


	@Override
	public int insert(ParaRulesStgDO rulesStgDO) {
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			rs = session.saveObject(TABLE_NAME, rulesStgDO, rulesStgDO.getIgnoreFields());
		} catch (SQLException e) {
			logger.error("新增存储规则信息表异常："+ e.getMessage());
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_MESSAGE, "新增存储规则信息表失败！");
		}
		return rs;
	}


	@Override
	public int update(ParaRulesStgDO rulesStgDO) {
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			rs = session.updateObject(TABLE_NAME, rulesStgDO, rulesStgDO.getMatchFields(), rulesStgDO.getIgnoreFields());
		} catch (SQLException e) {
			logger.error("修改存储规则信息表异常："+ e.getMessage());
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_MESSAGE, "修改存储规则信息表失败！");
		}
		return rs;
	}


	@Override
	public int delete(ParaRulesStgDO rulesStgDO) {
		StringBuilder sql = new StringBuilder();
		sql.append("delete from ").append(TABLE_NAME).append(" where id = ?");
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			rs = session.execute(sql.toString(), rulesStgDO.getId());
		} catch (SQLException e) {
			logger.error("删除存储规则信息表异常："+ e.getMessage());
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_MESSAGE, "删除存储规则信息表失败！");
		}
		return rs;
	}


	public ParaRulesStgDO get(String id) {
		ParaRulesStgDO obj = new ParaRulesStgDO();
		obj.setId(id);
		return get(obj);
	}


	@Override
	public ParaRulesStgDO get(ParaRulesStgDO rulesStgDO) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from ").append(TABLE_NAME).append(" where id = ?");
		ParaRulesStgDO rs = new ParaRulesStgDO();
		IDBSession session = DBSessionFactory.getSession();
		try {
			rs = session.getObject(sql.toString(), ParaRulesStgDO.class, rulesStgDO.getId());
		} catch (SQLException e) {
			logger.error("查询存储规则信息表异常："+ e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "查询存储规则信息表失败！");
		}
		return rs;
	}


	@Override
	public List<ParaRulesStgDO> list(ParaRulesStgDO rulesStgDO) {
		return list(rulesStgDO, 0, 0);
	}


	@Override
	public List<ParaRulesStgDO> list(ParaRulesStgDO rulesStgDO, int start, int limit) {
		logger.debug("rulesStgDO=" + rulesStgDO);
		logger.debug("start=" + start);
		logger.debug("limit=" + limit);
		List<Object> parameters = Lists.newArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(rulesStgDO, parameters));
		sql.append(" order by upt_time desc");
		logger.debug("sql=" + sql.toString());
		IDBSession session = DBSessionFactory.getSession();
		List<ParaRulesStgDO> list = null;
		try {
			if (limit == 0) {
				list = session.getObjectListByList(sql.toString(), ParaRulesStgDO.class, parameters);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), ParaRulesStgDO.class, start, limit, parameters);
			}
		} catch (SQLException e) {
			logger.error("列表查询存储规则信息表异常："+ e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "列表查询存储规则信息表失败！");
		}
		return list;
	}


	@Override
	public List<ParaRulesStgDO> list(int start, int limit, Object... param) {
		return null;
	}


	public int getTotal(ParaRulesStgDO rulesStgDO) {
		logger.debug("rulesStgDO=" + rulesStgDO);
		List<Object> parameters = Lists.newArrayList();
		StringBuilder sql = new StringBuilder();
//        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(rulesStgDO, parameters));
		sql.append("select distinct r.name as tenant_name ,c.ch_name as center_name, t.* from ").append(TABLE_NAME).append(" t ")
				.append(" left join ").append(T_SYS_RENT).append(" r on r.eng_name = t.use_tnt_no ")
				.append(" left join ").append(T_CACHE_CENTER).append(" c on c.id = t.cache_centr_id ")
				.append(" where 1=1 and t.del_flg = 'N'");
		if(!DataUtil.isNullStr(rulesStgDO.getCacheCentrId())){
			sql.append(" and t.cache_centr_id = ?");
			parameters.add(rulesStgDO.getCacheCentrId());
		}
		if(!DataUtil.isNullStr(rulesStgDO.getEngName())){
			sql.append(" and t.eng_name like ?");
			parameters.add("%"+rulesStgDO.getEngName()+"%");
		}
		if(!DataUtil.isNullStr(rulesStgDO.getChName())){
			sql.append(" and t.ch_name like ?");
			parameters.add("%"+rulesStgDO.getChName()+"%");
		}
		if(!DataUtil.isNullStr(rulesStgDO.getUseTntNo())){
			sql.append(" and t.use_tnt_no = ?");
			parameters.add(rulesStgDO.getUseTntNo());
		}
		if(!DataUtil.isNullStr(rulesStgDO.getReadAuthLvl())){
			sql.append(" and t.read_auth_lvl = ?");
			parameters.add(rulesStgDO.getReadAuthLvl());
		}
		if(!DataUtil.isNullStr(rulesStgDO.getStorgRuleTp())){
			sql.append(" and t.storg_rule_tp = ?");
			parameters.add(rulesStgDO.getStorgRuleTp());
		}
		if (!DataUtil.isNullStr(rulesStgDO.getSyncFlg())) {
			sql.append(" and t.sync_flg = ?");
			parameters.add(rulesStgDO.getSyncFlg());
		}
		sql.append(" order by t.upt_time desc");
		String countSql = "select count(1) from (" + sql.toString() + ") ct";
		logger.debug("countSql=" + countSql);
		IDBSession session = DBSessionFactory.getSession();
		int total = 0;
		try {
			total = session.accountByList(countSql, parameters);
		} catch (SQLException e) {
			logger.error("总记录数查询存储规则信息表异常："+ e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "总记录数查询存储规则信息表失败！");
		}
		return total;
	}


	/*根据传入的数据对象，对应属性值不空时拼接查询条件*/
	private String getWhereSql(ParaRulesStgDO rulesStgDO, List<Object> parameters) {
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if(!DataUtil.isNullStr(rulesStgDO.getId())){
			/*添加查询条件：编号*/
			whereSql.append(" and id = ?");
			parameters.add(rulesStgDO.getId());
		}
		if(!DataUtil.isNullStr(rulesStgDO.getCacheCentrId())){
			/*添加查询条件：所属缓存中心*/
			whereSql.append(" and cache_centr_id = ?");
			parameters.add(rulesStgDO.getCacheCentrId());
		}
		if(!DataUtil.isNullStr(rulesStgDO.getEngName())){
			/*添加查询条件：英文名称*/
			whereSql.append(" and eng_name like ?");
			parameters.add("%"+rulesStgDO.getCacheCentrId()+"%");
		}
		if(!DataUtil.isNullStr(rulesStgDO.getChName())){
			/*添加查询条件：中文名称*/
			whereSql.append(" and ch_name = ?");
			parameters.add("%"+rulesStgDO.getChName()+"%");
		}
		if(!DataUtil.isNullStr(rulesStgDO.getUniqKey())){
			/*添加查询条件：唯一主键组合*/
			whereSql.append(" and uniq_key = ?");
			parameters.add(rulesStgDO.getUniqKey());
		}
		if(!DataUtil.isNullStr(rulesStgDO.getUseTntNo())){
			/*添加查询条件：所属租户*/
			whereSql.append(" and tnt_no = ?");
			parameters.add(rulesStgDO.getUseTntNo());
		}
		if(!DataUtil.isNullStr(rulesStgDO.getReadAuthLvl())){
			/*添加查询条件：可读等级*/
			whereSql.append(" and read_auth_lvl = ?");
			parameters.add(rulesStgDO.getReadAuthLvl());
		}
		return whereSql.toString();
	}

	/**
	 * 存储规则列表查询
	 * @param rulesStgDO
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<ParaRulesStgDO> listByPage(ParaRulesStgDO rulesStgDO, int start, int limit) {
		logger.debug("enterDO=" + rulesStgDO);
		logger.debug("start=" + start);
		logger.debug("limit=" + limit);
		List<Object> parameters = Lists.newArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct r.name as tenant_name ,c.ch_name as center_name, t.* from ").append(TABLE_NAME).append(" t ")
				.append(" left join ").append(T_SYS_RENT).append(" r on r.eng_name = t.use_tnt_no ")
				.append(" left join ").append(T_CACHE_CENTER).append(" c on c.id = t.cache_centr_id ")
				.append(" where 1=1 and t.del_flg = 'N'");
		if(!DataUtil.isNullStr(rulesStgDO.getCacheCentrId())){
			sql.append(" and t.cache_centr_id = ?");
			parameters.add(rulesStgDO.getCacheCentrId());
		}
		if(!DataUtil.isNullStr(rulesStgDO.getEngName())){
			sql.append(" and t.eng_name like ?");
			parameters.add("%"+rulesStgDO.getEngName()+"%");
		}
		if(!DataUtil.isNullStr(rulesStgDO.getChName())){
			sql.append(" and t.ch_name like ?");
			parameters.add("%"+rulesStgDO.getChName()+"%");
		}
		if(!DataUtil.isNullStr(rulesStgDO.getUseTntNo())){
			sql.append(" and t.use_tnt_no = ?");
			parameters.add(rulesStgDO.getUseTntNo());
		}
		if(!DataUtil.isNullStr(rulesStgDO.getReadAuthLvl())){
			sql.append(" and t.read_auth_lvl = ?");
			parameters.add(rulesStgDO.getReadAuthLvl());
		}
		if(!DataUtil.isNullStr(rulesStgDO.getStorgRuleTp())){
			sql.append(" and t.storg_rule_tp = ?");
			parameters.add(rulesStgDO.getStorgRuleTp());
		}
		if (!DataUtil.isNullStr(rulesStgDO.getSyncFlg())) {
			sql.append(" and t.sync_flg = ?");
			parameters.add(rulesStgDO.getSyncFlg());
		}
		if (!DataUtil.isNullStr(rulesStgDO.getTabName())) {
			sql.append(" and t.tab_name = ?");
			parameters.add(rulesStgDO.getTabName());
		}
		sql.append(" order by t.upt_time desc");
		logger.debug("sql=" + sql.toString());
		IDBSession session = DBSessionFactory.getSession();
		List<ParaRulesStgDO> list = null;
		try {
			if (limit == 0) {
				list = session.getObjectListByList(sql.toString(), ParaRulesStgDO.class, parameters);
			}else {
				list = session.getObjectListByListForPage(sql.toString(), ParaRulesStgDO.class, start, limit, parameters);
			}
		}catch (SQLException e) {
			logger.error("存储规则列表查询异常："+ e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "存储规则列表查询失败！");
		}
		return list;
	}

	/**
	 * 根据存储规则id更新读取等级
	 * @param stgId
	 * @param level
	 * @return
	 */
	public int uptReadAuthLvById(String stgId, String level) {
		StringBuffer sql = new StringBuffer();
		sql.append("update ").append(TABLE_NAME).append(" set read_auth_lvl = ?")
				.append("where id = ?");
		logger.debug("sql=" + sql.toString());
		List<Object> parameters = Lists.newArrayList();
		parameters.add(level);
		parameters.add(stgId);
		IDBSession session = DBSessionFactory.getSession();
		int rs = 0;
		try {
			rs = session.executeByList(sql.toString(), parameters);
		}catch (SQLException e) {
			logger.error("存储规则列表更新异常："+ e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "存储规则列表更新失败！");
		}
		return rs;
	}

	/**
	 * 根据缓存中心获取存储规则 -- 管理台
	 * @param centerId
	 * @return
	 */
	public List<ParaRulesStgDO> getListByCenterId(String centerId) {
		ParaRulesStgDO rulesStgDO = new ParaRulesStgDO();
		rulesStgDO.setCacheCentrId(centerId);
		List<ParaRulesStgDO> list =  list(rulesStgDO);
		return list;
	}

	/**
	 * 根据缓存中心获取存储规则 -- 同步配置信息
	 * @param centerId
	 * @return
	 */
	public List<ParaRulesStgData> getColDataByCenterId(String centerId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select c.eng_name as cache_center, t.* from ")
				.append(TABLE_NAME)
				.append(" t left join ")
				.append(T_CACHE_CENTER)
				.append(" c on t.cache_centr_id = c.id ")
				.append("where t.cache_centr_id = ?");
		IDBSession session = DBSessionFactory.getSession();
		List<ParaRulesStgData> list = null;
		try {
			list = session.getObjectList(sql.toString(), ParaRulesStgData.class, centerId);
		}catch (SQLException e) {
			logger.error("存储规则列表更新异常："+ e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "存储规则列表更新失败！");
		}
		return list;
	}

	/**
	 * 根据登陆用户的租户信息获取已经同步到配置中心且拥有读取权限的存储规则
	 * @param tenant
	 * @return
	 */
	public List<ParaRulesStgDO> getRulesByTenant(String tenant) {
		List<Object> parameters = Lists.newArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct t.* from (")
				.append("select s.* from "+TABLE_NAME+" s where s.read_auth_lvl = '00' ")
				.append(" union")
				.append(" select s.* from "+TABLE_NAME+" s where s.tnt_no = ?")
//			.append(" union")
//			.append(" select s.* from t_cache_rules_stg s where s.read_auth_lvl = '01' and s.tnt_no = '").append(tenant).append("' ")
				.append(" union")
				.append(" select distinct s.* from "+TABLE_NAME+" s left join t_para_rules_stg_auth a on a.rule_id = s.id where s.read_auth_lvl in ('01', '02') and a.rule_auth_tp = '00' and a.tnt_no = ? ")
//			.append(" union")
//			.append(" select distinct s.* from t_cache_rules_stg s left join t_cache_rules_stg_auth a on a.stg_id = s.id where s.read_auth_lvl = '02' and a.auth_type = '00' and a.tnt_no = ? ")
				.append(" )t where t.SYNC_FLG = 'Y'");
		parameters.add(tenant);
		parameters.add(tenant);
		IDBSession session = DBSessionFactory.getSession();
		List<ParaRulesStgDO> list = null;
		try {
			list = session.getObjectListByList(sql.toString(), ParaRulesStgDO.class, parameters);
		}catch (SQLException e) {
			logger.error("获取存储规则异常："+ e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "获取存储规则失败！");
		}
		return list;
	}

	/**
	 * 根据缓存中心id更新存储规则同步状态
	 * @param centerId
	 * @param sync
	 */
	public int updateSyncFlgByCenterId(String centerId, String syncFlg) {
//		StringBuffer sql = new StringBuffer();
//		sql.append("update t_cache_rules_stg set remark1 = '").append(syncStatus).append("' ")
//			.append(" where center_id = '").append(centerId).append("'");
		List<Object> parameters = Lists.newArrayList();
		String sql = "update " + TABLE_NAME + " set sync_flg = ? where cache_centr_id = ?";
		parameters.add(syncFlg);
		parameters.add(centerId);
		IDBSession session = DBSessionFactory.getSession();
		int rs = 0;
		try {
			rs = session.executeByList(sql.toString(), parameters);
		}catch (SQLException e) {
			logger.error("存储规则列表更新异常："+ e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "存储规则列表更新失败！");
		}
		return rs;
	}

	/**
	 * 根据存储规则英文名称判断是否已存在
	 * @param enname
	 * @return
	 */
	public ParaRulesStgDO getRulesByEngName(ParaRulesStgDO rulesStgDO) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from "+TABLE_NAME+" where eng_name = ? and cache_centr_id = ?");
		IDBSession session = DBSessionFactory.getSession();
		ParaRulesStgDO rs = null;
		try {
			rs = session.getObject(sql.toString(), ParaRulesStgDO.class, rulesStgDO.getEngName(), rulesStgDO.getCacheCentrId());
//			rs = session.account(sql.toString(), rulesStgDO.getEngName(), rulesStgDO.getCacheCentrId());
//            rs = session.execute(sql.toString());
		}catch (SQLException e) {
			logger.error("获取存储规则异常："+ e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "获取存储规则失败！");
		}
		return rs;
	}

	/**
	 * 根据存储规则id更新存储规则同步状态
	 * @param stgId
	 * @param sync
	 */
	public int updateSyncFlgByStgId(String stgId, String syncStatus) {
		StringBuffer sql = new StringBuffer();
		sql.append("update t_para_rules_stg set sync_flg = ? ")
				.append(" where id = ? ");
		IDBSession session = DBSessionFactory.getSession();
		int rs = 0;
		try {
			rs = session.execute(sql.toString(), syncStatus, stgId);
		}catch (SQLException e) {
			logger.error("存储规则更新同步状态异常："+ e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "存储规则更新同步状态失败！");
		}
		return rs;
	}

	/**
	 * 根据存储规则Id删除数据
	 * @param stgId
	 */
	public int deleteStgById(String stgId) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from "+TABLE_NAME+" where id = ?");
		IDBSession session = DBSessionFactory.getSession();
		int rs = 0;
		try {
			rs = session.execute(sql.toString(), stgId);
		}catch (SQLException e) {
			logger.error("存储规则删除异常："+ e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "存储规则删除失败！");
		}
		return rs;
	}

	/**
	 * 根据所属租户，已同步缓存中心查询存储规则
	 * @param tenant
	 * @return
	 */
	public List<ParaRulesStgData> getStgDataByTenant(String tenant) {
		StringBuffer sql = new StringBuffer();
		sql.append("select s.* from "+TABLE_NAME+" s where s.tnt_no = ?")
				.append(" and s.cache_centr_id in (select c.id from t_para_center c where c.run_stat = '01')");
		IDBSession session = DBSessionFactory.getSession();
		List<ParaRulesStgData> list = null;
		try {
			list = session.getObjectList(sql.toString(), ParaRulesStgData.class, tenant);
		}catch (SQLException e) {
			logger.error("获取存储规则异常："+ e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "获取存储规则失败！");
		}
		return list;
	}


	/**
	 * 根据stgId获取存储规则信息与缓存中心名称
	 * @param stgId
	 * @return
	 */
	public ParaRulesStgData getStgDataByStgId(String stgId) {

		StringBuffer sql = new StringBuffer();
		sql.append("select c.eng_name as cache_center, t.* from ")
				.append(TABLE_NAME)
				.append(" t left join ")
				.append(T_CACHE_CENTER)
				.append(" c on t.cache_centr_id = c.id ")
				.append("where t.id = ? ");
		IDBSession session = DBSessionFactory.getSession();
		ParaRulesStgData ruleStgData = new ParaRulesStgData();
		try {
			ruleStgData = session.getObject(sql.toString(), ParaRulesStgData.class, stgId);
		}catch (SQLException e) {
			logger.error("获取存储规则异常："+ e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "获取存储规则异常！");
		}
		return ruleStgData;

	}

	/**
	 * 根据租户与参与者获取存储规则
	 * @param tenant
	 * @param partId
	 * @return
	 */
	public List<ParaRulesStgDO> getRulesByAuth(ParaRulesStgDO rulesStgDO, String tenant, String partId) {
		List<Object> parameters = Lists.newArrayList();
		StringBuffer sql = new StringBuffer();
//		sql.append(" select * from ( ")
//				.append(" select * from T_CACHE_RULES_STG where READ_AUTH_LVL = '00' ")
//				.append(" union ")
//				.append(" select s.* from T_CACHE_RULES_STG s left join T_CACHE_RULES_STG_AUTH a on a.RULE_ID = s.id where (s.READ_AUTH_LVL = '01' or s.STORG_RULE_TP = '01' ) ");
//		if (!DataUtil.isNullStr(tenant)) {
//			sql.append(" and a.USE_TNT_NO = ? ");
//			parameters.add(tenant);
//		}
//		sql.append(" ) t where t.sync_flg = 'Y'");

		sql.append("select t.* from "+ TABLE_NAME);
		sql.append("  t where t.sync_flg = 'Y'");
		IDBSession session = DBSessionFactory.getSession();
		List<ParaRulesStgDO> list = null;
		try {
			list = session.getObjectListByList(sql.toString(), ParaRulesStgDO.class, parameters);
		}catch (SQLException e) {
			logger.error("获取存储规则异常："+ e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "获取存储规则失败！");
		}
		return list;
	}

}
