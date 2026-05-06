package com.adtec.comp.sign.util;

import java.util.ArrayList;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.head.LocalHeadLimList;
import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.sys.common.utils.SeqUtil;
import com.adtec.sys.modules.sys.utils.UserUtils;

public class SignUtil {
	
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
	
		//GPM请求头
		// 系统头--TODO默认使用999000
//		ResourceBundle source = ResourceBundle.getBundle("busiConfig");
//		String modelNo = source.getString("SIGN_ModelNo");
//		req.getSYS_HEAD().setREQ_MODL_NO(modelNo);	//请求组件/模型编号
		
//		String seqNo = SeqUtil.getMBCSeq();
//		req.getSYS_HEAD().setREQ_SEQ_NO(DateUtil.getDate() + seqNo); // 服务请求发送方流水号
//		req.getSYS_HEAD().setSEQ_NO(DateUtil.getDate() + seqNo);//内部全局流水号
//		req.getSYS_HEAD().setREQ_DATE(DateUtil.getDate());
//		/*修改*/
//		req.getSYS_HEAD().setREQ_COMP_NO(modelNo);	//请求组件/模型编号
//		req.getSYS_HEAD().setREQ_SEQ(DateUtil.getDate() + seqNo); // 服务请求发送方流水号
//		req.getSYS_HEAD().setSND_SEQ(DateUtil.getDate() + seqNo);//内部全局流水号
//		//TODO:根据发起段设置
//		req.getSYS_HEAD().setCHNL_NO("300008");	
//	
//		// 应用头
//		req.getAPP_HEAD().setBRCH(UserUtils.getUser().getOffice().getCode());
//		req.getAPP_HEAD().setTLR_NO(UserUtils.getUser().getLoginName());
//		req.getAPP_HEAD().setBGN_REC_NO(String.valueOf(start)); // 开始记录数
//		req.getAPP_HEAD().setREQ_REC_NUM(pageSize); // 要求每页返回记录总数
//		req.getAPP_HEAD().setTX_DATE(DateUtil.getDate());//交易日期
//		req.getAPP_HEAD().setTX_TIME(DateUtil.getTime());//交易时间
//		/*修改*/
//		req.getAPP_HEAD().setTRAN_DATE(DateUtil.getDate());//交易日期
//		req.getAPP_HEAD().setTRAN_TIME(DateUtil.getTime());//交易时间
//		
//		// 本地扩展头
//		req.getLOCAL_HEAD().setCHNL_NO("000001");
//		req.getLOCAL_HEAD().setBUSI_NO(busiNo);
//		req.getLOCAL_HEAD().setLEGA_NO(UserUtils.getUser().getRent().getLawNo());
//		req.getLOCAL_HEAD().setCHNL_SEQ_NO(DateUtil.getDate() + seqNo);
//		//req.getLOCAL_HEAD().setENTR_NO("");
		
		
		String seqNo = SeqUtil.getMBCSeq();
		req.getSYS_HEAD().setREQ_SEQ_NO(DateUtil.getDate() + seqNo); // 服务请求发送方流水号
		//req.getSYS_HEAD().setREQ_MODL_NO(modelNo);	//请求组件/模型编号
		//req.getSYS_HEAD().setSEQ_NO(DateUtil.getDate() + seqNo);//内部全局流水号
		//req.getSYS_HEAD().setREQ_DATE(DateUtil.getDate());
		/*修改*/
//		req.getSYS_HEAD().setREQ_COMP_NO(modelNo);	//请求组件/模型编号
		//req.getSYS_HEAD().setREQ_SEQ(DateUtil.getDate() + seqNo); // 服务请求发送方流水号
		//req.getSYS_HEAD().setSND_SEQ(DateUtil.getDate() + seqNo);//内部全局流水号
		//req.getSYS_HEAD().setREQ_DATE(DateUtil.getDate());	//服务方请求日期
		//2020年2月12日17:23:16
		req.getSYS_HEAD().setCHNL_DATE(DateUtil.getDate());
		req.getSYS_HEAD().setCHNL_TIME(DateUtil.getTime());
		req.getSYS_HEAD().setCHNL_SEQ(DateUtil.getDate()+ seqNo);
		req.getSYS_HEAD().setGLOBAL_BUSI_SEQ(DateUtil.getDate()+ seqNo);
		req.getSYS_HEAD().setGLOBAL_SEQ(DateUtil.getDate()+ seqNo);
		//TODO:根据发起段设置
		req.getSYS_HEAD().setCHNL_NO("000001");
		req.getSYS_HEAD().setREQ_NODE_NO("head");
		req.getSYS_HEAD().setLOG_STEP_NO(DateUtil.getDate()+ seqNo);
		req.getSYS_HEAD().setCHNL_DATE(DateUtil.getDate());
		req.getSYS_HEAD().setMACH_DATE(DateUtil.getDate());
		req.getSYS_HEAD().setMACH_TIME("100000");
		req.getSYS_HEAD().setVER_NO("1.0.0");
		req.getSYS_HEAD().setREQ_IP(ParamUtil.getLocalAddr());
		req.getSYS_HEAD().setREQ_SVC_CODE(svcCode);
		
		
		// 应用头
		//req.getAPP_HEAD().setTX_DATE(DateUtil.getDate());//交易日期
		//req.getAPP_HEAD().setTX_TIME(DateUtil.getTime());//交易时间
		//req.getAPP_HEAD().setBGN_REC_NO(String.valueOf(start)); // 开始记录数
		//req.getAPP_HEAD().setREQ_REC_NUM(String.valueOf(pageSize)); // 要求每页返回记录总数
		
		/*修改*/
		//req.getAPP_HEAD().setTRAN_DATE(DateUtil.getDate());//交易日期
		//req.getAPP_HEAD().setTRAN_TIME(DateUtil.getTime());//交易时间
		//2020年2月12日21:17:08
		req.getAPP_HEAD().setSCENE_NO("");//场景号 (非必输)
		req.getAPP_HEAD().setBRCH(UserUtils.getUser().getOffice().getBrchCode());//机构号
		req.getAPP_HEAD().setTLR_NO(UserUtils.getUser().getLoginName());//柜员号
		req.getAPP_HEAD().setREQ_DATE(DateUtil.getDate());//流水日期
		req.getAPP_HEAD().setREQ_SEQ(DateUtil.getDate()+ seqNo);//流水日期
		req.getAPP_HEAD().setCLR_DATE("");//清算日期(非必输)
		//req.getAPP_HEAD().setAUTH_TLR();//柜员列表(非必输)
		req.getAPP_HEAD().setCHK_NO("");//对账分类编号
		req.getAPP_HEAD().setORIG_CLR_DATE("");//冲正原清算日期(非必输)
		req.getAPP_HEAD().setORIG_TRAN_DATE("");//需冲正的原交易日期(非必输)
		req.getAPP_HEAD().setORIG_TRAN_SEQ("");//需冲正的原交易流水号(非必输)
		req.getAPP_HEAD().setTERM_NO("");//交易终端设备号(非必输)
		req.getAPP_HEAD().setMAC_NODE_NO("");//MAC节点号(非必输)
		req.getAPP_HEAD().setPIN_NODE_NO("");//PIN节点号(非必输)
		//req.getAPP_HEAD().setSTR_REC_SER(0);
		//req.getAPP_HEAD().setREQ_REC_NUM(0);//要求每页返回记录总数(非必输)
		
		
		// 本地扩展头
		//req.getLOCAL_HEAD().setSYS(seqNo.substring(seqNo.length()-10));
		//req.getLOCAL_HEAD().setCHNL_NO("000001");
		req.getLOCAL_HEAD().setBUSI_NO(busiNo);
		//req.getLOCAL_HEAD().setENTR_NO(entrNo);
		req.getLOCAL_HEAD().setLEGA_NO(UserUtils.getLawNo());//法人编号
		//req.getLOCAL_HEAD().setCHNL_SEQ_NO(DateUtil.getDate() + seqNo);
		req.getLOCAL_HEAD().setCLR_BRCH("");//清算机构 (非必输)
		req.getLOCAL_HEAD().setTNT_NO(UserUtils.getUser().getRent().getEngName());
		req.getLOCAL_HEAD().setREPT_SND_FLG("");//重发标志("非必输")
		req.getLOCAL_HEAD().setSIGN_PROT_TP_NO("");//签约协议类型编号(非必输)
		req.getLOCAL_HEAD().setLIM_LIST(new ArrayList<LocalHeadLimList>());//限额LIST
	}
	
}
