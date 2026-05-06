package com.adtec.sys.modules.sys.service;

import com.adtec.framework.common.util.*;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.sys.common.persistence.Page;
import com.adtec.sys.common.security.Digests;
import com.adtec.sys.common.security.shiro.session.SessionDAO;
import com.adtec.sys.common.service.BaseService;
import com.adtec.sys.common.utils.Encodes;
import com.adtec.sys.modules.sys.dao.*;
import com.adtec.sys.modules.sys.entity.*;
import com.adtec.sys.modules.sys.security.SystemAuthorizingRealm;
import com.adtec.sys.modules.sys.utils.DictUtils;
import com.adtec.sys.modules.sys.utils.LogUtils;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.adtec.sys.modules.sys.vo.PermissionDTO;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.adtec.sys.modules.sys.utils.UserUtils.USER_CACHE;
import static com.adtec.sys.modules.sys.utils.UserUtils.USER_CACHE_LOGIN_NAME_;

/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 * 
 * @version 2013-12-05
 */
@Service
@Transactional(readOnly = true)
public class SystemService extends BaseService {

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;

	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private OfficeDao officeDao;
	@Autowired
	private SessionDAO sessionDao;
	@Autowired
	private SystemAuthorizingRealm systemRealm;
	@Autowired
	private RentDao rentDao;
	@Autowired
	private PermissionDao permissionDao;
	@Autowired
	private DictDao dictDao;
	@Autowired
	private AreaDao areaDao;
	@Autowired
	private SysPermissionWeightDao sysPermissionWeightDao;
    @Autowired
    private CorporationDao corporationDao;
    @Autowired
    private UserAcctDao userAcctDao;
    @Autowired
    private UserTntDao userTntDao;
	@Autowired
	private BusiUserDao busiUserDao;

	public SessionDAO getSessionDao() {
		return sessionDao;
	}

	// -- User Service --//

	/**
	 * 获取用户
	 * 
	 * @param id
	 * @return
	 */
	public User getUser(String id) {
		return UserUtils.get(id);
	}

	/**
	 * 根据登录名获取用户
	 * 
	 * @param loginName
	 * @return
	 */
	public User getUserByLoginName(String loginName) {
		return UserUtils.getByLoginName(loginName);
	}

	public User getUserByLoginNameNoCache(String loginName) {
		return UserUtils.getByLoginNameNoCache(loginName);
	}

	/**
	 * 根据姓名获取用户
	 * 
	 * @param name
	 * @return
	 */
	public User getUserByName(String name) {
		return UserUtils.getByName(name);
	}

	public User getUserByUserNo(String userNo) {
		return UserUtils.getByUserNo(userNo);
	}

	public Page<User> findUser(Page<User> page, User user) {
		// 生成数据权限过滤条件（dsf为dataScpFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		List<Object> filterParams = Lists.newArrayList();
		user.getSqlMap().put("dsf", dataScpFilterOffice(UserUtils.getUser(), "o", "a", filterParams));
		user.getSqlMap().put("dsfr", dataScpFilterRent(UserUtils.getUser(), "orent", "a", filterParams));
		user.getSqlMap().put("dsfc", dataScpFilterCorporation(UserUtils.getUser(), "co", "a", filterParams));
		// 设置分页参数
		user.setPage(page);

		// 执行分页查询
		List<User> list = userDao.findList(user, filterParams);

		page.setCount(list.size());
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize();
		int toIndex = page.getPageNo() * page.getPageSize();
		if (-1 == page.getPageSize()) {
			toIndex = (int) page.getCount();
		}
		List<User> userList = list.subList(fromIndex, toIndex > (int) page.getCount() ? (int) page.getCount() : toIndex);
		page.setList(userList);

		for (User u : userList) {
			u.setRoleTpStr(DictUtils.getDictLabel(u.getRoleTp(), "ROLE_TP", ""));
		}
		return page;
	}

	public Page<User> findUserAll(Page<User> page, User user) {
		// 生成数据权限过滤条件（dsf为dataScpFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		List<Object> filterParams = Lists.newArrayList();
		user.getSqlMap().put("dsf", dataScpFilterOffice(UserUtils.getUser(), "o", "a", filterParams));
		user.getSqlMap().put("dsfr", dataScpFilterRent(UserUtils.getUser(), "orent", "a", filterParams));
		user.getSqlMap().put("dsfc", dataScpFilterCorporation(UserUtils.getUser(), "co", "a", filterParams));
		// 设置分页参数
		user.setPage(page);

		List<User> list = userDao.findList(user, filterParams);

		page.setList(list);

		return page;
	}

	/**
	 * 无分页查询人员列表
	 *
	 * @param user
	 * @return
	 */
	public List<User> findUser(User user) {
		// 生成数据权限过滤条件（dsf为dataScpFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		List<Object> filterParams = Lists.newArrayList();
		user.getSqlMap().put("dsf", dataScpFilter(UserUtils.getUser(), "o", "a", filterParams));
		List<User> list = userDao.findList(user, filterParams);
		return list;
	}

	/**
	 * 无分页查询人员列表
	 *
	 * @param user
	 * @return
	 */
	public List<User> findUserByOfficeScope(User user) {
		// 生成数据权限过滤条件（dsf为dataScpFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		List<Object> filterParams = Lists.newArrayList();
		user.getSqlMap().put("dsf", dataScpFilterOffice(UserUtils.getUser(), "o", "a", filterParams));
		List<User> list = userDao.findList(user, filterParams);
		return list;
	}

	public Page<User> findFilterUser(Page<User> page, User user) {
		page = findUser(page, user);
		List<User> filterUser = Lists.newArrayList();
		List<User> list = page.getList();
		if (list.size() > 0) {
			for (User u : list) {
//				boolean flag = u.getOffice().getId().equals(user.getOffice().getId());
//				if (flag) {
					filterUser.add(u);
//				}
			}
		}
		page.setList(filterUser);
		return page;
	}

	 /**
     * 通过部门ID获取用户列表，仅返回用户id和name（树查询用户时用）
     *
     * @param officeId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<User> findUserByOfficeId(String officeId) {
        List<User> list = (List<User>) CacheUtil.get(UserUtils.USER_CACHE,
                UserUtils.USER_CACHE_LIST_BY_BRCH_ID_ + officeId);
        if (list == null) {
            User user = new User();
            user.setOffice(new Office(officeId));
            list = userDao.findUserByOfficeId(user);
            CacheUtil.put(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_BRCH_ID_ + officeId, list);
        }
        return list;
    }
    
    /**
     * 通过租户ID获取用户列表，仅返回用户id和name（树查询用户时用）
     * 
     * @param rentId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<User> findUserByRentId(String rentId) {
        List<User> list = (List<User>) CacheUtil.get(UserUtils.USER_CACHE,
                UserUtils.USER_CACHE_LIST_BY_RENT_ID_ + rentId);
        if (list == null) {
            list = userDao.findUserByRentId(new Rent(rentId));
            CacheUtil.put(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_RENT_ID_ + rentId, list);
        }
        return list;
    }
    
    /**
     * 通过法人ID获取用户列表，仅返回用户id和name（树查询用户时用）
     * @param corId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<User> findUserByCorId(String corId) {
        List<User> list = (List<User>) CacheUtil.get(UserUtils.USER_CACHE,
                UserUtils.USER_CACHE_LIST_BY_COR_ID_ + corId);
        if (list == null) {
            list = userDao.findUserByCorId(new Corporation(corId));
            CacheUtil.put(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_COR_ID_ + corId, list);
        }
        return list;
    }
	
	/**
	 * 通过部门ID获取用户列表，仅返回用户id和name（树查询用户时用）
	 * 
	 * @param brchId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> findUserByBrchId(String brchId) {
		List<User> list = (List<User>) CacheUtil.get(UserUtils.USER_CACHE,
				UserUtils.USER_CACHE_LIST_BY_BRCH_ID_ + brchId);
		if (list == null) {
			User user = new User();
			user.setOffice(new Office(brchId));
			list = userDao.findUserByBrchId(user);
			CacheUtil.put(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_BRCH_ID_ + brchId, list);
		}
		return list;
	}

	public List<User> findUserByTntId(Rent rent) {
		return userDao.findUserByTntId(rent);
	}

	public List<User> findUserByLegaId(Corporation corporation) {
		return userDao.findUserByLegaId(corporation);
	}

	/**
     * 通过角色ID获取用户列表，仅返回用户id和name（树查询用户时用）
     * 
     * @param roleId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<User> findUserByRoleId(String roleId) {
        List<User> list = (List<User>) CacheUtil.get(UserUtils.USER_CACHE,
                UserUtils.USER_CACHE_LIST_BY_ROLE_ID_ + roleId);
        if (list == null) {
            list = userDao.findUserByRoleId(roleId);
            CacheUtil.put(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_ROLE_ID_ + roleId, list);
        }
        return list;
    }	

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void saveUser(User user) {
		//防止导入数据变成科学记数法，这里转化一下数据
		if(!StringUtil.isBlank(user.getPhoneNo())){
			try {
				BigDecimal bd = new BigDecimal(user.getPhoneNo());
				user.setPhoneNo(bd.toPlainString());
			} catch (Exception e) {
				logger.error("手机号码转化异常"+user.getPhoneNo());
			}
		}
		if (StringUtil.isBlank(user.getId())) {
			user.preInsert();
			userDao.insert(user);
		} else {
			// 更新用户数据
			user.preUpdate();
			userDao.update(user);
		}
		if (StringUtil.isNotBlank(user.getId())) {
			// 更新用户与角色关联
			userDao.deleteUserRole(user, null);
			if (user.getRoleList() != null && user.getRoleList().size() > 0) {
				userDao.insertUserRole(user);
			}

			// 清除缓存
			CacheUtil.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_BRCH_ID_ + user.getOffice().getId());
			CacheUtil.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_RENT_ID_ + user.getRent().getId());
			CacheUtil.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_COR_ID_ + user.getCorporation().getId());
			for (String roleId : user.getRoleIdList()) {
				CacheUtil.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_ROLE_ID_ + roleId);
			}
			// 清除用户缓存
			UserUtils.clearCache(user);
			// 清除权限缓存
			systemRealm.clearAllCachedAuthorizationInfo();
		}
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void saveUserToRole(Role role, User user) {
		// 更新用户与角色关联
		userDao.saveUserRole(user.getId(), role.getId());
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void updateUserInfo(User user) {
		user.preUpdate();
		userDao.updateUserInfo(user);
		// 清除用户缓存
		UserUtils.clearCache(user);
		// 清除权限缓存
		systemRealm.clearAllCachedAuthorizationInfo();
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void deleteUser(User user) {
		userDao.delete(user);
		userDao.deleteUserRole(user, null);
		// 清除用户缓存
		UserUtils.clearCache(user);
		// 清除权限缓存
		systemRealm.clearAllCachedAuthorizationInfo();
		// 删除用户账户信息
		userAcctDao.delByUserId(user.getId());
		// 删除租户授权信息
		userTntDao.delByUserId(user.getId());
		// 删除商户用户的商户业务映射表
		if("merchans".equals(user.getRoleList().get(0).getEngName())){
			busiUserDao.delete(user);
		}
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void updatePasswordById(String id, String loginName, String newPassword) {
		User user = new User(id);
		user.setPwd(entryptPassword(newPassword));
		System.out.println("---ps:" + entryptPassword(newPassword));
		userDao.updatePasswordById(user);
		// 清除用户缓存
		user.setLoginName(loginName);
		UserUtils.clearCache(user);
		// 清除权限缓存
		systemRealm.clearAllCachedAuthorizationInfo();
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void updateUserLoginInfo(User user) {
		// 保存上次登录信息
		user.setOldLoginIp(user.getLoginIp());
		user.setOldLoginDate(user.getLoginDate());
		// 更新本次登录信息
//		user.setLoginIp(UserUtils.getSession().getHost());
		//解决首次登录修改密码不正确 等待若干时间后重新登录不需要再修改密码
		if(!StringUtil.isEmpty(user.getLoginIp())){
			user.setLoginIp(UserUtils.getSession().getHost());
		}
		user.setLoginDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		userDao.updateLoginInfo(user);
	}

	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public static String entryptPassword(String plainPassword) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
	}

	/**
	 * 验证密码
	 * 
	 * @param plainPassword 明文密码
	 * @param password      密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		byte[] salt = Encodes.decodeHex(password.substring(0, 16));
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return password.equals(Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword));
	}

	// -- Rent Service --//
	public Rent getRent(String id) {
		return rentDao.get(id);
	}
	
    public Corporation getCorporation(String id) {
        return corporationDao.get(id);
    }

	public Rent getRentByName(String name) {
		Rent r = new Rent();
		r.setName(name);
		return rentDao.getByName(r);
	}

	public Rent getRentByEngName(String engName) {
		Rent r = new Rent();
		r.setEngName(engName);
		return rentDao.getByEngName(r);
	}

	public List<Rent> findAllRent() {
		return UserUtils.getRentList();
	}

	/**
	 * 
	 * @return add by chenyl 2017-08-10
	 */
	public List<User> findAllUser() {
		return UserUtils.getUserList();
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void saveRent(Rent rent) {
		if (StringUtil.isBlank(rent.getId())) {
			rent.preInsert();
			rentDao.insert(rent);
			sysPermissionWeightDao.initMenuPermissionToRent(rent.getId());
		} else {
			rent.preUpdate();
			rentDao.update(rent);
		}
		// 清除用户租户缓存
		UserUtils.removeCache(UserUtils.CACHE_RENT_LIST);

		// 更新租户字典列表信息
		if (null != CacheUtil.get(DictUtils.CACHE_DICT_MAP)) {
			Map<String, List<Dict>> dictMap = (Map<String, List<Dict>>) CacheUtil.get(DictUtils.CACHE_DICT_MAP);
			dictMap.remove(DictUtils.RENT_DICT);
		}
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void deleteRent(Rent rent) {
		rent = rentDao.get(rent);
		// 删除租户表信息
		rentDao.delete(rent);
		// 清除用户租户缓存
		UserUtils.removeCache(UserUtils.CACHE_RENT_LIST);
		Object msRentService = null;
		try {
			msRentService = SpringContextHolder.getBean("msRentService");
		} catch (Exception e) {
			logger.warn("该版本中不包含微服务模块！");
		}
		if (null != msRentService) {
			try {
				Object[] param = new Object[1];
				param[0] = rent;
				ClassUtil.invokeMethodByName(msRentService, "cancleTenant", param);
			} catch (Exception e) {
				logger.error("反射调用msRentService服务的cancleTenant方法异常！");
				throw new BaseException(SysErr.E_MESSAGE, "反射调用msRentService服务的cancleTenant方法异常！" + e.getMessage());
			}
		}
	}

	public Role getRole(String id) {
		return roleDao.get(id);
	}

	public Role getRoleByName(String name) {
		Role r = new Role();
		r.setName(name);
		return roleDao.getByName(r);
	}

	public Role getRoleByEngName(String engName) {
		Role r = new Role();
		r.setEngName(engName);
		return roleDao.getByEngName(r);
	}

	public List<Role> findAllRoleWithUser(Role role) {
		return roleDao.findAllRoleWithUser(role);
	}

	public List<Role> findAllRole() {
		return UserUtils.getRoleList();
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void saveRole(Role role) {
		if (StringUtil.isBlank(role.getId())) {
			role.preInsert();
			roleDao.insert(role);
			sysPermissionWeightDao.initMenuPermissionToRole(role.getId());
		} else {
			role.preUpdate();
			roleDao.update(role);

			// 清除角色下的用户的缓存
			List<User> list = findUserByRoleId(role.getId());
			for (User u : list) {
				CacheUtil.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + u.getLoginName());
			}
		}

		// 清除用户角色缓存
		UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
		// 清除权限缓存
		systemRealm.clearAllCachedAuthorizationInfo();
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void deleteRole(Role role) {
		roleDao.delete(role);
		// 更新用户与角色关联
		roleDao.deleteUserRole(role);
		// 清除用户角色缓存
		UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
		// 清除权限缓存
		systemRealm.clearAllCachedAuthorizationInfo();
	}

	/*
	 * @Transactional(readOnly = false) public Boolean outUserInRole(Role role, User
	 * user) { List<Role> roles = user.getRoleList(); for (Role e : roles){ if
	 * (e.getId().equals(role.getId())){ roles.remove(e); saveUser(user); return
	 * true; } } return false; }
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Boolean outUserInRole(User user, String roleId) {
		int result = userDao.deleteUserRole(user, roleId);
		return result > 0;
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public User assignUserToRole(Role role, User user) {
		if (user == null) {
			return null;
		}
		saveUserToRole(role, user);
		return user;
	}

	public List<String> getUserIdFromUserRole(String roleId) {
		return userDao.getUserIdFromUserRole(roleId);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public int deleteUserRole(Role role) {
		return roleDao.deleteUserRole(role);
	}

	public Menu getMenu(String id) {
		return menuDao.get(id);
	}

	public Menu getMenuByName(String name) {
		return menuDao.getByName(name);
	}

	public List<Menu> findAllMenu() {
		return menuDao.findAllList();
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void saveMenu(Menu menu) throws Exception {

		// 获取父节点实体
		if (menu.getParentName() != null) {
			Menu temp = this.getMenuByName(menu.getParentName());
			if (temp.getId() == null) {
				throw new Exception("父菜单名称不存在！");
			}
			menu.setParent(temp);
		} else {
			menu.setParent(this.getMenu(menu.getParent().getId()));
		}

		// 获取修改前的parentIds，用于更新子节点的parentIds
		String oldParentIds = menu.getParentIdList();

		// 设置新的父节点串
		menu.setParentIdList(menu.getParent().getParentIdList() + menu.getParent().getId() + ",");

		if (menu.getParentIdList().split("\\,").length > 4) {
			throw new BaseException(SysErr.E_MESSAGE, "不能添加当前级别的菜单！");
		}

		// 保存或更新实体
		if (StringUtil.isBlank(menu.getId())) {
			menu.preInsert();
			if (checkSameParentMenuName(menu)) {
				throw new BaseException(SysErr.E_MESSAGE, "同级菜单中有相同的菜单名称！");
			}
			menuDao.insert(menu);

			// 增加菜单时，增加菜单拥有者的权限组
			User user = UserUtils.getUser();
			List<Menu> menuList = new ArrayList<Menu>();
			menuList.add(menu);

			// 创建二级菜单时，增加当前权限组，一级菜单"1"的所有权、转授权、使用权
			if ("1".equals(menu.getParent().getId())) {
				permissionDao.insertOneLevelPermission(user);
			}

			// 所属法人权限组
			PermissionDTO corporationVo = new PermissionDTO();
			corporationVo.setMenuList(menuList);
			corporationVo.setId(user.getCorporation().getId());
			corporationVo.setAuthTp("use");
			permissionDao.insertPermissionMenu(corporationVo, "LEGA_ID");
			corporationVo.setAuthTp("transfer");
			permissionDao.insertPermissionMenu(corporationVo, "LEGA_ID");
			corporationVo.setAuthTp("own");
			permissionDao.insertPermissionMenu(corporationVo, "LEGA_ID");

			// 所属租户权限组
			PermissionDTO rentVO = new PermissionDTO();
			rentVO.setMenuList(menuList);
			rentVO.setId(user.getRent().getId());
			rentVO.setAuthTp("use");
			permissionDao.insertPermissionMenu(rentVO, "TNT_ID");
			rentVO.setAuthTp("transfer");
			permissionDao.insertPermissionMenu(rentVO, "TNT_ID");
			rentVO.setAuthTp("own");
			permissionDao.insertPermissionMenu(rentVO, "TNT_ID");

			// 所属机构权限组
			PermissionDTO officeVO = new PermissionDTO();
			officeVO.setMenuList(menuList);
			officeVO.setId(user.getOffice().getId());
			officeVO.setAuthTp("use");
			permissionDao.insertPermissionMenu(officeVO, "BRCH_ID");
			officeVO.setAuthTp("transfer");
			permissionDao.insertPermissionMenu(officeVO, "BRCH_ID");
			officeVO.setAuthTp("own");
			permissionDao.insertPermissionMenu(officeVO, "BRCH_ID");

			// 所属角色权限组
			PermissionDTO roleVO = new PermissionDTO();
			roleVO.setMenuList(menuList);
			for (String roleId : user.getRoleIdList()) {
				roleVO.setId(roleId);
				roleVO.setAuthTp("use");
				permissionDao.insertPermissionMenu(roleVO, "ROLE_ID");
				roleVO.setAuthTp("transfer");
				permissionDao.insertPermissionMenu(roleVO, "ROLE_ID");
				roleVO.setAuthTp("own");
				permissionDao.insertPermissionMenu(roleVO, "ROLE_ID");
			}
		} else {
			menu.preUpdate();
			if (checkSameParentMenuName(menu)) {
				throw new BaseException(SysErr.E_MESSAGE, "同级菜单中有相同的菜单名称！");
			}
			menuDao.update(menu);
		}

		// 更新子节点 parentIds
		Menu m = new Menu();
		m.setParentIdList("%," + menu.getId() + ",%");
		List<Menu> list = menuDao.findByParentIdsLike(m);
		for (Menu e : list) {
			e.setParentIdList(e.getParentIdList().replace(oldParentIds, menu.getParentIdList()));
			menuDao.updateParentIds(e);
		}

		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
		// 清除权限缓存
		systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtil.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void saveMenuAuth(Menu menu) {

		// 获取父节点实体
		if (menu.getParentName() != null) {
			menu.setParent(this.getMenuByName(menu.getParentName()));
		} else {
			menu.setParent(this.getMenu(menu.getParent().getId()));
		}

		// 获取修改前的parentIds，用于更新子节点的parentIds
		String oldParentIds = menu.getParentIdList();

		// 设置新的父节点串
		menu.setParentIdList(menu.getParent().getParentIdList() + menu.getParent().getId() + ",");

		if (menu.getParentIdList().split("\\,").length != 5) {
			throw new BaseException(SysErr.E_MESSAGE, "请选择正确的上级菜单添加按钮权限！");
		}

		// 保存或更新实体
		if (StringUtil.isBlank(menu.getId())) {
			menu.preInsert();
			if (checkSameParentMenuName(menu)) {
				throw new BaseException(SysErr.E_MESSAGE, "同级按钮中有相同的按钮名称！");
			}
			menu.setDpyFlg("0");
			menuDao.insert(menu);

			// 增加菜单时，增加菜单拥有者的权限组
			User user = UserUtils.getUser();
			List<Menu> menuList = new ArrayList<Menu>();
			menuList.add(menu);

			// 创建二级菜单时，增加当前权限组，一级菜单"1"的所有权、转授权、使用权
			if ("1".equals(menu.getParent().getId())) {
				permissionDao.insertOneLevelPermission(user);
			}

			// 所属法人权限组
			PermissionDTO corporationVo = new PermissionDTO();
			corporationVo.setMenuList(menuList);
			corporationVo.setId(user.getCorporation().getId());
			corporationVo.setAuthTp("use");
			permissionDao.insertPermissionMenu(corporationVo, "LEGA_ID");
			corporationVo.setAuthTp("transfer");
			permissionDao.insertPermissionMenu(corporationVo, "LEGA_ID");
			corporationVo.setAuthTp("own");
			permissionDao.insertPermissionMenu(corporationVo, "LEGA_ID");

			// 所属租户权限组
			PermissionDTO rentVO = new PermissionDTO();
			rentVO.setMenuList(menuList);
			rentVO.setId(user.getRent().getId());
			rentVO.setAuthTp("use");
			permissionDao.insertPermissionMenu(rentVO, "TNT_ID");
			rentVO.setAuthTp("transfer");
			permissionDao.insertPermissionMenu(rentVO, "TNT_ID");
			rentVO.setAuthTp("own");
			permissionDao.insertPermissionMenu(rentVO, "TNT_ID");

			// 所属机构权限组
			PermissionDTO officeVO = new PermissionDTO();
			officeVO.setMenuList(menuList);
			officeVO.setId(user.getOffice().getId());
			officeVO.setAuthTp("use");
			permissionDao.insertPermissionMenu(officeVO, "BRCH_ID");
			officeVO.setAuthTp("transfer");
			permissionDao.insertPermissionMenu(officeVO, "BRCH_ID");
			officeVO.setAuthTp("own");
			permissionDao.insertPermissionMenu(officeVO, "BRCH_ID");

			// 所属角色权限组
			PermissionDTO roleVO = new PermissionDTO();
			roleVO.setMenuList(menuList);
			for (String roleId : user.getRoleIdList()) {
				roleVO.setId(roleId);
				roleVO.setAuthTp("use");
				permissionDao.insertPermissionMenu(roleVO, "ROLE_ID");
				roleVO.setAuthTp("transfer");
				permissionDao.insertPermissionMenu(roleVO, "ROLE_ID");
				roleVO.setAuthTp("own");
				permissionDao.insertPermissionMenu(roleVO, "ROLE_ID");
			}
		} else {
			menu.preUpdate();
			if (checkSameParentMenuName(menu)) {
				throw new BaseException(SysErr.E_MESSAGE, "同级按钮中有相同的按钮名称！");
			}
			menuDao.update(menu);
		}

		// 更新子节点 parentIds
		Menu m = new Menu();
		m.setParentIdList("%," + menu.getId() + ",%");
		List<Menu> list = menuDao.findByParentIdsLike(m);
		for (Menu e : list) {
			e.setParentIdList(e.getParentIdList().replace(oldParentIds, menu.getParentIdList()));
			menuDao.updateParentIds(e);
		}

		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
		// 清除权限缓存
		systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtil.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void updateMenuSort(Menu menu) {
		menuDao.updateSort(menu);
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
		// 清除权限缓存
		systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtil.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void deleteMenu(Menu menu) {
		menuDao.delete(menu);
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
		// 清除权限缓存
		systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtil.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}

	/**
	 * 获取Key加载信息
	 */
	public static boolean printKeyLoadMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append("\r\n======================================================================\r\n");
		sb.append("\r\n    欢迎使用 " + ParamUtil.getString("productName") + "  - Powered By "+ParamUtil.getString("project.name")+"    \r\n");
		sb.append("\r\n======================================================================\r\n");
		System.out.println(sb.toString());
		return true;
	}
	
	@PostConstruct
	public void initSystem(){
		/*20200322 add by chenyl for 新增系统初始后的方法，进行相关数据的缓存*/
		// 1、装载页面参数到缓存
		if(logger.isInfoEnabled()){
			logger.info("装载页面参数到缓存开始...");
		}
		// key：页面参数类型, value：对应页面参数类型的数据列表
		Map<String, List<Dict>> dictMap = (Map<String, List<Dict>>) CacheUtil.get(DictUtils.CACHE_DICT_MAP);
		int dictNum = 0;
		if (dictMap == null) {
			dictMap = Maps.newHashMap();
			for (Dict dict : dictDao.findAllList()) {
				List<Dict> dictList = dictMap.get(dict.getDictTp());
				if (dictList != null) {
					dictNum += dictList.size();
					dictList.add(dict);
				} else {
					dictMap.put(dict.getDictTp(), Lists.newArrayList(dict));
				}
			}
			CacheUtil.put(DictUtils.CACHE_DICT_MAP, dictMap);
		}
		if(logger.isInfoEnabled()){
			logger.info("装载页面参数到缓存结束,总记录数["+dictNum+"]...");
		}
		
		// 2、装载区域参数到缓存
		if (logger.isInfoEnabled()) {
			logger.info("装载区域参数到缓存开始...");
		}
		List<Area> areaList = (List<Area>)CacheUtil.get(UserUtils.CACHE_AREA_LIST);
		if (areaList == null){
			areaList = areaDao.findAllList();
			CacheUtil.put(UserUtils.CACHE_AREA_LIST, areaList);
		}
		// 初始化对应的areaTree
		UserUtils.getAreaTree("");
		if(logger.isInfoEnabled()){
			logger.info("装载区域参数到缓存结束,总记录数["+areaList.size()+"]...");
		}
		
		/*添加主动释放当前启动线程中使用到的数据库操作*/
		DBSessionFactory.clear();
	}

	/**
	 * @param rent
	 */
	public void regTenant(Rent rent) {
		Object msRentService = null;
		try {
			msRentService = SpringContextHolder.getBean("msRentService");
		} catch (Exception e) {
			logger.warn("该版本中不包含微服务模块！");
		}
		if (null != msRentService) {
			try {
				Object[] param = new Object[1];
				param[0] = rent;
				ClassUtil.invokeMethodByName(msRentService, "regTenant", param);
			} catch (Exception e) {
				logger.error("反射调用msRentService服务的regTenant方法异常！" + e.getMessage());
				throw new BaseException(SysErr.E_MESSAGE, "反射调用msRentService服务的regTenant方法异常！" + e.getMessage());
			}
		}
	}

	/**
	 * @param rent
	 */
	public void uptTenant(Rent rent) {
		Object msRentService = null;
		try {
			msRentService = SpringContextHolder.getBean("msRentService");
		} catch (Exception e) {
			logger.warn("该版本中不包含微服务模块！");
		}
		if (null != msRentService) {
			try {
				Object[] param = new Object[1];
				param[0] = rent;
				ClassUtil.invokeMethodByName(msRentService, "uptTenant", param);
			} catch (Exception e) {
				logger.error("反射调用msRentService服务的uptTenant方法异常！" + e.getMessage());
				throw new BaseException(SysErr.E_MESSAGE, "反射调用msRentService服务的uptTenant方法异常！" + e.getMessage());
			}
		}
	}

	/**
	 * @param rentList
	 */
	public String regMultiTenant(List<Rent> rentList) {
		try {
			Object msRentService = SpringContextHolder.getBean("msRentService");
			Object[] param = new Object[1];
			param[0] = rentList;
			return (String) ClassUtil.invokeMethodByName(msRentService, "regMultiTenant", param);
		} catch (Exception e) {
			logger.error("反射调用msRentService服务的regMultiTenant方法异常！", e);
		}
		return null;
	}

	/**
	 * 获取当前用户有权限访问的角色
	 *
	 * @author chenyl
	 * @date 2018-09-27
	 * @return
	 */
	public List<Role> findRoleList(Role role) {
		List<Role> roleList;
		User user = UserUtils.getUser();
		if (user.isAdmin()) {
			roleList = roleDao.findList(role);
		} else {
			roleList = roleDao.findListExceptAdmin(role);
		}
		for (Role temp : roleList) {
			if (user.isManager()) {
				if (temp.getRoleTp().equals("manager")) {
					if (user.isAdmin()) {
						temp.setDataSwitchFlg("1");
					} else {
						temp.setDataSwitchFlg("0");
					}
				} else {
					temp.setDataSwitchFlg("1");
				}
			} else {
				temp.setDataSwitchFlg("0");
			}
		}
		return roleList;
	}

	/**
	 * 获取当前用户授权用户列表
	 *
	 * @author chenyl
	 * @date 2018-10-09
	 * @param user
	 * @param request
	 * @param response
	 * @return
	 */
	public Page<User> userList(User user, HttpServletRequest request, HttpServletResponse response) {
		Page<User> page = findUser(new Page<User>(request, response), user);
		List<User> userList = page.getList();
		if (UserUtils.getUser().isAdmin()) {
			return page;
		} else {
			List<User> list = Lists.newArrayList();
			for (User u : userList) {
				if (!u.isAdmin()) {
					list.add(u);
				}
			}
			return page;
		}
	}

	/**
	 * 根据角色id查询角色
	 *
	 * @author chenyl
	 * @date 2018-10-09
	 * @param role
	 * @return
	 */
	public Role findRoleByRoleId(Role role) {
		return roleDao.findRole(role);
	}

	public List<Role> selectRoleByBrchId(String brchId) {
		return roleDao.selectRoleByBrchId(brchId);
	}

	/**
	 * 根据租户英文名查询下级租户
	 * 
	 * @author chenyl
	 * @date 2019-03-20
	 * @param rentEnName 租户英文名
	 * @return
	 */
	public List<Rent> getLowerRentsByEnName(String rentEnName) {
		Rent rent = new Rent();
		rent.setEngName(rentEnName);
		rent = rentDao.getByEngName(rent);
		return rentDao.findChildRentList(rent);
	}

	/**
	 * 根据租户所属机构的机构ID查询下级租户
	 * 
	 * @param rent
	 * @return
	 */
	public List<Rent> findChildRentList(Rent rent) {
		return rentDao.findChildRentList(rent);
	}
	/**
	 * 获取当前租户的所有下级租户列表（一直到叶子节点）
	 * @param rent
	 * @return
	 */
	public List<Rent> findChildsRentList(Rent rent) {
		return rentDao.findChildsRentList(rent);
	}

	/**
	 * 校验同父节点的菜单名，不能重复
	 * @param menu
	 * @return
	 */
	private boolean checkSameParentMenuName(Menu menu) {
		List<Menu> list = menuDao.findAllList();
		for (Menu temp : list) {
			if (temp.getParentId().equals(menu.getParentId()) && temp.getName().equals(menu.getName())) {
				if (temp.getId().equals(menu.getId())) {
					return false;
				} else {
					return true;
				}
			}
		}
		return false;
	}

}
