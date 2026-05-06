package com.adtec.pay.web.data;


import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.common.util.ServerResponse;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.pay.dto.bookList.Book;
import com.adtec.pay.dto.bookList.MLppQryBookListReq;
import com.adtec.pay.dto.bookList.MLppQryBookListRes;
import com.adtec.pay.service.MLppBookListQryService;
import com.adtec.pay.webResp.ComRespBody;
import com.adtec.sys.common.web.BaseController;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 缴费明细查询
 */
@RestController
@RequestMapping("${adminPath}/payDetails/data/")
public class PayDetailsInquiry extends BaseController {


    @Autowired
    private MLppBookListQryService mlppBookListQryService;


    /**
     * 缴费明细查询
     *
     * @param req
     * @return
     */
    @RequestMapping("/list")
    public ServerResponse<ComRespBody<MLppQryBookListRes>> query(HttpServletRequest req) {
        IDataset reqDs = DatasetService.getInstace().getDataset(req);
        int start = reqDs.getInt("start");
        int limit = 0 == reqDs.getInt("limit") ? 10 : reqDs.getInt("limit");
        MLppQryBookListReq qryListReq = getQueryParams(reqDs);
        MLppQryBookListRes respDto = mlppBookListQryService.getBookList(reqDs, qryListReq, start, limit);
        //自动扣款中没有业务编号 需要后期处理添加
        int total = respDto.getTOT_NUM().intValue();
        ComRespBody<MLppQryBookListRes> retData = new ComRespBody<MLppQryBookListRes>(start, limit, respDto, total);
        return ServerResponse.createBySuccess("查询成功", retData);
    }

    private MLppQryBookListReq getQueryParams(IDataset reqDs) {
        String busiNo = reqDs.getString("busiNo");
        String autoDeduct = reqDs.getString("autoDeduct");
        String txStat = reqDs.getString("txStat");
        String acct = reqDs.getString("payAcct");
        String payNo = reqDs.getString("payNo");
        String tranTp = reqDs.getString("tranTp");
        MLppQryBookListReq qryListReq = new MLppQryBookListReq();
        qryListReq.setBUSI_NO(busiNo);
        qryListReq.setAUTO_FLG(autoDeduct);
        qryListReq.setTRAN_STAT(StringUtils.isBlank(txStat) ? "AA" : txStat);
        qryListReq.setACCT(acct);
        qryListReq.setPAY_NO(payNo);
        qryListReq.setTRAN_TP(tranTp);
        return qryListReq;
    }


    /**
     * 导出缴费明细查询数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/export")
    public void exportFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        MLppQryBookListReq qryListReq = getQueryParams(reqDs);
        String strDate = reqDs.getString("startTime").replace("-", "");
        String endDate = reqDs.getString("endTime").replace("-", "");
        qryListReq.setSTR_DATE(strDate);
        qryListReq.setEND_DATE(endDate);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = "PayDetails" + DateUtil.getDate("yyyyMMddHHmmss");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        try {
            List<Book> list = mlppBookListQryService.getAllBookList(qryListReq);
            EasyExcel.write(response.getOutputStream(), Book.class).autoCloseStream(Boolean.FALSE).sheet("缴费明细").doWrite(list);
        } catch (Exception e) {
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = MapUtils.newHashMap();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            response.getWriter().println(JSON.toJSONString(map));
        }
//        IDataset resDs = DatasetService.getInstace().getDataset();
//        setResponseDataset(request, response,resDs, SysErr.E_SUCCESS, "交易成功");
    }

}
