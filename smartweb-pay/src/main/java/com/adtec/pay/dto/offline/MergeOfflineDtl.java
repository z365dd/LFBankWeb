package com.adtec.pay.dto.offline;

import com.adtec.pay.dto.offline.temple.*;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class MergeOfflineDtl {
    /**
     * 缴费日期
     */
    @ColumnWidth(15)
    @ExcelProperty(value= "缴费日期")
    private String PLAT_DATE;
    /**
     * 业务编号
     */
    @ColumnWidth(15)
    @ExcelProperty(value= "业务编号")
    private String BUSI_NO;
    /**
     * 业务名称
     */
    @ColumnWidth(15)
    @ExcelProperty(value= "业务名称")
    private String BUSI_NAME;

    /**
     * 收费周期
     */
    @ColumnWidth(15)
    @ExcelProperty(value= "收费周期")
    private String OWE_MONTH;
    /**
     * 客户名称
     */
    @ColumnWidth(15)
    @ExcelProperty(value= "班级")
    private String STU_CLASS;
    /**
     * 缴费号
     */
    @ExcelProperty(value= "学号")
    private String PAY_NO;
    /**
     * 客户名称
     */
    @ColumnWidth(15)
    @ExcelProperty(value= "学生姓名")
    private String NAME;


    /**
     * 手机号码
     */
    @ColumnWidth(15)
    @ExcelProperty(value= "手机号码")
    private String PHONE_NO;

    /**
     * 证件号码
     */
    @ColumnWidth(15)
    @ExcelProperty(value= "证件号码")
    private String CERT_NO;
    /**
     * 缴费状态
     */
    @ColumnWidth(15)
    @ExcelProperty(value= "缴费状态",converter = StatConverter.class)
    private String STAT;

    /**
     * 缴费方式
     */
//    @ColumnWidth(15)
//    @ExcelProperty(value= "缴费方式")
//    private String PAY_TP;


    /**
     * 费用总额
     */
    @ColumnWidth(15)
    @ExcelProperty(value= "费用总额",converter = DataConverter4.class)
    private String DATA4;
    /**
     * 应收学费
     */
    @ColumnWidth(15)
    @ExcelProperty(value= "应收学费",converter = DataConverter.class)
    private String DATA;
    /**
     * 学费减免
     */
    @ColumnWidth(15)
    @ExcelProperty(value= "学费减免",converter = DataConverter1.class)
    private String DATA1;
    /**
     * 应收住宿费
     */
    @ColumnWidth(15)
    @ExcelProperty(value= "应收住宿费",converter = DataConverter2.class)
    private String DATA2;

    /**
     * 住宿费减免
     */
    @ColumnWidth(15)
    @ExcelProperty(value= "住宿费减免",converter = DataConverter3.class)
    private String DATA3;

    /**
     * 餐费
     */
    @ColumnWidth(15)
    @ExcelProperty(value= "餐费",converter = DataConverter5.class)
    private String DATA5;

    /**
     * 餐费
     */
    @ColumnWidth(15)
    @ExcelProperty(value= "餐费减免",converter = DataConverter6.class)
    private String DATA6;




}
