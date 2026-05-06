package com.adtec.pay.web.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${adminPath}/offline_merRegister/page/")
public class OfflineMerRegPage {
    private static final String BASE_PATH = "starring/pay/offline/offline_merRegister/";

    @RequestMapping("register")
    public String index() {
        return BASE_PATH + "register";
    }
}
