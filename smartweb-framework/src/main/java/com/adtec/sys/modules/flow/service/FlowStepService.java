/**
 * 系统名称: SmartWeb平台
 * 模块名称: 步骤实例服务类
 * 类  名  称: FlowStepService.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2018年8月7日 上午9:39:26<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.sys.modules.flow.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.curator.shaded.com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.sys.modules.flow.dao.FlowStepDao;
import com.adtec.sys.modules.flow.entity.FlowStepDO;
import com.adtec.sys.modules.flow.entity.FlowStepTemplateDO;
import com.adtec.sys.modules.sys.entity.Office;
import com.adtec.sys.modules.sys.entity.Role;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.service.SystemService;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;

/**
 * @author chenyl
 *
 */
@Service
@Transactional(readOnly = true)
public class FlowStepService {
	private final static Logger log = LoggerFactory.getLogger(FlowStepService.class);
	@Autowired
	private FlowStepDao flowStepDao;
	@Autowired
	private FlowStepTemplateService flowStepTemplateService;
	@Autowired
	private FlowService flowService;
	@Autowired
	private SystemService systemService;
	
	/**
	 * 根据步骤ID获取步骤实例信息
	 * @param id
	 * @return
	 */
	public FlowStepDO get(String id) {
		// TODO Auto-generated method stub
		return flowStepDao.get(id);
	}

	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(FlowStepDO obj) {
		return flowStepDao.getTotal(obj);
	}

	/**
	 * 更新流程步骤实例
	 * @param obj
	 * @return
	 */
	public boolean update(FlowStepDO obj) {
		int rs = 0;
		obj.preUpdate();
		rs = flowStepDao.update(obj);
		return rs > 0 ? true : false;
	}
	
	 /**
     * 根据全局流水号+流程模板id删除对应的流程步骤模板
     * @param globalSeq
     * @param flowTmplId
     * @return
     */
    public boolean deleteByFlowTmplId(String globalSeq, String flowTmplId) {
    	int rs = 0;
    	FlowStepDO flowStepDO = new FlowStepDO();
    	flowStepDO.setGlobalSeq(globalSeq);
    	flowStepDO.setFlowTmplId(flowTmplId);
    	rs = flowStepDao.delete(flowStepDO);
        return rs>0?true:false;
    }
    
	/**
	 * 根据流程步骤模板生成对应的流程实例
	 * @param flowTmplId	流程模板ID
	 * @param globalSeq		流程实例全局流水号
	 * @param stepSer			步骤号
	 * @param flowTitle			流程标题
	 * @param isTimeoutUser		是否未超时处理
	 * @param isFlowTimeout		true-是否流程超时处理，false步骤流程超时处理
	 * @return
	 */
	public int generateStepInst(String flowTmplId, int stepSer, String globalSeq, String flowTitle, boolean isTimeoutUser, boolean isFlowTimeout){
		int rs = 0;
		log.info("根据flowTmplId["+flowTmplId+"],stepSer["+stepSer+"],globalSeq["+globalSeq+"],flowTitle["+flowTitle+"]生成对应的步骤实例");
		// 获取对应的流程模板
		FlowStepTemplateDO flowStepTemplateDO = flowStepTemplateService.getByStepSer(flowTmplId, stepSer);
		if(null==flowStepTemplateDO){
			throw new BaseException(SysErr.E_DEFAULT, "流程步骤模板不存在,请先配置流程["+flowTmplId+"]的步骤模板");
		}
		// 获取审批处理用户列表
		String stepTemplateName = flowStepTemplateDO.getName();
		String flowStepTmplId = flowStepTemplateDO.getId();
		List<User> userList = null;
		if(isTimeoutUser && isFlowTimeout){
			// 修改当前步骤号为最后一步
			FlowStepTemplateDO qryObj = new FlowStepTemplateDO();
			qryObj.setmQry(FlowStepTemplateDO.MQRY_Y);
			qryObj.setFlowTmplId(flowTmplId);
			List<FlowStepTemplateDO> stepTemplateList = flowStepTemplateService.list(qryObj, 0, 0);
			flowStepTemplateDO = stepTemplateList.get(stepTemplateList.size()-1);
			stepSer = flowStepTemplateDO.getStepSer();
			stepTemplateName = flowStepTemplateDO.getName();
			flowStepTmplId = flowStepTemplateDO.getId();
			// 生成流程超时处理步骤列表从步骤模板获取超时用户列表
			userList = flowService.getUserListByFlowTmplId(flowStepTemplateDO.getFlowTmplId(), isTimeoutUser);
			// 更新流程实例的步骤号
			flowService.updateStepSerByGlobalSeq(globalSeq, stepSer);
		}else{
			// 生成待处理步骤列表从步骤模板获取处理用户/超时处理用户列表
			userList = getUserListByStepTmplId(flowStepTmplId, isTimeoutUser);
		}

		// 存在外部指定待处理用户列表
		if(FlowService.STEP_DEAL_MAP.containsKey(globalSeq)){
			Map<String, List<User>> stepCurUserMap = FlowService.STEP_DEAL_MAP.get(globalSeq);
			// 检查当前步骤是否需要使用外部待处理用户列表
			if(stepCurUserMap.containsKey(""+stepSer)){
				// 存在则直接使用外部指定的用户列表
				userList.clear();
				userList.addAll(stepCurUserMap.get(""+stepSer));
			}
		}

		String strTime = DateUtil.getDateTime(); // 步骤开始时间
		if(userList.size()==0){
			log.error("生成步骤["+stepSer+"]的实例失败,待处理用户列表为空");
			throw new BaseException(SysErr.E_DEFAULT, "生成步骤["+stepSer+"]的实例失败,待处理用户列表为空");
		}
		/*20210612 add by chenyl for 新增重复用户的过滤*/
		Set<String> userSet = Sets.newHashSet();
		// 循环插入当前步骤实例
		for(User user:userList){
			if(userSet.contains(user.getId())){
				continue;	// 已处理过该用户
			}else{
				userSet.add(user.getId());
			}
			FlowStepDO step = new FlowStepDO();
			step.setGlobalSeq(globalSeq);	// 设置全局流水号
			step.setFlowTmplId(flowTmplId);	// 设置流程模板ID
			step.setStepTmplId(flowStepTmplId); // 设置流程步骤模板ID
			step.setStepSer(stepSer); // 设置步骤号
			step.setInfoTitle(stepTemplateName);	// 设置标题，默认为步骤模板中文名称
			step.setFlowDesc(flowTitle+"-"+step.getInfoTitle()); // 设置步骤描述，流程标题-步骤标题
			step.setFlowStat(FlowStepDO.STAT_PENDING);	// 设置步骤状态：01-待审批
			step.setCurProcUserId(user.getId());	// 设置审批用户ID
			step.setCurProcUserName(user.getName());	// 设置审批用户名称
			if(isTimeoutUser){
				// 设置超时处理用户
				step.setTimeOutProcUserId(user.getId());
				step.setTimeOutProcUserName(user.getName());
			}
			step.setStrTime(strTime); // 设置步骤开始时间
			// 生成保存的ID
			step.preInsert();
			rs += flowStepDao.insert(step);
		}
		log.info("生成步骤["+stepSer+"]的实例数["+rs+"]");
		return rs;
	}
	
	/**
	 * 根据全局流水号+流程模板ID+步骤模板ID+步骤号+审批用户ID
	 * @param globalSeq
	 * @param flowTmplId
	 * @param stepTmplId
	 * @param stepSer
	 * @param userId
	 * @return
	 */
	public boolean chkCurUser(String globalSeq, String flowTmplId, String stepTmplId, int stepSer, String userId) {
		// TODO Auto-generated method stub
		return flowStepDao.chkCurUser(globalSeq, flowTmplId, stepTmplId, stepSer, userId);
	}
	
	/**
	 * 根据步骤模板ID获取审批用户或超时处理用来列表
	 * @param flowStepTmplId
	 * @param isTimeoutUser			true-获取超时处理用户，false-获取审批处理用户
	 * @return
	 */
	public List<User> getUserListByStepTmplId(String flowStepTmplId, boolean isTimeoutUser){
		List<User> userList = Lists.newArrayList();
		// 获取对应的步骤模板信息
		FlowStepTemplateDO flowStepTemplateDO = flowStepTemplateService.get(flowStepTmplId);
		String userType = flowStepTemplateDO.getApprFlowUserTp();
		String userId = flowStepTemplateDO.getApprUserId();
		if(isTimeoutUser){
			// 获取超时处理用户
			userType = flowStepTemplateDO.getTimeOutFlowUserTp();
			userId = flowStepTemplateDO.getTimeOutProcUserId();
		}
		if("user".equals(userType)){
			// 指定用户
			User user = systemService.getUser(userId);
			if(null!=user){
				userList.add(user);
			}
		}else if("office".equals(userType)){
			// 指定机构
			User qryUser = new User();
			qryUser.setOffice(new Office(userId));
			// 获取当前机构以及所有的下级机构下的用户列表
			List<User> uList = systemService.findUser(qryUser);
			if(null!=uList){
				userList.addAll(uList);
			}
			if ("1".equals(userId)) {
				// 加入admin用户
				userList.add(systemService.getUser("1"));
			}
			log.error("-----------------------" + userList.size());
		}else if("role".equals(userType)){
			// 指定角色
			User qryUser = new User();
			qryUser.setRole(new Role(userId));
			List<User> uList = systemService.findUser(qryUser);
			if(null!=uList){
				userList.addAll(uList);
			}
			if ("1".equals(userId)) {
				// 加入admin用户
				userList.add(systemService.getUser("1"));
			}
		}		
		return userList;
	}

	/**
	 * 获取步骤列表信息
	 * @param obj
	 * @return
	 */
	public List<FlowStepDO> show(FlowStepDO obj) {
		// TODO Auto-generated method stub
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "查询步骤列表FlowStepDO不能空");
		}
		String globalSeq = obj.getGlobalSeq();
		String flowTmplId = obj.getFlowTmplId();
		int stepSer = obj.getStepSer();
		log.info("获取步骤信息列表入参globaSeqNo["+globalSeq+"], flowTmplId["+flowTmplId+"], stepSer["+stepSer+"]");
		if(DataUtil.isNullStr(globalSeq)){
			throw new BaseException(SysErr.E_DEFAULT, "全局流水号不能空");
		}
		if(DataUtil.isNullStr(flowTmplId)){
			throw new BaseException(SysErr.E_DEFAULT, "流程模板ID不能空");
		}
		if(FlowStepTemplateDO.STEP_START>=stepSer){
			throw new BaseException(SysErr.E_DEFAULT, "步骤号从1开水");
		}
		if(FlowStepTemplateDO.STEP_END<=stepSer){
			// 当前当前步骤号为结束步骤号时，步骤号设置为步骤模板的最后一个步骤
			FlowStepTemplateDO lastStep = flowStepTemplateService.getLastStep(flowTmplId);
			stepSer = lastStep.getStepSer();
			log.info("当前步骤为最后一步，步骤号使用最后一步["+stepSer+"]");
		}
		return flowStepDao.show(globalSeq, flowTmplId, stepSer);
	}

	/**
	 * 获取当前用户的待审批流程步骤实例
	 * @param obj
	 * @return
	 */
	public FlowStepDO getByCurUser(FlowStepDO obj) {
		// TODO Auto-generated method stub
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "查询参数FlowStepDO不能空");
		}
		String globalSeq = obj.getGlobalSeq();
		if(DataUtil.isNullStr(globalSeq)){
			throw new BaseException(SysErr.E_DEFAULT, "全局流水号不能空");
		}
		String flowTmplId = obj.getFlowTmplId();
		if(DataUtil.isNullStr(flowTmplId)){
			throw new BaseException(SysErr.E_DEFAULT, "流程模板ID不能空");
		}
		String stepTmplId = obj.getStepTmplId();
		int stepSer = obj.getStepSer();
		if(0>=stepSer){
			throw new BaseException(SysErr.E_DEFAULT, "步骤号从1开始");
		}
		String curProcUserId = obj.getCurProcUserId();
		if(DataUtil.isNullStr(stepTmplId)){
			FlowStepTemplateDO stepTemplateDO = flowStepTemplateService.getByStepSer(flowTmplId, stepSer);
			if(null==stepTemplateDO){
				throw new BaseException(SysErr.E_DEFAULT, "步骤实例对应的步骤模板不存在");
			}
			stepTmplId = stepTemplateDO.getId();
		}
		if(DataUtil.isNullStr(curProcUserId)){
			// 如果处理用户未指定默认为当前登录用户
			curProcUserId = UserUtils.getUser().getId();
		}
		
		return flowStepDao.getByCurUser(globalSeq, flowTmplId, stepTmplId, stepSer, curProcUserId);
	}


	/**
	 * 根据流程实例信息检查当前步骤是否符合通过
	 * @param step	步骤实例对象
	 * @param stat 当前步骤实例的审批结果
	 * @return
	 */
	public boolean chkFlowStepPass(FlowStepDO step, String stat) {
		// TODO Auto-generated method stub
		boolean isEnd = false;
		if(null==step){
			throw new BaseException(SysErr.E_DEFAULT, "步骤实例对象不能空在");
		}
		// 获取对应的步骤模板
		FlowStepTemplateDO stepTemplate = flowStepTemplateService.get(step.getStepTmplId());
		if (null == stepTemplate) {
			throw new BaseException(SysErr.E_DEFAULT, "步骤模板[" + step.getStepTmplId() + "]不存在");
		}
		String flowApprFlg = stepTemplate.getFlowApprFlg();
		int succNum = stepTemplate.getSuccNum();

		// 获取当前步骤实例已通过的人数
		FlowStepDO qryStepDO = new FlowStepDO();
		qryStepDO.setGlobalSeq(step.getGlobalSeq());
		qryStepDO.setFlowTmplId(step.getFlowTmplId());
		qryStepDO.setStepTmplId(step.getStepTmplId());
		qryStepDO.setStepSer(step.getStepSer());
		// 获取当前步骤的总实例数
		int stepTotal = getTotal(qryStepDO);
		String flowStat = "'"+FlowStepDO.STAT_PASS_END+"','"+FlowStepDO.STAT_AUTO_PASS_END+"','"+FlowStepDO.STAT_TIMEOUT_PASS_END+"'";
		qryStepDO.setFlowStat(flowStat);
		int stepPass = getTotal(qryStepDO);			
		if(flowStat.indexOf(stat)>-1){
			// 当前步骤审批为通过的，通过人数+1
			stepPass++;
		}
		double passRate = Double.parseDouble(""+stepPass)/stepTotal*100;
		// 根据通过标准判断当前步骤是否符合通过
		if("1".equals(flowApprFlg)){
			// 1-单用户通过
			/*20200406 add by chenyl for 对于单个用户审批通过时，且状态为审批拒绝的则设置为流程结束*/
			if(stepPass>=1 || FlowStepDO.STAT_REFUSE_END.equals(stat) || FlowStepDO.STAT_AUTO_PASS_END.equals(stat) || FlowStepDO.STAT_TIMEOUT_REFUSE_END.equals(stat)){
				isEnd = true;
			}
		}else if("2".equals(flowApprFlg)){
			// 2-部分用户通过
			if(passRate>=succNum){
				isEnd = true;
			}
		}else if("3".equals(flowApprFlg)){
			// 3-全部通过
			if(passRate>=1){
				isEnd = true;
			}
		}
		return isEnd;
	}


	/**
	 * 获取所有设置了超时处理的待处理流程步骤实例列表
	 * @return
	 */
	public List<FlowStepDO> getFlowTimeOutFlgFlow() {
		// TODO Auto-generated method stub
		return flowStepDao.getFlowTimeOutFlgFlow();
	}


	/**
	 * 删除待处理且超时处理id为空的步骤实例
	 * @param delObj
	 */
	public void deleteByTimeout(FlowStepDO delObj) {
		// TODO Auto-generated method stub
		flowStepDao.deleteByTimeout(delObj);
	}


	/**
	 * 更新步骤超时
	 * @param updObj
	 */
	public void updateTimeOut(FlowStepDO updObj) {
		// TODO Auto-generated method stub
		flowStepDao.updateTimeOut(updObj);
	}
	
    public List<FlowStepDO> getFlowStep(FlowStepDO obj) {
        return flowStepDao.getFlowStep(obj);
    }	
    
    public List<FlowStepDO> getEndFlowStep(FlowStepDO obj) {
        return flowStepDao.getEndFlowStep(obj);
    }	
}
