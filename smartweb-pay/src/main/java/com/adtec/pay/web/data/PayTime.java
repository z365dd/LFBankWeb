package com.adtec.pay.web.data;

import com.adtec.framework.common.util.ServerResponse;
import com.adtec.framework.common.util.StringUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.pay.dto.paytime.PayTimeModifyReq;
import com.adtec.pay.dto.paytime.PayTimeQryListRes;
import com.adtec.pay.service.PayTimeService;
import com.adtec.pay.webResp.ComRespBody;
import com.adtec.sys.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("${adminPath}/payTime/data/")
public class PayTime extends BaseController {

    @Autowired
    private PayTimeService payTimeService;

    /**
     * 缴费规则查询
     *
     * @param req
     * @return
     */
    @RequestMapping("/list")
    public ServerResponse<ComRespBody<List<PayTimeQryListRes>>> query(HttpServletRequest req) {
        IDataset reqDs = DatasetService.getInstace().getDataset(req);
        String mert_no = reqDs.getString("MERT_NO");
        if (StringUtil.isEmpty(mert_no)) {
            throw new BaseException(SysErr.E_MESSAGE, "该机构没有对应的合作商户，请使用合作商户对应的开户机构或上级机构用户进行查看");
        }
        int start = reqDs.getInt("start");
        start = start == 0 ? 1 : start;
        int limit = reqDs.getInt("limit");
        limit = limit == 0 ? 10 : limit;
        ComRespBody<List<PayTimeQryListRes>> body = payTimeService.callPayTimeQry(mert_no, start, limit);
        return ServerResponse.createBySuccess("查询成功", body);
    }

    /**
     * 规则维护新增
     *
     * @param req
     * @return
     */
    @RequestMapping("/add")
    public void add(HttpServletRequest req, HttpServletResponse res) {

        String OPER_TP = "1";
        modify(req, res, OPER_TP);

    }

    @RequestMapping("/update")
    public void update(HttpServletRequest req, HttpServletResponse res) {
        String OPER_TP = "3";
        modify(req, res, OPER_TP);
    }

    /**
     * @param req
     * @return
     */
    @RequestMapping("/delete")
    public void delete(HttpServletRequest req, HttpServletResponse res) {
        String OPER_TP = "2";
        modify(req, res, OPER_TP);

    }

    /**
     * paytimeModify unified processing
     *
     * @param req
     * @param res
     */
    public void modify(HttpServletRequest req, HttpServletResponse res, String OPER_TP) {
        IDataset reqDs = DatasetService.getInstace().getDataset(req);
        String MERT_NO = reqDs.getString("MERT_NO");
        String RULE_TP = reqDs.getString("RULE_TP");
        String STR_TIME = reqDs.getString("STR_TIME");
        String END_TIME = reqDs.getString("END_TIME");
        String RULE_NO = reqDs.getString("RULE_NO");
        PayTimeModifyReq modifyReqDTO = new PayTimeModifyReq();
        modifyReqDTO.setMERT_NO(MERT_NO);
        modifyReqDTO.setOPER_TP(OPER_TP);
        modifyReqDTO.setRULE_NO(RULE_NO);
        modifyReqDTO.setRULE_TP(RULE_TP);
        modifyReqDTO.setSTR_TIME(STR_TIME);
        modifyReqDTO.setEND_TIME(END_TIME);

        IDataset resDs = payTimeService.callPayTimeModify(modifyReqDTO);
        setResponseDataset(req, res, resDs, SysErr.E_SUCCESS, "交易成功");


    }

}
