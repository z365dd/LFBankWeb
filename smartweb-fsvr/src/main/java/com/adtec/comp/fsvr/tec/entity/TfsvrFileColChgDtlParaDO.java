/**
* 系统名称: SmartWeb平台
* 模块名称: comp.fsvr.tec的实体类模块
* 功能描述: 文件格式字段转换明细参数表数据定义
* 类 名 称  : TfsvrFileColChgDtlParaDO.java
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
 * 文件格式字段转换明细参数表
 * @author zhengjt
 * @version 20200630
 */
public class TfsvrFileColChgDtlParaDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String chgNo;		// chg_no
	private String chgName;		// chg_name
	private String fileFlg;		// flg
	private Long ser;		// ser
	private String colNo;		// col_no
	private String colName;		// col_name
	private String inKv;		// in_kv
	private String outKv;		// out_kv
	private Long colSer;		// col_ser
	private String compTp;		// comp_tp
	private String busiTp;		// busi_tp
	private String shortRmrk;		// short_rmrk
	private String midRmrk;		// mid_rmrk
	private String longRmrk;		// long_rmrk
	private String dac;		// dac
	private String lastUptTime;		// last_upt_time
	
	public TfsvrFileColChgDtlParaDO() {
		chgNo="";		// chg_no
		chgName="";		// chg_name
		fileFlg="";		// flg
		ser=(long) 0;		// ser
		colNo="";		// col_no
		colName="";		// col_name
		inKv="";		// in_kv
		outKv="";		// out_kv
		colSer=(long) 0;		// col_ser
		compTp="";		// comp_tp
		busiTp="";		// busi_tp
		shortRmrk="";		// short_rmrk
		midRmrk="";		// mid_rmrk
		longRmrk="";		// long_rmrk
		dac="";		// dac
		lastUptTime="";		// last_upt_time
	}

	public TfsvrFileColChgDtlParaDO(String id){
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
	
	@Length(min=1, max=2, message="flg长度必须介于 1 和 2 之间")
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
	
	@Length(min=0, max=256, message="in_kv长度必须介于 0 和 256 之间")
	public String getInKv() {
		return inKv;
	}

	public void setInKv(String inKv) {
		this.inKv = inKv;
	}
	
	@Length(min=0, max=256, message="out_kv长度必须介于 0 和 256 之间")
	public String getOutKv() {
		return outKv;
	}

	public void setOutKv(String outKv) {
		this.outKv = outKv;
	}
	
	public Long getColSer() {
		return colSer;
	}

	public void setColSer(Long colSer) {
		this.colSer = colSer;
	}
	
	@Length(min=0, max=2, message="comp_tp长度必须介于 0 和 2 之间")
	public String getCompTp() {
		return compTp;
	}

	public void setCompTp(String compTp) {
		this.compTp = compTp;
	}
	
	@Length(min=0, max=2, message="busi_tp长度必须介于 0 和 2 之间")
	public String getBusiTp() {
		return busiTp;
	}

	public void setBusiTp(String busiTp) {
		this.busiTp = busiTp;
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
		sb.append("TfsvrFileColChgDtlParaDO [ ");
		sb.append("chgNo="+chgNo+" , ");
		sb.append("chgName="+chgName+" , ");
		sb.append("fileFlg="+fileFlg+" , ");
		sb.append("ser="+ser+" , ");
		sb.append("colNo="+colNo+" , ");
		sb.append("colName="+colName+" , ");
		sb.append("inKv="+inKv+" , ");
		sb.append("outKv="+outKv+" , ");
		sb.append("colSer="+colSer+" , ");
		sb.append("compTp="+compTp+" , ");
		sb.append("busiTp="+busiTp+" , ");
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
		list.add("flg");
		list.add("ser");
		return list;
	}
}