/**
 * 
 */
package com.adtec.sys.modules.sys.web;

import java.security.SecureRandom;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.sys.common.web.BaseController;

/**
 * 标签Controller
 * 
 * @version 2013-3-23
 */
@Controller
@RequestMapping(value = "${adminPath}/tag")
public class TagController extends BaseController {
	
	/**
	 * 树结构选择标签（treeselect.tag）
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "treeselect")
	public String treeselect(HttpServletRequest request, Model model) {
		model.addAttribute("url", request.getParameter("url")); 	// 树结构数据URL
		model.addAttribute("extId", request.getParameter("extId")); // 排除的编号ID
		model.addAttribute("checked", request.getParameter("checked")); // 是否可复选
		model.addAttribute("selectIds", request.getParameter("selectIds")); // 指定默认选中的ID
		model.addAttribute("isAll", request.getParameter("isAll")); 	// 是否读取全部数据，不进行权限过滤
		model.addAttribute("module", request.getParameter("module"));	// 过滤栏目模型（仅针对CMS的Category树）
		return "modules/sys/tagTreeselect";
	}
	
	/**
	 * 图标选择标签（iconselect.tag）
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "iconselect")
	public String iconselect(HttpServletRequest request, Model model) {
		model.addAttribute("value", request.getParameter("value"));
		return "modules/sys/tagIconselect";
	}
	
	/**
	 * 字体图标选择标签（iconselect.tag）
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "fonticonselect")
	public String fonticonselect(HttpServletRequest request, Model model) {
		model.addAttribute("value", request.getParameter("value"));
		return "modules/sys/tagFontIconselect";
	}
	
	/**
	 *    
	 * 弹窗选择数据窗口 SelectDataWindow.tag
	 * 			  add by majianhua
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"tagSelectDataWindow",""})
	public String tagSelectDataWindow(HttpServletRequest request, Model model) {
		model.addAttribute("data_url", request.getParameter("data_url"));
		String [] columnNames = request.getParameter("columnNames").split(",");
		String [] [] columnsTitles = new String [columnNames.length][2];
		String [] temp = null;
		for(int i = 0;i<columnNames.length;i++){
			temp = columnNames[i].split(":");
			if(temp.length == 1){
				columnsTitles[i][0] = temp[0].trim();  //列名
				columnsTitles[i][1] = "";  //列名
			}else if(temp.length == 2){
			columnsTitles[i][0] = temp[0].trim();  //列名
			columnsTitles[i][1] = temp[1]; //标题
			}
		}
		model.addAttribute("columnNames",columnsTitles);
		if(!DataUtil.isNullStr(request.getParameter("searchPara"))){
			model.addAttribute("searchPara",request.getParameter("searchPara"));
		}
		//一个随机的bootstrapTable Id  
		model.addAttribute("bootstrapTableId", "smartweb_tableId"+new SecureRandom().nextInt());
		return "modules/comm/tagSelectDataWindow";
	}
	
	/**
	 * 图片选择标签（imgselect.tag）
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "imgselect")
	public String imgselect(HttpServletRequest request, Model model) {
		model.addAttribute("value", request.getParameter("value"));
		return "modules/sys/tagImgselect";
	}
	
}
