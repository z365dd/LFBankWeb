/**
* 系统名称: SmartWeb平台
* 模块名称: store-env的实体类模块
* 功能描述: 环境信息维护数据定义
* 类 名 称  : EnvDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20201010<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.sys.modules.sys.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.adtec.sys.common.persistence.BaseDO;

/**
 * 环境信息维护
 * @author tangxch
 * @version 20201010
 */
public class StoreEnv extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String chName;		// 中文名称
	private String engName;		// 英文名称
	private String mngrTp;		// 管理员类型
	private String mngrId;		// 管理员ID
	private String apprTp;		// 审批人类型
	private String apprId;		// 审批人ID
	private String cahceEngName;//缓存中心英文名
	private String cahceChName; //缓存中心中文名
	
	private String appEngName;
	private String oldCahceEngName;
	
	public String getOldCahceEngName() {
		return oldCahceEngName;
	}

	public void setOldCahceEngName(String oldCahceEngName) {
		this.oldCahceEngName = oldCahceEngName;
	}

	public String getCahceEngName() {
		return cahceEngName;
	}

	public void setCahceEngName(String cahceEngName) {
		this.cahceEngName = cahceEngName;
	}

	public String getCahceChName() {
		return cahceChName;
	}

	public void setCahceChName(String cahceChName) {
		this.cahceChName = cahceChName;
	}

	public String getAppEngName() {
		return appEngName;
	}

	public void setAppEngName(String appEngName) {
		this.appEngName = appEngName;
	}

	public StoreEnv() {
		super();
	}

	public StoreEnv(String id){
		super(id);
	}

	@Length(min=1, max=150, message="中文名称长度必须介于 1 和 150 之间")
	public String getChName() {
		return chName;
	}

	public void setChName(String chName) {
		this.chName = chName;
	}
	
	@Length(min=1, max=255, message="英文名称长度必须介于 1 和 255 之间")
	public String getEngName() {
		return engName;
	}

	public void setEngName(String engName) {
		this.engName = engName;
	}
	
	@Length(min=1, max=6, message="管理员类型长度必须介于 1 和 6 之间")
	public String getMngrTp() {
		return mngrTp;
	}

	public void setMngrTp(String mngrTp) {
		this.mngrTp = mngrTp;
	}
	
	@Length(min=1, max=64, message="管理员ID长度必须介于 1 和 64 之间")
	public String getMngrId() {
		return mngrId;
	}

	public void setMngrId(String mngrId) {
		this.mngrId = mngrId;
	}
	
	@Length(min=0, max=10, message="审批人类型长度必须介于 0 和 10 之间")
	public String getApprTp() {
		return apprTp;
	}

	public void setApprTp(String apprTp) {
		this.apprTp = apprTp;
	}
	
	@Length(min=0, max=64, message="审批人ID长度必须介于 0 和 64 之间")
	public String getApprId() {
		return apprId;
	}

	public void setApprId(String apprId) {
		this.apprId = apprId;
	}
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("EnvDO [ ");
		sb.append("chName="+chName+" , ");
		sb.append("engName="+engName+" , ");
		sb.append("mngrTp="+mngrTp+" , ");
		sb.append("mngrId="+mngrId+" , ");
		sb.append("apprTp="+apprTp+" , ");
		sb.append("apprId="+apprId+" , ");
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
		list.add("id");
		list.add("appEngName");
		list.add("oldCahceEngName");
		return list;
	}
	
	@Override
	public List<String> getMatchFields() {
		List<String> list = new ArrayList<String>();
		list.add("engName");
		return list;
	}
}