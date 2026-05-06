/**
* 系统名称: SmartWeb平台
* 模块名称: comp.ctrl.oper拦截器模块
* 功能描述: 柜员管理控制类
* 类 名 称  : TParaTlrController.java
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
import com.adtec.comp.ctrl.oper.dao.CtrlTParaChnlDao;
import com.adtec.comp.ctrl.oper.entity.CtrlTParaBrchDO;
import com.adtec.comp.ctrl.oper.entity.CtrlTParaChnlDO;
import com.adtec.comp.ctrl.oper.entity.CtrlTParaLegaDO;
import com.adtec.comp.ctrl.oper.entity.FCtrlRelatSysDO;
import com.adtec.comp.ctrl.oper.entity.TParaTlrDO;
import com.adtec.comp.ctrl.oper.service.TParaTlrService;
import com.adtec.comp.ctrl.oper.util.CompCtrlUtil;

/**
 * 柜员管理Controller
 * @author zhengjt
 * @version 20200310
 */
@Controller
@RequestMapping(value = "${adminPath}/comp/ctrl/oper/tParaTlr")
public class TParaTlrController extends BaseController {

	@Autowired
	private TParaTlrService tParaTlrService;
	@Autowired
	private CtrlTParaChnlDao ctrlTParaChnlDao;
	/*首页页面路径*/
	private String PATH = "starring/comp/ctrl/oper/tParaTlr";
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
	@RequestMapping(value ={"tParaTlrList"})
	public String tParaTlrList(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"List";
	}
	
	/**
	 * 进入新增页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tParaTlrAdd"})
	public String tParaTlrAdd(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"AddForm";
	}
	
	/**
	 * 进入修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tParaTlrUpdate"})
	public String tParaTlrUpdate(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"UpdateForm";
	}
	
	/**
	 * 进入详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tParaTlrDetail"})
	public String tParaTlrDetail(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"DetailForm";
	}
	
	/**
	 *  机构下拉框
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getBrch" })
	public void getBrch(HttpServletRequest request, HttpServletResponse response){
	List<CtrlTParaBrchDO> list = tParaTlrService.getBrch(new CtrlTParaBrchDO());
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
	 *  柜员下拉框
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getTlr" })
	public void getTlr(HttpServletRequest request, HttpServletResponse response){
	List<TParaTlrDO> list = tParaTlrService.list(new TParaTlrDO());
	  List<Map<String, Object>> maps = new ArrayList();
	  Map<String, Object> emptyMap = new HashMap<>(2);
	        emptyMap.put("label", "--请选择--");
	        emptyMap.put("value", "");
	        maps.add(emptyMap);
	        for(TParaTlrDO DO : list){
	         Map<String, Object> map = new HashMap<>(2);
	         map.put("label", DO.getTlrNo()+"-"+DO.getTlrName());
	         map.put("value", DO.getTlrNo());
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
		String tlrNo = reqDs.getString("tlrNo");
		TParaTlrDO tParaTlrDO=new TParaTlrDO();
		tParaTlrDO.setTlrNo(tlrNo);
		TParaTlrDO tParaTlrDO2 = tParaTlrService.get(tParaTlrDO);
		if(tParaTlrDO2!=null){
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "此柜员号已经存在！");
			return;
		}
		TParaTlrDO obj = DatasetService.getInstace().getObject(reqDs, TParaTlrDO.class);
		if(tParaTlrService.insert(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "新增交易成功！");
			// 调用参数缓存刷新
		    CompCtrlUtil.redisRefreshByTableName(CompCtrlUtil.TABLE_PARA_TLR);
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
		TParaTlrDO obj = DatasetService.getInstace().getObject(reqDs, TParaTlrDO.class);
		if(tParaTlrService.update(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "更新交易成功！");
			// 调用参数缓存刷新
		    CompCtrlUtil.redisRefreshByTableName(CompCtrlUtil.TABLE_PARA_TLR);
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
		String tlrNo = reqDs.getString("tlrNo");
		CtrlTParaChnlDO ctrlTParaChnlDO=new CtrlTParaChnlDO();
		ctrlTParaChnlDO.setTlrNo(tlrNo);
		List<CtrlTParaChnlDO> chnlList = ctrlTParaChnlDao.list(ctrlTParaChnlDO);
		if(chnlList.size()>0){
			String chnlNo="";
			for (CtrlTParaChnlDO ctrlTParaChnlDO2 : chnlList) {
				chnlNo=chnlNo+ctrlTParaChnlDO2.getChnlNo()+",";
			}
			chnlNo=chnlNo.substring(0, chnlNo.length()-1);
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "该柜员已被渠道["+chnlNo+"]使用！");
			return;
		}
		
		TParaTlrDO obj = DatasetService.getInstace().getObject(reqDs, TParaTlrDO.class);
		if(tParaTlrService.delete(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "删除交易成功！");
			// 调用参数缓存刷新
		    CompCtrlUtil.redisRefreshByTableName(CompCtrlUtil.TABLE_PARA_TLR);
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
		TParaTlrDO obj = DatasetService.getInstace().getObject(reqDs, TParaTlrDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		List<TParaTlrDO> list = tParaTlrService.list(obj, start, limit);
		int total = tParaTlrService.getTotal(obj);
		resDs = DatasetService.getInstace().getDataset(list,TParaTlrDO.class);
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
		String tlrNo = reqDs.getString("tlrNo");
		if (DataUtil.isNullStr(tlrNo)) {
			throw new BaseException(SysErr.E_IN_NULL, "输入项[tlrNo]不能空！");
		}		

		TParaTlrDO obj = tParaTlrService.get(brch, tlrNo);
		IDataset resDs = DatasetService.getInstace().getDataset(obj, TParaTlrDO.class);
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
		//新增机构中文描述列
		ds.addColumn("brchStr", DatasetColumnType.DS_STRING);
		//新增标志中文描述列
		ds.addColumn("realTlrFlgStr", DatasetColumnType.DS_STRING);
		//新增法人中文描述列
		ds.addColumn("legaNoStr", DatasetColumnType.DS_STRING);
		//新增租户中文描述列
		ds.addColumn("tntNoStr", DatasetColumnType.DS_STRING);
		if(isAction){
			//新增操作描述列
			ds.addColumn("action", DatasetColumnType.DS_STRING);
		}
		ds.beforeFirst();
		while(ds.hasNext()){
			ds.next();
			//更新机构中文描述
			String brch = ds.getString("brch");
			ds.updateString("brchStr", DictUtils.getDictLabels(brch, "", brch));
			//更新标志中文描述
			String realTlrFlg = ds.getString("realTlrFlg");
			ds.updateString("realTlrFlgStr", DictUtils.getDictLabels(realTlrFlg, "TLR_FLG", realTlrFlg));
			//更新法人中文描述
			String legaNo = ds.getString("legaNo");
			ds.updateString("legaNoStr", DictUtils.getDictLabels(legaNo, "", legaNo));
			//更新租户中文描述
			String tntNo = ds.getString("tntNo");
			ds.updateString("tntNoStr", DictUtils.getDictLabels(tntNo, "", tntNo));
			
			if(isAction){
				/*添加相关操作按钮*/
				StringBuffer action = new StringBuffer();
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"detail(" +"'"+ds.getString("brch")+"', "+"'"+ds.getString("tlrNo")+"'" + ")\" >详情</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"update(" +"'"+ds.getString("brch")+"', "+"'"+ds.getString("tlrNo")+"'" + ")\" >修改</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"del(" +"'"+ds.getString("brch")+"', "+"'"+ds.getString("tlrNo")+"'" + ")\" >删除</a>");
				ds.updateString("action", action.toString());
			}
		}
	}
}