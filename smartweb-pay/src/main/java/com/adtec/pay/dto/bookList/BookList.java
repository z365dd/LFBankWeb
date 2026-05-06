package com.adtec.pay.dto.bookList;

import java.io.Serializable;
import java.util.List;

public class BookList implements Serializable {
    /**
     * 平台日期
     */
    private String PLAT_DATE;
    /**
     * 平台流水
     */
    private String PLAT_SEQ;
    /**
     * 请求日期
     */
    private String REQ_DATE;
    /**
     * 请求流水
     */
    private String REQ_SEQ;
    /**
     * 请求时间
     */
    private String REQ_TIME;
    /**
     * 响应日期
     */
    private String RESP_DATE;
    /**
     * 响应流水
     */
    private String RESP_SEQ;
    /**
     * 交易类型
     */
    private String TRAN_TP;
    /**
     * 交易代码
     */
    private String TRAN_CODE;
    /**
     * 交易状态
     */
    private String TRAN_STAT;
    /**
     * 优惠标志
     */
    private String DCT_FLG;
    /**
     * 优惠日期
     */
    private String DCT_DATE;
    /**
     * 优惠流水
     */
    private String DCT_SEQ;
    /**
     * 优惠状态
     */
    private String DCT_STAT;
    /**
     * 核心日期
     */
    private String HOST_DATE;
    /**
     * 核心流水
     */
    private String HOST_SEQ;
    /**
     * 核心状态
     */
    private String HOST_STAT;
    /**
     * 第三方日期
     */
    private String OTH_DATE;
    /**
     * 第三方流水
     */
    private String OTH_SEQ;
    /**
     * 第三方状态
     */
    private String OTH_STAT;
    /**
     * 渠道号
     */
    private String CHNL_NO;
    /**
     * 法人号
     */
    private String LEGA_NO;
    /**
     * 业务编号
     */
    private String BUSI_NO;
    /**
     * 业务名称
     */
    private String BUSI_NAME;
    /**
     * 单位编号
     */
    private String ENTR_NO;
    /**
     * 单位名称
     */
    private String ENTR_NAME;
    /**
     * 终端号
     */
    private String TERM_NO;
    /**
     * 柜员号
     */
    private String TLR_NO;
    /**
     * 机构
     */
    private String BRCH;
    /**
     * 开客户机构
     */
    private String OPEN_CUST_BRCH;
    /**
     * 授权柜员号
     */
    private String AUTH_TLR_NO;
    /**
     * 授权机构
     */
    private String AUTH_BRCH;
    /**
     * 复核柜员号
     */
    private String INSPT_TLR_NO;
    /**
     * 复核机构
     */
    private String INSPT_BRCH;
    /**
     * 来往标志
     */
    private String RECV_SND_FLG;
    /**
     * 返回代码
     */
    private String RET_CODE;
    /**
     * 返回信息
     */
    private String RET_MSG;
    /**
     * 金额
     */
    private Double AMT;
    /**
     * 滞纳金金额
     */
    private Double LATE_FEE_AMT;
    /**
     * 手续费金额
     */
    private Double FEE_AMT;
    /**
     * 优惠金额
     */
    private Double DCT_AMT;
    /**
     * 总金额
     */
    private Double TOT_AMT;
    /**
     * 实际金额
     */
    private Double PRCTL_AMT;
    /**
     * 退汇状态
     */
    private String RFND_STAT;
    /**
     * 退款金额
     */
    private Double RFND_AMT;
    /**
     * 支付类型
     */
    private String PAY_TP;
    /**
     * 币种
     */
    private String CURR;
    /**
     * 钞汇标志
     */
    private String CASH_REM_FLG;
    /**
     * 付款账号
     */
    private String PAY_ACCT;
    /**
     * 付款账户名称
     */
    private String PAY_ACCT_NAME;
    /**
     * 收款账号
     */
    private String PAYEE_ACCT;
    /**
     * 收款账户名称
     */
    private String PAYEE_ACCT_NAME;
    /**
     * 支取方式
     */
    private String WDR_METH;
    /**
     * 凭证类型
     */
    private String VCH_TP;
    /**
     * 凭证号
     */
    private String VCH_NO;
    /**
     * 凭证日期
     */
    private String VCH_DATE;
    /**
     * 证件类型
     */
    private String CERT_TP;
    /**
     * 证件号码
     */
    private String CERT_NO;
    /**
     * 摘要代码
     */
    private String SUM_CODE;
    /**
     * 摘要描述
     */
    private String SUM_DESC;
    /**
     * 打印状态
     */
    private String PRT_STAT;
    /**
     * 缴费号
     */
    private String PAY_NO;
    /**
     * 名称
     */
    private String NAME;
    /**
     * 欠费月份
     */
    private String OWE_MONTH;
    /**
     * 地址
     */
    private String ADDR;
    /**
     * 联系手机号
     */
    private String CTCT_PHONE_NO;
    /**
     * 支票号
     */
    private String BILL_NO;
    /**
     * 缴费日期
     */
    private String PAY_DATE;
    /**
     * 报文CLOB
     */
    private String MSG_CLOB;
    /**
     * 自动标志
     */
    private String AUTO_FLG;

    private String STU_CLASS;
    /**
     * 优惠列表
     */
    private List<DctList> DCT_LIST;

    private List<SubList> SUB_LIST;

    public List<DctList> getDCT_LIST() {
        return DCT_LIST;
    }

    public void setDCT_LIST(List<DctList> dCT_LIST) {
        DCT_LIST = dCT_LIST;
    }

    public String getPLAT_DATE() {
        return PLAT_DATE;
    }

    public void setPLAT_DATE(String PLAT_DATE) {
        this.PLAT_DATE = PLAT_DATE;
    }

    public String getPLAT_SEQ() {
        return PLAT_SEQ;
    }

    public void setPLAT_SEQ(String PLAT_SEQ) {
        this.PLAT_SEQ = PLAT_SEQ;
    }

    public String getREQ_DATE() {
        return REQ_DATE;
    }

    public void setREQ_DATE(String REQ_DATE) {
        this.REQ_DATE = REQ_DATE;
    }

    public String getREQ_SEQ() {
        return REQ_SEQ;
    }

    public void setREQ_SEQ(String REQ_SEQ) {
        this.REQ_SEQ = REQ_SEQ;
    }

    public String getREQ_TIME() {
        return REQ_TIME;
    }

    public void setREQ_TIME(String REQ_TIME) {
        this.REQ_TIME = REQ_TIME;
    }

    public String getRESP_DATE() {
        return RESP_DATE;
    }

    public void setRESP_DATE(String RESP_DATE) {
        this.RESP_DATE = RESP_DATE;
    }

    public String getRESP_SEQ() {
        return RESP_SEQ;
    }

    public void setRESP_SEQ(String RESP_SEQ) {
        this.RESP_SEQ = RESP_SEQ;
    }

    public String getTRAN_TP() {
        return TRAN_TP;
    }

    public void setTRAN_TP(String TRAN_TP) {
        this.TRAN_TP = TRAN_TP;
    }

    public String getTRAN_CODE() {
        return TRAN_CODE;
    }

    public void setTRAN_CODE(String TRAN_CODE) {
        this.TRAN_CODE = TRAN_CODE;
    }

    public String getTRAN_STAT() {
        return TRAN_STAT;
    }

    public void setTRAN_STAT(String TRAN_STAT) {
        this.TRAN_STAT = TRAN_STAT;
    }

    public String getDCT_FLG() {
        return DCT_FLG;
    }

    public void setDCT_FLG(String DCT_FLG) {
        this.DCT_FLG = DCT_FLG;
    }

    public String getDCT_DATE() {
        return DCT_DATE;
    }

    public void setDCT_DATE(String DCT_DATE) {
        this.DCT_DATE = DCT_DATE;
    }

    public String getDCT_SEQ() {
        return DCT_SEQ;
    }

    public void setDCT_SEQ(String DCT_SEQ) {
        this.DCT_SEQ = DCT_SEQ;
    }

    public String getDCT_STAT() {
        return DCT_STAT;
    }

    public void setDCT_STAT(String DCT_STAT) {
        this.DCT_STAT = DCT_STAT;
    }

    public String getHOST_DATE() {
        return HOST_DATE;
    }

    public void setHOST_DATE(String HOST_DATE) {
        this.HOST_DATE = HOST_DATE;
    }

    public String getHOST_SEQ() {
        return HOST_SEQ;
    }

    public void setHOST_SEQ(String HOST_SEQ) {
        this.HOST_SEQ = HOST_SEQ;
    }

    public String getHOST_STAT() {
        return HOST_STAT;
    }

    public void setHOST_STAT(String HOST_STAT) {
        this.HOST_STAT = HOST_STAT;
    }

    public String getOTH_DATE() {
        return OTH_DATE;
    }

    public void setOTH_DATE(String OTH_DATE) {
        this.OTH_DATE = OTH_DATE;
    }

    public String getOTH_SEQ() {
        return OTH_SEQ;
    }

    public void setOTH_SEQ(String OTH_SEQ) {
        this.OTH_SEQ = OTH_SEQ;
    }

    public String getOTH_STAT() {
        return OTH_STAT;
    }

    public void setOTH_STAT(String OTH_STAT) {
        this.OTH_STAT = OTH_STAT;
    }

    public String getCHNL_NO() {
        return CHNL_NO;
    }

    public void setCHNL_NO(String CHNL_NO) {
        this.CHNL_NO = CHNL_NO;
    }

    public String getLEGA_NO() {
        return LEGA_NO;
    }

    public void setLEGA_NO(String LEGA_NO) {
        this.LEGA_NO = LEGA_NO;
    }

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

    public String getENTR_NO() {
        return ENTR_NO;
    }

    public void setENTR_NO(String ENTR_NO) {
        this.ENTR_NO = ENTR_NO;
    }

    public String getENTR_NAME() {
        return ENTR_NAME;
    }

    public void setENTR_NAME(String ENTR_NAME) {
        this.ENTR_NAME = ENTR_NAME;
    }

    public String getTERM_NO() {
        return TERM_NO;
    }

    public void setTERM_NO(String TERM_NO) {
        this.TERM_NO = TERM_NO;
    }

    public String getTLR_NO() {
        return TLR_NO;
    }

    public void setTLR_NO(String TLR_NO) {
        this.TLR_NO = TLR_NO;
    }

    public String getBRCH() {
        return BRCH;
    }

    public void setBRCH(String BRCH) {
        this.BRCH = BRCH;
    }

    public String getAUTH_TLR_NO() {
        return AUTH_TLR_NO;
    }

    public void setAUTH_TLR_NO(String AUTH_TLR_NO) {
        this.AUTH_TLR_NO = AUTH_TLR_NO;
    }

    public String getAUTH_BRCH() {
        return AUTH_BRCH;
    }

    public void setAUTH_BRCH(String AUTH_BRCH) {
        this.AUTH_BRCH = AUTH_BRCH;
    }

    public String getINSPT_TLR_NO() {
        return INSPT_TLR_NO;
    }

    public void setINSPT_TLR_NO(String INSPT_TLR_NO) {
        this.INSPT_TLR_NO = INSPT_TLR_NO;
    }

    public String getINSPT_BRCH() {
        return INSPT_BRCH;
    }

    public void setINSPT_BRCH(String INSPT_BRCH) {
        this.INSPT_BRCH = INSPT_BRCH;
    }

    public String getRECV_SND_FLG() {
        return RECV_SND_FLG;
    }

    public void setRECV_SND_FLG(String RECV_SND_FLG) {
        this.RECV_SND_FLG = RECV_SND_FLG;
    }

    public String getRET_CODE() {
        return RET_CODE;
    }

    public void setRET_CODE(String RET_CODE) {
        this.RET_CODE = RET_CODE;
    }

    public String getRET_MSG() {
        return RET_MSG;
    }

    public void setRET_MSG(String RET_MSG) {
        this.RET_MSG = RET_MSG;
    }

    public Double getAMT() {
        return AMT;
    }

    public void setAMT(Double AMT) {
        this.AMT = AMT;
    }

    public Double getLATE_FEE_AMT() {
        return LATE_FEE_AMT;
    }

    public void setLATE_FEE_AMT(Double LATE_FEE_AMT) {
        this.LATE_FEE_AMT = LATE_FEE_AMT;
    }

    public Double getFEE_AMT() {
        return FEE_AMT;
    }

    public void setFEE_AMT(Double FEE_AMT) {
        this.FEE_AMT = FEE_AMT;
    }

    public Double getDCT_AMT() {
        return DCT_AMT;
    }

    public void setDCT_AMT(Double DCT_AMT) {
        this.DCT_AMT = DCT_AMT;
    }

    public Double getTOT_AMT() {
        return TOT_AMT;
    }

    public void setTOT_AMT(Double TOT_AMT) {
        this.TOT_AMT = TOT_AMT;
    }

    public Double getPRCTL_AMT() {
        return PRCTL_AMT;
    }

    public void setPRCTL_AMT(Double PRCTL_AMT) {
        this.PRCTL_AMT = PRCTL_AMT;
    }

    public String getRFND_STAT() {
        return RFND_STAT;
    }

    public void setRFND_STAT(String RFND_STAT) {
        this.RFND_STAT = RFND_STAT;
    }

    public Double getRFND_AMT() {
        return RFND_AMT;
    }

    public void setRFND_AMT(Double RFND_AMT) {
        this.RFND_AMT = RFND_AMT;
    }

    public String getPAY_TP() {
        return PAY_TP;
    }

    public void setPAY_TP(String PAY_TP) {
        this.PAY_TP = PAY_TP;
    }

    public String getCURR() {
        return CURR;
    }

    public void setCURR(String CURR) {
        this.CURR = CURR;
    }

    public String getCASH_REM_FLG() {
        return CASH_REM_FLG;
    }

    public void setCASH_REM_FLG(String CASH_REM_FLG) {
        this.CASH_REM_FLG = CASH_REM_FLG;
    }

    public String getPAY_ACCT() {
        return PAY_ACCT;
    }

    public void setPAY_ACCT(String PAY_ACCT) {
        this.PAY_ACCT = PAY_ACCT;
    }

    public String getPAY_ACCT_NAME() {
        return PAY_ACCT_NAME;
    }

    public void setPAY_ACCT_NAME(String PAY_ACCT_NAME) {
        this.PAY_ACCT_NAME = PAY_ACCT_NAME;
    }

    public String getPAYEE_ACCT() {
        return PAYEE_ACCT;
    }

    public void setPAYEE_ACCT(String PAYEE_ACCT) {
        this.PAYEE_ACCT = PAYEE_ACCT;
    }

    public String getPAYEE_ACCT_NAME() {
        return PAYEE_ACCT_NAME;
    }

    public void setPAYEE_ACCT_NAME(String PAYEE_ACCT_NAME) {
        this.PAYEE_ACCT_NAME = PAYEE_ACCT_NAME;
    }

    public String getWDR_METH() {
        return WDR_METH;
    }

    public void setWDR_METH(String WDR_METH) {
        this.WDR_METH = WDR_METH;
    }

    public String getVCH_TP() {
        return VCH_TP;
    }

    public void setVCH_TP(String VCH_TP) {
        this.VCH_TP = VCH_TP;
    }

    public String getVCH_NO() {
        return VCH_NO;
    }

    public void setVCH_NO(String VCH_NO) {
        this.VCH_NO = VCH_NO;
    }

    public String getVCH_DATE() {
        return VCH_DATE;
    }

    public void setVCH_DATE(String VCH_DATE) {
        this.VCH_DATE = VCH_DATE;
    }

    public String getCERT_TP() {
        return CERT_TP;
    }

    public void setCERT_TP(String CERT_TP) {
        this.CERT_TP = CERT_TP;
    }

    public String getCERT_NO() {
        return CERT_NO;
    }

    public void setCERT_NO(String CERT_NO) {
        this.CERT_NO = CERT_NO;
    }

    public String getSUM_CODE() {
        return SUM_CODE;
    }

    public void setSUM_CODE(String SUM_CODE) {
        this.SUM_CODE = SUM_CODE;
    }

    public String getSUM_DESC() {
        return SUM_DESC;
    }

    public void setSUM_DESC(String SUM_DESC) {
        this.SUM_DESC = SUM_DESC;
    }

    public String getPRT_STAT() {
        return PRT_STAT;
    }

    public void setPRT_STAT(String PRT_STAT) {
        this.PRT_STAT = PRT_STAT;
    }

    public String getPAY_NO() {
        return PAY_NO;
    }

    public void setPAY_NO(String PAY_NO) {
        this.PAY_NO = PAY_NO;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getOWE_MONTH() {
        return OWE_MONTH;
    }

    public void setOWE_MONTH(String OWE_MONTH) {
        this.OWE_MONTH = OWE_MONTH;
    }

    public String getADDR() {
        return ADDR;
    }

    public void setADDR(String ADDR) {
        this.ADDR = ADDR;
    }

    public String getCTCT_PHONE_NO() {
        return CTCT_PHONE_NO;
    }

    public void setCTCT_PHONE_NO(String CTCT_PHONE_NO) {
        this.CTCT_PHONE_NO = CTCT_PHONE_NO;
    }

    public String getBILL_NO() {
        return BILL_NO;
    }

    public void setBILL_NO(String BILL_NO) {
        this.BILL_NO = BILL_NO;
    }

    public String getPAY_DATE() {
        return PAY_DATE;
    }

    public void setPAY_DATE(String PAY_DATE) {
        this.PAY_DATE = PAY_DATE;
    }

    public String getMSG_CLOB() {
        return MSG_CLOB;
    }

    public void setMSG_CLOB(String MSG_CLOB) {
        this.MSG_CLOB = MSG_CLOB;
    }

    public String getAUTO_FLG() {
        return AUTO_FLG;
    }

    public void setAUTO_FLG(String AUTO_FLG) {
        this.AUTO_FLG = AUTO_FLG;
    }

    public String getSTU_CLASS() {
        return STU_CLASS;
    }

    public void setSTU_CLASS(String STU_CLASS) {
        this.STU_CLASS = STU_CLASS;
    }

    public String getOPEN_CUST_BRCH() {
        return OPEN_CUST_BRCH;
    }

    public void setOPEN_CUST_BRCH(String OPEN_CUST_BRCH) {
        this.OPEN_CUST_BRCH = OPEN_CUST_BRCH;
    }

    public List<SubList> getSUB_LIST() {
        return SUB_LIST;
    }

    public void setSUB_LIST(List<SubList> SUB_LIST) {
        this.SUB_LIST = SUB_LIST;
    }
}
