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
package com.adtec.comp.fsvr.dto;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 文件格式明细参数配置表
 * @version 20200630
 */
public class TfmngFileDtlReqDTO {
	
	private String fileFlg;		//位置 1-头 2-体 3-尾
	private Long ser;		//序号 
	private String colName;		//字段名称
	private Long colLen;		//字段长度
	private String alignMeth;		//对齐标志
	private String fileColTp;		//字段类型
	private String tempFlg;		//模板位置 1-头 2-体 3-尾
	private String tempSer;		//模板序号
	private String chgFlg;		//是否转换 Y-是 N-否
	private String clobFlg;		//是否转换 1-是 0-否
	private String action;		
	
	private String list ;
	
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getFileFlg() {
		return fileFlg;
	}
	public void setFileFlg(String fileFlg) {
		this.fileFlg = fileFlg;
	}
	public Long getSer() {
		return ser;
	}
	public void setSer(Long ser) {
		this.ser = ser;
	}
	public String getColName() {
		return colName;
	}
	public void setColName(String colName) {
		this.colName = colName;
	}
	public Long getColLen() {
		return colLen;
	}
	public void setColLen(Long colLen) {
		this.colLen = colLen;
	}
	public String getAlignMeth() {
		return alignMeth;
	}
	public void setAlignMeth(String alignMeth) {
		this.alignMeth = alignMeth;
	}
	public String getFileColTp() {
		return fileColTp;
	}
	public void setFileColTp(String fileColTp) {
		this.fileColTp = fileColTp;
	}
	public String getTempFlg() {
		return tempFlg;
	}
	public void setTempFlg(String tempFlg) {
		this.tempFlg = tempFlg;
	}
	public String getChgFlg() {
		return chgFlg;
	}
	public void setChgFlg(String chgFlg) {
		this.chgFlg = chgFlg;
	}
	public String getTempSer() {
		return tempSer;
	}
	public void setTempSer(String tempSer) {
		this.tempSer = tempSer;
	}
	public String getList() {
		return list;
	}
	public void setList(String list) {
		this.list = list;
	}
	public String getClobFlg() {
		return clobFlg;
	}
	public void setClobFlg(String clobFlg) {
		this.clobFlg = clobFlg;
	}
	
}