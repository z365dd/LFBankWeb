
package com.adtec.pay.web.data;


import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.common.util.ServerResponse;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.pay.dto.bookList.BookList;
import com.adtec.pay.dto.bookList.MLppQryBookListRes;
import com.adtec.pay.dto.union.AcctCheckReq;
import com.adtec.pay.dto.union.BatDisuburseReq;
import com.adtec.pay.dto.union.TotDataListRes;
import com.adtec.pay.entity.union.UnionExpense;
import com.adtec.pay.entity.union.UnionInst;
import com.adtec.pay.entity.union.UnionParam;
import com.adtec.pay.entity.union.UnionUser;
import com.adtec.pay.service.UnionOptService;
import com.adtec.pay.utils.MultipartFileToFileUtils;
import com.adtec.pay.webResp.ComRespBody;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.alibaba.fastjson.JSONArray;
import com.kingbase8.util.Base64;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${adminPath}/union/data/")
public class UnionOpt extends BaseController {


    @Autowired
    private UnionOptService unionOptService;


    //新增
    private final static String OPT_ADD = "1";
    //修改
    private final static String OPT_UPT = "2";
    //刪除
    private final static String OPT_DEL = "3";
    //查询
    private final static String OPT_QRY = "4";

    //待提交
    private final static String STAT_INIT = "00";
    //未报销
    private final static String EPX_STAT_INIT = "00";

    //用户人员表 待审批
    private final static String VALID_FLG_FALSE = "0";
    //用户人员表 已审批
    private final static String VALID_FLG_TRUE = "1";


    /**
     * 工会缴费参数分页查询
     *
     * @param request
     * @return
     */

    @RequestMapping("/para_list")
    public ServerResponse<ComRespBody<List<UnionParam>>> projList(HttpServletRequest request) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        int start = reqDs.getInt("start");
        int limit = reqDs.getInt("limit");
        start = start == 0 ? start : (start - 1) * limit;
        List<UnionParam> list = unionOptService.paraList(start, limit);
        int total = unionOptService.countPara(new UnionParam());
        ComRespBody<List<UnionParam>> retData = new ComRespBody<List<UnionParam>>(start, limit, list, total);
        return ServerResponse.createBySuccess("查询成功", retData);
    }


    /**
     * 工会缴费参数维护
     *
     * @param request
     * @return
     */

    @RequestMapping("/para_modify")
    public ServerResponse<String> projModify(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String operTp = reqDs.getString("operTp");
        String year = reqDs.getString("year");
        String strDate = reqDs.getString("strDate");
        String endDate = reqDs.getString("endDate");
        String amt = reqDs.getString("amt");
        UnionParam req = new UnionParam();
        req.setYEAR(year);
        req.setSTR_DATE(strDate);
        req.setEND_DATE(endDate);
        req.setAMT(amt);
        if (OPT_ADD.equals(operTp)) {
            int rec = unionOptService.countPara(req);
            if (rec > 0) {
                return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "该年度缴费参数已存在,不可重复添加!");
            }
            unionOptService.paramAdd(req);
            return ServerResponse.createBySuccessMessage("新增成功!");
        } else if (OPT_UPT.equals(operTp)) {
            unionOptService.paramUpdate(req);
            return ServerResponse.createBySuccessMessage("修改成功!");
        } else {
            unionOptService.paramDelete(req);
            return ServerResponse.createBySuccessMessage("删除成功!");
        }

    }


    /**
     * 工会缴费参数分页查询
     *
     * @param request
     * @return
     */

    @RequestMapping("/appr_list")
    public ServerResponse<ComRespBody<List<UnionExpense>>> ExpApprList(HttpServletRequest request) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        int start = reqDs.getInt("start");
        int limit = reqDs.getInt("limit");
        start = start == 0 ? start : (start - 1) * limit;
        String busiNo = reqDs.getString("busiNo");
        String busiName = reqDs.getString("busiName");
        String stat = reqDs.getString("stat");
        String expStat = reqDs.getString("expStat");
        String name = reqDs.getString("name");
        String certNo = reqDs.getString("certNo");
        String strDate = reqDs.getString("strDate");
        String endDate = reqDs.getString("endDate");
        if (!StringUtils.isEmpty(strDate)) {
            strDate = strDate.length() == 8 ? strDate : strDate.substring(0, 4) + strDate.substring(5, 7) + strDate.substring(8);
        }
        if (!StringUtils.isEmpty(endDate)) {
            endDate = endDate.length() == 8 ? endDate : endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8);
        }
        UnionExpense req = new UnionExpense();
        req.setBUSI_NAME(busiName);
        req.setBUSI_NO(busiNo);
        req.setSTAT(stat);
        req.setEXP_STAT(expStat);
        req.setNAME(name);
        req.setCERT_NO(certNo);
        int total = unionOptService.countAppr(req, strDate, endDate);
        List<UnionExpense> list = unionOptService.apprList(req, strDate, endDate, start, limit);
        ComRespBody<List<UnionExpense>> retData = new ComRespBody<List<UnionExpense>>(start, limit, list, total);
        return ServerResponse.createBySuccess("查询成功", retData);
    }


    /**
     * 工会缴费参数维护
     *
     * @param request
     * @return
     */

    @RequestMapping("/appr_modify")
    public ServerResponse<String> apprModify(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);

        String operTp = reqDs.getString("operTp");
        UnionExpense req = buildApprModReqBody(reqDs);

        if (OPT_ADD.equals(operTp)) {
            req.setSTAT(STAT_INIT);
            req.setEXP_STAT(EPX_STAT_INIT);
            req.setREG_DATE(DateUtil.getDate());
            unionOptService.expApprAdd(req);
            return ServerResponse.createBySuccessMessage("新增成功!");
        }
        if (OPT_UPT.equals(operTp)) {
            unionOptService.expApprUpdate(req);
            return ServerResponse.createBySuccessMessage("修改成功!");
        }
        return null;
    }


    /**
     * 校验缴费记录是否已提交报销
     *
     * @param request
     * @return
     */

    @RequestMapping("/appr_add_check")
    public ServerResponse<String> apprAddCheck(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        UnionExpense req = buildApprModReqBody(reqDs);
        int rec = unionOptService.checkExpAppr(req);
        if (rec > 0) {
            return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "该缴费记录已提交报销,不可重复添加");
        }
        return ServerResponse.createBySuccessMessage("允许添加!");
    }


    /**
     * 组装请求
     *
     * @param reqDs
     * @return
     */
    private UnionExpense buildApprModReqBody(IDataset reqDs) {
        String platDate = reqDs.getString("platDate");
        String platSeq = reqDs.getString("platSeq");
        String regDate = reqDs.getString("regDate");
        String busiNo = reqDs.getString("busiNo");
        String busiName = reqDs.getString("busiName");
        String name = reqDs.getString("name");
        String certNo = reqDs.getString("certNo");
        String phoneNo = reqDs.getString("phoneNo");
        String payAcct = reqDs.getString("payAcct");

        String stat = reqDs.getString("stat");
        String expStat = reqDs.getString("expStat");


        double amt = reqDs.getDouble("amt");


        //修改使用
        String certUrl = reqDs.getString("certUrl");
        certUrl = new String(Base64.decode(certUrl), StandardCharsets.UTF_8);
        String payAcctUrl = reqDs.getString("payAcctUrl");
        payAcctUrl = new String(Base64.decode(payAcctUrl), StandardCharsets.UTF_8);
        String caseHisUrl = reqDs.getString("caseHisUrl");
        caseHisUrl = new String(Base64.decode(caseHisUrl), StandardCharsets.UTF_8);
        String billUrl = reqDs.getString("billUrl");
        billUrl = new String(Base64.decode(billUrl), StandardCharsets.UTF_8);

        UnionExpense req = new UnionExpense();
        req.setPLAT_DATE(platDate);
        req.setPLAT_SEQ(platSeq);
        req.setREG_DATE(regDate);
        req.setBUSI_NO(busiNo);
        req.setBUSI_NAME(busiName);
        req.setNAME(name);
        req.setCERT_NO(certNo);
        req.setPHONE_NO(phoneNo);
        req.setPAY_ACCT(payAcct);
        req.setSTAT(stat);
        req.setEXP_STAT(expStat);
        req.setCERT_URL(certUrl);
        req.setPAY_ACCT_URL(payAcctUrl);
        req.setCASE_HIS_URL(caseHisUrl);
        req.setBILL_URL(billUrl);
        req.setAMT(amt);

        return req;
    }


    /**
     * 工会缴费参数分页查询
     *
     * @param request
     * @return
     */

    @RequestMapping("/mock_data")
    public ServerResponse<ComRespBody<MLppQryBookListRes>> MockData(HttpServletRequest request) throws InvocationTargetException, IllegalAccessException {
        MLppQryBookListRes respDto = new MLppQryBookListRes();
        BookList bookList = new BookList();
        BookList bookList1 = new BookList();
        BookList bookList2 = new BookList();
        BookList bookList3 = new BookList();
        BookList bookList4 = new BookList();
        BookList bookList5 = new BookList();
        BookList bookList6 = new BookList();
        BookList bookList7 = new BookList();
        BookList bookList8 = new BookList();
        BookList bookList9 = new BookList();
        BookList bookList10 = new BookList();
        BookList bookList11 = new BookList();
        bookList.setBUSI_NO("2222222");
        bookList.setBUSI_NAME("测试单位2");
        bookList.setNAME("测试用户1");
        bookList.setPAY_ACCT("62111111111");
        bookList.setPLAT_DATE("20250327");
        bookList.setPLAT_SEQ("2025032722222");
        bookList.setCERT_NO("3700002123213214571237");
        bookList.setCTCT_PHONE_NO("15933667742");
        bookList.setPRCTL_AMT(100.68);
        bookList.setCHNL_NO("030");
        bookList.setPAY_TP("1");
        bookList1.setBUSI_NO("1111111");
        bookList1.setBUSI_NAME("测试单位1");
        bookList1.setPAY_ACCT("61111111111");
        bookList1.setPLAT_DATE("20250327");
        bookList1.setPLAT_SEQ("2025032711111");
        bookList1.setCERT_NO("3700002123213214572237");
        bookList1.setCTCT_PHONE_NO("15933667741");
        bookList1.setPRCTL_AMT(99.99);
        bookList1.setCHNL_NO("057");
        bookList1.setPAY_TP("5");
        List<BookList> list = new ArrayList<>();
        list.add(bookList);
        list.add(bookList1);
        list.add(bookList2);
        list.add(bookList3);
        list.add(bookList4);
        list.add(bookList5);
        list.add(bookList6);
        list.add(bookList7);
        list.add(bookList8);
        list.add(bookList9);
        list.add(bookList10);
        list.add(bookList11);

        BeanUtils.copyProperties(bookList2, bookList);
        BeanUtils.copyProperties(bookList3, bookList);
        BeanUtils.copyProperties(bookList4, bookList);
        BeanUtils.copyProperties(bookList5, bookList);
        BeanUtils.copyProperties(bookList6, bookList);
        BeanUtils.copyProperties(bookList7, bookList);
        BeanUtils.copyProperties(bookList8, bookList);
        BeanUtils.copyProperties(bookList9, bookList);
        BeanUtils.copyProperties(bookList10, bookList);
        BeanUtils.copyProperties(bookList11, bookList);


        bookList2.setPLAT_SEQ("20250327222221");
        bookList3.setPLAT_SEQ("20250327222222");
        bookList4.setPLAT_SEQ("20250327222223");
        bookList5.setPLAT_SEQ("20250327222224");
        bookList6.setPLAT_SEQ("20250327222225");
        bookList7.setPLAT_SEQ("20250327222226");
        bookList8.setPLAT_SEQ("20250327222227");
        bookList9.setPLAT_SEQ("20250327222228");
        bookList10.setPLAT_SEQ("20250327222229");
        bookList11.setPLAT_SEQ("20250327222220");


        respDto.setBOOK_LIST(list);

        ComRespBody<MLppQryBookListRes> retData = new ComRespBody<MLppQryBookListRes>(1, 10, respDto, 11);
        return ServerResponse.createBySuccess("查询成功", retData);
    }


    /**
     * 保存文件到本地
     *
     * @param request
     * @param certFile    身份证
     * @param payAcctFile 银行卡
     * @param caseHisFile 病历
     * @param billFile    票据
     * @return
     */

    @RequestMapping("/uploadFile")
    @ResponseBody
    private ServerResponse<ComRespBody<Map>> uploadFile(HttpServletRequest request,
                                                        MultipartFile certFile,
                                                        MultipartFile payAcctFile,
                                                        MultipartFile caseHisFile,
                                                        MultipartFile billFile) {

        if (certFile == null || certFile.getSize() == 0) {
            throw new BaseException(SysErr.E_MESSAGE, "请选择身份证上传!");
        }
        if (payAcctFile == null || payAcctFile.getSize() == 0) {
            throw new BaseException(SysErr.E_MESSAGE, "请选择银行卡上传!");
        }
        if (caseHisFile == null || caseHisFile.getSize() == 0) {
            throw new BaseException(SysErr.E_MESSAGE, "请选择病历上传!");
        }
        if (billFile == null || billFile.getSize() == 0) {
            throw new BaseException(SysErr.E_MESSAGE, "请选择票据上传!");
        }
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String busiNo = reqDs.getString("busiNo");
        String platDate = reqDs.getString("platDate");
        String certNo = reqDs.getString("certNo");
        //解决文件名称中文乱码问题
        String encodeFileName1 = "";
        String encodeFileName2 = "";
        String encodeFileName3 = "";
        String encodeFileName4 = "";
        try {
            encodeFileName1 = URLEncoder.encode(certFile.getOriginalFilename(), "UTF-8");
            encodeFileName2 = URLEncoder.encode(payAcctFile.getOriginalFilename(), "UTF-8");
            encodeFileName3 = URLEncoder.encode(caseHisFile.getOriginalFilename(), "UTF-8");
            encodeFileName4 = URLEncoder.encode(billFile.getOriginalFilename(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("文件名称转换异常");
        }
        String certFileName = encodeFileName1;
        String payAcctFileName = encodeFileName2;
        String caseHisFileName = encodeFileName3;
        String billFileName = encodeFileName4;
        //文件详细路径
        String filePath = "uploadFile" + File.separator + "UNION" + File.separator + busiNo + File.separator + platDate + File.separator + certNo;
        Map<String, String> res = new HashMap<>();
        try {
            File localFile1 = MultipartFileToFileUtils.multipartFileToFile(certFile, filePath);
            File localFile2 = MultipartFileToFileUtils.multipartFileToFile(payAcctFile, filePath);
            File localFile3 = MultipartFileToFileUtils.multipartFileToFile(caseHisFile, filePath);
            File localFile4 = MultipartFileToFileUtils.multipartFileToFile(billFile, filePath);
            res.put("certFileName", System.getenv("HOME") + File.separator + filePath + File.separator + certFile.getOriginalFilename());
            res.put("payAcctFileName", System.getenv("HOME") + File.separator + filePath + File.separator + payAcctFile.getOriginalFilename());
            res.put("caseHisFileName", System.getenv("HOME") + File.separator + filePath + File.separator + caseHisFile.getOriginalFilename());
            res.put("billFileName", System.getenv("HOME") + File.separator + filePath + File.separator + billFile.getOriginalFilename());
        } catch (IOException e) {
            throw new BaseException(SysErr.E_IO_ERROR, "保存到本地服务器失败");
        }
        ComRespBody<Map> retData = new ComRespBody<Map>(0, 10, res, 1);

        return ServerResponse.createBySuccess("文件上传成功", retData);

    }


    /**
     * @param request
     * @param response
     * @throws Exception
     */

    @RequestMapping("/getFileStream")
    @ResponseBody
    private void getFileStream(HttpServletRequest request, HttpServletResponse response) throws Exception {

        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String url = reqDs.getString("url");
        byte[] decode = Base64.decode(url);
        url = new String(decode, StandardCharsets.UTF_8);
        String fileName = url.substring(url.lastIndexOf(File.separator) + 1);

        // 1. 获取文件输入流
        try (InputStream inputStream = getFileStreamFromExternalSystem(url, request, response);
             OutputStream outputStream = response.getOutputStream()) {

            // 2. 设置响应头
            String encodedFileName = URLEncoder.encode(fileName, "UTF-8")
                    .replaceAll("\\+", "%20");

            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + fileName + "\"; " +
                            "filename*=UTF-8''" + encodedFileName);

            response.setContentType("application/octet-stream");

            // 3. 流式传输（8KB缓冲区）
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
                outputStream.flush();
            }
        }
    }


    /**
     * 从模型获取要下载的文件流
     *
     * @param url
     * @param request
     * @param response
     * @return
     */

    private InputStream getFileStreamFromExternalSystem(String url, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取本地文件
        Path path = Paths.get(url);
        if (!Files.exists(path)) {
            IDataset resDs = DatasetService.getInstace().getDataset();
            setResponseDataset(request, response, resDs, SysErr.E_IO_ERROR, "文件不存在");
        }
        byte[] bytes = Files.readAllBytes(path);
        return new ByteArrayInputStream(bytes);
    }


    /**
     * 批量出账
     *
     * @param request
     * @return
     */

    @RequestMapping("/batDisburse")
    public void batDisburse(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String selectedData = reqDs.getString("selectedData");
        String vrfyNoCrtId = reqDs.getString("vrfyNoCrtId");
        String vrfyNo = reqDs.getString("vrfyNo");
        String phoneNo = UserUtils.getUser().getPhoneNo();
        List<UnionExpense> list = JSONArray.parseArray(selectedData, UnionExpense.class);
        String busiNo = list.get(0).getBUSI_NO();
        BatDisuburseReq batDisuburseReq = new BatDisuburseReq();
        batDisuburseReq.setVRFY_NO(vrfyNo);
        batDisuburseReq.setBUSI_NO(busiNo);
        batDisuburseReq.setVRFY_NO_CRT_ID(vrfyNoCrtId);
        batDisuburseReq.setPHONE_NO(phoneNo);
        batDisuburseReq.setLIST(list);
        //todo LIST或者使用一个大字段传递数据
        batDisuburseReq.setREQ_CLOB(selectedData);


        //todo 调用模型   短信校验也在模型中进行

        /*IDataset resDs = unionOptService.callBatDisburse(batDisuburseReq);
        setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");*/


        IDataset resDs = DatasetService.getInstace().getDataset();
        setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "批量出账成功");
    }


    /**
     * 账户姓名一致性校验
     *
     * @param request
     * @return
     */

    /*@RequestMapping("/acctCheck")
    public void acctCheck(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String acct = reqDs.getString("acct");
        String name = reqDs.getString("name");

        AcctCheckReq acctCheckReq = new AcctCheckReq();
        acctCheckReq.setACCT(acct);
        acctCheckReq.setNAME(name);

        //todo 调用模型进行  账号、用户名信息校验

        Dataset resDs = unionOptService.callAcctCheck(acctCheckReq);
        setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");


        IDataset resDs = DatasetService.getInstace().getDataset();
        setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "校验成功");

    }*/


    /**
     * 工会汇总分页查询
     *
     * @param request
     * @return
     */


    @RequestMapping("/tot_data_list")
    public ServerResponse<ComRespBody<TotDataListRes>> totDataList(HttpServletRequest request) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        int start = reqDs.getInt("start");
        int limit = reqDs.getInt("limit");
        start = start == 0 ? start : (start - 1) * limit;
        String busiNo = reqDs.getString("busiNo");
        String strDate = reqDs.getString("strDate");
        String endDate = reqDs.getString("endDate");

        //请求体
        Map<String, String> reqBody = new HashMap<>();
        reqBody.put("busiNo", busiNo);
        reqBody.put("strDate", strDate);
        reqBody.put("endDate", endDate);

        List<UnionParam> list = unionOptService.paraList(start, limit);
        TotDataListRes res = new TotDataListRes();
        res = unionOptService.totDataList(reqBody);
        int total = res.getTOT_NUM().intValue();
        ComRespBody<TotDataListRes> retData = new ComRespBody<TotDataListRes>(start, limit, res, total);
        return ServerResponse.createBySuccess("查询成功", retData);
    }


    /**
     * 统计待审批用户数量
     *
     * @param request
     * @return
     */

    @RequestMapping("/user_appr_count")
    public ServerResponse<ComRespBody<Integer>> countApprUser(HttpServletRequest request) {

        String userId = UserUtils.getUser().getId();
        String busiNo = unionOptService.getBusiNoByUserId(userId);
        UnionUser unionUser = new UnionUser();
        unionUser.setBUSI_NO(busiNo);
        unionUser.setVALID_FLG(VALID_FLG_FALSE);
        int userNum = unionOptService.countApprUser(unionUser);
        ComRespBody<Integer> retData = new ComRespBody<Integer>(0, 10, userNum, 1);
        return ServerResponse.createBySuccess("查询成功", retData);
    }


    /**
     * 待审批用户列表
     *
     * @param request
     * @return
     */

    @RequestMapping("/user_list")
    public ServerResponse<ComRespBody<List<UnionUser>>> apprUserList(HttpServletRequest request) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        int start = reqDs.getInt("start");
        int limit = reqDs.getInt("limit");
        start = start == 0 ? start : (start - 1) * limit;
        String busiNo = reqDs.getString("busiNo");
        String busiName = reqDs.getString("busiName");
        String name = reqDs.getString("name");
        String certNo = reqDs.getString("certNo");
        String stat = reqDs.getString("stat");
        String validFlg = reqDs.getString("validFlg");
        UnionUser unionUser = new UnionUser();

        unionUser.setBUSI_NO(busiNo);
        unionUser.setBUSI_NAME(busiName);
        unionUser.setNAME(name);
        unionUser.setCERT_NO(certNo);
        unionUser.setSTAT(stat);
        unionUser.setVALID_FLG(validFlg);


        int total = unionOptService.countApprUser(unionUser);
        List<UnionUser> list = unionOptService.userList(unionUser, start, limit);
        ComRespBody<List<UnionUser>> retData = new ComRespBody<List<UnionUser>>(start, limit, list, total);
        return ServerResponse.createBySuccess("查询成功", retData);
    }


    /**
     * 工会缴费参数维护
     *
     * @param request
     * @return
     */

    @RequestMapping("/user_modify")
    public ServerResponse<String> userModify(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);

        String operTp = reqDs.getString("operTp");
        String busiNo = reqDs.getString("busiNo");
        String busiName = reqDs.getString("busiName");
        String name = reqDs.getString("name");
        String certNo = reqDs.getString("certNo");
        String phoneNo = reqDs.getString("phoneNo");
        String stat = reqDs.getString("stat");
        String validFlg = reqDs.getString("validFlg");
        UnionUser req = new UnionUser();
        req.setBUSI_NO(busiNo);
        req.setBUSI_NAME(busiName);
        req.setNAME(name);
        req.setCERT_NO(certNo);
        req.setPHONE_NO(phoneNo);
        req.setSTAT(stat);
        req.setVALID_FLG(validFlg);


        if (OPT_ADD.equals(operTp)) {
            //管理台新增的用户是已审批的
            req.setVALID_FLG(VALID_FLG_TRUE);
            unionOptService.unionUserAdd(req);

            return ServerResponse.createBySuccessMessage("新增成功!");
        } else if (OPT_UPT.equals(operTp)) {
            unionOptService.unionUserUpdate(req);
            return ServerResponse.createBySuccessMessage("修改成功!");
        } else {
            unionOptService.unionUserDelete(req);
            return ServerResponse.createBySuccessMessage("删除成功!");
        }
    }


    /**
     * 新增用户后增加缴费明细
     *
     * @param unionUser
     * @return
     */

    public ServerResponse<String> payDtlAdd(UnionUser unionUser) {

        return null;
    }


    /**
     * 机构列表
     *
     * @param request
     * @return
     */

    @RequestMapping("/inst_list")
    public ServerResponse<ComRespBody<List<UnionInst>>> unionInstList(HttpServletRequest request) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        int start = reqDs.getInt("start");
        int limit = reqDs.getInt("limit");
        start = start == 0 ? start : (start - 1) * limit;
        String busiNo = reqDs.getString("busiNo");
        String busiName = reqDs.getString("busiName");
        String brchTp = reqDs.getString("brchTp");
        String addr = reqDs.getString("addr");
        UnionInst unionInst = new UnionInst();

        unionInst.setBRCH_NO(busiNo);
        unionInst.setBRCH_NAME(busiName);
        unionInst.setBRCH_TP(brchTp);
        unionInst.setADDR(addr);


        int total = unionOptService.countUnionInst(unionInst, addr);
        List<UnionInst> list = unionOptService.unionInstList(unionInst, addr, start, limit);
        ComRespBody<List<UnionInst>> retData = new ComRespBody<List<UnionInst>>(start, limit, list, total);
        return ServerResponse.createBySuccess("查询成功", retData);
    }


    /**
     * 工会机构维护
     *
     * @param request
     * @return
     */

    @RequestMapping("/inst_modify")
    public ServerResponse<String> instModify(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);

        String operTp = reqDs.getString("operTp");
        String brchNo = reqDs.getString("brchNo");
        String brchName = reqDs.getString("brchName");
        String brchTp = reqDs.getString("brchTp");
        String certNo = reqDs.getString("certNo");
        String phoneNo = reqDs.getString("phoneNo");
        String acct = reqDs.getString("acct");


        //判断输入的清算账户是否合法  如果合法返回true
        //todo 需要使用模型 暂时不能使用
//        boolean flg = unionOptService.checkAcct(acct);
//        if (!flg) {
//            return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "清算账户不存在");
//        }


        int rec = 0;
        if (OPT_ADD.equals(operTp)) {
            try {
                rec = unionOptService.unionInstAdd(reqDs);
            } catch (Exception e) {
                if (e.getMessage().contains("唯一性约束")) {
                    return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "工会已存在,新增失败");
                }
                return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "新增工会失败" + e.getMessage());
            }
            return ServerResponse.createBySuccessMessage("新增成功!");
        } else if (OPT_UPT.equals(operTp)) {
            unionOptService.unionInstUpdate(reqDs);
            return ServerResponse.createBySuccessMessage("修改成功!");
        } else {
            unionOptService.unionDelete(reqDs);
            return ServerResponse.createBySuccessMessage("删除成功!");
        }
    }


}

