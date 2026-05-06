/**
* 系统名称: SmartWeb平台
* 模块名称: comp.ctrl.oper的实体类模块
* 功能描述: 渠道管理数据定义
* 类 名 称  : CtrlTParaChnlDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200310<br>
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
 * 渠道管理
 * @author zhengjt
 * @version 20200310
 */
public class CtrlTParaChnlDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String chnlNo;		// 渠道号
	private String chnlName;		// 渠道名称
	private String chnlStat;		// 渠道状态
	private String chnlDesc;		// 渠道描述
	private String brch;		// 机构
	private String chnlTp;		// 渠道类型
	private String chnlFlg;		// 渠道标志
	private String seqCrtId;		// seq_crt_id
	private String tlrNo;		// 柜员
	private String relatSys;		// 关联系统
	private String lastUptTime;		// last_upt_time
	private String shortRmrk;		// short_rmrk
	private String midRmrk;		// mid_rmrk
	private String longRmrk;		// long_rmrk
	private String dac;		// dac
	
	public CtrlTParaChnlDO() {
		super();
	}

	public CtrlTParaChnlDO(String id){
		super(id);
	}

	@Length(min=1, max=6, message="渠道号长度必须介于 1 和 6 之间")
	public String getChnlNo() {
		return chnlNo;
	}

	public void setChnlNo(String chnlNo) {
		this.chnlNo = chnlNo;
	}
	
	@Length(min=0, max=120, message="渠道名称长度必须介于 0 和 120 之间")
	public String getChnlName() {
		return chnlName;
	}

	public void setChnlName(String chnlName) {
		this.chnlName = chnlName;
	}
	
	@Length(min=0, max=3, message="渠道状态长度必须介于 0 和 3 之间")
	public String getChnlStat() {
		return chnlStat;
	}

	public void setChnlStat(String chnlStat) {
		this.chnlStat = chnlStat;
	}
	
	@Length(min=0, max=360, message="渠道描述长度必须介于 0 和 360 之间")
	public String getChnlDesc() {
		return chnlDesc;
	}

	public void setChnlDesc(String chnlDesc) {
		this.chnlDesc = chnlDesc;
	}
	
	@Length(min=0, max=20, message="机构长度必须介于 0 和 20 之间")
	public String getBrch() {
		return brch;
	}

	public void setBrch(String brch) {
		this.brch = brch;
	}
	
	@Length(min=1, max=2, message="渠道类型长度必须介于 1 和 2 之间")
	public String getChnlTp() {
		return chnlTp;
	}

	public void setChnlTp(String chnlTp) {
		this.chnlTp = chnlTp;
	}
	
	@Length(min=1, max=20, message="渠道标志长度必须介于 1 和 20 之间")
	public String getChnlFlg() {
		return chnlFlg;
	}

	public void setChnlFlg(String chnlFlg) {
		this.chnlFlg = chnlFlg;
	}
	
	@Length(min=0, max=64, message="seq_crt_id长度必须介于 0 和 64 之间")
	public String getSeqCrtId() {
		return seqCrtId;
	}

	public void setSeqCrtId(String seqCrtId) {
		this.seqCrtId = seqCrtId;
	}
	
	@Length(min=0, max=16, message="柜员长度必须介于 0 和 16 之间")
	public String getTlrNo() {
		return tlrNo;
	}

	public void setTlrNo(String tlrNo) {
		this.tlrNo = tlrNo;
	}
	
	@Length(min=0, max=10, message="关联系统长度必须介于 0 和 10 之间")
	public String getRelatSys() {
		return relatSys;
	}

	public void setRelatSys(String relatSys) {
		this.relatSys = relatSys;
	}
	
	@Length(min=1, max=20, message="last_upt_time长度必须介于 1 和 20 之间")
	public String getLastUptTime() {
		return lastUptTime;
	}

	public void setLastUptTime(String lastUptTime) {
		this.lastUptTime = lastUptTime;
	}
	
	@Length(min=1, max=64, message="short_rmrk长度必须介于 1 和 64 之间")
	public String getShortRmrk() {
		return shortRmrk;
	}

	public void setShortRmrk(String shortRmrk) {
		this.shortRmrk = shortRmrk;
	}
	
	@Length(min=1, max=128, message="mid_rmrk长度必须介于 1 和 128 之间")
	public String getMidRmrk() {
		return midRmrk;
	}

	public void setMidRmrk(String midRmrk) {
		this.midRmrk = midRmrk;
	}
	
	@Length(min=1, max=256, message="long_rmrk长度必须介于 1 和 256 之间")
	public String getLongRmrk() {
		return longRmrk;
	}

	public void setLongRmrk(String longRmrk) {
		this.longRmrk = longRmrk;
	}
	
	@Length(min=1, max=16, message="dac长度必须介于 1 和 16 之间")
	public String getDac() {
		return dac;
	}

	public void setDac(String dac) {
		this.dac = dac;
	}
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("CtrlTParaChnlDO [ ");
		sb.append("chnlNo="+chnlNo+" , ");
		sb.append("chnlName="+chnlName+" , ");
		sb.append("chnlStat="+chnlStat+" , ");
		sb.append("chnlDesc="+chnlDesc+" , ");
		sb.append("brch="+brch+" , ");
		sb.append("chnlTp="+chnlTp+" , ");
		sb.append("chnlFlg="+chnlFlg+" , ");
		sb.append("seqCrtId="+seqCrtId+" , ");
		sb.append("tlrNo="+tlrNo+" , ");
		sb.append("relatSys="+relatSys+" , ");
		sb.append("lastUptTime="+lastUptTime+" , ");
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
		list.add("chnlNo");
		list.add("chnlTp");
		return list;
	}
}