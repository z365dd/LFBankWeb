package com.adtec.comp.fsvr.tec.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adtec.comp.fsvr.dto.TfmngOmngSvrDimAddModListReqDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrDimAddModReqDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrDimDelReqDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrDimDtlQryReqDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrDimQryReqDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrParaAddModListReqDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrParaAddModReqDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrParaDelReqDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrParaDtlQryReqDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrParaQryReqDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrPortParaAddModReqDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrPortParaDelReqDTO;
import com.adtec.comp.fsvr.dto.TfmngOmngSvrPortParaQryReqDTO;
import com.adtec.comp.fsvr.tec.entity.TfsvrSvrDeponNetParaDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrSvrDeponParaDO;
import com.adtec.comp.fsvr.tec.service.FileTecService;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;


@Controller
@RequestMapping(value = "${adminPath}/comp/fsvr/tec/fileTec")
public class FileTecController extends BaseController{
	@Autowired
	private FileTecService fileTecService;
	
	/**
	 * 返回内部查询页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "inFileSerQry" })
	public String inFileSerQry(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/fsvr/tec/inFileSerQry";
	}

	/**
	 * 返回内部新增页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "inFileSerForm" })
	public String inFileSerForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/fsvr/tec/inFileSerForm";
	}
	
	/**
	 * 返回外部查询页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "outFileSerQry" })
	public String outFileSerQry(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/fsvr/tec/outFileSerQry";
	}

	/**
	 * 返回外部新增页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "outFileSerForm" })
	public String outFileSerForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/fsvr/tec/outFileSerForm";
	}
	
	/**
	 * 返回端口管理查询页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "portManageQry" })
	public String portManageQry(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/fsvr/tec/portManageQry";
	}

	/**
	 * 返回端口管理新增页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "portManageForm" })
	public String portManageForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/fsvr/tec/portManageForm";
	}
	
	/**
	 * 返回维度管理查询页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "dimensionQry" })
	public String dimensionQry(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/fsvr/tec/dimensionQry";
	}

	/**
	 * 返回维度管理新增页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "dimensionForm" })
	public String dimensionForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/fsvr/tec/dimensionForm";
	}
	
	/**
	 * 内部服务新增修改获取数据
	 */
	public TfmngOmngSvrParaAddModReqDTO getReqBody(IDataset reqDs){
		String IP = reqDs.getString("IP");
		String PORT = reqDs.getString("PORT");
		String DOWNLOAD_PATH = reqDs.getString("DOWNLOAD_PATH");
		String OPEN_SVC_FLG = reqDs.getString("OPEN_SVC_FLG");
		String SVR_DESC = reqDs.getString("SVR_DESC");
		String FILE_SVR_ID = reqDs.getString("FILE_SVR_ID");
		String FILE_SVR_TP = reqDs.getString("FILE_SVR_TP");
		String COMM_PROT_TP = reqDs.getString("COMM_PROT_TP");
		String USER_NO = reqDs.getString("USER_NO");
		String PWD = reqDs.getString("PWD");
		String UPLOAD_PATH = reqDs.getString("UPLOAD_PATH");
		String STAT = reqDs.getString("STAT");
		String MEMB_ID = reqDs.getString("MEMB_ID");
		String TRAN_TP = reqDs.getString("TRAN_TP");
		String CONT_FLG = reqDs.getString("CONT_FLG");
		String DEPON_FILE_SVR_ID =reqDs.getString("DEPON_FILE_SVR_ID");
		
		TfmngOmngSvrParaAddModReqDTO reqBody = new TfmngOmngSvrParaAddModReqDTO();
		
		if(DEPON_FILE_SVR_ID!=null&&DEPON_FILE_SVR_ID!=""){
			String[] arr = DEPON_FILE_SVR_ID.split("\\|");
			List <TfmngOmngSvrParaAddModListReqDTO> LIST  = new ArrayList();
			if(arr.length>0){
				for(String str : arr ){
					TfmngOmngSvrParaAddModListReqDTO l = new TfmngOmngSvrParaAddModListReqDTO();
					l.setDEPON_FILE_SVR_ID(str);
					LIST.add(l);		
				}
			}
			reqBody.setLIST(LIST);
		}
		
		
		reqBody.setIP(IP);
		reqBody.setPORT(PORT);
		reqBody.setDOWNLOAD_FILE_PATH(DOWNLOAD_PATH);
		reqBody.setOPEN_SVC_FLG(OPEN_SVC_FLG);
		reqBody.setSVR_DESC(SVR_DESC);
		reqBody.setFILE_SVR_ID(FILE_SVR_ID);
		reqBody.setFILE_SVR_TP(FILE_SVR_TP);
		reqBody.setCOMM_PROT_GRP_TP(COMM_PROT_TP);
		reqBody.setUSER_NO(USER_NO);
		reqBody.setPWD(PWD);
		reqBody.setUPLOAD_FILE_PATH(UPLOAD_PATH);
		reqBody.setSTAT(STAT);
		reqBody.setTRAN_TP(TRAN_TP);
		reqBody.setMEMB_ID(MEMB_ID);
		reqBody.setCONT_FLG(CONT_FLG);
		return reqBody;
	}
	
	/**
	 * 内部服务新增
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "add" })
	public void add(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		TfmngOmngSvrParaAddModReqDTO reqBody = getReqBody(reqDs);

		IDataset resDs = fileTecService.add(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 内部服务修改提交
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "revice" })
	public void revice(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		TfmngOmngSvrParaAddModReqDTO reqBody = getReqBody(reqDs);

		IDataset resDs = fileTecService.revice(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	
	/**
	 * 内部服务列表查询
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "qry" })
	public void qry(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String IP = reqDs.getString("IP");
		String STAT = reqDs.getString("FILE_SVR_STAT");
		String FILE_SVR_ID = reqDs.getString("FILE_SVR_ID");
		String FILE_SVR_TP = reqDs.getString("FILE_SVR_TP");
		
		TfmngOmngSvrParaQryReqDTO reqBody = new TfmngOmngSvrParaQryReqDTO();
		reqBody.setIP(IP);
		reqBody.setSTAT(STAT);
		reqBody.setFILE_SVR_ID(FILE_SVR_ID);
		reqBody.setFILE_SVR_TP(FILE_SVR_TP);

		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");

		IDataset resDs = fileTecService.qry(reqBody, start, limit);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 内部服务详细查询
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getDetail" })
	public void getDetail(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String FILE_SVR_ID = reqDs.getString("FILE_SVR_ID");
		
		TfmngOmngSvrParaDtlQryReqDTO reqBody = new TfmngOmngSvrParaDtlQryReqDTO();
		reqBody.setFILE_SVR_ID(FILE_SVR_ID);

		IDataset resDs = fileTecService.getDetail(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 内部服务删除
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "Delete" })
	public void Delete(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String FILE_SVR_ID = reqDs.getString("FILE_SVR_ID");
		
		TfmngOmngSvrParaDelReqDTO reqBody = new TfmngOmngSvrParaDelReqDTO();
		reqBody.setFILE_SVR_ID(FILE_SVR_ID);

		IDataset resDs = fileTecService.Delete(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	
	/**
	 * 内部服务 修改状态
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "ChangeStat" })
	public void ChangeStat(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String FILE_SVR_ID = reqDs.getString("FILE_SVR_ID");
		String TRAN_TP = reqDs.getString("TRAN_TP");
		String STAT = reqDs.getString("FILE_SVR_STAT");
		
		TfmngOmngSvrParaAddModReqDTO reqBody = new TfmngOmngSvrParaAddModReqDTO();
		reqBody.setFILE_SVR_ID(FILE_SVR_ID);
		reqBody.setTRAN_TP(TRAN_TP);
		reqBody.setSTAT(STAT);
		
		IDataset resDs = fileTecService.revice(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 端口管理新增修改获取数据
	 */
	public TfmngOmngSvrPortParaAddModReqDTO getPortReqBody(IDataset reqDs){
		String COMP_NO = reqDs.getString("COMP_NO");
		String COMP_NAME = reqDs.getString("COMP_NAME");
		String PORT = reqDs.getString("PORT");
		
		TfmngOmngSvrPortParaAddModReqDTO reqBody = new TfmngOmngSvrPortParaAddModReqDTO();
		reqBody.setCOMP_NO(COMP_NO);
		reqBody.setCOMP_NAME(COMP_NAME);
		reqBody.setPORT(PORT);
		
		return reqBody;
	}
	
	/**
	 * 端口管理新增
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "portAdd" })
	public void portAdd(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		TfmngOmngSvrPortParaAddModReqDTO reqBody = getPortReqBody(reqDs);

		IDataset resDs = fileTecService.portAdd(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 端口管理修改提交
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "portRevice" })
	public void portRevice(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		TfmngOmngSvrPortParaAddModReqDTO reqBody = getPortReqBody(reqDs);

		IDataset resDs = fileTecService.portRevice(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 端口列表查询
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "portQry" })
	public void portQry(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO");
		String COMP_NAME = reqDs.getString("COMP_NAME");
		String PORT = reqDs.getString("PORT");
		
		TfmngOmngSvrPortParaQryReqDTO reqBody = new TfmngOmngSvrPortParaQryReqDTO();
		reqBody.setCOMP_NO(COMP_NO);
		reqBody.setCOMP_NAME(COMP_NAME);
		reqBody.setPORT(PORT);

		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");

		IDataset resDs = fileTecService.portQry(reqBody, start, limit);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	
	/**
	 * 端口管理 删除
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "portDelete" })
	public void portDelete(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String COMP_NO = reqDs.getString("COMP_NO");
		
		TfmngOmngSvrPortParaDelReqDTO reqBody = new TfmngOmngSvrPortParaDelReqDTO();
		reqBody.setCOMP_NO(COMP_NO);

		IDataset resDs = fileTecService.portDelete(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 网络依赖列表查询
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "deponNetQry" })
	public void deponNetQry(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		TfsvrSvrDeponNetParaDO qryDo = new TfsvrSvrDeponNetParaDO();
		qryDo.setFileResTp("1");
		List<TfsvrSvrDeponNetParaDO> list = fileTecService.deponNetQry(qryDo);
		int total = list.size();
		resDs = DatasetService.getInstace().getDataset(list,TfsvrSvrDeponNetParaDO.class);
		resDs.setTotalCount(total);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "列表查询交易成功！");
	}
	
	/**
	 * 网络依赖关系列表查询
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "deponQry" })
	public void deponQry(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String FILE_SVR_ID = reqDs.getString("FILE_SVR_ID");
		TfsvrSvrDeponParaDO qryDo = new TfsvrSvrDeponParaDO();
		qryDo.setFileSvrId(FILE_SVR_ID);
		List<TfsvrSvrDeponParaDO> list = fileTecService.deponQry(qryDo);
		int total = list.size();
		resDs = DatasetService.getInstace().getDataset(list,TfsvrSvrDeponParaDO.class);
		resDs.setTotalCount(total);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "列表查询交易成功！");
	}
	
	/**
	 * 维度新增修改获取数据
	 */
	public TfmngOmngSvrDimAddModReqDTO getDimensionReqBody(IDataset reqDs){
		String BUSI_NO = reqDs.getString("BUSI_NO");
		String ENTR_NO = reqDs.getString("ENTR_NO");
		String LEGA_NO = reqDs.getString("LEGA_NO");
		String TRAN_CODE = reqDs.getString("TRAN_CODE");
		String CHNL_NO = reqDs.getString("CHNL_NO");
		String DEF_VAL = reqDs.getString("DEF_VAL");
		String DIM_DESC = reqDs.getString("DIM_DESC");
		String FILE_SVR_ID = reqDs.getString("FILE_SVR_ID");
		String STAT = reqDs.getString("STAT");
		String TRAN_TP = reqDs.getString("TRAN_TP");
		
		String COMP_NO = reqDs.getString("COMP_NO");
		String COMP_NAME = reqDs.getString("COMP_NAME");
		
		TfmngOmngSvrDimAddModReqDTO reqBody = new TfmngOmngSvrDimAddModReqDTO();
		reqBody.setBUSI_NO(BUSI_NO);
		reqBody.setENTR_NO(ENTR_NO);
		reqBody.setLEGA_NO(LEGA_NO);
		reqBody.setTRAN_CODE(TRAN_CODE);
		reqBody.setCHNL_NO(CHNL_NO);
		reqBody.setDEF_VAL(DEF_VAL);
		reqBody.setDIM_DESC(DIM_DESC);
		reqBody.setFILE_SVR_ID(FILE_SVR_ID);
		reqBody.setSTAT(STAT);
		reqBody.setTRAN_TP(TRAN_TP);
		
		List<TfmngOmngSvrDimAddModListReqDTO> LIST = new ArrayList<TfmngOmngSvrDimAddModListReqDTO>();
		String[] compNoArr = (COMP_NO+"").split(";");
		String[] compNameArr = (COMP_NAME+"").split(";");
		for(int a=0;a<compNoArr.length;a++){
			TfmngOmngSvrDimAddModListReqDTO listDto = new TfmngOmngSvrDimAddModListReqDTO();
			listDto.setCOMP_NAME(compNameArr[a]);
			listDto.setCOMP_NO(compNoArr[a]);
			LIST.add(listDto);
		}
		reqBody.setLIST(LIST);
		return reqBody;
	}
	
	/**
	 * 维度新增
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "dimensionAdd" })
	public void dimensionAdd(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		TfmngOmngSvrDimAddModReqDTO reqBody = getDimensionReqBody(reqDs);

		IDataset resDs = fileTecService.dimensionAdd(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 维度修改提交
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "dimensionRevice" })
	public void dimensionRevice(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		TfmngOmngSvrDimAddModReqDTO reqBody = getDimensionReqBody(reqDs);

		IDataset resDs = fileTecService.dimensionRevice(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 维度列表查询
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "conDimensionQry" })
	public void conDimensionQry(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String BUSI_NO = reqDs.getString("BUSI_NO");
		String ENTR_NO = reqDs.getString("ENTR_NO");
		String LEGA_NO = reqDs.getString("LEGA_NO");
		String TRAN_CODE = reqDs.getString("TRAN_CODE");
		String CHNL_NO = reqDs.getString("CHNL_NO");
		String DEF_VAL = reqDs.getString("DEF_VAL");
		String FILE_SVR_ID = reqDs.getString("FILE_SVR_ID");
		String STAT = reqDs.getString("STAT");
		
		TfmngOmngSvrDimQryReqDTO reqBody = new TfmngOmngSvrDimQryReqDTO();
		reqBody.setBUSI_NO(BUSI_NO);
		reqBody.setENTR_NO(ENTR_NO);
		reqBody.setLEGA_NO(LEGA_NO);
		reqBody.setTRAN_CODE(TRAN_CODE);
		reqBody.setCHNL_NO(CHNL_NO);
		reqBody.setDEF_VAL(DEF_VAL);
		reqBody.setFILE_SVR_ID(FILE_SVR_ID);
		reqBody.setSTAT(STAT);

		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");

		IDataset resDs = fileTecService.conDimensionQry(reqBody, start, limit);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 维度详细查询
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "dimensionGetDetail" })
	public void dimensionGetDetail(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String BUSI_NO = reqDs.getString("BUSI_NO");
		String ENTR_NO = reqDs.getString("ENTR_NO");
		String LEGA_NO = reqDs.getString("LEGA_NO");
		String TRAN_CODE = reqDs.getString("TRAN_CODE");
		String CHNL_NO = reqDs.getString("CHNL_NO");
		String DEF_VAL = reqDs.getString("DEF_VAL");
		
		TfmngOmngSvrDimDtlQryReqDTO reqBody = new TfmngOmngSvrDimDtlQryReqDTO();
		reqBody.setBUSI_NO(BUSI_NO);
		reqBody.setENTR_NO(ENTR_NO);
		reqBody.setLEGA_NO(LEGA_NO);
		reqBody.setTRAN_CODE(TRAN_CODE);
		reqBody.setCHNL_NO(CHNL_NO);
		reqBody.setDEF_VAL(DEF_VAL);

		IDataset resDs = fileTecService.dimensionGetDetail(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 维度删除
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "dimensionDelete" })
	public void dimensionDelete(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String BUSI_NO = reqDs.getString("BUSI_NO");
		String ENTR_NO = reqDs.getString("ENTR_NO");
		String LEGA_NO = reqDs.getString("LEGA_NO");
		String TRAN_CODE = reqDs.getString("TRAN_CODE");
		String CHNL_NO = reqDs.getString("CHNL_NO");
		String DEF_VAL = reqDs.getString("DEF_VAL");
		
		TfmngOmngSvrDimDelReqDTO reqBody = new TfmngOmngSvrDimDelReqDTO();
		reqBody.setBUSI_NO(BUSI_NO);
		reqBody.setENTR_NO(ENTR_NO);
		reqBody.setLEGA_NO(LEGA_NO);
		reqBody.setTRAN_CODE(TRAN_CODE);
		reqBody.setCHNL_NO(CHNL_NO);
		reqBody.setDEF_VAL(DEF_VAL);

		IDataset resDs = fileTecService.dimensionDelete(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 维度修改状态
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "dimensionChangeStat" })
	public void dimensionChangeStat(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String BUSI_NO = reqDs.getString("BUSI_NO");
		String ENTR_NO = reqDs.getString("ENTR_NO");
		String LEGA_NO = reqDs.getString("LEGA_NO");
		String TRAN_CODE = reqDs.getString("TRAN_CODE");
		String CHNL_NO = reqDs.getString("CHNL_NO");
		String DEF_VAL = reqDs.getString("DEF_VAL");
		String DIM_DESC = reqDs.getString("DIM_DESC");
		String STAT = reqDs.getString("STAT");
		String FILE_SVR_ID = reqDs.getString("FILE_SVR_ID");
		String TRAN_TP = reqDs.getString("TRAN_TP");
		
		TfmngOmngSvrDimAddModReqDTO reqBody = new TfmngOmngSvrDimAddModReqDTO();
		reqBody.setBUSI_NO(BUSI_NO);
		reqBody.setENTR_NO(ENTR_NO);
		reqBody.setLEGA_NO(LEGA_NO);
		reqBody.setTRAN_CODE(TRAN_CODE);
		reqBody.setCHNL_NO(CHNL_NO);
		reqBody.setDEF_VAL(DEF_VAL);
		reqBody.setDIM_DESC(DIM_DESC);
		reqBody.setFILE_SVR_ID(FILE_SVR_ID);
		reqBody.setSTAT(STAT);
		reqBody.setTRAN_TP(TRAN_TP);
		
		IDataset resDs = fileTecService.dimensionRevice(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 文件服务器标志查询
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "qryFileSvr" })
	public void qryFileSvr(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String FILE_SVR_TP = reqDs.getString("FILE_SVR_TP");
		
		TfmngOmngSvrParaQryReqDTO reqBody = new TfmngOmngSvrParaQryReqDTO();
		reqBody.setFILE_SVR_TP(FILE_SVR_TP);

		IDataset resDs = fileTecService.qryFileSvr(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
}
