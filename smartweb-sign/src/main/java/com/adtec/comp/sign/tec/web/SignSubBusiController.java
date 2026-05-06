package com.adtec.comp.sign.tec.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adtec.comp.sign.dto.ParaSubBusiAddReqDTO;
import com.adtec.comp.sign.dto.ParaSubBusiDelReqDTO;
import com.adtec.comp.sign.dto.ParaSubBusiModReqDTO;
import com.adtec.comp.sign.dto.ParaSubBusiPubDTO;
import com.adtec.comp.sign.dto.ParaSubBusiPubListDTO;
import com.adtec.comp.sign.dto.ParaSubBusiQryReqDTO;
import com.adtec.comp.sign.tec.service.SignSubBusiService;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;


@Controller
@RequestMapping(value="${adminPath}/comp/sign/tec/signSubBusi")
public class SignSubBusiController extends BaseController{
	
	@Autowired
	private SignSubBusiService signSubBusiService;
	
	
	@RequiresPermissions("user")
	@RequestMapping(value = { "start","" })
	public String start(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/sign/tec/subBusiQry";
	}
	
	@RequiresPermissions("user")
	@RequestMapping(value = { "form","" })
	public String form(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/sign/tec/subBusiForm";
	}
	
	
	/**
	 * 查询
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"qry"})
	public void qry(HttpServletRequest request,HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String compNo = reqDs.getString("COMP_NO");
		String busiNo = reqDs.getString("BUSI_NO");
		String subBusiNo = reqDs.getString("SUB_BUSI_NO");
		String OPEN_STAT = reqDs.getString("OPEN_STAT");
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		ParaSubBusiQryReqDTO reqBody = new ParaSubBusiQryReqDTO();
		reqBody.setCOMP_NO(compNo);
		reqBody.setBUSI_NO(busiNo);
		reqBody.setSUB_BUSI_NO(subBusiNo);
		reqBody.setOPEN_STAT(OPEN_STAT);
		IDataset res = signSubBusiService.qry(reqBody, start, limit);
		setResponseDataset(request, response, res, SysErr.E_SUCCESS, "查询成功");
	}
	
	/**
	 * 新增
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"add"})
	public void add(HttpServletRequest request,HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String busiNo = reqDs.getString("BUSI_NO");
		String subBusiNo = reqDs.getString("SUB_BUSI_NO");
		String subBusiName = reqDs.getString("SUB_BUSI_NAME");
		String openStat = reqDs.getString("OPEN_STAT");
		String mngBrch = reqDs.getString("MNG_BRCH");
		String busiBrch = reqDs.getString("BUSI_BRCH");
		String clrBrch = reqDs.getString("CLR_BRCH");
		String signFlg = reqDs.getString("SIGN_FLG");
		ParaSubBusiAddReqDTO reqBody = new ParaSubBusiAddReqDTO();
		ParaSubBusiPubDTO busiDTO = new ParaSubBusiPubDTO();
		ParaSubBusiPubListDTO subBusiDTO = new ParaSubBusiPubListDTO();
		//子业务赋值
		subBusiDTO.setSUB_BUSI_NO(subBusiNo);
		subBusiDTO.setSUB_BUSI_NAME(subBusiName);
		subBusiDTO.setOPEN_STAT(openStat);
		if("".equals(mngBrch)){
			subBusiDTO.setMNG_BRCH("");
		}else{
			subBusiDTO.setMNG_BRCH(signSubBusiService.getBrchCodeById(mngBrch));
		}
		if("".equals(busiBrch)){
			subBusiDTO.setBUSI_BRCH("");
		}else{
			subBusiDTO.setBUSI_BRCH(signSubBusiService.getBrchCodeById(busiBrch));
		}
		if("".equals(clrBrch)){
			subBusiDTO.setCLR_BRCH("");
		}else{
			subBusiDTO.setCLR_BRCH(signSubBusiService.getBrchCodeById(clrBrch));
		}
		subBusiDTO.setSIGN_FLG(signFlg);
		//业务赋值
		busiDTO.setSUB_BUSI_NUM(1);
		busiDTO.getSUB_BUSI_LIST().add(subBusiDTO);
		busiDTO.setBUSI_NO(busiNo);
		//业务list赋值
		reqBody.setBUSI_NUM(1);
		reqBody.getBUSI_LIST().add(busiDTO);
		
		IDataset res = signSubBusiService.add(reqBody);
		setResponseDataset(request, response, res, SysErr.E_SUCCESS, "新增成功");
	}
	
	/**
	 * 修改
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"mod"})
	public void mod(HttpServletRequest request,HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String busiNo = reqDs.getString("BUSI_NO");
		String subBusiNo = reqDs.getString("SUB_BUSI_NO");
		String subBusiName = reqDs.getString("SUB_BUSI_NAME");
		String openStat = reqDs.getString("OPEN_STAT");
		String mngBrch = reqDs.getString("MNG_BRCH");
		String busiBrch = reqDs.getString("BUSI_BRCH");
		String clrBrch = reqDs.getString("CLR_BRCH");
		String signFlg = reqDs.getString("SIGN_FLG");
		ParaSubBusiModReqDTO reqBody = new ParaSubBusiModReqDTO();
		ParaSubBusiPubDTO busiDTO = new ParaSubBusiPubDTO();
		ParaSubBusiPubListDTO subBusiDTO = new ParaSubBusiPubListDTO();
		//子业务赋值
		subBusiDTO.setSUB_BUSI_NO(subBusiNo);
		subBusiDTO.setSUB_BUSI_NAME(subBusiName);
		subBusiDTO.setOPEN_STAT(openStat);
		if("".equals(mngBrch)){
			subBusiDTO.setMNG_BRCH("");
		}else{
			subBusiDTO.setMNG_BRCH(signSubBusiService.getBrchCodeById(mngBrch));
		}
		if("".equals(busiBrch)){
			subBusiDTO.setBUSI_BRCH("");
		}else{
			subBusiDTO.setBUSI_BRCH(signSubBusiService.getBrchCodeById(busiBrch));
		}
		if("".equals(clrBrch)){
			subBusiDTO.setCLR_BRCH("");
		}else{
			subBusiDTO.setCLR_BRCH(signSubBusiService.getBrchCodeById(clrBrch));
		}
		subBusiDTO.setSIGN_FLG(signFlg);
		//业务赋值
		busiDTO.setSUB_BUSI_NUM(1);
		busiDTO.getSUB_BUSI_LIST().add(subBusiDTO);
		busiDTO.setBUSI_NO(busiNo);
		//业务list赋值
		reqBody.setBUSI_NUM(1);
		reqBody.getBUSI_LIST().add(busiDTO);
		
		IDataset res = signSubBusiService.mod(reqBody);
		setResponseDataset(request, response, res, SysErr.E_SUCCESS, "修改成功");
	}
	
	/**
	 * 删除
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"del"})
	public void del(HttpServletRequest request,HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String busiNo = reqDs.getString("BUSI_NO");
		String subBusiNo = reqDs.getString("SUB_BUSI_NO");
		ParaSubBusiDelReqDTO reqBody = new ParaSubBusiDelReqDTO();
		ParaSubBusiPubDTO busiDTO = new ParaSubBusiPubDTO();
		ParaSubBusiPubListDTO subBusiDTO = new ParaSubBusiPubListDTO();
		//子业务赋值
		subBusiDTO.setSUB_BUSI_NO(subBusiNo);
		//业务赋值
		busiDTO.setSUB_BUSI_NUM(1);
		busiDTO.getSUB_BUSI_LIST().add(subBusiDTO);
		busiDTO.setBUSI_NO(busiNo);
		//业务list赋值
		reqBody.setBUSI_NUM(1);
		reqBody.getBUSI_LIST().add(busiDTO);
		IDataset res = signSubBusiService.del(reqBody);
		setResponseDataset(request, response, res, SysErr.E_SUCCESS, "删除成功");
	}
	
	/**
	 * 查询子业务编号下拉框
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"qrySubBusiNo"})
	public void qrySubBusiNo(HttpServletRequest request,HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String compNo = reqDs.getString("COMP_NO");
		String busiNo = reqDs.getString("BUSI_NO");
		String subBusiNo = reqDs.getString("SUB_BUSI_NO");
		String openStat = reqDs.getString("OPEN_STAT");
		String signFlg = reqDs.getString("signFlg");
		ParaSubBusiQryReqDTO reqBody = new ParaSubBusiQryReqDTO();
		reqBody.setCOMP_NO(compNo);
		reqBody.setBUSI_NO(busiNo);
		reqBody.setSUB_BUSI_NO(subBusiNo);
		reqBody.setOPEN_STAT(openStat);
		IDataset res = signSubBusiService.qrySubBusiNo(reqBody,signFlg);
		setResponseDataset(request, response, res, SysErr.E_SUCCESS, "查询成功");
	}

}
