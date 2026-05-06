package com.adtec.pay.dto.proj.chk;

public class ProjInspModReq {
    /**
     * 操作状态
     * 1-新增
     * 2-修改
     * 3-删除
     */
    private String OPER_TP;
    /**
     * 项目类型
     * "01-代收
     * 02-代付
     * 03-缴费"
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
     * 巡检频次
     */
    private String INSP_CYC;
    /**
     * 专户账号
     */
    private String SPCL_ACCT;
    /**
     * 专户名称
     */
    private String SPCL_ACCT_NAME;
    /**
     * 代理业务号
     */
    private String AGT_BUSI_NO;
    /**
     * 巡检日期
     */
    private String INSP_DATE;
    /**
     * 最晚巡检日期
     */
    private String LST_INSP_DATE;
    /**
     * 巡检文件
     */
    private String INSP_FILE_NAME;
    /**
     * 巡检人员
     */
    private String INSP_USER_NAME;
    /**
     * 巡检状态
     * 01-已巡检
     * 02-未巡检
     */
    private String INSP_STAT;
    /**
     * 签约状态
     * 00-初始状态
     * 10-已签约
     * 20-已解约
     */
    private String SIGN_STAT;


    public String getOPER_TP() {
        return OPER_TP;
    }

    public void setOPER_TP(String OPER_TP) {
        this.OPER_TP = OPER_TP;
    }

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

    public String getINSP_CYC() {
        return INSP_CYC;
    }

    public void setINSP_CYC(String INSP_CYC) {
        this.INSP_CYC = INSP_CYC;
    }

    public String getSPCL_ACCT() {
        return SPCL_ACCT;
    }

    public void setSPCL_ACCT(String SPCL_ACCT) {
        this.SPCL_ACCT = SPCL_ACCT;
    }

    public String getSPCL_ACCT_NAME() {
        return SPCL_ACCT_NAME;
    }

    public void setSPCL_ACCT_NAME(String SPCL_ACCT_NAME) {
        this.SPCL_ACCT_NAME = SPCL_ACCT_NAME;
    }

    public String getAGT_BUSI_NO() {
        return AGT_BUSI_NO;
    }

    public void setAGT_BUSI_NO(String AGT_BUSI_NO) {
        this.AGT_BUSI_NO = AGT_BUSI_NO;
    }

    public String getINSP_DATE() {
        return INSP_DATE;
    }

    public void setINSP_DATE(String INSP_DATE) {
        this.INSP_DATE = INSP_DATE;
    }

    public String getLST_INSP_DATE() {
        return LST_INSP_DATE;
    }

    public void setLST_INSP_DATE(String LST_INSP_DATE) {
        this.LST_INSP_DATE = LST_INSP_DATE;
    }

    public String getINSP_FILE_NAME() {
        return INSP_FILE_NAME;
    }

    public void setINSP_FILE_NAME(String INSP_FILE_NAME) {
        this.INSP_FILE_NAME = INSP_FILE_NAME;
    }

    public String getINSP_USER_NAME() {
        return INSP_USER_NAME;
    }

    public void setINSP_USER_NAME(String INSP_USER_NAME) {
        this.INSP_USER_NAME = INSP_USER_NAME;
    }

    public String getINSP_STAT() {
        return INSP_STAT;
    }

    public void setINSP_STAT(String INSP_STAT) {
        this.INSP_STAT = INSP_STAT;
    }

    public String getSIGN_STAT() {
        return SIGN_STAT;
    }

    public void setSIGN_STAT(String SIGN_STAT) {
        this.SIGN_STAT = SIGN_STAT;
    }
}
