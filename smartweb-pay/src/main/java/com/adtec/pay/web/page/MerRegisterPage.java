package com.adtec.pay.web.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${adminPath}/merRegister/page/")
public class MerRegisterPage {

    private static final String BASE_PATH = "starring/pay/life/merRegister/";

    @RequestMapping("register")
    public String index() {
        return BASE_PATH + "register";
    }
}
