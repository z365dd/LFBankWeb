/**
 *
 */
package com.adtec.sys.modules.sys.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.sys.modules.sys.service.OfficeService;
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

import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.common.util.StringUtil;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.modules.sys.entity.Area;
import com.adtec.sys.modules.sys.service.AreaService;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 区域Controller
 *
 * @version 2013-5-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/area")
public class AreaController extends BaseController {

    private final AreaService areaService;
    private final OfficeService officeService;

    public AreaController(AreaService areaService, OfficeService officeService) {
        this.areaService = areaService;
        this.officeService = officeService;
    }

    @ModelAttribute("area")
    public Area get(@RequestParam(required = false) String id) {
        if (StringUtil.isNotBlank(id)) {
            return areaService.get(id);
        } else {
            return new Area();
        }
    }

    // @RequiresPermissions("sys:area:view")
    @RequestMapping(value = {"list", ""})
    public String list(Area area, Model model) {
        model.addAttribute("list", areaService.findAll());
        return "modules/sys/areaList";
    }

    // @RequiresPermissions("sys:area:view")
    @RequestMapping(value = "form")
    public String form(Area area, Model model) {
        if (area.getParent() == null || area.getParent().getId() == null) {
            area.setParent(UserUtils.getUser().getOffice().getArea());
        }
        area.setParent(areaService.get(area.getParent().getId()));
        model.addAttribute("area", area);
        return "modules/sys/areaForm";
    }

    @RequiresPermissions("sys:area:edit")
    @RequestMapping(value = "save")
    public String save(Area area, Model model, RedirectAttributes redirectAttributes) {
        if (ParamUtil.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/area";
        }
        if (!beanValidator(model, area)) {
            return form(area, model);
        }

        Area temp = areaService.getByCode(area.getRegionCode());
        if (StringUtil.isBlank(area.getId()) && temp != null) {
            addMessage(redirectAttributes, "保存失败，区域编码重复！");
            return form(area, model);
        }

        if (!StringUtil.isBlank(area.getId()) && temp != null) {
            if (!temp.getId().equals(area.getId())) {
                addMessage(redirectAttributes, "保存失败，区域编码重复！");
                return form(area, model);
            }
        }

        areaService.save(area);
        addMessage(redirectAttributes, "保存区域'" + area.getName() + "'成功");
        return "redirect:" + adminPath + "/sys/area/";
    }

    @RequiresPermissions("sys:area:edit")
    @RequestMapping(value = "delete")
    public String delete(Area area, RedirectAttributes redirectAttributes) {
        if (ParamUtil.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/area";
        }

        if (officeService.getOfficeByRegionId(area.getId()) != null) {
            addMessage(redirectAttributes, "不能删除被机构使用的区域！");
            return "redirect:" + adminPath + "/sys/area";
        }

        areaService.delete(area);
        addMessage(redirectAttributes, "删除区域成功");
        return "redirect:" + adminPath + "/sys/area/";
    }

    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId, HttpServletResponse response) {
        return UserUtils.getAreaTree(extId);
    }
}
