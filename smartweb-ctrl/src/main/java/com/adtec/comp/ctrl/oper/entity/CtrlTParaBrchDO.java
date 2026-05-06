/**
* 系统名称: SmartWeb平台
* 模块名称: comp.ctrl.oper的实体类模块
* 功能描述: 机构管理数据定义
* 类 名 称  : CtrlTParaBrchDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200311<br>
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
 * 机构管理
 * @author zhengjt
 * @version 20200311
 */
public class CtrlTParaBrchDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String brch;		// 机构
	private String brchName;		// 机构名
	private String brchLvlNo;		// 机构等级
	private String upBrch;		// 上级机构
	private String legaNo;		// 法人号
	private String tntNo;		// 租户号
	private String bank;		// 行号
	private String bankName;		// 银行名称
	private String lastUptTime;		// 最后更新时间
	private String shorkRmrk;		// short_rmrk
	private String midRmrk;		// mid_rmrk
	private String longRmrk;		// long_rmrk
	private String dac;		// dac
	
	public CtrlTParaBrchDO() {
		super();
	}

	public CtrlTParaBrchDO(String id){
		super(id);
	}

	@Length(min=1, max=20, message="机构长度必须介于 1 和 20 之间")
	public String getBrch() {
		return brch;
	}

	public void setBrch(String brch) {
		this.brch = brch;
	}
	
	@Length(min=0, max=120, message="机构名长度必须介于 0 和 120 之间")
	public String getBrchName() {
		return brchName;
	}

	public void setBrchName(String brchName) {
		this.brchName = brchName;
	}
	
	@Length(min=0, max=10, message="机构等级长度必须介于 0 和 10 之间")
	public String getBrchLvlNo() {
		return brchLvlNo;
	}

	public void setBrchLvlNo(String brchLvlNo) {
		this.brchLvlNo = brchLvlNo;
	}
	
	@Length(min=0, max=20, message="上级机构长度必须介于 0 和 20 之间")
	public String getUpBrch() {
		return upBrch;
	}

	public void setUpBrch(String upBrch) {
		this.upBrch = upBrch;
	}
	
	@Length(min=0, max=5, message="法人号长度必须介于 0 和 5 之间")
	public String getLegaNo() {
		return legaNo;
	}

	public void setLegaNo(String legaNo) {
		this.legaNo = legaNo;
	}
	
	@Length(min=0, max=20, message="租户号长度必须介于 0 和 20 之间")
	public String getTntNo() {
		return tntNo;
	}

	public void setTntNo(String tntNo) {
		this.tntNo = tntNo;
	}
	
	@Length(min=0, max=20, message="行号长度必须介于 0 和 20 之间")
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}
	
	@Length(min=0, max=120, message="银行名称长度必须介于 0 和 120 之间")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Length(min=1, max=20, message="最后更新时间长度必须介于 1 和 20 之间")
	public String getLastUptTime() {
		return lastUptTime;
	}

	public void setLastUptTime(String lastUptTime) {
		this.lastUptTime = lastUptTime;
	}
	
	@Length(min=1, max=64, message="short_rmrk长度必须介于 1 和 64 之间")
	public String getShorkRmrk() {
		return shorkRmrk;
	}

	public void setShorkRmrk(String shorkRmrk) {
		this.shorkRmrk = shorkRmrk;
	}
	
	@Length(min=1, max=128, message="mid_rmrk长度必须介于 1 和 128 之间")
	public String getMidRmrk() {
		return midRmrk;
	}

	public void setMidRmrk(String midRmrk) {
		this.midRmrk = midRmrk;
	}
	
	@Length(min=1, max=256, message="long_rmrk长度必须介于 1 和 256 之间")
	public String getLongRmrk() {
		return longRmrk;
	}

	public void setLongRmrk(String longRmrk) {
		this.longRmrk = longRmrk;
	}
	
	@Length(min=1, max=16, message="dac长度必须介于 1 和 16 之间")
	public String getDac() {
		return dac;
	}

	public void setDac(String dac) {
		this.dac = dac;
	}
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("CtrlTParaBrchDO [ ");
		sb.append("brch="+brch+" , ");
		sb.append("brchName="+brchName+" , ");
		sb.append("brchLvlNo="+brchLvlNo+" , ");
		sb.append("upBrch="+upBrch+" , ");
		sb.append("legaNo="+legaNo+" , ");
		sb.append("tntNo="+tntNo+" , ");
		sb.append("bank="+bank+" , ");
		sb.append("bankName="+bankName+" , ");
		sb.append("lastUptTime="+lastUptTime+" , ");
		sb.append("shorkRmrk="+shorkRmrk+" , ");
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
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		list.add("brch");
		return list;
	}
}