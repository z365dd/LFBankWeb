package com.adtec.pay.web.page;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${adminPath}/buscardUnconfmQry/page/")
public class BuscardUnconfmQryPage {
    private static final String BASE_PATH = "starring/pay/buscard/buscard_unconfm_qry/";

    @RequestMapping("index")
    public String index() {
        return BASE_PATH + "index";
    }

    @RequestMapping("list")
    public String list() {
        return BASE_PATH + "list";
    }
}
