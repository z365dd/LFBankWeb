package com.adtec.pay.web.data;

import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.common.util.ServerResponse;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.pay.dto.ErrExcel;
import com.adtec.pay.dto.bookList.BookList;
import com.adtec.pay.dto.bookList.MLppQryBookListReq;
import com.adtec.pay.dto.bookList.OfflineBook;
import com.adtec.pay.dto.bookList.SubList;
import com.adtec.pay.dto.offline.DataInfo;
import com.adtec.pay.dto.offline.MerInfo;
import com.adtec.pay.dto.offline.MergeOfflineDtl;
import com.adtec.pay.dto.offline.detail.*;
import com.adtec.pay.dto.offline.mission.*;
import com.adtec.pay.dto.offline.proj.ProjModifyReq;
import com.adtec.pay.dto.offline.proj.ProjQryReq;
import com.adtec.pay.dto.offline.proj.ProjQryRes;
import com.adtec.pay.dto.offline.temple.AccountExcelData;
import com.adtec.pay.dto.recorded.RecSumQryReq;
import com.adtec.pay.dto.recorded.RecSumQryRes;
import com.adtec.pay.dto.recorded.RecSumQryResList;
import com.adtec.pay.dto.template.PdfTemplate;
import com.adtec.pay.entity.OfflineDO;
import com.adtec.pay.entity.ProjDo;
import com.adtec.pay.entity.Template;
import com.adtec.pay.service.ComQueryService;
import com.adtec.pay.service.MLppBookListQryService;
import com.adtec.pay.service.OfflineOptService;
import com.adtec.pay.service.RecQryService;
import com.adtec.pay.utils.AmountToChinese;
import com.adtec.pay.utils.CaculateUtils;
import com.adtec.pay.utils.Constant;
import com.adtec.pay.utils.PdfUtils;
import com.adtec.pay.webResp.ComRespBody;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.modules.sys.utils.StringUtil;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.itextpdf.text.DocumentException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

/**
 * 缴费项操作
 */
@RestController
@RequestMapping("${adminPath}/offline/data/")
public class OfflineOpt extends BaseController {

    private static final String ADD = "1";//操作类型-新增
    private static final String MODIFY = "2";//操作类型-修改
    private static final String DELETE = "3";//操作类型-删除
    //excel操作类型-模板下载
    public static final String EXCEL_DOWNLOAD = "download";
    //excel操作类型-明细导出
    public static final String EXCEL_EXPORT = "export";

    public static final String OPER_TYPE_ONLINE = "00";

    @Autowired
    private OfflineOptService offlineOptService;

    @Autowired
    private ComQueryService comQueryService;

    @Autowired
    private RecQryService recQryService;
    /**
     * 缴费项查询
     *
     * @param request
     * @return
     */
    @RequestMapping("/proj_list")
    public ServerResponse<ComRespBody<ProjQryRes>> projList(HttpServletRequest request) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        int start = reqDs.getInt("start");
        int limit = 0 == reqDs.getInt("limit") ? 10 : reqDs.getInt("limit");
        String busiNo = reqDs.getString("busiNo");
        ProjQryReq projQryReq = new ProjQryReq();
        projQryReq.setBUSI_NO(busiNo);
        ProjQryRes res = offlineOptService.getProjList(projQryReq, start, limit);
        int total = res.getTOT_NUM().intValue();
        ComRespBody<ProjQryRes> retData = new ComRespBody<ProjQryRes>(start, limit, res, total);
        return ServerResponse.createBySuccess("查询成功", retData);
    }

    /**
     * 缴费项查询
     *
     * @param request
     * @return
     */
    @RequestMapping("/proj_modify")
    public void projModify(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String busiNo = reqDs.getString("busiNo");
        String busiName = reqDs.getString("busiName");
        String projName = reqDs.getString("projName");
        String origProjName = reqDs.getString("origProjName");
        String projDesc = reqDs.getString("projDesc");
        String projTp = reqDs.getString("projTp");
        String operTp = reqDs.getString("operTp");
        String oweMonth = reqDs.getString("oweMonth");
        double amt = reqDs.getDouble("amt");

        ProjModifyReq projModifyReq = new ProjModifyReq();
        projModifyReq.setBUSI_NO(busiNo);
        projModifyReq.setBUSI_NAME(busiName);
        projModifyReq.setPROJ_NAME(projName);
        //如果操作类型是新增 必传
        if (!DELETE.equals(operTp)) {
            projModifyReq.setPROJ_DESC(projDesc);
            projModifyReq.setPROJ_TP(projTp);
            //如果是修改 在owe_month里面存放
            if (MODIFY.equals(operTp)) {
                projModifyReq.setMID_RMRK(origProjName);
            }
            //自主录入  缴费金额和收费周期必输
            if ("01".equals(projTp)) {
                projModifyReq.setOWE_MONTH(oweMonth);
                projModifyReq.setAMT(amt);
            }
        }
        projModifyReq.setOPER_TP(operTp);
        IDataset resDs = offlineOptService.callProjModify(projModifyReq);
        setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");

    }

    /**
     * 报表查询列表返回
     *
     * @param request
     * @return
     */
    @RequestMapping("/mission_list")
    public ServerResponse<ComRespBody<MissionQryRes>> missionList(HttpServletRequest request) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        int start = reqDs.getInt("start");
        int limit = 0 == reqDs.getInt("limit") ? 10 : reqDs.getInt("limit");
        String busiNo = reqDs.getString("busiNo");
        String projName = reqDs.getString("projName");
        String oweMonth = reqDs.getString("oweMonth");
        String strDate = reqDs.getString("strDate");
        strDate = StringUtils.isEmpty(strDate) ? "" : strDate.substring(0, 4) + strDate.substring(5, 7) + strDate.substring(8);
        String endDate = reqDs.getString("endDate");
        endDate = StringUtils.isEmpty(endDate) ? "" : endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8);
        MissionQryReq missionQryReq = new MissionQryReq();
        missionQryReq.setBUSI_NO(busiNo);
        missionQryReq.setPROJ_NAME(projName);
        missionQryReq.setOWE_MONTH(oweMonth);
        missionQryReq.setSTR_DATE(strDate);
        missionQryReq.setEND_DATE(endDate);
        MissionQryRes res = offlineOptService.getMissionList(missionQryReq, start, limit);
        int total = res.getTOT_NUM().intValue();
        ComRespBody<MissionQryRes> retData = new ComRespBody<MissionQryRes>(start, limit, res, total);
        return ServerResponse.createBySuccess("查询成功", retData);
    }

    /**
     * 缴费明细列表查询
     *
     * @param request
     * @return
     */
    @RequestMapping("/dtl_list")
    public ServerResponse<ComRespBody<DtlQryRes>> dtlList(HttpServletRequest request) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        int start = reqDs.getInt("start");
        int limit = 0 == reqDs.getInt("limit") ? 10 : reqDs.getInt("limit");
        String busiNo = reqDs.getString("busiNo");
        String projName = reqDs.getString("projName");
        String oweMonth = reqDs.getString("oweMonth");
        String name = reqDs.getString("name");
        String phoneNo = reqDs.getString("phoneNo");
        String payNo = reqDs.getString("payNo");
        String strDate = reqDs.getString("strDate");
        strDate = StringUtils.isEmpty(strDate) ? "" : strDate.substring(0, 4) + strDate.substring(5, 7) + strDate.substring(8);
        String endDate = reqDs.getString("endDate");
        endDate = StringUtils.isEmpty(endDate) ? "" : endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8);
        String stat = reqDs.getString("stat");
        String projTp = reqDs.getString("projTp");
        String extraFields = reqDs.getString("extraFields");
        String[] split = extraFields.split(",\\$");

        //属性赋值
        DtlQryReq dtlQryReq = new DtlQryReq();
        if (split.length == 2) {
            if (StringUtils.isNotBlank(split[0]) && StringUtils.isNotBlank(split[1])) {
                //当输入内容为金额时  判断是否为规定格式的内容
//                if(split[0].contains("AMT") && split[1].matches("-?\\d+(\\.\\d+)?")){
//
//                }
                dtlQryReq.setSHORT_RMRK(extraFields);

            }
        }
        dtlQryReq.setBUSI_NO(busiNo);
        dtlQryReq.setPROJ_NAME(projName);
        dtlQryReq.setNAME(name);
        dtlQryReq.setPHONE_NO(phoneNo);
        dtlQryReq.setSTU_ID(payNo);
        dtlQryReq.setSTR_DATE(strDate);
        dtlQryReq.setEND_DATE(endDate);
        dtlQryReq.setSTAT(stat);
        dtlQryReq.setOWE_MONTH(oweMonth);
        dtlQryReq.setPROJ_TP(projTp);

        DtlQryRes res = offlineOptService.getDtlList(dtlQryReq, start, limit);
        int total = res.getTOT_NUM().intValue();
        ComRespBody<DtlQryRes> retData = new ComRespBody<DtlQryRes>(start, limit, res, total);
        return ServerResponse.createBySuccess("查询成功", retData);
    }


    /**
     * 缴费明细维护
     *
     * @param request
     * @return
     */
    @RequestMapping("/dtl_modify")
    public void dtlModify(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String busiNo = reqDs.getString("busiNo");
        String busiName = reqDs.getString("busiName");
        String projName = reqDs.getString("projName");
        String oweMonth = reqDs.getString("oweMonth");
        String certNo = reqDs.getString("certNo");
        Long subSer = reqDs.getLong("subSer");
        String operTp = reqDs.getString("operTp");
        String name = reqDs.getString("name");
        String phoneNo = reqDs.getString("phoneNo");
        double totAmt = reqDs.getDouble("totAmt");
        double dctAmt = reqDs.getDouble("dctAmt");
        double prctlAmt = CaculateUtils.sub(totAmt, dctAmt);
        String operStat = reqDs.getString("operStat");
        String posSeq = reqDs.getString("shortRmrk");
        DtlModifyReq modifyReq = new DtlModifyReq();
        List<DtlModifyReqList> list = Lists.newArrayList();
        DtlModifyReqList item = new DtlModifyReqList();
        modifyReq.setBUSI_NO(busiNo);
        modifyReq.setPROJ_NAME(projName);
        modifyReq.setOWE_MONTH(oweMonth);
        modifyReq.setBUSI_NAME(busiName);
        modifyReq.setSUB_SER(subSer);
        modifyReq.setSHORT_RMRK(posSeq);
        if (MODIFY.equals(operTp)) {
            item.setSUB_SER(subSer);
            item.setNAME(name);
            item.setPHONE_NO(phoneNo);
            item.setTOT_AMT(totAmt);
            item.setPRCTL_AMT(prctlAmt);
            item.setCERT_NO(certNo);
        }
        if (!OPER_TYPE_ONLINE.equals(operStat)) {
            modifyReq.setOPER_STAT(operStat);
        }
        modifyReq.setOPER_TP(operTp);
        list.add(item);
        modifyReq.setLIST(list);
        IDataset resDs = offlineOptService.callDtlModify(modifyReq);
        setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
    }

    /**
     * 下载模板
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String busiNo = reqDs.getString("busiNo");
        Map<String, List<List<String>>> head = getHead(busiNo, EXCEL_DOWNLOAD);
        List<List<String>> desc = head.get("desc");
        List<List<String>> list = head.get("default");
        if (list.get(0).size() == 0) {
            String content = "模板配置为空,请在模板管理中配置!";
            List<String> inList = new ArrayList<>();
            inList.add(content);
            list.add(inList);
        }
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = "offlinePay" + System.currentTimeMillis();
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream())
//                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .head(desc) //设置excel表头内容
//                .autoCloseStream(Boolean.FALSE)
                .registerWriteHandler(new SimpleColumnWidthStyleStrategy(15))
                .sheet("非联网缴费明细")
                .doWrite(list);
    }

    /**
     * 导入账单明细
     *
     * @param request
     */
    @RequestMapping("fileImport")
    @ResponseBody
    private ServerResponse<ComRespBody<DtlImportRes>> fileImport(HttpServletRequest request, MultipartFile file) {

        DtlImportRes res = offlineOptService.fileImport(request, file);
        int total = res.getTOT_NUM().intValue();
        ComRespBody<DtlImportRes> retData = new ComRespBody<DtlImportRes>(0, 10, res, total);
        return ServerResponse.createBySuccess("导入成功", retData);
    }


    /**
     * 获取业务编号对应的缴费项名称
     * * @return
     */
    @RequestMapping("/projNameList")
    public ServerResponse<List<ProjDo>> projNameList(String busiNo) {
        List<ProjDo> list = offlineOptService.projList(busiNo);
        return ServerResponse.createBySuccess("查询成功", list);
    }

    /**
     * 缴费明细导出
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("export")
    private void export(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取模板位置
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String busiNo = reqDs.getString("busiNo");
        String projName = reqDs.getString("projName");
        String stat = reqDs.getString("stat");
        String payNo = reqDs.getString("payNo");
        String name = reqDs.getString("name");
        String phoneNo = reqDs.getString("phoneNo");
        String strDate = reqDs.getString("strDate");
        strDate = StringUtils.isEmpty(strDate) ? "" : strDate.substring(0, 4) + strDate.substring(5, 7) + strDate.substring(8);
        String endDate = reqDs.getString("endDate");
        endDate = StringUtils.isEmpty(endDate) ? "" : endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8);
        String oweMonth = reqDs.getString("oweMonth");
        String projTp = reqDs.getString("projTp");
        //传递过来的值为    字段名/字段值/字段描述
        String extraField = reqDs.getString("extraField1");
        String[] split = extraField.split(",\\$");
        boolean extra = false;
        String fieldName = "";
        String fieldVal = "";
        if (split.length == 2) {
            //如果拓展字段作为查询条件 添加到excel头的map中
            if (StringUtils.isNotBlank(split[0]) && StringUtils.isNotBlank(split[1])) {
                extra = true;
                fieldName = split[0];
                fieldVal = split[1];
            }
        }

        Map<String, List<List<String>>> excelHead = getHead(busiNo, EXCEL_EXPORT);
        int num = 0;
        //查询需要导出的明细信息总数 判断是否有可选查询的参与
        if (extra) {
            num = offlineOptService.countExportData(busiNo, projName, stat, payNo,
                    name, phoneNo, strDate, endDate, oweMonth, projTp, fieldName, fieldVal);
        } else {
            num = offlineOptService.countExportData(busiNo, projName, stat, payNo,
                    name, phoneNo, strDate, endDate, oweMonth, projTp, "", "");
        }
        //每10000条数据导入到Excel的一个sheet页
        int times = num / 10000 == 0 || num % 10000 != 0 ? num / 10000 + 1 : num / 10000;
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("非联网缴费明细" + DateUtil.getDateTime("yyyyMMddHHmmss"), "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        //第三版：动态表头以及对应内容 不创建对象
//            EasyExcel.write(response.getOutputStream())
//                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
//                    .head(excelHead.get("desc")) //设置excel表头内容
////                .autoCloseStream(Boolean.FALSE)
//                    .sheet("非联网缴费明细")
//                    .doWrite(list);
        //第四版: 动态表头以及对应内容；不创建对象；数据分批写入多个sheet
        try (ExcelWriter excelWriter =
                     EasyExcel.write(response.getOutputStream())
                             .head(excelHead.get("desc"))
                             //设置固定列宽
                             .registerWriteHandler(new SimpleColumnWidthStyleStrategy(15))
                             .build()) {

            for (int page = 0; page < times; page++) {
                WriteSheet writeSheet = EasyExcel.writerSheet(page, "缴费明细第" + (page + 1) + "批").build();
                List<List<Object>> list = null;
                //获取所有批次详情查询数据
                if (extra) {
                    list = offlineOptService.getAllData(busiNo, projName, stat, payNo,
                            name, phoneNo, strDate, endDate, oweMonth, projTp, excelHead.get("key"), page * 10000, fieldName, fieldVal);
                } else {
                    list = offlineOptService.getAllData(busiNo, projName, stat, payNo,
                            name, phoneNo, strDate, endDate, oweMonth, projTp, excelHead.get("key"), page * 10000, "", "");
                }

                excelWriter.write(list, writeSheet);
            }
        }
        //第一版：将模板内容填充到excel模板上
//        try (ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(templateFileName).build()) {
//            WriteSheet writeSheet = EasyExcel.writerSheet().build();
//            excelWriter.fill(list, writeSheet);
//        }
        //第二版：直接将查询数据写成excel
//        EasyExcel.write(response.getOutputStream(), OfflineDO.class).head(excelHead.get("desc")).sheet("非联网缴费明细").doWrite(list);

    }


    /**
     * 缴费明细导出
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("exportByPayNo")
    private void exportByPayNo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取模板位置
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String busiNo = reqDs.getString("busiNo");
        String projName = reqDs.getString("projName");
        String stat = reqDs.getString("stat");
        String payNo = reqDs.getString("payNo");
        String name = reqDs.getString("name");
        String phoneNo = reqDs.getString("phoneNo");
        String strDate = reqDs.getString("strDate");
        strDate = StringUtils.isEmpty(strDate) ? "" : strDate.substring(0, 4) + strDate.substring(5, 7) + strDate.substring(8);
        String endDate = reqDs.getString("endDate");
        endDate = StringUtils.isEmpty(endDate) ? "" : endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8);
        String oweMonth = reqDs.getString("oweMonth");
        String projTp = reqDs.getString("projTp");
        OfflineDO qryList = new OfflineDO();
        qryList.setBUSI_NO(busiNo);
        qryList.setPROJ_NAME(projName);
        qryList.setSTAT(stat);
        qryList.setPAY_NO(payNo);
        qryList.setNAME(name);
        qryList.setPHONE_NO(phoneNo);
        qryList.setOWE_MONTH(oweMonth);
        qryList.setPROJ_TP(projTp);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("缴费明细合并导出" + DateUtil.getDateTime("yyyyMMddHHmmss"), "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        try {
            int num = offlineOptService.countMergeData(qryList, strDate, endDate);
            //每10000条数据导入到Excel的一个sheet页
            int times = num / 10000 == 0 || num % 10000 != 0 ? num / 10000 + 1 : num / 10000;
            //第四版: 动态表头以及对应内容；不创建对象；数据分批写入多个sheet
            try (ExcelWriter excelWriter =
                         EasyExcel.write(response.getOutputStream(), MergeOfflineDtl.class)
                                 .build()) {
                for (int page = 0; page < times; page++) {
                    WriteSheet writeSheet = EasyExcel.writerSheet(page, "缴费订单第" + (page + 1) + "批").build();
                    List<MergeOfflineDtl> list = offlineOptService.getMergeDataList(qryList, strDate, endDate, page * 10000);
                    excelWriter.write(list, writeSheet);
                }
            }
        } catch (Exception e) {
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = MapUtils.newHashMap();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            response.getWriter().println(JSON.toJSONString(map));
        }

    }

    /**
     * 缴费明细导出
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("exportPdf")
    private void exportPdf(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        //导出pdf
        createPdf(request, response);

    }

    /**
     * 生成pdf收据
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void createPdf(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String date = DateUtil.getDate("yyyy-MM-dd");
        String selectedData = reqDs.getString("selectedData");
        List<BookList> list = JSONArray.parseArray(selectedData, BookList.class);
        List<Map<String, String>> pdfDatas = new ArrayList<>();
        String busiNo = reqDs.getString("busiNo");
        if (!CollectionUtils.isEmpty(list)) {
            //导出pdf的只能是交易状态为成功的流水
            list.removeIf(item -> !"01".equals(item.getTRAN_STAT()) || "03".equals(item.getTRAN_TP()));
            for (BookList item : list) {

                Set<String> oweMonths = new HashSet<>();
                List<SubList> subList = item.getSUB_LIST();
                if (CollectionUtils.isEmpty(subList)) {
                    continue;
                }
                for (SubList subItem : subList) {
                    oweMonths.add(subItem.getOWE_MONTH());
                }

                for (String oweMonth : oweMonths) {
                    //新建  pdf对应实体类
                    Map<String, String> pdfData = new HashMap<>();
                    pdfData.put("name", StringUtils.isEmpty(item.getNAME()) ? "" : item.getNAME());
                    String stuClass = StringUtils.isEmpty(item.getSTU_CLASS()) ? "" : item.getSTU_CLASS();
                    pdfData.put("stu_CLASS", stuClass);
                    String phoneNo = StringUtils.isEmpty(item.getCTCT_PHONE_NO()) ? "" : item.getCTCT_PHONE_NO();
                    pdfData.put("phone_NO", phoneNo);
                    String platSeq = StringUtils.isEmpty(item.getPLAT_SEQ()) ? "" : item.getPLAT_SEQ();
                    pdfData.put("plat_SEQ", platSeq);
                    String reqDate = item.getREQ_TIME();
                    reqDate = reqDate.substring(0, 4) + "-" + reqDate.substring(4, 6) + "-" + reqDate.substring(6, 8) + "\n" + reqDate.substring(8, 10) + ":" + reqDate.substring(10, 12) + ":" + reqDate.substring(12, 14);
                    pdfData.put("plat_DATE", reqDate);
                    pdfData.put("date", date);
                    String projName = "";
                    double totAmt = 0.00;
                    double prctlAmt = 0.00;

                    for (SubList subItem : subList) {
                        String owe_month = subItem.getOWE_MONTH();
                        String proj_name = subItem.getPROJ_NAME();
                        if (oweMonth.equals(owe_month) && (proj_name.contains("学费")
                                || proj_name.contains("住宿费")
                                || proj_name.contains("餐费"))) {
                            String amt = subItem.getTOT_AMT();
                            String dctAmt = subItem.getDCT_AMT();
                            projName = oweMonth + "\n" + stuClass;
                            //收费项目
                            if (proj_name.contains("学费")) {
                                pdfData.put("tot_AMT1", "" + new BigDecimal(amt).setScale(2, BigDecimal.ROUND_HALF_UP));
                                pdfData.put("dct_AMT1", "-" + new BigDecimal(dctAmt).setScale(2, BigDecimal.ROUND_HALF_UP));
                                prctlAmt = CaculateUtils.sub(Double.parseDouble(amt), Double.parseDouble(dctAmt));
                            } else if (proj_name.contains("住宿费")) {
                                pdfData.put("tot_AMT2", "" + new BigDecimal(amt).setScale(2, BigDecimal.ROUND_HALF_UP));
                                pdfData.put("dct_AMT2", "-" + new BigDecimal(dctAmt).setScale(2, BigDecimal.ROUND_HALF_UP));
                                prctlAmt = CaculateUtils.sub(Double.parseDouble(amt), Double.parseDouble(dctAmt));
                            } else if (proj_name.contains("餐费")) {
                                pdfData.put("tot_AMT3", "" + new BigDecimal(amt).setScale(2, BigDecimal.ROUND_HALF_UP));
                                prctlAmt = Double.parseDouble(amt);
                            }

                            totAmt = CaculateUtils.add(totAmt, prctlAmt);
                        }
                    }
                    pdfData.put("proj_NAME", projName);
                    String busiName = StringUtils.isEmpty(item.getBUSI_NAME()) ? "" : item.getBUSI_NAME();
                    pdfData.put("busi_NAME", busiName);
                    String toChinese = AmountToChinese.convertToChinese(new BigDecimal(totAmt).setScale(2, BigDecimal.ROUND_HALF_UP));
                    pdfData.put("tot_AMT_toUpper", toChinese);
                    pdfData.put("tot_AMT", "" + new BigDecimal(totAmt).setScale(2, BigDecimal.ROUND_HALF_UP));
                    pdfData.put("tot_AMT_toLower", "" + new BigDecimal(totAmt).setScale(2, BigDecimal.ROUND_HALF_UP));
                    pdfDatas.add(pdfData);
                }
            }
        }


        //无数据处理
        if (CollectionUtils.isEmpty(pdfDatas)) {
            Map<String, String> pdfData = new HashMap<>();

            pdfData.put("plat_SEQ", "");
            pdfData.put("busi_NAME", "");


            pdfData.put("name", "");
            pdfData.put("stu_Class", "");
            pdfData.put("phone_NO", "");
            pdfData.put("date", date);

            pdfData.put("tot_AMT", "");
            pdfData.put("proj_NAME", "");
            pdfData.put("tot_AMT1", "");
            pdfData.put("tot_AMT2", "");
            pdfData.put("tot_AMT3", "");
            pdfData.put("dct_AMT1", "");
            pdfData.put("dct_AMT2", "");
            pdfData.put("plat_DATE", "");

            pdfData.put("tot_AMT_toUpper", "");
            pdfData.put("tot_AMT_toLower", "");

            pdfDatas.add(pdfData);

        }
            PdfTemplate pdfTemplate = offlineOptService.getPdfTemplate(busiNo);
        //根据业务编号获取pdf业务名称
        String templateName = pdfTemplate.getTMPL_ID();
        //pdf模板路径
        templateName = System.getenv("HOME") + File.separator + "excel" + File.separator + templateName;
        //打印数据名
        String fileName = "收据打印" + DateUtil.getDate("yyyyMMdd") + DateUtil.getTime() + ".pdf";
        Map<String, String> params = new HashMap<>();
        params.put("templateName", templateName);
        params.put("fileName", fileName);
        PdfUtils.pdfExport(params, pdfDatas, response,2);
    }


    /**
     * 导出缴费明细查询数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/exportExcel")
    public void exportFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        MLppQryBookListReq qryListReq = getQueryParams(reqDs);
        String strDate = reqDs.getString("startTime").replace("-", "");
        String endDate = reqDs.getString("endTime").replace("-", "");
        qryListReq.setSTR_DATE(strDate);
        qryListReq.setEND_DATE(endDate);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("缴费订单" + DateUtil.getDateTime("yyyyMMddHHmmss"), "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        int num = 0;
        try {
            num = offlineOptService.countExcelData(qryListReq);
            //每10000条数据导入到Excel的一个sheet页
            int times = num / 10000 == 0 || num % 10000 != 0 ? num / 10000 + 1 : num / 10000;
            //第四版: 动态表头以及对应内容；不创建对象；数据分批写入多个sheet
            try (ExcelWriter excelWriter =
                         EasyExcel.write(response.getOutputStream(), OfflineBook.class)
                                 .build()) {
                for (int page = 0; page < times; page++) {
                    WriteSheet writeSheet = EasyExcel.writerSheet(page, "缴费订单第" + (page + 1) + "批").build();
                    List<OfflineBook> list = offlineOptService.getAllBookList(qryListReq, page * 10000);
                    excelWriter.write(list, writeSheet);
                }
            }
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

    /**
     * 组装请求参数
     *
     * @param reqDs
     * @return
     */
    private MLppQryBookListReq getQueryParams(IDataset reqDs) {
        String busiNo = reqDs.getString("busiNo");
        String autoDeduct = reqDs.getString("autoDeduct");
        String txStat = reqDs.getString("txStat");
        String payNo = reqDs.getString("payNo");
        MLppQryBookListReq qryListReq = new MLppQryBookListReq();
        qryListReq.setBUSI_NO(busiNo);
        qryListReq.setAUTO_FLG(autoDeduct);
        qryListReq.setTRAN_STAT(StringUtils.isBlank(txStat) ? "AA" : txStat);
        qryListReq.setPAY_NO(payNo);
        return qryListReq;
    }


    /**
     * 动态获取excel表头
     * key：value
     * 'key':'当前字段的数据库名称'  'desc': '字段的中文描述'
     *
     * @param busiNo
     * @return
     */
    private Map<String, List<List<String>>> getHead(String busiNo, String type) {
        Map<String, List<List<String>>> map = new HashMap<>();
        //查询状态为Y的数据字段
        List<Template> allTmplField = offlineOptService.getAllTmplField(busiNo, type);
        //创建存放数据库字段名的list
        List<List<String>> listKey = new ArrayList<>();
        //创建存放中文描述的list
        List<List<String>> listDesc = new ArrayList<>();
        //下载模板展示的默认值
        List<List<String>> listDefaValue = new ArrayList<>();
        //只要导出的时候需要默认展示缴费项目、缴费状态
        if (EXCEL_EXPORT.equals(type)) {
            List<String> projNameKey = new ArrayList<>();
            List<String> projNameDesc = new ArrayList<>();
            projNameKey.add("PROJ_NAME");
            projNameDesc.add("缴费项目");
            listKey.add(projNameKey);
            listDesc.add(projNameDesc);
            List<String> oweMonthKey = new ArrayList<>();
            List<String> oweMonthDesc = new ArrayList<>();
            oweMonthKey.add("OWE_MONTH");
            oweMonthDesc.add("收费周期");
            listKey.add(oweMonthKey);
            listDesc.add(oweMonthDesc);

        }
        //存放下载模板的示例
        List<String> defaultValue = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(allTmplField)) {
            for (Template template : allTmplField) {
                List<String> inListKey = new ArrayList<>();
                List<String> inListDesc = new ArrayList<>();
                inListKey.add(template.getKEY_NO());
                inListDesc.add(template.getKEY_DESC());
                listKey.add(inListKey);
                listDesc.add(inListDesc);
                if (EXCEL_DOWNLOAD.equals(type)) {
                    defaultValue.add(template.getDEFA_VAL());
                }
            }
        }
        if (EXCEL_EXPORT.equals(type)) {
            List<String> dateKey = new ArrayList<>();
            List<String> dateDesc = new ArrayList<>();
            dateKey.add("PLAT_DATE");
            dateDesc.add("缴费日期");
            listKey.add(dateKey);
            listDesc.add(dateDesc);

            List<String> projTpKey = new ArrayList<>();
            List<String> projTpDesc = new ArrayList<>();
            projTpKey.add("PROJ_TP");
            projTpDesc.add("项目类型");
            listKey.add(projTpKey);
            listDesc.add(projTpDesc);

            List<String> statKey = new ArrayList<>();
            List<String> statDesc = new ArrayList<>();
            statKey.add("STAT");
            statDesc.add("缴费状态");
            listKey.add(statKey);
            listDesc.add(statDesc);

        }
        map.put("key", listKey);
        map.put("desc", listDesc);
        if (EXCEL_DOWNLOAD.equals(type)) {
            listDefaValue.add(defaultValue);
            map.put("default", listDefaValue);
        }
        return map;
    }


    /**
     * 发送短信验证码
     *
     * @param request
     * @return
     */
    @RequestMapping("/sendMsg")
    public ServerResponse<ComRespBody<SendMsgRes>> sendMsg(HttpServletRequest request) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String busiNo = reqDs.getString("busiNo");
        String busiName = reqDs.getString("busiName");
        //获取当前登录用户的手机号发送短信
        String phoneNo = UserUtils.getUser().getPhoneNo();
        SendMsgReq sendMsgReq = new SendMsgReq();
        sendMsgReq.setBUSI_NO(busiNo);
        sendMsgReq.setBUSI_NAME(busiName);
        sendMsgReq.setPHONE_NO(phoneNo);
        //短信模板id
        sendMsgReq.setTMPL_ID("SH001");
        SendMsgRes res = offlineOptService.sendMsg(sendMsgReq);
        int total = 1;
        ComRespBody<SendMsgRes> retData = new ComRespBody<SendMsgRes>(0, 10, res, total);
        return ServerResponse.createBySuccess("查询成功", retData);
    }


    /**
     * 短信验证码校验
     *
     * @param request
     * @return
     */
    @RequestMapping("/verifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String vrfyNoCrtId = reqDs.getString("vrfyNoCrtId");
        String vrfyNo = reqDs.getString("vrfyNo");
        String busiNo = reqDs.getString("busiNo");
        String rmrk = reqDs.getString("rmrk");

        //获取当前登录用户的手机号发送短信
        String phoneNo = UserUtils.getUser().getPhoneNo();
        MsgVrfyCodeReq msgVrfyCodeReq = new MsgVrfyCodeReq();
        msgVrfyCodeReq.setBUSI_NO(busiNo);
        msgVrfyCodeReq.setVRFY_NO_CRT_ID(vrfyNoCrtId);
        msgVrfyCodeReq.setVRFY_NO(vrfyNo);
        msgVrfyCodeReq.setPHONE_NO(phoneNo);
        msgVrfyCodeReq.setRMRK(rmrk);
        offlineOptService.verifyCode(msgVrfyCodeReq);
        dtlModify(request, response);
//        setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
    }

    /**
     * 退费前关联订单查询
     *
     * @param request
     * @return
     */
    @RequestMapping("/beforeRefund")
    public ServerResponse<ComRespBody<DtlRefundBeforeRes>> beforeRefund(HttpServletRequest request) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String platDate = reqDs.getString("platDate");
        String platSeq = reqDs.getString("platSeq");
        String busiNo = reqDs.getString("busiNo");
        String addErrFlg = reqDs.getString("msgClob");

        DtlRefundBeforeReq refundBeforeReq = new DtlRefundBeforeReq();
        refundBeforeReq.setBUSI_NO(busiNo);
        refundBeforeReq.setPLAT_DATE(platDate);
        refundBeforeReq.setPLAT_SEQ(platSeq);
        refundBeforeReq.setMSG_CLOB(addErrFlg);

        DtlRefundBeforeRes res = offlineOptService.beforeRefund(refundBeforeReq);
        int total = 1;
        ComRespBody<DtlRefundBeforeRes> retData = new ComRespBody<DtlRefundBeforeRes>(0, 10, res, total);
        return ServerResponse.createBySuccess("查询成功", retData);
    }

    /**
     * 退费操作
     *
     * @param request
     * @return
     */
    @RequestMapping("/refund")
    public void refund(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String busiNo = reqDs.getString("busi_NO");
        String platDate = reqDs.getString("plat_DATE");
        String platSeq = reqDs.getString("plat_SEQ");
        double refundAmt = reqDs.getDouble("prctl_AMT");
        String phoneNo = UserUtils.getUser().getPhoneNo();
        //短信关联码  生成短信验证码接口返回
        String vrfyNoCrtId = reqDs.getString("vrfyNoCrtId");
        //短信验证码
        String vrfyNo = reqDs.getString("vrfyNo");
        DtlRefundReq dtlRefundReq = new DtlRefundReq();
        dtlRefundReq.setBUSI_NO(busiNo);
        dtlRefundReq.setPLAT_DATE(platDate);
        dtlRefundReq.setPLAT_SEQ(platSeq);
        dtlRefundReq.setRFND_AMT(refundAmt);
        dtlRefundReq.setPHONE_NO(phoneNo);
        dtlRefundReq.setVRFY_NO_CRT_ID(vrfyNoCrtId);
        dtlRefundReq.setVRFY_NO(vrfyNo);
//        List<DtlRefundReqList> list = ListUtils.newArrayList();
        DtlRefundReqList dtlRefundReqList = new DtlRefundReqList();
//        list.add(dtlRefundReqList);
//        dtlRefundReq.setLIST(list);
        //如果操作类型是新增 必传
        IDataset resDs = offlineOptService.refund(dtlRefundReq);
        setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");

    }

    /**
     * 按照上次账单导入
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/autoCtrl")
    public ServerResponse<ComRespBody<MissionAutoRes>> autoCtrl(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String busiNo = reqDs.getString("busiNo");
        String busiName = reqDs.getString("busiName");
        String projName = reqDs.getString("projName");
        String oweMonth = reqDs.getString("OWE_MONTH");
        String strDate = reqDs.getString("STR_DATE");
        String endDate = reqDs.getString("END_DATE");
        if(StringUtil.isEmpty(strDate)){
            throw new BaseException(SysErr.E_MESSAGE, "缴费开始日期不能为空");
        }
        if(StringUtil.isEmpty(endDate)){
            throw new BaseException(SysErr.E_MESSAGE, "缴费截止日期不能为空");
        }
        strDate = strDate.substring(0, 4) + strDate.substring(5, 7) + strDate.substring(8);
        endDate = endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8);
        MissionAutoReq missionAutoReq = new MissionAutoReq();
        missionAutoReq.setBUSI_NO(busiNo);
        missionAutoReq.setBUSI_NAME(busiName);
        missionAutoReq.setPROJ_NAME(projName);
        missionAutoReq.setOWE_MONTH(oweMonth);
        missionAutoReq.setSTR_DATE(strDate);
        missionAutoReq.setEND_DATE(endDate);
        MissionAutoRes res = offlineOptService.createAutoCtrl(missionAutoReq);
        int total = res.getTOT_NUM().intValue();
        ComRespBody<MissionAutoRes> retData = new ComRespBody<MissionAutoRes>(0, 10, res, total);
        return ServerResponse.createBySuccess("查询成功", retData);
    }


    /**
     * 获取模板
     *
     * @param request
     * @return
     */
    @RequestMapping("/getTemplate")
    public ServerResponse<List<Template>> getTemplate(HttpServletRequest request) {
        List<Template> list = offlineOptService.getTemplate();
        return ServerResponse.createBySuccess("查询成功", list);
    }


    /**
     * 获取模板字段详情 分页展示
     *
     * @param request
     * @return
     */
    @RequestMapping("/getTmplField")
    public ServerResponse<ComRespBody<List<Template>>> getTmplField(HttpServletRequest request) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        int start = reqDs.getInt("start");
        int limit = reqDs.getInt("limit");
        start = start == 0 ? start : (start - 1) * limit;
        String busiNo = reqDs.getString("busiNo");
        if (StringUtils.isEmpty(busiNo)) {
            throw new BaseException(SysErr.E_MESSAGE, "该机构没有对应的合作商户，请使用合作商户对应的开户机构或上级机构用户进行查看");
        }
        String busiName = reqDs.getString("busiName");
        List<Template> list = offlineOptService.getTmplField(busiNo, start, limit);
        if (CollectionUtils.isEmpty(list)) {
            offlineOptService.addTmplFields(busiNo, busiName);
            list = offlineOptService.getTmplField(busiNo, start, limit);
        }
        int total = offlineOptService.countTmplField(busiNo, "", "");
        ComRespBody<List<Template>> retData = new ComRespBody<List<Template>>(start, limit, list, total);
        return ServerResponse.createBySuccess("查询成功", retData);
    }

    /**
     * 获取所有明细查询展示字段
     *
     * @param request
     * @return
     */
    @RequestMapping("/getAllTmplField")
    public ServerResponse<List<Template>> getAllTmplField(HttpServletRequest request) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String busiNo = reqDs.getString("busiNo");
        List<Template> list = offlineOptService.getAllTmplField(busiNo, EXCEL_EXPORT);
        return ServerResponse.createBySuccess("查询成功", list);
    }

    /**
     * 模板字段维护
     *
     * @param request
     * @return
     */
    @RequestMapping("/tmpl_modify")
    public ServerResponse<String> tmplModify(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String busiNo = reqDs.getString("busiNo");
        String busiName = reqDs.getString("busiName");
        String keyNo = reqDs.getString("keyNo");
        String keyDesc = reqDs.getString("keyDesc");
        String operTp = reqDs.getString("operTp");
        //用于标识stat是修改模板下载的状态还是明细查询的状态   query-明细查询   excel-模板下载
        String queryStat = reqDs.getString("queryStat");
        String excelStat = reqDs.getString("excelStat");
        //如果是新增
        if ("1".equals(operTp)) {
            int isExist = offlineOptService.countTmplField(busiNo, keyNo, "");
            if (isExist < 1) {
                int ser = offlineOptService.countTmplField(busiNo, "", "");
                offlineOptService.addTmplField(busiNo, busiName, ++ser, keyNo, keyDesc, queryStat, excelStat);
            } else {
                return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "该字段已存在,不可重复添加!");
            }
            return ServerResponse.createBySuccessMessage("新增成功!");
        } else {
            offlineOptService.changeTmplStat(busiNo, keyNo, queryStat, excelStat);
            return ServerResponse.createBySuccessMessage("状态修改成功!");
        }
    }

    /**
     * 获取商户列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/merQry")
    public ServerResponse<ComRespBody<List<MerInfo>>> merQry(HttpServletRequest request) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        int start = reqDs.getInt("start");
        int limit = 0 == reqDs.getInt("limit") ? 10 : reqDs.getInt("limit");
        start = (start - 1) * limit;
        String busiNo = reqDs.getString("busiNo");
        String busiName = reqDs.getString("busiName");
        String officeId = reqDs.getString("officeId");
//        officeId = officeId.equals("1") ? "600001" : officeId;
        Map<String, String> reqBody = new HashMap<>();
        reqBody.put("busiNo", busiNo);
        reqBody.put("busiName", busiName);
        reqBody.put("officeId", officeId);
        int total = offlineOptService.countMerList(reqBody);
        List<MerInfo> list = offlineOptService.getMerList(reqBody, start, limit);
        Map<String, Object> res = new HashMap<>();
//        res.put("total", total);
//        res.put("list", list);
        ComRespBody<List<MerInfo>> retData = new ComRespBody<>(start, limit, list, total);
        return ServerResponse.createBySuccess("查询成功", retData);
    }


    /**
     * 商户修改
     *
     * @param request
     * @return
     */
    @RequestMapping("/merModify")
    public ServerResponse<JSONObject> merModify(HttpServletRequest request) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        int start = reqDs.getInt("start");
        int limit = 0 == reqDs.getInt("limit") ? 10 : reqDs.getInt("limit");
        //操作种类  1-新增 2-修改
        String operStat = reqDs.getString("operStat");
        String acct = reqDs.getString("payAcct");
        //判断输入的清算账户是否合法  如果合法返回true
        boolean flg = offlineOptService.checkAcct(acct);
        if(!flg){
            if (operStat.equals("1")) {
                return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "清算账户不存在,新增失败");
            }else{
                return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "清算账户不存在,修改失败");
            }
        }
//        boolean rightAcctFlg = offlineOptService.validateAcct(payAcct);
        int rec = 0;
        try{
             rec = offlineOptService.modifyMerchan(reqDs, start, limit);
        }catch (Exception e){
            if(e.getMessage().contains("唯一性约束")){
                return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "商户号已存在,新增失败");
            }
            if(operStat.equals("1")){
                return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "新增商户失败" + e.getMessage());
            }else{
                return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "修改商户失败" + e.getMessage());
            }
        }
        if (rec == 0) {
            if(operStat.equals("1")){
                return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE,"新增商户失败");
            }else{
                return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE,"修改商户失败");
            }
        }
        if(operStat.equals("1")){
            return ServerResponse.createBySuccessMessage("新增商户成功");
        }else{
            return ServerResponse.createBySuccessMessage("修改商户成功");
        }
    }


    /**
     * 批量删除
     *
     * @param request
     * @return
     */
    @RequestMapping("/batDel")
    public ServerResponse<JSONObject> batDel(HttpServletRequest request) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        int rec = 0;
        try{
            //查询是否存在已缴费明细
            rec = offlineOptService.countSuccDtlList(reqDs);
            if(rec == 0 ){
                offlineOptService.batDelDtl(reqDs);
            }else{
                return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "已存在已缴费的缴费明细,不可批量删除");
            }
        }catch (Exception e){
            return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "批量删除失败 ：" + e.getMessage());
        }

        return ServerResponse.createBySuccessMessage("批量删除成功");

    }



    /**
     * 根据收费周期批量短信通知
     *
     * @param request
     * @return
     */
    @RequestMapping("/smsBatNotice")
    public void smsBatNotice(HttpServletRequest request,HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String busiNo = reqDs.getString("busiNo");
        String projName = reqDs.getString("projName");
        String oweMonth = reqDs.getString("oweMonth");
        MissionBatNoticeReq req = new MissionBatNoticeReq();
        String phoneNo = offlineOptService.getPhoneNo(busiNo);
        req.setBUSI_NO(busiNo);
        req.setPROJ_NAME(projName);
        req.setOWE_MONTH(oweMonth);
        req.setPHONE_NO(phoneNo);
        IDataset resDs = offlineOptService.smsBatNotice(req);
        setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "一键通知成功");
    }

    /**
     * 汇总缴费明细笔数  判断商户是否能够修改
     *
     * @param request
     * @return
     */
    @RequestMapping("/countDtl")
    public ServerResponse<ComRespBody<Integer>> countDtl(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String busiNo = reqDs.getString("busiNo");
        int rec = offlineOptService.countDtl(busiNo);
        ComRespBody<Integer> retData = new ComRespBody<>(0, 10, rec, 10);
        return ServerResponse.createBySuccess("查询成功", retData);
    }

    /**
     * 商户入账excel导出
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/expExcel")
    public void expExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String busiNo = reqDs.getString("busiNo");
        RecSumQryReq recSumQryReq = new RecSumQryReq();
        recSumQryReq.setBUSI_NO(busiNo);
        RecSumQryRes res = null;
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = "MerchansBill" + System.currentTimeMillis();
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        String message = "";
        try {
            res = recQryService.getTotList(reqDs, recSumQryReq, 1, 1000);
        } catch (Exception e) {
            if(e.getMessage().contains("清算任务尚未处理")){
                message = "清算任务尚未处理,请稍后再试";
            }else if(e.getMessage().contains("清算过程出现异常")){
                message = "清算过程出现异常,请稍后再试";
            }else if(e.getMessage().contains("清算正在处理中")){
                message = "清算任务尚未处理,请稍后再试";
            }
        }
        List<ErrExcel> errExcelList = new ArrayList<>();
        List<AccountExcelData> list = new ArrayList<>();
        ErrExcel errExcel = new ErrExcel();
        //如果报错信息为空 则当日清算已完成
        if(StringUtils.isEmpty(message)){
            AccountExcelData data = getData(res);
            //如果res中的list为空 则证明对账日期内未发生交易
            if(StringUtil.isEmpty(data.getBUSI_NO())){
                errExcel.setERR_MESSAGE("清算日期内无交易发生");
                errExcelList.add(errExcel);
                EasyExcel.write(response.getOutputStream(), ErrExcel.class).sheet("商户入账单").doWrite(errExcelList);
            }else{
                list.add(data);
                EasyExcel.write(response.getOutputStream(),AccountExcelData.class).sheet("商户入账单").doWrite(list);
            }
        }else{
            //如果报错信息不为空 下载excel内只含有提示信息   之所以这样处理是因为下载excel使用的submit提交
            // 如果抛出异常或者其他 都会跳转到另一个错误页面 导致页面风格不统一
            errExcel.setERR_MESSAGE(message);
            errExcelList.add(errExcel);
            EasyExcel.write(response.getOutputStream(), ErrExcel.class).sheet("商户入账单").doWrite(errExcelList);
        }

    }

    private AccountExcelData getData(RecSumQryRes res) {
        AccountExcelData data = new AccountExcelData();
        List<RecSumQryResList> list = res.getLIST();
        data.setSTR_DATE(res.getSTR_DATE());
        data.setEND_DATE(res.getEND_DATE());
        if(CollectionUtils.isEmpty(list)){
            return data;
        }
        RecSumQryResList qryResList = list.get(0);
        data.setBUSI_NO(qryResList.getBUSI_NO());
        data.setBUSI_NAME(qryResList.getBUSI_NAME());
        data.setCLR_DATE(qryResList.getCLR_DATE());
        data.setTOT_NUM(qryResList.getTOT_NUM());
        data.setTOT_AMT(qryResList.getTOT_AMT());
        data.setRFND_AMT(qryResList.getRFND_AMT());
        data.setCLR_AMT(qryResList.getCLR_AMT());
        data.setDCT_BANK_AMT(qryResList.getDCT_BANK_AMT());
        data.setDCT_MERT_AMT(qryResList.getDCT_MERT_AMT());
        data.setFEE_AMT(qryResList.getFEE_AMT());
        data.setOTH_AMT(qryResList.getOTH_AMT());
        data.setMUAL_AMT(qryResList.getMUAL_AMT());
        return data;
    }

    /**
     *
     * 商户信息导出
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("exportMerData")
    private void exportMerData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String busiNo = reqDs.getString("busiNo");
        String busiName = reqDs.getString("busiName");
        String officeId = reqDs.getString("officeId");

        Map<String, String> reqBody = new HashMap<>();
        reqBody.put("busiNo", busiNo);
        reqBody.put("busiName", busiName);
        reqBody.put("officeId", officeId);
        List<MerInfo> list = offlineOptService.getMerList(reqBody, 0, 10000);
        for (MerInfo merInfo : list) {
            String payInfo = merInfo.getPAY_INFO();
            String[] split = payInfo.split("\\|");
            merInfo.setPAY_INFO(split[0].equals("Y") ? "是" :"否");
            merInfo.setCLR_TP(split[0].equals("Y") ? "T1清算" :"D1清算");
            merInfo.setOPEN_STAT(merInfo.getOPEN_STAT().equals("Y") ? "已上架" : "已下架");
        }
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("商户信息" + System.currentTimeMillis(), "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(),MerInfo.class).sheet("商户信息").doWrite(list);

    }


    /**
     * 获取数据查询列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/dataQry")
    public ServerResponse<ComRespBody<JSONObject>> dataQry(HttpServletRequest request) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        int start = reqDs.getInt("start");
        int limit = 0 == reqDs.getInt("limit") ? 10 : reqDs.getInt("limit");
        start = (start - 1) * limit;
        String officeId = reqDs.getString("officeId");
        String strDate = reqDs.getString("strDate");
        String endDate = reqDs.getString("endDate");
        strDate = strDate.replaceAll("-", "");
        endDate = endDate.replaceAll("-", "");
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("officeId", officeId);
        reqBody.put("strDate", strDate);
        reqBody.put("endDate", endDate);
        //根据机构号数组获取所有业务编号
        if(!StringUtils.isEmpty(officeId)){
            List<String> busiNoList = comQueryService.getBusiNoByOfficeId(Constant.SHORT_RMRK_SCHOOL,officeId);
            reqBody.put("busiNoList", busiNoList);
        }
        List<String> totalInfo =  offlineOptService.countDataInfo(reqBody);
        List<DataInfo> list = offlineOptService.getDataList(reqBody, start, limit);
        JSONObject  res = new JSONObject();
        res.put("list", list);
        res.put("totNum",totalInfo.get(1) );
        res.put("totAmt",totalInfo.get(2) );
        ComRespBody<JSONObject> retData = new ComRespBody<>(start, limit, res, Integer.valueOf(totalInfo.get(0)));
        return ServerResponse.createBySuccess("查询成功", retData);
    }


    /**
     *
     * 数据查询信息导出
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("exportTotData")
    private void exportTotData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String officeId = reqDs.getString("officeId");
        String strDate = reqDs.getString("strDate");
        String endDate = reqDs.getString("endDate");
        strDate = strDate.replaceAll("-", "");
        endDate = endDate.replaceAll("-", "");
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("officeId", officeId);
        reqBody.put("strDate", strDate);
        reqBody.put("endDate", endDate);
        //根据机构号数组获取所有业务编号
        if(!StringUtils.isEmpty(officeId)){
            List<String> busiNoList = comQueryService.getBusiNoByOfficeId(Constant.SHORT_RMRK_SCHOOL,officeId);
            reqBody.put("busiNoList", busiNoList);
        }
        List<DataInfo> list = offlineOptService.getDataList(reqBody);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("数据查询信息" + System.currentTimeMillis(), "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(),DataInfo.class).sheet("数据查询信息").doWrite(list);

    }
}
