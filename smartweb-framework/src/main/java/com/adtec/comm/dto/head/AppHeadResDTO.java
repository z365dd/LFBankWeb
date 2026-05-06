/**
 * 系统名称: 中间业务云平台
 * 模块名称: 报文格式
 * 功能描述: 响应应用头
 * 类 名 称  : MBC_REQ_APP_HEAD.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2017年4月17日 下午6:24:15<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.comm.dto.head;

/**
 * @author chenyl
 *
 */
public class AppHeadResDTO {
	/*服务处理返回流水号*/
	private String RES_SEQ_NO;
	/*本次返回查询结果记录总数*/
	private String RES_REC_NUM;
	/*符合查询条件的记录总数*/
	private long TOT_NUM;
	/*是否结束(记录状态)*/
	private String END_STAT;
	
	/*修改新增*/
	/*服务处理返回流水号*/
	private String RESP_SEQ;
	/*服务处理返回日期*/
	private String RESP_DATE;
	/*本次返回查询结果记录总数*/
	private long RESP_REC_NUM;
	/*符合查询条件的记录总数*/
	/*private long TOT_NUM;*/
	/*是否结束(记录状态)*/
	private String END_FLG;
	
	
	public String getRESP_SEQ() {
		return RESP_SEQ;
	}
	public void setRESP_SEQ(String rESP_SEQ) {
		RESP_SEQ = rESP_SEQ;
	}
	public long getRESP_REC_NUM() {
		return RESP_REC_NUM;
	}
	public void setRESP_REC_NUM(long rESP_REC_NUM) {
		RESP_REC_NUM = rESP_REC_NUM;
	}
	public String getEND_FLG() {
		return END_FLG;
	}
	public void setEND_FLG(String eND_FLG) {
		END_FLG = eND_FLG;
	}
	
	/**
	 * 
	 */
	public AppHeadResDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param rES_SEQ_NO
	 * @param rES_REC_NUM
	 * @param tOT_NUM
	 * @param eND_STAT
	 */
	public AppHeadResDTO(String rES_SEQ_NO, String rES_REC_NUM, long tOT_NUM, String eND_STAT) {
		super();
		RES_SEQ_NO = rES_SEQ_NO;
		RES_REC_NUM = rES_REC_NUM;
		TOT_NUM = tOT_NUM;
		END_STAT = eND_STAT;
	}
	/**
	 * @return the rES_SEQ_NO
	 */
	public String getRES_SEQ_NO() {
		return RES_SEQ_NO;
	}
	/**
	 * @param rES_SEQ_NO the rES_SEQ_NO to set
	 */
	public void setRES_SEQ_NO(String rES_SEQ_NO) {
		RES_SEQ_NO = rES_SEQ_NO;
	}
	/**
	 * @return the rES_REC_NUM
	 */
	public String getRES_REC_NUM() {
		return RES_REC_NUM;
	}
	/**
	 * @param rES_REC_NUM the rES_REC_NUM to set
	 */
	public void setRES_REC_NUM(String rES_REC_NUM) {
		RES_REC_NUM = rES_REC_NUM;
	}
	/**
	 * @return the tOT_NUM
	 */
	public long getTOT_NUM() {
		return TOT_NUM;
	}
	/**
	 * @param tOT_NUM the tOT_NUM to set
	 */
	public void setTOT_NUM(long tOT_NUM) {
		TOT_NUM = tOT_NUM;
	}
	/**
	 * @return the eND_STAT
	 */
	public String getEND_STAT() {
		return END_STAT;
	}
	/**
	 * @param eND_STAT the eND_STAT to set
	 */
	public void setEND_STAT(String eND_STAT) {
		END_STAT = eND_STAT;
	}
	public String getRESP_DATE() {
		return RESP_DATE;
	}
	public void setRESP_DATE(String rESP_DATE) {
		RESP_DATE = rESP_DATE;
	}
	
}
