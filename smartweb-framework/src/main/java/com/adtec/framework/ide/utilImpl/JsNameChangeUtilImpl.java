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
import java.util.HashMap;
import java.util.Map;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.ide.abstractutil.AbstractJsNameChangeUtil;

public class JsNameChangeUtilImpl extends AbstractJsNameChangeUtil {

	@Override
	public Map<String, File> JsNameChangeUtil(String oldPathUrl, String newPathUrlName, String jspPath) {
		oldPathUrl = oldPathUrl.replaceAll("//", "/");
		newPathUrlName = newPathUrlName.replaceAll("//", "/");
		String newUrl = newPathUrlName;
		newPathUrlName = newPathUrlName.replaceFirst("\\.js", "\\.jsp");
		BufferedReader jsread = null;
		BufferedWriter jspre = null;
		InputStreamReader jsbeg = null;
		Map<String, File> fileMap = new HashMap<String, File>();
		try {
			String fileName = jspPath + newPathUrlName;
			jsbeg = new InputStreamReader(new FileInputStream(fileName), "UTF-8");
			jsread = new BufferedReader(jsbeg);
			File file = new File(fileName + "tmp");
			jspre = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			if (null == jsread) {
				throw new BaseException(SysErr.E_IO_ERROR, "读取失败");
			}
			String strtemp = null;
			while ((strtemp = jsread.readLine()) != null) {
				if (strtemp.indexOf(oldPathUrl) != -1) {
					strtemp = strtemp.replaceFirst(oldPathUrl, newUrl);
				}
				jspre.write(strtemp + "\n");
			}
			jspre.flush();

			File fileSec = new File(fileName);
			fileMap.put("tmpFile", file);
			fileMap.put("newFile", fileSec);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
                System.out.println("出现异常");
            } finally {
			if(null != jsread){
				try {
					jsread.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
			if(null != jspre){
				try {
					jspre.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
			if(null != jsbeg) {
				try {
					jsbeg.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
		}
		return fileMap;
	}

	@Override
	public void jsGeneratorUtil() {

	}

}
