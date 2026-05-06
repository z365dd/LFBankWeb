/**
 *
 */
package com.adtec.sys.modules.sys.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.common.util.StringUtil;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.modules.sys.entity.Corporation;
import com.adtec.sys.modules.sys.entity.Office;
import com.adtec.sys.modules.sys.entity.Rent;
import com.adtec.sys.modules.sys.entity.Role;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.service.OfficeService;
import com.adtec.sys.modules.sys.service.PermissionService;
import com.adtec.sys.modules.sys.service.SystemService;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.adtec.sys.modules.sys.vo.PermissionDTO;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 机构Controller
 *
 * @version 2013-5-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/office")
public class OfficeController extends BaseController {
    @Autowired
    private OfficeService officeService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private SystemService systemService;

    @ModelAttribute("office")
    public Office get(@RequestParam(required = false) String id) {
        if (StringUtil.isNotBlank(id)) {
            return officeService.get(id);
        } else {
            return new Office();
        }
    }

    // @RequiresPermissions("sys:office:view")
    @RequestMapping(value = {"list"})
    public String findList(Office office, Model model) {
        model.addAttribute("list", officeService.findList(false));
        if (office.getId() == null || StringUtil.isBlank(office.getId())) {
            User user = UserUtils.getUser();
            office.setId(user.getOffice().getId());
            model.addAttribute("office", office);
        }
        return "modules/sys/officeList";
    }

    // @RequiresPermissions("sys:office:view")
    @RequestMapping(value = "form")
    public String form(Office office, Model model) {
        boolean isUpdate = false;
        User user = UserUtils.getUser();
        office.setParent(officeService.get(office.getParent().getId()));
//		if(office.getRent()==null){
//			isUpdate = true;
        if (StringUtil.isBlank(office.getId())) {
//				office.setRent(user.getRent());
        } else {
            office = officeService.get(office);
        }
//		}
        if (office.getArea() == null) {
            office.setArea(user.getOffice().getArea());
        }
        // 自动获取排序号
        if (StringUtil.isBlank(office.getId()) && office.getParent() != null) {
            office.setBrchCode(officeService.getNewOfficeCode(office.getParent().getId(), office.getParent().getBrchCode()));
        }
        model.addAttribute("isUpdate", isUpdate);
        model.addAttribute("office", office);
        return "modules/sys/officeForm";
    }

    // @RequiresPermissions("sys:office:view")
    @RequestMapping(value = "detail")
    public String detail(Office office, Model model) {
        User user = UserUtils.getUser();
        office.setParent(officeService.get(office.getParent().getId()));
//		if(office.getRent()==null){
//			if(StringUtils.isBlank(office.getId())){
//				office.setRent(user.getRent());
//			}
//			else{
        office = officeService.get(office);
//			}
//		}
        if (office.getArea() == null) {
            office.setArea(user.getOffice().getArea());
        }
        // 自动获取排序号
        if (StringUtil.isBlank(office.getId()) && office.getParent() != null) {
            office.setBrchCode(officeService.getNewOfficeCode(office.getParent().getId(), office.getParent().getBrchCode()));
        }
        model.addAttribute("office", office);
        return "modules/sys/officeDetail";
    }

    @RequiresPermissions("sys:office:edit")
    @RequestMapping(value = "save")
    public String save(Office office, Model model, RedirectAttributes redirectAttributes) {
        if (ParamUtil.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/office/list";
        }
        if (!beanValidator(model, office)) {
            return form(office, model);
        }
        List<Office> officeList = officeService.findListByParent(office.getParent());
        if (officeList != null && !officeList.isEmpty()) {
            for (Office o : officeList) {
                if (o.getName().equals(office.getName()) && !o.getId().equals(office.getId())) {
                    addMessage(redirectAttributes, "同机构下不允许出现同名机构");
                    User user = UserUtils.getUser();
                    String id = user.getOffice().getId();
                    return "redirect:" + adminPath + "/sys/office/list?id=" + id + "&parentIdList=";
                }
            }
        }

        User user = UserUtils.getUser();
        String id = user.getOffice().getId();

        boolean isAdd = false;
        if (StringUtil.isBlank(office.getId())) {
            isAdd = true;
        }
        try {
            officeService.save(office);
        } catch (Exception e) {
            if (isAdd) {
                office.setId(null);
            }
            addMessage(model, "保存失败，" + e.getMessage());
            return form(office, model);
        }

        addMessage(redirectAttributes, "保存机构'" + office.getName() + "'成功");
        return "redirect:" + adminPath + "/sys/office/list?id=" + id + "&parentIdList=";
    }

    @RequiresPermissions("sys:office:edit")
    @RequestMapping(value = "delete")
    public String delete(Office office, RedirectAttributes redirectAttributes) {
        if (ParamUtil.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/office/list";
        }
        List<User> list = systemService.findUserByBrchId(office.getId());
        if(list != null && list.size() > 0){
            addMessage(redirectAttributes, "机构已被使用，不能删除！");
            return "redirect:" + adminPath + "/sys/office/list";
        }
        else {
            officeService.delete(office);
            addMessage(redirectAttributes, "删除机构成功");
        }
        User user = UserUtils.getUser();
        String id = user.getOffice().getId();
        return "redirect:" + adminPath + "/sys/office/list?id=" + id + "&parentIdList=";
    }

    /**
     * 获取机构JSON数据。
     * @param extId 排除的ID
     * @param type    类型（1：公司；2：部门/小组/其它；3：用户；4：角色）
     * @param isAll
     * @param response
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId,
			@RequestParam(required = false) String type, @RequestParam(required = false) Boolean isAll,
			HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Office> list = officeService.findList(isAll);
		// 5-租户和6-法人与机构没有关联关系，所以此处分开处理
		if ("4".equals(type)) {
			// 获取角色列表
			List<Role> roleList = systemService.findAllRole();
			for (Role role : roleList) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", role.getId());
				map.put("name", role.getName());
				mapList.add(map);
			}
		} else if ("5".equals(type)) {
			// 获取租户列表
			List<Rent> rentList = systemService.findAllRent();
			for (Rent rent : rentList) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", rent.getId());
				map.put("pId", rent.getParentId());
				map.put("pIds", rent.getParentIdList());
				map.put("name", rent.getName());
				mapList.add(map);
			}
		} else if ("6".equals(type)) {
			// 获取法人列表
			List<Corporation> corList = UserUtils.getCorporationList();
			for (Corporation cor : corList) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", cor.getId());
				map.put("pId", cor.getParentId());
				map.put("pIds", cor.getParentIdList());
				map.put("name", cor.getName());
				mapList.add(map);
			}
		} else {
			for (Office e : list) {
				if ((StringUtil.isBlank(extId)
						|| !extId.equals(e.getId()) && !e.getParentIdList().contains("," + extId + ","))
						&& (type == null || (!type.equals("1") || type.equals(e.getBrchTp())))
						&& ParamUtil.YES.equals(e.getValidSwitchFlg())) {
					Map<String, Object> map = Maps.newHashMap();
					map.put("id", e.getId());
					map.put("pId", e.getParentId());
					map.put("pIds", e.getParentIdList());
					map.put("name", e.getName());
					if (("3".equals(type) || "4".equals(type))) {
						map.put("isParent", true);
					}
					// 对于只获取租户的，则只需要总行节点对应的父节点为0
					if ("4".equals(type) && "0".equals(e.getParentId())) {
						mapList.add(map);
						break;
					} else {
						mapList.add(map);
					}
				}
			}
		}
		return mapList;
	}


    /*获取当前用户下的所有租户列表*/
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "getSubOfficeByCurrUser")
    public void getSubOfficeByCurrUser(HttpServletRequest request, HttpServletResponse response) {
        logger.info("getSubOfficeByCurrUser");
        Office office = UserUtils.getUser().getOffice();
        List<Office> list = officeService.findListByParent(office);
        Map<String, Object> m = new HashMap<String, Object>(2);
        m.put("retCode", "0000");
        m.put("list", list);
        renderString(response, m);
    }

    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "checkCode")
    public String checkCode(String oldCode, String brchCode) {
        if (brchCode != null && brchCode.equals(oldCode)) {
            return "true";
        } else if (brchCode != null) {
            // 中文正则匹配表达式
            // String pattern = "[\u4e00-\u9fa5]+";
            // 数字、26个英文字母或者下划线
            String pattern = "^\\w+$";

            boolean isMatch = Pattern.matches(pattern, brchCode);
            if (!isMatch) {
                return "false";
            } else if (null == officeService.getOfficeByCode(brchCode)) {
                return "true";
            }
        }
        return "false";
    }

    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "checkName")
    public String checkName(String oldName, String name) {
        if (name != null && name.equals(oldName)) {
            return "true";
        } else if (name != null && null == officeService.getOfficeByName(name)) {
            return "true";
        }
        return "false";
    }

    @RequestMapping("seePermissionToOffice")
    public String seePermissionToOffice(String id, Model model) {
        PermissionDTO permissionOwn = permissionService.assembleOfficeVoByAuthTp(id, "own");
        model.addAttribute("permissionOwn", permissionOwn);
        PermissionDTO permissionTransfer = permissionService.assembleOfficeVoByAuthTp(id, "transfer");
        model.addAttribute("permissionTransfer", permissionTransfer);
        PermissionDTO permissionUse = permissionService.assembleOfficeVoByAuthTp(id, "use");
        model.addAttribute("permissionUse", permissionUse);
        return "modules/sys/seePermissionToOffice";
    }

    @RequestMapping("assignPermissionToOffice")
    public String assignPermissionToOffice(String id, String permissionType, Model model) {
        PermissionDTO permissionDTO = permissionService.assembleOfficeVo(id, permissionType);
        model.addAttribute("permissionDTO", permissionDTO);
        return "modules/sys/assignPermissionToOffice";
    }

    @RequestMapping(value = "savePermissionToOffice")
    public String savePermissionToOffice(PermissionDTO permissionDTO, RedirectAttributes redirectAttributes) {
        if (ParamUtil.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/office/list";
        }
        int resultCount = permissionService.savePermissionToOffice(permissionDTO);
        if (resultCount > 0) {
            addMessage(redirectAttributes, "分配权限组成功");
        } else {
            addMessage(redirectAttributes, "分配权限组失败");
        }
        User user = UserUtils.getUser();
        String id = user.getOffice().getId();
        return "redirect:" + adminPath + "/sys/office/list?id=" + id + "&parentIdList=";
    }

}
