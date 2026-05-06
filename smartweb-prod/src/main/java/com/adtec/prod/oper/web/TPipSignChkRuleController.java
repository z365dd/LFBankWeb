/**
* 系统名称: SmartWeb平台
* 模块名称: prod-oper拦截器模块
* 功能描述: T_PIP_SIGN_CHK_RULE控制类
* 类 名 称  : TPipSignChkRuleController.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200313<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.prod.oper.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.DatasetColumnType;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.sys.common.web.BaseController;
import com.adtec.prod.oper.entity.TPipRuleRelatDO;
import com.adtec.prod.oper.entity.TPipSignChkRuleDO;
import com.adtec.prod.oper.service.TPipRuleRelatService;
import com.adtec.prod.oper.service.TPipSignChkRuleService;

/**
 * T_PIP_SIGN_CHK_RULEController
 * @author linyx
 * @version 20200313
 */
@Controller
@RequestMapping(value = "${adminPath}/prod/oper/tPipSignChkRule")
public class TPipSignChkRuleController extends BaseController {

	@Autowired
	private TPipRuleRelatService tPipRuleRelatService;
	@Autowired
	private TPipSignChkRuleService tPipSignChkRuleService;
	
	/*首页页面路径*/
	private String PATH = "starring/prod/oper/tPipSignChkRule";
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
	@RequestMapping(value ={"tPipSignChkRuleList"})
	public String tPipSignChkRuleList(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"List";
	}
	
	/**
	 * 进入修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"tPipSignChkRuleUpdate"})
	public String tPipSignChkRuleUpdate(HttpServletRequest request, HttpServletResponse response) {
		return PATH+"UpdateForm";
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
		TPipSignChkRuleDO obj = DatasetService.getInstace().getObject(reqDs, TPipSignChkRuleDO.class);
		if(tPipSignChkRuleService.update(obj)){
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "更新交易成功！");
		}else{
			setResponseDataset(request, response, resDs, SysErr.E_DEFAULT, "更新交易失败！");
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
		TPipRuleRelatDO obj = DatasetService.getInstace().getObject(reqDs, TPipRuleRelatDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		String busiNo =  reqDs.getString("busiNo");
		obj.setBusiNo(busiNo);
		obj.setRuleTp("402");
		
		List<TPipRuleRelatDO> list = tPipRuleRelatService.list(obj, start, limit);
		int total = tPipRuleRelatService.getTotal(obj);
		resDs = DatasetService.getInstace().getDataset(list,TPipRuleRelatDO.class);
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
		String ruleId = reqDs.getString("ruleId");
		if (DataUtil.isNullStr(ruleId)) {
			throw new BaseException(SysErr.E_IN_NULL, "输入项[ruleId]不能空！");
		}		

		TPipSignChkRuleDO obj = tPipSignChkRuleService.get(ruleId);
		IDataset resDs = DatasetService.getInstace().getDataset(obj, TPipSignChkRuleDO.class);
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
		ds.addColumn("ACTION", DatasetColumnType.DS_STRING);
		ds.beforeFirst();
		while(ds.hasNext()){
			ds.next();
			/*添加相关操作按钮*/
			StringBuffer action = new StringBuffer();
			action.append("	<a href=\"JavaScript:void(0);\" onClick=\"update('" + ds.getString("ruleId") + "')\" >配置</a>");
			ds.updateString("ACTION", action.toString());
		}
	}
}