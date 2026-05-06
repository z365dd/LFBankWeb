/**
* 系统名称: SmartWeb平台
* 模块名称: comp.fsvr.tec的实体类模块
* 功能描述: 文件格式明细参数配置表数据定义
* 类 名 称  : TfsvrFileDtlParaDO.java
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
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import com.adtec.sys.common.persistence.BaseDO;

/**
 * 文件格式明细参数配置表
 * @author zhengjt
 * @version 20200630
 */
public class TfsvrFileDtlParaDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String fmtNo;		// fmt_no
	private String fileFlg;		// flg
	private Long ser;		// ser
	private Long lineNum;		// line_num
	private String lineNo;		// line_no
	private String colNo;		// col_no
	private String colName;		// col_name
	private String fileColTp;		// col_tp
	private Long colLen;		// col_len
	private String alignMeth;		// align_meth
	private String dltSymTp;		// dlt_sym_tp
	private String colKd;		// col_kd
	private String tabColName;		// tab_col_name
	private String defVal;		// def_val
	private String chgFlg;		// chg_flg
	private String shortRmrk;		// short_rmrk
	private String midRmrk;		// mid_rmrk
	private String longRmrk;		// long_rmrk
	private String dac;		// dac
	private String lastUptTime;		// last_upt_time
	private String chgClobFlg;
	
	public TfsvrFileDtlParaDO() {
		fmtNo="";		// fmt_no
		fileFlg="";		// flg
		ser=(long) 0;		// ser
		lineNum=(long) 0;		// line_num
		lineNo="";		// line_no
		colNo="";		// col_no
		colName="";		// col_name
		fileColTp="";		// col_tp
		colLen=(long) 0;		// col_len
		alignMeth="";		// align_meth
		dltSymTp="";		// dlt_sym_tp
		colKd="";		// col_kd
		tabColName="";		// tab_col_name
		defVal="";		// def_val
		chgFlg="";		// chg_flg
		shortRmrk="";		// short_rmrk
		midRmrk="";		// mid_rmrk
		longRmrk="";		// long_rmrk
		dac="";		// dac
		lastUptTime="";		// last_upt_time
	}

	public TfsvrFileDtlParaDO(String id){
		super(id);
	}

	@Length(min=1, max=64, message="fmt_no长度必须介于 1 和 64 之间")
	public String getFmtNo() {
		return fmtNo;
	}

	public void setFmtNo(String fmtNo) {
		this.fmtNo = fmtNo;
	}
	
	@Length(min=0, max=2, message="flg长度必须介于 0 和 2 之间")
	public String getFileFlg() {
		return fileFlg;
	}

	public void setFileFlg(String fileFlg) {
		this.fileFlg = fileFlg;
	}
	
	@NotNull(message="ser不能为空")
	public Long getSer() {
		return ser;
	}

	public void setSer(Long ser) {
		this.ser = ser;
	}
	
	public Long getLineNum() {
		return lineNum;
	}

	public void setLineNum(Long lineNum) {
		this.lineNum = lineNum;
	}
	
	@Length(min=0, max=20, message="line_no长度必须介于 0 和 20 之间")
	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	
	@Length(min=0, max=30, message="col_no长度必须介于 0 和 30 之间")
	public String getColNo() {
		return colNo;
	}

	public void setColNo(String colNo) {
		this.colNo = colNo;
	}
	
	@Length(min=0, max=120, message="col_name长度必须介于 0 和 120 之间")
	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}
	
	@Length(min=0, max=2, message="col_tp长度必须介于 0 和 2 之间")
	public String getFileColTp() {
		return fileColTp;
	}

	public void setFileColTp(String fileColTp) {
		this.fileColTp = fileColTp;
	}
	
	public Long getColLen() {
		return colLen;
	}

	public void setColLen(Long colLen) {
		this.colLen = colLen;
	}
	
	@Length(min=0, max=2, message="align_meth长度必须介于 0 和 2 之间")
	public String getAlignMeth() {
		return alignMeth;
	}

	public void setAlignMeth(String alignMeth) {
		this.alignMeth = alignMeth;
	}
	
	@Length(min=0, max=2, message="dlt_sym_tp长度必须介于 0 和 2 之间")
	public String getDltSymTp() {
		return dltSymTp;
	}

	public void setDltSymTp(String dltSymTp) {
		this.dltSymTp = dltSymTp;
	}
	
	@Length(min=0, max=10, message="col_kd长度必须介于 0 和 10 之间")
	public String getColKd() {
		return colKd;
	}

	public void setColKd(String colKd) {
		this.colKd = colKd;
	}
	
	@Length(min=0, max=120, message="tab_col_name长度必须介于 0 和 120 之间")
	public String getTabColName() {
		return tabColName;
	}

	public void setTabColName(String tabColName) {
		this.tabColName = tabColName;
	}
	
	@Length(min=0, max=256, message="def_val长度必须介于 0 和 256 之间")
	public String getDefVal() {
		return defVal;
	}

	public void setDefVal(String defVal) {
		this.defVal = defVal;
	}
	
	@Length(min=0, max=2, message="chg_flg长度必须介于 0 和 2 之间")
	public String getChgFlg() {
		return chgFlg;
	}

	public void setChgFlg(String chgFlg) {
		this.chgFlg = chgFlg;
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
		sb.append("TfsvrFileDtlParaDO [ ");
		sb.append("fmtNo="+fmtNo+" , ");
		sb.append("fileFlg="+fileFlg+" , ");
		sb.append("ser="+ser+" , ");
		sb.append("lineNum="+lineNum+" , ");
		sb.append("lineNo="+lineNo+" , ");
		sb.append("colNo="+colNo+" , ");
		sb.append("colName="+colName+" , ");
		sb.append("fileColTp="+fileColTp+" , ");
		sb.append("colLen="+colLen+" , ");
		sb.append("alignMeth="+alignMeth+" , ");
		sb.append("dltSymTp="+dltSymTp+" , ");
		sb.append("colKd="+colKd+" , ");
		sb.append("tabColName="+tabColName+" , ");
		sb.append("defVal="+defVal+" , ");
		sb.append("chgFlg="+chgFlg+" , ");
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
		list.add("fmtNo");
		list.add("flg");
		list.add("ser");
		return list;
	}
}