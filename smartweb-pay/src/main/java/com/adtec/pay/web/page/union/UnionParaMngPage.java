package com.adtec.pay.web.page.union;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${adminPath}/union_para/page/")
public class UnionParaMngPage {
    private static final String BASE_PATH = "starring/pay/union/union_para/";

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

    @RequestMapping("update")
    public String update() {
        return BASE_PATH + "update";
    }
}
