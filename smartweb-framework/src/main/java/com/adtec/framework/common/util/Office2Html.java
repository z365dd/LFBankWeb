/**
 * 系统名称: SmartWeb平台
 * 模块名称: word、excel、ppt转成html
 * 类  名  称: Office2Html.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2018年3月14日 上午9:22:50<br>
 * 系统版本: V1.0.0<br>
 * * 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * <p>
 * ========     ======  ============================================
 */
package com.adtec.framework.common.util;

import com.sun.image.codec.jpeg.ImageFormatException;
import fr.opensagres.poi.xwpf.converter.core.BasicURIResolver;
import fr.opensagres.poi.xwpf.converter.core.FileImageExtractor;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLConverter;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFTextParagraph;
import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xslf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.Color;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

//import org.apache.poi.xwpf.converter.core.BasicURIResolver;
//import org.apache.poi.xwpf.converter.core.FileImageExtractor;
//import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
//import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;

/**
 * @author chenyl
 */
public class Office2Html {
    private final static Logger log = LoggerFactory.getLogger(Office2Html.class);
    // 20181106 mody by chenyl for 修改为静态代码块实现单例模式
    private static Office2Html instance;

    static {
        instance = new Office2Html();
    }

    private Office2Html() {
    }

    public static Office2Html getInstance() {
        return instance;
    }

    public static void toHtml(String officePath, String htmlPath) {
        if (null != officePath && officePath.length() > 0) {
            if (officePath.toLowerCase().endsWith(".doc") || officePath.toLowerCase().endsWith(".docx")) {
                getInstance().word2Html(officePath, htmlPath);
            } else if (officePath.toLowerCase().endsWith(".xls") || officePath.toLowerCase().endsWith(".xlsx")) {
                getInstance().excel2Html(officePath, htmlPath);
            } else if (officePath.toLowerCase().endsWith(".ppt") || officePath.toLowerCase().endsWith(".pptx")) {
                getInstance().ppt2Html(officePath, htmlPath);
            } else {
                log.error("[" + officePath + "]只支持word、excel、ppt文件进行转换");
                return;
            }

        }
    }

    /**
     * 将word文件转成html，用于web段展示
     * @param wordPath    word文件的绝对路径
     * @param htmlPath    生成html文件的绝对路径
     */
    @SuppressWarnings("rawtypes")
    public void word2Html(String wordPath, String htmlPath) {
        if (null == wordPath || (null != wordPath && !(wordPath.toLowerCase().lastIndexOf(".doc") > -1 || wordPath.toLowerCase().lastIndexOf(".docx") > -1))) {
            log.error("[" + wordPath + "]源文件不能空且必须为doc或docx结尾");
            return;
        }
        if (null == htmlPath || (null != htmlPath && htmlPath.lastIndexOf(".html") == -1)) {
            log.error("[" + htmlPath + "]目标文件不能空且必须为html结尾");
            return;
        }
        wordPath = FileUtil.path(wordPath);
        htmlPath = FileUtil.path(htmlPath);
        String path = htmlPath.substring(0, htmlPath.lastIndexOf("/") + 1);
        String imagesPath = path + "images/";
        InputStream input = null;
        ByteArrayOutputStream outStream = null;
        FileOutputStream out = null;
        File wordFile = new File(wordPath);
        if (!wordFile.exists()) {
            log.error("[" + wordPath + "]源文件不存在");
            return;
        }
        try {
            FileUtil.createDirectory(path);
            FileUtil.createDirectory(imagesPath);
            // 处理word2007+以上版本的
            if (wordFile.getName().toLowerCase().endsWith(".docx")) {
                //读取文档内容    
                input = new FileInputStream(wordFile);
                XWPFDocument document = new XWPFDocument(input);
                File imageFolderFile = new File(imagesPath);
                //加载html页面时图片路径  
                XHTMLOptions options = XHTMLOptions.create().URIResolver(new BasicURIResolver("./images"));
                //图片保存文件夹路径  
                options.setExtractor(new FileImageExtractor(imageFolderFile));
                out = new FileOutputStream(new File(htmlPath));
                XHTMLConverter.getInstance().convert(document, out, options);
                out.flush();
            } else if (wordFile.getName().toLowerCase().endsWith(".doc")) {
                // 处理word2003版本的
                input = new FileInputStream(wordPath);
                HWPFDocument wordDocument = new HWPFDocument(input);
                WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
                        DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
                wordToHtmlConverter.setPicturesManager(new PicturesManager() {
                    @Override
                    public String savePicture(byte[] content, PictureType pictureType, String suggestedName,
                                              float widthInches, float heightInches) {
                        return "./images/" + suggestedName;    // 图片为当前html文件的相对路径下的images
                    }
                });

                List pics = wordDocument.getPicturesTable().getAllPictures();
                if (pics != null) {
                    for (int i = 0; i < pics.size(); i++) {
                        Picture pic = (Picture) pics.get(i);
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(imagesPath + pic.suggestFullFileName());
                            pic.writeImageContent(fos);
                            fos.flush();
                        } catch (IOException e) {
                System.out.println("出现异常");
            } finally {
                            if (null != fos) {
                                try {
                                    fos.close();
                                } catch (IOException e) {
                System.out.println("出现异常");
            }
                            }

                        }
                    }
                }
                wordToHtmlConverter.processDocument(wordDocument);

                Document htmlDocument = wordToHtmlConverter.getDocument();
                outStream = new ByteArrayOutputStream();
                DOMSource domSource = new DOMSource(htmlDocument);
                StreamResult streamResult = new StreamResult(outStream);
                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer serializer = tf.newTransformer();
                serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
                serializer.setOutputProperty(OutputKeys.INDENT, "yes");
                serializer.setOutputProperty(OutputKeys.METHOD, "html");
                serializer.transform(domSource, streamResult);
                outStream.flush();
                String content = new String(outStream.toByteArray());
                FileUtil.writeToFile(htmlPath, content, false);
            } else {
                log.error("[" + wordPath + "]不支持word版本");
                return;
            }
            log.info("[" + wordPath + "]源文件转换为[" + htmlPath + "]成功");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("[" + wordPath + "]源文件转换为[" + htmlPath + "]失败", e);
        } finally {
            if (null != input) {
                try {
                    input.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error("关闭输入流失败", e);
                }
            }
            if (null != outStream) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error("关闭输出流失败", e);
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error("关闭输出流失败", e);
                }
            }
        }
    }

    /**
     * excel转换成html
     * @param excelPath        excel文件的绝对路径
     * @param htmlPath        生成html文件的绝对路径
     */
    @SuppressWarnings({"resource", "rawtypes"})
    public void excel2Html(String excelPath, String htmlPath) {
        if (null == excelPath || (null != excelPath && !(excelPath.lastIndexOf(".xls") > -1 || excelPath.lastIndexOf(".xlsx") > -1))) {
            log.error("[" + excelPath + "]源文件不能空且必须为xls或xlsx结尾");
            return;
        }
        if (null == htmlPath || (null != htmlPath && htmlPath.lastIndexOf(".html") == -1)) {
            log.error("[" + htmlPath + "]目标文件不能空且必须为html结尾");
            return;
        }
        excelPath = FileUtil.path(excelPath);
        htmlPath = FileUtil.path(htmlPath);
        String path = htmlPath.substring(0, htmlPath.lastIndexOf("/") + 1);
        String imagesPath = path + "images/";
        InputStream input = null;
        ByteArrayOutputStream outStream = null;
        FileOutputStream out = null;
        File excelFile = new File(excelPath);
        if (!excelFile.exists()) {
            log.error("[" + excelPath + "]源文件不存在");
            return;
        }
        try {
            FileUtil.createDirectory(path);
            FileUtil.createDirectory(imagesPath);
            // 处理excel2007+以上版本的
            if (excelFile.getName().toLowerCase().endsWith(".xlsx")) {
                // 20181106 mody by chenyl for 生成的html代码内容修改为使用StringBuilder类
                StringBuilder html = new StringBuilder();
                html.append("<html>\n<meta charset=\"utf-8\">");
                // 读取文档内容
                input = new FileInputStream(excelFile);
                Workbook workbook = new XSSFWorkbook(input);
                if (workbook.getNumberOfSheets() > 0) {
                    for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
                        Sheet sheet = workbook.getSheetAt(numSheet);
                        if (sheet == null) {
                            continue;
                        }
                        html.append("=======================" + sheet.getSheetName() + "=========================\n<br>\n<br>\n");

                        int firstRowIndex = sheet.getFirstRowNum();
                        int lastRowIndex = sheet.getLastRowNum();
                        html.append("<table border='1' align='left'>\n");
                        Row firstRow = sheet.getRow(firstRowIndex);
                        if (null != firstRow) {
                            for (int i = firstRow.getFirstCellNum(); i <= firstRow.getLastCellNum(); i++) {
                                Cell cell = firstRow.getCell(i);
                                String cellValue = getCellValue(cell, true);
                                html.append("\t<th>" + (DataUtil.isNullStr(cellValue) ? "&nbsp" : cellValue) + "</th>\n");
                            }

                            // 行
                            for (int rowIndex = firstRowIndex + 1; rowIndex <= lastRowIndex; rowIndex++) {
                                Row currentRow = sheet.getRow(rowIndex);
                                html.append("\t<tr>\n");
                                if (currentRow != null) {

                                    int firstColumnIndex = currentRow.getFirstCellNum();
                                    int lastColumnIndex = currentRow.getLastCellNum();
                                    // 列
                                    for (int columnIndex = firstColumnIndex; columnIndex <= lastColumnIndex; columnIndex++) {
                                        Cell currentCell = currentRow.getCell(columnIndex);
                                        String currentCellValue = getCellValue(currentCell, true);
                                        html.append("\t\t<td>" + (DataUtil.isNullStr(currentCellValue) ? "&nbsp" : currentCellValue) + "</td>\n");
                                    }
                                } else {
                                    html.append(" ");
                                }
                                html.append("\t</tr>\n");
                            }
                            html.append("</table>\n");
                        }
                        FileUtil.writeToFile(htmlPath, html.toString(), "utf-8", false);
                    }
                } else {
                    FileUtil.writeToFile(htmlPath, html.toString(), "utf-8", false);
                }

            } else if (excelFile.getName().toLowerCase().endsWith(".xls")) {
                // 处理excel2003版本的
                input = new FileInputStream(excelPath);
                HSSFWorkbook excelBook = new HSSFWorkbook(input);
                ExcelToHtmlConverter excelToHtmlConverter = new ExcelToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
                excelToHtmlConverter.processWorkbook(excelBook);
                List pics = excelBook.getAllPictures();
                if (pics != null) {
                    for (int i = 0; i < pics.size(); i++) {
                        //add by zx 20200417 不能转化为Picture，改为HSSFPictureData
                        //Picture pic = (Picture) pics.get(i);
                        FileOutputStream fos = null;
                        try {
                            HSSFPictureData pic = (HSSFPictureData) pics.get(i);
                            byte[] data = pic.getData();
                            fos = new FileOutputStream(imagesPath + pic.suggestFileExtension());
                            fos.write(data);
                            fos.flush();

                        } catch (IOException e) {
                            System.out.println("出现异常");
            } finally {
                            if (null != fos) {
                                try {
                                    fos.close();
                                } catch (Exception e) {
                                    System.out.println("操作失败");
                                }
                            }
                        }
                    }
                }
                Document htmlDocument = excelToHtmlConverter.getDocument();
                outStream = new ByteArrayOutputStream();
                DOMSource domSource = new DOMSource(htmlDocument);
                StreamResult streamResult = new StreamResult(outStream);
                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer serializer = tf.newTransformer();
                serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
                serializer.setOutputProperty(OutputKeys.INDENT, "yes");
                serializer.setOutputProperty(OutputKeys.METHOD, "html");
                serializer.transform(domSource, streamResult);
                outStream.flush();

                String content = new String(outStream.toByteArray());
                FileUtil.writeToFile(htmlPath, content, false);

            } else {
                log.error("[" + excelPath + "]不支持excel版本");
                return;
            }
            log.info("[" + excelPath + "]源文件转换为[" + htmlPath + "]成功");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("[" + excelPath + "]源文件转换为[" + htmlPath + "]失败", e);
        } finally {
            if (null != input) {
                try {
                    input.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error("关闭输入流失败", e);
                }
            }
            if (null != outStream) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error("关闭输出流失败", e);
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error("关闭输出流失败", e);
                }
            }
        }
    }

    /**
     * ppt转换成html
     * @param pptPath    PPT文件的绝对路径
     * @param htmlPath    生成HTML文件的绝对路径
     */
    @SuppressWarnings({"resource", "restriction"})
    public void ppt2Html(String pptPath, String htmlPath) {
        if (null == pptPath || (null != pptPath && !(pptPath.toLowerCase().lastIndexOf(".ppt") > -1 || pptPath.toLowerCase().lastIndexOf(".pptx") > -1))) {
            log.error("[" + pptPath + "]源文件不能空且必须为ppt或pptx结尾");
            return;
        }
        if (null == htmlPath || (null != htmlPath && htmlPath.lastIndexOf(".html") == -1)) {
            log.error("[" + htmlPath + "]目标文件不能空且必须为html结尾");
            return;
        }
        pptPath = FileUtil.path(pptPath);
        htmlPath = FileUtil.path(htmlPath);
        String path = htmlPath.substring(0, htmlPath.lastIndexOf("/") + 1);
        String fileName = htmlPath.substring(htmlPath.lastIndexOf("/") + 1, htmlPath.lastIndexOf("."));
        String imagesPath = path + "images/";
        // 20181106 mody by chenyl for 生成的html代码内容修改为使用StringBuilder类
        StringBuilder html = new StringBuilder("<html>\n<meta charset=\"utf-8\">");
        InputStream input = null;
        ByteArrayOutputStream outStream = null;
        FileOutputStream out = null;
        FileOutputStream picOut = null;
        File pptFile = new File(pptPath);
        if (!pptFile.exists()) {
            log.error("[" + pptPath + "]源文件不存在");
            return;
        }
        try {
            FileUtil.createDirectory(path);
            FileUtil.createDirectory(imagesPath);
            // 处理ppt2007+以上版本的
            if (pptFile.getName().toLowerCase().endsWith(".pptx")) {
                //读取文档内容
                input = new FileInputStream(pptPath);
                XMLSlideShow ppt = new XMLSlideShow(input);
                Dimension pgsize = ppt.getPageSize();
                System.out.println(pgsize.width + "--" + pgsize.height);

                html.append("<table border='1' align='left'>\n");
                for (int i = 0; i < ppt.getSlides().size(); i++) {
                    html.append("\t<tr><td>第" + (i + 1) + "页PPT</td></tr>\n");
                    XSLFSlide slide = ppt.getSlides().get(i);
                    // 防止中文乱码
                    for (XSLFShape shape : slide.getShapes()) {
                        if (shape instanceof XSLFTextShape) {
                            XSLFTextShape tsh = (XSLFTextShape) shape;
                            for (XSLFTextParagraph p : tsh) {
                                for (XSLFTextRun r : p) {
                                    r.setFontFamily("宋体");
                                }
                            }
                        }
                    }

                    BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
                    Graphics2D graphics = img.createGraphics();
                    // clear the drawing area
                    graphics.setPaint(Color.BLUE);
                    graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));

                    // render
                    slide.draw(graphics);

                    // 这里设置图片的存放路径和图片的格式(jpeg,png,bmp等等),注意生成文件路径
                    String imgName = fileName + "_" + (i + 1) + ".jpeg";
                    try {
                        picOut = new FileOutputStream(imagesPath + imgName);
                        com.sun.image.codec.jpeg.JPEGImageEncoder encoder = com.sun.image.codec.jpeg.JPEGCodec.createJPEGEncoder(picOut);
                        encoder.encode(img);
                        picOut.flush();

                    } catch (IOException e) {
                System.out.println("出现异常");
            } catch (ImageFormatException e) {
                        e.printStackTrace();
                    } finally {
                        if (null != picOut) {
                            try {
                                picOut.close();
                            } catch (IOException e) {
                System.out.println("出现异常");
            }
                        }
                    }
//                    javax.imageio.ImageIO.write(img, "jpeg", picOut);
                    html.append("\t<tr><td><img src='./images/" + imgName + "' ></img></td></tr>\n");
                }
                html.append("</table>\n");

                FileUtil.writeToFile(htmlPath, html.toString(), "utf-8", false);

            } else if (pptFile.getName().toLowerCase().endsWith(".ppt")) {
                // 处理ppt2003版本的
                input = new FileInputStream(pptPath);
                HSLFSlideShow ppt = new HSLFSlideShow(input);
                Dimension pgsize = ppt.getPageSize();
                List<HSLFSlide> slide = ppt.getSlides();
                html.append("<table border='1' align='left'>\n");
                for (int i = 0; i < slide.size(); i++) {
                    html.append("\t<tr><td>第" + (i + 1) + "页PPT</td></tr>\n");
                    List<List<HSLFTextParagraph>> truns = slide.get(i).getTextParagraphs();
                    for (List<HSLFTextParagraph> rtruns : truns) {
                        for (HSLFTextParagraph tp : rtruns) {
                            tp.setBulletFont("宋体");
                        }
                    }

                    BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);

                    Graphics2D graphics = img.createGraphics();
                    graphics.setPaint(Color.BLUE);
                    graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
                    slide.get(i).draw(graphics);

                    // 这里设置图片的存放路径和图片的格式(jpeg,png,bmp等等),注意生成文件路径
                    String imgName = fileName + "_" + (i + 1) + ".jpeg";
                    picOut = new FileOutputStream(imagesPath + imgName);
                    com.sun.image.codec.jpeg.JPEGImageEncoder encoder = com.sun.image.codec.jpeg.JPEGCodec.createJPEGEncoder(picOut);
                    encoder.encode(img);
                    picOut.flush();
//                    javax.imageio.ImageIO.write(img, "jpeg", picOut);
                    html.append("\t<tr><td><img src='./images/" + imgName + "' ></img></td></tr>\n");

                }
                html.append("</table>\n");

                FileUtil.writeToFile(htmlPath, html.toString(), "utf-8", false);
            } else {
                log.error("[" + pptPath + "]不支持ppt版本");
                return;
            }
            log.info("[" + pptPath + "]源文件转换为[" + htmlPath + "]成功");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("[" + pptPath + "]源文件转换为[" + htmlPath + "]失败", e);
        } finally {
            if (null != input) {
                try {
                    input.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error("关闭输入流失败", e);
                }
            }
            if (null != outStream) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error("关闭输出流失败", e);
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error("关闭输出流失败", e);
                }
            }
            if (null != picOut) {
                try {
                    picOut.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error("关闭输出流失败", e);
                }
            }
        }
    }

    /**
     * 读取单元格
     *
     */
    private static String getCellValue(Cell cell, boolean treatAsStr) {
        if (cell == null) {
            return "";
        }

        if (treatAsStr) {
            cell.setCellType(CellType.STRING);
        }

        if (cell.getCellType() == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        } else {
            return String.valueOf(cell.getStringCellValue());
        }
    }


}
