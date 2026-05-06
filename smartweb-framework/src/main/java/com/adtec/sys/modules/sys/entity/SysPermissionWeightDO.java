/**
* 系统名称: SmartWeb平台
* 模块名称: sys的实体类模块
* 功能描述: 权限维度数据定义
* 类 名 称  : SysPermissionWeightDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20190901<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.sys.modules.sys.entity;

import java.util.List;

import com.adtec.sys.common.persistence.BaseDO;

/**
 * 权限维度
 * @author 权限维度
 * @version 20190901
 */
public class SysPermissionWeightDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String wghtName;		// 权限维度
	private String switchFlg;		// 是否加入权限计算
	private String authLvlSwitchFlg;
	
	public SysPermissionWeightDO() {
		super();
	}

	public SysPermissionWeightDO(String id){
		super(id);
	}

	public String getWghtName() {
		return wghtName;
	}

	public void setWghtName(String wghtName) {
		this.wghtName = wghtName;
	}

	public String getSwitchFlg() {
		return switchFlg;
	}

	public void setSwitchFlg(String switchFlg) {
		this.switchFlg = switchFlg;
	}

	public String getAuthLvlSwitchFlg() {
		return authLvlSwitchFlg;
	}

	public void setAuthLvlSwitchFlg(String authLvlSwitchFlg) {
		this.authLvlSwitchFlg = authLvlSwitchFlg;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("SysPermissionWeightDO [ ");
		sb.append("id="+id+" , ");
		sb.append("weight="+wghtName+" , ");
		sb.append("val="+switchFlg+" , ");
		sb.append("crtr="+crtr+" , ");
		sb.append("crtTime="+crtTime+" , ");
		sb.append("uptr="+uptr+" , ");
		sb.append("uptTime="+uptTime+" , ");
		sb.append("rmrk="+rmrk+" , ");
		sb.append("delFlg="+delFlg+" , ");
		sb.append(" ] ");
		return sb.toString();
	}
	
	@Override
	public List<String> getIgnoreFields() {
		// TODO Auto-generated method stub
		List<String> list = super.getIgnoreFields();
		return list;
	}
}