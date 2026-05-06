package com.adtec.comp.sign.test.web;

import com.adtec.comp.sign.dto.FSignFuncCustSignFileReqDTO;
import com.adtec.comp.sign.dto.FSignFuncCustSignFileRsltQryReqDTO;
import com.adtec.comp.sign.dto.FSignSignVerifyFileReqDTO;
import com.adtec.comp.sign.dto.FileMsgDTO;
import com.adtec.comp.sign.test.service.SignFileService;
import com.adtec.framework.common.util.FileUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import com.google.common.net.HttpHeaders;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@Controller
@RequestMapping(value = "${adminPath}/comp/sign/test/signFile")
public class SignFileController extends BaseController {
    @Autowired
    private SignFileService signFileService;

    /**
     * 返回批量新增页面
     */
    @RequiresPermissions("user")
    @RequestMapping(value = {"signFileForm"})
    public String signFileForm(HttpServletRequest request, HttpServletResponse response) {
	/*	request.setAttribute("OPER_TP", OPER_TP);
		if("01".equals(OPER_TP)){
			request.setAttribute("tabName", "批量客户签约");
		}else if("02".equals(OPER_TP)){
			request.setAttribute("tabName", "批量客户解约");
		}*/
        return "starring/comp/sign/test/signFileForm";
    }

    /**
     * 返回批量结果页面
     */
    @RequiresPermissions("user")
    @RequestMapping(value = {"signFileList"})
    public String signFileList(HttpServletRequest request, HttpServletResponse response) {
        return "starring/comp/sign/test/signFileList";
    }


    // 批量测试提交
    @RequiresPermissions("user")
    @RequestMapping(value = "signFileAdd")
    public void signFileAdd(HttpServletRequest request, HttpServletResponse response) {
        // TODO
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String SIGN_PROT_TP_ID = reqDs.getString("SIGN_PROT_TP_ID");
        String OPER_TP = reqDs.getString("OPER_TP");
        String FILE_ID = reqDs.getString("FILE_ID");

        FSignFuncCustSignFileReqDTO reqBody = new FSignFuncCustSignFileReqDTO();
        reqBody.setSIGN_PROT_TP_ID(SIGN_PROT_TP_ID);
        reqBody.setOPER_TP(OPER_TP);

        // 调用文件获取，把文件上传到文件服务器
        reqBody.setREQ_FILE_SET_SEQ(signFileService.getFile(FILE_ID));

        IDataset resDs = signFileService.signFileAdd(reqBody);
        setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
    }

    // 批量签约校验提交
    @RequiresPermissions("user")
    @RequestMapping(value = "verifFileAdd")
    public void verifFileAdd(HttpServletRequest request, HttpServletResponse response) {
        // TODO
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String SIGN_PROT_TP_ID = reqDs.getString("SIGN_PROT_TP_ID");
        String FILE_ID = reqDs.getString("FILE_ID");

        FSignSignVerifyFileReqDTO reqBody = new FSignSignVerifyFileReqDTO();
        reqBody.setSIGN_PROT_TP_ID(SIGN_PROT_TP_ID);

        // 调用文件获取，把文件上传到文件服务器
        reqBody.setREQ_FILE_SET_SEQ(signFileService.getFile(FILE_ID));

        IDataset resDs = signFileService.verifFileAdd(reqBody);
        setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
    }

    // 批量结果查询
    @RequiresPermissions("user")
    @RequestMapping(value = "signFileQry")
    public void signFileQry(HttpServletRequest request, HttpServletResponse response) {
        // TODO
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String ORIG_REQ_SEQ = reqDs.getString("ORIG_REQ_SEQ");

        FSignFuncCustSignFileRsltQryReqDTO reqBody = new FSignFuncCustSignFileRsltQryReqDTO();
        reqBody.setORIG_REQ_SEQ(ORIG_REQ_SEQ);

        IDataset resDs = signFileService.signFileQry(reqBody);
        setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
    }

    // 批量签约校验结果查询
    @RequiresPermissions("user")
    @RequestMapping(value = "signVerifFileQry")
    public void signVerifFileQry(HttpServletRequest request, HttpServletResponse response) {
        // TODO
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String ORIG_REQ_SEQ = reqDs.getString("ORIG_REQ_SEQ");

        FSignFuncCustSignFileRsltQryReqDTO reqBody = new FSignFuncCustSignFileRsltQryReqDTO();
        reqBody.setORIG_REQ_SEQ(ORIG_REQ_SEQ);

        IDataset resDs = signFileService.signVerifFileQry(reqBody);
        setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
    }

    /**
     * 上传文件 返回本地保存信息
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "upload")
    public void upload(HttpServletRequest request, HttpServletResponse response,
                       @RequestParam("file") MultipartFile[] file) {
        /** 文件保存路径 */
        String savePath = ParamUtil.getUploadFile() + "/sign";
        // 创建文件保存目录
        FileUtil.createDirectory(savePath);

        if (file != null && file.length > 0) {
            try {

                FileMsgDTO fileDao = new FileMsgDTO();

                // fileName = file[i].getOriginalFilename();
                fileDao.setFileName(file[0].getOriginalFilename());
                // type = fileName.substring(fileName.indexOf(".") + 1);
                fileDao.setType(
                        file[0].getOriginalFilename().substring(file[0].getOriginalFilename().indexOf(".") + 1));
                // saveName = UUID.randomUUID().toString();
                fileDao.setSaveName(UUID.randomUUID().toString());
                fileDao.setPath(savePath);
                // 保存文件
                FileUtil.SaveFileFromInputStream(file[0].getInputStream(), savePath,
                        fileDao.getSaveName() + "." + fileDao.getType());
                /* ftpPath ftp文件存在位置 */
                //String ftpPath = ParamUtil.getConfig("ftp_path");

                // 提交时调文件传输组件
                /*
                 * FtpUtil.uploadFtpFile(GpmUtil.getFtpClient(), ftpPath,
                 * savePath, fileDao.getSaveName() + "." + fileDao.getType());
                 */

                IDataset responseData = DatasetService.getInstace().getDataset(fileDao, FileMsgDTO.class);
                setResponseDataset(request, response, responseData, SysErr.E_SUCCESS, "上传成功");

            } catch (Exception e) {
                e.printStackTrace();
                throw new BaseException(SysErr.E_MESSAGE, "上传出现异常！本地保存文件失败。");
            }
        } else {
            throw new BaseException(SysErr.E_MESSAGE, "没有检测到文件！");
        }
    }

    /**
     * 下载文件 返回路径
     *
     * @return
     */
    @RequestMapping(value = {"downLoad"})
    public void downLoad(HttpServletRequest request, HttpServletResponse response, String file) {
        // IDataset reqDs = DatasetService.getInstace().getDataset(request);
        // String file = reqDs.getString("file");
        String fileName = "";
        try {
            System.out.println(java.net.URLDecoder.decode(file, "UTF-8"));
            fileName = java.net.URLDecoder.decode(file, "UTF-8");
            fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        // /home/smartweb/uploadFile/a.txt
        /* String path = request.getRealPath("/"); */
        File localFile = new File(file);
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(localFile);
            int len = 0;
            byte buffer[] = new byte[1024];
            out = response.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
                System.out.println("出现异常");
            } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
        }
    }

}
