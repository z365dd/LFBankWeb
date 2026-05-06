/**
* 系统名称: SmartWeb平台
* 模块名称: comp.fsvr.tec拦截器模块
* 功能描述: 文件格式明细参数配置表控制类
* 类 名 称  : TfsvrFileDtlParaController.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200630<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.comp.fsvr.tec.web;

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
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.sys.modules.sys.entity.Office;
import com.adtec.sys.modules.sys.entity.Role;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.entity.Area;
import com.adtec.sys.modules.sys.service.OfficeService;
import com.adtec.sys.modules.sys.service.SystemService;
import com.adtec.sys.modules.sys.service.AreaService;
import com.adtec.comp.fsvr.tec.entity.TfsvrFileDtlParaDO;
import com.adtec.comp.fsvr.tec.service.TfsvrFileDtlParaService;

/**
 * 文件格式明细参数配置表Controller
 * @author zhengjt
 * @version 20200630
 */
@Controller
@RequestMapping(value = "${adminPath}/comp/fsvr/tec/tfsvrFileDtlPara")
public class TfsvrFileDtlParaController extends BaseController {

	@Autowired
	private TfsvrFileDtlParaService tfsvrFileDtlParaService;
	
	/*首页页面路径*/
	private String PATH = "starring/comp/fsvr/tec/tfsvrFileDtlPara";
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
	@RequestMapping(value ={"tfsvrFileDtlParaList"})
	public String tfsvrFileDtlParaList(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"List";
	}
	
	/**
	 * 进入新增页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tfsvrFileDtlParaAdd"})
	public String tfsvrFileDtlParaAdd(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"AddForm";
	}
	
	/**
	 * 进入修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tfsvrFileDtlParaUpdate"})
	public String tfsvrFileDtlParaUpdate(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"UpdateForm";
	}
	
	/**
	 * 进入详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tfsvrFileDtlParaDetail"})
	public String tfsvrFileDtlParaDetail(HttpServletRequest request, HttpServletResponse response) {
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
		TfsvrFileDtlParaDO obj = DatasetService.getInstace().getObject(reqDs, TfsvrFileDtlParaDO.class);
		if(tfsvrFileDtlParaService.insert(obj)){
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
		TfsvrFileDtlParaDO obj = DatasetService.getInstace().getObject(reqDs, TfsvrFileDtlParaDO.class);
		if(tfsvrFileDtlParaService.update(obj)){
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
		TfsvrFileDtlParaDO obj = DatasetService.getInstace().getObject(reqDs, TfsvrFileDtlParaDO.class);
		if(tfsvrFileDtlParaService.delete(obj)){
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
		TfsvrFileDtlParaDO obj = DatasetService.getInstace().getObject(reqDs, TfsvrFileDtlParaDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		List<TfsvrFileDtlParaDO> list = tfsvrFileDtlParaService.list(obj, start, limit);
		int total = tfsvrFileDtlParaService.getTotal(obj);
		resDs = DatasetService.getInstace().getDataset(list,TfsvrFileDtlParaDO.class);
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
		String fmtNo = reqDs.getString("fmtNo");
		if (DataUtil.isNullStr(fmtNo)) {
			throw new BaseException(SysErr.E_IN_NULL, "输入项[fmtNo]不能空！");
		}		
		String flg = reqDs.getString("fileFlg");
		if (DataUtil.isNullStr(flg)) {
			throw new BaseException(SysErr.E_IN_NULL, "输入项[flg]不能空！");
		}		
		Long ser = reqDs.getLong("ser");
		if (null==ser|| 0>=ser) {
			throw new BaseException(SysErr.E_IN_NULL, "输入项[ser]不能空！");
		}		

		TfsvrFileDtlParaDO obj = tfsvrFileDtlParaService.get(fmtNo, flg, ser);
		IDataset resDs = DatasetService.getInstace().getDataset(obj, TfsvrFileDtlParaDO.class);
		chgDict(resDs, false);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "明细查询交易成功！");
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
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"detail(" +"'"+ds.getString("fmtNo")+"', "+"'"+ds.getString("flg")+"', "+"'"+ds.getLong("ser")+"'" + ")\" >详情</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"update(" +"'"+ds.getString("fmtNo")+"', "+"'"+ds.getString("flg")+"', "+"'"+ds.getLong("ser")+"'" + ")\" >修改</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"del(" +"'"+ds.getString("fmtNo")+"', "+"'"+ds.getString("flg")+"', "+"'"+ds.getLong("ser")+"'" + ")\" >删除</a>");
				ds.updateString("action", action.toString());
			}
		}
	}
}