package com.adtec.sys.modules.sys.vo;

import com.adtec.sys.modules.sys.entity.Menu;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CorporationVO {

    private String legaId;
    private String legaName;
    // 保存可分配菜单的ID
    private List<String> tempMenuIdList;
    private List<Menu> menuList;
    private List<Menu> parentCorporationMenuList;
    private String permissionType;

    public String getLegaId() {
        return legaId;
    }

    public void setLegaId(String legaId) {
        this.legaId = legaId;
    }

    public String getLegaName() {
        return legaName;
    }

    public void setLegaName(String legaName) {
        this.legaName = legaName;
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

    public List<Menu> getParentCorporationMenuList() {
        return parentCorporationMenuList;
    }

    public void setParentCorporationMenuList(List<Menu> parentCorporationMenuList) {
        this.parentCorporationMenuList = parentCorporationMenuList;
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
        if (menuIds != null) {
            String[] ids = StringUtils.split(menuIds, ",");
            setMenuIdList(Lists.newArrayList(ids));
        }
    }

}
