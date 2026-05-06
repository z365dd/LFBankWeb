package com.adtec.pay.web.page.union;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
    @RequestMapping("${adminPath}/union_user/page/")
public class UnionUserMngPage {
    private static final String BASE_PATH = "starring/pay/union/union_user/";

    @RequestMapping("index")
    public String index() {
        return BASE_PATH + "index";
    }

    @RequestMapping("appr")
    public String appr() {
        return BASE_PATH + "appr";
    }

    @RequestMapping("add")
    public String add() {
        return BASE_PATH + "add";
    }

    @RequestMapping("list")
    public String list() {
        return BASE_PATH + "list";
    }

    @RequestMapping("update")
    public String update() {
        return BASE_PATH + "update";
    }
}
