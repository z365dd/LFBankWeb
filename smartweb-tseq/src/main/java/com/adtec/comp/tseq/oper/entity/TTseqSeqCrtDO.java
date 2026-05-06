/**
* 系统名称: SmartWeb平台
* 模块名称: comp.tseq.oper的实体类模块
* 功能描述: 流水号生成器数据定义
* 类 名 称  : TTseqSeqCrtDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200422<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.comp.tseq.oper.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import com.adtec.sys.common.persistence.BaseDO;

/**
 * 流水号生成器
 * @author zhengjt
 * @version 20200422
 */
public class TTseqSeqCrtDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String outSys;		// 外部系统
	private String outSubSys;		// 外部子系统
	private String seqCrtName;		// 流水号生成器名称
	private String minVal;		// 最小值
	private String maxVal;		// 最大值
	private Long seqLen;		// 流水号步长
	private Long respSeqLen;		// 响应流水号长度
	private long resetCyc;		// 重置周期
	private String digitFlg;		// 是否只支持数字
	private String seqCrtId;		// 流水号生成器ID
	private String resetDate;		// 重置日期
	private String regDt;		// 登记日期时间
	private String modDt;		// 修改日期时间
	private String modTp;		// 修改类型
	private String resetVal;		// 重置值
	private String efftFlg;		// 是否使用表达式
	private String starExpr;		// 表达式
	private String reptInsptStat;		// 是否重置
	private Long useNum;		// 使用数量
	private String shortRmrk;		// 短备注
	private String midRmrk;		// 中备注
	private String longRmrk;		// 长备注
	private String dac;		// DAC
	
	public TTseqSeqCrtDO() {
		super();
	}

	public TTseqSeqCrtDO(String id){
		super(id);
	}
	
	public String getReptInsptStat() {
		return reptInsptStat;
	}

	public void setReptInsptStat(String reptInsptStat) {
		this.reptInsptStat = reptInsptStat;
	}

	@Length(min=0, max=10, message="外部系统长度必须介于 0 和 10 之间")
	public String getOutSys() {
		return outSys;
	}

	public void setOutSys(String outSys) {
		this.outSys = outSys;
	}
	
	@Length(min=0, max=10, message="外部子系统长度必须介于 0 和 10 之间")
	public String getOutSubSys() {
		return outSubSys;
	}

	public void setOutSubSys(String outSubSys) {
		this.outSubSys = outSubSys;
	}
	
	@Length(min=1, max=120, message="流水号生成器名称长度必须介于 1 和 120 之间")
	public String getSeqCrtName() {
		return seqCrtName;
	}

	public void setSeqCrtName(String seqCrtName) {
		this.seqCrtName = seqCrtName;
	}
	
	@Length(min=1, max=256, message="最小值长度必须介于 1 和 256 之间")
	public String getMinVal() {
		return minVal;
	}

	public void setMinVal(String minVal) {
		this.minVal = minVal;
	}
	
	@Length(min=1, max=256, message="最大值长度必须介于 1 和 256 之间")
	public String getMaxVal() {
		return maxVal;
	}

	public void setMaxVal(String maxVal) {
		this.maxVal = maxVal;
	}
	
	@NotNull(message="流水号步长不能为空")
	public Long getSeqLen() {
		return seqLen;
	}

	public void setSeqLen(Long seqLen) {
		this.seqLen = seqLen;
	}
	
	@NotNull(message="响应流水号长度不能为空")
	public Long getRespSeqLen() {
		return respSeqLen;
	}

	public void setRespSeqLen(Long respSeqLen) {
		this.respSeqLen = respSeqLen;
	}
	
	public long getResetCyc() {
		return resetCyc;
	}

	public void setResetCyc(long resetCyc) {
		this.resetCyc = resetCyc;
	}
	
	@Length(min=0, max=2, message="是否只支持数字长度必须介于 0 和 2 之间")
	public String getDigitFlg() {
		return digitFlg;
	}

	public void setDigitFlg(String digitFlg) {
		this.digitFlg = digitFlg;
	}
	
	@Length(min=1, max=60, message="流水号生成器ID长度必须介于 1 和 60 之间")
	public String getSeqCrtId() {
		return seqCrtId;
	}

	public void setSeqCrtId(String seqCrtId) {
		this.seqCrtId = seqCrtId;
	}
	
	@Length(min=0, max=8, message="重置日期长度必须介于 0 和 8 之间")
	public String getResetDate() {
		return resetDate;
	}

	public void setResetDate(String resetDate) {
		this.resetDate = resetDate;
	}
	
	@Length(min=0, max=17, message="登记日期时间长度必须介于 0 和 17 之间")
	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	
	@Length(min=0, max=17, message="修改日期时间长度必须介于 0 和 17 之间")
	public String getModDt() {
		return modDt;
	}

	public void setModDt(String modDt) {
		this.modDt = modDt;
	}
	
	@Length(min=0, max=2, message="修改类型长度必须介于 0 和 2 之间")
	public String getModTp() {
		return modTp;
	}

	public void setModTp(String modTp) {
		this.modTp = modTp;
	}
	
	@Length(min=0, max=256, message="重置值长度必须介于 0 和 256 之间")
	public String getResetVal() {
		return resetVal;
	}

	public void setResetVal(String resetVal) {
		this.resetVal = resetVal;
	}
	
	@Length(min=0, max=2, message="是否使用表达式长度必须介于 0 和 2 之间")
	public String getEfftFlg() {
		return efftFlg;
	}

	public void setEfftFlg(String efftFlg) {
		this.efftFlg = efftFlg;
	}
	
	@Length(min=0, max=512, message="表达式长度必须介于 0 和 512 之间")
	public String getStarExpr() {
		return starExpr;
	}

	public void setStarExpr(String starExpr) {
		this.starExpr = starExpr;
	}
	
	public Long getUseNum() {
		return useNum;
	}

	public void setUseNum(Long useNum) {
		this.useNum = useNum;
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
		sb.append("TTseqSeqCrtDO [ ");
		sb.append("outSys="+outSys+" , ");
		sb.append("outSubSys="+outSubSys+" , ");
		sb.append("seqCrtName="+seqCrtName+" , ");
		sb.append("minVal="+minVal+" , ");
		sb.append("maxVal="+maxVal+" , ");
		sb.append("seqLen="+seqLen+" , ");
		sb.append("respSeqLen="+respSeqLen+" , ");
		sb.append("resetCyc="+resetCyc+" , ");
		sb.append("digitFlg="+digitFlg+" , ");
		sb.append("seqCrtId="+seqCrtId+" , ");
		sb.append("resetDate="+resetDate+" , ");
		sb.append("regDt="+regDt+" , ");
		sb.append("modDt="+modDt+" , ");
		sb.append("modTp="+modTp+" , ");
		sb.append("resetVal="+resetVal+" , ");
		sb.append("efftFlg="+efftFlg+" , ");
		sb.append("starExpr="+starExpr+" , ");
		sb.append("useNum="+useNum+" , ");
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