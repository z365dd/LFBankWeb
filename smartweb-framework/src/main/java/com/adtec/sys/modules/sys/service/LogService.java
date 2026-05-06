package com.adtec.sys.modules.sys.service;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adtec.framework.common.util.DateUtil;
import com.adtec.sys.common.persistence.Page;
import com.adtec.sys.common.service.BaseService;
import com.adtec.sys.modules.sys.dao.LogDao;
import com.adtec.sys.modules.sys.entity.Log;
import com.adtec.sys.modules.sys.entity.Role;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.utils.UserUtils;

import java.util.List;

/**
 * 日志Service
 *
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class LogService {
    @Autowired
    private LogDao dao;

    /**
     * @param page 分页对象
     * @param log
     * @return
     * @author chenyl
     * @date 2018-09-28
     */
    public Page<Log> findPage(Page<Log> page,final Log log) {
        User user = UserUtils.getUser();
        // 设置默认时间范围，默认当前月
        if (log.getBeginDate() == null) {
            log.setBeginDate(DateUtil.setDays(DateUtil.parseDate(DateUtil.getDate2()), 1));
        }
        if (log.getEndDate() == null) {
            log.setEndDate(DateUtil.addMonths(log.getBeginDate(), 1));
        }
        //超级管理员可查看全部日志
        if (user.isAdmin()) {
            log.setPage(page);
            List<Log> list = (List<Log>) UserUtils.getCache("logList");
            // list为空，或者查询第一页
            if (list == null || page.getPageNo() == 1) {
                list = dao.findList(log);
                UserUtils.putCache("logList", list);
            }
            page.setCount(list.size());
            int fromIndex = (page.getPageNo() - 1) * page.getPageSize();
            int toIndex = page.getPageNo() * page.getPageSize();
            List<Log> logList = list.subList(fromIndex, toIndex > (int) page.getCount() ? (int) page.getCount() : toIndex);
            page.setList(logList);
            return page;
        }
        //普通管理员可查看所在机构以及下属机构日志
        for (Role role : user.getRoleList()) {
            List<Object> filterParams = Lists.newArrayList();
            log.getSqlMap().put("dsf", BaseService.dataScpFilterOffice(user, "o", "", filterParams));
            log.setPage(page);
            List<Log> list = (List<Log>) UserUtils.getCache("logList");
            // list为空，或者查询第一页
            if (list == null || page.getPageNo() == 1) {
                list = dao.findList(log, filterParams);
                UserUtils.putCache("logList", list);
            }
            page.setCount(list.size());
            int fromIndex = (page.getPageNo() - 1) * page.getPageSize();
            int toIndex = page.getPageNo() * page.getPageSize();
            List<Log> logList = list.subList(fromIndex, toIndex > (int) page.getCount() ? (int) page.getCount() : toIndex);
            page.setList(logList);
            return page;
        }
        //普通用户仅能查看自己日志
        List<Object> filterParams = Lists.newArrayList();
        log.getSqlMap().put("dsf", BaseService.dataScpFilterOffice(user, "", "u", filterParams));
        log.setPage(page);
        List<Log> list = (List<Log>) UserUtils.getCache("logList");
        // list为空，或者查询第一页
        if (list == null || page.getPageNo() == 1) {
            list = dao.findList(log, filterParams);
            UserUtils.putCache("logList", list);
        }
        page.setCount(list.size());
        int fromIndex = (page.getPageNo() - 1) * page.getPageSize();
        int toIndex = page.getPageNo() * page.getPageSize();
        List<Log> logList = list.subList(fromIndex, toIndex > (int) page.getCount() ? (int) page.getCount() : toIndex);
        page.setList(logList);
        return page;
    }

}
