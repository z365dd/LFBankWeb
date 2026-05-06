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

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;

/**
 * 文件转换参数配置表
 * @author zhengjt
 * @version 20200630
 */
public class TfsvrFileFmtListDO {
	
	private String chgNo;		// chg_no
	private String chgName;
	private String tempFmtNo;		// temp_name
	private String tempFmtName;		// temp_name
	private String inFmtNo;		// in_fmt_no
	private String inFmtName;		// in_fmt_name
	private String outFmtNo;		// out_fmt_no
	private String outFmtName;		// out_fmt_name
	private String compNo;		// comp_no
	private String compName;		// comp_name
	private String strDate;		
	private String endDate;
	
	
	public String getChgName() {
		return chgName;
	}
	public void setChgName(String chgName) {
		this.chgName = chgName;
	}
	public String getTempFmtName() {
		return tempFmtName;
	}
	public void setTempFmtName(String tempFmtName) {
		this.tempFmtName = tempFmtName;
	}
	public String getChgNo() {
		return chgNo;
	}
	public void setChgNo(String chgNo) {
		this.chgNo = chgNo;
	}
	public String getTempFmtNo() {
		return tempFmtNo;
	}
	public void setTempFmtNo(String tempFmtNo) {
		this.tempFmtNo = tempFmtNo;
	}
	public String getInFmtNo() {
		return inFmtNo;
	}
	public void setInFmtNo(String inFmtNo) {
		this.inFmtNo = inFmtNo;
	}
	public String getInFmtName() {
		return inFmtName;
	}
	public void setInFmtName(String inFmtName) {
		this.inFmtName = inFmtName;
	}
	public String getOutFmtNo() {
		return outFmtNo;
	}
	public void setOutFmtNo(String outFmtNo) {
		this.outFmtNo = outFmtNo;
	}
	public String getOutFmtName() {
		return outFmtName;
	}
	public void setOutFmtName(String outFmtName) {
		this.outFmtName = outFmtName;
	}
	public String getCompNo() {
		return compNo;
	}
	public void setCompNo(String compNo) {
		this.compNo = compNo;
	}
	public String getCompName() {
		return compName;
	}
	public void setCompName(String compName) {
		this.compName = compName;
	}
	public String getStrDate() {
		return strDate;
	}
	public void setStrDate(String strDate) {
		this.strDate = strDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}		
	

}