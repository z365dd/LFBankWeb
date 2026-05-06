/**
 * 系统名称: SmartWeb平台
 * 模块名称: 流程步骤实例控制类
 * 类  名  称: FlowStepController.java
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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.modules.flow.entity.FlowStepDO;
import com.adtec.sys.modules.flow.service.FlowStepService;
import com.adtec.sys.modules.flow.utils.ChgDictUtil;
import com.adtec.sys.modules.sys.utils.UserUtils;

/**
 * @author chenyl
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/flow/flowStep")
public class FlowStepController extends BaseController {
	@Autowired
	private FlowStepService flowStepService;

	/*首页页面路径*/
	public final static String PATH = "starring/sys/flow/flowStep";
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
	* 展现当前步骤号及以前的步骤信息
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequestMapping(value="show")
	public void show(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		FlowStepDO obj = DatasetService.getInstace().getObject(reqDs, FlowStepDO.class);
		List<FlowStepDO> list = flowStepService.show(obj);
		resDs = DatasetService.getInstace().getDataset(list,FlowStepDO.class);
		ChgDictUtil.chgFlowStepDict(resDs, false);
		resDs.setTotalCount(list.size());
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "获取步骤列表交易成功！");
	}

	/**
	* 获取当前用户的待审批流程步骤实例
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequestMapping(value="get")
	public void get(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		FlowStepDO obj = DatasetService.getInstace().getObject(reqDs, FlowStepDO.class);
		obj.setCurProcUserId(UserUtils.getUser().getId()); // 设置当前用户为审批处理用户
		FlowStepDO stepDO = flowStepService.getByCurUser(obj);
		if(null!=stepDO){
			resDs = DatasetService.getInstace().getDataset(stepDO, FlowStepDO.class);
			ChgDictUtil.chgFlowStepApplyDict(resDs);
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "查询待审批实例交易成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "查询待审批实例交易失败！");
		}
	}
}
