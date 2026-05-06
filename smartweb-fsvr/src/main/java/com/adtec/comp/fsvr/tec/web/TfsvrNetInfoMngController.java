/**
* 系统名称: SmartWeb平台
* 模块名称: comp.fsvr.tec拦截器模块
* 功能描述: 网络信息管理控制类
* 类 名 称  : TfsvrNetInfoMngController.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200624<br>
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

import com.adtec.comp.fsvr.tec.entity.TfsvrInSvrParaDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrNetInfoMngDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrSvrDeponNetParaDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrSvrDeponParaDO;
import com.adtec.comp.fsvr.tec.service.TfsvrNetInfoMngService;
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
import com.alibaba.fastjson.JSON;

import net.sf.json.JSONArray;

/**
 * 网络信息管理Controller
 * @author zhengjt
 * @version 20200624
 */
@Controller
@RequestMapping(value = "${adminPath}/comp/fsvr/tec/tfsvrNetInfoMng")
public class TfsvrNetInfoMngController extends BaseController {

	@Autowired
	private TfsvrNetInfoMngService tfsvrNetInfoMngService;
	
	/*首页页面路径*/
	private String PATH = "starring/comp/fsvr/tec/tfsvrNetInfoMng";
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
	@RequestMapping(value ={"tfsvrNetInfoMngList"})
	public String tfsvrNetInfoMngList(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"List";
	}
	
	/**
	 * 进入新增页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tfsvrNetInfoMngAdd"})
	public String tfsvrNetInfoMngAdd(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"AddForm";
	}
	
	/**
	 * 进入修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tfsvrNetInfoMngUpdate"})
	public String tfsvrNetInfoMngUpdate(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"UpdateForm";
	}
	
	/**
	 * 进入详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tfsvrNetInfoMngDetail"})
	public String tfsvrNetInfoMngDetail(HttpServletRequest request, HttpServletResponse response) {
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
		TfsvrNetInfoMngDO obj = getReqBody(reqDs);
		if(tfsvrNetInfoMngService.insert(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "新增交易成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "新增交易失败！");
		}
	}
	
	/**
	 * 获取请求数据
	 */
	public TfsvrNetInfoMngDO getReqBody(IDataset reqDs) {
		String netRegion = reqDs.getString("netRegion");
		String deponNetRegion = reqDs.getString("deponNetRegion");
		String membId = reqDs.getString("membId");
		String svrList = reqDs.getString("list");
		TfsvrNetInfoMngDO reqBody = new TfsvrNetInfoMngDO();
		reqBody.setNetRegion(netRegion);
		 System.out.println("membId:"+membId);
		JSONArray jsonArr = JSONArray.fromObject(svrList);
		@SuppressWarnings("unchecked")
		List<TfsvrInSvrParaDO> SVR_LIST = JSONArray.toList(jsonArr,
				TfsvrInSvrParaDO.class);
		for(int a = 0; a < SVR_LIST.size(); a++) {
			SVR_LIST.get(a).setMembId(membId);
		}
		
		if(!DataUtil.isNullStr(deponNetRegion)){
			String[] arr = deponNetRegion.split(";",-1);
			List <TfsvrSvrDeponParaDO> LIST  = new ArrayList();
			if(arr.length>0){
				for(String str : arr ){
					List<TfsvrSvrDeponNetParaDO> list = tfsvrNetInfoMngService.svrIdQry(str);
					if (!list.isEmpty()) {
						for(int a = 0; a < SVR_LIST.size(); a++) {
							for(int i = 0; i < list.size(); i++) {
								TfsvrSvrDeponParaDO l = new TfsvrSvrDeponParaDO();
								l.setFileSvrId(SVR_LIST.get(a).getFileSvrId());
								l.setDeponFileSvrId(list.get(i).getFileSvrId());
								LIST.add(l);		
							}
						}
					}
				}
			}
			reqBody.setDeponList(LIST);
		}
		reqBody.setSvrList(SVR_LIST);
		 System.out.println("membId_body:"+reqBody.getSvrList().get(0).getMembId());
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
		TfsvrNetInfoMngDO obj = getReqBody(reqDs);
		if(tfsvrNetInfoMngService.update(obj)){
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
		TfsvrNetInfoMngDO obj = DatasetService.getInstace().getObject(reqDs, TfsvrNetInfoMngDO.class);
		if(tfsvrNetInfoMngService.delete(obj)){
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
		TfsvrNetInfoMngDO obj = DatasetService.getInstace().getObject(reqDs, TfsvrNetInfoMngDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		List<TfsvrNetInfoMngDO> list = tfsvrNetInfoMngService.list(obj, start, limit);
		int total = tfsvrNetInfoMngService.getTotal(obj);
		resDs = DatasetService.getInstace().getDataset(list,TfsvrNetInfoMngDO.class);
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
		String netRegion = reqDs.getString("netRegion");
		if (DataUtil.isNullStr(netRegion)) {
			throw new BaseException(SysErr.E_IN_NULL, "输入项[netRegion]不能空！");
		}		

		TfsvrNetInfoMngDO obj = tfsvrNetInfoMngService.get(netRegion);
		String jsonStr =JSON.toJSONString(obj);
	    System.out.println("JSON字符串:"+jsonStr);
	    renderDatasetString(response, jsonStr, SysErr.E_SUCCESS, "获取明细成功");
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
		if(isAction){
			//新增操作描述列
			ds.addColumn("action", DatasetColumnType.DS_STRING);
		}
		ds.beforeFirst();
		while(ds.hasNext()){
			ds.next();
			
			if(isAction){
				/*添加相关操作按钮*/
				StringBuffer action = new StringBuffer();
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"detail(" +"'"+ds.getString("netRegion")+"'" + ")\" >详情</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"update(" +"'"+ds.getString("netRegion")+"'" + ")\" >修改</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"del(" +"'"+ds.getString("netRegion")+"'" + ")\" >删除</a>");
				ds.updateString("action", action.toString());
			}
		}
	}
}