package com.adtec.pay.service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.pay.dao.OfflineDao;
import com.adtec.pay.dto.CheckAcctReq;
import com.adtec.pay.dto.CheckAcctRes;
import com.adtec.pay.dto.NoReturnRes;
import com.adtec.pay.dto.bookList.MLppQryBookListReq;
import com.adtec.pay.dto.bookList.OfflineBook;
import com.adtec.pay.dto.offline.DataInfo;
import com.adtec.pay.dto.offline.MerInfo;
import com.adtec.pay.dto.offline.MergeOfflineDtl;
import com.adtec.pay.dto.offline.detail.*;
import com.adtec.pay.dto.offline.mission.*;
import com.adtec.pay.dto.offline.proj.ProjModifyReq;
import com.adtec.pay.dto.offline.proj.ProjQryReq;
import com.adtec.pay.dto.offline.proj.ProjQryRes;
import com.adtec.pay.dto.template.PdfTemplate;
import com.adtec.pay.entity.OfflineDO;
import com.adtec.pay.entity.ProjDo;
import com.adtec.pay.entity.Template;
import com.adtec.pay.utils.MLppUtils;
import com.adtec.pay.utils.MultipartFileToFileUtils;
import com.adtec.pay.utils.ParamUtils;
import com.adtec.sys.modules.sys.utils.StringUtil;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

@Service
public class OfflineOptService {
    private final static Logger logger = LoggerFactory.getLogger(OfflineOptService.class);

    @Autowired
    private OfflineDao offlineDao;


    /**
     * 保存文件到服务器中
     *
     * @param file
     * @return
     */
    public DtlImportRes fileImport(HttpServletRequest request, MultipartFile file) {
        if (file == null || file.getSize() == 0) {
            throw new BaseException(SysErr.E_MESSAGE, "请选择上传的账单文件!");
        }
        //上传文件到文件服务器指定目录
        String fileName = saveFile(request, file);
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String busiNo = reqDs.getString("BUSI_NO");
        String busiName = reqDs.getString("BUSI_NAME");
        String projName = reqDs.getString("PROJ_NAME");
        String oweMonth = reqDs.getString("oweMonth");
        String strDate = reqDs.getString("STR_DATE");
        String endDate = reqDs.getString("END_DATE");
        if (StringUtil.isEmpty(strDate)) {
            throw new BaseException(SysErr.E_MESSAGE, "缴费开始日期不能为空");
        }
        if (StringUtil.isEmpty(endDate)) {
            throw new BaseException(SysErr.E_MESSAGE, "缴费截止日期不能为空");
        }
        strDate = strDate.length() == 8 ? strDate : strDate.substring(0, 4) + strDate.substring(5, 7) + strDate.substring(8);
        endDate = endDate.length() == 8 ? endDate : endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8);

        //导入的都是  非自主录入的
        String projTp = "00";
        //报文请求字段赋值
        DtlImportReq dtlImportReq = new DtlImportReq();
        dtlImportReq.setBUSI_NO(busiNo);
        dtlImportReq.setBUSI_NAME(busiName);
        dtlImportReq.setPROJ_NAME(projName);
        dtlImportReq.setFILE_NAME(fileName);
        dtlImportReq.setOWE_MONTH(oweMonth);
        dtlImportReq.setSTR_DATE(strDate);
        dtlImportReq.setEND_DATE(endDate);
        dtlImportReq.setPROJ_TP(projTp);
        ReqDTO reqDTO = new ReqDTO(dtlImportReq);
        MLppUtils.setReqHead(busiNo, reqDTO, "MLppOfflineFileImport");
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("MLPP_PARTID"), "MLppOfflineFileImport", reqDTO, DtlImportRes.class, null);
        DtlImportRes res = (DtlImportRes) resDTO.getBODY();
        return res;
    }

    /**
     * 将文件推送到文件服务器指定目录
     *
     * @param request
     * @param file
     * @return
     */
    private String saveFile(HttpServletRequest request, MultipartFile file) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String busiNo = reqDs.getString("BUSI_NO");
        String projName = reqDs.getString("PROJ_NAME");
        //解决文件名称中文乱码问题
        String encodeFileName = "";
        try {
            encodeFileName = URLEncoder.encode(file.getOriginalFilename(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("文件名称转换异常");
        }
        String fileName = DateUtil.getDateTime("yyyyMMddHHmmss") + "_" + encodeFileName;
//        String homePath = File.separator + "home" + File.separator + "zhudi";
        //文件详细路径
        String filePath = "uploadFile" + File.separator + DateUtil.getDate() + File.separator
                + UserUtils.getUser().getLoginName()
                + File.separator + busiNo;
        //服务器路径名
//        String uploadFileName = homePath + File.separator + filePath + File.separator + fileName;
        String uploadFileName = filePath + File.separator + fileName;
        FTPClient ftpClient = new FTPClient();
        InputStream inputStream = null;
        try {
            //连接ftp服务器  从数据库中取配置
            String ftpIp = ParamUtils.getParams("FTP_IP").getPARA_VAL();
            String ftpPort = ParamUtils.getParams("FTP_PORT").getPARA_VAL();
            String ftpUser = ParamUtils.getParams("FTP_USER").getPARA_VAL();
            String ftpPwd = ParamUtils.getParams("FTP_PWD").getPARA_VAL();
            ftpClient.connect(ftpIp, Integer.parseInt(ftpPort));
            //使用被动模式
            ftpClient.enterLocalPassiveMode();
            //登录ftp服务器
            ftpClient.login(ftpUser, ftpPwd);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            //切换到 上传文件夹
            String[] paths = filePath.split(Matcher.quoteReplacement(File.separator));
            for (String path : paths) {
                if (!ftpClient.changeWorkingDirectory(path)) {
                    ftpClient.makeDirectory(path);
                    ftpClient.changeWorkingDirectory(path);
                }
            }
//            File uploadFile = MultipartFileToFileUtils.multipartFileToFile(file);
            File uploadFile = MultipartFileToFileUtils.saveFile(file,busiNo);
            inputStream = new FileInputStream(uploadFile);
            ftpClient.storeFile(fileName, inputStream);
            inputStream.close();
            ftpClient.logout();
            ftpClient.disconnect();
        } catch (IOException e) {
            throw new BaseException(SysErr.E_IO_ERROR, "上传文件到文件服务器失败");
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("输入流关闭异常");
                }
            }
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                logger.error("ftp断开连接失败");
            }
        }
        return uploadFileName;
    }

//    /**
//     * 保存导入文件到本地并解析入库
//     *
//     * @param request
//     */
//    public void importFile(HttpServletRequest request) {
//        IDataset reqDs = DatasetService.getInstace().getDataset(request);
//        String busiNo = reqDs.getString("BUSI_NO");
//        String busiName = reqDs.getString("BUSI_NAME");
//        String projectName = reqDs.getString("PROJ_NAME");
//        String projTp = reqDs.getString("PROJ_TP");
////        String oweMonth = reqDs.getString("oweMonth");
//        String oweMonth = reqDs.getString("oweMonth");
//        String projDesc = reqDs.getString("PROJ_DESC");
//        String fileName = reqDs.getString("FILE_NAME");
//        //设置状态为成功
//        String stat = "01";
//        try {
//            offlineDao.insertMission(busiNo, busiName, projectName, projTp, oweMonth, projDesc);
//        } catch (Exception e) {
//            throw e;
//        }
//        try {
//            //插入任务控制数据
//            EasyExcel.read(fileName, OfflineDO.class,
//                    new ReadDataListener(offlineDao, busiNo, busiName, projectName, projTp, oweMonth)).sheet().doRead();
//        } catch (Exception e) {
//            stat = "02";
////            throw e;
//        } finally {
//            //不论成功与否 更新任务控制表的状态
//            offlineDao.updateMission(busiNo, projectName, oweMonth, stat);
//        }
//    }


    /**
     * 获取缴费项列表
     *
     * @param projQryReq
     * @param start
     * @param limit
     * @return
     */
    public ProjQryRes getProjList(ProjQryReq projQryReq, int start, int limit) {
        String busiNo = projQryReq.getBUSI_NO();
        ReqDTO reqDTO = new ReqDTO(projQryReq);
        MLppUtils.setReqHead(busiNo, reqDTO, "MLppOfflineProjQry", start, limit);
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        // 和Starring V6进行通信
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("MLPP_PARTID"), "MLppOfflineProjQry",
                reqDTO, ProjQryRes.class, null);
        ProjQryRes projQryRes = (ProjQryRes) resDTO.getBODY();
        return projQryRes;
    }

    /**
     * 获取缴费明细列表
     *
     * @param dtlQryReq
     * @param start
     * @param limit
     * @return
     */
    public DtlQryRes getDtlList(DtlQryReq dtlQryReq, int start, int limit) {
        String busiNo = dtlQryReq.getBUSI_NO();
        ReqDTO reqDTO = new ReqDTO(dtlQryReq);
        MLppUtils.setReqHead(busiNo, reqDTO, "MLppOfflinePayDtlQry", start, limit);
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        // 和Starring V6进行通信
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("MLPP_PARTID"), "MLppOfflinePayDtlQry",
                reqDTO, DtlQryRes.class, null);
        DtlQryRes dtlQryRes = (DtlQryRes) resDTO.getBODY();
        return dtlQryRes;
    }

    /**
     * 缴费项修改
     *
     * @param req
     * @return
     */
    public IDataset callProjModify(ProjModifyReq req) {
        ReqDTO reqDto = new ReqDTO(req);
        String busiNo = req.getBUSI_NO();
        MLppUtils.setReqHead(busiNo, reqDto, "MLppOfflineProjMod");
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("MLPP_PARTID"), "MLppOfflineProjMod", reqDto, NoReturnRes.class, null);
        NoReturnRes res = new NoReturnRes();
        if (null != resDTO.getBODY()) {
            res = (NoReturnRes) resDTO.getBODY();
        }
        IDataset responseData = DatasetService.getInstace().getDataset(res, NoReturnRes.class);
        return responseData;
    }

    public IDataset callDtlModify(DtlModifyReq req) {
        ReqDTO reqDto = new ReqDTO(req);
        String busiNo = req.getBUSI_NO();
        MLppUtils.setReqHead(busiNo, reqDto, "MLppOfflinePayDtlMod");
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("MLPP_PARTID"), "MLppOfflinePayDtlMod", reqDto, NoReturnRes.class, null);
        NoReturnRes res = new NoReturnRes();
        if (null != resDTO.getBODY()) {
            res = (NoReturnRes) resDTO.getBODY();
        }
        IDataset responseData = DatasetService.getInstace().getDataset(res, NoReturnRes.class);
        return responseData;
    }

    /**
     * 报表查询列表返回
     *
     * @param req
     * @param start
     * @param limit
     * @return
     */
    public MissionQryRes getMissionList(MissionQryReq req, int start, int limit) {
        String busiNo = req.getBUSI_NO();
        ReqDTO reqDTO = new ReqDTO(req);
        MLppUtils.setReqHead(busiNo, reqDTO, "MLppOfflineProjReportQry", start, limit);
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        // 和Starring V6进行通信
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("MLPP_PARTID"), "MLppOfflineProjReportQry",
                reqDTO, MissionQryRes.class, null);
        MissionQryRes res = (MissionQryRes) resDTO.getBODY();
        return res;
    }

    public List<ProjDo> projList(String busiNo) {
        return offlineDao.getProjList(busiNo);
    }

    /**
     * @param busiNo
     * @param projName
     * @param stat
     * @param payNo
     * @param name
     * @param phoneNo
     * @param strDate
     * @param endDate
     * @param listKey
     * @param page      页数
     * @param fieldName
     * @param fieldVal
     * @return
     */
    public List<List<Object>> getAllData(String busiNo, String projName, String stat, String payNo,
                                         String name, String phoneNo, String strDate, String endDate, String oweMonth, String projTp,
                                         List<List<String>> listKey, int page, String fieldName, String fieldVal) {
        return offlineDao.getAllData(busiNo, projName, stat, payNo, name, phoneNo, strDate, endDate, oweMonth, projTp, listKey, page, fieldName, fieldVal);
    }

    /**
     * 生成短信验证码
     *
     * @param req
     * @return
     */
    public SendMsgRes sendMsg(SendMsgReq req) {
        String busiNo = req.getBUSI_NO();
        ReqDTO reqDTO = new ReqDTO(req);
        MLppUtils.setReqHead(busiNo, reqDTO, "MsgGenVrfyCode");
        reqDTO.getAPP_HEAD().setTLR_NO("");
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        // 和Starring V6进行通信
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("FACT_PARTID"), "MsgGenVrfyCode",
                reqDTO, SendMsgRes.class, null);
        SendMsgRes res = (SendMsgRes) resDTO.getBODY();
        return res;
    }

    /**
     * 退费
     *
     * @param req
     * @return
     */
    public IDataset refund(DtlRefundReq req) {
        ReqDTO reqDto = new ReqDTO(req);
        String busiNo = req.getBUSI_NO();
        MLppUtils.setReqHead(busiNo, reqDto, "MLppPayRefund");
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("MLPP_PARTID"), "MLppPayRefund", reqDto, NoReturnRes.class, null);
        NoReturnRes res = new NoReturnRes();
        if (null != resDTO.getBODY()) {
            res = (NoReturnRes) resDTO.getBODY();
        }
        IDataset responseData = DatasetService.getInstace().getDataset(res, NoReturnRes.class);
        return responseData;
    }


    public IDataset verifyCode(MsgVrfyCodeReq req) {
        ReqDTO reqDto = new ReqDTO(req);
        String busiNo = req.getBUSI_NO();
        MLppUtils.setReqHead(busiNo, reqDto, "MsgVrfyCode");
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("FACT_PARTID"), "MsgVrfyCode", reqDto, NoReturnRes.class, null);
        NoReturnRes res = new NoReturnRes();
        if (null != resDTO.getBODY()) {
            res = (NoReturnRes) resDTO.getBODY();
        }
        IDataset responseData = DatasetService.getInstace().getDataset(res, NoReturnRes.class);
        return responseData;
    }

    /**
     * 按照上次账单导入
     *
     * @param req
     * @return
     */
    public MissionAutoRes createAutoCtrl(MissionAutoReq req) {
        String busiNo = req.getBUSI_NO();
        ReqDTO reqDTO = new ReqDTO(req);
        MLppUtils.setReqHead(busiNo, reqDTO, "MLppOfflineAutoCrtDtl");
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        // 和Starring V6进行通信
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("MLPP_PARTID"), "MLppOfflineAutoCrtDtl",
                reqDTO, MissionAutoRes.class, null);
        MissionAutoRes missionAutoRes = (MissionAutoRes) resDTO.getBODY();
        return missionAutoRes;
    }


    public List<Template> getTemplate() {
        return offlineDao.getTemplate();
    }

    /**
     * 分页返回所有模板字段
     *
     * @param busiNo
     * @param start
     * @param limit
     * @return
     */
    public List<Template> getTmplField(String busiNo, int start, int limit) {
        return offlineDao.getTmplField(busiNo, start, limit);
    }

    /**
     * 计算某业务的所有模板字段个数
     *
     * @param busiNo
     * @param keyNo
     * @param keyDesc
     * @return
     */
    public int countTmplField(String busiNo, String keyNo, String keyDesc) {
        return offlineDao.countTmplField(busiNo, keyNo, keyDesc);
    }

    /**
     * 修改业务模板字段的启用状态
     *
     * @param busiNo
     * @param keyNo
     * @param queryStat
     * @param excelStat
     * @return
     */
    public int changeTmplStat(String busiNo, String keyNo, String queryStat, String excelStat) {
        return offlineDao.updateTmplStat(busiNo, keyNo, queryStat, excelStat);
    }

    /**
     * 动态获取模板或明细查询字段
     *
     * @param busiNo
     * @param type
     * @return
     */
    public List<Template> getAllTmplField(String busiNo, String type) {
        return offlineDao.getAllTmplField(busiNo, type);
    }

    public int countExportData(String busiNo, String projName, String stat, String payNo, String name, String phoneNo, String strDate, String endDate, String oweMonth, String projTp, String fieldName, String fieldVal) {
        return offlineDao.countExportData(busiNo, projName, stat, payNo, name, phoneNo, strDate, endDate, oweMonth, projTp, fieldName, fieldVal);
    }

    /**
     * 退费前关联订单查询
     *
     * @param req
     * @return
     */
    public DtlRefundBeforeRes beforeRefund(DtlRefundBeforeReq req) {
        String busiNo = req.getBUSI_NO();
        ReqDTO reqDTO = new ReqDTO(req);
        MLppUtils.setReqHead(busiNo, reqDTO, "MLppOfflineBeforeRefundQry");
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        // 和Starring V6进行通信
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("MLPP_PARTID"), "MLppOfflineBeforeRefundQry",
                reqDTO, DtlRefundBeforeRes.class, null);
        DtlRefundBeforeRes res = (DtlRefundBeforeRes) resDTO.getBODY();
        return res;
    }

    /**
     * 初始化指定业务的模板
     *
     * @param busiNo
     * @param busiName
     */
    public void addTmplFields(String busiNo, String busiName) {
        offlineDao.addTmplFields(busiNo, busiName);
    }

    /**
     * 新增模板字段
     *
     * @param busiNo
     * @param busiName
     * @param keyNo
     * @param keyDesc
     * @param queryStat
     * @param excelStat
     */
    public void addTmplField(String busiNo, String busiName, int ser, String keyNo, String keyDesc, String queryStat, String excelStat) {
        offlineDao.addTmplField(busiNo, busiName, ser, keyNo, keyDesc, queryStat, excelStat);
    }


    /**
     * 导出 记录的查询
     *
     * @param qryListReq
     * @param page
     * @return
     */
    public List<OfflineBook> getAllBookList(MLppQryBookListReq qryListReq, int page) {
        List<OfflineBook> list = offlineDao.findAllBook(qryListReq, page);
        // 设置分页参数
        return list;
    }

    /**
     * 统计导出记录的excel数量
     *
     * @param qryListReq
     * @return
     */
    public int countExcelData(MLppQryBookListReq qryListReq) {
        return offlineDao.countExcelData(qryListReq);
    }

    /**
     * 根据业务编号获取pdf模板
     *
     * @param busiNo
     */
    public PdfTemplate getPdfTemplate(String busiNo) {
        return offlineDao.getPdfTemplate(busiNo);
    }

    /**
     * 获取合并缴费明细条数
     *
     * @param qryList
     * @param strDate
     * @param endDate
     * @return
     */
    public int countMergeData(OfflineDO qryList, String strDate, String endDate) {
        return offlineDao.countMergeData(qryList, strDate, endDate);
    }

    /**
     * 获取合并缴费明细
     *
     * @param qryList
     * @param strDate
     * @param endDate
     * @param i
     * @return
     */
    public List<MergeOfflineDtl> getMergeDataList(OfflineDO qryList, String strDate, String endDate, int i) {
        return offlineDao.getMergeDataList(qryList, strDate, endDate, i);
    }


    /**
     * 按照上次账单导入
     *
     * @param acct
     * @return
     */
    public boolean checkAcct(String acct) {
        CheckAcctReq req = new CheckAcctReq();
        req.setACCT(acct);
        ReqDTO reqDTO = new ReqDTO(req);
        MLppUtils.setReqHead(reqDTO, "MLppAcctConfm");
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        // 和Starring V6进行通信
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("MLPP_PARTID"), "MLppAcctConfm",
                reqDTO, CheckAcctRes.class, null);
        CheckAcctRes res = (CheckAcctRes) resDTO.getBODY();
        return res.getFLG().equals("Y");
    }

    /**
     * 获取商户列表
     *
     * @param reqBody
     * @param start
     * @param limit
     * @return
     */
    public List<MerInfo> getMerList(Map<String, String> reqBody, int start, int limit) {
        return offlineDao.getMerList(reqBody, start, limit);
    }

    /**
     * 获取商户总数
     *
     * @param reqBody
     * @return
     */
    public int countMerList(Map<String, String> reqBody) {
        return offlineDao.countMerList(reqBody);
    }

    public int modifyMerchan(IDataset reqDs, int start, int limit) {

        List<String> list = offlineDao.getMaxSer();
        Map<String, Object> req = buildReqBody(reqDs, list);
        return offlineDao.modifyMerchan(req, start, limit);
    }

    private Map<String, Object> buildReqBody(IDataset reqDs, List<String> list) {

        HashMap<String, Object> req = new HashMap<>();
        String item = list.get(0);
        long entrNo = Long.parseLong(item);
        entrNo++;
        String item1 = list.get(1);
        long busiPara1 = Long.parseLong(item1);
        busiPara1++;
        long busiPara2 = Long.parseLong("2" + String.valueOf(busiPara1).substring(1));
        long busiPara3 = Long.parseLong("3" + String.valueOf(busiPara1).substring(1));
        long busiPara4 = Long.parseLong("4" + String.valueOf(busiPara1).substring(1));

        req.put("entrNo", String.valueOf(entrNo));
        req.put("busiPara1", String.valueOf(busiPara1));
        req.put("busiPara2", String.valueOf(busiPara2));
        req.put("busiPara3", String.valueOf(busiPara3));
        req.put("busiPara4", String.valueOf(busiPara4));


        String busiNo = reqDs.getString("busiNo");
        String busiName = reqDs.getString("busiName");
        String officeId = reqDs.getString("officeId");
        officeId = officeId.equals("1") ? "600001" : officeId;
        String officeName = reqDs.getString("officeName");
        String payAcct = reqDs.getString("payAcct");
        String payAcctName = reqDs.getString("payAcctName");
        // 是否为T1清算 | 是否支持拆分缴费
        String payInfo = reqDs.getString("clrCycle") + "|" + reqDs.getString("sepaFlg");
        String phoneNo = reqDs.getString("phoneNo");
        String name = reqDs.getString("name");
        String openStat = reqDs.getString("openStat");
        //操作状态 1-新增  2-修改
        String operStat = reqDs.getString("operStat");
        String addr = reqDs.getString("provinceName") + "-"
                + reqDs.getString("city") + "-"
                + reqDs.getString("detailedAddress");


        req.put("busiNo", busiNo);
        req.put("busiName", busiName);
        req.put("brchId", officeId);
        req.put("brchName", officeName);
        req.put("acct", payAcct);
        req.put("acctName", payAcctName);
        req.put("payInfo", payInfo);
        req.put("phoneNo", phoneNo);
        req.put("name", name);
        req.put("openStat", openStat);
        req.put("operStat", operStat);
        req.put("addr", addr);

        return req;
    }

    /**
     * 验证是否存在已缴费明细
     *
     * @param reqDs
     * @return
     */
    public int countSuccDtlList(IDataset reqDs) {
        return offlineDao.countSuccDtlList(reqDs);
    }

    /**
     * 批量删除
     *
     * @param reqDs
     */
    public int batDelDtl(IDataset reqDs) {
        return offlineDao.batDelDtl(reqDs);
    }

    /**
     * 一键短信通知
     *
     * @param req
     * @return
     */
    public IDataset smsBatNotice(MissionBatNoticeReq req) {
        String busiNo = req.getBUSI_NO();
        ReqDTO reqDTO = new ReqDTO(req);

        MLppUtils.setReqHead(busiNo, reqDTO, "MLppSmsBatNotice");
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        // 和Starring V6进行通信
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("MLPP_PARTID"), "MLppSmsBatNotice",
                reqDTO, NoReturnRes.class, null);
        NoReturnRes res = new NoReturnRes();
        if (null != resDTO.getBODY()) {
            res = (NoReturnRes) resDTO.getBODY();
        }
        IDataset responseData = DatasetService.getInstace().getDataset(res, NoReturnRes.class);
        return responseData;
    }

    /**
     * 获取缴费单位的咨询电话
     *
     * @param busiNo
     * @return
     */
    public String getPhoneNo(String busiNo) {
        return offlineDao.getPhoneNo(busiNo);
    }

    /**
     * 获取当前业务对应的明细数量
     * @param busiNo
     * @return
     */
    public int countDtl(String busiNo) {
        return offlineDao.countDtl(busiNo);
    }

    public int offShelfMerchan(String busiNo) {
        return offlineDao.offShelfMerchan(busiNo);
    }

    /**
     *  获取数据查询列表总数量
     * @param reqBody
     * @return
     */
    public List<String> countDataInfo(Map<String, Object> reqBody) {
        return offlineDao.countDataInfo(reqBody);
    }

    /**
     * 获取数据查询分页数据
     * @param reqBody
     * @param start
     * @param limit
     * @return
     */
    public List<DataInfo> getDataList(Map<String, Object> reqBody, int start, int limit) {
        return offlineDao.getDataList(reqBody,start,limit);
    }
    /**
     * 获取数据查询数据
     * @param reqBody
     * @return
     */
    public List<DataInfo> getDataList(Map<String, Object> reqBody) {
        return offlineDao.getDataList(reqBody);
    }
}
