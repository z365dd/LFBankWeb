package com.adtec.sys.common.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.adtec.framework.common.util.StringUtil;
import com.adtec.sys.common.persistence.BaseEntity;
import com.adtec.sys.modules.sys.entity.Role;
import com.adtec.sys.modules.sys.entity.User;
import com.google.common.collect.Lists;

/**
 * Service基类
 * 
 * @version 2014-05-16
 */
@Transactional(readOnly = true)
public abstract class BaseService {
	
	/**
	 * 日志对象
	 */
	protected final static Logger logger = LoggerFactory.getLogger(BaseService.class);

	/**
	 * 数据范围过滤
	 * @param user 当前用户对象，通过“entity.getCurrentUser()”获取
	 * @param officeAlias 机构表别名，多个用“,”逗号隔开。
	 * @param userAlias 用户表别名，多个用“,”逗号隔开，传递空，忽略此参数
	 * @return 标准连接条件对象
	 */
//	public static String dataScpFilter(User user, String officeAlias, String userAlias) {
//
//		StringBuilder sqlString = new StringBuilder();
//
//		// 进行权限过滤，多个角色权限范围之间为或者关系。
//		List<String> dataScp = Lists.newArrayList();
//
//		// 超级管理员，跳过权限过滤
//		if (!user.isAdmin()){
//			boolean isDataScpAll = false;
//			for (Role r : user.getRoleList()){
//				for (String oa : StringUtil.split(officeAlias, ",")){
//					if (!dataScp.contains(r.getDataScp()) && StringUtil.isNotBlank(oa)){
//						if (Role.DATA_SCOPE_ALL.equals(r.getDataScp())){
//							isDataScpAll = true;
//						}
//						else if (Role.DATA_SCOPE_OFFICE_AND_CHILD.equals(r.getDataScp())){
//							sqlString.append(" OR " + oa + ".id = '" + user.getOffice().getId() + "'");
//							sqlString.append(" OR " + oa + ".parent_id_list LIKE '" + user.getOffice().getParentIdList() + user.getOffice().getId() + ",%'");
//						}
//						else if (Role.DATA_SCOPE_OFFICE.equals(r.getDataScp())){
//							sqlString.append(" OR " + oa + ".id = '" + user.getOffice().getId() + "'");
//						}
//						else if (Role.DATA_SCOPE_CUSTOM.equals(r.getDataScp())){
//							sqlString.append(" OR EXISTS (SELECT 1 FROM T_SYS_ROLE_OFFICE WHERE role_id = '" + r.getId() + "'");
//							sqlString.append(" AND brch_id = " + oa +".id)");
//						}
//						dataScp.add(r.getDataScp());
//					}
//				}
//			}
//			// 如果没有全部数据权限，并设置了用户别名，则当前权限为本人；如果未设置别名，当前无权限为已植入权限
//			if (!isDataScpAll){
//				if (StringUtil.isNotBlank(userAlias)){
//					for (String ua : StringUtil.split(userAlias, ",")){
//						/*20190109 add by chenyl for 当前用户id不为null是才进行查询条件拼接*/
//						if(null!=user.getId()){
//							sqlString.append(" OR " + ua + ".id = '" + user.getId() + "'");
//						}
//					}
//				}else {
//					for (String oa : StringUtil.split(officeAlias, ",")){
//						sqlString.append(" OR " + oa + ".id IS NULL");
//					}
//				}
//			}else{
//				// 如果包含全部权限，则去掉之前添加的所有条件，并跳出循环。
//				sqlString = new StringBuilder();
//			}
//		}
//		if (StringUtil.isNotBlank(sqlString.toString())){
//			return " AND (" + sqlString.substring(4) + ")";
//		}
//		return "";
//	}

	public static String dataScpFilter(User user, String officeAlias, String userAlias, List<Object> filterParams) {

		StringBuilder sqlString = new StringBuilder();

		// 进行权限过滤，多个角色权限范围之间为或者关系。
		List<String> dataScp = Lists.newArrayList();

		// 超级管理员，跳过权限过滤
		if (!user.isAdmin()){
			boolean isDataScpAll = false;
			for (Role r : user.getRoleList()){
				for (String oa : StringUtil.split(officeAlias, ",")){
					if (!dataScp.contains(r.getDataScp()) && StringUtil.isNotBlank(oa)){
						if (Role.DATA_SCOPE_ALL.equals(r.getDataScp())){
							isDataScpAll = true;
						}
						else if (Role.DATA_SCOPE_OFFICE_AND_CHILD.equals(r.getDataScp())){
							sqlString.append(" OR " + oa + ".id = ?");
							sqlString.append(" OR " + oa + ".parent_id_list LIKE ?");
							filterParams.add(user.getOffice().getId());
							filterParams.add(user.getOffice().getParentIdList() + user.getOffice().getId() + ",%");
						}
						else if (Role.DATA_SCOPE_OFFICE.equals(r.getDataScp())){
							sqlString.append(" OR " + oa + ".id = ?");
							filterParams.add(user.getOffice().getId());
						}
						else if (Role.DATA_SCOPE_CUSTOM.equals(r.getDataScp())){
							sqlString.append(" OR EXISTS (SELECT 1 FROM T_SYS_ROLE_OFFICE WHERE role_id = ?");
							sqlString.append(" AND brch_id = " + oa +".id)");
							filterParams.add(r.getId());
						}
						dataScp.add(r.getDataScp());
					}
				}
			}
			// 如果没有全部数据权限，并设置了用户别名，则当前权限为本人；如果未设置别名，当前无权限为已植入权限
			if (!isDataScpAll){
				if (StringUtil.isNotBlank(userAlias)){
					for (String ua : StringUtil.split(userAlias, ",")){
						/*20190109 add by chenyl for 当前用户id不为null是才进行查询条件拼接*/
						if(null!=user.getId()){
							sqlString.append(" OR " + ua + ".id = ?");
							filterParams.add(user.getId());
						}
					}
				}else {
					for (String oa : StringUtil.split(officeAlias, ",")){
						sqlString.append(" OR " + oa + ".id IS NULL");
					}
				}
			}else{
				// 如果包含全部权限，则去掉之前添加的所有条件，并跳出循环。
				sqlString = new StringBuilder();
			}
		}
		if (StringUtil.isNotBlank(sqlString.toString())){
			return " AND (" + sqlString.substring(4) + ")";
		}
		return "";
	}

	/**
	 * 数据范围过滤
	 * @param user 当前用户对象，通过“entity.getCurrentUser()”获取
	 * @param officeAlias 机构表别名，多个用“,”逗号隔开。
	 * @param userAlias 用户表别名，多个用“,”逗号隔开，传递空，忽略此参数
	 * @return 标准连接条件对象
	 */
//	public static String dataScpFilterOffice(User user, String officeAlias, String userAlias) {
//
//		StringBuilder sqlString = new StringBuilder();
//
//		// 进行权限过滤，多个角色权限范围之间为或者关系。
//		List<String> dataScp = Lists.newArrayList();
//
//		// 超级管理员，跳过权限过滤
//		if (!user.isAdmin()){
//			boolean isDataScpAll = false;
//			for (Role r : user.getRoleList()){
//				for (String oa : StringUtil.split(officeAlias, ",")){
//					if (!dataScp.contains(r.getDataScp()) && StringUtil.isNotBlank(oa)){
//						sqlString.append(" OR " + oa + ".id = '" + user.getOffice().getId() + "'");
//						sqlString.append(" OR " + oa + ".parent_id_list LIKE '" + user.getOffice().getParentIdList() + user.getOffice().getId() + ",%'");
//						dataScp.add(r.getDataScp());
//					}
//				}
//			}
//			// 如果没有全部数据权限，并设置了用户别名，则当前权限为本人；如果未设置别名，当前无权限为已植入权限
//			if (!isDataScpAll){
//				if (StringUtil.isNotBlank(userAlias)){
//					for (String ua : StringUtil.split(userAlias, ",")){
//						/*20190109 add by chenyl for 当前用户id不为null是才进行查询条件拼接*/
//						if(null!=user.getId()){
//							sqlString.append(" OR " + ua + ".id = '" + user.getId() + "'");
//						}
//					}
//				}else {
//					for (String oa : StringUtil.split(officeAlias, ",")){
//						sqlString.append(" OR " + oa + ".id IS NULL");
//					}
//				}
//			}else{
//				// 如果包含全部权限，则去掉之前添加的所有条件，并跳出循环。
//				sqlString = new StringBuilder();
//			}
//		}
//		if (StringUtil.isNotBlank(sqlString.toString())){
//			return " AND (" + sqlString.substring(4) + ")";
//		}
//		return "";
//	}

	public static String dataScpFilterOffice(User user, String officeAlias, String userAlias, List<Object> filterParams) {

		StringBuilder sqlString = new StringBuilder();

		// 进行权限过滤，多个角色权限范围之间为或者关系。
		List<String> dataScp = Lists.newArrayList();

		// 超级管理员，跳过权限过滤
		if (!user.isAdmin()){
			boolean isDataScpAll = false;
			for (Role r : user.getRoleList()){
				for (String oa : StringUtil.split(officeAlias, ",")){
					if (!dataScp.contains(r.getDataScp()) && StringUtil.isNotBlank(oa)){
						sqlString.append(" OR " + oa + ".id = ?");
						sqlString.append(" OR " + oa + ".parent_id_list LIKE ?");
						dataScp.add(r.getDataScp());
						filterParams.add(user.getOffice().getId());
						filterParams.add(user.getOffice().getParentIdList() + user.getOffice().getId() + ",%");
					}
				}
			}
			// 如果没有全部数据权限，并设置了用户别名，则当前权限为本人；如果未设置别名，当前无权限为已植入权限
			if (!isDataScpAll){
				if (StringUtil.isNotBlank(userAlias)){
					for (String ua : StringUtil.split(userAlias, ",")){
						/*20190109 add by chenyl for 当前用户id不为null是才进行查询条件拼接*/
						if(null!=user.getId()){
							sqlString.append(" OR " + ua + ".id = ?");
							filterParams.add(user.getId());
						}
					}
				}else {
					for (String oa : StringUtil.split(officeAlias, ",")){
						sqlString.append(" OR " + oa + ".id IS NULL");
					}
				}
			}else{
				// 如果包含全部权限，则去掉之前添加的所有条件，并跳出循环。
				sqlString = new StringBuilder();
			}
		}
		if (StringUtil.isNotBlank(sqlString.toString())){
			return " AND (" + sqlString.substring(4) + ")";
		}
		return "";
	}

	/**
	 * 数据范围过滤
	 * @param user 当前用户对象，通过“entity.getCurrentUser()”获取
	 * @param rentAlias 租户表别名，多个用“,”逗号隔开。
	 * @param userAlias 用户表别名，多个用“,”逗号隔开，传递空，忽略此参数
	 * @return 标准连接条件对象
	 */
//	public static String dataScpFilterRent(User user, String rentAlias, String userAlias) {
//
//		StringBuilder sqlString = new StringBuilder();
//
//		// 进行权限过滤，多个角色权限范围之间为或者关系。
//		List<String> dataScp = Lists.newArrayList();
//
//		// 超级管理员，跳过权限过滤
//		if (!user.isAdmin()){
//			boolean isDataScpAll = false;
//			for (Role r : user.getRoleList()){
//				for (String oa : StringUtil.split(rentAlias, ",")){
//					if (!dataScp.contains(r.getDataScp()) && StringUtil.isNotBlank(oa)){
//							sqlString.append(" OR " + oa + ".id = '" + user.getRent().getId() + "'");
//							sqlString.append(" OR " + oa + ".parent_id_list LIKE '" + user.getRent().getParentIdList() + user.getRent().getId() + ",%'");
//						dataScp.add(r.getDataScp());
//					}
//				}
//			}
//			// 如果没有全部数据权限，并设置了用户别名，则当前权限为本人；如果未设置别名，当前无权限为已植入权限
//			if (!isDataScpAll){
//				if (StringUtil.isNotBlank(userAlias)){
//					for (String ua : StringUtil.split(userAlias, ",")){
//						/*20190109 add by chenyl for 当前用户id不为null是才进行查询条件拼接*/
//						if(null!=user.getId()){
//							sqlString.append(" OR " + ua + ".id = '" + user.getId() + "'");
//						}
//					}
//				}else {
//					for (String oa : StringUtil.split(rentAlias, ",")){
//						sqlString.append(" OR " + oa + ".id IS NULL");
//					}
//				}
//			}else{
//				// 如果包含全部权限，则去掉之前添加的所有条件，并跳出循环。
//				sqlString = new StringBuilder();
//			}
//		}
//		if (StringUtil.isNotBlank(sqlString.toString())){
//			return " AND (" + sqlString.substring(4) + ")";
//		}
//		return "";
//	}

	public static String dataScpFilterRent(User user, String rentAlias, String userAlias, List<Object> filterParams) {

		StringBuilder sqlString = new StringBuilder();

		// 进行权限过滤，多个角色权限范围之间为或者关系。
		List<String> dataScp = Lists.newArrayList();

		// 超级管理员，跳过权限过滤
		if (!user.isAdmin()){
			boolean isDataScpAll = false;
			for (Role r : user.getRoleList()){
				for (String oa : StringUtil.split(rentAlias, ",")){
					if (!dataScp.contains(r.getDataScp()) && StringUtil.isNotBlank(oa)){
						sqlString.append(" OR " + oa + ".id = ?");
						sqlString.append(" OR " + oa + ".parent_id_list LIKE ?");
						dataScp.add(r.getDataScp());
						filterParams.add(user.getRent().getId());
						filterParams.add(user.getRent().getParentIdList() + user.getRent().getId() + ",%");
					}
				}
			}
			// 如果没有全部数据权限，并设置了用户别名，则当前权限为本人；如果未设置别名，当前无权限为已植入权限
			if (!isDataScpAll){
				if (StringUtil.isNotBlank(userAlias)){
					for (String ua : StringUtil.split(userAlias, ",")){
						/*20190109 add by chenyl for 当前用户id不为null是才进行查询条件拼接*/
						if(null!=user.getId()){
							sqlString.append(" OR " + ua + ".id = ?");
							filterParams.add(user.getId());
						}
					}
				}else {
					for (String oa : StringUtil.split(rentAlias, ",")){
						sqlString.append(" OR " + oa + ".id IS NULL");
					}
				}
			}else{
				// 如果包含全部权限，则去掉之前添加的所有条件，并跳出循环。
				sqlString = new StringBuilder();
			}
		}
		if (StringUtil.isNotBlank(sqlString.toString())){
			return " AND (" + sqlString.substring(4) + ")";
		}
		return "";
	}

	/**
	 * 数据范围过滤
	 * @param user 当前用户对象，通过“entity.getCurrentUser()”获取
	 * @param corporationAlias 法人表别名，多个用“,”逗号隔开。
	 * @param userAlias 用户表别名，多个用“,”逗号隔开，传递空，忽略此参数
	 * @return 标准连接条件对象
	 */
//	public static String dataScpFilterCorporation(User user, String corporationAlias, String userAlias) {
//
//		StringBuilder sqlString = new StringBuilder();
//
//		// 进行权限过滤，多个角色权限范围之间为或者关系。
//		List<String> dataScp = Lists.newArrayList();
//
//		// 超级管理员，跳过权限过滤
//		if (!user.isAdmin()){
//			boolean isDataScpAll = false;
//			for (Role r : user.getRoleList()){
//				for (String oa : StringUtil.split(corporationAlias, ",")){
//					if (!dataScp.contains(r.getDataScp()) && StringUtil.isNotBlank(oa)){
//							sqlString.append(" OR " + oa + ".id = '" + user.getCorporation().getId() + "'");
//							sqlString.append(" OR " + oa + ".parent_id_list LIKE '" + user.getCorporation().getParentIdList() + user.getCorporation().getId() + ",%'");
//						dataScp.add(r.getDataScp());
//					}
//				}
//			}
//			// 如果没有全部数据权限，并设置了用户别名，则当前权限为本人；如果未设置别名，当前无权限为已植入权限
//			if (!isDataScpAll){
//				if (StringUtil.isNotBlank(userAlias)){
//					for (String ua : StringUtil.split(userAlias, ",")){
//						/*20190109 add by chenyl for 当前用户id不为null是才进行查询条件拼接*/
//						if(null!=user.getId()){
//							sqlString.append(" OR " + ua + ".id = '" + user.getId() + "'");
//						}
//					}
//				}else {
//					for (String oa : StringUtil.split(corporationAlias, ",")){
//						sqlString.append(" OR " + oa + ".id IS NOT NULL");
//					}
//				}
//			}else{
//				// 如果包含全部权限，则去掉之前添加的所有条件，并跳出循环。
//				sqlString = new StringBuilder();
//			}
//		}
//		if (StringUtil.isNotBlank(sqlString.toString())){
//			return " AND (" + sqlString.substring(4) + ")";
//		}
//		return "";
//	}

	public static String dataScpFilterCorporation(User user, String corporationAlias, String userAlias, List<Object> filterParams) {

		StringBuilder sqlString = new StringBuilder();

		// 进行权限过滤，多个角色权限范围之间为或者关系。
		List<String> dataScp = Lists.newArrayList();

		// 超级管理员，跳过权限过滤
		if (!user.isAdmin()){
			boolean isDataScpAll = false;
			for (Role r : user.getRoleList()){
				for (String oa : StringUtil.split(corporationAlias, ",")){
					if (!dataScp.contains(r.getDataScp()) && StringUtil.isNotBlank(oa)){
						sqlString.append(" OR " + oa + ".id = ?");
						sqlString.append(" OR " + oa + ".parent_id_list LIKE ?");
						dataScp.add(r.getDataScp());
						filterParams.add(user.getCorporation().getId());
						filterParams.add(user.getCorporation().getParentIdList() + user.getCorporation().getId() + ",%");
					}
				}
			}
			// 如果没有全部数据权限，并设置了用户别名，则当前权限为本人；如果未设置别名，当前无权限为已植入权限
			if (!isDataScpAll){
				if (StringUtil.isNotBlank(userAlias)){
					for (String ua : StringUtil.split(userAlias, ",")){
						/*20190109 add by chenyl for 当前用户id不为null是才进行查询条件拼接*/
						if(null!=user.getId()){
							sqlString.append(" OR " + ua + ".id = ?");
							filterParams.add(user.getId());
						}
					}
				}else {
					for (String oa : StringUtil.split(corporationAlias, ",")){
						sqlString.append(" OR " + oa + ".id IS NULL");
					}
				}
			}else{
				// 如果包含全部权限，则去掉之前添加的所有条件，并跳出循环。
				sqlString = new StringBuilder();
			}
		}
		if (StringUtil.isNotBlank(sqlString.toString())){
			return " AND (" + sqlString.substring(4) + ")";
		}
		return "";
	}

	/**
	 * 数据范围过滤（符合业务表字段不同的时候使用，采用exists方法）
	 * @param entity 当前过滤的实体类
	 * @param sqlMapKey sqlMap的键值，例如设置“dsf”时，调用方法：${sqlMap.sdf}
	 * @param officeWheres office表条件，组成：部门表字段=业务表的部门字段
	 * @param userWheres user表条件，组成：用户表字段=业务表的用户字段
	 * @example
	 * 		dataScpFilter(user, "dsf", "id=a.brch_id", "id=a.crtr");
	 * 		dataScpFilter(entity, "dsf", "code=a.jgdm", "no=a.cjr"); // 适应于业务表关联不同字段时使用，如果关联的不是机构id是code。
	 */
//	public static void dataScpFilter(BaseEntity<?> entity, String sqlMapKey, String officeWheres, String userWheres) {
//
//		User user = entity.getCurrentUser();
//
//		// 如果是超级管理员，则不过滤数据
//		if (user.isAdmin()) {
//			return;
//		}
//
//		// 数据范围（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在部门及以下数据；5：所在部门数据；8：仅本人数据；9：按明细设置）
//		StringBuilder sqlString = new StringBuilder();
//
//		// 获取到最大的数据权限范围
//		String roleId = "";
//		int dataScpInteger = 8;
//		for (Role r : user.getRoleList()){
//			int ds = Integer.valueOf(r.getDataScp());
//			if (ds == 9){
//				roleId = r.getId();
//				dataScpInteger = ds;
//				break;
//			}else if (ds < dataScpInteger){
//				roleId = r.getId();
//				dataScpInteger = ds;
//			}
//		}
//		String dataScpString = String.valueOf(dataScpInteger);
//
//		// 生成部门权限SQL语句
//		for (String where : StringUtil.split(officeWheres, ",")){
//			if (Role.DATA_SCOPE_OFFICE_AND_CHILD.equals(dataScpString)){
//				sqlString.append(" AND EXISTS (SELECT 1 FROM T_SYS_OFFICE");
//				sqlString.append(" WHERE (id = '" + user.getOffice().getId() + "'");
//				sqlString.append(" OR parent_id_list LIKE '" + user.getOffice().getParentIdList() + user.getOffice().getId() + ",%')");
//				sqlString.append(" AND " + where +")");
//			}
//			else if (Role.DATA_SCOPE_OFFICE.equals(dataScpString)){
//				sqlString.append(" AND EXISTS (SELECT 1 FROM T_SYS_OFFICE");
//				sqlString.append(" WHERE id = '" + user.getOffice().getId() + "'");
//				sqlString.append(" AND " + where +")");
//			}
//			else if (Role.DATA_SCOPE_CUSTOM.equals(dataScpString)){
//				sqlString.append(" AND EXISTS (SELECT 1 FROM T_SYS_ROLE_OFFICE ro123456, T_SYS_OFFICE o123456");
//				sqlString.append(" WHERE ro123456.brch_id = o123456.id");
//				sqlString.append(" AND ro123456.role_id = '" + roleId + "'");
//				sqlString.append(" AND o123456." + where +")");
//			}
//		}
//		// 生成个人权限SQL语句
//		for (String where : StringUtil.split(userWheres, ",")){
//			if (Role.DATA_SCOPE_SELF.equals(dataScpString)){
//				sqlString.append(" AND EXISTS (SELECT 1 FROM T_SYS_USER");
//				sqlString.append(" WHERE id='" + user.getId() + "'");
//				sqlString.append(" AND " + where + ")");
//			}
//		}
//
//		System.out.println("dataScpFilter: " + sqlString.toString());
//
//		// 设置到自定义SQL对象
//		entity.getSqlMap().put(sqlMapKey, sqlString.toString());
//
//	}

}
