/**
* 系统名称: SmartWeb平台
* 模块名称: sys-modules拦截器模块
* 功能描述: 快捷菜单入口控制类
* 类 名 称  : SysQuickEntryController.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20190904<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.sys.modules.sys.web;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.DatasetColumnType;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.modules.sys.entity.Menu;
import com.adtec.sys.modules.sys.entity.SysQuickEntryDO;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.service.AreaService;
import com.adtec.sys.modules.sys.service.OfficeService;
import com.adtec.sys.modules.sys.service.SysQuickEntryService;
import com.adtec.sys.modules.sys.service.SystemService;
import com.adtec.sys.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 快捷菜单入口Controller
 * @author zx
 * @version 20190904
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/modules/sysQuickEntry")
public class SysQuickEntryController extends BaseController {

	@Autowired
	private SysQuickEntryService sysQuickEntryService;
	
	
	/*首页页面路径*/
	private String PATH = (ParamUtil.getJspPath().split(ParamUtil.getConfig("web.view.prefix"))[1])+"/"+("sys/modules/sysQuickEntry.jsp".replace(ParamUtil.getConfig("web.view.suffix"),""));
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
	@RequestMapping(value ={"sysQuickEntryList"})
	public String sysQuickEntryList(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"List";
	}
	
	/**
	 * 进入新增页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"sysQuickEntryAdd"})
	public String studentAdd(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"AddForm";
	}
	
	/**
	 * 进入修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"sysQuickEntryUpdate"})
	public String studentUpdate(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"UpdateForm";
	}
	
	/**
	 * 进入详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"sysQuickEntryDetail"})
	public String studentDetail(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"DetailForm";
	}
	
    @RequestMapping(value = { "mainIndex" })
    public String mainIndex(HttpServletRequest request, HttpServletResponse response) {
		/**监控大屏请求处理*/
		screenReqDeal(request);

        return "starring/sys/mainIndex";
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
		SysQuickEntryDO obj = DatasetService.getInstace().getObject(reqDs, SysQuickEntryDO.class);
		if(sysQuickEntryService.insert(obj)){
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
		SysQuickEntryDO obj = DatasetService.getInstace().getObject(reqDs, SysQuickEntryDO.class);
		if(sysQuickEntryService.update(obj)){
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
		SysQuickEntryDO obj = DatasetService.getInstace().getObject(reqDs, SysQuickEntryDO.class);
		if(sysQuickEntryService.delete(obj)){
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
		SysQuickEntryDO obj = DatasetService.getInstace().getObject(reqDs, SysQuickEntryDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		List<SysQuickEntryDO> list = sysQuickEntryService.list(obj, start, limit);
		int total = sysQuickEntryService.getTotal(obj);
		resDs = DatasetService.getInstace().getDataset(list,SysQuickEntryDO.class);
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
		String id = reqDs.getString("id");
		if (DataUtil.isNullStr(id)) {
			throw new BaseException(SysErr.E_MESSAGE, "输入项[id]不能空！");
		}
		SysQuickEntryDO obj = sysQuickEntryService.get(id);
		IDataset resDs = DatasetService.getInstace().getDataset(obj, SysQuickEntryDO.class);
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
		//新增uptr中文名称列
		ds.addColumn("uptrName", DatasetColumnType.DS_STRING);
		if(isAction){
			//新增操作描述列
			ds.addColumn("action", DatasetColumnType.DS_STRING);
		}
		ds.beforeFirst();
		while(ds.hasNext()){
			ds.next();
			//更新uptr中文名称
			String uptr = ds.getString("uptr");
			String uptrName = uptr;
			User uptrUser = systemService.getUser(uptr);
			if(null!=uptrUser){
					uptrName = uptrUser.getName();
			}
			ds.updateString("uptrName", uptrName);
			
			if(isAction){
				/*添加相关操作按钮*/
				StringBuffer action = new StringBuffer();
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"detail('" + ds.getString("id") + "')\" >详情</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"update('" + ds.getString("id") + "')\" >修改</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"del('" + ds.getString("id") + "')\" >删除</a>");
				ds.updateString("action", action.toString());
			}
		}
	}
	
    @ResponseBody
    @RequestMapping(value = "getQuickEntry")
    public List<Map<String, Object>> getQuickEntry() {
        return sysQuickEntryService.getQuickEntry();
    }
    
    @ResponseBody
    @RequestMapping(value = "delQuickEntry")
    public boolean delQuickEntry(String id) {
        return sysQuickEntryService.delete(id);
    }
    
    @ResponseBody
    @RequestMapping(value = "addQuickEntry")
    public boolean addQuickEntry(String menuId,String menuName,String pId,String pName,String img) {
        SysQuickEntryDO obj = new SysQuickEntryDO();
        obj.setMenuId(menuId);
        obj.setMenuName(menuName);
        obj.setPmenuId(pId);
        obj.setPmenuName(pName);
        obj.setUserId(UserUtils.getUser().getId());
        obj.setImg(img);
        String path = "";
        SystemService systemService = SpringContextHolder.getBean("systemService");
        Menu pMenu = systemService.getMenu(pId);
        Menu gMenu = systemService.getMenu(pMenu.getParentId());
        if(gMenu != null){
            path = gMenu.getName()+"->"+pName+"->"+menuName;
            obj.setPath(path);
        }
        return sysQuickEntryService.insert(obj);
    }

	/**
	 * 监控大屏请求处理类
	 * @param request
	 */
	public void screenReqDeal(HttpServletRequest request) {
		HttpSession session = request.getSession();
		request.setAttribute("screenReqFlag", session.getAttribute("screenReqFlag"));
		request.setAttribute("startTime", session.getAttribute("startTime"));
		request.setAttribute("endTime", session.getAttribute("endTime"));
		request.setAttribute("glbl_biz_swfno", session.getAttribute("glbl_biz_swfno"));
		request.setAttribute("prvpt_cenmd_no", session.getAttribute("prvpt_cenmd_no"));
		request.setAttribute("prvpt_mcrsv_no", session.getAttribute("prvpt_mcrsv_no"));
		request.setAttribute("cnsmr_mcrsv_swfno", session.getAttribute("cnsmr_mcrsv_swfno"));
		request.setAttribute("transTmMin", session.getAttribute("transTmMin"));
		request.setAttribute("transTmMax", session.getAttribute("transTmMax"));
		request.setAttribute("mcrsv_fnct_intfc_ecd", session.getAttribute("mcrsv_fnct_intfc_ecd"));
		request.setAttribute("trd_dlwth_retn_cd", session.getAttribute("trd_dlwth_retn_cd"));
		request.setAttribute("scene_idcd", session.getAttribute("scene_idcd"));
		request.setAttribute("biz_lunch_org_ecd", session.getAttribute("biz_lunch_org_ecd"));
		request.setAttribute("chnl_typ_cd", session.getAttribute("chnl_typ_cd"));
	}
}