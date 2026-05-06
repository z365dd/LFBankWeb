/**
* 系统名称: SmartWeb平台
* 模块名称: comp.ctrl.oper的实体类模块
* 功能描述: 数据同步任务数据定义
* 类 名 称  : CtrlTParaSyncTaskDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200513<br>
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
 * 数据同步任务
 * @author zhengjt
 * @version 20200513
 */
public class CtrlTParaSyncTaskDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String stepNo;		// 步骤号
	private String platSeq;		// 平台流水
	private String platDate;		// 同步日期
	private String stat;		// 处理状态
	private String regDt;		// 登记日期时间
	private String endDt;		// 结束日期
	private String rsltMsg;		// 处理结果
	private String shortRmrk;		// short_rmrk
	private String midRmrk;		// mid_rmrk
	private String longRmrk;		// long_rmrk
	private String dac;		// dac
	
	public CtrlTParaSyncTaskDO() {
		super();
	}

	public CtrlTParaSyncTaskDO(String id){
		super(id);
	}

	@Length(min=1, max=10, message="步骤号长度必须介于 1 和 10 之间")
	public String getStepNo() {
		return stepNo;
	}

	public void setStepNo(String stepNo) {
		this.stepNo = stepNo;
	}
	
	@Length(min=1, max=64, message="平台流水长度必须介于 1 和 64 之间")
	public String getPlatSeq() {
		return platSeq;
	}

	public void setPlatSeq(String platSeq) {
		this.platSeq = platSeq;
	}
	
	@Length(min=1, max=8, message="同步日期长度必须介于 1 和 8 之间")
	public String getPlatDate() {
		return platDate;
	}

	public void setPlatDate(String platDate) {
		this.platDate = platDate;
	}
	
	@Length(min=1, max=3, message="处理状态长度必须介于 1 和 3 之间")
	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}
	
	@Length(min=1, max=17, message="登记日期时间长度必须介于 1 和 17 之间")
	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	
	@Length(min=0, max=17, message="结束日期长度必须介于 0 和 17 之间")
	public String getEndDt() {
		return endDt;
	}

	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}
	
	@Length(min=0, max=360, message="处理结果长度必须介于 0 和 360 之间")
	public String getRsltMsg() {
		return rsltMsg;
	}

	public void setRsltMsg(String rsltMsg) {
		this.rsltMsg = rsltMsg;
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
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("CtrlTParaSyncTaskDO [ ");
		sb.append("stepNo="+stepNo+" , ");
		sb.append("platSeq="+platSeq+" , ");
		sb.append("platDate="+platDate+" , ");
		sb.append("stat="+stat+" , ");
		sb.append("regDt="+regDt+" , ");
		sb.append("endDt="+endDt+" , ");
		sb.append("rsltMsg="+rsltMsg+" , ");
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
		list.add("stepNo");
		list.add("platDate");
		return list;
	}
}