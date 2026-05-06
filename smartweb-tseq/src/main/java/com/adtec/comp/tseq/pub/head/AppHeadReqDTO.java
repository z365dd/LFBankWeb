/**
 * 系统名称: 中间业务云平台
 * 模块名称: 报文格式
 * 功能描述: 请求应用头
 * 类 名 称  : AppHeadReqDTO.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: ruanyh@ <br>
 * 开发时间: 2019年3月25日 下午14:00:00<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.comp.tseq.pub.head;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class AppHeadReqDTO {
	/*交易日期*/
	@JSONField(name="TX_DATE")
	private String TX_DATE;
	/*交易时间*/
	@JSONField(name="TX_TIME")
	private String TX_TIME;
	/*机构号*/
	@JSONField(name="BRCH_NO")
	private String BRCH_NO;
	/*柜员号*/
	@JSONField(name="TLR_NO")
	private String TLR_NO;
	/*交易录入柜员号*/
	@JSONField(name="ORIG_TLR_NO")
	private String ORIG_TLR_NO;
	/*授权柜员号*/
	@JSONField(name="AUTH_TLR_NO")
	private String AUTH_TLR_NO;
	/*需冲正的原交易日期*/
	@JSONField(name="ORIG_TX_DATE")
	private String ORIG_TX_DATE;
	/*需冲正的原业务流水号*/
	@JSONField(name="ORIG_TX_SEQ_NO")
	private String ORIG_TX_SEQ_NO;
	/*终端设备号*/
	@JSONField(name="TERM_NO")
	private String TERM_NO;
	/*MAC节点号*/
	@JSONField(name="MAC_NODE_NO")
	private String MAC_NODE_NO;
	/*PIN节点号*/
	@JSONField(name="PIN_NODE_NO")
	private String PIN_NODE_NO;
	/*开始记录数*/
	@JSONField(name="BGN_REC_NO")
	private String BGN_REC_NO;
	/*要求每页返回记录总数*/
	@JSONField(name="REQ_REC_NUM")
	private long REQ_REC_NUM;
	
	
	/*修改新增*/
	/*交易日期*/
	@JSONField(name="TRAN_DATE")
	private String TRAN_DATE;
	/*交易时间*/
	@JSONField(name="TRAN_TIME")
	private String TRAN_TIME;
	/*授权柜员-新增*/
	@JSONField(name="AUTH_TLR")
	private List<AppHeadAUListReqDTO> AUTH_TLR = new ArrayList<AppHeadAUListReqDTO>();
	/*需冲正的原交易日期*/
	@JSONField(name="ORIG_TRAN_DATE")
	private String ORIG_TRAN_DATE;
	/*需冲正的原业务流水号*/
	@JSONField(name="ORIG_TRAN_SEQ")
	private String ORIG_TRAN_SEQ;
	/*开始记录数*/
	@JSONField(name="STR_REC_SER")
	private long STR_REC_SER;
	/*要求每页返回记录总数*/
	/*private long REQ_REC_NUM;*/
	/*服务请求发送方流水号*/
	@JSONField(name="REQ_SEQ")
	private String REQ_SEQ;
	
	
	public String getREQ_SEQ() {
		return REQ_SEQ;
	}
	public void setREQ_SEQ(String rEQ_SEQ) {
		REQ_SEQ = rEQ_SEQ;
	}
	public String getTRAN_DATE() {
		return TRAN_DATE;
	}
	public void setTRAN_DATE(String tRAN_DATE) {
		TRAN_DATE = tRAN_DATE;
	}
	public String getTRAN_TIME() {
		return TRAN_TIME;
	}
	public void setTRAN_TIME(String tRAN_TIME) {
		TRAN_TIME = tRAN_TIME;
	}
	
	
	public List<AppHeadAUListReqDTO> getAUTH_TLR() {
		return AUTH_TLR;
	}
	public void setAUTH_TLR(List<AppHeadAUListReqDTO> aUTH_TLR) {
		AUTH_TLR = aUTH_TLR;
	}
	public String getORIG_TRAN_DATE() {
		return ORIG_TRAN_DATE;
	}
	public void setORIG_TRAN_DATE(String oRIG_TRAN_DATE) {
		ORIG_TRAN_DATE = oRIG_TRAN_DATE;
	}
	public String getORIG_TRAN_SEQ() {
		return ORIG_TRAN_SEQ;
	}
	public void setORIG_TRAN_SEQ(String oRIG_TRAN_SEQ) {
		ORIG_TRAN_SEQ = oRIG_TRAN_SEQ;
	}
	public long getSTR_REC_SER() {
		return STR_REC_SER;
	}
	public void setSTR_REC_SER(long sTR_REC_SER) {
		STR_REC_SER = sTR_REC_SER;
	}
	/**
	 * 
	 */
	public AppHeadReqDTO() {
	}
	/**
	 * @param tX_DATE
	 * @param tX_TIME
	 * @param bRCH_NO
	 * @param tLR_NO
	 * @param oRIG_TLR_NO
	 * @param aUTH_TLR_NO
	 * @param oRIG_TX_DATE
	 * @param oRIG_TX_SEQ_NO
	 * @param tERM_NO
	 * @param mAC_NODE_NO
	 * @param pIN_NODE_NO
	 * @param bGN_REC_NO
	 * @param rEQ_REC_NUM
	 */
	public AppHeadReqDTO(String tX_DATE, String tX_TIME, String bRCH_NO, String tLR_NO, String oRIG_TLR_NO,
			String aUTH_TLR_NO, String oRIG_TX_DATE, String oRIG_TX_SEQ_NO, String tERM_NO, String mAC_NODE_NO,
			String pIN_NODE_NO, String bGN_REC_NO, long rEQ_REC_NUM) {
		super();
		TX_DATE = tX_DATE;
		TX_TIME = tX_TIME;
		BRCH_NO = bRCH_NO;
		TLR_NO = tLR_NO;
		ORIG_TLR_NO = oRIG_TLR_NO;
		AUTH_TLR_NO = aUTH_TLR_NO;
		ORIG_TX_DATE = oRIG_TX_DATE;
		ORIG_TX_SEQ_NO = oRIG_TX_SEQ_NO;
		TERM_NO = tERM_NO;
		MAC_NODE_NO = mAC_NODE_NO;
		PIN_NODE_NO = pIN_NODE_NO;
		BGN_REC_NO = bGN_REC_NO;
		REQ_REC_NUM = rEQ_REC_NUM;
	}
	/**
	 * @return the tX_DATE
	 */
	public String getTX_DATE() {
		return TX_DATE;
	}
	/**
	 * @param tX_DATE the tX_DATE to set
	 */
	public void setTX_DATE(String tX_DATE) {
		TX_DATE = tX_DATE;
	}
	/**
	 * @return the tX_TIME
	 */
	public String getTX_TIME() {
		return TX_TIME;
	}
	/**
	 * @param tX_TIME the tX_TIME to set
	 */
	public void setTX_TIME(String tX_TIME) {
		TX_TIME = tX_TIME;
	}
	/**
	 * @return the bRCH_NO
	 */
	public String getBRCH_NO() {
		return BRCH_NO;
	}
	/**
	 * @param bRCH_NO the bRCH_NO to set
	 */
	public void setBRCH_NO(String bRCH_NO) {
		BRCH_NO = bRCH_NO;
	}
	/**
	 * @return the tLR_NO
	 */
	public String getTLR_NO() {
		return TLR_NO;
	}
	/**
	 * @param tLR_NO the tLR_NO to set
	 */
	public void setTLR_NO(String tLR_NO) {
		TLR_NO = tLR_NO;
	}
	/**
	 * @return the oRIG_TLR_NO
	 */
	public String getORIG_TLR_NO() {
		return ORIG_TLR_NO;
	}
	/**
	 * @param oRIG_TLR_NO the oRIG_TLR_NO to set
	 */
	public void setORIG_TLR_NO(String oRIG_TLR_NO) {
		ORIG_TLR_NO = oRIG_TLR_NO;
	}
	/**
	 * @return the aUTH_TLR_NO
	 */
	public String getAUTH_TLR_NO() {
		return AUTH_TLR_NO;
	}
	/**
	 * @param aUTH_TLR_NO the aUTH_TLR_NO to set
	 */
	public void setAUTH_TLR_NO(String aUTH_TLR_NO) {
		AUTH_TLR_NO = aUTH_TLR_NO;
	}
	/**
	 * @return the oRIG_TX_DATE
	 */
	public String getORIG_TX_DATE() {
		return ORIG_TX_DATE;
	}
	/**
	 * @param oRIG_TX_DATE the oRIG_TX_DATE to set
	 */
	public void setORIG_TX_DATE(String oRIG_TX_DATE) {
		ORIG_TX_DATE = oRIG_TX_DATE;
	}
	/**
	 * @return the oRIG_TX_SEQ_NO
	 */
	public String getORIG_TX_SEQ_NO() {
		return ORIG_TX_SEQ_NO;
	}
	/**
	 * @param oRIG_TX_SEQ_NO the oRIG_TX_SEQ_NO to set
	 */
	public void setORIG_TX_SEQ_NO(String oRIG_TX_SEQ_NO) {
		ORIG_TX_SEQ_NO = oRIG_TX_SEQ_NO;
	}
	/**
	 * @return the tERM_NO
	 */
	public String getTERM_NO() {
		return TERM_NO;
	}
	/**
	 * @param tERM_NO the tERM_NO to set
	 */
	public void setTERM_NO(String tERM_NO) {
		TERM_NO = tERM_NO;
	}
	/**
	 * @return the mAC_NODE_NO
	 */
	public String getMAC_NODE_NO() {
		return MAC_NODE_NO;
	}
	/**
	 * @param mAC_NODE_NO the mAC_NODE_NO to set
	 */
	public void setMAC_NODE_NO(String mAC_NODE_NO) {
		MAC_NODE_NO = mAC_NODE_NO;
	}
	/**
	 * @return the pIN_NODE_NO
	 */
	public String getPIN_NODE_NO() {
		return PIN_NODE_NO;
	}
	/**
	 * @param pIN_NODE_NO the pIN_NODE_NO to set
	 */
	public void setPIN_NODE_NO(String pIN_NODE_NO) {
		PIN_NODE_NO = pIN_NODE_NO;
	}
	/**
	 * @return the bGN_REC_NO
	 */
	public String getBGN_REC_NO() {
		return BGN_REC_NO;
	}
	/**
	 * @param bGN_REC_NO the bGN_REC_NO to set
	 */
	public void setBGN_REC_NO(String bGN_REC_NO) {
		BGN_REC_NO = bGN_REC_NO;
	}
	/**
	 * @return the rEQ_REC_NUM
	 */
	public long getREQ_REC_NUM() {
		return REQ_REC_NUM;
	}
	/**
	 * @param rEQ_REC_NUM the rEQ_REC_NUM to set
	 */
	public void setREQ_REC_NUM(long rEQ_REC_NUM) {
		REQ_REC_NUM = rEQ_REC_NUM;
	}
	
	
	
}
