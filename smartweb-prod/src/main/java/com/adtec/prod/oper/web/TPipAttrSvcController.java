/**
* 系统名称: SmartWeb平台
* 模块名称: comp.prod.oper拦截器模块
* 功能描述: 服务属性控制类
* 类 名 称  : TPipSvcController.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200108<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.prod.oper.web;

import com.adtec.prod.oper.entity.TPipCompSvcParaDO;
import com.adtec.prod.oper.entity.TPipCompSvcParaDOTemp;
import com.adtec.prod.oper.entity.TPipSvcCompScenDO;
import com.adtec.prod.oper.entity.TPipSvcDO;
import com.adtec.prod.oper.service.ProdAttrService;
import com.adtec.prod.oper.service.TPipAttrSvcService;
import com.adtec.framework.impl.share.dataset.CommonDatasets;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.DatasetColumnType;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.framework.interfaces.share.IDatasets;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.sys.common.web.BaseController;
import com.adtec.framework.exception.SysErr;
import com.adtec.sys.modules.sys.service.AreaService;
import com.adtec.sys.modules.sys.service.OfficeService;
import com.adtec.sys.modules.sys.service.SystemService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务属性Controller
 * @author zh
 * @version 20200108
 */
@Controller
@RequestMapping(value = "${adminPath}/prod/oper/tPipAttrSvc")
public class TPipAttrSvcController extends BaseController {
	@Autowired
	private ProdAttrService prodAttrService;

	@Autowired
	private TPipAttrSvcService tPipAttrSvcService;
	
	/*首页页面路径*/
	private String PATH = (ParamUtil.getJspPath().split(ParamUtil.getConfig("web.view.prefix"))[1])+"/"+("prod/oper/tPipAttrSvc.jsp".replace(ParamUtil.getConfig("web.view.suffix"),""));
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
	@RequestMapping(value ={"tPipAttrSvcList"})
	public String tPipAttrSvcList(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"List";
	}
	
	/**
	 * 进入新增页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tPipAttrSvcAdd"})
	public String tPipAttrSvcAdd(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"AddForm";
	}
	
	/**
	 * 进入修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tPipAttrSvcUpdate"})
	public String tPipAttrSvcUpdate(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"UpdateForm";
	}
	
	/**
	 * 进入详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tPipAttrSvcDetail"})
	public String tPipAttrSvcDetail(HttpServletRequest request, HttpServletResponse response) {
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
		TPipSvcDO obj = DatasetService.getInstace().getObject(reqDs, TPipSvcDO.class);
		String rowPara = reqDs.getString("rowPara");
		String rowScen = reqDs.getString("rowScen");
		if(tPipAttrSvcService.insert(obj, rowPara, rowScen)){
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
		TPipSvcDO obj = DatasetService.getInstace().getObject(reqDs, TPipSvcDO.class);
		obj.setCompNo(reqDs.getString("temp1"));
		obj.setSvcCode(reqDs.getString("temp2"));
		String rowPara = reqDs.getString("rowPara");
		String rowScen = reqDs.getString("rowScen");

		if(tPipAttrSvcService.delete(obj) && tPipAttrSvcService.insert(obj, rowPara, rowScen)){
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
		TPipSvcDO obj = DatasetService.getInstace().getObject(reqDs, TPipSvcDO.class);
		if(tPipAttrSvcService.delete(obj)){
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
		TPipCompSvcParaDO obj = DatasetService.getInstace().getObject(reqDs, TPipCompSvcParaDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		List<TPipCompSvcParaDO> list = tPipAttrSvcService.list(obj, start, limit);
		int total = tPipAttrSvcService.getTotal(obj);
		resDs = DatasetService.getInstace().getDataset(list,TPipCompSvcParaDO.class);
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
//		String id = reqDs.getString("id");
//		if (DataUtil.isNullStr(id)) {
//			throw new BaseException(SysErr.E_MESSAGE, "输入项[id]不能空！");
//		}
		String compNo = reqDs.getString("compNo");
		String svcCode = reqDs.getString("svcCode");
		TPipSvcDO tPipSvcDO = new TPipSvcDO();
		tPipSvcDO.setCompNo(compNo);
		tPipSvcDO.setSvcCode(svcCode);
		TPipSvcDO obj = tPipAttrSvcService.get(tPipSvcDO);
		IDataset tPipSvcDODs = DatasetService.getInstace().getDataset(obj, TPipSvcDO.class);
		tPipSvcDODs.setDatasetName("tPipSvcDODs");

		TPipCompSvcParaDO tPipCompSvcParaDO = new TPipCompSvcParaDO();
		tPipCompSvcParaDO.setCompNo(compNo);
		tPipCompSvcParaDO.setSvcCode(svcCode);
		List<TPipCompSvcParaDO> list = tPipAttrSvcService.listPara(tPipCompSvcParaDO, 0, 0);
		List<TPipCompSvcParaDOTemp> listTemp = new ArrayList<>();
		for (TPipCompSvcParaDO t : list) {
			TPipCompSvcParaDOTemp tPipCompSvcParaDOTemp = new TPipCompSvcParaDOTemp();
			tPipCompSvcParaDOTemp.setKEY_NO(t.getKeyNo());
			tPipCompSvcParaDOTemp.setKEY_NAME(t.getKeyName());
			listTemp.add(tPipCompSvcParaDOTemp);
		}
		IDataset tPipCompSvcParaDODs = DatasetService.getInstace().getDataset(listTemp, TPipCompSvcParaDOTemp.class);
		tPipCompSvcParaDODs.setDatasetName("tPipCompSvcParaDODs");

		TPipSvcCompScenDO tPipSvcCompScenDO = new TPipSvcCompScenDO();
		tPipSvcCompScenDO.setCompNo(compNo);
		tPipSvcCompScenDO.setSvcCode(svcCode);
		List<TPipSvcCompScenDO> list2 = tPipAttrSvcService.listScen(tPipSvcCompScenDO, 0, 0);
		IDataset tPipSvcCompScenDODs = DatasetService.getInstace().getDataset(list2, TPipSvcCompScenDO.class);
		tPipSvcCompScenDODs.setDatasetName("tPipSvcCompScenDODs");

		IDatasets resDss = new CommonDatasets();
		resDss.putDataset(tPipSvcDODs);
		resDss.putDataset(tPipCompSvcParaDODs);
		resDss.putDataset(tPipSvcCompScenDODs);
		//		chgDict(resDs, false);
		setResponseDataset(request, response, resDss, SysErr.E_SUCCESS, "明细查询交易成功！");
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
		//新增组件号中文描述列
//		ds.addColumn("compName", DatasetColumnType.DS_STRING);
		if(isAction){
			//新增操作描述列
			ds.addColumn("action", DatasetColumnType.DS_STRING);
		}
		ds.beforeFirst();
		while(ds.hasNext()){
			ds.next();
			//更新组件号中文描述
//			String compNo = ds.getString("compNo");
//			ProdAttrDO prodAttrDO = new ProdAttrDO();
//			prodAttrDO.setCOMP_NO(compNo);
//			ds.updateString("compName", prodAttrService.get(prodAttrDO).getCOMP_NAME());
			
			if(isAction){
				/*添加相关操作按钮*/
				StringBuffer action = new StringBuffer();
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"detail('" + ds.getString("compNo") + "', '" + ds.getString("svcCode") + "')\" >详情</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"update('" + ds.getString("compNo") + "', '" + ds.getString("svcCode") + "')\" >修改</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"del('" + ds.getString("compNo") + "', '" + ds.getString("svcCode") + "')\" >删除</a>");
				ds.updateString("action", action.toString());
			}
		}
	}
}