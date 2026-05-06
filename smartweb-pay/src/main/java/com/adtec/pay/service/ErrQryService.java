package com.adtec.pay.service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.pay.dto.clrrule.ClearRuleModifyRes;
import com.adtec.pay.dto.error.ErrHandleReq;
import com.adtec.pay.dto.error.ErrHandleRes;
import com.adtec.pay.dto.error.ErrQryReq;
import com.adtec.pay.dto.error.ErrQryRes;
import com.adtec.pay.utils.MLppUtils;
import org.springframework.stereotype.Component;

@Component
public class ErrQryService {
    /**
     * 对账查询
     *
     * @param req
     * @param start
     * @param limit
     * @return
     */
    public ErrQryRes getErrList(ErrQryReq req, int start, int limit) {
        String busiNo = req.getBUSI_NO();
        ReqDTO reqDTO = new ReqDTO(req);
        MLppUtils.setReqHead(busiNo, reqDTO, "MLppErrListQry", start, limit);
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        // 和Starring V6进行通信
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("MLPP_PARTID"), "MLppErrListQry",
                reqDTO, ErrQryRes.class, null);
        return (ErrQryRes) resDTO.getBODY();
    }

    public IDataset errHandle(ErrHandleReq req) {
        String busiNo = req.getBUSI_NO();
        ReqDTO reqDTO = new ReqDTO(req);
        MLppUtils.setReqHead(busiNo, reqDTO, "MLppChkErrDeal");
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("MLPP_PARTID"), "MLppChkErrDeal",
                reqDTO, ErrHandleRes.class, null);
        ErrHandleRes errHandleRes = new ErrHandleRes();
        IDataset responseData = DatasetService.getInstace().getDataset(errHandleRes, ClearRuleModifyRes.class);
        return responseData;
    }
}
