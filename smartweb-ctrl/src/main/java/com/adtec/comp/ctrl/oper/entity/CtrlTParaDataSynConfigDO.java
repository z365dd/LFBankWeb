/**
* 系统名称: SmartWeb平台
* 模块名称: comp.ctrl.oper的实体类模块
* 功能描述: 数据同步数据定义
* 类 名 称  : CtrlTParaDataSynConfigDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200512<br>
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
 * 数据同步
 * @author zhengjt
 * @version 20200512
 */
public class CtrlTParaDataSynConfigDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String srcTp;		// 来源类型
	private String uptTp;		// 更新类型
	private String srcTabName;		// 来源表名
	private String srcDataSrc;		// 来源数据源
	private String srcTabDesc;		// 来源表描述
	private String relatSys;		// 关联系统
	private String sameDbFlg;		// 是否同库
	private String dstDataSrc;		// 目的数据源
	private String dstTabName;		// 目标表名
	private String dstTabDesc;		// 目的表描述
	private String shortRmrk;		// short_rmrk
	private String midRmrk;		// mid_rmrk
	private String longRmrk;		// long_rmrk
	private String dac;		// dac
	private String stepNo;		// 步骤号
	
	public CtrlTParaDataSynConfigDO() {
		super();
	}

	public CtrlTParaDataSynConfigDO(String id){
		super(id);
	}

	@Length(min=1, max=5, message="来源类型长度必须介于 1 和 5 之间")
	public String getSrcTp() {
		return srcTp;
	}

	public void setSrcTp(String srcTp) {
		this.srcTp = srcTp;
	}
	
	@Length(min=1, max=5, message="更新类型长度必须介于 1 和 5 之间")
	public String getUptTp() {
		return uptTp;
	}

	public void setUptTp(String uptTp) {
		this.uptTp = uptTp;
	}
	
	@Length(min=0, max=120, message="来源表名长度必须介于 0 和 120 之间")
	public String getSrcTabName() {
		return srcTabName;
	}

	public void setSrcTabName(String srcTabName) {
		this.srcTabName = srcTabName;
	}
	
	@Length(min=0, max=20, message="来源数据源长度必须介于 0 和 20 之间")
	public String getSrcDataSrc() {
		return srcDataSrc;
	}

	public void setSrcDataSrc(String srcDataSrc) {
		this.srcDataSrc = srcDataSrc;
	}
	
	@Length(min=0, max=360, message="来源表描述长度必须介于 0 和 360 之间")
	public String getSrcTabDesc() {
		return srcTabDesc;
	}

	public void setSrcTabDesc(String srcTabDesc) {
		this.srcTabDesc = srcTabDesc;
	}
	
	@Length(min=0, max=10, message="关联系统长度必须介于 0 和 10 之间")
	public String getRelatSys() {
		return relatSys;
	}

	public void setRelatSys(String relatSys) {
		this.relatSys = relatSys;
	}
	
	@Length(min=1, max=2, message="是否同库长度必须介于 1 和 2 之间")
	public String getSameDbFlg() {
		return sameDbFlg;
	}

	public void setSameDbFlg(String sameDbFlg) {
		this.sameDbFlg = sameDbFlg;
	}
	
	@Length(min=0, max=20, message="目的数据源长度必须介于 0 和 20 之间")
	public String getDstDataSrc() {
		return dstDataSrc;
	}

	public void setDstDataSrc(String dstDataSrc) {
		this.dstDataSrc = dstDataSrc;
	}
	
	@Length(min=0, max=120, message="目标表名长度必须介于 0 和 120 之间")
	public String getDstTabName() {
		return dstTabName;
	}

	public void setDstTabName(String dstTabName) {
		this.dstTabName = dstTabName;
	}
	
	@Length(min=0, max=360, message="目的表描述长度必须介于 0 和 360 之间")
	public String getDstTabDesc() {
		return dstTabDesc;
	}

	public void setDstTabDesc(String dstTabDesc) {
		this.dstTabDesc = dstTabDesc;
	}
	
	@Length(min=0, max=64, message="short_rmrk长度必须介于 0 和 64 之间")
	public String getShortRmrk() {
		return shortRmrk;
	}

	public void setShortRmrk(String shortRmrk) {
		this.shortRmrk = shortRmrk;
	}
	
	@Length(min=0, max=128, message="mid_rmrk长度必须介于 0 和 128 之间")
	public String getMidRmrk() {
		return midRmrk;
	}

	public void setMidRmrk(String midRmrk) {
		this.midRmrk = midRmrk;
	}
	
	@Length(min=0, max=256, message="long_rmrk长度必须介于 0 和 256 之间")
	public String getLongRmrk() {
		return longRmrk;
	}

	public void setLongRmrk(String longRmrk) {
		this.longRmrk = longRmrk;
	}
	
	@Length(min=0, max=16, message="dac长度必须介于 0 和 16 之间")
	public String getDac() {
		return dac;
	}

	public void setDac(String dac) {
		this.dac = dac;
	}
	
	@Length(min=1, max=10, message="步骤号长度必须介于 1 和 10 之间")
	public String getStepNo() {
		return stepNo;
	}

	public void setStepNo(String stepNo) {
		this.stepNo = stepNo;
	}
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("CtrlTParaDataSynConfigDO [ ");
		sb.append("srcTp="+srcTp+" , ");
		sb.append("uptTp="+uptTp+" , ");
		sb.append("srcTabName="+srcTabName+" , ");
		sb.append("srcDataSrc="+srcDataSrc+" , ");
		sb.append("srcTabDesc="+srcTabDesc+" , ");
		sb.append("relatSys="+relatSys+" , ");
		sb.append("sameDbFlg="+sameDbFlg+" , ");
		sb.append("dstDataSrc="+dstDataSrc+" , ");
		sb.append("dstTabName="+dstTabName+" , ");
		sb.append("dstTabDesc="+dstTabDesc+" , ");
		sb.append("shortRmrk="+shortRmrk+" , ");
		sb.append("midRmrk="+midRmrk+" , ");
		sb.append("longRmrk="+longRmrk+" , ");
		sb.append("dac="+dac+" , ");
		sb.append("stepNo="+stepNo+" , ");
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
		list.add("stepNo");
		return list;
	}
}