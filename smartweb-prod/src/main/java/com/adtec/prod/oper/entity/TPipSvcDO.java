/**
* 系统名称: SmartWeb平台
* 模块名称: comp.prod.oper的实体类模块
* 功能描述: 服务数据定义
* 类 名 称  : TPipSvcDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200108<br>
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
 * 服务
 * @author zh
 * @version 20200108
 */
public class TPipSvcDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String compNo;		// 组件号
	private String svcCode;		// 服务名称
	private String svcName;		// 服务名称
	private String svcDesc;		// 服务描述
	private String signFlg;		// 是否校验签约
	private String chkFlg;		// 对账标志
	private String othConnFlg;		// 是否检查第三方系统状态
	private String othSvcCodeFlg;		// 是否检查第三方系统状态
	private String shortRmrk;		// 短备注
	private String midRmrk;		// 中备注
	private String longRmrk;		// 长备注
	private String dac;		// DAC
	private String openStat;
	
	public TPipSvcDO() {
		super();
	}

	public TPipSvcDO(String id){
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

	public String getSignFlg() {
		return signFlg;
	}

	public void setSignFlg(String signFlg) {
		this.signFlg = signFlg;
	}

	public String getChkFlg() {
		return chkFlg;
	}

	public void setChkFlg(String chkFlg) {
		this.chkFlg = chkFlg;
	}

	public String getOthConnFlg() {
		return othConnFlg;
	}

	public void setOthConnFlg(String othConnFlg) {
		this.othConnFlg = othConnFlg;
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

	public String getOpenStat() {
		return openStat;
	}

	public void setOpenStat(String openStat) {
		this.openStat = openStat;
	}

	public String getOthSvcCodeFlg() {
		return othSvcCodeFlg;
	}

	public void setOthSvcCodeFlg(String othSvcCodeFlg) {
		this.othSvcCodeFlg = othSvcCodeFlg;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("TPipSvcDO [ ");
		sb.append("compNo="+compNo+" , ");
		sb.append("svcCode="+svcCode+" , ");
		sb.append("svcName="+svcName+" , ");
		sb.append("svcDesc="+svcDesc+" , ");
		sb.append("signFlg="+signFlg+" , ");
		sb.append("chkFlg="+chkFlg+" , ");
		sb.append("othConnFlg="+othConnFlg+" , ");
		sb.append("othSvcCodeFlg="+othSvcCodeFlg+" , ");
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