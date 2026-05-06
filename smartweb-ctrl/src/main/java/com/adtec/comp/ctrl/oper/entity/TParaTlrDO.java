/**
* 系统名称: SmartWeb平台
* 模块名称: comp.ctrl.oper的实体类模块
* 功能描述: 柜员管理数据定义
* 类 名 称  : TParaTlrDO.java
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
 * 柜员管理
 * @author zhengjt
 * @version 20200310
 */
public class TParaTlrDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String brch;		// 机构
	private String tlrNo;		// 柜员号
	private String tlrName;		// 柜员名称
	private String tlrLvl;		// 柜员等级
	private String realTlrFlg;		// 标志
	private String lastUptTime;		// last_upt_time
	private String shortRmrk;		// short_rmrk
	private String midRmrk;		// mid_rmrk
	private String longRmrk;		// long_rmrk
	private String dac;		// dac
	
	public TParaTlrDO() {
		super();
	}

	public TParaTlrDO(String id){
		super(id);
	}

	@Length(min=1, max=20, message="机构长度必须介于 1 和 20 之间")
	public String getBrch() {
		return brch;
	}

	public void setBrch(String brch) {
		this.brch = brch;
	}
	
	@Length(min=1, max=16, message="柜员号长度必须介于 1 和 16 之间")
	public String getTlrNo() {
		return tlrNo;
	}

	public void setTlrNo(String tlrNo) {
		this.tlrNo = tlrNo;
	}
	
	@Length(min=0, max=120, message="柜员名称长度必须介于 0 和 120 之间")
	public String getTlrName() {
		return tlrName;
	}

	public void setTlrName(String tlrName) {
		this.tlrName = tlrName;
	}
	
	@Length(min=0, max=10, message="柜员等级长度必须介于 0 和 10 之间")
	public String getTlrLvl() {
		return tlrLvl;
	}

	public void setTlrLvl(String tlrLvl) {
		this.tlrLvl = tlrLvl;
	}
	
	@Length(min=0, max=2, message="real_tlr_flg长度必须介于 0 和 2 之间")
	public String getRealTlrFlg() {
		return realTlrFlg;
	}

	public void setRealTlrFlg(String realTlrFlg) {
		this.realTlrFlg = realTlrFlg;
	}

	
	
	@Length(min=1, max=20, message="last_upt_time长度必须介于 1 和 20 之间")
	public String getLastUptTime() {
		return lastUptTime;
	}

	public void setLastUptTime(String lastUptTime) {
		this.lastUptTime = lastUptTime;
	}
	
	@Length(min=1, max=64, message="short_rmrk长度必须介于 1 和 64 之间")
	public String getShortRmrk() {
		return shortRmrk;
	}

	public void setShortRmrk(String shortRmrk) {
		this.shortRmrk = shortRmrk;
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
		sb.append("TParaTlrDO [ ");
		sb.append("brch="+brch+" , ");
		sb.append("tlrNo="+tlrNo+" , ");
		sb.append("tlrName="+tlrName+" , ");
		sb.append("tlrLvl="+tlrLvl+" , ");
		sb.append("realTlrFlg="+realTlrFlg+" , ");
		sb.append("lastUptTime="+lastUptTime+" , ");
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
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		list.add("brch");
		list.add("tlrNo");
		return list;
	}
}