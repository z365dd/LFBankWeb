package com.adtec.framework.ide.po;

/**  
 * @类名 IdePo.java  
 * @描述: 
 *     EIDE 
 * @版本 v1.0  
 */

public class IdePo {
	
	private String jspfile;
	
	private String path;
	
	private String poContent;
	
	private String interfaceXmlfile;

	public String getJspfile() {
		return jspfile;
	}

	public void setJspfile(String jspfile) {
		this.jspfile = jspfile;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public String getInterfaceXmlfile() {
		return interfaceXmlfile;
	}

	public void setInterfaceXmlfile(String interfaceXmlfile) {
		this.interfaceXmlfile = interfaceXmlfile;
	}

	public String getPoContent() {
		return poContent;
	}

	public void setPoContent(String poContent) {
		this.poContent = poContent;
	}
}
