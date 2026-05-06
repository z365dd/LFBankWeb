/**
 * 系统名称: 中间业务云平台
 * 模块名称: 报文格式
 * 功能描述: 请求应用头
 * 类 名 称  : LocalHeadReqDTO.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: ruanyh@ <br>
 * 开发时间: 2019年3月25日 下午14:00:00<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.comp.tseq.pub.head;

import com.alibaba.fastjson.annotation.JSONField;

public class LocalHeadReqDTO {
	/*业务编号*/
	@JSONField(name="BUSI_NO")
	private String BUSI_NO;
	/*企业编号*/
	@JSONField(name="ENTR_NO")
	private String ENTR_NO;
	/*租户号*/
	@JSONField(name="RENT_NO")
	private String RENT_NO;
	/*渠道号*/
	@JSONField(name="CHNL_NO")
	private String CHNL_NO;
	/*关联系统编号*/
	@JSONField(name="SYS_NO")
	private String SYS_NO;
	/*法人编号*/
	@JSONField(name="LEGA_NO")
	private String LEGA_NO;
	/*内部对账分类编号*/
	@JSONField(name="CHK_NO")
	private String CHK_NO;
	/*渠道流水号*/
	@JSONField(name="CHNL_SEQ_NO")
	private String CHNL_SEQ_NO;
	
	
	/*修改新增*/
	/*租户号*/
	@JSONField(name="TNT_NO")
	private String TNT_NO;
	/*关联系统编号*/
	@JSONField(name="SYS")
	private String SYS;
	
	
	public String getTNT_NO() {
		return TNT_NO;
	}
	public void setTNT_NO(String tNT_NO) {
		TNT_NO = tNT_NO;
	}
	public String getSYS() {
		return SYS;
	}
	public void setSYS(String sYS) {
		SYS = sYS;
	}
	/**
	 * 
	 */
	public LocalHeadReqDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param bUSI_NO
	 * @param eNTR_NO
	 * @param rENT_NO
	 * @param cHNL_NO
	 * @param sYS_NO
	 * @param lEGA_NO
	 * @param cHK_NO
	 * @param cHNL_SEQ_NO
	 */
	public LocalHeadReqDTO(String bUSI_NO, String eNTR_NO, String rENT_NO, String cHNL_NO, String sYS_NO,
			String lEGA_NO, String cHK_NO, String cHNL_SEQ_NO) {
		super();
		BUSI_NO = bUSI_NO;
		ENTR_NO = eNTR_NO;
		RENT_NO = rENT_NO;
		CHNL_NO = cHNL_NO;
		SYS_NO = sYS_NO;
		LEGA_NO = lEGA_NO;
		CHK_NO = cHK_NO;
		CHNL_SEQ_NO = cHNL_SEQ_NO;
	}
	/**
	 * @return the bUSI_NO
	 */
	public String getBUSI_NO() {
		return BUSI_NO;
	}
	/**
	 * @param bUSI_NO the bUSI_NO to set
	 */
	public void setBUSI_NO(String bUSI_NO) {
		BUSI_NO = bUSI_NO;
	}
	/**
	 * @return the eNTR_NO
	 */
	public String getENTR_NO() {
		return ENTR_NO;
	}
	/**
	 * @param eNTR_NO the eNTR_NO to set
	 */
	public void setENTR_NO(String eNTR_NO) {
		ENTR_NO = eNTR_NO;
	}
	/**
	 * @return the rENT_NO
	 */
	public String getRENT_NO() {
		return RENT_NO;
	}
	/**
	 * @param rENT_NO the rENT_NO to set
	 */
	public void setRENT_NO(String rENT_NO) {
		RENT_NO = rENT_NO;
	}
	/**
	 * @return the cHNL_NO
	 */
	public String getCHNL_NO() {
		return CHNL_NO;
	}
	/**
	 * @param cHNL_NO the cHNL_NO to set
	 */
	public void setCHNL_NO(String cHNL_NO) {
		CHNL_NO = cHNL_NO;
	}
	/**
	 * @return the sYS_NO
	 */
	public String getSYS_NO() {
		return SYS_NO;
	}
	/**
	 * @param sYS_NO the sYS_NO to set
	 */
	public void setSYS_NO(String sYS_NO) {
		SYS_NO = sYS_NO;
	}
	/**
	 * @return the lEGA_NO
	 */
	public String getLEGA_NO() {
		return LEGA_NO;
	}
	/**
	 * @param lEGA_NO the lEGA_NO to set
	 */
	public void setLEGA_NO(String lEGA_NO) {
		LEGA_NO = lEGA_NO;
	}
	/**
	 * @return the cHK_NO
	 */
	public String getCHK_NO() {
		return CHK_NO;
	}
	/**
	 * @param cHK_NO the cHK_NO to set
	 */
	public void setCHK_NO(String cHK_NO) {
		CHK_NO = cHK_NO;
	}
	/**
	 * @return the cHNL_SEQ_NO
	 */
	public String getCHNL_SEQ_NO() {
		return CHNL_SEQ_NO;
	}
	/**
	 * @param cHNL_SEQ_NO the cHNL_SEQ_NO to set
	 */
	public void setCHNL_SEQ_NO(String cHNL_SEQ_NO) {
		CHNL_SEQ_NO = cHNL_SEQ_NO;
	}
	
	
}
