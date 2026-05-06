package com.adtec.prod.oper.entity;

import com.adtec.sys.common.persistence.BaseDO;

import java.util.ArrayList;
import java.util.List;

public class ProdAttrDictDO extends BaseDO {
    private static final long serialVersionUID = 1L;
    private String KEY_NO;
    private String KEY_NAME;
    private String KEY_TP;
    private String VAL_TP;
    private String VAL_LEN;
    private String ENTER_TP;
    private String SHORT_RMRK;
    private String MID_RMRK;
    private String LONG_RMRK;
    private String DAC;
    private List<ProdAttrDictListDO> LIST = new ArrayList<>();

    private String ELEM_KEY;
    private String ELEM_NAME;
    private String ELEM_KV;
    private String QUOTE;

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

    public String getKEY_TP() {
        return KEY_TP;
    }

    public void setKEY_TP(String KEY_TP) {
        this.KEY_TP = KEY_TP;
    }

    public String getVAL_TP() {
        return VAL_TP;
    }

    public void setVAL_TP(String VAL_TP) {
        this.VAL_TP = VAL_TP;
    }

    public String getVAL_LEN() {
        return VAL_LEN;
    }

    public void setVAL_LEN(String VAL_LEN) {
        this.VAL_LEN = VAL_LEN;
    }

    public String getENTER_TP() {
        return ENTER_TP;
    }

    public void setENTER_TP(String ENTER_TP) {
        this.ENTER_TP = ENTER_TP;
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

    public List<ProdAttrDictListDO> getLIST() {
        return LIST;
    }

    public void setLIST(List<ProdAttrDictListDO> LIST) {
        this.LIST = LIST;
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

    public String getQUOTE() {
        return QUOTE;
    }

    public void setQUOTE(String QUOTE) {
        this.QUOTE = QUOTE;
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
        ignoreFields.add("LIST");
        ignoreFields.add("ELEM_KEY");
        ignoreFields.add("ELEM_NAME");
        ignoreFields.add("ELEM_KV");
        ignoreFields.add("QUOTE");
        return ignoreFields;
    }

    public List<String> getMatchFields() {
        List<String> matchField = new ArrayList<String>();
        matchField.add("KEY_NO");
        return matchField;
    }
}
