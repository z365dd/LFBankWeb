package com.adtec.comp.ctrl.test.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.dto.head.AppHeadAUListReqDTO;
import com.adtec.comm.dto.head.SysHeadRetResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.ctrl.dto.FCtrlMngDimQryListDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngDimQryReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlMngDimQryResDTO;
import com.adtec.comp.ctrl.dto.TParaTlrQryListDTO;
import com.adtec.comp.ctrl.dto.TParaTlrQryReqDTO;
import com.adtec.comp.ctrl.dto.TParaTlrQryResDTO;
import com.adtec.comp.ctrl.oper.util.CompCtrlUtil;
import com.adtec.comp.ctrl.test.dao.TCtrlTranDao;
import com.adtec.comp.ctrl.test.dto.FCtrlTranFileDTO;
import com.adtec.comp.ctrl.test.dto.FCtrlTranTestReqDTO;
import com.adtec.comp.ctrl.test.dto.FCtrlTranExmAddResDTO;
import com.adtec.comp.ctrl.test.dto.FCtrlTranCaseResDTO;
import com.adtec.comp.ctrl.test.dto.FCtrlTranExmListDTO;
import com.adtec.comp.ctrl.test.dto.FCtrlTranExmReqDTO;
import com.adtec.comp.ctrl.test.dto.FCtrlTranExmResDTO;
import com.adtec.comp.ctrl.test.entity.TCtrlFileMsgDo;
import com.adtec.comp.ctrl.test.util.CtrlFileUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.service.BaseService;
import com.adtec.sys.modules.sys.utils.UserUtils;

@Service
public class FCtrlTranExmService extends BaseService{
	
	@Autowired
	private TCtrlTranDao tranDao;
	
	/**
	 * 柜员查询
	 */
	public IDataset qryTrl(TParaTlrQryReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlParaTlrQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlParaTlrQry", req, TParaTlrQryResDTO.class,null);
		TParaTlrQryResDTO resBody = (TParaTlrQryResDTO) res.getBODY();
		List<TParaTlrQryListDTO> tlist = resBody.getTLR_LIST();
		if(tlist!=null){
			for(int i=0;i<tlist.size();i++){
				String TLR_NO = tlist.get(i).getTLR_NO();
				String TLR_NAME = tlist.get(i).getTLR_NAME();
				String TLR_LVL = tlist.get(i).getTLR_LVL();
				String BRCH = tlist.get(i).getBRCH();
				String outStr = TLR_NO+"-"+TLR_NAME+"-"+TLR_LVL+"-"+BRCH;
				tlist.get(i).setOUTSTR(outStr);
			}
		}
		responseData = DatasetService.getInstace().getDataset(tlist, TParaTlrQryListDTO.class);
		return responseData;
	}
	
	/**
	 * 测试案例
	 */
	public IDataset testExm(FCtrlTranExmReqDTO reqBody,FCtrlTranTestReqDTO testReq){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlTranExm");
		/*重新赋值系统头里的 组件号、服务码*/
		req.getSYS_HEAD().setREQ_COMP_NO(testReq.getCOMP_NO());
		req.getSYS_HEAD().setREQ_SVC_CODE(testReq.getSVC_CODE());
		/*重新赋值 授权柜员*/
		List<AppHeadAUListReqDTO> tlrList = testReq.getTlrList();
		if(tlrList!=null&&tlrList.size()>0){
			req.getAPP_HEAD().setAUTH_TLR(tlrList);
		}
		
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlTranExm", req, FCtrlTranExmResDTO.class,null);
		FCtrlTranExmResDTO resBody = (FCtrlTranExmResDTO) res.getBODY();
		List<FCtrlTranExmListDTO> tlist = resBody.getLIST();
		/*系统头返回的信息*/
		List<SysHeadRetResDTO> retList = res.getSYS_HEAD().getTRAN_RET();
		//交易状态:S-交易成功、F-交易失败、T-交易超时
		String TRAN_STAT = res.getSYS_HEAD().getTRAN_STAT();
		String code = "";
		if(tlist!=null&&tlist.size()>0){
			if("S".equals(TRAN_STAT)){
				String resCode = retList.get(0).getRET_CODE();
				resCode = (String) (resCode!=null&&resCode!="" ? resCode.substring(0,2) : "01");
				if("00".equals(resCode)){
					code = "通过";
				}else{
					code = "未通过";
				}
			}else if("F".equals(TRAN_STAT)){
				code = "未通过";
			}else if("T".equals(TRAN_STAT)){
				code = "超时";
			}
			tlist.get(0).setRES_CODE(code);
			tlist.get(0).setRES_MSG(retList.get(0).getRET_MSG());
		}
		responseData = DatasetService.getInstace().getDataset(tlist, FCtrlTranExmListDTO.class);
		return responseData;
	}
	
	/**
	 * 查询案例()
	 */
	public IDataset qry(TCtrlFileMsgDo reqBody,int start,int limit){
		IDataset responseData = DatasetService.getInstace().getDataset();
		String userName = UserUtils.getUser().getLoginName();
		reqBody.setUserName(userName);
		List<TCtrlFileMsgDo> list = new ArrayList<TCtrlFileMsgDo>();
		list = tranDao.list(reqBody, start, limit);
		List<FCtrlTranCaseResDTO> flist = new ArrayList<FCtrlTranCaseResDTO>();
		if(list!=null){
			for(int i=0;i<list.size();i++){
				FCtrlTranCaseResDTO fdto = new FCtrlTranCaseResDTO();
				fdto.setORDER_NO(i+1);
				String caseNo = list.get(i).getCaseNo();
				String caseName = list.get(i).getCaseName();
				fdto.setCASENAME(caseName);
				String action = "<a href=\"JavaScript:void(0);\" onClick=\"getAction('" +caseNo+","+caseName+","+"ctrl"+"')\">获取</a>";
				fdto.setACTION(action);
				flist.add(fdto);
			}
		}
		responseData = DatasetService.getInstace().getDataset(flist,FCtrlTranCaseResDTO.class);
		responseData.setTotalCount(flist.size());
		return responseData;
	}
	
	/**
	 * 删除案例
	 */
	public int del(TCtrlFileMsgDo reqBody){
		String userName = UserUtils.getUser().getLoginName();
		reqBody.setUserName(userName);
		int rs = 0;
		if(reqBody!=null){
			rs = tranDao.delete(reqBody);
		}
		return rs;
	}
	
	/**
	 * 保存案例：(实际上就是更新文件内容)
	 * @param reqBody
	 * @return
	 */
	public IDataset save(FCtrlTranFileDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		FCtrlTranFileDTO addDto = (FCtrlTranFileDTO) req.getBODY();
		//获取要更新数据的文件
		String caseNo = addDto.getCaseNo();
		String caseName = addDto.getCaseName();
		String flg = "ctrl";
		String userName = UserUtils.getUser().getLoginName();
		TCtrlFileMsgDo tdo = new TCtrlFileMsgDo();
		tdo.setCaseNo(caseNo);
		tdo.setCaseName(caseName);
		tdo.setFlg(flg);
		tdo.setUserName(userName);
		//获取 保存的案例的信息
		TCtrlFileMsgDo objDo = tranDao.get(tdo);
		String fileName = objDo.getFileName();
		String filePath = objDo.getFilePath();
		reqBody.setFileName(fileName);
		reqBody.setFilePath(filePath);
		reqBody.setFlg("ctrl");
		CtrlFileUtil.saveFile(reqBody);
		responseData = DatasetService.getInstace().getDataset(addDto,FCtrlTranExmAddResDTO.class);
		return responseData;
	}
	
	/**
	 * 另存为案例 实现(保存页面数据到文件  保存文件信息到数据库)
	 */
	public IDataset saveNew(FCtrlTranFileDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		//先根据 输入的案例名 查询数据库是否已存在
		TCtrlFileMsgDo tdo = new TCtrlFileMsgDo();
		String userName = UserUtils.getUser().getLoginName();
		tdo.setCaseName(reqBody.getCaseName());
		tdo.setFlg("ctrl");
		tdo.setUserName(userName);
		TCtrlFileMsgDo tdo1 = tranDao.get(tdo);
		/**
		 * 1.案例已存在
		 * 2.案例不存在
		 */
		if(tdo1!=null){
			/*更新文件数据*/
			String fileName = tdo1.getFileName();
			String filePath = tdo1.getFilePath();
			reqBody.setFileName(fileName);
			reqBody.setFilePath(filePath);
			reqBody.setFlg("ctrl");
			CtrlFileUtil.saveFile(reqBody);
			
			/*更新数据库数据*/
			String caseNo = tdo1.getCaseNo();
			String caseName = tdo1.getCaseName();
			TCtrlFileMsgDo objDo = new TCtrlFileMsgDo();
			objDo.setCaseNo(caseNo);
			objDo.setCaseName(caseName);
			objDo.setFlg("ctrl");
			objDo.setUserName(userName);
			int rs = tranDao.update(objDo);
			if(rs!=0){
				logger.debug("更新成功");
			}
		}else{
			/*数据库中不存在案例 则添加*/
			int nextId = tranDao.getNextId("ctrl");
			//设置案例编号
			String caseNo = "c"+(nextId+1);
			String caseName = reqBody.getCaseName();
			//生成文件命名(ctrl_+caseNo.txt)
			String filePath = CtrlFileUtil.getSavePath("CtrlTest"); 
			String fileName = "ctrl_"+caseNo+".txt";
			reqBody.setFileName(fileName);
			reqBody.setFilePath(filePath);
			reqBody.setFlg("ctrl");
			CtrlFileUtil.saveFile(reqBody);
			
			//添加文件信息到数据库
			TCtrlFileMsgDo objDo = new TCtrlFileMsgDo();
			objDo.setCaseNo(caseNo);
			objDo.setCaseName(caseName);
			objDo.setFileName(fileName);
			objDo.setFilePath(filePath);
			objDo.setFlg("ctrl");
			objDo.setUserName(userName);
			int rs = tranDao.insert(objDo);
			if(rs!=0){
				logger.debug("添加成功");
			}
		}
		responseData = DatasetService.getInstace().getDataset(reqBody,FCtrlTranExmAddResDTO.class);
		return responseData;
	}
	
	/**
	 * 获取案例对应的文件
	 * @param 
	 */
	public FCtrlTranFileDTO getFileDTO(TCtrlFileMsgDo reqBody){
		String userName = UserUtils.getUser().getLoginName();
		reqBody.setUserName(userName);
		TCtrlFileMsgDo rs = tranDao.get(reqBody);
		FCtrlTranFileDTO resBody = new FCtrlTranFileDTO();
		if(rs!=null){
			String fileName = rs.getFileName();
			String filePath = rs.getFilePath();
			String file = filePath+"/"+fileName;
			resBody = CtrlFileUtil.getFile(file);
			resBody.setFileName(rs.getFileName());
		}
		return resBody;
	}
	
	/**
	 * 查询 私有维度或公用维度(0-)
	 */
	public IDataset qryDim(FCtrlMngDimQryReqDTO reqBody){
		IDataset responseData = DatasetService.getInstace().getDataset();
		ReqDTO req = new ReqDTO(reqBody);
		CompCtrlUtil.setReqHead(req, "FCtrlMngDimQry");
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO res = (ResDTO) httpJsonFactory.callService("",ParamUtil.getConfig("CTRL_PARTID"),"FCtrlMngDimQry", req, FCtrlMngDimQryResDTO.class,null);
		FCtrlMngDimQryResDTO resBody = (FCtrlMngDimQryResDTO) res.getBODY();
		List<FCtrlMngDimQryListDTO> flist = resBody.getDIM_LIST();
		List<FCtrlMngDimQryListDTO> pubList = new ArrayList<FCtrlMngDimQryListDTO>();
		List<FCtrlMngDimQryListDTO> priList = new ArrayList<FCtrlMngDimQryListDTO>();
		if(flist!=null&&flist.size()>0){
			for(int i=0;i<flist.size();i++){
				if("0".equals(flist.get(i).getCOMP_NO())){
					pubList.add(flist.get(i));
				}else{
					priList.add(flist.get(i));
				}
			}
		}
		if("0".equals(reqBody.getCOMP_NO())){
			responseData = DatasetService.getInstace().getDataset(pubList, FCtrlMngDimQryListDTO.class);
		}else{
			responseData = DatasetService.getInstace().getDataset(priList, FCtrlMngDimQryListDTO.class);
		}
		
		return responseData;
		
	}
	
}

