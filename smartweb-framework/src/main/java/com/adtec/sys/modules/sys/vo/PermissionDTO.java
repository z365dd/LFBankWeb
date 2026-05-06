package com.adtec.sys.modules.sys.vo;

import com.adtec.sys.modules.sys.entity.Menu;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author chenyl
 * @version 1.0
 * @date 2020-1-20 10:46
 */
public class PermissionDTO {

    private String id;
    private String name;
    private String authTp;
    // 保存可分配菜单的ID
    private List<String> tempMenuIdList;
    private List<Menu> menuList;
    private List<Menu> parentMenuList;

    public String getId() {
        return id;
    }

    public PermissionDTO setId(String id) {
        this.id = id;
        return this;
    }

    public String getAuthTp() {
        return authTp;
    }

    public PermissionDTO setAuthTp(String authTp) {
        this.authTp = authTp;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTempMenuIdList() {
        return tempMenuIdList;
    }

    public void setTempMenuIdList(List<String> tempMenuIdList) {
        this.tempMenuIdList = tempMenuIdList;
    }

    public void setTempMenuIdListUseMenu(List<Menu> tempMenuList) {
        this.tempMenuIdList = Lists.newArrayList();
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

    public List<Menu> getParentMenuList() {
        return parentMenuList;
    }

    public void setParentMenuList(List<Menu> parentMenuList) {
        this.parentMenuList = parentMenuList;
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
