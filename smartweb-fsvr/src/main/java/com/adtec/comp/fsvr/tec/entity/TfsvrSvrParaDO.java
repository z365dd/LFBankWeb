/**
* 系统名称: SmartWeb平台
* 模块名称: comp.fsvr.tec的实体类模块
* 功能描述: 外部文件服务器数据定义
* 类 名 称  : TfsvrSvrParaDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200618<br>
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
 * 外部文件服务器
 * @author zhengjt
 * @version 20200618
 */
public class TfsvrSvrParaDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String fileSvrId;		// 文件服务器标识号
	private String svrDesc;		// 服务器描述
	private String fileSvrTp;		// 文件服务器类型
	private String commProtGrpTp;		// 通信协议组类型
	private String openSvcFlg;		// 开通服务标志
	private String ip;		// IP
	private String port;		// 端口
	private String userNo;		// 用户号
	private String pwd;		// 密码
	private String stat;		// 状态
	private String downloadFilePath;		// 下载文件路径
	private String uploadFilePath;		// 上传文件路径
	private String shortRmrk;		// 短备注
	private String midRmrk;		// 中备注
	private String longRmrk;		// 长备注
	private String dac;		// dac
	private String contFlg;		// 继续标志
	private String membId;		// 参与者ID
	

	public TfsvrSvrParaDO() {
		super();
	}

	public TfsvrSvrParaDO(String id){
		super(id);
	}

	@Length(min=1, max=64, message="文件服务器标识号长度必须介于 1 和 64 之间")
	public String getFileSvrId() {
		return fileSvrId;
	}

	public void setFileSvrId(String fileSvrId) {
		this.fileSvrId = fileSvrId;
	}
	
	@Length(min=1, max=512, message="服务器描述长度必须介于 1 和 512 之间")
	public String getSvrDesc() {
		return svrDesc;
	}

	public void setSvrDesc(String svrDesc) {
		this.svrDesc = svrDesc;
	}
	
	@Length(min=1, max=2, message="文件服务器类型长度必须介于 1 和 2 之间")
	public String getFileSvrTp() {
		return fileSvrTp;
	}

	public void setFileSvrTp(String fileSvrTp) {
		this.fileSvrTp = fileSvrTp;
	}
	
	@Length(min=1, max=10, message="通信协议组类型长度必须介于 1 和 10 之间")
	public String getCommProtGrpTp() {
		return commProtGrpTp;
	}

	public void setCommProtGrpTp(String commProtGrpTp) {
		this.commProtGrpTp = commProtGrpTp;
	}
	
	@Length(min=1, max=10, message="开通服务标志长度必须介于 1 和 10 之间")
	public String getOpenSvcFlg() {
		return openSvcFlg;
	}

	public void setOpenSvcFlg(String openSvcFlg) {
		this.openSvcFlg = openSvcFlg;
	}
	
	@Length(min=1, max=30, message="IP长度必须介于 1 和 30 之间")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@Length(min=1, max=10, message="端口长度必须介于 1 和 10 之间")
	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
	@Length(min=1, max=60, message="用户号长度必须介于 1 和 60 之间")
	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	
	@Length(min=1, max=80, message="密码长度必须介于 1 和 80 之间")
	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	@Length(min=1, max=3, message="状态长度必须介于 1 和 3 之间")
	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}
	
	@Length(min=0, max=128, message="下载文件路径长度必须介于 0 和 128 之间")
	public String getDownloadFilePath() {
		return downloadFilePath;
	}

	public void setDownloadFilePath(String downloadFilePath) {
		this.downloadFilePath = downloadFilePath;
	}
	
	@Length(min=0, max=128, message="上传文件路径长度必须介于 0 和 128 之间")
	public String getUploadFilePath() {
		return uploadFilePath;
	}

	public void setUploadFilePath(String uploadFilePath) {
		this.uploadFilePath = uploadFilePath;
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
	
	@Length(min=0, max=2, message="继续标志长度必须介于 0 和 2 之间")
	public String getContFlg() {
		return contFlg;
	}

	public void setContFlg(String contFlg) {
		this.contFlg = contFlg;
	}
	
	@Length(min=0, max=64, message="参与者ID长度必须介于 0 和 64 之间")
	public String getMembId() {
		return membId;
	}

	public void setMembId(String membId) {
		this.membId = membId;
	}
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("TfsvrSvrParaDO [ ");
		sb.append("fileSvrId="+fileSvrId+" , ");
		sb.append("svrDesc="+svrDesc+" , ");
		sb.append("fileSvrTp="+fileSvrTp+" , ");
		sb.append("commProtGrpTp="+commProtGrpTp+" , ");
		sb.append("openSvcFlg="+openSvcFlg+" , ");
		sb.append("ip="+ip+" , ");
		sb.append("port="+port+" , ");
		sb.append("userNo="+userNo+" , ");
		sb.append("pwd="+pwd+" , ");
		sb.append("stat="+stat+" , ");
		sb.append("downloadFilePath="+downloadFilePath+" , ");
		sb.append("uploadFilePath="+uploadFilePath+" , ");
		sb.append("shortRmrk="+shortRmrk+" , ");
		sb.append("midRmrk="+midRmrk+" , ");
		sb.append("longRmrk="+longRmrk+" , ");
		sb.append("dac="+dac+" , ");
		sb.append("contFlg="+contFlg+" , ");
		sb.append("membId="+membId+" , ");
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
		return list;
	}
}