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
public class TPipClrMertAcctDO extends BaseDO {

	private static final long serialVersionUID = 1L;

	private String ruleId;// 规则ID
	private String mertNo;// 商户号
	private String mertName;// 商户名称
	private String upMertNo;// 上级商户号
	private String intrmAcctFlg;// 过渡账户标志
	private String intrmAcct;// 过渡账户
	private String intrmAcctName;// 过渡账户名称
	private String entrAcct;// 单位账号
	private String entrAcctName;// 单位账户名称
	private String entrAcctBank;// 单位账户行号
	private String entrAcctBankName;// 单位账户银行名称
	private String entrAcctBankFlg;// 单位账户行号标志
	private String sumCode;// 摘要代码
	private String sumDesc;// 摘要描述
	private String postingSumCode;// 过账摘要代码
	private String postingSumDesc;// 过账摘要描述
	private String feeTfOutAcct;// 手续费转出账户
	private String feeTfOutAcctName;// 手续费转出账户名称
	private String feeTfInAcct;// 手续费转入账户
	private String feeTfInAcctName;// 手续费转入账户名称
	private String feeSumCode;// 手续费摘要代码
	private String feeSumDesc;// 手续费摘要描述
	private String shortRmrk;// 短备注 -->过渡账户开户机构
	private String midRmrk;// 中备注
	private String longRmrk;// 长备注
	private String dac;// DAC

	public TPipClrMertAcctDO() {
		super();
	}

	public TPipClrMertAcctDO(String id) {
		super(id);
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getMertNo() {
		return mertNo;
	}

	public void setMertNo(String mertNo) {
		this.mertNo = mertNo;
	}

	public String getMertName() {
		return mertName;
	}

	public void setMertName(String mertName) {
		this.mertName = mertName;
	}

	public String getUpMertNo() {
		return upMertNo;
	}

	public void setUpMertNo(String upMertNo) {
		this.upMertNo = upMertNo;
	}

	public String getIntrmAcctFlg() {
		return intrmAcctFlg;
	}

	public void setIntrmAcctFlg(String intrmAcctFlg) {
		this.intrmAcctFlg = intrmAcctFlg;
	}

	public String getIntrmAcct() {
		return intrmAcct;
	}

	public void setIntrmAcct(String intrmAcct) {
		this.intrmAcct = intrmAcct;
	}

	public String getIntrmAcctName() {
		return intrmAcctName;
	}

	public void setIntrmAcctName(String intrmAcctName) {
		this.intrmAcctName = intrmAcctName;
	}

	public String getEntrAcct() {
		return entrAcct;
	}

	public void setEntrAcct(String entrAcct) {
		this.entrAcct = entrAcct;
	}

	public String getEntrAcctName() {
		return entrAcctName;
	}

	public void setEntrAcctName(String entrAcctName) {
		this.entrAcctName = entrAcctName;
	}

	public String getEntrAcctBank() {
		return entrAcctBank;
	}

	public void setEntrAcctBank(String entrAcctBank) {
		this.entrAcctBank = entrAcctBank;
	}

	public String getEntrAcctBankName() {
		return entrAcctBankName;
	}

	public void setEntrAcctBankName(String entrAcctBankName) {
		this.entrAcctBankName = entrAcctBankName;
	}

	public String getEntrAcctBankFlg() {
		return entrAcctBankFlg;
	}

	public void setEntrAcctBankFlg(String entrAcctBankFlg) {
		this.entrAcctBankFlg = entrAcctBankFlg;
	}

	public String getSumCode() {
		return sumCode;
	}

	public void setSumCode(String sumCode) {
		this.sumCode = sumCode;
	}

	public String getSumDesc() {
		return sumDesc;
	}

	public void setSumDesc(String sumDesc) {
		this.sumDesc = sumDesc;
	}

	public String getPostingSumCode() {
		return postingSumCode;
	}

	public void setPostingSumCode(String postingSumCode) {
		this.postingSumCode = postingSumCode;
	}

	public String getPostingSumDesc() {
		return postingSumDesc;
	}

	public void setPostingSumDesc(String postingSumDesc) {
		this.postingSumDesc = postingSumDesc;
	}

	public String getFeeTfOutAcct() {
		return feeTfOutAcct;
	}

	public void setFeeTfOutAcct(String feeTfOutAcct) {
		this.feeTfOutAcct = feeTfOutAcct;
	}

	public String getFeeTfOutAcctName() {
		return feeTfOutAcctName;
	}

	public void setFeeTfOutAcctName(String feeTfOutAcctName) {
		this.feeTfOutAcctName = feeTfOutAcctName;
	}

	public String getFeeTfInAcct() {
		return feeTfInAcct;
	}

	public void setFeeTfInAcct(String feeTfInAcct) {
		this.feeTfInAcct = feeTfInAcct;
	}

	public String getFeeTfInAcctName() {
		return feeTfInAcctName;
	}

	public void setFeeTfInAcctName(String feeTfInAcctName) {
		this.feeTfInAcctName = feeTfInAcctName;
	}

	public String getFeeSumCode() {
		return feeSumCode;
	}

	public void setFeeSumCode(String feeSumCode) {
		this.feeSumCode = feeSumCode;
	}

	public String getFeeSumDesc() {
		return feeSumDesc;
	}

	public void setFeeSumDesc(String feeSumDesc) {
		this.feeSumDesc = feeSumDesc;
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
		return "ClrMertAcctDO [ruleId=" + ruleId + ",mertNo=" + mertNo + ",mertName=" + mertName + ",upMertNo="
				+ upMertNo + ",intrmAcctFlg=" + intrmAcctFlg + ",intrmAcct=" + intrmAcct + ",intrmAcctName="
				+ intrmAcctName + ",entrAcct=" + entrAcct + ",entrAcctName=" + entrAcctName + ",entrAcctBank="
				+ entrAcctBank + ",entrAcctBankName=" + entrAcctBankName + ",entrAcctBankFlg=" + entrAcctBankFlg
				+ ",sumCode=" + sumCode + ",sumDesc=" + sumDesc + ",postingSumCode=" + postingSumCode
				+ ",postingSumDesc=" + postingSumDesc + ",feeTfOutAcct=" + feeTfOutAcct + ",feeTfOutAcctName="
				+ feeTfOutAcctName + ",feeTfInAcct=" + feeTfInAcct + ",feeTfInAcctName=" + feeTfInAcctName
				+ ",feeSumCode=" + feeSumCode + ",feeSumDesc=" + feeSumDesc + ",shortRmrk=" + shortRmrk + ",midRmrk="
				+ midRmrk + ",longRmrk=" + longRmrk + ",dac=" + dac + "]";
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
		matchField.add("mertNo");
		return matchField;
	}
}