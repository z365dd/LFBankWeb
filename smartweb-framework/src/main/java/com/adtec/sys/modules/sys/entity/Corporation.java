package com.adtec.sys.modules.sys.entity;

import com.adtec.framework.common.util.ClassUtil;
import com.adtec.sys.common.persistence.BaseDO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;

import javax.xml.bind.annotation.XmlTransient;
import java.util.Map;

public class Corporation extends BaseDO {
    private static final long serialVersionUID = 1L;

    protected Map<String, String> sqlMap;
    private String legaNo;
    private String name;
    private String engName;
    private String ctctAddr;
    private String parentIdList;
    protected Corporation parent;    // 父级编号
    private String validSwitchFlg;//是否可用

    public Corporation() {
        super();
    }

    public Corporation(String id) {
        super(id);
    }

    @JsonIgnore
    @XmlTransient
    public Map<String, String> getSqlMap() {
        if (sqlMap == null) {
            sqlMap = Maps.newHashMap();
        }
        return sqlMap;
    }

    public void setSqlMap(Map<String, String> sqlMap) {
        this.sqlMap = sqlMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLegaNo() {
        return legaNo;
    }

    public void setLegaNo(String legaNo) {
        this.legaNo = legaNo;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getCtctAddr() {
        return ctctAddr;
    }

    public void setCtctAddr(String ctctAddr) {
        this.ctctAddr = ctctAddr;
    }

    public String getParentIdList() {
        return parentIdList;
    }

    public void setParentIdList(String parentIdList) {
        this.parentIdList = parentIdList;
    }

    public Corporation getParent() {
        return parent;
    }

    public void setParent(Corporation parent) {
        this.parent = parent;
    }

    public String getParentId() {
        String id = null;
        if (parent != null) {
            id = (String) ClassUtil.getFieldValue(parent, "id");
        }
        return com.adtec.framework.common.util.StringUtil.isNotBlank(id) ? id : "0";
    }

    public String getValidSwitchFlg() {
        return validSwitchFlg;
    }

    public void setValidSwitchFlg(String validSwitchFlg) {
        this.validSwitchFlg = validSwitchFlg;
    }

    @Override
    public String toString() {
        return name;
    }
}
