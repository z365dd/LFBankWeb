package com.adtec.para.center.web;


import com.adtec.framework.common.util.FileUtil;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.framework.interfaces.share.IDatasets;
import com.adtec.para.center.entity.ParaCacheDO;
import com.adtec.para.center.service.ParaCacheService;
import com.adtec.sys.common.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "${adminPath}/para/center")
public class ParaCacheController extends BaseController {
	
	@Autowired
	private ParaCacheService centerService;

	/**
	 * 缓存中心管理
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"managePage"})
	public String managePage(HttpServletRequest request, HttpServletResponse response) {
		return "starring/para/center/centerManage";
	}
	
	/**
	 * 缓存中心列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "listPage" })
	public String listPage(HttpServletRequest request, HttpServletResponse response) {
		return "starring/para/center/centerList";
	}
	
	/**
	 * 缓存中心新增
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "addPage" })
	public String addPage(HttpServletRequest request, HttpServletResponse response) {
		return "starring/para/center/centerAddForm";
	}
	
	/**
	 * 缓存中心详情
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "detailPage" })
	public String detailPage(HttpServletRequest request, HttpServletResponse response) {
		return "starring/para/center/centerDetailForm";
	}
	
	/**
	 * 缓存中心修改
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "updatePage" })
	public String updatePage(HttpServletRequest request, HttpServletResponse response) {
		return "starring/para/center/centerUpdateForm";
	}
	
	/**
	 * 缓存中心列表查询
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "list" })
	public void list(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		IDataset dataset = centerService.listByPage(reqDs);
		setResponseDataset(request, response, dataset, SysErr.E_SUCCESS, "查询成功！");
		
	}
	
	/**
	 * 插入新缓存中心信息
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "insert" })
	public void insert(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		ParaCacheDO centerDO = DatasetService.getInstace().getObject(reqDs, ParaCacheDO.class);
		String engName = centerDO.getEngName();
		int rs = centerService.getCenterByEngName(engName);
		if (rs >= 1) {
			IDataset dataset = DatasetService.getInstace().getDataset();
			setResponseDataset(request, response, dataset, SysErr.E_MESSAGE, "缓存中心["+engName+"]已存在！");
		}else {
			centerService.insert(reqDs);
			IDataset responseData = DatasetService.getInstace().getDataset();
			setResponseDataset(request, response, responseData, SysErr.E_SUCCESS, "新增成功");
		}
	}
	
	/**
	 * 缓存中心详情
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "get" })
	public void detail(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		String id = reqDs.getString("id");
		IDatasets responseData = centerService.get(id);
		setResponseDataset(request, response, responseData, SysErr.E_SUCCESS, "查询成功");
	}
	
	/**
	 * 缓存中心更新
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "update" })
	public void update(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		centerService.update(reqDs);
		IDataset responseData = DatasetService.getInstace().getDataset();
		setResponseDataset(request, response, responseData, SysErr.E_SUCCESS, "更新成功");
	}
	
	/**
	 * 导出缓存中心配置文件
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "export" })
	public void export(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		String zipFilePath = centerService.genCfgFile(reqDs);
		try {
			String fileName = zipFilePath.substring(zipFilePath.lastIndexOf("/")+1);
			String downLoadFileName = fileName;
			fileName = new String(fileName.getBytes("iso8859-1"),"UTF-8");
	    	// 下载文件
	    	FileUtil.DownLoadFileByUri(zipFilePath, downLoadFileName, response);
		} catch (Exception e) {
			System.out.println("操作失败");
		}
	}
	
	/**
	 * 缓存中心停用
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "stop" })
	public void stop(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		centerService.stop(reqDs);
		IDataset responseData = DatasetService.getInstace().getDataset();
		setResponseDataset(request, response, responseData, SysErr.E_SUCCESS, "更新成功");
	}
	/**
	 * 缓存中心删除
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "del" })
	public void del(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		centerService.del(reqDs);
		IDataset responseData = DatasetService.getInstace().getDataset();
		setResponseDataset(request, response, responseData, SysErr.E_SUCCESS, "更新成功");
	}
	
	/**
	 * 获取缓存中心总数
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"getCenterSum"})
	public void getCenterSum(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		ParaCacheDO centerDO = new ParaCacheDO();
		centerDO.setRunStat("01");
		int rs = centerService.getCenterSum(centerDO);
		IDataset responseData = DatasetService.getInstace().getDataset(rs, Integer.class);
		responseData.addColumn("count");
		responseData.updateString("count", String.valueOf(rs));
		setResponseDataset(request, response, responseData, SysErr.E_SUCCESS, "更新成功");
	}
}
