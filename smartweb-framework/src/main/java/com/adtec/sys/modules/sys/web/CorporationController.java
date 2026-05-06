package com.adtec.sys.modules.sys.web;

import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.common.util.StringUtil;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.modules.sys.entity.Corporation;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.service.CorporationService;
import com.adtec.sys.modules.sys.service.PermissionService;
import com.adtec.sys.modules.sys.service.SystemService;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.adtec.sys.modules.sys.vo.PermissionDTO;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Controller
@RequestMapping(value = "${adminPath}/sys/corporation")
public class CorporationController extends BaseController {

    @Autowired
    private SystemService systemService;

    private final CorporationService corporationService;
    private final PermissionService permissionService;

    public CorporationController(CorporationService corporationService, PermissionService permissionService) {
        this.corporationService = corporationService;
        this.permissionService = permissionService;
    }

    // @RequiresPermissions("sys:corporation:view")
    @RequestMapping(value = {"list", ""})
    public String list(Corporation corporation, Model model) {
        List<Corporation> list = corporationService.findAllCorporation();
        model.addAttribute("list", list);
        if (corporation.getId() == null || StringUtil.isBlank(corporation.getId())) {
            User user = UserUtils.getUser();
            corporation.setId(user.getCorporation().getId());
            model.addAttribute("corporation", corporation);
        }
        return "modules/sys/corporationList";
    }

    @ResponseBody
    @RequestMapping(value = "checkLegaNo")
    public String checkLegaNo(String oldLegaNo, String legaNo) {
        if (legaNo != null && legaNo.equals(oldLegaNo)) {
            return "true";
        } else if (legaNo != null) {
            // 中文正则匹配表达式
            // String pattern = "[\u4e00-\u9fa5]+";
            // 数字、26个英文字母或者下划线
            String pattern = "^\\w+$";

            boolean isMatch =  Pattern.matches(pattern, legaNo);
            if (!isMatch) {
                return "false";
            } else if (corporationService.getByNumber(legaNo) == null) {
                return "true";
            }
        }
        return "false";
    }

    @ResponseBody
    @RequestMapping(value = "checkEngName")
    public String checkEngName(String oldEngName, String engName) {
        if (engName != null && engName.equals(oldEngName)) {
            return "true";
        }else if (engName != null){
            // 中文正则匹配表达式
            // String pattern = "[\u4e00-\u9fa5]+";
            // 数字、26个英文字母或者下划线
            String pattern = "^\\w+$";

            boolean isMatch =  Pattern.matches(pattern, engName);
            if (!isMatch) {
                return "false";
            } else if (corporationService.getByEngName(engName) == null) {
                return "true";
            }
        }
        return "false";
    }

    @RequiresPermissions("sys:corporation:edit")
    @RequestMapping(value = "delete")
    public String delete(Corporation corporation, RedirectAttributes redirectAttributes) {
        if (ParamUtil.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/corporation/?repage";
        }
        List<User> list = systemService.findUserByLegaId(corporation);
        if(list != null && list.size() > 0){
            addMessage(redirectAttributes, "法人已被使用，不能删除！");
            return "redirect:" + adminPath + "/sys/corporation/?repage";
        }
        // 非管理员的情况下不可以删除自己所有的法人信息
        if (!UserUtils.getUser().isManager()) {
            addMessage(redirectAttributes, "删除法人失败, 非管理员不能删除法人信息");
        } else {
            corporationService.delete(corporation);
            addMessage(redirectAttributes, "删除法人成功");
        }
        return "redirect:" + adminPath + "/sys/corporation/?repage";
    }

    // @RequiresPermissions("sys:corporation:view")
    @RequestMapping(value = "form")
    public String form(Corporation corporation, Model model) {
        if (corporation.getParent() == null) {
            corporation = corporationService.get(corporation.getId());
        } else {
            corporation.setParent(corporationService.get(corporation.getParent().getId()));
        }
        model.addAttribute("isUpdate", false);
        model.addAttribute("corporation", corporation);
        return "modules/sys/corporationForm";
    }

    @RequiresPermissions("sys:corporation:edit")
    @RequestMapping(value = "save")
    public String save(HttpServletRequest request, Corporation corporation, Model model, RedirectAttributes redirectAttributes) {
        if (ParamUtil.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/corporation/?repage";
        }
        if (!beanValidator(model, corporation)) {
            return form(corporation, model);
        }

        try {
            corporationService.save(corporation);
        } catch (Exception e) {
            logger.error("保存失败", e);
            addMessage(redirectAttributes, "保存失败，" + e.getMessage());
            return "redirect:" + adminPath + "/sys/corporation/?repage";
        }

        addMessage(redirectAttributes, "保存法人'" + corporation.getName() + "'成功");
        return "redirect:" + adminPath + "/sys/corporation/?repage";
    }

    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId, @RequestParam(required = false) String type,
                                              @RequestParam(required = false) Boolean isAll, HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<Corporation> list = corporationService.findAllCorporation();
        for (Corporation e : list) {
            if (StringUtil.isBlank(extId) || !extId.equals(e.getId()) && !e.getParentIdList().contains("," + extId + ",")) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", e.getId());
                map.put("pId", e.getParentId());
                map.put("pIds", e.getParentIdList());
                map.put("name", e.getName());
                mapList.add(map);
            }
        }
        return mapList;
    }

    @RequestMapping("seePermissionToCorporation")
    public String seePermissionToCorporation(String id, Model model) {
        PermissionDTO corporationVoOwn = permissionService.assembleCorporationVoByAuthTp(id, "own");
        model.addAttribute("corporationVoOwn", corporationVoOwn);
        PermissionDTO corporationVoTransfer = permissionService.assembleCorporationVoByAuthTp(id, "transfer");
        model.addAttribute("corporationVoTransfer", corporationVoTransfer);
        PermissionDTO corporationVoUse = permissionService.assembleCorporationVoByAuthTp(id, "use");
        model.addAttribute("corporationVoUse", corporationVoUse);
        return "modules/sys/seePermissionToCorporation";
    }

    @RequestMapping("assignPermissionToCorporation")
    public String assignPermissionToCorporation(String id, String permissionType, Model model) {
        PermissionDTO permissionDTO = permissionService.assembleCorporationVo(id, permissionType);
        model.addAttribute("permissionDTO", permissionDTO);
        return "modules/sys/assignPermissionToCorporation";
    }

    @RequestMapping(value = "savePermissionToCorporation")
    public String savePermissionToCorporation(PermissionDTO permissionDTO, RedirectAttributes redirectAttributes) {
        if (ParamUtil.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/corporation/list";
        }
        int resultCount = permissionService.savePermissionToCorporation(permissionDTO);
        if (resultCount > 0) {
            addMessage(redirectAttributes, "分配权限组成功");
        } else {
            addMessage(redirectAttributes, "分配权限组失败");
        }
        User user = UserUtils.getUser();
        String id = user.getCorporation().getId();
        return "redirect:" + adminPath + "/sys/corporation/list?id=" + id + "&parentIdList=";
    }

}
