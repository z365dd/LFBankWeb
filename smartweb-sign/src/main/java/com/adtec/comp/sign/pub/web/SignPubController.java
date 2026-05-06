package com.adtec.comp.sign.pub.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adtec.comp.sign.dto.ParaBusiNoCrtReqDTO;
import com.adtec.comp.sign.dto.ParaBusiQryReqDTO;
import com.adtec.comp.sign.dto.ParaEntrNoCrtReqDTO;
import com.adtec.comp.sign.dto.SignChnlQryReqDTO;
import com.adtec.comp.sign.dto.SignCustItemQryReqDTO;
import com.adtec.comp.sign.dto.SignEntrItemQryReqDTO;
import com.adtec.comp.sign.pub.service.SignPubService;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;


@Controller
@RequestMapping(value="${adminPath}/comp/sign/pub")
public class SignPubController extends BaseController{
	
	@Autowired
	private SignPubService signPubService;
	
	/**
	 * 单位编号生成
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"getBusiNo"})
	public void crtEntrNo(HttpServletRequest request,HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String legaNo = reqDs.getString("LEGA_NO");
		
		ParaEntrNoCrtReqDTO reqBody = new ParaEntrNoCrtReqDTO();
		reqBody.setLEGA_NO(legaNo);
		
		IDataset res = signPubService.crtEntrNo(reqBody);
		setResponseDataset(request, response, res, SysErr.E_SUCCESS, "删除成功");
	}
	
	/**
	 *  搜索树 获取业务编号数据
	 * 
	 * @param state 状态,多个用,隔开
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "busiData")
	public List<Map<String, Object>> busiData(String compNo, String state,String flg,String signFlg) {
		if(compNo == null){
			compNo = "";
		}
		if(state == null){
			state = "0";
		}
		if(flg == null){
			flg ="";
		}
		if(signFlg == null){
			signFlg ="";
		}
		
		return signPubService.listBusiData(compNo, state,flg,signFlg);
	}
	
	/**
	 *  搜索树 获取签约单位数据
	 * 
	 * @param state 状态,多个用,隔开
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "entrSignData")
	public List<Map<String, Object>> entrSignData(String busiNo, String subBusiNo, String acctNo, String stat) {
		if(stat == null){
			stat = "";
		}
		return signPubService.listEntrData(busiNo, subBusiNo, acctNo, stat);
	}
	
	/**
	 *  搜索树 获取单位维护数据
	 * 
	 * @param state 状态,多个用,隔开
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "entrData")
	public List<Map<String, Object>> entrData(String legaNo, String stat) {
		if(stat == null){
			stat = "";
		}
		return signPubService.listEntrData(legaNo, stat);
	}
	
	/**
	 *单位检查项查询
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"entrItemQry"})
	public void entrItemQry(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String busiNo = reqDs.getString("BUSI_NO");
		String subBusiNo = reqDs.getString("SUB_BUSI_NO");
		String entrNo = reqDs.getString("ENTR_NO");
		SignEntrItemQryReqDTO reqBody = new SignEntrItemQryReqDTO();
		reqBody.setBUSI_NO(busiNo);
		reqBody.setSUB_BUSI_NO(subBusiNo);
		reqBody.setENTR_NO(entrNo);
		
		IDataset resDs = signPubService.entrItemQry(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 *签约渠道查询
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"signChnlQry"})
	public void signChnlQry(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String busiNo = reqDs.getString("BUSI_NO");
		String subBusiNo = reqDs.getString("SUB_BUSI_NO");
		String entrNo = reqDs.getString("ENTR_NO");
		String chnlNo = reqDs.getString("CHNL_NO");
		SignChnlQryReqDTO reqBody = new SignChnlQryReqDTO();
		reqBody.setBUSI_NO(busiNo);
		reqBody.setSUB_BUSI_NO(subBusiNo);
		reqBody.setENTR_NO(entrNo);
		reqBody.setCHNL_NO(chnlNo);
		
		IDataset resDs = signPubService.signChnlQry(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 客户签约检查项查询
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"signCustItemQry"})
	public void SignCustItemQry(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String busiNo = reqDs.getString("BUSI_NO");
		String subBusiNo = reqDs.getString("SUB_BUSI_NO");
		String entrNo = reqDs.getString("ENTR_NO");
		SignCustItemQryReqDTO reqBody = new SignCustItemQryReqDTO();
		reqBody.setBUSI_NO(busiNo);
		reqBody.setSUB_BUSI_NO(subBusiNo);
		reqBody.setENTR_NO(entrNo);
		
		IDataset resDs = signPubService.SignCustItemQry(reqBody);
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
		
		IDataset resDs = signPubService.creatBusiNo(reqBody);
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
		
		IDataset resDs = signPubService.qryBusiNo(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 *  搜索树 获取单位签约的业务数据
	 * 
	 * @param state 状态,多个用,隔开
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "entrSignBusiList")
	public List<Map<String, Object>> entrSignBusiList(String entrNo, String busiNo, String state) {
		if(state == null){
			state = "";
		}
		return signPubService.listEntrBusi(entrNo, busiNo, state);
	}
	
	/**
	 * 获取单位签约的子业务数据
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"entrSignSubBusiList"})
	public void entrSignSubBusiList(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String entrNo = reqDs.getString("entrNo");
		String busiNo = reqDs.getString("busiNo");
		String subBusiNo = reqDs.getString("subBusiNo");
		String stat = reqDs.getString("state");
		IDataset resDs = signPubService.listEntrSubBusi(entrNo, busiNo, subBusiNo, stat);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
}
