/**
* 系统名称: SmartWeb平台
* 模块名称: sys-modules拦截器模块
* 功能描述: 用户账号控制类
* 类 名 称  : SysUserAcctController.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20210415<br>
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
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.DatasetColumnType;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.entity.UserAcctDO;
import com.adtec.sys.modules.sys.service.AreaService;
import com.adtec.sys.modules.sys.service.OfficeService;
import com.adtec.sys.modules.sys.service.SystemService;
import com.adtec.sys.modules.sys.service.UserAcctService;
import com.adtec.sys.modules.sys.utils.DictUtils;

/**
 * 用户账号Controller
 * 
 * @author lijb
 * @version 20210415
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/modules/sysUserAcct")
public class UserAcctController extends BaseController {

	@Autowired
	private UserAcctService sysUserAcctService;

	/* 首页页面路径 */
	private String PATH = "starring/sys/modules/sysUserAcct";

	/**
	 * 进入管理页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = { "manage", "" })
	public String index(HttpServletRequest request, HttpServletResponse response) {
		return PATH + "Manage";
	}

	/**
	 * 列表页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "sysUserAcctList" })
	public String sysUserAcctList(HttpServletRequest request, HttpServletResponse response) {
		return PATH + "List";
	}

	/**
	 * 进入新增页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "sysUserAcctAdd" })
	public String sysUserAcctAdd(HttpServletRequest request, HttpServletResponse response) {
		return PATH + "AddForm";
	}

	/**
	 * 进入修改页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "sysUserAcctUpdate" })
	public String sysUserAcctUpdate(HttpServletRequest request, HttpServletResponse response) {
		return PATH + "UpdateForm";
	}

	/**
	 * 进入详细页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "sysUserAcctDetail" })
	public String sysUserAcctDetail(HttpServletRequest request, HttpServletResponse response) {
		return PATH + "DetailForm";
	}

	/**
	 * 新增交易
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "insert")
	public void insert(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		UserAcctDO obj = DatasetService.getInstace().getObject(reqDs, UserAcctDO.class);
		sysUserAcctService.insert(obj);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "新增交易成功！");
	}

	/**
	 * 修改交易
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		UserAcctDO obj = DatasetService.getInstace().getObject(reqDs, UserAcctDO.class);
		sysUserAcctService.update(obj);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "更新交易成功！");
	}

	/**
	 * 删除交易
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		UserAcctDO obj = DatasetService.getInstace().getObject(reqDs, UserAcctDO.class);
		sysUserAcctService.delete(obj);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "删除交易成功！");
	}

	/**
	 * 列表查询
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value = "list")
	public void list(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		UserAcctDO obj = DatasetService.getInstace().getObject(reqDs, UserAcctDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		List<UserAcctDO> list = sysUserAcctService.list(obj, start, limit);
		int total = sysUserAcctService.getTotal(obj);
		resDs = DatasetService.getInstace().getDataset(list, UserAcctDO.class);
		chgDict(resDs, true);
		resDs.setTotalCount(total);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "列表查询交易成功！");
	}

	/**
	 * 明细查询
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "get")
	public void get(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String id = reqDs.getString("id");
		if (DataUtil.isNullStr(id)) {
			throw new BaseException(SysErr.E_IN_NULL, "输入项[id]不能空！");
		}

		UserAcctDO obj = sysUserAcctService.get(id);
		IDataset resDs = DatasetService.getInstace().getDataset(obj, UserAcctDO.class);
		chgDict(resDs, false);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "明细查询交易成功！");
	}

	/**
	 * 数字字典转换
	 * 
	 * @param ds
	 * @param isAction
	 */
	private void chgDict(IDataset ds, boolean isAction) {
		if (null == ds) {
			return;
		}
		SystemService systemService = SpringContextHolder.getBean("systemService");
		OfficeService officeService = SpringContextHolder.getBean("officeService");
		AreaService areaService = SpringContextHolder.getBean("areaService");
		// 新增用户ID中文名称列
		ds.addColumn("userIdName", DatasetColumnType.DS_STRING);
		// 新增用户账号类型中文描述列
		ds.addColumn("userAcctTpStr", DatasetColumnType.DS_STRING);
		if (isAction) {
			// 新增操作描述列
			ds.addColumn("action", DatasetColumnType.DS_STRING);
		}
		ds.beforeFirst();
		while (ds.hasNext()) {
			ds.next();
			String userId = ds.getString("userId");
			String userIdName = userId;
			User userIdUser = systemService.getUser(userId);
			if (null != userIdUser) {
				userIdName = userIdUser.getName();
			}
			ds.updateString("userIdName", userIdName);
			// 更新用户账号类型中文描述
			String userAcctTp = ds.getString("userAcctTp");
			ds.updateString("userAcctTpStr", DictUtils.getDictLabels(userAcctTp, "", userAcctTp));

			if (isAction) {
				/* 添加相关操作按钮 */
				/*String action = ActionsBuilder.create()
						.action("修改密码", "btnUpdateClick", ds.getString("id"), userAcctTp,
								ds.getString("loginName"))
						.action("删除", "del", ds.getString("id")).build();*/
				StringBuffer action = new StringBuffer();
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"btnUpdateClick('" + ds.getString("id") + "','" + userAcctTp + "','" + ds.getString("loginName") + "')\" >修改密码</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"del('" + ds.getString("id") + "')\" >删除</a>");
				ds.updateString("action", action.toString());
			}
		}
	}
}