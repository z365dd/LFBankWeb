package com.adtec.comm.protocol.soap;

import com.adtec.comm.dto.SOAPReqDTO;
import com.adtec.comm.dto.SOAPResDTO;
import com.adtec.comm.dto.head.SOAPResFaultDTO;
import com.adtec.framework.common.util.*;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.ms.msagent.service.AddressingService;
import com.adtec.ms.msagent.service.QualityService;
import com.adtec.ms.msagent.util.MapKey;
import com.adtec.ms.msagent.util.Status;
import com.adtec.sys.modules.sys.entity.Rent;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.soap.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.*;
import java.net.MalformedURLException;
import java.util.*;

/**
 * @类名 SoapFactory.java
 * @描述: TODO
 * @创建人 chenyl
 * @创建时间 2015年3月30日 上午9:36:27
 * @版本
 */
public class SoapFactory {
	/**
	 * 日志对象
	 */
	protected final static Logger logger = LoggerFactory.getLogger(SoapFactory.class);
	public static final int CONNECT_TIMEOUT = 3000;	//获取连接超时(3秒)
	public static final int RESPONSE_TIMEOUT = 30000;//获取响应超时 (30秒)
	public static final String TENANT = ParamUtil.getConfig("Tenant");	// 默认服务方所属租户
	public static final String PARTID = ParamUtil.getConfig("PartId");	// 默认服务方所属参与者
	public static final String EL_RESPONSE_BODY = "ResponseBody";	// 报文体标签
	private static SoapFactory soapFactory;
	private long startTime = 0L;
	private long endTime = 0L;
	
	//请求报文头实体类
	private static final String REQUEST_HEADER_CLASS = "com.adtec.soap.common.bean.RequestHeader";
	//响应报文头实体类
	private static final String RESPONSE_HEADER_CLASS = "com.adtec.soap.common.bean.ResponseHeader";
	//响应信息实体类
	private static final String FAULT_CLASS = "com.adtec.soap.common.bean.Fault";
	//报文体的包结构
	private static final String PREFIX_PACKAGE = "com.adtec.soap.bean.";
	//RPC的前缀
	private static final String RPC_NAME = "adtec:";
	
	private SoapFactory(){}
	
	public static SoapFactory getInstance(){
		if (null == soapFactory) {
			soapFactory = new SoapFactory();
		}
		return soapFactory;
	}
	
	/**
	 * 从className类获取名称为fieldName的成员变量的类型，若成员变量为List类型，则返回该集合的泛型类型
	 * 
	 * @param className
	 * @param fieldName
	 * @return
	 */
	public String getType(String className, String fieldName) {
		String type = "";
		try {
			Class clazz = Class.forName(className);
			//20170709 mod by chenyl for 修改成通过封装的反正工具类获取
			Field[] fields = ClassUtil.getAccessibleFields(clazz.newInstance());
			for (Field f : fields) {
				// 字段名的小写字符串是否包含传入参数的小写字符串
				if (f.getName().toLowerCase().indexOf(fieldName.toLowerCase()) != -1) {
					// 成员变量类型为List集合类型
					if (f.getType().toString().toLowerCase().indexOf("list") != -1) {
						ParameterizedType pt = (ParameterizedType) f.getGenericType();
						Type[] types = pt.getActualTypeArguments();
						if (types.length > 0) {
							type = types[0].toString().split(" ")[1];
						} else {
							try {
								throw new Exception(className + "的集合类型变量" + f.getName() + "需指定具体类型");
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} else {
						type = f.getType().toString().split(" ")[1];
					}
					break;
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return type;
	}

	/**
	 * 由报请求报文、服务码组装为SOAP报文
	 * @param request
	 * @param txnCd
	 * @return
	 * @throws BaseException
	 */
	public byte[] buildSoapMsg(Object request, String txnCd){
		MessageFactory msgFactory = null;
		SOAPMessage message = null;
		byte sendMessage[] = (byte[]) null;
		try {
			msgFactory = MessageFactory.newInstance();
			message = msgFactory.createMessage();
			SOAPPart soapPart = message.getSOAPPart();
			SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
//			soapEnvelope.setEncodingStyle("UTF-8");
			SOAPBody soapBody = soapEnvelope.getBody();
			//rpc方式
			String rpc = ParamUtil.getConfig("soap.rpc");
			// 默认命名空间
			String nameSpace = ParamUtil.getConfig("soap.namespace");
			soapEnvelope.addNamespaceDeclaration(rpc, nameSpace);
			// 设置请求报文体
			SOAPBodyElement bodyElement = soapBody.addBodyElement(soapEnvelope.createName(rpc + ":" + txnCd));
			// 根据请求报文对象生成对应的soapenv:Body
			buildSoapBody(bodyElement, request);
			message.saveChanges();
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			message.writeTo(byteOut);
			sendMessage = byteOut.toByteArray();
		} catch (SOAPException soapex) {
			soapex.printStackTrace();
		} catch (IOException ioex) {
			System.out.println("出现异常");
		}
		return sendMessage;
}

	/**
	 * 讲请求报文对象转成soapBODY,对于ArrayList类型的作为循环体,对于HashMap存在泛型的必须指定value的数据类型
	 *
	 * @param element
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	public void buildSoapBody(SOAPElement element, Object request) {
		if (null == request) {
			logger.info("处理对象为空不处理");
			return;
		}
		Class clazz = request.getClass();
		// 获取类自定义的属性
		Field[] fields = clazz.getDeclaredFields();
		try {
			for (Field field : fields) {
				// 根据每个属性创建对应的标签
				String name = field.getName();
				// 20180718 mod by chenyl for 去掉把属性标签首字母转为大写
				//强制将elementName字符串的首字符转为大写
//				name = name.substring(0,1).toUpperCase() + name.substring(1);
				String getMethod = "get"+(name.substring(0,1).toUpperCase() + name.substring(1));
				Method method = null;
				Object value = null;
				try{
					method = clazz.getMethod(getMethod);
					if (method.invoke(request) != null) {
						value = method.invoke(request);
					}
				}catch(Exception e){
					logger.info("方法不存在设置空标签["+name+"]");
					element.addChildElement(name).addTextNode("");
					continue;
				}
				
				if(null==value){
					logger.info("class["+clazz.getName()+"],属性["+field.getName()+"]的值为null,设置空标签["+name+"]");
					element.addChildElement(name).addTextNode("");
					continue;
				}
				
				Class type = field.getType();
				Class actType = null;
				SOAPElement fieldElement = null;
				String nodeText = "";
				// 如果为数组类型属性
				if(("List".equals(type.getSimpleName()) || "ArrayList".equals(type.getSimpleName())) && type.isAssignableFrom(ArrayList.class)){
					Type gt = field.getGenericType();   //得到泛型类型  
					if(gt instanceof ParameterizedType){
						// 为基本类型的，获取对应值的基本类型
						ParameterizedType pt = (ParameterizedType)gt;  
			            actType = (Class)pt.getActualTypeArguments()[0];
			            ArrayList list = (ArrayList)value;
			            // 如果为基本类型直接遍历当前数组值
			            if(actType.isPrimitive() || "String".equals(actType.getSimpleName())){		       
			            	for(int i=0;i<list.size();i++){
			            		value = list.get(i);
			            		element.addChildElement(name).addTextNode(""+(value==null?"":value));
			            		logger.info("节点["+name+"],值["+value+"]");
			            	}
			            }else{
			            	// 非基本类型的则遍历list中的每个数据对象迭代此方法
			            	for(int i=0;i<list.size();i++){
			            		Object item = list.get(i);
			            		fieldElement = element.addChildElement(name);
			            		buildSoapBody(fieldElement, item);
			            	}
			            }
					}else{
						logger.info("数组只支持ArrayList并指定泛型:"+type);
						continue;
					}
				}else if(("Map".equals(type.getSimpleName()) || "HashMap".equals(type.getSimpleName())) && type.isAssignableFrom(HashMap.class)){
					Type gt = field.getGenericType();   //得到泛型类型  
					if(gt instanceof ParameterizedType){
						// 为基本类型的，获取对应值的基本类型
						ParameterizedType pt = (ParameterizedType)gt;  
			            actType = (Class)pt.getActualTypeArguments()[1];
			            HashMap map = (HashMap)value;
			            fieldElement = element.addChildElement(name);
			            // 如果为基本类型直接遍历当前Map,key为标签，value为值
			            if(actType.isPrimitive() || "String".equals(actType.getSimpleName())){		       
			            	Set keys = map.keySet();
			            	for(Object key:keys){
			            		value = map.get(key);
			            		fieldElement.addChildElement(""+key).addTextNode(""+(value==null?"":value));
			            		logger.info("节点["+key+"],值["+value+"]");
			            	}
			            }else{
			            	// 非基本类型的则遍历list中的每个数据对象迭代此方法
			            	Set keys = map.keySet();
			            	for(Object key:keys){
			            		buildSoapBody(fieldElement.addChildElement(""+key), map.get(key));
			            	}
			            }
					}else{
						logger.info("Map只支持HashMap<String, ?>并指定value泛型:"+type);
						continue;
					}
				}else if(type.isPrimitive() || "String".equals(type.getSimpleName())){
					// 直接设置值
					fieldElement = element.addChildElement(name);
					fieldElement.addTextNode(""+(value==null?"":value));
					logger.info("节点["+name+"],值["+value+"]");
				}else{
					logger.info("非基本数据类型:"+type);
					fieldElement = element.addChildElement(name);
					buildSoapBody(fieldElement, value);
					continue;
				}	           
			}
		} catch (Exception e) {
			logger.error("创建SOAPBODY失败", e);
			throw new BaseException(SysErr.E_DEFAULT, "创建SOAPBODY失败");
		}
	}
	
	/**
	 * 将返回的SOAP报文以解析XML的方式解析为报文类实例
	 * 
	 * @param recvSoapMsg
	 * @param txnCd
	 * @return
	 */
	public Map<String, Object> parseSoapMsg(String recvSoapMsg, String txnCd) {
		Map<String, Object> map = new HashMap<String, Object>();
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(recvSoapMsg); // 将字符串转为XML
			Element bodyElt = doc.getRootElement().element("Body").element(txnCd);//RPC方式
//			Element bodyElt = doc.getRootElement().element("Body"); //document方式

			Object objectResponseHeader = parseElement(bodyElt.element("ResponseHeader"), RESPONSE_HEADER_CLASS);
			Object objectResponseBody = parseElement(bodyElt.element("ResponseBody"), PREFIX_PACKAGE + txnCd.toLowerCase() + ".ResponseBody");
			Object objectFault = parseElement(bodyElt.element("Fault"), FAULT_CLASS);
			map.put("ResponseHeader", objectResponseHeader);
			map.put("ResponseBody", objectResponseBody);
			map.put("Fault", objectFault);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return map;
	}
	
	/**
	 * 将实例写入SOAP报文,若实例含有集合类型的成员变量，则采用递归方式遍历
	 * 
	 * @param soapEnvelope
	 * @param soapElement 当前节点
	 * @param obj 要写入的实例
	 * @param className 实例的全类名
	 */
	public void buildMsg(SOAPEnvelope soapEnvelope, SOAPElement soapElement, Object obj, String className) {

		try {
			Class msgHeadC = Class.forName(className);
			Method[] methods = msgHeadC.getDeclaredMethods();

			for (Method m : methods) {
				if (m.getName().startsWith("get") && !"getClass".equals(m.getName())) {
					try {
						String key = m.getName().substring("get".length());
						//首字母强制转换为大写
						key = key.substring(0,1).toUpperCase() + key.substring(1);
						Object value = null;
						if (m.invoke(obj) != null) {
							value = m.invoke(obj);
						}

						String subClassName = getType(className, "list");
						if (subClassName != null && !"".equals(subClassName) && m.getName().indexOf("List") != -1) {
							SOAPElement listElement = soapElement.addChildElement(soapEnvelope.createName("Loop"));
							if(value != null){
								List list = (List) value;
								for (Object subParam : list) {
									Name name = soapEnvelope.createName("AddInfo");
									SOAPElement subSOAPElement = listElement.addChildElement(name);
									buildMsg(soapEnvelope, subSOAPElement, subParam, subClassName);
								}
							}
						} else {
							if (value == null) {
								value = "";
							}
							soapElement.addChildElement(key).addTextNode(value.toString());
						}
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (SOAPException e) {
						e.printStackTrace();
					}
				}
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 由报文头实例、报文体实例、服务码组装为SOAP报文
	 * @param objHeader
	 * @param objBody
	 * @param txnCd
	 * @return
	 * @throws BaseException
	 */
	public byte[] buildSoapMsg(Object objHeader, Object objBody, String txnCd){
		MessageFactory msgFactory = null;
		SOAPMessage message = null;
		byte sendMessage[] = (byte[]) null;
		try {
			msgFactory = MessageFactory.newInstance();
			message = msgFactory.createMessage();
			SOAPPart soapPart = message.getSOAPPart();
			SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
//			soapEnvelope.setEncodingStyle("UTF-8");
			SOAPBody soapBody = soapEnvelope.getBody();
			//rpc方式
			soapEnvelope.addNamespaceDeclaration("adt", "http://www.adtec.com.cn");
			SOAPBodyElement bodyElement = soapBody.addBodyElement(soapEnvelope.createName(RPC_NAME+txnCd));
			SOAPElement header = bodyElement.addChildElement(soapEnvelope.createName("RequestHeader"));
			SOAPElement body = bodyElement.addChildElement(soapEnvelope.createName("RequestBody"));

			//document方式
//			SOAPElement header = soapBody.addChildElement(soapEnvelope.createName("RequestHeader"));
//			SOAPElement body = soapBody.addChildElement(soapEnvelope.createName("RequestBody"));
			
			buildMsg(soapEnvelope, header, objHeader, REQUEST_HEADER_CLASS);

			String className = PREFIX_PACKAGE + txnCd.toLowerCase() + ".RequestBody";
			buildMsg(soapEnvelope, body, objBody, className);

			message.saveChanges();
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			message.writeTo(byteOut);
			sendMessage = byteOut.toByteArray();
		} catch (SOAPException soapex) {
			soapex.printStackTrace();
		} catch (IOException ioex) {
			System.out.println("出现异常");}
		return sendMessage;
	}
	
//原SOAP报文发送方法	
//	public Map<String, Object> sendSoapMsg(Object objHeader, Object objBody, String txnCd){
//		String url = CommonTool.getProperty("serverAddress") + txnCd;
//		System.out.println((new StringBuilder("URL is:[")).append(url).append("]").toString());
//		SOAPConnectionFactory scf;
//		Map<String, Object> map = null;
//
//		try {
//			scf = SOAPConnectionFactory.newInstance();
//			SOAPConnection sc = scf.createConnection();
//			URLEndpoint urlEndpoint = new URLEndpoint(url);
//
//			byte sendMessage[] = buildSoapMsg(objHeader, objBody, txnCd);
//			// System.out.println("send: " + new String(sendMessage));
//
//			java.io.InputStream inputStream = new ByteArrayInputStream(sendMessage);
//			SOAPMessage soapMessage = MessageFactory.newInstance().createMessage(null, inputStream);
//			SOAPMessage responseMessage = sc.call(soapMessage, urlEndpoint);
//			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
//			responseMessage.writeTo(byteOut);
//			byte recvBytes[] = byteOut.toByteArray();
//			// System.out.println("recv: " + new String(recvBytes));
//
//			map = parseSoapMsg(new String(recvBytes, "UTF-8"), txnCd);
//
//		} catch (UnsupportedOperationException e) {
//			e.printStackTrace();
//		} catch (SOAPException soapex) {
//			soapex.printStackTrace();
//		} catch (IOException ioex) {
//			ioex.printStackTrace();
//		}
//
//		return map;
//	}
	
	/**
	 * 发送SOAP报文，并将返回报文解析为实例放在集合map中返回
	 * @param objHeader
	 * @param objBody
	 * @param txnCd
	 * @return
	 * @throws BaseException
	 */
	public Map<String, Object> sendSoapMsg(Object objHeader, Object objBody, String txnCd){
		String url = ParamUtil.getWsurl();
		if(!url.endsWith("/")){
			url += "/";
		}
		url += txnCd;
		System.out.println((new StringBuilder("URL is:[")).append(url).append("]").toString());
		Map<String, Object> map = null;
		PostMethod postMethod = null;
		HttpClient client = null;

		try {
			byte sendMessage[] = buildSoapMsg(objHeader, objBody, txnCd);
			
			String reqMsg = "<?xml version='1.0' encoding='UTF-8'?>" + new String(sendMessage);
			System.out.println("send: \r\n" + reqMsg);
			RequestEntity entity = new StringRequestEntity(reqMsg, "text/xml", "utf-8");
			client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(3000);//获取连接超时(3秒)
			client.getHttpConnectionManager().getParams().setSoTimeout(30000);//获取响应超时 (30秒)
			postMethod = new PostMethod(url);
			postMethod.setRequestHeader("Content-type", "text/xml; charset=utf-8");
			postMethod.setRequestEntity(entity);
			int result = client.executeMethod(postMethod);
			String repMsg = "";
			String repSOAP = "";
			if (HttpStatus.SC_OK == result) {//发送成功，状态为200
				repMsg = postMethod.getResponseBodyAsString();
				repSOAP = new String(repMsg.getBytes("ISO-8859-1"), "UTF-8");//编码转换
				System.out.println("recv: \r\n" + repSOAP);
			}

			map = parseSoapMsg(repSOAP, txnCd);

		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException ioex) {
			System.out.println("出现异常");
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
 * 将返回的SOAP报文以解析XML的方式解析为报文类实例
 *
 * @param recvSoapMsg    soap报文
 * @param txnCd            交易码
 * @param responseClass 返回报文类 返回报文数据对象
	 * @return
	 */
	public <T> T parseSoapMsg(String recvSoapMsg, String txnCd, Class<T> responseClass) {
		Document doc = null;
		T responseObject = null;
		try {
			doc = DocumentHelper.parseText(recvSoapMsg); // 将字符串转为XML
			String isUsed = (String)ParamUtil.getConfig("soap.usenamespace");
			Element bodyElt = doc.getRootElement().element("Body");
			if(Boolean.parseBoolean(isUsed)){
				// 使用命名空间
				bodyElt = bodyElt.element(txnCd);//RPC方式
			}
			responseObject = (T)parseElement(bodyElt, responseClass);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseObject;
	}
	
	/**
	 * 将一个xml节点的信息解析为一个java实例
	 * 
	 * @param node
	 * @param clazz
	 */
	public Object parseElement(Element node, Class clazz) {
		Object obj = null;
		try {
			if(EL_RESPONSE_BODY.equals(node.getName().trim())){
				// 处理报文体时，把clazz转成对应的响应报文体class
				clazz = (Class) GVarContainer.getVar("RESPONSE_BODY_CLASS");
				logger.info("解析响应报文体标签："+EL_RESPONSE_BODY);
			}
			logger.info("转换xml到数据对象："+clazz.getName());
			// 实例化当前需要返回的类型
			obj = clazz.newInstance();
			// 获取属性列表
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				// 根据每个属性创建对应的标签
				String name = field.getName();
				logger.info("处理对象属性："+name);
				//强制将elementName字符串的首字符转为大写
				// 20180718 mod by chenyl for 去掉强制将elementName字符串的首字符转为大写
				String elementName = name;
				String setMethod = "set"+(name.substring(0,1).toUpperCase() + name.substring(1));
				Method method = null;
				Object value = null;
				Class type = field.getType();
				Class actType = null;
				try{
					method = clazz.getMethod(setMethod, type);
				}catch(Exception e){
					logger.error("class["+clazz.getName()+"],不存方法["+setMethod+"]");
					continue;
				}
				
				// 如果为数组类型属性
				if(("List".equals(type.getSimpleName()) || "ArrayList".equals(type.getSimpleName())) && type.isAssignableFrom(ArrayList.class)){
					logger.info("循环节点："+elementName);
					value = Lists.newArrayList();
					List<Element> subNodes = node.elements(elementName);
					if(null!=subNodes && !subNodes.isEmpty()){
						Type gt = field.getGenericType();   //得到泛型类型  
						if(gt instanceof ParameterizedType){
							// 为基本类型的，获取对应值的基本类型
							ParameterizedType pt = (ParameterizedType)gt;  
				            actType = (Class)pt.getActualTypeArguments()[0];
				            // 如果为基本类型直接遍历当前数组值
				            if(actType.isPrimitive() || "String".equals(actType.getSimpleName())){
				            	for(Element subNode:subNodes){
				            		String elementValue = subNode.getText();
				            		((ArrayList)value).add(elementValue);
				            	}
				            }else{
				            	// 非基本类型的则遍历list中的每个数据对象迭代此方法
				            	for(Element subNode:subNodes){
				            		Object elementValue = parseElement(subNode, actType);
				            		((ArrayList)value).add(elementValue);
				            	}
				            }
						}else{
							logger.info("数组只支持ArrayList并指定泛型:"+type);
							continue;
						}
					}
				} else if (("Map".equals(type.getSimpleName()) || "HashMap".equals(type.getSimpleName())) && type.isAssignableFrom(HashMap.class)){
					logger.info("HashMap节点："+elementName);
					// 获取所有Map节点
					List<Element> subNodes = node.elements(elementName);
					value = Maps.newHashMap();
					if(null!=subNodes && !subNodes.isEmpty()){
						Type gt = field.getGenericType();   //得到泛型类型  
						if(gt instanceof ParameterizedType){
							// 为基本类型的，获取对应值的基本类型
							ParameterizedType pt = (ParameterizedType)gt;  
				            actType = (Class)pt.getActualTypeArguments()[1];
				            // 如果为基本类型直接遍历当前Map,key为标签，value为值
				            if(actType.isPrimitive() || "String".equals(actType.getSimpleName())){
				            	for(Element subNode:subNodes){
				            		List<Element> elementKey = subNode.elements();
				            		if(null!=elementKey && !elementKey.isEmpty()){
				            			for(Element key:elementKey){
				            				((HashMap)value).put(key.getName(), key.getText());
				            			}
				            		}
				            	}
				            }else{
				            	// 非基本类型的则遍历Map中的key为标签，value为每个数据对象迭代此方法
				            	for(Element subNode:subNodes){
				            		Object elementValue = parseElement(subNode, actType);
				            		((HashMap)value).put(subNode.getName(), elementValue);
				            	}
				            }
						}else{
							logger.info("Map只支持HashMap<String, ?>并指定value泛型:"+type);
							continue;
						}
					}				
				} else if (type.isPrimitive() || "String".equals(type.getSimpleName())){
					// 直接获取当前节点的值
					Element el = node.element(elementName);
					if(null!=el){
						value = el.getText();
					}
				} else {
					logger.info("对象引用类型:"+type);
					Element el = node.element(elementName);
					if(null!=el){
						// 引用对象类型节点存在
						value = parseElement(el, type);
					}
				}
				
				logger.info("class["+clazz.getName()+"],属性[" + name + "],值[" + value +"]");
				// 设置值
				method.invoke(obj, value);	           
			}
		} catch (Exception e) {
			logger.error("解析SOAP报文:node["+node.getName()+"]到class["+clazz+"]异常", e);
			throw new BaseException(SysErr.E_DEFAULT, "解析SOAP报文:node["+node.getName()+"]到class["+clazz+"]异常");
		}

		return obj;
	}

	/**
	 * 将一个xml节点的信息解析为一个java实例
	 * 
	 * @param node
	 * @param className
	 */
	public Object parseElement(Element node, String className) {
		
		Class clazz = null;
		Object obj = null;
		try {
			clazz = Class.forName(className);
			obj = clazz.newInstance();
			//20170709 mod by chenyl for 修改成通过封装的反正工具类获取
			Field[] fields = ClassUtil.getAccessibleFields(clazz.newInstance());
			Method[] methods = clazz.getDeclaredMethods();
			for (Method m : methods) {
				if (m.getName().startsWith("set")) {
					try {
						String elementName = m.getName().substring("set".length());
						//强制将elementName字符串的首字符转为大写
						elementName = elementName.substring(0,1).toUpperCase() + elementName.substring(1);
						
						Element currentNode = null;
						String elementType = getType(className, elementName);
						// 当前节点无子节点
						if (elementType.toLowerCase().indexOf("string") != -1) {
							currentNode = node.element(elementName);
							if(null==currentNode){
								continue;
							}
							String elementValue = currentNode.getText();
							System.out.println("elementName: " + elementName + " elementValue: " + elementValue);
							m.invoke(obj, elementValue);
						} // 当前节点含有循环报文子节点
						else if (elementType.toLowerCase().indexOf("loopbody") != -1) {
							List<Object> objList = new ArrayList();
							List<Element> list = node.element("Loop").elements("AddInfo");
							for(int i=0; i<list.size(); i++){
								Element e = list.get(i);
								Object subObj = parseElement(e, elementType);
								objList.add(subObj);
							}
							m.invoke(obj, objList);
							// 当前节点含有子节点
						} else {
							currentNode = node.element(elementName);
							if(null==currentNode){
								continue;
							}
							Object subObj = parseElement(currentNode, elementType);
							m.invoke(obj, subObj);
						}
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		return obj;
	}
	
	/**
	 * 返回错误不抛异常直接返回整个报文
	 * */
	@SuppressWarnings({ "unchecked" })
	public Object callServiceNoException(String tenant, String partId, String serviceCode, Object reqObj, Class<?> resBodyClass,
			HashMap<String, Object> dyncParam) {
		SOAPReqDTO reqDTO = (SOAPReqDTO)reqObj;
		
		logger.info("调用服务：" + serviceCode + " 开始...");
		logger.info("输入参数：tenant[" + tenant + "] , partId[" + partId + "] , serviceCode[" + serviceCode + "] , ReqDTO["
				+ reqDTO + "] , dyncParam[" + dyncParam + "]");
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
		String requestUrl = "";
		// 20180628 add by chenyl for 以防未登陆的获取用户空
		User user = UserUtils.getUser();
		Rent rent = null;
		if(null!=user && null!=user.getOffice()){
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
			if(!DataUtil.isNullStr(tenant) && !DataUtil.isNullStr(partId)){
				// 入参中如果租户、参与者编号不为空，进行微服务框架寻址
				requestUrl = getUrl(seqNo, tenant, partId, serviceCode, dyncParam);
			} else {
				// 如果没有租户则不进行寻址，通过配置文件(serverAddress)获取默认URL
				requestUrl = ParamUtil.getConfig("serverAddress") + "/" + serviceCode;
			}
		}

		PostMethod postMethod = null;
		HttpClient client = null;
		// 获取缓存信息agent启动信息
		HashMap<String, Object> agentMap = (HashMap<String, Object>) CacheUtil.get("MSAgent");
		String token = (String) agentMap.get(MapKey.TOKEN); // 交易令牌
		String reqSvrName = reqDTO.getRequestBody().getClass().getSimpleName(); // 消费方服务名
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
			int responseTimeout = RESPONSE_TIMEOUT; // 默认超时时间
			if(!DataUtil.isNullStr(timeout) && Integer.parseInt(timeout)>0){
				// 服务方设置了超时时时间时使用服务方的超时
				responseTimeout = Integer.parseInt(timeout);
			}
			String charset = ParamUtil.getConfig("soap.charset");
			logger.info("字符集："+charset+",请求地址：" + requestUrl);			
			byte sendMessage[] = buildSoapMsg(reqDTO, serviceCode);			
			String reqMsg = "<?xml version='1.0' encoding='"+charset+"'?>" + new String(sendMessage);
			String isUsed = (String)ParamUtil.getConfig("soap.usenamespace");
			if(!Boolean.parseBoolean(isUsed)){
				//rpc方式
				String rpc = ParamUtil.getConfig("soap.rpc");
				// 默认命名空间
				String nameSpace = ParamUtil.getConfig("soap.namespace");
				String nameSpaceUrl = "xmlns:"+rpc+"=\""+nameSpace+"\"";
				String nameSpaceElementStart = "<"+rpc+":"+serviceCode+">";
				String nameSpaceElementEnd = "</"+rpc+":"+serviceCode+">";
				// 不使用命名空间，把命名空间标签移除掉
				reqMsg = reqMsg.replace(nameSpaceUrl, "");
				reqMsg = reqMsg.replace(nameSpaceElementStart, "");
				reqMsg = reqMsg.replace(nameSpaceElementEnd, "");
			}
			// 消费方服务请求开始
			resBuff = "{}".getBytes("UTF-8");
			try {
				qualityService.requestStart(token, seqNo, reqSvrName, reqVersion, svrTenant, svrPartId, svrPartVersion,
						svrPartInst, svrName, version, Integer.parseInt(timeout), reqMsg.getBytes("UTF-8"));
			} catch (Exception e) {
				logger.info("消费方服务请求开始出现异常，流程继续往下执行...");
			}
			
			logger.info("发送的请求报文：" + reqMsg);
			RequestEntity entity = new StringRequestEntity(reqMsg, "text/xml", charset);
			client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(CONNECT_TIMEOUT);//获取连接超时(3秒)
			client.getHttpConnectionManager().getParams().setSoTimeout(responseTimeout);//获取响应超时 (30秒)
			postMethod = new PostMethod(requestUrl);
			postMethod.setRequestHeader("Content-type", "text/xml; charset="+charset);
			postMethod.setRequestEntity(entity);
			startTime = System.currentTimeMillis();
			int status = client.executeMethod(postMethod);
			String repMsg = "";
			String repSOAP = "";
			logger.info("status：" + status);
			if(HttpStatus.SC_OK==status){//发送成功，状态为200
				repMsg = postMethod.getResponseBodyAsString();
				repSOAP = new String(repMsg.getBytes("ISO-8859-1"),charset);//编码转换
				if (DataUtil.isNullStr(repSOAP)) {
					repSOAP = "";
				}				
				resBuff = repSOAP.getBytes(charset); // 获取质量收集需要的响应报文
				retConnCode = Status.SUCCESS; // 设置通讯状态为成功
				System.out.println("recv: \r\n" + repSOAP);
				logger.info("接收的响应报文： \r\n" + repSOAP);
			}
			endTime = System.currentTimeMillis();
			logger.info("调用远程服务花费时间(单位：毫秒)：" + (endTime - startTime));
			
			// for test
//			String repSOAP = "<?xml version=\"1.0\" encoding=\"utf-8\"?><SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:adtec=\"http://www.adtec.com\"><SOAP-ENV:Header/><SOAP-ENV:Body><adtec:TFmngTranGet><ResponseHeader><VerNo/><ReqSysCd/><ReqSecCd/><TxnTyp/><TxnMod/><TxnCd/><TxnNme/><WhlSeqNo>201806281534470000008429</WhlSeqNo><WhlTm/><ReqDt>20180628</ReqDt><ReqTm>153451</ReqTm><ReqSeqNo>201806281534470000008428</ReqSeqNo><ChnlNo/><BrchNo/><BrchNme/><TlrNo/><AuthTlr/><SndFileNme/><BgnRec>0</BgnRec><MaxRec>0</MaxRec><VTlrNo/><ChkCd/><Checker/><AcctBrch/><BizTyp/><ChkNo/><BootNo/><WinNm/><WinId/><FsysFlg/><FileHMac/><HMac/></ResponseHeader><ResponseBody><AcctNo1>8001432432344234</AcctNo1><Ccy1>CNY</Ccy1><Amt1>100.00</Amt1><List>测试</List><List>测试循环</List></ResponseBody><Fault><FaultCode/><FaultString/><Detail><TxnStat>SUCCESS</TxnStat></Detail></Fault></adtec:TFmngTranGet></SOAP-ENV:Body></SOAP-ENV:Envelope>";
			// 解析soap报文到数据对象中
			GVarContainer.setVar("RESPONSE_BODY_CLASS", resBodyClass);	// 把报文体对象设置到线程缓存中
			res = parseSoapMsg(repSOAP, serviceCode, SOAPResDTO.class);
			// 报文返回码
			SOAPResFaultDTO fault = res.getFault();
			logger.info("SOAP返回码："+fault);
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
			// 移除寻址结果数据
//			GVarContainer.removeVar(seqNo);
			// 消费者调用服务结束
			try{
				// 20180630 add by chenyl for 对应微服务负载均衡测试时进行休眠
				String sleepTime = (String)GVarContainer.getVar("MS_SLEEP_TIME");	// 设置休眠时间,单位秒
				if(!DataUtil.isNullStr(sleepTime)){
					logger.info("微服务负载均衡测试,质量收集结束前休眠(s)："+sleepTime);
					Thread.sleep(Long.parseLong(sleepTime)*1000);
				}
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
	 * 基于微服务框架，根据交易码返回对应的服务url
	 * @param seqNo			全局流水号
	 * @param tenant		服务所属租户
	 * @param partId		服务所属参与者ID
	 * @param svcCode		服务码
	 * @param dyncParam		动态参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getUrl(String seqNo, String tenant, String partId, String svcCode, HashMap<String, Object> dyncParam){
		String url = "";

		HashMap<String, Object> agentMap = (HashMap<String, Object>)CacheUtil.get("MSAgent"); // 获取缓存信息agent启动信息
		String token = (String) agentMap.get(MapKey.TOKEN);		//交易令牌
		if(null!=tenant && tenant.isEmpty()){
			tenant = TENANT;
		}
		if(null!=partId && partId.isEmpty()){
			partId = PARTID;
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
		
		url = url + "/" + svcCode;
		logger.info("url："+url);
		// 设置寻址结果外部调用
		GVarContainer.setVar(seqNo, retMap);

		return url;
	}
	
}
