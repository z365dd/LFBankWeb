package com.adtec.comm.protocol.ftp;

import com.adtec.framework.common.util.FileUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class FtpUtil {
    private final static Log logger = LogFactory.getLog(FtpUtil.class);

    /**
     * 获取FTPClient对象
     *
     * @param ftpHost     FTP主机服务器
     * @param ftpPassword FTP 登录密码
     * @param ftpUserName FTP登录用户名
     * @param ftpPort     FTP端口 默认为21
     * @return
     */
    public static FTPClient getFTPClient(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器
            ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                logger.info("未连接到FTP，用户名或密码错误。");
                ftpClient.disconnect();
            } else {
                logger.info("FTP连接成功。");
            }
        } catch (SocketException e) {

            logger.info("FTP的IP地址可能错误，请正确配置。");
        } catch (IOException e) {

            logger.info("FTP的端口错误,请正确配置。");
        }
        return ftpClient;
    }

    /*
     * 从FTP服务器下载文件
     *
     * @param ftpHost FTP IP地址
     *
     * @param ftpUserName FTP 用户名
     *
     * @param ftpPassword FTP用户名密码
     *
     * @param ftpPort FTP端口
     *
     * @param ftpPath FTP服务器中文件所在路径 格式： ftptest/aa
     *
     * @param localPath 下载到本地的位置 格式：H:/download
     *
     * @param fileName 文件名称
     */
    public static void downloadFtpFile(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort,
                                       String ftpPath, String localPath, String fileName) {

        FTPClient ftpClient = null;
        OutputStream os = null;
        try {
            ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            ftpClient.setControlEncoding("UTF-8"); // 中文支持
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(ftpPath);
            String FileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            File localFile = new File(localPath + File.separatorChar + FileName);
            os = new FileOutputStream(localFile);
            ftpClient.retrieveFile(FileName, os);
        } catch (FileNotFoundException e) {
            logger.error("没有找到" + ftpPath + "文件");
        } catch (SocketException e) {
            logger.error("连接FTP失败.");
        } catch (IOException e) {
            logger.error("文件读取错误。");
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }

            if (ftpClient != null) {
                try {
                    ftpClient.logout();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
        }

    }

    /**
     * @param ftpClient ftpClient
     * @param ftpPath   ftp文件存在位置
     * @param localPath 下载文件存放路径
     * @param fileName  文件名
     * @Title: downloadFtpFile
     * @Description: ftp文件下载
     * @author: chenyl
     * @date: 2017年9月29日 下午2:39:44
     */
    public static void downloadFtpFile(FTPClient ftpClient, String ftpPath, String localPath, String fileName) {
		OutputStream os = null;
		if(null == ftpClient){
			throw new BaseException(SysErr.E_NULL_POINTER, "ftpClient为null");
		}
        try {
            ftpClient.setControlEncoding("UTF-8"); // 中文支持
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            //ftpClient.changeWorkingDirectory(ftpPath);
            ftpClient.cwd("/" + ftpPath);
            logger.debug("ftpPath:" + ftpPath);
            FileUtil.createDirectory(localPath);
            File localFile = new File(localPath + File.separatorChar + fileName);
             os = new FileOutputStream(localFile);
            ftpClient.retrieveFile(fileName, os);
            os.close();
            ftpClient.logout();

        } catch (FileNotFoundException e) {

            logger.error("没有找到" + ftpPath + "文件");

        } catch (SocketException e) {

            logger.error("连接FTP失败.");

        } catch (IOException e) {

            logger.error("文件读取错误。");

        }finally{
        	if(null != os) {
				try {
					os.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
        	if(null != ftpClient){
				try {
					ftpClient.logout();
                } catch (IOException e) {
                    System.out.println("出现异常");
                }
            }
		}

    }

    /**
     * @param ftpClient ftpClient
     * @param ftpPath   ftp文件存在位置
     * @param localPath 本地文件存放路径
     * @param fileName  文件名
     * @Title: uploadFtpFile
     * @Description: ftp文件上传
     * @author: 阮雅辉
     * @date: 2017年9月29日 下午2:39:44
     */
    public static void uploadFtpFile(FTPClient ftpClient, String ftpPath, String localPath, String fileName) throws BaseException {
		InputStream is = null;
        try {
            ftpClient.setControlEncoding("UTF-8"); // 中文支持
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            //ftpClient.sendCommand("OPTS UTF8", "ON");
            //ftpClient.changeWorkingDirectory(ftpPath);
            logger.debug("ftpPath:" + ftpPath);
            //判断文件路径带 /的默认使用绝对路径目录，否则以用户工作目录
            if (ftpPath.startsWith("\\/")) {
                ftpClient.cwd("/" + ftpPath);
            } else {
                if (!ftpClient.changeWorkingDirectory(ftpPath)) {

                    String[] dirs = ftpPath.split("\\/");
                    for (String str : dirs) {
                        if (".".equals(str) || "".equals(str)) {
                            continue;
                        } else {
                            str = new String(str.getBytes("UTF-8"), "ISO-8859-1");
                            if (!ftpClient.changeWorkingDirectory(str)) {
                                if (ftpClient.makeDirectory(str)) {
                                    ftpClient.changeWorkingDirectory(str);
                                } else {
                                    throw new BaseException("10000", "创建目录[" + str + "]失败");
                                }
                            }
                        }
                    }
                }
            }

            File localFile = new File(localPath + File.separatorChar + fileName);
            is = new FileInputStream(localFile);
            ftpClient.storeFile(new String(fileName.getBytes("UTF-8"), "ISO-8859-1"), is);


        } catch (FileNotFoundException e) {

            logger.error("没有找到" + ftpPath + "文件");
            throw new BaseException("10000", e, "没有找到" + ftpPath + "文件");
        } catch (SocketException e) {

            logger.error("连接FTP失败.");

            throw new BaseException("10000", e, "连接FTP失败.");
        } catch (IOException e) {

            logger.error("文件读取错误。");

            throw new BaseException("10000", e, "文件读取错误。");
        }finally{
			if(null != is){
				try {
					is.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
			if(null != ftpClient){
				try {
					ftpClient.logout();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
		}

    }

    /**
     * @return FTPClient
     * @Title: getFtpClient
     * @Description: 默认ftp连接方式
     * @date: 2017年9月29日 下午2:41:25
     */
    public static FTPClient getFtpClient() {
        String ftpHost = (String) ParamUtil.getConfig("ftp_ip");
        String ftpUserName = (String) ParamUtil.getConfig("ftp_user");
        String ftpPassword = (String) ParamUtil.getConfig("ftp_pwd");
        int ftpPort = Integer.parseInt((String) ParamUtil.getConfig("ftp_port"));
        return FtpUtil.getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
    }

    /**
     * @param name 参数起始名
     * @return FTPClient
     * @Title: getFtpClient
     * @Description: 按命名获取ftp连接方式
     * @date: 2017年9月29日 下午2:41:25
     */
    public static FTPClient getFtpClient(String name) {
        String ftpHost = (String) ParamUtil.getConfig(name.toLowerCase() + "_ftp_ip");
        String ftpUserName = (String) ParamUtil.getConfig(name.toLowerCase() + "_ftp_user");
        String ftpPassword = (String) ParamUtil.getConfig(name.toLowerCase() + "_ftp_pwd");
        int ftpPort = Integer.parseInt((String) ParamUtil.getConfig(name.toLowerCase() + "_ftp_port"));
        return FtpUtil.getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
    }

    /**
     * @param properFileName 配置文件名
     * @return
     */
    public static Map<String, Object> getConfig(String properFileName) {
        // 获得资源包
        ResourceBundle rb = ResourceBundle.getBundle(properFileName.trim());
        // 通过资源包拿到所有的key
        Enumeration<String> allKey = rb.getKeys();
        // 遍历key 得到 value
        Map<String, Object> map = new HashMap<String, Object>();
        while (allKey.hasMoreElements()) {
            String key = allKey.nextElement();
            String value = (String) rb.getString(key);
            map.put(key, value);
        }
        return map;
    }

}
