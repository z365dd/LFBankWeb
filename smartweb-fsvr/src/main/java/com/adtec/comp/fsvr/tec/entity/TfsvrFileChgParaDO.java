/**
* 系统名称: SmartWeb平台
* 模块名称: comp.fsvr.tec的实体类模块
* 功能描述: 文件转换参数配置表数据定义
* 类 名 称  : TfsvrFileChgParaDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200630<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.comp.fsvr.tec.entity;

import org.hibernate.validator.constraints.Length;
import java.util.ArrayList;
import java.util.List;
import com.adtec.sys.common.persistence.BaseDO;

/**
 * 文件转换参数配置表
 * @author zhengjt
 * @version 20200630
 */
public class TfsvrFileChgParaDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String chgNo;		// chg_no
	private String chgName;		// chg_name
	private String inFmtNo;		// in_fmt_no
	private String inFmtName;		// in_fmt_name
	private String outFmtNo;		// out_fmt_no
	private String outFmtName;		// out_fmt_name
	private String compNo;		// comp_no
	private String compName;		// comp_name
	private String regDate;		// reg_date
	private String regBrch;		// reg_brch
	private String regTlrNo;		// reg_tlr_no
	private String regTime;		// reg_time
	private String modBrch;		// mod_brch
	private String modTlrNo;		// mod_tlr_no
	private String modDate;		// mod_date
	private String modTime;		// mod_time
	private String shortRmrk;		// short_rmrk
	private String midRmrk;		// mid_rmrk
	private String longRmrk;		// long_rmrk
	private String dac;		// dac
	private String lastUptTime;		// last_upt_time
	
	public TfsvrFileChgParaDO() {
		chgNo="";		// chg_no
		chgName="";		// chg_name
		inFmtNo="";		// in_fmt_no
		inFmtName="";		// in_fmt_name
		outFmtNo="";		// out_fmt_no
		outFmtName="";		// out_fmt_name
		compNo="";		// comp_no
		compName="";		// comp_name
		regDate="";		// reg_date
		regBrch="";		// reg_brch
		regTlrNo="";		// reg_tlr_no
		regTime="";		// reg_time
		modBrch="";		// mod_brch
		modTlrNo="";		// mod_tlr_no
		modDate="";		// mod_date
		modTime="";		// mod_time
	shortRmrk="";		// short_rmrk
		midRmrk="";		// mid_rmrk
		longRmrk="";		// long_rmrk
		dac="";		// dac
		lastUptTime="";		// last_upt_time
	}

	public TfsvrFileChgParaDO(String id){
		super(id);
	}

	@Length(min=1, max=64, message="chg_no长度必须介于 1 和 64 之间")
	public String getChgNo() {
		return chgNo;
	}

	public void setChgNo(String chgNo) {
		this.chgNo = chgNo;
	}
	
	@Length(min=0, max=120, message="chg_name长度必须介于 0 和 120 之间")
	public String getChgName() {
		return chgName;
	}

	public void setChgName(String chgName) {
		this.chgName = chgName;
	}
	
	@Length(min=1, max=64, message="in_fmt_no长度必须介于 1 和 64 之间")
	public String getInFmtNo() {
		return inFmtNo;
	}

	public void setInFmtNo(String inFmtNo) {
		this.inFmtNo = inFmtNo;
	}
	
	@Length(min=0, max=120, message="in_fmt_name长度必须介于 0 和 120 之间")
	public String getInFmtName() {
		return inFmtName;
	}

	public void setInFmtName(String inFmtName) {
		this.inFmtName = inFmtName;
	}
	
	@Length(min=0, max=64, message="out_fmt_no长度必须介于 0 和 64 之间")
	public String getOutFmtNo() {
		return outFmtNo;
	}

	public void setOutFmtNo(String outFmtNo) {
		this.outFmtNo = outFmtNo;
	}
	
	@Length(min=0, max=120, message="out_fmt_name长度必须介于 0 和 120 之间")
	public String getOutFmtName() {
		return outFmtName;
	}

	public void setOutFmtName(String outFmtName) {
		this.outFmtName = outFmtName;
	}
	
	@Length(min=0, max=6, message="comp_no长度必须介于 0 和 6 之间")
	public String getCompNo() {
		return compNo;
	}

	public void setCompNo(String compNo) {
		this.compNo = compNo;
	}
	
	@Length(min=0, max=120, message="comp_name长度必须介于 0 和 120 之间")
	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}
	
	@Length(min=0, max=8, message="reg_date长度必须介于 0 和 8 之间")
	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	
	@Length(min=0, max=20, message="reg_brch长度必须介于 0 和 20 之间")
	public String getRegBrch() {
		return regBrch;
	}

	public void setRegBrch(String regBrch) {
		this.regBrch = regBrch;
	}
	
	@Length(min=0, max=16, message="reg_tlr_no长度必须介于 0 和 16 之间")
	public String getRegTlrNo() {
		return regTlrNo;
	}

	public void setRegTlrNo(String regTlrNo) {
		this.regTlrNo = regTlrNo;
	}
	
	@Length(min=0, max=20, message="reg_time长度必须介于 0 和 20 之间")
	public String getRegTime() {
		return regTime;
	}

	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}
	
	@Length(min=0, max=20, message="mod_brch长度必须介于 0 和 20 之间")
	public String getModBrch() {
		return modBrch;
	}

	public void setModBrch(String modBrch) {
		this.modBrch = modBrch;
	}
	
	@Length(min=0, max=16, message="mod_tlr_no长度必须介于 0 和 16 之间")
	public String getModTlrNo() {
		return modTlrNo;
	}

	public void setModTlrNo(String modTlrNo) {
		this.modTlrNo = modTlrNo;
	}
	
	@Length(min=0, max=8, message="mod_date长度必须介于 0 和 8 之间")
	public String getModDate() {
		return modDate;
	}

	public void setModDate(String modDate) {
		this.modDate = modDate;
	}
	
	@Length(min=0, max=20, message="mod_time长度必须介于 0 和 20 之间")
	public String getModTime() {
		return modTime;
	}

	public void setModTime(String modTime) {
		this.modTime = modTime;
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
	
	@Length(min=0, max=20, message="last_upt_time长度必须介于 0 和 20 之间")
	public String getLastUptTime() {
		return lastUptTime;
	}

	public void setLastUptTime(String lastUptTime) {
		this.lastUptTime = lastUptTime;
	}
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("TfsvrFileChgParaDO [ ");
		sb.append("chgNo="+chgNo+" , ");
		sb.append("chgName="+chgName+" , ");
		sb.append("inFmtNo="+inFmtNo+" , ");
		sb.append("inFmtName="+inFmtName+" , ");
		sb.append("outFmtNo="+outFmtNo+" , ");
		sb.append("outFmtName="+outFmtName+" , ");
		sb.append("compNo="+compNo+" , ");
		sb.append("compName="+compName+" , ");
		sb.append("regDate="+regDate+" , ");
		sb.append("regBrch="+regBrch+" , ");
		sb.append("regTlrNo="+regTlrNo+" , ");
		sb.append("regTime="+regTime+" , ");
		sb.append("modBrch="+modBrch+" , ");
		sb.append("modTlrNo="+modTlrNo+" , ");
		sb.append("modDate="+modDate+" , ");
		sb.append("modTime="+modTime+" , ");
		sb.append("shortRmrk="+shortRmrk+" , ");
		sb.append("midRmrk="+midRmrk+" , ");
		sb.append("longRmrk="+longRmrk+" , ");
		sb.append("dac="+dac+" , ");
		sb.append("lastUptTime="+lastUptTime+" , ");
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
		list.add("chgNo");
		return list;
	}
}