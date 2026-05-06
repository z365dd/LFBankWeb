package com.adtec.prod.oper.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adtec.prod.oper.entity.TPipRuleRelatDO;
import com.adtec.prod.oper.service.TPipRuleRelatService;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.DatasetColumnType;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.adtec.framework.exception.SysErr;
@Controller
@RequestMapping(value = "${adminPath}/prod/oper/ruleRelat")
public class RuleRelatController extends BaseController{
	@Autowired
	private TPipRuleRelatService tPipRuleRelatService;
	
	/**
	 * 列表查询
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "list" })
	public void qry(HttpServletRequest request, HttpServletResponse response) {

		IDataset resDs = DatasetService.getInstace().getDataset();
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		TPipRuleRelatDO obj = DatasetService.getInstace().getObject(reqDs, TPipRuleRelatDO.class);
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		String busiNo =  reqDs.getString("busiNo");
		String busiName =  reqDs.getString("busiName");
		String ruleTp =  reqDs.getString("ruleTp");
		obj.setBusiNo(busiNo);
		obj.setRuleTp(ruleTp);
		obj.setBusiName(busiName);
		List<TPipRuleRelatDO> list = tPipRuleRelatService.list(obj, start, limit);
		int total = tPipRuleRelatService.getTotal(obj);
		resDs = DatasetService.getInstace().getDataset(list,TPipRuleRelatDO.class);
		chgDict(resDs);
		resDs.setTotalCount(total);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "列表查询交易成功！");
	}
	
	/**
	 * 列表操作处理
	 */
	private void chgDict(IDataset ds) {
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
