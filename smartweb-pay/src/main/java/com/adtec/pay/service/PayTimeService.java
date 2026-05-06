package com.adtec.pay.service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.pay.dto.paytime.*;
import com.adtec.pay.utils.MLppUtils;
import com.adtec.pay.webResp.ComRespBody;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayTimeService {

    /**
     * @param mert_no
     * @param start
     * @param limit
     * @return
     */
    public ComRespBody<List<PayTimeQryListRes>> callPayTimeQry(String mert_no, int start, int limit) {
        PayTimeQryReq req = new PayTimeQryReq();
        //将请求加入到 请求报文的报文体
        req.setMERT_NO(mert_no);
        ReqDTO reqDTO = new ReqDTO(req);
        //注意  mert_no不是 busiNo
        MLppUtils.setReqHead(null, reqDTO, "FCtrlBusiRuleQry", start, limit);
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        //和Starring V6进行通信
        ResDTO resDTO = (ResDTO) httpJsonFactory.
                callService("", ParamUtil.getConfig("FCTRL_PARTID"), "FCtrlBusiRuleQry", reqDTO, PayTimeQryRes.class, null);
        PayTimeQryRes payTimeResDTO = (PayTimeQryRes) resDTO.getBODY();
        int total = payTimeResDTO.getREC_NUM();
        List<PayTimeQryListRes> payTimeLists = payTimeResDTO.getLIST();
        //分页操作
//        payTimeLists = payTimeLists.stream().skip((start - 1) * limit).limit(limit).collect(Collectors.toList());
        return new ComRespBody<>(start, limit, payTimeLists, total);
    }


    /**
     * @param modifyReqDTO 请求报文参数
     */
    public IDataset callPayTimeModify(PayTimeModifyReq modifyReqDTO) {
        ReqDTO reqDto = new ReqDTO(modifyReqDTO);
        MLppUtils.setReqHead(reqDto, "FCtrlBusiRuleMod");
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("FCTRL_PARTID"), "FCtrlBusiRuleMod", reqDto, PayTimeModifyRes.class, null);
        PayTimeModifyRes resBody = new PayTimeModifyRes();
        if (resDTO.getBODY() != null) {
            resBody = (PayTimeModifyRes) resDTO.getBODY();
        }
        IDataset responseData = DatasetService.getInstace().getDataset(resBody, PayTimeModifyRes.class);
        return responseData;
    }

}
