package com.adtec.comp.ctrl.oper.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adtec.comp.ctrl.dto.FCtrlPlaDayChgNotiReqDTO;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.framework.exception.SysErr;

@Controller
@RequestMapping(value = "${adminPath}/comp/ctrl/oper/fctrlTParaDataSyn")
public class CtrlTParaDataSynController extends BaseController {
	
	/*首页页面路径*/
	private String PATH = "starring/comp/ctrl/oper/tParaDataSyn";
	/**
	 * 进入查询页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tParaDataSynQryForm"})
	public String tParaDataSynQryForm(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"QryForm";
	}
	
	/**
	 * 提交页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tParaDataSynExecForm"})
	public String tParaDataSynExecForm(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"ExecForm";
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
		//resDs = fCtrlTParaDayService.dayChgNotice(dayChgReqDTO);
		setResponseDataset(request, response,resDs, SysErr.E_SUCCESS,"交易成功");		
	}
}
