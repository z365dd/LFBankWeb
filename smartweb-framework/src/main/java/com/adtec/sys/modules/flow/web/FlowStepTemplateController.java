/**
 * 系统名称: SmartWeb平台
 * 模块名称: 流程步骤模板控制类
 * 类  名  称: FlowStepTemplateController.java
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
import com.adtec.framework.common.util.StringUtil;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.modules.flow.entity.FlowStepTemplateDO;
import com.adtec.sys.modules.flow.entity.FlowTemplateDO;
import com.adtec.sys.modules.flow.service.FlowStepTemplateService;
import com.adtec.sys.modules.flow.utils.ChgDictUtil;
import com.adtec.sys.modules.sys.entity.Dict;

/**
 * @author chenyl
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/flow/stepTemplate")
public class FlowStepTemplateController extends BaseController {
	@Autowired
	private FlowStepTemplateService flowStepTemplateService;

	/*首页页面路径*/
	public final static String PATH = "starring/sys/flow/stepTemplate";
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
	@RequestMapping(value ={"stepTemplateList"})
	public String stepTemplateList(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"List";
	}
	
	/**
	 * 进入新增页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"stepTemplateAdd"})
	public String stepTemplateAdd(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"AddForm";
	}
	
	/**
	 * 进入修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"stepTemplateUpdate"})
	public String stepTemplateUpdate(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String id = reqDs.getString("id");
		FlowStepTemplateDO stepTemplateDO = flowStepTemplateService.get(id);
		if(null!=stepTemplateDO) {
			request.setAttribute("flowTmplId", stepTemplateDO.getFlowTmplId());
		}
		return PATH+"UpdateForm";
	}
	
	/**
	 * 进入详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"stepTemplateDetail"})
	public String stepTemplateDetail(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String id = reqDs.getString("id");
		FlowStepTemplateDO stepTemplateDO = flowStepTemplateService.get(id);
		if(null!=stepTemplateDO) {
			request.setAttribute("flowTmplId", stepTemplateDO.getFlowTmplId());
		}
		return PATH+"DetailForm";
	}
	
	/**
	* 流程步骤模板新增
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequestMapping(value="insert")
	public void insert(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		FlowStepTemplateDO obj = DatasetService.getInstace().getObject(reqDs, FlowStepTemplateDO.class);		
		if(flowStepTemplateService.insert(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "新增交易成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "新增交易失败！");
		}
	}
	
	/**
	* 流程步骤模板修改
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequestMapping(value="update")
	public void update(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		FlowStepTemplateDO obj = DatasetService.getInstace().getObject(reqDs, FlowStepTemplateDO.class);		
		if(flowStepTemplateService.update(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "更新交易成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "更新交易失败！");
		}
	}
	
	/**
	* 流程步骤模板删除
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequestMapping(value="delete")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		FlowStepTemplateDO obj = DatasetService.getInstace().getObject(reqDs, FlowStepTemplateDO.class);		
		if(flowStepTemplateService.delete(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "删除交易成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "删除交易失败！");
		}
	}
	
	/**
	* 流程步骤模板明细查询
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequestMapping(value="get")
	public void get(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		FlowStepTemplateDO obj = DatasetService.getInstace().getObject(reqDs, FlowStepTemplateDO.class);
		FlowStepTemplateDO stepTemplateDO = flowStepTemplateService.get(obj.getId());
		if(null!=stepTemplateDO){
			resDs = DatasetService.getInstace().getDataset(stepTemplateDO, FlowStepTemplateDO.class);
			ChgDictUtil.chgFlowStepTemplateDict(resDs, false);
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "明细查询交易成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "明细查询交易失败！");
		}
	}

	@ResponseBody
	@RequestMapping(value="getByTmpl")
	public void getByTmpl(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		FlowStepTemplateDO stepTemplateDO = flowStepTemplateService.getByTmpl(reqDs.getString("flowTmplId"));
		if(null!=stepTemplateDO){
			resDs = DatasetService.getInstace().getDataset(stepTemplateDO, FlowStepTemplateDO.class);
			ChgDictUtil.chgFlowStepTemplateDict(resDs, false);
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
		FlowStepTemplateDO obj = DatasetService.getInstace().getObject(reqDs, FlowStepTemplateDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		obj.setmQry(FlowTemplateDO.MQRY_Y);// 中英文名称模糊查询
		List<FlowStepTemplateDO> list = flowStepTemplateService.list(obj, start, limit);
		int total = flowStepTemplateService.getTotal(obj);
		resDs = DatasetService.getInstace().getDataset(list,FlowStepTemplateDO.class);
		ChgDictUtil.chgFlowStepTemplateDict(resDs, true);
		resDs.setTotalCount(total);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "列表查询交易成功！");
	}

	/**
	 * 获取步骤模板下拉列表数据
	 * @param flowTmplId
	 * @param isblank
	 * @param blankText
	 * @param blankValue
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "selectData")
	public void getSelectJson(@RequestParam(required=false) String flowTmplId, @RequestParam(required=false) String isblank,@RequestParam(required=false) String blankText,@RequestParam(required=false) String blankValue, HttpServletResponse response) {
		/*前台接收value、label、title、selected、disabled*/
		logger.info("FlowTemplate getSelectJson");
		String type = "STEP_TEMPATE";
		FlowStepTemplateDO qryDO = new FlowStepTemplateDO();
		if(!StringUtil.isEmpty(flowTmplId)){
			qryDO.setFlowTmplId(flowTmplId);
		}
		qryDO.setmQry(FlowTemplateDO.MQRY_Y);
		List<FlowStepTemplateDO> flowTemplateList = flowStepTemplateService.list(qryDO, 1, 0);
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
		for(FlowStepTemplateDO obj:flowTemplateList){
			if(null!=obj){
				Dict d = new Dict();
				d.setDictTp(type);
				d.setLabel(obj.getName());
				d.setValue(""+obj.getStepSer());
				list.add(d);
			}
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("retCode", "0000");
		m.put("list", list);
		renderString(response, m);
	}
}
