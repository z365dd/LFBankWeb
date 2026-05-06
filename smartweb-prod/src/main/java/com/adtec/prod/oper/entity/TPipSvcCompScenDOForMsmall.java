package com.adtec.prod.oper.entity;

import com.adtec.sys.common.persistence.BaseDO;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务场景表
 * @author zh
 * @version 20200109
 */
public class TPipSvcCompScenDOForMsmall extends BaseDO {

	private static final long serialVersionUID = 1L;
	private String compNo;		// 组件号
	private String svcCode;		// 服务代码
	private String sceneNo;		// 场景
	private String sceneName;		// 场景名
	private String sceneDesc;		// 场景描述
	private String shortRmrk;		// 短备注
	private String midRmrk;		// 中备注
	private String longRmrk;		// 长备注
	private String dac;		// DAC
	private String saleProdCode;
	private String atomProdCode;
	private String atomProdDesc;

	public TPipSvcCompScenDOForMsmall() {
		super();
	}

	public TPipSvcCompScenDOForMsmall(String id){
		super(id);
	}

	public String getCompNo() {
		return compNo;
	}

	public void setCompNo(String compNo) {
		this.compNo = compNo;
	}
	
	public String getSvcCode() {
		return svcCode;
	}

	public void setSvcCode(String svcCode) {
		this.svcCode = svcCode;
	}

	public String getSceneNo() {
		return sceneNo;
	}

	public void setSceneNo(String sceneNo) {
		this.sceneNo = sceneNo;
	}

	public String getSceneName() {
		return sceneName;
	}

	public void setSceneName(String sceneName) {
		this.sceneName = sceneName;
	}

	public String getSceneDesc() {
		return sceneDesc;
	}

	public void setSceneDesc(String sceneDesc) {
		this.sceneDesc = sceneDesc;
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

	public String getSaleProdCode() {
		return saleProdCode;
	}

	public void setSaleProdCode(String saleProdCode) {
		this.saleProdCode = saleProdCode;
	}

	public String getAtomProdCode() {
		return atomProdCode;
	}

	public void setAtomProdCode(String atomProdCode) {
		this.atomProdCode = atomProdCode;
	}

	public String getAtomProdDesc() {
		return atomProdDesc;
	}

	public void setAtomProdDesc(String atomProdDesc) {
		this.atomProdDesc = atomProdDesc;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("TPipSvcCompScenDO [ ");
		sb.append("compNo="+compNo+" , ");
		sb.append("svcCode="+svcCode+" , ");
		sb.append("shortRmrk="+shortRmrk+" , ");
		sb.append("midRmrk="+midRmrk+" , ");
		sb.append("longRmrk="+longRmrk+" , ");
		sb.append("dac="+dac+" , ");
		sb.append(" ] ");
		return sb.toString();
	}

	public List<String> getIgnoreFields() {
		List<String> ignoreFields = super.getIgnoreFields();
		ignoreFields.add("serialVersionUID");
		ignoreFields.add("DEL_FLAG_NORMAL");
		ignoreFields.add("DEL_FLAG_DELETE");
		ignoreFields.add("id");
		ignoreFields.add("crtr");
		ignoreFields.add("crtTime");
		ignoreFields.add("uptr");
		ignoreFields.add("uptTime");
		ignoreFields.add("rmrk");
		ignoreFields.add("delFlg");
		return ignoreFields;
	}

	public List<String> getMatchFields() {
		List<String> matchField = new ArrayList<String>();
		matchField.add("compNo");
		matchField.add("svcCode");
		return matchField;
	}
}