package com.adtec.prod.om.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adtec.prod.dto.FPRodEntrRegRedisParaReqDTO;
import com.adtec.prod.om.service.FreshParaService;
import com.adtec.prod.oper.entity.BusiSignDO;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.framework.interfaces.share.IDatasets;
import com.adtec.sys.common.web.BaseController;
import com.adtec.framework.exception.SysErr;

@Controller
@RequestMapping(value = "${adminPath}/prod/om/freshPara")
public class FreshParaController extends BaseController {
	@Autowired
	private FreshParaService freshParaService;

	
	/**
	 * 业务一键生效页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "freshParaForm" })
	public String freshParaForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/prod/om/freshParaForm";
	}
	/**
	 * 业务一键生效管理页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "paraConfManage" })
	public String paraConfManage(HttpServletRequest request, HttpServletResponse response) {
		return "starring/prod/om/paraConfManage";
	}
	/**
	 * 业务一键生效页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "paraConfForm" })
	public String paraConfForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/prod/om/paraConfForm";
	}
	
	/**
	 * 业务一键生效
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"fresh"})
	public void fresh(HttpServletRequest request,HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String busiNo = reqDs.getString("busiNo");
		FPRodEntrRegRedisParaReqDTO reqBody = new FPRodEntrRegRedisParaReqDTO();
		reqBody.setOPER_TP("2");
		IDataset res = freshParaService.fresh(reqBody,busiNo);
		setResponseDataset(request, response, res, SysErr.E_SUCCESS, "提交成功");
	}
	
	/**
	 * 获取业务信息
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"getBusiInfo"})
	public void getBusiInfo(HttpServletRequest request,HttpServletResponse response){
		//IDataset reqDs = DatasetService.getInstace().getDataset(request);
		
		List<BusiSignDO> list = freshParaService.getBusiInfo();
		if(list !=null && list.size() > 0){
			for(BusiSignDO bd : list){
				bd.setBusiName(bd.getBusiNo()+"-"+bd.getBusiName());
			}
		}
		IDataset res = DatasetService.getInstace().getDataset(list, BusiSignDO.class);
//		IDataset res = freshParaService.fresh(reqBody);
		setResponseDataset(request, response, res, SysErr.E_SUCCESS, "提交成功");
	}
	/**
	 * 获取组件技术参数
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"getParaConfInfo"})
	public void getParaConfInfo(HttpServletRequest request,HttpServletResponse response){
		//IDataset reqDs = DatasetService.getInstace().getDataset(request);
		
		IDatasets resDss = freshParaService.getParaConfInfo();
//		IDataset res = DatasetService.getInstace().getDataset(list, TPipCompDO.class);
//		IDataset res = freshParaService.fresh(reqBody);
		setResponseDataset(request, response, resDss, SysErr.E_SUCCESS, "获取成功");
	}
	
	/**
	 * 保存组件技术参数
	 */
	@RequiresPermissions("user")
	@RequestMapping(value ={"saveParaConfInfo"})
	public void saveParaConfInfo(HttpServletRequest request,HttpServletResponse response){
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String json = reqDs.getString("contents");
		freshParaService.saveParaConfInfo(json);
//		IDatasets resDss = freshParaService.getParaConfInfo();
		IDataset res = DatasetService.getInstace().getDataset();
		setResponseDataset(request, response, res, SysErr.E_SUCCESS, "提交成功");
	}
	
	
	
}
