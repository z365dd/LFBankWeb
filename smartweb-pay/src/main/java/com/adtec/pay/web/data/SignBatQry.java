package com.adtec.pay.web.data;

import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.common.util.ServerResponse;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.pay.dto.signbat.SignBatQryReq;
import com.adtec.pay.dto.signbat.SignBatQryReqList;
import com.adtec.pay.dto.signbat.SignBatQryRes;
import com.adtec.pay.dto.signbat.SignBatQryResList;
import com.adtec.pay.service.SignBatQryService;
import com.adtec.pay.webResp.ComRespBody;
import com.alibaba.excel.EasyExcel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${adminPath}/signBatQry/data/")
public class SignBatQry {

    @Autowired
    private SignBatQryService signBatQryService;

    /**
     * 签约信息查询_批量
     *
     * @param req
     * @return
     */
    @RequestMapping("/list")
    public ServerResponse<ComRespBody<SignBatQryRes>> query(HttpServletRequest req) {
        IDataset reqDs = DatasetService.getInstace().getDataset(req);

        int start = reqDs.getInt("start");
        start = start == 0 ? 1 : start;
        int limit = reqDs.getInt("limit");
        limit = limit == 0 ? 10 : limit;
        //构建签约查询请求参数
        SignBatQryReq signBatQryReq = buildQueryParams(reqDs);
        SignBatQryRes signBatQryRes = signBatQryService.callSignBatQry(reqDs, signBatQryReq, start, limit);
        int total = signBatQryRes.getTOT_NUM().intValue();
        ComRespBody<SignBatQryRes> retData = new ComRespBody<SignBatQryRes>(start, limit, signBatQryRes, total);
        return ServerResponse.createBySuccess("查询成功", retData);
    }

    /**
     * 构建签约查询请求参数
     * @param reqDs
     * @return
     */
    private SignBatQryReq buildQueryParams(IDataset reqDs) {
        String busiNo = reqDs.getString("busiNo");
        String payNo = reqDs.getString("payNo");
        String payAcct = reqDs.getString("payAcct");
        String signStat = reqDs.getString("signStat");
        String custNo = reqDs.getString("custNo");
        String signBrch = reqDs.getString("signBrch");
        signStat = StringUtils.isEmpty(signStat) ? "AA" : signStat;
        String strDate = reqDs.getString("strDate");
        strDate = StringUtils.isEmpty(strDate) ? "" : strDate.substring(0, 4) + strDate.substring(5, 7) + strDate.substring(8);
        String endDate = reqDs.getString("endDate");
        endDate = StringUtils.isEmpty(endDate) ? "" : endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8);
        SignBatQryReq signBatQryReq = new SignBatQryReq();
        List<SignBatQryReqList> list = new ArrayList<>();
        SignBatQryReqList signBatQryReqList = new SignBatQryReqList();
        signBatQryReqList.setOTH_ENTR_NO(busiNo);
        signBatQryReqList.setOTH_CUST_NO(payNo);
        signBatQryReqList.setACCT(payAcct);
        signBatQryReqList.setSIGN_STAT(signStat);
        signBatQryReqList.setSTR_DATE(strDate);
        signBatQryReqList.setEND_DATE(endDate);
        signBatQryReqList.setCUST_NO(custNo);
        signBatQryReqList.setSIGN_BRCH(signBrch);
        list.add(signBatQryReqList);
        signBatQryReq.setLIST(list);
        return signBatQryReq;
    }


    /**
     * 签约查询导出
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/export")
    public void print(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取回单打印数据
        List<SignBatQryResList> list = getSignData(request);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("签约信息" + DateUtil.getDateTime("yyyyMMddHHmmss"), "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        EasyExcel.write(response.getOutputStream(), SignBatQryResList.class).sheet("签约信息").doWrite(list);

    }

    /**
     * 获取签约数据
     * @param req
     * @return
     */
    private List<SignBatQryResList> getSignData(HttpServletRequest req) {
        IDataset reqDs = DatasetService.getInstace().getDataset(req);
        SignBatQryReq signBatQryReq = buildQueryParams(reqDs);
        //目前暂定为查询一万条 目前无分页
        SignBatQryRes signBatQryRes = signBatQryService.callSignBatQry(reqDs, signBatQryReq, 1, 10000);
        return signBatQryRes.getLIST();
    }

}
