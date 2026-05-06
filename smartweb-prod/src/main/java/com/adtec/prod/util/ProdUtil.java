package com.adtec.prod.util;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.sys.common.utils.SeqUtil;
import com.adtec.sys.modules.sys.utils.UserUtils;

public class ProdUtil {
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
	 * 设置请求头，根据不同的组件号，业务编号
	 * @param req
	 * @param svcCode
	 */
	public static void setReqHead(ReqDTO req,String svcCode,String compNo,String busiNo){
		setReqHead(null, req, svcCode, 0, 0);
		req.getSYS_HEAD().setREQ_COMP_NO(compNo);	//请求组件/模型编号
		req.getSYS_HEAD().setREQ_MODL_NO(compNo);	//请求组件/模型编号
		
		req.getLOCAL_HEAD().setBUSI_NO(busiNo);     //本地头，业务编号
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
	
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		
		//公共请求头
		httpJsonFactory.buildReqHead(svcCode, busiNo, start, pageSize);
		
		//GPM请求头
		// 系统头--TODO默认使用999000
		/*ResourceBundle source = ResourceBundle.getBundle("busiConfig");
		String modelNo = source.getString("PROD_ModelNo");*/
		String modelNo = ParamUtil.getConfig("PROD_PARTID");
		
		req.getSYS_HEAD().setREQ_MODL_NO(modelNo);	//请求组件/模型编号
		String seqNo = SeqUtil.getMBCSeq();
		req.getSYS_HEAD().setREQ_SEQ_NO(DateUtil.getDate() + seqNo); // 服务请求发送方流水号
		req.getSYS_HEAD().setSEQ_NO(DateUtil.getDate() + seqNo);//内部全局流水号
		req.getSYS_HEAD().setREQ_DATE(DateUtil.getDate());
		/*修改*/
		req.getSYS_HEAD().setREQ_COMP_NO(modelNo);	//请求组件/模型编号
		req.getSYS_HEAD().setREQ_SEQ(DateUtil.getDate() + seqNo); // 服务请求发送方流水号
		req.getSYS_HEAD().setSND_SEQ(DateUtil.getDate() + seqNo);//内部全局流水号
		
		req.getSYS_HEAD().setREQ_NODE_NO(modelNo);
		// 20200331
		req.getSYS_HEAD().setCHNL_NO("000001");
		req.getSYS_HEAD().setCHNL_SEQ(DateUtil.getDate() + seqNo);
		
		// 应用头
		req.getAPP_HEAD().setBRCH(UserUtils.getUser().getOffice().getBrchCode());
		req.getAPP_HEAD().setTLR_NO(UserUtils.getUser().getLoginName());
		req.getAPP_HEAD().setTX_DATE(DateUtil.getDate());//交易日期
		req.getAPP_HEAD().setTX_TIME(DateUtil.getTime());//交易时间
		/*修改*/
		req.getAPP_HEAD().setTRAN_DATE(DateUtil.getDate());//交易日期
		req.getAPP_HEAD().setTRAN_TIME(DateUtil.getTime());//交易时间
		req.getAPP_HEAD().setREQ_DATE(DateUtil.getDate());//交易日期
		
		// 本地扩展头
		
		req.getLOCAL_HEAD().setBUSI_NO(busiNo);
		req.getLOCAL_HEAD().setLEGA_NO(UserUtils.getUser().getCorporation().getLegaNo());
		
		//req.getLOCAL_HEAD().setENTR_NO("");
		//租户
		req.getLOCAL_HEAD().setTNT_NO(UserUtils.getUser().getRent().getEngName());
	}
	
	/*日期加分隔符*/
	public static String addDate(String Date) {
		if (null != Date && !"".equals(Date)) {
			return Date.substring(0, 4) + "-" + Date.substring(4, 6) + "-" + Date.substring(6, 8);
		} else {
			return Date;
		}

	}
	
	/**
	 * 转金额格式
	 */
	public static String changeMoney(String num){
		if(num.indexOf(".")==-1){
	   	 	//没有.为整数
			num += ".00"; 
		}else{
			num += "00"; 
		}
		return num.substring(0, num.indexOf(".")+3);
	}
	
}
