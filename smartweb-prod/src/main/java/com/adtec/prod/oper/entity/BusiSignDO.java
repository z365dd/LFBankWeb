package com.adtec.prod.oper.entity;

import java.util.ArrayList;
import java.util.List;

import com.adtec.prod.dto.FProdBusiSubmitKLReqDTO;
import com.adtec.sys.common.persistence.BaseDO;

public class BusiSignDO extends BaseDO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8818423690095733092L;
	//业务编号
	private String busiNo;
	//业务名称
	private String busiName;
	//可售产品编号
	private String saleProdCode;
	//单位编号
	private String entrNo;
	//单位名称
	private String entrName;
	/**状态 00停用，01启用*/
	private String openStat;
	//产品描述
	private String busiDesc;
	//清算模式
	private String clrTp;
	//开通渠道
	private String openGrpChnlNo;
	//过渡账户
	private String intrmAcct;
	//过渡账户名称
	private String intrmAcctName;
	//账户名称
	private String entrAcctName;
	//清算账户/客户账户
	private String entrAcct;
	//跨行标志
	private String  inOutBankFlg;
	//清算账户/客户账户所属行号
	private String entrAcctBank;
	//签约检查
	private String signPat;
	//对账模式
	private String chkPat;
	//手续费模式
	private String clrFeeTp;
	//手续费费率
	private double defFrt;
	//每笔金额
	private double amt;
	//手续费付款账号
	private String feeTfOutAcct;
	//手续费收款账号
	private String feeTfInAcct;
	//联机业务
	private String busiTp;
	//关联系统编号
	private String relatSysNo;
	private String action;
	private List<BusiSignParaDO> keyList = new ArrayList<BusiSignParaDO>();
	private List<FProdBusiSubmitKLReqDTO> KEY_LIST = new ArrayList<FProdBusiSubmitKLReqDTO>();
	public String getBusiNo() {
		return busiNo;
	}
	public void setBusiNo(String busiNo) {
		this.busiNo = busiNo;
	}
	public String getBusiName() {
		return busiName;
	}
	public void setBusiName(String busiName) {
		this.busiName = busiName;
	}
	
	public String getSaleProdCode() {
		return saleProdCode;
	}
	public void setSaleProdCode(String saleProdCode) {
		this.saleProdCode = saleProdCode;
	}
	public String getEntrNo() {
		return entrNo;
	}
	public void setEntrNo(String entrNo) {
		this.entrNo = entrNo;
	}
	public String getEntrName() {
		return entrName;
	}
	public void setEntrName(String entrName) {
		this.entrName = entrName;
	}

	public String getOpenStat() {
		return openStat;
	}

	public void setOpenStat(String openStat) {
		this.openStat = openStat;
	}

	public String getBusiDesc() {
		return busiDesc;
	}
	public void setBusiDesc(String busiDesc) {
		this.busiDesc = busiDesc;
	}
	public String getClrTp() {
		return clrTp;
	}
	public void setClrTp(String clrTp) {
		this.clrTp = clrTp;
	}
	public String getOpenGrpChnlNo() {
		return openGrpChnlNo;
	}
	public void setOpenGrpChnlNo(String openGrpChnlNo) {
		this.openGrpChnlNo = openGrpChnlNo;
	}
	public String getIntrmAcct() {
		return intrmAcct;
	}
	public void setIntrmAcct(String intrmAcct) {
		this.intrmAcct = intrmAcct;
	}
	public String getEntrAcctName() {
		return entrAcctName;
	}
	public void setEntrAcctName(String entrAcctName) {
		this.entrAcctName = entrAcctName;
	}
	public String getEntrAcct() {
		return entrAcct;
	}
	public void setEntrAcct(String entrAcct) {
		this.entrAcct = entrAcct;
	}
	public String getEntrAcctBank() {
		return entrAcctBank;
	}
	public void setEntrAcctBank(String entrAcctBank) {
		this.entrAcctBank = entrAcctBank;
	}
	public String getSignPat() {
		return signPat;
	}
	public void setSignPat(String signPat) {
		this.signPat = signPat;
	}
	public String getChkPat() {
		return chkPat;
	}
	public void setChkPat(String chkPat) {
		this.chkPat = chkPat;
	}
	public String getClrFeeTp() {
		return clrFeeTp;
	}
	public void setClrFeeTp(String clrFeeTp) {
		this.clrFeeTp = clrFeeTp;
	}
	public double getDefFrt() {
		return defFrt;
	}
	public void setDefFrt(double defFrt) {
		this.defFrt = defFrt;
	}
	public double getAmt() {
		return amt;
	}
	public void setAmt(double amt) {
		this.amt = amt;
	}
	public String getFeeTfOutAcct() {
		return feeTfOutAcct;
	}
	public void setFeeTfOutAcct(String feeTfOutAcct) {
		this.feeTfOutAcct = feeTfOutAcct;
	}
	public String getFeeTfInAcct() {
		return feeTfInAcct;
	}
	public void setFeeTfInAcct(String feeTfInAcct) {
		this.feeTfInAcct = feeTfInAcct;
	}
	public String getBusiTp() {
		return busiTp;
	}
	public void setBusiTp(String busiTp) {
		this.busiTp = busiTp;
	}
	public String getRelatSysNo() {
		return relatSysNo;
	}
	public void setRelatSysNo(String relatSysNo) {
		this.relatSysNo = relatSysNo;
	}
	public List<BusiSignParaDO> getKeyList() {
		return keyList;
	}
	public void setKeyList(List<BusiSignParaDO> keyList) {
		this.keyList = keyList;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getIntrmAcctName() {
		return intrmAcctName;
	}
	public void setIntrmAcctName(String intrmAcctName) {
		this.intrmAcctName = intrmAcctName;
	}
	public String getInOutBankFlg() {
		return inOutBankFlg;
	}
	public void setInOutBankFlg(String inOutBankFlg) {
		this.inOutBankFlg = inOutBankFlg;
	}
	public List<FProdBusiSubmitKLReqDTO> getKEY_LIST() {
		return KEY_LIST;
	}
	public void setKEY_LIST(List<FProdBusiSubmitKLReqDTO> kEY_LIST) {
		KEY_LIST = kEY_LIST;
	}
	@Override
	public List<String> getIgnoreFields() {
		List<String> ignoreFields = super.getIgnoreFields();
		ignoreFields.add("id");
		ignoreFields.add("crtr");
		ignoreFields.add("crtTime");
		ignoreFields.add("uptr");
		ignoreFields.add("uptTime");
		ignoreFields.add("rmrk");
		ignoreFields.add("delFlg");
		ignoreFields.add("keyList");
		ignoreFields.add("entrName");
		ignoreFields.add("action");
		return ignoreFields;
	}
	@Override
	public List<String> getMatchFields() {
		List<String> matchFields = new ArrayList<>();
		matchFields.add("busi_no");
		return matchFields;
	}
	
	
	
	
}
