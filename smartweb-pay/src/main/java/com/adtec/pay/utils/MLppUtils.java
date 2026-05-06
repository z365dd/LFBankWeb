package com.adtec.pay.utils;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.head.LocalHeadLimList;
import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.common.util.StringUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.sys.common.utils.SeqUtil;
import com.adtec.sys.modules.sys.utils.UserUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

public class MLppUtils {
    /**
     * 设置请求头
     *
     * @param busiNo
     * @param req
     * @param svcCode
     * @return
     */
    public static void setReqHead(String busiNo, ReqDTO req, String svcCode) {
        setReqHead(busiNo, req, svcCode, 0, 0);
    }

    /**
     * 设置请求头
     *
     * @param req
     * @param svcCode
     */
    public static void setReqHead(ReqDTO req, String svcCode) {
        setReqHead(null, req, svcCode, 0, 0);
    }


    public static void setReqHead(ReqDTO req, String svcCode,int start, int pageSize){
        setReqHead("", "", req, svcCode, start, pageSize);
    }
    /**
     * 设置请求头
     *
     * @param busiNo
     * @param req
     * @param svcCode
     * @param start
     * @param pageSize
     * @return
     */
    public static void setReqHead(String busiNo, ReqDTO req, String svcCode, int start, int pageSize) {
        if (StringUtil.isEmpty(busiNo)
                && !svcCode.startsWith("FCtrlBusiRule")
                && !svcCode.equals("MLppAcctConfm")
                && !svcCode.equals("MLppErrListQry")
                && !svcCode.equals("MLppMerRecList")) {
            throw new BaseException(SysErr.E_MESSAGE, "该机构没有对应的合作商户，请使用合作商户对应的开户机构或上级机构用户进行查看");
        }
        setReqHead(busiNo, "", req, svcCode, start, pageSize);
    }

    /**
     * 设置请求头
     *
     * @param busiNo
     * @param entrNo
     * @param req
     * @param svcCode
     * @param start
     * @param pageSize
     * @return
     */
    public static void setReqHead(String busiNo, String entrNo, ReqDTO req, String svcCode, int start, int pageSize) {

		/*HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();

		//公共请求头
		httpJsonFactory.buildReqHead(svcCode, busiNo, start, pageSize);*/

        //GPM请求头
        // 系统头--TODO默认使用999000
		/*ResourceBundle source = ResourceBundle.getBundle("busiConfig");
		String modelNo = source.getString("GPM_ModelNo");*/
//		String modelNo = ParamUtil.getConfig("GPM_PARTID");
//		//req.getSYS_HEAD().setREQ_MODL_NO(modelNo);	//请求组件/模型编号
//		String seqNo = SeqUtil.getMBCSeq();
//		req.getSYS_HEAD().setREQ_SEQ_NO(DateUtil.getDate() + seqNo); // 服务请求发送方流水号
//		//req.getSYS_HEAD().setSEQ_NO(DateUtil.getDate() + seqNo);//内部全局流水号
//		req.getSYS_HEAD().setREQ_DATE(DateUtil.getDate());
//		/*修改*/
//		req.getSYS_HEAD().setREQ_COMP_NO(modelNo);	//请求组件/模型编号
//		//req.getSYS_HEAD().setREQ_SEQ(DateUtil.getDate() + seqNo); // 服务请求发送方流水号
//		//req.getSYS_HEAD().setSND_SEQ(DateUtil.getDate() + seqNo);//内部全局流水号
//		//req.getSYS_HEAD().setREQ_DATE(DateUtil.getDate());	//服务方请求日期
//		//2020年2月12日17:23:16
//		req.getSYS_HEAD().setCHNL_DATE(DateUtil.getDate());
//		req.getSYS_HEAD().setCHNL_SEQ(DateUtil.getDate()+ seqNo);
//		req.getSYS_HEAD().setGLOBAL_SEQ(DateUtil.getDate()+ seqNo);
//		req.getSYS_HEAD().setCHNL_NO("000001");
//		req.getSYS_HEAD().setREQ_NODE_NO("head");
//		req.getSYS_HEAD().setLOG_STEP_NO(DateUtil.getDate()+ seqNo);
//		req.getSYS_HEAD().setCHNL_DATE(DateUtil.getDate());
//		req.getSYS_HEAD().setMACH_DATE(DateUtil.getDate());
//		req.getSYS_HEAD().setMACH_TIME("100000");
//		req.getSYS_HEAD().setVER_NO("1.0.0");
//		req.getSYS_HEAD().setREQ_IP(ParamUtil.getLocalAddr());
//
//		// 应用头
//		req.getAPP_HEAD().setBRCH(UserUtils.getUser().getOffice().getCode());
//		//req.getAPP_HEAD().setTLR_NO(UserUtils.getUser().getLoginName());
//		//req.getAPP_HEAD().setTX_DATE(DateUtil.getDate());//交易日期
//		//req.getAPP_HEAD().setTX_TIME(DateUtil.getTime());//交易时间
//		req.getAPP_HEAD().setBGN_REC_NO(String.valueOf(start)); // 开始记录数
//		req.getAPP_HEAD().setREQ_REC_NUM(String.valueOf(pageSize)); // 要求每页返回记录总数
//
//		/*修改*/
//		//req.getAPP_HEAD().setTRAN_DATE(DateUtil.getDate());//交易日期
//		//req.getAPP_HEAD().setTRAN_TIME(DateUtil.getTime());//交易时间
//		//2020年2月12日21:17:08
//		req.getAPP_HEAD().setREQ_DATE(DateUtil.getDate());//流水日期
//		req.getAPP_HEAD().setREQ_SEQ(DateUtil.getDate()+ seqNo);//流水日期
//
//		// 本地扩展头
//		req.getLOCAL_HEAD().setSYS(seqNo.substring(seqNo.length()-10));
//		//req.getLOCAL_HEAD().setCHNL_NO("000001");
//		req.getLOCAL_HEAD().setBUSI_NO(busiNo);
//		req.getLOCAL_HEAD().setENTR_NO(entrNo);
//		req.getLOCAL_HEAD().setLEGA_NO(UserUtils.getUser().getRent().getLawNo());//法人编号
//		req.getLOCAL_HEAD().setCHNL_SEQ_NO(DateUtil.getDate() + seqNo);
        String seqNo = SeqUtil.getMBCSeq();
        req.getSYS_HEAD().setREQ_SEQ_NO(DateUtil.getDate() + seqNo); // 服务请求发送方流水号
        //req.getSYS_HEAD().setREQ_MODL_NO(modelNo);	//请求组件/模型编号
        //req.getSYS_HEAD().setSEQ_NO(DateUtil.getDate() + seqNo);//内部全局流水号
        //req.getSYS_HEAD().setREQ_DATE(DateUtil.getDate());
        /*修改*/
        //req.getSYS_HEAD().setREQ_COMP_NO(modelNo);	//请求组件/模型编号
        //req.getSYS_HEAD().setREQ_SEQ(DateUtil.getDate() + seqNo); // 服务请求发送方流水号
        //req.getSYS_HEAD().setSND_SEQ(DateUtil.getDate() + seqNo);//内部全局流水号
        //req.getSYS_HEAD().setREQ_DATE(DateUtil.getDate());	//服务方请求日期
        //2020年2月12日17:23:16
        req.getSYS_HEAD().setCHNL_DATE(DateUtil.getDate());
        req.getSYS_HEAD().setCHNL_TIME(DateUtil.getTime());
        req.getSYS_HEAD().setCHNL_SEQ(DateUtil.getDate() + seqNo);
        req.getSYS_HEAD().setGLOBAL_BUSI_SEQ(DateUtil.getDate() + seqNo);
        req.getSYS_HEAD().setGLOBAL_SEQ(DateUtil.getDate() + seqNo);
        //这里设置生活缴费平台
        req.getSYS_HEAD().setCHNL_NO("160");
        req.getSYS_HEAD().setREQ_NODE_NO("head");
        req.getSYS_HEAD().setLOG_STEP_NO(DateUtil.getDate() + seqNo);
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
        //机构号 当前登录用户的机构号
        req.getAPP_HEAD().setBRCH(UserUtils.getUser().getOffice().getBrchCode());
        req.getAPP_HEAD().setTLR_NO(UserUtils.getUser().getLoginName());//柜员号
        req.getAPP_HEAD().setREQ_DATE(DateUtil.getDate());//流水日期
        req.getAPP_HEAD().setREQ_SEQ(DateUtil.getDate() + seqNo);//流水日期
        req.getAPP_HEAD().setCLR_DATE("");//清算日期(非必输)
        //req.getAPP_HEAD().setAUTH_TLR();//柜员列表(非必输)
        req.getAPP_HEAD().setCHK_NO("");//对账分类编号
        req.getAPP_HEAD().setORIG_CLR_DATE("");//冲正原清算日期(非必输)
        req.getAPP_HEAD().setORIG_TRAN_DATE("");//需冲正的原交易日期(非必输)
        req.getAPP_HEAD().setORIG_TRAN_SEQ("");//需冲正的原交易流水号(非必输)
        req.getAPP_HEAD().setTERM_NO("");//交易终端设备号(非必输)
        req.getAPP_HEAD().setMAC_NODE_NO("");//MAC节点号(非必输)
        req.getAPP_HEAD().setPIN_NODE_NO("");//PIN节点号(非必输)
        req.getAPP_HEAD().setSTR_REC_SER(start);
        //要求每页返回记录总数(非必输)
        req.getAPP_HEAD().setREQ_REC_NUM(pageSize);


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

    /**
     * 时间间隔校验 开始时间和结束时间间隔是否超过一个月
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean dateCheck(String startTime, String endTime) {
        LocalDate startLocalDate = null;
        LocalDate endLocalDate;
        try {
            startLocalDate = LocalDate.parse(startTime, ComUtils.yyyyMMdd);
            endLocalDate = LocalDate.parse(endTime, ComUtils.yyyyMMdd);
        } catch (Exception e) {
            throw new BaseException(SysErr.E_MESSAGE, "日期格式错误");
        }
        long duration = Duration.between(startLocalDate.atTime(0, 0, 0), endLocalDate.atTime(23, 59, 59)).toDays();
        if (duration > 31) {
            throw new BaseException(SysErr.E_MESSAGE, "开始时间与结束时间的时间间隔不能超过一个月");
        }
        return true;
    }

}
