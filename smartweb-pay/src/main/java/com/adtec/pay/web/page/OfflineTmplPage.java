package com.adtec.pay.web.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${adminPath}/offline_tmpl/page/")
public class OfflineTmplPage {
    private static final String BASE_PATH = "starring/pay/offline/offline_tmpl/";

    @RequestMapping("index")
    public String index() {
        return BASE_PATH + "index";
    }

    @RequestMapping("add")
    public String add() {
        return BASE_PATH + "add";
    }

    @RequestMapping("list")
    public String list() {
        return BASE_PATH + "list";
    }

}
