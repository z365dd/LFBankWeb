package com.adtec.pay.web.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${adminPath}/school_register/page/")
public class SchoolRegisterPage {
    private static final String BASE_PATH = "starring/pay/offline/school_register/";

    @RequestMapping("index")
    public String index() {
        return BASE_PATH + "index";
    }

    @RequestMapping("list")
    public String list() {
        return BASE_PATH + "list";
    }

    @RequestMapping("add")
    public String add() {
        return BASE_PATH + "add";
    }

    @RequestMapping("update")
    public String update() {
        return BASE_PATH + "update";
    }
}
