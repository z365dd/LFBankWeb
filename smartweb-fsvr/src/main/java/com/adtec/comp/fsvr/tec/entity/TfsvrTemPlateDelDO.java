/**
* 系统名称: SmartWeb平台
* 模块名称: comp.fsvr.tec的实体类模块
* 功能描述: 模板信息管理
* 类 名 称  : TfsvrTemPlateDelDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200624<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.comp.fsvr.tec.entity;

/**
 * 模板信息
 * @author zhengjt
 * @version 20200624
 */
public class TfsvrTemPlateDelDO {
	
	private String fileFlg;//位置
	private Long ser;//序号
	private String colName;//名称
	private String fileColTp;//列字段类型
	
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
	public String getFileColTp() {
		return fileColTp;
	}
	public void setFileColTp(String fileColTp) {
		this.fileColTp = fileColTp;
	}
	
	
}