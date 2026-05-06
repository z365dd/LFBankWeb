package com.adtec.pay.dto.offline.temple;

import com.adtec.pay.utils.CaculateUtils;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.WriteCellData;

public class DataConverter4 implements Converter<String> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * 这里是读的时候会调用 不用管
     *
     * @return
     */
    @Override
    public String convertToJavaData(ReadConverterContext<?> context) {
        return context.getReadCellData().getStringValue();
    }

    /**
     * 数据解析-应收学费
     *
     * @return
     */
    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<String> context) {
        String value = context.getValue();
        String[] items = value.split("\\|");
        value = "0";
        double totAmt = 0.00;
        double dctAmt = 0.00;
        for (String item : items) {
            String[] subItems = item.split(",");
            if(subItems[0].contains("学费") || subItems[0].contains("住宿费") || subItems[0].contains("餐费")){
                totAmt = CaculateUtils.add(totAmt,Double.parseDouble(subItems[1]));
                dctAmt = CaculateUtils.add(dctAmt, Double.parseDouble(subItems[2]));
            }

        }
        value= String.valueOf(CaculateUtils.sub(totAmt, dctAmt));
        return new WriteCellData<>(value);
    }
}