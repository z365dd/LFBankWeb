/**
* 系统名称: SmartWeb平台
* 模块名称: comp.ctrl.oper拦截器模块
* 功能描述: 业务机构开通控制类
* 类 名 称  : CtrlTPipBusiBrchOpenController.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200324<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.comp.ctrl.oper.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adtec.comp.ctrl.oper.entity.CtrlTParaBrchDO;
import com.adtec.comp.ctrl.oper.entity.CtrlTPipBusiBrchOpenDO;
import com.adtec.comp.ctrl.oper.entity.CtrlTPipBusiDO;
import com.adtec.comp.ctrl.oper.service.CtrlTPipBusiBrchOpenService;
import com.adtec.comp.ctrl.oper.util.CompCtrlUtil;
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

/**
 * 业务机构开通Controller
 * 
 * @author zhengjt
 * @version 20200324
 */
@Controller
@RequestMapping(value = "${adminPath}/comp/ctrl/oper/ctrlTPipBusiBrchOpen")
public class CtrlTPipBusiBrchOpenController extends BaseController {

	@Autowired
	private CtrlTPipBusiBrchOpenService ctrlTPipBusiBrchOpenService;

	/* 首页页面路径 */
	private String PATH = "starring/comp/ctrl/oper/ctrlTPipBusiBrchOpen";

	/**
	 * 进入管理页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
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
	@RequestMapping(value = { "ctrlTPipBusiBrchOpenList" })
	public String ctrlTPipBusiBrchOpenList(HttpServletRequest request, HttpServletResponse response) {
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
	@RequestMapping(value = { "ctrlTPipBusiBrchOpenAdd" })
	public String ctrlTPipBusiBrchOpenAdd(HttpServletRequest request, HttpServletResponse response) {
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
	@RequestMapping(value = { "ctrlTPipBusiBrchOpenUpdate" })
	public String ctrlTPipBusiBrchOpenUpdate(HttpServletRequest request, HttpServletResponse response) {
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
	@RequestMapping(value = { "ctrlTPipBusiBrchOpenDetail" })
	public String ctrlTPipBusiBrchOpenDetail(HttpServletRequest request, HttpServletResponse response) {
		return PATH + "DetailForm";
	}

	/**
	 * 新增交易
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value = "insert")
	public void insert(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String busiNo = reqDs.getString("busiNo");
		String brchStr = reqDs.getString("brch");
		String flg = reqDs.getString("flg");
		String[] strArray = null;
		strArray = brchStr.split(",");
		boolean Flg = true;
		boolean exist = false;
		for(int i = 0; i < strArray.length; i++){
			CtrlTPipBusiBrchOpenDO obj = new CtrlTPipBusiBrchOpenDO();
			obj.setBusiNo(busiNo);
			obj.setBrch(strArray[i]);
			CtrlTPipBusiBrchOpenDO ctrlTPipBusiBrchOpenDO = ctrlTPipBusiBrchOpenService.get(obj);
			if(ctrlTPipBusiBrchOpenDO!=null){
				exist=true;
			}
		}
		if(exist){
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "此业务机构已存在");
			return;
		}
		for (int i = 0; i < strArray.length; i++) {
			CtrlTPipBusiBrchOpenDO obj = new CtrlTPipBusiBrchOpenDO();
			obj.setBusiNo(busiNo);
			obj.setBrch(strArray[i]);
			obj.setFlg(flg);
			if (!ctrlTPipBusiBrchOpenService.insert(obj)) {
				Flg = false;
			}
		}
		if (Flg) {
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "新增交易成功！");
			// 调用参数缓存刷新
		    CompCtrlUtil.redisRefreshByTableName(CompCtrlUtil.TABLE_PARA_BRCH_OPEN);
		} else {
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "新增交易失败！");
		}
		/*
		 * CtrlTPipBusiBrchOpenDO obj =
		 * DatasetService.getInstace().getObject(reqDs,
		 * CtrlTPipBusiBrchOpenDO.class);
		 * if(ctrlTPipBusiBrchOpenService.insert(obj)){
		 * setResponseDataset(request, response, resDs, SysErr.E_SUCCESS,
		 * "新增交易成功！"); }else{ setResponseDataset(request, response, resDs,
		 * SysErr.E_DEFAULT, "新增交易失败！"); }
		 */
	}

	/**
	 * 修改标志交易
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value = "updateFlg")
	public void updateFlg(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		CtrlTPipBusiBrchOpenDO obj = DatasetService.getInstace().getObject(reqDs, CtrlTPipBusiBrchOpenDO.class);
		if (ctrlTPipBusiBrchOpenService.update(obj)) {
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "更新交易成功！");
			// 调用参数缓存刷新
		    CompCtrlUtil.redisRefreshByTableName(CompCtrlUtil.TABLE_PARA_BRCH_OPEN);
		} else {
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "更新交易失败！");
		}
	}

	/**
	 * 修改交易
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String ori_brch = reqDs.getString("ORI_brch");
		String busiNo = reqDs.getString("busiNo");
		String brch = reqDs.getString("brch");
		String flg = reqDs.getString("flg");
		CtrlTPipBusiBrchOpenDO obj = new CtrlTPipBusiBrchOpenDO();
		obj.setBusiNo(busiNo);
		obj.setBrch(brch);
		obj.setFlg(flg);
		if (ctrlTPipBusiBrchOpenService.update(obj, ori_brch)) {
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "更新交易成功！");
			// 调用参数缓存刷新
		    CompCtrlUtil.redisRefreshByTableName(CompCtrlUtil.TABLE_PARA_BRCH_OPEN);
		} else {
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "更新交易失败！");
		}
	}

	/**
	 * 删除交易
	 * 
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		CtrlTPipBusiBrchOpenDO obj = DatasetService.getInstace().getObject(reqDs, CtrlTPipBusiBrchOpenDO.class);
		if (ctrlTPipBusiBrchOpenService.delete(obj)) {
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "删除交易成功！");
			// 调用参数缓存刷新
		    CompCtrlUtil.redisRefreshByTableName(CompCtrlUtil.TABLE_PARA_BRCH_OPEN);
		} else {
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "删除交易失败！");
		}
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
		CtrlTPipBusiBrchOpenDO obj = DatasetService.getInstace().getObject(reqDs, CtrlTPipBusiBrchOpenDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		List<CtrlTPipBusiBrchOpenDO> list = ctrlTPipBusiBrchOpenService.list(obj, start, limit);
		int total = ctrlTPipBusiBrchOpenService.getTotal(obj);
		resDs = DatasetService.getInstace().getDataset(list, CtrlTPipBusiBrchOpenDO.class);
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
		String busiNo = reqDs.getString("busiNo");
		if (DataUtil.isNullStr(busiNo)) {
			throw new BaseException(SysErr.E_IN_NULL, "输入项[busiNo]不能空！");
		}
		String brch = reqDs.getString("brch");
		if (DataUtil.isNullStr(brch)) {
			throw new BaseException(SysErr.E_IN_NULL, "输入项[brch]不能空！");
		}

		CtrlTPipBusiBrchOpenDO obj = ctrlTPipBusiBrchOpenService.get(busiNo, brch);
		IDataset resDs = DatasetService.getInstace().getDataset(obj, CtrlTPipBusiBrchOpenDO.class);
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
		// 新增业务编号中文描述列
		ds.addColumn("busiNoStr", DatasetColumnType.DS_STRING);
		// 新增机构中文描述列
		ds.addColumn("brchStr", DatasetColumnType.DS_STRING);
		// 新增标志中文描述列
		ds.addColumn("flgStr", DatasetColumnType.DS_STRING);
		if (isAction) {
			// 新增操作描述列
			ds.addColumn("action", DatasetColumnType.DS_STRING);
		}
		ds.beforeFirst();
		while (ds.hasNext()) {
			ds.next();
			// 更新业务编号中文描述
			String busiNo = ds.getString("busiNo");
			CtrlTPipBusiDO ctrlTPipBusiDO = ctrlTPipBusiBrchOpenService.getBusiName(busiNo);
			if (ctrlTPipBusiDO != null) {
				ds.updateString("busiNoStr", ctrlTPipBusiDO.getBusiName());
			} else {
				ds.updateString("busiNoStr", busiNo);
			}
			// 更新机构中文描述
			String brch = ds.getString("brch");
			CtrlTParaBrchDO ctrlTPipBrchDO = ctrlTPipBusiBrchOpenService.getBrchName(brch);
			if (ctrlTPipBrchDO != null) {
				ds.updateString("brchStr", ctrlTPipBrchDO.getBrchName());
			} else {
				ds.updateString("brchStr", brch);
			}
			// 更新标志中文描述
			String flg = ds.getString("flg");
			ds.updateString("flgStr", DictUtils.getDictLabels(flg, "OPEN_STAT", flg));

			if (isAction) {
				/* 添加相关操作按钮 */
				StringBuffer action = new StringBuffer();
				if (flg.equals("Y")) { 
					action.append("	<a href=\"JavaScript:void(0);\" onClick=\"closeFlg(" + "'" + ds.getString("busiNo")
					+ "', " + "'" + ds.getString("brch") + "'" + ")\" >关闭</a>");
				} else if (flg.equals("N")){
					action.append("	<a href=\"JavaScript:void(0);\" onClick=\"openFlg(" + "'" + ds.getString("busiNo")
					+ "', " + "'" + ds.getString("brch") + "'" + ")\" >开通</a>");
				}
				/*action.append("	<a href=\"JavaScript:void(0);\" onClick=\"detail(" + "'" + ds.getString("busiNo")
						+ "', " + "'" + ds.getString("brch") + "'" + ")\" >详情</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"update(" + "'" + ds.getString("busiNo")
						+ "', " + "'" + ds.getString("brch") + "'" + ")\" >修改</a>");*/
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"del(" + "'" + ds.getString("busiNo") + "', "
						+ "'" + ds.getString("brch") + "'" + ")\" >删除</a>");
				ds.updateString("action", action.toString());
			}
		}
	}
}