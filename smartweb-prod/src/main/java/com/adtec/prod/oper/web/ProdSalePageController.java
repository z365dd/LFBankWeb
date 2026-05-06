package com.adtec.prod.oper.web;

import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.prod.dto.FProdSaleProdPageParaViewReqDTO;
import com.adtec.prod.oper.definition.adapter.entity.TPipSaleProdAdapterDOForMsmall;
import com.adtec.prod.oper.service.ProdSalePageService;
import com.adtec.sys.common.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "${adminPath}/prod/oper/prodSalePage")
public class ProdSalePageController extends BaseController {
	@Autowired
	private ProdSalePageService prodSalePageService;

	/**
	 * 返回查询页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "prodSalePageList" })
	public String prodSalePageList(HttpServletRequest request, HttpServletResponse response) {
		return "starring/prod/oper/prodSalePageList";
	}

	/**
	 * 返回表单页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "prodSalePageForm" })
	public String prodSalePageForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/prod/oper/prodSalePageForm";
	}
	
	/**
	 * 返回预览页面
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "prodSalePagePerForm" })
	public String prodSalePagePerForm(HttpServletRequest request, HttpServletResponse response) {
		return "starring/prod/oper/prodSalePagePerForm";
	}

	/**
	 * 获取预览数据
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "perPage" })
	public void perPage(HttpServletRequest request, HttpServletResponse response) {
		// TODO
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String SALE_PROD_CODE = reqDs.getString("SALE_PROD_CODE");
		FProdSaleProdPageParaViewReqDTO reqBody = new FProdSaleProdPageParaViewReqDTO();
		reqBody.setSALE_PROD_CODE(SALE_PROD_CODE);

		IDataset resDs = prodSalePageService.perPage(reqBody);
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

	/**
	 * 获取预览数据
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = { "perPageForMsmall" })
	public void perPageForMsmall(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		TPipSaleProdAdapterDOForMsmall obj = DatasetService.getInstace().getObject(reqDs, TPipSaleProdAdapterDOForMsmall.class);

		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		IDataset resDs = DatasetService.getInstace().getDataset(prodSalePageService.perPageForMsmall(obj, start, limit), TPipSaleProdAdapterDOForMsmall.class);
		resDs.setTotalCount(prodSalePageService.getTotal(obj));
		setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
	}

}
