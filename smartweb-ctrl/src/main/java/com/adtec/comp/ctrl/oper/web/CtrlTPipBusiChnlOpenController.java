/**
* 系统名称: SmartWeb平台
* 模块名称: comp.ctrl.oper拦截器模块
* 功能描述: 业务渠道开通控制类
* 类 名 称  : CtrlTPipBusiChnlOpenController.java
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

import com.adtec.comp.ctrl.oper.entity.CtrlTParaChnlDO;
import com.adtec.comp.ctrl.oper.entity.CtrlTPipBusiBrchOpenDO;
import com.adtec.comp.ctrl.oper.entity.CtrlTPipBusiChnlOpenDO;
import com.adtec.comp.ctrl.oper.entity.CtrlTPipBusiDO;
import com.adtec.comp.ctrl.oper.service.CtrlTPipBusiChnlOpenService;
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

/**
 * 业务渠道开通Controller
 * @author zhengjt
 * @version 20200324
 */
@Controller
@RequestMapping(value = "${adminPath}/comp/ctrl/oper/ctrlTPipBusiChnlOpen")
public class CtrlTPipBusiChnlOpenController extends BaseController {

	@Autowired
	private CtrlTPipBusiChnlOpenService ctrlBusiChnlOpenService;
	
	/*首页页面路径*/
	private String PATH = "starring/comp/ctrl/oper/ctrlTPipBusiChnlOpen";
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
	@RequestMapping(value ={"ctrlTPipBusiChnlOpenList"})
	public String ctrlTPipBusiChnlOpenList(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"List";
	}
	
	/**
	 * 进入新增页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"ctrlTPipBusiChnlOpenAdd"})
	public String ctrlTPipBusiChnlOpenAdd(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"AddForm";
	}
	
	/**
	 * 进入修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"ctrlTPipBusiChnlOpenUpdate"})
	public String ctrlTPipBusiChnlOpenUpdate(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"UpdateForm";
	}
	
	/**
	 * 进入详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"ctrlTPipBusiChnlOpenDetail"})
	public String ctrlTPipBusiChnlOpenDetail(HttpServletRequest request, HttpServletResponse response) {
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
		String busiNo= reqDs.getString("busiNo");
		String chnlNoStr= reqDs.getString("chnlNo");
		String flg = reqDs.getString("flg");
		String[] strArray = null;   
	    strArray = chnlNoStr.split(",");
	    boolean Flg = true;
	    boolean exist = false;
		for(int i = 0; i < strArray.length; i++){
			CtrlTPipBusiChnlOpenDO obj = new CtrlTPipBusiChnlOpenDO();
			obj.setBusiNo(busiNo);
			obj.setChnlNo(strArray[i]);
			CtrlTPipBusiChnlOpenDO ctrlTPipBusiChnlOpenDO = ctrlBusiChnlOpenService.get(obj);
			if(ctrlTPipBusiChnlOpenDO!=null){
				exist=true;
			}
		}
		if(exist){
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "此业务渠道已存在");
			return;
		}
	    for (int i = 0; i < strArray.length; i++){
			CtrlTPipBusiChnlOpenDO obj = new CtrlTPipBusiChnlOpenDO();
			obj.setBusiNo(busiNo);
			obj.setChnlNo(strArray[i]);
			obj.setFlg(flg);
			if(!ctrlBusiChnlOpenService.insert(obj)){
				Flg = false;
			}
	    }
	    if(Flg) {
	    	setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "新增交易成功！");
	    	// 调用参数缓存刷新
		    CompCtrlUtil.redisRefreshByTableName(CompCtrlUtil.TABLE_PARA_CHNL_OPEN);
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "新增交易失败！");
		}
	}
	
	/**
	 * flg修改交易
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value="updateFlg")
	public void updateFlg(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		CtrlTPipBusiChnlOpenDO obj = DatasetService.getInstace().getObject(reqDs, CtrlTPipBusiChnlOpenDO.class);
		if(ctrlBusiChnlOpenService.update(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "更新交易成功！");
			// 调用参数缓存刷新
		    CompCtrlUtil.redisRefreshByTableName(CompCtrlUtil.TABLE_PARA_CHNL_OPEN);
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
	@RequestMapping(value="update")
	public void update(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String ori_chnlNo = reqDs.getString("ORI_chnlNo");
		String busiNo= reqDs.getString("busiNo");
		String chnlNo= reqDs.getString("chnlNo");
		String flg = reqDs.getString("flg");
		/*CtrlTPipBusiChnlOpenDO obj = DatasetService.getInstace().getObject(reqDs, CtrlTPipBusiChnlOpenDO.class);*/
		CtrlTPipBusiChnlOpenDO obj = new CtrlTPipBusiChnlOpenDO();
		obj.setBusiNo(busiNo);
		obj.setChnlNo(chnlNo);
		obj.setFlg(flg);
		if(ctrlBusiChnlOpenService.update(obj,ori_chnlNo)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "更新交易成功！");
			// 调用参数缓存刷新
		    CompCtrlUtil.redisRefreshByTableName(CompCtrlUtil.TABLE_PARA_CHNL_OPEN);
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
		CtrlTPipBusiChnlOpenDO obj = DatasetService.getInstace().getObject(reqDs, CtrlTPipBusiChnlOpenDO.class);
		if(ctrlBusiChnlOpenService.delete(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "删除交易成功！");
			// 调用参数缓存刷新
		    CompCtrlUtil.redisRefreshByTableName(CompCtrlUtil.TABLE_PARA_CHNL_OPEN);
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
		CtrlTPipBusiChnlOpenDO obj = DatasetService.getInstace().getObject(reqDs, CtrlTPipBusiChnlOpenDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		List<CtrlTPipBusiChnlOpenDO> list = ctrlBusiChnlOpenService.list(obj, start, limit);
		int total = ctrlBusiChnlOpenService.getTotal(obj);
		resDs = DatasetService.getInstace().getDataset(list,CtrlTPipBusiChnlOpenDO.class);
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
		String busiNo = reqDs.getString("busiNo");
		if (DataUtil.isNullStr(busiNo)) {
			throw new BaseException(SysErr.E_IN_NULL, "输入项[busiNo]不能空！");
		}		
		String chnlNo = reqDs.getString("chnlNo");
		if (DataUtil.isNullStr(chnlNo)) {
			throw new BaseException(SysErr.E_IN_NULL, "输入项[chnlNo]不能空！");
		}		

		CtrlTPipBusiChnlOpenDO obj = ctrlBusiChnlOpenService.get(busiNo, chnlNo);
		IDataset resDs = DatasetService.getInstace().getDataset(obj, CtrlTPipBusiChnlOpenDO.class);
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
		//新增业务编号中文描述列
		ds.addColumn("busiNoStr", DatasetColumnType.DS_STRING);
		//新增渠道号中文描述列
		ds.addColumn("chnlNoStr", DatasetColumnType.DS_STRING);
		//新增标志中文描述列
		ds.addColumn("flgStr", DatasetColumnType.DS_STRING);
		if(isAction){
			//新增操作描述列
			ds.addColumn("action", DatasetColumnType.DS_STRING);
		}
		ds.beforeFirst();
		while(ds.hasNext()){
			ds.next();
			String busiNo = ds.getString("busiNo");
			CtrlTPipBusiDO ctrlTPipBusiDO = ctrlBusiChnlOpenService.getBusiName(busiNo); 
			if (ctrlTPipBusiDO != null) {
				ds.updateString("busiNoStr", ctrlTPipBusiDO.getBusiName());
			} else {
				ds.updateString("busiNoStr", busiNo);
			}
			//更新渠道号中文描述
			String chnlNo = ds.getString("chnlNo");
			CtrlTParaChnlDO ctrlTPipChnlDO = ctrlBusiChnlOpenService.getChnlName(chnlNo);  
			if (ctrlTPipChnlDO != null) {
				ds.updateString("chnlNoStr", ctrlTPipChnlDO.getChnlName());
			} else {
				ds.updateString("chnlNoStr", chnlNo);
			}
			//更新标志中文描述
			String flg = ds.getString("flg");
			if (flg.equals("Y")) {
				ds.updateString("flgStr","开通");
			} else {
				ds.updateString("flgStr", "关闭");
			}
		
			
			if(isAction){
				/*添加相关操作按钮*/
				StringBuffer action = new StringBuffer();
				if (flg.equals("Y")) {
					action.append("	<a href=\"JavaScript:void(0);\" onClick=\"closeFlg(" +"'"+ds.getString("busiNo")+"', "+"'"+ds.getString("chnlNo")+"'" + ")\" >关闭</a>");
				} else if (flg.equals("N")){
					action.append("	<a href=\"JavaScript:void(0);\" onClick=\"openFlg(" +"'"+ds.getString("busiNo")+"', "+"'"+ds.getString("chnlNo")+"'" + ")\" >开通</a>");
				}
				//action.append("	<a href=\"JavaScript:void(0);\" onClick=\"detail(" +"'"+ds.getString("busiNo")+"', "+"'"+ds.getString("chnlNo")+"'" + ")\" >详情</a>");
				//action.append("	<a href=\"JavaScript:void(0);\" onClick=\"update(" +"'"+ds.getString("busiNo")+"', "+"'"+ds.getString("chnlNo")+"'" + ")\" >修改</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"del(" +"'"+ds.getString("busiNo")+"', "+"'"+ds.getString("chnlNo")+"'" + ")\" >删除</a>");
				ds.updateString("action", action.toString());
			}
		}
	}
	
	/**
	 *  渠道下拉框
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getChnlNo" })
	public void getChnlNo(HttpServletRequest request, HttpServletResponse response){
	List<CtrlTParaChnlDO> list = ctrlBusiChnlOpenService.getChnlNo(new CtrlTParaChnlDO());
	  List<Map<String, Object>> maps = new ArrayList();
	  Map<String, Object> emptyMap = new HashMap<>(2);
	        emptyMap.put("label", "--请选择--");
	        emptyMap.put("value", "");
	        maps.add(emptyMap);
	        for(CtrlTParaChnlDO DO : list){
	         Map<String, Object> map = new HashMap<>(2);
	         map.put("label", DO.getChnlNo()+DO.getChnlName());
	         map.put("value", DO.getChnlNo());
	         maps.add(map);
	        }
	        Map<String, Object> m = new HashMap<>();
	        m.put("retCode", "0000");
	        m.put("list", maps);
	        renderString(response, m);
	 }
	
	/**
	 *  渠道复选下拉框
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getMulChnlNo" })
	public void getMulChnlNo(HttpServletRequest request, HttpServletResponse response){
		List<CtrlTParaChnlDO> list = ctrlBusiChnlOpenService.getChnlNo(new CtrlTParaChnlDO());
		List<Map<String, Object>> maps = new ArrayList();
		Map<String, Object> emptyMap = new HashMap<>(2);
		/* emptyMap.put("label", "--请选择--");
	        emptyMap.put("value", "");
	        maps.add(emptyMap);*/
		for(CtrlTParaChnlDO DO : list){
			Map<String, Object> map = new HashMap<>(2);
			map.put("label", DO.getChnlNo()+"-"+DO.getChnlName());
			map.put("value", DO.getChnlNo());
			maps.add(map);
		}
		Map<String, Object> m = new HashMap<>();
		m.put("retCode", "0000");
		m.put("list", maps);
		renderString(response, m);
	}
	
	/**
	 *  业务下拉框
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getBusiNo" })
	public void getBusiNo(HttpServletRequest request, HttpServletResponse response){
		List<CtrlTPipBusiDO> list = ctrlBusiChnlOpenService.getBusiNo(new CtrlTPipBusiDO()); 
		List<Map<String, Object>> maps = new ArrayList();
		Map<String, Object> emptyMap = new HashMap<>(2);
		emptyMap.put("label", "--请选择--"); 
		emptyMap.put("value", "");
		maps.add(emptyMap);
		for(CtrlTPipBusiDO DO : list){
			Map<String, Object> map = new HashMap<>(2);
			map.put("label", DO.getBusiNo()+"-"+DO.getBusiName());
			map.put("value", DO.getBusiNo());
			maps.add(map);
		}
		Map<String, Object> m = new HashMap<>();
		m.put("retCode", "0000");
		m.put("list", maps);
		renderString(response, m);
	}
}