package com.adtec.comp.ctrl.oper.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comp.ctrl.oper.entity.FCtrlParaLoadDO;
import com.adtec.comp.ctrl.oper.entity.FCtrlRelatSysDO;
import com.adtec.comp.ctrl.oper.dao.FCtrlParaLoadDao;
import com.adtec.comp.ctrl.oper.dao.FCtrlRelatSysDao;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.ctrl.dto.FCtrlTranChangePwdReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlTranCommonResDTO;
import com.adtec.comp.ctrl.dto.FCtrlTranForceOutNotiReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlTranLoginReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlTranLogoutReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlTranRedisRefreshReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlTranSysParaUpdateReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlTranSysRunNotiOutReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlTranSysStatChangeNotiReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlTranSysTestCommReqDTO;
import com.adtec.comp.ctrl.oper.util.CompCtrlUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.service.BaseService;

@Service
public class FCtrlRelatTranService extends BaseService{
	
	@Autowired
	private FCtrlRelatSysDao relatSysDao;
	@Autowired
	private FCtrlParaLoadDao paraLoadDao;
	
	/**
	 * 数据库多笔查询
	 * @param obj 数据对象DO
	 * @return List返回集合
	 */
	public List<FCtrlRelatSysDO> getRuleList(FCtrlRelatSysDO obj) {
		return relatSysDao.list(obj);
	}
	
	/**
	 * 数据库多笔查询
	 * @param obj 数据对象DO
	 * @return List返回集合
	 */
	public List<FCtrlParaLoadDO> getParaLoadList(FCtrlParaLoadDO obj) {
		return paraLoadDao.list(obj);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @param relatSys
	 * @return
	 */
	public FCtrlRelatSysDO getSingleData(FCtrlRelatSysDO obj, String relatSys) {
		return relatSysDao.getSingleData(obj, relatSys);
	}

	/**
	 * 登录					
	 * @param reqBody
	 * @return
	 */
	public IDataset login(FCtrlTranLoginReqDTO reqBody,String BUSI_NO){
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(BUSI_NO,req, "FCtrlTranLogin");
		/*req.getAPP_HEAD().setBRCH("8888"); */
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("CTRL_PARTID"),
				"FCtrlTranLogin", req, FCtrlTranCommonResDTO.class, null);
		FCtrlTranCommonResDTO resBody = new FCtrlTranCommonResDTO();
		if (resDTO.getBODY() != null) {
			resBody = (FCtrlTranCommonResDTO) resDTO.getBODY();
		}	
		IDataset resDs = DatasetService.getInstace().getDataset(resBody,FCtrlTranCommonResDTO.class);
		return resDs;
	}

	/**
	 * 修改密码
	 * @param reqBody
	 * @return
	 */
	public IDataset change(FCtrlTranChangePwdReqDTO reqBody,String BUSI_NO){
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(BUSI_NO,req, "FCtrlTranChangePassword");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("CTRL_PARTID"),
				"FCtrlTranChangePassword", req, FCtrlTranCommonResDTO.class, null);
		FCtrlTranCommonResDTO resBody = new FCtrlTranCommonResDTO();
		if (resDTO.getBODY() != null) {
			resBody = (FCtrlTranCommonResDTO) resDTO.getBODY();
		}	
		IDataset resDs = DatasetService.getInstace().getDataset(resBody,FCtrlTranCommonResDTO.class);
		return resDs;
	}
	
	/**
	 * 退出
	 * @param reqBody
	 * @return
	 */
	public IDataset logout(FCtrlTranLogoutReqDTO reqBody,String BUSI_NO){
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(BUSI_NO,req, "FCtrlTranLogout");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("CTRL_PARTID"),
				"FCtrlTranLogout", req, FCtrlTranCommonResDTO.class, null);
		FCtrlTranCommonResDTO resBody = new FCtrlTranCommonResDTO();
		if (resDTO.getBODY() != null) {
			resBody = (FCtrlTranCommonResDTO) resDTO.getBODY();
		}	
		IDataset resDs = DatasetService.getInstace().getDataset(resBody,FCtrlTranCommonResDTO.class);
		return resDs;
	}
	
	/**
	 * 测试连接
	 * @param reqBody
	 * @return
	 */
	public IDataset testconn(FCtrlTranSysTestCommReqDTO reqBody,String BUSI_NO){
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(BUSI_NO,req, "FCtrlTranSysTestComm");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("CTRL_PARTID"),
				"FCtrlTranSysTestComm", req, FCtrlTranCommonResDTO.class, null);
		FCtrlTranCommonResDTO resBody = new FCtrlTranCommonResDTO();
		if (resDTO.getBODY() != null) {
			resBody = (FCtrlTranCommonResDTO) resDTO.getBODY();
		}	
		IDataset resDs = DatasetService.getInstace().getDataset(resBody,FCtrlTranCommonResDTO.class);
		return resDs;
	}
	
	
	
	/**
	 * 强制退出通知
	 * @param reqBody
	 * @return
	 */
	public IDataset forceLogoutNoti(FCtrlTranForceOutNotiReqDTO reqBody){
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlTranForceLogoutNoticeOut");
		 
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("CTRL_PARTID"),
				"FCtrlTranForceLogoutNoticeOut", req, FCtrlTranCommonResDTO.class, null);
		FCtrlTranCommonResDTO resBody = new FCtrlTranCommonResDTO();
		if (resDTO.getBODY() != null) {
			resBody = (FCtrlTranCommonResDTO) resDTO.getBODY();
		}	
		IDataset resDs = DatasetService.getInstace().getDataset(resBody,FCtrlTranCommonResDTO.class);
		return resDs;
	}
	
	/**
	 * 停运启运通知
	 * @param reqBody
	 * @return
	 */
	public IDataset runNoti(FCtrlTranSysRunNotiOutReqDTO reqBody){
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlTranSysRunNoticeOut");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("CTRL_PARTID"),
				"FCtrlTranSysRunNoticeOut", req, FCtrlTranCommonResDTO.class, null);
		FCtrlTranCommonResDTO resBody = new FCtrlTranCommonResDTO();
		if (resDTO.getBODY() != null) {
			resBody = (FCtrlTranCommonResDTO) resDTO.getBODY();
		}	
		IDataset resDs = DatasetService.getInstace().getDataset(resBody,FCtrlTranCommonResDTO.class);
		return resDs;
	}
	/**
	 * 状态改变通知
	 * @param reqBody
	 * @return
	 */
	public IDataset statChangeNoti(FCtrlTranSysStatChangeNotiReqDTO reqBody){
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlTranSysStatChangeNoticeOut");
		 
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("CTRL_PARTID"),
				"FCtrlTranSysStatChangeNoticeOut", req, FCtrlTranCommonResDTO.class, null);
		FCtrlTranCommonResDTO resBody = new FCtrlTranCommonResDTO();
		if (resDTO.getBODY() != null) {
			resBody = (FCtrlTranCommonResDTO) resDTO.getBODY();
		}	
		IDataset resDs = DatasetService.getInstace().getDataset(resBody,FCtrlTranCommonResDTO.class);
		return resDs;
	}
	/**
	 * 系统参数刷新
	 * @param reqBody
	 * @return
	 */
	public IDataset sysParaUpdate(FCtrlTranRedisRefreshReqDTO reqBody){
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
