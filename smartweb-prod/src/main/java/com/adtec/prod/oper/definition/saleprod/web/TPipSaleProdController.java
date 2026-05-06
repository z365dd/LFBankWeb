/**
* 系统名称: SmartWeb平台
* 模块名称: comp-prod/oper/definition/saleprod拦截器模块
* 功能描述: 可售产品管理控制类
* 类 名 称  : TPipSaleProdController.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200104<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.prod.oper.definition.saleprod.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adtec.prod.oper.definition.adapter.service.TPipSaleProdAdapterService;
import com.adtec.prod.oper.definition.saleprod.entity.TPipSaleProdDOForMsmall;
import com.adtec.prod.oper.definition.saleprod.entity.TPipSaleProdAtomProdDO;
import com.adtec.prod.oper.definition.saleprod.entity.TPipSaleProdDO;
import com.adtec.prod.oper.definition.saleprod.service.TPipSaleProdService;
import com.google.common.collect.Lists;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adtec.framework.impl.share.dataset.CommonDatasets;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.DatasetColumnType;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.framework.interfaces.share.IDatasets;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.sys.common.web.BaseController;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.sys.modules.sys.service.OfficeService;
import com.adtec.sys.modules.sys.service.SystemService;
import com.adtec.sys.modules.sys.service.AreaService;

/**
 * 可售产品管理Controller
 * @author zengxj
 * @version 20200104
 */
@Controller
@RequestMapping(value = "${adminPath}/prod/oper/definition/saleprod/tPipSaleProd")
public class TPipSaleProdController extends BaseController {

	@Autowired
	private TPipSaleProdService tPipSaleProdService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private TPipSaleProdAdapterService tPipSaleProdAdapterService;
	
	/*首页页面路径*/
	private String PATH = (ParamUtil.getJspPath().split(ParamUtil.getConfig("web.view.prefix"))[1])+"/"+("prod/oper/definition/saleprod/tPipSaleProd.jsp".replace(ParamUtil.getConfig("web.view.suffix"),""));
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
	@RequestMapping(value ={"tPipSaleProdList"})
	public String tPipSaleProdList(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"List";
	}
	
	/**
	 * 进入新增页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tPipSaleProdAdd"})
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
	@RequestMapping(value ={"tPipSaleProdUpdate"})
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
	@RequestMapping(value ={"tPipSaleProdDetail"})
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
		TPipSaleProdDO obj = DatasetService.getInstace().getObject(reqDs, TPipSaleProdDO.class);
		String atomProdJson = reqDs.getString("atomProdJson");
		if(tPipSaleProdService.insert(obj, atomProdJson)){
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
		TPipSaleProdDO obj = DatasetService.getInstace().getObject(reqDs, TPipSaleProdDO.class);
		String atomProdJson = reqDs.getString("atomProdJson");
		if(tPipSaleProdService.update(obj, atomProdJson)){
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
		TPipSaleProdDO obj = DatasetService.getInstace().getObject(reqDs, TPipSaleProdDO.class);
		if(tPipSaleProdService.delete(obj)){
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
		if (obj == null){
			obj = new TPipSaleProdDO();
		}
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		List<TPipSaleProdDO> list = tPipSaleProdService.list(obj, start, limit);
		int total = tPipSaleProdService.getTotal(obj);
		resDs = DatasetService.getInstace().getDataset(list,TPipSaleProdDO.class);
		chgDict(resDs, true);
		resDs.setTotalCount(total);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "列表查询交易成功！");
	}

	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value="listNotPackage")
	public void listNotPackage(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		TPipSaleProdDO obj = DatasetService.getInstace().getObject(reqDs, TPipSaleProdDO.class);
		if (obj == null){
			obj = new TPipSaleProdDO();
		}
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		List<TPipSaleProdDO> list = tPipSaleProdService.list(obj, start, limit);

		// 查询已经包装的可售产品
		List<TPipSaleProdDO> list1 = tPipSaleProdAdapterService.list(obj, start, limit);
		List<TPipSaleProdDO> newList = Lists.newArrayList();

		// 排除已经包装的可售产品
		for (int i=0;i<list.size();i++) {
			boolean isPackage = false;
			for (TPipSaleProdDO temp : list1) {
				if (temp.getSaleProdCode().equals(list.get(i).getSaleProdCode())) {
					isPackage = true;
					break;
				}
			}
			if (!isPackage) {
				newList.add(list.get(i));
			}
		}

		resDs = DatasetService.getInstace().getDataset(newList,TPipSaleProdDO.class);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "列表查询交易成功！");
	}

	/**
	 * 列表查询
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value="listForMsmall")
	public void listForMsmall(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		TPipSaleProdDOForMsmall obj = DatasetService.getInstace().getObject(reqDs, TPipSaleProdDOForMsmall.class);
		if (obj == null){
			obj = new TPipSaleProdDOForMsmall();
		}
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		List<TPipSaleProdDOForMsmall> list = tPipSaleProdService.listForMsmall(obj, start, limit);
		int total = tPipSaleProdService.getTotalForMsmall(obj);
		resDs = DatasetService.getInstace().getDataset(list,TPipSaleProdDOForMsmall.class);
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
			throw new BaseException(SysErr.E_MESSAGE, "输入项[可售产品编号]不能空！");
		}
		TPipSaleProdDO obj = tPipSaleProdService.get(id);
		if(!DataUtil.isNullStr(obj.getBrchId())) {
			obj.setBrchIdName(officeService.get(obj.getBrchId()).getName());
		}
		List<TPipSaleProdAtomProdDO> list = tPipSaleProdService.getSaleAtomList(id);
		IDataset saleDetailDs = DatasetService.getInstace().getDataset(obj, TPipSaleProdDO.class);
		IDataset saleAtomListDs = DatasetService.getInstace().getDataset(list, TPipSaleProdAtomProdDO.class);
//		chgDict(resDs, false);
		saleDetailDs.setDatasetName("saleDetailDs");
		saleAtomListDs.setDatasetName("saleAtomListDs");
		IDatasets resDss = new CommonDatasets(); 
		resDss.putDataset(saleDetailDs);
		resDss.putDataset(saleAtomListDs);
		setResponseDataset(request, response, resDss, SysErr.E_SUCCESS, "明细查询交易成功！");
	}

	/**
	 * 可售产品关联原子产品列表查询
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value="listAtomForMsmall")
	public void listAtomForMsmall(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		TPipSaleProdAtomProdDO tPipSaleProdAtomProdDO = DatasetService.getInstace().getObject(reqDs, TPipSaleProdAtomProdDO.class);

		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		List<TPipSaleProdAtomProdDO> list = tPipSaleProdService.getSaleAtomList(tPipSaleProdAtomProdDO, start, limit);
		IDataset saleAtomListDs = DatasetService.getInstace().getDataset(list, TPipSaleProdAtomProdDO.class);
		int total = tPipSaleProdService.getTotalAtom(tPipSaleProdAtomProdDO);
		setResponseDataset(request, response, saleAtomListDs, SysErr.E_SUCCESS, "列表查询成功！");
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
		ds.beforeFirst();
		while(ds.hasNext()){
			ds.next();

			if(isAction){
				/*添加相关操作按钮*/
				StringBuffer action = new StringBuffer();
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"detail('" + ds.getString("saleProdCode") + "')\" >详情</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"update('" + ds.getString("saleProdCode") + "')\" >修改</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"del('" + ds.getString("saleProdCode") + "')\" >删除</a>");
				ds.updateString("action", action.toString());
			}
		}
	}
}