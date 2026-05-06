package com.adtec.pay.web.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${adminPath}/offline_refund/page/")
public class OfflineRefundPage {
    private static final String BASE_PATH = "starring/pay/offline/offline_refund/";

    @RequestMapping("index")
    public String index() {
        return BASE_PATH + "index";
    }

    @RequestMapping("list")
    public String list() {
        return BASE_PATH + "list";
    }

    @RequestMapping("info")
    public String info() {
        return BASE_PATH + "info";
    }
}
