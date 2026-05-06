/**
* 系统名称: SmartWeb平台
* 模块名称: comp.ctrl.oper的实体类模块
* 功能描述: 平台日切数据定义
* 类 名 称  : FCtrlTParaDayDO.java
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
 * 平台日切
 * @author zhengjt
 * @version 20200512
 */
public class FCtrlTParaDayDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String platNo;		// 平台编号
	private String platName;		// 平台名称
	private String platStat;		// 平台状态
	private String lastDate;		// 最后日期
	private String platDate;		// 平台日期
	private String rsltMsg;		// 结果信息
	private String modTime;		// 修改时间
	private Long switchCyc;		// 切换周期
	private String lastUptTime;		// last_upt_time
	private String shortRmrk;		// short_rmrk
	private String midRmrk;		// mid_rmrk
	private String longRmrk;		// long_rmrk
	private String dac;		// dac
	
	public FCtrlTParaDayDO() {
		super();
	}

	public FCtrlTParaDayDO(String id){
		super(id);
	}

	@Length(min=1, max=20, message="平台编号长度必须介于 1 和 20 之间")
	public String getPlatNo() {
		return platNo;
	}

	public void setPlatNo(String platNo) {
		this.platNo = platNo;
	}
	
	@Length(min=0, max=120, message="平台名称长度必须介于 0 和 120 之间")
	public String getPlatName() {
		return platName;
	}

	public void setPlatName(String platName) {
		this.platName = platName;
	}
	
	@Length(min=1, max=3, message="平台状态长度必须介于 1 和 3 之间")
	public String getPlatStat() {
		return platStat;
	}

	public void setPlatStat(String platStat) {
		this.platStat = platStat;
	}
	
	@Length(min=0, max=8, message="最后日期长度必须介于 0 和 8 之间")
	public String getLastDate() {
		return lastDate;
	}

	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}
	
	@Length(min=1, max=8, message="平台日期长度必须介于 1 和 8 之间")
	public String getPlatDate() {
		return platDate;
	}

	public void setPlatDate(String platDate) {
		this.platDate = platDate;
	}
	
	@Length(min=0, max=180, message="结果信息长度必须介于 0 和 180 之间")
	public String getRsltMsg() {
		return rsltMsg;
	}

	public void setRsltMsg(String rsltMsg) {
		this.rsltMsg = rsltMsg;
	}
	
	@Length(min=0, max=20, message="修改时间长度必须介于 0 和 20 之间")
	public String getModTime() {
		return modTime;
	}

	public void setModTime(String modTime) {
		this.modTime = modTime;
	}
	
	public Long getSwitchCyc() {
		return switchCyc;
	}

	public void setSwitchCyc(Long switchCyc) {
		this.switchCyc = switchCyc;
	}
	
	@Length(min=0, max=20, message="last_upt_time长度必须介于 0 和 20 之间")
	public String getLastUptTime() {
		return lastUptTime;
	}

	public void setLastUptTime(String lastUptTime) {
		this.lastUptTime = lastUptTime;
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
		sb.append("FCtrlTParaDayDO [ ");
		sb.append("platNo="+platNo+" , ");
		sb.append("platName="+platName+" , ");
		sb.append("platStat="+platStat+" , ");
		sb.append("lastDate="+lastDate+" , ");
		sb.append("platDate="+platDate+" , ");
		sb.append("rsltMsg="+rsltMsg+" , ");
		sb.append("modTime="+modTime+" , ");
		sb.append("switchCyc="+switchCyc+" , ");
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
		list.add("platNo");
		return list;
	}
}