package com.adtec.pay.service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.pay.dao.BuscardDao;
import com.adtec.pay.dto.NoReturnRes;
import com.adtec.pay.dto.bus.*;
import com.adtec.pay.entity.buscard.BuscardAcctStatement;
import com.adtec.pay.entity.buscard.BuscardAcctStatementDtl;
import com.adtec.pay.utils.MLppUtils;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BusCardOptService {

    @Autowired
    private BuscardDao buscardDao;

    /**
     * 公交交易明细列表查询
     *
     * @param req
     * @param start
     * @param limit
     * @return
     */
    public BusListQryRes getBusList(BusListQryReq req, String busiNo, int start, int limit) {
        ReqDTO reqDTO = new ReqDTO(req);
        MLppUtils.setReqHead(busiNo, reqDTO, "MLppBusCardListQry", start, limit);
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("MLPP_PARTID"), "MLppBusCardListQry",
                reqDTO, BusListQryRes.class, null);
        BusListQryRes res = (BusListQryRes) resDTO.getBODY();
        return res;
    }

    /**
     * 公交卡白名单列表查询
     *
     * @param req
     * @param start
     * @param limit
     * @return
     */
    public WhiteListQryRes getWhiteList(String busiNo, WhiteListQryReq req, int start, int limit) {
        ReqDTO reqDTO = new ReqDTO(req);
        MLppUtils.setReqHead(busiNo, reqDTO, "MLppBusCardWhiteListQry", start, limit);
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("MLPP_PARTID"), "MLppBusCardWhiteListQry",
                reqDTO, WhiteListQryRes.class, null);
        WhiteListQryRes res = (WhiteListQryRes) resDTO.getBODY();
        return res;

    }

    /**
     * 公交卡未确认交易处理
     *
     * @param req
     * @return
     */
    public IDataset confmDeal(BusConfmDealReq req) {
        String busiNo = req.getBUSI_NO();
        ReqDTO reqDTO = new ReqDTO(req);
        MLppUtils.setReqHead(busiNo, reqDTO, "MLppBusCardNotConfmDeal");
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("MLPP_PARTID"), "MLppBusCardNotConfmDeal",
                reqDTO, NoReturnRes.class, null);
        NoReturnRes noReturnRes = new NoReturnRes();
        IDataset responseData = DatasetService.getInstace().getDataset(noReturnRes, NoReturnRes.class);
        return responseData;
    }

    public ClrListQryRes getClrList(String busiNo, ClrListQryReq req, int start, int limit) {

        ReqDTO reqDTO = new ReqDTO(req);
        MLppUtils.setReqHead(busiNo, reqDTO, "MLppBusCardClrListQry", start, limit);
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("MLPP_PARTID"), "MLppBusCardClrListQry",
                reqDTO, ClrListQryRes.class, null);
        ClrListQryRes res = (ClrListQryRes) resDTO.getBODY();
        return res;
    }

    /**
     * 下载充值报表
     *
     * @param params   查询条件
     * @param response HttpServletResponse
     */
    public void getAcctStatement(Map<String, String> params, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = "";
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        List list = new ArrayList<>();

        //获取登录用户名称
        String name = UserUtils.getUser().getName();
        String crtDate = DateUtil.getDate("yyyy-MM-dd");

        //获取excel模板路径
        String templateFileName = System.getenv("HOME") + File.separator + "excel" + File.separator + "LFGJBillTemplate.xls";
        fileName = URLEncoder.encode("售卡充值对账报表" + DateUtil.getDateTime("yyyyMMddHHmmss"), "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        //获取excel模板填充数据
        list = buscardDao.getList(params);
        //获取excel模板汇总数据
        List<BuscardAcctStatement> totList = buscardDao.getTotData(params);
        BuscardAcctStatement data = new BuscardAcctStatement();
        if (!CollectionUtils.isEmpty(totList)) {
            data = totList.get(0);
        }
        data.setDATE(params.get("date"));
        data.setNAME(name);
        data.setCRT_DATE(crtDate);
        try (ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(templateFileName).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
            excelWriter.fill(list, fillConfig, writeSheet);
            excelWriter.fill(data, writeSheet);
        }

    }

    /**
     * 下载公交卡交易明细
     * @param params
     * @param res
     */
    public void printDtl(Map<String, String> params, HttpServletResponse res) {
        String tranTp = params.get("tranTp");
        res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        res.setCharacterEncoding("utf-8");
        String fileName = "";
        res.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        List list = new ArrayList<>();
        String name = "";
        try {
            if (BuscardAcctStatement.TRAN_TP_ACTIVE.equals(tranTp)) {
                name = "公交卡激活明细";
            } else if (BuscardAcctStatement.TRAN_TP_CHARGE.equals(tranTp)) {
                name = "公交卡充值明细";
            } else if(BuscardAcctStatement.TRAN_TP_RTN.equals(tranTp)){
                name = "公交卡退卡明细";
            } else{
                name = "公交卡交易明细";
            }
            fileName = URLEncoder.encode(name + DateUtil.getDateTime("yyyyMMddHHmmss"), "UTF-8");
            res.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            //获取明细数据
            list = buscardDao.getDtlList(params);
            EasyExcel.write(res.getOutputStream(), BuscardAcctStatementDtl.class).autoCloseStream(Boolean.FALSE).sheet(name).doWrite(list);
        } catch (IOException e) {
            System.out.println("文件导出异常");
        }
    }

}
