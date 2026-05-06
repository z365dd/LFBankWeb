/**
 * 系统名称: SmartWeb平台
 * 模块名称: SOAP报文的响应头
 * 类  名  称: SOAPResHeaderDTO.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2018年6月28日 上午8:23:32<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.comm.dto.head;

/**
 * @author chenyl
 *
 */
public class SOAPResHeaderDTO {
	private String VerNo;
	private String RespSysCd;
	private String RespSecCd;
	private String TxnCd;
	private String TxnNme;
	private String WhlSeqNo;
	private String ReqDt;
	private String ReqTm;
	private String ReqSeqNo;
	private String SvrDt;
	private String SvrTm;
	private String SvrSeqNo;
	private String ChnlNo;
	private String RcvFileNme;
	private String TotNum;
	private String CurrRecNum;
	private String HostDt;
	private String HostSeq;
	private String ErrSeq;
	private String ErrScpt;
	private String FileHMac;
	private String HMac;
	/**
	 * @return the verNo
	 */
	public String getVerNo() {
		return VerNo;
	}
	/**
	 * @param verNo the verNo to set
	 */
	public void setVerNo(String verNo) {
		VerNo = verNo;
	}
	/**
	 * @return the respSysCd
	 */
	public String getRespSysCd() {
		return RespSysCd;
	}
	/**
	 * @param respSysCd the respSysCd to set
	 */
	public void setRespSysCd(String respSysCd) {
		RespSysCd = respSysCd;
	}
	/**
	 * @return the respSecCd
	 */
	public String getRespSecCd() {
		return RespSecCd;
	}
	/**
	 * @param respSecCd the respSecCd to set
	 */
	public void setRespSecCd(String respSecCd) {
		RespSecCd = respSecCd;
	}
	/**
	 * @return the txnCd
	 */
	public String getTxnCd() {
		return TxnCd;
	}
	/**
	 * @param txnCd the txnCd to set
	 */
	public void setTxnCd(String txnCd) {
		TxnCd = txnCd;
	}
	/**
	 * @return the txnNme
	 */
	public String getTxnNme() {
		return TxnNme;
	}
	/**
	 * @param txnNme the txnNme to set
	 */
	public void setTxnNme(String txnNme) {
		TxnNme = txnNme;
	}
	/**
	 * @return the whlSeqNo
	 */
	public String getWhlSeqNo() {
		return WhlSeqNo;
	}
	/**
	 * @param whlSeqNo the whlSeqNo to set
	 */
	public void setWhlSeqNo(String whlSeqNo) {
		WhlSeqNo = whlSeqNo;
	}
	/**
	 * @return the reqDt
	 */
	public String getReqDt() {
		return ReqDt;
	}
	/**
	 * @param reqDt the reqDt to set
	 */
	public void setReqDt(String reqDt) {
		ReqDt = reqDt;
	}
	/**
	 * @return the reqTm
	 */
	public String getReqTm() {
		return ReqTm;
	}
	/**
	 * @param reqTm the reqTm to set
	 */
	public void setReqTm(String reqTm) {
		ReqTm = reqTm;
	}
	/**
	 * @return the reqSeqNo
	 */
	public String getReqSeqNo() {
		return ReqSeqNo;
	}
	/**
	 * @param reqSeqNo the reqSeqNo to set
	 */
	public void setReqSeqNo(String reqSeqNo) {
		ReqSeqNo = reqSeqNo;
	}
	/**
	 * @return the svrDt
	 */
	public String getSvrDt() {
		return SvrDt;
	}
	/**
	 * @param svrDt the svrDt to set
	 */
	public void setSvrDt(String svrDt) {
		SvrDt = svrDt;
	}
	/**
	 * @return the svrTm
	 */
	public String getSvrTm() {
		return SvrTm;
	}
	/**
	 * @param svrTm the svrTm to set
	 */
	public void setSvrTm(String svrTm) {
		SvrTm = svrTm;
	}
	/**
	 * @return the svrSeqNo
	 */
	public String getSvrSeqNo() {
		return SvrSeqNo;
	}
	/**
	 * @param svrSeqNo the svrSeqNo to set
	 */
	public void setSvrSeqNo(String svrSeqNo) {
		SvrSeqNo = svrSeqNo;
	}
	/**
	 * @return the chnlNo
	 */
	public String getChnlNo() {
		return ChnlNo;
	}
	/**
	 * @param chnlNo the chnlNo to set
	 */
	public void setChnlNo(String chnlNo) {
		ChnlNo = chnlNo;
	}
	/**
	 * @return the rcvFileNme
	 */
	public String getRcvFileNme() {
		return RcvFileNme;
	}
	/**
	 * @param rcvFileNme the rcvFileNme to set
	 */
	public void setRcvFileNme(String rcvFileNme) {
		RcvFileNme = rcvFileNme;
	}
	/**
	 * @return the totNum
	 */
	public String getTotNum() {
		return TotNum;
	}
	/**
	 * @param totNum the totNum to set
	 */
	public void setTotNum(String totNum) {
		TotNum = totNum;
	}
	/**
	 * @return the currRecNum
	 */
	public String getCurrRecNum() {
		return CurrRecNum;
	}
	/**
	 * @param currRecNum the currRecNum to set
	 */
	public void setCurrRecNum(String currRecNum) {
		CurrRecNum = currRecNum;
	}
	/**
	 * @return the hostDt
	 */
	public String getHostDt() {
		return HostDt;
	}
	/**
	 * @param hostDt the hostDt to set
	 */
	public void setHostDt(String hostDt) {
		HostDt = hostDt;
	}
	/**
	 * @return the hostSeq
	 */
	public String getHostSeq() {
		return HostSeq;
	}
	/**
	 * @param hostSeq the hostSeq to set
	 */
	public void setHostSeq(String hostSeq) {
		HostSeq = hostSeq;
	}
	/**
	 * @return the errSeq
	 */
	public String getErrSeq() {
		return ErrSeq;
	}
	/**
	 * @param errSeq the errSeq to set
	 */
	public void setErrSeq(String errSeq) {
		ErrSeq = errSeq;
	}
	/**
	 * @return the errScpt
	 */
	public String getErrScpt() {
		return ErrScpt;
	}
	/**
	 * @param errScpt the errScpt to set
	 */
	public void setErrScpt(String errScpt) {
		ErrScpt = errScpt;
	}
	/**
	 * @return the fileHMac
	 */
	public String getFileHMac() {
		return FileHMac;
	}
	/**
	 * @param fileHMac the fileHMac to set
	 */
	public void setFileHMac(String fileHMac) {
		FileHMac = fileHMac;
	}
	/**
	 * @return the hMac
	 */
	public String getHMac() {
		return HMac;
	}
	/**
	 * @param hMac the hMac to set
	 */
	public void setHMac(String hMac) {
		HMac = hMac;
	}

}
