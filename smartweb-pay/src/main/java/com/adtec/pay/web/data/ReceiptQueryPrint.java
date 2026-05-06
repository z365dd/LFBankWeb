package com.adtec.pay.web.data;

import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.common.util.ServerResponse;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.pay.dto.recorded.RecQryReq;
import com.adtec.pay.dto.recorded.RecQryRes;
import com.adtec.pay.dto.recorded.RecQryResList;
import com.adtec.pay.entity.ReceiptPrint;
import com.adtec.pay.service.RecQryService;
import com.adtec.pay.webResp.ComRespBody;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 回单查询打印
 */
@RestController
@RequestMapping("${adminPath}/pay/data/")
public class ReceiptQueryPrint {

    @Autowired
    private RecQryService recQryService;

    @RequestMapping("/list")
    public ServerResponse<ComRespBody<RecQryRes>> getReceiptQryList(HttpServletRequest request) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        int start = reqDs.getInt("start");
        int limit = reqDs.getInt("limit");
        start = start == 0 ? start : (start - 1) * limit;
        //获取条件
        String busiNo = reqDs.getString("busiNo");
        if(StringUtils.isBlank(busiNo)){
            throw new BaseException(SysErr.E_MESSAGE, "该机构没有对应的合作商户，请使用合作商户对应的开户机构或上级机构用户进行查看");
        }
        String chnlNo = reqDs.getString("chnlNo");
        String strDate = reqDs.getString("strDate");
        strDate = strDate.substring(0, 4) + strDate.substring(5, 7) + strDate.substring(8);
        String endDate = reqDs.getString("endDate");
        endDate = endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8);
        String brchName = UserUtils.getUser().getOffice().getName();
        RecQryReq recQryReq = new RecQryReq();
        recQryReq.setBUSI_NO(busiNo);
        recQryReq.setCHNL_NO(chnlNo);
        recQryReq.setSTR_DATE(strDate);
        recQryReq.setEND_DATE(endDate);
        //获取汇总数据
        RecQryRes recQryRes = recQryService.getSumData(recQryReq);
        recQryRes.setBrchName(brchName);
        int total = recQryService.count(recQryReq);
        List<RecQryResList> list = recQryService.getQryList(recQryReq, start, limit);
        recQryRes.setLIST(list);
        ComRespBody<RecQryRes> retData = new ComRespBody<RecQryRes>(start, limit, recQryRes, total);
        return ServerResponse.createBySuccess("查询成功", retData);
    }


    /**
     * 生成对应的excel
     * s
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/print")
    public void excelPrint(HttpServletRequest request, HttpServletResponse response) throws IOException {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String busiNo = reqDs.getString("busiNo");
        String chnlNo = reqDs.getString("chnlNo");
        String strDate = reqDs.getString("strDate");
        strDate = strDate.substring(0, 4) + strDate.substring(5, 7) + strDate.substring(8);
        String endDate = reqDs.getString("endDate");
        endDate = endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("回单打印" + DateUtil.getDateTime("yyyyMMddHHmmss"), "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        List<ReceiptPrint> list = recQryService.getExcelData(busiNo, strDate, endDate, chnlNo);
        EasyExcel.write(response.getOutputStream(), ReceiptPrint.class)
                .sheet("单据打印")
                .registerWriteHandler(new SimpleColumnWidthStyleStrategy(15))
                .doWrite(list);
    }

}
