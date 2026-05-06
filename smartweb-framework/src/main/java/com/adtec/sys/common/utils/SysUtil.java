/**
 * 系统名称: SmartWeb平台
 * 模块名称: 系统工具
 * 功能描述: 系统工具
 * 类 名 称  : SysUtil.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2017年4月17日 下午9:14:38<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.sys.common.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import com.adtec.framework.exception.BaseException;

/**
 * @author chenyl
 *
 */
public class SysUtil {
	/**
	 * 管理台模型编号:999000
	 */
	public static final String MODL_NO = "999000";
	/**
	 * 获取本机IP
	 * 
	 * 
	 */
	public static String getLocalIp() {
		String ip = null;
		try {
			Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = allNetInterfaces.nextElement();
				Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
				boolean isBreak = false;
				while (addresses.hasMoreElements()) {
					InetAddress inetAddress = addresses.nextElement();
					if ((inetAddress != null) && ((inetAddress instanceof Inet4Address))) {
						ip = inetAddress.getHostAddress();
						isBreak = true;
						break;
					}
				}
				if (isBreak)
					break;
			}
		} catch (SocketException ex) {
			ex.printStackTrace();
			throw new BaseException("20000000", ex, "获取IP失败"); // 通用系统错误
		}
		return ip;
	}
}
