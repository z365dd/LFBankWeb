/**
* 系统名称: SmartWeb平台
* 模块名称: comp-prod/oper/definition/saleprod的实体类模块
* 功能描述: 可售产品管理数据定义
* 类 名 称  : TPipSaleProdDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200104<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.prod.oper.definition.saleprod.entity;

import java.util.ArrayList;
import java.util.List;
import com.adtec.sys.common.persistence.BaseDO;

/**
 * 可售产品管理
 * @author zengxj
 * @version 20200104
 */
public class TPipSaleProdDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String saleProdCode;		// 可售产品代码
	private String saleProdDesc;		// 可售产品描述
	private String prodLineCode;		// 产品线编号
	private String prodLineName;		// 产品线名称
	private String url;		// 产品线名称
	private String omUrl;		// 运维url
	private String shortRmrk;		// 短备注
	private String midRmrk;		// 中备注
	private String longRmrk;		// 长备注
	private String dac;		// DAC

	private String brchId;
	private String brchIdName;

	public TPipSaleProdDO() {
		super();
	}

	public TPipSaleProdDO(String id){
		super(id);
	}

	public String getSaleProdCode() {
		return saleProdCode;
	}

	public void setSaleProdCode(String saleProdCode) {
		this.saleProdCode = saleProdCode;
	}
	
	public String getSaleProdDesc() {
		return saleProdDesc;
	}

	public void setSaleProdDesc(String saleProdDesc) {
		this.saleProdDesc = saleProdDesc;
	}
	
	public String getProdLineCode() {
		return prodLineCode;
	}

	public void setProdLineCode(String prodLineCode) {
		this.prodLineCode = prodLineCode;
	}
	
	public String getProdLineName() {
		return prodLineName;
	}

	public void setProdLineName(String prodLineName) {
		this.prodLineName = prodLineName;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getOmUrl() {
		return omUrl;
	}

	public void setOmUrl(String omUrl) {
		this.omUrl = omUrl;
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

	public String getBrchId() {
		return brchId;
	}

	public void setBrchId(String brchId) {
		this.brchId = brchId;
	}

	public String getBrchIdName() {
		return brchIdName;
	}

	public void setBrchIdName(String brchIdName) {
		this.brchIdName = brchIdName;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("TPipSaleProdDO [ ");
		sb.append("saleProdCode="+saleProdCode+" , ");
		sb.append("saleProdDesc="+saleProdDesc+" , ");
		sb.append("prodLineCode="+prodLineCode+" , ");
		sb.append("prodLineName="+prodLineName+" , ");
		sb.append("url="+url+" , ");
		sb.append("omUrl="+omUrl+" , ");
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
		list.add("id");
		list.add("prodLineName");
		list.add("omUrl");
		list.add("delFlg");
		list.add("rmrk");
		list.add("brchIdName");
		return list;
	}
	
	/**
	 * 返回固定的匹配域ID
	 * @return
	 */
	@Override
	public List<String> getMatchFields(){
		//设置更新匹配条件
		List<String> matchField = new ArrayList<String>();
		matchField.add("saleProdCode");
		return matchField;
	}
}