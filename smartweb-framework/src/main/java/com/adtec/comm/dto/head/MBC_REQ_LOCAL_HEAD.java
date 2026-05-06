/**
 * 系统名称: 中间业务云平台
 * 模块名称: 报文格式
 * 功能描述: 请求扩展头
 * 类 名 称  : MBC_REQ_LOCAL_HEAD.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2017年4月17日 下午6:26:19<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.comm.dto.head;

/**
 * @author yyjgs03
 *
 */
public class MBC_REQ_LOCAL_HEAD {
	/*业务编号*/
	private String BUSI_NO;
	/*企业编号*/
	private String ENTR_NO;
	/*租户号*/
	private String RENT_NO;
	/*渠道号*/
	private String CHNL_NO;
	/*关联系统编号*/
	private String SYS_NO;
	/*法人编号*/
	private String LEGA_NO;
	/*内部对账分类编号*/
	private String CHK_NO;
	/*渠道流水号*/
	private String CHNL_SEQ_NO;
	/**
	 * 
	 */
	public MBC_REQ_LOCAL_HEAD() {
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
	public MBC_REQ_LOCAL_HEAD(String bUSI_NO, String eNTR_NO, String rENT_NO, String cHNL_NO, String sYS_NO,
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
