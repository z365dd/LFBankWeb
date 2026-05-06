/**
* 系统名称: SmartWeb平台
* 模块名称: comp.prod.oper的实体类模块
* 功能描述: 规则关系表数据定义
* 类 名 称  : TPipRuleRelatDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200114<br>
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
 * 规则关系表
 * @author zh
 * @version 20200114
 */
public class TPipRuleRelatDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String busiNo;		// 业务编号
	private String svcCode;		// 服务代码
	private String sceneNo;		//场景编号
	private String ruleId;		// 规则编号
	private String ruleTp;		// 规则类型
	private String shortRmrk;		// 短备注
	private String midRmrk;		// 中备注
	private String longRmrk;		// 长备注
	private String dac;		// DAC
	private String busiName;		// 业务名称
	
	public TPipRuleRelatDO() {
		super();
	}

	public TPipRuleRelatDO(String id){
		super(id);
	}

	public String getBusiNo() {
		return busiNo;
	}

	public void setBusiNo(String busiNo) {
		this.busiNo = busiNo;
	}
	
	public String getSvcCode() {
		return svcCode;
	}

	public void setSvcCode(String svcCode) {
		this.svcCode = svcCode;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleTp() {
		return ruleTp;
	}

	public void setRuleTp(String ruleTp) {
		this.ruleTp = ruleTp;
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

	
	public String getSceneNo() {
		return sceneNo;
	}

	public void setSceneNo(String sceneNo) {
		this.sceneNo = sceneNo;
	}
	
	public String getBusiName() {
		return busiName;
	}

	public void setBusiName(String busiName) {
		this.busiName = busiName;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("TPipRuleRelatDO [ ");
		sb.append("busiNo="+busiNo+" , ");
		sb.append("svcCode="+svcCode+" , ");
		sb.append("ruleId="+ruleId+" , ");
		sb.append("ruleTp="+ruleTp+" , ");
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
		ignoreFields.add("busiName");
		return ignoreFields;
	}

	 public List<String> getMatchFields() {
	        List<String> matchField = new ArrayList<String>();
	        matchField.add("BUSI_NO");
	        matchField.add("SVC_CODE");
	        matchField.add("SCENE_NO");
	        matchField.add("RULE_TP");
	        return matchField;
	    }
	
}