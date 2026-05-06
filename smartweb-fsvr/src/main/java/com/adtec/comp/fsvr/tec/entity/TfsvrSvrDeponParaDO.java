/**
* 系统名称: SmartWeb平台
* 模块名称: comp.fsvr.tec的实体类模块
* 功能描述: 网络依赖关系参数表数据定义
* 类 名 称  : TfsvrSvrDeponParaDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200616<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.comp.fsvr.tec.entity;

import org.hibernate.validator.constraints.Length;
import java.util.ArrayList;
import java.util.List;
import com.adtec.sys.common.persistence.BaseDO;

/**
 * 网络依赖关系参数表
 * @author zhengjt
 * @version 20200616
 */
public class TfsvrSvrDeponParaDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String fileSvrId;		// file_svr_id
	private String deponFileSvrId;		// depon_file_svr_id
	private String shortRmrk;		// short_rmrk
	private String midRmrk;		// mid_rmrk
	private String longRmrk;		// long_rmrk
	private String dac;		// dac
	
	public TfsvrSvrDeponParaDO() {
		super();
	}

	public TfsvrSvrDeponParaDO(String id){
		super(id);
	}

	@Length(min=1, max=64, message="file_svr_id长度必须介于 1 和 64 之间")
	public String getFileSvrId() {
		return fileSvrId;
	}

	public void setFileSvrId(String fileSvrId) {
		this.fileSvrId = fileSvrId;
	}
	
	@Length(min=1, max=64, message="depon_file_svr_id长度必须介于 1 和 64 之间")
	public String getDeponFileSvrId() {
		return deponFileSvrId;
	}

	public void setDeponFileSvrId(String deponFileSvrId) {
		this.deponFileSvrId = deponFileSvrId;
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
		sb.append("TfsvrSvrDeponParaDO [ ");
		sb.append("fileSvrId="+fileSvrId+" , ");
		sb.append("deponFileSvrId="+deponFileSvrId+" , ");
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
		list.add("fileSvrId");
		list.add("deponFileSvrId");
		return list;
	}
}