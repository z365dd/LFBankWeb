package com.adtec.sys.modules.sys.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.DatasetColumnType;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.modules.sys.service.OfficeService;
import com.adtec.sys.modules.sys.service.SystemService;
import com.adtec.sys.modules.sys.service.AreaService;
import com.adtec.sys.modules.sys.entity.TSysDictDO;
import com.adtec.sys.modules.sys.service.TSysDictService;

/**
 * 页面参数Controller
 * @author zh
 * @version 20200630
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/tSysDict")
public class TSysDictController extends BaseController {

	@Autowired
	private TSysDictService tSysDictService;
	
	/*首页页面路径*/
	private String PATH = "starring/sys/tSysDict";
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
	@RequestMapping(value ={"tSysDictList"})
	public String tSysDictList(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"List";
	}
	
	/**
	 * 进入新增页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tSysDictAdd"})
	public String tSysDictAdd(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"AddForm";
	}

	/**
	 * 进入新增页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tSysDictValAdd"})
	public String tSysDictValAdd(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"ValAddForm";
	}

	/**
	 * 进入修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tSysDictUpdate"})
	public String tSysDictUpdate(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"UpdateForm";
	}

	/**
	 * 进入修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tSysDictValUpdate"})
	public String tSysDictValUpdate(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"ValUpdateForm";
	}

	/**
	 * 进入详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tSysDictDetail"})
	public String tSysDictDetail(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"DetailForm";
	}

	/**
	 * 进入详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tSysDictValDetail"})
	public String tSysDictValDetail(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"ValDetailForm";
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
		TSysDictDO obj = DatasetService.getInstace().getObject(reqDs, TSysDictDO.class);
		if(tSysDictService.insert(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "新增交易成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "新增交易失败！");
		}
	}

	/**
	 * 新增交易
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value="insertVal")
	public void insertVal(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		TSysDictDO obj = DatasetService.getInstace().getObject(reqDs, TSysDictDO.class);
		if(tSysDictService.insertVal(obj)){
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
		TSysDictDO obj = DatasetService.getInstace().getObject(reqDs, TSysDictDO.class);
		if(tSysDictService.update(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "更新交易成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "更新交易失败！");
		}
	}

	/**
	 * 修改交易
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value="updateVal")
	public void updateVal(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		TSysDictDO obj = DatasetService.getInstace().getObject(reqDs, TSysDictDO.class);
		if(tSysDictService.updateVal(obj)){
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
		TSysDictDO obj = DatasetService.getInstace().getObject(reqDs, TSysDictDO.class);
		if(tSysDictService.delete(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "删除交易成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "删除交易失败！");
		}
	}

	/**
	 * 删除交易
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value="deleteVal")
	public void deleteVal(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		TSysDictDO obj = DatasetService.getInstace().getObject(reqDs, TSysDictDO.class);
		if(tSysDictService.deleteVal(obj)){
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
		TSysDictDO obj = DatasetService.getInstace().getObject(reqDs, TSysDictDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		if (obj == null) obj = new TSysDictDO();
		List<TSysDictDO> list = tSysDictService.list(obj, start, limit);
		int total = tSysDictService.getTotal(obj);
		resDs = DatasetService.getInstace().getDataset(list,TSysDictDO.class);
		chgDict(resDs, true);
		resDs.setTotalCount(total);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "列表查询交易成功！");
	}

	/**
	 * 列表查询
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value="listVal")
	public void listVal(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		TSysDictDO obj = DatasetService.getInstace().getObject(reqDs, TSysDictDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		List<TSysDictDO> list = tSysDictService.listVal(obj, start, limit);
		int total = tSysDictService.getTotalVal(obj);
		resDs = DatasetService.getInstace().getDataset(list,TSysDictDO.class);
		chgDictVal(resDs, true);
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
		String dictTp = reqDs.getString("dictTp");
		if (DataUtil.isNullStr(dictTp)) {
			throw new BaseException(SysErr.E_IN_NULL, "输入项[dictTp]不能空！");
		}

		TSysDictDO temp = new TSysDictDO();
		temp.setDictTp(dictTp);
		TSysDictDO obj = tSysDictService.get(temp);
		IDataset resDs = DatasetService.getInstace().getDataset(obj, TSysDictDO.class);
		chgDict(resDs, false);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "明细查询交易成功！");
	}

	/**
	 * 明细查询
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value="getVal")
	public void getVal(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String dictTp = reqDs.getString("dictTp");
		if (DataUtil.isNullStr(dictTp)) {
			throw new BaseException(SysErr.E_IN_NULL, "输入项[dictTp]不能空！");
		}
		String dictVal = reqDs.getString("dictVal");
		if (DataUtil.isNullStr(dictVal)) {
			throw new BaseException(SysErr.E_IN_NULL, "输入项[dictVal]不能空！");
		}
		TSysDictDO temp = new TSysDictDO();
		temp.setDictTp(dictTp);
		temp.setDictVal(dictVal);

		TSysDictDO obj = tSysDictService.getVal(temp);
		IDataset resDs = DatasetService.getInstace().getDataset(obj, TSysDictDO.class);
		chgDict(resDs, false);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "明细查询交易成功！");
	}

	@ResponseBody
	@RequestMapping(value="getSort")
	public void getSort(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String dictTp = reqDs.getString("dictTp");
		if (DataUtil.isNullStr(dictTp)) {
			throw new BaseException(SysErr.E_IN_NULL, "输入项[dictTp]不能空！");
		}
		TSysDictDO temp = new TSysDictDO();
		temp.setDictTp(dictTp);

		TSysDictDO obj = tSysDictService.getSort(temp);
		if (obj.getSort() == null) {
			obj.setSort(10L);
		} else {
			obj.setSort(obj.getSort() + 10);
		}
		IDataset resDs = DatasetService.getInstace().getDataset(obj, TSysDictDO.class);
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
				StringBuffer action = new StringBuffer();
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"detail(" +"'"+ds.getString("dictTp")+"'" + ")\" >详情</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"update(" +"'"+ds.getString("dictTp")+"'" + ")\" >修改</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"del(" +"'"+ds.getString("dictTp")+"'" + ")\" >删除</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"addVal(" +"'"+ds.getString("dictTp")+"','" + ds.getString("dictInfo")+"')\" >增加标签</a>");
				ds.updateString("action", action.toString());
			}
		}
	}

	/**
	 * 数字字典转换
	 * @param ds
	 * @param isAction
	 */
	private void chgDictVal(IDataset ds, boolean isAction){
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
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"detailVal(" +"'"+ds.getString("dictTp")+"','" + ds.getString("dictVal")+"')\" >详情</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"updateVal(" +"'"+ds.getString("dictTp")+"','" + ds.getString("dictVal")+"')\" >修改</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"delVal(" +"'"+ds.getString("dictTp")+"','" + ds.getString("dictVal")+"')\" >删除</a>");
				ds.updateString("action", action.toString());
			}
		}
	}
}