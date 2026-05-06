/**
* 系统名称: SmartWeb平台
* 模块名称: comp.ctrl.oper拦截器模块
* 功能描述: 关联系统管理控制类
* 类 名 称  : CtrlTParaRelatSysController.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.adtec.comp.ctrl.oper.dao.CtrlTParaChnlDao;
import com.adtec.comp.ctrl.oper.dao.CtrlTPipBusiDao;
import com.adtec.comp.ctrl.oper.entity.CtrlTParaChnlDO;
import com.adtec.comp.ctrl.oper.entity.CtrlTParaRelatSysDO;
import com.adtec.comp.ctrl.oper.entity.CtrlTPipBusiDO;
import com.adtec.comp.ctrl.oper.entity.TParaTlrDO;
import com.adtec.comp.ctrl.oper.service.CtrlTParaRelatSysService;
import com.adtec.comp.ctrl.oper.util.CompCtrlUtil;

/**
 * 关联系统管理Controller
 * @author zhengjt
 * @version 20200310
 */
@Controller
@RequestMapping(value = "${adminPath}/comp/ctrl/oper/ctrlTParaRelatSys")
public class CtrlTParaRelatSysController extends BaseController {

	@Autowired
	private CtrlTParaRelatSysService ctrlTParaRelatSysService;
	@Autowired
	private CtrlTParaChnlDao ctrlTParaChnlDao;
	@Autowired
	private CtrlTPipBusiDao ctrlTPipBusiDao;
	/*首页页面路径*/
	private String PATH = "starring/comp/ctrl/oper/ctrlTParaRelatSys";
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
	@RequestMapping(value ={"ctrlTParaRelatSysList"})
	public String ctrlTParaRelatSysList(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"List";
	}
	
	/**
	 * 进入新增页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"ctrlTParaRelatSysAdd"})
	public String ctrlTParaRelatSysAdd(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"AddForm";
	}
	
	/**
	 * 进入修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"ctrlTParaRelatSysUpdate"})
	public String ctrlTParaRelatSysUpdate(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"UpdateForm";
	}
	
	/**
	 * 进入详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"ctrlTParaRelatSysDetail"})
	public String ctrlTParaRelatSysDetail(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"DetailForm";
	}
	
	/**
	 *  关联系统下拉框
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "getRelatSys" })
	public void getRelatSys(HttpServletRequest request, HttpServletResponse response){
	List<CtrlTParaRelatSysDO> list = ctrlTParaRelatSysService.list(new CtrlTParaRelatSysDO());
	  List<Map<String, Object>> maps = new ArrayList();
	  Map<String, Object> emptyMap = new HashMap<>(2);
	        emptyMap.put("label", "--请选择--");
	        emptyMap.put("value", "");
	        maps.add(emptyMap);
	        for(CtrlTParaRelatSysDO DO : list){
	         Map<String, Object> map = new HashMap<>(2);
	         map.put("label", DO.getRelatSys()+"-"+DO.getSysName());
	         map.put("value", DO.getRelatSys());
	         maps.add(map);
	        }
	        Map<String, Object> m = new HashMap<>();
	        m.put("retCode", "0000");
	        m.put("list", maps);
	        renderString(response, m);
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
		
		String relatSys = reqDs.getString("relatSys");
		CtrlTParaRelatSysDO ctrlTParaRelatSysDO=new CtrlTParaRelatSysDO();
		ctrlTParaRelatSysDO.setRelatSys(relatSys);
		CtrlTParaRelatSysDO ctrlTParaRelatSysDO2 = ctrlTParaRelatSysService.get(ctrlTParaRelatSysDO);
		if(ctrlTParaRelatSysDO2!=null){
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "此关联系统已经存在！");
			return;
		}
		
		CtrlTParaRelatSysDO obj = DatasetService.getInstace().getObject(reqDs, CtrlTParaRelatSysDO.class);
		if(ctrlTParaRelatSysService.insert(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "新增交易成功！");
			// 调用参数缓存刷新
		    CompCtrlUtil.redisRefreshByTableName(CompCtrlUtil.TABLE_PARA_RELAT_SYS);
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
		CtrlTParaRelatSysDO obj = DatasetService.getInstace().getObject(reqDs, CtrlTParaRelatSysDO.class);
		if(ctrlTParaRelatSysService.update(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "更新交易成功！");
			// 调用参数缓存刷新
		    CompCtrlUtil.redisRefreshByTableName(CompCtrlUtil.TABLE_PARA_RELAT_SYS);
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
		
		String relatSys = reqDs.getString("relatSys");
		CtrlTParaChnlDO ctrlTParaChnlDO=new CtrlTParaChnlDO();
		ctrlTParaChnlDO.setRelatSys(relatSys);
		List<CtrlTParaChnlDO> list = ctrlTParaChnlDao.list(ctrlTParaChnlDO);
		if(list.size()>0){
			String chnlNo="";
			for (CtrlTParaChnlDO ctrlTParaChnlDO2 : list) {
				chnlNo=chnlNo+ctrlTParaChnlDO2.getChnlNo()+",";
			}
			chnlNo=chnlNo.substring(0, chnlNo.length()-1);
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "该关联系统已被渠道["+chnlNo+"]使用！");
			return;
		}
		CtrlTPipBusiDO ctrlTPipBusiDO=new CtrlTPipBusiDO();
		ctrlTPipBusiDO.setRelatSys(relatSys);
		List<CtrlTPipBusiDO> list2 = ctrlTPipBusiDao.list(ctrlTPipBusiDO);
		if(list2.size()>0){
			String busiNo="";
			for (CtrlTPipBusiDO ctrlTPipBusiDO2 : list2) {
				busiNo=busiNo+ctrlTPipBusiDO2.getBusiNo()+",";
			}
			busiNo=busiNo.substring(0, busiNo.length()-1);
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "该关联系统已被业务["+busiNo+"]使用！");
			return;
		}
		CtrlTParaRelatSysDO obj = DatasetService.getInstace().getObject(reqDs, CtrlTParaRelatSysDO.class);
		if(ctrlTParaRelatSysService.delete(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "删除交易成功！");
			// 调用参数缓存刷新
		    CompCtrlUtil.redisRefreshByTableName(CompCtrlUtil.TABLE_PARA_RELAT_SYS);
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
		CtrlTParaRelatSysDO obj = DatasetService.getInstace().getObject(reqDs, CtrlTParaRelatSysDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		List<CtrlTParaRelatSysDO> list = ctrlTParaRelatSysService.list(obj, start, limit);
		int total = ctrlTParaRelatSysService.getTotal(obj);
		resDs = DatasetService.getInstace().getDataset(list,CtrlTParaRelatSysDO.class);
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
		String relatSys = reqDs.getString("relatSys");
		if (DataUtil.isNullStr(relatSys)) {
			throw new BaseException(SysErr.E_IN_NULL, "输入项[relatSys]不能空！");
		}		

		CtrlTParaRelatSysDO obj = ctrlTParaRelatSysService.get(relatSys);
		IDataset resDs = DatasetService.getInstace().getDataset(obj, CtrlTParaRelatSysDO.class);
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
		//新增系统类型中文描述列
		ds.addColumn("sysTpStr", DatasetColumnType.DS_STRING);
		//新增状态中文描述列
		ds.addColumn("openStatStr", DatasetColumnType.DS_STRING);
		if(isAction){
			//新增操作描述列
			ds.addColumn("action", DatasetColumnType.DS_STRING);
		}
		ds.beforeFirst();
		while(ds.hasNext()){
			ds.next();
			//更新系统类型中文描述
			String fctrlSysTp = ds.getString("sysTp");
			ds.updateString("sysTpStr", DictUtils.getDictLabels(fctrlSysTp, "SYS_TP", fctrlSysTp));
			//更新状态中文描述
			String fctrlStat = ds.getString("openStat");
			ds.updateString("openStatStr", DictUtils.getDictLabels(fctrlStat, "OPEN_STAT", fctrlStat));
			
			if(isAction){
				/*添加相关操作按钮*/
				StringBuffer action = new StringBuffer();
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"detail(" +"'"+ds.getString("relatSys")+"'" + ")\" >详情</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"update(" +"'"+ds.getString("relatSys")+"'" + ")\" >修改</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"del(" +"'"+ds.getString("relatSys")+"'" + ")\" >删除</a>");
				ds.updateString("action", action.toString());
			}
		}
	}
}