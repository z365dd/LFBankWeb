package com.adtec.comp.ctrl.oper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.ctrl.dto.FCtrlTranCommonResDTO;
import com.adtec.comp.ctrl.dto.CtrlRedisRefreshReqDTO;
import com.adtec.comp.ctrl.oper.dao.CtrlTPipBusiDao;
import com.adtec.comp.ctrl.oper.dao.FCtrlParaLoadDao;
import com.adtec.comp.ctrl.oper.entity.CtrlTPipBusiDO;
import com.adtec.comp.ctrl.oper.entity.FCtrlParaLoadDO;
import com.adtec.comp.ctrl.oper.util.CompCtrlUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.service.BaseService;

@Service
public class CtrlRedisRefreService extends BaseService{
	
	@Autowired
	private FCtrlParaLoadDao paraLoadDao;
	@Autowired
	private CtrlTPipBusiDao ctrlTPipBusiDao;
	/**
	 * 数据库多笔查询
	 * @param obj 数据对象DO
	 * @return List返回集合
	 */
	public List<FCtrlParaLoadDO> getParaLoadList(FCtrlParaLoadDO obj) {
		return paraLoadDao.list(obj);
	}
	
	/**
	 * 业务表多笔查询
	 * 
	 * @param obj
	 *            数据对象DO
	 * @return List返回集合
	 */
	public List<CtrlTPipBusiDO> getBusiNo(CtrlTPipBusiDO obj) {
		return ctrlTPipBusiDao.list(obj);
	}
	
	/**
	 * 系统参数刷新
	 * @param reqBody
	 * @return
	 */
	public IDataset sysParaUpdate(CtrlRedisRefreshReqDTO reqBody){
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlTranRedisRefresh");
		
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("CTRL_PARTID"),
				"FCtrlTranRedisRefresh", req, FCtrlTranCommonResDTO.class, null);
		FCtrlTranCommonResDTO resBody = new FCtrlTranCommonResDTO();
		if (resDTO.getBODY() != null) {
			resBody = (FCtrlTranCommonResDTO) resDTO.getBODY();
		}	
		IDataset resDs = DatasetService.getInstace().getDataset(resBody,FCtrlTranCommonResDTO.class);
		return resDs;
	}
	
	
}
