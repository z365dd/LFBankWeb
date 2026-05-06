package com.adtec.pay.dto.proj.mng;

public class MngProjQryResList {

    /**
     * 项目类型
     * 01-代收
     * 02-代付
     * 03-缴费
     */
    private String PROJ_TP;
    /**
     * 客户名称
     */
    private String CUST_NAME;
    /**
     * 所属机构
     */
    private String BRCH;
    /**
     * 所属机构名称
     */
    private String BRCH_NAME;
    /**
     * 账号
     */
    private String ACCT;
    /**
     * 客户经理名称
     */
    private String CUST_MNG_NAME;
    /**
     * 签约日期
     */
    private String SIGN_DATE;
    /**
     * 解约日期
     */
    private String UNSIGN_DATE;
    /**
     * 管理项目文件
     */
    private String MNG_FILE_NAME;
    /**
     * 响应ClOB
     */
    private String RESP_CLOB;
    /**
     * 巡检频次
     */
    private String INSP_CYC;
    /**
     * 状态
     * 00-待提交审批
     * 10-待分行初审
     * 20-待总行审批
     * 30-审批成功
     */
    private String STAT;
    /**
     * 申请人
     */
    private String APPR_NAME;
    /**
     * 分行审批人
     */
    private String FST_APPR_NAME;
    /**
     * 总行审批人
     */
    private String SECD_APPR_NAME;
    /**
     * 短备注
     */
    private String SHORT_RMRK;
    /**
     * 中备注
     */
    private String MID_RMRK;
    /**
     * 长备注
     */
    private String LONG_RMRK;


    public String getPROJ_TP() {
        return PROJ_TP;
    }

    public void setPROJ_TP(String PROJ_TP) {
        this.PROJ_TP = PROJ_TP;
    }

    public String getCUST_NAME() {
        return CUST_NAME;
    }

    public void setCUST_NAME(String CUST_NAME) {
        this.CUST_NAME = CUST_NAME;
    }

    public String getBRCH() {
        return BRCH;
    }

    public void setBRCH(String BRCH) {
        this.BRCH = BRCH;
    }

    public String getBRCH_NAME() {
        return BRCH_NAME;
    }

    public void setBRCH_NAME(String BRCH_NAME) {
        this.BRCH_NAME = BRCH_NAME;
    }

    public String getACCT() {
        return ACCT;
    }

    public void setACCT(String ACCT) {
        this.ACCT = ACCT;
    }

    public String getCUST_MNG_NAME() {
        return CUST_MNG_NAME;
    }

    public void setCUST_MNG_NAME(String CUST_MNG_NAME) {
        this.CUST_MNG_NAME = CUST_MNG_NAME;
    }

    public String getSIGN_DATE() {
        return SIGN_DATE;
    }

    public void setSIGN_DATE(String SIGN_DATE) {
        this.SIGN_DATE = SIGN_DATE;
    }

    public String getUNSIGN_DATE() {
        return UNSIGN_DATE;
    }

    public void setUNSIGN_DATE(String UNSIGN_DATE) {
        this.UNSIGN_DATE = UNSIGN_DATE;
    }

    public String getMNG_FILE_NAME() {
        return MNG_FILE_NAME;
    }

    public void setMNG_FILE_NAME(String MNG_FILE_NAME) {
        this.MNG_FILE_NAME = MNG_FILE_NAME;
    }

    public String getRESP_CLOB() {
        return RESP_CLOB;
    }

    public void setRESP_CLOB(String RESP_CLOB) {
        this.RESP_CLOB = RESP_CLOB;
    }

    public String getINSP_CYC() {
        return INSP_CYC;
    }

    public void setINSP_CYC(String INSP_CYC) {
        this.INSP_CYC = INSP_CYC;
    }

    public String getSTAT() {
        return STAT;
    }

    public void setSTAT(String STAT) {
        this.STAT = STAT;
    }

    public String getAPPR_NAME() {
        return APPR_NAME;
    }

    public void setAPPR_NAME(String APPR_NAME) {
        this.APPR_NAME = APPR_NAME;
    }

    public String getFST_APPR_NAME() {
        return FST_APPR_NAME;
    }

    public void setFST_APPR_NAME(String FST_APPR_NAME) {
        this.FST_APPR_NAME = FST_APPR_NAME;
    }

    public String getSECD_APPR_NAME() {
        return SECD_APPR_NAME;
    }

    public void setSECD_APPR_NAME(String SECD_APPR_NAME) {
        this.SECD_APPR_NAME = SECD_APPR_NAME;
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
}
