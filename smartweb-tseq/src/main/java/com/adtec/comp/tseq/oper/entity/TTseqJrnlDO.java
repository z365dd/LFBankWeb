/**
* 系统名称: SmartWeb平台
* 模块名称: comp.tseq.oper的实体类模块
* 功能描述: 流水表数据定义
* 类 名 称  : TTseqJrnlDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200424<br>
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
 * 流水表
 * @author zhengjt
 * @version 20200424
 */
public class TTseqJrnlDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String tranDate;		// 交易日期
	private String tranTime;		// 交易时间
	private String seqCrtId;		// 流水生成器ID
	private String nodeNo;		// 节点号
	private String digitFlg;		// 数字标志
	private String seqNodeNo;		// 流水节点号
	private String curSeq;		// 当前流水
	private String respSeq;		// 响应流水
	private String shortRmrk;		// 短备注
	private String midRmrk;		// 中备注
	private String longRmrk;		// 长备注
	private String dac;		// DAC
	
	public TTseqJrnlDO() {
		super();
	}

	public TTseqJrnlDO(String id){
		super(id);
	}

	@Length(min=1, max=8, message="交易日期长度必须介于 1 和 8 之间")
	public String getTranDate() {
		return tranDate;
	}

	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}
	
	@Length(min=1, max=20, message="交易时间长度必须介于 1 和 20 之间")
	public String getTranTime() {
		return tranTime;
	}

	public void setTranTime(String tranTime) {
		this.tranTime = tranTime;
	}
	
	@Length(min=1, max=60, message="流水生成器ID长度必须介于 1 和 60 之间")
	public String getSeqCrtId() {
		return seqCrtId;
	}

	public void setSeqCrtId(String seqCrtId) {
		this.seqCrtId = seqCrtId;
	}
	
	@Length(min=1, max=64, message="节点号长度必须介于 1 和 64 之间")
	public String getNodeNo() {
		return nodeNo;
	}

	public void setNodeNo(String nodeNo) {
		this.nodeNo = nodeNo;
	}
	
	@Length(min=0, max=2, message="数字标志长度必须介于 0 和 2 之间")
	public String getDigitFlg() {
		return digitFlg;
	}

	public void setDigitFlg(String digitFlg) {
		this.digitFlg = digitFlg;
	}
	
	@Length(min=1, max=64, message="流水节点号长度必须介于 1 和 64 之间")
	public String getSeqNodeNo() {
		return seqNodeNo;
	}

	public void setSeqNodeNo(String seqNodeNo) {
		this.seqNodeNo = seqNodeNo;
	}
	
	@Length(min=1, max=64, message="当前流水长度必须介于 1 和 64 之间")
	public String getCurSeq() {
		return curSeq;
	}

	public void setCurSeq(String curSeq) {
		this.curSeq = curSeq;
	}
	
	@Length(min=1, max=64, message="响应流水长度必须介于 1 和 64 之间")
	public String getRespSeq() {
		return respSeq;
	}

	public void setRespSeq(String respSeq) {
		this.respSeq = respSeq;
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
		sb.append("TTseqJrnlDO [ ");
		sb.append("tranDate="+tranDate+" , ");
		sb.append("tranTime="+tranTime+" , ");
		sb.append("seqCrtId="+seqCrtId+" , ");
		sb.append("nodeNo="+nodeNo+" , ");
		sb.append("digitFlg="+digitFlg+" , ");
		sb.append("seqNodeNo="+seqNodeNo+" , ");
		sb.append("curSeq="+curSeq+" , ");
		sb.append("respSeq="+respSeq+" , ");
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