package com.adtec.pay.dto.bookList;

import com.adtec.pay.dto.offline.temple.DataConverter5;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 和联网缴费的导出缴费明细excel区别
 */
@Getter
@Setter
@EqualsAndHashCode
public class OfflineBook {
    /**
     * 缴费日期
     */
    @ColumnWidth(15)
    @ExcelProperty({"缴费明细查询数据", "缴费日期"})
    private String PLAT_DATE;
    /**
     * 业务编号
     */
    @ColumnWidth(15)
    @ExcelProperty({"缴费明细查询数据", "业务编号"})
    private String BUSI_NO;
    /**
     * 业务名称
     */
    @ColumnWidth(15)
    @ExcelProperty({"缴费明细查询数据", "业务名称"})
    private String BUSI_NAME;
    /**
     * 渠道
     */
    @ExcelProperty({"缴费明细查询数据", "渠道"})
    private String CHNL_NO;
    /**
     * 缴费号
     */
    @ExcelProperty({"缴费明细查询数据", "缴费号"})
    private String PAY_NO;
    /**
     * 缴费账号
     */
    @ColumnWidth(15)
    @ExcelProperty({"缴费明细查询数据", "缴费账号"})
    private String PAY_ACCT;
    /**
     * 客户名称
     */
    @ColumnWidth(15)
    @ExcelProperty({"缴费明细查询数据", "客户名称"})
    private String NAME;
    /**
     * 缴费时间
     */
    @ColumnWidth(15)
    @ExcelProperty({"缴费明细查询数据", "缴费时间"})
    private String REQ_TIME;
    /**
     * 缴费方式
     */
    @ColumnWidth(15)
    @ExcelProperty({"缴费明细查询数据", "缴费方式"})
    private String PAY_TP;

    /**
     * 录入缴费金额
     */
    @ColumnWidth(15)
    @ExcelProperty({"缴费明细查询数据", "缴费金额"})
    private Double REC_AMT;
    /**
     * 录入减免金额
     */
    @ColumnWidth(15)
    @ExcelProperty({"缴费明细查询数据", "减免金额"})
    private Double LEGA_NO;
    /**
     * 缴费金额
     */
    @ColumnWidth(15)
    @ExcelProperty({"缴费明细查询数据", "应缴金额"})
    private Double TOT_AMT;
    /**
     * 实际金额
     */
    @ColumnWidth(15)
    @ExcelProperty({"缴费明细查询数据", "实缴金额"})
    private Double PRCTL_AMT;
    /**
     * 优惠金额
     */
    @ColumnWidth(15)
    @ExcelProperty({"缴费明细查询数据", "优惠金额"})
    private Double DCT_AMT;
    /**
     * 银行优惠
     */
    @ColumnWidth(15)
    @ExcelProperty({"缴费明细查询数据", "银行优惠"})
    private Double AMT;
    /**
     * 商户优惠
     */
    @ColumnWidth(15)
    @ExcelProperty({"缴费明细查询数据", "商户优惠"})
    private String SHORT_RMRK;
    /**
     * 手续费
     */
    @ExcelProperty({"缴费明细查询数据", "手续费"})
    private Double FEE_AMT;
    /**
     * 缴费状态
     */
    @ColumnWidth(15)
    @ExcelProperty({"缴费明细查询数据", "缴费状态"})
    private String TRAN_STAT;

    /**
     * 交易类型
     */
    @ColumnWidth(15)
    @ExcelProperty({"缴费明细查询数据", "缴费类型"})
    private String TRAN_TP;
    /**
     * 核心流水号
     */
    @ColumnWidth(15)
    @ExcelProperty({"缴费明细查询数据", "核心流水号"})
    private String HOST_SEQ;
    /**
     * 渠道流水号
     */
    @ColumnWidth(15)
    @ExcelProperty({"缴费明细查询数据", "渠道流水号"})
    private String REQ_SEQ;
}
