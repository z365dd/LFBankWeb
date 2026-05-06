package com.adtec.comp.tseq.util;

import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.time.FastDateFormat;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.dto.head.AppHeadReqDTO;
import com.adtec.comm.dto.head.LocalHeadReqDTO;
import com.adtec.comm.dto.head.SysHeadReqDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.comp.tseq.dto.TSeqTranGetSeqReqDTO;
import com.adtec.comp.tseq.dto.TSeqTranGetSeqResDTO;
import com.adtec.framework.common.util.GVarContainer;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.ms.msagent.service.AddressingService;
import com.adtec.ms.msagent.util.MapKey;
import com.adtec.ms.msagent.util.Status;
import com.adtec.starring_comp.file.util.CacheUtils;
import com.alibaba.fastjson.JSONObject;

/**
 * 系统名称: 金融交易云<br>
 * 模块名称: 公共框架<br>
 * 类名称: SeqUtil<br>
 * 功能说明: 获取金融交易云平台外部流水号工具（流水号组件二级缓存）<br>
 * 软件版权: 北京先进数通信息技术股份公司<br>
 * 开发人员: linyx@ <br>
 * 开发时间: 2020年8月05日 上午14:30:00<br>
 * 系统版本: V1.00.001<br>
 * 修改记录:<br>
 * 修改日期 修改人员 修改说明 <br>
 * ========== ======= ===========================================<br>
 * 2020-08-05 文成名 创建<br>
 */
public class SeqTran {
	private final static String SVC_NAME_TSEQ_GETSEQLIST = "TSeqTranGetSeqList";
	private final static String KEY_PLAT_DATE = "platDate";
	private final static String KEY_MOD_DT = "modDt";
	public final static String KEY_SEQ_NO_LIST = "seqNoList";
	private final static String KEY_LOCK = "lock";
	private final static String DIGIT_FLG_YES = "Y"; // 是否纯数字：Y-是 N-否
	private final static String DIGIT_FLG_NO = "N"; // 是否纯数字：Y-是 N-否
	private final static String EFFT_FLG_YES = "Y"; // 有效标志 Y-是
	public final static int NUM = 2;// 节点数，本地缓存为请求流水号组件获取流水而分配的节点，每个节点存放获取的流水号列表

	/**
	 * 1.HashMap<String,HashMap<String,Object>>缓存结构 字段 类型 中文名称 值 key
	 * HashMap<String,Object> HashMap<String,Object>
	 * 1.1HashMap<String,Object>缓存结构 字段 类型 中文名称 seqNoList List<String> 流水号列表
	 * lock ReentrantLock 锁资源
	 */
	private static ConcurrentHashMap<String, ConcurrentHashMap<String, Object>> seqCrtMapList = new ConcurrentHashMap<>();
	private static Lock initlock = new ReentrantLock();
	

	/**
	 * 获取流水号<br>
	 * 根据传入的流水号生成器id获取对应的流水号
	 * 
	 * @param seqCrtID
	 * @return 返回对应格式的流水号
	 */
	public static String getOutSysSeq(String seqCrtID) throws Exception {
		System.out.println("获取流水号开始");
		// 1、初始化
		ConcurrentHashMap<String, Object> seqCrtMap = seqCrtMapList.get(seqCrtID);
		if (seqCrtMap == null) {
			if (initlock.tryLock(10, TimeUnit.SECONDS)) {
				try {// 初始化本地缓存
					seqCrtMap = seqCrtMapList.get(seqCrtID);
					if (seqCrtMap == null) {
						System.out.println("初始Map List");
						initSeqCrtMapList(seqCrtID);
						seqCrtMap = seqCrtMapList.get(seqCrtID);
					}
				} catch (Exception e) {
					throw e;
				} finally {
					initlock.unlock();
				}
			} else {
				throw new Exception("initlock.tryLock fail");
			}

		}

		// 2、随机取本地缓存节点
		System.out.println("获取锁开始");
		SecureRandom random = new SecureRandom();
		int ranNum = (int) (1 + random.nextDouble() * (NUM - 1 + 1));
		@SuppressWarnings("unchecked")
		ConcurrentHashMap<String, Object> tmpMap = (ConcurrentHashMap<String, Object>) seqCrtMap.get(ranNum + "");

		String outSysSeq = "";

		String modDt ="";
		try {
			// 获取流水号
			@SuppressWarnings("unchecked")
			ConcurrentLinkedQueue<String> seqNoList = (ConcurrentLinkedQueue<String>) tmpMap.get(KEY_SEQ_NO_LIST);
			 // 从redis缓存中获取流水规则信息 检查流水号规则修改日期与缓存中日期做匹配 大于缓存日期则更新缓存
			 String redisKey="/PARAM_DATA/ParaTseqSeqCrt/"+seqCrtID;
			List<RedisDataDO> redisDataList =redisUtil.getData(redisKey);
			System.out.println("redisDataList.size():"+redisDataList.size());
			if(redisDataList.size()==0){
				throw new Exception("缓存中不存在流水["+seqCrtID+"]信息,请检查配置");
			}
			for(int i=0;i<redisDataList.size();i++){
				if("MOD_DT".equals(redisDataList.get(i).getKey())){
					System.out.println("redisDataList.get(i)="+redisDataList.get(i).getObject());
					modDt=(String) redisDataList.get(i).getObject();
					break;
				}
			}
			System.out.println("缓存时间modDt="+modDt+"平台时间platDate="+getPlatDate());
			System.out.println("tmpMap.get(KEY_PLAT_DATE)="+tmpMap.get(KEY_PLAT_DATE)+"tmpMap.get(KEY_MOD_DT)="+tmpMap.get(KEY_MOD_DT));
			 if(seqNoList.size() == 0 ||!(getPlatDate().equals(tmpMap.get(KEY_PLAT_DATE)))||checkDate(modDt,(String) tmpMap.get(KEY_MOD_DT))){//获取新一批流水号
				System.out.println("获取新一批流水号开始");
				if (!seqNoList.isEmpty()) {
					seqNoList.clear();// 当队列中有数据时，代表流水日期与当天平台日期不一致，需要抛弃旧流水数据
				}
				callTSeqGetSeqList(seqCrtID, ranNum);// 向流水号组件申请一批流水号
				System.out.println("获取新一批流水号成功");
			}
			// 取一个本地流水号返回
			outSysSeq = seqNoList.poll();

		} catch (BaseException e) {
			//流水号用尽，则取本地流水返回  50020034为流水用尽错误码
			System.out.println("缓存数据错误信息:"+e.getErrorCode()+".."+e.getErrorDesc());
			if (e.getErrorDesc().contains("50020034")) {
				String otherSeq = "";
				for(int i=0;i<SeqTran.NUM;i++){
					@SuppressWarnings("unchecked")
					ConcurrentHashMap<String,Object> tempMap = (ConcurrentHashMap<String, Object>) seqCrtMap.get((i+1)+"");
					@SuppressWarnings("unchecked")
					ConcurrentLinkedQueue<String> seqNoList = (ConcurrentLinkedQueue<String>) tempMap.get(SeqTran.KEY_SEQ_NO_LIST);
					if (seqNoList.size() == 0||!(getPlatDate().equals(tmpMap.get(KEY_PLAT_DATE)))||checkDate(modDt,(String) tempMap.get(KEY_MOD_DT))) {
						if (!seqNoList.isEmpty()) {
							seqNoList.clear();// 当队列中有数据时，代表流水日期与当天平台日期不一致，需要抛弃旧流水数据
						}
						continue;
					} else {
						otherSeq = seqNoList.poll();
						break;
					}
				}
				if (DataUtil.isNullStr(otherSeq)) {
					throw e;
				} else {
					return otherSeq;
				}
			} else {
				throw e;
			}
		} catch (Exception e) {
			throw e;
		}

		System.out.println("获取流水号成功[" + seqCrtID + "][" + outSysSeq + "]");
		// 3、返回流水号
		return outSysSeq;
	}

	/**
	 * 初始化本地缓存的流水号节点
	 * 
	 * @param seqCrtID
	 */
	private static void initSeqCrtMapList(String seqCrtID) {
		ConcurrentHashMap<String, Object> seqCrtMap = new ConcurrentHashMap<String, Object>();
		for (int i = 0; i < NUM; i++) {
			ConcurrentHashMap<String, Object> tmpMap = new ConcurrentHashMap<String, Object>();
			tmpMap.put(KEY_PLAT_DATE, getPlatDate());
			tmpMap.put(KEY_MOD_DT, "");
			tmpMap.put(KEY_SEQ_NO_LIST, new ConcurrentLinkedQueue<>());
			tmpMap.put(KEY_LOCK, new ReentrantLock());
			tmpMap.put("SER", (i + 1));
			seqCrtMap.put((i + 1) + "", tmpMap);

		}
		seqCrtMapList.put(seqCrtID, seqCrtMap);
	}

	/**
	 * 调用流水号组件获取一批流水号并装载到本地缓存中
	 * 
	 * @param seqCrtID：流水号生成器id
	 * @param num：流水节点号
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private static void callTSeqGetSeqList(String seqCrtID, int num) throws Exception {
		TSeqTranGetSeqReqDTO reqBody = new TSeqTranGetSeqReqDTO();
		reqBody.setSEQ_CRT_ID(seqCrtID);
		ReqDTO req = new ReqDTO(reqBody);
		String comp_no = ParamUtil.getConfig("FLOW_PARTID");
		TseqUtil.setReqHead(req, "TSeqTranGetSeqList");
		req.getSYS_HEAD().setSND_COMP_NO(comp_no); // TODO 流水号组件用于测试用
		HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
		ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("FLOW_PARTID"),
				"TSeqTranGetSeqList", req, TSeqTranGetSeqResDTO.class, null);
		TSeqTranGetSeqResDTO resBody = new TSeqTranGetSeqResDTO();
		if (resDTO.getBODY() != null) {
			resBody = (TSeqTranGetSeqResDTO) resDTO.getBODY();
		}

		/* 组织报文 */
		// System.out.println("组织报文开始");
		// ReqDTO req = new ReqDTO();
		// String reqJsonData = packReqJsonData(req,seqCrtID);
		// System.out.println("reqJsonData["+reqJsonData+"]");
		// System.out.println("组织报文结束");
		//
		// /*通讯*/
		// //通过微服务获取url
		// System.out.println("通过微服务获取url开始");
		// //String url=getUrl(req.getSYS_HEAD().getREQ_SEQ(),
		// ParamUtil.getConfig("Tenant"), ParamUtil.getConfig("FLOW_PARTID"),
		// SVC_NAME_TSEQ_GETSEQLIST, null);
		// String
		// if ( url.indexOf("?") != -1 ) {
		// url = url + "&REQ_SEQ=" +req.getSYS_HEAD().getREQ_SEQ();
		// } else {
		// url = url + "?REQ_SEQ=" + req.getSYS_HEAD().getREQ_SEQ();
		// }
		// System.out.println("url["+url+"]");
		// System.out.println("通过微服务获取url结束");
		// //发起通讯
		// System.out.println("发起通讯开始");
		// String respJsonData = "";
		// respJsonData = doHttpClientPost(url,reqJsonData);
		// System.out.println("respJsonData["+respJsonData+"]");
		// System.out.println("发起通讯结束");

		/* 解析报文到seqNoList */
		System.out.println("解析报文开始");
		ConcurrentHashMap<String, Object> seqCrtMap = seqCrtMapList.get(seqCrtID);
		ConcurrentHashMap<String, Object> tmpMap = (ConcurrentHashMap<String, Object>) seqCrtMap.get(num + "");
		ConcurrentLinkedQueue<String> seqNoList = (ConcurrentLinkedQueue<String>) tmpMap.get(KEY_SEQ_NO_LIST);
		// 解析返回报文
		System.out.println("seqCrtMap[" + seqCrtMap + "]");
		unpackRespJsonData(resBody, seqNoList, tmpMap);
		System.out.println("解析报文结束");

	}

	/**
	 * post请求
	 * 
	 * @param url
	 * @param json
	 * @return
	 * @throws Exception
	 */
	private static String doHttpClientPost(String url, String json) throws Exception {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		String response = null;
		HttpResponse res = null;
		try {
			StringEntity s = new StringEntity(json, Charset.forName("UTF-8"));
			post.addHeader("Content-Type", "application/json; charset=UTF-8");
			post.setHeader("Accept", "aplication/json");
			post.setEntity(s);
			res = httpclient.execute(post);
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				response = EntityUtils.toString(res.getEntity());// 返回json格式：
				response = new String(response.getBytes("ISO-8859-1"), "UTF-8");// httpClient默认编码为ISO-8859-1
			}
		} catch (Exception e) {
			System.out.println("doHttpClientPost通讯异常：" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			if (httpclient != null)
				httpclient.close();

		}
		return response;
	}

	private static String packReqJsonData(ReqDTO req, String seqCrtID) throws Exception {
		setReqHead(req);

		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(req);
		String starringJson = jsonObject.toString();
		System.out.println("starringJson:" + starringJson);

		// 转换为JSONObject
		// JSONObject jsonObject=JSONObject.parseObject(starringJson);
		// //可以将json格式的字符串变成json对象
		// JSONObject rootJSONObject= (JSONObject)
		// jsonObject.get("STARRING_REQ");
		// starringJson = rootJSONObject.toJSONString();
		//
		jsonObject = JSONObject.parseObject(starringJson); // 将json格式的字符串变成json对象
		jsonObject.put("BODY", new JSONObject());
		JSONObject bodyJSONObject = (JSONObject) jsonObject.get("BODY");
		bodyJSONObject.put("SEQ_CRT_ID", seqCrtID);

		return jsonObject.toJSONString();
	}

	/**
	 * 公共报文头赋值<br>
	 * 注意：所有应用代码在调用接出点服务前必须执行此方法
	 * 
	 * @return 返回公共报文头对象，调用者可进一步赋值
	 * @throws Exception
	 */
	public static void setReqHead(ReqDTO req) throws Exception {
		// 系统头--TODO默认使用999000
		String seqNo = PlatSeq.getSeq();
		SysHeadReqDTO sysHead = new SysHeadReqDTO();
		sysHead.setREQ_SEQ(getNowDate() + seqNo); // 服务请求发送方流水号
		sysHead.setSND_SEQ(getNowDate() + seqNo);// 内部全局流水号
		sysHead.setREQ_DATE(getNowDate());
		sysHead.setREQ_COMP_NO("999888"); // 请求组件/模型编号
		sysHead.setREQ_SEQ(getNowDate() + seqNo); // 服务请求发送方流水号
		sysHead.setSND_SEQ(getNowDate() + seqNo);// 内部全局流水号
		sysHead.setREQ_NODE_NO("00000");
		sysHead.setREQ_IP("");
		sysHead.setMACH_DATE(getNowDate());
		sysHead.setREQ_SVC_CODE(SVC_NAME_TSEQ_GETSEQLIST);
		req.setSYS_HEAD(sysHead);

		// 应用头
		AppHeadReqDTO appHead = new AppHeadReqDTO();
		appHead.setBRCH_NO("00000");
		appHead.setTLR_NO("0000000");
		appHead.setTX_DATE(getNowDate());// 交易日期
		appHead.setTX_TIME(getTime());// 交易时间
		appHead.setTRAN_DATE(getNowDate());// 交易日期
		appHead.setTRAN_TIME(getTime());// 交易时间
		appHead.setREQ_SEQ(getNowDate() + seqNo);
		req.setAPP_HEAD(appHead);

		// 本地扩展头
		LocalHeadReqDTO localHead = new LocalHeadReqDTO();
		localHead.setCHNL_NO("000001");
		localHead.setBUSI_NO("");
		localHead.setLEGA_NO("0000");
		localHead.setCHNL_SEQ_NO(getNowDate() + seqNo);
		localHead.setTNT_NO("0000");
		req.setLOCAL_HEAD(localHead);

	}

//	/**
//	 * 获取本机IP<br>
//	 * 从运行的机器参数中获取机器IP,即ESAdmin.xml机器ip
//	 *
//	 * @return 本机IP xxx.xxx.xxx.xxx
//	 */
//	public static String getLocalIp() {
//
//		return "127.0.0.1";
//	}

	/**
	 * 解析流水号组件返回报文
	 * 
	 * @param resBody
	 * @param seqNoList
	 * @return
	 * @throws Exception
	 */
	private static String unpackRespJsonData(TSeqTranGetSeqResDTO resBody, ConcurrentLinkedQueue<String> seqNoList,
			ConcurrentHashMap<String, Object> tempMap) throws Exception {

		// 转换为JSONObject
		// JSONObject jsonObject=JSONObject.parseObject(respJsonData);
		// //可以将json格式的字符串变成json对象
		// JSONObject sysheadJSONObject= (JSONObject)
		// jsonObject.get("SYS_HEAD");
		// JSONArray tranRet = (JSONArray) sysheadJSONObject.get("TRAN_RET");
		// String tranStat= (String) sysheadJSONObject.get("TRAN_STAT");
		// JSONObject tranRetZore = (JSONObject)tranRet.get(0);
		// String retMsg = "";
		// if ( tranRetZore != null ) {
		// retMsg = (String)tranRetZore.get("RET_MSG");
		// }
		// if(!"S".equals(tranStat)){
		// throw new Exception(retMsg);
		// }
		//
		// //赋值到流水生成器对应seqNoList中
		// JSONObject bodyJSONObject= (JSONObject) jsonObject.get("BODY");

		String strSeq = resBody.getSTR_SEQ();
		String endSeq = resBody.getEND_SEQ();
		String seqNodeNo = resBody.getSEQ_NODE_NO();
		int seqLen = (int) resBody.getSEQ_LEN();
		String digitFlg = resBody.getDIGIT_FLG();
		String efftFlg = resBody.getEFFT_FLG();
		String starExpr = resBody.getSTAR_EXPR();
		String tranDate = resBody.getTRAN_DATE();
		String modDt = resBody.getMOD_DT();

		tempMap.put(KEY_PLAT_DATE, tranDate);
		tempMap.put(KEY_MOD_DT, modDt);

		if (EFFT_FLG_YES.equals(efftFlg)) {
			if (isNullOrEmpty(starExpr)) {
				throw new Exception("流水号组件返回日期表达式为空");
			}
			String platDate = getPlatDate();
			String currTime = getCurTime();
			if (starExpr.indexOf("YYYY") != -1)
				starExpr = starExpr.replace("YYYY", platDate.substring(0, 4));
			if (starExpr.indexOf("yyyy") != -1)
				starExpr = starExpr.replace("yyyy", platDate.substring(0, 4));
			if (starExpr.indexOf("YY") != -1)
				starExpr = starExpr.replace("YY", platDate.substring(2, 4));
			if (starExpr.indexOf("yy") != -1)
				starExpr = starExpr.replace("yy", platDate.substring(2, 4));
			if (starExpr.indexOf("MM") != -1)
				starExpr = starExpr.replace("MM", platDate.substring(4, 6));
			if (starExpr.indexOf("DD") != -1)
				starExpr = starExpr.replace("DD", platDate.substring(6, 8));
			if (starExpr.indexOf("hh") != -1)
				starExpr = starExpr.replace("hh", currTime.substring(0, 2));
			if (starExpr.indexOf("mm") != -1)
				starExpr = starExpr.replace("mm", currTime.substring(2, 4));
			if (starExpr.indexOf("ss") != -1)
				starExpr = starExpr.replace("ss", currTime.substring(4, 6));
		}

		int length = strSeq.trim().length();

		// 计算
		if (DIGIT_FLG_YES.equals(digitFlg)) {// 纯数字计算
			long lStrSeq = Long.parseLong(strSeq);
			long lEndSeq = Long.parseLong(endSeq);
			String tmpSeqExpr = "";
			while (lStrSeq <= lEndSeq) {
				String format = "%0" + length + "d";
				String tmpSeq = seqNodeNo + String.format(format, lStrSeq);// 不足原字符串长度不足前面的0

				if (EFFT_FLG_YES.equals(efftFlg))
					tmpSeqExpr = starExpr.replace("SEQ", tmpSeq);
				else
					tmpSeqExpr = tmpSeq;

				seqNoList.add(tmpSeqExpr);
				lStrSeq += seqLen;
			}
		} else if (DIGIT_FLG_NO.equals(digitFlg)) {// 非纯数字计算
			long lStrSeq = stringToNumeric(strSeq, 32);
			long lEndSeq = stringToNumeric(endSeq, 32);
			String tmpSeqExpr = "";
			while (lStrSeq <= lEndSeq) {
				String tmpStr = numericToString(lStrSeq, 32);
				if (tmpStr.length() < length) {// 不足原字符串长度不足前面的0
					String format = "%0" + (length - tmpStr.length()) + "d";
					String tmpZero = String.format(format, 0);
					tmpStr = tmpZero + tmpStr;
				}
				String tmpSeq = seqNodeNo + tmpStr;
				if (EFFT_FLG_YES.equals(efftFlg))
					tmpSeqExpr = starExpr.replace("SEQ", tmpSeq);
				else
					tmpSeqExpr = tmpSeq;
				seqNoList.add(tmpSeqExpr);
				lStrSeq += seqLen;
			}
		} else {
			throw new Exception("不支持该流水号类型");
		}
		return tranDate;

	}

	/** 这里在转换32进制时，没有把 I、O、S、Z 作为转换字母 */
	private final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
			'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'T', 'U', 'V', 'W', 'X', 'Y' };

	/**
	 * 将十进制的数字转换为指定进制的字符串。
	 * 
	 * @param i
	 *            十进制的数字
	 * @param radix
	 *            指定的进制
	 * @return 转换后的字符串
	 */
	private static String numericToString(long i, int radix) {
		long num = 0;
		if (i < 0) {
			num = ((long) 2 * 0x7fffffff) + i + 2;
		} else {
			num = i;
		}
		char[] buf = new char[32];
		int charPos = 32;
		while ((num / radix) > 0) {
			buf[--charPos] = digits[(int) (num % radix)];
			num /= radix;
		}
		buf[--charPos] = digits[(int) (num % radix)];
		return new String(buf, charPos, (32 - charPos));
	}

	/**
	 * 将其它进制的数字（字符串形式）转换为十进制的数字
	 * 
	 * @param s
	 *            其它进制的数字（字符串形式）
	 * @param radix
	 *            指定的进制
	 * @return 转换后的数字
	 */
	private static long stringToNumeric(String s, int radix) {
		char[] buf = new char[s.length()];
		s.getChars(0, s.length(), buf, 0);
		long num = 0;
		for (int i = 0; i < buf.length; i++) {
			for (int j = 0; j < digits.length; j++) {
				if (digits[j] == buf[i]) {
					num += j * Math.pow(radix, buf.length - i - 1);
					break;
				}
			}
		}
		return num;
	}

	/**
	 * 获取缓存流水信息<br>
	 * 
	 * @return 缓存流水信息
	 */
	public static ConcurrentHashMap<String, ConcurrentHashMap<String, Object>> getSeqCrtMapList() {
		return seqCrtMapList;
	}

	/**
	 * 取平台日期<br>
	 * 
	 * @return 平台日期yyyyMMdd
	 */
	public static String getPlatDate() {
		List<RedisDataDO> redisDataList =redisUtil.getData("ParaDay");
		System.out.println("redisDataList.size():"+redisDataList.size());
		String platDate="";
		if (redisDataList.size() == 0) {
			return getNowDate();// 当缓存上没有日期时，返回机器日期
		}else{
			for(int i=0;i<redisDataList.size();i++){
				if("PLAT_DATE".equals(redisDataList.get(i).getKey())){
					platDate=(String) redisDataList.get(i).getObject();
					break;
				}
			}
		}
		return platDate;
	}

	/**
	 * 比较两个字符串的时间大小
	 * 
	 * @param beginData
	 * @param endDate
	 */
	public static boolean checkDate(String beginData, String endDate) {
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date start = null;
		Date end = null;
		try {
			start = format.parse(beginData);
			end = format.parse(endDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return start.after(end);
	}

	public static boolean isNullOrEmpty(String strValue) {
		if ((strValue == null) || (strValue.equals(""))) {
			return true;
		}
		return false;
	}

	public static String getNowDate() {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String nowDate = sdf.format(now);
		return nowDate;
	}

	public static String getCurTime() {
		FastDateFormat ISO_TIME_FORMAT_MIL = FastDateFormat.getInstance("HH:mm:ss.SS");
		return ISO_TIME_FORMAT_MIL.format(new Date());
	}

	public static String getTime() {
		FastDateFormat ISO_TIME_FORMAT_MIL = FastDateFormat.getInstance("HH:mm:ss");
		return ISO_TIME_FORMAT_MIL.format(new Date());
	}

	/**
	 * 基于微服务框架，根据交易码返回对应的服务url
	 * 
	 * @param seqNo
	 *            全局流水号
	 * @param tenant
	 *            服务所属租户
	 * @param partId
	 *            服务所属参与者ID
	 * @param svcCode
	 *            服务码
	 * @param dyncParam
	 *            动态参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getUrl(String seqNo, String tenant, String partId, String svcCode,
			HashMap<String, Object> dyncParam) {
		String url = "";

		HashMap<String, Object> agentMap = (HashMap<String, Object>) CacheUtils.get("MSAgent"); // 获取缓存信息agent启动信息
		if (null == agentMap) {
			throw new BaseException(SysErr.E_MESSAGE, "服务寻址失败,本地缓存中没有微服务登录认证信息！");
		}
		String token = (String) agentMap.get(MapKey.TOKEN); // 交易令牌
		if (null != tenant && tenant.isEmpty()) {
			tenant = ParamUtil.getConfig("Tenant");
		}
		if (null != partId && partId.isEmpty()) {
			partId = ParamUtil.getConfig("PartId");
		}
		AddressingService addressingService = (AddressingService) SpringContextHolder.getBean("addressingService");
		HashMap<String, Object> retMap = addressingService.getSvrAddr(seqNo, token, tenant, partId, svcCode, dyncParam);
		System.out.println("服务寻址结果 retMap=" + retMap.toString());
		String retCode = (String) retMap.get(MapKey.RETCODE);
		String msg = (String) retMap.get(MapKey.MSG);
		if (null != retCode && !Status.SUCCESS.equals(retCode)) {
			System.out.println("服务寻址失败！" + msg);
			throw new BaseException(SysErr.E_MESSAGE, "服务寻址失败！" + msg);
		}

		com.alibaba.fastjson.JSONObject comHttp = (com.alibaba.fastjson.JSONObject) retMap.get("COM_HTTP");
		url = (String) comHttp.get("URL");

		url = url + "/" + svcCode;
		System.out.println("url：" + url);
		// 设置寻址结果外部调用
		GVarContainer.setVar(seqNo, retMap);

		return url;
	}


	
}
