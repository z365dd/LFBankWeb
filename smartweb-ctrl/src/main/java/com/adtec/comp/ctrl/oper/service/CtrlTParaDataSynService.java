package com.adtec.comp.ctrl.oper.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.ctrl.dto.FCtrlPlaDayChgNotiReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlTranCommonResDTO;
import com.adtec.comp.ctrl.oper.dao.CtrlTParaRelatSysDao;
import com.adtec.comp.ctrl.oper.util.CompCtrlUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;


@Service
@Transactional(readOnly = true)
public class CtrlTParaDataSynService {
private final static Logger log = LoggerFactory.getLogger(CtrlTParaRelatSysService.class);
	
	@Autowired
	private CtrlTParaRelatSysDao ctrlTParaRelatSysDao;
	
	/**
	 * 系统参数刷新
	 * @param reqBody
	 * @return
	 */
	public IDataset dayChgNotice(FCtrlPlaDayChgNotiReqDTO reqBody){
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlTranPlaDayChgNotice");
		 
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("CTRL_PARTID"),
				"FCtrlTranPlaDayChgNotice", req, FCtrlTranCommonResDTO.class, null);
		FCtrlTranCommonResDTO resBody = new FCtrlTranCommonResDTO();
		if (resDTO.getBODY() != null) {
			resBody = (FCtrlTranCommonResDTO) resDTO.getBODY();
		}	
		IDataset resDs = DatasetService.getInstace().getDataset(resBody,FCtrlTranCommonResDTO.class);
		return resDs;
	}
}
