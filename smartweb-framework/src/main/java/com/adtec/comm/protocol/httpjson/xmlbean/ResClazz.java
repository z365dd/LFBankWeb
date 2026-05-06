/**
 * 系统名称: SmartWeb平台
 * 模块名称: 
 * 类 名 称: ResClazz.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2017年5月27日 上午9:46:46<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.comm.protocol.httpjson.xmlbean;

import java.io.Serializable;

/**
 * @author chenyl
 *
 */
public class ResClazz implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5104223690491514659L;
	/*全路径class*/
	private String id;
	/*自定义属性的描述*/
	private String name;
	/*在父类中定义的属性名称*/
	private String parentAttrName;
	
	public ResClazz(){}
	
	public ResClazz(String id, String name, String parentAttrName) {
		super();
		this.id = id;
		this.name = name;
		this.parentAttrName = parentAttrName;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentAttrName() {
		return parentAttrName;
	}
	public void setParentAttrName(String parentAttrName) {
		this.parentAttrName = parentAttrName;
	}
	
}
