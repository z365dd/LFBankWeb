/**
* 系统名称: SmartWeb平台
* 模块名称: comp-prod/oper/definition/adapter拦截器模块
* 功能描述: 可售产品包装控制类
* 类 名 称  : TPipSaleProdAdapterController.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200106<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.prod.oper.definition.adapter.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adtec.prod.oper.definition.adapter.dto.SaleProdAdapterDTO;
import com.adtec.prod.oper.definition.adapter.entity.TPipSaleProdAdapterDO;
import com.adtec.sys.modules.sys.utils.DictUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.DatasetColumnType;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.framework.exception.SysErr;
import com.adtec.sys.common.web.BaseController;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.sys.modules.sys.service.OfficeService;
import com.adtec.sys.modules.sys.service.SystemService;
import com.adtec.sys.modules.sys.service.AreaService;
import com.adtec.prod.oper.definition.adapter.service.TPipSaleProdAdapterService;
import com.adtec.prod.oper.definition.saleprod.entity.TPipSaleProdDO;
import com.adtec.prod.util.ProdStrEnum;

/**
 * 可售产品包装Controller
 * @author zengxj
 * @version 20200106
 */
@Controller
@RequestMapping(value = "${adminPath}/prod/oper/definition/adapter/tPipSaleProdAdapter")
public class TPipSaleProdAdapterController extends BaseController {

	@Autowired
	private TPipSaleProdAdapterService tPipSaleProdAdapterService;
	
	/*首页页面路径*/
	private String PATH = (ParamUtil.getJspPath().split(ParamUtil.getConfig("web.view.prefix"))[1])+"/"+("prod/oper/definition/adapter/tPipSaleProdAdapter.jsp".replace(ParamUtil.getConfig("web.view.suffix"),""));
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
	@RequestMapping(value ={"tPipSaleProdAdapterList"})
	public String tPipSaleProdAdapterList(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"List";
	}
	
	/**
	 * 进入新增页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tPipSaleProdAdapterAdd"})
	public String studentAdd(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"AddForm";
	}
	
	/**
	 * 进入修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tPipSaleProdAdapterUpdate"})
	public String studentUpdate(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"UpdateForm";
	}
	
	/**
	 * 进入详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tPipSaleProdAdapterDetail"})
	public String studentDetail(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"DetailForm";
	}
	
	/**
	* 新增交易
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value="insert")
	public void insert(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		TPipSaleProdAdapterDO obj = DatasetService.getInstace().getObject(reqDs, TPipSaleProdAdapterDO.class);
		String keyJson = reqDs.getString("keyJson");
		if(tPipSaleProdAdapterService.insert(obj, keyJson)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "新增交易成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "新增交易失败！");
		}
	}
	
	/**
	* 修改交易
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value="update")
	public void update(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
//		TPipSaleProdAdapterDO obj = DatasetService.getInstace().getObject(reqDs, TPipSaleProdAdapterDO.class);
		String keyJson = reqDs.getString("keyJson");
		String saleProdCode = reqDs.getString("id");
		if(tPipSaleProdAdapterService.update(saleProdCode, keyJson)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "更新交易成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "更新交易失败！");
		}
	} 
	
	/**
	* 删除交易
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value="delete")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		TPipSaleProdAdapterDO obj = DatasetService.getInstace().getObject(reqDs, TPipSaleProdAdapterDO.class);
		if(tPipSaleProdAdapterService.delete(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "删除交易成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "删除交易失败！");
		}
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
		TPipSaleProdDO obj = DatasetService.getInstace().getObject(reqDs, TPipSaleProdDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		List<TPipSaleProdDO> list = tPipSaleProdAdapterService.list(obj, start, limit);
		int total = tPipSaleProdAdapterService.getTotal(obj);
		resDs = DatasetService.getInstace().getDataset(list,TPipSaleProdDO.class);
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
		String saleProdCode = reqDs.getString("id");
		TPipSaleProdAdapterDO obj = tPipSaleProdAdapterService.get(saleProdCode);
		IDataset resDs = DatasetService.getInstace().getDataset(obj, TPipSaleProdAdapterDO.class);
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
		if(isAction){
			//新增操作描述列
			ds.addColumn("action", DatasetColumnType.DS_STRING);
		}
		if ("ctrlDS".equals(ds.getDatasetName())) {
			ds.addColumn("keyTpStr", DatasetColumnType.DS_STRING);
			ds.addColumn("keyTypeStr", DatasetColumnType.DS_STRING);
		}
		ds.beforeFirst();
		while(ds.hasNext()){
			ds.next();
			if ("ctrlDS".equals(ds.getDatasetName())) {
				ds.updateString("keyTpStr", DictUtils.getDictLabel(ds.getString("enterTp"), "ENTER_TP", ""));
				ds.updateString("keyTypeStr", DictUtils.getDictLabel(ds.getString("keyTp"), "KEY_TP", ""));
			}
			if(isAction){
				/*添加相关操作按钮*/
				StringBuffer action = new StringBuffer();
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"detail('" + ds.getString("saleProdCode") + "', '"+ds.getString("prodLineCode")+"')\" >详情</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"update('" + ds.getString("saleProdCode") + "', '"+ds.getString("prodLineCode")+"')\" >修改</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"del('" + ds.getString("saleProdCode") + "')\" >删除</a>");
				ds.updateString("action", action.toString());
			}
		}
	}
	
	
	/**
	* 根据可售产品-原子产品获取对应的服务属性
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value="getKeyList")
	public void getKeyList(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String saleProdCode = reqDs.getString("saleProdCode");
		String detailFlag = reqDs.getString("detailFlag");// 0-否，1-是
		List<TPipSaleProdAdapterDO> list = tPipSaleProdAdapterService.getKeyList(saleProdCode, detailFlag);
		resDs = DatasetService.getInstace().getDataset(list, TPipSaleProdAdapterDO.class);
		resDs.setDatasetName("ctrlDS");
		chgDict(resDs, false);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "列表查询交易成功！");
	} 
	
	/**
	 * 根据可售产品-原子产品获取对应的服务属性
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value="getKeyCtrlList")
	public void getKeyCtrlList(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String keyNo = reqDs.getString("keyNo");
		List<SaleProdAdapterDTO> list = tPipSaleProdAdapterService.getKeyCtrlList(keyNo);
		resDs = DatasetService.getInstace().getDataset(list, SaleProdAdapterDTO.class);
//		chgDict(resDs, false);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "列表查询交易成功！");
	} 
	
}