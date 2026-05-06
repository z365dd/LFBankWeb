package com.adtec.scheduler.service;

import static com.adtec.framework.common.constant.Constants.Scheduler.DELETE;
import static com.adtec.framework.common.constant.Constants.Scheduler.INSERT;
import static com.adtec.framework.common.constant.Constants.Scheduler.LAUNCH;
import static com.adtec.framework.common.constant.Constants.Scheduler.STOP;
import static com.adtec.framework.common.constant.Constants.Scheduler.UPDATE;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.adtec.framework.common.constant.Constants.Scheduler.CronUnit;
import com.adtec.framework.common.constant.Constants.Scheduler.Status;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.scheduler.core.event.ScheduleChangeEvent;
import com.adtec.scheduler.core.event.ScheduleExecOnceEvent;
import com.adtec.scheduler.core.handle.IJobHandle;
import com.adtec.scheduler.core.model.ScheduleJobZkDTO;
import com.adtec.scheduler.core.model.ScheduleServerZkDTO;
import com.adtec.scheduler.core.monitor.ScheduleMonitor;
import com.adtec.scheduler.core.util.CronUtil;
import com.adtec.scheduler.core.util.ScheduleUtil;
import com.adtec.scheduler.dao.ScheduleJobDao;
import com.adtec.scheduler.dao.ScheduleJobLogDao;
import com.adtec.scheduler.entity.ScheduleJobDO;
import com.adtec.scheduler.entity.ScheduleJobLogDO;
import com.adtec.scheduler.event.TaskSaveLogEvent;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.google.common.base.Optional;

/**
 * @author lijunbin
 */
@Service("schedulerService")
public class ScheduleJobService {

    private static final transient Logger LOG = LoggerFactory.getLogger(ScheduleJobService.class);

    private final ScheduleJobDao scheduleJobDao;
    private final ScheduleJobLogDao scheduleJobLogDao;
    private final ScheduleMonitor scheduleMonitor;

    public ScheduleJobService(ScheduleJobDao scheduleJobDao, ScheduleJobLogDao scheduleJobLogDao, ScheduleMonitor scheduleMonitor) {
        super();
        this.scheduleJobDao = scheduleJobDao;
        this.scheduleJobLogDao = scheduleJobLogDao;
        this.scheduleMonitor = scheduleMonitor;
    }

    public int insert(ScheduleJobDO scheduleJobDO) {
        int rs = 0;
        try {
            if (null != ScheduleUtil.get(scheduleJobDO.getBeanName())) {
                throw new BaseException(SysErr.E_MESSAGE, "处理类[" + scheduleJobDO.getBeanName() + "]已有自动任务在使用");
            }
            Class<?> handleClass = Class.forName(scheduleJobDO.getBeanName());
            Object handleClassInstance = handleClass.newInstance();
            if (handleClassInstance instanceof IJobHandle) {
                scheduleJobDO.preInsert();
                if (!DataUtil.isNullStr(scheduleJobDO.getCronExpr())) {
                    if ("0".equals(scheduleJobDO.getPlanExecMeth())) {
                        scheduleJobDO.setNumKv("1");
                    }
                    if (CronUnit.ONCE.equals(scheduleJobDO.getTimeUnitTp())) {
                        scheduleJobDO.setIntvlTime("1");
                    }
                }
                if (DataUtil.isNullStr(scheduleJobDO.getCronExpr())) {
                    scheduleJobDO.setCronExpr("");
                }
                // cron表达式改为前端手动输入
                // scheduleJobDO.setCronExpr(CronUtil.generateCron(scheduleJobDO));
                rs = scheduleJobDao.insert(scheduleJobDO);
                Date invlTime = DateUtil.string2Date(scheduleJobDO.getInvlTime(), CronUtil.TRANS_DATE_FMT);
                if (rs > 0 && (invlTime.getTime() > System.currentTimeMillis())) {
                    if (!SpringContextHolder.containsBean(ScheduleUtil.processBeanName(handleClass.getSimpleName()))) {
                        ScheduleUtil.registerBean(handleClass);
                    }
                    scheduleJobDO.setListenerType(INSERT);
                    this.trigger(scheduleJobDO);
                }
            } else {
                throw new BaseException(SysErr.E_MESSAGE, "自动任务处理类必须实现com.adtec.scheduler.core.handle.IJobHandle接口");
            }
        } catch (ClassNotFoundException e) {
            throw new BaseException(SysErr.E_MESSAGE, String.format("类[%s]不存在", scheduleJobDO.getBeanName()));
        } catch (IllegalAccessException e) {
            throw new BaseException(SysErr.E_MESSAGE, "构造方法权限为private，无权限实例化！" + e.getMessage());
        } catch (InstantiationException e) {
            throw new BaseException(SysErr.E_MESSAGE, "实例化对象失败！" + e.getMessage());
        }
        return rs;
    }

    private void trigger(ScheduleJobDO scheduleJobDO) {
        ScheduleJobZkDTO scheduleJobZkDTO = new ScheduleJobZkDTO();
        scheduleJobZkDTO.setBeanName(scheduleJobDO.getBeanName());
        if (ScheduleUtil.checkClusterFlag()) {
            switch (scheduleJobDO.getListenerType()) {
                case INSERT:
                    if (scheduleJobDO.statusRunning()) {
                        scheduleMonitor.getScheduleJobManager().save(scheduleJobZkDTO);
                        ScheduleUtil.put(scheduleJobDO.getBeanName(), scheduleJobDO);
                    }
                    break;
                case DELETE:
                    scheduleMonitor.getScheduleJobManager().delete(scheduleJobZkDTO);
                    ScheduleUtil.remove(scheduleJobDO.getBeanName());
                    break;
                case UPDATE:
                case LAUNCH:
                	if (scheduleJobDO.statusRunning()) {
                        if (!scheduleMonitor.getScheduleJobManager().isExist(scheduleJobZkDTO)) {
                            scheduleMonitor.getScheduleJobManager().save(scheduleJobZkDTO);
                        } else {
                            if (scheduleMonitor.getScheduleServerManager().isOwner(scheduleJobDO.getBeanName(), ScheduleServerZkDTO.getInstance().getUuid())) {
                                SpringContextHolder.publishEvent(new ScheduleChangeEvent(scheduleJobDO));
                            }
                        }
                    } else {
                        scheduleMonitor.getScheduleJobManager().delete(scheduleJobZkDTO);
                    }
                    ScheduleUtil.put(scheduleJobDO.getBeanName(), scheduleJobDO);
                    break;
                case STOP:
                    if (scheduleJobDO.statusRunning()) {
                        if (!scheduleMonitor.getScheduleJobManager().isExist(scheduleJobZkDTO)) {
                            scheduleMonitor.getScheduleJobManager().save(scheduleJobZkDTO);
                        } else {
                            if (scheduleMonitor.getScheduleServerManager().isOwner(scheduleJobDO.getBeanName(), ScheduleServerZkDTO.getInstance().getUuid())) {
                                SpringContextHolder.publishEvent(new ScheduleChangeEvent(scheduleJobDO));
                            }
                        }
                    } else {
                        scheduleMonitor.getScheduleJobManager().delete(scheduleJobZkDTO);
                    }
                    ScheduleUtil.remove(scheduleJobDO.getBeanName());
                    break;
                default:
                    break;
            }
        } else {
            SpringContextHolder.publishEvent(new ScheduleChangeEvent(scheduleJobDO));
        }
    }

    public List<ScheduleJobDO> list(ScheduleJobDO scheduleJobDO, int start, int limit) {
        List<ScheduleJobDO> list = scheduleJobDao.list(scheduleJobDO, start, limit);
        buildAction(list);
        return list;
    }

    public int getTotalCount(ScheduleJobDO scheduleJobDO) {
        return scheduleJobDao.getTotal(scheduleJobDO);
    }

    public int delete(String beanName) {
        ScheduleJobDO scheduleJobDO = ScheduleUtil.get(beanName);
        int rs = scheduleJobDao.delete(scheduleJobDO);
        if (rs > 0) {
            try {
                Class<?> handleClass = Class.forName(scheduleJobDO.getBeanName());
                if (SpringContextHolder.containsBean(ScheduleUtil.processBeanName(handleClass.getSimpleName()))) {
                    ScheduleUtil.removeBeanDefinition(handleClass);
                }
                ScheduleUtil.remove(beanName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            scheduleJobDO.setListenerType(DELETE);
            this.trigger(scheduleJobDO);
        }
        return rs;
    }

    private void buildAction(List<ScheduleJobDO> scheduleJobs) {
        for (ScheduleJobDO scheduleJobDO : scheduleJobs) {
            StringBuilder action = new StringBuilder();
            action.append("<a href=\"#\" onClick=\"action('").append(scheduleJobDO.getBeanName()).append("','update')\">修改</a>");
            action.append(" <a href=\"#\" onClick=\"action('").append(scheduleJobDO.getBeanName()).append("','delete')\">删除</a>");
            Date invlTime = DateUtil.string2Date(scheduleJobDO.getInvlTime(), CronUtil.TRANS_DATE_FMT);
            if (invlTime.getTime() > System.currentTimeMillis()) {
                if (Status.RUN.equals(scheduleJobDO.getOpenSwitchFlg())) {
                    action.append(" <a href=\"#\" onClick=\"action('").append(scheduleJobDO.getBeanName())
                            .append("','chgStatus')\">停用</a>");
                    scheduleJobDO.setStatStr("已启用");
                } else {
                    action.append(" <a href=\"#\" onClick=\"action('").append(scheduleJobDO.getBeanName())
                            .append("','chgStatus')\">启用</a>");
                    scheduleJobDO.setStatStr("已停用");
                }
            } else {
                scheduleJobDO.setStatStr("已失效");
            }
            action.append(" <a href=\"#\" onClick=\"action('").append(scheduleJobDO.getBeanName())
                    .append("','execOnce')\">立刻执行一次</a>");
            action.append(" <a href=\"#\" onClick=\"action('").append(scheduleJobDO.getBeanName())
                    .append("','logDetail')\">查看日志</a>");
            scheduleJobDO.setAction(action.toString());
        }
    }

    public List<ScheduleJobDO> queryTasks() {
        return scheduleJobDao.queryTasks();
    }

    public Optional<ScheduleJobDO> getDetail(String id) {
        ScheduleJobDO scheduleJobDO = scheduleJobDao.get(id);
        return Optional.fromNullable(scheduleJobDO);
    }

    /**
     * 执行一次任务
     *
     * @param beanName 任务ID
     */
    public void execOnce(String beanName) {
        ScheduleJobDO scheduleJobDO = ScheduleUtil.get(beanName);
        final User user = UserUtils.getUser();
        if (!DataUtil.isNullStr(user.getLoginName())) {
            scheduleJobDO.setOperUsername(user.getLoginName());
        }
        SpringContextHolder.publishEvent(new ScheduleExecOnceEvent(scheduleJobDO));
    }

    public void chgStatus(String beanName) {
        ScheduleJobDO scheduleJobDO = ScheduleUtil.get(beanName);
        if (scheduleJobDO.statusRunning()) {
            scheduleJobDO.setOpenSwitchFlg(Status.STOP);
            scheduleJobDO.setListenerType(STOP);
        } else {
            scheduleJobDO.setOpenSwitchFlg(Status.RUN);
            scheduleJobDO.setListenerType(LAUNCH);
        }
        
        IDBSession session = DBSessionFactory.getSession();
        try{
        	session.beginTransaction();
            scheduleJobDO.preUpdate();
            int rs = scheduleJobDao.update(scheduleJobDO);
            if (rs > 0) {
                this.trigger(scheduleJobDO);
            }
        	session.endTransaction();
        }catch(Exception e){
        	LOG.error("更改状态异常", e);
        	try {
				session.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }finally{
        	try {
				DBSessionFactory.closeSession(session);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    public void update(ScheduleJobDO scheduleJobDO) {
        ScheduleJobDO scheduleInDb = scheduleJobDao.get(scheduleJobDO);
        scheduleJobDO.preUpdate();
        scheduleJobDO.setCrtr(scheduleInDb.getCrtr());
        scheduleJobDO.setCrtTime(scheduleInDb.getCrtTime());
        if (DataUtil.isNullStr(scheduleJobDO.getCronExpr())) {
            scheduleJobDO.setCronExpr("");
        }
        if ("0".equals(scheduleJobDO.getPlanExecMeth())) {
            scheduleJobDO.setNumKv("1");
        }
        int rs = scheduleJobDao.update(scheduleJobDO);
        // 更新缓存
        ScheduleUtil.put(scheduleJobDO.getBeanName(), scheduleJobDO);
        Date invlTime = DateUtil.string2Date(scheduleJobDO.getInvlTime(), CronUtil.TRANS_DATE_FMT);
        if (rs > 0 && invlTime.getTime() > System.currentTimeMillis()) {
            scheduleJobDO.setListenerType(UPDATE);
            this.trigger(scheduleJobDO);
        }
    }

    @EventListener
    public void saveTaskLog(TaskSaveLogEvent taskSaveLogEvent) {
        ScheduleJobLogDO scheduleJobLogDO = (ScheduleJobLogDO) taskSaveLogEvent.getSource();
        scheduleJobLogDO.preInsert();
        scheduleJobLogDao.insertLog(scheduleJobLogDO);
    }

    public List<ScheduleJobLogDO> getLog(ScheduleJobLogDO scheduleJobLogDO, int start, int limit) {
        List<ScheduleJobLogDO> logs = scheduleJobLogDao.getLog(scheduleJobLogDO, start, limit);
        return CollectionUtils.isEmpty(logs) ? new ArrayList<ScheduleJobLogDO>() : logs;
    }


    public int getTotalLog(ScheduleJobLogDO scheduleJobLogDO) {
        return scheduleJobLogDao.getTotal(scheduleJobLogDO);
    }

    public List<com.adtec.scheduler.dto.ScheduleServerDTO> getScheduleServers() {
        List<com.adtec.scheduler.dto.ScheduleServerDTO> list = new ArrayList<com.adtec.scheduler.dto.ScheduleServerDTO>();
        List<String> servers = scheduleMonitor.getScheduleServerManager().getServers();
        for (String serverUuid : servers) {
            com.adtec.scheduler.dto.ScheduleServerDTO server = new com.adtec.scheduler.dto.ScheduleServerDTO();
            server.setNodeName(serverUuid);
            if (scheduleMonitor.getScheduleServerManager().isMaster(serverUuid, servers)) {
                server.setMasterFlag("调度节点");
            } else {
                server.setMasterFlag("普通节点");
            }
            list.add(server);
        }
        return list;
    }

}
