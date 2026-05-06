package com.adtec.pay.utils.excelExport;

import com.adtec.framework.interfaces.share.IDataset;


public class ExcelUtil {
    /**
     * 最简单的填充
     *
     * @since 2.1.1
     */
    public static void FillData(IDataset data) {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
//        String templateFileName =ParamUtil.getString("excelTemplate.location") + File.separator + "pdfTemplate.xlsx";
        String templateFileName = "C:/Users/11276/Desktop/pdfTemplate.xlsx";
        System.out.println("Excel模板的位置在:" + templateFileName);

        // 方案1 根据对象填充
//        String fileName = FileUtil.getPath() + "simpleFill" + System.currentTimeMillis() + ".xlsx";
//        String fileName = ParamUtil.getString("excelTemplate.location") + File.separator + System.currentTimeMillis() + "fillData.xlsx";
        String fileName = "C:/Users/11276/Desktop/" + "ReceiptPrint" + System.currentTimeMillis() + ".xlsx";
        System.out.println("充填excel的位置在:" + fileName);
        // 这里 会填充到第一个sheet， 然后文件流会自动关闭
        FillData fillData = new FillData();
//        sendData(fillData);
//        EasyExcel.write(fileName).withTemplate(templateFileName).sheet().doFill(fillData);
    }

}
