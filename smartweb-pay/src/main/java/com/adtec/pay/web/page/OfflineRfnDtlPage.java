package com.adtec.pay.web.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${adminPath}/offline_rfn_dtl/page/")
public class OfflineRfnDtlPage {
    private static final String BASE_PATH = "starring/pay/offline/offline_rfn_dtl/";

    @RequestMapping("index")
    public String index() {
        return BASE_PATH + "index";
    }

    @RequestMapping("list")
    public String list() {
        return BASE_PATH + "list";
    }

}
