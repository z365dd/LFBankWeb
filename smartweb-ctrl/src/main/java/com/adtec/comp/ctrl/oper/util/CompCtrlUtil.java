package com.adtec.comp.ctrl.oper.util;


import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.ctrl.dto.CtrlRedisRefreshReqDTO;
import com.adtec.comp.ctrl.dto.FCtrlTranCommonResDTO;
import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.sys.common.utils.SeqUtil;
import com.adtec.sys.modules.sys.utils.UserUtils;

public class CompCtrlUtil {
	
	public static final String TABLE_PARA_TNT = "ParaTnt";
	public static final String TABLE_PARA_LEGA = "ParaLega";
	public static final String TABLE_PARA_RELAT_SYS = "ParaRelatSys";
	public static final String TABLE_PARA_CHNL = "ParaChnl";
	public static final String TABLE_PARA_BRCH = "ParaBrch";
	public static final String TABLE_PARA_TLR = "ParaTlr";
	public static final String TABLE_PARA_BRCH_OPEN = "PipBusiBrchOpen";
	public static final String TABLE_PARA_CHNL_OPEN = "PipBusiChnlOpen";

   /**
    * 参数刷新操作类型
    */
   public static final String OPER_TP_TABLE_ALL = "4"; // 4：按缓存类型（表)全量刷新
	
	/**
	 * 参数刷新-按表名全量刷新（不抛异常）
	 * @param reqBody
	 * @return
	 */
	public static void redisRefreshByTableName(String paraName){
		try {
			CtrlRedisRefreshReqDTO ctrlRedisRefreshReqDTO = new CtrlRedisRefreshReqDTO();
			 ctrlRedisRefreshReqDTO.setPARA_NAME(paraName);
			 ctrlRedisRefreshReqDTO.setOPER_TP(OPER_TP_TABLE_ALL);
			ReqDTO req = new ReqDTO(ctrlRedisRefreshReqDTO);
			setReqHead(req, "FCtrlTranRedisRefresh");
			HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
			httpJsonFactory.callService("", ParamUtil.getConfig("CTRL_PARTID"),
					"FCtrlTranRedisRefresh", req, FCtrlTranCommonResDTO.class, null);
		} catch (Exception e) {
			System.out.println("参数刷新失败");
		}
			
	}
	
	
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
		/*ResourceBundle source = ResourceBundle.getBundle("busiConfig");
		String modelNo = source.getString("CTRL_ModelNo");*/
		String modelNo = ParamUtil.getConfig("CTRL_PARTID");
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
