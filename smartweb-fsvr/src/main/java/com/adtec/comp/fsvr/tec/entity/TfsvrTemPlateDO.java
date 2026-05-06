/**
* 系统名称: SmartWeb平台
* 模块名称: comp.fsvr.tec的实体类模块
* 功能描述: 模板信息管理
* 类 名 称  : TfsvrTemPlateDO.java
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

import java.util.List;

/**
 * 网络信息管理
 * @author zhengjt
 * @version 20200624
 */
public class TfsvrTemPlateDO {
	
	private String temStr;//模板串
	
	private List<TfsvrTemPlateDelDO> headList;//头尾模板列表
	
	private List<TfsvrTemPlateDelDO> bodyList;//体模板列表

	public String getTemStr() {
		return temStr;
	}

	public void setTemStr(String temStr) {
		this.temStr = temStr;
	}

	public List<TfsvrTemPlateDelDO> getHeadList() {
		return headList;
	}

	public void setHeadList(List<TfsvrTemPlateDelDO> headList) {
		this.headList = headList;
	}

	public List<TfsvrTemPlateDelDO> getBodyList() {
		return bodyList;
	}

	public void setBodyList(List<TfsvrTemPlateDelDO> bodyList) {
		this.bodyList = bodyList;
	}
	
}