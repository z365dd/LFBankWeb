package com.adtec.framework.common.util;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.sys.common.web.Servlets;
import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;
import org.apache.commons.compress.utils.Lists;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 文件操作工具类
 * 实现文件的创建、删除、复制、压缩、解压以及目录的创建、删除、复制、压缩解压等功能
 *
 * @version 2013-06-21
 */
public class FileUtil extends org.apache.commons.io.FileUtils {

    private final static Logger log = LoggerFactory.getLogger(FileUtil.class);
    /**
     * 使用GBK编码可以避免压缩中文文件名乱码
     */
    private static final String CHINESE_CHARSET = "GBK";

    /**
     * 复制单个文件，如果目标文件存在，则不覆盖
     *
     * @param srcFileName  待复制的文件名
     * @param descFileName 目标文件名
     * @return 如果复制成功，则返回true，否则返回false
     */
    public static boolean copyFile(String srcFileName, String descFileName) {
        return FileUtil.copyFileCover(srcFileName, descFileName, false);
    }

    /**
     * 复制单个文件
     *
     * @param srcFileName  待复制的文件名
     * @param descFileName 目标文件名
     * @param coverlay     如果目标文件已存在，是否覆盖
     * @return 如果复制成功，则返回true，否则返回false
     */
    public static boolean copyFileCover(String srcFileName,
                                        String descFileName, boolean coverlay) {
        File srcFile = new File(srcFileName);
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            log.debug("复制文件失败，源文件 " + srcFileName + " 不存在!");
            return false;
        }
        // 判断源文件是否是合法的文件
        else if (!srcFile.isFile()) {
            log.debug("复制文件失败，" + srcFileName + " 不是一个文件!");
            return false;
        }
        File descFile = new File(descFileName);
        // 判断目标文件是否存在
        if (descFile.exists()) {
            // 如果目标文件存在，并且允许覆盖
            if (coverlay) {
                log.debug("目标文件已存在，准备删除!");
                if (!FileUtil.delFile(descFileName)) {
                    log.debug("删除目标文件 " + descFileName + " 失败!");
                    return false;
                }
            } else {
                log.debug("复制文件失败，目标文件 " + descFileName + " 已存在!");
                return false;
            }
        } else {
            if (!descFile.getParentFile().exists()) {
                // 如果目标文件所在的目录不存在，则创建目录
                log.debug("目标文件所在的目录不存在，创建目录!");
                // 创建目标文件所在的目录
                if (!descFile.getParentFile().mkdirs()) {
                    log.debug("创建目标文件所在的目录失败!");
                    return false;
                }
            }
        }

        // 准备复制文件
        // 读取的位数
        int readByte = 0;
        InputStream ins = null;
        OutputStream outs = null;
        try {
            // 打开源文件
            ins = new FileInputStream(srcFile);
            // 打开目标文件的输出流
            outs = new FileOutputStream(descFile);
            byte[] buf = new byte[1024];
            // 一次读取1024个字节，当readByte为-1时表示文件已经读取完毕
            while ((readByte = ins.read(buf)) != -1) {
                // 将读取的字节流写入到输出流
                outs.write(buf, 0, readByte);
            }
            log.debug("复制单个文件 " + srcFileName + " 到" + descFileName
                    + "成功!");
            return true;
        } catch (Exception e) {
            log.debug("复制文件失败：" + e.getMessage());
            return false;
        } finally {
            // 关闭输入输出流，首先关闭输出流，然后再关闭输入流
            if (outs != null) {
                try {
                    outs.close();
                } catch (IOException oute) {
                    System.out.println("操作失败");
                }
            }
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException ine) {
                    System.out.println("操作失败");
                }
            }
        }
    }

    /**
     * 复制整个目录的内容，如果目标目录存在，则不覆盖
     *
     * @param srcDirName  源目录名
     * @param descDirName 目标目录名
     * @return 如果复制成功返回true，否则返回false
     */
    public static boolean copyDirectory(String srcDirName, String descDirName) {
        return FileUtil.copyDirectoryCover(srcDirName, descDirName,
                false);
    }

    /**
     * 递归合并文件夹，若当前目录不存在则创建，若文件存在则覆盖,若单纯复制，调用前需先清空目的地文件夹
     *
     * @param srcDirName  源文件夹
     * @param descDirName 目的地文件夹
     * @param file        源文件对象
     * @param coverlay    是否覆盖
     */
    public static boolean copyDirectory(String srcDirName, String descDirName, File file, boolean coverlay) {
        File[] fs = file.listFiles();
        if (null != fs) {
            for (File f : fs) {
                if (f.isDirectory()) {
                    String dirPath = FileUtil.path(f.getAbsolutePath()).replace(srcDirName, descDirName);
                    FileUtil.createDirectory(dirPath);
                    copyDirectory(srcDirName, descDirName, f, coverlay);
                } else {
                    String filePath = FileUtil.path(f.getAbsolutePath()).replace(srcDirName, descDirName);
                    FileUtil.copyFileCover(f.getAbsolutePath(), filePath, coverlay);
                }
            }
        }
        return true;
    }

    /**
     * 复制整个目录的内容
     *
     * @param srcDirName  源目录名
     * @param descDirName 目标目录名
     * @param coverlay    如果目标目录存在，是否覆盖
     * @return 如果复制成功返回true，否则返回false
     */
    public static boolean copyDirectoryCover(String srcDirName,
                                             String descDirName, boolean coverlay) {
        File srcDir = new File(srcDirName);
        // 判断源目录是否存在
        if (!srcDir.exists()) {
            log.debug("复制目录失败，源目录 " + srcDirName + " 不存在!");
            return false;
        }
        // 判断源目录是否是目录
        else if (!srcDir.isDirectory()) {
            log.debug("复制目录失败，" + srcDirName + " 不是一个目录!");
            return false;
        }
        // 如果目标文件夹名不以文件分隔符结尾，自动添加文件分隔符
        String descDirNames = descDirName;
        if (!descDirNames.endsWith(File.separator)) {
            descDirNames = descDirNames + File.separator;
        }
        File descDir = new File(descDirNames);
        // 如果目标文件夹存在
        if (descDir.exists()) {
            if (coverlay) {
                // 允许覆盖目标目录
                log.debug("目标目录已存在，准备删除!");
                if (!FileUtil.delFile(descDirNames)) {
                    log.debug("删除目录 " + descDirNames + " 失败!");
                    return false;
                }
            } else {
                log.debug("目标目录复制失败，目标目录 " + descDirNames + " 已存在!");
                return false;
            }
        } else {
            // 创建目标目录
            log.debug("目标目录不存在，准备创建!");
            if (!descDir.mkdirs()) {
                log.debug("创建目标目录失败!");
                return false;
            }

        }

        boolean flag = true;
        // 列出源目录下的所有文件名和子目录名
        File[] files = srcDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 如果是一个单个文件，则直接复制
            if (files[i].isFile()) {
                flag = FileUtil.copyFile(files[i].getAbsolutePath(),
                        descDirName + files[i].getName());
                // 如果拷贝文件失败，则退出循环
                if (!flag) {
                    break;
                }
            }
            // 如果是子目录，则继续复制目录
            if (files[i].isDirectory()) {
                flag = FileUtil.copyDirectory(files[i]
                        .getAbsolutePath(), descDirName + files[i].getName());
                // 如果拷贝目录失败，则退出循环
                if (!flag) {
                    break;
                }
            }
        }

        if (!flag) {
            log.debug("复制目录 " + srcDirName + " 到 " + descDirName + " 失败!");
            return false;
        }
        log.debug("复制目录 " + srcDirName + " 到 " + descDirName + " 成功!");
        return true;

    }

    /**
     * 删除文件，可以删除单个文件或文件夹
     *
     * @param fileName 被删除的文件名
     * @return 如果删除成功，则返回true，否是返回false
     */
    public static boolean delFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            log.debug(fileName + " 文件不存在!");
            return true;
        } else {
            if (file.isFile()) {
                return FileUtil.deleteFile(fileName);
            } else {
                return FileUtil.deleteDirectory(fileName);
            }
        }
    }

    /**
     * 删除单个文件
     *
     * @param fileName 被删除的文件名
     * @return 如果删除成功，则返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                log.debug("删除文件 " + fileName + " 成功!");
                return true;
            } else {
                log.debug("删除文件 " + fileName + " 失败!");
                return false;
            }
        } else {
            log.debug(fileName + " 文件不存在!");
            return true;
        }
    }

    /**
     * 删除目录及目录下的文件
     *
     * @param dirName 被删除的目录所在的文件路径
     * @return 如果目录删除成功，则返回true，否则返回false
     */
    public static boolean deleteDirectory(String dirName) {
        String dirNames = dirName;
        if (!dirNames.endsWith(File.separator)) {
            dirNames = dirNames + File.separator;
        }
        File dirFile = new File(dirNames);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            log.debug(dirNames + " 目录不存在!");
            return true;
        }
        boolean flag = true;
        // 列出全部文件及子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = FileUtil.deleteFile(files[i].getAbsolutePath());
                // 如果删除文件失败，则退出循环
                if (!flag) {
                    break;
                }
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = FileUtil.deleteDirectory(files[i]
                        .getAbsolutePath());
                // 如果删除子目录失败，则退出循环
                if (!flag) {
                    break;
                }
            }
        }

        if (!flag) {
            log.debug("删除目录失败!");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            log.debug("删除目录 " + dirName + " 成功!");
            return true;
        } else {
            log.debug("删除目录 " + dirName + " 失败!");
            return false;
        }

    }

    /**
     * 删除目录下的文件（不删除目录）
     *
     * @param dirName 被删除的目录所在的文件路径
     * @return 如果目录删除成功，则返回true，否则返回false
     */
    public static boolean deleteDirectoryFiles(String dirName) {
        String dirNames = dirName;
        if (!dirNames.endsWith(File.separator)) {
            dirNames = dirNames + File.separator;
        }
        File dirFile = new File(dirNames);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            log.debug(dirNames + " 目录不存在!");
            return true;
        }
        boolean flag = true;
        // 列出全部文件及子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = FileUtil.deleteFile(files[i].getAbsolutePath());
                // 如果删除文件失败，则退出循环
                if (!flag) {
                    break;
                }
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = FileUtil.deleteDirectory(files[i]
                        .getAbsolutePath());
                // 如果删除子目录失败，则退出循环
                if (!flag) {
                    break;
                }
            }
        }

        if (!flag) {
            log.debug("删除子文件或目录失败!");
            return false;
        }

        log.debug("删除目录 " + dirName + " 下文件及子目录成功!");
        return true;

    }

    /**
     * 创建单个文件
     *
     * @param descFileName 文件名，包含路径
     * @return 如果创建成功，则返回true，否则返回false
     */
    public static boolean createFile(String descFileName) {
        File file = new File(descFileName);
        if (file.exists()) {
            log.debug("文件 " + descFileName + " 已存在!");
            return false;
        }
        if (descFileName.endsWith(File.separator)) {
            log.debug(descFileName + " 为目录，不能创建目录!");
            return false;
        }
        if (!file.getParentFile().exists()) {
            // 如果文件所在的目录不存在，则创建目录
            if (!file.getParentFile().mkdirs()) {
                log.debug("创建文件所在的目录失败!");
                return false;
            }
        }

        // 创建文件
        try {
            if (file.createNewFile()) {
                log.debug(descFileName + " 文件创建成功!");
                return true;
            } else {
                log.debug(descFileName + " 文件创建失败!");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.debug(descFileName + " 文件创建失败!");
            return false;
        }

    }

    /**
     * 检查文件或目录是否存在
     *
     * @param fileName
     * @return
     */
    public static boolean exsitsFile(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    /**
     * 创建目录
     *
     * @param descDirName 目录名,包含路径
     * @return 如果创建成功，则返回true，否则返回false
     */
    public static boolean createDirectory(String descDirName) {
        String descDirNames = descDirName;
        if (!descDirNames.endsWith(File.separator)) {
            descDirNames = descDirNames + File.separator;
        }
        File descDir = new File(descDirNames);
        if (descDir.exists()) {
            log.debug("目录 " + descDirNames + " 已存在!");
            return false;
        }
        // 创建目录
        if (descDir.mkdirs()) {
            log.debug("目录 " + descDirNames + " 创建成功!");
            return true;
        } else {
            log.debug("目录 " + descDirNames + " 创建失败!");
            return false;
        }

    }

    /**
     * 写入文件
     *
     * @param fileName 要写入的文件
     * @param content  要写入内容
     * @param append   true-内容加入到文件末尾，false-覆盖文件内容
     */
    public static void writeToFile(String fileName, String content, boolean append) {
        try {
            FileUtil.write(new File(fileName), content, "utf-8", append);
            log.debug("文件 " + fileName + " 写入成功!");
        } catch (IOException e) {
            log.debug("文件 " + fileName + " 写入失败! " + e.getMessage());
        }
    }

    /**
     * 写入文件
     *
     * @param fileName 要写入的文件
     * @param content  要写入内容
     * @param encoding 编码格式
     * @param append   true-内容加入到文件末尾，false-覆盖文件内容
     */
    public static void writeToFile(String fileName, String content, String encoding, boolean append) {
        try {
            FileUtil.write(new File(fileName), content, encoding, append);
            log.debug("文件 " + fileName + " 写入成功!");
        } catch (IOException e) {
            log.debug("文件 " + fileName + " 写入失败! " + e.getMessage());
        }
    }

    /**
     * 压缩文件或目录
     *
     * @param srcDirName   压缩的根目录
     * @param fileName     根目录下的待压缩的文件名或文件夹名，其中*或""表示跟目录下的全部文件
     * @param descFileName 目标zip文件
     */
    public static void zipFiles(String srcDirName, String fileName,
                                String descFileName) {
        // 判断目录是否存在
        if (srcDirName == null) {
            log.debug("文件压缩失败，目录 " + srcDirName + " 不存在!");
            return;
        }
        File fileDir = new File(srcDirName);
        if (!fileDir.exists() || !fileDir.isDirectory()) {
            log.debug("文件压缩失败，目录 " + srcDirName + " 不存在!");
            return;
        }
        String dirPath = fileDir.getAbsolutePath();
        File descFile = new File(descFileName);
        ZipOutputStream zouts = null;
        try {
            zouts = new ZipOutputStream(new FileOutputStream(
                    descFile));
            /*20200408 add by chenyl for 设置压缩的编码，解决压缩路径中的中文乱码问题 */
            zouts.setEncoding("GBK");
            if ("*".equals(fileName) || "".equals(fileName)) {
                FileUtil.zipDirectoryToZipFile(dirPath, fileDir, zouts);
            } else {
                File file = new File(fileDir, fileName);
                if (file.isFile()) {
                    FileUtil.zipFilesToZipFile(dirPath, file, zouts);
                } else {
                    FileUtil
                            .zipDirectoryToZipFile(dirPath, file, zouts);
                }
            }

            log.debug(descFileName + " 文件压缩成功!");
        } catch (Exception e) {
            log.debug("文件压缩失败：" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (null != zouts) {
                try {
                    zouts.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }

        }

    }

    /**
     * 根据文件清单压缩文件
     *
     * @param filePaths   文件清单（绝对路径）
     * @param zipFilePath 输出压缩文件ZIP（绝对路径）
     */
    public static void zipFilesByList(List<String> filePaths, String zipFilePath) {
        String tempPath = "";
        try {
            File zipFile = new File(zipFilePath);
            //zip文件不存在，则创建文件，用于压缩
            if (!zipFile.exists())
                FileUtil.createFile(zipFilePath);
            String filePath = FileUtil.path(zipFilePath);
            tempPath = filePath.substring(0, filePath.length() - FileUtil.getFileType(zipFilePath).length() - 1);
            // 创建临时目录
            FileUtil.createDirectory(tempPath);
            for (String path : filePaths) {
                File f = new File(path);
                if (f.isFile() && f.exists()) {
                    path = FileUtil.path(path);
                    String npath = tempPath + path.substring(path.indexOf("/"));
                    FileUtil.copyFile(path, npath);
                    System.out.println("源文件【" + path + "】,拷贝到【" + npath + "】");
                }

            }

            FileUtil.zipFiles(tempPath, "*", zipFilePath);
        } catch (Exception e) {
            System.out.println("操作失败");
        } finally {
            FileUtil.deleteDirectory(tempPath);
        }
    }

    /**
     * 解压缩ZIP文件，将ZIP文件里的内容解压到descFileName目录下
     *
     * @param zipFileName  需要解压的ZIP文件
     * @param descFileName 目标文件
     * @param encoding     解压zip字符集，为空则默认gbk
     * @param index        当前已经尝试解压次数，每次解压zip最多尝试两次，分别使用gbk和utf-8进行解压，index大于或等于1则只解压一次
     * @return 解压成功-true，解压失败-false
     */
    public static boolean unZipFiles(String zipFileName, String descFileName, String encoding, int index) {
        //当前解压次数，防止递归陷入死循环
        index = index <= 0 ? 0 : index;
        String descFileNames = descFileName;
        encoding = DataUtil.isNullStr(encoding) ? "GBK" : "UTF-8";
        if (!descFileNames.endsWith(File.separator)) {
            descFileNames = descFileNames + File.separator;
        }
        OutputStream os = null;
        InputStream is = null;
        ZipFile zipFile = null;

        try {
            // 根据ZIP文件创建ZipFile对象
            zipFile = new ZipFile(zipFileName, encoding);
            ZipEntry entry = null;
            String entryName = null;
            String descFileDir = null;
            byte[] buf = new byte[4096];
            int readByte = 0;
            // 获取ZIP文件里所有的entry
            @SuppressWarnings("rawtypes")
            Enumeration enums = zipFile.getEntries();
            boolean chgEncoding = false;
            // 遍历所有entry
            while (enums.hasMoreElements()) {
                entry = (ZipEntry) enums.nextElement();
                // 获得entry的名字
                entryName = entry.getName();
                //判断是否存在乱码，存在乱码但已经尝试解压一次则继续按当前字符集解压完
                if (isMessyCode(entryName) && index < 1) {
                    deleteDirectoryFiles(descFileName);
                    chgEncoding = true;
                    break;
                }
                descFileDir = descFileNames + entryName;
                if (entry.isDirectory()) {
                    // 如果entry是一个目录，则创建目录
                    new File(descFileDir).mkdirs();
                    continue;
                } else {
                    // 如果entry是一个文件，则创建父目录
                    new File(descFileDir).getParentFile().mkdirs();
                }
                File file = new File(descFileDir);
                // 打开文件输出流
                os = new FileOutputStream(file);
                // 从ZipFile对象中打开entry的输入流
                is = zipFile.getInputStream(entry);
                while ((readByte = is.read(buf)) != -1) {
                    os.write(buf, 0, readByte);
                }

            }

            //判断是否改变字符集进行重试解压
            if (chgEncoding) {
                index++;
                encoding = "GBK".equals(encoding.toUpperCase()) ? "UTF-8" : "GBK";
                unZipFiles(zipFileName, descFileNames, encoding, index);
            }
            log.debug("文件解压成功!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("文件解压失败：" + e.getMessage());
            return false;
        } finally {
            if (null != os) {
                try {
                    os.close();
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
			if(null != zipFile){
				try {
					zipFile.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
        }
    }

    private static boolean isMessyCode(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            // 当从Unicode编码向某个字符集转换时，如果在该字符集中没有对应的编码，则得到0x3f（即问号字符?）
            // 从其他字符集向Unicode编码转换时，如果这个二进制数在该字符集中没有标识任何的字符，则得到的结果是0xfffd
            if ((int) c == 0xfffd) {
                // 存在乱码
                return true;
            }
        }
        return false;
    }


    /**
     * 将目录压缩到ZIP输出流
     *
     * @param dirPath 目录路径
     * @param fileDir 文件信息
     * @param zouts   输出流
     */
    public static void zipDirectoryToZipFile(String dirPath, File fileDir,
                                             ZipOutputStream zouts) {
        if (fileDir.isDirectory()) {
            File[] files = fileDir.listFiles();
            // 空的文件夹
            if (files.length == 0) {
                // 目录信息
                ZipEntry entry = new ZipEntry(getEntryName(dirPath, fileDir));
                try {
                    zouts.putNextEntry(entry);
                    zouts.closeEntry();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }

            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    // 如果是文件，则调用文件压缩方法
                    FileUtil
                            .zipFilesToZipFile(dirPath, files[i], zouts);
                } else {
                    // 如果是目录，则递归调用
                    FileUtil.zipDirectoryToZipFile(dirPath, files[i],
                            zouts);
                }
            }

        }

    }

    /**
     * 将文件压缩到ZIP输出流
     *
     * @param dirPath 目录路径
     * @param file    文件
     * @param zouts   输出流
     */
    public static void zipFilesToZipFile(String dirPath, File file,
                                         ZipOutputStream zouts) {
        FileInputStream fin = null;
        ZipEntry entry = null;
        // 创建复制缓冲区
        byte[] buf = new byte[4096];
        int readByte = 0;
        if (file.isFile()) {
            try {
                // 创建一个文件输入流
                fin = new FileInputStream(file);
                // 创建一个ZipEntry
                entry = new ZipEntry(getEntryName(dirPath, file));
                // 存储信息到压缩文件
                zouts.putNextEntry(entry);
                // 复制字节到压缩文件
                while ((readByte = fin.read(buf)) != -1) {
                    zouts.write(buf, 0, readByte);
                }
                zouts.closeEntry();
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
            	if(null != fin){
					try {
						fin.close();
					} catch (IOException e) {
                System.out.println("出现异常");
            }

				}
			}
        }

    }

    /**
     * 获取待压缩文件在ZIP文件中entry的名字，即相对于跟目录的相对路径名
     *
     * @param dirPath 目录名
     * @param file   entry文件名
     * @return 待压缩文件在ZIP文件中entry的名字
     */
    private static String getEntryName(String dirPath, File file) {
        String dirPaths = dirPath;
        if (!dirPaths.endsWith(File.separator)) {
            dirPaths = dirPaths + File.separator;
        }
        String filePath = file.getAbsolutePath();
        // 对于目录，必须在entry名字后面加上"/"，表示它将以目录项存储
        if (file.isDirectory()) {
            filePath += "/";
        }
        int index = filePath.indexOf(dirPaths);

        return filePath.substring(index + dirPaths.length());
    }

    /**
     * 解压缩RAR文件，将RAR文件里的内容解压到descFileName目录下,只支持rar5.0以下版本压缩的rar文件解压
     *
     * @param zipFileName  需要解压的RAR文件
     * @param descFileName 目标文件
     * @return 解压成功-true，解压失败-false
     */
    public static boolean unRarFiles(String zipFileName, String descFileName) {
        String descFileNames = descFileName;
        if (!descFileNames.endsWith(File.separator)) {
            descFileNames = descFileNames + File.separator;
        }
        createDirectory(descFileName);
        Archive archive = null;
		FileOutputStream os = null;
		try {
            archive = new Archive(new FileInputStream(new File(zipFileName)));
            archive.getMainHeader().print();
            FileHeader fileHeader = archive.nextFileHeader();
            while (fileHeader != null) {
                // 解决中文乱码问题【压缩文件中文乱码】
                String fileName = fileHeader.getFileNameW().isEmpty() ? fileHeader.getFileNameString() : fileHeader.getFileNameW();
                // 文件夹
                if (fileHeader.isDirectory()) {
                    File fol = new File(descFileName + File.separator + fileName.trim());
                    fol.mkdirs();
                } else { // 文件
                    // 解决linux系统中\分隔符无法识别问题
                    String[] fileParts = fileName.split("\\\\");
                    StringBuilder filePath = new StringBuilder();
                    for (String filePart : fileParts) {
                        filePath.append(filePart).append(File.separator);
                    }
                    fileName = filePath.substring(0, filePath.length() - 1);
                    File out = new File(descFileName + File.separator + fileName.trim());
                    if (!out.exists()) {
                        // 相对路径可能多级，可能需要创建父目录.
                        if (!out.getParentFile().exists()) {
                            out.getParentFile().mkdirs();
                        }
                        out.createNewFile();
                    }
                    os= new FileOutputStream(out);
                    archive.extractFile(fileHeader, os);
                    os.close();
                }
                fileHeader = archive.nextFileHeader();
            }
            return true;
        } catch (Exception e) {
            log.debug("文件解压失败：" + e.getMessage());
            return false;
        } finally {
            if (null != archive) {
                try {
                    archive.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
			if (null != os) {
				try {
					os.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
        }
    }

    /**
     * 修复路径，将 \\ 或 / 等替换为 File.separator
     *
     * @param path 路径
     * @return 修复后的路径
     */
    public static String path(String path) {
        String p = StringUtil.replace(path, "\\", "/");
        p = StringUtil.join(StringUtil.split(p, "/"), "/");
        if (!StringUtil.startsWithAny(p, "/") && StringUtil.startsWithAny(path, "\\", "/")) {
            p = "/" + p;
        }
        if (!StringUtil.endsWithAny(p, "/") && StringUtil.endsWithAny(path, "\\", "/")) {
            p = p + "/";
        }
        return p;
    }

    /**
     * 保存文件
     *
     * @param stream   输入流
     * @param path     文件路径
     * @param filename 文件名称
     * @throws IOException
     */
    public static void SaveFileFromInputStream(InputStream stream, String path, String filename) throws IOException {
        FileOutputStream fs = null;

		try {
			fs = new FileOutputStream(path + "/" + filename);
			byte[] buffer = new byte[1024 * 1024];
			int bytesum = 0;
			int byteread = 0;
			while ((byteread = stream.read(buffer)) != -1) {
				bytesum += byteread;
				fs.write(buffer, 0, byteread);
				fs.flush();
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			if(null != fs){
				try {
					fs.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
			if(null != stream){
				try {
					stream.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
		}

    }

    /**
     * 根据文件路径获取对应的文件名称
     *
     * @param filePath 文件路径
     * @return 文件路径对应的文件名
     */
    public static String getFileName(String filePath) {
        String fileName = "";
        if (!DataUtil.isNullStr(filePath)) {
            String file = FileUtil.path(filePath);    // 格式化文件路径
            fileName = file.substring(file.lastIndexOf("/") + 1);    // 获取文件名
        }
        return fileName;
    }

    /**
     * 根据文件路径获取对应的文件文件类型
     *
     * @param filePath 文件路径
     * @return 文件路径对应的文件类型
     */
    public static String getFileType(String filePath) {
        String fileType = "";
        if (!DataUtil.isNullStr(filePath)) {
            String file = FileUtil.path(filePath);    // 格式化文件路径
            String fileName = file.substring(file.lastIndexOf("/") + 1);    // 获取文件名
            if (!DataUtil.isNullStr(fileName)) {
                fileType = fileName.substring(fileName.lastIndexOf(".") + 1); // 获取文件类型
            }
        }
        return fileType;
    }

    /**
     * 浏览器下载文件(单文件)
     *
     * @param uri              文件资源地址
     * @param downLoadFileName 下载时浏览器默认保存文件名
     * @param response         HttpServletResponse对象
     */
    public static void DownLoadFileByUri(String uri, String downLoadFileName, HttpServletResponse response) {
        InputStream in = null;
        OutputStream out = null;
        File file = null;
        try {
            //得到要下载的文件
            uri = path(uri);
            file = new File(uri);
            // 20180612 add by chenyl for 处理跨域请求问题
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Headers", "Authentication");
            if (file != null && file.exists() && file.isFile()) {
                if (DataUtil.isNullStr(downLoadFileName)) {
                    // 设置默认下载文件名称为保存的文件名
                    downLoadFileName = uri.substring(uri.lastIndexOf("/") + 1);
                }
                downLoadFileName = downLoadFileName.replaceAll("\r","")
                        .replaceAll("\n","")
                        .replaceAll("\\\\","");
                //设置响应头，控制浏览器下载该文件
                response.setContentType("application/x-msdownload");
                response.setContentLength((int) file.length());
                response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(downLoadFileName.getBytes("GBK"),// 只有GBK才可以
                        "iso8859-1") + "\"");
                //读取要下载的文件，保存到文件输入流
                in = new FileInputStream(uri);
                //创建输出流
                out = response.getOutputStream();
                //创建缓冲区
                byte buffer[] = new byte[1024];
                int len = 0;
                //循环将输入流中的内容读取到缓冲区当中
                while ((len = in.read(buffer)) > 0) {
                    //输出缓冲区的内容到浏览器，实现文件下载
                    out.write(buffer, 0, len);
                    out.flush();
                }
                out.flush();
            } else {
                response.getWriter().println("<script>");
                response.getWriter().println(" modals.info('文件不存在!');");
                response.getWriter().println("</script>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                //关闭文件输入流
                try {
                    in.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
            if (out != null) {
                //关闭输出流
                try {
                    out.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
        }
    }

    /**
     * 浏览器下载文件(多个文件压缩成zip下载)
     *
     * @param uriList          文件资源地址列表(每个要素存储的为文件存放的绝对路径)
     * @param downLoadFileName 下载时浏览器默认保存文件名
     * @param response         HttpServletResponse对象
     */
    public static void DownLoadFileByUri(List<String> uriList, String downLoadFileName, HttpServletResponse response) {
        if (null == uriList || (null != uriList && uriList.isEmpty())) {
            log.error("文件资源列表不能空");
            throw new BaseException(SysErr.E_MESSAGE, "文件资源列表不能空");
        }
        // 设置默认的下载文件显示名称
        String fileName = "download_" + DateUtil.getDate() + DateUtil.getTime() + ".zip";
        InputStream in = null;
        OutputStream out = null;
        File file = null;
        String uri = ParamUtil.getUploadFile() + "/" + fileName; // 临时文件存储路径
        File zipFile = new File(uri); // 定义压缩文件名称
        ZipOutputStream zipOut = null;// 声明压缩流对象
        InputStream input = null;

        try {
            createDirectory(ParamUtil.getUploadFile()); // 创建保存文件临时目录
            // 将要压缩的文件加入到压缩输出流中
            zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
            // 解决中文文件名乱码
            zipOut.setEncoding(CHINESE_CHARSET);
            for (String path : uriList) {
                file = new File(path);
                input = new FileInputStream(file);// 定义文件的输入流
                zipOut.putNextEntry(new ZipEntry(file.getName())); // 设置ZipEntry对象
                // 将文件写入到压缩文件中
                int temp = 0;
                while ((temp = input.read()) != -1) { // 读取内容
                    zipOut.write(temp); // 写到压缩文件中
                }
            }
            zipOut.flush();
        } catch (Exception e) {
            log.error("创建临时压缩文件失败", e);
            e.printStackTrace();
        } finally {
            if (null != input) {
                try {
                    input.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
            if (null != zipOut) {
                try {
                    zipOut.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
        }

        try {
            // 得到要下载的文件
            if (!DataUtil.isNullStr(downLoadFileName)) {
                fileName = downLoadFileName
                        .replaceAll("\r","")
                        .replaceAll("\n","")
                        .replaceAll("\\\\","");
            }
            // 20180612 add by chenyl for 处理跨域请求问题
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Headers", "Authentication");
            if (zipFile != null && zipFile.exists() && zipFile.isFile()) {
                // 设置响应头，控制浏览器下载该文件
                response.setContentType("application/x-msdownload");
                response.setContentLength((int) zipFile.length());
                response.addHeader("Content-Disposition",
                        "attachment; filename=\"" + new String(fileName.getBytes("GBK"), // 只有GBK才可以
                                "iso8859-1") + "\"");
                // 读取要下载的文件，保存到文件输入流
                in = new FileInputStream(uri);
                // 清空response
                response.reset();
                // 创建输出流
                out = response.getOutputStream();
                // 创建缓冲区
                byte buffer[] = new byte[1024];
                int len = 0;
                // 循环将输入流中的内容读取到缓冲区当中
                while ((len = in.read(buffer)) > 0) {
                    // 输出缓冲区的内容到浏览器，实现文件下载
                    out.write(buffer, 0, len);
                }
                out.flush();
            } else {
                response.getWriter().println("<script>");
                response.getWriter().println(" modals.info('文件不存在!');");
                response.getWriter().println("</script>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                // 关闭文件输入流
                try {
                    in.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
            if (out != null) {
                // 关闭输出流
                try {
                    out.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
            // 将生成的服务器端文件删除
            deleteFile(uri);
        }
    }


    /**
     * 读取txt文件内容并返回对应内容字符串
     *
     * @param path
     * @return
     */
    public static String readTxtFileByPath(String path) {
        System.out.println("文件保存路径：" + path);
        //开始解析文件
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
		InputStreamReader inputStreamReader = null;
        try {
            File file = new File(path);
            if (file.exists()) {//文件存在
				inputStreamReader = new InputStreamReader(new FileInputStream(file), "utf-8");
                reader = new BufferedReader(inputStreamReader);
                if (null == reader) {
                    throw new BaseException(SysErr.E_IO_ERROR, ",文件读取失败");
                }
                String tempString = null;
                // 一次读入一行，直到读入null为文件结束
                while ((tempString = reader.readLine()) != null) {
                    content.append(tempString).append("\r\n");
                }
            }
        } catch (IOException e) {
                System.out.println("出现异常");
            } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
			if(null != inputStreamReader){
				try {
					inputStreamReader.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
		}

        return content.toString();
    }

    /**
     * 根据文件保存路径生成对应页面预览文件路径
     * 如果文件为pdf、excel、ppt、word则需要转成成对应的预览html文件，其他则直接迁移到文件上传路径下的userfiles下
     *
     * @param filePath  文件保存的全路径
     * @param overWrite 是否覆盖，true-覆盖、false-不覆盖
     * @return 对应文件预览的全路径
     */
    public static String getPreviewFilePath(String filePath, boolean overWrite) {
        String previewUrl = getPreviewFilePath(filePath, "", overWrite);
        return previewUrl;
    }

    /**
     * 根据文件保存路径生成对应页面预览文件路径
     * 如果文件为pdf、excel、ppt、word则需要转成成对应的预览html文件，其他则直接迁移到文件上传路径下的userfiles下
     *
     * @param filePath  文件保存的全路径
     * @param prePath   文件路径前缀 为空时取Servlets.getWebContextPath();
     * @param overWrite 是否覆盖，true-覆盖、false-不覆盖
     * @return 对应文件预览的全路径
     */
    public static String getPreviewFilePath(String filePath, String prePath, boolean overWrite) {
        String previewUrl = "";
        // 生成的预览路径不含ip和端口
        String preUrl = Servlets.getWebContextPath();
        if (!DataUtil.isNullStr(prePath)) {
            preUrl = prePath;
        }
        String file = FileUtil.path(filePath);    // 格式化文件路径
        String fileName = file.substring(file.lastIndexOf("/") + 1);    // 获取文件名
        String htmlFile = file.substring(file.lastIndexOf("/") + 1, file.lastIndexOf(".")) + ".html";

        if (fileName.toLowerCase().endsWith(".doc") || fileName.toLowerCase().endsWith(".docx")
                || fileName.toLowerCase().endsWith(".xls") || fileName.toLowerCase().endsWith(".xlsx")
                || fileName.toLowerCase().endsWith(".ppt") || fileName.toLowerCase().endsWith(".pptx")) {
            // 如果是属于office文件的，则转换成对应的html文件
            String htmlPath = ParamUtil.USERFILES_BASE_URL + "preview/" + file.substring(file.lastIndexOf("/") + 1, file.lastIndexOf("."));
            if (file.indexOf(ParamUtil.getUploadFile() + htmlPath) == -1 || overWrite) {
                // 如果不存在预览html，则生成对应的
                createDirectory(ParamUtil.getUploadFile() + htmlPath);
                Office2Html.toHtml(file, ParamUtil.getUploadFile() + htmlPath + "/" + htmlFile);
            }
            previewUrl = preUrl + htmlPath + "/" + htmlFile;
        } else if (fileName.toLowerCase().endsWith(".pdf")) {
            // 如果是pdf则使用pdf预览路径
            if (file.indexOf(ParamUtil.getUploadFile() + ParamUtil.USERFILES_BASE_URL) == -1 || overWrite) {
                // 否则把文件复制到上传目录下的userfiles下
                StringBuffer sb = new StringBuffer();
                sb.append(ParamUtil.USERFILES_BASE_URL).append("preview");
                createDirectory(ParamUtil.getUploadFile() + sb.toString());
                previewUrl = preUrl + "/b_base/pdfjs/web/viewer.html?file=" + preUrl + sb.toString() + "/" + fileName;
                String copyedFile = ParamUtil.getUploadFile() + sb.toString() + "/" + fileName;
                copyFile(file, copyedFile);
            } else {
                // 其他文件检查是否保存在上传路径下的userfiles下，是的则直接返回对应的预览路径
                previewUrl = preUrl + "/b_base/pdfjs/web/viewer.html?file=" + preUrl + ParamUtil.USERFILES_BASE_URL + file.split(ParamUtil.USERFILES_BASE_URL)[1];
            }
        } else {
            if (file.indexOf(ParamUtil.getUploadFile() + ParamUtil.USERFILES_BASE_URL) == -1 || overWrite) {
                // 否则把文件复制到上传目录下的userfiles下
                StringBuffer sb = new StringBuffer();
                sb.append(ParamUtil.USERFILES_BASE_URL).append("preview");
                createDirectory(ParamUtil.getUploadFile() + sb.toString());
                previewUrl = preUrl + sb.toString() + "/" + fileName;
                String copyedFile = ParamUtil.getUploadFile() + sb.toString() + "/" + fileName;
                copyFile(file, copyedFile);
            } else {
                // 其他文件检查是否保存在上传路径下的userfiles下，是的则直接返回对应的预览路径
                previewUrl = preUrl + ParamUtil.USERFILES_BASE_URL + file.split(ParamUtil.USERFILES_BASE_URL)[1];
            }
        }
        return previewUrl;
    }

    /**
     * 递归获取当前路径下的所有文件，文件夹下的文件，并输出
     *
     * @param path 当前路径
     * @return
     */
    public static List<File> listFiles(String path) {
        List<File> fileList = new ArrayList<File>();
        File file = new File(path);
        // 如果这个路径是文件夹
        if (file.isDirectory()) {
            // 获取路径下的所有文件
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                // 如果还是文件夹 递归获取里面的文件 
                if (files[i].isDirectory()) {
                    listFiles(files[i].getPath());
                    fileList.add(files[i]);
                } else {
                    fileList.add(files[i]);
                }

            }
        } else {
            fileList.add(file);
        }
        return fileList;
    }

    /**
     * 从绝对路径中读取文件
     *
     * @param path
     * @return
     * @author lijunbin
     */
    public static String readFileFromPath(String path) {
        try (InputStream is = new FileInputStream(path)) {
            return StreamUtils.copyToString(is, Charset.defaultCharset());
        } catch (Exception e) {
            log.debug(String.format("Failed to load file from path: %s: %s", path, e.getMessage()));
            return null;
        }
    }

    /**
     * 从Classpath中读取文件
     *
     * @param path
     * @return
     * @author lijunbin
     * @date 2019年4月30日 下午12:24:14
     */
    public static String readFileFromClasspath(String path) {
        ClassPathResource classPathResource = new ClassPathResource(path);
        try (InputStream is = classPathResource.getInputStream()) {
            return StreamUtils.copyToString(is, Charset.defaultCharset());
        } catch (Exception e) {
            log.debug(String.format("Failed to load file from url: %s: %s", path, e.getMessage()));
            return null;
        }
    }

    /**
     * 零拷贝复制
     *
     * @param srcPath  源文件或目录
     * @param destPath 目标文件或目录，目标不存在会自动创建（目录、文件都创建）
     */
    public static void zeroCopy(String srcPath, String destPath) {
        createFile(destPath);
        try (FileChannel inputChannel = new FileInputStream(srcPath).getChannel();
             FileChannel outputChannel = new FileOutputStream(destPath).getChannel()
        ) {
            inputChannel.transferTo(0, inputChannel.size(), outputChannel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 替换文件中的内容
     *
     * @param file
     * @param oldStr
     * @param newStr
     */
    public static void replaceFileContext(File file, List<String> oldStr, List<String> newStr) {
        BufferedReader br_File = null;
        CharArrayWriter caw = null;
        BufferedWriter bw_File = null;
        try {
            if (file.isDirectory()) {
                log.error(file.getAbsolutePath() + "该文件是一个目录，无法进行替换！");
                return;
            }
            if (file.getName().contains("jpg")) {
                log.warn("该文件是jpg类型，无法进行替换！");
                return;
            }
            br_File = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            if (null == br_File) {
                throw new BaseException(SysErr.E_IO_ERROR, "待替换文件读取失败");
            }
            caw = new CharArrayWriter();
            String string;
            while ((string = br_File.readLine()) != null) {
                // 判断是否包含目标字符，包含则替换
                for (int i = 0; i < oldStr.size(); i++) {
                    if (string.contains(oldStr.get(i))) {
                        string = new String(string.replace(oldStr.get(i), newStr.get(i)));
                    }
                }
                // 写入内容并添加换行
                caw.write(string);
                caw.write("\r\n");
            }
            bw_File = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            caw.writeTo(bw_File);
        } catch (Exception e) {
            log.error("替换文件内容异常！", e);
        } finally {
            if (null != br_File) {
                try {
                    br_File.close();
                } catch (IOException e) {
                    log.error("BufferedReader关闭异常！", e);
                }
            }
            if (null != caw) {
                caw.close();
            }
            if (null != bw_File) {
                try {
                    bw_File.close();
                } catch (IOException e) {
                    log.error("BufferedWriter关闭异常！", e);
                }
            }
        }
    }

    public static void folderMethod(File file, List<String> oldStr, List<String> newStr) {
        if (file.exists()) {
            String fileName = file.getName();
            String path = FileUtil.path(file.getAbsolutePath());
            String rootPath = path.substring(0, path.lastIndexOf("/"));
            File newFile = null;
            boolean isDirec = file.isDirectory();
            for (int i = 0; i < oldStr.size(); i++) {
                if (fileName.contains(oldStr.get(i))) {
                    newFile = new File(rootPath + "/" + fileName.replaceAll(oldStr.get(i), newStr.get(i)));
                    if (newFile.exists()) {
                        newFile.delete();
                    }
                    file.renameTo(newFile);
                }
            }
            if (isDirec) {//文件夹类型
                File[] childs = null;
                if (newFile != null) {
                    childs = newFile.listFiles();
                } else {
                    childs = file.listFiles();
                }
                if (null != childs) {
                    for (File file2 : childs) {
                        folderMethod(file2, oldStr, newStr);
                    }
                }
            } else {
                if (newFile != null) {
                    FileUtil.replaceFileContext(newFile, oldStr, newStr);
                } else {
                    FileUtil.replaceFileContext(file, oldStr, newStr);
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }

}
