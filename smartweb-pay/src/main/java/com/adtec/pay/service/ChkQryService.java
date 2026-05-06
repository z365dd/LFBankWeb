package com.adtec.pay.service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.pay.dto.check.ChkQryReq;
import com.adtec.pay.dto.check.ChkQryRes;
import com.adtec.pay.utils.MLppUtils;
import org.springframework.stereotype.Service;

/**
 * 对账服务层
 */
@Service
public class ChkQryService {


    /**
     * 对账查询
     *
     * @param req
     * @param start
     * @param limit
     * @return
     */
    public ChkQryRes getChkList(ChkQryReq req, int start, int limit) {
        String busiNo = req.getBUSI_NO();
        ReqDTO reqDTO = new ReqDTO(req);
        MLppUtils.setReqHead(busiNo, reqDTO, "MLppChkQry", start, limit);
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        // 和Starring V6进行通信
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("MLPP_PARTID"), "MLppChkQry",
                reqDTO, ChkQryRes.class, null);
        ChkQryRes chkQryRes = (ChkQryRes) resDTO.getBODY();
        return chkQryRes;
    }


}
