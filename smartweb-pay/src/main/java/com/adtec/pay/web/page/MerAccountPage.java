package com.adtec.pay.web.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${adminPath}/merAccount/page/")
public class MerAccountPage {
    private static final String BASE_PATH = "starring/pay/life/merAccount/";

    @RequestMapping("index")
    public String index() {
        return BASE_PATH + "index";
    }

    /**
     * 给行方使用的商户入账页面
     *
     * @return
     */
    @RequestMapping("indexForAdmin")
    public String indexForAdmin() {
        return BASE_PATH + "indexForAdmin";
    }

    @RequestMapping("list")
    public String list() {
        return BASE_PATH + "list";
    }


    @RequestMapping("listForAdmin")
    public String listForAdmin() {
        return BASE_PATH + "listForAdmin";
    }

    @RequestMapping("info")
    public String info() {
        return BASE_PATH + "info";
    }
}
