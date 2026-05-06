/**
 * 系统名称: SmartWeb平台
 * 模块名称: 启动监听localPort，接收json报文字符，并通过反射调用对应的方法执行，
 * 			调用的方法必须入参为Map<String,Object>,返回为Map<String,Object>
 * 类  名  称: TCPJsonServer.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2018年3月29日 下午7:21:10<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.comm.protocol.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.adtec.framework.exception.SysErr;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.GVarContainer;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.log.PatternParserConstant;
import com.adtec.sys.seq.PlatSeq;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

/**
 * @author chenyl
 *
 */
public class TCPJsonServer {
	/**
	 * 日志对象
	 */
	private final static Logger logger = LoggerFactory.getLogger(TCPJsonServer.class);
	/**
	 * 交易码
	 */
	public final static String TRAN = "Tran";
	/**
	 * 子交易码
	 */
	public final static String SUB_TRAN = "SubTran";
	/**
	 * 请求流水号
	 */
	public final static String REQUEST_SEQ_NO = "RequestSeqNo";
	/**
	 * 响应流水号
	 */
	public final static String RESPONSE_SEQ_NO = "ResponseSeqNo";
	/**
	 * 返回错误码
	 */
	public final static String RETCODE = "RetCode";
	/**
	 * 返回错误信息
	 */
	public final static String MSG = "Msg";
	
	/**
	 * 启动Server
	 */
	public static void startServer(){
		// 默认监听端口为7793
		String localPort = "7793";
		if(!DataUtil.isNullStr(ParamUtil.getConfig("localPort"))){
			localPort = ParamUtil.getConfig("localPort"); // 设置为配置的监听端口
		}
		ServerThread st = new ServerThread(Integer.parseInt(localPort));
		new Thread(st).start(); // 启动监听
	}
	
	/**
	 * 通过TCP发送json报文请求
	 * @param ip		目的方ip
	 * @param port		目的方端口
	 * @param reqSeqNo	请求流水号(为空时直接取全局流水号作为请求流水号)
	 * @param tran		交易码
	 * @param subTran	子交易码
	 * @param reqData	请求报文
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static HashMap<String, Object> sendTcpJson(String ip, int port, String reqSeqNo, String tran, String subTran, HashMap<String, Object> reqData){
		HashMap<String, Object> retMap = Maps.newHashMap();
		// 记录日志的全局流水号
		String seqNo = (String)GVarContainer.getVar(PatternParserConstant.GLOBAL_SEQ);
        if(DataUtil.isNullStr(seqNo)){
        	seqNo = PlatSeq.getGlobalSeq();
        	GVarContainer.setVar(PatternParserConstant.GLOBAL_SEQ, seqNo);
        }

		if(DataUtil.isNullStr(ip)){
			throw new BaseException("2000","ip地址不能空");
		}
		if(DataUtil.isNullStr(""+port)){
			throw new BaseException("2000","port端口不能空");
		}
		if(DataUtil.isNullStr(tran)){
			throw new BaseException("2000","tran交易码不能空");
		}
		if(DataUtil.isNullStr(subTran)){
			throw new BaseException("2000","subTran子交易码不能空");
		}		
		if(DataUtil.isNullStr(reqSeqNo)){
			reqSeqNo = seqNo;
		}
		
		// 设置请求的请求流水号、交易码和子交易码
		reqData.put(REQUEST_SEQ_NO, reqSeqNo);
		reqData.put(TRAN, tran);
		reqData.put(SUB_TRAN, subTran);
		String reqMSg = JSON.toJSONString(reqData);
		if (DataUtil.isNullStr(reqMSg)) {
			reqMSg = "{}";
		}
		
		Date st = new Date();
		logger.info("调用交易["+tran+"],子交易["+subTran+"]开始...");
		Socket socket = null;
		OutputStream os = null;
		PrintWriter pw = null;
		InputStream is = null;
		BufferedReader br = null;
		
		try {
			socket = new Socket(ip, port);
			 //2.获取输出流，向服务器端发送信息
			logger.info("发送TCP请求到["+ip+"_"+port+"],报文："+reqMSg);
            os = socket.getOutputStream();//字节输出流
            pw = new PrintWriter(os);//将输出流包装为打印流
            pw.write(reqMSg); // 发送报文
            pw.flush();
            socket.shutdownOutput();//关闭输出流
            //3.获取输入流，并读取服务器端的响应信息
            is=socket.getInputStream();
            br=new BufferedReader(new InputStreamReader(is));
			if (br == null) {
				throw new BaseException(SysErr.E_IO_ERROR, "响应报文读取失败");
			}
            StringBuffer json = new StringBuffer();
            String info=null;
            while((info=br.readLine())!=null){
            	json.append(info);
            }
            logger.info("接收到的响应报文："+json.toString());           
            String reqMsg = "{}";
            if(!DataUtil.isNullStr(json.toString())){
            	reqMsg = json.toString();
            }
            HashMap<String, Object> repMap = (HashMap<String, Object>) JSON.parseObject(reqMsg, Map.class);
            retMap.putAll(repMap);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BaseException("0600", e, ip, port);
		} catch (IOException e) {
                System.out.println("出现异常");
            } finally{
			// 4.关闭资源
			try {
				if (null != br) {
					br.close();
				}
				if(null!=is){
					is.close();
				}
				if(null!=pw){
					pw.close();
				}
				if(null!=os){
					os.close();
				}
				if(null!=socket){
					socket.close();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("关闭资源失败", e);
			} finally {
				Date ed = new Date();
				logger.info("总耗时["+(ed.getTime()-st.getTime())+"ms],调用交易["+tran+"],子交易["+subTran+"]结束...");
			}
		}
		
		return retMap;
	}
	
}

class ServerThread implements Runnable{
	/**
	 * 日志对象
	 */
	private final static Logger logger = LoggerFactory.getLogger(ServerThread.class);

	/**
	 * 监听localPort端口ServerSocket
	 */
	private ServerSocket server = null;
	/**
	 * 启动监听标识
	 */
	private boolean isRun = true;
	
	private int port;
	
	public ServerThread(int port){
		this.port = port;
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		Socket socket = null;
		try {
			if(null==server){
				server = new ServerSocket(port);
			}
			logger.info("启动TCPJsonServer监听端口："+port);
			isRun = true;
			while(isRun){
				// 接收到请求连接
				socket = server.accept();
				if(null!=socket && socket.isConnected()){
					Thread dealThread = new Thread(new TCPJsonReadThread(socket));
					dealThread.start();
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			logger.error("配置端口格式有误["+port+"]", e);
		} catch (IOException e) {
			logger.error("监听端口["+port+"]异常", e);
		}finally {
			if(null != socket){
				try {
					socket.close();
				} catch (IOException e) {
					logger.error("socket关闭失败");
				}
			}
		}
	}
}

/**
 * 	
 * @author chenyl
 *
 */
class TCPJsonReadThread implements Runnable{
	/**
	 * 日志对象
	 */
	private final static Logger logger = LoggerFactory.getLogger(TCPJsonReadThread.class);
	private Socket socket;
	
	public TCPJsonReadThread(Socket socket){
		this.socket = socket;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		// TODO Auto-generated method stub
		InputStream is = null;
		InputStreamReader isr=null;
		BufferedReader br=null;
		OutputStream os=null;
        PrintWriter pw=null;
        TCPRetMap retMap = new TCPRetMap();
        String seqNo = (String)GVarContainer.getVar(PatternParserConstant.GLOBAL_SEQ);
        if(DataUtil.isNullStr(seqNo)){
        	seqNo = PlatSeq.getGlobalSeq();
        	GVarContainer.setVar(PatternParserConstant.GLOBAL_SEQ, seqNo);
        }
        retMap.setResponseSeqNo(seqNo);
		Date st = new Date();
		logger.info("开始处理TCPJsonServer收到的请求["+socket.toString()+"]");
		if(null!=socket && socket.isConnected()){
			try {
				is = socket.getInputStream();
				isr = new InputStreamReader(is);
	            br = new BufferedReader(isr);
				if (br == null) {
					throw new BaseException(SysErr.E_IO_ERROR, "请求报文读取失败");
				}
	            StringBuffer json = new StringBuffer();
	            String info=null;
	            while((info=br.readLine())!=null){//循环读取客户端的信息
	            	json.append(info);
	            }
	            socket.shutdownInput();//关闭输入流
	            logger.info("接收到的请求报文："+json.toString());
	            //获取输出流，响应客户端的请求
	            os = socket.getOutputStream();
	            pw = new PrintWriter(os);
	            
	            String reqMsg = "{}";
	            if(!DataUtil.isNullStr(json.toString())){
	            	reqMsg = json.toString();
	            }
	            HashMap<String, Object> reqMap = (HashMap<String, Object>) JSON.parseObject(reqMsg, Map.class);
	            if(null!=reqMap && null!=reqMap.get(TCPJsonServer.TRAN) && null!=reqMap.get(TCPJsonServer.SUB_TRAN) && null!=reqMap.get(TCPJsonServer.REQUEST_SEQ_NO)){
	            	String tran = (String)reqMap.get(TCPJsonServer.TRAN);
	            	String subTran = (String)reqMap.get(TCPJsonServer.SUB_TRAN);
	            	String reqSeqNo = (String)reqMap.get(TCPJsonServer.REQUEST_SEQ_NO);
	            	retMap.setTran(tran);
	            	retMap.setSubTran(subTran);
	            	retMap.setRequestSeqNo(reqSeqNo);
	            	// 通过spring获取对应的bean进行反射调用
	            	Object obj = SpringContextHolder.getBean(tran);
	            	Method method = obj.getClass().getMethod(subTran, HashMap.class);
	            	HashMap<String, Object> data = (HashMap<String, Object>)method.invoke(obj, reqMap);
	            	retMap.setData(data);
	            }else{
	            	throw new BaseException("2000","请求报文中["+TCPJsonServer.TRAN+"、"+TCPJsonServer.SUB_TRAN+"、"+TCPJsonServer.REQUEST_SEQ_NO+"]不能空");
	            }	            
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("处理请求异常");
			} finally {
				if(pw!=null){
					// 返回json报文
					String resJson = JSON.toJSONString(retMap.toMap());
					logger.info("TCPJsonServer处理交易结束，发送响应报文："+resJson);
					pw.write(resJson);
					pw.flush();//调用flush()方法将缓冲输出
				}
				//关闭资源
	            try {
	                if(pw!=null)
						try {
							pw.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					if(os!=null)
						try {
							os.close();
						} catch (IOException e) {
                System.out.println("出现异常");
            }
					if(br!=null)
						try {
							br.close();
						} catch (IOException e) {
                System.out.println("出现异常");
            }
					if(isr!=null)
						try {
							isr.close();
						} catch (IOException e) {
                System.out.println("出现异常");
            }
					if(is!=null)
						try {
							is.close();
						} catch (IOException e) {
                System.out.println("出现异常");
            }
					if(socket!=null)
						try {
							socket.close();
						} catch (IOException e) {
                System.out.println("出现异常");
            }
				} catch (Exception e) {
	            	logger.error("关闭资源异常", e);
	            } finally {
	            	// 关闭当前线程的DBSession的数据库连接
		    		DBSessionFactory.clear();
		    		
		    		// 清理异常信息线程池
		    		BaseException.clearErrInfo();
		    		
		    		// 清理线程全局变量
		    		GVarContainer.clearVar();
		    		
		    		Date ed = new Date();
					logger.info("总耗时["+(ed.getTime()-st.getTime())+"ms],处理交易["+retMap.getTran()+"],子交易["+retMap.getSubTran()+"]结束...");
	            }
			}
		}else{
			logger.error("客户端与服务端连接异常");
		}
	}
	
}
