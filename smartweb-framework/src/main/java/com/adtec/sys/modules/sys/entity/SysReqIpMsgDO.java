/**
* 系统名称: SmartWeb平台
* 模块名称: sys-modules的实体类模块
* 功能描述: 请求IP信息表数据定义
* 类 名 称  : SysReqIpMsgDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20211023<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.sys.modules.sys.entity;

import com.adtec.sys.common.persistence.BaseDO;

import java.util.ArrayList;
import java.util.List;

/**
 * 请求IP信息表
 * @author zengxj
 * @version 20211023
 */
public class SysReqIpMsgDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String termIp;		// 请求IP
	private String countryName;		// 国家名称
	private String countryCode;		// 国家编码
	private String regionCode;		// 省份编码
	private String regionName;		// 省份名称
	private String city;		// 城市名称
	private String zipCode;		// 邮政编码
	private double lat;		// 纬度
	private double lon;		// 经度
	private String timezone;		// 时区
	private String isp;		// Internet服务提供商
	private String org;		// 所属组织
	private String addrMsg;		// 地址信息
	private String status;
	private String mobile;		// 是否手机
	private String proxy;		// 是否代理
	private String chkFlg;		// 是否查询使用者
	private String usrName;		// 使用者
	private String usrTp;		// 使用者类型
	private String orderNo;		// 使用者类型

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTermIp() {
		return termIp;
	}

	public void setTermIp(String termIp) {
		this.termIp = termIp;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getIsp() {
		return isp;
	}

	public void setIsp(String isp) {
		this.isp = isp;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getAddrMsg() {
		return addrMsg;
	}

	public void setAddrMsg(String addrMsg) {
		this.addrMsg = addrMsg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProxy() {
		return proxy;
	}

	public void setProxy(String proxy) {
		this.proxy = proxy;
	}

	public String getChkFlg() {
		return chkFlg;
	}

	public void setChkFlg(String chkFlg) {
		this.chkFlg = chkFlg;
	}

	public String getUsrName() {
		return usrName;
	}

	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}

	public String getUsrTp() {
		return usrTp;
	}

	public void setUsrTp(String usrTp) {
		this.usrTp = usrTp;
	}

	@Override
	public String toString() {
		return "SysReqIpMsgDO{" +
				"termIp='" + termIp + '\'' +
				", countryName='" + countryName + '\'' +
				", countryCode='" + countryCode + '\'' +
				", regionCode='" + regionCode + '\'' +
				", regionName='" + regionName + '\'' +
				", city='" + city + '\'' +
				", zipCode='" + zipCode + '\'' +
				", lat=" + lat +
				", lon=" + lon +
				", timezone='" + timezone + '\'' +
				", isp='" + isp + '\'' +
				", org='" + org + '\'' +
				", addrMsg='" + addrMsg + '\'' +
				", status='" + status + '\'' +
				", mobile='" + mobile + '\'' +
				", proxy='" + proxy + '\'' +
				", chkFlg='" + chkFlg + '\'' +
				", usrName='" + usrName + '\'' +
				", usrTp='" + usrTp + '\'' +
				", orderNo='" + orderNo + '\'' +
				'}';
	}

	@Override
	public List<String> getIgnoreFields() {
		// TODO Auto-generated method stub
		List<String> list = super.getIgnoreFields();
		list.add("delFlg");
		list.add("crtr");
		list.add("uptr");
		return list;
	}
	
	@Override
	public List<String> getMatchFields() {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		list.add("id");
		return list;
	}
}