/**
* 系统名称: SmartWeb平台
* 模块名称: comp.fsvr.tec的实体类模块
* 功能描述: 组件文件传输端口数据定义
* 类 名 称  : TfsvrSvrPortParaDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200622<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.comp.fsvr.tec.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.adtec.sys.common.persistence.BaseDO;

/**
 * 组件文件传输端口
 * @author zhengjt
 * @version 20200622
 */
public class TfsvrSvrPortParaDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String compNo;		// 组件号
	private String compName;		// 组件名称
	private String port;		// 端口
	private String shortRmrk;		// 短备注
	private String midRmrk;		// 中备注
	private String longRmrk;		// 长备注
	private String dac;		// dac
	
	//网络依赖区域链表
	List<TfsvrSvrDeponNetParaDO> list;
	
	
	public List<TfsvrSvrDeponNetParaDO> getList() {
		return list;
	}

	public void setList(List<TfsvrSvrDeponNetParaDO> list) {
		this.list = list;
	}

	public TfsvrSvrPortParaDO() {
		super();
	}

	public TfsvrSvrPortParaDO(String id){
		super(id);
	}

	@Length(min=1, max=6, message="组件号长度必须介于 1 和 6 之间")
	public String getCompNo() {
		return compNo;
	}

	public void setCompNo(String compNo) {
		this.compNo = compNo;
	}
	
	@Length(min=1, max=120, message="组件名称长度必须介于 1 和 120 之间")
	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}
	
	@Length(min=1, max=10, message="端口长度必须介于 1 和 10 之间")
	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
	@Length(min=0, max=64, message="短备注长度必须介于 0 和 64 之间")
	public String getShortRmrk() {
		return shortRmrk;
	}

	public void setShortRmrk(String shortRmrk) {
		this.shortRmrk = shortRmrk;
	}
	
	@Length(min=0, max=128, message="中备注长度必须介于 0 和 128 之间")
	public String getMidRmrk() {
		return midRmrk;
	}

	public void setMidRmrk(String midRmrk) {
		this.midRmrk = midRmrk;
	}
	
	@Length(min=0, max=256, message="长备注长度必须介于 0 和 256 之间")
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
		sb.append("TfsvrSvrPortParaDO [ ");
		sb.append("compNo="+compNo+" , ");
		sb.append("compName="+compName+" , ");
		sb.append("port="+port+" , ");
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
		list.add("list");
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
		list.add("compNo");
		return list;
	}
}