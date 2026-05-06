/**
* 系统名称: SmartWeb平台
* 模块名称: comp.ctrl.oper拦截器模块
* 功能描述: 数据同步控制类
* 类 名 称  : CtrlTParaDataSynConfigController.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200512<br>
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

import com.adtec.comp.ctrl.oper.entity.CtrlTParaDataSynConfigDO;
import com.adtec.comp.ctrl.oper.service.CtrlTParaDataSynConfigService;
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
 * 数据同步Controller
 * @author zhengjt
 * @version 20200512
 */
@Controller
@RequestMapping(value = "${adminPath}/comp/ctrl/oper/ctrlTParaDataSynConfig")
public class CtrlTParaDataSynConfigController extends BaseController {

	@Autowired
	private CtrlTParaDataSynConfigService ctrlTParaDataSynConfigService;
	
	/*首页页面路径*/
	private String PATH = "starring/comp/ctrl/oper/ctrlTParaDataSynConfig";
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
	@RequestMapping(value ={"ctrlTParaDataSynConfigList"})
	public String ctrlTParaDataSynConfigList(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"List";
	}
	
	/**
	 * 进入新增页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"ctrlTParaDataSynConfigAdd"})
	public String ctrlTParaDataSynConfigAdd(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"AddForm";
	}
	
	/**
	 * 进入修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"ctrlTParaDataSynConfigUpdate"})
	public String ctrlTParaDataSynConfigUpdate(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"UpdateForm";
	}
	
	/**
	 * 进入详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"ctrlTParaDataSynConfigDetail"})
	public String ctrlTParaDataSynConfigDetail(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"DetailForm";
	}
	
	/**
	 *  步骤号下拉框
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getList" })
	public void getRelatSys(HttpServletRequest request, HttpServletResponse response){
	List<CtrlTParaDataSynConfigDO> list = ctrlTParaDataSynConfigService.list(new CtrlTParaDataSynConfigDO());
	  List<Map<String, Object>> maps = new ArrayList();
	  Map<String, Object> emptyMap = new HashMap<>(2);
	        emptyMap.put("label", "--请选择--");
	        emptyMap.put("value", "");
	        maps.add(emptyMap);
	        for(CtrlTParaDataSynConfigDO DO : list){
	         Map<String, Object> map = new HashMap<>(2);
	         map.put("label", DO.getStepNo()+"-"+DO.getStepNo());
	         map.put("value", DO.getStepNo());
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
		CtrlTParaDataSynConfigDO obj = DatasetService.getInstace().getObject(reqDs, CtrlTParaDataSynConfigDO.class);
		int total = ctrlTParaDataSynConfigService.getTotal(new CtrlTParaDataSynConfigDO());
		if (total == 0){
			obj.setStepNo("0000000001");
		} else {
			String stepNo = getStepNo();
			obj.setStepNo(stepNo);
		}
		if(ctrlTParaDataSynConfigService.insert(obj)){
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
		CtrlTParaDataSynConfigDO obj = DatasetService.getInstace().getObject(reqDs, CtrlTParaDataSynConfigDO.class);
		if(ctrlTParaDataSynConfigService.update(obj)){
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
		CtrlTParaDataSynConfigDO obj = DatasetService.getInstace().getObject(reqDs, CtrlTParaDataSynConfigDO.class);
		if(ctrlTParaDataSynConfigService.delete(obj)){
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
		CtrlTParaDataSynConfigDO obj = DatasetService.getInstace().getObject(reqDs, CtrlTParaDataSynConfigDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		List<CtrlTParaDataSynConfigDO> list = ctrlTParaDataSynConfigService.list(obj, start, limit);
		int total = ctrlTParaDataSynConfigService.getTotal(obj);
		resDs = DatasetService.getInstace().getDataset(list,CtrlTParaDataSynConfigDO.class);
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
		String stepNo = reqDs.getString("stepNo");
		if (DataUtil.isNullStr(stepNo)) {
			throw new BaseException(SysErr.E_IN_NULL, "输入项[stepNo]不能空！");
		}		

		CtrlTParaDataSynConfigDO obj = ctrlTParaDataSynConfigService.get(stepNo);
		IDataset resDs = DatasetService.getInstace().getDataset(obj, CtrlTParaDataSynConfigDO.class);
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
		//新增来源类型中文描述列
		ds.addColumn("srcTpStr", DatasetColumnType.DS_STRING);
		//新增关联系统中文描述列
		ds.addColumn("relatSysStr", DatasetColumnType.DS_STRING);
		//新增目的数据源中文描述列
		ds.addColumn("dstDataSrcStr", DatasetColumnType.DS_STRING);
		if(isAction){
			//新增操作描述列
			ds.addColumn("action", DatasetColumnType.DS_STRING);
		}
		ds.beforeFirst();
		while(ds.hasNext()){
			ds.next();
			//更新来源类型中文描述
			String srcTp = ds.getString("srcTp");
			if ("1".equals(srcTp)) {
				ds.updateString("srcTpStr", "数据源");
			} else if ("2".equals(srcTp)) {
				ds.updateString("srcTpStr", "文件");
			} else {
				ds.updateString("srcTpStr", "未知");
			}
			//更新关联系统中文描述
			String relatSys = ds.getString("relatSys");
			ds.updateString("relatSysStr", DictUtils.getDictLabels(relatSys, "", relatSys));
			//更新目的数据源中文描述
			String dstDataSrc = ds.getString("dstDataSrc");
			ds.updateString("dstDataSrcStr", DictUtils.getDictLabels(dstDataSrc, "", dstDataSrc));
			
			if(isAction){
				/*添加相关操作按钮*/
				StringBuffer action = new StringBuffer();
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"detail(" +"'"+ds.getString("stepNo")+"'" + ")\" >详情</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"update(" +"'"+ds.getString("stepNo")+"'" + ")\" >修改</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"del(" +"'"+ds.getString("stepNo")+"'" + ")\" >删除</a>");
				ds.updateString("action", action.toString());
			}
		}
	}
	
	public String getStepNo(){
		String maxStepNo = ctrlTParaDataSynConfigService.getStepNo();
		Integer num = Integer.valueOf(maxStepNo);
		num++;
		String stepNo = String.format("%010d", num);
		System.out.println("********StepNp:"+stepNo);
		return stepNo;
	}
}