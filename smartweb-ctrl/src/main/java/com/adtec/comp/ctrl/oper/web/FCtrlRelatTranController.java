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

import com.adtec.comp.ctrl.oper.entity.FCtrlParaLoadDO;
import com.adtec.comp.ctrl.oper.entity.FCtrlRelatSysDO;
import com.adtec.comp.ctrl.dto.FCtrlTranChangePwdReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlTranForceOutNotiReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlTranLoginReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlTranLogoutReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlTranRedisRefreshReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlTranSysParaUpdateReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlTranSysRunNotiOutReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlTranSysStatChangeNotiReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlTranSysTestCommReqDTO;
import com.adtec.comp.ctrl.oper.service.FCtrlRelatTranService;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.framework.exception.SysErr;

@Controller
@RequestMapping(value="${adminPath}/comp/ctrl/oper/relatTran")
public class FCtrlRelatTranController extends BaseController{
	@Autowired
	private FCtrlRelatTranService relatTranService;
	
	/**
	 * 返回管理页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "relatTranManage" })
	public String sysManage(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/relatTran/relatTranManage";
	};
	
	/**
	 * 返回参数刷新页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "tranRedisRefre" })
	public String sysManageList(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/relatTran/tranRedisRefre";
	};
	
	/**
	 * 返回登录页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "tranLogin" })
	public String sysManageLogin(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/relatTran/tranLogin";
	};
	
	/**
	 * 返回退出页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "tranLogout" })
	public String sysManageExit(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/relatTran/tranLogout";
	};
	
	/**
	 * 返回修改密码页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "tranChangePwd" })
	public String sysPassChange(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/relatTran/tranChangePwd";
	};
	/**
	 * 返回连接测试页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "sysTestComm" })
	public String sysTestComm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/relatTran/sysTestComm";
	};
	
	
	/**
	 * 返回强制退出页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "forceOutNoti" })
	public String sysForceExit(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/relatTran/forceOutNoti";
	};

	/**
	 * 返回停运通知页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "sysRunNotiOut" })
	public String sysStopRunNoti(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/relatTran/sysRunNotiOut";
	};

	/** 
	 * 返回系统状态变更页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "sysStatChangeNoti" })
	public String sysStatusModi(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/relatTran/sysStatChangeNoti";
	};
	
	/**
	 *  关联系统下拉框
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getRelatSys" })
	public void getRelatSys(HttpServletRequest request, HttpServletResponse response){
	List<FCtrlRelatSysDO> list = relatTranService.getRuleList(new FCtrlRelatSysDO());
	  List<Map<String, Object>> maps = new ArrayList();
	  Map<String, Object> emptyMap = new HashMap<>(2);
	        emptyMap.put("label", "--请选择--");
	        emptyMap.put("value", "");
	        maps.add(emptyMap);
	        for(FCtrlRelatSysDO DO : list){
	         Map<String, Object> map = new HashMap<>(2);
	         map.put("label", DO.getRelatSys());
	         map.put("value", DO.getRelatSys());
	         maps.add(map);
	        }
	        Map<String, Object> m = new HashMap<>();
	        m.put("retCode", "0000");
	        m.put("list", maps);
	        renderString(response, m);
	 }
	
	/**
	 *  测试连接页面关联系统号下拉框
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getTestRelatSys" })
	public void getTestRelatSys(HttpServletRequest request, HttpServletResponse response){
	List<FCtrlRelatSysDO> list = relatTranService.getRuleList(new FCtrlRelatSysDO());
	  List<Map<String, Object>> maps = new ArrayList();
	  Map<String, Object> emptyMap = new HashMap<>(2);
	        emptyMap.put("label", "请选择");
	        emptyMap.put("value", "");
	        maps.add(emptyMap);
	        for(FCtrlRelatSysDO DO : list){
	        String str = DO.getDimFlg();
	         if (str.charAt(6) == 'Y')  {
	        	 Map<String, Object> map = new HashMap<>(2);
	 	         map.put("label", DO.getRelatSys());
	 	         map.put("value", DO.getRelatSys());
	 	         maps.add(map);
	         }
	        }
	        Map<String, Object> m = new HashMap<>();
	        m.put("retCode", "0000");
	        m.put("list", maps);
	        renderString(response, m);
	 }
	
	/**
	 * 参数名称下拉框
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getParaName" })
	public void getParaName(HttpServletRequest request, HttpServletResponse response){
		List<FCtrlParaLoadDO> list = relatTranService.getParaLoadList(new FCtrlParaLoadDO());
		List<Map<String, Object>> maps = new ArrayList();
		Map<String, Object> emptyMap = new HashMap<>(2);
		emptyMap.put("label", "请选择");
		emptyMap.put("value", "");
		maps.add(emptyMap);
		for(FCtrlParaLoadDO DO : list){
			Map<String, Object> map = new HashMap<>(2);
			map.put("label", DO.getChName());
			map.put("value", DO.getEngName());
			maps.add(map);
		}
		Map<String, Object> m = new HashMap<>();
		m.put("retCode", "0000");
		m.put("list", maps);
		renderString(response, m);
	}
	
	/**
	 * 关联系统号查询数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getSingleData" })
	public void getSingleData(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String RELAT_SYS= reqDs.getString("RELAT_SYS");
		FCtrlRelatSysDO paraLoadDO = relatTranService.getSingleData(new FCtrlRelatSysDO(), RELAT_SYS);
		Map<String, Object> m = new HashMap<>();
		m.put("retCode", "0000");
		m.put("list", paraLoadDO);
		renderString(response, m);
	}
	
	
	/**
	 * 登录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "login" })
	public void login(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String BUSI_NO =reqDs.getString("BUSI_NO");
		String RELAT_SYS= reqDs.getString("RELAT_SYS");
		String SYS_TP= reqDs.getString("SYS_TP");
		String OPER_TP= reqDs.getString("OPER_TP");
		String USER_NAME = reqDs.getString("USER_NAME");
		String PWD = reqDs.getString("PWD");
		String NEW_PWD= reqDs.getString("NEW_PWD");
		FCtrlTranLoginReqDTO tranLoginReqDTO = new FCtrlTranLoginReqDTO();
		tranLoginReqDTO.setRELAT_SYS(RELAT_SYS);
		tranLoginReqDTO.setSYS_TP(SYS_TP);
		tranLoginReqDTO.setOPER_TP(OPER_TP);
		tranLoginReqDTO.setUSER_NAME(USER_NAME);
		tranLoginReqDTO.setPWD(PWD);
		tranLoginReqDTO.setNEW_PWD(NEW_PWD);
		IDataset  resDs = relatTranService.login(tranLoginReqDTO,BUSI_NO);
		setResponseDataset(request, response,resDs, SysErr.E_SUCCESS,"交易成功");		
	}
	
	/**
	 * 修改密码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "change" })
	public void change(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String BUSI_NO =reqDs.getString("BUSI_NO");
		String RELAT_SYS= reqDs.getString("RELAT_SYS");
		String SYS_TP= reqDs.getString("SYS_TP");
		String OPER_TP= reqDs.getString("OPER_TP");
		String USER_NAME = reqDs.getString("USER_NAME");
		String PWD = reqDs.getString("PWD");
		String NEW_PWD= reqDs.getString("NEW_PWD");
		FCtrlTranChangePwdReqDTO manageChangReqDTO = new FCtrlTranChangePwdReqDTO();
		manageChangReqDTO.setRELAT_SYS(RELAT_SYS);
		manageChangReqDTO.setSYS_TP(SYS_TP);
		manageChangReqDTO.setOPER_TP(OPER_TP);
		manageChangReqDTO.setUSER_NAME(USER_NAME);
		manageChangReqDTO.setPWD(PWD);
		manageChangReqDTO.setNEW_PWD(NEW_PWD);
		IDataset  resDs = relatTranService.change(manageChangReqDTO,BUSI_NO);
		setResponseDataset(request, response,resDs, SysErr.E_SUCCESS,"交易成功");		
	}
	
	/**
	 * 退出
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "logout" })
	public void logout(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String BUSI_NO = reqDs.getString("BUSI_NO");
		String RELAT_SYS= reqDs.getString("RELAT_SYS");
		String SYS_TP= reqDs.getString("SYS_TP");
		String OPER_TP= reqDs.getString("OPER_TP");
		String USER_NAME = reqDs.getString("USER_NAME");
		String PWD = reqDs.getString("PWD");
		FCtrlTranLogoutReqDTO manageLogoutReqDTO = new FCtrlTranLogoutReqDTO();
		manageLogoutReqDTO.setRELAT_SYS(RELAT_SYS);
		manageLogoutReqDTO.setSYS_TP(SYS_TP);
		manageLogoutReqDTO.setOPER_TP(OPER_TP);
		manageLogoutReqDTO.setUSER_NAME(USER_NAME);
		manageLogoutReqDTO.setPWD(PWD);
		IDataset  resDs = relatTranService.logout(manageLogoutReqDTO,BUSI_NO);
		setResponseDataset(request, response,resDs, SysErr.E_SUCCESS,"交易成功");		
	}
	
	/**
	 * 连接测试
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "testconn" })
	public void testconn(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String BUSI_NO =reqDs.getString("BUSI_NO");
		 String RELAT_SYS = reqDs.getString("RELAT_SYS");
		 String SYS_TP = reqDs.getString("SYS_TP");;
		FCtrlTranSysTestCommReqDTO reqBody = new FCtrlTranSysTestCommReqDTO();
		reqBody.setRELAT_SYS(RELAT_SYS);
		reqBody.setSYS_TP(SYS_TP);
		IDataset  resDs = relatTranService.testconn(reqBody,BUSI_NO);
		setResponseDataset(request, response,resDs, SysErr.E_SUCCESS,"交易成功");		
	}
	
	
	/**
	 * 状态改变通知
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "statChangeNoti" })
	public void statChangeNoti(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String RELAT_SYS= reqDs.getString("RELAT_SYS");
		String SYS_TP= reqDs.getString("SYS_TP");
		String OPER_TP= reqDs.getString("OPER_TP");
		String USER_NAME = reqDs.getString("USER_NAME");
		String PWD = reqDs.getString("PWD");
		String SYS_STAT = reqDs.getString("SYS_STAT");
		String WORK_DATE = reqDs.getString("WORK_DATE");
		String HLD_FLG = reqDs.getString("HLD_FLG");
		String ERR_MSG = reqDs.getString("WORK_DATE");
		FCtrlTranSysStatChangeNotiReqDTO statChangeNotiReqDTO = new FCtrlTranSysStatChangeNotiReqDTO();
		statChangeNotiReqDTO.setRELAT_SYS(RELAT_SYS);
		statChangeNotiReqDTO.setSYS_TP(SYS_TP);
		statChangeNotiReqDTO.setOPER_TP(OPER_TP);
		statChangeNotiReqDTO.setUSER_NAME(USER_NAME);
		statChangeNotiReqDTO.setSYS_STAT(SYS_STAT);
		statChangeNotiReqDTO.setWORK_DATE(WORK_DATE);
		statChangeNotiReqDTO.setHLD_FLG(HLD_FLG);
		statChangeNotiReqDTO.setERR_MSG(ERR_MSG);
		statChangeNotiReqDTO.setPWD(PWD);
		IDataset  resDs = relatTranService.statChangeNoti(statChangeNotiReqDTO);
		setResponseDataset(request, response,resDs, SysErr.E_SUCCESS,"交易成功");		
	}
	
	/**
	 * 强制退出通知
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "forceLogoutNoti" })
	public void forceLogoutNoti(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String RELAT_SYS= reqDs.getString("RELAT_SYS");
		String SYS_TP= reqDs.getString("SYS_TP");
		String OPER_TP= reqDs.getString("OPER_TP");
		String USER_NAME = reqDs.getString("USER_NAME");
		String PWD = reqDs.getString("PWD");
		String REASN_DESC = reqDs.getString("REASN_DESC");
		FCtrlTranForceOutNotiReqDTO forceLogoutNotiReqDTO = new FCtrlTranForceOutNotiReqDTO();
		forceLogoutNotiReqDTO.setRELAT_SYS(RELAT_SYS);
		forceLogoutNotiReqDTO.setSYS_TP(SYS_TP);
		forceLogoutNotiReqDTO.setOPER_TP(OPER_TP);
		forceLogoutNotiReqDTO.setUSER_NAME(USER_NAME);
		forceLogoutNotiReqDTO.setPWD(PWD);
		forceLogoutNotiReqDTO.setREASN_DESC(REASN_DESC);
		IDataset  resDs = relatTranService.forceLogoutNoti(forceLogoutNotiReqDTO);
		setResponseDataset(request, response,resDs, SysErr.E_SUCCESS,"交易成功");		
	}
	
	/**
	 * 停运启运通知
	 * @param request
	 * @param response 
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "runNoti" })
	public void runNoti(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String RELAT_SYS= reqDs.getString("RELAT_SYS");
		String SYS_TP= reqDs.getString("SYS_TP");
		String OPER_TP= reqDs.getString("OPER_TP");
		String USER_NAME = reqDs.getString("USER_NAME");
		String PWD = reqDs.getString("PWD");
		String RUN_FLG = reqDs.getString("RUN_FLG");
		String STR_DT = reqDs.getString("STR_DT");
		String END_DT = reqDs.getString("END_DT");
		String REASN_DESC = reqDs.getString("REASN_DESC");
		FCtrlTranSysRunNotiOutReqDTO runNotiReqDTO = new FCtrlTranSysRunNotiOutReqDTO();
		runNotiReqDTO.setRELAT_SYS(RELAT_SYS);
		runNotiReqDTO.setSYS_TP(SYS_TP);
		runNotiReqDTO.setOPER_TP(OPER_TP);
		runNotiReqDTO.setUSER_NAME(USER_NAME);
		runNotiReqDTO.setPWD(PWD);
		runNotiReqDTO.setRUN_FLG(RUN_FLG);
		runNotiReqDTO.setSTR_DT(STR_DT);
		runNotiReqDTO.setEND_DT(END_DT);
		runNotiReqDTO.setREASN_DESC(REASN_DESC);
		IDataset  resDs = relatTranService.runNoti(runNotiReqDTO);
		setResponseDataset(request, response,resDs, SysErr.E_SUCCESS,"交易成功");		
	}
	
	/**
	 * 系统参数刷新
	 * @param request
	 * @param response 
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "sysParaUpdate" })
	public void sysParaUpdate(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String OPER_TP= reqDs.getString("OPER_TP");
		String PARA_NAME= reqDs.getString("PARA_NAME");
		FCtrlTranRedisRefreshReqDTO sysParaUpdateReqDTO = new FCtrlTranRedisRefreshReqDTO();
		sysParaUpdateReqDTO.setOPER_TP(OPER_TP);
		sysParaUpdateReqDTO.setPARA_NAME(PARA_NAME);
		IDataset  resDs = relatTranService.sysParaUpdate(sysParaUpdateReqDTO);
		setResponseDataset(request, response,resDs, SysErr.E_SUCCESS,"交易成功");		
	}
/*	@RequiresPermissions("user")
	@RequestMapping(value = { "sysParaUpdate" })
	public void sysParaUpdate(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String RELAT_SYS= reqDs.getString("RELAT_SYS");
		String SYS_TP= reqDs.getString("SYS_TP");
		String SYS_NAME= reqDs.getString("SYS_NAME");
		String STAT= reqDs.getString("STAT");
		String SYS_STAT= reqDs.getString("SYS_STAT");
		String ORIG_SYS_STAT= reqDs.getString("ORIG_SYS_STAT");
		String CLR_BRCH= reqDs.getString("CLR_BRCH");
		String CLR_BANK= reqDs.getString("CLR_BANK");
		String OTH_DATE= reqDs.getString("OTH_DATE");
		String ORIG_OTH_DATE= reqDs.getString("ORIG_OTH_DATE");
		String NODE_STAT= reqDs.getString("NODE_STAT");
		String LOGIN_STAT= reqDs.getString("LOGIN_STAT");
		String HLD_FLG= reqDs.getString("HLD_FLG");
		String MSG_SKEY= reqDs.getString("MSG_SKEY");
		String SEQ_CRT_ID= reqDs.getString("SEQ_CRT_ID");
		String FILE_SVR_ID= reqDs.getString("FILE_SVR_ID");
		String LOGIN_ID= reqDs.getString("LOGIN_ID");
		String LOGIN_PWD= reqDs.getString("LOGIN_PWD");
		String REASN_DESC= reqDs.getString("REASN_DESC");
		FCtrlTranSysParaUpdateReqDTO sysParaUpdateReqDTO = new FCtrlTranSysParaUpdateReqDTO();
		sysParaUpdateReqDTO.setRELAT_SYS(RELAT_SYS);
		sysParaUpdateReqDTO.setSYS_TP(SYS_TP);
		sysParaUpdateReqDTO.setSYS_NAME(SYS_NAME);
		sysParaUpdateReqDTO.setSTAT(STAT);
		sysParaUpdateReqDTO.setSYS_STAT(SYS_STAT);
		sysParaUpdateReqDTO.setORIG_SYS_STAT(ORIG_SYS_STAT);
		sysParaUpdateReqDTO.setCLR_BRCH(CLR_BRCH);
		sysParaUpdateReqDTO.setCLR_BANK(CLR_BANK);
		sysParaUpdateReqDTO.setOTH_DATE(OTH_DATE);
		sysParaUpdateReqDTO.setORIG_OTH_DATE(ORIG_OTH_DATE);
		sysParaUpdateReqDTO.setNODE_STAT(NODE_STAT);
		sysParaUpdateReqDTO.setLOGIN_STAT(LOGIN_STAT);
		sysParaUpdateReqDTO.setHLD_FLG(HLD_FLG);
		sysParaUpdateReqDTO.setMSG_SKEY(MSG_SKEY);
		sysParaUpdateReqDTO.setSEQ_CRT_ID(SEQ_CRT_ID);
		sysParaUpdateReqDTO.setFILE_SVR_ID(FILE_SVR_ID);
		sysParaUpdateReqDTO.setLOGIN_ID(LOGIN_ID);
		sysParaUpdateReqDTO.setLOGIN_PWD(LOGIN_PWD);
		sysParaUpdateReqDTO.setREASN_DESC(REASN_DESC);
		IDataset  resDs = relatTranService.sysParaUpdate(sysParaUpdateReqDTO);
		setResponseDataset(request, response,resDs, SysErr.E_SUCCESS,"交易成功");		
	}
*/	
	
}
