package com.adtec.pay.web.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${adminPath}/buscardErrQry/page/")
public class BuscardErrQryPage {
    private static final String BASE_PATH = "starring/pay/buscard/buscard_err_qry/";

    @RequestMapping("index")
    public String index() {
        return BASE_PATH + "index";
    }

    @RequestMapping("list")
    public String list() {
        return BASE_PATH + "list";
    }

    @RequestMapping("info")
    public String detail() {
        return BASE_PATH + "info";
    }
}
