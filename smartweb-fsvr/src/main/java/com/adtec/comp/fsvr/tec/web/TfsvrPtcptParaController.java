/**
* 系统名称: SmartWeb平台
* 模块名称: comp.fsvr.tec拦截器模块
* 功能描述: 调用方信息控制类
* 类 名 称  : TfsvrPtcptParaController.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200622<br>
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
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.DatasetColumnType;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.sys.common.web.BaseController;

import com.adtec.sys.modules.sys.utils.DictUtils;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.alibaba.fastjson.JSON;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.sys.modules.sys.entity.Office;
import com.adtec.sys.modules.sys.entity.Role;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.entity.Area;
import com.adtec.sys.modules.sys.service.OfficeService;
import com.adtec.sys.modules.sys.service.SystemService;
import com.adtec.sys.modules.sys.service.AreaService;
import com.adtec.comp.fsvr.tec.entity.TfsvrPtcptParaDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrSvrDeponNetParaDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrSvrDeponParaDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrSvrParaInfoDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrSvrPortParaDO;
import com.adtec.comp.fsvr.tec.service.TfsvrPtcptParaService;

/**
 * 调用方信息Controller
 * @author zhengjt
 * @version 20200622
 */
@Controller
@RequestMapping(value = "${adminPath}/comp/fsvr/tec/tfsvrPtcptPara")
public class TfsvrPtcptParaController extends BaseController {

	@Autowired
	private TfsvrPtcptParaService tfsvrPtcptParaService;
	
	/*首页页面路径*/
	private String PATH = "starring/comp/fsvr/tec/tfsvrPtcptPara";
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
	@RequestMapping(value ={"tfsvrPtcptParaList"})
	public String tfsvrPtcptParaList(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"List";
	}
	
	/**
	 * 进入新增页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tfsvrPtcptParaAdd"})
	public String tfsvrPtcptParaAdd(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"AddForm";
	}
	
	/**
	 * 进入修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tfsvrPtcptParaUpdate"})
	public String tfsvrPtcptParaUpdate(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"UpdateForm";
	}
	
	/**
	 * 进入详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tfsvrPtcptParaDetail"})
	public String tfsvrPtcptParaDetail(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"DetailForm";
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
		TfsvrPtcptParaDO obj = getReqBody(reqDs);
		if(tfsvrPtcptParaService.insert(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "新增交易成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "新增交易失败！");
		}
	}
	
	/**
	 * 获取请求数据
	 */
	public TfsvrPtcptParaDO getReqBody(IDataset reqDs) {
		String callerId = reqDs.getString("callerId");
		String callerDesc = reqDs.getString("callerDesc");
		String stat = reqDs.getString("stat");
		String callMeth = reqDs.getString("callMeth");
		TfsvrPtcptParaDO reqBody = new TfsvrPtcptParaDO();
		reqBody.setCallerId(callerId);
		reqBody.setCallerDesc(callerDesc);
		reqBody.setStat(stat);
		reqBody.setCallMeth(callMeth);
		if(callMeth.equals("01")) {
			String userNo = reqDs.getString("userNo");
			String pwd = reqDs.getString("pwd");
			String encrpFlg = reqDs.getString("encrpFlg");
			String subctrctSendFileSize = reqDs.getString("subctrvtSendFileSize");
			String subctrctRecvFileSize = reqDs.getString("subctrctRecvFileSize");
			String attestFlg = reqDs.getString("attestFlg");
			String reduceFlg = reqDs.getString("reduceFlg");
			String resumeFlg = reqDs.getString("resumeFlg");
			String speedlimFlg = reqDs.getString("speedlimFlg");
			String sndSpeedlimSize = reqDs.getString("sndSpeedlimSize");
			String recvSpeedlimSize = reqDs.getString("recvSpeedlimSize");
			String uploadFilePath = reqDs.getString("uploadFilePath");
			String downloadFilePath = reqDs.getString("downloadFilePath");
			reqBody.setUserNo(userNo);
			reqBody.setPwd(pwd);
			reqBody.setEncrpFlg(encrpFlg);
			if(!DataUtil.isNullStr(subctrctRecvFileSize)){
				reqBody.setSubctrctRecvFileSize(Long.parseLong(subctrctRecvFileSize));
			}
			if(!DataUtil.isNullStr(subctrctSendFileSize)){
				reqBody.setSubctrctSendFileSize(Long.parseLong(subctrctSendFileSize));
			}
			reqBody.setAttestFlg(attestFlg);
			reqBody.setReduceFlg(reduceFlg);
			reqBody.setResumeFlg(resumeFlg);
			reqBody.setSpeedlimFlg(speedlimFlg);
			if(!DataUtil.isNullStr(sndSpeedlimSize)){
				reqBody.setSndSpeedlimSize(Long.parseLong(sndSpeedlimSize));
			}
			if(!DataUtil.isNullStr(recvSpeedlimSize)){
				reqBody.setRecvSpeedlimSize(Long.parseLong(recvSpeedlimSize));
			}
			reqBody.setUploadFilePath(uploadFilePath);
			reqBody.setDownloadFilePath(downloadFilePath);
		} else {
			String port = reqDs.getString("port");
			reqBody.setPort(port);
			String netRegion = reqDs.getString("netRegion");
			if(!DataUtil.isNullStr(netRegion)){
				String[] arr = netRegion.split(";",-1);
				List <TfsvrSvrDeponNetParaDO> LIST  = new ArrayList();
				if(arr.length>0){
					for(String str : arr ){
						TfsvrSvrDeponNetParaDO l = new TfsvrSvrDeponNetParaDO();
						l.setCompNo(callerId);
						l.setFileResTp("2");
						l.setNetRegion(str);
						LIST.add(l);		
					}
				}
				reqBody.setDeponList(LIST);
			}
		}
		return reqBody;
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
		TfsvrPtcptParaDO obj = getReqBody(reqDs);
		if(tfsvrPtcptParaService.update(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "更新交易成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "更新交易失败！");
		}
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
		TfsvrPtcptParaDO obj = DatasetService.getInstace().getObject(reqDs, TfsvrPtcptParaDO.class);
		if(tfsvrPtcptParaService.delete(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "删除交易成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "删除交易失败！");
		}
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
		TfsvrPtcptParaDO obj = DatasetService.getInstace().getObject(reqDs, TfsvrPtcptParaDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		List<TfsvrPtcptParaDO> list = tfsvrPtcptParaService.list(obj, start, limit);
		int total = tfsvrPtcptParaService.getTotal(obj);
		resDs = DatasetService.getInstace().getDataset(list,TfsvrPtcptParaDO.class);
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
		String callerId = reqDs.getString("callerId");
		if (DataUtil.isNullStr(callerId)) {
			throw new BaseException(SysErr.E_IN_NULL, "输入项[callerId]不能空！");
		}		

		TfsvrPtcptParaDO obj = tfsvrPtcptParaService.get(callerId);
		String jsonStr =JSON.toJSONString(obj);
	    System.out.println("JSON字符串:"+jsonStr);
	    renderDatasetString(response, jsonStr, SysErr.E_SUCCESS, "明细查询交易成功！");
	}
	
	/**
	* 状态改变
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequestMapping(value="statChange")
	public void statChange(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		TfsvrPtcptParaDO obj = DatasetService.getInstace().getObject(reqDs, TfsvrPtcptParaDO.class);
		if(tfsvrPtcptParaService.statChange(obj)){
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
		//新增是否加密中文描述列
		ds.addColumn("encrpFlgStr", DatasetColumnType.DS_STRING);
		//新增是否加签中文描述列
		ds.addColumn("attestFlgStr", DatasetColumnType.DS_STRING);
		if(isAction){
			//新增操作描述列
			ds.addColumn("action", DatasetColumnType.DS_STRING);
		}
		ds.beforeFirst();
		while(ds.hasNext()){
			ds.next();
			//更新状态中文描述
			String stat = ds.getString("stat");
			String statStr = ds.getString("stat");
			if (stat.equals("00")) {
				statStr = "启用";
			} else if (stat.equals("01")) {
				statStr = "停止";
			}
			ds.updateString("statStr", statStr);
			//更新是否加密中文描述
			String encrpFlg = ds.getString("encrpFlg");
			if (encrpFlg.equals("Y")) {
				encrpFlg = "是";
			} else if (encrpFlg.equals("N")) {
				encrpFlg = "否";
			}
			ds.updateString("encrpFlgStr", encrpFlg);
			//更新是否加签中文描述
			String attestFlg = ds.getString("attestFlg");
			if (attestFlg.equals("Y")) {
				attestFlg = "是";
			} else if (attestFlg.equals("N")) {
				attestFlg = "否";
			}
			ds.updateString("attestFlgStr", attestFlg);
			
			if(isAction){
				/*添加相关操作按钮*/
				StringBuffer action = new StringBuffer();
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"detail(" +"'"+ds.getString("callerId")+"'" + ")\" >详情</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"update(" +"'"+ds.getString("callerId")+"'" + ")\" >修改</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"del(" +"'"+ds.getString("callerId")+"'" + ")\" >删除</a>");
				if(stat.equals("00")) {
					action.append("	<a href=\"JavaScript:void(0);\" onClick=\"statChange(" +"'"+ds.getString("callerId")+"'," +"'01'"+ ")\" >停止</a>");
				} else {
					action.append("	<a href=\"JavaScript:void(0);\" onClick=\"statChange(" +"'"+ds.getString("callerId")+"'," +"'00'"+ ")\" >启用</a>");
				}
				ds.updateString("action", action.toString());
			}
		}
	}
}