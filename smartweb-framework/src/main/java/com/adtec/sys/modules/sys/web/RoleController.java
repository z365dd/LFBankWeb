/**
 * 
 */
package com.adtec.sys.modules.sys.web;

import com.adtec.framework.common.util.CacheUtil;
import com.adtec.framework.common.util.CollectionUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.sys.common.persistence.Page;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.modules.sys.entity.Office;
import com.adtec.sys.modules.sys.entity.Role;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.service.OfficeService;
import com.adtec.sys.modules.sys.service.PermissionService;
import com.adtec.sys.modules.sys.service.RoleService;
import com.adtec.sys.modules.sys.service.SystemService;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.adtec.sys.modules.sys.vo.PermissionDTO;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 角色Controller
 * 
 * @version 2013-12-05
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/role")
public class RoleController extends BaseController {

	@Autowired
	private SystemService systemService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;

	@ModelAttribute("role")
	public Role get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return systemService.getRole(id);
		}else{
			return new Role();
		}
	}
	
	// @RequiresPermissions("sys:role:view")
	@RequestMapping(value = {"list", ""})
	public String list(Role role, Model model) {
		List<Role> list = systemService.findRoleList(role);
		model.addAttribute("list", list);
		return "modules/sys/roleList";
	}

	// @RequiresPermissions("sys:role:view")
	@RequestMapping(value = "detail")
	public String detail(Role role, Model model) {
		User user = UserUtils.getUser();
		boolean isUpdate = false;
		if (role.getId() != null){
			role = systemService.findRoleByRoleId(role);
			isUpdate = true;
		}
		model.addAttribute("isUpdate", isUpdate);
		model.addAttribute("role", role);
		return "modules/sys/roleDetail";
	}

	// @RequiresPermissions("sys:role:view")
	@RequestMapping(value = "form")
	public String form(Role role, Model model) {
		User user = UserUtils.getUser();
		boolean isUpdate = false;
		if (role.getId() != null){
			role = systemService.findRoleByRoleId(role);
			isUpdate = true;
		}
		model.addAttribute("isUpdate", isUpdate);
		model.addAttribute("role", role);
		return "modules/sys/roleForm";
	}

	//TODO 授权给下级单位管理员
	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "save")
	public String save(Role role, Model model, RedirectAttributes redirectAttributes) {
		if(ParamUtil.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/role/?repage";
		}
		if (!beanValidator(model, role)){
			return form(role, model);
		}
		if (!"true".equals(checkName(role.getOldName(), role.getName()))){
			addMessage(model, "保存角色'" + role.getName() + "'失败, 角色名已存在");
			return form(role, model);
		}
		if (!"true".equals(checkEngName(role.getOldEngName(), role.getEngName()))){
			addMessage(model, "保存角色'" + role.getName() + "'失败, 英文名已存在");
			return form(role, model);
		}

		try {
			systemService.saveRole(role);
		} catch (Exception e) {
			logger.error("保存失败", e);
			addMessage(redirectAttributes, "保存失败，" + e.getMessage());
			return "redirect:" + adminPath + "/sys/role/?repage";
		}

		addMessage(redirectAttributes, "保存角色'" + role.getName() + "'成功");
		return "redirect:" + adminPath + "/sys/role/?repage";
	}
	
	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "delete")
	public String delete(Role role, RedirectAttributes redirectAttributes) {
		if(!UserUtils.getUser().isManager() && role.getDataSwitchFlg().equals(ParamUtil.YES)){
			addMessage(redirectAttributes, "越权操作，只有超级管理员才能修改此数据！");
			return "redirect:" + adminPath + "/sys/role/?repage";
		}
		if(ParamUtil.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/role/?repage";
		}
		String id = role.getId();
		List<String> list = systemService.getUserIdFromUserRole(id);
		if(list != null && list.size() > 0){
			addMessage(redirectAttributes, "角色已被使用，不能删除！");
			return "redirect:" + adminPath + "/sys/role/?repage";
		}
		if (UserUtils.getUser().getRoleIdList().contains(id)){
			addMessage(redirectAttributes, "删除角色失败, 不能删除当前用户所在角色");
		}else{
			systemService.deleteRole(role);
			addMessage(redirectAttributes, "删除角色成功");
		}
		return "redirect:" + adminPath + "/sys/role/?repage";
	}
	
	/**
	 * 角色分配页面
	 * @param role
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "assign")
	public String assign(Role role, String brchId, Model model) {
		Office office = officeService.get(brchId);
		if (office != null) {
			model.addAttribute("office", office);
		}
		List<User> userList = systemService.findUserByOfficeScope(new User(new Role(role.getId())));
		// 清除缓存
		CacheUtil.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_ROLE_ID_ + role.getId());
		model.addAttribute("userList", userList);
		return "modules/sys/roleAssign";
	}
	
	/**
	 * 角色分配 -- 打开角色分配对话框
	 * @param role
	 * @param model
	 * @return
	 */
	// @RequiresPermissions("sys:role:view")
	@RequestMapping(value = "usertorole")
	public String selectUserToRole(Role role, Model model) {
		Role r = systemService.findRoleByRoleId(role);
		User user = new User();
		user.setRole(r);
		List<User> userList = systemService.findUserByOfficeScope(user);
		model.addAttribute("role", role);
		model.addAttribute("userList", userList);
		model.addAttribute("selectIds", CollectionUtil.extractToString(userList, "id", ","));
		model.addAttribute("officeList", officeService.findAll());
		return "modules/sys/selectUserToRole";
	}
	
	/**
	 * 角色分配 -- 根据部门编号获取角色列表
	 * @param brchId
	 * @param response
	 * @return/o
	 */
	// @RequiresPermissions("sys:role:view")
	@ResponseBody
	@RequestMapping(value = "users")
	public List<Map<String, Object>> users(String brchId, String roleId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		User user = new User();
		user.setOffice(new Office(brchId));
		Page<User> page = systemService.findFilterUser(new Page<User>(1, -1), user);
		Role role = new Role();
		role.setId(roleId);
		Role r = systemService.findRoleByRoleId(role);
		User u = new User();
		u.setRole(r);
		List<User> selectedUserList = systemService.findUserByOfficeScope(u);
		page.getList().removeAll(selectedUserList);
		for (User e : page.getList()) {
			if (!e.getRoleTp().equals(r.getRoleTp())) {
				continue;
			}
			if (!e.getOffice().getId().equals(UserUtils.getUser().getOffice().getId())) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", 0);
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	/**
	 * 根据系统分类获取角色列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getRoleList")
	public Map<String, Object> getRoleList(HttpServletRequest request, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		String roleTp = request.getParameter("roleTp");
		String userId= request.getParameter("userId");
		Role role  = new Role();
		User user  = new User();
		user.setId(userId);
		role.setUser(user);
		role.setRoleTp(roleTp);
		List<Role> roleList = roleService.getRolesWithUser(role);
		for (Role r : roleList) {
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", r.getId());
			map.put("value", r.getId());
			map.put("label", r.getName());
			if(r.getUser() != null && StringUtils.isNotBlank(r.getUser().getId())){
				map.put("checked", "true");
			}
			mapList.add(map);
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("retCode", "0000");
		m.put("list", mapList);
		return m;
	}
	
	/**
	 * 角色分配 -- 从角色中移除用户
	 * @param userId
	 * @param roleId
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "outrole")
	public String outrole(String userId, String roleId, RedirectAttributes redirectAttributes) {
		if(ParamUtil.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/role/assign?id="+roleId;
		}
		Role role = systemService.getRole(roleId);
		User user = systemService.getUser(userId);
		if (UserUtils.getUser().getId().equals(userId)) {
			addMessage(redirectAttributes, "无法从角色【" + role.getName() + "】中移除用户【" + user.getName() + "】自己！");
		}else {
			Boolean flag = systemService.outUserInRole(user, roleId);
			if (!flag) {
				addMessage(redirectAttributes, "用户【" + user.getName() + "】从角色【" + role.getName() + "】中移除失败！");
			}else {
				addMessage(redirectAttributes, "用户【" + user.getName() + "】从角色【" + role.getName() + "】中移除成功！");
			}
			UserUtils.clearCache(user);
		}
		return "redirect:" + adminPath + "/sys/role/assign?id="+role.getId();
	}
	
	/**
	 * 角色分配
	 * @param role
	 * @param idsArr
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "assignrole")
	public String assignRole(Role role, String[] idsArr, RedirectAttributes redirectAttributes) {
		role = systemService.getRole(role.getId());
		if(ParamUtil.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/role/assign?id="+role.getId();
		}
		StringBuilder msg = new StringBuilder();
		int newNum = 0;
		List<String> existUserIds = systemService.getUserIdFromUserRole(role.getId());
		systemService.deleteUserRole(role);
		for (String userId : idsArr) {
			User user = systemService.assignUserToRole(role, systemService.getUser(userId));
			if (null != user && !existUserIds.contains(userId)) {
				msg.append("<br/>新增用户【").append(user.getName()).append("】到角色【").append(role.getName()).append("】！");
				newNum++;
			}
			UserUtils.clearCache(user);
		}
		addMessage(redirectAttributes, "已成功分配 "+newNum+" 个用户"+msg);
		return "redirect:" + adminPath + "/sys/role/assign?id="+role.getId();
	}

	/**
	 * 验证角色名是否有效
	 * @param oldName
	 * @param name
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "checkName")
	public String checkName(String oldName, String name) {
		if (name!=null && name.equals(oldName)) {
			return "true";
		} else if (name!=null && systemService.getRoleByName(name) == null) {
			return "true";
		}
		return "false";
	}

	/**
	 * 验证角色英文名是否有效
	 * @param oldEngName
	 * @param engName
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "checkEngName")
	public String checkEngName(String oldEngName, String engName) {
		if (engName!=null && engName.equals(oldEngName)) {
			return "true";
		} else if (engName!=null) {
			// 中文正则匹配表达式
			// String pattern = "[\u4e00-\u9fa5]+";
			// 数字、26个英文字母或者下划线
			String pattern = "^\\w+$";

			boolean isMatch = Pattern.matches(pattern, engName);
			if (!isMatch) {
				return "false";
			} else if (systemService.getRoleByEngName(engName) == null) {
				return "true";
			}
		}
		return "false";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "selectRoleJson")
	public Map<String, Object> getSelectJson(String brchId) {
		logger.info("selectRoleJson");
		List<Role> list = systemService.selectRoleByBrchId(brchId);
		Map<String, Object> m = Maps.newHashMap();
		m.put("list", list);
		if (list.size() > 0) {
			m.put("hadManager", "true");
		}
		return m;
	}

	@RequestMapping("seePermissionToRole")
	public String seePermissionToRole(String id, Model model) {
		PermissionDTO permissionOwn = permissionService.assembleRoleVoByAuthTp(id, "own");
		model.addAttribute("permissionOwn", permissionOwn);
		PermissionDTO permissionTransfer = permissionService.assembleRoleVoByAuthTp(id, "transfer");
		model.addAttribute("permissionTransfer", permissionTransfer);
		PermissionDTO permissionUse = permissionService.assembleRoleVoByAuthTp(id, "use");
		model.addAttribute("permissionUse", permissionUse);
		return "modules/sys/seePermissionToRole";
	}

	@RequestMapping("assignPermissionToRole")
	public String assignPermissionToRole(String id, String permissionType, Model model) {
		PermissionDTO permissionDTO = permissionService.assembleRoleVo(id, permissionType);
		model.addAttribute("permissionDTO", permissionDTO);
		return "modules/sys/assignPermissionToRole";
	}

	@RequestMapping(value = "savePermissionToRole")
	public String savePermissionToRole(PermissionDTO permissionDTO, RedirectAttributes redirectAttributes) {
		if (ParamUtil.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/role/list";
		}
		int resultCount = permissionService.savePermissionToRole(permissionDTO);
		if (resultCount > 0) {
			addMessage(redirectAttributes, "分配权限组成功");
		} else {
			addMessage(redirectAttributes, "分配权限组失败");
		}
		return "redirect:" + adminPath + "/sys/role/list";
	}

}
