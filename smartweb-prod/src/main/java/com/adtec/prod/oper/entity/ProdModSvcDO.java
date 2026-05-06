package com.adtec.prod.oper.entity;

import com.adtec.sys.common.persistence.BaseDO;

public class ProdModSvcDO extends BaseDO {
    private static final long serialVersionUID = 1L;
    private String COMP_NO;
    private String COMP_NAME;
    private String SVC_CODE;
    private String SVC_NAME;
    private String SVC_DESC;
    private String CHK_SIGN_FLG;
    private String CHK_OTH_SYS_FLG;
    private String KEY_TYPE;
    private String KEY_NO;
    private String KEY_NAME;
    private String SHORT_RMRK;
    private String MID_RMRK;
    private String LONG_RMRK;
    private String DAC;

    public String getCOMP_NO() {
        return COMP_NO;
    }

    public void setCOMP_NO(String COMP_NO) {
        this.COMP_NO = COMP_NO;
    }

    public String getCOMP_NAME() {
        return COMP_NAME;
    }

    public void setCOMP_NAME(String COMP_NAME) {
        this.COMP_NAME = COMP_NAME;
    }

    public String getSVC_CODE() {
        return SVC_CODE;
    }

    public void setSVC_CODE(String SVC_CODE) {
        this.SVC_CODE = SVC_CODE;
    }

    public String getSVC_NAME() {
        return SVC_NAME;
    }

    public void setSVC_NAME(String SVC_NAME) {
        this.SVC_NAME = SVC_NAME;
    }

    public String getSVC_DESC() {
        return SVC_DESC;
    }

    public void setSVC_DESC(String SVC_DESC) {
        this.SVC_DESC = SVC_DESC;
    }

    public String getCHK_SIGN_FLG() {
        return CHK_SIGN_FLG;
    }

    public void setCHK_SIGN_FLG(String CHK_SIGN_FLG) {
        this.CHK_SIGN_FLG = CHK_SIGN_FLG;
    }

    public String getCHK_OTH_SYS_FLG() {
        return CHK_OTH_SYS_FLG;
    }

    public void setCHK_OTH_SYS_FLG(String CHK_OTH_SYS_FLG) {
        this.CHK_OTH_SYS_FLG = CHK_OTH_SYS_FLG;
    }

    public String getKEY_TYPE() {
        return KEY_TYPE;
    }

    public void setKEY_TYPE(String KEY_TYPE) {
        this.KEY_TYPE = KEY_TYPE;
    }

    public String getKEY_NO() {
        return KEY_NO;
    }

    public void setKEY_NO(String KEY_NO) {
        this.KEY_NO = KEY_NO;
    }

    public String getKEY_NAME() {
        return KEY_NAME;
    }

    public void setKEY_NAME(String KEY_NAME) {
        this.KEY_NAME = KEY_NAME;
    }

    public String getSHORT_RMRK() {
        return SHORT_RMRK;
    }

    public void setSHORT_RMRK(String SHORT_RMRK) {
        this.SHORT_RMRK = SHORT_RMRK;
    }

    public String getMID_RMRK() {
        return MID_RMRK;
    }

    public void setMID_RMRK(String MID_RMRK) {
        this.MID_RMRK = MID_RMRK;
    }

    public String getLONG_RMRK() {
        return LONG_RMRK;
    }

    public void setLONG_RMRK(String LONG_RMRK) {
        this.LONG_RMRK = LONG_RMRK;
    }

    public String getDAC() {
        return DAC;
    }

    public void setDAC(String DAC) {
        this.DAC = DAC;
    }
}
