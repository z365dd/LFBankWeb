/**
* 系统名称: SmartWeb平台
* 模块名称: sys-modules的实体类模块
* 功能描述: 用户账号数据定义
* 类 名 称  : SysUserAcctDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20210415<br>
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
 * 用户账号
 * 
 * @author lijb
 * @version 20210415
 */
public class UserAcctDO extends BaseDO {

	private static final long serialVersionUID = 1L;
	private String loginName; // 登录账号
	private String pwd; // 登陆密码
	private String userId; // 用户ID
	private String userAcctTp; // 用户账号类型

	public UserAcctDO() {
		super();
	}

	public UserAcctDO(String id) {
		super(id);
	}

	public UserAcctDO(String userAcctTp, String userId) {
		this.userAcctTp = userAcctTp;
		this.userId = userId;
	}

	@Length(min = 1, max = 100, message = "登录账号长度必须介于 1 和 100 之间")
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Length(min = 1, max = 128, message = "登陆密码长度必须介于 1 和 128 之间")
	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Length(min = 1, max = 64, message = "用户ID长度必须介于 1 和 64 之间")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Length(min = 1, max = 2, message = "用户账号类型长度必须介于 1 和 10 之间")
	public String getUserAcctTp() {
		return userAcctTp;
	}

	public void setUserAcctTp(String userAcctTp) {
		this.userAcctTp = userAcctTp;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("SysUserAcctDO [ ");
		sb.append("id=" + id + " , ");
		sb.append("loginName=" + loginName + " , ");
		sb.append("pwd=" + pwd + " , ");
		sb.append("userId=" + userId + " , ");
		sb.append("userAcctTp=" + userAcctTp + " , ");
		sb.append("crtr=" + crtr + " , ");
		sb.append("crtTime=" + crtTime + " , ");
		sb.append("uptr=" + uptr + " , ");
		sb.append("uptTime=" + uptTime + " , ");
		sb.append("rmrk=" + rmrk + " , ");
		sb.append(" ] ");
		return sb.toString();
	}

	@Override
	public List<String> getIgnoreFields() {
		// TODO Auto-generated method stub
		List<String> list = super.getIgnoreFields();
		list.add("delFlg");
		return list;
	}

	@Override
	public List<String> getMatchFields() {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		list.add("id");
		return list;
	}

	public interface UserAcctType {
		String SVN = "svn";
	}
}