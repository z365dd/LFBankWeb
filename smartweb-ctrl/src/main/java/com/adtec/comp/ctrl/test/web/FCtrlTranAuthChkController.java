package com.adtec.comp.ctrl.test.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adtec.comm.dto.head.AppHeadAUListReqDTO;
import com.adtec.comp.ctrl.test.dto.FCtrlTranAuthChkReqDTO;
import com.adtec.comp.ctrl.test.dto.FCtrlTranFileDTO;
import com.adtec.comp.ctrl.test.dto.FCtrlTranTestReqDTO;
import com.adtec.comp.ctrl.test.entity.TCtrlFileMsgDo;
import com.adtec.comp.ctrl.test.service.FCtrlTranAuthChkService;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.framework.exception.SysErr;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 授权检查 Controller
 * @author wcz
 */

@Controller
@RequestMapping(value="${adminPath}/comp/ctrl/test/tranauthchk")
public class FCtrlTranAuthChkController extends BaseController{
	
	@Autowired
	private FCtrlTranAuthChkService authChkService;
	
	@RequiresPermissions("user")
	@RequestMapping(value = { "start","" })
	public String start(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/test/fCtrlTranAuthChk";
	}
	
	//主页面
	@RequiresPermissions("user")
	@RequestMapping(value = { "toList","" })
	public String toList(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/test/fCtrlTranAuthList";
	}
	
	//返回载入案例界面(查询案例)
	@RequiresPermissions("user")
	@RequestMapping(value = { "toQry","" })
	public String toQry(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/test/fCtrlTranAuthQry";
	}
	
	//返回另保存案例界面
	@RequiresPermissions("user")
	@RequestMapping(value = { "toSave","" })
	public String toSave(HttpServletRequest request, HttpServletResponse response)  {
		return "starring/comp/ctrl/test/fCtrlTranAuthSave";
	}
	
	/**
	 * 保存案例(实际上就是更新已有的文件的内容，否则就跳到另存为操作)
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"saveAuth"})
	public void saveAuth(HttpServletRequest request,HttpServletResponse response,ModelMap map){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String caseNo = reqDs.getString("caseNo");
		String caseName = reqDs.getString("caseName");
		String Flg = "auth";
		String fileName = reqDs.getString("fileName");
		String COMP_NO = reqDs.getString("COMP_NO");
		String SVC_CODE = reqDs.getString("SVC_CODE");
		String SUB_SVC_CODE = reqDs.getString("SUB_SVC_CODE");
		String PUB_DIM_LIST = reqDs.getString("PUB_DIM_LIST");
		String PRI_DIM_LIST = reqDs.getString("PRI_DIM_LIST");
		String TRL_LIST = reqDs.getString("TRL_LIST");
		String TRAN_AMT = reqDs.getString("TRAN_AMT");
		FCtrlTranFileDTO reqBody = new FCtrlTranFileDTO();
		reqBody.setCaseNo(caseNo);
		reqBody.setCaseName(caseName);
		reqBody.setFlg(Flg);
		reqBody.setFileName(fileName);
		reqBody.setCOMP_NO(COMP_NO);
		reqBody.setSVC_CODE(SVC_CODE);
		reqBody.setSUB_SVC_CODE(SUB_SVC_CODE);
		reqBody.setPUB_DIM_LIST(PUB_DIM_LIST);
		reqBody.setPRI_DIM_LIST(PRI_DIM_LIST);
		reqBody.setTRL_LIST(TRL_LIST);
		if(!"".equals(TRAN_AMT)){
			reqBody.setTRAN_AMT(Double.parseDouble(TRAN_AMT));
		}
		
		IDataset resDs = authChkService.save(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 另存为案例
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"saveNewAuth"})
	public void saveNewAuth(HttpServletRequest request,HttpServletResponse response,ModelMap map){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String caseName = reqDs.getString("caseName");
		String Flg = "auth";
		String fileName = reqDs.getString("fileName");
		String COMP_NO = reqDs.getString("COMP_NO");
		String SVC_CODE = reqDs.getString("SVC_CODE");
		String SUB_SVC_CODE = reqDs.getString("SUB_SVC_CODE");
		String PUB_DIM_LIST = reqDs.getString("PUB_DIM_LIST");
		String PRI_DIM_LIST = reqDs.getString("PRI_DIM_LIST");
		String TRL_LIST = reqDs.getString("TRL_LIST");
		String TRAN_AMT = reqDs.getString("TRAN_AMT");
		FCtrlTranFileDTO reqBody = new FCtrlTranFileDTO();
		reqBody.setCaseName(caseName);
		reqBody.setFlg(Flg);
		reqBody.setFileName(fileName);
		reqBody.setCOMP_NO(COMP_NO);
		reqBody.setSVC_CODE(SVC_CODE);
		reqBody.setSUB_SVC_CODE(SUB_SVC_CODE);
		reqBody.setPUB_DIM_LIST(PUB_DIM_LIST);
		reqBody.setPRI_DIM_LIST(PRI_DIM_LIST);
		reqBody.setTRL_LIST(TRL_LIST);
		if(!"".equals(TRAN_AMT)){
			reqBody.setTRAN_AMT(Double.parseDouble(TRAN_AMT));
		}
		
		IDataset resDs = authChkService.saveNew(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 查询案例
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"qryAuth"})
	public void qryAuth(HttpServletRequest request,HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String caseNo = reqDs.getString("caseNo");
		String caseName = reqDs.getString("caseName");
		String FLG = "auth";
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		TCtrlFileMsgDo reqBody = new TCtrlFileMsgDo();
		reqBody.setCaseNo(caseNo);
		reqBody.setCaseName(caseName);
		reqBody.setFlg(FLG);
		IDataset resDs = authChkService.qry(reqBody, start, limit);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 获取案例(并把数据返回到主页面)
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"getAuth"})
	public void getAuth(HttpServletRequest request,HttpServletResponse response,PrintWriter out) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String caseNo = reqDs.getString("caseNo");
		String caseName = reqDs.getString("caseName");
		String FLG = "auth";
		TCtrlFileMsgDo reqBody = new TCtrlFileMsgDo();
		reqBody.setCaseNo(caseNo);
		reqBody.setCaseName(caseName);
		reqBody.setFlg(FLG);
		FCtrlTranFileDTO ftdto = authChkService.getFileDTO(reqBody);
		ftdto.setCaseName(caseName);
		ftdto.setCaseNo(caseNo);
		JSONObject json = JSONObject.fromObject(ftdto);
		JSONObject jo = new JSONObject();
		jo.put("caseJson", json);
		out.print(jo);
	}
	
	/**
	 * 删除案例
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"delAuth"})
	public void delAuth(HttpServletRequest request,HttpServletResponse response,PrintWriter out){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String caseNo = reqDs.getString("caseNo");
		String caseName = reqDs.getString("caseName");
		String FLG = "auth";
		TCtrlFileMsgDo reqBody = new TCtrlFileMsgDo();
		reqBody.setCaseNo(caseNo);
		reqBody.setCaseName(caseName);
		reqBody.setFlg(FLG);
		int rs = authChkService.del(reqBody);
		JSONObject jo = new JSONObject();
		jo.put("rs", rs);
		out.print(jo);
	}
	
	/**
	 * 测试案例
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"testAuth"})
	public void testAuth(HttpServletRequest request,HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		
		String SUB_SVC_CODE = reqDs.getString("SUB_SVC_CODE");
		String TRAN_AMT = reqDs.getString("TRAN_AMT");
		String DYN_DATA_ARR = reqDs.getString("DYN_DATA");
		JSONObject DYN_DATA = JSONObject.fromObject(DYN_DATA_ARR);
		
		/* 请求报文头 赋值 */
		FCtrlTranTestReqDTO testReq = new FCtrlTranTestReqDTO();
		/*系统头-组件号、服务码赋值*/
		String COMP_NO = reqDs.getString("COMP_NO");
		String SVC_CODE = reqDs.getString("SVC_CODE");
		testReq.setCOMP_NO(COMP_NO);
		testReq.setSVC_CODE(SVC_CODE);
		
		/*应用头-授权柜员号赋值*/
		String TLR_ARR = reqDs.getString("TLR_ARR");
		List<AppHeadAUListReqDTO> tlrList = new ArrayList<AppHeadAUListReqDTO>();
		if(TLR_ARR!=null&&!"".equals(TLR_ARR)){
			JSONArray jarr = JSONArray.fromObject(TLR_ARR);
			for(int i=0;i<jarr.size();i++){
				JSONObject jo = (JSONObject) jarr.get(i);
				String TLR_NO = jo.getString("TLR_NO");
				String BRCH = jo.getString("BRCH");
				AppHeadAUListReqDTO tlrDTO = new AppHeadAUListReqDTO();
				tlrDTO.setAUTH_TLR_NO(TLR_NO);
				tlrDTO.setAUTH_BRCH(BRCH);
				tlrList.add(tlrDTO);
			}
			
		}
		testReq.setTlrList(tlrList);
		
		FCtrlTranAuthChkReqDTO req = new FCtrlTranAuthChkReqDTO();
		req.setSUB_SVC_CODE(SUB_SVC_CODE);
		req.setDYN_DATA(DYN_DATA);
		if(!"".equals(TRAN_AMT)){
			req.setTRAN_AMT(Double.parseDouble(TRAN_AMT));
		}else{
			req.setTRAN_AMT(0);
		}
		
		IDataset resDs = authChkService.testAuth(req,testReq);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
}
