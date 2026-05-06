package com.adtec.sys.modules.sys.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.adtec.framework.common.util.*;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.sys.modules.sys.entity.*;
import com.adtec.sys.modules.sys.service.CorporationService;
import com.adtec.sys.modules.sys.utils.PwdCheck;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.beanvalidator.BeanValidators;
import com.adtec.sys.common.persistence.Page;
import com.adtec.sys.common.security.RSAUtils;
import com.adtec.sys.common.utils.excel.ExportExcel;
import com.adtec.sys.common.utils.excel.ImportExcel;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.modules.sys.service.RentService;
import com.adtec.sys.modules.sys.service.SystemService;
import com.adtec.sys.modules.sys.service.UserService;
import com.adtec.sys.modules.sys.utils.Const;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

/**
 * 用户Controller
 *
 * @version 2013-8-29
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/user")
public class UserController extends BaseController {

	private final SystemService systemService;
	private final UserService userService;
	private final CorporationService corporationService;

	@Autowired
	public UserController(SystemService systemService, UserService userService,CorporationService corporationService) {
		this.systemService = systemService;
		this.userService = userService;
		this.corporationService = corporationService;
	}

	@ModelAttribute
	public User get(@RequestParam(required = false) String id) {
		if (StringUtil.isNotBlank(id)) {
			return systemService.getUser(id);
		} else {
			return new User();
		}
	}

	// @RequiresPermissions("sys:user:view")
	@RequestMapping(value = { "list", "" })
	public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = systemService.userList(user, request, response);
		model.addAttribute("page", page);
		return "modules/sys/userList";
	}

	// @RequiresPermissions("sys:user:view")
	@RequestMapping(value = "form")
	public String form(User user, Model model) {
		if (user.getOffice() == null || user.getOffice().getId() == null) {
			user.setOffice(UserUtils.getUser().getOffice());
			user.setRent(UserUtils.getUser().getRent());
			user.setCorporation(UserUtils.getUser().getCorporation());
		}
		List<Corporation> allCorporation = corporationService.findAllCorporation();
		if(!CollectionUtil.isEmpty(allCorporation)){
			user.setCorporation(allCorporation.get(0));
		}
		boolean isUpdate = StringUtil.isNotBlank(user.getId());
		if (isUpdate) {
			user = UserUtils.get(user.getId());
			// 清除缓存
			CacheUtil.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_BRCH_ID_ + user.getOffice().getId());
			CacheUtil.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_RENT_ID_ + user.getRent().getId());
			CacheUtil.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_COR_ID_ + user.getCorporation().getId());
			for (String roleId : user.getRoleIdList()) {
				CacheUtil.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_ROLE_ID_ + roleId);
			}
		}
		model.addAttribute("isUpdate", isUpdate);
		model.addAttribute("user", user);
		return "modules/sys/userForm";
	}

	// @RequiresPermissions("sys:user:view")
	@RequestMapping(value = { "updateOfficeForm" })
	public String updateTheOfficeOfUserForm(String userId, Model model) {
		User user = userService.getUser(userId);
		model.addAttribute("user", user);
		// 清理机构ID对应的用户
		CacheUtil.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_BRCH_ID_ + user.getOffice().getId());
		return "modules/sys/updateTheOfficeOfUser";
	}

	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "updateOffice")
	public String updateOffice(User user, RedirectAttributes redirectAttributes) {
		int resultCount = userService.updateOffice(user);
		if(resultCount > 0) {
			addMessage(redirectAttributes, "用户：【"+ user.getName() +"】 的所在机构已变更为：【" + user.getOffice().getName() + "】");
		} else {
			addMessage(redirectAttributes, "变更机构失败");
		}
		return "redirect:" + adminPath + "/sys/user/list";
	}

    @RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "save")
    public String save(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (ParamUtil.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/user/list?repage";
        }
//        if(StringUtil.isNotBlank(user.getNewPassword())){
//        	user.setImg(RSAUtils.decryptBase64(user.getImg()));
//        }
        if(StringUtil.isNotBlank(user.getNewPassword())){
        	user.setImg(user.getImg());
        }
        user.setOffice(new Office(request.getParameter("office.id")));
        // 如果新密码为空，则不更换密码
        if (StringUtil.isNotBlank(user.getNewPassword()) && user.getNewPassword().length() >= 6) {
//			20181206 add by chenyl for 对密码解密
            user.setNewPassword(RSAUtils.decryptBase64(user.getNewPassword()));
            user.setPwd(SystemService.entryptPassword(user.getNewPassword()));
        }
        if (StringUtils.isNotBlank(user.getEmail()) && user.getEmail().length() > 1) {
            user.setEmail(user.getEmail());
        }
        if (StringUtils.isNotBlank(user.getTelNo()) && user.getTelNo().length() > 1) {
            user.setTelNo(user.getTelNo());
        }
        if (StringUtils.isNotBlank(user.getPhoneNo())) {
            user.setPhoneNo(user.getPhoneNo());
        }
		if(UserUtils.checkMobile(user.getPhoneNo())){
			if(StringUtils.isNotBlank(user.getPhoneNo())){
				user.setPhoneNo(user.getPhoneNo());
			}
		}else{
			model.addAttribute("message", "修改失败，请输入正确的手机号码");
			return "modules/sys/userInfo";
		}
		if(UserUtils.checkTelphone(user.getTelNo())){
			if(StringUtils.isNotBlank(user.getTelNo())){
				user.setPhoneNo(user.getTelNo());
			}
		}else{
			model.addAttribute("message", "修改失败，请输入正确的电话号码");
			return "modules/sys/userInfo";
		}
        if (StringUtils.isNotBlank(user.getLoginName())) {
            user.setLoginName(user.getLoginName());
        }
        if (StringUtils.isNotBlank(user.getName())) {
            user.setName(user.getName());
        }
        if (StringUtils.isNotBlank(user.getOldName())) {
            user.setOldName(user.getOldName());
        }
        if (StringUtils.isNotBlank(user.getOldLoginName())) {
            user.setOldLoginName(user.getOldLoginName());
        }
        //修改保存原ip
		if (StringUtils.isNotBlank(user.getLoginIp())) {
			user.setLoginIp(user.getLoginIp());
		}
        if (!beanValidator(model, user)) {
            return form(user, model);
        }
        if (!Const.Boolean.TRUE.equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))) {
            addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
            return form(user, model);
        }

		if (user.getRoleIdList().size() < 1) {
			UserUtils.clearCache(user);
			addMessage(model, "未分配角色，或者可分配角色为空");
			return form(user, model);
		}

		if ("manager".equals(user.getRoleTp()) && !UserUtils.getUser().isManager()) {
			addMessage(model, "不可添加角色类型为系统管理员的用户");
			return form(user, model);
		}

		try {
			// 保存用户信息
			systemService.saveUser(user);
		} catch (Exception e) {
			logger.error("保存失败", e);
			addMessage(redirectAttributes, "保存失败，" + e.getMessage());
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}

		// 清除当前用户缓存
		if (user.getLoginName().equals(UserUtils.getUser().getLoginName())) {
			UserUtils.clearCache();
		}
		addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功");
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}

	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "delete")
	public String delete(User user, RedirectAttributes redirectAttributes) {
		if (ParamUtil.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		if (UserUtils.getUser().getId().equals(user.getId())) {
			addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");
		} else if (User.isAdmin(user.getId())) {
			addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
		} else {
			systemService.deleteUser(user);
			addMessage(redirectAttributes, "删除用户成功");
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}

	/**
	 * 导出用户数据
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	// @RequiresPermissions("sys:user:view")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(User user, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			String fileName = "用户数据" + DateUtil.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<User> page = systemService.findUserAll(new Page<User>(request, response, -1), user);
			new ExportExcel("用户数据", User.class).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}

	/**
	 * 导入用户数据
	 * 
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		if (ParamUtil.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<User> list = ei.getDataList(User.class);
			for (User user : list) {
				try {
					if ("true".equals(checkLoginName("", user.getLoginName()))) {
						user.setPwd(SystemService.entryptPassword("111111"));
						BeanValidators.validateWithException(validator, user);

						String temp = null;
						boolean success = true;
						for (Role role : user.getRoleList()) {
							if (temp == null) {
								temp = role.getRoleTp();
							} else {
								if (!temp.equals(role.getRoleTp())) {
									failureMsg.append("<br/>用户 ").append(user.getLoginName()).append(" 的角色类型不一致; ");
									failureNum++;
									success = false;
									break;
								}
							}
							if ("manager".equals(role.getRoleTp()) && !"1".equals(UserUtils.getUser().getId())) {
								failureMsg.append("<br/>用户 ").append(user.getLoginName()).append(" 的角色类型为管理员，不能导入; ");
								failureNum++;
								success = false;
								break;
							}
						}

						if (success) {
							systemService.saveUser(user);
							successNum++;
						}
					} else {
						failureMsg.append("<br/>登录名 ").append(user.getLoginName()).append(" 已存在; ");
						failureNum++;
					}
				} catch (ConstraintViolationException ex) {
					failureMsg.append("<br/>登录名 ").append(user.getLoginName()).append(" 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message).append("; ");
						failureNum++;
					}
				} catch (Exception ex) {
					failureMsg.append("<br/>登录名 ").append(user.getLoginName()).append(" 导入失败：").append(ex.getMessage());
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条用户，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条用户" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}

	/**
	 * 下载导入用户数据模板
	 * 
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	// @RequiresPermissions("sys:user:view")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "用户数据导入模板.xlsx";
			List<User> list = Lists.newArrayList();
			list.add(UserUtils.getUser());
			new ExportExcel("用户数据", User.class, 2).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}

	/**
	 * 验证登录名是否有效
	 * 
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginName, String loginName) {
		if (loginName != null && loginName.equals(oldLoginName)) {
			return Const.Boolean.TRUE;
		} else if (loginName != null && systemService.getUserByLoginName(loginName) == null) {
			return Const.Boolean.TRUE;
		}
		return Const.Boolean.FALSE;
	}

	/**
	 * 验证姓名是否存在
	 * 
	 * @param oldName
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "checkName")
	public String checkName(String oldName, String name) {
		if (name != null && name.equals(oldName)) {
			return Const.Boolean.TRUE;
		} else if (name != null && systemService.getUserByName(name) == null) {
			return Const.Boolean.TRUE;
		}
		return Const.Boolean.FALSE;
	}

	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "checkUserNo")
	public String checkUserNo(String oldUserNo, String userNo) {
		if (userNo != null && userNo.equals(oldUserNo)) {
			return Const.Boolean.TRUE;
		} else if (userNo != null && systemService.getUserByUserNo(userNo) == null) {
			return Const.Boolean.TRUE;
		}
		return Const.Boolean.FALSE;
	}

    /**
     * 用户信息显示及保存
     *
     * @param user
     * @param model
     * @return
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "info")
    public String info(User user, Model model) {
        User currentUser = UserUtils.getUser();
        logger.debug("remark=" + user.getRmrk());
        if (StringUtil.isNotBlank(user.getName())) {
            if (ParamUtil.isDemoMode()) {
                model.addAttribute("message", "演示模式，不允许操作！");
                return "modules/sys/userInfo";
            }
            currentUser.setEmail(StringUtils.isBlank(user.getEmail()) ? "" : user.getEmail());
            if(UserUtils.checkMobile(user.getPhoneNo())){
				currentUser.setPhoneNo(StringUtils.isBlank(user.getPhoneNo()) ? "" : user.getPhoneNo());
			}else{
				model.addAttribute("message", "修改失败，请输入正确的手机号码");
				return "modules/sys/userInfo";
			}
			if(UserUtils.checkTelphone(user.getTelNo())){
				currentUser.setTelNo(StringUtils.isBlank(user.getTelNo()) ? "" : user.getTelNo());
			}else{
				model.addAttribute("message", "修改失败，请输入正确的电话号码");
				return "modules/sys/userInfo";
			}
            currentUser.setRmrk(user.getRmrk());
            currentUser.setImg(StringUtils.isBlank(user.getImg()) ? "" : user.getImg());
            systemService.updateUserInfo(currentUser);
            model.addAttribute("message", "保存用户信息成功");
        }
        //加了租户切换功能后，用户的租户信息不能从缓存中取
        String userId = currentUser.getId();
        if (!DataUtil.isNullStr(userId)) {
            User u = userService.getUser(userId);
            if (u != null) {
                RentService rentService = SpringContextHolder.getBean("rentService");
                currentUser.setRent(rentService.get(u.getRent().getId()));
            }
        }
        model.addAttribute("user", currentUser);
        model.addAttribute("Global", new ParamUtil());
        return "modules/sys/userInfo";
    }

	/**
	 * 修改个人用户密码
	 * 
	 * @param oldPassword
	 * @param newPassword
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "modifyPwd")
	public String modifyPwd(String oldPassword, String newPassword, Model model) {
		User user = UserUtils.getUser();
		if (StringUtil.isNotBlank(oldPassword) && StringUtil.isNotBlank(newPassword)) {
			/*20181206 add by chenyl for 对密码解密*/
			oldPassword = RSAUtils.decryptBase64(oldPassword);
			newPassword = RSAUtils.decryptBase64(newPassword);
			if(PwdCheck.hasContinuousChar(newPassword)){
				model.addAttribute("message", "修改密码失败，新密码中不能包含三个及以上相同或连续字符");
				return "modules/sys/userModifyPwd";
			}
			if(PwdCheck.hasKeyBoardContinuousChar(newPassword)){
				model.addAttribute("message", "修改密码失败，新密码中不能包含三个及以上连续字符");
				return "modules/sys/userModifyPwd";
			}
			if (ParamUtil.isDemoMode()) {
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userModifyPwd";
			}
			if (SystemService.validatePassword(oldPassword, user.getPwd())) {
				systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
				model.addAttribute("message", "修改密码成功");
			} else {
				model.addAttribute("message", "修改密码失败，旧密码错误");
			}
		}
		model.addAttribute("user", user);
		return "modules/sys/userModifyPwd";
	}

	/**
	 * 首次登录修改个人用户密码
	 *
	 * @param oldPassword
	 * @param newPassword
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "firstLoginModifyPwd")
	public String firstLoginModifyPwd(String oldPassword, String newPassword, Model model) {
		User user = UserUtils.getUser();
		if (StringUtil.isNotBlank(oldPassword) && StringUtil.isNotBlank(newPassword)) {
			//对密码解密
			oldPassword = RSAUtils.decryptBase64(oldPassword);
			newPassword = RSAUtils.decryptBase64(newPassword);
			if(PwdCheck.hasContinuousChar(newPassword)){
				model.addAttribute("message", "修改密码失败，新密码中不能包含三个及以上相同或连续字符");
				return "redirect:" + adminPath;
			}
			if(PwdCheck.hasKeyBoardContinuousChar(newPassword)){
				model.addAttribute("message", "修改密码失败，新密码中不能包含三个及以上连续字符");
				return "redirect:" + adminPath;
			}
			if (SystemService.validatePassword(oldPassword, user.getPwd())) {
				systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
				systemService.updateUserLoginInfo(user);
				//解决首次登录修改密码不正确 等待若干时间后重新登录不需要再修改密码
				user.setLoginIp(UserUtils.getSession().getHost());
				userService.updateUserLoginInfo(user);
				model.addAttribute("message", "修改密码成功");
			} else {
				model.addAttribute("message", "修改密码失败，旧密码错误");
			}
			return "redirect:" + adminPath;
		}
		model.addAttribute("user", user);
		return "redirect:" + adminPath;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required = false) String brchId, @RequestParam(required = false) String type,
			HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		if (null != type && "5".equals(type)) {
			// 获取所有的租户列表
			List<Rent> rentList = systemService.findAllRent();
			for (Rent rent : rentList) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", "u_" + rent.getId());
				map.put("pId", brchId);
				map.put("name", StringUtil.replace(rent.getName(), " ", ""));
				mapList.add(map);
			}
			
		}else if(null!=type && "4".equals(type)){
			// 获取所有的角色列表
			List<Role> roleList = systemService.findAllRole();
			for (Role role : roleList) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", "u_" + role.getId());
				map.put("pId", brchId);
				map.put("name", StringUtil.replace(role.getName(), " ", ""));
				mapList.add(map);
			}
		}else{
			// 获取各机构下的用户列表
			List<User> list = systemService.findUserByBrchId(brchId);
			for (User e : list) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", "u_" + e.getId());
				map.put("pId", brchId);
				map.put("name", StringUtil.replace(e.getName(), " ", ""));
				mapList.add(map);
			}
		}
		return mapList;
	}

	// @RequiresPermissions("sys:user:view")
	@RequestMapping(value = "detail")
	public String detail(String id, Model model) {
		User user = userService.getUser(id);
		model.addAttribute("user", user);
		return "modules/sys/userDetail";
	}

	// @RequiresPermissions("sys:user:view")
	@RequestMapping(value = "userMenu")
	public String userMenu(String id, Model model) {
		model.addAttribute("user", userService.userMenu(userService.getUser(id)));
		return "modules/sys/userMenu";
	}
	
	@RequestMapping(value = { "userAcct" })
	public String userAcct(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		User user = DatasetService.getInstace().getObject(reqDs, User.class);
		user = UserUtils.get(user.getId());
		request.setAttribute("userId", user.getId());
		request.setAttribute("username", user.getLoginName());
		return "starring/sys/userAcct";
	}
	
	@RequestMapping(value = { "userTnt" })
	public String userTnt(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		User user = DatasetService.getInstace().getObject(reqDs, User.class);
		user = UserUtils.get(user.getId());
		request.setAttribute("userId", user.getId());
		request.setAttribute("userName", user.getName());
		return "starring/sys/userTnt";
	}
}
