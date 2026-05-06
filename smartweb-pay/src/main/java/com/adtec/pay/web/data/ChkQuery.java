package com.adtec.pay.web.data;

import com.adtec.framework.common.util.ServerResponse;
import com.adtec.framework.common.util.StringUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.pay.dto.check.ChkQryReq;
import com.adtec.pay.dto.check.ChkQryRes;
import com.adtec.pay.service.ChkQryService;
import com.adtec.pay.service.ComQueryService;
import com.adtec.pay.webResp.ComRespBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("${adminPath}/chkQuery/data/")
public class ChkQuery {


    @Autowired
    private ChkQryService chkQryService;
    @Autowired
    private ComQueryService comQueryService;

    /**
     * 对账查询
     *
     * @param req
     * @return
     */
    @RequestMapping("/list")
    public ServerResponse<ComRespBody<ChkQryRes>> query(HttpServletRequest req) throws Exception {
        IDataset reqDs = DatasetService.getInstace().getDataset(req);
        int limit = reqDs.getInt("limit");
        int start = reqDs.getInt("start");
        String startTime = reqDs.getString("startTime");
        String endTime = reqDs.getString("endTime");
        String STR_TIME = startTime.substring(0, 4) + startTime.substring(5, 7) + startTime.substring(8);
        String END_TIME = endTime.substring(0, 4) + endTime.substring(5, 7) + endTime.substring(8);
        ChkQryReq chkQryReq = new ChkQryReq();
        chkQryReq.setSTR_DATE(STR_TIME);
        chkQryReq.setEND_DATE(END_TIME);
        String busiNo = reqDs.getString("busiNo");
        String chkStat = reqDs.getString("chkStat");
        //todo 业务编码不传的时候根据登录用户所属的机构号来获取所有
        chkQryReq.setBUSI_NO(busiNo);
        chkQryReq.setCHK_STAT(StringUtil.isNotBlank(chkStat) ? chkStat : "AA");
        ChkQryRes chkQryRes = chkQryService.getChkList(chkQryReq, start, limit);
        int total = chkQryRes.getREC_NUM().intValue();
        ComRespBody<ChkQryRes> retData = new ComRespBody<ChkQryRes>(start, limit, chkQryRes,
                total);
        return ServerResponse.createBySuccess("查询成功", retData);
    }
}
