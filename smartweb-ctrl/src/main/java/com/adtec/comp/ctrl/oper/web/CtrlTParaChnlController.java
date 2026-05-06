/**
* 系统名称: SmartWeb平台
* 模块名称: comp.ctrl.oper拦截器模块
* 功能描述: 渠道管理控制类
* 类 名 称  : CtrlTParaChnlController.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200310<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.comp.ctrl.oper.web;

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
import com.adtec.framework.exception.SysErr;
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
import com.adtec.comp.ctrl.oper.dao.CtrlTPipBusiChnlOpenDao;
import com.adtec.comp.ctrl.oper.entity.CtrlTParaChnlDO;
import com.adtec.comp.ctrl.oper.entity.CtrlTPipBusiChnlOpenDO;
import com.adtec.comp.ctrl.oper.entity.TParaTlrDO;
import com.adtec.comp.ctrl.oper.service.CtrlTParaChnlService;
import com.adtec.comp.ctrl.oper.util.CompCtrlUtil;

/**
 * 渠道管理Controller
 * @author zhengjt
 * @version 20200310
 */
@Controller
@RequestMapping(value = "${adminPath}/comp/ctrl/oper/ctrlTParaChnl")
public class CtrlTParaChnlController extends BaseController {

	@Autowired
	private CtrlTParaChnlService ctrlTParaChnlService;
	@Autowired
	private CtrlTPipBusiChnlOpenDao ctrlTPipBusiChnlOpenDao;
	/*首页页面路径*/
	private String PATH = "starring/comp/ctrl/oper/ctrlTParaChnl";
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
	@RequestMapping(value ={"ctrlTParaChnlList"})
	public String ctrlTParaChnlList(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"List";
	}
	
	/**
	 * 进入新增页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"ctrlTParaChnlAdd"})
	public String ctrlTParaChnlAdd(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"AddForm";
	}
	
	/**
	 * 进入修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"ctrlTParaChnlUpdate"})
	public String ctrlTParaChnlUpdate(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"UpdateForm";
	}
	
	/**
	 * 进入详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"ctrlTParaChnlDetail"})
	public String ctrlTParaChnlDetail(HttpServletRequest request, HttpServletResponse response) {
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
		
		String chnlNo = reqDs.getString("chnlNo");
		String chnlTp = reqDs.getString("chnlTp");
		CtrlTParaChnlDO ctrlTParaChnlDO=new CtrlTParaChnlDO();
		ctrlTParaChnlDO.setChnlNo(chnlNo);
		ctrlTParaChnlDO.setChnlTp(chnlTp);
		CtrlTParaChnlDO ctrlTParaChnlDO2 = ctrlTParaChnlService.get(ctrlTParaChnlDO);
		if(ctrlTParaChnlDO2!=null){
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "此渠道号已经存在！");
			return;
		}
		
		CtrlTParaChnlDO obj = DatasetService.getInstace().getObject(reqDs, CtrlTParaChnlDO.class);
		if(ctrlTParaChnlService.insert(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "新增交易成功！");
			// 调用参数缓存刷新
		    CompCtrlUtil.redisRefreshByTableName(CompCtrlUtil.TABLE_PARA_CHNL);
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
		CtrlTParaChnlDO obj = DatasetService.getInstace().getObject(reqDs, CtrlTParaChnlDO.class);
		if(ctrlTParaChnlService.update(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "更新交易成功！");
			// 调用参数缓存刷新
		    CompCtrlUtil.redisRefreshByTableName(CompCtrlUtil.TABLE_PARA_CHNL);
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
		String chnlNo = reqDs.getString("chnlNo");
		CtrlTPipBusiChnlOpenDO busiChnlOpenDO=new CtrlTPipBusiChnlOpenDO();
		busiChnlOpenDO.setChnlNo(chnlNo);
		List<CtrlTPipBusiChnlOpenDO> list = ctrlTPipBusiChnlOpenDao.list(busiChnlOpenDO);
		if(list.size()>0){
			String busiNo="";
			for (CtrlTPipBusiChnlOpenDO ctrlTPipBusiChnlOpenDO : list) {
				busiNo=busiNo+ctrlTPipBusiChnlOpenDO.getBusiNo()+",";
			}
			busiNo=busiNo.substring(0, busiNo.length()-1);
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "该渠道已被业务["+busiNo+"]使用！");
			return;
		}
		CtrlTParaChnlDO obj = DatasetService.getInstace().getObject(reqDs, CtrlTParaChnlDO.class);
		if(ctrlTParaChnlService.delete(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "删除交易成功！");
			// 调用参数缓存刷新
		    CompCtrlUtil.redisRefreshByTableName(CompCtrlUtil.TABLE_PARA_CHNL);
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
		CtrlTParaChnlDO obj = DatasetService.getInstace().getObject(reqDs, CtrlTParaChnlDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		List<CtrlTParaChnlDO> list = ctrlTParaChnlService.list(obj, start, limit);
		int total = ctrlTParaChnlService.getTotal(obj);
		resDs = DatasetService.getInstace().getDataset(list,CtrlTParaChnlDO.class);
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
		String chnlNo = reqDs.getString("chnlNo");
		String chnlTp = reqDs.getString("chnlTp");
		if (DataUtil.isNullStr(chnlNo)) {
			throw new BaseException(SysErr.E_IN_NULL, "输入项[chnlNo]不能空！");
		}		
		CtrlTParaChnlDO chnlDO=new CtrlTParaChnlDO();
		chnlDO.setChnlNo(chnlNo);
		chnlDO.setChnlTp(chnlTp);
		CtrlTParaChnlDO obj = ctrlTParaChnlService.get(chnlDO);
		IDataset resDs = DatasetService.getInstace().getDataset(obj, CtrlTParaChnlDO.class);
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
		//新增渠道类型中文描述列
		ds.addColumn("chnlTpStr", DatasetColumnType.DS_STRING);
		//新增渠道状态中文描述列
		ds.addColumn("chnlStatStr", DatasetColumnType.DS_STRING);
		if(isAction){
			//新增操作描述列
			ds.addColumn("action", DatasetColumnType.DS_STRING);
		}
		ds.beforeFirst();
		while(ds.hasNext()){
			ds.next();
			//更新渠道类型中文描述
			String fctrlChnlTp = ds.getString("chnlTp");
			ds.updateString("chnlTpStr", DictUtils.getDictLabels(fctrlChnlTp, "CHNL_TP", fctrlChnlTp)); 
			//更新渠道状态中文描述
			String fctrlChnlTphnlStat = ds.getString("chnlStat");
			ds.updateString("chnlStatStr", DictUtils.getDictLabels(fctrlChnlTphnlStat, "CHNL_STAT", fctrlChnlTphnlStat)); 
			
			if(isAction){
				/*添加相关操作按钮*/
				StringBuffer action = new StringBuffer();
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"detail(" +"'"+ds.getString("chnlNo")+"'" +",'"+ds.getString("chnlTp")+ "')\" >详情</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"update(" +"'"+ds.getString("chnlNo")+"'" +",'"+ds.getString("chnlTp")+ "')\" >修改</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"del(" +"'"+ds.getString("chnlNo")+"'" +",'"+ds.getString("chnlTp")+ "')\" >删除</a>");
				ds.updateString("action", action.toString());
			}
		}
	}
}