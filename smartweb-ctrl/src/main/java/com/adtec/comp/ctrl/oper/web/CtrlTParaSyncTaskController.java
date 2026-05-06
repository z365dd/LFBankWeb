/**
* 系统名称: SmartWeb平台
* 模块名称: comp.ctrl.oper拦截器模块
* 功能描述: 数据同步任务控制类
* 类 名 称  : CtrlTParaSyncTaskController.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200513<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.comp.ctrl.oper.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adtec.comp.ctrl.dto.FCtrlTranDelDayStepReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlTranRcvDayStepReqDTO;
import com.adtec.comp.ctrl.oper.entity.CtrlTParaSyncTaskDO;
import com.adtec.comp.ctrl.oper.service.CtrlTParaSyncTaskService;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.DatasetColumnType;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.sys.common.web.BaseController;
import com.adtec.framework.exception.SysErr;
import com.adtec.sys.modules.sys.service.AreaService;
import com.adtec.sys.modules.sys.service.OfficeService;
import com.adtec.sys.modules.sys.service.SystemService;
import com.adtec.sys.modules.sys.utils.DictUtils;
import com.adtec.sys.modules.sys.utils.UserUtils;

/**
 * 数据同步任务Controller
 * @author zhengjt
 * @version 20200513
 */
@Controller
@RequestMapping(value = "${adminPath}/comp/ctrl/oper/ctrlTParaSyncTask")
public class CtrlTParaSyncTaskController extends BaseController {

	@Autowired
	private CtrlTParaSyncTaskService ctrlTParaSyncTaskService;
	
	/*首页页面路径*/
	private String PATH = "starring/comp/ctrl/oper/ctrlTParaSyncTask";
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
	@RequestMapping(value ={"ctrlTParaSyncTaskList"})
	public String ctrlTParaSyncTaskList(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"List";
	}
	
	/**
	 * 任务发起页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"ctrlTParaSyncTaskForm"})
	public String ctrlTParaSyncTaskForm(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"Form";
	}
	/**
	 *  步骤号下拉框
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getList" })
	public void getRelatSys(HttpServletRequest request, HttpServletResponse response){
	List<CtrlTParaSyncTaskDO> list = ctrlTParaSyncTaskService.list(new CtrlTParaSyncTaskDO());
	  List<Map<String, Object>> maps = new ArrayList();
	  Map<String, Object> emptyMap = new HashMap<>(2);
	        emptyMap.put("label", "--请选择--");
	        emptyMap.put("value", "");
	        maps.add(emptyMap);
	        for(CtrlTParaSyncTaskDO DO : list){
	         Map<String, Object> map = new HashMap<>(2);
	         map.put("label", DO.getStepNo());
	         map.put("value", DO.getStepNo());
	         maps.add(map);
	        }
	        Map<String, Object> m = new HashMap<>();
	        m.put("retCode", "0000");
	        m.put("list", maps);
	        renderString(response, m);
	 }
	
	/**
	 * 数据同步任务提交（接受日终步骤通知）
	 * @param request
	 * @param response 
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "dayChgNotice" })
	public void dayChgNotice(HttpServletRequest request, HttpServletResponse response){
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String stepNo= reqDs.getString("stepNo");
		String platDate= reqDs.getString("platDate");
		ResourceBundle source = ResourceBundle.getBundle("busiConfig");
		String modelNo = source.getString("CTRL_ModelNo");
		FCtrlTranRcvDayStepReqDTO rcvDayReqDTO = new FCtrlTranRcvDayStepReqDTO(); 
		rcvDayReqDTO.setTNT_NO(UserUtils.getUser().getRent().getEngName());
		rcvDayReqDTO.setCOMP_NO(modelNo);
		rcvDayReqDTO.setSTEP_NO(stepNo);
		rcvDayReqDTO.setTRAN_DATE(platDate);
		resDs = ctrlTParaSyncTaskService.rcvDayStep(rcvDayReqDTO);
		setResponseDataset(request, response,resDs, SysErr.E_SUCCESS,"交易成功");		
	}
	
	/**
	 * 数据同步任务处理（日终步骤处理）
	 * @param request
	 * @param response 
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "delDayStep" })
	public void delDayStep(HttpServletRequest request, HttpServletResponse response){
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String stepNo= reqDs.getString("stepNo");
		String platDate= reqDs.getString("platDate");
		ResourceBundle source = ResourceBundle.getBundle("busiConfig");
		String modelNo = source.getString("CTRL_ModelNo");
		FCtrlTranDelDayStepReqDTO rcvDayReqDTO = new FCtrlTranDelDayStepReqDTO(); 
		rcvDayReqDTO.setTNT_NO(UserUtils.getUser().getRent().getEngName());
		rcvDayReqDTO.setCOMP_NO(modelNo);
		rcvDayReqDTO.setSTEP_NO(stepNo);
		rcvDayReqDTO.setTRAN_DATE(platDate);
		resDs = ctrlTParaSyncTaskService.delDayStep(rcvDayReqDTO);
		setResponseDataset(request, response,resDs, SysErr.E_SUCCESS,"交易成功");		
	}
	
	/**
	* 列表查询
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value="list")
	public void list(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		CtrlTParaSyncTaskDO obj = DatasetService.getInstace().getObject(reqDs, CtrlTParaSyncTaskDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		List<CtrlTParaSyncTaskDO> list = ctrlTParaSyncTaskService.list(obj, start, limit);
		int total = ctrlTParaSyncTaskService.getTotal(obj);
		resDs = DatasetService.getInstace().getDataset(list,CtrlTParaSyncTaskDO.class);
		chgDict(resDs, true);
		resDs.setTotalCount(total);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "列表查询交易成功！");
	} 
	
	/**
	* 明细查询
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequestMapping(value="get")
	public void get(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String stepNo = reqDs.getString("stepNo");
		if (DataUtil.isNullStr(stepNo)) {
			throw new BaseException(SysErr.E_IN_NULL, "输入项[stepNo]不能空！");
		}		
		String platDate = reqDs.getString("platDate");
		if (DataUtil.isNullStr(platDate)) {
			throw new BaseException(SysErr.E_IN_NULL, "输入项[platDate]不能空！");
		}		

		CtrlTParaSyncTaskDO obj = ctrlTParaSyncTaskService.get(stepNo, platDate);
		IDataset resDs = DatasetService.getInstace().getDataset(obj, CtrlTParaSyncTaskDO.class);
		chgDict(resDs, false);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "明细查询交易成功！");
	}
	
	/**
	 * 数字字典转换
	 * @param ds
	 * @param isAction
	 */
	private void chgDict(IDataset ds, boolean isAction){
		if(null==ds){
			return;
		}
		SystemService systemService = SpringContextHolder.getBean("systemService");
		OfficeService officeService = SpringContextHolder.getBean("officeService");
		AreaService areaService = SpringContextHolder.getBean("areaService");
		//新增处理状态中文描述列
		ds.addColumn("statStr", DatasetColumnType.DS_STRING);
		if(isAction){
			//新增操作描述列
			ds.addColumn("action", DatasetColumnType.DS_STRING);
		}
		ds.beforeFirst();
		while(ds.hasNext()){
			ds.next();
			//更新处理状态中文描述
			String stat = ds.getString("stat");
			if ("001".equals(stat)) {
				ds.updateString("statStr", "登记");
			} else if ("002".equals(stat)) {
				ds.updateString("statStr", "正在处理");
			} else if ("003".equals(stat)) {
				ds.updateString("statStr", "处理成功");
			} else if ("004".equals(stat)) {
				ds.updateString("statStr", "处理失败");
			} 
			
			
			if(isAction){
				/*添加相关操作按钮*/
				if ("001".equals(stat) || "004".equals(stat)) {
					StringBuffer action = new StringBuffer();
					action.append("	<a href=\"JavaScript:void(0);\" onClick=\"del(" +"'"+ds.getString("stepNo")+"', "+"'"+ds.getString("platDate")+"'" + ")\" >处理</a>");
					ds.updateString("action", action.toString());
				}
			}
		}
	}
}