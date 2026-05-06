/**
* 系统名称: SmartWeb平台
* 模块名称: sys-modules拦截器模块
* 功能描述: 请求IP信息表控制类
* 类 名 称  : SysReqIpMsgController.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20211021<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.sys.modules.sys.web;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.DatasetColumnType;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.modules.sys.entity.SysReqIpMsgDO;
import com.adtec.sys.modules.sys.service.SysReqIpMsgService;
import com.adtec.sys.modules.sys.utils.DictUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 请求IP信息表Controller
 * @author zengxj
 * @version 20211021
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/modules/sysReqIpMsg")
public class SysReqIpMsgController extends BaseController {

	@Autowired
	private SysReqIpMsgService sysReqIpMsgService;
	
	
	/*首页页面路径*/
	private String PATH = "starring/sys/modules/sysReqIpMsg";
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
	@RequestMapping(value ={"sysReqIpMsgList"})
	public String sysReqIpMsgList(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"List";
	}
	
	/**
	 * 进入新增页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"sysReqIpMsgAdd"})
	public String sysReqIpMsgAdd(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"AddForm";
	}
	
	/**
	 * 进入修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"sysReqIpMsgUpdate"})
	public String sysReqIpMsgUpdate(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"UpdateForm";
	}
	
	/**
	 * 进入详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"sysReqIpMsgDetail"})
	public String sysReqIpMsgDetail(HttpServletRequest request, HttpServletResponse response) {
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
		SysReqIpMsgDO obj = DatasetService.getInstace().getObject(reqDs, SysReqIpMsgDO.class);
		sysReqIpMsgService.insert(obj);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "新增交易成功！");
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
		SysReqIpMsgDO obj = DatasetService.getInstace().getObject(reqDs, SysReqIpMsgDO.class);
		sysReqIpMsgService.update(obj);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "更新交易成功！");
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
		SysReqIpMsgDO obj = DatasetService.getInstace().getObject(reqDs, SysReqIpMsgDO.class);
		sysReqIpMsgService.delete(obj);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "删除交易成功！");
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
		SysReqIpMsgDO obj = DatasetService.getInstace().getObject(reqDs, SysReqIpMsgDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		List<SysReqIpMsgDO> list = sysReqIpMsgService.list(obj, start, limit);
		int total = sysReqIpMsgService.getTotal(obj);
		resDs = DatasetService.getInstace().getDataset(list,SysReqIpMsgDO.class);
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
			throw new BaseException(SysErr.E_IN_NULL, "输入项[id]不能空！");
		}		

		SysReqIpMsgDO obj = sysReqIpMsgService.get(id);
		IDataset resDs = DatasetService.getInstace().getDataset(obj, SysReqIpMsgDO.class);
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

		ds.addColumn("statusStr", DatasetColumnType.DS_STRING);
		if(isAction){
			//新增操作描述列
			ds.addColumn("action", DatasetColumnType.DS_STRING);
		}
		ds.beforeFirst();
		while(ds.hasNext()){
			ds.next();
			String status = ds.getString("status");
			ds.updateString("statusStr", DictUtils.getDictLabels(status, "REQ_STATUS", status));
			if(isAction){
				/*添加相关操作按钮*/
				StringBuffer action = new StringBuffer();
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"detail(" +"'"+ds.getString("id")+"'" + ")\" >详情</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"update(" +"'"+ds.getString("id")+"'" + ")\" >修改</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"del(" +"'"+ds.getString("id")+"'" + ")\" >删除</a>");
				ds.updateString("action", action.toString());
			}
		}
	}
}