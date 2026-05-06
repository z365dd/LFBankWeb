package com.adtec.prod.util;

import org.apache.curator.shaded.com.google.common.base.Strings;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelUtil {

    private final static String excel2003L =".xls";    //2003- 版本的excel  
    private final static String excel2007U =".xlsx";   //2007+ 版本的excel  

    /** 
     * 描述：获取IO流中的数据，组装成List<List<Object>>对象 
     * @param in,fileName 
     * @return 
     * @throws IOException  
     */  
    public  static List<List<Object>> getBankListByExcel(InputStream in,String fileName) throws Exception{  
        List<List<Object>> list = null;  

        //创建Excel工作薄  
        Workbook work = getWorkbook(in,fileName);  
        if(null == work){  
            throw new Exception("创建Excel工作薄为空！");  
        }  
        Sheet sheet = null;  
        Row row = null;  
        Cell cell = null;  

        list = new ArrayList<List<Object>>();  
        //遍历Excel中所有的sheet  
        for (int i = 0; i < work.getNumberOfSheets(); i++) {  
            sheet = work.getSheetAt(i);  
            if(sheet==null){continue;}  

            //遍历当前sheet中的所有行  
            for (int j = sheet.getFirstRowNum(); j <=sheet.getLastRowNum(); j++) {  
                row = sheet.getRow(j); 
                if(row==null||row.getFirstCellNum()==j){continue;}  

                //遍历所有的列  
                List<Object> li = new ArrayList<Object>();  
                for (int k = row.getFirstCellNum(); k <row.getLastCellNum(); k++) {  
                    cell = row.getCell(k);
                    if(cell==null){continue;}  

                    li.add(getCellValue(cell));  
                }  
                list.add(li);  
            }  
        }  
        //System.out.println(list);
        return list;  

        }

    /** 
     * 描述：根据文件后缀，自适应上传文件的版本  
     * @param inStr,fileName 
     * @return 
     * @throws Exception 
     */  
    public  static Workbook getWorkbook(InputStream inStr,String fileName) throws Exception{  
        Workbook wb = null;  
        String fileType = fileName.substring(fileName.lastIndexOf("."));  
        if(excel2003L.equals(fileType)){  
            wb = new HSSFWorkbook(inStr);  //2003-  
        }else if(excel2007U.equals(fileType)){  
            wb = new XSSFWorkbook(inStr);  //2007+  
        }else{  
            throw new Exception("解析的文件格式有误！");  
        }  
        return wb;  
    }  


    /** 
     * 描述：对表格中数值进行格式化 
     * @param cell 
     * @return 
     */  
    public  static Object getCellValue(Cell cell){  
        Object value = null;  
        DecimalFormat df = new DecimalFormat("0");  //格式化number String字符  
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");  //日期格式化  
        DecimalFormat df2 = new DecimalFormat("0.00");  //格式化数字  

        /*switch (cell.getCellType()) {  
        case STRING:  
            value = cell.getRichStringCellValue().getString();  
            break;  
        case NUMERIC:  
            if("General".equals(cell.getCellStyle().getDataFormatString())){  
                value = df.format(cell.getNumericCellValue());  
            }else if("m/d/yy".equals(cell.getCellStyle().getDataFormatString())){  
                value = sdf.format(cell.getDateCellValue());  
            }else{  
                value = df2.format(cell.getNumericCellValue());  
            }  
            break;  
        case BOOLEAN:  
            value = cell.getBooleanCellValue();  
            break;  
        case BLANK:  
            value = "";  
            break;  
        default:  
            break;  
        }  */
        return value;  
    }      

    public static byte[] export(String sheetTitle, String[] title, List<Map<String, Object>> data,String[] heardKey) throws Exception {

        HSSFWorkbook wb = new HSSFWorkbook();//创建excel表
        HSSFSheet sheet = wb.createSheet(sheetTitle);
        sheet.setDefaultColumnWidth(20);//设置默认行宽

//        //表头样式（加粗，水平居中，垂直居中）
//        HSSFCellStyle cellStyle = wb.createCellStyle();
//        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
//        //cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
//        //设置边框样式
//        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
//        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
//        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
//        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        
        //表头样式（加粗，水平居中，垂直居中）
        HSSFCellStyle cellStyle = wb.createCellStyle();
//        cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
//        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
//        //设置边框样式
//        cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
//        cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
//        cellStyle.setBorderTop(BorderStyle.THIN);//上边框
//        cellStyle.setBorderRight(BorderStyle.THIN);//右边框

        HSSFFont fontStyle = wb.createFont();
//        fontStyle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        fontStyle.setBold(true);
        
        cellStyle.setFont(fontStyle);

        //标题样式（加粗，垂直居中）
        HSSFCellStyle cellStyle2 = wb.createCellStyle();
        //cellStyle2.setAlignment(HorizontalAlignment.CENTER);//垂直居中
        cellStyle2.setFont(fontStyle);

        //设置边框样式
//        cellStyle2.setBorderBottom(BorderStyle.THIN); //下边框
//        cellStyle2.setBorderLeft(BorderStyle.THIN);//左边框
//        cellStyle2.setBorderTop(BorderStyle.THIN);//上边框
//        cellStyle2.setBorderRight(BorderStyle.THIN);//右边框

        //字段样式（垂直居中）
        HSSFCellStyle cellStyle3 = wb.createCellStyle();
//        cellStyle3.setAlignment(HorizontalAlignment.CENTER);//垂直居中
//
//        //设置边框样式
//        cellStyle3.setBorderBottom(BorderStyle.THIN); //下边框
//        cellStyle3.setBorderLeft(BorderStyle.THIN);//左边框
//        cellStyle3.setBorderTop(BorderStyle.THIN);//上边框
//        cellStyle3.setBorderRight(BorderStyle.THIN);//右边框

        //创建表头
        HSSFRow row = sheet.createRow(0);
        row.setHeightInPoints(20);//行高
        
        HSSFCell cell = row.createCell(0);
        cell.setCellValue(sheetTitle);
        cell.setCellStyle(cellStyle);

        sheet.addMergedRegion(new CellRangeAddress(0,0,0,(title.length-1)));
        
        //创建标题
        HSSFRow rowTitle = sheet.createRow(1);
        rowTitle.setHeightInPoints(20);

        //设置每格数据的样式2（字体蓝色）
        HSSFCellStyle cellParamStyle2 = wb.createCellStyle();
//        cellParamStyle2.setAlignment(HorizontalAlignment.CENTER);
//        cellParamStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFFont ParamFontStyle2 = wb.createFont();
//        ParamFontStyle2.setColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());   //设置字体颜色 (蓝色)
        //ParamFontStyle2.setFontHeightInPoints((short) this.fontSize);
        cellParamStyle2.setFont(ParamFontStyle2);
        
        HSSFCell hc;
        for (int i = 0; i < title.length; i++) {
            hc = rowTitle.createCell(i);
            hc.setCellValue(title[i]);
            hc.setCellStyle(cellStyle2);
        }

        byte result[] = null;

        ByteArrayOutputStream out = null;
        int a = 2;
        
        try {
        	for (int i = 0; i < data.size(); i++) {
        	     HSSFRow roww = sheet.createRow((int) a);
        	     Map<String, Object> map = data.get(i);
        	     cell = null;
        	     for (int j = 0; j < heardKey.length; j++) {
        	         cell = roww.createCell(j);
        	         cell.setCellStyle(cellStyle);
        	         Object valueObject = map.get(heardKey[j]);
        	         String value = null;
        	         if (valueObject == null) {
        	             valueObject = "";
        	         }
        	         if (valueObject instanceof String) {
        	             //取出的数据是字符串直接赋值
        	             value = (String) map.get(heardKey[j]);
        	         } else if (valueObject instanceof Integer) {
        	             //取出的数据是Integer
        	             value = String.valueOf(((Integer) (valueObject)).floatValue());
        	         } else if (valueObject instanceof BigDecimal) {
        	             //取出的数据是BigDecimal
        	             value = String.valueOf(((BigDecimal) (valueObject)).floatValue());
        	         } else {
        	             value = valueObject.toString();
        	         }
        	         //设置单个单元格的字体颜色
        	         if(heardKey[j].equals("ddNum") || heardKey[j].equals("sjNum")){
        	         if((Long)map.get("ddNum")!=null){
        	             if((Long)map.get("sjNum")==null){
        	                 cell.setCellStyle(cellParamStyle2);
        	             } else if((Long) map.get("ddNum") != (Long) map.get("sjNum")){
        	                 if ((Long) map.get("ddNum") > (Long) map.get("sjNum")) {
        	                     cell.setCellStyle(cellParamStyle2);
        	                 }
        	                 if ((Long) map.get("ddNum") < (Long) map.get("sjNum")) {
        	                     cell.setCellStyle(cellParamStyle2);
        	                 }
        	             }else {
        	                 cell.setCellStyle(cellStyle);
        	             }
        	         }
        	         }
        	         cell.setCellValue(Strings.isNullOrEmpty(value) ? "" : value);
        	     }
        	     a++;
        	 }

            out = new ByteArrayOutputStream();
            wb.write(out);
            result =  out.toByteArray();
        } catch (Exception ex) {
        	System.out.println("ex:"+ex.getMessage());
        	throw ex;
        } finally{
            try {
                if(null != out){
                    out.close();
                }
            } catch (IOException e) {
                System.out.println("出现异常");
            } finally {
                try {
                    wb.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
        }

        return result;
    }

}
