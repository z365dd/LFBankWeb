/**
* 系统名称: SmartWeb平台
* 模块名称: comp.ctrl.oper拦截器模块
* 功能描述: 法人管理控制类
* 类 名 称  : CtrlTParaLegaController.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200310<br>
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.adtec.framework.exception.SysErr;
import com.adtec.sys.modules.sys.utils.DictUtils;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.sys.modules.sys.entity.Office;
import com.adtec.sys.modules.sys.entity.Role;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.entity.Area;
import com.adtec.sys.modules.sys.service.OfficeService;
import com.adtec.sys.modules.sys.service.SystemService;
import com.adtec.sys.modules.sys.service.AreaService;
import com.adtec.comp.ctrl.oper.dao.CtrlTParaBrchDao;
import com.adtec.comp.ctrl.oper.entity.CtrlTParaBrchDO;
import com.adtec.comp.ctrl.oper.entity.CtrlTParaLegaAndTntDO;
import com.adtec.comp.ctrl.oper.entity.CtrlTParaLegaDO;
import com.adtec.comp.ctrl.oper.entity.CtrlTPipBusiBrchOpenDO;
import com.adtec.comp.ctrl.oper.service.CtrlTParaLegaService;
import com.adtec.comp.ctrl.oper.util.CompCtrlUtil;

/**
 * 法人管理Controller
 * @author zhengjt
 * @version 20200310
 */
@Controller
@RequestMapping(value = "${adminPath}/comp/ctrl/oper/ctrlTParaLega")
public class CtrlTParaLegaController extends BaseController {

	@Autowired
	private CtrlTParaLegaService ctrlTParaLegaService;
	@Autowired
	private CtrlTParaBrchDao ctrlTparaBrahDao;
	
	/*首页页面路径*/
	private String PATH = "starring/comp/ctrl/oper/ctrlTParaLega";
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
	@RequestMapping(value ={"ctrlTParaLegaList"})
	public String ctrlTParaLegaList(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"List";
	}
	
	/**
	 * 进入新增页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"ctrlTParaLegaAdd"})
	public String ctrlTParaLegaAdd(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"AddForm";
	}
	
	/**
	 * 进入修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"ctrlTParaLegaUpdate"})
	public String ctrlTParaLegaUpdate(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"UpdateForm";
	}
	
	/**
	 * 进入详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"ctrlTParaLegaDetail"})
	public String ctrlTParaLegaDetail(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"DetailForm";
	}
	
	/**
	 *  法人下拉框
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getLega" })
	public void getLega(HttpServletRequest request, HttpServletResponse response){
	List<CtrlTParaLegaDO> list = ctrlTParaLegaService.list(new CtrlTParaLegaDO());
	  List<Map<String, Object>> maps = new ArrayList();
	  Map<String, Object> emptyMap = new HashMap<>(2);
	        emptyMap.put("label", "--请选择--");
	        emptyMap.put("value", "");
	        maps.add(emptyMap);
	        for(CtrlTParaLegaDO DO : list){
	         Map<String, Object> map = new HashMap<>(2);
	         map.put("label", DO.getLegaNo()+"-"+DO.getLegaName());
	         map.put("value", DO.getLegaNo());
	         maps.add(map);
	        }
	        Map<String, Object> m = new HashMap<>();
	        m.put("retCode", "0000");
	        m.put("list", maps);
	        renderString(response, m);
	 }
	
	@RequiresPermissions("user")
	@RequestMapping(value = { "getLegaNo" })
	public void getLegaNo(HttpServletRequest request, HttpServletResponse response){
	List<CtrlTParaLegaDO> list = ctrlTParaLegaService.list(new CtrlTParaLegaDO());
	  List<Map<String, Object>> maps = new ArrayList();
	  Map<String, Object> emptyMap = new HashMap<>(2);
	        emptyMap.put("label", "--请选择--");
	        emptyMap.put("value", "");
	        maps.add(emptyMap);
	        for(CtrlTParaLegaDO DO : list){
	         Map<String, Object> map = new HashMap<>(2);
	         map.put("label", DO.getLegaNo()+"-"+DO.getLegaName());
	         map.put("value", DO.getLegaNo());
	         maps.add(map);
	        }
	        Map<String, Object> m = new HashMap<>();
	        m.put("retCode", "0000");
	        m.put("list", maps);
	        renderString(response, m);
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
		String legaNo = reqDs.getString("legaNo");
		CtrlTParaLegaDO ctrlTParaLegaDO = ctrlTParaLegaService.get(legaNo);
		if(ctrlTParaLegaDO!=null){
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "此法人号已经存在！");
			return;
		}
		CtrlTParaLegaDO obj = DatasetService.getInstace().getObject(reqDs, CtrlTParaLegaDO.class);
		if(ctrlTParaLegaService.insert(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "新增交易成功！");
			// 调用参数缓存刷新
		    CompCtrlUtil.redisRefreshByTableName(CompCtrlUtil.TABLE_PARA_LEGA);
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
		CtrlTParaLegaDO obj = DatasetService.getInstace().getObject(reqDs, CtrlTParaLegaDO.class);
		if(ctrlTParaLegaService.update(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "更新交易成功！");
			// 调用参数缓存刷新
		    CompCtrlUtil.redisRefreshByTableName(CompCtrlUtil.TABLE_PARA_LEGA);
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
		String legaNo = reqDs.getString("legaNo");
		CtrlTParaBrchDO brchDO=new CtrlTParaBrchDO();
		brchDO.setLegaNo(legaNo);
		List<CtrlTParaBrchDO> list = ctrlTparaBrahDao.list(brchDO);
		if(list.size()>0){
			String brah="";
			for (CtrlTParaBrchDO ctrlTParaBrchDO : list) {
				brah=brah+ctrlTParaBrchDO.getBrch()+",";
			}
			brah=brah.substring(0, brah.length()-1);
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "该法人已被机构["+brah+"]使用！");
			return;
		}
		CtrlTParaLegaDO obj = DatasetService.getInstace().getObject(reqDs, CtrlTParaLegaDO.class);
		if(ctrlTParaLegaService.delete(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "删除交易成功！");
			// 调用参数缓存刷新
		    CompCtrlUtil.redisRefreshByTableName(CompCtrlUtil.TABLE_PARA_LEGA);
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
		CtrlTParaLegaDO obj = DatasetService.getInstace().getObject(reqDs, CtrlTParaLegaDO.class);
		CtrlTParaLegaAndTntDO obj1 = DatasetService.getInstace().getObject(reqDs, CtrlTParaLegaAndTntDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		List<CtrlTParaLegaAndTntDO> list = ctrlTParaLegaService.listAndTnt(obj1, start, limit);
		int total = ctrlTParaLegaService.getTotal(obj);
		resDs = DatasetService.getInstace().getDataset(list,CtrlTParaLegaAndTntDO.class);
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
		String legaNo = reqDs.getString("legaNo");
		if (DataUtil.isNullStr(legaNo)) {
			throw new BaseException(SysErr.E_IN_NULL, "输入项[legaNo]不能空！");
		}		

		CtrlTParaLegaDO obj = ctrlTParaLegaService.get(legaNo);
		IDataset resDs = DatasetService.getInstace().getDataset(obj, CtrlTParaLegaDO.class);
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
		//新增租户号中文描述列
		ds.addColumn("tntNoStr", DatasetColumnType.DS_STRING);
		if(isAction){
			//新增操作描述列
			ds.addColumn("action", DatasetColumnType.DS_STRING);
		}
		ds.beforeFirst();
		while(ds.hasNext()){
			ds.next();
			//更新租户号中文描述
			String tntNo = ds.getString("tntNo");
			ds.updateString("tntNoStr", DictUtils.getDictLabels(tntNo, "", tntNo));
			
			if(isAction){
				/*添加相关操作按钮*/
				StringBuffer action = new StringBuffer();
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"detail(" +"'"+ds.getString("legaNo")+"'" + ")\" >详情</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"update(" +"'"+ds.getString("legaNo")+"'" + ")\" >修改</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"del(" +"'"+ds.getString("legaNo")+"'" + ")\" >删除</a>");
				ds.updateString("action", action.toString());
			}
		}
	}
}