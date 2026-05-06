package com.adtec.prod.oper.definition.saleprod.entity;

import com.adtec.sys.common.persistence.BaseDO;

import java.util.ArrayList;
import java.util.List;

public class TPipSaleProdDOForMsmall extends BaseDO {

	private static final long serialVersionUID = 1L;
	private String saleProdCode;		// 可售产品代码
	private String saleProdDesc;		// 可售产品描述
	private String prodLineCode;		// 产品线编号
	private String prodLineName;		// 产品线名称
	private String url;		// 产品线名称
	private String omUrl;		// 运维url
	private String shortRmrk;		// 短备注
	private String midRmrk;		// 中备注
	private String longRmrk;		// 长备注
	private String dac;		// DAC
	private String compNo;

	public TPipSaleProdDOForMsmall() {
		super();
	}

	public TPipSaleProdDOForMsmall(String id){
		super(id);
	}

	public String getSaleProdCode() {
		return saleProdCode;
	}

	public void setSaleProdCode(String saleProdCode) {
		this.saleProdCode = saleProdCode;
	}
	
	public String getSaleProdDesc() {
		return saleProdDesc;
	}

	public void setSaleProdDesc(String saleProdDesc) {
		this.saleProdDesc = saleProdDesc;
	}
	
	public String getProdLineCode() {
		return prodLineCode;
	}

	public void setProdLineCode(String prodLineCode) {
		this.prodLineCode = prodLineCode;
	}
	
	public String getProdLineName() {
		return prodLineName;
	}

	public void setProdLineName(String prodLineName) {
		this.prodLineName = prodLineName;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getOmUrl() {
		return omUrl;
	}

	public void setOmUrl(String omUrl) {
		this.omUrl = omUrl;
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

	public String getCompNo() {
		return compNo;
	}

	public void setCompNo(String compNo) {
		this.compNo = compNo;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("TPipSaleProdDO [ ");
		sb.append("saleProdCode="+saleProdCode+" , ");
		sb.append("saleProdDesc="+saleProdDesc+" , ");
		sb.append("prodLineCode="+prodLineCode+" , ");
		sb.append("prodLineName="+prodLineName+" , ");
		sb.append("url="+url+" , ");
		sb.append("omUrl="+omUrl+" , ");
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
		list.add("prodLineName");
		list.add("omUrl");
		list.add("delFlg");
		list.add("rmrk");
		list.add("crtr");
		list.add("crtTime");
		list.add("uptr");
		list.add("uptTime");
		return list;
	}
	
	/**
	 * 返回固定的匹配域ID
	 * @return
	 */
	@Override
	public List<String> getMatchFields(){
		//设置更新匹配条件
		List<String> matchField = new ArrayList<String>();
		matchField.add("saleProdCode");
		return matchField;
	}
}