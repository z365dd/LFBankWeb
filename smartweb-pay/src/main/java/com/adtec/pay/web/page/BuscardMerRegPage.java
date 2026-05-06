package com.adtec.pay.web.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${adminPath}/buscard_merRegister/page/")
public class BuscardMerRegPage {
    private static final String BASE_PATH = "starring/pay/buscard/buscard_merRegister/";

    @RequestMapping("register")
    public String index() {
        return BASE_PATH + "register";
    }
}
