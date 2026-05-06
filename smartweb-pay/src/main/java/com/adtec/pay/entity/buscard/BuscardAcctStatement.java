package com.adtec.pay.entity.buscard;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 公交卡充值报表实体类
 */
@Getter
@Setter
@EqualsAndHashCode
public class BuscardAcctStatement {
    //激活交易
    public static final String TRAN_TP_ACTIVE = "01";
    //实体卡充值交易
    public static final String TRAN_TP_CHARGE = "02";
    //退卡交易
    public static final String TRAN_TP_RTN = "03";
    /**
     * 单位名称
     */
    private String BUSI_NAME;
    /**
     * 交易日期
     */
    private String TRAN_DATE;
    /**
     * 激活卡数
     */
    private int ACTIVE_NUM;
    /**
     * 激活金额
     */
    private Double ACTIVE_AMT;
    /**
     * 电子码充值次数
     */
    private int ELE_RECHARGE_NUM;
    /**
     * 电子码充值金额
     */
    private Double ELE_RECHARGE_AMT;
    /**
     * 实体卡充值次数
     */
    private int RECHARGE_NUM;
    /**
     * 实体卡充值金额
     */
    private Double RECHARGE_AMT;
    /**
     * 退卡卡数
     */
    private int RTN_NUM;
    /**
     * 退卡金额
     */
    private Double RTN_AMT;
    /**
     * 合计金额
     */
    private Double TOT_AMT;
    //清算金额
    private Double CLR_AMT;
    //备用金娥
    private Double PC_AMT;

    private String DATE;

    private String NAME;

    private String CRT_DATE;

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getCRT_DATE() {
        return CRT_DATE;
    }

    public void setCRT_DATE(String CRT_DATE) {
        this.CRT_DATE = CRT_DATE;
    }
}
