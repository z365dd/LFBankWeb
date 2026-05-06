/**
* 系统名称: SmartWeb平台
* 模块名称: comp.fsvr.tec拦截器模块
* 功能描述: 文件格式总体参数配置表控制类
* 类 名 称  : TfsvrFileTotParaController.java
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
import com.adtec.starring_file.util.StringTool;
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
import com.adtec.comp.fsvr.tec.entity.TfsvrFileTotParaDO;
import com.adtec.comp.fsvr.tec.entity.TfsvrSvrDeponNetParaDO;
import com.adtec.comp.fsvr.tec.service.TfsvrFileTotParaService;

/**
 * 文件格式总体参数配置表Controller
 * @author zhengjt
 * @version 20200630
 */
@Controller
@RequestMapping(value = "${adminPath}/comp/fsvr/tec/tfsvrFileTotPara")
public class TfsvrFileTotParaController extends BaseController {

	@Autowired
	private TfsvrFileTotParaService tfsvrFileTotParaService;
	
	/*首页页面路径*/
	private String PATH = "starring/comp/fsvr/tec/tfsvrFileTotPara";
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
	@RequestMapping(value ={"tfsvrFileTotParaList"})
	public String tfsvrFileTotParaList(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"List";
	}
	
	/**
	 * 进入新增页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tfsvrFileTotParaAdd"})
	public String tfsvrFileTotParaAdd(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"AddForm";
	}
	
	/**
	 * 进入修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tfsvrFileTotParaUpdate"})
	public String tfsvrFileTotParaUpdate(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"UpdateForm";
	}
	
	/**
	 * 进入详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tfsvrFileTotParaDetail"})
	public String tfsvrFileTotParaDetail(HttpServletRequest request, HttpServletResponse response) {
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
		TfsvrFileTotParaDO obj = DatasetService.getInstace().getObject(reqDs, TfsvrFileTotParaDO.class);
		if(tfsvrFileTotParaService.insert(obj)){
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
		TfsvrFileTotParaDO obj = DatasetService.getInstace().getObject(reqDs, TfsvrFileTotParaDO.class);
		if(tfsvrFileTotParaService.update(obj)){
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
		TfsvrFileTotParaDO obj = DatasetService.getInstace().getObject(reqDs, TfsvrFileTotParaDO.class);
		if(tfsvrFileTotParaService.delete(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "删除交易成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "删除交易失败！");
		}
	}
	
	/**
	 * 模板格式号下拉框列表查询
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "selecList" })
	public void selecList(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		

		TfsvrFileTotParaDO qryDo = new TfsvrFileTotParaDO();
		String compNo = reqDs.getString("compNo");
		if ( !StringTool.isNullOrEmpty(compNo) ) {
			qryDo.setCompNo(compNo);
		}

		List<TfsvrFileTotParaDO> list = tfsvrFileTotParaService.selectList(qryDo);
		int total = list.size();
		resDs = DatasetService.getInstace().getDataset(list,TfsvrFileTotParaDO.class);
		resDs.setTotalCount(total);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "列表查询交易成功！");
	}
	
	/**
	 * 带衍生模板格式号下拉框列表查询
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "selectListTwo" })
	public void selectListTwo(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		TfsvrFileTotParaDO qryDo = new TfsvrFileTotParaDO();
		List<TfsvrFileTotParaDO> list = tfsvrFileTotParaService.selectListTwo(qryDo);
		int total = list.size();
		resDs = DatasetService.getInstace().getDataset(list,TfsvrFileTotParaDO.class);
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
		TfsvrFileTotParaDO obj = DatasetService.getInstace().getObject(reqDs, TfsvrFileTotParaDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		List<TfsvrFileTotParaDO> list = tfsvrFileTotParaService.list(obj, start, limit);
		int total = tfsvrFileTotParaService.getTotal(obj);
		resDs = DatasetService.getInstace().getDataset(list,TfsvrFileTotParaDO.class);
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

		TfsvrFileTotParaDO obj = tfsvrFileTotParaService.get(fmtNo);
		IDataset resDs = DatasetService.getInstace().getDataset(obj, TfsvrFileTotParaDO.class);
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
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"detail(" +"'"+ds.getString("fmtNo")+"'" + ")\" >详情</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"update(" +"'"+ds.getString("fmtNo")+"'" + ")\" >修改</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"del(" +"'"+ds.getString("fmtNo")+"'" + ")\" >删除</a>");
				ds.updateString("action", action.toString());
			}
		}
	}
}