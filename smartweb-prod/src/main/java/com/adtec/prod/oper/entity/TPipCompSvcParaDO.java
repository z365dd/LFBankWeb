/**
* 系统名称: SmartWeb平台
* 模块名称: comp.prod.oper的实体类模块
* 功能描述: 服务属性表数据定义
* 类 名 称  : TPipCompSvcParaDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200109<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.prod.oper.entity;

import java.util.ArrayList;
import java.util.List;
import com.adtec.sys.common.persistence.BaseDO;

/**
 * 服务属性表
 * @author zh
 * @version 20200109
 */
public class TPipCompSvcParaDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String compNo;		// 组件号
	private String compName;		// 组件名称
	private String svcCode;		// 服务代码
	private String svcName;		// 服务名称
	private String svcDesc;		// 服务描述
	private String keyTp;		// 键类型
	private String keyNo;		// 键编号
	private String keyName;		// 键名称
	private String shortRmrk;		// 短备注
	private String midRmrk;		// 中备注
	private String longRmrk;		// 长备注
	private String dac;		// DAC
	
	public TPipCompSvcParaDO() {
		super();
	}

	public TPipCompSvcParaDO(String id){
		super(id);
	}

	public String getCompNo() {
		return compNo;
	}

	public void setCompNo(String compNo) {
		this.compNo = compNo;
	}
	
	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}
	
	public String getSvcCode() {
		return svcCode;
	}

	public void setSvcCode(String svcCode) {
		this.svcCode = svcCode;
	}
	
	public String getSvcName() {
		return svcName;
	}

	public void setSvcName(String svcName) {
		this.svcName = svcName;
	}
	
	public String getSvcDesc() {
		return svcDesc;
	}

	public void setSvcDesc(String svcDesc) {
		this.svcDesc = svcDesc;
	}

	public String getKeyTp() {
		return keyTp;
	}

	public void setKeyTp(String keyTp) {
		this.keyTp = keyTp;
	}

	public String getKeyNo() {
		return keyNo;
	}

	public void setKeyNo(String keyNo) {
		this.keyNo = keyNo;
	}
	
	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
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
		sb.append("TPipCompSvcParaDO [ ");
		sb.append("compNo="+compNo+" , ");
		sb.append("compName="+compName+" , ");
		sb.append("svcCode="+svcCode+" , ");
		sb.append("svcName="+svcName+" , ");
		sb.append("svcDesc="+svcDesc+" , ");
		sb.append("keyType="+keyTp+" , ");
		sb.append("keyNo="+keyNo+" , ");
		sb.append("keyName="+keyName+" , ");
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