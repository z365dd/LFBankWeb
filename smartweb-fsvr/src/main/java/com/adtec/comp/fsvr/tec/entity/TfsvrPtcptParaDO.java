/**
* 系统名称: SmartWeb平台
* 模块名称: comp.fsvr.tec的实体类模块
* 功能描述: 调用方信息数据定义
* 类 名 称  : TfsvrPtcptParaDO.java
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

import org.hibernate.validator.constraints.Length;
import java.util.ArrayList;
import java.util.List;
import com.adtec.sys.common.persistence.BaseDO;

/**
 * 调用方信息
 * @author zhengjt
 * @version 20200622
 */
public class TfsvrPtcptParaDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String callerId;		// 调用方参与者
	private String callerDesc;		// 调用方描述
	private String callMeth;		// 调用方式
	private String port;		// 端口
	private String userNo;		// 用户名
	private String pwd;		// 密码
	private String stat;		// 状态
	private String encrpFlg;		// 是否加密
	private Long subctrctSendFileSize;		// 分包发送文件大小
	private Long subctrctRecvFileSize;		// 分包接收文件大小
	private String attestFlg;		// 是否加签
	private String ip;		// IP
	private String reduceFlg;		// 是否压缩
	private String resumeFlg;		// 是否断点续传
	private String speedlimFlg;		// 是否限速
	private Long sndSpeedlimSize;		// 发送限速大小
	private Long recvSpeedlimSize;		// 接收限速大小
	private String shortRmrk;		// short_rmrk
	private String midRmrk;		// mid_rmrk
	private String longRmrk;		// long_rmrk
	private String dac;		// dac
	private String uploadFilePath;		// 上传路径
	private String downloadFilePath;		// 下载路径
	private String netRegion;		// 网络区域
	List<TfsvrSvrDeponNetParaDO> deponList;
	
	public List<TfsvrSvrDeponNetParaDO> getDeponList() {
		return deponList;
	}

	public void setDeponList(List<TfsvrSvrDeponNetParaDO> deponList) {
		this.deponList = deponList;
	}

	public String getNetRegion() {
		return netRegion;
	}

	public void setNetRegion(String netRegion) {
		this.netRegion = netRegion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TfsvrPtcptParaDO() {
		super();
	}

	public TfsvrPtcptParaDO(String id){
		super(id);
	}

	@Length(min=1, max=64, message="调用方参与者长度必须介于 1 和 64 之间")
	public String getCallerId() {
		return callerId;
	}

	public void setCallerId(String callerId) {
		this.callerId = callerId;
	}
	
	@Length(min=0, max=1024, message="调用方描述长度必须介于 0 和 1024 之间")
	public String getCallerDesc() {
		return callerDesc;
	}

	public void setCallerDesc(String callerDesc) {
		this.callerDesc = callerDesc;
	}
	
	@Length(min=0, max=5, message="调用方式长度必须介于 0 和 5 之间")
	public String getCallMeth() {
		return callMeth;
	}

	public void setCallMeth(String callMeth) {
		this.callMeth = callMeth;
	}
	
	@Length(min=0, max=10, message="端口长度必须介于 0 和 10 之间")
	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
	@Length(min=0, max=30, message="用户名长度必须介于 0 和 30 之间")
	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	
	@Length(min=0, max=80, message="密码长度必须介于 0 和 80 之间")
	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	@Length(min=0, max=3, message="状态长度必须介于 0 和 3 之间")
	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}
	
	@Length(min=0, max=20, message="是否加密长度必须介于 0 和 20 之间")
	public String getEncrpFlg() {
		return encrpFlg;
	}

	public void setEncrpFlg(String encrpFlg) {
		this.encrpFlg = encrpFlg;
	}
	
	public Long getSubctrctSendFileSize() {
		return subctrctSendFileSize;
	}

	public void setSubctrctSendFileSize(Long subctrctSendFileSize) {
		this.subctrctSendFileSize = subctrctSendFileSize;
	}
	
	public Long getSubctrctRecvFileSize() {
		return subctrctRecvFileSize;
	}

	public void setSubctrctRecvFileSize(Long subctrctRecvFileSize) {
		this.subctrctRecvFileSize = subctrctRecvFileSize;
	}
	
	@Length(min=0, max=20, message="是否加签长度必须介于 0 和 20 之间")
	public String getAttestFlg() {
		return attestFlg;
	}

	public void setAttestFlg(String attestFlg) {
		this.attestFlg = attestFlg;
	}
	
	@Length(min=0, max=30, message="IP长度必须介于 0 和 30 之间")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@Length(min=0, max=20, message="是否压缩长度必须介于 0 和 20 之间")
	public String getReduceFlg() {
		return reduceFlg;
	}

	public void setReduceFlg(String reduceFlg) {
		this.reduceFlg = reduceFlg;
	}
	
	@Length(min=0, max=20, message="是否断点续传长度必须介于 0 和 20 之间")
	public String getResumeFlg() {
		return resumeFlg;
	}

	public void setResumeFlg(String resumeFlg) {
		this.resumeFlg = resumeFlg;
	}
	
	@Length(min=0, max=2, message="是否限速长度必须介于 0 和 2 之间")
	public String getSpeedlimFlg() {
		return speedlimFlg;
	}

	public void setSpeedlimFlg(String speedlimFlg) {
		this.speedlimFlg = speedlimFlg;
	}
	
	public Long getSndSpeedlimSize() {
		return sndSpeedlimSize;
	}

	public void setSndSpeedlimSize(Long sndSpeedlimSize) {
		this.sndSpeedlimSize = sndSpeedlimSize;
	}
	
	public Long getRecvSpeedlimSize() {
		return recvSpeedlimSize;
	}

	public void setRecvSpeedlimSize(Long recvSpeedlimSize) {
		this.recvSpeedlimSize = recvSpeedlimSize;
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
	
	@Length(min=0, max=256, message="上传路径长度必须介于 0 和 256 之间")
	public String getUploadFilePath() {
		return uploadFilePath;
	}

	public void setUploadFilePath(String uploadFilePath) {
		this.uploadFilePath = uploadFilePath;
	}
	
	@Length(min=0, max=256, message="下载路径长度必须介于 0 和 256 之间")
	public String getDownloadFilePath() {
		return downloadFilePath;
	}

	public void setDownloadFilePath(String downloadFilePath) {
		this.downloadFilePath = downloadFilePath;
	}
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("TfsvrPtcptParaDO [ ");
		sb.append("callerId="+callerId+" , ");
		sb.append("callerDesc="+callerDesc+" , ");
		sb.append("callMeth="+callMeth+" , ");
		sb.append("port="+port+" , ");
		sb.append("userNo="+userNo+" , ");
		sb.append("pwd="+pwd+" , ");
		sb.append("stat="+stat+" , ");
		sb.append("encrpFlg="+encrpFlg+" , ");
		sb.append("subctrctSendFileSize="+subctrctSendFileSize+" , ");
		sb.append("subctrctRecvFileSize="+subctrctRecvFileSize+" , ");
		sb.append("attestFlg="+attestFlg+" , ");
		sb.append("ip="+ip+" , ");
		sb.append("reduceFlg="+reduceFlg+" , ");
		sb.append("resumeFlg="+resumeFlg+" , ");
		sb.append("speedlimFlg="+speedlimFlg+" , ");
		sb.append("sndSpeedlimSize="+sndSpeedlimSize+" , ");
		sb.append("recvSpeedlimSize="+recvSpeedlimSize+" , ");
		sb.append("shortRmrk="+shortRmrk+" , ");
		sb.append("midRmrk="+midRmrk+" , ");
		sb.append("longRmrk="+longRmrk+" , ");
		sb.append("dac="+dac+" , ");
		sb.append("uploadFilePath="+uploadFilePath+" , ");
		sb.append("downloadFilePath="+downloadFilePath+" , ");
		sb.append(" ] ");
		return sb.toString();
	}
	
	@Override
	public List<String> getIgnoreFields() {
		// TODO Auto-generated method stub
		List<String> list = super.getIgnoreFields();
		list.add("deponList");
		list.add("netRegion");
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
		list.add("callerId");
		return list;
	}
}