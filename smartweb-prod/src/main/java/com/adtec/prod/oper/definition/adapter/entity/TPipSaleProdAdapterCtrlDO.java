/**
* 系统名称: SmartWeb平台
* 模块名称: comp-prod/oper/definition/adapter的实体类模块
* 功能描述: 可售产品包装控件数据定义
* 类 名 称  : TPipSaleProdAdapterCtrlDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200106<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.prod.oper.definition.adapter.entity;

import java.util.List;
import com.adtec.sys.common.persistence.BaseDO;

/**
 * 可售产品包装控件
 * @author zengxj
 * @version 20200106
 */
public class TPipSaleProdAdapterCtrlDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String saleProdCode;		// 可售产品代码
	private String keyNo;		// 键编号
	private String compNo;		// 组件号
	private String elemKey;		// 元素键
	private String elemName;		// 元素名称
	private String elemKv;		// 元素键值
	private String shortRmrk;		// 短备注
	private String midRmrk;		// 中备注
	private String longRmrk;		// 长备注
	private String dac;		// DAC
	
	public TPipSaleProdAdapterCtrlDO() {
		super();
	}

	public TPipSaleProdAdapterCtrlDO(String id){
		super(id);
	}

	public String getSaleProdCode() {
		return saleProdCode;
	}

	public void setSaleProdCode(String saleProdCode) {
		this.saleProdCode = saleProdCode;
	}
	
	public String getKeyNo() {
		return keyNo;
	}

	public void setKeyNo(String keyNo) {
		this.keyNo = keyNo;
	}
	
	public String getCompNo() {
		return compNo;
	}

	public void setCompNo(String compNo) {
		this.compNo = compNo;
	}
	
	public String getElemKey() {
		return elemKey;
	}

	public void setElemKey(String elemKey) {
		this.elemKey = elemKey;
	}
	
	public String getElemName() {
		return elemName;
	}

	public void setElemName(String elemName) {
		this.elemName = elemName;
	}
	
	public String getElemKv() {
		return elemKv;
	}

	public void setElemKv(String elemKv) {
		this.elemKv = elemKv;
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
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("TPipSaleProdAdapterCtrlDO [ ");
		sb.append("saleProdCode="+saleProdCode+" , ");
		sb.append("keyNo="+keyNo+" , ");
		sb.append("compNo="+compNo+" , ");
		sb.append("elemKey="+elemKey+" , ");
		sb.append("elemName="+elemName+" , ");
		sb.append("elemKv="+elemKv+" , ");
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
		list.add("id");
		list.add("delFlg");
		list.add("rmrk");
		list.add("crtr");
		list.add("crtTime");
		list.add("uptr");
		list.add("uptTime");
		return list;
	}
}