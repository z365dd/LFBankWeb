/**
* 系统名称: SmartWeb平台
* 模块名称: comp-prod/oper/definition/atom拦截器模块
* 功能描述: 原子产品控制类
* 类 名 称  : TPipAtomProdController.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200102<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.prod.oper.definition.atom.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adtec.prod.oper.definition.atom.entity.TPipAtomProdDO;
import com.adtec.prod.oper.definition.atom.service.TPipAtomProdService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.DatasetColumnType;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.sys.common.web.BaseController;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.sys.modules.sys.service.OfficeService;
import com.adtec.sys.modules.sys.service.SystemService;
import com.adtec.sys.modules.sys.service.AreaService;

/**
 * 原子产品Controller
 * @author zengxj
 * @version 20200102
 */
@Controller
@RequestMapping(value = "${adminPath}/prod/oper/definition/atom/tPipAtomProd")
public class TPipAtomProdController extends BaseController {

	@Autowired
	private TPipAtomProdService tPipAtomProdService;
	
	/*首页页面路径*/
	private String PATH = (ParamUtil.getJspPath().split(ParamUtil.getConfig("web.view.prefix"))[1])+"/"+("prod/oper/definition/atom/tPipAtomProd.jsp".replace(ParamUtil.getConfig("web.view.suffix"),""));
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
	@RequestMapping(value ={"tPipAtomProdList"})
	public String tPipAtomProdList(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"List";
	}
	
	/**
	 * 进入新增页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tPipAtomProdAdd"})
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
	@RequestMapping(value ={"tPipAtomProdUpdate"})
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
	@RequestMapping(value ={"tPipAtomProdDetail"})
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
		DatasetService.printDataset(reqDs);
		TPipAtomProdDO obj = DatasetService.getInstace().getObject(reqDs, TPipAtomProdDO.class);
		String data = reqDs.getString("data");
		if(tPipAtomProdService.insert(obj, data)){
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
		TPipAtomProdDO obj = DatasetService.getInstace().getObject(reqDs, TPipAtomProdDO.class);
		String data = reqDs.getString("data");
		if(tPipAtomProdService.update(obj, data)){
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
//		TPipAtomProdDO obj = DatasetService.getInstace().getObject(reqDs, TPipAtomProdDO.class);
		String atomProdCode = reqDs.getString("id");
		if(tPipAtomProdService.delete(atomProdCode)){
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
		TPipAtomProdDO obj = DatasetService.getInstace().getObject(reqDs, TPipAtomProdDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		List<TPipAtomProdDO> list = tPipAtomProdService.list(obj, start, limit);
		int total = tPipAtomProdService.getTotal(obj);
		resDs = DatasetService.getInstace().getDataset(list,TPipAtomProdDO.class);
		chgDict(resDs, true);
		resDs.setTotalCount(total);
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
		TPipAtomProdDO obj = DatasetService.getInstace().getObject(reqDs, TPipAtomProdDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		List<TPipAtomProdDO> list = tPipAtomProdService.list(obj, start, limit);
		int total = tPipAtomProdService.getTotal(obj);
		resDs = DatasetService.getInstace().getDataset(list,TPipAtomProdDO.class);
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
			throw new BaseException(SysErr.E_MESSAGE, "输入项[原子产品编号]不能空！");
		}
		TPipAtomProdDO obj = tPipAtomProdService.get(id);
		IDataset resDs = DatasetService.getInstace().getDataset(obj, TPipAtomProdDO.class);
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
		ds.beforeFirst();
		while(ds.hasNext()){
			ds.next();
			
			if(isAction){
				/*添加相关操作按钮*/
				String id = ds.getString("atomProdCode");//ATOM_PROD_CODE
				StringBuffer action = new StringBuffer();
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"detail('" + id + "')\" >详情</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"update('" + id + "')\" >修改</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"del('" + id + "')\" >删除</a>");
				ds.updateString("action", action.toString());
			}
		}
	}
}