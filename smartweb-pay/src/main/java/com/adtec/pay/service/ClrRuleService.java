package com.adtec.pay.service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.pay.dto.clrrule.*;
import com.adtec.pay.utils.MLppUtils;
import com.adtec.pay.webResp.ComRespBody;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClrRuleService {

    /**
     * 获取清算规则
     *
     * @param busiNo
     * @param start
     * @param limit
     * @return
     */
    public ComRespBody<ClearRuleQryRes> getClearRule(String busiNo, int start, int limit) {
        ClearRuleQryReq clearRuleQryReq = new ClearRuleQryReq();
        clearRuleQryReq.setBUSI_NO(busiNo);
        ReqDTO reqDTO = new ReqDTO(clearRuleQryReq);
        MLppUtils.setReqHead(busiNo, reqDTO, "FCtrlChkClrRuleQry", start, limit);
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        ResDTO resDTO = (ResDTO) httpJsonFactory.
                callService("", ParamUtil.getConfig("FCTRL_PARTID"), "FCtrlChkClrRuleQry", reqDTO, ClearRuleQryResList.class, null);
        ClearRuleQryResList clearRuleQryResList = (ClearRuleQryResList) resDTO.getBODY();
        if (null != clearRuleQryResList) {
            clearRuleQryResList.setTEMP_ACCT(null == clearRuleQryResList.getTEMP_ACCT() ? "" : clearRuleQryResList.getTEMP_ACCT());
            clearRuleQryResList.setTEMP_ACCT_NAME(null == clearRuleQryResList.getTEMP_ACCT_NAME() ? "" : clearRuleQryResList.getTEMP_ACCT_NAME());
        }
        ClearRuleQryRes clearRuleQryRes = new ClearRuleQryRes();
        //将返回的数据放入到list中 方便前段页面解析
        List<ClearRuleQryResList> clearRuleQryResLists = new ArrayList<ClearRuleQryResList>();
        clearRuleQryResLists.add(clearRuleQryResList);
        clearRuleQryRes.setLIST(clearRuleQryResLists);

        int total = 0;
        if (null != clearRuleQryResList) {
            total = 1;
        }
        return new ComRespBody<>(start, limit, clearRuleQryRes, total);
    }


    public IDataset modifyClearRule(HttpServletRequest req, HttpServletResponse res, ClearRuleModifyReq clearRuleModifyReqDTO) {
        String busiNo = clearRuleModifyReqDTO.getBUSI_NO();
        ReqDTO reqDTO = new ReqDTO(clearRuleModifyReqDTO);
        MLppUtils.setReqHead(busiNo, reqDTO, "FCtrlChkClrRuleMod", 1, 10);
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("FCTRL_PARTID"), "FCtrlChkClrRuleMod", reqDTO, ClearRuleModifyRes.class, null);
        ClearRuleModifyRes resBody = new ClearRuleModifyRes();
        if (resDTO.getBODY() != null) {
            resBody = (ClearRuleModifyRes) resDTO.getBODY();
        }
        IDataset responseData = DatasetService.getInstace().getDataset(resBody, ClearRuleModifyRes.class);
        return responseData;

    }


}
