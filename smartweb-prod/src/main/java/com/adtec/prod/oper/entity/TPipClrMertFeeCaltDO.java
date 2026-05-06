/**
* 系统名称: SmartWeb平台
* 模块名称: comp.prod.oper的实体类模块
* 功能描述: 单位清算规则表数据定义
* 类 名 称  : TPipClrRuleDO.java
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
 * 单位清算规则表
 * 
 * @author zh
 * @version 20200114
 */
public class TPipClrMertFeeCaltDO extends BaseDO {

	private static final long serialVersionUID = 1L;
	private String ruleId;// 规则ID
	private String ruleName;// 规则名称
	private double minFee;// 最小手续费
	private double maxFee;// 最大的手续费
	private String feePrcsnTp;// 手续费精度类型
	private String feeCaltMeth;// 手续费计算方式
	private String feeRuleExpr;// 手续费规则表达式
	private String shortRmrk;// 短备注
	private String midRmrk;// 中备注
	private String longRmrk;// 长备注
	private String dac;// DAC

	public TPipClrMertFeeCaltDO() {
		super();
	}

	public TPipClrMertFeeCaltDO(String id) {
		super(id);
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public double getMinFee() {
		return minFee;
	}

	public void setMinFee(double minFee) {
		this.minFee = minFee;
	}

	public double getMaxFee() {
		return maxFee;
	}

	public void setMaxFee(double maxFee) {
		this.maxFee = maxFee;
	}

	public String getFeePrcsnTp() {
		return feePrcsnTp;
	}

	public void setFeePrcsnTp(String feePrcsnTp) {
		this.feePrcsnTp = feePrcsnTp;
	}

	public String getFeeCaltMeth() {
		return feeCaltMeth;
	}

	public void setFeeCaltMeth(String feeCaltMeth) {
		this.feeCaltMeth = feeCaltMeth;
	}

	public String getFeeRuleExpr() {
		return feeRuleExpr;
	}

	public void setFeeRuleExpr(String feeRuleExpr) {
		this.feeRuleExpr = feeRuleExpr;
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
		return "ClrMertFeeCaltDO [ruleId=" + ruleId + ",ruleName=" + ruleName + ",minFee=" + minFee + ",maxFee="
				+ maxFee + ",feePrcsnTp=" + feePrcsnTp + ",feeCaltMeth=" + feeCaltMeth + ",feeRuleExpr=" + feeRuleExpr
				+ ",shortRmrk=" + shortRmrk + ",midRmrk=" + midRmrk + ",longRmrk=" + longRmrk + ",dac=" + dac + "]";
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
		// 设置更新匹配条件
		List<String> matchField = new ArrayList<String>();
		matchField.add("ruleId");
		return matchField;
	}
}