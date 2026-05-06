package com.adtec.prod.om.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.prod.oper.dao.TPipCompDao;
import com.adtec.prod.oper.entity.TPipCompDO;
import com.adtec.prod.oper.entity.TPipCompParaDO;
import com.adtec.prod.dto.FPRodEntrRegRedisParaReqDTO;
import com.adtec.prod.dto.FPRodEntrRegRedisParaResDTO;
import com.adtec.prod.oper.dao.BusiSignDao;
import com.adtec.prod.oper.entity.BusiSignDO;
import com.adtec.prod.util.ProdUtil;
import com.adtec.framework.impl.share.dataset.CommonDatasets;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.framework.interfaces.share.IDatasets;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.alibaba.fastjson.JSON;

@Service
public class FreshParaService {
	
	@Autowired
	private BusiSignDao busiSignDao;
	@Autowired
	private TPipCompDao tPipCompDao;
	/**
	 * 刷新
	 */
	public IDataset fresh(FPRodEntrRegRedisParaReqDTO reqBody,String BUSI_NO){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		ProdUtil.setReqHead(BUSI_NO,req, "FCtrlTranRedisRefresh");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();//FPRodEntrRegRedisPara //ParamUtil.getConfig("PROD_PARTID")
		ResDTO res = (ResDTO) httpJsonFactory.callService("","999103","FCtrlTranRedisRefresh", req, FPRodEntrRegRedisParaReqDTO.class,null);
		responseData = DatasetService.getInstace().getDataset(res.getBODY(),FPRodEntrRegRedisParaResDTO.class);
		return responseData;
	}

	/**
	 * 获取业务信息
	 * @return
	 */
	public List<BusiSignDO> getBusiInfo() {
		BusiSignDO busiSign = new BusiSignDO();
		List<BusiSignDO> list = busiSignDao.list(busiSign);
		return list;
	}

	/**
	 * 获取技术参数
	 * @return
	 */
	public IDatasets getParaConfInfo() {
		IDatasets resDss = new CommonDatasets();
		
		TPipCompDO obj = new TPipCompDO();
		List<TPipCompDO> list = tPipCompDao.list(obj);
		for (TPipCompDO item : list) {
			List<TPipCompParaDO> compParaList = tPipCompDao.getCompParaByCompNo(item);
			if (compParaList.size() == 0) {
				continue;
			}
			IDataset res = DatasetService.getInstace().getDataset(compParaList, TPipCompParaDO.class);
			res.setDatasetName(item.getCompNo()+"-"+item.getCompName());
			resDss.putDataset(res);
		}
		return resDss;
	}
	
	/**
	 * 保存技术参数
	 * @param json
	 */
	public void saveParaConfInfo(String json) {
		List<TPipCompParaDO> compParaList = JSON.parseArray(json, TPipCompParaDO.class);
		IDBSession session = DBSessionFactory.getSession();
		try {
			session.beginTransaction();
			for (TPipCompParaDO item : compParaList) {
				tPipCompDao.saveParaConfInfo(item);
			}
			session.endTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				session.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_MESSAGE, e.getMessage());
		}
		
	}
	
	
}
