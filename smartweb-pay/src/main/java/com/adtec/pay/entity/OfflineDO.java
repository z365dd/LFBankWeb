package com.adtec.pay.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 脱机缴费明细
 */
@Getter
@Setter
@EqualsAndHashCode
public class OfflineDO {

    /**
     * 业务编号
     */

    String BUSI_NO;
    /**
     * 业务名称
     */

    String BUSI_NAME;
    /**
     * 业务类型
     */

    String BUSI_TP;
    /**
     * 创建日期
     */

    String CRT_DATE;
    /**
     * 收费编号
     */

    String PROJ_NO;
    /**
     * 收费名称
     */

    String PROJ_NAME;
    /**
     * 项目类型
     */

    String PROJ_TP;
    /**
     * 子序号
     */

    Long SUB_SER;
    /**
     * 账单周期
     */

    String OWE_MONTH;
    /**
     * 状态
     */

    String STAT;
    /**
     * 平台日期
     */

    String PLAT_DATE;
    /**
     * 平台流水
     */

    String PLAT_SEQ;
    /**
     * 渠道号
     */

    String CHNL_NO;
    /**
     * 缴费号
     */

    String PAY_NO;
    /**
     * 名称
     */

    String NAME;
    /**
     * 手机号码
     */

    String PHONE_NO;
    /**
     * 地址
     */

    String ADDR;
    /**
     * 小区名称
     */

    String COMMUNITY;
    /**
     * 楼号
     */

    String BUID_NO;
    /**
     * 单元编号
     */

    String UNIT_NO;
    /**
     * 房间编号
     */

    String ROOM_NO;
    /**
     * 总面积
     */

    Double TOT_AREA;
    /**
     * 单价
     */

    Double UNIT_PRICE;
    /**
     * 使用数量
     */

    Long USE_NUM;
    /**
     * 证件类型
     */

    String CERT_TP;
    /**
     * 证件号码
     */

    String CERT_NO;
    /**
     * 学号
     */

    String STU_ID;
    /**
     * 班级
     */

    String STU_CLASS;
    /**
     * 学院
     */

    String COLLEGE;
    /**
     * 学校
     */

    String SCHOOL;
    /**
     * 专业
     */

    String MAJOR;
    /**
     * 年级
     */

    String GRADE;
    /**
     * 入学年度
     */

    String ENROLL_YEAR;
    /**
     * 总金额
     */

    Double TOT_AMT;
    /**
     * 优惠金额
     */

    Double DCT_AMT;
    /**
     * 优惠额度
     */

    Double DCT_QTA;
    /**
     * 实际金额
     */

    Double PRCTL_AMT;
    /**
     * 手续费金额
     */

    Double FEE_AMT;
    /**
     * 滞纳金金额
     */

    Double LATE_FEE_AMT;
    /**
     * 车牌号
     */

    String CAR_NO;
    /**
     * 退款金额
     */

    Double RFND_AMT;
    /**
     * 退款日期
     */

    String RFND_DATE;
    /**
     * 退款平台流水
     */

    String RFND_PLAT_SEQ;
    /**
     * 柜员号
     */

    String TLR_NO;
    /**
     * 机构
     */

    String BRCH;
    /**
     * 清算日期
     */

    String CLR_DATE;
    /**
     * 清算标志
     */

    String CLR_FLG;
    /**
     * 锁定状态
     */

    String LOCK_STAT;
    /**
     * 处理数量
     */

    Long PROC_NUM;
    /**
     * 最后更新时间
     */

    String LAST_UPT_TIME;
    /**
     * 返回代码
     */

    String RET_CODE;
    /**
     * 返回信息
     */

    String RET_MSG;
    /**
     * 短备注
     */

    String SHORT_RMRK;
    /**
     * 中备注
     */

    String MID_RMRK;
    /**
     * 长备注
     */

    String LONG_RMRK;
    /**
     * DAC
     */

    String DAC;


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

    public String getBUSI_TP() {
        return BUSI_TP;
    }

    public void setBUSI_TP(String BUSI_TP) {
        this.BUSI_TP = BUSI_TP;
    }

    public String getCRT_DATE() {
        return CRT_DATE;
    }

    public void setCRT_DATE(String CRT_DATE) {
        this.CRT_DATE = CRT_DATE;
    }

    public String getPROJ_NO() {
        return PROJ_NO;
    }

    public void setPROJ_NO(String PROJ_NO) {
        this.PROJ_NO = PROJ_NO;
    }

    public String getPROJ_NAME() {
        return PROJ_NAME;
    }

    public void setPROJ_NAME(String PROJ_NAME) {
        this.PROJ_NAME = PROJ_NAME;
    }

    public String getPROJ_TP() {
        return PROJ_TP;
    }

    public void setPROJ_TP(String PROJ_TP) {
        this.PROJ_TP = PROJ_TP;
    }

    public Long getSUB_SER() {
        return SUB_SER;
    }

    public void setSUB_SER(Long SUB_SER) {
        this.SUB_SER = SUB_SER;
    }

    public String getOWE_MONTH() {
        return OWE_MONTH;
    }

    public void setOWE_MONTH(String OWE_MONTH) {
        this.OWE_MONTH = OWE_MONTH;
    }

    public String getSTAT() {
        return STAT;
    }

    public void setSTAT(String STAT) {
        this.STAT = STAT;
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

    public String getCHNL_NO() {
        return CHNL_NO;
    }

    public void setCHNL_NO(String CHNL_NO) {
        this.CHNL_NO = CHNL_NO;
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

    public String getPHONE_NO() {
        return PHONE_NO;
    }

    public void setPHONE_NO(String PHONE_NO) {
        this.PHONE_NO = PHONE_NO;
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

    public Double getTOT_AMT() {
        return TOT_AMT;
    }

    public void setTOT_AMT(Double TOT_AMT) {
        this.TOT_AMT = TOT_AMT;
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

    public String getCAR_NO() {
        return CAR_NO;
    }

    public void setCAR_NO(String CAR_NO) {
        this.CAR_NO = CAR_NO;
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

    public String getRFND_PLAT_SEQ() {
        return RFND_PLAT_SEQ;
    }

    public void setRFND_PLAT_SEQ(String RFND_PLAT_SEQ) {
        this.RFND_PLAT_SEQ = RFND_PLAT_SEQ;
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

    public String getLOCK_STAT() {
        return LOCK_STAT;
    }

    public void setLOCK_STAT(String LOCK_STAT) {
        this.LOCK_STAT = LOCK_STAT;
    }

    public Long getPROC_NUM() {
        return PROC_NUM;
    }

    public void setPROC_NUM(Long PROC_NUM) {
        this.PROC_NUM = PROC_NUM;
    }

    public String getLAST_UPT_TIME() {
        return LAST_UPT_TIME;
    }

    public void setLAST_UPT_TIME(String LAST_UPT_TIME) {
        this.LAST_UPT_TIME = LAST_UPT_TIME;
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
