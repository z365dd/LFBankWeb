package com.adtec.pay.entity.buscard;

import com.adtec.pay.dto.signbat.SignStatConverter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 充值报表明细列表
 */
@Getter
@Setter
@EqualsAndHashCode
public class BuscardAcctStatementDtl {

    @ColumnWidth(15)
    @ExcelProperty("交易日期")
    private String TRAN_DATE;

    @ColumnWidth(15)
    @ExcelProperty("机构号")
    private String BRCH;

    @ColumnWidth(15)
    @ExcelProperty("设备号")
    private String TLR_NO;

    @ColumnWidth(15)
    @ExcelProperty("应用序列号")
    private String APP_ID;


    @ColumnWidth(15)
    @ExcelProperty("银行卡号")
    private String ACCT;

    @ExcelProperty("金额")
    private Double TRAN_AMT;

    @ColumnWidth(15)
    @ExcelProperty("交易类型")
    private String TRAN_TP;

    @ColumnWidth(15)
    @ExcelProperty("三方流水号")
    private String OTH_SEQ;

    @ColumnWidth(15)
    @ExcelProperty("交易状态")
    private String TRAN_STAT;

    @ColumnWidth(15)
    @ExcelProperty("确认状态")
    private String CONFM_STAT;
}
