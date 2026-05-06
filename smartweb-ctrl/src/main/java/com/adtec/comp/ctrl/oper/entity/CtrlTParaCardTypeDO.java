/**
* 系统名称: SmartWeb平台
* 模块名称: comp.ctrl.oper的实体类模块
* 功能描述: 卡种类管理数据定义
* 类 名 称  : CtrlTParaCardTypeDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200310<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.comp.ctrl.oper.entity;

import org.hibernate.validator.constraints.Length;
import java.util.ArrayList;
import java.util.List;
import com.adtec.sys.common.persistence.BaseDO;

/**
 * 卡种类管理
 * @author zhengjt
 * @version 20200310
 */
public class CtrlTParaCardTypeDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String cardBinNo;		// 卡BIN号
	private String cardTp;		// 卡类型
	private String acctCardFlg;		// 账号卡号标志
	private String intOutBankFlg;		// 内部外部的行号标志
	private String clrBank;		// 清算行号
	private String clrBankName;		// 清算银行名称
	private String hostSys;		// 核心系统
	private String legaNo;		// 法人号
	private String stat;		// 状态
	private String lastUptTime;		// last_upt_time
	private String shortRmrk;		// 短备注
	private String midRmrk;		// 中备注
	private String longRmrk;		// 长备注
	private String dac;		// DAC
	private String cardTpName;		// 卡类型名称
	
	public CtrlTParaCardTypeDO() {
		super();
	}

	public CtrlTParaCardTypeDO(String id){
		super(id);
	}

	@Length(min=1, max=20, message="卡BIN号长度必须介于 1 和 20 之间")
	public String getCardBinNo() {
		return cardBinNo;
	}

	public void setCardBinNo(String cardBinNo) {
		this.cardBinNo = cardBinNo;
	}
	
	@Length(min=0, max=5, message="卡类型长度必须介于 0 和 5 之间")
	public String getCardTp() {
		return cardTp;
	}

	public void setCardTp(String cardTp) {
		this.cardTp = cardTp;
	}
	
	@Length(min=0, max=2, message="账号卡号标志长度必须介于 0 和 2 之间")
	public String getAcctCardFlg() {
		return acctCardFlg;
	}

	public void setAcctCardFlg(String acctCardFlg) {
		this.acctCardFlg = acctCardFlg;
	}
	
	@Length(min=1, max=2, message="内部外部的行号标志长度必须介于 1 和 2 之间")
	public String getIntOutBankFlg() {
		return intOutBankFlg;
	}

	public void setIntOutBankFlg(String intOutBankFlg) {
		this.intOutBankFlg = intOutBankFlg;
	}
	
	@Length(min=0, max=20, message="清算行号长度必须介于 0 和 20 之间")
	public String getClrBank() {
		return clrBank;
	}

	public void setClrBank(String clrBank) {
		this.clrBank = clrBank;
	}
	
	@Length(min=0, max=280, message="清算银行名称长度必须介于 0 和 280 之间")
	public String getClrBankName() {
		return clrBankName;
	}

	public void setClrBankName(String clrBankName) {
		this.clrBankName = clrBankName;
	}
	
	@Length(min=0, max=10, message="核心系统长度必须介于 0 和 10 之间")
	public String getHostSys() {
		return hostSys;
	}

	public void setHostSys(String hostSys) {
		this.hostSys = hostSys;
	}
	
	@Length(min=0, max=5, message="法人号长度必须介于 0 和 5 之间")
	public String getLegaNo() {
		return legaNo;
	}

	public void setLegaNo(String legaNo) {
		this.legaNo = legaNo;
	}
	
	@Length(min=0, max=5, message="状态长度必须介于 0 和 5 之间")
	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}
	
	@Length(min=1, max=20, message="last_upt_time长度必须介于 1 和 20 之间")
	public String getLastUptTime() {
		return lastUptTime;
	}

	public void setLastUptTime(String lastUptTime) {
		this.lastUptTime = lastUptTime;
	}
	
	@Length(min=1, max=64, message="短备注长度必须介于 1 和 64 之间")
	public String getShortRmrk() {
		return shortRmrk;
	}

	public void setShortRmrk(String shortRmrk) {
		this.shortRmrk = shortRmrk;
	}
	
	@Length(min=1, max=128, message="中备注长度必须介于 1 和 128 之间")
	public String getMidRmrk() {
		return midRmrk;
	}

	public void setMidRmrk(String midRmrk) {
		this.midRmrk = midRmrk;
	}
	
	@Length(min=1, max=256, message="长备注长度必须介于 1 和 256 之间")
	public String getLongRmrk() {
		return longRmrk;
	}

	public void setLongRmrk(String longRmrk) {
		this.longRmrk = longRmrk;
	}
	
	@Length(min=1, max=16, message="DAC长度必须介于 1 和 16 之间")
	public String getDac() {
		return dac;
	}

	public void setDac(String dac) {
		this.dac = dac;
	}
	
	@Length(min=0, max=120, message="卡类型名称长度必须介于 0 和 120 之间")
	public String getCardTpName() {
		return cardTpName;
	}

	public void setCardTpName(String cardTpName) {
		this.cardTpName = cardTpName;
	}
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("CtrlTParaCardTypeDO [ ");
		sb.append("cardBinNo="+cardBinNo+" , ");
		sb.append("cardTp="+cardTp+" , ");
		sb.append("acctCardFlg="+acctCardFlg+" , ");
		sb.append("intOutBankFlg="+intOutBankFlg+" , ");
		sb.append("clrBank="+clrBank+" , ");
		sb.append("clrBankName="+clrBankName+" , ");
		sb.append("hostSys="+hostSys+" , ");
		sb.append("legaNo="+legaNo+" , ");
		sb.append("stat="+stat+" , ");
		sb.append("lastUptTime="+lastUptTime+" , ");
		sb.append("shortRmrk="+shortRmrk+" , ");
		sb.append("midRmrk="+midRmrk+" , ");
		sb.append("longRmrk="+longRmrk+" , ");
		sb.append("dac="+dac+" , ");
		sb.append("cardTpName="+cardTpName+" , ");
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
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		list.add("cardBinNo");
		return list;
	}
}