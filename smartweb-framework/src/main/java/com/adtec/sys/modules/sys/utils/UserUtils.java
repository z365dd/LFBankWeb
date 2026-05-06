package com.adtec.sys.modules.sys.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.adtec.framework.common.util.CacheUtil;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.framework.common.util.StringUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.sys.common.service.BaseService;
import com.adtec.sys.modules.sys.dao.AreaDao;
import com.adtec.sys.modules.sys.dao.CorporationDao;
import com.adtec.sys.modules.sys.dao.MenuDao;
import com.adtec.sys.modules.sys.dao.OfficeDao;
import com.adtec.sys.modules.sys.dao.RentDao;
import com.adtec.sys.modules.sys.dao.RoleDao;
import com.adtec.sys.modules.sys.dao.UserAcctDao;
import com.adtec.sys.modules.sys.dao.UserDao;
import com.adtec.sys.modules.sys.entity.Area;
import com.adtec.sys.modules.sys.entity.Corporation;
import com.adtec.sys.modules.sys.entity.Menu;
import com.adtec.sys.modules.sys.entity.Office;
import com.adtec.sys.modules.sys.entity.Rent;
import com.adtec.sys.modules.sys.entity.Role;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.entity.UserAcctDO;
import com.adtec.sys.modules.sys.entity.UserAcctDO.UserAcctType;
import com.adtec.sys.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 用户工具类
 * 
 * @version 2013-12-05
 */
public class UserUtils {

	private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);
	private static RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);
	private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);
	private static AreaDao areaDao = SpringContextHolder.getBean(AreaDao.class);
	private static OfficeDao officeDao = SpringContextHolder.getBean(OfficeDao.class);
	private static RentDao rentDao = SpringContextHolder.getBean(RentDao.class);
	private static CorporationDao corporationDao = SpringContextHolder.getBean(CorporationDao.class);
	private static UserAcctDao userAcctDao = SpringContextHolder.getBean(UserAcctDao.class);

	public static final String USER_CACHE = "userCache";
	public static final String USER_CACHE_ID_ = "id_";
	public static final String USER_CACHE_LOGIN_NAME_ = "ln";
	public static final String USER_CACHE_NAME_ = "name";
	public static final String USER_CACHE_LIST_BY_BRCH_ID_ = "oid_";
	public static final String USER_CACHE_LIST_BY_RENT_ID_ = "rentId_";//租户
    public static final String USER_CACHE_LIST_BY_COR_ID_ = "corId_";//法人
	public static final String USER_CACHE_LIST_BY_ROLE_ID_ = "rid_";
	public static final String CACHE_USER_ID_LIST = "userIdList_";

	public static final String CACHE_RENT_LIST = "rentList";
	public static final String CACHE_CORPORATION_LIST = "corporationList";
	public static final String CACHE_USER_LIST = "userList";
	public static final String CACHE_ROLE_LIST = "roleList";
	public static final String CACHE_MENU_LIST = "menuList";
	public static final String CACHE_MENU_LIST_SHOW = "menuListShow";
	public static final String CACHE_AREA_LIST = "areaList";
	public static final String CACHE_AREA_TREE = "areaTree";
	public static final String CACHE_OFFICE_LIST = "officeList";
	public static final String CACHE_OFFICE_ALL_LIST = "officeAllList";

	/**
	 * 根据ID获取用户
	 * @param id
	 * @return 取不到返回null
	 */
	public static User get(String id) {
		User user = (User) CacheUtil.get(USER_CACHE, USER_CACHE_ID_ + id);
		if (user == null) {
			user = userDao.get(id);
			if (user == null) {
				return null;
			}
			user.setRoleList(roleDao.findList(new Role(user)));
			CacheUtil.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
			CacheUtil.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
		}
		return user;
	}
	
	/**
	 * 刷新用户缓存信息
	 * @param user
	 */
	public static void  refreshUserCache(User user){
		if(user != null){
			CacheUtil.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
			CacheUtil.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
		}
	}

	/**
	 * 获取法人编号
	 * @return
	 */
	public static String getLawNo() {
		return getUser().getCorporation().getLegaNo();
	}

	/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @return 取不到返回null
	 */
	public static User getByLoginName(String loginName){
		User user = (User)CacheUtil.get(USER_CACHE, USER_CACHE_LOGIN_NAME_ + loginName);
		if (user == null){
			user = userDao.getByLoginName(new User(null, loginName));
			if (user == null){
				return null;
			}
			user.setRoleList(roleDao.findList(new Role(user)));
			CacheUtil.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
			CacheUtil.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
		}
		return user;
	}

	public static User getByLoginNameNoCache(String loginName){
		return userDao.getByLoginName(new User(null, loginName));
	}

	/**
	 * 根据登录名获取用户
	 * @param name
	 * @return 取不到返回null
	 */
	public static User getByName(String name){
		User user = (User)CacheUtil.get(USER_CACHE, USER_CACHE_NAME_ + name);
		if (user == null){
			user = new User();
			user.setName(name);
			user = userDao.getByName(user);
			if (user == null){
				return null;
			}
			user.setRoleList(roleDao.findList(new Role(user)));
			CacheUtil.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
			CacheUtil.put(USER_CACHE, USER_CACHE_NAME_ + user.getName(), user);
		}
		return user;
	}

	public static User getByUserNo(String userNo){
		User user = new User();
		user.setUserNo(userNo);
		return userDao.getByUserNo(user);
	}

	/**
	 * 清除当前用户缓存
	 */
	public static void clearCache(){
		//add by chenyl 20170426 for 租户信息
		removeCache(CACHE_RENT_LIST);
		removeCache(CACHE_CORPORATION_LIST);
		removeCache(CACHE_ROLE_LIST);
		removeCache(CACHE_MENU_LIST);
		removeCache(CACHE_OFFICE_LIST);
		removeCache(CACHE_OFFICE_ALL_LIST);
		UserUtils.clearCache(getUser());
	}
	
	/**
	 * 清除指定用户缓存
	 * @param user
	 */
	public static void clearCache(User user){
		CacheUtil.remove(USER_CACHE, USER_CACHE_ID_ + user.getId());
		CacheUtil.remove(USER_CACHE, USER_CACHE_NAME_ + user.getName());
		CacheUtil.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName());
		CacheUtil.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getOldLoginName());
		if (user.getOffice() != null && user.getOffice().getId() != null){
			CacheUtil.remove(USER_CACHE, USER_CACHE_LIST_BY_BRCH_ID_ + user.getOffice().getId());
		}
	}
	
	/**
	 * 获取当前登录用户的屏幕像素高，默认1280
	 * @return
	 */
	public static String getMaxWidth(){
		String maxWidth = "1280";	//默认最大宽度1280px
		Principal principal = getPrincipal();
		if (principal!=null){
			if (!DataUtil.isNullStr(principal.getMaxWidth())){
				maxWidth = principal.getMaxWidth();
			}
		}
		return maxWidth;
	}
	
	/**
	 * 获取当前用户
	 * @return 取不到返回 new User()
	 */
	public static User getUser(){
		Principal principal = getPrincipal();
		if (principal!=null){
			User user = get(principal.getId());
			if (user != null){
				Office office = user.getOffice();
				if (office == null) {
					user = userDao.get(principal.getId());
				}
				return user;
			}
			return new User();
		}
		// 如果没有登录，则返回实例化空的User对象。
		return new User();
	}

	/**
	 * 获取当前用户角色列表
	 * @return
	 */
	public static List<Role> getRoleList(){
		@SuppressWarnings("unchecked")
		List<Role> roleList = (List<Role>)getCache(CACHE_ROLE_LIST);
		if (roleList == null){
			roleList = roleDao.findAllList();
			putCache(CACHE_ROLE_LIST, roleList);
		}
		return roleList;
	}
	
	/**
	 * 获取当前用户租户列表
	 * @return
	 */
	public static List<Rent> getRentList(){
		@SuppressWarnings("unchecked")
		List<Rent> rentList = (List<Rent>)getCache(CACHE_RENT_LIST);
		if (rentList == null){
			User user = getUser();
			if (user.isAdmin()){
				rentList = rentDao.findAllList();
			}else{
				Rent rent = new Rent();
				rent.setUser(UserUtils.getUser());
				List<Object> filterParams = Lists.newArrayList();
				rent.getSqlMap().put("dsf", BaseService.dataScpFilterRent(user, "a", "", filterParams));
				rentList = rentDao.findList(rent, filterParams);
			}
			putCache(CACHE_RENT_LIST, rentList);
		}
		return rentList;
	}
	
	/**
	 * 获取用户列表
	 * @return
	 * add by chenyl 2017-8-11
	 */
	public static List<User> getUserList(){
		@SuppressWarnings("unchecked")
		List<User> userList = (List<User>)getCache(CACHE_USER_LIST);
		if (userList == null){
			userList = userDao.findAllList();
			putCache(CACHE_USER_LIST, userList);
		}
		return userList;
	}
	
	/**
	 * 获取当前用户授权菜单
	 * @return
	 */
	public static List<Menu> getMenuList(){
		@SuppressWarnings("unchecked")
		List<Menu> menuList = (List<Menu>)getCache(CACHE_MENU_LIST);
		if (menuList == null){
			UserUtils.clearCache();
			User user = getUser();
			if (user.isAdmin()){
				menuList = menuDao.findAllList();
			}else{
				menuList = menuDao.findByUser(user);
			}
			putCache(CACHE_MENU_LIST, menuList);
		}
		return menuList;
	}
	
	/**
	 * 根据id查找menu信息
	 * @param id
	 * @return
	 */
	public static Menu getMenuById(String id){
	    Menu m = null;
	    List<Menu> list = getMenuList();
	    for (int i = 0; i < list.size(); i++) {
	        Menu menu = list.get(i);
	        if(id.equals(menu.getId())){
	            m = menu;
	            break;
	        }
        }
	    return m;
	}

	/**
	 * 获取所有区域参数
	 * @return
	 */
	public static List<Area> getAreaList(){
		/*20200322 mod by chenyl for 修改成从系统级缓存中获取区域参数*/
		@SuppressWarnings("unchecked")
		List<Area> areaList = (List<Area>)CacheUtil.get(CACHE_AREA_LIST);
		if (areaList == null){
			areaList = areaDao.findAllList();
			CacheUtil.put(CACHE_AREA_LIST, areaList);
		}
		return areaList;
	}
	
	/**
	 * 根据id从缓存中获取区域参数
	 * @param id
	 * @return
	 */
	public static Area getAreaByIdInCache(String id){
		Area area = null;
		List<Area> areaList = (List<Area>)CacheUtil.get(CACHE_AREA_LIST);
		if(null!=areaList){
			for(Area a:areaList){
				if(null!=a && a.getId().equals(id)){
					area = a;
					break;					
				}
			}
		}
		return area;
	}

	public static List<Map<String, Object>> getAreaTree(String extId){
		/*20200322 mod by chenyl for 修改成从系统级缓存中获取区域参数树*/
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> areaTree = (List<Map<String, Object>>)CacheUtil.get(CACHE_AREA_TREE);
		if (areaTree == null){
			areaTree = Lists.newArrayList();
			List<Area> areaList = getAreaList();
			for (Area e : areaList) {
				if (StringUtil.isBlank(extId) || !extId.equals(e.getId()) && !e.getParentIdList().contains("," + extId + ",")) {
					Map<String, Object> map = Maps.newHashMap();
					map.put("id", e.getId());
					map.put("pId", e.getParentId());
					map.put("name", e.getName());
					areaTree.add(map);
				}
			}
			CacheUtil.put(CACHE_AREA_TREE, areaTree);
		}
		return areaTree;
	}

	public static List<Corporation> getCorporationList(){
		List<Corporation> corporationList = (List<Corporation>)getCache(CACHE_CORPORATION_LIST);
		if (corporationList == null){
			Corporation corporation = new Corporation();
			List<Object> filterParams = Lists.newArrayList();
			corporation.getSqlMap().put("dsf", BaseService.dataScpFilterCorporation(UserUtils.getUser(), "a", "", filterParams));
			corporationList = corporationDao.findAllList(corporation, filterParams);
			putCache(CACHE_CORPORATION_LIST, corporationList);
		}
		return corporationList;
	}

	/**
	 * 获取当前用户有权限访问的部门
	 * @return
	 */
	public static List<Office> getOfficeList(){
		List<Office> officeList = null;
		if (officeList == null){
			UserUtils.clearCache();
			User user = getUser();
			if (user.isAdmin()){
				officeList = officeDao.findAllList();
			}else{
				Office office = new Office();
				List<Object> filterParams = Lists.newArrayList();
				office.getSqlMap().put("dsf", BaseService.dataScpFilterOffice(user, "a", "", filterParams));
				officeList = officeDao.findList(office, filterParams);
			}
			putCache(CACHE_OFFICE_LIST, officeList);
		}
		return officeList;
	}

	/**
	 * 获取当前用户有权限访问的部门
	 * @return
	 */
	public static List<Office> getOfficeAllList(){
		@SuppressWarnings("unchecked")
		List<Office> officeList = (List<Office>)getCache(CACHE_OFFICE_ALL_LIST);
		if (officeList == null){
			officeList = officeDao.findAllList();
		}
		return officeList;
	}
	
	/**
	 * 获取授权主要对象
	 */
	public static Subject getSubject(){
		return SecurityUtils.getSubject();
	}
	
	/**
	 * 获取当前登录者对象
	 */
	public static Principal getPrincipal(){
		try{
			Subject subject = SecurityUtils.getSubject();
			Principal principal = (Principal)subject.getPrincipal();
			if (principal != null){
				return principal;
			}
			subject.logout();
		}catch (UnavailableSecurityManagerException e) {
			System.out.println("Unavailable Security Manager");
		}catch (InvalidSessionException e){
			System.out.println("Invalid Session");
		}
		return null;
	}
	
	public static Session getSession(){
		try{
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null){
				session = subject.getSession();
			}
			if (session != null){
				return session;
			}
			subject.logout();
		}catch (InvalidSessionException e){
			System.out.println("Invalid Session Exception");
		}
		return null;
	}
	
	// ============== User Acct ==============
	public static Map<String, UserAcctDO> getAcctMap() {
		Principal principal = getPrincipal();
		if (principal != null) {
			return principal.getAcctMap();
		}
		return new HashMap<String, UserAcctDO>();
	}

	public static UserAcctDO getAcct(String acctTp) {
		return getAcctMap().get(acctTp);
	}

	public static UserAcctDO getSvnAcct() {
		UserAcctDO acct = getAcct(UserAcctType.SVN);
		if (acct == null) {
			UserAcctDO userAcctDO = new UserAcctDO(UserAcctType.SVN, getUser().getId());
			acct = userAcctDao.get(userAcctDO);
			if(null==acct) {
				throw new BaseException(SysErr.E_MESSAGE,"用户未配置svn账号信息，请先到用户管理配置svn账号信息!");
			}
			acct.setPwd(Encry.decryptString(acct.getPwd()));
			// 密码解密
			getAcctMap().put(UserAcctType.SVN, acct);
		}
		return acct;
	}
	
	public static UserAcctDO getSvnAcct(String type) {
		UserAcctDO acct = getAcct(type);
		if (acct == null) {
			UserAcctDO userAcctDO = new UserAcctDO(type, getUser().getId());
			acct = userAcctDao.get(userAcctDO);
			if(null==acct) {
				throw new BaseException(SysErr.E_MESSAGE,"用户未配置svn账号信息，请先到用户管理配置svn账号信息!");
			}
			acct.setPwd(Encry.decryptString(acct.getPwd()));
			// 密码解密
			getAcctMap().put(type, acct);
		}
		return acct;
	}
	
	public static UserAcctDO setAcctInfo(String acctTp, UserAcctDO userAcctDO) {
		Assert.notBlank(acctTp, "acctTp must not be blank!");
		Assert.notNull(userAcctDO, "userAcctDO must not be null!");
		// 密码解密
		userAcctDO.setPwd(Encry.decryptString(userAcctDO.getPwd()));
		getAcctMap().put(acctTp, userAcctDO);
		return userAcctDO;
	}	
	
	// ============== User Cache ==============
	
	public static Object getCache(String key) {
		return getCache(key, null);
	}
	
	public static Object getCache(String key, Object defaultValue) {
		Object obj = getCacheMap().get(key);
//		Object obj = getSession().getAttribute(key);
		return obj==null?defaultValue:obj;
	}

	public static void putCache(String key, Object value) {
		getCacheMap().put(key, value);
//		getSession().setAttribute(key, value);
	}

	public static void removeCache(String key) {
		getCacheMap().remove(key);
//		getSession().removeAttribute(key);
	}
	
	public static Map<String, Object> getCacheMap(){
		Principal principal = getPrincipal();
		if(principal!=null){
			return principal.getCacheMap();
		}
		return new HashMap<String, Object>();
	}

	public static String getBrchIdByRoleId(String roleId) {
		return officeDao.findBrchIdByRoleId(roleId);
	}
	
    /**
     * 获取当前用户所属租户和下级租户的所有用户id字符串
     * @return
     */
	public static String getOwnAndSubUser(){
	    User user = getUser();
	    String userIdList = (String) getCache(CACHE_USER_ID_LIST+user.getId());
	    if(StringUtil.isEmpty(userIdList)){
	        StringBuffer sb = new StringBuffer();
	        List<User> list = userDao.getOwnAndSubUser(user);
	        for(User u : list){
	            sb.append("'").append(u.getId()).append("',");
	        }
	        userIdList = sb.toString();
	        if(userIdList.length() > 0){
	            userIdList = userIdList.substring(0,userIdList.length()-1);
	        }else{
	            userIdList = "''";
	        }
	        putCache(CACHE_USER_ID_LIST+user.getId(), userIdList);
	    }
	    return userIdList;
	}

	public static Map<Role, List<User>> getRoleUserList() {
		Map<Role, List<User>> map = new HashMap<Role, List<User>>();
		List<Role> roleList = getRoleList();
		for (Role role : roleList) {
			List<User> userList = userDao.findUserByRoleId(role.getId());
			map.put(role, userList);
		}
		return map;
	}

	public static Boolean checkMobile(String phoneNo){
		if(StringUtils.isBlank(phoneNo)){
			return true;
		}
		String regex = "^((13[0-9])|(14[579])|(15([0-3]|[5-9]]))|(16[56])|(17[0-8])|(18[0-9])|(19[1589]))+\\d{8}$";
		Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(phoneNo);
		return m.matches();
	}

	public static Boolean checkTelphone(String telNo){
		if(StringUtils.isBlank(telNo)){
			return true;
		}
		String regex = "^(\\d{3,4}-?)?\\d{7,9}$";
		Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(telNo);
		return m.matches();
	}
}
