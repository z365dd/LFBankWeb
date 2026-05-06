package com.adtec.comp.fsvr.om.web;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adtec.comp.fsvr.dto.TfmngMngJrnlDtlQryReqDTO;
import com.adtec.comp.fsvr.dto.TfmngMngJrnlQryReqDTO;
import com.adtec.comp.fsvr.dto.TfmngMngJrnlStatsReqDTO;
import com.adtec.comp.fsvr.om.entity.FsvrTPipBusiDO;
import com.adtec.comp.fsvr.om.entity.FsvrTPipCompDO;
import com.adtec.comp.fsvr.om.service.FileOmService;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.modules.sys.entity.Dict;
import com.adtec.sys.modules.sys.utils.DictUtils;


@Controller
@RequestMapping(value = "${adminPath}/comp/fsvr/om/qryCount")
public class FileOmController extends BaseController{
	@Autowired
	private FileOmService fileOmService;
	
	/**
	 * 返回统计查询页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "countQry" })
	public String countQry(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/fsvr/om/countQry";
	}

	/**
	 * 返回流水查询页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "flowQry" })
	public String flowQry(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/fsvr/om/flowQry";
	}
	
	/**
	 * 返回流水详细页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "flowDetail" })
	public String flowDetail(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/fsvr/om/flowDetail";
	}
	
	 /**
	   * 获取组件编号列表 (91)
	   * @param BUSI_NO
	   * @param BUSI_NAME
	   * @param compNo
	   */
	  @RequiresPermissions({"user"})
	  @ResponseBody
	  @RequestMapping(value = {"getCompNo"})
	  public void getCompNo(HttpServletRequest request, HttpServletResponse response){
		IDataset resDs = DatasetService.getInstace().getDataset();
		List<FsvrTPipCompDO> list = fileOmService.getCompNo(new FsvrTPipCompDO());
		int total = list.size();
		resDs = DatasetService.getInstace().getDataset(list,FsvrTPipCompDO.class);
		resDs.setTotalCount(total);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "列表查询交易成功！");
	  }
	  
	  /**
	   * 获取业务编号列表 (91)
	   * @param BUSI_NO
	   * @param BUSI_NAME
	   * @param compNo
	   * @return
	   */
	  @RequiresPermissions({"user"})
	  @ResponseBody
	  @RequestMapping(value = {"busiList"})
	  public List<Map<String, Object>> busiList(HttpServletRequest request, HttpServletResponse response){
	  IDataset reqDs = DatasetService.getInstace().getDataset(request);
	  String compNo = reqDs.getString("compNo");
	    FsvrTPipBusiDO reqBody = new FsvrTPipBusiDO();
	    reqBody.setCompNo(compNo);
	    return this.fileOmService.busiList(reqBody);
	  }
	  
	/**
	 * 统计查询
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getCountQry" })
	public void getCountQry(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String JRNL_STATS_TP = reqDs.getString("JRNL_STATS_TP");
		String FILE_TRAN_STAT = reqDs.getString("FILE_TRAN_STAT");
		String STR_DATE = reqDs.getString("STR_DATE");
		String END_DATE = reqDs.getString("END_DATE");
		String COMP_NO = reqDs.getString("COMP_NO");
		String BUSI_NO = reqDs.getString("BUSI_NO");
		
		TfmngMngJrnlStatsReqDTO reqBody = new TfmngMngJrnlStatsReqDTO();
		reqBody.setJRNL_STATS_TP(JRNL_STATS_TP);
		reqBody.setFILE_TRAN_STAT(FILE_TRAN_STAT);
		reqBody.setSTR_DATE(STR_DATE);
		reqBody.setEND_DATE(END_DATE);
		reqBody.setCOMP_NO(COMP_NO);
		reqBody.setBUSI_NO(BUSI_NO);

		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");

		IDataset resDs = fileOmService.getCountQry(reqBody, start, limit);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 流水查询
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getFlowQry" })
	public void getFlowQry(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String TRAN_SEQ = reqDs.getString("TRAN_SEQ");
		String FILE_TRAN_STAT = reqDs.getString("FILE_TRAN_STAT");
		String STR_DATE = reqDs.getString("STR_DATE");
		String END_DATE = reqDs.getString("END_DATE");
		String COMP_NO = reqDs.getString("COMP_NO");
		String BUSI_NO = reqDs.getString("BUSI_NO");
		
		TfmngMngJrnlQryReqDTO reqBody = new TfmngMngJrnlQryReqDTO();
		reqBody.setTRAN_SEQ(TRAN_SEQ);
		reqBody.setFILE_TRAN_STAT(FILE_TRAN_STAT);
		reqBody.setSTR_DATE(STR_DATE);
		reqBody.setEND_DATE(END_DATE);
		reqBody.setCOMP_NO(COMP_NO);
		reqBody.setBUSI_NO(BUSI_NO);

		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");

		IDataset resDs = fileOmService.getFlowQry(reqBody, start, limit);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 流水详细查询
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getFlowDetail" })
	public void getFlowDetail(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String TRAN_SEQ = reqDs.getString("TRAN_SEQ");
		
		TfmngMngJrnlDtlQryReqDTO reqBody = new TfmngMngJrnlDtlQryReqDTO();
		reqBody.setTRAN_SEQ(TRAN_SEQ);

		IDataset resDs = fileOmService.getFlowDetail(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	@ResponseBody
	@RequestMapping(value = "selectData")
	public void getSelectJson(@RequestParam(required=false) String type,@RequestParam(required=false) String isblank,@RequestParam(required=false) String blankText,@RequestParam(required=false) String blankValue, HttpServletResponse response) {
	   /*前台接收value、label、title、selected、disabled*/
	   logger.info("getSelectJson");
	   List<Dict> list = new ArrayList<Dict>();
//	   if(!DataUtil.isNullStr(isblank) && "true".equals(isblank)){
//	      //单选下拉框需要添加空选项
//	      Dict d = new Dict();
//	      d.setDictTp(type);
//	      d.setLabel(blankText);
//	      d.setValue(blankValue);
//	      list.add(d);
//	   }
	   List<Dict> tmpList = DictUtils.getDictList(type);
	   for (int i=0; i<tmpList.size(); i++) {
		   if ( "01".equals(tmpList.get(i).getValue()) ) {
			   tmpList.get(i).setLabel("全部");
			   list.add(tmpList.get(i));
		   } else {
			   list.add(tmpList.get(i));
		   }
	   }
	   Map<String, Object> m = new HashMap<String, Object>();
	   m.put("retCode", "0000");
	   m.put("list", list);
	   renderString(response, m);
	}
	@ResponseBody
	@RequestMapping(value = "selectData1")
	public void getSelectJson1(@RequestParam(required=false) String type,@RequestParam(required=false) String isblank,@RequestParam(required=false) String blankText,@RequestParam(required=false) String blankValue, HttpServletResponse response) {
	   /*前台接收value、label、title、selected、disabled*/
	   logger.info("getSelectJson1");
	   List<Dict> list = new ArrayList<Dict>();
//	   if(!DataUtil.isNullStr(isblank) && "true".equals(isblank)){
//	      //单选下拉框需要添加空选项
//	      Dict d = new Dict();
//	      d.setDictTp(type);
//	      d.setLabel(blankText);
//	      d.setValue(blankValue);
//	      list.add(d);
//	   }
	   List<Dict> tmpList = DictUtils.getDictList(type);
	   for (int i=0; i<tmpList.size(); i++) {
		   if ( "12".equals(tmpList.get(i).getValue()) ) {
			   
		   } else if ( "13".equals(tmpList.get(i).getValue()) ) {
			   
		   } else {
			   list.add(tmpList.get(i));
		   }
	   }
	   Map<String, Object> m = new HashMap<String, Object>();
	   m.put("retCode", "0000");
	   m.put("list", list);
	   renderString(response, m);
	}
}
