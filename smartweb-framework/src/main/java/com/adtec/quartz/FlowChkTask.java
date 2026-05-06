/**
 * 系统名称: SmartWeb平台
 * 模块名称: 流程超时检查自动任务
 * 类  名  称: FlowChkTask.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2018年9月6日 上午21:14:23<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.quartz;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adtec.framework.common.constant.Constants;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.common.util.GVarContainer;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.framework.log.PatternParserConstant;
import com.adtec.sys.common.cluster.ZKLock;
import com.adtec.sys.modules.flow.entity.FlowDO;
import com.adtec.sys.modules.flow.entity.FlowStepDO;
import com.adtec.sys.modules.flow.entity.FlowStepTemplateDO;
import com.adtec.sys.modules.flow.entity.FlowTemplateDO;
import com.adtec.sys.modules.flow.event.IFlowEvent;
import com.adtec.sys.modules.flow.service.FlowService;
import com.adtec.sys.modules.flow.service.FlowStepService;
import com.adtec.sys.modules.flow.service.FlowStepTemplateService;
import com.adtec.sys.modules.flow.service.FlowTemplateService;
import com.adtec.sys.modules.sys.utils.DictUtils;
import com.adtec.sys.seq.PlatSeq;
import com.google.common.collect.Maps;

public class FlowChkTask {
	protected final static Logger logger = LoggerFactory.getLogger(FlowChkTask.class);
	
	private CuratorFramework myCuratorClient;
	public FlowChkTask(){
		super();
	}
	
	public FlowChkTask(CuratorFramework myCuratorClient){
		this.myCuratorClient = myCuratorClient;
	}

	public void execute(){
		String clusterFlag = ParamUtil.getClusterFlag();
		if (check(clusterFlag)) {
			String interval = ParamUtil.getFlowChkInterval();
			if (DataUtil.isNullStr(interval)) {
				interval = "30000";//默认30秒
			}
			String taskPath = ZKLock.getLock(TaskParam.FLOWCHKTASK, interval + "", myCuratorClient);
			if (!DataUtil.isNullStr(taskPath)) {
				try {
					doIt();
				} finally {
					ZKLock.releaseLock(myCuratorClient, taskPath);
				}
			} 
		}else{
			doIt();
		}
	}
	
	public void doIt(){
		boolean FlowChkTaskDebug = logger.isDebugEnabled(); // 是否为调试模式，用于调用子类时判断是否需要开启日志打印
		GVarContainer.setVar(Constants.IS_DEBUG, FlowChkTaskDebug); // 设置是否调试日志变量
		GVarContainer.setVar(PatternParserConstant.GLOBAL_SEQ, PlatSeq.getGlobalSeq()); //添加全局流水号(yyyyMMddHHmmss+10位流水序号)到日志输出
		if (logger.isInfoEnabled()) {
			logger.info("开始执行检查流程超时处理自动任务...");
		}
		try {
			chkFlowTimeOut();
		} catch (Exception e) {
			logger.error("执行检查流程超时处理自动任务异常", e);
		} finally {
			// 最终关闭所有使用的数据库连接
			DBSessionFactory.clear();
			// 清理当前线程变量
			GVarContainer.clearVar();
		}
		if (logger.isInfoEnabled()) {
			logger.info("结束执行检查流程超时处理自动任务...");
		} 
	
	}
	public void chkFlowTimeOut(){
		FlowTemplateService flowTemplateService = SpringContextHolder.getBean("flowTemplateService");
		FlowStepTemplateService flowStepTemplateService = SpringContextHolder.getBean("flowStepTemplateService");
		FlowService flowService = SpringContextHolder.getBean("flowService");
		FlowStepService flowStepService = SpringContextHolder.getBean("flowStepService");
		// 获取所有设置了超时处理的待处理流程步骤实例列表
		List<FlowStepDO> stepList = flowStepService.getFlowTimeOutFlgFlow();
		String endTime = DateUtil.getDateTime();
		String nowDate = DateUtil.getDate() + DateUtil.getTime(); // yyyymmddhhMMss
		if(null!=stepList){
			// 列举每个步骤的唯一索引，超时处理为拒绝时只需要获取一笔执行处理
			String globalSeq = "";
			String flowTmplId = "";
			String stepTmplId = "";
			String flowTimeOutFlg = "";
			String flowFlowTimeOutFlg = "";
			int stepSer = 0;
			boolean isDealOnce = false; // 超时处理方式为02-审批拒绝、03-人工处理时，同一类步骤实例只需要处理一次
			boolean isDealFlow = false; // 处理流程实例是否超时
			// 步骤模板对象
			FlowStepTemplateDO stepTemplate = null;
			// 流程模板对象
			FlowTemplateDO flowTemplate = null;
			// 流程对象
			FlowDO flow = null;
			
			for(FlowStepDO step:stepList){
				// 是否执行步骤审批结束事件
				boolean isStepEvent = false;
				boolean isStepPass = false;
				// 是否执行流程审批结束事件
				boolean isFlowEvent = false;
				boolean isFlowPass = false;
				String stepEventClass = "";
				String flowEventClass = "";
				// 设置审批结束事件入参
				HashMap<String, Object> dataMap = Maps.newHashMap();

				logger.info("流程步骤["+step.getId()+"-流水号"+step.getGlobalSeq()+"-模板"+step.getFlowTmplId()+"-步骤"+step.getStepTmplId()+"-步骤号"+step.getStepSer()+"]");
				
				if(!(globalSeq.equals(step.getGlobalSeq()) && flowTmplId.equals(step.getFlowTmplId()) && stepTmplId.equals(step.getStepTmplId()) && stepSer==step.getStepSer())){
					// 同一类的流程实例开始
					globalSeq = step.getGlobalSeq();
					flowTmplId = step.getFlowTmplId();
					stepTmplId = step.getStepTmplId();
					stepSer = step.getStepSer();
					
					// 获取对应的步骤模板
					stepTemplate = flowStepTemplateService.get(step.getStepTmplId());
					if(null==stepTemplate && logger.isInfoEnabled()){
						logger.info("流程实例["+step.getId()+"-"+step.getInfoTitle()+"-"+step.getStepTmplId()+"]对应的步骤模板不存在");
					}
					flowTimeOutFlg = stepTemplate.getFlowTimeOutFlg();
					if("02".equals(flowTimeOutFlg) || "03".equals(flowTimeOutFlg) || "01".equals(flowTimeOutFlg)){
						// 超时处理类型：02-审批拒绝
						isDealOnce = true;
					}
					// 获取对应的流程模板
					flowTemplate = flowTemplateService.get(step.getFlowTmplId());
					if(null==flowTemplate && logger.isInfoEnabled()){
						logger.info("流程实例["+step.getId()+"-"+step.getInfoTitle()+"-"+step.getFlowTmplId()+"]对应的流程模板不存在");
					}
					flowFlowTimeOutFlg = flowTemplate.getFlowTimeOutFlg();
					// 获取对应的流程模板
					flow = flowService.getByGlobalSeq(globalSeq);
					if(null==flow && logger.isInfoEnabled()){
						logger.info("流程实例["+globalSeq+"]不存在");
					}
					isDealFlow = true;
				}
				if(null!=stepTemplate && null!=flowTemplate){
					// 初始化事件参数
					dataMap.put(IFlowEvent.KEY_GLOBAL_SEQ_NO, globalSeq); // 全局流水号
					dataMap.put(IFlowEvent.KEY_FLOW_TEMPLATE_ID, flowTmplId); // 流程模板ID
					dataMap.put(IFlowEvent.KEY_STEP_NO, stepSer); // 当前步骤号
					dataMap.put(IFlowEvent.KEY_FLOW_TITLE, flow.getInfoTitle()); // 流程标题
					dataMap.put(IFlowEvent.KEY_STEP_TITLE, step.getInfoTitle()); // 当前步骤标题
					dataMap.put(IFlowEvent.KEY_UP_USER_ID, flow.getSndUserId()); // 流程发起用户ID
					dataMap.put(IFlowEvent.KEY_UP_USER_NAME, flow.getSndUserName()); // 流程发起用户名称
					dataMap.put(IFlowEvent.KEY_DEAL_USER_ID, step.getCurProcUserId()); // 最后审批人ID
					dataMap.put(IFlowEvent.KEY_DEAL_USER_NAME, step.getCurProcUserName()); // 最后审批人中文名
					
					// 开启事务
					IDBSession session = DBSessionFactory.getSession();
					try {
						session.beginTransaction();
						// 达到设置的超时天数
						String strTime = step.getStrTime().trim().replace("-", "").replace(":", "").replace(" ","");
						int stepDays = stepTemplate.getDayNum();
						long diffStepDays = DateUtil.diffDateTimeBySecond(nowDate, strTime)/(60*60*24*1000);
						if(logger.isInfoEnabled()){
							logger.info("步骤程实例["+step.getId()+"],当前时间["+nowDate+"],当前步骤开始时间["+strTime+"],当前步骤已运行天数["+diffStepDays+"],步骤超时天数["+stepDays+"]");
						}

						// 步骤是否超时
						boolean isStepTimeOut = false;

						if(stepDays>0 && diffStepDays>=stepDays){
							isStepTimeOut = true;
							// 进入步骤超时处理
							if(logger.isInfoEnabled()){
								logger.info("步骤实例["+step.getId()+"]超时处理开始...");
								logger.info("超时处理类型："+DictUtils.getDictLabel(flowTimeOutFlg, "FLOW_TIME_OUT_FLG", flowTimeOutFlg));
							}
							if("03".equals(flowTimeOutFlg) && isDealOnce){
								// 当前步骤超时为人工处理，则移除当前未处理的步骤实例，然后生成当前步骤的超时处理流程
								FlowStepDO delObj = new FlowStepDO();
								delObj.setGlobalSeq(globalSeq);
								delObj.setFlowTmplId(flowTmplId);
								delObj.setStepTmplId(stepTmplId);
								delObj.setStepSer(stepSer);
								delObj.setFlowStat(FlowStepDO.STAT_PENDING);
								flowStepService.deleteByTimeout(delObj);
								flowStepService.generateStepInst(flowTmplId, stepSer, globalSeq, flow.getInfoTitle(), true, false);
							}
							
							if("02".equals(flowTimeOutFlg) && isDealOnce){
								// 当前步骤超时为审批拒绝，更新当前超时处理用户未自动处理系统内置用户
								FlowStepDO updObj = new FlowStepDO();
								updObj.setGlobalSeq(globalSeq);
								updObj.setFlowTmplId(flowTmplId);
								updObj.setStepTmplId(stepTmplId);
								updObj.setStepSer(stepSer);
								updObj.setFlowStat(FlowStepDO.STAT_AUTO_REFUSE_END);
								updObj.setTimeOutProcUserId(FlowDO.FLOW_INIT_USER);
								updObj.setTimeOutProcUserName(FlowDO.FLOW_INIT_USER_NAME);
								updObj.setEndTime(endTime);
								updObj.setAppMsg("步骤超时，自动处理为拒绝");
								// 更新步骤超时
								flowStepService.updateTimeOut(updObj);
								
								// 更新总流程状态、步骤号、超时处理用户
								FlowDO updFlowObj = new FlowDO();
								updFlowObj.setGlobalSeq(globalSeq);
								updFlowObj.setFlowStat(FlowDO.STAT_AUTO_REFUSE_END);
								updFlowObj.setTimeOutProcUserId(FlowDO.FLOW_INIT_USER);
								updFlowObj.setTimeOutProcUserName(FlowDO.FLOW_INIT_USER_NAME);
								updFlowObj.setStepSer(stepSer);
								updFlowObj.setEndTime(endTime);
								flowService.updateTimeOut(updFlowObj);
								
								// 设置总流程结束，后续不再超时处理
								isDealFlow = false;	
								isFlowEvent = true;
								isStepEvent = true;
								isStepPass = false;
								isFlowPass = false;
								stepEventClass = stepTemplate.getFlowProcClssTp();
								flowEventClass = flowTemplate.getFlowProcClssTp();
								dataMap.put(IFlowEvent.KEY_APPLY_STAT, updObj.getFlowStat()); // 最后步骤处理结果
								dataMap.put(IFlowEvent.KEY_APPLY_MSG, updObj.getAppMsg()); // 最后步骤审批信息
								
							}
							
							if("01".equals(flowTimeOutFlg) && isDealOnce){
								// 当前步骤超时为审批通过，更新当前超时处理用户未自动处理系统内置用户
								FlowStepDO updObj = new FlowStepDO();
								updObj.setGlobalSeq(globalSeq);
								updObj.setFlowTmplId(flowTmplId);
								updObj.setStepTmplId(stepTmplId);
								updObj.setStepSer(stepSer);
								updObj.setFlowStat(FlowStepDO.STAT_AUTO_PASS_END);
								updObj.setTimeOutProcUserId(FlowDO.FLOW_INIT_USER);
								updObj.setTimeOutProcUserName(FlowDO.FLOW_INIT_USER_NAME);
								updObj.setEndTime(endTime);
								updObj.setAppMsg("步骤超时，自动处理为通过");
								// 更新步骤超时
								flowStepService.updateTimeOut(updObj);
								
								// 检查当前步骤模板的下一步骤是否为总流程结束步骤
								if(FlowStepTemplateDO.STEP_END==stepTemplate.getNextStepSer()){
									// 更新总流程状态、步骤号、超时处理用户
									FlowDO updFlowObj = new FlowDO();
									updFlowObj.setGlobalSeq(globalSeq);
									updFlowObj.setFlowStat(FlowDO.STAT_AUTO_PASS_END);
									updFlowObj.setTimeOutProcUserId(FlowDO.FLOW_INIT_USER);
									updFlowObj.setTimeOutProcUserName(FlowDO.FLOW_INIT_USER_NAME);
									updFlowObj.setStepSer(FlowStepTemplateDO.STEP_END);
									updFlowObj.setEndTime(endTime);
									flowService.updateTimeOut(updFlowObj);
									
									// 设置总流程结束，后续不再超时处理
									isDealFlow = false;
									isFlowEvent = true;
									flowEventClass = flowTemplate.getFlowProcClssTp();
								}else{
									// 更新总流程状态、步骤号、超时处理用户
									FlowDO updFlowObj = new FlowDO();
									updFlowObj.setGlobalSeq(globalSeq);
									updFlowObj.setStepSer(stepTemplate.getNextStepSer());
									updFlowObj.setFlowStat(FlowDO.STAT_DEAL);
									updFlowObj.setEndTime(null);
									updFlowObj.setTimeOutProcUserId(null);
									updFlowObj.setTimeOutProcUserName(null);
									flowService.updateTimeOut(updFlowObj);

									// 未生成下一步骤实例的则生成下一步骤待处理实例
									FlowStepDO qryStep = new FlowStepDO();
									qryStep.setGlobalSeq(globalSeq);
									qryStep.setFlowTmplId(flowTmplId);
									qryStep.setStepTmplId(stepTmplId);
									qryStep.setStepSer(stepTemplate.getNextStepSer());
									if(flowStepService.getTotal(qryStep)<=0){
										flowStepService.generateStepInst(flowTmplId, stepTemplate.getNextStepSer(), globalSeq, flow.getInfoTitle(), false, false);
									}
								}
								isStepEvent = true;
								isStepPass = true;
								isFlowPass = true;
								stepEventClass = stepTemplate.getFlowProcClssTp();
								dataMap.put(IFlowEvent.KEY_APPLY_STAT, updObj.getFlowStat()); // 最后步骤处理结果
								dataMap.put(IFlowEvent.KEY_APPLY_MSG, updObj.getAppMsg()); // 最后步骤审批信息
							}
						}
						
						
						// 达到设置的超时天数
						String flowStrTime = flow.getStrTime().trim().replace("-", "").replace(":", "").replace(" ", "");
						int flowDays = flowTemplate.getDayNum();
						long diffFlowDays = DateUtil.diffDateTimeBySecond(nowDate, flowStrTime)/(60*60*24*1000);
						if(logger.isInfoEnabled()){
							logger.info("流程实例["+flow.getId()+"],当前时间["+nowDate+"],流程开始时间["+flowStrTime+"],流程已运行天数["+diffFlowDays+"],流程超时天数["+flowDays+"]");
						}
						if(isDealFlow && flowDays>0 && diffFlowDays>=flowDays && !isStepTimeOut){
							// 进入流程超时处理
							if(logger.isInfoEnabled()){
								logger.info("流程实例["+flow.getId()+"]超时处理开始...");
								logger.info("超时处理类型："+DictUtils.getDictLabel(flowFlowTimeOutFlg, "FLOW_TIME_OUT_FLG", flowFlowTimeOutFlg));
							}
							if("03".equals(flowFlowTimeOutFlg)){
								// 当前流程超时为人工处理，则移除当前未处理的步骤实例，然后生成最后一步骤的超时处理流程
								FlowStepDO delObj = new FlowStepDO();
								delObj.setGlobalSeq(globalSeq);
								delObj.setFlowTmplId(flowTmplId);
								delObj.setStepTmplId(stepTmplId);
								delObj.setStepSer(stepSer);
								delObj.setFlowStat(FlowStepDO.STAT_PENDING);
								flowStepService.deleteByTimeout(delObj);
								// 获取最后一个步骤号 
								FlowStepTemplateDO lastStepTemplate = flowStepTemplateService.getLastStep(flowTmplId);
								int lastStepSer = lastStepTemplate.getPrvStepSer();
								if (lastStepSer == 0) {
									lastStepSer = lastStepTemplate.getStepSer();
								}
								flowStepService.generateStepInst(flowTmplId, lastStepSer, globalSeq, flow.getInfoTitle(), true, true);
								
								// 更新流程步骤号为最后一步
								flowService.updateStepSerByGlobalSeq(globalSeq, lastStepSer);
							}
							
							if("02".equals(flowFlowTimeOutFlg)){
								// 当前流程超时为审批拒绝，更新当前超时处理用户未自动处理系统内置用户
								FlowStepDO updObj = new FlowStepDO();
								updObj.setGlobalSeq(globalSeq);
								updObj.setFlowTmplId(flowTmplId);
								updObj.setStepTmplId(stepTmplId);
								updObj.setStepSer(stepSer);
								updObj.setFlowStat(FlowStepDO.STAT_AUTO_REFUSE_END);
								updObj.setTimeOutProcUserId(FlowDO.FLOW_INIT_USER);
								updObj.setTimeOutProcUserName(FlowDO.FLOW_INIT_USER_NAME);
								updObj.setEndTime(endTime);
								updObj.setAppMsg("流程超时，自动处理为拒绝");
								// 更新步骤超时
								flowStepService.updateTimeOut(updObj);
								
								// 更新总流程状态、步骤号、超时处理用户
								FlowDO updFlowObj = new FlowDO();
								updFlowObj.setGlobalSeq(globalSeq);
								updFlowObj.setFlowStat(FlowDO.STAT_AUTO_REFUSE_END);
								updFlowObj.setTimeOutProcUserId(FlowDO.FLOW_INIT_USER);
								updFlowObj.setTimeOutProcUserName(FlowDO.FLOW_INIT_USER_NAME);
								updFlowObj.setStepSer(stepSer);
								updFlowObj.setEndTime(endTime);
								flowService.updateTimeOut(updFlowObj);
								isFlowEvent = true;
								isFlowPass = false;
								flowEventClass = flowTemplate.getFlowProcClssTp();
								dataMap.put(IFlowEvent.KEY_APPLY_STAT, updObj.getFlowStat()); // 最后步骤处理结果
								dataMap.put(IFlowEvent.KEY_APPLY_MSG, updObj.getAppMsg()); // 最后步骤审批信息
							}
							
							if("01".equals(flowFlowTimeOutFlg)){
								// 当前步流程时为审批通过，更新当前超时处理用户未自动处理系统内置用户
								FlowStepDO updObj = new FlowStepDO();
								updObj.setGlobalSeq(globalSeq);
								updObj.setFlowTmplId(flowTmplId);
								updObj.setStepTmplId(stepTmplId);
								updObj.setStepSer(stepSer);
								updObj.setFlowStat(FlowStepDO.STAT_AUTO_PASS_END);
								updObj.setTimeOutProcUserId(FlowDO.FLOW_INIT_USER);
								updObj.setTimeOutProcUserName(FlowDO.FLOW_INIT_USER_NAME);
								updObj.setEndTime(endTime);
								updObj.setAppMsg("流程超时，自动处理为通过");
								// 更新步骤超时
								flowStepService.updateTimeOut(updObj);
								
								// 更新总流程状态(总流程结束)、步骤号、超时处理用户
								FlowDO updFlowObj = new FlowDO();
								updFlowObj.setGlobalSeq(globalSeq);
								updFlowObj.setFlowStat(FlowDO.STAT_AUTO_PASS_END);
								updFlowObj.setTimeOutProcUserId(FlowDO.FLOW_INIT_USER);
								updFlowObj.setTimeOutProcUserName(FlowDO.FLOW_INIT_USER_NAME);
								updFlowObj.setStepSer(FlowStepTemplateDO.STEP_END);
								updFlowObj.setEndTime(endTime);
								flowService.updateTimeOut(updFlowObj);
								isFlowEvent = true;
								isFlowPass = true;
								flowEventClass = flowTemplate.getFlowProcClssTp();
								dataMap.put(IFlowEvent.KEY_APPLY_STAT, updObj.getFlowStat()); // 最后步骤处理结果
								dataMap.put(IFlowEvent.KEY_APPLY_MSG, updObj.getAppMsg()); // 最后步骤审批信息
							}
						}						
						session.endTransaction();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						try {
							session.rollback();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						logger.error("自动任务处理超时步骤异常", e);
						throw new BaseException(SysErr.E_DEFAULT, e, "自动任务处理超时步骤[" + step.getId()+"-"+step.getInfoTitle() + "]数据库事务失败");
					}
				}
				
				// 每一类的审批步骤处理结束后重置是否拒绝审批标识
				isDealOnce = false;
				isDealFlow = false;
				
				// 检查是否需要执行审批结束事件
				if(isStepEvent && !DataUtil.isNullStr(stepEventClass)){
					// 需要执行步骤结束事件
					IFlowEvent stepEvent = null;
					if(!DataUtil.isNullStr(stepEventClass)){
						try{
							stepEvent = (IFlowEvent)Class.forName(stepEventClass).newInstance();
						}catch(Exception e){
							logger.warn("步骤处理事件类["+stepEventClass+"]不存在");
						}
					}
					
					if(null!=stepEvent && isStepPass){
						// 执行步骤审批通过事件
						try{
							stepEvent.passEvent(dataMap);
						}catch(Exception e){
							logger.error("业务流水["+globalSeq+"],执行步骤审批通过后事件失败");
						}
					}
					
					if(null!=stepEvent && !isStepPass){
						// 执行步骤审批拒绝事件
						try{
							stepEvent.refuseEvent(dataMap);
						}catch(Exception e){
							logger.error("业务流水["+globalSeq+"],执行步骤审批拒绝后事件失败");
						}
					}
				}
				
				if(isFlowEvent && !DataUtil.isNullStr(flowEventClass)){
					// 需要执行流程结束事件
					IFlowEvent flowEvent = null;
					if(!DataUtil.isNullStr(flowEventClass)){
						try{
							flowEvent = (IFlowEvent)Class.forName(flowEventClass).newInstance();
						}catch(Exception e){
							logger.warn("流程处理事件类["+flowEventClass+"]不存在");
						}
					}
					
					if(null!=flowEvent && isFlowPass){
						// 执行流程审批通过事件
						try{
							flowEvent.passEvent(dataMap);
						}catch(Exception e){
							logger.error("业务流水["+globalSeq+"],执行流程通过后事件失败");
						}
					}
					
					if(null!=flowEvent && !isFlowPass){
						// 执行流程审批拒绝事件
						try{
							flowEvent.refuseEvent(dataMap);
						}catch(Exception e){
							logger.error("业务流水["+globalSeq+"],执行流程拒绝结束事件失败");
						}
					}
					
				}
				if(logger.isInfoEnabled()){
					logger.info("步骤程实例["+step.getId()+"]超时处理结束");
				}
			}
		}
	}
	public static boolean check(String taskRun){
        if( taskRun != null || (null!=taskRun && !taskRun.isEmpty()) ) {
            if (taskRun.toUpperCase().equals("Y")){
            	return true;
            }else{
            	return false;
            }
        }else{
        	return false;
        }
    }
}
