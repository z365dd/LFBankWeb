package com.adtec.comp.ctrl.test.util;

import com.adtec.comp.ctrl.test.dto.FCtrlTranFileDTO;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;

import java.io.*;

public class CtrlFileUtil {
	
	/**
	 * 获取文件保存路径
	 * @param ralativePah 相对路径
	 * @return
	 */
	public static String getSavePath(String ralativePah){
		return ParamUtil.getUploadFile() + "/CtrlCaseTest/" + ralativePah;
	} 
	
	//获取文件（按行读取）
	public static FCtrlTranFileDTO getFile(String file){
		BufferedReader reader = null;
		FCtrlTranFileDTO fdto = new FCtrlTranFileDTO();
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
			String tempStr = null;
			// 一次读入一行，直到读入null为文件结束
			while(reader != null && (tempStr = reader.readLine()) != null){
//				System.out.println("文件数据："+tempStr);
				String []tempArry = tempStr.split("\\#\\#",-1);
				fdto.setCOMP_NO(tempArry[0]);
				fdto.setSVC_CODE(tempArry[1]);
				fdto.setSUB_SVC_CODE(tempArry[2]);
				fdto.setPUB_DIM_LIST(tempArry[3]);
				fdto.setPRI_DIM_LIST(tempArry[4]);
				fdto.setTRL_LIST(tempArry[5]);
				fdto.setTRAN_AMT(Double.parseDouble(tempArry[6]));
			}
			reader.close();
		} catch (IOException e) {
                System.out.println("出现异常");
            }finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
		}
		return fdto;
	}
		
	//保存案例数据到文件
	public static void saveFile(FCtrlTranFileDTO fileDto){
		//文件要保存的路径
		String fileName = fileDto.getFileName();
		String filePath = fileDto.getFilePath();
		String flg = fileDto.getFlg();
		//文件要保存的内容(xxx##xxx##xxx)
		StringBuilder str = new StringBuilder();
		str.append(fileDto.getCOMP_NO()).append("##");
		str.append(fileDto.getSVC_CODE()).append("##");
		str.append(fileDto.getSUB_SVC_CODE()).append("##");
		str.append(fileDto.getPUB_DIM_LIST()).append("##");
		str.append(fileDto.getPRI_DIM_LIST()).append("##");
		str.append(fileDto.getTRL_LIST()).append("##");
		str.append(fileDto.getTRAN_AMT());
		FileWriter writer = null;
		File file;
		// 上传文件路径(控制检查、授权检查的路径不一致)
		if (filePath == null) {
			if ("ctrl".equals(flg)) {
				filePath = CtrlFileUtil.getSavePath("CtrlTest");
			} else {
				filePath = CtrlFileUtil.getSavePath("AuthTest");
			}
		}
		try {
			  //先创建文件夹
			  file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			fileName = filePath + "/" + fileName;
			writer = new FileWriter(fileName);
			if(writer == null){
				throw new BaseException(SysErr.E_IO_ERROR, "读取文件失败");
			}
			writer.write(str.toString());
			writer.flush();
		} catch (IOException e) {
                System.out.println("出现异常");
            } finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}

		}
	}
	
	
}
