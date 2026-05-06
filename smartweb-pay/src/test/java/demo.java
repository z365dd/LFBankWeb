

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;
import java.util.Map;

public class demo {
    public static void main(String[] args) throws IOException {
//        readCsv();


        writeExcel();
        readXls();

        int num = 1000;
        int times = num / 10000 == 0 || num % 10000 != 0 ? num / 10000 + 1 : num/10000;
        System.out.println(times);

    }

    public static void readCsv() throws IOException {
        File file = new File("C:\\Users\\11276\\Desktop\\1.csv");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "gbk"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }

    public static void readXls() throws IOException {
//        String fileName = "C:\\Users\\52774\\Desktop\\data_test.xlsx";
        String fileName = "d:\\testexcel.xlsx";

        InputStream inputStream = new FileInputStream(fileName);
        Workbook workbook = null;
        if (fileName.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (fileName.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        }

        // 获取第一个sheet
        Sheet sheet = workbook.getSheetAt(0);
        //循环获取每一行
        int num = sheet.getPhysicalNumberOfRows();
        for (int i = 0; i < num; i++) {
            Row row = sheet.getRow(i);
            System.out.println("开始处理第" + i + "行数据");
            if (i == 0) {
                System.out.println("表头：" + row.toString());
            } else {
                int cellNum = row.getPhysicalNumberOfCells();
                for (int j = 0; j < cellNum; j++) {
                    Cell cell = row.getCell(j);
                    cell.setCellType(CellType.STRING);
                    String val = cell.getStringCellValue();
                    System.out.print("第" + j + "列，值为:" + val + ",");
                }
                System.out.println();
            }
        }
        workbook.close();
    }

    public static void writeExcel() {

        String fileName = "d:\\testexcel.xlsx";
        try {
            Workbook wb = null;

            if (fileName.endsWith("xls")) {
                wb = new HSSFWorkbook();
            } else if (fileName.endsWith("xlsx")) {
                wb = new XSSFWorkbook();
            }
            Sheet sheet = wb.createSheet("数据文件");//参数new sheet是工作表的表名

            String[] title = {"序号","姓名", "电话", "金额", "证件号码"};
            XSSFRow row = (XSSFRow) sheet.createRow(0);
            XSSFCell cell = row.createCell(0);
            for (int i = 0; i < title.length; i++) {
                row.createCell(i).setCellValue(title[i]);
            }

            int num = 10000;
            // 写明细数据
            for (int i = 1; i <= num; i++) {
                row = (XSSFRow) sheet.createRow(i);
                cell = row.createCell(0);
                row.createCell(0).setCellValue( i);
                row.createCell(1).setCellValue("张三" + i);
                String tel = String.format("130%010d", i);
                row.createCell(2).setCellValue(tel);
                row.createCell(3).setCellValue(0.01);
                String certNo = String.format("110001%04d0102", i);
                row.createCell(4).setCellValue(certNo);
            }

            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            wb.write(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("写入excel完成");


    }

    private XSSFWorkbook createUserListExcel(List<Map<String, Object>> listresult) {
        // 1.创建HSSFWorkbook，一个HSSFWorkbook对应一个Excel文件
        XSSFWorkbook wb = new XSSFWorkbook();
        // 2.在workbook中添加一个sheet,对应Excel文件中的sheet
        XSSFSheet sheet = wb.createSheet("sheet1");
        // 3.设置表头，即每个列的列名
        String[] titel = {"姓名", "电话", "金额", "证件号码"};
        // 3.1创建第一行
        XSSFRow row = sheet.createRow(0);
        // 此处创建一个序号列
        row.createCell(0).setCellValue("序号");
        // 将列名写入
        for (int i = 0; i < titel.length; i++) {
            // 给列写入数据,创建单元格，写入数据
            row.createCell(i + 1).setCellValue(titel[i]);
        }
        // 写入正式数据
        for (int i = 0; i < listresult.size(); i++) {
            // 创建行
            row = sheet.createRow(i + 1);
            // 序号
            row.createCell(0).setCellValue(i + 1);
            // 医院名称
            row.createCell(1).setCellValue(listresult.get(i).get("rowKey1").toString());
            sheet.autoSizeColumn(1, true);
            // 业务类型
            row.createCell(2).setCellValue(listresult.get(i).get("rowKey2").toString());
            // 异常信息
            row.createCell(3).setCellValue(listresult.get(i).get("rowKey3").toString());
            // 数量
            row.createCell(4).setCellValue(listresult.get(i).get("rowKey4").toString());
        }
        /**
         * 上面的操作已经是生成一个完整的文件了，只需要将生成的流转换成文件即可；
         * 下面的设置宽度可有可无，对整体影响不大
         */
        // 设置单元格宽度
        int curColWidth = 0;
        for (int i = 0; i <= titel.length; i++) {
            // 列自适应宽度，对于中文半角不友好，如果列内包含中文需要对包含中文的重新设置。
            sheet.autoSizeColumn(i, true);
            // 为每一列设置一个最小值，方便中文显示
            curColWidth = sheet.getColumnWidth(i);
            if (curColWidth < 2500) {
                sheet.setColumnWidth(i, 2500);
            }
            // 第3列文字较多，设置较大点。
            sheet.setColumnWidth(3, 8000);
        }
        return wb;
    }


}
