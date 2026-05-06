/**
* 系统名称: SmartWeb平台
* 模块名称: comp.fsvr.om的实体类模块
* 功能描述: 组件表操作数据定义
* 类 名 称  : FsvrTPipCompDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200824<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.comp.fsvr.om.entity;

import org.hibernate.validator.constraints.Length;
import java.util.ArrayList;
import java.util.List;
import com.adtec.sys.common.persistence.BaseDO;

/**
 * 组件表操作
 * @author zhengjt
 * @version 20200824
 */
public class FsvrTPipCompDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String compNo;		// 组件号
	private String compName;		// 组件名称
	private String prodCompTp;		// 标志
	private String lastUptTime;		// 最后更新时间
	private String shortRmrk;		// 短备注
	private String midRmrk;		// 中备注
	private String longRmrk;		// 长备注
	private String dac;		// DAC
	private String openStat;		// open_stat
	
	public FsvrTPipCompDO() {
		super();
	}

	public FsvrTPipCompDO(String id){
		super(id);
	}

	@Length(min=1, max=6, message="组件号长度必须介于 1 和 6 之间")
	public String getCompNo() {
		return compNo;
	}

	public void setCompNo(String compNo) {
		this.compNo = compNo;
	}
	
	@Length(min=1, max=120, message="组件名称长度必须介于 1 和 120 之间")
	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}
	
	@Length(min=1, max=2, message="标志长度必须介于 1 和 2 之间")
	public String getProdCompTp() {
		return prodCompTp;
	}

	public void setProdCompTp(String prodCompTp) {
		this.prodCompTp = prodCompTp;
	}
	
	@Length(min=0, max=20, message="最后更新时间长度必须介于 0 和 20 之间")
	public String getLastUptTime() {
		return lastUptTime;
	}

	public void setLastUptTime(String lastUptTime) {
		this.lastUptTime = lastUptTime;
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
	
	@Length(min=0, max=2, message="open_stat长度必须介于 0 和 2 之间")
	public String getOpenStat() {
		return openStat;
	}

	public void setOpenStat(String openStat) {
		this.openStat = openStat;
	}
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("FsvrTPipCompDO [ ");
		sb.append("compNo="+compNo+" , ");
		sb.append("compName="+compName+" , ");
		sb.append("prodCompTp="+prodCompTp+" , ");
		sb.append("lastUptTime="+lastUptTime+" , ");
		sb.append("shortRmrk="+shortRmrk+" , ");
		sb.append("midRmrk="+midRmrk+" , ");
		sb.append("longRmrk="+longRmrk+" , ");
		sb.append("dac="+dac+" , ");
		sb.append("openStat="+openStat+" , ");
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
		return list;
	}
}