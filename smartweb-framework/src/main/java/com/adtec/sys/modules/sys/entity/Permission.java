package com.adtec.sys.modules.sys.entity;

import com.adtec.sys.common.persistence.BaseDO;

public class Permission extends BaseDO {
    private static final long serialVersionUID = 1L;

    private String brchId;
    private String tntId;
    private String legaId;
    private String roleId;
    private String authTp;
    private String menuId;

    public Permission() {
        super();
    }

    public Permission(String id) {
        super(id);
    }

    public String getBrchId() {
        return brchId;
    }

    public void setBrchId(String brchId) {
        this.brchId = brchId;
    }

    public String getTntId() {
        return tntId;
    }

    public void setTntId(String tntId) {
        this.tntId = tntId;
    }

    public String getLegaId() {
        return legaId;
    }

    public void setLegaId(String legaId) {
        this.legaId = legaId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getAuthTp() {
        return authTp;
    }

    public void setAuthTp(String authTp) {
        this.authTp = authTp;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    @Override
    public String toString() {
        return id;
    }
}
