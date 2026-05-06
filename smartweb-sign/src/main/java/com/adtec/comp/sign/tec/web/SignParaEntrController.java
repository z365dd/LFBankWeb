package com.adtec.comp.sign.tec.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adtec.comp.sign.dto.ParaEntrAddReqDTO;
import com.adtec.comp.sign.dto.ParaEntrDelReqDTO;
import com.adtec.comp.sign.dto.ParaEntrModReqDTO;
import com.adtec.comp.sign.dto.ParaEntrPubListDTO;
import com.adtec.comp.sign.dto.ParaEntrQryReqDTO;
import com.adtec.comp.sign.tec.service.SignParaEntrService;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;


@Controller
@RequestMapping(value="${adminPath}/comp/sign/tec/signParaEntr")
public class SignParaEntrController extends BaseController{
	
	@Autowired
	private SignParaEntrService signParaEntrService;
	
	
	@RequiresPermissions("user")
	@RequestMapping(value = { "start","" })
	public String start(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/sign/tec/paraEntrQry";
	}
	
	@RequiresPermissions("user")
	@RequestMapping(value = { "form","" })
	public String form(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/sign/tec/paraEntrForm";
	}
	
	
	/**
	 * 查询
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"qry"})
	public void qry(HttpServletRequest request,HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String legaNo = reqDs.getString("LEGA_NO");
		String entrNo = reqDs.getString("ENTR_NO");
		String entrName = reqDs.getString("ENTR_NAME");
		String openStat = reqDs.getString("OPEN_STAT");
		String certTp = reqDs.getString("CERT_TP");
		String certNo = reqDs.getString("CERT_NO");
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		
		ParaEntrQryReqDTO reqBody = new ParaEntrQryReqDTO();
		reqBody.setLEGA_NO(legaNo);
		reqBody.setENTR_NO(entrNo);
		reqBody.setENTR_NAME(entrName);
		reqBody.setOPEN_STAT(openStat);
		reqBody.setCERT_TP(certTp);
		reqBody.setCERT_NO(certNo);
		
		IDataset res = signParaEntrService.qry(reqBody, start, limit);
		setResponseDataset(request, response, res, SysErr.E_SUCCESS, "查询成功");
	}
	
	/**
	 * 新增
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"add"})
	public void add(HttpServletRequest request,HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String legaNo = reqDs.getString("LEGA_NO");
		String entrNo = reqDs.getString("ENTR_NO");
		String entrName = reqDs.getString("ENTR_NAME");
		String prtName = reqDs.getString("PRT_NAME");
		String certTp = reqDs.getString("CERT_TP");
		String certNo = reqDs.getString("CERT_NO");
		String openStat = reqDs.getString("OPEN_STAT");
		String ctct = reqDs.getString("CTCT_PER_NAME");
		String perTelNo = reqDs.getString("PER_TEL_NO");
		String entrTelNo = reqDs.getString("ENTR_TEL_NO");
		String commAddr = reqDs.getString("COMM_ADDR");
		String postEcd = reqDs.getString("POST_ECD");
		String emailAddr = reqDs.getString("EMAIL_ADDR");
		String bankCustNo = reqDs.getString("BANK_CUST_NO");
		
		ParaEntrAddReqDTO reqBody = new ParaEntrAddReqDTO();
		ParaEntrPubListDTO subDTO = new ParaEntrPubListDTO();
		subDTO.setLEGA_NO(legaNo);
		subDTO.setENTR_NO(entrNo);
		subDTO.setENTR_NAME(entrName);
		subDTO.setPRT_NAME(prtName);
		subDTO.setCERT_TP(certTp);
		subDTO.setCERT_NO(certNo);
		subDTO.setOPEN_STAT(openStat);
		subDTO.setCTCT_PER_NAME(ctct);
		subDTO.setPER_TEL_NO(perTelNo);
		subDTO.setENTR_TEL_NO(entrTelNo);
		subDTO.setCOMM_ADDR(commAddr);
		subDTO.setPOST_ECD(postEcd);
		subDTO.setEMAIL_ADDR(emailAddr);
		subDTO.setBANK_CUST_NO(bankCustNo);
		
		reqBody.getENTR_LIST().add(subDTO);
		reqBody.setENTR_NUM(1);
		
		IDataset res = signParaEntrService.add(reqBody);
		setResponseDataset(request, response, res, SysErr.E_SUCCESS, "新增成功");
	}
	
	/**
	 * 修改
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"mod"})
	public void mod(HttpServletRequest request,HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String legaNo = reqDs.getString("LEGA_NO");
		String entrNo = reqDs.getString("ENTR_NO");
		String entrName = reqDs.getString("ENTR_NAME");
		String prtName = reqDs.getString("PRT_NAME");
		String certTp = reqDs.getString("CERT_TP");
		String certNo = reqDs.getString("CERT_NO");
		String openStat = reqDs.getString("OPEN_STAT");
		String ctct = reqDs.getString("CTCT_PER_NAME");
		String perTelNo = reqDs.getString("PER_TEL_NO");
		String entrTelNo = reqDs.getString("ENTR_TEL_NO");
		String commAddr = reqDs.getString("COMM_ADDR");
		String postEcd = reqDs.getString("POST_ECD");
		String emailAddr = reqDs.getString("EMAIL_ADDR");
		String bankCustNo = reqDs.getString("BANK_CUST_NO");
		
		ParaEntrModReqDTO reqBody = new ParaEntrModReqDTO();
		ParaEntrPubListDTO subDTO = new ParaEntrPubListDTO();
		subDTO.setLEGA_NO(legaNo);
		subDTO.setENTR_NO(entrNo);
		subDTO.setENTR_NAME(entrName);
		subDTO.setPRT_NAME(prtName);
		subDTO.setCERT_TP(certTp);
		subDTO.setCERT_NO(certNo);
		subDTO.setOPEN_STAT(openStat);
		subDTO.setCTCT_PER_NAME(ctct);
		subDTO.setPER_TEL_NO(perTelNo);
		subDTO.setENTR_TEL_NO(entrTelNo);
		subDTO.setCOMM_ADDR(commAddr);
		subDTO.setPOST_ECD(postEcd);
		subDTO.setEMAIL_ADDR(emailAddr);
		subDTO.setBANK_CUST_NO(bankCustNo);
		
		reqBody.getENTR_LIST().add(subDTO);
		reqBody.setENTR_NUM(1);
		
		IDataset res = signParaEntrService.mod(reqBody);
		setResponseDataset(request, response, res, SysErr.E_SUCCESS, "新增成功");
	}
	
	/**
	 * 删除
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"del"})
	public void del(HttpServletRequest request,HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String entrNo = reqDs.getString("ENTR_NO");
		
		ParaEntrDelReqDTO reqBody = new ParaEntrDelReqDTO();
		ParaEntrPubListDTO subDTO = new ParaEntrPubListDTO();
		subDTO.setENTR_NO(entrNo);
		
		reqBody.getENTR_LIST().add(subDTO);
		reqBody.setENTR_NUM(1);
		
		IDataset res = signParaEntrService.del(reqBody);
		setResponseDataset(request, response, res, SysErr.E_SUCCESS, "删除成功");
	}
	
}
