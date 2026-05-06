/**
* 系统名称: SmartWeb平台
* 模块名称: comp.tseq.oper的实体类模块
* 功能描述: 流水号生成器加载状态数据定义
* 类 名 称  : TTseqSeqCrtStatDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200423<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.comp.tseq.oper.entity;

import org.hibernate.validator.constraints.Length;
import java.util.ArrayList;
import java.util.List;
import com.adtec.sys.common.persistence.BaseDO;

/**
 * 流水号生成器加载状态
 * @author zhengjt
 * @version 20200423
 */
public class TTseqSeqCrtStatDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String nodeNo;		// 节点号
	private String seqCrtId;		// 流水生成标识号
	private String modStat;		// 修改状态
	private String resetStat;		// 重置状态
	private String shortRmrk;		// 短备注
	private String midRmrk;		// 中备注
	private String longRmrk;		// 长备注
	private String dac;		// DAC
	
	public TTseqSeqCrtStatDO() {
		super();
	}

	public TTseqSeqCrtStatDO(String id){
		super(id);
	}

	@Length(min=1, max=64, message="节点号长度必须介于 1 和 64 之间")
	public String getNodeNo() {
		return nodeNo;
	}

	public void setNodeNo(String nodeNo) {
		this.nodeNo = nodeNo;
	}
	
	@Length(min=1, max=60, message="流水生成标识号长度必须介于 1 和 60 之间")
	public String getSeqCrtId() {
		return seqCrtId;
	}

	public void setSeqCrtId(String seqCrtId) {
		this.seqCrtId = seqCrtId;
	}
	
	@Length(min=0, max=3, message="修改状态长度必须介于 0 和 3 之间")
	public String getModStat() {
		return modStat;
	}

	public void setModStat(String modStat) {
		this.modStat = modStat;
	}
	
	@Length(min=0, max=3, message="重置状态长度必须介于 0 和 3 之间")
	public String getResetStat() {
		return resetStat;
	}

	public void setResetStat(String resetStat) {
		this.resetStat = resetStat;
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
		sb.append("TTseqSeqCrtStatDO [ ");
		sb.append("nodeNo="+nodeNo+" , ");
		sb.append("seqCrtId="+seqCrtId+" , ");
		sb.append("modStat="+modStat+" , ");
		sb.append("resetStat="+resetStat+" , ");
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
		list.add("seqCrtId");
		return list;
	}
}