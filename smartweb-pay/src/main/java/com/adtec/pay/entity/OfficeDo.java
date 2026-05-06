package com.adtec.pay.entity;

import com.adtec.sys.common.persistence.BaseDO;

public class OfficeDo extends BaseDO {

    private static final long serialVersionUID = -6859495405938196730L;
    //机构层级 0-总行 1-分行 2-支行
    private String parentId;
    //分支行名称
    private String name;
    //分支行机构号
    private String brchCode;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrchCode() {
        return brchCode;
    }

    public void setBrchCode(String brchCode) {
        this.brchCode = brchCode;
    }


}
