package com.adtec.pay.web.page.ykt;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${adminPath}/ykt_mer_mng/page/")
public class YKTMerMngPage {
    private static final String BASE_PATH = "starring/pay/ykt/ykt_mer_mng/";

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

    @RequestMapping("delete")
    public String delete() {
        return BASE_PATH + "delete";
    }
}
