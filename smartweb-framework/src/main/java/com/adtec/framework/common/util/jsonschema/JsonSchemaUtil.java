/**
 * 系统名称: SmartWeb平台
 * 模块名称: JsonSchema工具类
 * 类  名  称: JsonSchemaUtil.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2019年10月5日 上午10:06:10<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.framework.common.util.jsonschema;

public class JsonSchemaUtil {
	/**
	 * 金融交易云报文：SYS_HEAD-系统头
	 */
	public static final String SYS_HEAD = "SYS_HEAD";
	/**
	 * 金融交易云报文：APP_HEAD-应用头
	 */
	public static final String APP_HEAD = "APP_HEAD";
	/**
	 * 金融交易云报文：LOCAL_HEAD-扩展头
	 */
	public static final String LOCAL_HEAD = "LOCAL_HEAD";
	/**
	 * 金融交易云报文：BODY-报文体
	 */
	public static final String BODY = "BODY";
	
	/**
	 * 创建金融交易云请求报文模板
	 * @param reqBodySchema
	 * @return
	 */
	public static JsonSchemaDO getReqDTOSchema(JsonSchemaPropertyDO reqBodySchema) {
		// TODO Auto-generated method stub
		JsonSchemaDO reqSchema = new JsonSchemaDO();
		reqSchema.setTitle("Http Json Request DTO");	// 设置该节点的标题
		reqSchema.setTitle("金融交易云请求报文模板");
		// 设置请求报文必输检查字段
		reqSchema.setRequiredKey(SYS_HEAD);
		reqSchema.setRequiredKey(APP_HEAD);
		reqSchema.setRequiredKey(LOCAL_HEAD);
		if(null!=reqBodySchema && !reqBodySchema.getRequired().isEmpty()){
			// 报文体不为空时设置为必输字段
			reqSchema.setRequiredKey(BODY);
		}
		// 设置报文体模板
		reqSchema.setKeyProperties(BODY, reqBodySchema);
		
		/**----------------------------------设置SYS_HEAD属性----------------------------------------------------**/
		String sysHead$id = JsonSchemaKeyType.$ID_URI+JsonSchemaKeyType.PROPERTIES_URI+SYS_HEAD;
		JsonSchemaPropertyDO sysHeadProperties = new JsonSchemaPropertyDO(sysHead$id, JsonSchemaKeyType.TYPE_OBJECT);
		sysHeadProperties.setTitle("SYS_HEAD NODE");
		sysHeadProperties.setDescription("请求报文系统头模板");
		reqSchema.setKeyProperties(SYS_HEAD, sysHeadProperties);
		// REQ_LOG_SEQ-请求日志流水号
		JsonSchemaPropertyDO sysREQ_LOG_SEQ = new JsonSchemaPropertyDO(sysHead$id+JsonSchemaKeyType.PROPERTIES_URI+"REQ_LOG_SEQ", JsonSchemaKeyType.TYPE_STRING);
		sysREQ_LOG_SEQ.setTitle("REQ_LOG_SEQ NODE");
		sysREQ_LOG_SEQ.setDescription("请求日志流水号");
		sysHeadProperties.setKeyProperties("REQ_LOG_SEQ", sysREQ_LOG_SEQ);
		sysHeadProperties.setRequiredKey("REQ_LOG_SEQ");
		
		// REQ_COMP_NO-请求系统编号
		JsonSchemaPropertyDO sysREQ_COMP_NO = new JsonSchemaPropertyDO(sysHead$id + JsonSchemaKeyType.PROPERTIES_URI + "REQ_COMP_NO", JsonSchemaKeyType.TYPE_STRING);
		sysREQ_COMP_NO.setTitle("REQ_COMP_NO NODE");
		sysREQ_COMP_NO.setDescription("请求系统编号");
		sysHeadProperties.setKeyProperties("REQ_COMP_NO", sysREQ_COMP_NO);
		sysHeadProperties.setRequiredKey("REQ_COMP_NO");	
		
		// REQ_NODE_NO-请求系统节点号
		JsonSchemaPropertyDO sysREQ_NODE_NO = new JsonSchemaPropertyDO(sysHead$id + JsonSchemaKeyType.PROPERTIES_URI + "REQ_NODE_NO", JsonSchemaKeyType.TYPE_STRING);
		sysREQ_NODE_NO.setTitle("REQ_NODE_NO NODE");
		sysREQ_NODE_NO.setDescription("请求系统节点号");
		sysHeadProperties.setKeyProperties("REQ_NODE_NO", sysREQ_NODE_NO);
		sysHeadProperties.setRequiredKey("REQ_NODE_NO");
		
		// REQ_IP-请求方IP
		JsonSchemaPropertyDO sysREQ_IP = new JsonSchemaPropertyDO(sysHead$id + JsonSchemaKeyType.PROPERTIES_URI + "REQ_IP", JsonSchemaKeyType.TYPE_STRING);
		sysREQ_IP.setTitle("REQ_IP NODE");
		sysREQ_IP.setDescription("请求方IP");
		sysHeadProperties.setKeyProperties("REQ_IP", sysREQ_IP);
		sysHeadProperties.setRequiredKey("REQ_IP");	

		// REQ_SVC_CODE-服务请求方服务码
		JsonSchemaPropertyDO sysREQ_SVC_CODE = new JsonSchemaPropertyDO(sysHead$id + JsonSchemaKeyType.PROPERTIES_URI + "REQ_SVC_CODE", JsonSchemaKeyType.TYPE_STRING);
		sysREQ_SVC_CODE.setTitle("REQ_SVC_CODE NODE");
		sysREQ_SVC_CODE.setDescription("服务请求方服务码");
		sysHeadProperties.setKeyProperties("REQ_SVC_CODE", sysREQ_SVC_CODE);
		sysHeadProperties.setRequiredKey("REQ_SVC_CODE");	
		
		// REQ_DATE-服务请求方日期
		JsonSchemaPropertyDO sysREQ_DATE = new JsonSchemaPropertyDO(sysHead$id + JsonSchemaKeyType.PROPERTIES_URI + "REQ_DATE", JsonSchemaKeyType.TYPE_STRING);
		sysREQ_DATE.setTitle("REQ_DATE NODE");
		sysREQ_DATE.setDescription("服务请求方日期");
		sysHeadProperties.setKeyProperties("REQ_DATE", sysREQ_DATE);
		sysHeadProperties.setRequiredKey("REQ_DATE");	
		
		// REQ_SEQ-服务请求发送方流水号
		JsonSchemaPropertyDO sysREQ_SEQ = new JsonSchemaPropertyDO(sysHead$id + JsonSchemaKeyType.PROPERTIES_URI + "REQ_SEQ", JsonSchemaKeyType.TYPE_STRING);
		sysREQ_SEQ.setTitle("REQ_SEQ NODE");
		sysREQ_SEQ.setDescription("服务请求发送方流水号");
		sysHeadProperties.setKeyProperties("REQ_SEQ", sysREQ_SEQ);
		sysHeadProperties.setRequiredKey("REQ_SEQ");	
		
		// SND_COMP_NO-源发起方系统编号
		JsonSchemaPropertyDO sysSND_COMP_NO = new JsonSchemaPropertyDO(sysHead$id + JsonSchemaKeyType.PROPERTIES_URI + "SND_COMP_NO", JsonSchemaKeyType.TYPE_STRING);
		sysSND_COMP_NO.setTitle("SND_COMP_NO NODE");
		sysSND_COMP_NO.setDescription("源发起方系统编号");
		sysHeadProperties.setKeyProperties("SND_COMP_NO", sysSND_COMP_NO);
		sysHeadProperties.setRequiredKey("SND_COMP_NO");	
		
		// SND_NODE_NO-源发起方系统节点号
		JsonSchemaPropertyDO sysSND_NODE_NO = new JsonSchemaPropertyDO(sysHead$id + JsonSchemaKeyType.PROPERTIES_URI + "SND_NODE_NO", JsonSchemaKeyType.TYPE_STRING);
		sysSND_NODE_NO.setTitle("SND_NODE_NO NODE");
		sysSND_NODE_NO.setDescription("源发起方系统节点号");
		sysHeadProperties.setKeyProperties("SND_NODE_NO", sysSND_NODE_NO);
		sysHeadProperties.setRequiredKey("SND_NODE_NO");
		
		// SND_DATE-源发送方交易日期
		JsonSchemaPropertyDO sysSND_DATE = new JsonSchemaPropertyDO(sysHead$id + JsonSchemaKeyType.PROPERTIES_URI + "SND_DATE", JsonSchemaKeyType.TYPE_STRING);
		sysSND_DATE.setTitle("SND_DATE NODE");
		sysSND_DATE.setDescription("源发送方交易日期");
		sysHeadProperties.setKeyProperties("SND_DATE", sysSND_DATE);
		sysHeadProperties.setRequiredKey("SND_DATE");	
		
		// SND_SEQ-源发起方流水号
		JsonSchemaPropertyDO sysSND_SEQ = new JsonSchemaPropertyDO(sysHead$id + JsonSchemaKeyType.PROPERTIES_URI + "SND_SEQ", JsonSchemaKeyType.TYPE_STRING);
		sysSND_SEQ.setTitle("SND_SEQ NODE");
		sysSND_SEQ.setDescription("源发起方流水号");
		sysHeadProperties.setKeyProperties("SND_SEQ", sysSND_SEQ);
		sysHeadProperties.setRequiredKey("SND_SEQ");
		
		// OUT_DATE-渠道日期
		JsonSchemaPropertyDO sysOUT_DATE = new JsonSchemaPropertyDO(sysHead$id + JsonSchemaKeyType.PROPERTIES_URI + "OUT_DATE", JsonSchemaKeyType.TYPE_STRING);
		sysOUT_DATE.setTitle("OUT_DATE NODE");
		sysOUT_DATE.setDescription("渠道日期");
		sysHeadProperties.setKeyProperties("OUT_DATE", sysOUT_DATE);
		sysHeadProperties.setRequiredKey("OUT_DATE");	
		
		// OUT_TIME-渠道时间
		JsonSchemaPropertyDO sysOUT_TIME = new JsonSchemaPropertyDO(sysHead$id + JsonSchemaKeyType.PROPERTIES_URI + "OUT_TIME", JsonSchemaKeyType.TYPE_STRING);
		sysOUT_TIME.setTitle("OUT_TIME NODE");
		sysOUT_TIME.setDescription("渠道时间");
		sysHeadProperties.setKeyProperties("OUT_TIME", sysOUT_TIME);
		
		// OUT_SYS-渠道编号
		JsonSchemaPropertyDO sysOUT_SYS = new JsonSchemaPropertyDO(sysHead$id + JsonSchemaKeyType.PROPERTIES_URI + "OUT_SYS", JsonSchemaKeyType.TYPE_STRING);
		sysOUT_SYS.setTitle("OUT_SYS NODE");
		sysOUT_SYS.setDescription("渠道编号");
		sysHeadProperties.setKeyProperties("OUT_SYS", sysOUT_SYS);
		sysHeadProperties.setRequiredKey("OUT_SYS");
		
		// OUT_SEQ-渠道流水号
		JsonSchemaPropertyDO sysOUT_SEQ = new JsonSchemaPropertyDO(sysHead$id + JsonSchemaKeyType.PROPERTIES_URI + "OUT_SEQ", JsonSchemaKeyType.TYPE_STRING);
		sysOUT_SEQ.setTitle("OUT_SEQ NODE");
		sysOUT_SEQ.setDescription("渠道流水号");
		sysHeadProperties.setKeyProperties("OUT_SEQ", sysOUT_SEQ);
		sysHeadProperties.setRequiredKey("OUT_SEQ");
		
		// VER_NO-服务版本号
		JsonSchemaPropertyDO sysVER_NO = new JsonSchemaPropertyDO(sysHead$id + JsonSchemaKeyType.PROPERTIES_URI + "VER_NO", JsonSchemaKeyType.TYPE_STRING);
		sysVER_NO.setTitle("VER_NO NODE");
		sysVER_NO.setDescription("服务版本号");
		sysHeadProperties.setKeyProperties("VER_NO", sysVER_NO);		
		
		/**----------------------------------设置APP_HEAD属性----------------------------------------------------**/
		String appHead$id = JsonSchemaKeyType.$ID_URI+JsonSchemaKeyType.PROPERTIES_URI+APP_HEAD;
		JsonSchemaPropertyDO appHeadProperties = new JsonSchemaPropertyDO(appHead$id, JsonSchemaKeyType.TYPE_OBJECT);
		appHeadProperties.setTitle("APP_HEAD NODE");
		appHeadProperties.setDescription("请求报文应用头模板");
		reqSchema.setKeyProperties(APP_HEAD, appHeadProperties);
		
		// TRAN_DATE-交易日期
		JsonSchemaPropertyDO appTRAN_DATE = new JsonSchemaPropertyDO(appHead$id + JsonSchemaKeyType.PROPERTIES_URI + "TRAN_DATE", JsonSchemaKeyType.TYPE_STRING);
		appTRAN_DATE.setTitle("TRAN_DATE NODE");
		appTRAN_DATE.setDescription("交易日期");
		appHeadProperties.setKeyProperties("TRAN_DATE", appTRAN_DATE);
		appHeadProperties.setRequiredKey("TRAN_DATE");	
		
		// TRAN_TIME-交易时间
		JsonSchemaPropertyDO appTRAN_TIME = new JsonSchemaPropertyDO(appHead$id + JsonSchemaKeyType.PROPERTIES_URI + "TRAN_TIME", JsonSchemaKeyType.TYPE_STRING);
		appTRAN_TIME.setTitle("TRAN_TIME NODE");
		appTRAN_TIME.setDescription("交易时间");
		appHeadProperties.setKeyProperties("TRAN_TIME", appTRAN_TIME);
		appHeadProperties.setRequiredKey("TRAN_TIME");	

		// BRCH-机构号
		JsonSchemaPropertyDO appBRCH = new JsonSchemaPropertyDO(appHead$id + JsonSchemaKeyType.PROPERTIES_URI + "BRCH", JsonSchemaKeyType.TYPE_STRING);
		appBRCH.setTitle("BRCH NODE");
		appBRCH.setDescription("机构号");
		appHeadProperties.setKeyProperties("BRCH", appBRCH);
		appHeadProperties.setRequiredKey("BRCH");	

		// TLR_NO-柜员号
		JsonSchemaPropertyDO appTLR_NO = new JsonSchemaPropertyDO(appHead$id + JsonSchemaKeyType.PROPERTIES_URI + "TLR_NO", JsonSchemaKeyType.TYPE_STRING);
		appTLR_NO.setTitle("TLR_NO NODE");
		appTLR_NO.setDescription("柜员号");
		appHeadProperties.setKeyProperties("TLR_NO", appTLR_NO);
		appHeadProperties.setRequiredKey("TLR_NO");	
	
		// ORIG_TLR_NO-交易录入柜员号
		JsonSchemaPropertyDO appORIG_TLR_NO = new JsonSchemaPropertyDO(appHead$id + JsonSchemaKeyType.PROPERTIES_URI + "ORIG_TLR_NO", JsonSchemaKeyType.TYPE_STRING);
		appORIG_TLR_NO.setTitle("ORIG_TLR_NO NODE");
		appORIG_TLR_NO.setDescription("交易录入柜员号");
		appHeadProperties.setKeyProperties("ORIG_TLR_NO", appORIG_TLR_NO);
		
		// AUTH_TLR-授权柜员
		JsonSchemaPropertyDO appAUTH_TLR = new JsonSchemaPropertyDO(appHead$id + JsonSchemaKeyType.PROPERTIES_URI + "AUTH_TLR", JsonSchemaKeyType.TYPE_ARRAY);
		appAUTH_TLR.setTitle("AUTH_TLR NODE");
		appAUTH_TLR.setDescription("授权柜员");
		appHeadProperties.setKeyProperties("AUTH_TLR", appAUTH_TLR);
		
		// AUTH_TLR_NO-授权柜员号
		JsonSchemaPropertyDO appAUTH_TLR_NO = new JsonSchemaPropertyDO(appAUTH_TLR.getItems().get$id() + JsonSchemaKeyType.PROPERTIES_URI + "AUTH_TLR_NO", JsonSchemaKeyType.TYPE_STRING);
		appAUTH_TLR_NO.setTitle("AUTH_TLR_NO Item Schema");
		appAUTH_TLR_NO.setDescription("授权柜员号");
		appAUTH_TLR.getItems().setKeyProperties("AUTH_TLR_NO", appAUTH_TLR_NO);
	
		// AUTH_BRCH-授权机构号
		JsonSchemaPropertyDO appAUTH_BRCH = new JsonSchemaPropertyDO(appAUTH_TLR.getItems().get$id() + JsonSchemaKeyType.PROPERTIES_URI + "AUTH_BRCH", JsonSchemaKeyType.TYPE_STRING);
		appAUTH_BRCH.setTitle("AUTH_BRCH Item Schema");
		appAUTH_BRCH.setDescription("授权机构号");
		appAUTH_TLR.getItems().setKeyProperties("AUTH_BRCH", appAUTH_BRCH);
		
		// ORIG_TRAN_DATE-需冲正的原交易日期
		JsonSchemaPropertyDO appORIG_TRAN_DATE = new JsonSchemaPropertyDO(appHead$id + JsonSchemaKeyType.PROPERTIES_URI + "ORIG_TRAN_DATE", JsonSchemaKeyType.TYPE_STRING);
		appORIG_TRAN_DATE.setTitle("ORIG_TRAN_DATE NODE");
		appORIG_TRAN_DATE.setDescription("需冲正的原交易日期");
		appHeadProperties.setKeyProperties("ORIG_TRAN_DATE", appORIG_TRAN_DATE);
		
		// ORIG_TRAN_SEQ-需冲正的原业务流水号
		JsonSchemaPropertyDO appORIG_TRAN_SEQ = new JsonSchemaPropertyDO(appHead$id + JsonSchemaKeyType.PROPERTIES_URI + "ORIG_TRAN_SEQ", JsonSchemaKeyType.TYPE_STRING);
		appORIG_TRAN_SEQ.setTitle("ORIG_TRAN_SEQ NODE");
		appORIG_TRAN_SEQ.setDescription("需冲正的原业务流水号");
		appHeadProperties.setKeyProperties("ORIG_TRAN_SEQ", appORIG_TRAN_SEQ);
		
		// TERM_NO-终端设备号
		JsonSchemaPropertyDO appTERM_NO = new JsonSchemaPropertyDO(appHead$id + JsonSchemaKeyType.PROPERTIES_URI + "TERM_NO", JsonSchemaKeyType.TYPE_STRING);
		appTERM_NO.setTitle("TERM_NO NODE");
		appTERM_NO.setDescription("终端设备号");
		appHeadProperties.setKeyProperties("TERM_NO", appTERM_NO);
		
		// MAC_NODE_NO-MAC节点号
		JsonSchemaPropertyDO appMAC_NODE_NO = new JsonSchemaPropertyDO(appHead$id + JsonSchemaKeyType.PROPERTIES_URI + "MAC_NODE_NO", JsonSchemaKeyType.TYPE_STRING);
		appMAC_NODE_NO.setTitle("MAC_NODE_NO NODE");
		appMAC_NODE_NO.setDescription("MAC节点号");
		appHeadProperties.setKeyProperties("MAC_NODE_NO", appMAC_NODE_NO);
		
		// PIN_NODE_NO-PIN节点号
		JsonSchemaPropertyDO appPIN_NODE_NO = new JsonSchemaPropertyDO(appHead$id + JsonSchemaKeyType.PROPERTIES_URI + "PIN_NODE_NO", JsonSchemaKeyType.TYPE_STRING);
		appPIN_NODE_NO.setTitle("PIN_NODE_NO NODE");
		appPIN_NODE_NO.setDescription("PIN节点号");
		appHeadProperties.setKeyProperties("PIN_NODE_NO", appPIN_NODE_NO);
				
		// STR_REC_SER-开始记录数
		JsonSchemaPropertyDO appSTR_REC_SER = new JsonSchemaPropertyDO(appHead$id + JsonSchemaKeyType.PROPERTIES_URI + "STR_REC_SER", JsonSchemaKeyType.TYPE_INTEGER);
		appSTR_REC_SER.setTitle("STR_REC_SER NODE");
		appSTR_REC_SER.setDescription("开始记录数");
		appHeadProperties.setKeyProperties("STR_REC_SER", appSTR_REC_SER);
		
		// REQ_REC_NUM-要求每页返回记录总数
		JsonSchemaPropertyDO appREQ_REC_NUM = new JsonSchemaPropertyDO(appHead$id + JsonSchemaKeyType.PROPERTIES_URI + "REQ_REC_NUM", JsonSchemaKeyType.TYPE_INTEGER);
		appREQ_REC_NUM.setTitle("REQ_REC_NUM NODE");
		appREQ_REC_NUM.setDescription("要求每页返回记录总数");
		appHeadProperties.setKeyProperties("REQ_REC_NUM", appREQ_REC_NUM);
		
		/**----------------------------------设置LOCAL_HEAD属性----------------------------------------------------**/
		String localHead$id = JsonSchemaKeyType.$ID_URI+JsonSchemaKeyType.PROPERTIES_URI+LOCAL_HEAD;
		JsonSchemaPropertyDO localHeadProperties = new JsonSchemaPropertyDO(localHead$id, JsonSchemaKeyType.TYPE_OBJECT);
		localHeadProperties.setTitle("LOCAL_HEAD NODE");
		appHeadProperties.setDescription("请求报文本地扩展头模板");
		reqSchema.setKeyProperties(LOCAL_HEAD, localHeadProperties);

		// BUSI_NO-业务编号
		JsonSchemaPropertyDO localBUSI_NO = new JsonSchemaPropertyDO(appHead$id + JsonSchemaKeyType.PROPERTIES_URI + "BUSI_NO", JsonSchemaKeyType.TYPE_STRING);
		localBUSI_NO.setTitle("BUSI_NO NODE");
		localBUSI_NO.setDescription("业务编号");
		localHeadProperties.setKeyProperties("BUSI_NO", localBUSI_NO);
		localHeadProperties.setRequiredKey("BUSI_NO");	
				
		// ENTR_NO-业务编号
		JsonSchemaPropertyDO localENTR_NO = new JsonSchemaPropertyDO(appHead$id + JsonSchemaKeyType.PROPERTIES_URI + "ENTR_NO", JsonSchemaKeyType.TYPE_STRING);
		localENTR_NO.setTitle("ENTR_NO NODE");
		localENTR_NO.setDescription("业务编号");
		localHeadProperties.setKeyProperties("ENTR_NO", localENTR_NO);
		
		// SUB_ENTR_NO-子单位编号
		JsonSchemaPropertyDO localSUB_ENTR_NO = new JsonSchemaPropertyDO(appHead$id + JsonSchemaKeyType.PROPERTIES_URI + "SUB_ENTR_NO", JsonSchemaKeyType.TYPE_STRING);
		localSUB_ENTR_NO.setTitle("SUB_ENTR_NO NODE");
		localSUB_ENTR_NO.setDescription("子单位编号");
		localHeadProperties.setKeyProperties("SUB_ENTR_NO", localSUB_ENTR_NO);
		
		// TNT_NO-租户号
		JsonSchemaPropertyDO localTNT_NO = new JsonSchemaPropertyDO(appHead$id + JsonSchemaKeyType.PROPERTIES_URI + "TNT_NO", JsonSchemaKeyType.TYPE_STRING);
		localTNT_NO.setTitle("TNT_NO NODE");
		localTNT_NO.setDescription("租户号");
		localHeadProperties.setKeyProperties("TNT_NO", localTNT_NO);
		
		// CHNL_NO-渠道号
		JsonSchemaPropertyDO localCHNL_NO = new JsonSchemaPropertyDO(appHead$id + JsonSchemaKeyType.PROPERTIES_URI + "CHNL_NO", JsonSchemaKeyType.TYPE_STRING);
		localCHNL_NO.setTitle("CHNL_NO NODE");
		localCHNL_NO.setDescription("渠道号");
		localHeadProperties.setKeyProperties("CHNL_NO", localCHNL_NO);
		
		// SYS-关联系统编号
		JsonSchemaPropertyDO localSYS = new JsonSchemaPropertyDO(appHead$id + JsonSchemaKeyType.PROPERTIES_URI + "SYS", JsonSchemaKeyType.TYPE_STRING);
		localSYS.setTitle("SYS NODE");
		localSYS.setDescription("关联系统编号");
		localHeadProperties.setKeyProperties("SYS", localSYS);
		
		// LEGA_NO-法人编号
		JsonSchemaPropertyDO localLEGA_NO = new JsonSchemaPropertyDO(appHead$id + JsonSchemaKeyType.PROPERTIES_URI + "LEGA_NO", JsonSchemaKeyType.TYPE_STRING);
		localLEGA_NO.setTitle("LEGA_NO NODE");
		localLEGA_NO.setDescription("法人编号");
		localHeadProperties.setKeyProperties("LEGA_NO", localLEGA_NO);
		localHeadProperties.setRequiredKey("LEGA_NO");
		
		// FUNC_NO-功能编号
		JsonSchemaPropertyDO localFUNC_NO = new JsonSchemaPropertyDO(appHead$id + JsonSchemaKeyType.PROPERTIES_URI + "FUNC_NO", JsonSchemaKeyType.TYPE_STRING);
		localFUNC_NO.setTitle("FUNC_NO NODE");
		localFUNC_NO.setDescription("功能编号");
		localHeadProperties.setKeyProperties("FUNC_NO", localFUNC_NO);
		
		// SIGN_PROT_TP_NO-签约协议类型编号
		JsonSchemaPropertyDO localSIGN_PROT_TP_NO = new JsonSchemaPropertyDO(appHead$id + JsonSchemaKeyType.PROPERTIES_URI + "SIGN_PROT_TP_NO", JsonSchemaKeyType.TYPE_STRING);
		localSIGN_PROT_TP_NO.setTitle("SIGN_PROT_TP_NO NODE");
		localSIGN_PROT_TP_NO.setDescription("签约协议类型编号");
		localHeadProperties.setKeyProperties("SIGN_PROT_TP_NO", localSIGN_PROT_TP_NO);
		
		// LIM_LIST-限额LIST
		JsonSchemaPropertyDO localLIM_LIST = new JsonSchemaPropertyDO(appHead$id + JsonSchemaKeyType.PROPERTIES_URI + "LIM_LIST", JsonSchemaKeyType.TYPE_ARRAY);
		localLIM_LIST.setTitle("LIM_LIST NODE");
		localLIM_LIST.setDescription("限额LIST");
		localHeadProperties.setKeyProperties("LIM_LIST", localLIM_LIST);
		
		// LIM_FLG-限额标志
		JsonSchemaPropertyDO localLIM_FLG = new JsonSchemaPropertyDO(localLIM_LIST.getItems().get$id() + "LIM_FLG", JsonSchemaKeyType.TYPE_STRING);
		localLIM_FLG.setTitle("LIM_FLG ITEMS Schema");
		localLIM_FLG.setDescription("限额标志");
		localLIM_LIST.getItems().setKeyProperties("LIM_FLG", localLIM_FLG);
		
		// LIM_NUM-限额数量
		JsonSchemaPropertyDO localLIM_NUM = new JsonSchemaPropertyDO(localLIM_LIST.getItems().get$id() + "LIM_NUM", JsonSchemaKeyType.TYPE_INTEGER);
		localLIM_NUM.setTitle("LIM_NUM ITEMS Schema");
		localLIM_NUM.setDescription("限额数量");
		localLIM_LIST.getItems().setKeyProperties("LIM_NUM", localLIM_NUM);
		
		
		// LIM_AMT-限额金额
		JsonSchemaPropertyDO localLIM_AMT = new JsonSchemaPropertyDO(localLIM_LIST.getItems().get$id() + "LIM_AMT", JsonSchemaKeyType.TYPE_NUMBER);
		localLIM_AMT.setTitle("LIM_AMT ITEMS Schema");
		localLIM_AMT.setDescription("限额金额");
		localLIM_LIST.getItems().setKeyProperties("LIM_AMT", localLIM_AMT);
		
		return reqSchema;
	}

	/**
	 * 创建金融交易云响应报文模板
	 * @param resBodySchema
	 * @return
	 */
	public static JsonSchemaDO getResDTOSchema(JsonSchemaPropertyDO resBodySchema) {
		// TODO Auto-generated method stub
		JsonSchemaDO reqSchema = new JsonSchemaDO();
		reqSchema.setTitle("Http Json Response DTO");	// 设置该节点的标题
		reqSchema.setTitle("金融交易云响应报文模板");
		// 设置响应报文必输检查字段
		reqSchema.setRequiredKey(SYS_HEAD);
		if(null!=resBodySchema && !resBodySchema.getRequired().isEmpty()){
			// 报文体不为空时设置为必输字段
			reqSchema.setRequiredKey(BODY);
		}
		// 设置报文体模板
		reqSchema.setKeyProperties(BODY, resBodySchema);
		
		/**----------------------------------设置SYS_HEAD属性----------------------------------------------------**/
		String sysHead$id = JsonSchemaKeyType.$ID_URI+JsonSchemaKeyType.PROPERTIES_URI+SYS_HEAD;
		JsonSchemaPropertyDO sysHeadProperties = new JsonSchemaPropertyDO(sysHead$id, JsonSchemaKeyType.TYPE_OBJECT);
		sysHeadProperties.setTitle("SYS_HEAD NODE");
		sysHeadProperties.setDescription("响应报文系统头模板");
		reqSchema.setKeyProperties(SYS_HEAD, sysHeadProperties);
		
		// TRAN_STAT-交易状态
		JsonSchemaPropertyDO sysTRAN_STAT = new JsonSchemaPropertyDO(sysHead$id + JsonSchemaKeyType.PROPERTIES_URI + "TRAN_STAT", JsonSchemaKeyType.TYPE_STRING);
		sysTRAN_STAT.setTitle("TRAN_STAT NODE");
		sysTRAN_STAT.setDescription("交易状态");
		sysHeadProperties.setKeyProperties("TRAN_STAT", sysTRAN_STAT);
		sysHeadProperties.setRequiredKey("TRAN_STAT");	
	
		// TRAN_RET-交易返回代码数组
		JsonSchemaPropertyDO sysTRAN_RET = new JsonSchemaPropertyDO(sysHead$id + JsonSchemaKeyType.PROPERTIES_URI + "TRAN_RET", JsonSchemaKeyType.TYPE_ARRAY);
		sysTRAN_RET.setTitle("TRAN_RET NODE");
		sysTRAN_RET.setDescription("交易返回代码数组");
		sysHeadProperties.setKeyProperties("TRAN_RET", sysTRAN_RET);
		sysHeadProperties.setRequiredKey("TRAN_RET");
		
		// RET_CODE-交易返回代码
		JsonSchemaPropertyDO sysRET_CODE = new JsonSchemaPropertyDO(sysTRAN_RET.getItems().get$id() + JsonSchemaKeyType.PROPERTIES_URI + "RET_CODE", JsonSchemaKeyType.TYPE_STRING);
		sysRET_CODE.setTitle("RET_CODE Items Schema");
		sysRET_CODE.setDescription("交易返回代码");
		sysTRAN_RET.getItems().setKeyProperties("RET_CODE", sysRET_CODE);
		sysTRAN_RET.getItems().setRequiredKey("RET_CODE");	
		
		// RET_MSG-交易返回代码
		JsonSchemaPropertyDO sysRET_MSG = new JsonSchemaPropertyDO(sysTRAN_RET.getItems().get$id() + JsonSchemaKeyType.PROPERTIES_URI + "RET_MSG", JsonSchemaKeyType.TYPE_STRING);
		sysRET_MSG.setTitle("RET_MSG Items Schema");
		sysRET_MSG.setDescription("交易返回代码");
		sysTRAN_RET.getItems().setKeyProperties("RET_MSG", sysRET_MSG);
		sysTRAN_RET.getItems().setRequiredKey("RET_MSG");
		
		/**----------------------------------设置APP_HEAD属性----------------------------------------------------**/
		String appHead$id = JsonSchemaKeyType.$ID_URI+JsonSchemaKeyType.PROPERTIES_URI+APP_HEAD;
		JsonSchemaPropertyDO appHeadProperties = new JsonSchemaPropertyDO(appHead$id, JsonSchemaKeyType.TYPE_OBJECT);
		appHeadProperties.setTitle("APP_HEAD NODE");
		appHeadProperties.setDescription("响应报文应用头模板");
		reqSchema.setKeyProperties(APP_HEAD, appHeadProperties);
		
		// RESP_DATE-响应日期
		JsonSchemaPropertyDO appRESP_DATE = new JsonSchemaPropertyDO(appHead$id + JsonSchemaKeyType.PROPERTIES_URI + "RESP_DATE", JsonSchemaKeyType.TYPE_STRING);
		appRESP_DATE.setTitle("RESP_DATE NODE");
		appRESP_DATE.setDescription("响应日期");
		appHeadProperties.setKeyProperties("RESP_DATE", appRESP_DATE);
		
		// RESP_SEQ-响应流水号
		JsonSchemaPropertyDO appRESP_SEQ = new JsonSchemaPropertyDO(appHead$id + JsonSchemaKeyType.PROPERTIES_URI + "RESP_SEQ", JsonSchemaKeyType.TYPE_STRING);
		appRESP_SEQ.setTitle("RESP_SEQ NODE");
		appRESP_SEQ.setDescription("响应流水号");
		appHeadProperties.setKeyProperties("RESP_SEQ", appRESP_SEQ);
		
		// RESP_REC_NUM-本次返回查询结果记录总数
		JsonSchemaPropertyDO appRESP_REC_NUM = new JsonSchemaPropertyDO(appHead$id + JsonSchemaKeyType.PROPERTIES_URI + "RESP_REC_NUM", JsonSchemaKeyType.TYPE_INTEGER);
		appRESP_REC_NUM.setTitle("RESP_REC_NUM NODE");
		appRESP_REC_NUM.setDescription("本次返回查询结果记录总数");
		appHeadProperties.setKeyProperties("RESP_REC_NUM", appRESP_REC_NUM);
	
		// TOT_NUM-符合查询条件的记录总数
		JsonSchemaPropertyDO appTOT_NUM = new JsonSchemaPropertyDO(appHead$id + JsonSchemaKeyType.PROPERTIES_URI + "TOT_NUM", JsonSchemaKeyType.TYPE_INTEGER);
		appTOT_NUM.setTitle("TOT_NUM NODE");
		appTOT_NUM.setDescription("符合查询条件的记录总数");
		appHeadProperties.setKeyProperties("TOT_NUM", appTOT_NUM);
		
		// END_FLG-结束标志
		JsonSchemaPropertyDO appEND_FLG = new JsonSchemaPropertyDO(appHead$id + JsonSchemaKeyType.PROPERTIES_URI + "END_FLG", JsonSchemaKeyType.TYPE_STRING);
		appEND_FLG.setTitle("END_FLG NODE");
		appEND_FLG.setDescription("结束标志");
		appHeadProperties.setKeyProperties("END_FLG", appEND_FLG);
		
		/**----------------------------------设置LOCAL_HEAD属性----------------------------------------------------**/
		String localHead$id = JsonSchemaKeyType.$ID_URI+JsonSchemaKeyType.PROPERTIES_URI+LOCAL_HEAD;
		JsonSchemaPropertyDO localHeadProperties = new JsonSchemaPropertyDO(localHead$id, JsonSchemaKeyType.TYPE_OBJECT);
		localHeadProperties.setTitle("LOCAL_HEAD NODE");
		appHeadProperties.setDescription("响应报文本地扩展头模板");
		reqSchema.setKeyProperties(LOCAL_HEAD, localHeadProperties);
		
		return reqSchema;
	}

}
