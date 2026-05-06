/**
* 系统名称: SmartWeb平台
* 模块名称: prod-oper的实体类模块
* 功能描述: T_PIP_SIGN_CHK_RULE数据定义
* 类 名 称  : TPipSignChkRuleDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200313<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.prod.oper.entity;

import org.hibernate.validator.constraints.Length;
import java.util.ArrayList;
import java.util.List;
import com.adtec.sys.common.persistence.BaseDO;

/**
 * T_PIP_SIGN_CHK_RULE
 * @author linyx
 * @version 20200313
 */
public class TPipSignChkRuleDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String ruleId;		// rule_id
	private String ruleDesc;		// rule_desc
	private String signChkTp;		// sign_chk_tp
	private String signChkSndTp;		// sign_chk_snd_tp
	private String autoChkStrTime;		// auto_chk_str_time
	private String chkRsltFileFlg;		// chk_rslt_file_flg
	private String chkRsltFileName;		// chk_rslt_file_name
	private String othFileGetTp;		// oth_file_get_tp
	private String othChkFileName;		// oth_chk_file_name
	private String shortRmrk;		// short_rmrk
	private String midRmrk;		// mid_rmrk
	private String longRmrk;		// long_rmrk
	private String dac;		// dac
	
	public TPipSignChkRuleDO() {
		super();
	}

	public TPipSignChkRuleDO(String id){
		super(id);
	}

	@Length(min=1, max=64, message="rule_id长度必须介于 1 和 64 之间")
	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	
	@Length(min=1, max=360, message="rule_desc长度必须介于 1 和 360 之间")
	public String getRuleDesc() {
		return ruleDesc;
	}

	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}
	
	@Length(min=0, max=2, message="sign_chk_tp长度必须介于 0 和 2 之间")
	public String getSignChkTp() {
		return signChkTp;
	}

	public void setSignChkTp(String signChkTp) {
		this.signChkTp = signChkTp;
	}
	
	@Length(min=0, max=2, message="sign_chk_snd_tp长度必须介于 0 和 2 之间")
	public String getSignChkSndTp() {
		return signChkSndTp;
	}

	public void setSignChkSndTp(String signChkSndTp) {
		this.signChkSndTp = signChkSndTp;
	}
	
	@Length(min=0, max=20, message="auto_chk_str_time长度必须介于 0 和 20 之间")
	public String getAutoChkStrTime() {
		return autoChkStrTime;
	}

	public void setAutoChkStrTime(String autoChkStrTime) {
		this.autoChkStrTime = autoChkStrTime;
	}
	
	@Length(min=0, max=2, message="chk_rslt_file_flg长度必须介于 0 和 2 之间")
	public String getChkRsltFileFlg() {
		return chkRsltFileFlg;
	}

	public void setChkRsltFileFlg(String chkRsltFileFlg) {
		this.chkRsltFileFlg = chkRsltFileFlg;
	}
	
	@Length(min=0, max=64, message="chk_rslt_file_name长度必须介于 0 和 64 之间")
	public String getChkRsltFileName() {
		return chkRsltFileName;
	}

	public void setChkRsltFileName(String chkRsltFileName) {
		this.chkRsltFileName = chkRsltFileName;
	}
	
	@Length(min=0, max=2, message="oth_file_get_tp长度必须介于 0 和 2 之间")
	public String getOthFileGetTp() {
		return othFileGetTp;
	}

	public void setOthFileGetTp(String othFileGetTp) {
		this.othFileGetTp = othFileGetTp;
	}
	
	@Length(min=0, max=64, message="oth_chk_file_name长度必须介于 0 和 64 之间")
	public String getOthChkFileName() {
		return othChkFileName;
	}

	public void setOthChkFileName(String othChkFileName) {
		this.othChkFileName = othChkFileName;
	}
	
	@Length(min=0, max=64, message="short_rmrk长度必须介于 0 和 64 之间")
	public String getShortRmrk() {
		return shortRmrk;
	}

	public void setShortRmrk(String shortRmrk) {
		this.shortRmrk = shortRmrk;
	}
	
	@Length(min=0, max=128, message="mid_rmrk长度必须介于 0 和 128 之间")
	public String getMidRmrk() {
		return midRmrk;
	}

	public void setMidRmrk(String midRmrk) {
		this.midRmrk = midRmrk;
	}
	
	@Length(min=0, max=256, message="long_rmrk长度必须介于 0 和 256 之间")
	public String getLongRmrk() {
		return longRmrk;
	}

	public void setLongRmrk(String longRmrk) {
		this.longRmrk = longRmrk;
	}
	
	@Length(min=0, max=16, message="dac长度必须介于 0 和 16 之间")
	public String getDac() {
		return dac;
	}

	public void setDac(String dac) {
		this.dac = dac;
	}
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("TPipSignChkRuleDO [ ");
		sb.append("ruleId="+ruleId+" , ");
		sb.append("ruleDesc="+ruleDesc+" , ");
		sb.append("signChkTp="+signChkTp+" , ");
		sb.append("signChkSndTp="+signChkSndTp+" , ");
		sb.append("autoChkStrTime="+autoChkStrTime+" , ");
		sb.append("chkRsltFileFlg="+chkRsltFileFlg+" , ");
		sb.append("chkRsltFileName="+chkRsltFileName+" , ");
		sb.append("othFileGetTp="+othFileGetTp+" , ");
		sb.append("othChkFileName="+othChkFileName+" , ");
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
		list.add("delFlg");
		list.add("id");
		list.add("crtr");
		list.add("crtTime");
		list.add("uptr");
		list.add("uptTime");
		list.add("rmrk");
		return list;
	}
	
	@Override
	public List<String> getMatchFields() {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		list.add("ruleId");
		return list;
	}
}