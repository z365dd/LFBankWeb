package com.adtec.comp.fsvr.util;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.common.util.FileUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.sys.common.utils.SeqUtil;
import com.adtec.sys.common.utils.SysUtil;
import com.adtec.sys.modules.sys.utils.UserUtils;

import java.io.*;

public class FsvrUtil {
	
	/**
	 * 设置请求头
	 * @param busiNo
	 * @param req
	 * @param svcCode
	 * @return
	 */
	public static void setReqHead(String busiNo,ReqDTO req,String svcCode){
		setReqHead(busiNo, req, svcCode, 0, 0);
	}
	
	/**
	 * 设置请求头
	 * @param req
	 * @param svcCode
	 */
	public static void setReqHead(ReqDTO req,String svcCode){
		setReqHead(null, req, svcCode, 0, 0);
	}
	
	
	/**
	 * 复制单个文件
	 * @param srcFileName 待复制的文件名
	 * @param descFileName 目标文件名
	 * @param coverlay 如果目标文件已存在，是否覆盖
	 * @return 如果复制成功，则返回true，否则返回false
	 */
	public static boolean copyFileCover(String srcFileName,
			String descFileName, boolean coverlay) {
		File srcFile = new File(srcFileName);
		// 判断源文件是否存在
		if (!srcFile.exists()) {
			System.out.println("复制文件失败，源文件 " + srcFileName + " 不存在!");
			return false;
		}
		// 判断源文件是否是合法的文件
		else if (!srcFile.isFile()) {
			System.out.println("复制文件失败，" + srcFileName + " 不是一个文件!");
			return false;
		}
		File descFile = new File(descFileName);
		// 判断目标文件是否存在
		if (descFile.exists()) {
			// 如果目标文件存在，并且允许覆盖
			if (coverlay) {
				System.out.println("目标文件已存在，准备删除!");
				if (!FileUtil.delFile(descFileName)) {
					System.out.println("删除目标文件 " + descFileName + " 失败!");
					return false;
				}
			} else {
				System.out.println("复制文件失败，目标文件 " + descFileName + " 已存在!");
				return false;
			}
		} else {
			if (!descFile.getParentFile().exists()) {
				// 如果目标文件所在的目录不存在，则创建目录
				System.out.println("目标文件所在的目录不存在，创建目录!");
				// 创建目标文件所在的目录
				if (!descFile.getParentFile().mkdirs()) {
					System.out.println("创建目标文件所在的目录失败!");
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
			System.out.println("复制单个文件 " + srcFileName + " 到" + descFileName
					+ "成功!");
			return true;
		} catch (Exception e) {
			System.out.println("复制文件失败：" + e.getMessage());
			return false;
		} finally {
			// 关闭输入输出流，首先关闭输出流，然后再关闭输入流
			if (outs != null) {
				try {
					outs.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
		}
	}
	/**
	 * 设置请求头
	 * @param busiNo
	 * @param req
	 * @param svcCode
	 * @param start
	 * @param pageSize
	 * @return
	 */
	public static void setReqHead(String busiNo,ReqDTO req,String svcCode, int start,int pageSize){
	
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		
		//公共请求头
		httpJsonFactory.buildReqHead(svcCode, busiNo, start, pageSize);
		
		//GPM请求头
		// 系统头--TODO默认使用999000
		/*ResourceBundle source = ResourceBundle.getBundle("busiConfig");
		String modelNo = source.getString("FSVR_ModelNo");*/
		String modelNo = ParamUtil.getConfig("FSVR_PARTID");
		req.getSYS_HEAD().setREQ_MODL_NO(modelNo);	//请求组件/模型编号
		String seqNo = SeqUtil.getMBCSeq();
		req.getSYS_HEAD().setREQ_SEQ_NO(DateUtil.getDate() + seqNo); // 服务请求发送方流水号
		req.getSYS_HEAD().setSEQ_NO(DateUtil.getDate() + seqNo);//内部全局流水号
		req.getSYS_HEAD().setREQ_DATE(DateUtil.getDate());
		/*修改*/
		req.getSYS_HEAD().setREQ_COMP_NO(modelNo);	//请求组件/模型编号
		req.getSYS_HEAD().setREQ_SEQ(DateUtil.getDate() + seqNo); // 服务请求发送方流水号
		req.getSYS_HEAD().setSND_SEQ(DateUtil.getDate() + seqNo);//内部全局流水号
		
		req.getSYS_HEAD().setREQ_NODE_NO(modelNo);
		req.getSYS_HEAD().setMACH_DATE(DateUtil.getDate());
		req.getSYS_HEAD().setREQ_IP(SysUtil.getLocalIp());
	
		// 应用头
		req.getAPP_HEAD().setBRCH(UserUtils.getUser().getOffice().getBrchCode());
		req.getAPP_HEAD().setTLR_NO(UserUtils.getUser().getLoginName());
		req.getAPP_HEAD().setTX_DATE(DateUtil.getDate());//交易日期
		req.getAPP_HEAD().setTX_TIME(DateUtil.getTime());//交易时间
		/*修改*/
		req.getAPP_HEAD().setTRAN_DATE(DateUtil.getDate());//交易日期
		req.getAPP_HEAD().setTRAN_TIME(DateUtil.getTime());//交易时间
		req.getAPP_HEAD().setREQ_DATE(DateUtil.getDate());
		req.getAPP_HEAD().setREQ_SEQ(DateUtil.getDate() + seqNo);
		
		// 本地扩展头
		req.getLOCAL_HEAD().setCHNL_NO("000001");
		req.getLOCAL_HEAD().setBUSI_NO(busiNo);
		req.getLOCAL_HEAD().setLEGA_NO(UserUtils.getLawNo());
		req.getLOCAL_HEAD().setCHNL_SEQ_NO(DateUtil.getDate() + seqNo);
		//req.getLOCAL_HEAD().setENTR_NO("");
		//租户
		req.getLOCAL_HEAD().setTNT_NO(UserUtils.getUser().getRent().getEngName());
	}
	
	/*日期加分隔符*/
	public static String addDate(String Date){
		if (null != Date && !"".equals(Date)) {
			return Date.substring(0, 4)+"-"+Date.substring(4, 6)+"-"+Date.substring(6, 8);
		}else{
			return Date;
		}
		
	}

	/**

	* 字符串+1方法，该方法将其结尾的整数+1,适用于任何以整数结尾的字符串,不限格式，不限分隔符。
	* @author zxcvbnmzb
	* @param testStr 要+1的字符串
	* @return +1后的字符串
	* @exception NumberFormatException
	*/
	public static String getStr(String testStr){
		String str="";
		String[] strs = testStr.split("[^0-9]");//根据不是数字的字符拆分字符串
	    String numStr = strs[strs.length-1];//取出最后一组数字
	    if(numStr != null && numStr.length()>0){//如果最后一组没有数字(也就是不以数字结尾)，抛NumberFormatException异常
	        int n = numStr.length();//取出字符串的长度
	        int num = Integer.parseInt(numStr)+1;//将该数字加一
	        String added = String.valueOf(num);
	        n = Math.min(n, added.length());
	        //拼接字符串
	        str=testStr.subSequence(0, testStr.length()-n)+added;
	    }
	    return str;
	}
	
}
