/**
* 系统名称: SmartWeb平台
* 模块名称: sys拦截器模块
* 功能描述: 权限维度控制类
* 类 名 称  : SysPermissionWeightController.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20190901<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.sys.modules.sys.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.DatasetColumnType;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.modules.sys.entity.SysPermissionWeightDO;
import com.adtec.sys.modules.sys.service.SysPermissionWeightService;
import com.adtec.sys.modules.sys.utils.DictUtils;

/**
 * 权限维度Controller
 * @author 权限维度
 * @version 20190901
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysPermissionWeight")
public class SysPermissionWeightController extends BaseController {

	@Autowired
	private SysPermissionWeightService sysPermissionWeightService;
	
	/*首页页面路径*/
	private String PATH = (ParamUtil.getJspPath().split(ParamUtil.getConfig("web.view.prefix"))[1])+"/"+("sys/sysPermissionWeight.jsp".replace(ParamUtil.getConfig("web.view.suffix"),""));
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
	@RequestMapping(value ={"sysPermissionWeightList"})
	public String sysPermissionWeightList(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"List";
	}

	/**
	 * 列表页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"init"})
	public String init(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"Init";
	}

	/**
	 * 进入新增页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"sysPermissionWeightAdd"})
	public String sysPermissionWeightAdd(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"AddForm";
	}
	
	/**
	 * 进入修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"sysPermissionWeightUpdate"})
	public String sysPermissionWeightUpdate(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"UpdateForm";
	}
	
	/**
	 * 进入详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"sysPermissionWeightDetail"})
	public String sysPermissionWeightDetail(HttpServletRequest request, HttpServletResponse response) {
		DatasetService.printDataset(DatasetService.getInstace().getDataset(request));
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
		SysPermissionWeightDO obj = DatasetService.getInstace().getObject(reqDs, SysPermissionWeightDO.class);
		if(sysPermissionWeightService.insert(obj)){
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
		SysPermissionWeightDO obj = DatasetService.getInstace().getObject(reqDs, SysPermissionWeightDO.class);
		if(sysPermissionWeightService.update(obj)){
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
		SysPermissionWeightDO obj = DatasetService.getInstace().getObject(reqDs, SysPermissionWeightDO.class);
		if(sysPermissionWeightService.delete(obj)){
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
		SysPermissionWeightDO obj = DatasetService.getInstace().getObject(reqDs, SysPermissionWeightDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		List<SysPermissionWeightDO> list = sysPermissionWeightService.list(obj, start, limit);
		int total = sysPermissionWeightService.getTotal(obj);
		resDs = DatasetService.getInstace().getDataset(list,SysPermissionWeightDO.class);
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
		String id = reqDs.getString("id");
		if (DataUtil.isNullStr(id)) {
			throw new BaseException(SysErr.E_MESSAGE, "输入项[id]不能空！");
		}
		SysPermissionWeightDO obj = sysPermissionWeightService.get(id);
		IDataset resDs = DatasetService.getInstace().getDataset(obj, SysPermissionWeightDO.class);
		chgDict(resDs, false);
		DatasetService.printDataset(reqDs);
		DatasetService.printDataset(resDs);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "明细查询交易成功！");
	}

	/**
	 * 权限组初始化
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value="initPermission")
	public void initPermission(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String isAll = reqDs.getString("isAll");
		boolean success = sysPermissionWeightService.initPermission(isAll);
		IDataset resDs = DatasetService.getInstace().getDataset();
		if(success){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "初始化成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "初始化失败！");
		}
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
		//新增是否加入权限计算中文描述列
		ds.addColumn("switchFlgStr", DatasetColumnType.DS_STRING);
		ds.addColumn("authLvlSwitchFlgStr", DatasetColumnType.DS_STRING);
		if(isAction){
			//新增操作描述列
			ds.addColumn("action", DatasetColumnType.DS_STRING);
		}
		ds.beforeFirst();
		while(ds.hasNext()){
			ds.next();
			//更新是否加入权限计算中文描述
			String switchFlg = ds.getString("switchFlg");
			ds.updateString("switchFlgStr", DictUtils.getDictLabels(switchFlg, "SWITCH_FLG", switchFlg));
			String authLvlSwitchFlg = ds.getString("authLvlSwitchFlg");
			ds.updateString("authLvlSwitchFlgStr", DictUtils.getDictLabels(authLvlSwitchFlg, "AUTH_LVL_SWITCH_FLG", authLvlSwitchFlg));
			
			if(isAction){
				/*添加相关操作按钮*/
				StringBuffer action = new StringBuffer();
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"detail('" + ds.getString("id") + "')\" >详情</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"update('" + ds.getString("id") + "')\" >修改</a>");
//				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"del('" + ds.getString("id") + "')\" >删除</a>");
				ds.updateString("action", action.toString());
			}
		}
	}
}