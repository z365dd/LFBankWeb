package com.adtec.pay.web.page.ykt;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${adminPath}/ykt_mer_account/page/")
public class YKTMerAccountPage {
    private static final String BASE_PATH = "starring/pay/ykt/ykt_mer_account/";

    @RequestMapping("index")
    public String index() {
        return BASE_PATH + "index";
    }

    @RequestMapping("list")
    public String list() {
        return BASE_PATH + "list";
    }


}
