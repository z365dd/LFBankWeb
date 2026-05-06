package com.adtec.comp.sign.tec.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adtec.comp.sign.dto.ParaBusiAddListDTO;
import com.adtec.comp.sign.dto.ParaBusiAddReqDTO;
import com.adtec.comp.sign.dto.ParaBusiDelListDTO;
import com.adtec.comp.sign.dto.ParaBusiDelReqDTO;
import com.adtec.comp.sign.dto.ParaBusiModListDTO;
import com.adtec.comp.sign.dto.ParaBusiModReqDTO;
import com.adtec.comp.sign.dto.ParaBusiNoCrtReqDTO;
import com.adtec.comp.sign.dto.ParaBusiQryReqDTO;
import com.adtec.comp.sign.tec.service.SignParaBusiService;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;


@Controller
@RequestMapping(value="${adminPath}/comp/sign/tec/parabusi")
public class SignParaBusiController extends BaseController{
	
	@Autowired
	private SignParaBusiService pService;
	
	/**
	 *返回查询页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"toList"})
	public String toList(HttpServletRequest request, HttpServletResponse response,String compNo) {
		//SwitchesController.setPub_compNo(compNo);
		return "starring/comp/sign/tec/paraBusiList";
	}
	
	/**
	 *返回新增或修改页面页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"toForm"})
	public String toForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/sign/tec/paraBusiForm";
	}
	
	/**
	 * 业务查询
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"paraBusiQry"})
	public void qry(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO");
		String COMP_NAME = reqDs.getString("COMP_NAME");
		String BUSI_NO = reqDs.getString("BUSI_NO");
		String BUSI_NAME = reqDs.getString("BUSI_NAME");
		String OPEN_STAT = reqDs.getString("OPEN_STAT");
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		ParaBusiQryReqDTO reqBody = new ParaBusiQryReqDTO();
		reqBody.setCOMP_NO(COMP_NO);
		reqBody.setCOMP_NAME(COMP_NAME);
		reqBody.setBUSI_NO(BUSI_NO);
		reqBody.setBUSI_NAME(BUSI_NAME);
		reqBody.setOPEN_STAT(OPEN_STAT);
		
		IDataset resDs = pService.qry(reqBody, start, limit);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 业务新增
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"paraBusiAdd"})
	public void add(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO");
		String BUSI_NO = reqDs.getString("BUSI_NO");
		String BUSI_NAME = reqDs.getString("BUSI_NAME");
		String OPEN_STAT = reqDs.getString("OPEN_STAT");
		String LEGA_NO = reqDs.getString("LEGA_NO");
		String SIGN_FLG = reqDs.getString("SIGN_FLG");
		String FLG = reqDs.getString("FLG");
		ParaBusiAddListDTO reqDTO = new ParaBusiAddListDTO();
		reqDTO.setCOMP_NO(COMP_NO);
		reqDTO.setBUSI_NO(BUSI_NO);
		reqDTO.setBUSI_NAME(BUSI_NAME);
		reqDTO.setOPEN_STAT(OPEN_STAT);
		reqDTO.setLEGA_NO(LEGA_NO);
		reqDTO.setSIGN_FLG(SIGN_FLG);
		reqDTO.setFLG(FLG);
		List<ParaBusiAddListDTO> reqList = new ArrayList<ParaBusiAddListDTO>();
		reqList.add(reqDTO);
		ParaBusiAddReqDTO reqBody = new ParaBusiAddReqDTO();
		reqBody.setBUSI_NUM(reqList.size());
		reqBody.setBUSI_LIST(reqList);
		
		IDataset resDs = pService.add(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 业务修改
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"paraBusiMod"})
	public void mod(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO");
		String BUSI_NO = reqDs.getString("BUSI_NO");
		String BUSI_NAME = reqDs.getString("BUSI_NAME");
		String OPEN_STAT = reqDs.getString("OPEN_STAT");
		String LEGA_NO = reqDs.getString("LEGA_NO");
		String SIGN_FLG = reqDs.getString("SIGN_FLG");
		String FLG = reqDs.getString("FLG");
		ParaBusiModListDTO reqDTO = new ParaBusiModListDTO();
		reqDTO.setCOMP_NO(COMP_NO);
		reqDTO.setBUSI_NO(BUSI_NO);
		reqDTO.setBUSI_NAME(BUSI_NAME);
		reqDTO.setOPEN_STAT(OPEN_STAT);
		reqDTO.setLEGA_NO(LEGA_NO);
		reqDTO.setSIGN_FLG(SIGN_FLG);
		reqDTO.setFLG(FLG);
		List<ParaBusiModListDTO> reqList = new ArrayList<ParaBusiModListDTO>();
		reqList.add(reqDTO);
		ParaBusiModReqDTO reqBody = new ParaBusiModReqDTO();
		reqBody.setBUSI_NUM(reqList.size());
		reqBody.setBUSI_LIST(reqList);
		
		IDataset resDs = pService.mod(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 业务删除
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"paraBusiDel"})
	public void del(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String BUSI_NO = reqDs.getString("BUSI_NO");
		ParaBusiDelListDTO reqDTO = new ParaBusiDelListDTO();
		reqDTO.setBUSI_NO(BUSI_NO);
		List<ParaBusiDelListDTO> reqList = new ArrayList<ParaBusiDelListDTO>();
		reqList.add(reqDTO);
		ParaBusiDelReqDTO reqBody = new ParaBusiDelReqDTO();
		reqBody.setBUSI_NUM(reqList.size());
		reqBody.setBUSI_LIST(reqList);
		
		IDataset resDs = pService.del(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 生成业务编号
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"creatBusiNo"})
	public void creatBusiNo(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String compNO = reqDs.getString("COMP_NO");
		ParaBusiNoCrtReqDTO reqBody = new ParaBusiNoCrtReqDTO();
		reqBody.setCOMP_NO(compNO);
		
		IDataset resDs = pService.creatBusiNo(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
		
	}
	
	/**
	 * 查询业务编号组成下拉框option
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"qryBusiNo"})
	public void qryBusiNo(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String compNO = reqDs.getString("COMP_NO");
		/*开通状态：1-开通(只返回该组件下处于开通状态的业务编号)*/
		String openStat = reqDs.getString("1");
		ParaBusiQryReqDTO reqBody = new ParaBusiQryReqDTO();
		reqBody.setCOMP_NO(compNO);
		reqBody.setOPEN_STAT(openStat);
		
		IDataset resDs = pService.qryBusiNo(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
}
