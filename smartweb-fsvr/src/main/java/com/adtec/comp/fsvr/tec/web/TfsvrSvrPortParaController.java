/**
* 系统名称: SmartWeb平台
* 模块名称: comp.fsvr.tec拦截器模块
* 功能描述: 组件文件传输端口控制类
* 类 名 称  : TfsvrSvrPortParaController.java
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

import java.sql.SQLException;
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
import com.adtec.comp.fsvr.tec.dao.TfsvrSvrDeponParaDao;
import com.adtec.comp.fsvr.tec.entity.TfsvrSvrDeponNetParaDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrSvrDeponParaDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrSvrParaInfoDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrSvrPortParaDO;
import com.adtec.comp.fsvr.tec.service.TfsvrSvrPortParaService;

/**
 * 组件文件传输端口Controller
 * @author zhengjt
 * @version 20200622
 */
@Controller
@RequestMapping(value = "${adminPath}/comp/fsvr/tec/tfsvrSvrPortPara")
public class TfsvrSvrPortParaController extends BaseController {

	@Autowired
	private TfsvrSvrPortParaService tfsvrSvrPortParaService;
	
	/*首页页面路径*/
	private String PATH = "starring/comp/fsvr/tec/tfsvrSvrPortPara";
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
	@RequestMapping(value ={"tfsvrSvrPortParaList"})
	public String tfsvrSvrPortParaList(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"List";
	}
	
	/**
	 * 进入新增页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tfsvrSvrPortParaAdd"})
	public String tfsvrSvrPortParaAdd(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"AddForm";
	}
	
	/**
	 * 进入修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tfsvrSvrPortParaUpdate"})
	public String tfsvrSvrPortParaUpdate(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"UpdateForm";
	}
	
	/**
	 * 进入详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tfsvrSvrPortParaDetail"})
	public String tfsvrSvrPortParaDetail(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"DetailForm";
	}
	
	/**
	* 新增交易
	* @param request
	* @param response
	 * @throws SQLException 
	*/
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value="insert")
	public void insert(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		TfsvrSvrPortParaDO obj =  getReqBody(reqDs);
		
		if(tfsvrSvrPortParaService.insert(obj)){
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
		TfsvrSvrPortParaDO obj =  getReqBody(reqDs);
		if(tfsvrSvrPortParaService.update(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "更新交易成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "更新交易失败！");
		}
	} 
	
	/**
	 * 获取请求数据
	 */
	public TfsvrSvrPortParaDO getReqBody(IDataset reqDs) {
		String compNo = reqDs.getString("compNo");
		String compName = reqDs.getString("compName");
		String port = reqDs.getString("port");
		String netRegion = reqDs.getString("netRegion");
		TfsvrSvrPortParaDO reqBody = new TfsvrSvrPortParaDO();
		reqBody.setCompNo(compNo);
		reqBody.setCompName(compName);
		reqBody.setPort(port);
		if(!DataUtil.isNullStr(netRegion)){
			String[] arr = netRegion.split(";",-1);
			List <TfsvrSvrDeponNetParaDO> LIST  = new ArrayList();
			if(arr.length>0){
				for(String str : arr ){
					TfsvrSvrDeponNetParaDO l = new TfsvrSvrDeponNetParaDO();
					l.setCompNo(compNo);
					l.setNetRegion(str);
					l.setFileResTp("2");
					LIST.add(l);		
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
		TfsvrSvrPortParaDO obj = DatasetService.getInstace().getObject(reqDs, TfsvrSvrPortParaDO.class);
		if(tfsvrSvrPortParaService.delete(obj)){
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
		TfsvrSvrPortParaDO obj = DatasetService.getInstace().getObject(reqDs, TfsvrSvrPortParaDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		List<TfsvrSvrPortParaDO> list = tfsvrSvrPortParaService.list(obj, start, limit);
		int total = tfsvrSvrPortParaService.getTotal(obj);
		resDs = DatasetService.getInstace().getDataset(list,TfsvrSvrPortParaDO.class);
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
		String compNo = reqDs.getString("compNo");
		if (DataUtil.isNullStr(compNo)) {
			throw new BaseException(SysErr.E_IN_NULL, "输入项[compNo]不能空！");
		}		
		TfsvrSvrPortParaDO obj = tfsvrSvrPortParaService.get(compNo);
		String jsonStr =JSON.toJSONString(obj);
	    System.out.println("JSON字符串:"+jsonStr);
	    renderDatasetString(response, jsonStr, SysErr.E_SUCCESS, "明细查询交易成功");
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
		//新增组件号中文描述列
		ds.addColumn("compNoStr", DatasetColumnType.DS_STRING);
		if(isAction){
			//新增操作描述列
			ds.addColumn("action", DatasetColumnType.DS_STRING);
		}
		ds.beforeFirst();
		while(ds.hasNext()){
			ds.next();
			//更新组件号中文描述
			String compNo = ds.getString("compNo");
			ds.updateString("compNoStr", DictUtils.getDictLabels(compNo, "", compNo));
			
			if(isAction){
				/*添加相关操作按钮*/
				StringBuffer action = new StringBuffer();
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"update(" +"'"+ds.getString("compNo")+"'" + ")\" >修改</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"del(" +"'"+ds.getString("compNo")+"'" + ")\" >删除</a>");
				ds.updateString("action", action.toString());
			}
		}
	}
}