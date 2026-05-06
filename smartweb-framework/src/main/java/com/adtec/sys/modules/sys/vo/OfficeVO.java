/**
 * 
 */
package com.adtec.sys.modules.sys.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.adtec.sys.modules.sys.entity.Menu;
import com.google.common.collect.Lists;

/**
 * @author Leize
 * @date: 2018年11月9日下午4:36:20
 */
public class OfficeVO {
	
	private String brchId;
	private String brchName;
	// 保存可分配菜单的ID
	private List<String> tempMenuIdList;
	private List<Menu> menuList;
	private List<Menu> parentOfficeMenuList;
	private String permissionType;
	
	public String getBrchId() {
		return brchId;
	}
	public void setBrchId(String brchId) {
		this.brchId = brchId;
	}
	public String getBrchName() {
		return brchName;
	}
	public void setBrchName(String brchName) {
		this.brchName = brchName;
	}

	public List<String> getTempMenuIdList() {
		return tempMenuIdList;
	}

	public void setTempMenuIdList(List<String> tempMenuIdList) {
		this.tempMenuIdList = tempMenuIdList;
	}

	public void setTempMenuIdListUseMenu(List<Menu> tempMenuList) {
		this.tempMenuIdList = new ArrayList<String>();
		for (Menu menu : tempMenuList) {
			this.tempMenuIdList.add(menu.getId());
		}
	}

	public List<Menu> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}
	public List<Menu> getParentOfficeMenuList() {
		return parentOfficeMenuList;
	}
	public void setParentOfficeMenuList(List<Menu> parentOfficeMenuList) {
		this.parentOfficeMenuList = parentOfficeMenuList;
	}

	public String getAuthTp() {
		return permissionType;
	}

	public void setAuthTp(String permissionType) {
		this.permissionType = permissionType;
	}

	public List<String> getMenuIdList() {
		List<String> menuIdList = Lists.newArrayList();
		for (Menu menu : menuList) {
			menuIdList.add(menu.getId());
		}
		return menuIdList;
	}
	
	public void setMenuIdList(List<String> menuIdList) {
		menuList = Lists.newArrayList();
		for (String menuId : menuIdList) {
			Menu menu = new Menu();
			menu.setId(menuId);
			menuList.add(menu);
		}
	}

	public String getMenuIds() {
		return StringUtils.join(getMenuIdList(), ",");
	}
	
	public void setMenuIds(String menuIds) {
		menuList = Lists.newArrayList();
		if (menuIds != null){
			String[] ids = StringUtils.split(menuIds, ",");
			setMenuIdList(Lists.newArrayList(ids));
		}
	}
	
}
