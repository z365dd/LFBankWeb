/**
 * 系统名称: SmartWeb平台
 * 模块名称: 流程模板控制类
 * 类  名  称: FlowTemplateController.java
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
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.modules.flow.entity.FlowTemplateDO;
import com.adtec.sys.modules.flow.service.FlowTemplateService;
import com.adtec.sys.modules.flow.utils.ChgDictUtil;
import com.adtec.sys.modules.sys.entity.Dict;

/**
 * @author chenyl
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/flow/template")
public class FlowTemplateController extends BaseController {
	@Autowired
	private FlowTemplateService flowTemplateService;

	/*首页页面路径*/
	public final static String PATH = "starring/sys/flow/template";
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
	@RequestMapping(value ={"templateList"})
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
	@RequestMapping(value ={"templateAdd"})
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
	@RequestMapping(value ={"templateUpdate"})
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
	@RequestMapping(value ={"templateDetail"})
	public String templateDetail(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"DetailForm";
	}
	
	/**
	* 流程模板新增
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequestMapping(value="insert")
	public void insert(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		FlowTemplateDO obj = DatasetService.getInstace().getObject(reqDs, FlowTemplateDO.class);		
		if(flowTemplateService.insert(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "新增交易成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "新增交易失败！");
		}
	}
	
	/**
	* 流程模板修改
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequestMapping(value="update")
	public void update(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		FlowTemplateDO obj = DatasetService.getInstace().getObject(reqDs, FlowTemplateDO.class);		
		if(flowTemplateService.update(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "更新交易成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "更新交易失败！");
		}
	}
	
	/**
	* 流程模板删除
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequestMapping(value="delete")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		FlowTemplateDO obj = DatasetService.getInstace().getObject(reqDs, FlowTemplateDO.class);		
		if(flowTemplateService.delete(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "删除交易成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "删除交易失败！");
		}
	}
	
	/**
	* 流程模板明细查询
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequestMapping(value="get")
	public void get(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		FlowTemplateDO obj = DatasetService.getInstace().getObject(reqDs, FlowTemplateDO.class);
		FlowTemplateDO templateDO = flowTemplateService.get(obj.getId());
		if(null!=templateDO){
			resDs = DatasetService.getInstace().getDataset(templateDO, FlowTemplateDO.class);
			ChgDictUtil.chgFlowTemplateDict(resDs, false);
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
		FlowTemplateDO obj = DatasetService.getInstace().getObject(reqDs, FlowTemplateDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		obj.setmQry(FlowTemplateDO.MQRY_Y);// 中英文名称模糊查询
		List<FlowTemplateDO> list = flowTemplateService.list(obj, start, limit);
		int total = flowTemplateService.getTotal(obj);
		resDs = DatasetService.getInstace().getDataset(list,FlowTemplateDO.class);
		ChgDictUtil.chgFlowTemplateDict(resDs, true);
		resDs.setTotalCount(total);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "列表查询交易成功！");
	}

	/**
	* 变更流程模板状态
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequestMapping(value="updateStat")
	public void updateStat(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String flowTmplId = reqDs.getString("id");
		String stat = reqDs.getString("flowTmplStat");
		if(flowTemplateService.updateStatus(flowTmplId, stat)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "更新流程模板状态成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "更新流程模板状态失败！");
		}
	}
	
	/**
	 * 获取所有的流程模板下拉列表数据
	 * @param stat
	 * @param isblank
	 * @param blankText
	 * @param blankValue
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "selectData")
	public void getSelectJson(@RequestParam(required=false) String stat,@RequestParam(required=false) String isblank,@RequestParam(required=false) String blankText,@RequestParam(required=false) String blankValue, HttpServletResponse response) {
		/*前台接收value、label、title、selected、disabled*/
		logger.info("FlowTemplate getSelectJson");
		String type = "FLOW_TEMPATE";
		FlowTemplateDO qryDO = new FlowTemplateDO();
		qryDO.setmQry(FlowTemplateDO.MQRY_Y);
		if(!DataUtil.isNullStr(stat)){
			// 设置查询流程模板状态：1-启用、2-挂起
			qryDO.setFlowTmplStat(stat);
		}
		List<FlowTemplateDO> templateList = flowTemplateService.list(qryDO, 1, 0);
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
}
