package com.adtec.comp.ctrl.oper.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adtec.comp.ctrl.dto.TParaCompDealTParaCardTypeReqDTO;
import com.adtec.comp.ctrl.oper.service.CardTypeService;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.framework.exception.SysErr;

@Controller
@RequestMapping(value = "${adminPath}/comp/ctrl/oper/cardType")
public class CardTypeController extends BaseController{
	@Autowired
	private CardTypeService cardTypeService;
	
	
	/**
	 * 返回查询页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "cardTypeList" })
	public String cardTypeList(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/oper/cardTypeList";
	}

	/**
	 * 返回表单页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "cardTypeForm" })
	public String cardTypeForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/oper/cardTypeForm";
	}
	
	
	
	/**
	 * 查询、新增、修改、删除
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "action" })
	public void qry(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String CARD_BIN_NO = reqDs.getString("CARD_BIN_NO");
		String CARD_TP = reqDs.getString("CARD_TP");
		String ACCT_CARD_FLG = reqDs.getString("ACCT_CARD_FLG");
		String NET_NO = reqDs.getString("NET_NO");
		String LEGA_NO = reqDs.getString("LEGA_NO");
		String OPER_TP = reqDs.getString("OPER_TP");
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		TParaCompDealTParaCardTypeReqDTO reqBody = new TParaCompDealTParaCardTypeReqDTO();
		reqBody.setCARD_BIN_NO(CARD_BIN_NO);
		reqBody.setCARD_TP(CARD_TP);
		reqBody.setACCT_CARD_FLG(ACCT_CARD_FLG);
		reqBody.setNET_NO(NET_NO);
		reqBody.setLEGA_NO(LEGA_NO);
		reqBody.setOPER_TP(OPER_TP);
		
		IDataset resDs = cardTypeService.action(reqBody, start, limit);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	
	
	
}
