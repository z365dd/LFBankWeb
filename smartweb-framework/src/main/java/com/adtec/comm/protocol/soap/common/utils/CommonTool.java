package com.adtec.comm.protocol.soap.common.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Date;
import javax.imageio.ImageIO;

import com.adtec.framework.common.util.ParamUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;

import com.adtec.comm.protocol.soap.common.bean.RequestHeader;
import com.adtec.comm.protocol.soap.common.bean.ResponseHeader;
import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.common.util.UUIDGenerator;
import com.adtec.sys.common.persistence.BaseDO;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @类名 CommonTool.java
 * @描述: TODO
 * @创建人 chenyl
 * @创建时间 2016年3月30日 上午9:36:27
 * @版本
 */
public class CommonTool {
	
	/**
	 * 日期字符串格式转换
	 */
	public static String dateTrans(String date, String flag) {
		String datetimefmt1 = "yyyyMMddHHmmss";
		String datetimefmt2 = "yyyy-MM-dd HH:mm:ss";
		String datefmt1 = "yyyyMMdd";
		String datefmt2 = "yyyy-MM-dd";
		String timefmt1 = "HHmmss";
		String timefmt2 = "HH:mm:ss";
		if(date == null || "".equals(date)){
			return "";
		}
		if ("<".equals(flag)) {
			if(date.length() == 14)
				return DateUtil.Date2String(DateUtil.string2Date(date, datetimefmt1), datetimefmt2);
			else if(date.length() == 8)
				return DateUtil.Date2String(DateUtil.string2Date(date, datefmt1), datefmt2);
			else
				return DateUtil.Date2String(DateUtil.string2Date(date, timefmt1), timefmt2);
		} else {
			if(date.length() == 19)
				return DateUtil.Date2String(DateUtil.string2Date(date, datetimefmt2), datetimefmt1);
			else if(date.length() == 10)
				return DateUtil.Date2String(DateUtil.string2Date(date, datefmt2), datefmt1);
			else
				return DateUtil.Date2String(DateUtil.string2Date(date, timefmt2), timefmt1);
		}
	}

	/**
	 * 实例化一个报文头对象
	 * @param txnCd
	 * @return
	 */
	public RequestHeader getReqHead(String txnCd, BaseDO po) {
		RequestHeader requestHeader = new RequestHeader();
		requestHeader.setVerNo(ParamUtil.getString("VerNo"));//版本号
		requestHeader.setReqSysCd(ParamUtil.getString("ReqSysCd"));//请求方系统代码
		requestHeader.setReqSecCd(ParamUtil.getString("ReqSecCd"));//请求方安全节点号
		requestHeader.setTxnTyp(ParamUtil.getString("TxnTyp"));//交易类型
		requestHeader.setTxnMod(ParamUtil.getString("TxnMod"));//交易模式
		requestHeader.setTxnCd(txnCd);//交易码
		requestHeader.setTxnNme(ParamUtil.getString("TxnNme"));//公共交易名称
		String datePattern="yyyyMMddHHmmss";
		String nowDate = DateUtil.Date2String(new Date(), datePattern);
		
		requestHeader.setWhlSeqNo("MNG"+nowDate+UUIDGenerator.getUUID().substring(0, 10));//全渠道流水号
		requestHeader.setWhlTm(ParamUtil.getString("WhlTm"));//全渠道时间
		
		requestHeader.setReqDt(nowDate.substring(0, 8));//请求方交易日期
		requestHeader.setReqTm(nowDate + "000000");//请求方交易时间戳
		requestHeader.setReqSeqNo(ParamUtil.getString("ReqSeqNo"));//请求方流水号
		requestHeader.setChnlNo(ParamUtil.getString("ChnlNo"));//渠道号
		requestHeader.setBrchNo(ParamUtil.getString("BrchNo"));//机构号
		requestHeader.setBrchNme(ParamUtil.getString("BrchNme"));//公共交易机构名称
		requestHeader.setTlrNo(ParamUtil.getString("TlrNo"));//柜员号
		requestHeader.setAuthTlr(ParamUtil.getString("AuthTlr"));//授权柜员
		requestHeader.setSndFileNme(ParamUtil.getString("SndFileNme"));//发送文件名
		
		int page = po.getStart();
    	int rows = po.getLimit();
    	int offset = rows*(page-1)+1;
		requestHeader.setBgnRec(offset + "");//开始记录数（文件记录数和现有系统一致）
		requestHeader.setMaxRec(rows + "");//一次查询最大记录数
		
		requestHeader.setvTlrNo(ParamUtil.getString("VTlrNo"));//自动柜员
		requestHeader.setChkCd(ParamUtil.getString("ChkCd"));//中间件校验码
		requestHeader.setChecker(ParamUtil.getString("Checker"));//复核柜员号
		requestHeader.setAcctBrch(ParamUtil.getString("AcctBrch"));//账务所属机构编码
		requestHeader.setBizTyp(ParamUtil.getString("BizTyp"));//业务类别
		requestHeader.setChkNo(ParamUtil.getString("ChkNo"));//对帐分类编号
		requestHeader.setBootNo(ParamUtil.getString("BootNo"));//尾箱号
		requestHeader.setWinNm(ParamUtil.getString("WinNm"));//交易窗体ID
		requestHeader.setWinId(ParamUtil.getString("WinId"));//菜单交易码
		requestHeader.setFsysFlg(ParamUtil.getString("FsysFlg"));//柜面厂家标识
		requestHeader.setFileHMac(ParamUtil.getString("FileHMac"));//文件MAC值
		requestHeader.sethMac(ParamUtil.getString("HMac"));//报文MAC值
		requestHeader.setBkVerNo(ParamUtil.getString("BkVerNo"));//渠道版本
		requestHeader.setBkSubVerNo(ParamUtil.getString("BkSubVerNo"));//渠道子版本
		requestHeader.setBkNodeId(ParamUtil.getString("BkNodeId"));//渠道节点索引号
		requestHeader.setBkEntrNo(ParamUtil.getString("BkEntrNo"));//产品/业务
		requestHeader.setBkBusiNo(ParamUtil.getString("BkBusiNo"));//子产品/子业务
		return requestHeader;
	}
	
	/**
	 * 从返回报文头取最大记录数
	 * @param rsponHead
	 * @return
	 */
	public static int getCountFromRsponHead(ResponseHeader rsponHead){
		int count = 0;
		String totNum = rsponHead.getTotNum();
		String currRecNum = rsponHead.getCurrRecNum();
		if("".equals(totNum)){
			if("".equals(currRecNum)){
				currRecNum = "0";
			}
			count = Integer.parseInt(currRecNum);
		}else{
			count = Integer.parseInt(totNum);
		}
		return count;
	}
	
	/**
	 * 若类classFrom与类classTo有部分成员变量名称相同，则将相同的部分从前者的实例objFrom克隆给后者的实例objTo
	 * @param classFrom
	 * @param objFrom
	 * @param classTo
	 * @param objTo
	 */
	public static void objInfoClone(String classFrom, Object objFrom, String classTo, Object objTo){
		try {
			Class clazzFrom = Class.forName(classFrom);
			Class clazzTo = Class.forName(classTo);
			
			Method[] mthsF = clazzFrom.getDeclaredMethods();
			Method[] mthsT = clazzTo.getDeclaredMethods();
			for (Method mT : mthsT) {
				String mthName = mT.getName();
				if (mthName.startsWith("get") && !"getClass".equals(mthName)) {
					Object obj = null;
					for (Method mF : mthsF) {
						if(mthName.equals(mF.getName())){
							obj = mF.invoke(objFrom);
							if(obj == null){
								obj = "";
							}
							String value = obj.toString();
							Method setMethod = clazzTo.getMethod("s" + mT.getName().substring(1), String.class);
							setMethod.invoke(objTo, value);
							break;
						}
					}
					
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public static String sendFile(String filePath) throws Exception{
		String responseStr = "";
		String URLString = ParamUtil.getWsurl();
		PostMethod postMethod = null;
		try{
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(3000);//获取连接超时(3秒)
			client.getHttpConnectionManager().getParams().setSoTimeout(3000);//获取响应超时 (10秒)
			postMethod = new PostMethod(URLString);
		    File file = new File(filePath);
			FilePart fp = new FilePart("", file);
//			fp.setTransferEncoding("8bit");
			Part[] parts = {fp}; 
			MultipartRequestEntity reqEntity = new MultipartRequestEntity(parts,postMethod.getParams()); 
			postMethod.setRequestEntity(reqEntity);
			int result = client.executeMethod(postMethod);
			if(HttpStatus.SC_OK==result){//发送成功，状态为200
//				Header[] header = postMethod.getResponseHeaders();
//				for(int i=0;i<header.length;i++){
//					System.out.println(header[i].getName()+"-----"+header[i].getValue());
//				}
				responseStr = "SUCCESS";
			}else{
				responseStr = "FAIL：" + postMethod.getResponseBodyAsString();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return responseStr;
	}
	
//	public static Log getLog(User u,Log log) {
//		log.setId(UUIDGenerator.getUUID());
//		log.setTellerNo(u.getTellerNo());// 获取user编号
//		log.setTellerName(u.getTellerName());// 获取user姓名
//		log.setOperTime(DateUtil.Date2String(new Date(), "yyyyMMddHHmmss"));
//		log.setOperType("01");// 设置操作类型
//		return log;
//	}
	
	/**
	 * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	 * @param imageFile
	 * @return
	 */
	public static String encodeImgageToBase64(File imageFile) {
		ByteArrayOutputStream outputStream = null;
		try {
			BufferedImage bufferedImage = ImageIO.read(imageFile);
			outputStream = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "jpg", outputStream);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
                System.out.println("出现异常");
            }
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(outputStream.toByteArray());// 返回Base64编码过的字节数组字符串
	}

	/**
	 * 将Base64位编码的图片进行解码，并保存到指定目录
	 * @param base64
	 * @param path
	 * @param imgName
	 */
	public static void decodeBase64ToImage(String base64, String path,String imgName) {
		BASE64Decoder decoder = new BASE64Decoder();
		FileOutputStream write = null;
		try {
			write = new FileOutputStream(new File(path + imgName));
			byte[] decoderBytes = decoder.decodeBuffer(base64);
			write.write(decoderBytes);
		} catch (IOException e) {
                System.out.println("出现异常");
            }finally {
			if(null != write){
				try {
					write.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
		}
	}
}
