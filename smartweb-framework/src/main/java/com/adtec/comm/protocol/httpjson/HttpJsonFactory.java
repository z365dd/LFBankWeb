package com.adtec.comm.protocol.httpjson;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.curator.shaded.com.google.common.collect.Maps;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adtec.comm.dto.MBC_REQ;
import com.adtec.comm.dto.MBC_RES;
import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.dto.SOAPReqDTO;
import com.adtec.comm.dto.SOAPResDTO;
import com.adtec.comm.dto.head.MBC_REQ_APP_HEAD;
import com.adtec.comm.dto.head.MBC_REQ_LOCAL_HEAD;
import com.adtec.comm.dto.head.MBC_REQ_SYS_HEAD;
import com.adtec.comm.dto.head.MBC_RES_SYS_HEAD_RET;
import com.adtec.comm.dto.head.SysHeadRetResDTO;
import com.adtec.comm.protocol.ICommFactory;
import com.adtec.comm.protocol.httpjson.xmlbean.ResClazz;
import com.adtec.comm.protocol.httpjson.xmlbean.ResponseBody;
import com.adtec.comm.protocol.httpjson.xmlbean.Service;
import com.adtec.framework.common.util.CacheUtil;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.common.util.GVarContainer;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.framework.common.util.StringUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.impl.uiengine.tools.web.jsonwriter.JSONResult;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.framework.log.PatternParserConstant;
import com.adtec.ms.msagent.service.AddressingService;
import com.adtec.ms.msagent.service.AgentMngService;
import com.adtec.ms.msagent.service.QualityService;
import com.adtec.ms.msagent.util.MapKey;
import com.adtec.ms.msagent.util.Status;
import com.adtec.sys.common.utils.SeqUtil;
import com.adtec.sys.common.utils.SysUtil;
import com.adtec.sys.modules.sys.entity.Rent;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.service.SystemService;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.adtec.sys.seq.PlatSeq;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HttpJsonFactory implements ICommFactory {
	/**
	 * 日志对象
	 */
	protected final static Logger logger = LoggerFactory.getLogger(HttpJsonFactory.class);
//	public static Map<String, Map<String, Object>> svrInstMap = Maps.newHashMap();  // 用于保存当前寻址结果
	public static final int CONNECT_TIMEOUT = 3000;	//获取连接超时(3秒)
	public static final int RESPONSE_TIMEOUT = 30000;//获取响应超时 (30秒)
	public static final String TENANT = ParamUtil.getConfig("Tenant");	// 默认服务方所属租户
	public static final String PARTID = ParamUtil.getConfig("PartId");	// 默认服务方所属参与者
	private static HttpJsonFactory httpJson;
	private long startTime = 0L;
	private long endTime = 0L;
	//用于记录微服务寻址获取的地址
	public static String ADDR = null;
	
	private HttpJsonFactory(){}
	
	public static HttpJsonFactory getInstance(){
		if (null == httpJson) {
			httpJson = new HttpJsonFactory();
		}
		return httpJson;
	}
	/**	auth通讯协议方法
	 * 发送Http+Json协议报文到第三方
	 * @param reqUrl	发送请求路径
	 * @param reqData	发送请求报文
	 * @return	返回json报文后转成Map
	 */
	public Map<String, Object> sendAuthHttpJson(String reqUrl, Map<String, Object> reqData) throws BaseException{
		//String msregUrl = ParamUtil.getConfig("msService.url");
		//if (DataUtil.isNullStr(msregUrl)) {
			/*20190411 add by chenyl for 与msreg通讯修改为通过寻址的方式*/
		String seqNo = (String) GVarContainer.getVar(PatternParserConstant.GLOBAL_SEQ); // 获取日志的全局流水号
		if (!StringUtil.isNotBlank(seqNo)) {
			// 如果为空则产生一个新的流水号好
			seqNo = PlatSeq.getGlobalSeq();
		}
		String tenant = "head";
		String partId = "Auth";
		String svrName = "chkPartUser"; // 通过寻址msreg提供的认证服务截取对应你的msreg请求url前缀
		String msregUrl = getUrl(seqNo, tenant, partId, svrName, null);
		if (StringUtil.isNotBlank(msregUrl)) {
			msregUrl = msregUrl.substring(0, msregUrl.lastIndexOf("/msauth/") + 7);
		}
		//}
		reqUrl = msregUrl +reqUrl;
		Map<String, Object> map = null;
		PostMethod postMethod = null;
		HttpClient client = null;
		try {
			logger.info("请求地址："+reqUrl);
			String json = JSON.toJSONString(reqData);
			logger.info("发送的请求报文："+json);
            RequestEntity entity = new StringRequestEntity(json,"text/xml","utf-8");
            client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(CONNECT_TIMEOUT);//获取连接超时(3秒)
			//20181113 mod by chenyl  for 修改请求msreg的最大超时时间为五分钟
			client.getHttpConnectionManager().getParams().setSoTimeout(5*60*1000);//获取响应超时 (30秒)
			postMethod = new PostMethod(reqUrl);
			postMethod.setRequestHeader("Content-type", "application/json; charset=utf-8");
			postMethod.setRequestHeader("Accept", "application/json");
			postMethod.setRequestEntity(entity);

			startTime = System.currentTimeMillis();
			int status = client.executeMethod(postMethod);
			logger.info("status："+status);
			
			String repMsg = "";
			String repJSON = "";
			if(HttpStatus.SC_OK==status){//发送成功，状态为200
				repMsg = postMethod.getResponseBodyAsString();
				repJSON = new String(repMsg.getBytes(),"UTF-8");//编码转换
				logger.info("接收的响应报文： \r\n" + repJSON);
			}
			
			endTime = System.currentTimeMillis();
			logger.info("调用远程服务花费时间(单位：毫秒)："+(endTime - startTime));
			
			//把json报文转成map返回
			map = JSON.parseObject(repJSON, new TypeReference<Map<String, Object>>() {});
			//this.ADDR = msregUrl;
			//map.put("Addr", msregUrl);
        } catch (MalformedURLException e) {
            logger.error(SysErr.E_DEFAULT,"请求地址异常！");
            throw new BaseException(SysErr.E_MESSAGE,"请求地址异常！");
        } catch (IOException e) {
            logger.error(SysErr.E_DEFAULT,"网络IO异常！");
            throw new BaseException(SysErr.E_MESSAGE,"网络IO异常！");
        } finally {
        	//this.ADDR = msregUrl;
        	// 20180705 add by chenyl for 关闭httpClient
        	if(null!=postMethod){
        		postMethod.releaseConnection();
        	}
        	if(null!=client){
        		client.getHttpConnectionManager().closeIdleConnections(0);
        	}
        }
		return map;
	}
	/**	reg通讯协议方法
	 * 发送Http+Json协议报文到第三方
	 * @param reqUrl	发送请求路径
	 * @param reqData	发送请求报文
	 * @return	返回json报文后转成Map
	 */
	public Map<String, Object> sendRegHttpJson(String reqUrl, Map<String, Object> reqData) throws BaseException{
		String msregUrl = ParamUtil.getConfig("msService.url");
		if (DataUtil.isNullStr(msregUrl)) {
			/*20190411 add by chenyl for 与msreg通讯修改为通过寻址的方式*/
			String seqNo = (String) GVarContainer.getVar(PatternParserConstant.GLOBAL_SEQ); // 获取日志的全局流水号
			if (!StringUtil.isNotBlank(seqNo)) {
				// 如果为空则产生一个新的流水号好
				seqNo = PlatSeq.getGlobalSeq();
			}
			String tenant = "head";
			String partId = "Reg";
			String svrName = "regSvr"; // 通过寻址msreg提供的认证服务截取对应你的msreg请求url前缀
			msregUrl = getUrl(seqNo, tenant, partId, svrName, null);
			if (StringUtil.isNotBlank(msregUrl)) {
				msregUrl = msregUrl.substring(0, msregUrl.lastIndexOf("/MSReg/")+6);
			}
		}
		reqUrl = msregUrl+reqUrl;
		Map<String, Object> map = null;
		PostMethod postMethod = null;
		HttpClient client = null;
		try {
			logger.info("请求地址："+reqUrl);
			/*20200110 mod by chenyl for 新增请求数据对象null处理*/
			if(null==reqData){
				reqData = new HashMap<String, Object>();
			}
			String json = JSON.toJSONString(reqData);
			logger.info("发送的请求报文："+json);
            RequestEntity entity = new StringRequestEntity(json,"text/xml","utf-8");
            client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(CONNECT_TIMEOUT);//获取连接超时(3秒)
			//20181113 mod by chenyl  for 修改请求msreg的最大超时时间为五分钟
			client.getHttpConnectionManager().getParams().setSoTimeout(5*60*1000);//获取响应超时 (30秒)
			postMethod = new PostMethod(reqUrl);
			postMethod.setRequestHeader("Content-type", "application/json; charset=utf-8");
			postMethod.setRequestHeader("Accept", "application/json");
			postMethod.setRequestEntity(entity);

			startTime = System.currentTimeMillis();
			int status = client.executeMethod(postMethod);
			logger.info("status："+status);
			
			String repMsg = "";
			String repJSON = "";
			if(HttpStatus.SC_OK==status){//发送成功，状态为200
				repMsg = postMethod.getResponseBodyAsString();
				repJSON = new String(repMsg.getBytes(),"UTF-8");//编码转换
				logger.info("接收的响应报文： \r\n" + repJSON);
			}
			
			endTime = System.currentTimeMillis();
			logger.info("调用远程服务花费时间(单位：毫秒)："+(endTime - startTime));
			
			//把json报文转成map返回
			map = JSON.parseObject(repJSON, new TypeReference<Map<String, Object>>() {});
			//this.ADDR = msregUrl;
			//map.put("Addr", msregUrl);
        } catch (MalformedURLException e) {
            logger.error(SysErr.E_DEFAULT,"请求地址异常！");
            throw new BaseException(SysErr.E_MESSAGE,"请求地址异常！");
        } catch (IOException e) {
            logger.error(SysErr.E_DEFAULT,"网络IO异常！");
            throw new BaseException(SysErr.E_MESSAGE,"网络IO异常！");
        } finally {
        	this.ADDR = msregUrl;
        	// 20180705 add by chenyl for 关闭httpClient
        	if(null!=postMethod){
        		postMethod.releaseConnection();
        	}
        	if(null!=client){
        		client.getHttpConnectionManager().closeIdleConnections(0);
        	}
        }
		return map;
	}
	
	/**	StarringV6管理通讯协议方法
	 * 发送Http+Json协议报文到第三方
	 * @param reqUrl	发送请求路径
	 * @param reqData	发送请求报文
	 * @return	返回json报文后转成Map
	 */
	public Map<String, Object> sendHttpJson2(String reqUrl, Map<String, Object> reqData) throws BaseException{
		/*20190411 add by chenyl for 与msreg通讯修改为通过寻址的方式*/
		String seqNo = (String)GVarContainer.getVar(PatternParserConstant.GLOBAL_SEQ);	// 获取日志的全局流水号
		if(!StringUtil.isNotBlank(seqNo)){
			// 如果为空则产生一个新的流水号好
			seqNo = PlatSeq.getGlobalSeq();
		}
		if(DataUtil.isNullStr(reqUrl)){
			throw new BaseException(SysErr.E_MESSAGE, "请求服务地址不能空!");
		}
		Map<String, Object> map = null;
		PostMethod postMethod = null;
		HttpClient client = null;
		try {
			logger.info("请求地址："+reqUrl);
			if(null==reqData){
				reqData = Maps.newHashMap();
			}
			String json = JSON.toJSONString(reqData);
			logger.info("发送的请求报文："+json);
            RequestEntity entity = new StringRequestEntity(json,"text/xml","utf-8");
            client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(CONNECT_TIMEOUT);//获取连接超时(3秒)
			client.getHttpConnectionManager().getParams().setSoTimeout(RESPONSE_TIMEOUT);//获取响应超时 (30秒)
			postMethod = new PostMethod(reqUrl);
			postMethod.setRequestHeader("Content-type", "application/json; charset=utf-8");
			postMethod.setRequestHeader("Accept", "application/json");
			postMethod.setRequestEntity(entity);

			startTime = System.currentTimeMillis();
			int status = client.executeMethod(postMethod);
			logger.info("status："+status);
			
			String repMsg = "";
			String repJSON = "";
			if(HttpStatus.SC_OK==status){//发送成功，状态为200
				repMsg = postMethod.getResponseBodyAsString();
				repJSON = new String(repMsg.getBytes(),"UTF-8");//编码转换
				logger.info("接收的响应报文： \r\n" + repJSON);
			}
			
			endTime = System.currentTimeMillis();
			logger.info("调用远程服务花费时间(单位：毫秒)："+(endTime - startTime));
			
			//把json报文转成map返回
			map = JSON.parseObject(repJSON, new TypeReference<Map<String, Object>>() {});
        } catch (MalformedURLException e) {
            logger.error(SysErr.E_DEFAULT,"请求地址异常！");
            throw new BaseException(SysErr.E_MESSAGE,"请求地址异常！");
        } catch (IOException e) {
            logger.error(SysErr.E_DEFAULT,"网络IO异常！");
            throw new BaseException(SysErr.E_MESSAGE,"网络IO异常！");
        } finally {
        	// 20180705 add by chenyl for 关闭httpClient
        	if(null!=postMethod){
        		postMethod.releaseConnection();
        	}
        	if(null!=client){
        		client.getHttpConnectionManager().closeIdleConnections(0);
        	}
        }
		return map;
	}
	
	/**
	 * 发送http+json格式请求到默认地址
	 * @param serviceCode
	 * @param ds
	 * @param result
	 * @return
	 * @throws IOException
	 */
	public boolean sendHttpJson(String serviceCode, IDataset ds, IDataset result){
		boolean flag = false;
		if(null==ds){
			logger.error(SysErr.E_DEFAULT, "请求报文不能空！");
			return flag;
		}
		
		if(DataUtil.isNullStr(serviceCode)){
			logger.error(SysErr.E_DEFAULT, "请求服务码不能空！");
			return flag;
		}
		String requestUrl = ParamUtil.getHttpJsonAddress();
		return sendHttpJson(serviceCode, requestUrl, ds, result);
	}
	
	/**
	 * 根据dataset发送http+json的方式到第三方进行服务调用，请求路径在配置文件config.properties中的httpjsonAddres(默认值)
	 * @param ds	请求报文内容(其中服务码service_code是必输的,request_url非必输的存在则用自定义的)
	 * @param result	调用报文后返回报文,外部需要返回的内容进行传参
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	public boolean sendHttpJson(String serviceCode, String requestUrl, IDataset ds, IDataset result){
		boolean flag = false;
		PostMethod postMethod = null;
		HttpClient client = null;
		
		if(null==ds){
			logger.error(SysErr.E_DEFAULT, "请求报文不能空！");
			return flag;
		}
		
		if(DataUtil.isNullStr(serviceCode)){
			logger.error(SysErr.E_DEFAULT, "请求服务码不能空！");
			return flag;
		}
		
		//请求地址
		if(DataUtil.isNullStr(requestUrl)){
			requestUrl = ParamUtil.getHttpJsonAddress();
		}
		
		try {
			String url = requestUrl+serviceCode;
			logger.info("请求地址："+url);
            JSONResult jsonResult=new JSONResult();
            String reqMsg = jsonResult.parseIDatasetJson(ds, false);
            //去掉收个[]
            if(null!=reqMsg && reqMsg.length()>2){
            	reqMsg = reqMsg.substring(1, reqMsg.length()-1);
            }
			logger.info("发送的请求报文："+reqMsg);
            RequestEntity entity = new StringRequestEntity(reqMsg,"text/xml","utf-8");
            client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(3000);//获取连接超时(3秒)
			client.getHttpConnectionManager().getParams().setSoTimeout(30000);//获取响应超时 (30秒)
			postMethod = new PostMethod(url);
			postMethod.setRequestHeader("Content-type", "application/json; charset=utf-8");
			postMethod.setRequestHeader("Accept", "application/json");
			postMethod.setRequestEntity(entity);
//			postMethod.setRequestBody(reqMsg);
			startTime = System.currentTimeMillis();
			int status = client.executeMethod(postMethod);
			logger.info("status："+status);
			
			String repMsg = "";
			String repJSON = "";
			if(HttpStatus.SC_OK==status){//发送成功，状态为200
				repMsg = postMethod.getResponseBodyAsString();
				repJSON = new String(repMsg.getBytes("ISO-8859-1"),"UTF-8");//编码转换
				logger.info("接收的响应报文： \r\n" + repJSON);
			}
			
			endTime = System.currentTimeMillis();
			logger.info("调用远程服务花费时间(单位：毫秒)："+(endTime - startTime));
			
			//如果存在接收返回参数，则进行返回参数处理
            if(null!=result){
            	//将json字符串转成map
    			Map<String, String> jsonMap1 = com.alibaba.fastjson.JSONObject.parseObject(repJSON, Map.class);
            	JSONObject jsonObject = JSONObject.fromObject(jsonMap1);	
        		Map<String, Object> mapJson = JSONObject.fromObject(jsonObject);
        		//再将map转成dataset
        		result = DatasetService.getInstace().getDataset(mapJson);
        		DatasetService.getInstace().printDataset(result);
            }

            flag = true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            logger.error(SysErr.E_DEFAULT, "请求地址异常！");
        } catch (IOException e) {
            logger.error(SysErr.E_DEFAULT, "网络IO异常！");
        }  finally {
        	// 20180705 add by chenyl for 关闭httpClient
        	if(null!=postMethod){
        		postMethod.releaseConnection();
        	}
        	if(null!=client){
        		client.getHttpConnectionManager().closeIdleConnections(0);
        	}
        }
		return flag;
		
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object callServiceByUrl(String requestUrl, Object reqObj, Class<?> resBodyClass) {

		ReqDTO reqDTO = (ReqDTO)reqObj;
		
		logger.info("调用服务：" + requestUrl + " 开始...");
		logger.info("输入参数：url[" + requestUrl + "], ReqDTO[" + reqDTO + "] , resBodyClass[" + resBodyClass + "]");
		if (DataUtil.isNullStr(requestUrl)) {
			logger.error(SysErr.E_DEFAULT, "请求url不能空！");
			throw new BaseException(SysErr.E_MESSAGE, "请求url不能空！");
		}
		if (null == reqDTO) {
			logger.error(SysErr.E_DEFAULT, "请求报文不能空！");
			throw new BaseException(SysErr.E_MESSAGE, "请求报文不能空！");
		}

		ResDTO res = null;

		PostMethod postMethod = null;
		HttpClient client = null;

		String retMsg = "交易失败";
		try {
			logger.info("请求地址：" + requestUrl);
			JSONResult jsonResult = new JSONResult();
			String reqMsg = jsonResult.parseJson("", reqDTO);

			logger.info("发送的请求报文：" + reqMsg);
			RequestEntity entity = new StringRequestEntity(reqMsg, "text/xml", "utf-8");
			client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(CONNECT_TIMEOUT);// 获取连接超时(3秒)
			client.getHttpConnectionManager().getParams().setSoTimeout(RESPONSE_TIMEOUT);// 获取响应超时
																							// (30秒)
			postMethod = new PostMethod(requestUrl);
			postMethod.setRequestHeader("Content-type", "application/json; charset=utf-8");
			postMethod.setRequestHeader("Accept", "application/json");
			postMethod.setRequestEntity(entity);
			startTime = System.currentTimeMillis();
			int status = client.executeMethod(postMethod);
			logger.info("status：" + status);
			StringBuffer repMsg = new StringBuffer();
			String repJSON = "";
			if (HttpStatus.SC_OK == status) {// 发送成功，状态为200
				InputStream is = postMethod.getResponseBodyAsStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				if (br == null) {
					throw new BaseException(SysErr.E_IO_ERROR, "响应报文读取失败");
				}
				String str = "";
				while (null != (str = br.readLine())) {
					repMsg.append(str);
				}
				repJSON = new String(repMsg.toString().getBytes("UTF-8"));
				if (DataUtil.isNullStr(repJSON)) {
					repJSON = "{}";
				}
				logger.info("接收的响应报文： \r\n" + repJSON);
			}
			endTime = System.currentTimeMillis();
			logger.info("调用远程服务花费时间(单位：毫秒)：" + (endTime - startTime));

			// 将json字符串转出json对象
			Map<String, String> jsonMap1 = com.alibaba.fastjson.JSONObject.parseObject(repJSON, Map.class);
			JSONObject jsonObject = JSONObject.fromObject(jsonMap1);
			Map classResMap = new HashMap(); // 设置响应报文中需要用到转换的数据对象类
			classResMap.put("TX_RET", SysHeadRetResDTO.class); // 设置响应报文系统头中错误信息类
			classResMap.put("BODY", resBodyClass); // 设置响应报文体类
			// 附加映射类,如http-json.xml配置文件搜索到本服务有附加配置则按在根节点下增加附加映射
			/*Map classMap = getClassMap(requestUrl);
			if(classMap != null)
				classResMap.putAll(classMap);*/
			
			/*add by ruanyh 增加报文头类获取*/
			getClassMap(classResMap, ResDTO.class);
			/*报文体类获取*/
			getClassMap(classResMap, resBodyClass);
			/*add by ruanyh 将JsonObject对象节点不在classResMap中的删除*/
			removeOtherNode(jsonObject, classResMap);

			// 处理返回报文
			res = (ResDTO) JSONObject.toBean(jsonObject, ResDTO.class, classResMap);

			// 将json字符串转出json对象
			JSONObject jsonHead = jsonObject.getJSONObject("SYS_HEAD");
			if ("F".equals(jsonHead.get("TX_STAT")) || "f".equals(jsonHead.get("TX_STAT"))) {
				logger.error(SysErr.E_DEFAULT, "请求失败[" + requestUrl + "]获取数据异常！");
				List<JSONObject> msgList = (List<JSONObject>) jsonHead.get("TX_RET");
				String errCode = null;
				String msg = null;
				for (JSONObject json : msgList) {
					errCode = (String) json.get("RET_CODE");
					msg = (String) json.get("RET_MSG");
				}
				retMsg = "错误码：" + errCode + ".错误信息：" + msg; // 设置交易错误信息
				throw new BaseException(SysErr.E_MESSAGE, "错误码：" + errCode + ".错误信息：" + msg);
			}
			retMsg = "交易成功";
		} catch (MalformedURLException e) {
			retMsg = "调用[" + requestUrl + "]请求地址异常！";
			logger.error(SysErr.E_DEFAULT, "调用[" + requestUrl + "]请求地址异常！");
			throw new BaseException(SysErr.E_MESSAGE, "调用[" + requestUrl + "]请求地址异常！");
		} catch (IOException e) {
			retMsg = "调用[" + requestUrl + "]网络IO异常！";
			logger.error(SysErr.E_DEFAULT, "调用[" + requestUrl + "]网络IO异常！");
			throw new BaseException(SysErr.E_MESSAGE, "调用[" + requestUrl + "]网络IO异常！");
		} catch (BaseException e) {
			logger.error(SysErr.E_DEFAULT, "调用[" + requestUrl + "]失败，"+retMsg);
			throw new BaseException(SysErr.E_MESSAGE, "调用[" + requestUrl + "]失败，"+retMsg);
		}  catch (Exception e) {
			retMsg = "调用[" + requestUrl + "]发生未知异常！";
			logger.error(SysErr.E_DEFAULT, "调用[" + requestUrl + "]发生未知异常！");
			throw new BaseException(SysErr.E_MESSAGE, e, "调用[" + requestUrl + "]发生未知异常！");
		} finally {
        	// 20180705 add by chenyl for 关闭httpClient
        	if(null!=postMethod){
        		postMethod.releaseConnection();
        	}
        	if(null!=client){
        		client.getHttpConnectionManager().closeIdleConnections(0);
        	}
        }
		
		logger.info("调用服务：" + requestUrl + " 结束...");
		return res;
	}

	@Override
	public Object callService(String serviceCode, Object reqObj, Class<?> resBodyClass) {
		return callService("", "", serviceCode, reqObj, resBodyClass, null);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object callService(String tenant, String partId, String serviceCode, Object reqObj, Class<?> resBodyClass,
			HashMap<String, Object> dyncParam) {
		
		ReqDTO reqDTO = (ReqDTO)reqObj;
		
		logger.info("调用服务：" + serviceCode + " 开始...");
		logger.info("输入参数：tenant[" + tenant + "] , partId[" + partId + "] , serviceCode[" + serviceCode + "] , ReqDTO["
				+ reqDTO + "] , resBodyClass[" + resBodyClass + "] , dyncParam[" + dyncParam + "]");
		if (DataUtil.isNullStr(serviceCode)) {
			logger.error(SysErr.E_DEFAULT, "请求服务码不能空！");
			throw new BaseException(SysErr.E_MESSAGE, "请求服务码不能空！");
		}
		if (null == reqDTO) {
			logger.error(SysErr.E_DEFAULT, "请求报文不能空！");
			throw new BaseException(SysErr.E_MESSAGE, "请求报文不能空！");
		}

		ResDTO res = null;

		// 获取请求流水号
		String seqNo = reqDTO.getSYS_HEAD().getREQ_SEQ_NO();
		// add by chenyl for poc根据不同服务码开头设置不同的参与者
		if (null != serviceCode && serviceCode.indexOf("Ctrl") == 0) {
			// 控制组件
			partId = ParamUtil.getConfig("CTRL_PARTID");
		} else if (null != serviceCode && serviceCode.indexOf("Gpm") == 0) {
			// 通用缴费模型
			partId = ParamUtil.getConfig("GPM_PARTID");
		} else if (null != serviceCode && serviceCode.indexOf("Mbm") == 0) {
			// 公积金模型
			partId = ParamUtil.getConfig("DPS_PARTID");
		}else if(null != serviceCode && serviceCode.indexOf("MBat") == 0){
			// 批量模型 add by weizhj
			partId = ParamUtil.getConfig("MBAT_PARTID");
		}

		// 请求地址,后续需要通过服务寻址的方式获取
		String requestUrl = getUrl(seqNo, tenant, partId, serviceCode, dyncParam);

		PostMethod postMethod = null;
		HttpClient client = null;
		
		// 获取寻址返回的数据
		String token = "";	// 交易令牌
		String svrTenant = ""; // 服务方租户
		String svrPartId = ""; // 服务方参与者ID
		String svrPartVersion = ""; // 服务方参与者版本
		String svrPartInst = ""; // 服务方参与者实例
		String svrName = ""; // 服务方服务名
		String version = ""; // 服务方服务版本
		String timeout = null; // 服务方服务超时时间，单位：毫秒
		String retConnCode = Status.FAIL;
		String retAppCode = Status.FAIL;
		String retMsg = "交易失败";
		String retMsg1 = "";
		byte[] resBuff = null;

		try {
			logger.info("请求地址：" + requestUrl);
			JSONResult jsonResult = new JSONResult();
			String reqMsg = jsonResult.parseJson("", reqDTO);

			// 消费方服务请求开始
			resBuff = "{}".getBytes("UTF-8");
			if(ParamUtil.CONF_Y.equals(ParamUtil.getMsAgentOpen())){
				// 获取缓存信息agent启动信息
				HashMap<String, Object> agentMap = (HashMap<String, Object>) CacheUtil.get("MSAgent");
				token = (String) agentMap.get(MapKey.TOKEN); // 交易令牌
				String reqSvrName = reqDTO.getBODY().getClass().getSimpleName(); // 消费方服务名
				String reqVersion = MapKey.DEFAULT_VERSION; // 消费方服务版本

				// 获取寻址返回的数据
				Map<String, Object> retMap = (Map<String, Object>) GVarContainer.getVar(seqNo);
				svrTenant = (String) retMap.get(MapKey.TENANT); // 服务方租户
				svrPartId = (String) retMap.get(MapKey.PARTID); // 服务方参与者ID
				svrPartVersion = (String) retMap.get(MapKey.PART_VERSION); // 服务方参与者版本
				svrPartInst = (String) retMap.get(MapKey.PART_INST); // 服务方参与者实例
				svrName = (String) retMap.get(MapKey.SVR_NAME); // 服务方服务名
				version = (String) retMap.get(MapKey.VERSION); // 服务方服务版本
				timeout = (String) retMap.get(MapKey.TIME_OUT); // 服务方服务超时时间，单位：毫秒
				try{
					QualityService qualityService = (QualityService) SpringContextHolder.getBean("qualityService");
					qualityService.requestStart(token, seqNo, reqSvrName, reqVersion, svrTenant, svrPartId, svrPartVersion, svrPartInst, svrName, version, Integer.parseInt(timeout), reqMsg.getBytes("UTF-8"));
				}catch (Exception e) {
					logger.info("消费方服务请求开始出现异常，流程继续往下执行...");
				}
			}
			// 20180627 add by chenyl for 使用服务方超时时间
			int responseTimeout = RESPONSE_TIMEOUT; // 默认超时时间
			if (!DataUtil.isNullStr(timeout) && Integer.parseInt(timeout) > 0) {
				// 服务方设置了超时时时间时使用服务方的超时
				responseTimeout = Integer.parseInt(timeout);
			}

			logger.info("发送的请求报文：" + reqMsg);
			RequestEntity entity = new StringRequestEntity(reqMsg, "text/xml", "utf-8");
			client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(CONNECT_TIMEOUT);// 获取连接超时(3秒)
			client.getHttpConnectionManager().getParams().setSoTimeout(responseTimeout);// 获取响应超时
																							// (30秒)
			postMethod = new PostMethod(requestUrl);
			postMethod.setRequestHeader("Content-type", "application/json; charset=utf-8");
			postMethod.setRequestHeader("Accept", "application/json");
			postMethod.setRequestEntity(entity);
			startTime = System.currentTimeMillis();
			int status = client.executeMethod(postMethod);
			logger.info("status：" + status);
			StringBuffer repMsg = new StringBuffer();
			String repJSON = "";
			if (HttpStatus.SC_OK == status) {// 发送成功，状态为200
				InputStream is = postMethod.getResponseBodyAsStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				if (br == null) {
					throw new BaseException(SysErr.E_IO_ERROR, "响应报文读取失败");
				}
				String str = "";
				while (null != (str = br.readLine())) {
					repMsg.append(str);
				}
				repJSON = new String(repMsg.toString().getBytes("UTF-8"));
				if (DataUtil.isNullStr(repJSON)) {
					repJSON = "{}";
				}
				resBuff = repJSON.getBytes("UTF-8"); // 获取质量收集需要的响应报文
				retConnCode = Status.SUCCESS; // 设置通讯状态为成功
				logger.info("接收的响应报文： \r\n" + repJSON);
			}
			endTime = System.currentTimeMillis();
			logger.info("调用远程服务花费时间(单位：毫秒)：" + (endTime - startTime));

			// 将json字符串转出json对象
			Map<String, String> jsonMap1 = com.alibaba.fastjson.JSONObject.parseObject(repJSON, Map.class);
			JSONObject jsonObject = JSONObject.fromObject(jsonMap1);
			Map classResMap = new HashMap(); // 设置响应报文中需要用到转换的数据对象类
			classResMap.put("TX_RET", SysHeadRetResDTO.class); // 设置响应报文系统头中错误信息类
			classResMap.put("BODY", resBodyClass); // 设置响应报文体类
			// 附加映射类,如http-json.xml配置文件搜索到本服务有附加配置则按在根节点下增加附加映射
			/*Map classMap = getClassMap(serviceCode);
			if(classMap != null)
				classResMap.putAll(classMap);*/
			
			/*add by ruanyh 增加报文头类获取*/
			getClassMap(classResMap, ResDTO.class);
			/*报文体类获取*/
			getClassMap(classResMap, resBodyClass);
			/*add by ruanyh 将JsonObject对象节点不在classResMap中的删除*/
			removeOtherNode(jsonObject, classResMap);

			// 处理返回报文
			res = (ResDTO) JSONObject.toBean(jsonObject, ResDTO.class, classResMap);

			// 将json字符串转出json对象
			JSONObject jsonHead = jsonObject.getJSONObject("SYS_HEAD");
			
			// 添加成功S的判断-add by weizhj
			if("S".equals(jsonHead.get("TX_STAT")) || "s".equals(jsonHead.get("TX_STAT"))){
				
			}else if("S".equals(jsonHead.get("TRAN_STAT")) || "s".equals(jsonHead.get("TRAN_STAT"))){
				
			}else{
				List<JSONObject> msgList = new ArrayList<JSONObject>();
				// 请求失败
				if ("F".equals(jsonHead.get("TX_STAT")) || "f".equals(jsonHead.get("TX_STAT"))) {
					logger.error(SysErr.E_DEFAULT, "请求失败[" + serviceCode + "]获取数据异常！");
					msgList = (List<JSONObject>) jsonHead.get("TX_RET");
				}else if ("F".equals(jsonHead.get("TRAN_STAT")) || "f".equals(jsonHead.get("TRAN_STAT"))) {
					logger.error(SysErr.E_DEFAULT, "请求失败[" + serviceCode + "]获取数据异常！");
					msgList = (List<JSONObject>) jsonHead.get("TRAN_RET");
				}else {
					// 请求超时
					if ("T".equals(jsonHead.get("TX_STAT")) || "t".equals(jsonHead.get("TX_STAT"))) {
						logger.error(SysErr.E_DEFAULT, "请求[" + serviceCode + "]超时！");
						msgList = (List<JSONObject>) jsonHead.get("TX_RET");
					}else if ("T".equals(jsonHead.get("TRAN_STAT")) || "t".equals(jsonHead.get("TRAN_STAT"))) {
						logger.error(SysErr.E_DEFAULT, "请求[" + serviceCode + "]超时！");
						msgList = (List<JSONObject>) jsonHead.get("TRAN_RET");
					}
				}
				if(null != msgList && !msgList.isEmpty()){
					String errCode = null;
					String msg = null;
					for (JSONObject json : msgList) {
						errCode = (String) json.get("RET_CODE");
						msg = (String) json.get("RET_MSG");
					}
					retMsg = "错误码：" + errCode + ".错误信息：" + msg; // 设置交易错误信息
					retMsg1 = msg;
					throw new BaseException(SysErr.E_MESSAGE, "错误码：" + errCode + ".错误信息：" + msg);
				}else{
					throw new BaseException(SysErr.E_MESSAGE, "请求[" + serviceCode + "]失败！");
				}
			} 
			retAppCode = Status.SUCCESS; // 设置应用状态成功
			retMsg = "交易成功";
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			// 设置失败
			retConnCode = Status.FAIL;
			retAppCode = Status.FAIL;
			retMsg = "调用[" + serviceCode + "]请求地址异常！";
			logger.error(SysErr.E_DEFAULT, "调用[" + serviceCode + "]请求地址异常！");
			throw new BaseException(SysErr.E_MESSAGE, "调用[" + serviceCode + "]请求地址异常！");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// 设置失败
			retConnCode = Status.FAIL;
			retAppCode = Status.FAIL;
			retMsg = "调用[" + serviceCode + "]网络IO异常！";
			logger.error(SysErr.E_DEFAULT, "调用[" + serviceCode + "]网络IO异常！");
			throw new BaseException(SysErr.E_MESSAGE, "调用[" + serviceCode + "]网络IO异常！");
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			// 设置失败
			retConnCode = Status.FAIL;
			retAppCode = Status.FAIL;
			logger.error(SysErr.E_DEFAULT, "调用[" + serviceCode + "]失败，"+retMsg);
//			throw new BaseException(SysErr.E_MESSAGE, "调用[" + serviceCode + "]失败，"+retMsg);
			throw new BaseException(SysErr.E_MESSAGE, retMsg1);
		}  catch (Exception e) {
			// 设置失败
			retConnCode = Status.FAIL;
			retAppCode = Status.FAIL;
			retMsg = "调用[" + serviceCode + "]发生未知异常！";
			logger.error(SysErr.E_DEFAULT, "调用[" + serviceCode + "]发生未知异常！");
			throw new BaseException(SysErr.E_MESSAGE, e, "调用[" + serviceCode + "]发生未知异常！");
		} finally {
			if(ParamUtil.CONF_Y.equals(ParamUtil.getMsAgentOpen())){
				// 移除寻址结果数据
				GVarContainer.removeVar(seqNo);
				// 消费者调用服务结束
				try{
					QualityService qualityService = (QualityService) SpringContextHolder.getBean("qualityService");
					qualityService.requestEnd(token, seqNo, svrTenant, svrPartId, svrPartVersion, svrPartInst, svrName, version, retConnCode, retAppCode, retMsg, resBuff);
				}catch (Exception e) {
					logger.info("消费者调用服务结束出现异常，流程继续往下执行...");
				}
			}
			
			// 20180705 add by chenyl for 关闭httpClient
        	if(null!=postMethod){
        		postMethod.releaseConnection();
        	}
        	if(null!=client){
        		client.getHttpConnectionManager().closeIdleConnections(0);
        	}
			
		}
		logger.info("调用服务：" + serviceCode + " 结束...");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject callService(String serviceCode, Object reqObj, String tenant, String partId, HashMap<String, Object> dyncParam) {
		logger.info("调用服务：" + serviceCode + " 开始...");
		if (DataUtil.isNullStr(serviceCode)) {
			logger.error(SysErr.E_DEFAULT, "请求服务码不能空！");
			throw new BaseException(SysErr.E_MESSAGE, "请求服务码不能空！");
		}
		if (null == reqObj) {
			logger.error(SysErr.E_DEFAULT, "请求报文不能空！");
			throw new BaseException(SysErr.E_MESSAGE, "请求报文不能空！");
		}
		
		ReqDTO reqDTO = (ReqDTO)reqObj;
		String seqNo = reqDTO.getSYS_HEAD().getREQ_SEQ_NO();
		// add by chenyl for poc根据不同服务码开头设置不同的参与者
		if (null != serviceCode && serviceCode.indexOf("Ctrl") == 0) {
			// 控制组件
			partId = ParamUtil.getConfig("CTRL_PARTID");
		} else if (null != serviceCode && serviceCode.indexOf("Gpm") == 0) {
			// 通用缴费模型
			partId = ParamUtil.getConfig("GPM_PARTID");
		} else if (null != serviceCode && serviceCode.indexOf("Mbm") == 0) {
			// 公积金模型
			partId = ParamUtil.getConfig("DPS_PARTID");
		}
		
		String requestUrl = getUrl(seqNo, tenant, partId, serviceCode, dyncParam);

		PostMethod postMethod = null;
		HttpClient client = null;
		JSONObject jsonObject;

		// 获取寻址返回的数据
		String token = ""; // 交易令牌
		String svrTenant = ""; // 服务方租户
		String svrPartId = ""; // 服务方参与者ID
		String svrPartVersion = ""; // 服务方参与者版本
		String svrPartInst = ""; // 服务方参与者实例
		String svrName = ""; // 服务方服务名
		String version = ""; // 服务方服务版本
		String timeout = null; // 服务方服务超时时间，单位：毫秒
		String retConnCode = Status.FAIL;
		String retAppCode = Status.FAIL;
		String retMsg = "交易失败";
		byte[] resBuff = null;

		try {
			logger.info("请求地址：" + requestUrl);
			JSONResult jsonResult = new JSONResult();
			String reqMsg = jsonResult.parseJson("", reqDTO);

			// 消费方服务请求开始
			resBuff = "{}".getBytes("UTF-8");
			if(ParamUtil.CONF_Y.equals(ParamUtil.getMsAgentOpen())){
				// 获取缓存信息agent启动信息
				HashMap<String, Object> agentMap = (HashMap<String, Object>) CacheUtil.get("MSAgent");
				token = (String) agentMap.get(MapKey.TOKEN); // 交易令牌
				String reqSvrName = reqDTO.getBODY().getClass().getSimpleName(); // 消费方服务名
				String reqVersion = MapKey.DEFAULT_VERSION; // 消费方服务版本

				// 获取寻址返回的数据
				Map<String, Object> retMap = (Map<String, Object>) GVarContainer.getVar(seqNo);
				svrTenant = (String) retMap.get(MapKey.TENANT); // 服务方租户
				svrPartId = (String) retMap.get(MapKey.PARTID); // 服务方参与者ID
				svrPartVersion = (String) retMap.get(MapKey.PART_VERSION); // 服务方参与者版本
				svrPartInst = (String) retMap.get(MapKey.PART_INST); // 服务方参与者实例
				svrName = (String) retMap.get(MapKey.SVR_NAME); // 服务方服务名
				version = (String) retMap.get(MapKey.VERSION); // 服务方服务版本
				timeout = (String) retMap.get(MapKey.TIME_OUT); // 服务方服务超时时间，单位：毫秒
				try{
					QualityService qualityService = (QualityService) SpringContextHolder.getBean("qualityService");
					qualityService.requestStart(token, seqNo, reqSvrName, reqVersion, svrTenant, svrPartId, svrPartVersion, svrPartInst, svrName, version, Integer.parseInt(timeout), reqMsg.getBytes("UTF-8"));
				}catch (Exception e) {
					logger.info("消费方服务请求开始出现异常，流程继续往下执行...");
				}
			}
			// 20180627 add by chenyl for 使用服务方超时时间
			int responseTimeout = RESPONSE_TIMEOUT; // 默认超时时间
			if (!DataUtil.isNullStr(timeout) && Integer.parseInt(timeout) > 0) {
				// 服务方设置了超时时时间时使用服务方的超时
				responseTimeout = Integer.parseInt(timeout);
			}
			
			logger.info("发送的请求报文：" + reqMsg);
			RequestEntity entity = new StringRequestEntity(reqMsg, "text/xml", "utf-8");
			client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(CONNECT_TIMEOUT);// 获取连接超时(3秒)
			client.getHttpConnectionManager().getParams().setSoTimeout(responseTimeout);// 获取响应超时
																				// (30秒)
			postMethod = new PostMethod(requestUrl);
			postMethod.setRequestHeader("Content-type", "application/json; charset=utf-8");
			postMethod.setRequestHeader("Accept", "application/json");
			postMethod.setRequestEntity(entity);
			long startTime = System.currentTimeMillis();
			int status = client.executeMethod(postMethod);
			logger.info("status：" + status);
//			System.out.println("=============stats=[" + status + "]");
			StringBuffer repMsg = new StringBuffer();
			String repJSON = "";
			if (HttpStatus.SC_OK == status) {// 发送成功，状态为200
				InputStream is = postMethod.getResponseBodyAsStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				if (br == null) {
					throw new BaseException(SysErr.E_IO_ERROR, "响应报文读取失败");
				}
				String str = "";
				while (null != (str = br.readLine())) {
					repMsg.append(str);
				}
				repJSON = new String(repMsg.toString().getBytes("UTF-8"));
				if (DataUtil.isNullStr(repJSON)) {
					repJSON = "{}";
				}

				// 20171110 add by chenyl for 服务质量收集
				resBuff = repJSON.getBytes("UTF-8"); // 获取质量收集需要的响应报文
				retConnCode = Status.SUCCESS; // 设置通讯状态为成功

				logger.info("接收的响应报文： \r\n" + repJSON);
			}
			long endTime = System.currentTimeMillis();
			logger.info("调用远程服务花费时间(单位：毫秒)：" + (endTime - startTime));

			// 将json字符串转出json对象
			Map<String, String> jsonMap1 = com.alibaba.fastjson.JSONObject.parseObject(repJSON, Map.class);
			jsonObject = JSONObject.fromObject(jsonMap1);

			// 20171110 add by chenyl for 服务质量收集
			JSONObject jsonHead = jsonObject.getJSONObject("SYS_HEAD");
			if ("F".equals(jsonHead.get("TX_STAT")) || "f".equals(jsonHead.get("TX_STAT")) || "T".equals(jsonHead.get("TX_STAT")) || "t".equals(jsonHead.get("TX_STAT"))) {
				logger.error(SysErr.E_DEFAULT, "请求失败[" + serviceCode + "]获取数据异常！");
				List<JSONObject> msgList = (List<JSONObject>) jsonHead.get("TX_RET");
				String errCode = null;
				String msg = null;
				for (JSONObject json : msgList) {
					errCode = (String) json.get("RET_CODE");
					msg = (String) json.get("RET_MSG");
				}
				retMsg = "错误码：" + errCode + ".错误信息：" + msg; // 设置交易错误信息
			} else if ("F".equals(jsonHead.get("TRAN_STAT")) || "f".equals(jsonHead.get("TRAN_STAT")) || "T".equals(jsonHead.get("TRAN_STAT")) || "t".equals(jsonHead.get("TRAN_STAT"))) {
				logger.error(SysErr.E_DEFAULT, "请求失败[" + serviceCode + "]获取数据异常！");
				List<JSONObject> msgList = (List<JSONObject>) jsonHead.get("TRAN_RET");
				String errCode = null;
				String msg = null;
				for (JSONObject json : msgList) {
					errCode = (String) json.get("RET_CODE");
					msg = (String) json.get("RET_MSG");
				}
				retMsg = "错误码：" + errCode + ".错误信息：" + msg; // 设置交易错误信息
				throw new BaseException(SysErr.E_MESSAGE, "错误码：" + errCode + ".错误信息：" + msg);
			} else {
				retAppCode = Status.SUCCESS; // 设置应用状态成功
				retMsg = "交易成功";
			}

		} catch (BaseException e) {
			// TODO Auto-generated catch block
			// 设置失败
			retConnCode = Status.FAIL;
			retAppCode = Status.FAIL;
			retMsg = e.getErrorDesc();

			logger.error(SysErr.E_DEFAULT, retMsg);
			throw e;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			// 设置失败
			retConnCode = Status.FAIL;
			retAppCode = Status.FAIL;
			retMsg = "调用[" + serviceCode + "]请求地址异常！";

			logger.error(SysErr.E_DEFAULT, "调用[" + serviceCode + "]请求地址异常！");
			throw new BaseException(SysErr.E_MESSAGE, "调用[" + serviceCode + "]请求地址异常！");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// 设置失败
			retConnCode = Status.FAIL;
			retAppCode = Status.FAIL;
			retMsg = "调用[" + serviceCode + "]网络IO异常！";

			logger.error(SysErr.E_DEFAULT, "调用[" + serviceCode + "]网络IO异常！");
			throw new BaseException(SysErr.E_MESSAGE, "调用[" + serviceCode + "]网络IO异常！");
		} catch (Exception e) {
			// 设置失败
			retConnCode = Status.FAIL;
			retAppCode = Status.FAIL;
			retMsg = "调用[" + serviceCode + "]发生未知异常！";

			logger.error(SysErr.E_DEFAULT, "调用[" + serviceCode + "]发生未知异常！");
			throw new BaseException(SysErr.E_MESSAGE, e, "调用[" + serviceCode + "]发生未知异常！");
		} finally {
			if(ParamUtil.CONF_Y.equals(ParamUtil.getMsAgentOpen())){
				// 移除寻址结果数据
				GVarContainer.removeVar(seqNo);
				// 消费者调用服务结束
				try{
					QualityService qualityService = (QualityService) SpringContextHolder.getBean("qualityService");
					qualityService.requestEnd(token, seqNo, svrTenant, svrPartId, svrPartVersion, svrPartInst, svrName, version, retConnCode, retAppCode, retMsg, resBuff);
				}catch (Exception e) {
					logger.info("消费者调用服务结束出现异常，流程继续往下执行...");
				}
			}
			// 20180705 add by chenyl for 关闭httpClient
        	if(null!=postMethod){
        		postMethod.releaseConnection();
        	}
        	if(null!=client){
        		client.getHttpConnectionManager().closeIdleConnections(0);
        	}
		}
		logger.info("调用服务：" + serviceCode + " 结束...");
		return jsonObject;

	}
	
	/**
	 * 返回错误不抛异常直接返回整个报文
	 * */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object callServiceNoException(String tenant, String partId, String serviceCode, Object reqObj, Class<?> resBodyClass,
			HashMap<String, Object> dyncParam) {
		
		ReqDTO reqDTO = (ReqDTO)reqObj;
		
		logger.info("调用服务：" + serviceCode + " 开始...");
		logger.info("输入参数：tenant[" + tenant + "] , partId[" + partId + "] , serviceCode[" + serviceCode + "] , ReqDTO["
				+ reqDTO + "] , resBodyClass[" + resBodyClass + "] , dyncParam[" + dyncParam + "]");
		if (DataUtil.isNullStr(serviceCode)) {
			logger.error(SysErr.E_DEFAULT, "请求服务码不能空！");
			throw new BaseException(SysErr.E_MESSAGE, "请求服务码不能空！");
		}
		if (null == reqDTO) {
			logger.error(SysErr.E_DEFAULT, "请求报文不能空！");
			throw new BaseException(SysErr.E_MESSAGE, "请求报文不能空！");
		}

		ResDTO res = null;

		// 获取请求流水号
		String seqNo = reqDTO.getSYS_HEAD().getREQ_SEQ_NO();
		// add by chenyl for poc根据不同服务码开头设置不同的参与者
		if (null != serviceCode && serviceCode.indexOf("Ctrl") == 0) {
			// 控制组件
			partId = ParamUtil.getConfig("CTRL_PARTID");
		} else if (null != serviceCode && serviceCode.indexOf("Gpm") == 0) {
			// 通用缴费模型
			partId = ParamUtil.getConfig("GPM_PARTID");
		} else if (null != serviceCode && serviceCode.indexOf("Dps") == 0) {
			// 公积金模型
			partId = ParamUtil.getConfig("DPS_PARTID");
		}

		// 请求地址,后续需要通过服务寻址的方式获取
		String requestUrl = getUrl(seqNo, tenant, partId, serviceCode, dyncParam);
		
		PostMethod postMethod = null;
		HttpClient client = null;
		// 获取寻址返回的数据
		String token = ""; // 交易令牌
		String svrTenant = ""; // 服务方租户
		String svrPartId = ""; // 服务方参与者ID
		String svrPartVersion = ""; // 服务方参与者版本
		String svrPartInst = ""; // 服务方参与者实例
		String svrName = ""; // 服务方服务名
		String version = ""; // 服务方服务版本
		String timeout = null; // 服务方服务超时时间，单位：毫秒
		String retConnCode = Status.FAIL;
		String retAppCode = Status.FAIL;
		String retMsg = "交易失败";
		byte[] resBuff = null;

		try {
			logger.info("请求地址：" + requestUrl);
			JSONResult jsonResult = new JSONResult();
			String reqMsg = jsonResult.parseJson("", reqDTO);

			// 消费方服务请求开始
			resBuff = "{}".getBytes("UTF-8");
			if (ParamUtil.CONF_Y.equals(ParamUtil.getMsAgentOpen())) {
				// 获取缓存信息agent启动信息
				HashMap<String, Object> agentMap = (HashMap<String, Object>) CacheUtil.get("MSAgent");
				token = (String) agentMap.get(MapKey.TOKEN); // 交易令牌
				String reqSvrName = reqDTO.getBODY().getClass().getSimpleName(); // 消费方服务名
				String reqVersion = MapKey.DEFAULT_VERSION; // 消费方服务版本

				// 获取寻址返回的数据
				Map<String, Object> retMap = (Map<String, Object>) GVarContainer.getVar(seqNo);
				svrTenant = (String) retMap.get(MapKey.TENANT); // 服务方租户
				svrPartId = (String) retMap.get(MapKey.PARTID); // 服务方参与者ID
				svrPartVersion = (String) retMap.get(MapKey.PART_VERSION); // 服务方参与者版本
				svrPartInst = (String) retMap.get(MapKey.PART_INST); // 服务方参与者实例
				svrName = (String) retMap.get(MapKey.SVR_NAME); // 服务方服务名
				version = (String) retMap.get(MapKey.VERSION); // 服务方服务版本
				timeout = (String) retMap.get(MapKey.TIME_OUT); // 服务方服务超时时间，单位：毫秒
				try {
					QualityService qualityService = (QualityService) SpringContextHolder.getBean("qualityService");
					qualityService.requestStart(token, seqNo, reqSvrName, reqVersion, svrTenant, svrPartId,
							svrPartVersion, svrPartInst, svrName, version, Integer.parseInt(timeout),
							reqMsg.getBytes("UTF-8"));
				} catch (Exception e) {
					logger.info("消费方服务请求开始出现异常，流程继续往下执行...");
				}
			}
			// 20180627 add by chenyl for 使用服务方超时时间
			int responseTimeout = RESPONSE_TIMEOUT; // 默认超时时间
			if (!DataUtil.isNullStr(timeout) && Integer.parseInt(timeout) > 0) {
				// 服务方设置了超时时时间时使用服务方的超时
				responseTimeout = Integer.parseInt(timeout);
			}

			logger.info("发送的请求报文：" + reqMsg);
			RequestEntity entity = new StringRequestEntity(reqMsg, "text/xml", "utf-8");
			client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(CONNECT_TIMEOUT);// 获取连接超时(3秒)
			client.getHttpConnectionManager().getParams().setSoTimeout(responseTimeout);// 获取响应超时
																							// (30秒)
			postMethod = new PostMethod(requestUrl);
			postMethod.setRequestHeader("Content-type", "application/json; charset=utf-8");
			postMethod.setRequestHeader("Accept", "application/json");
			postMethod.setRequestEntity(entity);
			startTime = System.currentTimeMillis();
			int status = client.executeMethod(postMethod);
			logger.info("status：" + status);
			StringBuffer repMsg = new StringBuffer();
			String repJSON = "";
			if (HttpStatus.SC_OK == status) {// 发送成功，状态为200
				InputStream is = postMethod.getResponseBodyAsStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				if (br == null) {
					throw new BaseException(SysErr.E_IO_ERROR, "响应报文读取失败");
				}
				String str = "";
				while (null != (str = br.readLine())) {
					repMsg.append(str);
				}
				repJSON = new String(repMsg.toString().getBytes("UTF-8"));
				if (DataUtil.isNullStr(repJSON)) {
					repJSON = "{}";
				}
				resBuff = repJSON.getBytes("UTF-8"); // 获取质量收集需要的响应报文
				retConnCode = Status.SUCCESS; // 设置通讯状态为成功
				logger.info("接收的响应报文： \r\n" + repJSON);
			}
			endTime = System.currentTimeMillis();
			logger.info("调用远程服务花费时间(单位：毫秒)：" + (endTime - startTime));

			// 将json字符串转出json对象
			Map<String, String> jsonMap1 = com.alibaba.fastjson.JSONObject.parseObject(repJSON, Map.class);
			JSONObject jsonObject = JSONObject.fromObject(jsonMap1);
			Map classResMap = new HashMap(); // 设置响应报文中需要用到转换的数据对象类
			classResMap.put("TX_RET", SysHeadRetResDTO.class); // 设置响应报文系统头中错误信息类
			classResMap.put("BODY", resBodyClass); // 设置响应报文体类
			// 附加映射类,如http-json.xml配置文件搜索到本服务有附加配置则按在根节点下增加附加映射
			/*Map classMap = getClassMap(serviceCode);
			if(classMap != null)
				classResMap.putAll(classMap);*/
			
			/*add by ruanyh 增加报文头类获取*/
			getClassMap(classResMap, ResDTO.class);
			/*报文体类获取*/
			getClassMap(classResMap, resBodyClass);
			/*add by ruanyh 将JsonObject对象节点不在classResMap中的删除*/
			removeOtherNode(jsonObject, classResMap);

			// 处理返回报文
			res = (ResDTO) JSONObject.toBean(jsonObject, ResDTO.class, classResMap);

			// 将json字符串转出json对象
			/*JSONObject jsonHead = jsonObject.getJSONObject("SYS_HEAD");*/
			/*if ("F".equals(jsonHead.get("TX_STAT")) || "f".equals(jsonHead.get("TX_STAT"))) {
				logger.error(SysErr.E_DEFAULT, "请求失败[" + serviceCode + "]获取数据异常！");
				List<JSONObject> msgList = (List<JSONObject>) jsonHead.get("TX_RET");
				String errCode = null;
				String msg = null;
				for (JSONObject json : msgList) {
					errCode = (String) json.get("RET_CODE");
					msg = (String) json.get("RET_MSG");
				}
				retMsg = "错误码：" + errCode + ".错误信息：" + msg; // 设置交易错误信息
				throw new BaseException(SysErr.E_MESSAGE, "错误码：" + errCode + ".错误信息：" + msg);
			}*/
			retAppCode = Status.SUCCESS; // 设置应用状态成功
			retMsg = "交易成功";
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			// 设置失败
			retConnCode = Status.FAIL;
			retAppCode = Status.FAIL;
			retMsg = "调用[" + serviceCode + "]请求地址异常！";
			logger.error(SysErr.E_DEFAULT, "调用[" + serviceCode + "]请求地址异常！");
			throw new BaseException(SysErr.E_MESSAGE, "调用[" + serviceCode + "]请求地址异常！");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// 设置失败
			retConnCode = Status.FAIL;
			retAppCode = Status.FAIL;
			retMsg = "调用[" + serviceCode + "]网络IO异常！";
			logger.error(SysErr.E_DEFAULT, "调用[" + serviceCode + "]网络IO异常！");
			throw new BaseException(SysErr.E_MESSAGE, "调用[" + serviceCode + "]网络IO异常！");
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			// 设置失败
			retConnCode = Status.FAIL;
			retAppCode = Status.FAIL;
			logger.error(SysErr.E_DEFAULT, "调用[" + serviceCode + "]失败，"+retMsg);
			throw new BaseException(SysErr.E_MESSAGE, "调用[" + serviceCode + "]失败，"+retMsg);
		}  catch (Exception e) {
			// 设置失败
			retConnCode = Status.FAIL;
			retAppCode = Status.FAIL;
			retMsg = "调用[" + serviceCode + "]发生未知异常！";
			logger.error(SysErr.E_DEFAULT, "调用[" + serviceCode + "]发生未知异常！");
			throw new BaseException(SysErr.E_MESSAGE, e, "调用[" + serviceCode + "]发生未知异常！");
		} finally {
			if(ParamUtil.CONF_Y.equals(ParamUtil.getMsAgentOpen())){
				// 移除寻址结果数据
				GVarContainer.removeVar(seqNo);
				// 消费者调用服务结束
				try{
					QualityService qualityService = (QualityService) SpringContextHolder.getBean("qualityService");
					qualityService.requestEnd(token, seqNo, svrTenant, svrPartId, svrPartVersion, svrPartInst, svrName, version, retConnCode, retAppCode, retMsg, resBuff);
				}catch (Exception e) {
					logger.info("消费者调用服务结束出现异常，流程继续往下执行...");
				}
			}
			// 20180705 add by chenyl for 关闭httpClient
        	if(null!=postMethod){
        		postMethod.releaseConnection();
        	}
        	if(null!=client){
        		client.getHttpConnectionManager().closeIdleConnections(0);
        	}
		}
		logger.info("调用服务：" + serviceCode + " 结束...");
		return res;
	}
	
	/**
	 * 测试JSON协议服务治理，返回错误不抛异常直接返回整个报文
	 * */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object callServiceNoExceptionForTestJson(String tenant, String partId, String serviceCode, Object reqObj, Class<?> resBodyClass,
			HashMap<String, Object> dyncParam) {
		
		SOAPReqDTO reqDTO = (SOAPReqDTO)reqObj;
		
		logger.info("调用服务：" + serviceCode + " 开始...");
		logger.info("输入参数：tenant[" + tenant + "] , partId[" + partId + "] , serviceCode[" + serviceCode + "] , ReqDTO["
				+ reqDTO + "] , resBodyClass[" + resBodyClass + "] , dyncParam[" + dyncParam + "]");
		if (DataUtil.isNullStr(serviceCode)) {
			logger.error(SysErr.E_DEFAULT, "请求服务码不能空！");
			throw new BaseException(SysErr.E_MESSAGE, "请求服务码不能空！");
		}
		if (null == reqDTO) {
			logger.error(SysErr.E_DEFAULT, "请求报文不能空！");
			throw new BaseException(SysErr.E_MESSAGE, "请求报文不能空！");
		}

		SOAPResDTO res = null;

		// 获取请求流水号
		String seqNo = reqDTO.getRequestHeader().getReqSeqNo();
		// add by chenyl for poc根据不同服务码开头设置不同的参与者
		if (null != serviceCode && serviceCode.indexOf("Ctrl") == 0) {
			// 控制组件
			partId = ParamUtil.getConfig("CTRL_PARTID");
		} else if (null != serviceCode && serviceCode.indexOf("Gpm") == 0) {
			// 通用缴费模型
			partId = ParamUtil.getConfig("GPM_PARTID");
		} else if (null != serviceCode && serviceCode.indexOf("Dps") == 0) {
			// 公积金模型
			partId = ParamUtil.getConfig("DPS_PARTID");
		}

		// 请求地址,后续需要通过服务寻址的方式获取
		String requestUrl = getUrl(seqNo, tenant, partId, serviceCode, dyncParam);
		
		PostMethod postMethod = null;
		HttpClient client = null;
		// 获取寻址返回的数据
		String token = ""; // 交易令牌
		String svrTenant = ""; // 服务方租户
		String svrPartId = ""; // 服务方参与者ID
		String svrPartVersion = ""; // 服务方参与者版本
		String svrPartInst = ""; // 服务方参与者实例
		String svrName = ""; // 服务方服务名
		String version = ""; // 服务方服务版本
		String timeout = null; // 服务方服务超时时间，单位：毫秒
		String retConnCode = Status.FAIL;
		String retAppCode = Status.FAIL;
		String retMsg = "交易失败";
		byte[] resBuff = null;

		try {
			logger.info("请求地址：" + requestUrl);
			JSONResult jsonResult = new JSONResult();
			String reqMsg = jsonResult.parseJson("", reqDTO);

			// 消费方服务请求开始
			resBuff = "{}".getBytes("UTF-8");
			if (ParamUtil.CONF_Y.equals(ParamUtil.getMsAgentOpen())) {
				// 获取缓存信息agent启动信息
				HashMap<String, Object> agentMap = (HashMap<String, Object>) CacheUtil.get("MSAgent");
				token = (String) agentMap.get(MapKey.TOKEN); // 交易令牌
				String reqSvrName = reqObj.getClass().getSimpleName(); // 消费方服务名
				String reqVersion = MapKey.DEFAULT_VERSION; // 消费方服务版本

				// 获取寻址返回的数据
				Map<String, Object> retMap = (Map<String, Object>) GVarContainer.getVar(seqNo);
				svrTenant = (String) retMap.get(MapKey.TENANT); // 服务方租户
				svrPartId = (String) retMap.get(MapKey.PARTID); // 服务方参与者ID
				svrPartVersion = (String) retMap.get(MapKey.PART_VERSION); // 服务方参与者版本
				svrPartInst = (String) retMap.get(MapKey.PART_INST); // 服务方参与者实例
				svrName = (String) retMap.get(MapKey.SVR_NAME); // 服务方服务名
				version = (String) retMap.get(MapKey.VERSION); // 服务方服务版本
				timeout = (String) retMap.get(MapKey.TIME_OUT); // 服务方服务超时时间，单位：毫秒
				try {
					QualityService qualityService = (QualityService) SpringContextHolder.getBean("qualityService");
					qualityService.requestStart(token, seqNo, reqSvrName, reqVersion, svrTenant, svrPartId,
							svrPartVersion, svrPartInst, svrName, version, Integer.parseInt(timeout),
							reqMsg.getBytes("UTF-8"));
				} catch (Exception e) {
					logger.info("消费方服务请求开始出现异常，流程继续往下执行...");
				}
			}
			// 20180627 add by chenyl for 使用服务方超时时间
			int responseTimeout = RESPONSE_TIMEOUT; // 默认超时时间
			if (!DataUtil.isNullStr(timeout) && Integer.parseInt(timeout) > 0) {
				// 服务方设置了超时时时间时使用服务方的超时
				responseTimeout = Integer.parseInt(timeout);
			}

			logger.info("发送的请求报文：" + reqMsg);
			RequestEntity entity = new StringRequestEntity(reqMsg, "text/xml", "utf-8");
			client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(CONNECT_TIMEOUT);// 获取连接超时(3秒)
			client.getHttpConnectionManager().getParams().setSoTimeout(responseTimeout);// 获取响应超时
																							// (30秒)
			postMethod = new PostMethod(requestUrl);
			postMethod.setRequestHeader("Content-type", "application/json; charset=utf-8");
			postMethod.setRequestHeader("Accept", "application/json");
			postMethod.setRequestEntity(entity);
			startTime = System.currentTimeMillis();
			int status = client.executeMethod(postMethod);
			logger.info("status：" + status);
			StringBuffer repMsg = new StringBuffer();
			String repJSON = "";
			if (HttpStatus.SC_OK == status) {// 发送成功，状态为200
				InputStream is = postMethod.getResponseBodyAsStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				if (br == null) {
					throw new BaseException(SysErr.E_IO_ERROR, "响应报文读取失败");
				}
				String str = "";
				while (null != (str = br.readLine())) {
					repMsg.append(str);
				}
				repJSON = new String(repMsg.toString().getBytes("UTF-8"));
				if (DataUtil.isNullStr(repJSON)) {
					repJSON = "{}";
				}
				resBuff = repJSON.getBytes("UTF-8"); // 获取质量收集需要的响应报文
				retConnCode = Status.SUCCESS; // 设置通讯状态为成功
				logger.info("接收的响应报文： \r\n" + repJSON);
			}
			endTime = System.currentTimeMillis();
			logger.info("调用远程服务花费时间(单位：毫秒)：" + (endTime - startTime));

			// 将json字符串转出json对象
//			JSONObject jsonObject = JSONObject.fromObject(repJSON);
//			Map classResMap = new HashMap(); // 设置响应报文中需要用到转换的数据对象类
//			classResMap.put("Fault", SOAPResFaultDTO.class); // 设置响应报文系统头中错误信息类
//			classResMap.put("ResponseBody", resBodyClass); // 设置响应报文体类
//			// 附加映射类,如http-json.xml配置文件搜索到本服务有附加配置则按在根节点下增加附加映射
//			/*Map classMap = getClassMap(serviceCode);
//			if(classMap != null)
//				classResMap.putAll(classMap);*/
//			
//			/*add by ruanyh 增加报文头类获取*/
//			getClassMap(classResMap, SOAPResDTO.class);
//			/*报文体类获取*/
//			getClassMap(classResMap, resBodyClass);
//			/*add by ruanyh 将JsonObject对象节点不在classResMap中的删除*/
//			removeOtherNode(jsonObject, classResMap);

			// 处理返回报文
//			res = (SOAPResDTO) JSONObject.toBean(jsonObject, SOAPResDTO.class, classResMap);
			// 换成fastjson转换报文
			res = JSON.parseObject(repJSON, new TypeReference<SOAPResDTO>() {});
			
			// 将json字符串转出json对象
			/*JSONObject jsonHead = jsonObject.getJSONObject("SYS_HEAD");*/
			/*if ("F".equals(jsonHead.get("TX_STAT")) || "f".equals(jsonHead.get("TX_STAT"))) {
				logger.error(SysErr.E_DEFAULT, "请求失败[" + serviceCode + "]获取数据异常！");
				List<JSONObject> msgList = (List<JSONObject>) jsonHead.get("TX_RET");
				String errCode = null;
				String msg = null;
				for (JSONObject json : msgList) {
					errCode = (String) json.get("RET_CODE");
					msg = (String) json.get("RET_MSG");
				}
				retMsg = "错误码：" + errCode + ".错误信息：" + msg; // 设置交易错误信息
				throw new BaseException(SysErr.E_MESSAGE, "错误码：" + errCode + ".错误信息：" + msg);
			}*/
			retAppCode = Status.SUCCESS; // 设置应用状态成功
			retMsg = "交易成功";
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			// 设置失败
			retConnCode = Status.FAIL;
			retAppCode = Status.FAIL;
			retMsg = "调用[" + serviceCode + "]请求地址异常！";
			logger.error(SysErr.E_DEFAULT, "调用[" + serviceCode + "]请求地址异常！");
			throw new BaseException(SysErr.E_MESSAGE, "调用[" + serviceCode + "]请求地址异常！");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// 设置失败
			retConnCode = Status.FAIL;
			retAppCode = Status.FAIL;
			retMsg = "调用[" + serviceCode + "]网络IO异常！";
			logger.error(SysErr.E_DEFAULT, "调用[" + serviceCode + "]网络IO异常！");
			throw new BaseException(SysErr.E_MESSAGE, "调用[" + serviceCode + "]网络IO异常！");
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			// 设置失败
			retConnCode = Status.FAIL;
			retAppCode = Status.FAIL;
			logger.error(SysErr.E_DEFAULT, "调用[" + serviceCode + "]失败，"+retMsg);
			throw new BaseException(SysErr.E_MESSAGE, "调用[" + serviceCode + "]失败，"+retMsg);
		}  catch (Exception e) {
			// 设置失败
			retConnCode = Status.FAIL;
			retAppCode = Status.FAIL;
			retMsg = "调用[" + serviceCode + "]发生未知异常！";
			logger.error(SysErr.E_DEFAULT, "调用[" + serviceCode + "]发生未知异常！");
			throw new BaseException(SysErr.E_MESSAGE, e, "调用[" + serviceCode + "]发生未知异常！");
		} finally {
			if(ParamUtil.CONF_Y.equals(ParamUtil.getMsAgentOpen())){
				// 移除寻址结果数据
				GVarContainer.removeVar(seqNo);
				// 消费者调用服务结束
				try{
					QualityService qualityService = (QualityService) SpringContextHolder.getBean("qualityService");
					qualityService.requestEnd(token, seqNo, svrTenant, svrPartId, svrPartVersion, svrPartInst, svrName, version, retConnCode, retAppCode, retMsg, resBuff);
				}catch (Exception e) {
					logger.info("消费者调用服务结束出现异常，流程继续往下执行...");
				}
			}
			// 20180705 add by chenyl for 关闭httpClient
        	if(null!=postMethod){
        		postMethod.releaseConnection();
        	}
        	if(null!=client){
        		client.getHttpConnectionManager().closeIdleConnections(0);
        	}
		}
		logger.info("调用服务：" + serviceCode + " 结束...");
		return res;
	}
	
	/**
	 * 初始化http_json.xml的配置参数
	 */
	@SuppressWarnings("rawtypes")
	public static void initHttpJsonXml(){
		try {
			//首先清理已存在的缓存
			CacheUtil.getCacheManager().removeCache(CacheUtil.HTTP_JSON_CACHE);
			String path = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
			File file = new File(path+"http-json.xml");
			logger.info("初始化http-json.xml开始...");
			SAXReader reader = new SAXReader();
			Document doc = reader.read(file);
			Element root = doc.getRootElement();
			Element svc;
			for (Iterator i = root.elementIterator("service"); i.hasNext();) {
				svc = (Element) i.next();
				String svcId = svc.attributeValue("id");
				String svcName = svc.attributeValue("name");
				String svcUrl = svc.attributeValue("url");
				Service service = new Service(svcId, svcName, svcUrl);
//				System.out.println("service:{" + svcId+" , "+svcName+" , "+svcUrl+" , ");
				for(Iterator i2 = svc.elementIterator("responsebody"); i2.hasNext();){
					Element res = (Element)i2.next();
					String resId = res.attributeValue("id");
					String resName = res.attributeValue("name");
					ResponseBody responseBody = new ResponseBody(resId, resName);
//					System.out.println("\tresponseBody:{" + resId+" , "+resName+" }, [");
					for(Iterator i3 = res.elementIterator("clazz"); i3.hasNext();){
						Element cla = (Element)i3.next();
						String claId = cla.attributeValue("id");
						String claName = cla.attributeValue("name");
						String claPName = cla.attributeValue("parent_attr_name");
						ResClazz resClazz = new ResClazz(claId, claName, claPName);
						responseBody.getClazzList().add(resClazz);
//						System.out.println("\t\tclazz:{" + claId+" , "+claName+" , "+claPName+" },");
					}
					service.setResponseBody(responseBody);
//					System.out.println("\t]");
				}
//				System.out.println("}");
				//配置参数缓存到httpJson缓存中
				CacheUtil.put(CacheUtil.HTTP_JSON_CACHE, svcId, service);
			}
			logger.info("缓存http-json.xml完成...");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 调用中间业务云平台交易
	 * @param serviceCode	服务码
	 * @param reqBody		请求报文体对象
	 * @param resBodyClass	响应报文体的类
	 * @return				返回响应报文MBC_RES对象
	 */
	@SuppressWarnings("rawtypes")
	public MBC_RES callMBC(String serviceCode, Object reqBody, Class resBodyClass){
		return callMBC("", "", serviceCode, reqBody, resBodyClass, 0, 0, null);
	}
	
	/**
	 * 调用中间业务云平台交易
	 * @param serviceCode	服务码
	 * @param reqBody		请求报文体对象
	 * @param resBodyClass	响应报文体的类
	 * @param start			起始记录数：1开始
	 * @param pageSize		每页最大记录数
	 * @return				返回响应报文MBC_RES对象
	 */
	@SuppressWarnings("rawtypes")
	public MBC_RES callMBC(String serviceCode, Object reqBody, Class resBodyClass,  int start,int pageSize){
		return callMBC("", "", serviceCode, reqBody, resBodyClass, start, pageSize, null);
	}
	
	/**
	 * 调用中间业务云平台交易(带动态参数)
	 * @param tenant		服务所属租户
	 * @param partId		服务所属参与者ID
	 * @param serviceCode	服务码
	 * @param reqBody		请求报文体对象
	 * @param resBodyClass	响应报文体的类
	 * @param dyncParam		动态参数，可以实现灰度、流控、熔断、黑白名单
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public MBC_RES callMBC(String tenant, String partId, String serviceCode, Object reqBody, Class resBodyClass, HashMap<String, Object> dyncParam){
		return callMBC(tenant, partId, serviceCode, reqBody, resBodyClass,  0, 0, dyncParam);
	}
	
	/**
	 * 调用中间业务云平台交易(带动态参数)
	 * @param tenant		服务所属租户
	 * @param partId		服务所属参与者ID
	 * @param serviceCode	服务码
	 * @param reqBody		请求报文体对象
	 * @param resBodyClass	响应报文体的类
	 * @param start			起始记录数：1开始
	 * @param pageSize		每页最大记录数
	 * @param dyncParam		动态参数，可以实现灰度、流控、熔断、黑白名单
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MBC_RES callMBC(String tenant, String partId, String serviceCode, Object reqBody, Class resBodyClass,
			int start, int pageSize, HashMap<String, Object> dyncParam) {
		logger.info("调用服务：" + serviceCode + " 开始...");
		logger.info("输入参数：tenant[" + tenant + "] , partId[" + partId + "] , serviceCode[" + serviceCode + "] , reqBody["
				+ reqBody + "] , resBodyClass[" + resBodyClass + "] , start[" + start + "] , pageSize[" + pageSize
				+ "] , dyncParam[" + dyncParam + "]");
		if (DataUtil.isNullStr(serviceCode)) {
			logger.error(SysErr.E_DEFAULT, "请求服务码不能空！");
			throw new BaseException(SysErr.E_MESSAGE, "请求服务码不能空！");
		}
		if (null == reqBody) {
			logger.error(SysErr.E_DEFAULT, "请求报文不能空！");
			throw new BaseException(SysErr.E_MESSAGE, "请求报文不能空！");
		}
		// 设置请求报文
		MBC_REQ req = buildReqHead(serviceCode, start, pageSize);
		req.setBODY(reqBody);
		// 设置响应报文
		MBC_RES res = null;

		// 获取请求流水号
		String seqNo = req.getSYS_HEAD().getREQ_SEQ_NO();
		// add by chenyl for poc根据不同服务码开头设置不同的参与者
		if (null != serviceCode && serviceCode.indexOf("Ctrl") == 0) {
			// 控制组件
			partId = ParamUtil.getConfig("CTRL_PARTID");
		} else if (null != serviceCode && serviceCode.indexOf("Gpm") == 0) {
			// 通用缴费模型
			partId = ParamUtil.getConfig("GPM_PARTID");
		} else if (null != serviceCode && serviceCode.indexOf("Mbm") == 0) {
			// 公积金模型
			partId = ParamUtil.getConfig("DPS_PARTID");
		}
		
		// 请求地址,后续需要通过服务寻址的方式获取
		String requestUrl = "";
		// 20180628 add by chenyl for 以防未登陆的获取用户空
		User user = UserUtils.getUser();
		Rent rent = null;
		if (null != user && null != user.getOffice()) {
			rent = user.getRent();
		}
		if (null != rent) {
			logger.info("租户ID：{} , 默认寻址：{}", rent.getEngName(), rent.getUrl());
			if (null != rent.getUrl() && !"".equals(rent.getUrl())) {
				// 根据配置的固定地址进行寻址
				requestUrl = rent.getUrl() + "/" + serviceCode;
			} else {
				// 为空时，则调用服务框架进行对应的寻址
				requestUrl = getUrl(seqNo, tenant, partId, serviceCode, dyncParam);
			}
		} else {
			if (!DataUtil.isNullStr(tenant) && !DataUtil.isNullStr(partId)) {
				// 入参中如果租户、参与者编号不为空，进行微服务框架寻址
				requestUrl = getUrl(seqNo, tenant, partId, serviceCode, dyncParam);
			} else {
				// 如果没有租户则不进行寻址，通过配置文件(httpJsonAddress)获取默认URL
				requestUrl = ParamUtil.getConfig("httpJsonAddress") + "/" + serviceCode;
			}
		}

		PostMethod postMethod = null;
		HttpClient client = null;
		// 获取缓存信息agent启动信息
		HashMap<String, Object> agentMap = (HashMap<String, Object>) CacheUtil.get("MSAgent");
		String token = (String) agentMap.get(MapKey.TOKEN); // 交易令牌
		String reqSvrName = req.getBODY().getClass().getSimpleName(); // 消费方服务名
		String reqVersion = MapKey.DEFAULT_VERSION; // 消费方服务版本

		// 获取寻址返回的数据
		Map<String, Object> retMap = (Map<String, Object>) GVarContainer.getVar(seqNo);
		String svrTenant = (String) retMap.get(MapKey.TENANT); // 服务方租户
		String svrPartId = (String) retMap.get(MapKey.PARTID); // 服务方参与者ID
		String svrPartVersion = (String) retMap.get(MapKey.PART_VERSION); // 服务方参与者版本
		String svrPartInst = (String) retMap.get(MapKey.PART_INST); // 服务方参与者实例
		String svrName = (String) retMap.get(MapKey.SVR_NAME); // 服务方服务名
		String version = (String) retMap.get(MapKey.VERSION); // 服务方服务版本
		String timeout = (String) retMap.get(MapKey.TIME_OUT); // 服务方服务超时时间，单位：毫秒
		String retConnCode = Status.FAIL;
		String retAppCode = Status.FAIL;
		String retMsg = "交易失败";
		byte[] resBuff = null;
		QualityService qualityService = (QualityService) SpringContextHolder.getBean("qualityService");

		try {
			// 20180627 add by chenyl for 使用服务方超时时间
			int responseTimeout = RESPONSE_TIMEOUT; // 默认超时时间
			if (!DataUtil.isNullStr(timeout) && Integer.parseInt(timeout) > 0) {
				// 服务方设置了超时时时间时使用服务方的超时
				responseTimeout = Integer.parseInt(timeout);
			}
			logger.info("请求地址：" + requestUrl);
			JSONResult jsonResult = new JSONResult();
			String reqMsg = jsonResult.parseJson("", req);

			// 消费方服务请求开始
			resBuff = "{}".getBytes("UTF-8");
			try{
				qualityService.requestStart(token, seqNo, reqSvrName, reqVersion, svrTenant, svrPartId, svrPartVersion, svrPartInst, svrName, version, Integer.parseInt(timeout), reqMsg.getBytes("UTF-8"));
			}catch (Exception e) {
				logger.info("消费方服务请求开始出现异常，流程继续往下执行...");
			}

			logger.info("发送的请求报文：" + reqMsg);
			RequestEntity entity = new StringRequestEntity(reqMsg, "text/xml", "utf-8");
			client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(CONNECT_TIMEOUT);// 获取连接超时(3秒)
			client.getHttpConnectionManager().getParams().setSoTimeout(responseTimeout);// 获取响应超时
																							// (30秒)
			postMethod = new PostMethod(requestUrl);
			postMethod.setRequestHeader("Content-type", "application/json; charset=utf-8");
			postMethod.setRequestHeader("Accept", "application/json");
			postMethod.setRequestEntity(entity);
			startTime = System.currentTimeMillis();
			int status = client.executeMethod(postMethod);
			logger.info("status：" + status);
			StringBuffer repMsg = new StringBuffer();
			String repJSON = "";
			if (HttpStatus.SC_OK == status) {// 发送成功，状态为200
				InputStream is = postMethod.getResponseBodyAsStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				if (br == null) {
					throw new BaseException(SysErr.E_IO_ERROR, "响应报文读取失败");
				}
				String str = "";
				while (null != (str = br.readLine())) {
					repMsg.append(str);
				}
				repJSON = new String(repMsg.toString().getBytes("UTF-8"));
				if (DataUtil.isNullStr(repJSON)) {
					repJSON = "{}";
				}
				resBuff = repJSON.getBytes("UTF-8"); // 获取质量收集需要的响应报文
				retConnCode = Status.SUCCESS; // 设置通讯状态为成功
				logger.info("接收的响应报文： \r\n" + repJSON);
			}
			endTime = System.currentTimeMillis();
			logger.info("调用远程服务花费时间(单位：毫秒)：" + (endTime - startTime));

			// 将json字符串转出json对象
			Map<String, String> jsonMap1 = com.alibaba.fastjson.JSONObject.parseObject(repJSON, Map.class);
			JSONObject jsonObject = JSONObject.fromObject(jsonMap1);

			JSONObject jsonHead = jsonObject.getJSONObject("SYS_HEAD");
			if ("F".equals(jsonHead.get("TX_STAT")) || "f".equals(jsonHead.get("TX_STAT"))) {
				logger.error(SysErr.E_DEFAULT, "请求失败[" + serviceCode + "]获取数据异常！");
				List<JSONObject> msgList = (List<JSONObject>) jsonHead.get("TX_RET");
				String errCode = null;
				String msg = null;
				for (JSONObject json : msgList) {
					errCode = (String) json.get("RET_CODE");
					msg = (String) json.get("RET_MSG");
				}
				retMsg = "错误码：" + errCode + ".错误信息：" + msg; // 设置交易错误信息
				throw new BaseException(SysErr.E_MESSAGE, "错误码：" + errCode + ".错误信息：" + msg);
			}

			Map classResMap = new HashMap(); // 设置响应报文中需要用到转换的数据对象类
			classResMap.put("TX_RET", MBC_RES_SYS_HEAD_RET.class); // 设置响应报文系统头中错误信息类
			classResMap.put("BODY", resBodyClass); // 设置响应报文体类
			// 附加映射类
			classResMap.putAll(getClassMap(serviceCode));

			// 处理返回报文
			res = (MBC_RES) JSONObject.toBean(jsonObject, MBC_RES.class, classResMap);
			retAppCode = Status.SUCCESS; // 设置应用状态成功
			retMsg = "交易成功";
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			// 设置失败
			retConnCode = Status.FAIL;
			retAppCode = Status.FAIL;
			retMsg = "调用[" + serviceCode + "]请求地址异常！";

			logger.error(SysErr.E_DEFAULT, "调用[" + serviceCode + "]请求地址异常！");
			throw new BaseException(SysErr.E_MESSAGE, "调用[" + serviceCode + "]请求地址异常！");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// 设置失败
			retConnCode = Status.FAIL;
			retAppCode = Status.FAIL;
			retMsg = "调用[" + serviceCode + "]网络IO异常！";

			logger.error(SysErr.E_DEFAULT, "调用[" + serviceCode + "]网络IO异常！");
			throw new BaseException(SysErr.E_MESSAGE, "调用[" + serviceCode + "]网络IO异常！");
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			// 设置失败
			retConnCode = Status.FAIL;
			retAppCode = Status.FAIL;
			retMsg = "调用[" + serviceCode + "]请求报文转换JSON格式异常！";

			logger.error(SysErr.E_DEFAULT, "调用[" + serviceCode + "]请求报文转换JSON格式异常！");
			throw new BaseException(SysErr.E_MESSAGE, "调用[" + serviceCode + "]请求报文转换JSON格式异常！");
		} catch (Exception e) {
			// 设置失败
			retConnCode = Status.FAIL;
			retAppCode = Status.FAIL;
			retMsg = "调用[" + serviceCode + "]发生未知异常！";

			logger.error(SysErr.E_DEFAULT, "调用[" + serviceCode + "]发生未知异常！");
			throw new BaseException(SysErr.E_MESSAGE, e, "调用[" + serviceCode + "]发生未知异常！");
		} finally {
			// 移除寻址结果数据
			GVarContainer.removeVar(seqNo);
			// 消费者调用服务结束
			try{
				qualityService.requestEnd(token, seqNo, svrTenant, svrPartId, svrPartVersion, svrPartInst, svrName, version, retConnCode, retAppCode, retMsg, resBuff);
			}catch (Exception e) {
				logger.info("消费者调用服务结束出现异常，流程继续往下执行...");
			}
			// 20180705 add by chenyl for 关闭httpClient
        	if(null!=postMethod){
        		postMethod.releaseConnection();
        	}
        	if(null!=client){
        		client.getHttpConnectionManager().closeIdleConnections(0);
        	}
		}
		logger.info("调用服务：" + serviceCode + " 结束...");
		return res;

	}
	
	/**
	 * 设置请求报文头
	 */
	public MBC_REQ buildReqHead(String svcCode, int start,int pageSize){
		//系统头
		MBC_REQ_SYS_HEAD reqSysHead = new MBC_REQ_SYS_HEAD();
		reqSysHead.setREQ_MODL_NO(SysUtil.MODL_NO);	//请求组件/模型编号
		reqSysHead.setREQ_IP(SysUtil.getLocalIp());				//请求方IP
		reqSysHead.setREQ_DATE(DateUtil.getDate());		//服务请求方日期
		reqSysHead.setREQ_SVC_CODE(svcCode);	//请求服务码
		reqSysHead.setREQ_SEQ_NO(SeqUtil.getMBCSeq());	//服务请求发送方流水号
		//应用头
		MBC_REQ_APP_HEAD reqAppHead = new MBC_REQ_APP_HEAD();
		reqAppHead.setBRCH_NO("0758"); //机构号
		reqAppHead.setTX_DATE(DateUtil.getDate());	//交易日期
		reqAppHead.setTX_TIME(DateUtil.getTime());	//交易时间
		reqAppHead.setBGN_REC_NO(""+start);	//开始记录数
		reqAppHead.setREQ_REC_NUM(""+pageSize);	//要求每页返回记录总数
		//本地扩展头
		MBC_REQ_LOCAL_HEAD reqLocalHead = new MBC_REQ_LOCAL_HEAD();
		MBC_REQ req = new MBC_REQ();
		req.setSYS_HEAD(reqSysHead);
		req.setAPP_HEAD(reqAppHead);
		req.setLOCAL_HEAD(reqLocalHead);
		return req;
	}
	
	/**
	 * 设置请求报文头---业务
	 * 
	 * add by chenyl 20170905
	 */
	public MBC_REQ buildReqHead(String svcCode, String busiNo, int start, int pageSize) {
		// 系统头
		MBC_REQ_SYS_HEAD reqSysHead = new MBC_REQ_SYS_HEAD();
		reqSysHead.setREQ_MODL_NO(SysUtil.MODL_NO); // 请求组件/模型编号
		reqSysHead.setREQ_IP(SysUtil.getLocalIp()); // 请求方IP
		reqSysHead.setREQ_DATE(DateUtil.getDate()); // 服务请求方日期
		reqSysHead.setREQ_SVC_CODE(svcCode); // 请求服务码
		String seqNo = SeqUtil.getMBCSeq();
		reqSysHead.setREQ_SEQ_NO(DateUtil.getDate() + seqNo); // 服务请求发送方流水号
		reqSysHead.setSEQ_NO(DateUtil.getDate() + seqNo);
		// 应用头
		MBC_REQ_APP_HEAD reqAppHead = new MBC_REQ_APP_HEAD();
		reqAppHead.setBRCH_NO("0758"); // 机构号
		reqAppHead.setTX_DATE(DateUtil.getDate()); // 交易日期
		reqAppHead.setTX_TIME(DateUtil.getTime()); // 交易时间
		reqAppHead.setBGN_REC_NO("" + start); // 开始记录数
		reqAppHead.setREQ_REC_NUM("" + pageSize); // 要求每页返回记录总数
		// 本地扩展头
		MBC_REQ_LOCAL_HEAD reqLocalHead = new MBC_REQ_LOCAL_HEAD();
		reqLocalHead.setCHNL_NO("000001");
		reqLocalHead.setBUSI_NO(busiNo);
		reqLocalHead.setLEGA_NO("00001");
		reqLocalHead.setCHNL_SEQ_NO(DateUtil.getDate() + seqNo);
		reqLocalHead.setENTR_NO("00070100000012");
		MBC_REQ req = new MBC_REQ();
		req.setSYS_HEAD(reqSysHead);
		req.setAPP_HEAD(reqAppHead);
		req.setLOCAL_HEAD(reqLocalHead);
		return req;
	}
	/**
	 * 
	 * @Title: DPS_buildReqHead   
	 * @Description: 保证金请求头  
	 * @author: chenyl    
	 * @param svcCode
	 * @param busiNo
	 * @param start
	 * @param pageSize
	 * @return
	 * @date:   2017年9月27日 上午11:37:13
	 */
	public MBC_REQ DPS_buildReqHead(String svcCode, String busiNo, int start, int pageSize) {
		// 系统头
		MBC_REQ_SYS_HEAD reqSysHead = new MBC_REQ_SYS_HEAD();
		reqSysHead.setREQ_MODL_NO("999304"); // 请求组件/模型编号
		reqSysHead.setREQ_IP(SysUtil.getLocalIp()); // 请求方IP
		reqSysHead.setREQ_DATE(DateUtil.getDate()); // 服务请求方日期
		reqSysHead.setREQ_SVC_CODE(svcCode); // 请求服务码
		String seqNo = SeqUtil.getMBCSeq();
		reqSysHead.setREQ_SEQ_NO(DateUtil.getDate() + seqNo); // 服务请求发送方流水号
		reqSysHead.setSEQ_NO(DateUtil.getDate() + seqNo);
		// 应用头
		MBC_REQ_APP_HEAD reqAppHead = new MBC_REQ_APP_HEAD();
		reqAppHead.setBRCH_NO("0758"); // 机构号
		reqAppHead.setTLR_NO(UserUtils.getUser().getLoginName());
		reqAppHead.setTX_DATE(DateUtil.getDate()); // 交易日期
		reqAppHead.setTX_TIME(DateUtil.getTime()); // 交易时间
		reqAppHead.setBGN_REC_NO("" + start); // 开始记录数
		reqAppHead.setREQ_REC_NUM("" + pageSize); // 要求每页返回记录总数
		// 本地扩展头
		MBC_REQ_LOCAL_HEAD reqLocalHead = new MBC_REQ_LOCAL_HEAD();
		reqLocalHead.setCHNL_NO("000001");
		reqLocalHead.setBUSI_NO(busiNo);
		reqLocalHead.setLEGA_NO("00001");
		reqLocalHead.setCHNL_SEQ_NO(DateUtil.getDate() + seqNo);
		MBC_REQ req = new MBC_REQ();
		req.setSYS_HEAD(reqSysHead);
		req.setAPP_HEAD(reqAppHead);
		req.setLOCAL_HEAD(reqLocalHead);
		return req;
	}
	/**
	 * call 服务器 --- 业务
	 * 
	 * @param requestUrl	请求服务器url地址
	 * @param serviceCode	服务码
	 * @param busiNo		业务编号
	 * @param reqBody		请求报文体
	 * @param resBodyClass	请求响应实体类
	 * @param start			起始记录数：1开始
	 * @param pageSize		每页最大记录数
	 * @return				请求返回报文体
	 * 
	 * add by chenyl 20170905
	 */
	public JSONObject callMBC(String requestUrl, String serviceCode, String busiNo, Object reqBody,
			Object resBodyClass, int start, int pageSize) {
		logger.info("调用服务：" + serviceCode + " 开始...");
		if (DataUtil.isNullStr(serviceCode)) {
			logger.error(SysErr.E_DEFAULT, "请求服务码不能空！");
			throw new BaseException(SysErr.E_MESSAGE, "请求服务码不能空！");
		}
		if (null == reqBody) {
			logger.error(SysErr.E_DEFAULT, "请求报文不能空！");
			throw new BaseException(SysErr.E_MESSAGE, "请求报文不能空！");
		}
		// 设置请求报文
		MBC_REQ req = this.buildReqHead(serviceCode, busiNo, start, pageSize);
		req.setBODY(reqBody);

		PostMethod postMethod = null;
		HttpClient client = null;
		JSONObject jsonObject;
		try {
			logger.info("请求地址：" + requestUrl);
			JSONResult jsonResult = new JSONResult();
			String reqMsg = jsonResult.parseJson("", req);
			logger.info("发送的请求报文：" + reqMsg);
			RequestEntity entity = new StringRequestEntity(reqMsg, "text/xml", "utf-8");
			client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(6000);// 获取连接超时(3秒)
			client.getHttpConnectionManager().getParams().setSoTimeout(60000);// 获取响应超时
																				// (30秒)
			postMethod = new PostMethod(requestUrl);
			postMethod.setRequestHeader("Content-type", "application/json; charset=utf-8");
			postMethod.setRequestHeader("Accept", "application/json");
			postMethod.setRequestEntity(entity);
			long startTime = System.currentTimeMillis();
			int status = client.executeMethod(postMethod);
			logger.info("status：" + status);
//			System.out.println("=============stats=[" + status + "]");
			StringBuffer repMsg = new StringBuffer();
			String repJSON = "";
			if (HttpStatus.SC_OK == status) {// 发送成功，状态为200
				InputStream is = postMethod.getResponseBodyAsStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				if (br == null) {
					throw new BaseException(SysErr.E_IO_ERROR, "响应报文读取失败");
				}
				String str = "";
				while (null != (str = br.readLine())) {
					repMsg.append(str);
				}
				repJSON = new String(repMsg.toString().getBytes("UTF-8"));
				if (DataUtil.isNullStr(repJSON)) {
					repJSON = "{}";
				}
				logger.info("接收的响应报文： \r\n" + repJSON);
			}
			long endTime = System.currentTimeMillis();
			logger.info("调用远程服务花费时间(单位：毫秒)：" + (endTime - startTime));

			// 将json字符串转出json对象
			Map<String, String> jsonMap1 = com.alibaba.fastjson.JSONObject.parseObject(repJSON, Map.class);
			jsonObject = JSONObject.fromObject(jsonMap1);
			
			JSONObject jsonHead = jsonObject.getJSONObject("SYS_HEAD");
			if ("F".equals(jsonHead.get("TX_STAT")) || "f".equals(jsonHead.get("TX_STAT"))) {
				logger.error(SysErr.E_DEFAULT, "请求失败[" + serviceCode + "]获取数据异常！");
				@SuppressWarnings("unchecked")
				List<JSONObject> msgList = (List<JSONObject>) jsonHead.get("TX_RET");
				String errCode = null;
				String msg = null;
				for (JSONObject json : msgList) {
					errCode = (String) json.get("RET_CODE");
					msg = (String) json.get("RET_MSG");
				}
				throw new BaseException(SysErr.E_MESSAGE, "错误码：" + errCode + ".错误信息：" + msg);
			}

		} catch (MalformedURLException e) {
			logger.error(SysErr.E_DEFAULT, "调用[" + serviceCode + "]请求地址异常！");
			throw new BaseException(SysErr.E_MESSAGE, "调用[" + serviceCode + "]请求地址异常！");
		} catch (IOException e) {
			logger.error(SysErr.E_DEFAULT, "调用[" + serviceCode + "]网络IO异常！");
			throw new BaseException(SysErr.E_MESSAGE, "调用[" + serviceCode + "]网络IO异常！");
		} catch (BaseException e) {
			logger.error(SysErr.E_DEFAULT, "调用[" + serviceCode + "]请求报文转换JSON格式异常！");
			throw new BaseException(SysErr.E_MESSAGE, "调用[" + serviceCode + "]请求报文转换JSON格式异常！");
		} catch (Exception e) {
			logger.error(SysErr.E_DEFAULT, "调用[" + serviceCode + "]发生未知异常！");
			throw new BaseException(SysErr.E_MESSAGE, e, "调用[" + serviceCode + "]发生未知异常！" + e);
		} finally {
        	// 20180705 add by chenyl for 关闭httpClient
        	if(null!=postMethod){
        		postMethod.releaseConnection();
        	}
        	if(null!=client){
        		client.getHttpConnectionManager().closeIdleConnections(0);
        	}
        }
		logger.info("调用服务：" + serviceCode + " 结束...");
		return jsonObject;
	}
	/**
	 * 
	 * @Title: MBC_DPS_callMBC   
	 * @Description: 保证金服务调用  
	 * @author: chenyl
	 * @param serviceCode
	 * @param busiNo
	 * @param reqBody
	 * @param resBodyClass
	 * @param start
	 * @param pageSize
	 * @return
	 * @date:   2017年9月27日 上午11:34:47
	 */
	public JSONObject MBC_DPS_callMBC(String serviceCode, String busiNo, Object reqBody,
			Object resBodyClass, int start, int pageSize) {
		logger.info("调用服务：" + serviceCode + " 开始...");
		if (DataUtil.isNullStr(serviceCode)) {
			logger.error(SysErr.E_DEFAULT, "请求服务码不能空！");
			throw new BaseException(SysErr.E_MESSAGE, "请求服务码不能空！");
		}
		if (null == reqBody) {
			logger.error(SysErr.E_DEFAULT, "请求报文不能空！");
			throw new BaseException(SysErr.E_MESSAGE, "请求报文不能空！");
		}
		// 设置请求报文
		MBC_REQ req = this.DPS_buildReqHead(serviceCode, busiNo, start, pageSize);
		req.setBODY(reqBody);

		PostMethod postMethod = null;
		HttpClient client = null;
		JSONObject jsonObject;
		
		// 获取请求流水号
		String seqNo = req.getSYS_HEAD().getREQ_SEQ_NO();
		String partId = "";
		// add by chenyl for poc根据不同服务码开头设置不同的参与者
		if (null != serviceCode && serviceCode.indexOf("Ctrl") == 0) {
			// 控制组件
			partId = ParamUtil.getConfig("CTRL_PARTID");
		} else if (null != serviceCode && serviceCode.indexOf("Gpm") == 0) {
			// 通用缴费模型
			partId = ParamUtil.getConfig("GPM_PARTID");
		} else if (null != serviceCode && serviceCode.indexOf("Mbm") == 0) {
			// 公积金模型
			partId = ParamUtil.getConfig("DPS_PARTID");
		}
		
		// 请求地址,后续需要通过服务寻址的方式获取
		String requestUrl = "";
		// 20180628 add by chenyl for 以防未登陆的获取用户空
		User user = UserUtils.getUser();
		Rent rent = null;
		if (null != user && null != user.getOffice()) {
			rent = user.getRent();
		}
		if (null != rent) {
			logger.info("租户ID：{} , 默认寻址：{}", rent.getEngName(), rent.getUrl());
			if (null != rent.getUrl() && !"".equals(rent.getUrl())) {
				// 根据配置的固定地址进行寻址
				requestUrl = rent.getUrl() + "/" + serviceCode;
			} else {
				// 为空时，则调用服务框架进行对应的寻址
				requestUrl = getUrl(seqNo, "", partId, serviceCode, null);
			}
		} else {
			if (!DataUtil.isNullStr(partId)) {
				// 入参中如果参与者编号不为空，进行微服务框架寻址
				requestUrl = getUrl(seqNo, "", partId, serviceCode, null);
			} else {
				// 如果没有租户则不进行寻址，通过配置文件(httpJsonAddress)获取默认URL
				requestUrl = ParamUtil.getConfig("httpJsonAddress") + "/" + serviceCode;
			}
		}
		
		try {
			logger.info("请求地址：" + requestUrl);
			JSONResult jsonResult = new JSONResult();
			String reqMsg = jsonResult.parseJson("", req);
			logger.info("发送的请求报文：" + reqMsg);
			RequestEntity entity = new StringRequestEntity(reqMsg, "text/xml", "utf-8");
			client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(6000);// 获取连接超时(3秒)
			client.getHttpConnectionManager().getParams().setSoTimeout(60000);// 获取响应超时
																				// (30秒)
			postMethod = new PostMethod(requestUrl);
			postMethod.setRequestHeader("Content-type", "application/json; charset=utf-8");
			postMethod.setRequestHeader("Accept", "application/json");
			postMethod.setRequestEntity(entity);
			long startTime = System.currentTimeMillis();
			int status = client.executeMethod(postMethod);
			logger.info("status：" + status);
//			System.out.println("=============stats=[" + status + "]");
			StringBuffer repMsg = new StringBuffer();
			String repJSON = "";
			if (HttpStatus.SC_OK == status) {// 发送成功，状态为200
				InputStream is = postMethod.getResponseBodyAsStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				if (br == null) {
					throw new BaseException(SysErr.E_IO_ERROR, "响应报文读取失败");
				}
				String str = "";
				while (null != (str = br.readLine())) {
					repMsg.append(str);
				}
				repJSON = new String(repMsg.toString().getBytes("UTF-8"));
				if (DataUtil.isNullStr(repJSON)) {
					repJSON = "{}";
				}
				logger.info("接收的响应报文： \r\n" + repJSON);
			}
			long endTime = System.currentTimeMillis();
			logger.info("调用远程服务花费时间(单位：毫秒)：" + (endTime - startTime));

			// 将json字符串转出json对象
			Map<String, String> jsonMap1 = com.alibaba.fastjson.JSONObject.parseObject(repJSON, Map.class);
			jsonObject = JSONObject.fromObject(jsonMap1);
			
			JSONObject jsonHead = jsonObject.getJSONObject("SYS_HEAD");
			if ("F".equals(jsonHead.get("TX_STAT")) || "f".equals(jsonHead.get("TX_STAT"))) {
				logger.error(SysErr.E_DEFAULT, "请求失败[" + serviceCode + "]获取数据异常！");
				@SuppressWarnings("unchecked")
				List<JSONObject> msgList = (List<JSONObject>) jsonHead.get("TX_RET");
				String errCode = null;
				String msg = null;
				for (JSONObject json : msgList) {
					errCode = (String) json.get("RET_CODE");
					msg = (String) json.get("RET_MSG");
				}
				if(!"80004030".equals(errCode)){
					throw new BaseException(SysErr.E_MESSAGE, "错误码：" + errCode + ".错误信息：" + msg);
				}
			}

		} catch (MalformedURLException e) {
			logger.error(SysErr.E_DEFAULT, "调用[" + serviceCode + "]请求地址异常！");
			throw new BaseException(SysErr.E_MESSAGE, "调用[" + serviceCode + "]请求地址异常！");
		} catch (IOException e) {
			logger.error(SysErr.E_DEFAULT, "调用[" + serviceCode + "]网络IO异常！");
			throw new BaseException(SysErr.E_MESSAGE, "调用[" + serviceCode + "]网络IO异常！");
		} catch (BaseException e) {
			logger.error(SysErr.E_DEFAULT, "调用[" + serviceCode + "]请求报文转换JSON格式异常！");
			throw new BaseException(SysErr.E_MESSAGE, "调用[" + serviceCode + "]请求报文转换JSON格式异常！");
		} catch (Exception e) {
			logger.error(SysErr.E_DEFAULT, "调用[" + serviceCode + "]发生未知异常！");
			throw new BaseException(SysErr.E_MESSAGE, e, "调用[" + serviceCode + "]发生未知异常！" + e);
		} finally {
        	// 20180705 add by chenyl for 关闭httpClient
        	if(null!=postMethod){
        		postMethod.releaseConnection();
        	}
        	if(null!=client){
        		client.getHttpConnectionManager().closeIdleConnections(0);
        	}
        }
		logger.info("调用服务：" + serviceCode + " 结束...");
		return jsonObject;
	}
	/*根据交易码返回响应报文下存在的自定义属性*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map getClassMap(String svcCode){
		Map classMap = new HashMap();
		Service service = (Service)CacheUtil.get(CacheUtil.HTTP_JSON_CACHE, svcCode);
		ResponseBody responseBody = service.getResponseBody();
		for(ResClazz resClazz:responseBody.getClazzList()){
			try {
				classMap.put(resClazz.getParentAttrName(), Class.forName(resClazz.getId()));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		return classMap;
	}
	
	/*递归遍历class，将对象属性为List和Map类型的放入classMap中*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void getClassMap(Map classMap, Class clazz){
		//logger.info("getClassMap.clazz" + clazz);
		Field[] fs = clazz.getDeclaredFields();
		for(Field field:fs){
			//logger.info("getClassMap.field.name====" + field.getName());
			Class actType = null;
			Class type = field.getType();
			if(type.isAssignableFrom(ArrayList.class)){
				Type gt = field.getGenericType();   //得到泛型类型  
				if(gt instanceof ParameterizedType){
					ParameterizedType pt = (ParameterizedType)gt;  
		            actType = (Class)pt.getActualTypeArguments()[0]; 
				}else{
					continue;
				}
			}else if(type.isAssignableFrom(HashMap.class)){
				Type gt = field.getGenericType();   //得到泛型类型  
				if(gt instanceof ParameterizedType){
					ParameterizedType pt = (ParameterizedType)gt;  
		            actType = (Class)pt.getActualTypeArguments()[1]; 
				}else{
					continue;
				}
			}else if(!(type.isPrimitive() || "String".equals(type.getSimpleName()) || "Object".equals(type.getSimpleName()))){
				actType = type;
			}else{
				continue;
			}
			
            if(!(actType.isPrimitive() || "String".equals(actType.getSimpleName()) || "Object".equals(actType.getSimpleName()) || "Long".equals(actType.getSimpleName()) || "Double".equals(actType.getSimpleName())||"BigDecimal".equals(actType.getSimpleName()))){
            	try {
            		Class subClass = Class.forName(actType.getName());
					classMap.put(field.getName(), subClass);
					getClassMap(classMap, subClass);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
		}
	}
	
	/*递归遍历JsonOnject对象，将对象节点不在classResMap中的删除*/
	@SuppressWarnings("rawtypes")
	public static void removeOtherNode(Object obj, Map classResMap){
		
		JSONObject jsonObject = null;
		
		if(obj == null){
			return;
		}else if(obj instanceof JSONObject){
			jsonObject = (JSONObject)obj;
		}else if(obj instanceof JSONArray){
			JSONArray jsonArray = (JSONArray)obj;
			if(jsonArray.size() <= 0){
				return;
			}
			jsonObject = jsonArray.getJSONObject(0);
		}else{
			return;
		}
		
		JSONArray arr = jsonObject.names();
		for(int i=0;i<arr.size();i++){
			if(arr.get(i) == null){
				continue;
			}
			
			String key = arr.get(i).toString().trim();
			if(jsonObject.get(key) instanceof JSONObject || jsonObject.get(key) instanceof JSONArray){
				if("CLOB".equals(key)){		//CLOB动态字段不删除
					continue;
				}else if(classResMap.get(key) == null){		//删除非指定解析内容节点
					logger.info("节点["+key+"]非指定解析内容，删除本节点...");
					jsonObject.remove(key);
				}else{
					removeOtherNode(jsonObject.get(key), classResMap);
				}
			}
		}
	}
	
	/*根据交易码返回对应的服务url*/
//	public static String getUrl(String svcCode){
//		String url = "";
//		Service service = (Service)CacheUtil.get(CacheUtil.HTTP_JSON_CACHE, svcCode);
//		url = service.getUrl();		
//		return url;
//	}
	
	/**
	 * 基于微服务框架，根据交易码返回对应的服务url
	 * @param seqNo			全局流水号
	 * @param tenant		服务所属租户
	 * @param partId		服务所属参与者ID
	 * @param svcCode		服务码
	 * @param dyncParam		动态参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getUrl(String seqNo, String tenant, String partId, String svcCode, HashMap<String, Object> dyncParam){
		String url = "";

		if (null != tenant && tenant.isEmpty()) {
			tenant = TENANT;
		}
		if (null != partId && partId.isEmpty()) {
			partId = PARTID;
		}
		/*
		 * 20200715 add by chenyl for 增加未启用微服务代理时，通过 租户+参与者
		 * 方式指定请求路径前缀的方式拼接服务码后形成真实访问的url地址
		 */
		if (ParamUtil.CONF_Y.equals(ParamUtil.getMsAgentOpen())) {
			HashMap<String, Object> agentMap = (HashMap<String, Object>)CacheUtil.get("MSAgent"); // 获取缓存信息agent启动信息
			if(null==agentMap){
				logger.error("服务寻址失败,本地缓存中没有微服务登录认证信息！");
				throw new BaseException(SysErr.E_MESSAGE, "服务寻址失败,本地缓存中没有微服务登录认证信息！");
			}
			String token = (String) agentMap.get(MapKey.TOKEN);		//交易令牌
			if("Y".equals(ParamUtil.getString("ms.check.token"))){
				AgentMngService agentMngService = (AgentMngService) SpringContextHolder.getBean("agentMngService");
				HashMap<String, Object > chkMap = agentMngService.checkRefToken(token);
				if(!Status.SUCCESS.equals(chkMap.get(MapKey.RETCODE))){
					logger.warn("令牌["+token+"]已失效，准备更新令牌！");
					HashMap<String, Object > getMap = agentMngService.genRefToken(token);
					if(Status.SUCCESS.equals(getMap.get(MapKey.RETCODE))){
						String newToken = (String) getMap.get(MapKey.TOKEN);
						token = newToken;
						agentMap.put(MapKey.TOKEN, newToken);
						CacheUtil.put("MSAgent", agentMap);
						logger.warn("令牌更新成功，获取新令牌["+newToken+"]！");
					}
				}
			}
			AddressingService addressingService = (AddressingService) SpringContextHolder.getBean("addressingService");		
			HashMap<String, Object> retMap = addressingService.getSvrAddr(seqNo, token, tenant, partId, svcCode, dyncParam);
			logger.info("服务寻址结果 retMap="+retMap.toString());
			String retCode = (String) retMap.get(MapKey.RETCODE);
			String msg = (String) retMap.get(MapKey.MSG);
			if(null!=retCode && !Status.SUCCESS.equals(retCode)){
				logger.error("服务寻址失败！"+msg);
				throw new BaseException(SysErr.E_MESSAGE, "服务寻址失败！"+msg);
			}

			com.alibaba.fastjson.JSONObject comHttp = (com.alibaba.fastjson.JSONObject)retMap.get("COM_HTTP");
			url = (String) comHttp.get("URL");

			// 设置寻址结果外部调用
			GVarContainer.setVar(seqNo, retMap);
		} else {
			// 非寻址模式通过租户地址或配置文件
			SystemService systemService = SpringContextHolder.getBean("systemService");
			Rent rent = systemService.getRentByEngName(tenant);
			if (null != rent) {
				logger.info("租户ID：{} , 默认寻址：{}", rent.getEngName(), rent.getUrl());
				if (null != rent.getUrl() && !"".equals(rent.getUrl())) {
					// 根据配置的固定地址进行寻址
					url = rent.getUrl();
				} else {
					/*20220315 mod by chenyl for 当存在参与者时则进行转换处理*/
					if(!DataUtil.isNullStr(partId)){
						url = ParamUtil.getConfig("requrl."+partId);
					}
					if(DataUtil.isNullStr(url)){
						// 如果没有租户则不进行寻址，通过配置文件(httpJsonAddress)获取默认URL
						url = ParamUtil.getConfig("httpJsonAddress");
					}else{
						logger.info("参与者：{} , 默认寻址：{}", partId, url);
					}
				}
			} else {
				/*20220315 mod by chenyl for 当存在参与者时则进行转换处理*/
				if(!DataUtil.isNullStr(partId)){
					url = ParamUtil.getConfig("requrl."+partId);
				}
				if(DataUtil.isNullStr(url)){
					// 如果没有租户则不进行寻址，通过配置文件(httpJsonAddress)获取默认URL
					url = ParamUtil.getConfig("httpJsonAddress");
				}else{
					logger.info("参与者：{} , 默认寻址：{}", partId, url);
				}
			}
		}
		url = url.endsWith("/")?(url+svcCode):(url+ "/" + svcCode);
		logger.info("url：" + url);
		return url;
	}
	
		/**请求报文全大写
		 * by dyf
		 * jsonObject会把A_B_C的格式默认转成a_B_C
		 * 该方法替换掉此格式
		 * @param serviceCode
		 * @param reqObj
		 * @param resBodyClass
		 * @return
		 */
		public Object callService(boolean upper,String tenant, String partId, String serviceCode, Object reqObj, Class<?> resBodyClass,
				HashMap<String, Object> dyncParam) {

			
			ReqDTO reqDTO = (ReqDTO)reqObj;
			
			logger.info("调用服务：" + serviceCode + " 开始...");
			logger.info("输入参数：tenant[" + tenant + "] , partId[" + partId + "] , serviceCode[" + serviceCode + "] , ReqDTO["
					+ reqDTO + "] , resBodyClass[" + resBodyClass + "] , dyncParam[" + dyncParam + "]");
			if (DataUtil.isNullStr(serviceCode)) {
				logger.error(SysErr.E_DEFAULT, "请求服务码不能空！");
				throw new BaseException(SysErr.E_MESSAGE, "请求服务码不能空！");
			}
			if (null == reqDTO) {
				logger.error(SysErr.E_DEFAULT, "请求报文不能空！");
				throw new BaseException(SysErr.E_MESSAGE, "请求报文不能空！");
			}

			ResDTO res = null;

			// 获取请求流水号
			String seqNo = reqDTO.getSYS_HEAD().getREQ_SEQ_NO();
			// add by chenyl for poc根据不同服务码开头设置不同的参与者
			if (null != serviceCode && serviceCode.indexOf("Ctrl") == 0) {
				// 控制组件
				partId = ParamUtil.getConfig("CTRL_PARTID");
			} else if (null != serviceCode && serviceCode.indexOf("Gpm") == 0) {
				// 通用缴费模型
				partId = ParamUtil.getConfig("GPM_PARTID");
			} else if (null != serviceCode && serviceCode.indexOf("Mbm") == 0) {
				// 公积金模型
				partId = ParamUtil.getConfig("DPS_PARTID");
			}else if(null != serviceCode && serviceCode.indexOf("MBat") == 0){
				// 批量模型 add by weizhj
				partId = ParamUtil.getConfig("MBAT_PARTID");
			}

			// 请求地址,后续需要通过服务寻址的方式获取
			String requestUrl = getUrl(seqNo, tenant, partId, serviceCode, dyncParam);
			
			PostMethod postMethod = null;
			HttpClient client = null;
			// 获取寻址返回的数据
			String token = ""; // 交易令牌
			String svrTenant = ""; // 服务方租户
			String svrPartId = ""; // 服务方参与者ID
			String svrPartVersion = ""; // 服务方参与者版本
			String svrPartInst = ""; // 服务方参与者实例
			String svrName = ""; // 服务方服务名
			String version = ""; // 服务方服务版本
			String timeout = null; // 服务方服务超时时间，单位：毫秒
			String retConnCode = Status.FAIL;
			String retAppCode = Status.FAIL;
			String retMsg = "交易失败";
			byte[] resBuff = null;

			try {
				
				logger.info("请求地址：" + requestUrl);
				JSONResult jsonResult = new JSONResult();
				String reqMsg = jsonResult.parseJson("", reqDTO);
				//String newMsg= JSONObject.fromObject(reqDTO).toString();
				
				if(upper){
					//reqMsg= appendReplacementRegex(reqMsg).toString();
					reqMsg= dealWithRecursion( JSONObject.fromObject(reqDTO)).toString();
				}
				
				// 消费方服务请求开始
				resBuff = "{}".getBytes("UTF-8");
				if(ParamUtil.CONF_Y.equals(ParamUtil.getMsAgentOpen())){
					// 获取缓存信息agent启动信息
					HashMap<String, Object> agentMap = (HashMap<String, Object>) CacheUtil.get("MSAgent");
					token = (String) agentMap.get(MapKey.TOKEN); // 交易令牌
					String reqSvrName = reqDTO.getBODY().getClass().getSimpleName(); // 消费方服务名
					String reqVersion = MapKey.DEFAULT_VERSION; // 消费方服务版本

					// 获取寻址返回的数据
					Map<String, Object> retMap = (Map<String, Object>) GVarContainer.getVar(seqNo);
					svrTenant = (String) retMap.get(MapKey.TENANT); // 服务方租户
					svrPartId = (String) retMap.get(MapKey.PARTID); // 服务方参与者ID
					svrPartVersion = (String) retMap.get(MapKey.PART_VERSION); // 服务方参与者版本
					svrPartInst = (String) retMap.get(MapKey.PART_INST); // 服务方参与者实例
					svrName = (String) retMap.get(MapKey.SVR_NAME); // 服务方服务名
					version = (String) retMap.get(MapKey.VERSION); // 服务方服务版本
					timeout = (String) retMap.get(MapKey.TIME_OUT); // 服务方服务超时时间，单位：毫秒
					try{
						QualityService qualityService = (QualityService) SpringContextHolder.getBean("qualityService");
						qualityService.requestStart(token, seqNo, reqSvrName, reqVersion, svrTenant, svrPartId, svrPartVersion, svrPartInst, svrName, version, Integer.parseInt(timeout), reqMsg.getBytes("UTF-8"));
					}catch (Exception e) {
						logger.info("消费方服务请求开始出现异常，流程继续往下执行...");
					}
				}
				// 20180627 add by chenyl for 使用服务方超时时间
				int responseTimeout = RESPONSE_TIMEOUT; // 默认超时时间
				if(!DataUtil.isNullStr(timeout) && Integer.parseInt(timeout)>0){
					// 服务方设置了超时时时间时使用服务方的超时
					responseTimeout = Integer.parseInt(timeout);
				}

				logger.info("发送的请求报文：" + reqMsg);
				RequestEntity entity = new StringRequestEntity(reqMsg, "text/xml", "utf-8");
				client = new HttpClient();
				client.getHttpConnectionManager().getParams().setConnectionTimeout(CONNECT_TIMEOUT);// 获取连接超时(3秒)
				client.getHttpConnectionManager().getParams().setSoTimeout(responseTimeout);// 获取响应超时
																								// (30秒)
				postMethod = new PostMethod(requestUrl);
				postMethod.setRequestHeader("Content-type", "application/json; charset=utf-8");
				postMethod.setRequestHeader("Accept", "application/json");
				postMethod.setRequestEntity(entity);
				startTime = System.currentTimeMillis();
				int status = client.executeMethod(postMethod);
				logger.info("status：" + status);
				StringBuffer repMsg = new StringBuffer();
				String repJSON = "";
				if (HttpStatus.SC_OK == status) {// 发送成功，状态为200
					InputStream is = postMethod.getResponseBodyAsStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
					if (br == null) {
						throw new BaseException(SysErr.E_IO_ERROR, "响应报文读取失败");
					}
					String str = "";
					while (null != (str = br.readLine())) {
						repMsg.append(str);
					}
					repJSON = new String(repMsg.toString().getBytes("UTF-8"));
					if (DataUtil.isNullStr(repJSON)) {
						repJSON = "{}";
					}
					resBuff = repJSON.getBytes("UTF-8"); // 获取质量收集需要的响应报文
					retConnCode = Status.SUCCESS; // 设置通讯状态为成功
					logger.info("接收的响应报文： \r\n" + repJSON);
				}
				endTime = System.currentTimeMillis();
				logger.info("调用远程服务花费时间(单位：毫秒)：" + (endTime - startTime));

				// 将json字符串转出json对象
				Map<String, String> jsonMap1 = com.alibaba.fastjson.JSONObject.parseObject(repJSON, Map.class);
				JSONObject jsonObject = JSONObject.fromObject(jsonMap1);
				Map classResMap = new HashMap(); // 设置响应报文中需要用到转换的数据对象类
				classResMap.put("TX_RET", SysHeadRetResDTO.class); // 设置响应报文系统头中错误信息类
				classResMap.put("BODY", resBodyClass); // 设置响应报文体类
				// 附加映射类,如http-json.xml配置文件搜索到本服务有附加配置则按在根节点下增加附加映射
				/*Map classMap = getClassMap(serviceCode);
				if(classMap != null)
					classResMap.putAll(classMap);*/
				
				/*add by ruanyh 增加报文头类获取*/
				getClassMap(classResMap, ResDTO.class);
				/*报文体类获取*/
				getClassMap(classResMap, resBodyClass);
				/*add by ruanyh 将JsonObject对象节点不在classResMap中的删除*/
				removeOtherNode(jsonObject, classResMap);

				// 处理返回报文
				res = (ResDTO) JSONObject.toBean(jsonObject, ResDTO.class, classResMap);

				// 将json字符串转出json对象
				JSONObject jsonHead = jsonObject.getJSONObject("SYS_HEAD");
				
				// 添加成功S的判断-add by weizhj
				if("S".equals(jsonHead.get("TX_STAT")) || "s".equals(jsonHead.get("TX_STAT"))){
					
				}else if("S".equals(jsonHead.get("TRAN_STAT")) || "s".equals(jsonHead.get("TRAN_STAT"))){
					
				}else{
					List<JSONObject> msgList = new ArrayList<JSONObject>();
					// 请求失败
					if ("F".equals(jsonHead.get("TX_STAT")) || "f".equals(jsonHead.get("TX_STAT"))) {
						logger.error(SysErr.E_DEFAULT, "请求失败[" + serviceCode + "]获取数据异常！");
						msgList = (List<JSONObject>) jsonHead.get("TX_RET");
					}else if ("F".equals(jsonHead.get("TRAN_STAT")) || "f".equals(jsonHead.get("TRAN_STAT"))) {
						logger.error(SysErr.E_DEFAULT, "请求失败[" + serviceCode + "]获取数据异常！");
						msgList = (List<JSONObject>) jsonHead.get("TRAN_RET");
					}else {
						// 请求超时
						if ("T".equals(jsonHead.get("TX_STAT")) || "t".equals(jsonHead.get("TX_STAT"))) {
							logger.error(SysErr.E_DEFAULT, "请求[" + serviceCode + "]超时！");
							msgList = (List<JSONObject>) jsonHead.get("TX_RET");
						}else if ("T".equals(jsonHead.get("TRAN_STAT")) || "t".equals(jsonHead.get("TRAN_STAT"))) {
							logger.error(SysErr.E_DEFAULT, "请求[" + serviceCode + "]超时！");
							msgList = (List<JSONObject>) jsonHead.get("TRAN_RET");
						}
					}
					if(null != msgList && !msgList.isEmpty()){
						String errCode = null;
						String msg = null;
						for (JSONObject json : msgList) {
							errCode = (String) json.get("RET_CODE");
							msg = (String) json.get("RET_MSG");
						}
						retMsg = "错误码：" + errCode + ".错误信息：" + msg; // 设置交易错误信息
						throw new BaseException(SysErr.E_MESSAGE, "错误码：" + errCode + ".错误信息：" + msg);
					}else{
						throw new BaseException(SysErr.E_MESSAGE, "请求[" + serviceCode + "]失败！");
					}
				} 
				retAppCode = Status.SUCCESS; // 设置应用状态成功
				retMsg = "交易成功";
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				// 设置失败
				retConnCode = Status.FAIL;
				retAppCode = Status.FAIL;
				retMsg = "调用[" + serviceCode + "]请求地址异常！";
				logger.error(SysErr.E_DEFAULT, "调用[" + serviceCode + "]请求地址异常！");
				throw new BaseException(SysErr.E_MESSAGE, "调用[" + serviceCode + "]请求地址异常！");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// 设置失败
				retConnCode = Status.FAIL;
				retAppCode = Status.FAIL;
				retMsg = "调用[" + serviceCode + "]网络IO异常！";
				logger.error(SysErr.E_DEFAULT, "调用[" + serviceCode + "]网络IO异常！");
				throw new BaseException(SysErr.E_MESSAGE, "调用[" + serviceCode + "]网络IO异常！");
			} catch (BaseException e) {
				// TODO Auto-generated catch block
				// 设置失败
				retConnCode = Status.FAIL;
				retAppCode = Status.FAIL;
				logger.error(SysErr.E_DEFAULT, "调用[" + serviceCode + "]失败，"+retMsg);
				throw new BaseException(SysErr.E_MESSAGE, "调用[" + serviceCode + "]失败，"+retMsg);
			}  catch (Exception e) {
				// 设置失败
				retConnCode = Status.FAIL;
				retAppCode = Status.FAIL;
				retMsg = "调用[" + serviceCode + "]发生未知异常！";
				logger.error(SysErr.E_DEFAULT, "调用[" + serviceCode + "]发生未知异常！");
				throw new BaseException(SysErr.E_MESSAGE, e, "调用[" + serviceCode + "]发生未知异常！");
			} finally {
				if(ParamUtil.CONF_Y.equals(ParamUtil.getMsAgentOpen())){
					// 移除寻址结果数据
					GVarContainer.removeVar(seqNo);
					// 消费者调用服务结束
					try{
						QualityService qualityService = (QualityService) SpringContextHolder.getBean("qualityService");
						qualityService.requestEnd(token, seqNo, svrTenant, svrPartId, svrPartVersion, svrPartInst, svrName, version, retConnCode, retAppCode, retMsg, resBuff);
					}catch (Exception e) {
						logger.info("消费者调用服务结束出现异常，流程继续往下执行...");
					}
				}
				// 20180705 add by chenyl for 关闭httpClient
	        	if(null!=postMethod){
	        		postMethod.releaseConnection();
	        	}
	        	if(null!=client){
	        		client.getHttpConnectionManager().closeIdleConnections(0);
	        	}
				
			}
			logger.info("调用服务：" + serviceCode + " 结束...");
			return res;
		}
		
		
		/**
		 * 递归net.sf.json.JSONAbject 替换key
		 * @param object
		 * @return
		 * @throws Exception
		 */
		public  Object dealWithRecursion(Object object) throws Exception {
	        if (object instanceof JSONObject) {
	            JSONObject jsonObject = (JSONObject) object;
	            if (jsonObject.isNullObject()){return "";}
	            Set keySet = jsonObject.keySet();   //得到当前层所有的key
	            Map map = new HashMap<String ,Object>();
	            for (Object o : keySet) {//循环当前层的所有key
	                if (jsonObject.get(o) instanceof JSONObject) {  //若为JSONObject再次递归此方法
	                    JSONObject ooo = (JSONObject) jsonObject.get(o);
	                    if (ooo.isNullObject()) {
	                        jsonObject.put(o, "");
	                    } else {
	                        dealWithRecursion(jsonObject.get(o));
	                    }
	                } else if (jsonObject.get(o) instanceof JSONArray) {//若为JSONOArray再次递归此方法
	                    dealWithRecursion(jsonObject.get(o));
	                } else {    //若为字符串,则把该key 和 value 放进Map
	                    //在此装入所有String类型的key和值
	                    map.put(o.toString(),jsonObject.get(o));
	                }
	            }
	            Set set = map.keySet();//得到上面map的所有key
	            for (Object key :set){    //在此for循环内替换
	                String exStr = appendReplacementRegex(key.toString()).toString();

	                jsonObject.remove(key);
	                //jsonObject.element(key.toString().toUpperCase(),map.get(key));
	                jsonObject.element(exStr,map.get(key));

	            }
	            return jsonObject;
	        } else if (object instanceof JSONArray) {
	            List<Object> list = new ArrayList<>();
	            JSONArray jsonArray = (JSONArray) object;
	            for (int i = 0; i < jsonArray.size(); i++) {
	                Object o = jsonArray.get(i);
	                list.add(dealWithRecursion(o));
	            }
	            return list;
	        } else {
	            return object + "";
	        }
	    }
		
		
		
	/**
	 * 替换掉所有 [a-z]_ 为[A-Z]_
	 * @param str
	 * @return
	 */
	public StringBuffer appendReplacementRegex(String str) {
		String regEx = "[a-z]_";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(0).toUpperCase());
        }
       sb.append(str.substring(sb.length()));
        return sb;
    }

	public Object callService(String partCode, String serviceCode, Object reqObj, Class<?> resBodyClass) {

		ReqDTO reqDTO = (ReqDTO)reqObj;

		ResDTO res = null;
		String requestUrl = ParamUtil.getString(partCode) + serviceCode;

		PostMethod postMethod = null;
		HttpClient client = null;
		String retMsg = "交易失败";

		try {
			JSONResult jsonResult = new JSONResult();
			String reqMsg = jsonResult.parseJson("", reqDTO);

			RequestEntity entity = new StringRequestEntity(reqMsg, "text/xml", "utf-8");
			client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(3000);// 获取连接超时(3秒)
			client.getHttpConnectionManager().getParams().setSoTimeout(60000);// 获取响应超时
			// (30秒)
			postMethod = new PostMethod(requestUrl);
			postMethod.setRequestHeader("Content-type", "application/json; charset=utf-8");
			postMethod.setRequestHeader("Accept", "application/json");
			postMethod.setRequestEntity(entity);
			int status = client.executeMethod(postMethod);
			logger.info("发送的请求报文：" + reqMsg);
			StringBuffer repMsg = new StringBuffer();
			String repJSON = "";
			if (HttpStatus.SC_OK == status) {// 发送成功，状态为200
				InputStream is = postMethod.getResponseBodyAsStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				if (br == null) {
					throw new BaseException(SysErr.E_IO_ERROR, "响应报文读取失败");
				}
				String str = "";
				while (null != (str = br.readLine())) {
					repMsg.append(str);
				}
				repJSON = new String(repMsg.toString().getBytes("UTF-8"));
				if (DataUtil.isNullStr(repJSON)) {
					repJSON = "{}";
				}
			}
			logger.info("接收的响应报文：" + repMsg);

			// 将json字符串转出json对象
			Map<String, String> jsonMap1 = com.alibaba.fastjson.JSONObject.parseObject(repJSON, Map.class);
			JSONObject jsonObject = JSONObject.fromObject(jsonMap1);
			Map classResMap = new HashMap(); // 设置响应报文中需要用到转换的数据对象类
			classResMap.put("TX_RET", SysHeadRetResDTO.class); // 设置响应报文系统头中错误信息类
			classResMap.put("BODY", resBodyClass); // 设置响应报文体类
			/*add by ruanyh 增加报文头类获取*/
			getClassMap(classResMap, ResDTO.class);
			/*报文体类获取*/
			getClassMap(classResMap, resBodyClass);
			/*add by ruanyh 将JsonObject对象节点不在classResMap中的删除*/
			removeOtherNode(jsonObject, classResMap);
			// 处理返回报文
			res = (ResDTO) JSONObject.toBean(jsonObject, ResDTO.class, classResMap);

			// 将json字符串转出json对象
			JSONObject jsonHead = jsonObject.getJSONObject("SYS_HEAD");

			// 添加成功S的判断-add by weizhj
			if("S".equals(jsonHead.get("TX_STAT")) || "s".equals(jsonHead.get("TX_STAT"))){

			}else if("S".equals(jsonHead.get("TRAN_STAT")) || "s".equals(jsonHead.get("TRAN_STAT"))){

			}else{
				List<JSONObject> msgList = new ArrayList<JSONObject>();
				// 请求失败
				if ("F".equals(jsonHead.get("TX_STAT")) || "f".equals(jsonHead.get("TX_STAT"))) {
					logger.error(SysErr.E_DEFAULT, "请求失败[" + serviceCode + "]获取数据异常！");
					msgList = (List<JSONObject>) jsonHead.get("TX_RET");
				}else if ("F".equals(jsonHead.get("TRAN_STAT")) || "f".equals(jsonHead.get("TRAN_STAT"))) {
					logger.error(SysErr.E_DEFAULT, "请求失败[" + serviceCode + "]获取数据异常！");
					msgList = (List<JSONObject>) jsonHead.get("TRAN_RET");
				}else {
					// 请求超时
					if ("T".equals(jsonHead.get("TX_STAT")) || "t".equals(jsonHead.get("TX_STAT"))) {
						logger.error(SysErr.E_DEFAULT, "请求[" + serviceCode + "]超时！");
						msgList = (List<JSONObject>) jsonHead.get("TX_RET");
					}else if ("T".equals(jsonHead.get("TRAN_STAT")) || "t".equals(jsonHead.get("TRAN_STAT"))) {
						logger.error(SysErr.E_DEFAULT, "请求[" + serviceCode + "]超时！");
						msgList = (List<JSONObject>) jsonHead.get("TRAN_RET");
					}
				}
				if(null != msgList && !msgList.isEmpty()){
					String errCode = null;
					String msg = null;
					for (JSONObject json : msgList) {
						errCode = (String) json.get("RET_CODE");
						msg = (String) json.get("RET_MSG");
					}
					retMsg = "错误码：" + errCode + ".错误信息：" + msg; // 设置交易错误信息
					throw new BaseException(SysErr.E_MESSAGE, "错误码：" + errCode + ".错误信息：" + msg);
				}else{
					throw new BaseException(SysErr.E_MESSAGE, "请求[" + serviceCode + "]失败！");
				}
			}
			retMsg = "交易成功";

		} catch (MalformedURLException e) {
			throw new BaseException(SysErr.E_MESSAGE, "调用[" + serviceCode + "]请求地址异常！");
		} catch (IOException e) {
			throw new BaseException(SysErr.E_MESSAGE, "调用[" + serviceCode + "]网络IO异常！");
		} catch (BaseException e) {
			throw new BaseException(SysErr.E_MESSAGE, "调用[" + serviceCode + "]失败，"+retMsg);
		}  catch (Exception e) {
			throw new BaseException(SysErr.E_MESSAGE, e, "调用[" + serviceCode + "]发生未知异常！");
		} finally {
			if(null!=postMethod){
				postMethod.releaseConnection();
			}
			if(null!=client){
				client.getHttpConnectionManager().closeIdleConnections(0);
			}

		}
		return res;
	}

}
