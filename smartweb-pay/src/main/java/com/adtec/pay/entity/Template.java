package com.adtec.pay.entity;

public class Template {
    /**
     * 业务
     */
    private String BUSI_NO;

    /**
     * 业务名称
     */
    private String BUSI_NAME;

    /**
     * 模板描述
     */
    private String TMPL_DESC;

    /**
     * 字段序号
     */
    private Long SER;
    /**
     * 字段英文
     */
    private String KEY_NO;
    /**
     * 字段中文描述
     */
    private String KEY_DESC;
    /**
     * 模板下载启用状态
     */
    private String EXCEL_STAT;
    /**
     * 明细查询启用状态
     */
    private String QUERY_STAT;
    /**
     * 字段默认值
     */
    private String DEFA_VAL;
    /**
     *
     */
    private String SHORT_RMRK;
    /**
     *
     */
    private String MID_RMRK;
    /**
     *
     */
    private String LONG_RMRK;

    public String getBUSI_NO() {
        return BUSI_NO;
    }

    public void setBUSI_NO(String BUSI_NO) {
        this.BUSI_NO = BUSI_NO;
    }

    public String getBUSI_NAME() {
        return BUSI_NAME;
    }

    public void setBUSI_NAME(String BUSI_NAME) {
        this.BUSI_NAME = BUSI_NAME;
    }

    public String getTMPL_DESC() {
        return TMPL_DESC;
    }

    public void setTMPL_DESC(String TMPL_DESC) {
        this.TMPL_DESC = TMPL_DESC;
    }

    public Long getSER() {
        return SER;
    }

    public void setSER(Long SER) {
        this.SER = SER;
    }

    public String getKEY_NO() {
        return KEY_NO;
    }

    public void setKEY_NO(String KEY_NO) {
        this.KEY_NO = KEY_NO;
    }

    public String getKEY_DESC() {
        return KEY_DESC;
    }

    public void setKEY_DESC(String KEY_DESC) {
        this.KEY_DESC = KEY_DESC;
    }

    public String getDEFA_VAL() {
        return DEFA_VAL;
    }

    public void setDEFA_VAL(String DEFA_VAL) {
        this.DEFA_VAL = DEFA_VAL;
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

    public String getEXCEL_STAT() {
        return EXCEL_STAT;
    }

    public void setEXCEL_STAT(String EXCEL_STAT) {
        this.EXCEL_STAT = EXCEL_STAT;
    }

    public String getQUERY_STAT() {
        return QUERY_STAT;
    }

    public void setQUERY_STAT(String QUERY_STAT) {
        this.QUERY_STAT = QUERY_STAT;
    }
}
