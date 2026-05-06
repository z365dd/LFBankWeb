/**
* 系统名称: SmartWeb平台
* 模块名称: comp.prod.oper的实体类模块
* 功能描述: 对账规则表数据定义
* 类 名 称  : TPipChkRuleDO.java
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
 * 对账规则表
 * 
 * @author zh
 * @version 20200114
 */
public class TPipChkRuleDO extends BaseDO {

	private static final long serialVersionUID = 1L;
	private String ruleId;// 规则ID
	private String ruleName;// 规则名称
	private String chkTp;// 对账类型
	private String chkSndGrpFlg;// 对账发送组标志
	private String chkCycTp;// 对账周期类型
	private long chkCyc;// 对账周期
	private String strTime;// 开始时间
	private String endTime;// 结束时间
	private String chkRsltPushFlg;// 对账结果推送标志
	private String chkRsltFileName;// 对账结果文件名称
	private String chkFileDownloadFlg;// 对账文件下载标志
	private String othChkFileName;// 第三方对账文件名称
	private String chkSwitchFlg;// 对账标志
	private String errSwitchFlg;// 差错标志
	private String bean;// BEAN
	private String upRuleId;// 上级规则ID
	private String chkNo;// 对账编号
	private String chkRuleId;// 对账规则ID
	private String shortRmrk;// 短备注
	private String midRmrk;// 中备注
	private String longRmrk;// 长备注
	private String dac;// DAC

	public TPipChkRuleDO() {
		super();
	}

	public TPipChkRuleDO(String id) {
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

	public String getChkTp() {
		return chkTp;
	}

	public void setChkTp(String chkTp) {
		this.chkTp = chkTp;
	}

	public String getChkSndGrpFlg() {
		return chkSndGrpFlg;
	}

	public void setChkSndGrpFlg(String chkSndGrpFlg) {
		this.chkSndGrpFlg = chkSndGrpFlg;
	}

	public String getChkCycTp() {
		return chkCycTp;
	}

	public void setChkCycTp(String chkCycTp) {
		this.chkCycTp = chkCycTp;
	}

	public long getChkCyc() {
		return chkCyc;
	}

	public void setChkCyc(long chkCyc) {
		this.chkCyc = chkCyc;
	}

	public String getStrTime() {
		return strTime;
	}

	public void setStrTime(String strTime) {
		this.strTime = strTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getChkRsltPushFlg() {
		return chkRsltPushFlg;
	}

	public void setChkRsltPushFlg(String chkRsltPushFlg) {
		this.chkRsltPushFlg = chkRsltPushFlg;
	}

	public String getChkRsltFileName() {
		return chkRsltFileName;
	}

	public void setChkRsltFileName(String chkRsltFileName) {
		this.chkRsltFileName = chkRsltFileName;
	}

	public String getChkFileDownloadFlg() {
		return chkFileDownloadFlg;
	}

	public void setChkFileDownloadFlg(String chkFileDownloadFlg) {
		this.chkFileDownloadFlg = chkFileDownloadFlg;
	}

	public String getOthChkFileName() {
		return othChkFileName;
	}

	public void setOthChkFileName(String othChkFileName) {
		this.othChkFileName = othChkFileName;
	}

	public String getChkSwitchFlg() {
		return chkSwitchFlg;
	}

	public void setChkSwitchFlg(String chkSwitchFlg) {
		this.chkSwitchFlg = chkSwitchFlg;
	}

	public String getErrSwitchFlg() {
		return errSwitchFlg;
	}

	public void setErrSwitchFlg(String errSwitchFlg) {
		this.errSwitchFlg = errSwitchFlg;
	}

	public String getBean() {
		return bean;
	}

	public void setBean(String bean) {
		this.bean = bean;
	}

	public String getUpRuleId() {
		return upRuleId;
	}

	public void setUpRuleId(String upRuleId) {
		this.upRuleId = upRuleId;
	}

	public String getChkNo() {
		return chkNo;
	}

	public void setChkNo(String chkNo) {
		this.chkNo = chkNo;
	}

	public String getChkRuleId() {
		return chkRuleId;
	}

	public void setChkRuleId(String chkRuleId) {
		this.chkRuleId = chkRuleId;
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
		return "ChkRuleDO [ruleId=" + ruleId + ",ruleName=" + ruleName + ",chkTp=" + chkTp + ",chkSndGrpFlg="
				+ chkSndGrpFlg + ",chkCycTp=" + chkCycTp + ",chkCyc=" + chkCyc + ",strTime=" + strTime + ",endTime="
				+ endTime + ",chkRsltPushFlg=" + chkRsltPushFlg + ",chkRsltFileName=" + chkRsltFileName
				+ ",chkFileDownloadFlg=" + chkFileDownloadFlg + ",othChkFileName=" + othChkFileName + ",chkSwitchFlg="
				+ chkSwitchFlg + ",errSwitchFlg=" + errSwitchFlg + ",bean=" + bean + ",upRuleId=" + upRuleId + ",chkNo=" + chkNo
				+ ",chkRuleId=" + chkRuleId + ",shortRmrk=" + shortRmrk + ",midRmrk=" + midRmrk + ",longRmrk="
				+ longRmrk + ",dac=" + dac + "]";
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