/**
 * 
 */
package com.adtec.sys.modules.sys.web;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.common.util.StringUtil;
import com.adtec.sys.common.persistence.Page;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.modules.sys.entity.Dict;
import com.adtec.sys.modules.sys.service.DictService;
import com.adtec.sys.modules.sys.utils.DictUtils;
import com.adtec.sys.modules.sys.utils.UserUtils;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典Controller
 * 
 * @version 2014-05-16
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/dict")
public class DictController extends BaseController {

	private final DictService dictService;

	private final static String roleType = "ROLE_TP";

	private static final String admin = "manager";

	@Autowired
	public DictController(DictService dictService) {
		this.dictService = dictService;
	}

	@ModelAttribute
	public Dict get(@RequestParam(required=false) String id) {
		if (StringUtil.isNotBlank(id)){
			return dictService.get(id);
		}else{
			return new Dict();
		}
	}
	
	// @RequiresPermissions("sys:dict:view")
	@RequestMapping(value = {"list", ""})
	public String list(Dict dict, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<String> typeList = dictService.findTypeList();
		model.addAttribute("typeList", typeList);
        Page<Dict> page = dictService.findPage(new Page<Dict>(request, response), dict); 
        model.addAttribute("page", page);
		return "modules/sys/dictList";
	}

	// @RequiresPermissions("sys:dict:view")
	@RequestMapping(value = "form")
	public String form(Dict dict, Model model) {
		System.out.println("dictInfo="+dict.getDictInfo());
		model.addAttribute("dict", dict);
		return "modules/sys/dictForm";
	}

	@RequiresPermissions("sys:dict:edit")
	@RequestMapping(value = "save")//@Valid 
	public String save(Dict dict, Model model, RedirectAttributes redirectAttributes) {
		if(ParamUtil.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/dict/?repage&dictTp="+dict.getDictTp();
		}
		if (!beanValidator(model, dict)){
			return form(dict, model);
		}

		Dict temp = dictService.get(dict.getDictTp(), dict.getValue());

		if (StringUtils.isBlank(dict.getId()) && temp != null) {
			addMessage(redirectAttributes, "保存失败，字典类型和字典值重复！");
			return "redirect:" + adminPath + "/sys/dict/?repage&dictTp="+dict.getDictTp();
		}

		if (!StringUtils.isBlank(dict.getId()) && temp != null) {
			if (!temp.getId().equals(dict.getId())) {
				addMessage(redirectAttributes, "保存失败，字典类型和字典值重复！");
				return "redirect:" + adminPath + "/sys/dict/?repage&dictTp=" + dict.getDictTp();
			}
		}

		try {
			dictService.save(dict);
		} catch (Exception e) {
			addMessage(redirectAttributes, "保存失败，" + e.getMessage());
			return "redirect:" + adminPath + "/sys/dict/?repage&dictTp=" + dict.getDictTp();
		}

		addMessage(redirectAttributes, "保存字典'" + dict.getLabel() + "'成功");
		return "redirect:" + adminPath + "/sys/dict/?repage&dictTp="+dict.getDictTp();
	}
	
	@RequiresPermissions("sys:dict:edit")
	@RequestMapping(value = "delete")
	public String delete(Dict dict, RedirectAttributes redirectAttributes) {
		if(ParamUtil.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/dict/?repage";
		}
		dictService.delete(dict);
		addMessage(redirectAttributes, "删除字典成功");
		return "redirect:" + adminPath + "/sys/dict/?repage&dictTp="+dict.getDictTp();
	}
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String type, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		Dict dict = new Dict();
		dict.setDictTp(type);
		List<Dict> list = dictService.findList(dict);
		for (int i=0; i<list.size(); i++){
			Dict e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentId());
			map.put("name", StringUtil.replace(e.getLabel(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}
	
	@ResponseBody
	@RequestMapping(value = "listData")
	public List<Dict> listData(@RequestParam(required=false) String type) {
		Dict dict = new Dict();
		dict.setDictTp(type);
		return dictService.findList(dict);
	}

	@ResponseBody
	@RequestMapping(value = "selectData")
	public void getSelectJson(@RequestParam(required=false) String type,@RequestParam(required=false) String isblank,@RequestParam(required=false) String blankText,@RequestParam(required=false) String blankValue, HttpServletResponse response) {
		/*前台接收value、label、title、selected、disabled*/
		logger.info("getSelectJson--type【"+type+"】");
		List<Dict> list = new ArrayList<Dict>();
		if(!DataUtil.isNullStr(isblank) && "true".equals(isblank)){
			//单选下拉框需要添加空选项
			Dict d = new Dict();
			d.setDictTp(type);
			d.setLabel(blankText);
			d.setValue(blankValue);
			list.add(d);
		}
		list.addAll(DictUtils.getDictList(type));
		//业务管理员不能新增系统管理员类型的角色、用户
		if(roleType.equals(type) && !UserUtils.getUser().isAdmin()){
			for (Dict dict : list) {
				if(admin.equals(dict.getValue())){
					list.remove(dict);
					break;
				}
			}
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("retCode", "0000");
		m.put("list", list);
		renderString(response, m);
	}
}
