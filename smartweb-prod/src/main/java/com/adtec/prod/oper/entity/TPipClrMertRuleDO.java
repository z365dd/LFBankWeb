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
public class TPipClrMertRuleDO extends BaseDO {

	private static final long serialVersionUID = 1L;

	private String ruleId;// 规则ID
	private String ruleName;// 规则名称
	private String clrMeth;// 清算方式
	private String clrDimTp;// 清算维度类型
	private String batProcFlg;// 批次处理标志
	private String clrSndGrpFlg;// 清算发送组标志
	private String clrCycTp;// 清算周期类型
	private long clrCyc;// 清算周期
	private String strTime;// 开始时间
	private String endTime;// 结束时间
	private String bean;// BEAN
	private String autoClrChnlNo;// 自动清算渠道号
	private String shortRmrk;// 短备注 -->过渡账户开户机构
	private String midRmrk;// 中备注
	private String longRmrk;// 长备注
	private String dac;// DAC

	public TPipClrMertRuleDO() {
		super();
	}

	public TPipClrMertRuleDO(String id) {
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

	public String getClrMeth() {
		return clrMeth;
	}

	public void setClrMeth(String clrMeth) {
		this.clrMeth = clrMeth;
	}

	public String getClrDimTp() {
		return clrDimTp;
	}

	public void setClrDimTp(String clrDimTp) {
		this.clrDimTp = clrDimTp;
	}

	public String getBatProcFlg() {
		return batProcFlg;
	}

	public void setBatProcFlg(String batProcFlg) {
		this.batProcFlg = batProcFlg;
	}

	public String getClrSndGrpFlg() {
		return clrSndGrpFlg;
	}

	public void setClrSndGrpFlg(String clrSndGrpFlg) {
		this.clrSndGrpFlg = clrSndGrpFlg;
	}

	public String getClrCycTp() {
		return clrCycTp;
	}

	public void setClrCycTp(String clrCycTp) {
		this.clrCycTp = clrCycTp;
	}

	public long getClrCyc() {
		return clrCyc;
	}

	public void setClrCyc(long clrCyc) {
		this.clrCyc = clrCyc;
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

	public String getBean() {
		return bean;
	}

	public void setBean(String bean) {
		this.bean = bean;
	}

	public String getAutoClrChnlNo() {
		return autoClrChnlNo;
	}

	public void setAutoClrChnlNo(String autoClrChnlNo) {
		this.autoClrChnlNo = autoClrChnlNo;
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
		return "ClrMertRuleDO [ruleId=" + ruleId + ",ruleName=" + ruleName + ",clrMeth=" + clrMeth + ",clrDimTp="
				+ clrDimTp + ",batProcFlg=" + batProcFlg + ",clrSndGrpFlg=" + clrSndGrpFlg + ",clrCycTp=" + clrCycTp
				+ ",clrCyc=" + clrCyc + ",strTime=" + strTime + ",endTime=" + endTime + ",bean=" + bean
				+ ",autoClrChnlNo=" + autoClrChnlNo + ",shortRmrk=" + shortRmrk + ",midRmrk=" + midRmrk + ",longRmrk="
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