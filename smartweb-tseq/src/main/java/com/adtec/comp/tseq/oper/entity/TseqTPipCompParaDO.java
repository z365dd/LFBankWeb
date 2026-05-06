/**
* 系统名称: SmartWeb平台
* 模块名称: comp.tseq.oper的实体类模块
* 功能描述: 组件属性表数据定义
* 类 名 称  : TseqTPipCompParaDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200804<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.comp.tseq.oper.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import com.adtec.sys.common.persistence.BaseDO;

/**
 * 组件属性表
 * @author zhengjt
 * @version 20200804
 */
public class TseqTPipCompParaDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String compNo;		// 组件号
	private String compName;		// 组件名称
	private String keyTp;		// 键类型
	private String keyNo;		// 键编号
	private String keyName;		// 键名称
	private String kv;		// 键值
	private String keyDesc;		// 描述说明
	private Long ser;		// 序号
	private String flg;		// 标志
	private String lastUptTime;		// 更新时间
	private String shortRmrk;		// 短备注
	private String midRmrk;		// 中备注
	private String longRmrk;		// 长备注
	private String dac;		// DAC
	
	public TseqTPipCompParaDO() {
		super();
	}

	public TseqTPipCompParaDO(String id){
		super(id);
	}

	@Length(min=1, max=6, message="组件号长度必须介于 1 和 6 之间")
	public String getCompNo() {
		return compNo;
	}

	public void setCompNo(String compNo) {
		this.compNo = compNo;
	}
	
	@Length(min=0, max=120, message="组件名称长度必须介于 0 和 120 之间")
	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}
	
	@Length(min=1, max=2, message="键类型长度必须介于 1 和 2 之间")
	public String getKeyTp() {
		return keyTp;
	}

	public void setKeyTp(String keyTp) {
		this.keyTp = keyTp;
	}
	
	@Length(min=1, max=64, message="键编号长度必须介于 1 和 64 之间")
	public String getKeyNo() {
		return keyNo;
	}

	public void setKeyNo(String keyNo) {
		this.keyNo = keyNo;
	}
	
	@Length(min=0, max=120, message="键名称长度必须介于 0 和 120 之间")
	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	
	@Length(min=0, max=256, message="键值长度必须介于 0 和 256 之间")
	public String getKv() {
		return kv;
	}

	public void setKv(String kv) {
		this.kv = kv;
	}
	
	@Length(min=0, max=360, message="描述说明长度必须介于 0 和 360 之间")
	public String getKeyDesc() {
		return keyDesc;
	}

	public void setKeyDesc(String keyDesc) {
		this.keyDesc = keyDesc;
	}
	
	@NotNull(message="序号不能为空")
	public Long getSer() {
		return ser;
	}

	public void setSer(Long ser) {
		this.ser = ser;
	}
	
	@Length(min=1, max=2, message="标志长度必须介于 1 和 2 之间")
	public String getFlg() {
		return flg;
	}

	public void setFlg(String flg) {
		this.flg = flg;
	}
	
	@Length(min=0, max=20, message="更新时间长度必须介于 0 和 20 之间")
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
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("TseqTPipCompParaDO [ ");
		sb.append("compNo="+compNo+" , ");
		sb.append("compName="+compName+" , ");
		sb.append("keyTp="+keyTp+" , ");
		sb.append("keyNo="+keyNo+" , ");
		sb.append("keyName="+keyName+" , ");
		sb.append("kv="+kv+" , ");
		sb.append("keyDesc="+keyDesc+" , ");
		sb.append("ser="+ser+" , ");
		sb.append("flg="+flg+" , ");
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
		list.add("compNo");
		list.add("keyTp");
		list.add("keyNo");
		return list;
	}
}