/**
* 系统名称: SmartWeb平台
* 模块名称: sys-modules拦截器模块
* 功能描述: 公告消息控制类
* 类 名 称  : SysNoticeController.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20190829<br>
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
import com.adtec.sys.modules.sys.entity.SysNoticeDO;
import com.adtec.sys.modules.sys.entity.SysNoticeDataDO;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.service.AreaService;
import com.adtec.sys.modules.sys.service.OfficeService;
import com.adtec.sys.modules.sys.service.SysNoticeService;
import com.adtec.sys.modules.sys.service.SystemService;
import com.adtec.sys.modules.sys.utils.NotificationUtil;
import com.adtec.sys.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 公告消息Controller
 * @author zx
 * @version 20190829
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/modules/sysNotice")
public class SysNoticeController extends BaseController {

	@Autowired
	private SysNoticeService sysNoticeService;
	/** 消息发送工具对象 */
	/*首页页面路径*/
	private String PATH = (ParamUtil.getJspPath().split(ParamUtil.getConfig("web.view.prefix"))[1])+"/"+("sys/modules/sysNotice.jsp".replace(ParamUtil.getConfig("web.view.suffix"),""));
	/**
	 * 进入管理页面
	 * @param request
	 * @param response
	 * @return
	 */
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
	@RequestMapping(value ={"sysNoticeList"})
	public String sysNoticeList(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"List";
	}
	
	/**
	 * 进入新增页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ={"sysNoticeAdd"})
	public String sysNoticeAdd(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"AddForm";
	}
	
	/**
	 * 进入修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ={"sysNoticeUpdate"})
	public String sysNoticeUpdate(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"UpdateForm";
	}
	
	/**
	 * 进入详细页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ={"sysNoticeDetail"})
	public String sysNoticeDetail(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"DetailForm";
	}
	
	/**
	* 新增交易
	* @param request
	* @param response
	*/
	@ResponseBody
	@RequestMapping(value="insert")
	public void insert(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String ids = reqDs.getString("ids");
		System.out.println("============="+ids);
		SysNoticeDO obj = DatasetService.getInstace().getObject(reqDs, SysNoticeDO.class);
		if(sysNoticeService.insert(obj)){
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
	@RequestMapping(value="update")
	public void update(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		SysNoticeDO obj = DatasetService.getInstace().getObject(reqDs, SysNoticeDO.class);
		if(sysNoticeService.update(obj)){
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
	@RequestMapping(value="delete")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		SysNoticeDO obj = DatasetService.getInstace().getObject(reqDs, SysNoticeDO.class);
		if(sysNoticeService.delete(obj)){
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
	@RequestMapping(value="list")
	public void list(HttpServletRequest request, HttpServletResponse response) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		SysNoticeDO obj = DatasetService.getInstace().getObject(reqDs, SysNoticeDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		List<SysNoticeDO> list = sysNoticeService.list(obj, start, limit);
		int total = sysNoticeService.getTotal(obj);
		resDs = DatasetService.getInstace().getDataset(list,SysNoticeDO.class);
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
		SysNoticeDO obj = sysNoticeService.get(id);
		List<SysNoticeDataDO> objData = sysNoticeService.listData(id);
		String ids = "";
		if(objData !=null && objData.size() > 0){
		    for (int i = 0; i < objData.size(); i++) {
		        ids += objData.get(i).getDataId() + ",";
            }
		    if(ids.length() > 0){
		        ids = ids.substring(0, ids.length()-1);
		    }
		}
		obj.setIds(ids);
		IDataset resDs = DatasetService.getInstace().getDataset(obj, SysNoticeDO.class);
		chgDict(resDs, false);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "明细查询交易成功！");
	}
	
	/**
	 * 数字字典转换
	 * @param ds
	 * @param isAction
	 */
	private void chgDict(IDataset ds, boolean isAction){
		User user = UserUtils.getUser();
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
				if (user.getId().equals(ds.getString("crtr"))) {
					action.append("	<a href=\"JavaScript:void(0);\" onClick=\"update('" + ds.getString("id") + "')\" >修改</a>");
					action.append("	<a href=\"JavaScript:void(0);\" onClick=\"del('" + ds.getString("id") + "')\" >删除</a>");
				}
				ds.updateString("action", action.toString());
			}
		}
	}
	
    @ResponseBody
    @RequestMapping(value = "getTreeData")
    public List<Map<String, Object>> getTreeData(String type) {
        return sysNoticeService.getTreeData(type);
    }

    @ResponseBody
    @RequestMapping(value = "getNoticeList")
    public List<Map<String, Object>> getNoticeList() {
        return sysNoticeService.getNoticeList();
    }
    
    @ResponseBody
    @RequestMapping(value = "getUserInfo")
    public List<Map<String, Object>> getUserInfo() {
        return sysNoticeService.getUserInfo();
    }

	@RequestMapping("/sendMessage")
	public void sendTopicMessage() {
		SysNoticeDO sysNoticeDO = new SysNoticeDO();
		sysNoticeDO.setNoteTitle("测试公告消息");
		sysNoticeDO.setNoteCntt("公告消息系统测试");
		sysNoticeDO.setNoteScp("1");
		sysNoticeDO.setPopupFlg("Y");
		sysNoticeDO.setUrl("http://www.baidu.com");
		sysNoticeDO.setLinkDesc("百度");
		// NotificationUtil.notifyCurrentUser(sysNoticeDO);
	}
}