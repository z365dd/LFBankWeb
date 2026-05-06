package com.adtec.comp.ctrl.oper.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adtec.comp.ctrl.dto.TParaChnlAddListDTO;
import com.adtec.comp.ctrl.dto.TParaChnlAddReqDTO;
import com.adtec.comp.ctrl.dto.TParaChnlDelListDTO;
import com.adtec.comp.ctrl.dto.TParaChnlDelReqDTO;
import com.adtec.comp.ctrl.dto.TParaChnlModListDTO;
import com.adtec.comp.ctrl.dto.TParaChnlModReqDTO;
import com.adtec.comp.ctrl.dto.TParaChnlQryReqDTO;
import com.adtec.comp.ctrl.oper.service.ChannelService;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.framework.exception.SysErr;

@Controller
@RequestMapping(value="${adminPath}/comp/ctrl/oper/channel")
public class ChannelController extends BaseController{
	
	@Autowired
	private ChannelService channelService;
	
	@RequiresPermissions("user")
	@RequestMapping(value = { "start","" })
	public String start(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/channel/tParaChnl";
	}
	
	/**
	 *返回查询页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"toList"})
	public String toChnlist(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/channel/tParaChnlList";
	}
	
	/**
	 *返回新增页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"toAdd"})
	public String toChnlAdd(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/channel/tParaChnlAdd";
	}
	
	/**
	 *返回修改页面
	 * @throws UnsupportedEncodingException 
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"toUpdate"})
	public String toChnlMod(HttpServletRequest request, HttpServletResponse response){
		/*String updateStr = URLDecoder.decode(str,"UTF-8");
		map.addAttribute("CHNL_NO", updateStr.split(",")[0]);
		map.addAttribute("CHNL_NAME", updateStr.split(",")[1]);*/
		return "starring/comp/ctrl/channel/tParaChnlMod";
	}
	
	/**
	 * 获取渠道
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"getChnl"})
	public void tParaChnlGet(HttpServletRequest request,HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String LEGA_NO = reqDs.getString("LEGA_NO");
		TParaChnlQryReqDTO req = new TParaChnlQryReqDTO();
		req.setLEGA_NO(LEGA_NO);
		IDataset resDs = channelService.getChnl(req);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 查询渠道
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"TParaChnlQry"})
	public void tParaChnlQry(HttpServletRequest request,HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String CHNL_NO = reqDs.getString("CHNL_NO");
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		TParaChnlQryReqDTO req = new TParaChnlQryReqDTO();
		req.setCHNL_NO(CHNL_NO);
		IDataset resDs = channelService.chnlQry(req, start, limit);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}
	
	/**
	 * 新增渠道
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"TParaChnlAdd"})
	public void tParaChnlAdd(HttpServletRequest request,HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String CHNL_NO = reqDs.getString("CHNL_NO");
		String CHNL_NAME = reqDs.getString("CHNL_NAME");
		String CHNL_TP = reqDs.getString("CHNL_TP");
		String SEQ_CRT_ID = reqDs.getString("SEQ_CRT_ID");
		TParaChnlAddReqDTO req = new TParaChnlAddReqDTO();
		TParaChnlAddListDTO reqList = new TParaChnlAddListDTO();
		List<TParaChnlAddListDTO> list = new ArrayList<TParaChnlAddListDTO>();
		reqList.setCHNL_NAME(CHNL_NAME);
		reqList.setCHNL_NO(CHNL_NO);
		reqList.setCHNL_TP(CHNL_TP);
		reqList.setSEQ_CRT_ID(SEQ_CRT_ID);
		list.add(reqList);
		req.setCHNL_LIST(list);
		req.setNUM(list.size());
		IDataset resDs = channelService.chnlAdd(req);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "添加成功");
	}
	
	/**
	 * 修改渠道
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"TParaChnlMod"})
	public void tParaChnlMod(HttpServletRequest request,HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String CHNL_NO = reqDs.getString("CHNL_NO");
		String CHNL_NAME = reqDs.getString("CHNL_NAME");
		String CHNL_TP = reqDs.getString("CHNL_TP");
		String SEQ_CRT_ID = reqDs.getString("SEQ_CRT_ID");
		TParaChnlModReqDTO req = new TParaChnlModReqDTO();
		TParaChnlModListDTO reqList = new TParaChnlModListDTO();
		List<TParaChnlModListDTO> list = new ArrayList<TParaChnlModListDTO>();
		reqList.setCHNL_NAME(CHNL_NAME);
		reqList.setCHNL_NO(CHNL_NO);
		reqList.setCHNL_TP(CHNL_TP);
		reqList.setSEQ_CRT_ID(SEQ_CRT_ID);
		list.add(reqList);
		req.setCHNL_LIST(list);
		req.setNUM(list.size());
		IDataset resDs = channelService.chnlMod(req);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "修改成功");
	}
	
	/**
	 * 删除渠道
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"TParaChnlDel"})
	public void tParaChnlDel(HttpServletRequest request,HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String CHNL_NO = reqDs.getString("CHNL_NO");
		TParaChnlDelReqDTO req = new TParaChnlDelReqDTO();
		TParaChnlDelListDTO reqList = new TParaChnlDelListDTO();
		List<TParaChnlDelListDTO> list = new ArrayList<TParaChnlDelListDTO>();
		reqList.setCHNL_NO(CHNL_NO);
		list.add(reqList);
		req.setCHNL_LIST(list);
		req.setNUM(list.size());
		
		IDataset resDs = channelService.chnlDel(req);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "修改成功");
	}
}
