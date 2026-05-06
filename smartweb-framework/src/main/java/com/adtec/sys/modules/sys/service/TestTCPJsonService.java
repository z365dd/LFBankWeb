/**
 * 系统名称: SmartWeb平台
 * 模块名称: 
 * 类  名  称: TestTCPJsonService.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2018年3月30日 上午11:45:30<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.sys.modules.sys.service;

import java.util.HashMap;

import org.apache.curator.shaded.com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.adtec.comm.protocol.tcp.TCPRetMap;

/**
 * @author chenyl
 *
 */
@Service
public class TestTCPJsonService {
	/**
	 * 日志对象
	 */
	protected final static Logger logger = LoggerFactory.getLogger(TestTCPJsonService.class);
	
	public HashMap<String, Object> testService(HashMap reqData){
		TCPRetMap retMap = new TCPRetMap();
		HashMap<String, Object> dataMap = Maps.newHashMap();
		logger.info("收到的请求报文："+reqData);
		dataMap.put("Amt","100.00");
		dataMap.put("Name", "Hello "+reqData.get("Name")+" 欢迎来到 Smartweb！");
		retMap.setSuccess(true);
		retMap.setMsg("交易成功");
		retMap.setData(dataMap);
		return retMap.toMap();
	}
}
