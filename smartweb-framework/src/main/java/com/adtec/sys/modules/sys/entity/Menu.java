package com.adtec.sys.modules.sys.entity;

import com.adtec.sys.common.persistence.BaseDO;
import com.adtec.sys.common.utils.excel.annotation.ExcelField;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单Entity
 *
 * @version 2013-05-15
 */
public class Menu extends BaseDO {

    private static final long serialVersionUID = 1L;
    private String menuIcon;    //一级菜单图片
    private Menu parent;    // 父级菜单
    private String parentName; // 父菜单名称
    private String parentIdList; // 所有父级编号
    private String name;    // 名称
    private String menuLink;    // 链接
    private String windowVal;    // 目标（ mainFrame、_blank、_self、_parent、_top）
    private String appIcon;    // 图标
    private Integer sort;    // 排序
    private String dpyFlg;    // 是否在菜单中显示（1：显示；0：不显示）
    private String auth; // 权限标识
    private String userId;

	private List<Menu> children;
	
    // 菜单创建者法人ID
    private String legaId;
    // 菜单创建者法人名称
    private String legaName;
    // 菜单创建者机构ID
    private String brchId;
    // 菜单创建者机构名称
    private String brchName;
    // 菜单创建者租户ID
    private String tntId;
    // 菜单创建者租户名称
    private String tntName;
    // 菜单创建者角色ID
    private List<String> roleId = new ArrayList<String>();
    // 菜单创建者角色名称
    private List<String> roleName = new ArrayList<String>();
    // 当前用户是否是菜单拥有者
    private String isOwn = "0";

    public Menu() {
        super();
        this.sort = 30;
        this.dpyFlg = "1";
    }

    public Menu(String id) {
        super(id);
    }

    @JsonIgnore
    public static String getRootId() {
        return "1";
    }

    @JsonIgnore
    public static void sortList(List<Menu> list, List<Menu> sourcelist, String parentId, boolean cascade) {
        for (int i = 0; i < sourcelist.size(); i++) {
            Menu e = sourcelist.get(i);
            if (e.getParent() != null && e.getParent().getId() != null
                    && e.getParent().getId().equals(parentId) && e.getParentIdList().split("\\,").length < 5) {
                list.add(e);
                if (cascade) {
                    // 判断是否还有子节点, 有则继续获取子节点
                    for (int j = 0; j < sourcelist.size(); j++) {
                        Menu child = sourcelist.get(j);
                        if (child.getParent() != null && child.getParent().getId() != null
                                && child.getParent().getId().equals(e.getId())) {
                            sortList(list, sourcelist, e.getId(), true);
                            break;
                        }
                    }
                }
            }
        }
    }

    @JsonIgnore
    public static void sortListAuth(List<Menu> list, List<Menu> sourcelist, String parentId, boolean cascade) {
        for (int i = 0; i < sourcelist.size(); i++) {
            Menu e = sourcelist.get(i);
            if (e.getParent() != null && e.getParent().getId() != null
                    && e.getParent().getId().equals(parentId)) {
                if (e.getParentIdList().split("\\,").length != 5) {
                    e.setMenuLink(null);
                }
                list.add(e);
                if (cascade) {
                    // 判断是否还有子节点, 有则继续获取子节点
                    for (int j = 0; j < sourcelist.size(); j++) {
                        Menu child = sourcelist.get(j);
                        if (child.getParent() != null && child.getParent().getId() != null
                                && child.getParent().getId().equals(e.getId())) {
                            sortListAuth(list, sourcelist, e.getId(), true);
                            break;
                        }
                    }
                }
            }
        }
    }

    @Length(min = 0, max = 2000)
    @ExcelField(title = "菜单链接", align = 1, sort = 30)
    public String getMenuLink() {
        return menuLink;
    }

    public void setMenuLink(String menuLink) {
        this.menuLink = menuLink;
    }

    @Length(min = 0, max = 100)
    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

    @Length(min = 0, max = 100)
    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    @Length(min = 1, max = 1)
    @ExcelField(title = "是否可见", align = 1, sort = 50)
    public String getDpyFlg() {
        return dpyFlg;
    }

    public void setDpyFlg(String dpyFlg) {
        this.dpyFlg = dpyFlg;
    }

    @Length(min = 1, max = 100)
    @ExcelField(title = "菜单名称", align = 1, sort = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    @ExcelField(title = "父菜单名称", align = 1, sort = 10)
    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentId() {
        return parent != null && parent.getId() != null ? parent.getId() : "0";
    }

    @Length(min = 1, max = 2000)
    public String getParentIdList() {
        return parentIdList;
    }

    public void setParentIdList(String parentIdList) {
        this.parentIdList = parentIdList;
    }

    @Length(min = 0, max = 200)
    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    @NotNull
    @ExcelField(title = "排序值", align = 1, sort = 40)
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Length(min = 0, max = 20)
    public String getWindowVal() {
        return windowVal;
    }

    public void setWindowVal(String windowVal) {
        this.windowVal = windowVal;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

	public List<Menu> getChildren(){
		return children;
	}

	public void setChildren(List<Menu> menuList){
		this.children = menuList;
	}

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

    public String getRentid() {
        return tntId;
    }

    public void setRentid(String tntId) {
        this.tntId = tntId;
    }

    public String getTntName() {
        return tntName;
    }

    public void setTntName(String tntName) {
        this.tntName = tntName;
    }

    public List<String> getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId.add(roleId);
    }

    public String getRoleName() {
        StringBuilder sb = new StringBuilder();
        for (String s : roleName) {
            if (sb.length() > 0) {
                sb.append(",").append(s);
            } else {
                sb.append(s);
            }
        }
        return sb.toString();
    }

    public void setRoleName(String roleName) {
        this.roleName.add(roleName);
    }

    public String getIsOwn() {
        return isOwn;
    }

    public void setIsOwn(String isOwn) {
        this.isOwn = isOwn;
    }

    @Override
    public String toString() {
        return name;
    }


}