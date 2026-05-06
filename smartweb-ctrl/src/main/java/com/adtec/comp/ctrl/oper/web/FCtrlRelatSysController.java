package com.adtec.comp.ctrl.oper.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adtec.comp.ctrl.oper.entity.FCtrlRelatSysDO;
import com.adtec.comp.ctrl.oper.service.FCtrlRelatSysService;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.DatasetColumnType;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.modules.sys.utils.DictUtils;
import com.adtec.framework.exception.SysErr;

@Controller
@RequestMapping(value = "${adminPath}/comp/ctrl/oper/relatSys")
public class FCtrlRelatSysController extends BaseController {
	@Autowired
	private FCtrlRelatSysService relatSysService;
	/**
	 * 返回管理页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "relatSysManage" })
	public String sysManage(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/relatSys/relatSysManage";
	};
	
	/**
	 * 返回查询页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "relatSysList" })
	public String relatSysList(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/relatSys/relatSysList";
	}

	/**
	 * 返回新增页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "relatSysForm" })
	public String relatSysForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/relatSys/relatSysForm";
	}
	
	/**
	 * 返回修改页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "relatSysMod" })
	public String relatSysMod(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/relatSys/relatSysMod";
	}

	/**
	 * 返回详情页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "relatSysDet" })
	public String relatSysDet(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/relatSys/relatSysDet";
	}
	
	/**
	 * 列表查询
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "qry" })
	public void qry(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String RELAT_SYS = reqDs.getString("RELAT_SYS");
		String SYS_TP = reqDs.getString("SYS_TP");
		String OPEN_STAT = reqDs.getString("OPEN_STAT");
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		FCtrlRelatSysDO relatSysDO = new FCtrlRelatSysDO();
		
		relatSysDO.setRelatSys(RELAT_SYS);
		relatSysDO.setSysTp(SYS_TP);
		relatSysDO.setOpenStat(OPEN_STAT);
		
		List<FCtrlRelatSysDO> list = relatSysService.list(relatSysDO, start, limit);
		int total = relatSysService.getTotal(relatSysDO);
		IDataset resDs = DatasetService.getInstace().getDataset(list, FCtrlRelatSysDO.class);
		chgDict(resDs, true);
		resDs.setTotalCount(total);

		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

	
	/**
	 * 状态修改 after
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "UpdateStat" })
	public void UpdateStat(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String RELAT_SYS = reqDs.getString("RELAT_SYS");
		String OPEN_STAT = reqDs.getString("OPEN_STAT");
		FCtrlRelatSysDO relatSysDO = new FCtrlRelatSysDO();
		relatSysDO.setRelatSys(RELAT_SYS);
		relatSysDO.setOpenStat(OPEN_STAT);
		int rs = relatSysService.UpdateStat(relatSysDO);
		IDataset resDs = DatasetService.getInstace().getDataset();
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 提交 after
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "add" })
	public void add(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String RELAT_SYS= reqDs.getString("RELAT_SYS");
		String SYS_TP= reqDs.getString("SYS_TP");
		String SYS_NAME= reqDs.getString("SYS_NAME");
		String OPEN_STAT= reqDs.getString("OPEN_STAT");
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
		String MEMB_ID= reqDs.getString("MEMB_ID");
		String DIM_FLG= reqDs.getString("DIM_FLG");
		FCtrlRelatSysDO addDo = new FCtrlRelatSysDO();
		addDo.setSysTp(SYS_TP);
		addDo.setRelatSys(RELAT_SYS);
		addDo.setSysName(SYS_NAME);
		addDo.setOpenStat(OPEN_STAT);
		addDo.setSysStat(SYS_STAT);
		addDo.setOrigSysStat(ORIG_SYS_STAT);
		addDo.setClrBrch(CLR_BRCH);
		addDo.setClrBank(CLR_BANK);
		addDo.setOthDate(OTH_DATE);
		addDo.setOrigOthDate(ORIG_OTH_DATE);
		addDo.setNodeStat(NODE_STAT);
		addDo.setLoginStat(LOGIN_STAT);
		addDo.setHldFlg(HLD_FLG);
		addDo.setMsgSkey(MSG_SKEY);
		addDo.setSeqCrtId(SEQ_CRT_ID);
		addDo.setFileSvrId(FILE_SVR_ID);
		addDo.setLoginId(LOGIN_ID);
		addDo.setLoginPwd(LOGIN_PWD);
		addDo.setReasnDesc(REASN_DESC);
		addDo.setMembId(MEMB_ID);
		addDo.setDimFlg(DIM_FLG);
		
		if("1".equals(reqDs.getString("OPER_TP"))){
			//addDo.setRelatSys(relatSysService.getNewEntrNO());//获取单位编号
			relatSysService.add(addDo);	
		}else if("2".equals(reqDs.getString("OPER_TP"))){
			addDo.setRelatSys(RELAT_SYS);
			relatSysService.update(addDo);	
		}
		IDataset resDs = DatasetService.getInstace().getDataset();
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
		
	}
		
	/**
	 * 获取详细数据 before
	 */
	/*@RequiresPermissions("user")
	@RequestMapping(value = { "getDetail" })
	public void getDetail(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String RELAT_SYS = reqDs.getString("RELAT_SYS");
		FProdEntrQryDtlReqDTO reqBody = new FProdEntrQryDtlReqDTO();
		reqBody.setRELAT_SYS(RELAT_SYS);

		IDataset resDs = relatSysService.getDetail(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}*/
	
	/**
	 * 获取详细数据 after
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getDetail" })
	public void getDetail(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String RELAT_SYS = reqDs.getString("RELAT_SYS");
		FCtrlRelatSysDO DO = new FCtrlRelatSysDO();
		DO.setRelatSys(RELAT_SYS);
		FCtrlRelatSysDO result = relatSysService.getDetail(DO);
		IDataset resDs = DatasetService.getInstace().getDataset(result, FCtrlRelatSysDO.class);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 删除记录
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "delete" })
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String RELAT_SYS = reqDs.getString("RELAT_SYS");
		FCtrlRelatSysDO DO = new FCtrlRelatSysDO();
		DO.setRelatSys(RELAT_SYS);
		relatSysService.delete(DO);
		IDataset resDs = DatasetService.getInstace().getDataset();
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	
	
	/**
	 * 列表查询
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "qryRelatSys" })
	public void qryName(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String SYS_TP = reqDs.getString("SYS_TP");
		String RELAT_SYS = reqDs.getString("RELAT_SYS");
		FCtrlRelatSysDO relatSysDO = new FCtrlRelatSysDO();
		relatSysDO.setSysTp(SYS_TP);
		relatSysDO.setRelatSys(RELAT_SYS);
		List<FCtrlRelatSysDO> list = relatSysService.qryRelatSys(relatSysDO);
		IDataset resDs = DatasetService.getInstace().getDataset(list, FCtrlRelatSysDO.class);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

	/**
	 * 数字字典转换
	 * @param ds
	 * @param isAction
	 */
	private void chgDict(IDataset ds, boolean isAction){
		if(null==ds){
			return;
		}
		//中文描述
//		ds.addColumn("valStr", DatasetColumnType.DS_STRING);
		
		if(isAction){
			//新增操作描述列
			ds.addColumn("action", DatasetColumnType.DS_STRING);
			ds.addColumn("sysTpStr", DatasetColumnType.DS_STRING);
			ds.addColumn("openStatStr", DatasetColumnType.DS_STRING);
		}
		ds.beforeFirst();
		while(ds.hasNext()){
			ds.next();
			//中文描述
//			ds.updateString("valStr", ProdStrEnum.getSelectKEY_TP(ds.getString("val")));
//			if("00".equals(ds.getString("stat"))){
//				ds.updateString("openStatStr", "正常");
//			}else if("01".equals(ds.getString("stat"))){
//				ds.updateString("openStatStr", "停用");
//			}
//			if("00".equals(ds.getString("sysTp"))){
//				ds.updateString("sysTpStr", "行内系统");
//			}else if("01".equals(ds.getString("sysTp"))){
//				ds.updateString("sysTpStr", "行外系统");
//			}
			String openStat = ds.getString("openStat");
			ds.updateString("openStatStr", DictUtils.getDictLabels(openStat, "OPEN_STAT", openStat));
			String sysTp = ds.getString("sysTp");
			ds.updateString("sysTpStr", DictUtils.getDictLabels(sysTp, "SYS_TP", sysTp));
			if(isAction){
				/*添加相关操作按钮*/
				StringBuffer action = new StringBuffer();
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"Detail('" + ds.getString("relatSys") + "')\" >详情</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"Revice('" + ds.getString("relatSys") + "')\" >修改</a>");
				
				/*action.append("	<a href=\"JavaScript:void(0);\" onClick=\"Delete('"+ds.getString("entrNo")+"')\" >删除</a>");*/
				ds.updateString("action", action.toString());
			}
		}
	}
}
