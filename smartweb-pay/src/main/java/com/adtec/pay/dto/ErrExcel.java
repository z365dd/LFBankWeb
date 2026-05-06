package com.adtec.pay.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 错误导出excel对应实体类
 */
@Getter
@Setter
@EqualsAndHashCode
public class ErrExcel {
    /**
     * 协议编号
     */
    @ColumnWidth(15)
    @ExcelProperty({"提示信息"})
    private String ERR_MESSAGE;


    public String getERR_MESSAGE() {
        return ERR_MESSAGE;
    }

    public void setERR_MESSAGE(String ERR_MESSAGE) {
        this.ERR_MESSAGE = ERR_MESSAGE;
    }
}
