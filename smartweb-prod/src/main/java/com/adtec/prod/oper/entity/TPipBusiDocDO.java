/**
* 系统名称: SmartWeb平台
* 模块名称: comp.prod.oper的实体类模块
* 功能描述: 业务文档表数据定义
* 类 名 称  : TPipBusiDocDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200114<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.prod.oper.entity;

import com.adtec.sys.common.persistence.BaseDO;

import java.util.ArrayList;
import java.util.List;

/**
 * 业务文档表
 * @author zh
 * @version 20200114
 */
public class TPipBusiDocDO extends BaseDO {

	private static final long serialVersionUID = 1L;
	private String busiNo;		// 业务编号
	private String ser;		// 序号
	private String fileName;		// 文档名称
	private String shortRmrk;		// 短备注
	private String midRmrk;		// 中备注
	private String longRmrk;		// 长备注
	private String dac;		// DAC
	private String fileTp;
	private String url;
	
	public TPipBusiDocDO() {
		super();
	}

	public TPipBusiDocDO(String id){
		super(id);
	}

	public String getBusiNo() {
		return busiNo;
	}

	public void setBusiNo(String busiNo) {
		this.busiNo = busiNo;
	}
	
	public String getSer() {
		return ser;
	}

	public void setSer(String ser) {
		this.ser = ser;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

	public String getFileTp() {
		return fileTp;
	}

	public void setFileTp(String fileTp) {
		this.fileTp = fileTp;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("TPipBusiDocDO [ ");
		sb.append("busiNo="+busiNo+" , ");
		sb.append("ser="+ser+" , ");
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


	public List<String> getMatchFields(){
		//设置更新匹配条件
		List<String> matchField = new ArrayList<String>();
		matchField.add("busiNo");
		return matchField;
	}
}