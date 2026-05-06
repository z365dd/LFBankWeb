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


/**
 * 文件格式明细参数配置表
 * @version 20200630
 */
public class TfmngFileDtlReqListDTO {
	
	private String inKv;		//内部值
	private String outKv;		//外部值
	private String action;	
	
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getInKv() {
		return inKv;
	}
	public void setInKv(String inKv) {
		this.inKv = inKv;
	}
	public String getOutKv() {
		return outKv;
	}
	public void setOutKv(String outKv) {
		this.outKv = outKv;
	}
	
	
}