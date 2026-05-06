package com.adtec.pay.web.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${adminPath}/proj_chk/page/")
public class ProjectChkPage {
    private static final String BASE_PATH = "starring/pay/project/proj_chk/";

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

    @RequestMapping("modify")
    public String modify() {
        return BASE_PATH + "update";
    }

    @RequestMapping("info")
    public String info() {
        return BASE_PATH + "info";
    }
}
