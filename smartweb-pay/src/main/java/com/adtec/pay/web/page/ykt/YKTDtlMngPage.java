package com.adtec.pay.web.page.ykt;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${adminPath}/ykt_dtl_mng/page/")
public class YKTDtlMngPage {
    private static final String BASE_PATH = "starring/pay/ykt/ykt_dtl_mng/";

    @RequestMapping("index")
    public String index() {
        return BASE_PATH + "index";
    }

    @RequestMapping("list")
    public String list() {
        return BASE_PATH + "list";
    }

    @RequestMapping("update")
    public String update() {
        return BASE_PATH + "update";
    }

    @RequestMapping("info")
    public String info() { return BASE_PATH + "info";
    }
}
