package com.adtec.pay.utils;

import com.adtec.framework.common.util.DateUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * 将MultipartFile转化为file
 */
public class MultipartFileToFileUtils {


    public static File multipartFileToFile(MultipartFile multipartFile, String filePath) throws IOException {
        File file = null;
        if ("".equals(multipartFile) || multipartFile.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = multipartFile.getInputStream();
            file = new File(multipartFile.getOriginalFilename());
            file = inputStreamToFile(ins, file);
            ins.close();
        }
        return file;
    }


    private static File inputStreamToFile(InputStream ins, File file) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int len = 0;
            byte[] buffer = new byte[8192];
            while ((len = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, len);
            }
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(null != os){
                try {
                    os.close();
                } catch (IOException e) {
                    System.out.println("关闭异常");
                }
            }
            if(null != ins){
                try {
                    ins.close();
                } catch (IOException e) {
                    System.out.println("关闭异常");
                }
            }
        }
        return null;
    }

    public static File saveFile(MultipartFile multipartFile,String busiNo) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        //最终文件保存路径
        String userDir  = System.getenv("HOME") + File.separator + "uploadFile" + File.separator + "excel" +
                File.separator + DateUtil.getDate() + File.separator + busiNo ;
        String fullPath = userDir +File.separator +  fileName;
        File file = new File(fullPath);
        File dir = new File(userDir);
        //创建文件夹
        if(!dir.exists()){
            dir.mkdirs();
        }
        //保存文件
        if(!file.exists()){
            file.createNewFile();
        }else{
            file.delete();
            file.createNewFile();
        }
        multipartFile.transferTo(file);
        return file;
    }
}
