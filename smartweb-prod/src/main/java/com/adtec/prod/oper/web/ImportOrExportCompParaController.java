package com.adtec.prod.oper.web;

import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.prod.oper.service.ImportOrExportCompParaService;
import com.adtec.sys.common.web.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

@Controller
@RequestMapping(value = "${adminPath}/prod/oper/importOrExportComp")
public class ImportOrExportCompParaController extends BaseController {

    @Autowired
    private ImportOrExportCompParaService service;

    @RequiresPermissions("user")
    @RequestMapping(value = "manageData")
    public String manageData(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        return "starring/prod/oper/importOrExportCompPara";
    }

    /**
     * 业务参数导入
     *
     * @param file
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "importData")
    public void importData(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        IDataset resDs = DatasetService.getInstace().getDataset();
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        BufferedWriter bw = null;
        BufferedReader br = null;
        InputStream is = null;
        try {
            if (file == null) {
                throw new RuntimeException("导入文件为空!");
            }

            String fileName = file.getOriginalFilename();
            is = file.getInputStream();

            if (StringUtils.isBlank(fileName)) {
                throw new RuntimeException("导入文件为空！");
            }

            String path = ParamUtil.getConfig("EntrParaPath");
            File fileDir = new File(path);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            File newfile = new File(path + fileName);

            if (!newfile.exists()) {
                newfile.createNewFile();
            }
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newfile)));
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String str;
            String line = "\r";
            while (null != (str = br.readLine())) {
                bw.write(str + line);
                bw.flush();
            }
            //文件路径为:
            String absPath = newfile.getAbsolutePath();
        } catch (Exception e) {
            logger.error("导入数据失败", e);
            throw new BaseException(SysErr.E_MESSAGE, e.getMessage());
        } finally {
            if (null != bw) {
                try {
                    bw.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
        }
        setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "导入数据成功");
    }

    /**
     * 业务参数导出
     *
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "exportData")
    public void exportData(HttpServletRequest request, HttpServletResponse response) {
        IDataset resDs = DatasetService.getInstace().getDataset();
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String COMP_NO = reqDs.getString("COMP_NO");
        String path = ParamUtil.getConfig("uploadFile");
        long time = new Date().getTime();
        String FileName = COMP_NO + "_" + time + ".txt";
        System.out.println(FileName);
        response.setCharacterEncoding("UTF-8");
        FileInputStream in = null;
        PrintWriter out = null;
        BufferedReader br = null;
        try {
            File file = new File(path + "/" + FileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            file = service.getSQLFile(file, COMP_NO);

            String fileName = new String((COMP_NO + time + ".txt").getBytes(), "utf-8")
                    .replaceAll("\r","")
                    .replaceAll("\n","")
                    .replaceAll("\\\\","");
            response.reset();// 重置 响应头
            response.addHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");// 下载文件的名称
            response.setContentType("application/x-download");// 告知浏览器下载文件，而不是直接打开，浏览器默认为打开
            response.setCharacterEncoding("utf-8");
            in = new FileInputStream(file);
            //response.resetBuffer();

            // 创建输出流
            out = response.getWriter();

            // 循环将输入流中的内容读取到缓冲区当中
            br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String str = null;
            while ((str = br.readLine()) != null) {//使用readLine方法，一次读一行
                out.println(str);
            }

        } catch (Exception e) {
            logger.error("导入数据失败", e);
            throw new BaseException(SysErr.E_MESSAGE, e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    System.out.println("操作失败");
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    System.out.println("操作失败");
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    System.out.println("操作失败");
                }
            }
        }
        setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "导出数据成功");
    }

}
