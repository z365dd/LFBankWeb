package com.adtec.framework.ide.utilImpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.adtec.framework.common.util.FileUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.ide.abstractutil.AbstractJsLoadingUtil;
import com.adtec.sys.common.web.Servlets;

/**
 * @类名 JsLoadingUtilImpl.java
 * @描述: 关于js编辑器的抽象的实现
 * @版本 v1.0
 */
public class JsLoadingUtilImpl extends AbstractJsLoadingUtil {
	private final static Logger logger = LoggerFactory.getLogger(JsLoadingUtilImpl.class);
	
	@Override
	public boolean setJsLoading(String content, String pathUrl) {
		BufferedWriter jspbw = null;
		BufferedWriter mjspbw = null;
		try {
			String jsPath = ParamUtil.getJsPath();
			pathUrl = pathUrl.replaceFirst("\\.jsp", "\\.js");
			String filePath = jsPath + pathUrl;
			logger.info("filePath:[{}]", new Object[] { filePath });
			//.metadata_path 路径
			String webroot = ParamUtil.getConfig("web.path");
			String metadata_path = jsPath+pathUrl;
			logger.info("metadata_path:[{}]", new Object[] { metadata_path });
			String mFilePath = FileUtil.path(Servlets.getRequest().getRealPath(metadata_path.substring(metadata_path.indexOf(webroot)+webroot.length())));
			logger.info("文件:[{} {}]", new Object[] { filePath },new Object[] { mFilePath });
			
			File file = new File(filePath);
			File mFile = new File(mFilePath);

			if (!file.exists()) {
				FileUtil.createFile(filePath);
			}
			
			if(!mFile.exists()){
				FileUtil.createFile(mFilePath);
			}

			jspbw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			mjspbw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mFile), "UTF-8"));
			
			jspbw.write(content, 0, content.length());
			jspbw.write("\n");
			jspbw.flush();
			mjspbw.write(content, 0, content.length());
			mjspbw.write("\n");
			mjspbw.flush();
			return true;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
                System.out.println("出现异常");
            } finally {
			if(jspbw != null){
				try {
					jspbw.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
			if(mjspbw != null){
				try {
					mjspbw.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}

		}
		return false;
	}

	@Override
	public void jsGeneratorUtil() {

	}

	@Override
	public String getJsLoading(String pathUrl) {

		logger.info("文件:[{}]", new Object[] { pathUrl });
		pathUrl = pathUrl.replaceFirst("\\.jsp", "\\.js");
		String webroot = ParamUtil.getConfig("web.path");
		String jsPath = ParamUtil.getJsPath()+pathUrl;
		String path = FileUtil.path(Servlets.getRequest().getRealPath(jsPath.substring(jsPath.indexOf(webroot)+webroot.length())));
		logger.info("path:[{}]", new Object[] { path });
		BufferedReader jsdec = null;
		InputStreamReader jsbeg = null;
		String str = null;
		try {

			String fileName = path;
			logger.info("文件全路径:[{}]", new Object[] { fileName });
			File file = new File(fileName);
			if (!file.exists()) {
				logger.info("本地没有JS,获取不了  故创建文件：[{}]", new Object[] { file });
				FileUtil.createFile(fileName);
			}
			jsbeg = new InputStreamReader(new FileInputStream(fileName), "UTF-8");
			jsdec = new BufferedReader(jsbeg);
			if (null == jsdec) {
				throw new BaseException(SysErr.E_IO_ERROR, "js读取失败");
			}

			String strtemp = null;
			while ((strtemp = jsdec.readLine()) != null) {
				str += strtemp + "\n";
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
                System.out.println("出现异常");
            } finally {
			if(null != jsdec){
				try {
					jsdec.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
			if(null != jsbeg){
				try {
					jsbeg.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
		}
		return str;
	}

}
