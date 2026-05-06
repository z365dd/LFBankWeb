/**
* 系统名称: SmartWeb平台
* 模块名称: comp.fsvr.tec的实体类模块
* 功能描述: 网络区域关系参数表数据定义
* 类 名 称  : TfsvrSvrDeponNetParaDO.java
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
 * 网络区域关系参数表
 * @author zhengjt
 * @version 20200616
 */
public class TfsvrSvrDeponNetParaDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String netRegion;		// 网络区域
	private String fileResTp;		// 资源类型
	private String fileSvrId;		// 文件服务器标识号
	private String compNo;		// 组件号
	private String shortRmrk;		// 短备注
	private String midRmrk;		// 中备注
	private String longRmrk;		// 长备注
	private String dac;		// DAC
	
	public TfsvrSvrDeponNetParaDO() {
		super();
	}

	public TfsvrSvrDeponNetParaDO(String id){
		super(id);
	}

	@Length(min=1, max=32, message="网络区域长度必须介于 1 和 32 之间")
	public String getNetRegion() {
		return netRegion;
	}

	public void setNetRegion(String netRegion) {
		this.netRegion = netRegion;
	}
	
	@Length(min=1, max=5, message="资源类型长度必须介于 1 和 5 之间")
	public String getFileResTp() {
		return fileResTp;
	}

	public void setFileResTp(String fileResTp) {
		this.fileResTp = fileResTp;
	}
	
	@Length(min=0, max=64, message="文件服务器标识号长度必须介于 0 和 64 之间")
	public String getFileSvrId() {
		return fileSvrId;
	}

	public void setFileSvrId(String fileSvrId) {
		this.fileSvrId = fileSvrId;
	}
	
	@Length(min=0, max=6, message="组件号长度必须介于 0 和 6 之间")
	public String getCompNo() {
		return compNo;
	}

	public void setCompNo(String compNo) {
		this.compNo = compNo;
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
	
	@Length(min=0, max=16, message="DAC长度必须介于 0 和 16 之间")
	public String getDac() {
		return dac;
	}

	public void setDac(String dac) {
		this.dac = dac;
	}
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("TfsvrSvrDeponNetParaDO [ ");
		sb.append("netRegion="+netRegion+" , ");
		sb.append("fileResTp="+fileResTp+" , ");
		sb.append("fileSvrId="+fileSvrId+" , ");
		sb.append("compNo="+compNo+" , ");
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
		list.add("netRegion");
		list.add("fileResTp");
		return list;
	}
}