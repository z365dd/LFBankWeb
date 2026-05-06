
package com.adtec.pay.web.data;

import com.adtec.framework.common.util.ServerResponse;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.pay.dto.proj.chk.ProjInspModReq;
import com.adtec.pay.dto.proj.chk.ProjInspQryReq;
import com.adtec.pay.dto.proj.chk.ProjInspQryRes;
import com.adtec.pay.dto.proj.mng.MngProjModReq;
import com.adtec.pay.dto.proj.mng.MngProjQryReq;
import com.adtec.pay.dto.proj.mng.MngProjQryRes;
import com.adtec.pay.dto.proj.rec.ProjInspRecReq;
import com.adtec.pay.dto.proj.rec.ProjInspRecRes;
import com.adtec.pay.dto.proj.rec.ProjInspRecResList;
import com.adtec.pay.service.ProjectOptService;
import com.adtec.pay.utils.MultipartFileToFileUtils;
import com.adtec.pay.webResp.ComRespBody;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.modules.sys.utils.UserUtils;
import org.apache.axis.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * 项目管理操作控制类
 */

@RestController
@RequestMapping("/${adminPath}/proj/data/")
public class ProjectOpt extends BaseController {

    @Autowired
    private ProjectOptService projectOptService;
    //新增
    private final static String ADD = "1";
    //修改
    private final static String UPT = "2";

    //待分行审批
    private final static String STAT_LV1 = "10";
    //待总行审批
    private final static String STAT_LV2 = "20";
    //审批通过
    private final static String STAT_SUCC = "30";

    //总行id
    private final static String BRCH_TOP_ID = "1";




/**
     * 管理项目查询
     *
     * @param request
     * @return
     */

    @RequestMapping("/proj_mng_list")
    public ServerResponse<ComRespBody<MngProjQryRes>> projList(HttpServletRequest request) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String projTp = reqDs.getString("projTp");
        String custName = reqDs.getString("custName");
        String brch = reqDs.getString("office.id");
        int start = reqDs.getInt("start");
        int limit = 0 == reqDs.getInt("limit") ? 10 : reqDs.getInt("limit");

        MngProjQryReq mngProjQryReq = new MngProjQryReq();
        mngProjQryReq.setPROJ_TP(projTp);
        mngProjQryReq.setCUST_NAME(custName);

        String brchId = UserUtils.getUser().getOffice().getId();
        //非总行用户模糊查询时上送登录用户所属机构号
        if(StringUtils.isEmpty(brchId)){
            if(BRCH_TOP_ID.equals(brchId)){
                mngProjQryReq.setBRCH(brchId);
            }
        }else{
            mngProjQryReq.setBRCH(brch);
        }

        MngProjQryRes res = projectOptService.listMngProj(mngProjQryReq, start, limit);
        int total = res.getTOT_NUM().intValue();
        ComRespBody<MngProjQryRes> retData = new ComRespBody<MngProjQryRes>(start, limit, res, total);
        return ServerResponse.createBySuccess("查询成功", retData);
    }


/**
     * 管理项目维护
     *
     * @param request
     * @return
     */

    @RequestMapping("/proj_mng_modify")
    public void projModify(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String operTp = reqDs.getString("operTp");
        String projTp = reqDs.getString("projTp");
        String custName = reqDs.getString("custName");
        String acct = reqDs.getString("acct");
        String custMngName = reqDs.getString("custMngName");
        String signDate = reqDs.getString("signDate");
        signDate = signDate.substring(0, 4) + signDate.substring(5, 7) + signDate.substring(8);
        String unsignDate = reqDs.getString("unsignDate");
        unsignDate = unsignDate.substring(0, 4) + unsignDate.substring(5, 7) + unsignDate.substring(8);

        String mngFileName = reqDs.getString("mngFileName");
        String inspCyc = reqDs.getString("inspCyc");
        String stat = reqDs.getString("stat");

        String brch = UserUtils.getUser().getOffice().getId();
        String brchName = UserUtils.getUser().getOffice().getName();

        MngProjModReq mngProjModReq = new MngProjModReq();
        mngProjModReq.setOPER_TP(operTp);
        mngProjModReq.setPROJ_TP(projTp);
        mngProjModReq.setCUST_NAME(custName);
        mngProjModReq.setBRCH(brch);
        mngProjModReq.setBRCH_NAME(brchName);
        mngProjModReq.setACCT(acct);
        mngProjModReq.setCUST_MNG_NAME(custMngName);
        mngProjModReq.setSIGN_DATE(signDate);
        mngProjModReq.setUNSIGN_DATE(unsignDate);
        //如果修改时 未修改已上传文件 则上传mngFileName为空
        if (!StringUtils.isEmpty(mngFileName)) {
            mngProjModReq.setMNG_FILE_NAME(mngFileName);
        }
        mngProjModReq.setINSP_CYC(inspCyc);
        mngProjModReq.setSTAT(stat);
        //新增
        if (operTp.equals(ADD) && stat.equals(STAT_LV1)) {
            mngProjModReq.setAPPR_NAME(UserUtils.getUser().getLoginName());
        }
        //修改时保存登录用户名作为审批人名称
        if (operTp.equals(UPT)) {
            if (stat.equals(STAT_LV2)) {
                mngProjModReq.setFST_APPR_NAME(UserUtils.getUser().getName());
            }
            if (stat.equals(STAT_SUCC)) {
                mngProjModReq.setSECD_APPR_NAME(UserUtils.getUser().getName());
            }
        }
        IDataset resDs = projectOptService.callProjMngModify(mngProjModReq);
        setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
    }



/**
     * 上传文件到影像平台
     *
     * @param request
     */

    @RequestMapping("/uploadFile")
    @ResponseBody
    private ServerResponse<String> uploadFile(HttpServletRequest request, MultipartFile file) {

        if (file == null || file.getSize() == 0) {
            throw new BaseException(SysErr.E_MESSAGE, "请选择上传的文件!");
        }
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String projTp = reqDs.getString("projTp");
        String custName = reqDs.getString("custName");
        String brch = reqDs.getString("brch");
        //解决文件名称中文乱码问题
        String encodeFileName = "";
        try {
            encodeFileName = URLEncoder.encode(file.getOriginalFilename(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("文件名称转换异常");
        }
        String fileName = encodeFileName;
        //文件详细路径
        String filePath = "uploadFile" + File.separator + projTp + File.separator + brch + File.separator + custName;

        String id = "";
        try {
            File localFile = MultipartFileToFileUtils.multipartFileToFile(file,filePath);



            //todo 上传文件到影像平台  获取返回的日期+影像id

            /*Client client = new Client();
            List<String> uploadFileList = new ArrayList();
            String path = localFile.getAbsolutePath();
            uploadFileList.add(path);
            client.uploadExample(uploadFileList);*/




            id = uploadFileToYXClient();

        } catch (IOException e) {
            throw new BaseException(SysErr.E_IO_ERROR, "保存到本地服务器失败");
        }
        return ServerResponse.createBySuccess("查询成功", id);

    }



/**
     * 上传文件到影像平台
     * @return
     */

    private String uploadFileToYXClient() {
            return null;
    }



/**
     *
     * @param request
     * @param response
     * @throws Exception
     */

    @RequestMapping("/getFileStream")
    @ResponseBody
    private void getFileStream(HttpServletRequest request,HttpServletResponse response) throws Exception{

        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String projTp = reqDs.getString("projTp");
        String custName = reqDs.getString("custName");
        String brch = reqDs.getString("brch");
        String fileId = reqDs.getString("id");

        String fileName = projTp + custName + brch + ".txt";


        // 1. 获取文件输入流
        try (InputStream inputStream = getFileStreamFromExternalSystem(fileId);
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
     * @param fileId
     * @return
     */

    private InputStream getFileStreamFromExternalSystem(String  fileId) {
        // 实现方式举例：
        // 1. 从云存储（OSS）获取：ossClient.getObject(bucketName, objectName).getObjectContent()
        // 2. 从数据库读取：Blob blob = fileRepository.findById(fileId); return blob.getBinaryStream();
        // 3. 调用第三方API：RestTemplate.getForObject(url, InputStream.class)
        // 4. 动态生成文件：new ByteArrayInputStream(content.getBytes())


        //todo 直接通过id去请求影像平台获取文件内容


        //smartweb单机自测通过
        String content = "Hello, 这是动态生成的文件内容！" ;
        return new ByteArrayInputStream(content.getBytes());
    }





/**
     * 管理项目查询
     *
     * @param request
     * @return
     */

    @RequestMapping("/proj_chk_list")
    public ServerResponse<ComRespBody<ProjInspQryRes>> projChkList(HttpServletRequest request) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String projTp = reqDs.getString("projTp");
        String custName = reqDs.getString("custName");
        String brch = reqDs.getString("office.id");
        String inspStat = reqDs.getString("inspStat");
        String signStat = reqDs.getString("signStat");
        String efftFlg = reqDs.getString("efftFlg");
        int start = reqDs.getInt("start");
        int limit = 0 == reqDs.getInt("limit") ? 10 : reqDs.getInt("limit");

        ProjInspQryReq projInspQryReq = new ProjInspQryReq();
        projInspQryReq.setPROJ_TP(projTp);
        projInspQryReq.setCUST_NAME(custName);

        String brchId = UserUtils.getUser().getOffice().getId();
        //非总行用户模糊查询时上送登录用户所属机构号
        if(StringUtils.isEmpty(brchId)){
            if(BRCH_TOP_ID.equals(brchId)){
                projInspQryReq.setBRCH(brchId);
            }
        }else{
            projInspQryReq.setBRCH(brch);
        }
        projInspQryReq.setINSP_STAT(inspStat);
        projInspQryReq.setSIGN_STAT(signStat);
        projInspQryReq.setEFFT_FLG(efftFlg);
        ProjInspQryRes res = projectOptService.listProjChk(projInspQryReq, start, limit);
        int total = res.getTOT_NUM().intValue();
        ComRespBody<ProjInspQryRes> retData = new ComRespBody<ProjInspQryRes>(start, limit, res, total);
        return ServerResponse.createBySuccess("查询成功", retData);
    }


/**
     * 管理项目维护
     *
     * @param request
     * @return
     */

    @RequestMapping("/proj_chk_modify")
    public void projInspMod(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String operTp = reqDs.getString("operTp");
        String projTp = reqDs.getString("projTp");
        String custName = reqDs.getString("custName");
        String brch = reqDs.getString("brch");
        String brchName = reqDs.getString("brchName");
        String inspDate = reqDs.getString("inspDate");
        inspDate = inspDate.substring(0, 4) + inspDate.substring(5, 7) + inspDate.substring(8);
        String inspCyc = reqDs.getString("inspCyc");
        String lstInspDate = reqDs.getString("lstInspDate");
        String inspFileName = reqDs.getString("inspFileName");
        String inspUserName = reqDs.getString("inspUserName");
        String inspStat = reqDs.getString("inspStat");

        ProjInspModReq projInspModReq = new ProjInspModReq();
        projInspModReq.setOPER_TP(operTp);
        projInspModReq.setPROJ_TP(projTp);
        projInspModReq.setCUST_NAME(custName);          // 客户名称
        projInspModReq.setBRCH(brch);                   // 分支机构代码
        projInspModReq.setBRCH_NAME(brchName);          // 分支机构名称
        projInspModReq.setINSP_CYC(inspCyc);            // 巡检周期
        projInspModReq.setINSP_DATE(inspDate);          // 巡检日期
        projInspModReq.setLST_INSP_DATE(lstInspDate);   // 最后巡检日期
        projInspModReq.setINSP_FILE_NAME(inspFileName); // 巡检文件名称
        projInspModReq.setINSP_USER_NAME(inspUserName); // 巡检人员姓名
        projInspModReq.setINSP_STAT(inspStat);          // 巡检状态

        IDataset resDs = projectOptService.callProjInspModify(projInspModReq);
        setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
    }





/**
     * 巡检记录查询
     *
     * @param request
     * @return
     */

    @RequestMapping("/insp_rec_list")
    public ServerResponse<ComRespBody<ProjInspRecRes>> projInspRecList(HttpServletRequest request) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String projTp = reqDs.getString("projTp");
        String custName = reqDs.getString("custName");
        String brch = reqDs.getString("brch");

        int start = reqDs.getInt("start");
        int limit = reqDs.getInt("limit");
        start = start == 0 ? start : (start - 1) * limit;


        ProjInspRecReq projInspRecReq = new ProjInspRecReq();
        projInspRecReq.setPROJ_TP(projTp);
        projInspRecReq.setCUST_NAME(custName);
        projInspRecReq.setBRCH(brch);
        int total = projectOptService.countInspRec(projInspRecReq);
        List<ProjInspRecResList> list = projectOptService.listInspRec(projInspRecReq, start, limit);
        ProjInspRecRes res = new ProjInspRecRes();
        res.setList(list);
        res.setTOT_NUM((long)total);
        res.setREC_NUM((long)limit);
        ComRespBody<ProjInspRecRes> retData = new ComRespBody<ProjInspRecRes>(start, limit, res, total);
        return ServerResponse.createBySuccess("查询成功", retData);
    }






}

