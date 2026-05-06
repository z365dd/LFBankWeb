package com.adtec.pay.web.data;

import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.common.util.ServerResponse;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.pay.dto.bus.*;
import com.adtec.pay.service.BusCardOptService;
import com.adtec.pay.webResp.ComRespBody;
import com.adtec.sys.common.web.BaseController;
import com.alibaba.excel.EasyExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${adminPath}/bus/data/")
public class BusCardOpt extends BaseController {


    @Autowired
    private BusCardOptService busCardOptService;

    private final static String SUCCESS_STAT = "01";

    private final static String SUCCESS_COMF_STAT = "10";

    private final static String ACTIVE_TPYE = "01";

    /**
     * 公交卡交易明细查询
     *
     * @param req
     * @return
     * @throws Exception
     */
    @RequestMapping("/busList")
    public ServerResponse<ComRespBody<BusListQryRes>> getBusList(HttpServletRequest req){
        IDataset reqDs = DatasetService.getInstace().getDataset(req);
        int limit = reqDs.getInt("limit");
        int start = reqDs.getInt("start");
        String acct = reqDs.getString("acct");
        String appId = reqDs.getString("appId");
        String strDate = reqDs.getString("strDate");
        strDate = strDate.substring(0, 4) + strDate.substring(5, 7) + strDate.substring(8);
        String endDate = reqDs.getString("endDate");
        endDate = endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8);
        String tranTp = reqDs.getString("tranTp");
        String stat = reqDs.getString("stat");
        String confmStat = reqDs.getString("confmStat");
        String busiNo = reqDs.getString("busiNo");
        BusListQryReq busListQryReq = new BusListQryReq();
        busListQryReq.setACCT(acct);
        busListQryReq.setAPP_ID(appId);
        busListQryReq.setSTR_DATE(strDate);
        busListQryReq.setEND_DATE(endDate);
        busListQryReq.setOTH_SYS("LFGJ");
        busListQryReq.setPAG_NUM(start);
        busListQryReq.setREQ_REC_NUM(limit);
        busListQryReq.setSTAT(stat);
        busListQryReq.setCONFM_STAT(confmStat);
        busListQryReq.setTRAN_TP(tranTp);

        BusListQryRes res = busCardOptService.getBusList(busListQryReq,busiNo,start,limit);
        int total = res.getTOT_NUM().intValue();
        ComRespBody<BusListQryRes> retData = new ComRespBody<BusListQryRes>(start, limit, res,
                total);
        return ServerResponse.createBySuccess("查询成功", retData);
    }


    /**
     * 公交卡白名单查询
     *
     * @param req
     * @return
     * @throws Exception
     */
    @RequestMapping("/whiteList")
    public ServerResponse<ComRespBody<WhiteListQryRes>> getWhiteList(HttpServletRequest req){
        IDataset reqDs = DatasetService.getInstace().getDataset(req);
        int limit = reqDs.getInt("limit");
        int start = reqDs.getInt("start");
        String custNo = reqDs.getString("custNo");
        String certNo = reqDs.getString("certNo");
        String acct = reqDs.getString("acct");
        String stat = reqDs.getString("stat");
        String busiNo = reqDs.getString("busiNo");
        WhiteListQryReq whiteListQryReq = new WhiteListQryReq();
        whiteListQryReq.setACCT(acct);
        whiteListQryReq.setSTAT(stat);
        whiteListQryReq.setCUST_NO(custNo);
        whiteListQryReq.setCERT_NO(certNo);
        WhiteListQryRes res = busCardOptService.getWhiteList(busiNo,whiteListQryReq,start,limit);
        int total = res.getTOT_NUM().intValue();
        ComRespBody<WhiteListQryRes> retData = new ComRespBody<WhiteListQryRes>(start, limit, res,
                total);
        return ServerResponse.createBySuccess("查询成功", retData);
    }


    /**
     * 公交卡未确认交易处理
     *
     * @param req
     * @return
     * @throws Exception
     */
    @RequestMapping("/confmDeal")
    public void confmDeal(HttpServletRequest req, HttpServletResponse res){
        IDataset reqDs = DatasetService.getInstace().getDataset(req);

        String busiNo = reqDs.getString("busiNo");
        String platDate = reqDs.getString("platDate");
        String platSeq = reqDs.getString("platSeq");
        String confmStat = reqDs.getString("confmStat");

        BusConfmDealReq busConfmDealReq = new BusConfmDealReq();
        busConfmDealReq.setBUSI_NO(busiNo);
        busConfmDealReq.setPLAT_DATE(platDate);
        busConfmDealReq.setPLAT_SEQ(platSeq);
        busConfmDealReq.setCONFM_STAT(confmStat);
        IDataset resDs = busCardOptService.confmDeal(busConfmDealReq);
        setResponseDataset(req, res, resDs, SysErr.E_SUCCESS, "交易成功");

    }



    /**
     * 公交卡回单打印查询
     *
     * @param req
     * @return
     * @throws Exception
     */
    @RequestMapping("/clrList")
    public ServerResponse<ComRespBody<ClrListQryRes>> getclrList(HttpServletRequest req){
        IDataset reqDs = DatasetService.getInstace().getDataset(req);
        int limit = reqDs.getInt("limit");
        int start = reqDs.getInt("start");
        String busiNo = reqDs.getString("busiNo");
        String strDate = reqDs.getString("strDate");
        String endDate = reqDs.getString("endDate");

        if(checkLimitDuration(strDate, endDate)){
            throw new BaseException(SysErr.E_MESSAGE, "开始时间与结束时间的时间间隔不能超过一个月!");
        }
        strDate = strDate.substring(0, 4) + strDate.substring(5, 7) + strDate.substring(8);
        endDate = endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8);
        ClrListQryReq clrListQryReq = new ClrListQryReq();
        clrListQryReq.setBUSI_NO(busiNo);
        clrListQryReq.setSTR_DATE(strDate);
        clrListQryReq.setEND_DATE(endDate);

        ClrListQryRes res = busCardOptService.getClrList(busiNo,clrListQryReq,start,limit);
        int total = res.getTOT_NUM().intValue();
        ComRespBody<ClrListQryRes> retData = new ComRespBody<ClrListQryRes>(start, limit, res,
                total);
        return ServerResponse.createBySuccess("查询成功", retData);
    }

    /**
     * 计算两个日期之间相差多少天
     * @param strDate
     * @param endDate
     * @return
     */
    private boolean checkLimitDuration(String strDate, String endDate) {

        LocalDate str = LocalDate.of(Integer.parseInt(strDate.substring(0, 4)), Integer.parseInt(strDate.substring(5, 7)), Integer.parseInt(strDate.substring(8, 10)));
        LocalDate end = LocalDate.of(Integer.parseInt(endDate.substring(0, 4)), Integer.parseInt(endDate.substring(5, 7)), Integer.parseInt(endDate.substring(8, 10)));

        long daysDiff = ChronoUnit.DAYS.between(str, end);

        return daysDiff > 31 ;
    }


    /**
     * 回单打印下载
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/print")
    public void print(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取回单打印数据
        List<ClrListQryResList> list = getReceiptData(request);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("回单打印" + DateUtil.getDateTime("yyyyMMddHHmmss"), "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        EasyExcel.write(response.getOutputStream(), ClrListQryResList.class).sheet("回单打印").doWrite(list);


    }

    private List<ClrListQryResList> getReceiptData(HttpServletRequest req) {
        IDataset reqDs = DatasetService.getInstace().getDataset(req);
        String busiNo = reqDs.getString("busiNo");
        String strDate = reqDs.getString("strDate");
        String endDate = reqDs.getString("endDate");
        if(checkLimitDuration(strDate, endDate)){
            throw new BaseException(SysErr.E_MESSAGE, "开始日期和结束日期最多相差31天!");
        }
        strDate = strDate.substring(0, 4) + strDate.substring(5, 7) + strDate.substring(8);
        endDate = endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8);
        ClrListQryReq clrListQryReq = new ClrListQryReq();
        clrListQryReq.setBUSI_NO(busiNo);
        clrListQryReq.setSTR_DATE(strDate);
        clrListQryReq.setEND_DATE(endDate);

        ClrListQryRes res = busCardOptService.getClrList(busiNo,clrListQryReq,1,31);
        List<ClrListQryResList> list = res.getLIST();
        for (ClrListQryResList item : list) {
            //获取确认日期  公交卡业务是D1 所以确认日期一定是清算日期的前一天！
            item.setCONFM_DATE(DateUtil.addDate(item.getCLR_DATE(), -1));
        }
        return list;
    }

    /**
     * 下载充值报表
     *
     * @param req
     * @param res
     * @return
     */
    @RequestMapping("/getAcctStatement")
    public void getAcctStatement(HttpServletRequest req, HttpServletResponse res) throws IOException {
        IDataset reqDs = DatasetService.getInstace().getDataset(req);
        String busiNo = reqDs.getString("busiNo");
        String strDate = reqDs.getString("strDate");
        String endDate = reqDs.getString("endDate");
        String date = strDate + "—" + endDate;
        String tranTp = reqDs.getString("tranTp");
        strDate = strDate.substring(0, 4) + strDate.substring(5, 7) + strDate.substring(8);
        endDate = endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8);
        Map<String, String> params = new HashMap<>();
        params.put("date", date);
        params.put("strDate", strDate);
        params.put("endDate", endDate);
        params.put("busiNo", busiNo);
        params.put("tranTp", tranTp);
        busCardOptService.getAcctStatement(params, res);
    }

    /**
     * 下载公交卡交易明细
     * @param req
     * @param res
     */
    @RequestMapping("/printDtl")
    public void printDtl(HttpServletRequest req,HttpServletResponse res) {
        IDataset reqDs = DatasetService.getInstace().getDataset(req);
        String busiNo = reqDs.getString("busiNo");
        String strDate = reqDs.getString("strDate");
        String endDate = reqDs.getString("endDate");
        String stat = reqDs.getString("stat");
        String confmStat = reqDs.getString("confmStat");
        String acct = reqDs.getString("acct");
        String appId = reqDs.getString("appId");
        String tranTp = reqDs.getString("tranTp");
        strDate = strDate.substring(0, 4) + strDate.substring(5, 7) + strDate.substring(8);
        endDate = endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8);
        Map<String, String> params = new HashMap<>();
        params.put("strDate", strDate);
        params.put("endDate", endDate);
        params.put("busiNo", busiNo);
        params.put("tranTp", tranTp);
        params.put("stat", stat);
        params.put("confmStat", confmStat);
        params.put("appId", appId);
        params.put("acct", acct);
        busCardOptService.printDtl(params, res);
    }
}
