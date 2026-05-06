/**
* 系统名称: SmartWeb平台
* 模块名称: comp.ctrl.oper的实体类模块
* 功能描述: 业务表数据定义
* 类 名 称  : CtrlTPipBusiDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200324<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.comp.ctrl.oper.entity;

import org.hibernate.validator.constraints.Length;
import java.util.ArrayList;
import java.util.List;
import com.adtec.sys.common.persistence.BaseDO;

/**
 * 业务表
 * @author zhengjt
 * @version 20200324
 */
public class CtrlTPipBusiDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String busiNo;		// 业务编号
	private String entrNo;		// 单位编号
	private String busiName;		// 业务名称
	private String saleProdCode;		// 可售产品代码
	private String stat;		// 状态
	private String busiDesc;		// 业务描述
	private String clrTp;		// 清算类型
	private String signFlg;		// 是否校验签约
	private String chkFlg;		// 对账标志
	private String feeTp;		// 清算手续费标志
	private String busiTp;		// 业务类型
	private String relatSys;		// 关联系统号
	private String uptTime;		// 更新时间
	private String modStat;		// 修改状态
	private String lastUptTime;		// last_upt_time
	private String shortRmrk;		// 短备注
	private String midRmrk;		// 中备注
	private String longRmrk;		// 长备注
	private String dac;		// DAC
	private String openGrpChnlNo;		// 开通组渠道
	private String clrDate;		// 清算日期
	private String brchTp;		// brch_tp
	private String crtr;		// crtr
	
	public CtrlTPipBusiDO() {
		super();
	}

	public CtrlTPipBusiDO(String id){
		super(id);
	}

	@Length(min=1, max=14, message="业务编号长度必须介于 1 和 14 之间")
	public String getBusiNo() {
		return busiNo;
	}

	public void setBusiNo(String busiNo) {
		this.busiNo = busiNo;
	}
	
	@Length(min=1, max=14, message="单位编号长度必须介于 1 和 14 之间")
	public String getEntrNo() {
		return entrNo;
	}

	public void setEntrNo(String entrNo) {
		this.entrNo = entrNo;
	}
	
	@Length(min=1, max=120, message="业务名称长度必须介于 1 和 120 之间")
	public String getBusiName() {
		return busiName;
	}

	public void setBusiName(String busiName) {
		this.busiName = busiName;
	}
	
	@Length(min=1, max=20, message="可售产品代码长度必须介于 1 和 20 之间")
	public String getSaleProdCode() {
		return saleProdCode;
	}

	public void setSaleProdCode(String saleProdCode) {
		this.saleProdCode = saleProdCode;
	}
	
	@Length(min=0, max=3, message="状态长度必须介于 0 和 3 之间")
	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}
	
	@Length(min=0, max=360, message="业务描述长度必须介于 0 和 360 之间")
	public String getBusiDesc() {
		return busiDesc;
	}

	public void setBusiDesc(String busiDesc) {
		this.busiDesc = busiDesc;
	}
	
	@Length(min=0, max=2, message="清算类型长度必须介于 0 和 2 之间")
	public String getClrTp() {
		return clrTp;
	}

	public void setClrTp(String clrTp) {
		this.clrTp = clrTp;
	}
	
	@Length(min=0, max=2, message="是否校验签约长度必须介于 0 和 2 之间")
	public String getSignFlg() {
		return signFlg;
	}

	public void setSignFlg(String signFlg) {
		this.signFlg = signFlg;
	}
	
	@Length(min=0, max=2, message="对账标志长度必须介于 0 和 2 之间")
	public String getChkFlg() {
		return chkFlg;
	}

	public void setChkFlg(String chkFlg) {
		this.chkFlg = chkFlg;
	}
	
	@Length(min=0, max=2, message="清算手续费标志长度必须介于 0 和 2 之间")
	public String getFeeTp() {
		return feeTp;
	}

	public void setFeeTp(String feeTp) {
		this.feeTp = feeTp;
	}
	
	@Length(min=0, max=2, message="业务类型长度必须介于 0 和 2 之间")
	public String getBusiTp() {
		return busiTp;
	}

	public void setBusiTp(String busiTp) {
		this.busiTp = busiTp;
	}
	
	@Length(min=0, max=10, message="关联系统号长度必须介于 0 和 10 之间")
	public String getRelatSys() {
		return relatSys;
	}

	public void setRelatSys(String relatSys) {
		this.relatSys = relatSys;
	}
	
	@Length(min=0, max=20, message="更新时间长度必须介于 0 和 20 之间")
	public String getUptTime() {
		return uptTime;
	}

	public void setUptTime(String uptTime) {
		this.uptTime = uptTime;
	}
	
	@Length(min=0, max=3, message="修改状态长度必须介于 0 和 3 之间")
	public String getModStat() {
		return modStat;
	}

	public void setModStat(String modStat) {
		this.modStat = modStat;
	}
	
	@Length(min=0, max=24, message="last_upt_time长度必须介于 0 和 24 之间")
	public String getLastUptTime() {
		return lastUptTime;
	}

	public void setLastUptTime(String lastUptTime) {
		this.lastUptTime = lastUptTime;
	}
	
	@Length(min=0, max=64, message="短备注长度必须介于 0 和 64 之间")
	public String getShortRmrk() {
		return shortRmrk;
	}

	public void setShortRmrk(String shortRmrk) {
		this.shortRmrk = shortRmrk;
	}
	
	@Length(min=0, max=128, message="中备注长度必须介于 0 和 128 之间")
	public String getMidRmrk() {
		return midRmrk;
	}

	public void setMidRmrk(String midRmrk) {
		this.midRmrk = midRmrk;
	}
	
	@Length(min=0, max=256, message="长备注长度必须介于 0 和 256 之间")
	public String getLongRmrk() {
		return longRmrk;
	}

	public void setLongRmrk(String longRmrk) {
		this.longRmrk = longRmrk;
	}
	
	@Length(min=0, max=16, message="DAC长度必须介于 0 和 16 之间")
	public String getDac() {
		return dac;
	}

	public void setDac(String dac) {
		this.dac = dac;
	}
	
	@Length(min=0, max=250, message="开通组渠道长度必须介于 0 和 250 之间")
	public String getOpenGrpChnlNo() {
		return openGrpChnlNo;
	}

	public void setOpenGrpChnlNo(String openGrpChnlNo) {
		this.openGrpChnlNo = openGrpChnlNo;
	}
	
	@Length(min=0, max=8, message="清算日期长度必须介于 0 和 8 之间")
	public String getClrDate() {
		return clrDate;
	}

	public void setClrDate(String clrDate) {
		this.clrDate = clrDate;
	}
	
	@Length(min=0, max=20, message="brch_tp长度必须介于 0 和 20 之间")
	public String getBrchTp() {
		return brchTp;
	}

	public void setBrchTp(String brchTp) {
		this.brchTp = brchTp;
	}
	
	@Length(min=0, max=8, message="crtr长度必须介于 0 和 8 之间")
	public String getCrtr() {
		return crtr;
	}

	public void setCrtr(String crtr) {
		this.crtr = crtr;
	}
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("CtrlTPipBusiDO [ ");
		sb.append("busiNo="+busiNo+" , ");
		sb.append("entrNo="+entrNo+" , ");
		sb.append("busiName="+busiName+" , ");
		sb.append("saleProdCode="+saleProdCode+" , ");
		sb.append("stat="+stat+" , ");
		sb.append("busiDesc="+busiDesc+" , ");
		sb.append("clrTp="+clrTp+" , ");
		sb.append("signFlg="+signFlg+" , ");
		sb.append("chkFlg="+chkFlg+" , ");
		sb.append("feeTp="+feeTp+" , ");
		sb.append("busiTp="+busiTp+" , ");
		sb.append("relatSys="+relatSys+" , ");
		sb.append("uptTime="+uptTime+" , ");
		sb.append("modStat="+modStat+" , ");
		sb.append("lastUptTime="+lastUptTime+" , ");
		sb.append("shortRmrk="+shortRmrk+" , ");
		sb.append("midRmrk="+midRmrk+" , ");
		sb.append("longRmrk="+longRmrk+" , ");
		sb.append("dac="+dac+" , ");
		sb.append("openGrpChnlNo="+openGrpChnlNo+" , ");
		sb.append("clrDate="+clrDate+" , ");
		sb.append("brchTp="+brchTp+" , ");
		sb.append("crtr="+crtr+" , ");
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
		list.add("busiNo");
		return list;
	}
}