/**
* 系统名称: SmartWeb平台
* 模块名称: comp-prod/oper/definition/adapter的实体类模块
* 功能描述: 可售产品包装数据定义
* 类 名 称  : TPipSaleProdAdapterDO.java
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
 * 可售产品包装
 * @author zengxj
 * @version 20200106
 */
public class TPipSaleProdAdapterDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String saleProdCode;		// 可售产品代码
	private String ser;		// 序号
	private String keyNo;		// 键编号
	private String keyTp;		// 键类型
	private String keyName;		// 键名称
	private String atomProdCode;		// 原子产品代码
	private String compNo;		// 组件号
	private String defaKv;		// 默认值
	private String vslFlg;		// 标志
	private String valTp;		// 值类型
	private String valLen;		// 数值长度
	private String enterTp;		// 键类型
	private String shortRmrk;		// 短备注
	private String midRmrk;		// 中备注
	private String longRmrk;		// 长备注
	private String dac;		// DAC
	
	public TPipSaleProdAdapterDO() {
		super();
	}

	public TPipSaleProdAdapterDO(String id){
		super(id);
	}

	public String getSaleProdCode() {
		return saleProdCode;
	}

	public void setSaleProdCode(String saleProdCode) {
		this.saleProdCode = saleProdCode;
	}
	
	public String getSer() {
		return ser;
	}

	public void setSer(String ser) {
		this.ser = ser;
	}
	
	public String getKeyNo() {
		return keyNo;
	}

	public void setKeyNo(String keyNo) {
		this.keyNo = keyNo;
	}
	
	public String getEnterTp() {
		return enterTp;
	}

	public void setEnterTp(String enterTp) {
		this.enterTp = enterTp;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	
	public String getAtomProdCode() {
		return atomProdCode;
	}

	public void setAtomProdCode(String atomProdCode) {
		this.atomProdCode = atomProdCode;
	}
	
	public String getCompNo() {
		return compNo;
	}

	public void setCompNo(String compNo) {
		this.compNo = compNo;
	}
	
	public String getDefaKv() {
		return defaKv;
	}

	public void setDefaKv(String defaKv) {
		this.defaKv = defaKv;
	}
	
	public String getVslFlg() {
		return vslFlg;
	}

	public void setVslFlg(String vslFlg) {
		this.vslFlg = vslFlg;
	}
	
	public String getValTp() {
		return valTp;
	}

	public void setValTp(String valTp) {
		this.valTp = valTp;
	}
	
	public String getValLen() {
		return valLen;
	}

	public void setValLen(String valLen) {
		this.valLen = valLen;
	}
	
	public String getKeyTp() {
		return keyTp;
	}

	public void setKeyTp(String keyTp) {
		this.keyTp = keyTp;
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
		sb.append("TPipSaleProdAdapterDO [ ");
		sb.append("saleProdCode="+saleProdCode+" , ");
		sb.append("ser="+ser+" , ");
		sb.append("keyNo="+keyNo+" , ");
		sb.append("enterTp="+enterTp+" , ");
		sb.append("keyName="+keyName+" , ");
		sb.append("atomProdCode="+atomProdCode+" , ");
		sb.append("compNo="+compNo+" , ");
		sb.append("defaKv="+defaKv+" , ");
		sb.append("vslFlg="+vslFlg+" , ");
		sb.append("valTp="+valTp+" , ");
		sb.append("valLen="+valLen+" , ");
		sb.append("keyTp="+keyTp+" , ");
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
		list.add("atomProdCode");
		list.add("compNo");
		list.add("delFlg");
		list.add("rmrk");
		list.add("crtr");
		list.add("crtTime");
		list.add("uptr");
		list.add("uptTime");
		return list;
	}
}