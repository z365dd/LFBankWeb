package com.adtec.pay.entity;

import com.adtec.sys.common.persistence.BaseDO;

public class ProjDo extends BaseDO {
    private static final long serialVersionUID = 4524922918310436925L;

    private String busiNo;

    private String projName;

    public String getBusiNo() {
        return busiNo;
    }

    public void setBusiNo(String busiNo) {
        this.busiNo = busiNo;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }
}
