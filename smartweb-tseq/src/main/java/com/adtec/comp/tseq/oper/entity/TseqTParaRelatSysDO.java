package com.adtec.comp.tseq.oper.entity;

import org.hibernate.validator.constraints.Length;
import java.util.ArrayList;
import java.util.List;
import com.adtec.sys.common.persistence.BaseDO;

public class TseqTParaRelatSysDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String relatSys;		// 关联系统
	private String sysTp;		// 系统类型
	private String sysName;		// 系统名称
	private String membId;		// 参与者ID
	private String stat;		// 状态
	private String sysStat;		// 系统状态
	private String origSysStat;		// 原系统状态
	private String clrBrch;		// 清算机构
	private String clrBank;		// 清算行号
	private String othDate;		// 第三方日期
	private String origOthDate;		// 原第三方日期
	private String nodeStat;		// 节点状态
	private String loginStat;		// 登录状态
	private String hldFlg;		// 节假日标志
	private String msgSkey;		// 报文密钥
	private String seqCrtId;		// 流水生成标识号
	private String fileSvrId;		// 文件服务器标识号
	private String loginId;		// 登录标识号
	private String loginPwd;		// 登录密码
	private String reasnDesc;		// 原因描述
	private String dimFlg;		// 维度标志
	private String lastUptTime;		// 最后更新时间
	private String shortRmrk;		// 短备注
	private String midRmrk;		// 中备注
	private String longRmrk;		// 长备注
	private String dac;		// DAC
	
	public TseqTParaRelatSysDO() {
		super();
	}

	public TseqTParaRelatSysDO(String id){
		super(id);
	}

	@Length(min=1, max=10, message="关联系统长度必须介于 1 和 10 之间")
	public String getRelatSys() {
		return relatSys;
	}

	public void setRelatSys(String relatSys) {
		this.relatSys = relatSys;
	}
	
	@Length(min=1, max=2, message="系统类型长度必须介于 1 和 2 之间")
	public String getSysTp() {
		return sysTp;
	}

	public void setSysTp(String sysTp) {
		this.sysTp = sysTp;
	}
	
	@Length(min=0, max=120, message="系统名称长度必须介于 0 和 120 之间")
	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	
	@Length(min=0, max=64, message="参与者ID长度必须介于 0 和 64 之间")
	public String getMembId() {
		return membId;
	}

	public void setMembId(String membId) {
		this.membId = membId;
	}
	
	@Length(min=0, max=5, message="状态长度必须介于 0 和 5 之间")
	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}
	
	@Length(min=0, max=5, message="系统状态长度必须介于 0 和 5 之间")
	public String getSysStat() {
		return sysStat;
	}

	public void setSysStat(String sysStat) {
		this.sysStat = sysStat;
	}
	
	@Length(min=0, max=5, message="原系统状态长度必须介于 0 和 5 之间")
	public String getOrigSysStat() {
		return origSysStat;
	}

	public void setOrigSysStat(String origSysStat) {
		this.origSysStat = origSysStat;
	}
	
	@Length(min=0, max=20, message="清算机构长度必须介于 0 和 20 之间")
	public String getClrBrch() {
		return clrBrch;
	}

	public void setClrBrch(String clrBrch) {
		this.clrBrch = clrBrch;
	}
	
	@Length(min=0, max=20, message="清算行号长度必须介于 0 和 20 之间")
	public String getClrBank() {
		return clrBank;
	}

	public void setClrBank(String clrBank) {
		this.clrBank = clrBank;
	}
	
	@Length(min=0, max=8, message="第三方日期长度必须介于 0 和 8 之间")
	public String getOthDate() {
		return othDate;
	}

	public void setOthDate(String othDate) {
		this.othDate = othDate;
	}
	
	@Length(min=0, max=8, message="原第三方日期长度必须介于 0 和 8 之间")
	public String getOrigOthDate() {
		return origOthDate;
	}

	public void setOrigOthDate(String origOthDate) {
		this.origOthDate = origOthDate;
	}
	
	@Length(min=0, max=5, message="节点状态长度必须介于 0 和 5 之间")
	public String getNodeStat() {
		return nodeStat;
	}

	public void setNodeStat(String nodeStat) {
		this.nodeStat = nodeStat;
	}
	
	@Length(min=0, max=5, message="登录状态长度必须介于 0 和 5 之间")
	public String getLoginStat() {
		return loginStat;
	}

	public void setLoginStat(String loginStat) {
		this.loginStat = loginStat;
	}
	
	@Length(min=0, max=4, message="节假日标志长度必须介于 0 和 4 之间")
	public String getHldFlg() {
		return hldFlg;
	}

	public void setHldFlg(String hldFlg) {
		this.hldFlg = hldFlg;
	}
	
	@Length(min=0, max=64, message="报文密钥长度必须介于 0 和 64 之间")
	public String getMsgSkey() {
		return msgSkey;
	}

	public void setMsgSkey(String msgSkey) {
		this.msgSkey = msgSkey;
	}
	
	@Length(min=0, max=64, message="流水生成标识号长度必须介于 0 和 64 之间")
	public String getSeqCrtId() {
		return seqCrtId;
	}

	public void setSeqCrtId(String seqCrtId) {
		this.seqCrtId = seqCrtId;
	}
	
	@Length(min=0, max=64, message="文件服务器标识号长度必须介于 0 和 64 之间")
	public String getFileSvrId() {
		return fileSvrId;
	}

	public void setFileSvrId(String fileSvrId) {
		this.fileSvrId = fileSvrId;
	}
	
	@Length(min=0, max=64, message="登录标识号长度必须介于 0 和 64 之间")
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
	@Length(min=0, max=80, message="登录密码长度必须介于 0 和 80 之间")
	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}
	
	@Length(min=0, max=360, message="原因描述长度必须介于 0 和 360 之间")
	public String getReasnDesc() {
		return reasnDesc;
	}

	public void setReasnDesc(String reasnDesc) {
		this.reasnDesc = reasnDesc;
	}
	
	@Length(min=0, max=20, message="维度标志长度必须介于 0 和 20 之间")
	public String getDimFlg() {
		return dimFlg;
	}

	public void setDimFlg(String dimFlg) {
		this.dimFlg = dimFlg;
	}
	
	@Length(min=1, max=20, message="最后更新时间长度必须介于 1 和 20 之间")
	public String getLastUptTime() {
		return lastUptTime;
	}

	public void setLastUptTime(String lastUptTime) {
		this.lastUptTime = lastUptTime;
	}
	
	@Length(min=1, max=64, message="短备注长度必须介于 1 和 64 之间")
	public String getShortRmrk() {
		return shortRmrk;
	}

	public void setShortRmrk(String shortRmrk) {
		this.shortRmrk = shortRmrk;
	}
	
	@Length(min=1, max=128, message="中备注长度必须介于 1 和 128 之间")
	public String getMidRmrk() {
		return midRmrk;
	}

	public void setMidRmrk(String midRmrk) {
		this.midRmrk = midRmrk;
	}
	
	@Length(min=1, max=256, message="长备注长度必须介于 1 和 256 之间")
	public String getLongRmrk() {
		return longRmrk;
	}

	public void setLongRmrk(String longRmrk) {
		this.longRmrk = longRmrk;
	}
	
	@Length(min=1, max=16, message="DAC长度必须介于 1 和 16 之间")
	public String getDac() {
		return dac;
	}

	public void setDac(String dac) {
		this.dac = dac;
	}
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("CtrlTParaRelatSysDO [ ");
		sb.append("relatSys="+relatSys+" , ");
		sb.append("sysTp="+sysTp+" , ");
		sb.append("sysName="+sysName+" , ");
		sb.append("membId="+membId+" , ");
		sb.append("stat="+stat+" , ");
		sb.append("sysStat="+sysStat+" , ");
		sb.append("origSysStat="+origSysStat+" , ");
		sb.append("clrBrch="+clrBrch+" , ");
		sb.append("clrBank="+clrBank+" , ");
		sb.append("othDate="+othDate+" , ");
		sb.append("origOthDate="+origOthDate+" , ");
		sb.append("nodeStat="+nodeStat+" , ");
		sb.append("loginStat="+loginStat+" , ");
		sb.append("hldFlg="+hldFlg+" , ");
		sb.append("msgSkey="+msgSkey+" , ");
		sb.append("seqCrtId="+seqCrtId+" , ");
		sb.append("fileSvrId="+fileSvrId+" , ");
		sb.append("loginId="+loginId+" , ");
		sb.append("loginPwd="+loginPwd+" , ");
		sb.append("reasnDesc="+reasnDesc+" , ");
		sb.append("dimFlg="+dimFlg+" , ");
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
		list.add("relatSys");
		return list;
	}
}