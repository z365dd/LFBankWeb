package com.adtec.para.cachetest.web;

import com.adtec.framework.common.util.FileUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.CommonDatasets;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.framework.interfaces.share.IDatasets;
import com.adtec.para.cachetest.service.ParaTestService;
import com.adtec.para.center.service.ParaCacheService;
import com.adtec.para.common.constants.ParaConst;
import com.adtec.para.common.util.ParaMapKey;
import com.adtec.para.rules.entity.ParaRulesStgColDO;
import com.adtec.sys.common.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "${adminPath}/para/test")
public class ParaTestController extends BaseController {
	
	@Autowired
	private ParaTestService cacheTestService;
	
	@Autowired
	private ParaCacheService centerService;

	/**
	 * 本地缓存数据管理界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"local/managePage"})
	public String localPage(HttpServletRequest request, HttpServletResponse response) {
		return "starring/para/cachetest/localManage";
	}
	
	/**
	 * 本地缓存数据列表页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"local/listPage"})
	public String localListPage(HttpServletRequest request, HttpServletResponse response) {
		return "starring/para/cachetest/localList";
	}

	/**
	 * 本地缓存数据列表页面-多表查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"local/tablesPage"})
	public String localTablesPage(HttpServletRequest request, HttpServletResponse response) {
		return "starring/para/cachetest/localTables";
	}
	
	/**
	 * 本地缓存数据-新增
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"local/addPage"})
	public String localAddPage(HttpServletRequest request, HttpServletResponse response) {
		return "starring/para/cachetest/localAddForm";
	}
	
	/**
	 * 本地缓存数据-修改
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"local/updatePage"})
	public String localUpdatePage(HttpServletRequest request, HttpServletResponse response) {
		return "starring/para/cachetest/localUpdateForm";
	}
	
	
	/**
	 * 本地缓存数据-详情
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"local/detailPage"})
	public String localDetailPage(HttpServletRequest request, HttpServletResponse response) {
		return "starring/para/cachetest/localDetailForm";
	}
	
	/**
	 * 本地缓存数据-详情
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"local/transferDataForm"})
	public String localTransferDataForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/para/cachetest/transferDataForm";
	}
	

	/**
	 * 获取本地缓存数据列表
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"local/list"})
	public void getLocalList(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		IDataset dataset = cacheTestService.getLocalList(reqDs);
		setResponseDataset(request, response, dataset, SysErr.E_SUCCESS, "查询成功！");
	}

	/**
	 * 获取本地缓存数据列表-多表查询
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"local/tables"})
	public void getLocalTables(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		IDataset dataset = cacheTestService.getLocalTables(reqDs);
		setResponseDataset(request, response, dataset, SysErr.E_SUCCESS, "查询成功！");
	}


	/**
	 * 获取登陆用户所属租户可用的存储规则
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"local/getRules"})
	public void getRules(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		IDataset dataset = cacheTestService.getRules();
		setResponseDataset(request, response, dataset, SysErr.E_SUCCESS, "查询成功！");
	}
	
	/**
	 * 获取登陆用户所属租户可用的存储规则
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"local/getRulesCol"})
	public void getRulesCol(HttpServletRequest request, HttpServletResponse response){
		IDatasets resDss = new CommonDatasets();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		List<ParaRulesStgColDO> colList = cacheTestService.getRulesCol(reqDs);
		IDataset dataset = DatasetService.getInstace().getDataset(colList, ParaRulesStgColDO.class);
		dataset.setDatasetName("stgColList");
		resDss.putDataset(dataset);
		setResponseDataset(request, response, resDss, SysErr.E_SUCCESS, "查询成功！");
	}
	
	/**
	 * 获取登陆用户所属租户可用的存储规则
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"local/setData"})
	public void setData(HttpServletRequest request, HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		HashMap<String, Object> retMap = cacheTestService.setData(reqDs);
		IDataset responseData = DatasetService.getInstace().getDataset();
		if(null!=retMap && !"SUCCESS".equals((String)retMap.get("RetCode"))){
			setResponseDataset(request, response, responseData, SysErr.E_MESSAGE, retMap.get("RetCode")+":"+retMap.get("Msg"));
		}else {
			setResponseDataset(request, response, responseData, SysErr.E_SUCCESS, "设置数据成功");
		}
	}
	
	/**
	 * 本地缓存数据-删除
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"local/delete"})
	public void localDelete(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		HashMap<String, Object> retMap = cacheTestService.delData(reqDs);
		IDataset responseData = DatasetService.getInstace().getDataset();
		if(null!=retMap && !"SUCCESS".equals((String)retMap.get("RetCode"))){
			setResponseDataset(request, response, responseData, SysErr.E_MESSAGE, retMap.get("RetCode")+":"+retMap.get("Msg"));
		}else {
			setResponseDataset(request, response, responseData, SysErr.E_SUCCESS, "删除数据成功");
		}
	}
	
	/**
	 * 本地缓存数据-详情
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"local/getData"})
	public void getlocalData(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
		IDataset responseData = cacheTestService.getlocalData(reqDs);
		setResponseDataset(request, response, responseData, SysErr.E_SUCCESS, "查询数据成功");
	}
	
	/**
	 * 本地缓存数据-详情-更新页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"local/getDataForUpt"})
	public void getDataForUpt(HttpServletRequest request, HttpServletResponse response) {
		IDatasets resDss = new CommonDatasets();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		DatasetService.printDataset(reqDs);
//		IDataset responseData = cacheTestService.getDataForUpt(reqDs);
		List<ParaRulesStgColDO> list = cacheTestService.getDataForUpt(reqDs);
		IDataset dataset = DatasetService.getInstace().getDataset(list, ParaRulesStgColDO.class);
		dataset.setDatasetName("stgColList");
		resDss.putDataset(dataset);
		setResponseDataset(request, response, resDss, SysErr.E_SUCCESS, "查询数据成功");
	}
	

	/**
	 * 导出模板
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = {"local/template"})
	public void templete(HttpServletRequest request, HttpServletResponse response) {
		String fileName = "param.properties";
		StringBuilder sb = new StringBuilder();
		sb.append("#--------------基础参数---------------");
		sb.append("#----------------提示-----------------#");
		sb.append("#存储规则英文名=唯一索引英文名1,唯一索引英文名2");
		sb.append("#各元素均不能存在空格");
		sb.append("#----------------提示-----------------#");
		sb.append("\r\n");
		sb.append("#日切参数表参数");
		sb.append("\r\n");
		sb.append("ParaDay=PLAT_NO");
		sb.append("\r\n");
		sb.append("#统一行名行号参数");
		sb.append("\r\n");
		sb.append("ParaUnionBank=BANK,ROUTE_NO");
		sb.append("\r\n");
		String path = ParamUtil.getUploadFile() + "/userfiles/cache/rules/template/"+fileName;
		FileUtil.writeToFile(path, sb.toString(), false);
		FileUtil.DownLoadFileByUri(path, fileName, response);
	}

	/**
	 * 导入
	 * @param request
	 * @param response
	 * @param mfile
	 * @param redirectAttributes
	 */
	@ResponseBody
	@RequestMapping(value = "local/import", method = RequestMethod.POST)
	public void importFile (HttpServletRequest request, HttpServletResponse response, MultipartFile mfile, RedirectAttributes redirectAttributes) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String centerId = reqDs.getString("cacheCentrId");
		int centerCnt = centerService.getCenterByStatus(ParaConst.RunStat.RUN, centerId);
		if (centerCnt >= 1) {
			setResponseDataset(request, response, responseData, SysErr.E_MESSAGE, "先停用缓存中心");
		}else {
			HashMap<String, Object> retMap = cacheTestService.importRules(reqDs, mfile);
			List<Map<String, String>> retList = (List<Map<String, String>>) retMap.get(ParaMapKey.DATA);
			StringBuffer result = new StringBuffer();
			for (Map<String, String> map : retList) {
				for (String key : map.keySet()) {
					result.append("{\"paramType\":\""+key+"\",\"msg\":\""+map.get(key)+"\"},");
				}
			}
			String ret = "";
			if (result.length()>0) {
				ret = "["+result.substring(0, result.length()-1)+"]";
			}else {
				ret = "[]";
			}
			responseData = DatasetService.getInstace().getDataset(result.toString(), String.class);
			responseData.setDatasetName(ret);
			setResponseDataset(request, response, responseData, SysErr.E_SUCCESS, "数据初始化成功");
		}
	}
}
