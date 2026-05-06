package com.adtec.scheduler.web;

import com.adtec.framework.common.functions.FuncP;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.ServerResponse;
import com.adtec.framework.common.util.StringUtil;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.db.handler.ExceptionHandler;
import com.adtec.framework.interfaces.db.handler.TxHandler;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.scheduler.core.util.CronUtil;
import com.adtec.scheduler.core.util.ScheduleUtil;
import com.adtec.scheduler.dto.ScheduleServerDTO;
import com.adtec.scheduler.entity.ScheduleJobDO;
import com.adtec.scheduler.entity.ScheduleJobLogDO;
import com.adtec.scheduler.service.ScheduleJobService;
import com.adtec.sys.common.utils.Encodes;
import com.adtec.sys.common.web.BaseController;
import org.apache.taglibs.standard.functions.Functions;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.List;

/**
 * @author lijunbin
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/schedule")
public class SchedulerController extends BaseController {
	
	private static final ExceptionHandler EXCEPTION_HANDLER = new ExceptionHandler() {
		
		@Override
		public void apply(IDBSession session, Exception ex) {
			// TODO Auto-generated method stub
			
		}
	};

    /**
     * 首页页面路径
     */
    private final String PATH = "starring/scheduler/schedule";

    private final ScheduleJobService scheduleJobService;

    public SchedulerController(ScheduleJobService scheduleJobService) {
        this.scheduleJobService = scheduleJobService;
    }

    /**
     * 管理页面
     *
     * @param request  /
     * @param response /
     * @return /
     */
    @RequestMapping(value = {"managePage"})
    public String managePage(HttpServletRequest request, HttpServletResponse response) {
        return PATH + "Manage";
    }

    /**
     * 列表页面
     *
     * @param request  /
     * @param response /
     * @return /
     */
    @RequestMapping(value = {"listPage"})
    public String listPage(HttpServletRequest request, HttpServletResponse response) {
        return PATH + "List";
    }

    /**
     * 新增页面
     *
     * @param request  /
     * @param response /
     * @return /
     */
    @RequestMapping(value = {"addPage"})
    public String addPage(HttpServletRequest request, HttpServletResponse response) {
        return PATH + "AddForm";
    }

    /**
     * 修改页面
     *
     * @param request  /
     * @param response /
     * @return /
     */
    @RequestMapping(value = {"updatePage"})
    public String uptPage(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        ScheduleJobDO scheduleJobDO = DatasetService.getInstace().getObject(reqDs, ScheduleJobDO.class);
        scheduleJobDO = ScheduleUtil.get(scheduleJobDO.getBeanName());
        scheduleJobDO.setUseCron(DataUtil.isNullStr(scheduleJobDO.getCronExpr()) ? "0" : "1");
        request.setAttribute("s", scheduleJobDO);
        return PATH + "UpdateForm";
    }

    /**
     * 日志列表
     *
     * @param request  /
     * @param response /
     * @return /
     */
    @RequestMapping(value = {"logPage"})
    public String logPage(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        ScheduleJobDO scheduleJobDO = DatasetService.getInstace().getObject(reqDs, ScheduleJobDO.class);
        request.setAttribute("beanName", scheduleJobDO.getBeanName());
        return PATH + "Log";
    }

    /**
     * 查询自动任务列表
     *
     * @param request  /
     * @param response /
     */
    @RequestMapping(value = {"list"})
    public void list(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        DatasetService.printDataset(reqDs);
        int start = reqDs.getInt("start");
        int limit = reqDs.getInt("pageSize");
        String condition = reqDs.getString("condition");
        ScheduleJobDO scheduleJobDO = new ScheduleJobDO();
        if (!DataUtil.isNullStr(condition)) {
            scheduleJobDO.setBeanName(condition);
            scheduleJobDO.setName(condition);
        }
        List<ScheduleJobDO> scheduleJobs = scheduleJobService.list(scheduleJobDO, start, limit);
        IDataset responseData = DatasetService.getInstace().getDataset(scheduleJobs, ScheduleJobDO.class);
        responseData.setTotalCount(scheduleJobService.getTotalCount(scheduleJobDO));
        setResponseDataset(request, response, responseData, SysErr.E_SUCCESS, "查询成功！");
    }

    /**
     * 保存自动任务
     *
     * @param request  /
     * @param response /
     */
    @RequestMapping(value = {"insert"})
    public void insert(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        DatasetService.printDataset(reqDs);
        final ScheduleJobDO scheduleJobDO = DatasetService.getInstace().getObject(reqDs, ScheduleJobDO.class);
        DBSessionFactory.use().txAutoRollback(new TxHandler() {
			@Override
			public void call(IDBSession session) throws Exception {
				scheduleJobService.insert(scheduleJobDO);
			}
		}, EXCEPTION_HANDLER);
        
        IDataset dataset = DatasetService.getInstace().getDataset();
        setResponseDataset(request, response, dataset, SysErr.E_SUCCESS, "保存成功");
    }

    /**
     * 修改自动任务
     *
     * @param request  /
     * @param response /
     */
    @RequestMapping(value = {"update"})
    public void update(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        DatasetService.printDataset(reqDs);
        final ScheduleJobDO scheduleJobDO = DatasetService.getInstace().getObject(reqDs, ScheduleJobDO.class);
        DBSessionFactory.use().txAutoRollback(new TxHandler() {
			@Override
			public void call(IDBSession session) throws Exception {
				scheduleJobService.update(scheduleJobDO);
			}
		}, EXCEPTION_HANDLER);
        
        IDataset dataset = DatasetService.getInstace().getDataset();
        setResponseDataset(request, response, dataset, SysErr.E_SUCCESS, "保存成功");
    }

    /**
     * 删除自动任务
     *
     * @param request  /
     * @param response /
     */
    @RequestMapping(value = {"delete"})
    public void delete(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        DatasetService.printDataset(reqDs);
        final String beanName = reqDs.getString("beanName");
        DBSessionFactory.use().txAutoRollback(new TxHandler() {
			@Override
			public void call(IDBSession session) throws Exception {
				scheduleJobService.delete(beanName);
			}
		}, EXCEPTION_HANDLER);
        
        IDataset dataset = DatasetService.getInstace().getDataset();
        setResponseDataset(request, response, dataset, SysErr.E_SUCCESS, "删除成功");
    }

    /**
     * 执行一次自动任务
     *
     * @param request  /
     * @param response /
     */
    @RequestMapping(value = {"execOnce"})
    public void execOnce(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        DatasetService.printDataset(reqDs);
        String beanName = reqDs.getString("beanName");
        scheduleJobService.execOnce(beanName);
        IDataset dataset = DatasetService.getInstace().getDataset();
        setResponseDataset(request, response, dataset, SysErr.E_SUCCESS, "正在执行");
    }

    /**
     * 更改自动任务状态
     *
     * @param request  /
     * @param response /
     */
    @RequestMapping(value = {"chgStatus"})
    public void chgStatus(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        DatasetService.printDataset(reqDs);
        String beanName = reqDs.getString("beanName");
        scheduleJobService.chgStatus(beanName);
        IDataset dataset = DatasetService.getInstace().getDataset();
        setResponseDataset(request, response, dataset, SysErr.E_SUCCESS, "修改状态成功");
    }

    /**
     * 获取自动任务详情
     *
     * @param beanName /
     * @return /
     */
    @ResponseBody
    @RequestMapping(value = {"getDetail"})
    public ServerResponse<ScheduleJobDO> getDetail(String beanName) {
        ScheduleJobDO scheduleJobDO = ScheduleUtil.get(beanName);
        if (null != scheduleJobDO) {
            scheduleJobDO.setUseCron(DataUtil.isNullStr(scheduleJobDO.getCronExpr()) ? "0" : "1");
            return ServerResponse.createBySuccess("查询成功", scheduleJobDO);
        }
        return ServerResponse.createBySuccessMessage("查询失败");
    }

    /**
     * 获取自动任务日志
     *
     * @param request  /
     * @param response /
     */
    @RequestMapping(value = {"getLog"})
    public void getLog(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        DatasetService.printDataset(reqDs);
        String beanName = reqDs.getString("beanName");
        int limit, start = 0;
        int startIndex = reqDs.findColumn("start");
        int limitIndex = reqDs.findColumn("pageSize");
        if (startIndex == 0 && limitIndex == 0) {
            limit = 5;
        } else {
            start = reqDs.getInt("start");
            limit = reqDs.getInt("pageSize");
        }
        ScheduleJobLogDO qryDO = DatasetService.getInstace().getObject(reqDs, ScheduleJobLogDO.class);
        List<ScheduleJobLogDO> scheduleJobLogs = scheduleJobService.getLog(qryDO, start, limit);
        for (ScheduleJobLogDO scheduleJobLogDO : scheduleJobLogs) {
            if (null != scheduleJobLogDO.getErrMsgByteData()) {
                String s = StringUtil.toString(scheduleJobLogDO.getErrMsgByteData());
                String replace = Functions.replace(Encodes.escapeHtml(s), "\n", "<br/>");
                scheduleJobLogDO.setErrMsgStr(replace);
            } else {
                scheduleJobLogDO.setErrMsgByteData("".getBytes());
            }
        }
        IDataset responseData = DatasetService.getInstace().getDataset(scheduleJobLogs, ScheduleJobLogDO.class);
        responseData.setTotalCount(scheduleJobService.getTotalLog(qryDO));
        setResponseDataset(request, response, responseData, SysErr.E_SUCCESS, "查询成功！");
    }

    /**
     * 获取自动任务最近五次执行时间
     *
     * @param request  /
     * @param response /
     * @return /
     */
    @ResponseBody
    @RequestMapping(value = {"getNextExecTime"})
    public ServerResponse<List<String>> getNextExecTime(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        DatasetService.printDataset(reqDs);
        String cronExpr = reqDs.getString("cronExpr");
        List<String> list = CronUtil.getNextExecTimeStr(cronExpr, 5);
        if (CollectionUtils.isEmpty(list)) {
            return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "表达式错误");
        }
        return ServerResponse.createBySuccess("查询成功", list);
    }

    /**
     * 获取调度服务节点列表.
     *
     * @return /
     */
    @RequestMapping(value = {"getScheduleServers"})
    public void getScheduleServers(HttpServletRequest request, HttpServletResponse response) {
        List<ScheduleServerDTO> servers = scheduleJobService.getScheduleServers();
        IDataset responseData = DatasetService.getInstace().getDataset(servers, ScheduleServerDTO.class);
        responseData.setTotalCount(servers.size());
        setResponseDataset(request, response, responseData, SysErr.E_SUCCESS, "查询成功！");
    }

}
