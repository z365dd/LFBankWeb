/**
* 系统名称: SmartWeb平台
* 模块名称: comp.ctrl.oper服务模块
* 功能描述: 平台日切服务提供类
* 类 名 称  : FCtrlTParaDayService.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200512<br>
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
import com.adtec.comp.ctrl.dto.FCtrlPlaDayChgNotiReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlTranCommonResDTO;
import com.adtec.comp.ctrl.oper.dao.FCtrlTParaDayDao;
import com.adtec.comp.ctrl.oper.entity.FCtrlTParaDayDO;
import com.adtec.comp.ctrl.oper.util.CompCtrlUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;

/**
 * FCtrlTParaDayService
 * @author zhengjt
 * @version 20200512
 */
@Service
@Transactional(readOnly = true)
public class FCtrlTParaDayService {
	private final static Logger log = LoggerFactory.getLogger(FCtrlTParaDayService.class);
	
	@Autowired
	private FCtrlTParaDayDao fCtrlTParaDayDao;
	
	
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
	
	/**
	 * 获取单条数据
	 * @param platNo
	 * @return
	 */
	public FCtrlTParaDayDO get(
		String platNo
	) {
		return fCtrlTParaDayDao.get(
		platNo
);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	public FCtrlTParaDayDO get(FCtrlTParaDayDO obj) {
		return fCtrlTParaDayDao.get(obj);
	}
	
	/**
	 * 数据库多笔查询
	 * @param obj 数据对象DO
	 * @return List返回集合
	 */
	public List<FCtrlTParaDayDO> list(FCtrlTParaDayDO obj) {
		return fCtrlTParaDayDao.list(obj);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param obj 数据对象DO
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @return List返回集合
	 */
	public List<FCtrlTParaDayDO> list(FCtrlTParaDayDO obj, int start, int limit) {
		return fCtrlTParaDayDao.list(obj, start, limit);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @param param 查询参数
	 * @return List返回集合
	 */
	public List<FCtrlTParaDayDO> list(int start, int limit, Object... param) {
		return fCtrlTParaDayDao.list(start, limit, param);
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(FCtrlTParaDayDO obj) {
		return fCtrlTParaDayDao.getTotal(obj);
	}
}