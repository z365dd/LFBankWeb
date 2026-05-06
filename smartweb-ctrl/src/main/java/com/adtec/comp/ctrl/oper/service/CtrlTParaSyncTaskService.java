/**
* 系统名称: SmartWeb平台
* 模块名称: comp.ctrl.oper服务模块
* 功能描述: 数据同步任务服务提供类
* 类 名 称  : CtrlTParaSyncTaskService.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200513<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.comp.ctrl.oper.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.ctrl.dto.FCtrlTranDelDayStepReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlTranDelDayStepResDTO;
import com.adtec.comp.ctrl.dto.FCtrlTranRcvDayStepReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlTranRcvDayStepResDTO;
import com.adtec.comp.ctrl.oper.dao.CtrlTParaSyncTaskDao;
import com.adtec.comp.ctrl.oper.entity.CtrlTParaSyncTaskDO;
import com.adtec.comp.ctrl.oper.util.CompCtrlUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;

/**
 * CtrlTParaSyncTaskService
 * @author zhengjt
 * @version 20200513
 */
@Service
@Transactional(readOnly = true)
public class CtrlTParaSyncTaskService {
	private final static Logger log = LoggerFactory.getLogger(CtrlTParaSyncTaskService.class);
	
	@Autowired
	private CtrlTParaSyncTaskDao ctrlTParaSyncTaskDao;
	
	/**
	 * 获取单条数据
	 * @param stepNo
	 * @param platDate
	 * @return
	 */
	public CtrlTParaSyncTaskDO get(
		String stepNo
				,
		String platDate
	) {
		return ctrlTParaSyncTaskDao.get(
		stepNo
				,
		platDate
);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	public CtrlTParaSyncTaskDO get(CtrlTParaSyncTaskDO obj) {
		return ctrlTParaSyncTaskDao.get(obj);
	}
	
	
	/**
	 * 数据库多笔查询
	 * @param obj 数据对象DO
	 * @return List返回集合
	 */
	public List<CtrlTParaSyncTaskDO> list(CtrlTParaSyncTaskDO obj) {
		return ctrlTParaSyncTaskDao.list(obj);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param obj 数据对象DO
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @return List返回集合
	 */
	public List<CtrlTParaSyncTaskDO> list(CtrlTParaSyncTaskDO obj, int start, int limit) {
		return ctrlTParaSyncTaskDao.list(obj, start, limit);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @param param 查询参数
	 * @return List返回集合
	 */
	public List<CtrlTParaSyncTaskDO> list(int start, int limit, Object... param) {
		return ctrlTParaSyncTaskDao.list(start, limit, param);
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(CtrlTParaSyncTaskDO obj) {
		return ctrlTParaSyncTaskDao.getTotal(obj);
	}

	public IDataset rcvDayStep(FCtrlTranRcvDayStepReqDTO reqBody) {
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlTranRcvDayStep");
		 
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("CTRL_PARTID"),
				"FCtrlTranRcvDayStep", req, FCtrlTranRcvDayStepResDTO.class, null);
		FCtrlTranRcvDayStepResDTO resBody = new FCtrlTranRcvDayStepResDTO();
		if (resDTO.getBODY() != null) {
			resBody = (FCtrlTranRcvDayStepResDTO) resDTO.getBODY();
		}	
		IDataset resDs = DatasetService.getInstace().getDataset(resBody,FCtrlTranRcvDayStepResDTO.class);
		return resDs;
	}
	
	public IDataset delDayStep(FCtrlTranDelDayStepReqDTO reqBody) {
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlTranDelDayStep");
		 
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("CTRL_PARTID"),
				"FCtrlTranDelDayStep", req, FCtrlTranDelDayStepResDTO.class, null);
		FCtrlTranDelDayStepResDTO resBody = new FCtrlTranDelDayStepResDTO();
		if (resDTO.getBODY() != null) {
			resBody = (FCtrlTranDelDayStepResDTO) resDTO.getBODY();
		}	
		IDataset resDs = DatasetService.getInstace().getDataset(resBody,FCtrlTranDelDayStepResDTO.class);
		return resDs;
	}
}