/**
* 系统名称: SmartWeb平台
* 模块名称: comp.ctrl.oper的实体类模块
* 功能描述: 数据装载管理数据定义
* 类 名 称  : CtrlTParaLoadDataDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200310<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.comp.ctrl.oper.entity;

import org.hibernate.validator.constraints.Length;
import java.util.ArrayList;
import java.util.List;
import com.adtec.sys.common.persistence.BaseDO;

/**
 * 数据装载管理
 * @author zhengjt
 * @version 20200310
 */
public class CtrlTParaLoadDataDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String paraName;		// 参数名称
	private String tabName;		// 表名称
	private String srcTabDesc;		// 来源表描述
	private String sameDbFlg;		// 是否同库
	private String srcDataSrc;		// 来源数据源
	private String lastUptTime;		// last_upt_time
	private String shortRmrk;		// short_rmrk
	private String midRmrk;		// mid_rmrk
	private String longRmrk;		// long_rmrk
	private String dac;		// dac
	
	

	public String getSameDbFlg() {
		return sameDbFlg;
	}

	public void setSameDbFlg(String sameDbFlg) {
		this.sameDbFlg = sameDbFlg;
	}

	public String getSrcDataSrc() {
		return srcDataSrc;
	}

	public void setSrcDataSrc(String srcDataSrc) {
		this.srcDataSrc = srcDataSrc;
	}

	public CtrlTParaLoadDataDO() {
		super();
	}

	public CtrlTParaLoadDataDO(String id){
		super(id);
	}

	@Length(min=1, max=120, message="参数名称长度必须介于 1 和 120 之间")
	public String getParaName() {
		return paraName;
	}

	public void setParaName(String paraName) {
		this.paraName = paraName;
	}
	
	@Length(min=1, max=120, message="表名称长度必须介于 1 和 120 之间")
	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
	
	@Length(min=0, max=360, message="来源表描述长度必须介于 0 和 360 之间")
	public String getSrcTabDesc() {
		return srcTabDesc;
	}

	public void setSrcTabDesc(String srcTabDesc) {
		this.srcTabDesc = srcTabDesc;
	}
	
	@Length(min=1, max=20, message="last_upt_time长度必须介于 1 和 20 之间")
	public String getLastUptTime() {
		return lastUptTime;
	}

	public void setLastUptTime(String lastUptTime) {
		this.lastUptTime = lastUptTime;
	}
	
	@Length(min=1, max=64, message="short_rmrk长度必须介于 1 和 64 之间")
	public String getShortRmrk() {
		return shortRmrk;
	}

	public void setShortRmrk(String shortRmrk) {
		this.shortRmrk = shortRmrk;
	}
	
	@Length(min=1, max=128, message="mid_rmrk长度必须介于 1 和 128 之间")
	public String getMidRmrk() {
		return midRmrk;
	}

	public void setMidRmrk(String midRmrk) {
		this.midRmrk = midRmrk;
	}
	
	@Length(min=1, max=256, message="long_rmrk长度必须介于 1 和 256 之间")
	public String getLongRmrk() {
		return longRmrk;
	}

	public void setLongRmrk(String longRmrk) {
		this.longRmrk = longRmrk;
	}
	
	@Length(min=1, max=16, message="dac长度必须介于 1 和 16 之间")
	public String getDac() {
		return dac;
	}

	public void setDac(String dac) {
		this.dac = dac;
	}
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("CtrlTParaLoadDataDO [ ");
		sb.append("paraName="+paraName+" , ");
		sb.append("tabName="+tabName+" , ");
		sb.append("srcTabDesc="+srcTabDesc+" , ");
		sb.append("lastUptTime="+lastUptTime+" , ");
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
		list.add("delFlg");
		list.add("id");
		list.add("crtr");
		list.add("crtTime");
		list.add("uptr");
		list.add("uptTime");
		list.add("rmrk");
		return list;
	}
	
	@Override
	public List<String> getMatchFields() {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		list.add("paraName");
		return list;
	}
}