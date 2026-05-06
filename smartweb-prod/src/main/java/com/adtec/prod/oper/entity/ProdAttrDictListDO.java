package com.adtec.prod.oper.entity;

import com.adtec.sys.common.persistence.BaseDO;

import java.util.ArrayList;
import java.util.List;

public class ProdAttrDictListDO extends BaseDO {
    private static final long serialVersionUID = 1L;
    private String KEY_NO;
    private String ELEM_KEY;
    private String ELEM_NAME;
    private String ELEM_KV;
    private String SHORT_RMRK;
    private String MID_RMRK;
    private String LONG_RMRK;
    private String DAC;

    public String getKEY_NO() {
        return KEY_NO;
    }

    public void setKEY_NO(String KEY_NO) {
        this.KEY_NO = KEY_NO;
    }

    public String getELEM_KEY() {
        return ELEM_KEY;
    }

    public void setELEM_KEY(String ELEM_KEY) {
        this.ELEM_KEY = ELEM_KEY;
    }

    public String getELEM_NAME() {
        return ELEM_NAME;
    }

    public void setELEM_NAME(String ELEM_NAME) {
        this.ELEM_NAME = ELEM_NAME;
    }

    public String getELEM_KV() {
        return ELEM_KV;
    }

    public void setELEM_KV(String ELEM_KV) {
        this.ELEM_KV = ELEM_KV;
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

    public List<String> getIgnoreFields() {
        List<String> ignoreFields = super.getIgnoreFields();
        ignoreFields.add("serialVersionUID");
        ignoreFields.add("DEL_FLAG_NORMAL");
        ignoreFields.add("DEL_FLAG_DELETE");
        ignoreFields.add("id");
        ignoreFields.add("crtr");
        ignoreFields.add("crtTime");
        ignoreFields.add("uptr");
        ignoreFields.add("uptTime");
        ignoreFields.add("rmrk");
        ignoreFields.add("delFlg");
        return ignoreFields;
    }

    public List<String> getMatchFields() {
        List<String> matchField = new ArrayList<String>();
        matchField.add("KEY_NO");
        return matchField;
    }
}
