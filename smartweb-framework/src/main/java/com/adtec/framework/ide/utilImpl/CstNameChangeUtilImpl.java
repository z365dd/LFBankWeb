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
import com.adtec.framework.ide.abstractutil.AbstractCstNameChangeUtil;

public class CstNameChangeUtilImpl extends AbstractCstNameChangeUtil {

	@Override
	public Map<String, File> CstNameChangeUtil(String oldPathUrl,
			String newPathUrlName, String jspPath) {
		// TODO Auto-generated method stub
		oldPathUrl = oldPathUrl.replaceAll("//", "/");
		newPathUrlName = newPathUrlName.replaceAll("//", "/");
		String newUrl = newPathUrlName;
		newPathUrlName = newPathUrlName.replaceFirst("\\.cst", "\\.jsp");
		BufferedReader cstread = null;
		BufferedWriter cstpre = null;
		InputStreamReader cstbeg = null;
		try {
			String fileName = jspPath + newPathUrlName;
			cstbeg = new InputStreamReader(new FileInputStream(fileName), "UTF-8");
			cstread = new BufferedReader(cstbeg);
			File file = new File(fileName + "tmp");
			cstpre = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			if (null == cstread) {
				throw new BaseException(SysErr.E_IO_ERROR, "CST读取失败");
			}
			String strtemp = null;
			while ((strtemp = cstread.readLine()) != null) {
				if (strtemp.indexOf(oldPathUrl) != -1) {
					strtemp = strtemp.replaceFirst(oldPathUrl, newUrl);
				}
				cstpre.write(strtemp + "\n");
			}
			cstpre.flush();

			File fileSec = new File(fileName);
			Map<String, File> fileMap = new HashMap<String, File>();
			fileMap.put("tmpFile", file);
			fileMap.put("newFile", fileSec);
			return fileMap;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
                System.out.println("出现异常");
            } finally {
			if(null != cstread){
				try {
					cstread.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
			if(null != cstpre){
				try {
					cstpre.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
			if(null != cstbeg){
				try {
					cstbeg.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
		}
		return null;
	}

}
