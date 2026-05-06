/**
 * 系统名称: SmartWeb平台
 * 模块名称: 流程实例控制类
 * 类  名  称: FlowController.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2018年8月q4日 下午9:34:13<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.sys.modules.flow.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.GVarContainer;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.framework.log.PatternParserConstant;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.modules.flow.entity.FlowDO;
import com.adtec.sys.modules.flow.entity.FlowTemplateDO;
import com.adtec.sys.modules.flow.service.FlowService;
import com.adtec.sys.modules.flow.service.FlowStepService;
import com.adtec.sys.modules.flow.service.FlowTemplateService;
import com.adtec.sys.modules.flow.utils.ChgDictUtil;
import com.adtec.sys.modules.sys.entity.Dict;
import com.adtec.sys.modules.sys.utils.UserUtils;

/**
 * @author chenyl
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/flow/flow")
public class FlowController extends BaseController {
	@Autowired
	private FlowService flowService;
	@Autowired
	private FlowTemplateService flowTemplateService;
	@Autowired
	private FlowStepService flowStepService;

	/*首页页面路径*/
	public final static String PATH = "starring/sys/flow/flow";
	/**
	 * 进入管理页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"manage", ""})
	public String index(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"Manage";
	}
	
	/**
	 * 列表页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"flowList"})
	public String templateList(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"List";
	}
	
	/**
	 * 进入新增页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"flowAdd"})
	public String templateAdd(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"AddForm";
	}
	
	/**
	 * 进入修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"flowUpdate"})
	public String templateUpdate(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"UpdateForm";
	}
	
	/**
	 * 进入详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"flowDetail"})
	public String templateDetail(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"DetailForm";
	}
	
	/**
	 * 进入审批页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"flowApply"})
	public String flowApply(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"ApplyForm";
	}
	
	/**
	* 流程审批
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequestMapping(value="apply")
	public void apply(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		// 获取流程实例ID	
		String stepId = reqDs.getString("id");
		// 获取审批结果
		String stat = reqDs.getString("flowStat");
		// 获取审批信息
		String appMsg = reqDs.getString("appMsg");
		// 执行审批
		flowService.apply(stepId, stat, appMsg);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "审批交易成功！");
	}
	
	/**
	* 流程新增
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequestMapping(value="insert")
	public void insert(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		FlowDO obj = DatasetService.getInstace().getObject(reqDs, FlowDO.class);
		// 设置全局流水号
		obj.setGlobalSeq(""+GVarContainer.getVar(PatternParserConstant.GLOBAL_SEQ));
		if(flowService.insert(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "新增交易成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "新增交易失败！");
		}
	}
	
	/**
	* 流程修改
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequestMapping(value="update")
	public void update(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		FlowDO obj = DatasetService.getInstace().getObject(reqDs, FlowDO.class);		
		if(flowService.update(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "更新交易成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "更新交易失败！");
		}
	}
	
	/**
	* 流程删除
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequestMapping(value="delete")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		FlowDO obj = DatasetService.getInstace().getObject(reqDs, FlowDO.class);		
		if(flowService.delete(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "删除交易成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "删除交易失败！");
		}
	}
	
	/**
	* 流程明细查询
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequestMapping(value="get")
	public void get(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		FlowDO obj = DatasetService.getInstace().getObject(reqDs, FlowDO.class);
		FlowDO flowDO = flowService.get(obj.getId());
		if(null!=flowDO){
			resDs = DatasetService.getInstace().getDataset(flowDO, FlowDO.class);
			ChgDictUtil.chgFlowDict(resDs, false);
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "明细查询交易成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "明细查询交易失败！");
		}
	}

	/**
	 * 流程明细查询
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value="getBySeqNo")
	public void getBySeqNo(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		FlowDO obj = DatasetService.getInstace().getObject(reqDs, FlowDO.class);
		FlowDO flowDO = flowService.getByGlobalSeq(obj.getGlobalSeq());
		if(null!=flowDO){
			resDs = DatasetService.getInstace().getDataset(flowDO, FlowDO.class);
			ChgDictUtil.chgFlowDict(resDs, false);
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "明细查询交易成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "明细查询交易失败！");
		}
	}

	/**
	* 列表查询
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequestMapping(value="list")
	public void list(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		FlowDO obj = DatasetService.getInstace().getObject(reqDs, FlowDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		obj.setmQry(FlowDO.MQRY_Y);// 中英文名称模糊查询
		HashMap<String, Object> retMap = flowService.listByAuth(obj, start, limit);
		List<FlowDO> list = (List<FlowDO>)retMap.get("LIST");
		int total = Integer.parseInt(""+retMap.get("TOT"));
		resDs = DatasetService.getInstace().getDataset(list,FlowDO.class);
		ChgDictUtil.chgFlowDict(resDs, true);
		resDs.setTotalCount(total);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "列表查询交易成功！");
	}

	/**
	 * 获取流程模板下拉列表数据(当前用户有权限发起的)
	 * @param isblank
	 * @param blankText
	 * @param blankValue
	 * @param response
	 * @param stat
	 */
	@ResponseBody
	@RequestMapping(value = "selectData")
	public void getSelectJson(@RequestParam(required=false) String stat,@RequestParam(required=false) String isblank,@RequestParam(required=false) String blankText,@RequestParam(required=false) String blankValue, HttpServletResponse response) {
		/*前台接收value、label、title、selected、disabled*/
		logger.info("FlowTemplate getSelectJson");
		String type = "FLOW_TEMPATE";
		FlowTemplateDO qryDO = new FlowTemplateDO();
		qryDO.setmQry(FlowDO.MQRY_Y);
		qryDO.setSndUserId(UserUtils.getUser().getId());
		if(!DataUtil.isNullStr(stat)){
			// 设置查询流程模板状态：1-启用、2-挂起
			qryDO.setFlowTmplStat(stat);
		}
		List<FlowTemplateDO> templateList = flowTemplateService.listBySndUser(qryDO);
		List<Dict> list = new ArrayList<Dict>();
		if(!DataUtil.isNullStr(isblank) && "true".equals(isblank)){
			//单选下拉框需要添加空选项
			Dict d = new Dict();
			d.setDictTp(type);
			d.setLabel(blankText);
			d.setValue(blankValue);
			list.add(d);
		}
		// 设置返回的流程模板列表
		for(FlowTemplateDO obj:templateList){
			if(null!=obj){
				Dict d = new Dict();
				d.setDictTp(type);
				d.setLabel(obj.getName());
				d.setValue(obj.getId());
				list.add(d);
			}
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("retCode", "0000");
		m.put("list", list);
		renderString(response, m);
	}
	
    @ResponseBody
    @RequestMapping(value = "getLatestFlowInfo")
    public List<Map<String, Object>> getLatestFlowInfo() {
        return flowService.getLatestFlowInfo();
    }
    
	/**
	* 追加备注信息
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequestMapping(value="appendDesc")
	public void appendDesc(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		FlowDO obj = DatasetService.getInstace().getObject(reqDs, FlowDO.class);		
		flowService.appendDesc(obj);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "追加备注信息交易成功！");
	}
}
