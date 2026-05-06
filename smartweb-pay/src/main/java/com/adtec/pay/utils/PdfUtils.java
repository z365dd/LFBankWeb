package com.adtec.pay.utils;

import com.adtec.pay.dto.bookList.Book;
import com.adtec.pay.dto.bookList.BookList;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.RectangleReadOnly;
import com.itextpdf.text.pdf.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PdfUtils {
    private final static Log logger = LogFactory.getLog(PdfUtils.class);

    /**
     * 利用模板生成 pdf 导出（支持分页模板） list格式
     * @param list         导入到模板的数据
     * @param params     参数 包含pdf模板路径、pdf导出文件名称等等
     * @param response     响应对象
     */
//    public static void pdfExport(Map<String, String> params, List list, HttpServletResponse response) {
//        OutputStream out = null;
//        ByteArrayOutputStream[] bos = new ByteArrayOutputStream[list.size()];
//        String fileName = params.get("fileName");
//        String templateName = params.get("templateName");
//        try {
//            // 输出流
//            response.setContentType("application/pdf");
//            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
//            out = response.getOutputStream();
//
//            // 解决中文字体不显示的问题
//            BaseFont baseFont = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
//            ArrayList<BaseFont> fontList = new ArrayList<BaseFont>();
//            fontList.add(baseFont);
//            for (int i = 0; i < list.size(); i++) {
//                //获取表单
//                PdfReader reader = new PdfReader(templateName);
//                bos[i] = new ByteArrayOutputStream();
//                PdfStamper stamp = new PdfStamper(reader, bos[i]);
//                AcroFields form = stamp.getAcroFields();
//                BookList item = list.get(i);
////                JSONObject item = list.getJSONObject(i);
//                Field[] fields = BookList.class.getDeclaredFields();
//                form.setSubstitutionFonts(fontList);
//                //填充表单  制作模板的时候 将模板内容与数据库字段相对应 这样不用再配置模板与业务关系了
////                for (String key : item.keySet()) {
////                    form.setField(key, item.get(key).toString());
////                }
//                //依靠反射获取对象的某个key对应的value
//
////                try {
////                    for (Field field : fields) {
////                        String name = field.getName();
////                        String value = field.get(item).toString();
////                        form.setField(name, value);
////                    }
////                } catch (IllegalAccessException e) {
////                    logger.error("获取对象属性值失败");
////                }
//                stamp.setFormFlattening(true);
//                stamp.close();
//                reader.close();
//            }
//            // 创建并打开一个 pdf 对象
//            Document doc = new Document();
//            PdfCopy pdfCopy = new PdfCopy(doc, out);
//            doc.open();
//
//            //把生成的多页拼到一个文档里
//            for (int i = 0; i < list.size(); i++) {
//                PdfImportedPage impage = pdfCopy.getImportedPage(new PdfReader(bos[i].toByteArray()), 1);
//                pdfCopy.addPage(impage);
//            }
//            //关闭
//            doc.close();
//            pdfCopy.close();
//
//        } catch (Exception e) {
//            logger.error("文件操作错误");
//        } finally {
//            try {
//                if (out != null) {
//                    out.close();
//                }
//            } catch (IOException e) {
//                logger.error("关闭输出流异常");
//            }
//        }
//    }


    /**
     * 利用模板生成 pdf 导出（支持分页模板）   json格式
     *
     * @param list     导入到模板的数据
     * @param params   参数 包含pdf模板路径、pdf导出文件名称等等
     * @param response 响应对象
     */
    public static void pdfExport(Map<String, String> params, JSONArray list, HttpServletResponse response) {
        OutputStream out = null;
        ByteArrayOutputStream[] bos = new ByteArrayOutputStream[list.size()];
        String fileName = params.get("fileName");
        String templateName = params.get("templateName");
        try {
            // 输出流
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
            out = response.getOutputStream();

            // 解决中文字体不显示的问题
            BaseFont baseFont = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            ArrayList<BaseFont> fontList = new ArrayList<BaseFont>();
            fontList.add(baseFont);
            for (int i = 0; i < list.size(); i++) {
                //获取表单
                PdfReader reader = new PdfReader(templateName);
                bos[i] = new ByteArrayOutputStream();
                PdfStamper stamp = new PdfStamper(reader, bos[i]);
                AcroFields form = stamp.getAcroFields();
//                BookList item = list.get(i);
                JSONObject item = list.getJSONObject(i);
                Field[] fields = BookList.class.getDeclaredFields();
                form.setSubstitutionFonts(fontList);
                //填充表单  制作模板的时候 将模板内容与数据库字段相对应 这样不用再配置模板与业务关系了
                for (String key : item.keySet()) {
                    form.setField(key, item.get(key).toString());
                }
                //依靠反射获取对象的某个key对应的value

//                try {
//                    for (Field field : fields) {
//                        String name = field.getName();
//                        String value = field.get(item).toString();
//                        form.setField(name, value);
//                    }
//                } catch (IllegalAccessException e) {
//                    logger.error("获取对象属性值失败");
//                }
                stamp.setFormFlattening(true);
                stamp.close();
                reader.close();
            }
            // 创建并打开一个 pdf 对象
            Document doc = new Document();
            PdfCopy pdfCopy = new PdfCopy(doc, out);
            doc.open();

            //把生成的多页拼到一个文档里
            for (int i = 0; i < list.size(); i++) {
                PdfImportedPage impage = pdfCopy.getImportedPage(new PdfReader(bos[i].toByteArray()), 1);
                pdfCopy.addPage(impage);
            }
            //关闭
            doc.close();
            pdfCopy.close();

        } catch (Exception e) {
            logger.error("文件操作错误");
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                logger.error("关闭输出流异常");
            }
        }
    }

    /**
     * 利用模板生成 pdf 导出（支持分页模板） List格式
     *
     * @param list     导入到模板的数据
     * @param params   参数 包含pdf模板路径、pdf导出文件名称等等
     * @param response 响应对象
     */
    public static void pdfExport(Map<String, String> params, List<Map<String, String>> list, HttpServletResponse response) {
        OutputStream out = null;
        ByteArrayOutputStream[] bos = new ByteArrayOutputStream[list.size()];
        String fileName = params.get("fileName");
        String templateName = params.get("templateName");
        try {
            // 输出流
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
            out = response.getOutputStream();

            // 解决中文字体不显示的问题
            BaseFont baseFont = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            ArrayList<BaseFont> fontList = new ArrayList<BaseFont>();
            fontList.add(baseFont);
            for (int i = 0; i < list.size(); i++) {
                //获取表单
                PdfReader reader = new PdfReader(templateName);
                bos[i] = new ByteArrayOutputStream();
                PdfStamper stamp = new PdfStamper(reader, bos[i]);
                AcroFields form = stamp.getAcroFields();
                Map<String, String> item = (Map<String, String>) list.get(i);
                form.setSubstitutionFonts(fontList);
                //填充表单  制作模板的时候 将模板内容与数据库字段相对应 这样不用再配置模板与业务关系了
                for (String key : item.keySet()) {
                    form.setField(key, item.get(key).toString());
                }
                stamp.setFormFlattening(true);
                stamp.close();
                reader.close();
            }
            // 创建并打开一个 pdf 对象
            Document doc = new Document();
            PdfCopy pdfCopy = new PdfCopy(doc, out);
            doc.open();

            //把生成的多页拼到一个文档里
            for (int i = 0; i < list.size(); i++) {
                PdfImportedPage impage = pdfCopy.getImportedPage(new PdfReader(bos[i].toByteArray()), 1);
                pdfCopy.addPage(impage);
            }
            //关闭
            doc.close();
            pdfCopy.close();

        } catch (Exception e) {
            logger.error("文件操作错误");
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                logger.error("关闭输出流异常");
            }
        }
    }


    /**
     * 利用模板生成 pdf 导出（支持分页模板） List格式
     *
     * @param list     导入到模板的数据
     * @param params   参数 包含pdf模板路径、pdf导出文件名称等等
     * @param response 响应对象
     * @param page     每张页数
     */
    public static void pdfExport(Map<String, String> params, List<Map<String, String>> list, HttpServletResponse response, int page) {
        OutputStream out = null;
        ByteArrayOutputStream[] bos = new ByteArrayOutputStream[list.size()];
        String fileName = params.get("fileName");
        String templateName = params.get("templateName");

        PdfWriter writer = null;
        PdfContentByte cb = null;

        try {
            // 输出流
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
            out = response.getOutputStream();
            // 解决中文字体不显示的问题
            BaseFont baseFont = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            ArrayList<BaseFont> fontList = new ArrayList<BaseFont>();
            fontList.add(baseFont);

            // 创建并打开一个 pdf 对象
            Document doc = new Document();

            for (int i = 0; i < list.size(); i++) {
                //获取表单
                PdfReader reader = new PdfReader(templateName);
                bos[i] = new ByteArrayOutputStream();
                PdfStamper stamp = new PdfStamper(reader, bos[i]);
                AcroFields form = stamp.getAcroFields();
                Map<String, String> item = (Map<String, String>) list.get(i);
                form.setSubstitutionFonts(fontList);
                //填充表单  制作模板的时候 将模板内容与数据库字段相对应 这样不用再配置模板与业务关系了
                for (String key : item.keySet()) {
                    form.setField(key, item.get(key).toString());
                }
                stamp.setFormFlattening(true);
                stamp.close();
                reader.close();
            }

            writer = PdfWriter.getInstance(doc, out);
            doc.open();
            cb = writer.getDirectContent();

            //把生成的多页拼到一个文档里
            PdfImportedPage impage = null;
            for (int i = 0; i < list.size(); i++) {
                if (writer != null){
                     impage = writer.getImportedPage(new PdfReader(bos[i].toByteArray()), 1);

                }else{
                    logger.error("writer为null，无法获取PdfImportedPage");
                }


                float documentWidth = doc.getPageSize().getWidth();
                float documentHeight = doc.getPageSize().getHeight();

                float pageWidth = 0;
                float pageHeight = 0;
                if (impage !=null){
                     pageWidth = impage.getWidth();
                     pageHeight = impage.getHeight();
                }


                //第二子页面的位置
                float offsetY = -450;

                float nums = i % page;
                float widthScale = documentWidth / pageWidth;
                float heightScale = documentHeight / pageHeight;
                float scale = Math.min(widthScale, heightScale);
                float offsetX = documentWidth - (pageWidth * scale) + 20f;

                if(nums == 0){
                    //创建新的页码
                    doc.newPage();
                    offsetY = (impage !=null ? impage.getHeight() : 0f)/2 - 450f;
                }
                if(cb != null){
                    cb.addTemplate(impage, scale, 0, 0, scale, offsetX, offsetY);
                }


            }
            //关闭
            doc.close();
//            pdfCopy.close();

        } catch (Exception e) {
            logger.error("文件操作错误");
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                logger.error("关闭输出流异常");
            }
        }
    }

}
