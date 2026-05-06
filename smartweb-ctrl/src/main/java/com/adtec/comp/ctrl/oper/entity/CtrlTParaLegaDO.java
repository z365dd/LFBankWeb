/**
* 系统名称: SmartWeb平台
* 模块名称: comp.ctrl.oper的实体类模块
* 功能描述: 法人管理数据定义
* 类 名 称  : CtrlTParaLegaDO.java
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
 * 法人管理
 * @author zhengjt
 * @version 20200310
 */
public class CtrlTParaLegaDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String legaNo;		// 法人号
	private String legaName;		// 法人名称
	private String tntNo;		// 租户号
	private String lastUptTime;		// last_upt_time
	private String shortRmrk;		// short_rmrk
	private String midRmrk;		// mid_rmrk
	private String longRmrk;		// long_rmrk
	private String dac;		// dac
	
	public CtrlTParaLegaDO() {
		super();
	}

	public CtrlTParaLegaDO(String id){
		super(id);
	}

	@Length(min=1, max=5, message="法人号长度必须介于 1 和 5 之间")
	public String getLegaNo() {
		return legaNo;
	}

	public void setLegaNo(String legaNo) {
		this.legaNo = legaNo;
	}
	
	@Length(min=0, max=120, message="法人名称长度必须介于 0 和 120 之间")
	public String getLegaName() {
		return legaName;
	}

	public void setLegaName(String legaName) {
		this.legaName = legaName;
	}
	
	@Length(min=0, max=20, message="租户号长度必须介于 0 和 20 之间")
	public String getTntNo() {
		return tntNo;
	}

	public void setTntNo(String tntNo) {
		this.tntNo = tntNo;
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
		sb.append("CtrlTParaLegaDO [ ");
		sb.append("legaNo="+legaNo+" , ");
		sb.append("legaName="+legaName+" , ");
		sb.append("tntNo="+tntNo+" , ");
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
		list.add("legaNo");
		return list;
	}
}