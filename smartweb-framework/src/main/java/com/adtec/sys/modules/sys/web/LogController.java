/**
 *
 */
package com.adtec.sys.modules.sys.web;

import com.adtec.sys.common.persistence.Page;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.modules.sys.entity.Log;
import com.adtec.sys.modules.sys.service.LogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 日志Controller
 *
 * @version 2016-3-5
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/log")
public class LogController extends BaseController {

    private final LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    @RequiresPermissions("sys:log:view")
    @RequestMapping(value = {"list", ""})
    public String list(final Log log, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Log> page = logService.findPage(new Page<Log>(request, response), log);
        model.addAttribute("page", page);
        return "modules/sys/logList";
    }

}
