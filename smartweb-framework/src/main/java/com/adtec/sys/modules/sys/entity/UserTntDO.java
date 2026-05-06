/**
* 系统名称: SmartWeb平台
* 模块名称: sys-modules.sys的实体类模块
* 功能描述: 用户租户数据定义
* 类 名 称  : UserTntDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20210714<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.sys.modules.sys.entity;

import org.hibernate.validator.constraints.Length;
import java.util.ArrayList;
import java.util.List;
import com.adtec.sys.common.persistence.BaseDO;

/**
 * 用户租户
 * @author zx
 * @version 20210714
 */
public class UserTntDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户ID
	private String tntId;		// 租户ID
	
	public UserTntDO() {
		super();
	}

	public UserTntDO(String id){
		super(id);
	}

	@Length(min=1, max=64, message="用户ID长度必须介于 1 和 64 之间")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=1, max=64, message="租户ID长度必须介于 1 和 64 之间")
	public String getTntId() {
		return tntId;
	}

	public void setTntId(String tntId) {
		this.tntId = tntId;
	}
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("UserTntDO [ ");
		sb.append("id="+id+" , ");
		sb.append("userId="+userId+" , ");
		sb.append("tntId="+tntId+" , ");
		sb.append("crtr="+crtr+" , ");
		sb.append("crtTime="+crtTime+" , ");
		sb.append("uptr="+uptr+" , ");
		sb.append("uptTime="+uptTime+" , ");
		sb.append("rmrk="+rmrk+" , ");
		sb.append(" ] ");
		return sb.toString();
	}
	
	@Override
	public List<String> getIgnoreFields() {
		List<String> list = super.getIgnoreFields();
		list.add("delFlg");
		return list;
	}
	
	@Override
	public List<String> getMatchFields() {
		List<String> list = new ArrayList<String>();
		list.add("id");
		return list;
	}
}