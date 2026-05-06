package com.adtec.pay.service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.pay.dto.signbat.SignBatQryReq;
import com.adtec.pay.dto.signbat.SignBatQryRes;
import com.adtec.pay.utils.MLppUtils;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class SignBatQryService {

    public SignBatQryRes callSignBatQry(IDataset reqDs, SignBatQryReq signBatQryReq, int start, int limit) {
        String busiNo = signBatQryReq.getLIST().get(0).getOTH_ENTR_NO();
        ReqDTO reqDTO = new ReqDTO(signBatQryReq);
        MLppUtils.setReqHead(busiNo, reqDTO, "FSignFuncSignQryBat", start, limit);
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("SIGN_PARTID"), "FSignFuncSignQryBat", reqDTO, SignBatQryRes.class, null);
        SignBatQryRes signBatQryRes = (SignBatQryRes) resDTO.getBODY();
        return signBatQryRes;
    }
}
