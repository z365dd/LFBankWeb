/**
* 系统名称: SmartWeb平台
* 模块名称: comp-prod/oper/definition/atom的实体类模块
* 功能描述: 原子产品服务关联数据定义
* 类 名 称  : TPipAtomProdSvcDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200103<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.prod.oper.definition.atom.entity;

import java.util.ArrayList;
import java.util.List;
import com.adtec.sys.common.persistence.BaseDO;

/**
 * 原子产品服务关联
 * @author zengxj
 * @version 20200103
 */
public class TPipAtomProdSvcDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String atomProdCode;		// 原子产品代码
	private String svcCode;		// 服务代码
	private String svcDesc;		// 服务描述
	private String sceneNo;		// 子服务代码
	private String sceneName;		// 子服务描述
	private String sceneDesc;
	private String shortRmrk;		// 短备注
	private String midRmrk;		// 中备注
	private String longRmrk;		// 长备注
	private String dac;		// DAC
	
	public TPipAtomProdSvcDO() {
		super();
	}

	public TPipAtomProdSvcDO(String id){
		super(id);
	}

	public String getAtomProdCode() {
		return atomProdCode;
	}

	public void setAtomProdCode(String atomProdCode) {
		this.atomProdCode = atomProdCode;
	}
	
	public String getSvcCode() {
		return svcCode;
	}

	public void setSvcCode(String svcCode) {
		this.svcCode = svcCode;
	}
	
	public String getSvcDesc() {
		return svcDesc;
	}

	public void setSvcDesc(String svcDesc) {
		this.svcDesc = svcDesc;
	}
	/**
	 * @return the scen
	 */
	public String getSceneNo() {
		return sceneNo;
	}

	/**
	 * @param scen the scen to set
	 */
	public void setSceneNo(String sceneNo) {
		this.sceneNo = sceneNo;
	}

	/**
	 * @return the scenName
	 */
	public String getSceneName() {
		return sceneName;
	}

	/**
	 * @param scenName the scenName to set
	 */
	public void setSceneName(String sceneName) {
		this.sceneName = sceneName;
	}

	/**
	 * @return the scenDesc
	 */
	public String getSceneDesc() {
		return sceneDesc;
	}

	/**
	 * @param scenDesc the scenDesc to set
	 */
	public void setSceneDesc(String sceneDesc) {
		this.sceneDesc = sceneDesc;
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
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("TPipAtomProdSvcDO [ ");
		sb.append("atomProdCode="+atomProdCode+" , ");
		sb.append("svcCode="+svcCode+" , ");
		sb.append("svcDesc="+svcDesc+" , ");
		sb.append("sceneNo="+sceneNo+" , ");
		sb.append("sceneName="+sceneName+" , ");
		sb.append("sceneDesc="+sceneDesc+" , ");
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
		list.add("delFlg");
		list.add("rmrk");
		list.add("crtr");
		list.add("crtTime");
		list.add("uptr");
		list.add("uptTime");
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
		matchField.add("atomProdCode");
		return matchField;
	}
}