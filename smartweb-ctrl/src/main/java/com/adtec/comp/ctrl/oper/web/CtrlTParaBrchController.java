/**
* 系统名称: SmartWeb平台
* 模块名称: comp.ctrl.oper拦截器模块
* 功能描述: 机构管理控制类
* 类 名 称  : CtrlTParaBrchController.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200311<br>
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adtec.comp.ctrl.oper.dao.CtrlTParaChnlDao;
import com.adtec.comp.ctrl.oper.dao.TParaTlrDao;
import com.adtec.comp.ctrl.oper.entity.CtrlTParaBrchDO;
import com.adtec.comp.ctrl.oper.entity.CtrlTParaChnlDO;
import com.adtec.comp.ctrl.oper.entity.TParaTlrDO;
import com.adtec.comp.ctrl.oper.service.CtrlTParaBrchService;
import com.adtec.comp.ctrl.oper.util.CompCtrlUtil;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.DatasetColumnType;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.modules.sys.entity.Office;
import com.adtec.sys.modules.sys.service.AreaService;
import com.adtec.sys.modules.sys.service.OfficeService;
import com.adtec.sys.modules.sys.service.SystemService;
import com.adtec.sys.modules.sys.utils.DictUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 机构管理Controller
 * @author zhengjt
 * @version 20200311
 */
@Controller
@RequestMapping(value = "${adminPath}/comp/ctrl/oper/ctrlTParaBrch")
public class CtrlTParaBrchController extends BaseController {

	@Autowired
	private CtrlTParaBrchService ctrlTParaBrchService;
	@Autowired
	private TParaTlrDao tParaTlrDao;
	@Autowired
	private CtrlTParaChnlDao ctrlTParaChnlDao;
	
	
	/*首页页面路径*/
	private String PATH = "starring/comp/ctrl/oper/ctrlTParaBrch";
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
	@RequestMapping(value ={"ctrlTParaBrchList"})
	public String ctrlTParaBrchList(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"List";
	}
	
	/**
	 * 进入新增页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"ctrlTParaBrchAdd"})
	public String ctrlTParaBrchAdd(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"AddForm";
	}
	
	/**
	 * 进入修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"ctrlTParaBrchUpdate"})
	public String ctrlTParaBrchUpdate(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"UpdateForm";
	}
	
	/**
	 * 进入详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"ctrlTParaBrchDetail"})
	public String ctrlTParaBrchDetail(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"DetailForm";
	}
	
	 /**
     * 获取机构JSON数据。
     * @param extId 排除的ID
     * @param type    类型（1：公司；2：部门/小组/其它；3：用户；4：角色）
     * @param isAll
     * @param response
     * @return
     */
    @RequiresPermissions("user")

    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId, @RequestParam(required = false) String type,
                                              @RequestParam(required = false) Boolean isAll, HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<CtrlTParaBrchDO> list = ctrlTParaBrchService.list(new CtrlTParaBrchDO());
        for (CtrlTParaBrchDO e : list) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", e.getBrch());
                map.put("pId", e.getUpBrch());
                map.put("pIds", e.getUpBrch());
                map.put("name", e.getBrchName());
                mapList.add(map);
            
        }
        return mapList;
    }
    
    /**
	 *  机构下拉框
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getBrchNo" })
	public void getBrchNo(HttpServletRequest request, HttpServletResponse response){
	List<CtrlTParaBrchDO> list = ctrlTParaBrchService.list(new CtrlTParaBrchDO());
	  List<Map<String, Object>> maps = new ArrayList();
	  Map<String, Object> emptyMap = new HashMap<>(2);
	        emptyMap.put("label", "--请选择--");
	        emptyMap.put("value", "");
	        maps.add(emptyMap);
	        for(CtrlTParaBrchDO DO : list){
	         Map<String, Object> map = new HashMap<>(2);
	         map.put("label", DO.getBrch()+"-"+DO.getBrchName());
	         map.put("value", DO.getBrch());
	         maps.add(map);
	        }
	        Map<String, Object> m = new HashMap<>();
	        m.put("retCode", "0000");
	        m.put("list", maps);
	        renderString(response, m);
	 }
	
	/**
	 *  机构复选下拉框
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getMulBrchNo" })
	public void getMulBrchNo(HttpServletRequest request, HttpServletResponse response){
		List<CtrlTParaBrchDO> list = ctrlTParaBrchService.list(new CtrlTParaBrchDO());
		List<Map<String, Object>> maps = new ArrayList();
		Map<String, Object> emptyMap = new HashMap<>(2);
		/*  emptyMap.put("label", "--请选择--");
	        emptyMap.put("value", "");
	        maps.add(emptyMap);*/
		for(CtrlTParaBrchDO DO : list){
			Map<String, Object> map = new HashMap<>(2);
			map.put("label", DO.getBrch()+"-"+DO.getBrchName());
			map.put("value", DO.getBrch());
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
		String brch = reqDs.getString("brch");
		CtrlTParaBrchDO ctrlTParaBrchDO=new CtrlTParaBrchDO();
		ctrlTParaBrchDO.setBrch(brch);
		CtrlTParaBrchDO ctrlTParaBrchDO2 = ctrlTParaBrchService.get(ctrlTParaBrchDO);
		if(ctrlTParaBrchDO2!=null){
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "此机构号已经存在！");
			return;
		}
		CtrlTParaBrchDO obj = DatasetService.getInstace().getObject(reqDs, CtrlTParaBrchDO.class);
		if(ctrlTParaBrchService.insert(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "新增交易成功！");
			// 调用参数缓存刷新
		    CompCtrlUtil.redisRefreshByTableName(CompCtrlUtil.TABLE_PARA_BRCH);
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
		CtrlTParaBrchDO obj = DatasetService.getInstace().getObject(reqDs, CtrlTParaBrchDO.class);
		if(ctrlTParaBrchService.update(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "更新交易成功！");
			// 调用参数缓存刷新
		    CompCtrlUtil.redisRefreshByTableName(CompCtrlUtil.TABLE_PARA_BRCH);
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
		String brch = reqDs.getString("brch");
		TParaTlrDO tParaTlrDo=new TParaTlrDO();
		tParaTlrDo.setBrch(brch);
		List<TParaTlrDO> list = tParaTlrDao.list(tParaTlrDo);
		if(list.size()>0){
			String tlrNo="";
			for (TParaTlrDO tParaTlrDO2 : list) {
				tlrNo=tlrNo+tParaTlrDO2.getTlrNo()+",";
			}
			tlrNo=tlrNo.substring(0, tlrNo.length()-1);
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "该机构已被柜员["+tlrNo+"]使用！");
			return;
		}
		
		CtrlTParaChnlDO ctrlTParaChnlDO=new CtrlTParaChnlDO();
		ctrlTParaChnlDO.setBrch(brch);
		List<CtrlTParaChnlDO> chnlList = ctrlTParaChnlDao.list(ctrlTParaChnlDO);
		if(chnlList.size()>0){
			String chnlNo="";
			for (CtrlTParaChnlDO ctrlTParaChnlDO2 : chnlList) {
				chnlNo=chnlNo+ctrlTParaChnlDO2.getChnlNo()+",";
			}
			chnlNo=chnlNo.substring(0, chnlNo.length()-1);
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "该机构已被渠道["+chnlNo+"]使用！");
			return;
		}
		
		CtrlTParaBrchDO obj = DatasetService.getInstace().getObject(reqDs, CtrlTParaBrchDO.class);
		if(ctrlTParaBrchService.delete(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "删除交易成功！");
			// 调用参数缓存刷新
		    CompCtrlUtil.redisRefreshByTableName(CompCtrlUtil.TABLE_PARA_BRCH);
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
		CtrlTParaBrchDO obj = DatasetService.getInstace().getObject(reqDs, CtrlTParaBrchDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		List<CtrlTParaBrchDO> list = ctrlTParaBrchService.list(obj, start, limit);
		int total = ctrlTParaBrchService.getTotal(obj);
		resDs = DatasetService.getInstace().getDataset(list,CtrlTParaBrchDO.class);
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
		String brch = reqDs.getString("brch");
		if (DataUtil.isNullStr(brch)) {
			throw new BaseException(SysErr.E_IN_NULL, "输入项[brch]不能空！");
		}		

		CtrlTParaBrchDO obj = ctrlTParaBrchService.get(brch);
		IDataset resDs = DatasetService.getInstace().getDataset(obj, CtrlTParaBrchDO.class);
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
		//新增上级机构中文名称列
		ds.addColumn("upBrchName", DatasetColumnType.DS_STRING);
		//新增法人号中文描述列
		ds.addColumn("legaNoStr", DatasetColumnType.DS_STRING);
		//新增租户号中文描述列
		ds.addColumn("tntNoStr", DatasetColumnType.DS_STRING);
		if(isAction){
			//新增操作描述列
			ds.addColumn("action", DatasetColumnType.DS_STRING);
		}
		ds.beforeFirst();
		while(ds.hasNext()){
			ds.next();
			//更新上级机构中文名称
			String upBrch = ds.getString("upBrch");
			String upBrchName = upBrch;
			Office upBrchOffice = officeService.get(upBrch);
			if(null!=upBrchOffice){
				upBrchName = upBrchOffice.getName();
			}
			ds.updateString("upBrchName", upBrchName);
			//更新法人号中文描述
			String legaNo = ds.getString("legaNo");
			ds.updateString("legaNoStr", DictUtils.getDictLabels(legaNo, "", legaNo));
			//更新租户号中文描述
			String tntNo = ds.getString("tntNo");
			ds.updateString("tntNoStr", DictUtils.getDictLabels(tntNo, "", tntNo));
			
			if(isAction){
				/*添加相关操作按钮*/
				StringBuffer action = new StringBuffer();
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"detail(" +"'"+ds.getString("brch")+"'" + ")\" >详情</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"update(" +"'"+ds.getString("brch")+"'" + ")\" >修改</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"del(" +"'"+ds.getString("brch")+"'" + ")\" >删除</a>");
				ds.updateString("action", action.toString());
			}
		}
	}
}