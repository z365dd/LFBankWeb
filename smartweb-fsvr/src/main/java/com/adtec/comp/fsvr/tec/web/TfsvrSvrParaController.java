/**
* 系统名称: SmartWeb平台
* 模块名称: comp.fsvr.tec拦截器模块
* 功能描述: 外部文件服务器控制类
* 类 名 称  : TfsvrSvrParaController.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200618<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.comp.fsvr.tec.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adtec.comp.fsvr.tec.entity.TfsvrFileChgParaDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrInSvrParaDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrSvrDeponNetParaDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrSvrDeponParaDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrSvrParaInfoDO;
import com.adtec.comp.fsvr.tec.service.TfsvrSvrParaService;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.DatasetColumnType;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.modules.sys.service.AreaService;
import com.adtec.sys.modules.sys.service.OfficeService;
import com.adtec.sys.modules.sys.service.SystemService;
import com.adtec.sys.modules.sys.utils.DictUtils;
import com.alibaba.fastjson.JSON;

/**
 * 外部文件服务器Controller
 * @author zhengjt
 * @version 20200618
 */
@Controller
@RequestMapping(value = "${adminPath}/comp/fsvr/tec/tfsvrSvrPara")
public class TfsvrSvrParaController extends BaseController {

	@Autowired
	private TfsvrSvrParaService tfsvrSvrParaService;
	
	/*首页页面路径*/
	private String PATH = "starring/comp/fsvr/tec/tfsvrSvrPara";
	/**
	 * 进入管理页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"manage", ""})
	public String index(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"Manage";
	}
	
	/**
	 * 列表页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tfsvrSvrParaList"})
	public String tfsvrSvrParaList(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"List";
	}
	
	/**
	 * 进入新增页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tfsvrSvrParaAdd"})
	public String tfsvrSvrParaAdd(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"AddForm";
	}
	
	/**
	 * 进入修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tfsvrSvrParaUpdate"})
	public String tfsvrSvrParaUpdate(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"UpdateForm";
	}
	
	/**
	 * 进入详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tfsvrSvrParaDetail"})
	public String tfsvrSvrParaDetail(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"DetailForm";
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
		List<TfsvrSvrDeponNetParaDO> list = tfsvrSvrParaService.deponNetQry(qryDo);
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
		List<TfsvrSvrDeponNetParaDO> list = tfsvrSvrParaService.deponQry(FILE_SVR_ID);
		int total = list.size();
		resDs = DatasetService.getInstace().getDataset(list,TfsvrSvrDeponNetParaDO.class);
		resDs.setTotalCount(total);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "列表查询交易成功！");
	}
	
	/**
	* 新增交易
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value="insert")
	public void insert(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		TfsvrSvrParaInfoDO obj = getReqBody(reqDs);
		if(tfsvrSvrParaService.insert(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "新增交易成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "新增交易失败！");
		}
	}
	
	/**
	* 修改交易
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value="update")
	public void update(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		TfsvrSvrParaInfoDO obj = getReqBody(reqDs);
		if(tfsvrSvrParaService.update(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "更新交易成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "更新交易失败！");
		}
	} 
	
	/**
	 * 获取请求数据
	 */
	public TfsvrSvrParaInfoDO getReqBody(IDataset reqDs) {
		String fileSvrId = reqDs.getString("fileSvrId");
		String svrDesc = reqDs.getString("svrDesc");
		//String fileSvrTp = reqDs.getString("fileSvrTp");
		String commProtGrpTp = reqDs.getString("commProtGrpTp");
		String openSvcFlg = reqDs.getString("openSvcFlg");
		String ip = reqDs.getString("ip");
		String port = reqDs.getString("port");
		String userNo = reqDs.getString("userNo");
		String pwd = reqDs.getString("pwd");
		String fileSvrStat = reqDs.getString("fileSvrStat");
		String downloadFilePath = reqDs.getString("downloadFilePath");
		String uploadFilePath = reqDs.getString("uploadFilePath");
		String contFlg = reqDs.getString("contFlg");
		//String membId = reqDs.getString("membId");
		String deponFileSvrId = reqDs.getString("deponFileSvrId");
		TfsvrSvrParaInfoDO reqBody = new TfsvrSvrParaInfoDO();
		reqBody.setFileSvrId(fileSvrId);
		reqBody.setSvrDesc(svrDesc);
		reqBody.setFileSvrTp("B2");
		reqBody.setCommProtGrpTp(commProtGrpTp);
		reqBody.setOpenSvcFlg(openSvcFlg);
		reqBody.setIp(ip);
		reqBody.setPort(port);
		reqBody.setUserNo(userNo);
		reqBody.setPwd(pwd);
		reqBody.setFileSvrStat(fileSvrStat);
		reqBody.setDownloadFilePath(downloadFilePath);
		reqBody.setUploadFilePath(uploadFilePath);
		reqBody.setContFlg(contFlg);
		if(!DataUtil.isNullStr(deponFileSvrId)){
			String[] arr = deponFileSvrId.split(";",-1);
			List <TfsvrSvrDeponParaDO> LIST  = new ArrayList();
			if(arr.length>0){
				for(String str : arr ){
					List<TfsvrSvrDeponNetParaDO> list = tfsvrSvrParaService.svrIdQry(str);
					if (!list.isEmpty()) {
						for(int i = 0; i < list.size(); i++) {
							TfsvrSvrDeponParaDO l = new TfsvrSvrDeponParaDO();
							l.setFileSvrId(fileSvrId);
							l.setDeponFileSvrId(list.get(i).getFileSvrId());
							LIST.add(l);		
						}
					}
				}
			}
			reqBody.setList(LIST);
		}
		return reqBody;
	}
	
	/**
	* 删除交易
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value="delete")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		TfsvrSvrParaInfoDO obj = DatasetService.getInstace().getObject(reqDs, TfsvrSvrParaInfoDO.class);
		if(tfsvrSvrParaService.delete(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "删除交易成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "删除交易失败！");
		}
	}
	
	/**
	* 下拉框列表查询
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value="getList")
	public void getTempList(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		List<TfsvrInSvrParaDO> list = tfsvrSvrParaService.getList(new TfsvrInSvrParaDO());
		int total = list.size();
		resDs = DatasetService.getInstace().getDataset(list,TfsvrInSvrParaDO.class);
		resDs.setTotalCount(total);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "列表查询交易成功！");
	} 
	
	/**
	* 列表查询
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value="list")
	public void list(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		TfsvrSvrParaInfoDO obj = DatasetService.getInstace().getObject(reqDs, TfsvrSvrParaInfoDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		List<TfsvrSvrParaInfoDO> list = tfsvrSvrParaService.list(obj, start, limit);
		int total = tfsvrSvrParaService.getTotal(obj);
		resDs = DatasetService.getInstace().getDataset(list,TfsvrSvrParaInfoDO.class);
		chgDict(resDs, true);
		resDs.setTotalCount(total);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "列表查询交易成功！");
	} 
	
	/**
	* 明细查询
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequestMapping(value="get")
	public void get(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String fileSvrId = reqDs.getString("fileSvrId");
		if (DataUtil.isNullStr(fileSvrId)) {
			throw new BaseException(SysErr.E_IN_NULL, "输入项[fileSvrId]不能空！");
		}		
		TfsvrSvrParaInfoDO obj = tfsvrSvrParaService.get(fileSvrId);
		String jsonStr =JSON.toJSONString(obj);
	    System.out.println("JSON字符串:"+jsonStr);
	    renderDatasetString(response, jsonStr, SysErr.E_SUCCESS, "获取明细成功");
	}
	
	/**
	* 开通状态改变
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequestMapping(value="statChange")
	public void statChange(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		TfsvrSvrParaInfoDO obj = DatasetService.getInstace().getObject(reqDs, TfsvrSvrParaInfoDO.class);
		if(tfsvrSvrParaService.statChange(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "状态修改成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "状态修改失败！");
		}
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
		SystemService systemService = SpringContextHolder.getBean("systemService");
		OfficeService officeService = SpringContextHolder.getBean("officeService");
		AreaService areaService = SpringContextHolder.getBean("areaService");
		//新增状态中文描述列
		ds.addColumn("statStr", DatasetColumnType.DS_STRING);
		if(isAction){
			//新增操作描述列
			ds.addColumn("action", DatasetColumnType.DS_STRING);
		}
		ds.beforeFirst();
		while(ds.hasNext()){
			ds.next();
			//更新状态中文描述
			String fileSvrStat = ds.getString("fileSvrStat");
			ds.updateString("statStr", DictUtils.getDictLabels(fileSvrStat, "FSVR_STAT", fileSvrStat));
			
			if(isAction){
				/*添加相关操作按钮*/
				StringBuffer action = new StringBuffer();
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"detail(" +"'"+ds.getString("fileSvrId")+"'" + ")\" >详细</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"update(" +"'"+ds.getString("fileSvrId")+"'" + ")\" >修改</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"del(" +"'"+ds.getString("fileSvrId")+"'" + ")\" >删除</a>");
				if(fileSvrStat.equals("01")) {
					action.append("	<a href=\"JavaScript:void(0);\" onClick=\"statChange(" +"'"+ds.getString("fileSvrId")+"'," +"'02'"+ ")\" >关闭</a>");
				} else {
					action.append("	<a href=\"JavaScript:void(0);\" onClick=\"statChange(" +"'"+ds.getString("fileSvrId")+"'," +"'01'"+ ")\" >开通</a>");
				}
				ds.updateString("action", action.toString());
			}
		}
	}
}