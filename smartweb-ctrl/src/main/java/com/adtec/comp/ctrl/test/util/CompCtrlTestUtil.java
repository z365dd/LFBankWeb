package com.adtec.comp.ctrl.test.util;

import java.util.ResourceBundle;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comp.ctrl.test.dto.FCtrlTranExmReqDTO;
import com.adtec.framework.common.util.DateUtil;
import com.adtec.sys.common.utils.SeqUtil;
import com.adtec.sys.modules.sys.utils.UserUtils;

public class CompCtrlTestUtil {
	/**
	 * 设置请求头
	 * @param busiNo
	 * @param req
	 * @param svcCode
	 * @return
	 */
	public static void setReqHead(String busiNo,ReqDTO req,String svcCode){
		setReqHead(busiNo, req, svcCode, 0, 0);
	}
	
	/**
	 * 设置请求头
	 * @param req
	 * @param svcCode
	 */
	public static void setReqHead(ReqDTO req,String svcCode){
		setReqHead(null, req, svcCode, 0, 0);
	}
	
	/**
	 * 设置请求头
	 * @param busiNo
	 * @param req
	 * @param svcCode
	 * @param start
	 * @param pageSize
	 * @return
	 */
	public static void setReqHead(String busiNo,ReqDTO req,String svcCode, int start,int pageSize){
		String objName = req.getBODY().getClass().getName();
		FCtrlTranExmReqDTO reqBody = (FCtrlTranExmReqDTO) req.getBODY();
		/*JSONArray jsonArr = JSONArray.parseArray();
		for(int i=0;i<jsonArr.size();i++){
		}*/
		//GPM请求头
		// 系统头--TODO默认使用999000
		ResourceBundle source = ResourceBundle.getBundle("busiConfig");
		String modelNo = source.getString("CTRL_ModelNo");
		req.getSYS_HEAD().setREQ_MODL_NO(modelNo);	//请求组件/模型编号
		String seqNo = SeqUtil.getMBCSeq();
		req.getSYS_HEAD().setREQ_SEQ_NO(DateUtil.getDate() + seqNo); // 服务请求发送方流水号
		req.getSYS_HEAD().setSEQ_NO(DateUtil.getDate() + seqNo);//内部全局流水号
		req.getSYS_HEAD().setREQ_DATE(DateUtil.getDate());
		req.getSYS_HEAD().setREQ_SVC_CODE(svcCode);//请求服务码
		/*修改*/
		req.getSYS_HEAD().setREQ_COMP_NO(modelNo);	//请求组件/模型编号
		req.getSYS_HEAD().setREQ_SEQ(DateUtil.getDate() + seqNo); // 服务请求发送方流水号
		req.getSYS_HEAD().setSND_SEQ(DateUtil.getDate() + seqNo);//内部全局流水号

		// 应用头
		req.getAPP_HEAD().setBRCH(UserUtils.getUser().getOffice().getBrchCode());
		req.getAPP_HEAD().setTLR_NO(UserUtils.getUser().getLoginName());
		req.getAPP_HEAD().setBGN_REC_NO(String.valueOf(start)); // 开始记录数
		req.getAPP_HEAD().setREQ_REC_NUM(pageSize); // 要求每页返回记录总数
		req.getAPP_HEAD().setTX_DATE(DateUtil.getDate());//交易日期
		req.getAPP_HEAD().setTX_TIME(DateUtil.getTime());//交易时间
		/*修改*/
		req.getAPP_HEAD().setTRAN_DATE(DateUtil.getDate());//交易日期
		req.getAPP_HEAD().setTRAN_TIME(DateUtil.getTime());//交易时间
		
		// 本地扩展头
		req.getLOCAL_HEAD().setCHNL_NO("000001");
		req.getLOCAL_HEAD().setBUSI_NO(busiNo);
		req.getLOCAL_HEAD().setLEGA_NO(UserUtils.getLawNo());
		req.getLOCAL_HEAD().setCHNL_SEQ_NO(DateUtil.getDate() + seqNo);
		//req.getLOCAL_HEAD().setENTR_NO("");
	}
}
