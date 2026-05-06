//import com.adtec.pay.utils.pdf.PdfGenerator;

import java.io.File;
import java.util.*;

public class HtmlToPdf {


    public static void main(String[] args) {
        Map<String, String> values = new HashMap<>();

        List<Map<String,String>> payList = new ArrayList<>();
        Map<String, String> item1 = new HashMap<>();
        item1.put("project", "学生书费");
        item1.put("period", "2024年高一学生书费");
        item1.put("amount", "50");
        item1.put("time", "20250212");
        item1.put("stat", "已缴费");
        payList.add(item1);
//        values.put("payList", payList);
//        PdfGenerator.createPdf("template.html", values, "htmlToPdf.pdf");

    }


}
