package com.adtec.pay.dto.offline;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class DataInfo {


    /**
     * 归属机构
     */
    @ColumnWidth(15)
    @ExcelProperty({"归属机构"})
    private String BELONG_BRCH;

    /**
     * 归属机构名称
     */
    @ColumnWidth(15)
    @ExcelProperty({"归属机构名称"})
    private String NAME;

    /**
     * 业务编号
     */
    @ColumnWidth(15)
    @ExcelProperty({"业务编号"})
    private String BUSI_NO;

    /**
     * 业务编号
     */
    @ColumnWidth(15)
    @ExcelProperty({"业务名称"})
    private String BUSI_NAME;

    /**
     * 本行卡笔数
     */
    @ColumnWidth(15)
    @ExcelProperty({"本行卡笔数"})
    private String BANKCARD_NUM;

    /**
     * 本行卡金额
     */
    @ColumnWidth(15)
    @ExcelProperty({"本行卡金额"})
    private String BANKCARD_AMT;

    /**
     * 成功数量
     */
    @ColumnWidth(15)
    @ExcelProperty({"成功缴费笔数"})
    private String TOT_NUM;
    /**
     * 成功金额
     */
    @ColumnWidth(15)
    @ExcelProperty({"成功缴费金额"})
    private String TOT_AMT;





    public String getTOT_NUM() {
        return TOT_NUM;
    }

    public void setTOT_NUM(String TOT_NUM) {
        this.TOT_NUM = TOT_NUM;
    }

    public String getTOT_AMT() {
        return TOT_AMT;
    }

    public void setTOT_AMT(String TOT_AMT) {
        this.TOT_AMT = TOT_AMT;
    }

    public String getBUSI_NO() {
        return BUSI_NO;
    }

    public void setBUSI_NO(String BUSI_NO) {
        this.BUSI_NO = BUSI_NO;
    }

    public String getBELONG_BRCH() {
        return BELONG_BRCH;
    }

    public void setBELONG_BRCH(String BELONG_BRCH) {
        this.BELONG_BRCH = BELONG_BRCH;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getBANKCARD_NUM() {
        return BANKCARD_NUM;
    }

    public void setBANKCARD_NUM(String BANKCARD_NUM) {
        this.BANKCARD_NUM = BANKCARD_NUM;
    }

    public String getBANKCARD_AMT() {
        return BANKCARD_AMT;
    }

    public void setBANKCARD_AMT(String BANKCARD_AMT) {
        this.BANKCARD_AMT = BANKCARD_AMT;
    }

    public String getBUSI_NAME() {
        return BUSI_NAME;
    }

    public void setBUSI_NAME(String BUSI_NAME) {
        this.BUSI_NAME = BUSI_NAME;
    }
}
