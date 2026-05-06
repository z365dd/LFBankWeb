package com.adtec.para.cfgcenter.web;

import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.para.center.service.ParaCacheService;
import com.adtec.para.cfgcenter.service.ParaCfgCenterService;
import com.adtec.para.common.constants.ParaConst;
import com.adtec.sys.common.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "${adminPath}/para/cfg")
public class ParaCfgCenterController extends BaseController{
	
	@Autowired
	private ParaCfgCenterService cfgCenterService;
	@Autowired
	private ParaCacheService centerService;
	
	private static final String STATUS_RUN = "0";
	private static final String STATUS_STOP = "1";
	
	/**
	 * 同步配置信息
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "infoRelease" })
	public void infoRelease(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String id = reqDs.getString("id");
//		int rs = centerService.getCenterByStatus(STATUS_RUN, id);
		int rs = 0;
		if(rs >= 1){
			IDataset dataset = DatasetService.getInstace().getDataset();
			setResponseDataset(request, response, dataset, SysErr.E_MESSAGE, "先停用缓存中心");
		}else {
			DatasetService.printDataset(reqDs);
			cfgCenterService.infoRelease(id);
			IDataset responseData = DatasetService.getInstace().getDataset();
			setResponseDataset(request, response, responseData, SysErr.E_SUCCESS, "同步配置信息成功");
		}
	}
	
	/**
	 * 同步存储规则
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "syncStgs" })
	public void syncStgs(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		String stgId = reqDs.getString("id");
		int rs = centerService.getCenterByStgId(stgId);
		if (rs < 1) {
			IDataset dataset = DatasetService.getInstace().getDataset();
			setResponseDataset(request, response, dataset, SysErr.E_MESSAGE, "该缓存中心暂未启用");
		}else {
			cfgCenterService.syncStgs(stgId, "run", ParaConst.SyncFlg.SYNC);
			IDataset responseData = DatasetService.getInstace().getDataset();
			setResponseDataset(request, response, responseData, SysErr.E_SUCCESS, "同步存储规则成功");
		}
	}
	
	
}
