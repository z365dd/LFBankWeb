package com.adtec.pay.dto.bookList;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode
public class Book {
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
     * 欠费时段
     */
    @ColumnWidth(15)
    @ExcelProperty({"缴费明细查询数据", "欠费时段"})
    private String OWE_MONTH;
    /**
     * 缴费方式
     */
    @ColumnWidth(15)
    @ExcelProperty({"缴费明细查询数据", "缴费方式"})
    private String PAY_TP;
    /**
     * 地址
     */
    @ColumnWidth(20)
    @ExcelProperty({"缴费明细查询数据", "住址"})
    private String ADDR;
    /**
     * 缴费金额
     */
    @ColumnWidth(15)
    @ExcelProperty({"缴费明细查询数据", "缴费金额"})
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
    /**
     * 三方流水号
     */
    @ColumnWidth(15)
    @ExcelProperty({"缴费明细查询数据", "三方流水号"})
    private String OTH_SEQ;
    /**
     * 开户机构
     */
    @ColumnWidth(15)
    @ExcelProperty({"缴费明细查询数据", "开户机构"})
    private String OPEN_CUST_BRCH;
    /**
     * 是否自动扣款
     */
    @ColumnWidth(20)
    @ExcelProperty({"缴费明细查询数据", "是否自动扣款"})
    private String AUTO_FLG;
}
