package com.adtec.para.rules.dao;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.para.chk.entity.ParaAgentDO;
import com.adtec.para.rules.entity.ParaRulesStgAuthDO;
import com.adtec.sys.common.dao.IBaseDao;
import com.adtec.sys.modules.sys.entity.Rent;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;


@Component
public class ParaRulesStgAuthDao implements IBaseDao<ParaRulesStgAuthDO>{

    /*日志对象*/
    protected final static Logger logger = LoggerFactory.getLogger(ParaRulesStgAuthDao.class);
    /*表名称*/
    public static final String TABLE_NAME = "t_para_rules_stg_auth";
    public static final String T_SGP_PART = "t_sgp_part";
    public static final String T_SYS_OFFICE = "t_sys_office";
    public static final String T_SYS_RENT = "t_sys_rent";

    @Override
    public int insert(ParaRulesStgAuthDO rulesStgAuthDO) {
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.saveObject(TABLE_NAME, rulesStgAuthDO, rulesStgAuthDO.getIgnoreFields());
        } catch (SQLException e) {
            logger.error("新增存储规则权限表异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "新增存储规则权限表失败！");
        }
        return rs;
    }


    @Override
    public int update(ParaRulesStgAuthDO rulesStgAuthDO) {
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.updateObject(TABLE_NAME, rulesStgAuthDO, rulesStgAuthDO.getMatchFields(), rulesStgAuthDO.getIgnoreFields());
        } catch (SQLException e) {
            logger.error("修改存储规则权限表异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "修改存储规则权限表失败！");
        }
        return rs;
    }


    @Override
    public int delete(ParaRulesStgAuthDO rulesStgAuthDO) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from ").append(TABLE_NAME).append(" where id='").append(rulesStgAuthDO.getId()).append("'");
        int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.execute(sql.toString());
        } catch (SQLException e) {
            logger.error("删除存储规则权限表异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "删除存储规则权限表失败！");
        }
        return rs;
    }


    public ParaRulesStgAuthDO get(String id) {
        ParaRulesStgAuthDO obj = new ParaRulesStgAuthDO();
        obj.setId(id);
        return get(obj);
    }


    @Override
	public ParaRulesStgAuthDO get(ParaRulesStgAuthDO rulesStgAuthDO) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where id = ?");
        ParaRulesStgAuthDO rs = new ParaRulesStgAuthDO();
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObject(sql.toString(), ParaRulesStgAuthDO.class, rulesStgAuthDO.getId());
        } catch (SQLException e) {
            logger.error("查询存储规则权限表异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "查询存储规则权限表失败！");
        }
        return rs;
    }


    @Override
    public List<ParaRulesStgAuthDO> list(ParaRulesStgAuthDO rulesStgAuthDO) {
        return list(rulesStgAuthDO, 0, 0);
    }


    @Override
    public List<ParaRulesStgAuthDO> list(ParaRulesStgAuthDO rulesStgAuthDO, int start, int limit) {
        logger.debug("rulesStgAuthDO=" + rulesStgAuthDO);
        logger.debug("start=" + start);
        logger.debug("limit=" + limit);
        List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(rulesStgAuthDO, parameters));
        sql.append(" order by upt_time desc");
        logger.debug("sql=" + sql.toString());
        IDBSession session = DBSessionFactory.getSession();
        List<ParaRulesStgAuthDO> list = null;
        try {
            if (limit == 0) {
                list = session.getObjectList(sql.toString(), ParaRulesStgAuthDO.class);
            } else {
                list = session.getObjectListForPage(sql.toString(), ParaRulesStgAuthDO.class, start, limit);
            }
        } catch (SQLException e) {
            logger.error("列表查询存储规则权限表异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "列表查询存储规则权限表失败！");
        }
        return list;
    }


    @Override
    public List<ParaRulesStgAuthDO> list(int start, int limit, Object... param) {
        return null;
    }


    public int getTotal(ParaRulesStgAuthDO rulesStgAuthDO) {
        logger.debug("rulesStgAuthDO=" + rulesStgAuthDO);
        List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(rulesStgAuthDO, parameters));
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        logger.debug("countSql=" + countSql);
        IDBSession session = DBSessionFactory.getSession();
        int total = 0;
        try {
            total = session.account(countSql);
        } catch (SQLException e) {
            logger.error("总记录数查询存储规则权限表异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "总记录数查询存储规则权限表失败！");
        }
        return total;
    }


    /*根据传入的数据对象，对应属性值不空时拼接查询条件*/
    private String getWhereSql(ParaRulesStgAuthDO rulesStgAuthDO, List<Object> parameters) {
        StringBuffer whereSql = new StringBuffer(" where 1=1 ");
        if(!DataUtil.isNullStr(rulesStgAuthDO.getId())){
            /*添加查询条件：编号*/
            whereSql.append(" and id = ?");
            parameters.add(rulesStgAuthDO.getId());
        }
        if(!DataUtil.isNullStr(rulesStgAuthDO.getRuleId())){
            /*添加查询条件：所属存储规则ID*/
            whereSql.append(" and rule_id = ?");
            parameters.add(rulesStgAuthDO.getRuleId());
        }
        if(!DataUtil.isNullStr(rulesStgAuthDO.getRuleAuthTp())){
            /*添加查询条件：权限类型*/
            whereSql.append(" and rule_auth_tp = ?");
            parameters.add(rulesStgAuthDO.getRuleAuthTp());
        }
        if(!DataUtil.isNullStr(rulesStgAuthDO.getUseTntNo())){
            /*添加查询条件：授权租户*/
            whereSql.append(" and tnt_no = ?");
            parameters.add(rulesStgAuthDO.getUseTntNo());
        }
        if(!DataUtil.isNullStr(rulesStgAuthDO.getMembNo())){
            /*添加查询条件：授权参与者*/
            whereSql.append(" and memb_no = ?");
            parameters.add(rulesStgAuthDO.getMembNo());
        }
        return whereSql.toString();
    }


	/**
	 * 获取所有参与者
	 */
	public List<ParaRulesStgAuthDO> getAllPart() {
		StringBuffer sql = new StringBuffer();
		sql.append("select p.TNT_ENG_NAME AS tnt_no, p.MEMB_NO, r.NAME AS tenant_name, p.ch_name AS part_name  from ")
			.append(T_SGP_PART)
			.append(" p left join ")
			.append(T_SYS_RENT)
			.append(" r on r.eng_name = p.TNT_ENG_NAME")
			.append(" where 1=1 and r.del_flg = '0' order by p.upt_time desc");
		logger.debug("sql=" + sql.toString());
        IDBSession session = DBSessionFactory.getSession();
        List<ParaRulesStgAuthDO> list = null;
        try {
        	list = session.getObjectList(sql.toString(),ParaRulesStgAuthDO.class);
        }catch (SQLException e) {
           	logger.error("参与者查询异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "参与者查询失败！");
       }
   		return list;
	}

	/**
	 * 根据当前用户获取所在的租户下的所有参与者、下级租户参与者
	 * @param user
	 * @return
	 */
//	public List<RulesStgAuthDO> getPartByUser(User user) {
//
//		String officeId = user.getOffice().getId();
//		StringBuffer sql = new StringBuffer();
//		sql.append("select p.tnt_eng_name as tnt_no, p.memb_no, r.name as tenant_name, p.ch_name as part_name from ")
//			.append(T_SYS_RENT).append(" r ")
//			.append(" left join ")
//			.append(T_SGP_PART).append(" p ")
//			.append(" on p.tnt_eng_name = r.eng_name ")
//			.append(" where r.parent_id_list like ? ")
//			.append(" and r.del_flg = 0  and p.del_flg = 0");
//		logger.debug("sql=" + sql.toString());
//		IDBSession session = DBSessionFactory.getSession();
//		List<RulesStgAuthDO> list = null;
//        try {
//        	list = session.getObjectList(sql.toString(), RulesStgAuthDO.class);
//        }catch (SQLException e) {
//           	logger.error("参与者查询异常："+ e.getMessage());
//            throw new BaseException(SysErr.E_MESSAGE, "参与者查询失败！");
//        }
//   		return list;
//	}

	/**
	 * 根据存储规则id删除权限
	 * @param stgId
	 */
	public void deleteByStgId(String stgId) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from ").append(TABLE_NAME).append(" where rule_id = ?");
		logger.debug("sql=" + sql.toString());
		IDBSession session = DBSessionFactory.getSession();
		try {
			session.execute(sql.toString(), stgId);
        }catch (SQLException e) {
           	logger.error("存储规则权限新增异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "存储规则权限新增失败！");
        }
	}

	/**
	 * 根据存储规则id获取权限
	 * @param id
	 * @return
	 */
	public List<ParaRulesStgAuthDO> getListByStgId(String stgId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ").append(TABLE_NAME).append(" where rule_id = ?");
		logger.debug("sql=" + sql.toString());
		IDBSession session = DBSessionFactory.getSession();
		List<ParaRulesStgAuthDO> list = null;
		try {
        	list = session.getObjectList(sql.toString(), ParaRulesStgAuthDO.class, stgId);
        }catch (SQLException e) {
           	logger.error("存储规则权限新增异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "存储规则权限新增失败！");
        }
		return list;
	}

	/**
	 * 根据存储规则id，权限类型，租户，参与者获取对应记录
	 * @param stgWirteAuthDO
	 * @return
	 */
	public ParaRulesStgAuthDO getAuthByUK(ParaRulesStgAuthDO stgAuthDO) {
	    List<Object> parameters = Lists.newArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ").append(TABLE_NAME).append(getWhereSql(stgAuthDO, parameters));
		logger.debug("sql=" + sql.toString());
		IDBSession session = DBSessionFactory.getSession();
		ParaRulesStgAuthDO authDO = new ParaRulesStgAuthDO();
		try {
			authDO = session.getObjectByList(sql.toString(),ParaRulesStgAuthDO.class, parameters);
        }catch (SQLException e) {
           	logger.error("存储规则权限新增异常："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "存储规则权限新增失败！");
        }
		return authDO;
	}

	/**
	 * 根据租户和参与者查询对应的中文名称
	 * @param tenant
	 * @param partId
	 * @return
	 */
	public String getTenantPartByEnname(String tenant, String partId) {
//		ParaAgentDO agentDO = new ParaAgentDO();
//		List<Object> parameters = Lists.newArrayList();
//		StringBuffer sql = new StringBuffer();
//		sql.append("select distinct r.name as tenant, p.ch_name as part_id from ")
//            .append(T_SGP_PART)
//            .append(" p left join ")
//            .append(T_SYS_RENT)
//            .append(" r on p.tnt_eng_name = r.eng_name where 1=1 and r.del_flg = '0'");
//		if (!DataUtil.isNullStr(tenant)){
//			sql.append(" and p.TNT_ENG_NAME = ?");
//            parameters.add(tenant);
//		}
//		if (!DataUtil.isNullStr(partId)){
//			sql.append(" and p.memb_no = ?");
//            parameters.add(partId);
//		}
//		IDBSession session = DBSessionFactory.getSession();
//		try {
//			agentDO = session.getObjectByList(sql.toString(), ParaAgentDO.class, parameters);
//        }catch (SQLException e) {
//           	logger.error("查询租户与参与者异常："+ e.getMessage());
//            throw new BaseException(SysErr.E_MESSAGE, "查询租户与参与者失败！");
//        }
//		String tenantPart = "";
//		if (null != agentDO && null != agentDO.getTenant() && null != agentDO.getPartId()) {
//			tenantPart = agentDO.getTenant() + "_" + agentDO.getPartId();
//		}else {
//			tenantPart = tenant + "_" + partId;
//		}
		return tenant + "_" + partId;
	}

	/**
	 * 获取租户的所有参与者
	 * @param rent
	 * @return
	 */
	public List<ParaRulesStgAuthDO> getPartByRent(Rent rent) {
	    List<Object> parameters = Lists.newArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select p.memb_no as auth_part_id, p.ch_name as part_name, r.name as tenant_name, p.tnt_eng_name as auth_tenant from ")
            .append(T_SGP_PART)
            .append(" p left join ")
            .append(T_SYS_RENT)
            .append(" r on r.eng_name = p.tnt_eng_name")
		    .append(" where p.del_flg='N' and p.tnt_eng_name = ?")
		    .append(" AND  p.use_tnt_no = ?")
		    .append(" order by p.memb_no asc");
        parameters.add(rent.getEngName());
        parameters.add(rent.getEngName());
		IDBSession session = DBSessionFactory.getSession();
		List<ParaRulesStgAuthDO> list = Lists.newArrayList();
		try {
			list = session.getObjectListByList(sql.toString(), ParaRulesStgAuthDO.class, parameters);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BaseException(SysErr.E_MESSAGE, "查询所有参与者信息失败！" + e.getMessage());
		}
		return list;
	}

}
