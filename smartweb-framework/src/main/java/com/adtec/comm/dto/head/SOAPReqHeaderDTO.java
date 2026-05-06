/**
 * 系统名称: SmartWeb平台
 * 模块名称: SOAP报文的请求头
 * 类  名  称: SOAPReqHeaderDTO.java
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
public class SOAPReqHeaderDTO {
	private String VerNo;
	private String ReqSysCd;
	private String ReqSecCd;
	private String TxnTyp;
	private String TxnMod;
	private String TxnCd;
	private String TxnNme;
	private String WhlSeqNo;
	private String WhlTm;
	private String ReqDt;
	private String ReqTm;
	private String ReqSeqNo;
	private String ChnlNo;
	private String BrchNo;
	private String BrchNme;
	private String TlrNo;
	private String AuthTlr;
	private String SndFileNme;
	private String BgnRec;
	private String MaxRec;
	private String VTlrNo;
	private String ChkCd;
	private String Checker;
	private String AcctBrch;
	private String BizTyp;
	private String ChkNo;
	private String BootNo;
	private String WinNm;
	private String WinId;
	private String FsysFlg;
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
	 * @return the reqSysCd
	 */
	public String getReqSysCd() {
		return ReqSysCd;
	}
	/**
	 * @param reqSysCd the reqSysCd to set
	 */
	public void setReqSysCd(String reqSysCd) {
		ReqSysCd = reqSysCd;
	}
	/**
	 * @return the reqSecCd
	 */
	public String getReqSecCd() {
		return ReqSecCd;
	}
	/**
	 * @param reqSecCd the reqSecCd to set
	 */
	public void setReqSecCd(String reqSecCd) {
		ReqSecCd = reqSecCd;
	}
	/**
	 * @return the txnTyp
	 */
	public String getTxnTyp() {
		return TxnTyp;
	}
	/**
	 * @param txnTyp the txnTyp to set
	 */
	public void setTxnTyp(String txnTyp) {
		TxnTyp = txnTyp;
	}
	/**
	 * @return the txnMod
	 */
	public String getTxnMod() {
		return TxnMod;
	}
	/**
	 * @param txnMod the txnMod to set
	 */
	public void setTxnMod(String txnMod) {
		TxnMod = txnMod;
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
	 * @return the whlTm
	 */
	public String getWhlTm() {
		return WhlTm;
	}
	/**
	 * @param whlTm the whlTm to set
	 */
	public void setWhlTm(String whlTm) {
		WhlTm = whlTm;
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
	 * @return the brchNo
	 */
	public String getBrchNo() {
		return BrchNo;
	}
	/**
	 * @param brchNo the brchNo to set
	 */
	public void setBrchNo(String brchNo) {
		BrchNo = brchNo;
	}
	/**
	 * @return the brchNme
	 */
	public String getBrchNme() {
		return BrchNme;
	}
	/**
	 * @param brchNme the brchNme to set
	 */
	public void setBrchNme(String brchNme) {
		BrchNme = brchNme;
	}
	/**
	 * @return the tlrNo
	 */
	public String getTlrNo() {
		return TlrNo;
	}
	/**
	 * @param tlrNo the tlrNo to set
	 */
	public void setTlrNo(String tlrNo) {
		TlrNo = tlrNo;
	}
	/**
	 * @return the authTlr
	 */
	public String getAuthTlr() {
		return AuthTlr;
	}
	/**
	 * @param authTlr the authTlr to set
	 */
	public void setAuthTlr(String authTlr) {
		AuthTlr = authTlr;
	}
	/**
	 * @return the sndFileNme
	 */
	public String getSndFileNme() {
		return SndFileNme;
	}
	/**
	 * @param sndFileNme the sndFileNme to set
	 */
	public void setSndFileNme(String sndFileNme) {
		SndFileNme = sndFileNme;
	}
	/**
	 * @return the bgnRec
	 */
	public String getBgnRec() {
		return BgnRec;
	}
	/**
	 * @param bgnRec the bgnRec to set
	 */
	public void setBgnRec(String bgnRec) {
		BgnRec = bgnRec;
	}
	/**
	 * @return the maxRec
	 */
	public String getMaxRec() {
		return MaxRec;
	}
	/**
	 * @param maxRec the maxRec to set
	 */
	public void setMaxRec(String maxRec) {
		MaxRec = maxRec;
	}
	/**
	 * @return the vTlrNo
	 */
	public String getVTlrNo() {
		return VTlrNo;
	}
	/**
	 * @param vTlrNo the vTlrNo to set
	 */
	public void setVTlrNo(String vTlrNo) {
		VTlrNo = vTlrNo;
	}
	/**
	 * @return the chkCd
	 */
	public String getChkCd() {
		return ChkCd;
	}
	/**
	 * @param chkCd the chkCd to set
	 */
	public void setChkCd(String chkCd) {
		ChkCd = chkCd;
	}
	/**
	 * @return the checker
	 */
	public String getChecker() {
		return Checker;
	}
	/**
	 * @param checker the checker to set
	 */
	public void setChecker(String checker) {
		Checker = checker;
	}
	/**
	 * @return the acctBrch
	 */
	public String getAcctBrch() {
		return AcctBrch;
	}
	/**
	 * @param acctBrch the acctBrch to set
	 */
	public void setAcctBrch(String acctBrch) {
		AcctBrch = acctBrch;
	}
	/**
	 * @return the bizTyp
	 */
	public String getBizTyp() {
		return BizTyp;
	}
	/**
	 * @param bizTyp the bizTyp to set
	 */
	public void setBizTyp(String bizTyp) {
		BizTyp = bizTyp;
	}
	/**
	 * @return the chkNo
	 */
	public String getChkNo() {
		return ChkNo;
	}
	/**
	 * @param chkNo the chkNo to set
	 */
	public void setChkNo(String chkNo) {
		ChkNo = chkNo;
	}
	/**
	 * @return the bootNo
	 */
	public String getBootNo() {
		return BootNo;
	}
	/**
	 * @param bootNo the bootNo to set
	 */
	public void setBootNo(String bootNo) {
		BootNo = bootNo;
	}
	/**
	 * @return the winNm
	 */
	public String getWinNm() {
		return WinNm;
	}
	/**
	 * @param winNm the winNm to set
	 */
	public void setWinNm(String winNm) {
		WinNm = winNm;
	}
	/**
	 * @return the winId
	 */
	public String getWinId() {
		return WinId;
	}
	/**
	 * @param winId the winId to set
	 */
	public void setWinId(String winId) {
		WinId = winId;
	}
	/**
	 * @return the fsysFlg
	 */
	public String getFsysFlg() {
		return FsysFlg;
	}
	/**
	 * @param fsysFlg the fsysFlg to set
	 */
	public void setFsysFlg(String fsysFlg) {
		FsysFlg = fsysFlg;
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
