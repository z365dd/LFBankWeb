/**
* 系统名称: SmartWeb平台
* 模块名称: comp.ctrl.oper拦截器模块
* 功能描述: 平台日切控制类
* 类 名 称  : FCtrlTParaDayController.java
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

import com.adtec.comp.ctrl.dto.FCtrlPlaDayChgNotiReqDTO;
import com.adtec.comp.ctrl.oper.entity.FCtrlTParaDayDO;
import com.adtec.comp.ctrl.oper.service.FCtrlTParaDayService;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.sys.common.web.BaseController;
import com.adtec.framework.exception.SysErr;

/**
 * 平台日切Controller
 * @author zhengjt
 * @version 20200512
 */
@Controller
@RequestMapping(value = "${adminPath}/comp/ctrl/oper/fCtrlTParaDay")
public class FCtrlTParaDayController extends BaseController {

	@Autowired
	private FCtrlTParaDayService fCtrlTParaDayService;
	
	/*首页页面路径*/
	private String PATH = "starring/comp/ctrl/oper/fCtrlTParaDay";
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
	 * 进入新增页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"fCtrlTParaDayForm"})
	public String fCtrlTParaDayForm(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"Form";
	}
	
	/**
	 *  平台编号下拉框
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getPlatNo" })
	public void getPlatNo(HttpServletRequest request, HttpServletResponse response){
	List<FCtrlTParaDayDO> list = fCtrlTParaDayService.list(new FCtrlTParaDayDO());
	  List<Map<String, Object>> maps = new ArrayList();
	  Map<String, Object> emptyMap = new HashMap<>(2);
	        emptyMap.put("label", "--请选择--");
	        emptyMap.put("value", "");
	        maps.add(emptyMap);
	        for(FCtrlTParaDayDO DO : list){
	         Map<String, Object> map = new HashMap<>(2);
	         map.put("label", DO.getPlatNo()+"-"+DO.getPlatName());
	         map.put("value", DO.getPlatNo());
	         maps.add(map);
	        }
	        Map<String, Object> m = new HashMap<>();
	        m.put("retCode", "0000");
	        m.put("list", maps);
	        renderString(response, m);
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
		String platNo = reqDs.getString("platNo");
		if (DataUtil.isNullStr(platNo)) {
			throw new BaseException(SysErr.E_IN_NULL, "平台编号不能为空！");
		}		
		FCtrlTParaDayDO obj = fCtrlTParaDayService.get(platNo);
		IDataset resDs = DatasetService.getInstace().getDataset(obj, FCtrlTParaDayDO.class);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "明细查询交易成功！");
	}
	
	/**
	 * 日切
	 * @param request
	 * @param response 
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "dayChgNotice" })
	public void dayChgNotice(HttpServletRequest request, HttpServletResponse response){
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String platDate= reqDs.getString("platDate");
		FCtrlPlaDayChgNotiReqDTO dayChgReqDTO = new FCtrlPlaDayChgNotiReqDTO();
		dayChgReqDTO.setPLAT_DATE(platDate);
		resDs = fCtrlTParaDayService.dayChgNotice(dayChgReqDTO);
		setResponseDataset(request, response,resDs, SysErr.E_SUCCESS,"交易成功");		
	}
	
	
}