package com.adtec.prod.oper.service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.prod.oper.dao.ProdSaleProdDao;
import com.adtec.prod.oper.definition.adapter.entity.TPipSaleProdAdapterDOForMsmall;
import com.adtec.prod.util.ProdUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.prod.dto.*;
import com.adtec.prod.oper.entity.ProdSaleProdDO;
import com.adtec.sys.common.web.Servlets;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProdSalePageService {
	@Autowired
	private ProdSaleProdDao saleProdDao;

	public IDataset perPage(FProdSaleProdPageParaViewReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();

		com.adtec.framework.json.JSONObject resBody = new com.adtec.framework.json.JSONObject(saleProdDao.getPerPage(reqBody.getSALE_PROD_CODE()));
		responseData.addColumn("FProdSaleProdPageParaViewRes");
		responseData.beforeFirst();
		if(!responseData.hasNext()){
			responseData.appendRow();
		}
		responseData.next();
		responseData.updateValue("FProdSaleProdPageParaViewRes", resBody);
		return responseData;
	}

	public List<TPipSaleProdAdapterDOForMsmall> perPageForMsmall(TPipSaleProdAdapterDOForMsmall obj, int start, int limit) {
		return saleProdDao.getPerPageForMsmall(obj, start, limit);
	}

	public int getTotal(TPipSaleProdAdapterDOForMsmall obj) {
		return saleProdDao.getTotal(obj);
	}

}
