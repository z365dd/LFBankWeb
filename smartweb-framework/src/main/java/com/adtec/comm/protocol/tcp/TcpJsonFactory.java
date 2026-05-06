/**
 * 系统名称: SmartWeb平台
 * 模块名称: 
 * 类  名  称: tcpJson.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2017年12月18日 上午10:31:55<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.comm.protocol.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.framework.common.util.CacheUtil;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.ms.msagent.entity.RetMap;
import com.adtec.ms.msagent.service.QualityService;
import com.adtec.ms.msagent.util.MapKey;
import com.adtec.ms.msagent.util.Status;
import com.adtec.sys.common.utils.SeqUtil;
import com.alibaba.fastjson.JSON;

public class TcpJsonFactory {
	private final static Logger logger = LoggerFactory.getLogger(HttpJsonFactory.class);
	private static TcpJsonFactory tcpJson;
	
	private TcpJsonFactory(){}
	
	/**
	 * 获取TcpJsonFactory实例
	 * @return
	 */
	public static TcpJsonFactory getInstance(){
		if (null == tcpJson) {
			tcpJson = new TcpJsonFactory();
		}
		return tcpJson;
	}
	/**
	 * 向服务器发送tcp报文
	 * @param host				IP
	 * @param port				端口
	 * @param svrName			服务名
	 * @param svrTenant			服务租户
	 * @param svrVersion		服务版本
	 * @param timeout			服务超时时间
	 * @param svrPartId			服务参与者
	 * @param svrPartVersion	服务参与者版本
	 * @param svrPartInst		服务参与者实例
	 * @param reqData			请求数据
	 * @param sleepTime			睡眠时间
	 * @param isFusing			服务是否熔断--true-是，false-否
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> sendTcpJson(String seqNo,String host,int port,String svrName,String svrTenant,String svrVersion,String timeout,String svrPartId,String svrPartVersion, String svrPartInst, Map<String, Object> reqData,long sleepTime,boolean isFusing){
		RetMap retMap = new RetMap();
		retMap.setSuccess(false);
		retMap.setMsg("交易失败！");
		// 获取缓存信息agent启动信息
		HashMap<String, Object> agentMap = (HashMap<String, Object>) CacheUtil.get("MSAgent");
		String token = (String) agentMap.get(MapKey.TOKEN); // 交易令牌
		String reqSvrName = "smartweb"; // 消费方服务名
		String reqVersion = MapKey.DEFAULT_VERSION; // 消费方服务版本
		if(DataUtil.isNullStr(seqNo)){
			seqNo = DateUtil.getDate()+SeqUtil.getMBCSeq();
		}
		String retConnCode = Status.FAIL;
		String retAppCode = Status.FAIL;
		String retMsg = "交易失败！";
		byte[] repBuff = null;
		//拼装消费方租户，消费方参与者，消费方参与者版本，消费方参与者实例
		reqData.put(MapKey.SEQNO, seqNo);
		reqData.put(MapKey.REQ_TENANT, ParamUtil.getConfig("ReqTenant"));
		reqData.put(MapKey.REQ_PARTID, ParamUtil.getConfig("ReqPartId"));
		reqData.put(MapKey.REQ_PART_VERSION, ParamUtil.getConfig("ReqPartVersion"));
		reqData.put(MapKey.REQ_PART_INST, agentMap.get(MapKey.PART_INST));//参与者实例缓存在Agent启动信息中
		//开始服务质量收集
		QualityService qualityService = (QualityService) SpringContextHolder.getBean("qualityService");
		Socket socket = null;
		OutputStream out = null;
		InputStream in = null;
		try {
			repBuff = "{}".getBytes("UTF-8");
			qualityService.requestStart(token, seqNo, reqSvrName, reqVersion, svrTenant, svrPartId, svrPartVersion, svrPartInst, svrName, svrVersion, Integer.parseInt(timeout), JSON.toJSONString(reqData).getBytes("UTF-8"));
			logger.info("发送的请求报文：" + reqData);
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (!isFusing) {//正常发送
				socket = new Socket(host, port);
				out = socket.getOutputStream();
				String reqJson = JSON.toJSONString(reqData);
				System.out.println("报文长度："+reqJson.length()+"_____转为字节后的长度："+reqJson.getBytes().length);
				String len = String.valueOf(reqJson.length());
				// 报文格式：8位报文体长度(前补0)+15位服务码(后补空格)+报文体
				String reqBuf = DataUtil.fix0BeforeString(len, 8) + DataUtil.fixSpaceAfterString(svrName, 15) + reqJson;
				out.write(reqBuf.getBytes());
				out.flush();
				System.out.println("socket请求____" + socket.toString());
				in = socket.getInputStream();
				int count = 0;
	            Date startTime = new Date();
				while (count == 0) {
					count = in.available(); 
	    	        Date nowTime = new Date();
	    	        if(nowTime.getTime()-startTime.getTime()>15000){
	    	        	break;
	    	        }
				}
				byte[] reqByte = new byte[count];//接收报文体字节
				if(null == reqByte || reqByte.length == 0){
					throw new BaseException(SysErr.E_NULL_POINTER, "接收报文体为空");
				}
				in.read(reqByte);
				String repData = new String(reqByte);
				if (!DataUtil.isNullStr(repData)) {
					int bodyLength = Integer.parseInt(repData.substring(0, 8));//报文体长度
					System.out.println("报文体长度：" + bodyLength);
					String repSvrName = repData.substring(8, 23).replaceAll(" ", "");//服务响应码
					String repBody = repData.substring(23, repData.length());//报文体
					System.out.println("响应服务码为：" + repSvrName);
					System.out.println("响应的报文体为：" + repBody);
					repBuff = repBody.getBytes("UTF-8");
					retMsg = "交易成功！";
					retConnCode = Status.SUCCESS;
					retAppCode = Status.SUCCESS;
					retMap.setSuccess(true);
					retMap.setMsg("交易成功！");
					retMap.setData((HashMap<String, Object>) JSON.parseObject(repBody, Map.class));
				}else{
					retMsg = "交易失败！";
					retConnCode = Status.FAIL;
					retAppCode = Status.FAIL;
					retMap.setSuccess(false);
					retMap.setMsg("交易失败！");
				}
			} else{//触发熔断
				retConnCode = Status.TIMEOUT;
				retAppCode = Status.FAIL;
				retMsg = "【网络IO异常】,交易失败！";
				retMap.setSuccess(false);
				retMap.setMsg(retMsg);
			}
		} catch (UnknownHostException e) {
			retConnCode = Status.FAIL;
			retAppCode = Status.FAIL;
			retMsg = "域名解析异常！";
			logger.error(SysErr.E_DEFAULT,"域名解析异常！");
            throw new BaseException(SysErr.E_MESSAGE,"域名解析异常！");
		} catch (IOException e) {
			retConnCode = Status.FAIL;
			retAppCode = Status.FAIL;
			retMsg = "网络IO异常！";
			logger.error(SysErr.E_DEFAULT,"网络IO异常！");
            throw new BaseException(SysErr.E_MESSAGE,"网络IO异常！");
		} catch (Exception e) {
			retConnCode = Status.FAIL;
			retAppCode = Status.FAIL;
			retMsg = "未知异常！";
			logger.error(SysErr.E_DEFAULT,"未知异常！");
            throw new BaseException(SysErr.E_MESSAGE,"未知异常！");
		}finally {
			// 消费者调用服务结束
			qualityService.requestEnd(token, seqNo, svrTenant, svrPartId, svrPartVersion, svrPartInst, svrName, svrVersion, retConnCode, retAppCode, retMsg, repBuff);
			if(null!=in){
				try {
					in.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
			if(null!=out){
				try {
					out.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
			if(null!=socket){
				try {
					socket.close();
				} catch (IOException e) {
                System.out.println("出现异常");
            }
			}
		}
		logger.info("调用服务：" + svrName + " 结束...");
		return retMap.toMap();
		
	}
}
