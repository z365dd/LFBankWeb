/**
* 系统名称: SmartWeb平台
* 模块名称: comp.prod.oper的实体类模块
* 功能描述: 业务表数据定义
* 类 名 称  : TPipBusiDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200114<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.prod.oper.entity;

import com.adtec.sys.common.persistence.BaseDO;

import java.util.ArrayList;
import java.util.List;

/**
 * 业务表
 * @author zh
 * @version 20200114
 */
public class TPipBusiDO extends BaseDO {

	private static final long serialVersionUID = 1L;
	private String busiNo;		// 业务编号
	private String entrNo;		// 单位编号
	private String entrName;		// 单位名称
	private String busiName;		// 业务名称
	private String saleProdCode;		// 可售产品代码
	private String openStat;		// 状态
	private String busiDesc;		// 业务描述
	private String clrTp;		// 清算类型
	private String signPat;		// 是否校验签约
	private String chkPat;		// 对账标志
	private String feeTp;		// 清算手续费标志
	private String busiTp;		// 业务类型
	private String relatSys;		// 关联系统号
	private String shortRmrk;		// 短备注  -> 过渡账户开户机构
	private String midRmrk;		// 中备注
	private String longRmrk;		// 长备注
	private String dac;		// DAC
	//private String openGrpChnlNo;
	private String action;
	private List<TPipCompSvcParaDOTemp> SALEPROD = new ArrayList<TPipCompSvcParaDOTemp>();
	
	private List<TPipBusiChnlOpenDO> openGrpChnlNo = new ArrayList<TPipBusiChnlOpenDO>();
	private List<TPipBusiBrchOpenDO> openGrpBrch = new ArrayList<TPipBusiBrchOpenDO>();

	private String BUSI_NO;
	private String BUSI_NAME;
	private String ENTR_NO;
	private String ENTR_NAME;
	private String PROD_LINE_CODE;

	private String signChkFlg;
	private String clrFeeTp;
	private String relatSysNo;
	
	// 商户清算模式-本金
	private String intrmAcct;//过度账户
	private String intrmAcctName;//过度账户名
	private String inOutBankFlg;//账号跨行标志
	private String entrAcct;//清算账户/客户账号
	private String entrAcctName;//清算账户名/客户账号名
	private String entrAcctBank;//账号所属行号
	private String isClrFlg;//是否对账清算

	// 商户清算模式-手续费
	private int defFrt;//手续费费率
	private double amt;//每笔金额
	private String feeTfOutAcct;//手续费付款账号
	private String feeTfInAcct;//手续费收款账号
	
	private String FILE_PATH1;
	private String FILE_PATH2;
	private String FILE_PATH3;

	private String fileTp;
	private String fileName;
	private String url;
	private String prevPath;
	private String fileSuffix;

	private String bgImg; // 单位图片

	private String brchTp;//部门类型
	private String brchName;//部门名称
	private String officeCode;//机构
	private String officeName;
	private String compNo;		// 组件号
	private String svcCode;
	private String brchId;
	private String brchIdName;

	public TPipBusiDO() {
		super();
	}

	public TPipBusiDO(String id){
		super(id);
	}

	public String getBusiNo() {
		return busiNo;
	}

	public void setBusiNo(String busiNo) {
		this.busiNo = busiNo;
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

	public String getFeeTp() {
		return feeTp;
	}

	public void setFeeTp(String feeTp) {
		this.feeTp = feeTp;
	}

	public String getBusiTp() {
		return busiTp;
	}

	public void setBusiTp(String busiTp) {
		this.busiTp = busiTp;
	}

	public String getRelatSys() {
		return relatSys;
	}

	public void setRelatSys(String relatSys) {
		this.relatSys = relatSys;
	}

	public String getShortRmrk() {
		return shortRmrk;
	}

	public void setShortRmrk(String shortRmrk) {
		this.shortRmrk = shortRmrk;
	}
	
	public String getMidRmrk() {
		return midRmrk;
	}

	public void setMidRmrk(String midRmrk) {
		this.midRmrk = midRmrk;
	}

	public String getLongRmrk() {
		return longRmrk;
	}

	public void setLongRmrk(String longRmrk) {
		this.longRmrk = longRmrk;
	}

	public String getDac() {
		return dac;
	}

	public void setDac(String dac) {
		this.dac = dac;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<TPipCompSvcParaDOTemp> getSALEPROD() {
		return SALEPROD;
	}

	public void setSALEPROD(List<TPipCompSvcParaDOTemp> SALEPROD) {
		this.SALEPROD = SALEPROD;
	}

	public String getBUSI_NO() {
		return BUSI_NO;
	}

	public void setBUSI_NO(String BUSI_NO) {
		this.BUSI_NO = BUSI_NO;
	}

	public String getBUSI_NAME() {
		return BUSI_NAME;
	}

	public void setBUSI_NAME(String BUSI_NAME) {
		this.BUSI_NAME = BUSI_NAME;
	}

	public String getENTR_NO() {
		return ENTR_NO;
	}

	public void setENTR_NO(String ENTR_NO) {
		this.ENTR_NO = ENTR_NO;
	}

	public String getENTR_NAME() {
		return ENTR_NAME;
	}

	public void setENTR_NAME(String ENTR_NAME) {
		this.ENTR_NAME = ENTR_NAME;
	}

	public String getPROD_LINE_CODE() {
		return PROD_LINE_CODE;
	}

	public void setPROD_LINE_CODE(String PROD_LINE_CODE) {
		this.PROD_LINE_CODE = PROD_LINE_CODE;
	}

	public String getIntrmAcct() {
		return intrmAcct;
	}

	public void setIntrmAcct(String intrmAcct) {
		this.intrmAcct = intrmAcct;
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

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("TPipBusiDO [ ");
		sb.append("busiNo="+busiNo+" , ");
		sb.append("entrNo="+entrNo+" , ");
		sb.append("busiName="+busiName+" , ");
		sb.append("saleProdCode="+saleProdCode+" , ");
		sb.append("openStat="+openStat+" , ");
		sb.append("busiDesc="+busiDesc+" , ");
		sb.append("clrTp="+clrTp+" , ");
		sb.append("signPat="+signPat+" , ");
		sb.append("chkPat="+chkPat+" , ");
		sb.append("feeTp="+feeTp+" , ");
		sb.append("busiTp="+busiTp+" , ");
		sb.append("relatSys="+relatSys+" , ");
		sb.append("shortRmrk="+shortRmrk+" , ");
		sb.append("midRmrk="+midRmrk+" , ");
		sb.append("longRmrk="+longRmrk+" , ");
		sb.append("dac="+dac+" , ");
		sb.append(" ] ");
		return sb.toString();
	}

	public List<String> getIgnoreFields() {
		List<String> ignoreFields = super.getIgnoreFields();
		ignoreFields.add("serialVersionUID");
		ignoreFields.add("DEL_FLAG_NORMAL");
		ignoreFields.add("DEL_FLAG_DELETE");
		ignoreFields.add("id");
		ignoreFields.add("rmrk");
		ignoreFields.add("delFlg");
		ignoreFields.add("action");
		ignoreFields.add("entrName");
		ignoreFields.add("keyList");
		ignoreFields.add("KEY_LIST");
		ignoreFields.add("SALEPROD");
		ignoreFields.add("BUSI_NO");
		ignoreFields.add("BUSI_NAME");
		ignoreFields.add("ENTR_NO");
		ignoreFields.add("ENTR_NAME");
		ignoreFields.add("PROD_LINE_CODE");
		ignoreFields.add("intrmAcct");
		ignoreFields.add("intrmAcctName");
		ignoreFields.add("inOutBankFlg");
		ignoreFields.add("entrAcct");
		ignoreFields.add("entrAcctName");
		ignoreFields.add("entrAcctBank");
		ignoreFields.add("isClrFlg");

		ignoreFields.add("defFrt");
		ignoreFields.add("amt");
		ignoreFields.add("feeTfOutAcct");
		ignoreFields.add("feeTfInAcct");
		
		ignoreFields.add("signChkFlg");
		ignoreFields.add("clrFeeTp");
		ignoreFields.add("relatSysNo");

		ignoreFields.add("FILE_PATH1");
		ignoreFields.add("FILE_PATH2");
		ignoreFields.add("FILE_PATH3");


		ignoreFields.add("openGrpChnlNo");
		ignoreFields.add("openGrpBrch");
		
		ignoreFields.add("fileTp");
		ignoreFields.add("fileName");
		ignoreFields.add("url");
		ignoreFields.add("prevPath");
		ignoreFields.add("fileSuffix");

		ignoreFields.add("brchName");
		ignoreFields.add("officeCode");
		ignoreFields.add("officeName");
		ignoreFields.add("compNo");
		ignoreFields.add("svcCode");
		ignoreFields.add("brchIdName");
		return ignoreFields;
	}

	public List<String> getMatchFields(){
		//设置更新匹配条件
		List<String> matchField = new ArrayList<String>();
		matchField.add("busiNo");
		return matchField;
	}

	public String getIsClrFlg() {
		return isClrFlg;
	}

	public void setIsClrFlg(String isClrFlg) {
		this.isClrFlg = isClrFlg;
	}

	public String getSignChkFlg() {
		return signChkFlg;
	}

	public void setSignChkFlg(String signChkFlg) {
		this.signChkFlg = signChkFlg;
	}

	public String getClrFeeTp() {
		return clrFeeTp;
	}

	public void setClrFeeTp(String clrFeeTp) {
		this.clrFeeTp = clrFeeTp;
	}

	public String getRelatSysNo() {
		return relatSysNo;
	}

	public void setRelatSysNo(String relatSysNo) {
		this.relatSysNo = relatSysNo;
	}

	public String getFILE_PATH1() {
		return FILE_PATH1;
	}

	public void setFILE_PATH1(String FILE_PATH1) {
		this.FILE_PATH1 = FILE_PATH1;
	}

	public String getFILE_PATH2() {
		return FILE_PATH2;
	}

	public void setFILE_PATH2(String FILE_PATH2) {
		this.FILE_PATH2 = FILE_PATH2;
	}

	public String getFILE_PATH3() {
		return FILE_PATH3;
	}

	public void setFILE_PATH3(String FILE_PATH3) {
		this.FILE_PATH3 = FILE_PATH3;
	}

	public List<TPipBusiChnlOpenDO> getOpenGrpChnlNo() {
		return openGrpChnlNo;
	}

	public void setOpenGrpChnlNo(List<TPipBusiChnlOpenDO> openGrpChnlNo) {
		this.openGrpChnlNo = openGrpChnlNo;
	}

	public List<TPipBusiBrchOpenDO> getOpenGrpBrch() {
		return openGrpBrch;
	}

	public void setOpenGrpBrch(List<TPipBusiBrchOpenDO> openGrpBrch) {
		this.openGrpBrch = openGrpBrch;
	}

	public String getFileTp() {
		return fileTp;
	}

	public void setFileTp(String fileTp) {
		this.fileTp = fileTp;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPrevPath() {
		return prevPath;
	}

	public void setPrevPath(String prevPath) {
		this.prevPath = prevPath;
	}

	public String getFileSuffix() {
		return fileSuffix;
	}

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	public String getBgImg() {
		return bgImg;
	}

	public void setBgImg(String bgImg) {
		this.bgImg = bgImg;
	}

	public String getBrchTp() {
		return brchTp;
	}

	public void setBrchTp(String brchTp) {
		this.brchTp = brchTp;
	}

	public String getBrchName() {
		return brchName;
	}

	public void setBrchName(String brchName) {
		this.brchName = brchName;
	}

	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getCompNo() {
		return compNo;
	}

	public void setCompNo(String compNo) {
		this.compNo = compNo;
	}

	public String getSvcCode() {
		return svcCode;
	}

	public void setSvcCode(String svcCode) {
		this.svcCode = svcCode;
	}

	public String getBrchId() {
		return brchId;
	}

	public void setBrchId(String brchId) {
		this.brchId = brchId;
	}

	public String getBrchIdName() {
		return brchIdName;
	}

	public void setBrchIdName(String brchIdName) {
		this.brchIdName = brchIdName;
	}

	public int getDefFrt() {
		return defFrt;
	}

	public void setDefFrt(int defFrt) {
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
	
}