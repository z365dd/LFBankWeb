/**
 * 系统名称: SmartWeb平台
 * 模块名称: 对于流程信息字典转换工具类
 * 类  名  称: ChgDictUtil.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2018年5月31日 下午3:04:29<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.sys.modules.flow.utils;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.framework.interfaces.share.DatasetColumnType;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.Servlets;
import com.adtec.sys.modules.flow.entity.FlowDO;
import com.adtec.sys.modules.flow.entity.FlowStepDO;
import com.adtec.sys.modules.flow.entity.FlowStepTemplateDO;
import com.adtec.sys.modules.flow.entity.FlowTemplateDO;
import com.adtec.sys.modules.flow.service.FlowStepService;
import com.adtec.sys.modules.flow.service.FlowStepTemplateService;
import com.adtec.sys.modules.flow.service.FlowTemplateService;
import com.adtec.sys.modules.sys.entity.Office;
import com.adtec.sys.modules.sys.entity.Role;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.service.OfficeService;
import com.adtec.sys.modules.sys.service.SystemService;
import com.adtec.sys.modules.sys.utils.DictUtils;
import com.adtec.sys.modules.sys.utils.UserUtils;

/**
 * @author chenyl
 *
 */
public class ChgDictUtil {
	
	/**
	 * 流程模板数字字典转换
	 * @param ds
	 * @param isAction	是否需要生产action操作
	 */
	public static void chgFlowTemplateDict(IDataset ds, boolean isAction){
		if(null==ds){
			return;
		}
		SystemService systemService = SpringContextHolder.getBean("systemService");
		OfficeService officeService = SpringContextHolder.getBean("officeService");
		
		//新增流程类型中文描述
		ds.addColumn("flowTpStr", DatasetColumnType.DS_STRING);
		//新增流程模板状态中文描述
		ds.addColumn("statStr", DatasetColumnType.DS_STRING);
		//新增超时处理用户名称列
		ds.addColumn("timeOutProcUserName", DatasetColumnType.DS_STRING);
		//新增发起用户名称列
		ds.addColumn("sndUserName", DatasetColumnType.DS_STRING);
		if(isAction){
			//新增操作描述列
			ds.addColumn("action", DatasetColumnType.DS_STRING);
		}
		
		ds.beforeFirst();
		while(ds.hasNext()){
			ds.next();
			//更新流程类型中文描述
			String flowTp = ds.getString("flowTp");
			String flowTpStr = DictUtils.getDictLabels(flowTp, "FLOW_TP", flowTp);
			ds.updateString("flowTpStr", flowTpStr);
			//更新流程模板状态中文描述
			String stat = ds.getString("flowTmplStat");
			String statStr = DictUtils.getDictLabels(stat, "FLOW_TMPL_STAT", stat);
			ds.updateString("statStr", statStr);
			
			//更新超时处理用户名称
			String timeOutFlowUserTp = ds.getString("timeOutFlowUserTp");
			String timeOutProcUserId = ds.getString("timeOutProcUserId");
			String timeOutProcUserName = "";
			if("user".equals(timeOutFlowUserTp)){
				/*通过用户表T_SYS_USER查询对应的中文名称*/
				User user = systemService.getUser(timeOutProcUserId);
				if(null!=user){
					timeOutProcUserName = user.getName();
				}		
			} else if("office".equals(timeOutFlowUserTp)){
				/*通过机构表T_SYS_OFFICE查询对应的中文名称*/
				Office office = officeService.get(timeOutProcUserId);
				if(null!=office){
					timeOutProcUserName = office.getName();
				}			
			} else if("role".equals(timeOutFlowUserTp)){
				/*通过角色表T_SYS_ROLE查询对应的中文名称*/
				Role role = systemService.getRole(timeOutProcUserId);
				if(null!=role){
					timeOutProcUserName = role.getName();
				}	
			}
			ds.updateString("timeOutProcUserName", timeOutProcUserName);
			
			//更新发起用户名称
			String sndFlowUserTp = ds.getString("sndFlowUserTp");
			String sndUserId = ds.getString("sndUserId");
			String sndUserName = "";
			if("user".equals(sndFlowUserTp)){
				/*通过用户表T_SYS_USER查询对应的中文名称*/
				User user = systemService.getUser(sndUserId);
				if(null!=user){
					sndUserName = user.getName();
				}		
			} else if("office".equals(sndFlowUserTp)){
				/*通过机构表T_SYS_OFFICE查询对应的中文名称*/
				Office office = officeService.get(sndUserId);
				if(null!=office){
					sndUserName = office.getName();
				}			
			} else if("role".equals(sndFlowUserTp)){
				/*通过角色表T_SYS_ROLE查询对应的中文名称*/
				Role role = systemService.getRole(sndUserId);
				if(null!=role){
					sndUserName = role.getName();
				}	
			}
			ds.updateString("sndUserName", sndUserName);
			
			if(isAction){
				/*添加相关操作按钮*/
				StringBuffer action = new StringBuffer();
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"detail('" + ds.getString("id") + "')\" >详情</a>");
				if(FlowTemplateDO.STAT_ON.equals(stat)){
					// 启用
					action.append("	<a href=\"JavaScript:void(0);\" onClick=\"updateStat('" + ds.getString("id") + "' , '"+ FlowTemplateDO.STAT_OFF + "')\" >挂起</a>");
				}else{
					// 挂起
					action.append("	<a href=\"JavaScript:void(0);\" onClick=\"updateStat('" + ds.getString("id") + "' , '"+ FlowTemplateDO.STAT_ON + "')\" >启用</a>");
					action.append("	<a href=\"JavaScript:void(0);\" onClick=\"update('" + ds.getString("id") + "')\" >修改</a>");
					action.append("	<a href=\"JavaScript:void(0);\" onClick=\"del('" + ds.getString("id") + "')\" >删除</a>");
				}
				ds.updateString("action", action.toString());
			}
		}
	}

	/**
	 * 流程步骤模板数字字典转换
	 * @param ds
	 * @param isAction	是否需要生产action操作
	 */
	public static void chgFlowStepTemplateDict(IDataset ds, boolean isAction){
		if(null==ds){
			return;
		}
		FlowTemplateService flowTemplateService = SpringContextHolder.getBean("flowTemplateService");
		FlowStepTemplateService flowStepTemplateService = SpringContextHolder.getBean("flowStepTemplateService");
		SystemService systemService = SpringContextHolder.getBean("systemService");
		OfficeService officeService = SpringContextHolder.getBean("officeService");
		
		//新增流程模板中文描述
		ds.addColumn("flowTemplateStr", DatasetColumnType.DS_STRING);
		//新增流程模板类型中文描述
		ds.addColumn("flowTpStr", DatasetColumnType.DS_STRING);
		//新增流程模板版本
		ds.addColumn("version", DatasetColumnType.DS_STRING);
		//新增上一步骤中文描述
		ds.addColumn("preStepStr", DatasetColumnType.DS_STRING);
		//新增下一步骤中文描述
		ds.addColumn("nextStepStr", DatasetColumnType.DS_STRING);
		//新增超时处理用户名称列
		ds.addColumn("timeOutProcUserName", DatasetColumnType.DS_STRING);
		//新增审批用户名称列
		ds.addColumn("apprUserName", DatasetColumnType.DS_STRING);
		if(isAction){
			//新增操作描述列
			ds.addColumn("action", DatasetColumnType.DS_STRING);
		}
		
		ds.beforeFirst();
		while(ds.hasNext()){
			ds.next();
			//更新流程模板中文描述
			String flowTmplId = ds.getString("flowTmplId");
			FlowTemplateDO flowTemplateDO = flowTemplateService.get(flowTmplId);
			String flowTemplateName = flowTmplId;
			if(null!=flowTemplateDO){
				flowTemplateName = flowTemplateDO.getName();
			}
			ds.updateString("flowTemplateStr", flowTemplateName);
			
			//更新流程模板类型中文描述
			String flowTp = flowTemplateDO.getFlowTp();
			ds.updateString("flowTpStr", DictUtils.getDictLabel(flowTp, "FLOW_TP", flowTp));
			
			//更新流程模板版本
			ds.updateString("version", flowTemplateDO.getVerNo());
			
			//更新上一步骤中文描述
			int prvStepSer = ds.getInt("prvStepSer");
			String preStepStr = ""+prvStepSer;
			if(FlowStepTemplateDO.STEP_START==prvStepSer){
				// 总流程开始节点
				preStepStr = FlowStepTemplateDO.STEP_START_NAME;
			}else{
				// 获取对应的流程步骤节点信息
				FlowStepTemplateDO stepTemplateDO = flowStepTemplateService.getByStepSer(flowTmplId, prvStepSer);
				if(null!=stepTemplateDO){
					preStepStr = stepTemplateDO.getName();
				}
			}
			ds.updateString("preStepStr", preStepStr);
			
			//更新下一步骤中文描述
			int nextStepSer = ds.getInt("nextStepSer");
			String nextStepStr = ""+nextStepSer;
			if(FlowStepTemplateDO.STEP_END==nextStepSer){
				// 总流程结束节点
				nextStepStr = FlowStepTemplateDO.STEP_END_NAME;
			}else{
				// 获取对应的流程步骤节点信息
				FlowStepTemplateDO stepTemplateDO = flowStepTemplateService.getByStepSer(flowTmplId, nextStepSer);
				if(null!=stepTemplateDO){
					nextStepStr = stepTemplateDO.getName();
				}
			}
			ds.updateString("nextStepStr", nextStepStr);
			
			//更新超时处理用户名称
			String timeOutFlowUserTp = ds.getString("timeOutFlowUserTp");
			String timeOutProcUserId = ds.getString("timeOutProcUserId");
			String timeOutProcUserName = "";
			if("user".equals(timeOutFlowUserTp)){
				/*通过用户表T_SYS_USER查询对应的中文名称*/
				User user = systemService.getUser(timeOutProcUserId);
				if(null!=user){
					timeOutProcUserName = user.getName();
				}		
			} else if("office".equals(timeOutFlowUserTp)){
				/*通过机构表T_SYS_OFFICE查询对应的中文名称*/
				Office office = officeService.get(timeOutProcUserId);
				if(null!=office){
					timeOutProcUserName = office.getName();
				}			
			} else if("role".equals(timeOutFlowUserTp)){
				/*通过角色表T_SYS_ROLE查询对应的中文名称*/
				Role role = systemService.getRole(timeOutProcUserId);
				if(null!=role){
					timeOutProcUserName = role.getName();
				}	
			}
			ds.updateString("timeOutProcUserName", timeOutProcUserName);
			
			//更新发起用户名称
			String apprFlowUserTp = ds.getString("apprFlowUserTp");
			String apprUserId = ds.getString("apprUserId");
			String apprUserName = "";
			if("user".equals(apprFlowUserTp)){
				/*通过用户表T_SYS_USER查询对应的中文名称*/
				User user = systemService.getUser(apprUserId);
				if(null!=user){
					apprUserName = user.getName();
				}		
			} else if("office".equals(apprFlowUserTp)){
				/*通过机构表T_SYS_OFFICE查询对应的中文名称*/
				Office office = officeService.get(apprUserId);
				if(null!=office){
					apprUserName = office.getName();
				}			
			} else if("role".equals(apprFlowUserTp)){
				/*通过角色表T_SYS_ROLE查询对应的中文名称*/
				Role role = systemService.getRole(apprUserId);
				if(null!=role){
					apprUserName = role.getName();
				}	
			}
			ds.updateString("apprUserName", apprUserName);

			if(isAction){
				/*添加相关操作按钮*/
				StringBuffer action = new StringBuffer();
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"detail('" + ds.getString("id") + "')\" >详情</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"update('" + ds.getString("id") + "')\" >修改</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"del('" + ds.getString("id") + "')\" >删除</a>");
				ds.updateString("action", action.toString());
			}
		}
	}

	/**
	 * 流程实例数字字典转换
	 * @param ds
	 * @param isAction	是否需要生产action操作
	 */
	public static void chgFlowDict(IDataset ds, boolean isAction) {
		// TODO Auto-generated method stub
		if(null==ds){
			return;
		}
		FlowTemplateService flowTemplateService = SpringContextHolder.getBean("flowTemplateService");
		FlowStepTemplateService stepTemplateService = SpringContextHolder.getBean("flowStepTemplateService");
		FlowStepService stepService = SpringContextHolder.getBean("flowStepService");
		
		String userId = "";
		String userName = "";
		User user = UserUtils.getUser();
		if(null!=user){
			userId = user.getId();
			userName = user.getName();
		}
		// 新增流程模板中文名
		ds.addColumn("flowTemplateStr", DatasetColumnType.DS_STRING);
		// 新增当前步骤中文描述
		ds.addColumn("stepStr", DatasetColumnType.DS_STRING);
		// 新增流程状态中文描述
		ds.addColumn("statStr", DatasetColumnType.DS_STRING);
		// 新增超时处理
		ds.addColumn("flowTimeOutFlg", DatasetColumnType.DS_STRING);
		// 新增流程总步骤数
		ds.addColumn("stepCount", DatasetColumnType.DS_STRING);
		// 新增申请详情URL
		ds.addColumn("applyUrl", DatasetColumnType.DS_STRING);
		// 新增当前用户ID
		ds.addColumn("curUserId", DatasetColumnType.DS_STRING);
		// 新增当前用户名
		ds.addColumn("curUserName", DatasetColumnType.DS_STRING);

		if(isAction){
			//新增操作描述列
			ds.addColumn("action", DatasetColumnType.DS_STRING);
		}
		
		ds.beforeFirst();
		while(ds.hasNext()){
			ds.next();
			
			// 更新当前操作用户id和名称
			ds.updateString("curUserId", userId);
			ds.updateString("curUserName", userName);
			
			String globalSeq = ds.getString("globalSeq");
			//更新流程模板中文描述
			String flowTmplId = ds.getString("flowTmplId");
			FlowTemplateDO flowTemplate = flowTemplateService.get(flowTmplId);
			String sndFlowUserTp = flowTemplate.getSndFlowUserTp(); // 发起用户类型
			String flowTemplateStr = flowTmplId;
			if(null!=flowTemplate){
				flowTemplateStr = flowTemplate.getName();
			}
			ds.updateString("flowTemplateStr", flowTemplateStr);
			
			//更新申请详情URL列
			String applyUrl = "";
			if(null!=flowTemplate){
				applyUrl = flowTemplate.getApplyUrl();
			}
			if(!DataUtil.isNullStr(applyUrl)){
				applyUrl = Servlets.getWebContextPath()+ParamUtil.getAdminPath()+applyUrl+(applyUrl.startsWith("/")?"":"/")+(applyUrl.endsWith("?")?"&id=":"?id=")+globalSeq;
			}
			ds.updateString("applyUrl", applyUrl);
			
			//更新当前步骤中文描述
			FlowStepTemplateDO stepTemplate = null;
			int stepSer = ds.getInt("stepSer");
			String stepStr = ""+stepSer;
			if(FlowStepTemplateDO.STEP_START==stepSer){
				// 总流程开始
				stepStr = stepSer+"-"+FlowStepTemplateDO.STEP_START_NAME;
			}else if(FlowStepTemplateDO.STEP_END==stepSer){
				// 总流程结束
				stepStr = stepSer+"-"+FlowStepTemplateDO.STEP_END_NAME;
				// 获取最后一个流程步骤
				stepTemplate = stepTemplateService.getLastStep(flowTmplId);
			}else{
				// 处理流程
				stepTemplate = stepTemplateService.getByStepSer(flowTmplId, stepSer);
				if(null!=stepTemplate){
					stepStr = stepSer+"-"+stepTemplate.getName();
				}
			}
			ds.updateString("stepStr", stepStr);
						
			//更新流程状态中文描述
			String stat = ds.getString("flowStat");
			String statStr = DictUtils.getDictLabels(stat, "FLOW_STAT", stat);
			ds.updateString("statStr", statStr);
			
			//更新超时处理
			ds.updateString("flowTimeOutFlg", flowTemplate.getFlowTimeOutFlg());
			
			//更新流程总步骤数
			String stepCount = "0";
			FlowStepTemplateDO qryFlowStepTemplateDO = new FlowStepTemplateDO();
			qryFlowStepTemplateDO.setFlowTmplId(flowTmplId);
			qryFlowStepTemplateDO.setmQry(FlowStepTemplateDO.MQRY_Y);
			stepCount = ""+stepTemplateService.getTotal(qryFlowStepTemplateDO);
			ds.updateString("stepCount", stepCount);
			
			if(isAction){
				String sndUserId = ds.getString("sndUserId");	// 发起用户ID
				/*添加相关操作按钮*/
				StringBuffer action = new StringBuffer();
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"detail('" + ds.getString("id") + "')\" >详情</a>");
				FlowStepTemplateDO nextStepTemplate = stepTemplateService.getByStepSer(flowTmplId, stepSer+1);
				if((FlowDO.STAT_START.equals(stat) && null!=nextStepTemplate && stepService.chkCurUser(globalSeq, flowTmplId, nextStepTemplate.getId(), stepSer+1, userId)) || (FlowDO.STAT_DEAL.equals(stat) && null!=stepTemplate && stepService.chkCurUser(globalSeq, flowTmplId, stepTemplate.getId(), stepSer, userId))){
					//审批操作：状态为：开始且下一步骤处理人包含当前登录用户；状态为：处理中，且当前步骤处理人包含当前登录用户
					action.append("	<a href=\"JavaScript:void(0);\" onClick=\"apply('" + ds.getString("id") + "')\" >审批</a>");
				}else{
					//action.append("	<a href=\"JavaScript:void(0);\" onClick=\"return false;\" >&nbsp;&nbsp;&nbsp;&nbsp;</a>");
				}
				
				if(FlowDO.STAT_START.equals(stat) && (userId.equals(sndUserId) || DataUtil.isNullStr(sndFlowUserTp))){
					//状态为：开始，且为本用户发起的或流程模板中不限制发起用户时审批用户可以进行修改
					action.append("	<a href=\"JavaScript:void(0);\" onClick=\"update('" + ds.getString("id") + "')\" >修改</a>");
				}else{
					//action.append("	<a href=\"JavaScript:void(0);\" onClick=\"return false;\" >&nbsp;&nbsp;&nbsp;&nbsp;</a>");
				}
				
				if(!FlowDO.STAT_DEAL.equals(stat) && (userId.equals(sndUserId) || (DataUtil.isNullStr(sndFlowUserTp) && ((FlowDO.STAT_START.equals(stat) && null!=nextStepTemplate && stepService.chkCurUser(globalSeq, flowTmplId, nextStepTemplate.getId(), (stepSer+1), userId)) || (null!=stepTemplate && stepService.chkCurUser(globalSeq, flowTmplId, stepTemplate.getId(), stepTemplate.getStepSer(), userId)))))){
					//状态为：非处理中，且为本用户发起的或流程模板中不限制发起用户时审批用户可以进行删除；
					action.append("	<a href=\"JavaScript:void(0);\" onClick=\"del('" + ds.getString("id") + "')\" >删除</a>");
				}else{
					//action.append("	<a href=\"JavaScript:void(0);\" onClick=\"return false;\" >&nbsp;&nbsp;&nbsp;&nbsp;</a>");
				}

				ds.updateString("action", action.toString());
			}
		}
	}

	/**
	 * 步骤实例字典转换
	 * @param ds
	 * @param isAction
	 */
	public static void chgFlowStepDict(IDataset ds, boolean isAction) {
		// TODO Auto-generated method stub
		if(null==ds){
			return;
		}

		FlowStepTemplateService stepTemplateService = SpringContextHolder.getBean("flowStepTemplateService");
		
		// 新增步骤状态中文名称
		ds.addColumn("statStr", DatasetColumnType.DS_STRING);
		// 新增步骤通过标准
		ds.addColumn("flowApprFlg", DatasetColumnType.DS_STRING);
		// 新增步骤通过标准中文名称
		ds.addColumn("flowApprFlgStr", DatasetColumnType.DS_STRING);
		// 新增步骤通过用户个数百分比
		ds.addColumn("succNum", DatasetColumnType.DS_STRING);
		
		if(isAction){
			//新增操作描述列
			ds.addColumn("action", DatasetColumnType.DS_STRING);
		}
		
		ds.beforeFirst();
		while(ds.hasNext()){
			ds.next();
			// 更新步骤状态中文名称
			String statStr = ds.getString("flowStat");
			ds.updateString("statStr", DictUtils.getDictLabel(statStr, "FLOW_STAT", statStr));
			// 获取流程步骤模板
			String stepTmplId = ds.getString("stepTmplId");
			FlowStepTemplateDO stepTemplate = stepTemplateService.get(stepTmplId);
			if(null!=stepTemplate){
				String flowApprFlg = stepTemplate.getFlowApprFlg();
				// 更新通过标准
				ds.updateString("flowApprFlg", flowApprFlg);
				// 更新通过标准中文描述
				ds.updateString("flowApprFlgStr", DictUtils.getDictLabel(flowApprFlg, "FLOW_APPR_FLG", flowApprFlg));
				// 更新通过用户比例
				ds.updateString("succNum", ""+stepTemplate.getSuccNum());
			}
		}
	}

	/**
	 * 转换步骤审批数字字典
	 * @param ds
	 */
	public static void chgFlowStepApplyDict(IDataset ds) {
		// TODO Auto-generated method stub
		if(null==ds){
			return;
		}

		FlowStepTemplateService stepTemplateService = SpringContextHolder.getBean("flowStepTemplateService");
		FlowStepService stepService = SpringContextHolder.getBean("flowStepService");
		
		// 新增步骤模板的通过标准
		ds.addColumn("flowApprFlg", DatasetColumnType.DS_STRING);
		// 新增当前步骤通过用户比例
		ds.addColumn("succNumRate", DatasetColumnType.DS_STRING);

		ds.beforeFirst();
		while(ds.hasNext()){
			ds.next();
			
			// 获取对应的步骤模板
			String stepTmplId = ds.getString("stepTmplId");
			FlowStepTemplateDO stepTemplateDO = stepTemplateService.get(stepTmplId);
			// 更新当前步骤通过标准
			ds.updateString("flowApprFlg", stepTemplateDO.getFlowApprFlg());
			
			// 统计当前步骤的实例数
			FlowStepDO qryStepDO = new FlowStepDO();
			qryStepDO.setGlobalSeq(ds.getString("globalSeq"));
			qryStepDO.setFlowTmplId(ds.getString("flowTmplId"));
			qryStepDO.setStepTmplId(ds.getString("stepTmplId"));
			qryStepDO.setStepSer(ds.getInt("stepSer"));
			int stepTotal = stepService.getTotal(qryStepDO);
			// 统计当前步骤的通过人数
			String stat = "'"+FlowStepDO.STAT_PASS_END+"','"+FlowStepDO.STAT_AUTO_PASS_END+"','"+FlowStepDO.STAT_TIMEOUT_PASS_END+"'";
			qryStepDO.setFlowStat(stat);
			int stepPass = stepService.getTotal(qryStepDO);			
			// 更新当前步骤通过用户比例
			ds.updateString("succNumRate", stepPass+"/"+stepTotal);
		}
	}
}
