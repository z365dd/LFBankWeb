/**
* 系统名称: SmartWeb平台
* 模块名称: comp.ctrl.oper的实体类模块
* 功能描述: 数据源数据定义
* 类 名 称  : CtrlTParaDataSourceDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200512<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.comp.ctrl.oper.entity;

import org.hibernate.validator.constraints.Length;
import java.util.ArrayList;
import java.util.List;
import com.adtec.sys.common.persistence.BaseDO;

/**
 * 数据源
 * @author zhengjt
 * @version 20200512
 */
public class CtrlTParaDataSourceDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String dataSrc;		// 数据源
	private String dataSrcName;		// 数据源名称
	private String dbTp;		// 数据库类型
	private String dataSrcAddr;		// 数据源地址
	private String dbUserName;		// 数据库用户
	private String dbPwd;		// 数据库密码
	private String shortRmrk;		// short_rmrk
	private String midRmrk;		// mid_rmrk
	private String longRmrk;		// long_rmrk
	private String dac;		// dac
	
	public CtrlTParaDataSourceDO() {
		super();
	}

	public CtrlTParaDataSourceDO(String id){
		super(id);
	}

	@Length(min=1, max=20, message="数据源长度必须介于 1 和 20 之间")
	public String getDataSrc() {
		return dataSrc;
	}

	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}
	
	@Length(min=1, max=256, message="数据源名称长度必须介于 1 和 256 之间")
	public String getDataSrcName() {
		return dataSrcName;
	}

	public void setDataSrcName(String dataSrcName) {
		this.dataSrcName = dataSrcName;
	}
	
	@Length(min=1, max=64, message="数据库类型长度必须介于 1 和 64 之间")
	public String getDbTp() {
		return dbTp;
	}

	public void setDbTp(String dbTp) {
		this.dbTp = dbTp;
	}
	
	@Length(min=1, max=240, message="数据源地址长度必须介于 1 和 240 之间")
	public String getDataSrcAddr() {
		return dataSrcAddr;
	}

	public void setDataSrcAddr(String dataSrcAddr) {
		this.dataSrcAddr = dataSrcAddr;
	}
	
	@Length(min=1, max=256, message="数据库用户长度必须介于 1 和 256 之间")
	public String getDbUserName() {
		return dbUserName;
	}

	public void setDbUserName(String dbUserName) {
		this.dbUserName = dbUserName;
	}
	
	@Length(min=1, max=80, message="数据库密码长度必须介于 1 和 80 之间")
	public String getDbPwd() {
		return dbPwd;
	}

	public void setDbPwd(String dbPwd) {
		this.dbPwd = dbPwd;
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
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("CtrlTParaDataSourceDO [ ");
		sb.append("dataSrc="+dataSrc+" , ");
		sb.append("dataSrcName="+dataSrcName+" , ");
		sb.append("dbTp="+dbTp+" , ");
		sb.append("dataSrcAddr="+dataSrcAddr+" , ");
		sb.append("dbUserName="+dbUserName+" , ");
		sb.append("dbPwd="+dbPwd+" , ");
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
		list.add("dataSrc");
		return list;
	}
}