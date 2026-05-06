package com.adtec.pay.dto.offline.detail;

public class DtlQryResList {
    /**
     * 业务编号
     */
    private String BUSI_NO;
    /**
     * 业务名称
     */
    private String BUSI_NAME;
    /**
     * 项目名称
     */
    private String PROJ_NAME;
    /**
     * 子序号
     */
    private Long SUB_SER;
    /**
     * 创建日期
     */
    private String CRT_DATE;
    /**
     * 欠费月份
     */
    private String OWE_MONTH;
    /**
     * 名称
     */
    private String NAME;
    /**
     * 手机号码
     */
    private String PHONE_NO;
    /**
     * 批次日期
     */
    private String BAT_DATE;
    /**
     * 渠道号
     */
    private String CHNL_NO;
    /**
     * 状态
     */
    private String STAT;
    /**
     * 缴费号
     */
    private String PAY_NO;
    /**
     * 地址
     */
    private String ADDR;
    /**
     * 小区名称
     */
    private String COMMUNITY;
    /**
     * 楼号
     */
    private String BUID_NO;
    /**
     * 单元编号
     */
    private String UNIT_NO;
    /**
     * 房间编号
     */
    private String ROOM_NO;
    /**
     * 项目编号
     */
    private String PROJ_NO;
    /**
     * 总面积
     */
    private Double TOT_AREA;
    /**
     * 单价
     */
    private Double UNIT_PRICE;
    /**
     * 使用数量
     */
    private Long USE_NUM;
    /**
     * 证件类型
     */
    private String CERT_TP;
    /**
     * 证件号码
     */
    private String CERT_NO;
    /**
     * 学号
     */
    private String STU_ID;
    /**
     * 班级
     */
    private String STU_CLASS;
    /**
     * 学院
     */
    private String COLLEGE;
    /**
     * 学校
     */
    private String SCHOOL;
    /**
     * 专业
     */
    private String MAJOR;
    /**
     * 优惠金额
     */
    private Double DCT_AMT;
    /**
     * 总金额
     */
    private Double TOT_AMT;
    /**
     * 项目类型
     */
    private String PROJ_TP;
    /**
     * 优惠额度
     */
    private Double DCT_QTA;
    /**
     * 实际金额
     */
    private Double PRCTL_AMT;
    /**
     * 手续费金额
     */
    private Double FEE_AMT;
    /**
     * 滞纳金金额
     */
    private Double LATE_FEE_AMT;
    /**
     * 退款金额
     */
    private Double RFND_AMT;
    /**
     * 退款日期
     */
    private String RFND_DATE;
    /**
     * 支付类型
     */
    private String PAY_TP;
    /**
     * 开始日期
     */
    private String STR_DATE;
    /**
     * 结束日期
     */
    private String END_DATE;
    /**
     * 柜员号
     */
    private String TLR_NO;
    /**
     * 机构
     */
    private String BRCH;
    /**
     * 平台日期
     */
    private String PLAT_DATE;

    /**
     * 平台流水
     */
    private String PLAT_SEQ;
    /**
     * 清算日期
     */
    private String CLR_DATE;
    /**
     * 清算标志
     */
    private String CLR_FLG;
    /**
     * 年级
     */
    private String GRADE;
    /**
     * 入学年度
     */
    private String ENROLL_YEAR;
    /**
     * 车牌号
     */
    private String CAR_NO;
    /**
     * 返回代码
     */
    private String RET_CODE;
    /**
     * 返回信息
     */
    private String RET_MSG;

    private String OPER_STAT;

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

    public String getPROJ_NAME() {
        return PROJ_NAME;
    }

    public void setPROJ_NAME(String PROJ_NAME) {
        this.PROJ_NAME = PROJ_NAME;
    }

    public Double getTOT_AREA() {
        return TOT_AREA;
    }

    public void setTOT_AREA(Double TOT_AREA) {
        this.TOT_AREA = TOT_AREA;
    }

    public Double getUNIT_PRICE() {
        return UNIT_PRICE;
    }

    public void setUNIT_PRICE(Double UNIT_PRICE) {
        this.UNIT_PRICE = UNIT_PRICE;
    }

    public Long getUSE_NUM() {
        return USE_NUM;
    }

    public void setUSE_NUM(Long USE_NUM) {
        this.USE_NUM = USE_NUM;
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

    public String getSTU_ID() {
        return STU_ID;
    }

    public void setSTU_ID(String STU_ID) {
        this.STU_ID = STU_ID;
    }

    public String getSTU_CLASS() {
        return STU_CLASS;
    }

    public void setSTU_CLASS(String STU_CLASS) {
        this.STU_CLASS = STU_CLASS;
    }

    public String getCOLLEGE() {
        return COLLEGE;
    }

    public void setCOLLEGE(String COLLEGE) {
        this.COLLEGE = COLLEGE;
    }

    public String getSCHOOL() {
        return SCHOOL;
    }

    public void setSCHOOL(String SCHOOL) {
        this.SCHOOL = SCHOOL;
    }

    public String getMAJOR() {
        return MAJOR;
    }

    public void setMAJOR(String MAJOR) {
        this.MAJOR = MAJOR;
    }

    public Double getDCT_AMT() {
        return DCT_AMT;
    }

    public void setDCT_AMT(Double DCT_AMT) {
        this.DCT_AMT = DCT_AMT;
    }

    public Double getDCT_QTA() {
        return DCT_QTA;
    }

    public void setDCT_QTA(Double DCT_QTA) {
        this.DCT_QTA = DCT_QTA;
    }

    public Double getPRCTL_AMT() {
        return PRCTL_AMT;
    }

    public void setPRCTL_AMT(Double PRCTL_AMT) {
        this.PRCTL_AMT = PRCTL_AMT;
    }

    public Double getFEE_AMT() {
        return FEE_AMT;
    }

    public void setFEE_AMT(Double FEE_AMT) {
        this.FEE_AMT = FEE_AMT;
    }

    public Double getLATE_FEE_AMT() {
        return LATE_FEE_AMT;
    }

    public void setLATE_FEE_AMT(Double LATE_FEE_AMT) {
        this.LATE_FEE_AMT = LATE_FEE_AMT;
    }

    public Double getRFND_AMT() {
        return RFND_AMT;
    }

    public void setRFND_AMT(Double RFND_AMT) {
        this.RFND_AMT = RFND_AMT;
    }

    public String getRFND_DATE() {
        return RFND_DATE;
    }

    public void setRFND_DATE(String RFND_DATE) {
        this.RFND_DATE = RFND_DATE;
    }

    public String getPAY_TP() {
        return PAY_TP;
    }

    public void setPAY_TP(String PAY_TP) {
        this.PAY_TP = PAY_TP;
    }

    public String getSTR_DATE() {
        return STR_DATE;
    }

    public void setSTR_DATE(String STR_DATE) {
        this.STR_DATE = STR_DATE;
    }

    public String getEND_DATE() {
        return END_DATE;
    }

    public void setEND_DATE(String END_DATE) {
        this.END_DATE = END_DATE;
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

    public String getCLR_DATE() {
        return CLR_DATE;
    }

    public void setCLR_DATE(String CLR_DATE) {
        this.CLR_DATE = CLR_DATE;
    }

    public String getCLR_FLG() {
        return CLR_FLG;
    }

    public void setCLR_FLG(String CLR_FLG) {
        this.CLR_FLG = CLR_FLG;
    }

    public String getGRADE() {
        return GRADE;
    }

    public void setGRADE(String GRADE) {
        this.GRADE = GRADE;
    }

    public String getENROLL_YEAR() {
        return ENROLL_YEAR;
    }

    public void setENROLL_YEAR(String ENROLL_YEAR) {
        this.ENROLL_YEAR = ENROLL_YEAR;
    }

    public String getCAR_NO() {
        return CAR_NO;
    }

    public void setCAR_NO(String CAR_NO) {
        this.CAR_NO = CAR_NO;
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

    public Long getSUB_SER() {
        return SUB_SER;
    }

    public void setSUB_SER(Long SUB_SER) {
        this.SUB_SER = SUB_SER;
    }

    public String getCRT_DATE() {
        return CRT_DATE;
    }

    public void setCRT_DATE(String CRT_DATE) {
        this.CRT_DATE = CRT_DATE;
    }

    public String getOWE_MONTH() {
        return OWE_MONTH;
    }

    public void setOWE_MONTH(String OWE_MONTH) {
        this.OWE_MONTH = OWE_MONTH;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getPHONE_NO() {
        return PHONE_NO;
    }

    public void setPHONE_NO(String PHONE_NO) {
        this.PHONE_NO = PHONE_NO;
    }

    public String getBAT_DATE() {
        return BAT_DATE;
    }

    public void setBAT_DATE(String BAT_DATE) {
        this.BAT_DATE = BAT_DATE;
    }

    public String getCHNL_NO() {
        return CHNL_NO;
    }

    public void setCHNL_NO(String CHNL_NO) {
        this.CHNL_NO = CHNL_NO;
    }

    public String getSTAT() {
        return STAT;
    }

    public void setSTAT(String STAT) {
        this.STAT = STAT;
    }

    public String getPAY_NO() {
        return PAY_NO;
    }

    public void setPAY_NO(String PAY_NO) {
        this.PAY_NO = PAY_NO;
    }

    public String getADDR() {
        return ADDR;
    }

    public void setADDR(String ADDR) {
        this.ADDR = ADDR;
    }

    public String getCOMMUNITY() {
        return COMMUNITY;
    }

    public void setCOMMUNITY(String COMMUNITY) {
        this.COMMUNITY = COMMUNITY;
    }

    public String getBUID_NO() {
        return BUID_NO;
    }

    public void setBUID_NO(String BUID_NO) {
        this.BUID_NO = BUID_NO;
    }

    public String getUNIT_NO() {
        return UNIT_NO;
    }

    public void setUNIT_NO(String UNIT_NO) {
        this.UNIT_NO = UNIT_NO;
    }

    public String getROOM_NO() {
        return ROOM_NO;
    }

    public void setROOM_NO(String ROOM_NO) {
        this.ROOM_NO = ROOM_NO;
    }

    public String getPROJ_NO() {
        return PROJ_NO;
    }

    public void setPROJ_NO(String PROJ_NO) {
        this.PROJ_NO = PROJ_NO;
    }

    public Double getTOT_AMT() {
        return TOT_AMT;
    }

    public void setTOT_AMT(Double TOT_AMT) {
        this.TOT_AMT = TOT_AMT;
    }

    public String getPROJ_TP() {
        return PROJ_TP;
    }

    public void setPROJ_TP(String PROJ_TP) {
        this.PROJ_TP = PROJ_TP;
    }

    public String getOPER_STAT() {
        return OPER_STAT;
    }

    public void setOPER_STAT(String OPER_STAT) {
        this.OPER_STAT = OPER_STAT;
    }
}
