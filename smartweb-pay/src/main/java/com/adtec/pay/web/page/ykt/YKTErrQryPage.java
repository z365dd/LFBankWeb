package com.adtec.pay.web.page.ykt;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${adminPath}/ykt_err_qry/page/")
public class YKTErrQryPage {
    private static final String BASE_PATH = "starring/pay/ykt/ykt_err_qry/";

    @RequestMapping("index")
    public String index() {
        return BASE_PATH + "index";
    }

    @RequestMapping("list")
    public String list() {
        return BASE_PATH + "list";
    }

}
