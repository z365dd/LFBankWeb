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

import com.adtec.comp.ctrl.dto.CtrlRedisRefreshReqDTO;
import com.adtec.comp.ctrl.oper.entity.CtrlTPipBusiDO;
import com.adtec.comp.ctrl.oper.entity.FCtrlParaLoadDO;
import com.adtec.comp.ctrl.oper.service.CtrlRedisRefreService;
import com.adtec.framework.impl.share.dataset.DatasetService;
import org.springframework.util.StringUtils;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.framework.exception.SysErr;

@Controller
@RequestMapping(value="${adminPath}/comp/ctrl/oper/ctrlRedisRefre")
public class CtrlRedisRefreController extends BaseController{
	@Autowired
	private CtrlRedisRefreService redisRefreService;


	/**
	 * 返回参数刷新页面
	 * @param request
	 * @param response
	 * @return
	 */ 
	@RequiresPermissions("user")
	@RequestMapping(value = { "ctrlRedisRefreForm" })
	public String ctrlRedisRefreForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/comp/ctrl/oper/ctrlRedisRefreForm";
	};
	
	/**
	 * 表名称下拉框
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getParaName" }) 
	public void getParaName(HttpServletRequest request, HttpServletResponse response){
		List<FCtrlParaLoadDO> list = redisRefreService.getParaLoadList(new FCtrlParaLoadDO());
		List<Map<String, Object>> maps = new ArrayList();
	/*	Map<String, Object> emptyMap = new HashMap<>(2);
		emptyMap.put("label", "请选择");
		emptyMap.put("value", "");
		maps.add(emptyMap);*/
		for(FCtrlParaLoadDO DO : list){
			Map<String, Object> map = new HashMap<>(2);
			map.put("label", DO.getChName()+"-"+DO.getEngName());
			map.put("value", DO.getEngName());
			maps.add(map);
		}
		Map<String, Object> m = new HashMap<>();
		m.put("retCode", "0000");
		m.put("list", maps);
		renderString(response, m);
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
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String OPER_TP= reqDs.getString("OPER_TP");
		String PARA_NAME= reqDs.getString("PARA_NAME");
		CtrlRedisRefreshReqDTO sysParaUpdateReqDTO = new CtrlRedisRefreshReqDTO();
		sysParaUpdateReqDTO.setOPER_TP(OPER_TP);
		if (OPER_TP.equals("0")) {
			 resDs = redisRefreService.sysParaUpdate(sysParaUpdateReqDTO);
		} else {
			if(!StringUtils.isEmpty(PARA_NAME)){
				String[] paraArr = PARA_NAME.split(";",-1);
				for(int a =0;a<paraArr.length;a++){
					sysParaUpdateReqDTO.setPARA_NAME(paraArr[a]);
					redisRefreService.sysParaUpdate(sysParaUpdateReqDTO);
				}
			}
		}
		setResponseDataset(request, response,resDs, SysErr.E_SUCCESS,"交易成功");		
	}
	
	/**
	 *  业务下拉框
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getBusiNo" })
	public void getBusiNo(HttpServletRequest request, HttpServletResponse response){
		List<CtrlTPipBusiDO> list = redisRefreService.getBusiNo(new CtrlTPipBusiDO()); 
		List<Map<String, Object>> maps = new ArrayList();
		for(CtrlTPipBusiDO DO : list){
			Map<String, Object> map = new HashMap<>(2);
			map.put("label", DO.getBusiName());
			map.put("value", DO.getBusiNo());
			maps.add(map);
		} 
		Map<String, Object> m = new HashMap<>();
		m.put("retCode", "0000");
		m.put("list", maps);
		renderString(response, m);
	}
}
