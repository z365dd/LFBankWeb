/**
* 系统名称: SmartWeb平台
* 模块名称: comp.prod.oper的实体类模块
* 功能描述: 签约规则表数据定义
* 类 名 称  : TPipSignRuleDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200303<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.prod.oper.entity;

import org.hibernate.validator.constraints.Length;
import java.util.ArrayList;
import java.util.List;
import com.adtec.sys.common.persistence.BaseDO;

/**
 * 签约规则表
 * @author zh
 * @version 20200303
 */
public class TPipSignRuleDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String ruleId;		// 规则ID
	private String ruleDesc;		// 规则描述
	private String signFlg;		// 签约标志
	private String signTp;		// 签约类型
	private String noteTp;		// 通知类型
	private String acctStatList;		// 账户状态列表
	private String vrfyAcctInfoFlg;		// 核对账号信息标志
	private String vrfyAcctNameFlg;		// 核对账号名称标志
	private String vrfyCertFlg;		// 验证证件标志
	private String vrfyPhoneFlg;		// 验证手机标志
	private String vrfyOpenAcctBrchFlg;		// 核对开户机构标志
	private String vrfyModBrchFlg;		// 核对修改机构标志
	private String vrfyCanclBrchFlg;		// 核对撤销机构标志
	private String custDefLimFlg;		// 客户自定义限额标志
	private String acctJrnlNoteFlg;		// 账户流水通知
	private String compNo;		// 组件号
	private String modlSvcCode;		// 模型服务码
	private String shortRmrk;		// 短备注
	private String midRmrk;		// 中备注
	private String longRmrk;		// 长备注
	private String dac;		// DAC
	
	
	public String getAcctJrnlNoteFlg() {
		return acctJrnlNoteFlg;
	}

	public void setAcctJrnlNoteFlg(String acctJrnlNoteFlg) {
		this.acctJrnlNoteFlg = acctJrnlNoteFlg;
	}

	public String getCompNo() {
		return compNo;
	}

	public void setCompNo(String compNo) {
		this.compNo = compNo;
	}

	public String getModlSvcCode() {
		return modlSvcCode;
	}

	public void setModlSvcCode(String modlSvcCode) {
		this.modlSvcCode = modlSvcCode;
	}

	public String getVrfyAcctInfoFlg() {
		return vrfyAcctInfoFlg;
	}

	public void setVrfyAcctInfoFlg(String vrfyAcctInfoFlg) {
		this.vrfyAcctInfoFlg = vrfyAcctInfoFlg;
	}

	public String getVrfyOpenAcctBrchFlg() {
		return vrfyOpenAcctBrchFlg;
	}

	public void setVrfyOpenAcctBrchFlg(String vrfyOpenAcctBrchFlg) {
		this.vrfyOpenAcctBrchFlg = vrfyOpenAcctBrchFlg;
	}

	public TPipSignRuleDO() {
		super();
	}

	public TPipSignRuleDO(String id){
		super(id);
	}

	@Length(min=1, max=64, message="规则ID长度必须介于 1 和 64 之间")
	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	
	@Length(min=1, max=360, message="规则描述长度必须介于 1 和 360 之间")
	public String getRuleDesc() {
		return ruleDesc;
	}

	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}
	
	@Length(min=0, max=2, message="签约标志长度必须介于 0 和 2 之间")
	public String getSignFlg() {
		return signFlg;
	}

	public void setSignFlg(String signFlg) {
		this.signFlg = signFlg;
	}
	
	@Length(min=0, max=2, message="签约类型长度必须介于 0 和 2 之间")
	public String getSignTp() {
		return signTp;
	}

	public void setSignTp(String signTp) {
		this.signTp = signTp;
	}
	
	@Length(min=0, max=2, message="通知类型长度必须介于 0 和 2 之间")
	public String getNoteTp() {
		return noteTp;
	}

	public void setNoteTp(String noteTp) {
		this.noteTp = noteTp;
	}
	
	@Length(min=0, max=255, message="账户状态列表长度必须介于 0 和 255 之间")
	public String getAcctStatList() {
		return acctStatList;
	}

	public void setAcctStatList(String acctStatList) {
		this.acctStatList = acctStatList;
	}
	
	@Length(min=0, max=2, message="核对账号名称标志长度必须介于 0 和 2 之间")
	public String getVrfyAcctNameFlg() {
		return vrfyAcctNameFlg;
	}

	public void setVrfyAcctNameFlg(String vrfyAcctNameFlg) {
		this.vrfyAcctNameFlg = vrfyAcctNameFlg;
	}
	
	@Length(min=0, max=2, message="验证证件标志长度必须介于 0 和 2 之间")
	public String getVrfyCertFlg() {
		return vrfyCertFlg;
	}

	public void setVrfyCertFlg(String vrfyCertFlg) {
		this.vrfyCertFlg = vrfyCertFlg;
	}
	
	@Length(min=0, max=2, message="验证手机标志长度必须介于 0 和 2 之间")
	public String getVrfyPhoneFlg() {
		return vrfyPhoneFlg;
	}

	public void setVrfyPhoneFlg(String vrfyPhoneFlg) {
		this.vrfyPhoneFlg = vrfyPhoneFlg;
	}
	
	@Length(min=0, max=2, message="核对修改机构标志长度必须介于 0 和 2 之间")
	public String getVrfyModBrchFlg() {
		return vrfyModBrchFlg;
	}

	public void setVrfyModBrchFlg(String vrfyModBrchFlg) {
		this.vrfyModBrchFlg = vrfyModBrchFlg;
	}
	
	@Length(min=0, max=2, message="核对撤销机构标志长度必须介于 0 和 2 之间")
	public String getVrfyCanclBrchFlg() {
		return vrfyCanclBrchFlg;
	}

	public void setVrfyCanclBrchFlg(String vrfyCanclBrchFlg) {
		this.vrfyCanclBrchFlg = vrfyCanclBrchFlg;
	}
	
	@Length(min=0, max=2, message="客户自定义限额标志长度必须介于 0 和 2 之间")
	public String getCustDefLimFlg() {
		return custDefLimFlg;
	}

	public void setCustDefLimFlg(String custDefLimFlg) {
		this.custDefLimFlg = custDefLimFlg;
	}
	
	@Length(min=0, max=64, message="短备注长度必须介于 0 和 64 之间")
	public String getShortRmrk() {
		return shortRmrk;
	}

	public void setShortRmrk(String shortRmrk) {
		this.shortRmrk = shortRmrk;
	}
	
	@Length(min=0, max=128, message="中备注长度必须介于 0 和 128 之间")
	public String getMidRmrk() {
		return midRmrk;
	}

	public void setMidRmrk(String midRmrk) {
		this.midRmrk = midRmrk;
	}
	
	@Length(min=0, max=256, message="长备注长度必须介于 0 和 256 之间")
	public String getLongRmrk() {
		return longRmrk;
	}

	public void setLongRmrk(String longRmrk) {
		this.longRmrk = longRmrk;
	}
	
	@Length(min=0, max=16, message="DAC长度必须介于 0 和 16 之间")
	public String getDac() {
		return dac;
	}

	public void setDac(String dac) {
		this.dac = dac;
	}
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("TPipSignRuleDO [ ");
		sb.append("ruleId="+ruleId+" , ");
		sb.append("ruleDesc="+ruleDesc+" , ");
		sb.append("signFlg="+signFlg+" , ");
		sb.append("signTp="+signTp+" , ");
		sb.append("noteTp="+noteTp+" , ");
		sb.append("acctStatList="+acctStatList+" , ");
		sb.append("vrfyAcctNameFlg="+vrfyAcctNameFlg+" , ");
		sb.append("vrfyCertFlg="+vrfyCertFlg+" , ");
		sb.append("vrfyPhoneFlg="+vrfyPhoneFlg+" , ");
		sb.append("vrfyModBrchFlg="+vrfyModBrchFlg+" , ");
		sb.append("vrfyCanclBrchFlg="+vrfyCanclBrchFlg+" , ");
		sb.append("custDefLimFlg="+custDefLimFlg+" , ");
		sb.append("acctJrnlNoteFlg="+acctJrnlNoteFlg+" , ");
		sb.append("compNo="+compNo+" , ");
		sb.append("modlSvcCode="+modlSvcCode+" , ");
		sb.append("shortRmrk="+shortRmrk+" , ");
		sb.append("midRmrk="+midRmrk+" , ");
		sb.append("longRmrk="+longRmrk+" , ");
		sb.append("dac="+dac+" , ");
		sb.append(" ] ");
		return sb.toString();
	}
	
	@Override
	public List<String> getIgnoreFields() {
		// TODO Auto-generated method stub
		List<String> list = super.getIgnoreFields();
		list.add("delFlg");
		list.add("id");
		list.add("crtr");
		list.add("crtTime");
		list.add("uptr");
		list.add("uptTime");
		list.add("rmrk");
		return list;
	}
	
	@Override
	public List<String> getMatchFields() {
		List<String> list = new ArrayList<String>();
		list.add("ruleId");
		return list;
	}
}