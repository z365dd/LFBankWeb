/*
package com.adtec.pay.utils;
import com.adtec.pay.dto.offline.pdf.ReportData;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.util.Locale;

public class PdfGenerator {

    // 生成PDF主方法
    public static void generatePdf(ReportData data, String outputPath) throws Exception {
        // 1. 渲染HTML模板
        String htmlContent = renderHtmlTemplate(data);

        // 2. 转换HTML为PDF
        try (FileOutputStream os = new FileOutputStream(outputPath)) {
            ITextRenderer renderer = new ITextRenderer();

            // 解决中文显示问题（需引入字体）
            renderer.getFontResolver().addFont(
                    "static/simhei.ttf",
                    BaseFont.IDENTITY_H,
                    BaseFont.NOT_EMBEDDED
            );

            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(os);
        }
    }

    // 使用Thymeleaf渲染模板
    private static String renderHtmlTemplate(ReportData data) {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("/templates/");    // 模板存放目录
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        Context context = new Context(Locale.CHINA);
        context.setVariable("data", data);

        return engine.process("template", context); // 模板文件名（不含后缀）
    }
}
*/
