/**
 * 系统名称: 缓存中心
 * 模块名称: 存储规则权限表实体类
 * 类  名  称: RulesStgAuthDO.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl
 * 开发时间: 2018-11-13 11:47:37
 * 系统版本: V1.0.0
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================ *
 * ========     ======  ============================================
 */

package com.adtec.para.rules.entity;



import com.adtec.sys.common.persistence.BaseDO;

import java.util.List;

public class ParaRulesStgAuthDO extends BaseDO {
    private static final long serialVersionUID = -1L;
    /*所属存储规则ID*/
    private String ruleId;
    /*权限类型*/
    private String ruleAuthTp;
    /*授权租户*/
    private String useTntNo;
    /*授权参与者*/
    private String membNo;

    private String tenantName;
    private String partName;

    public ParaRulesStgAuthDO() {
		super();
	}

    public ParaRulesStgAuthDO(String ruleId, String ruleAuthTp, String tntNo, String membNo) {
        this.ruleId = ruleId;
        this.ruleAuthTp = ruleAuthTp;
        this.useTntNo = tntNo;
        this.membNo = membNo;
    }

    public ParaRulesStgAuthDO(String ruleId, String ruleAuthTp, String tntNo) {
        this.ruleId = ruleId;
        this.ruleAuthTp = ruleAuthTp;
        this.useTntNo = tntNo;
    }

    public ParaRulesStgAuthDO(String id) {
		super(id);
	}

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleAuthTp() {
        return ruleAuthTp;
    }

    public void setRuleAuthTp(String ruleAuthTp) {
        this.ruleAuthTp = ruleAuthTp;
    }

    public String getUseTntNo() {
        return useTntNo;
    }

    public void setUseTntNo(String useTntNo) {
        this.useTntNo = useTntNo;
    }

    public String getMembNo() {
        return membNo;
    }

    public void setMembNo(String membNo) {
        this.membNo = membNo;
    }

    /**
	 * @return the tenantName
	 */
	public String getTenantName() {
		return tenantName;
	}
	/**
	 * @param tenantName the tenantName to set
	 */
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	/**
	 * @return the partName
	 */
	public String getPartName() {
		return partName;
	}
	/**
	 * @param partName the partName to set
	 */
	public void setPartName(String partName) {
		this.partName = partName;
	}

	@Override
	public List<String> getIgnoreFields() {
		List<String> list = super.getIgnoreFields();
		list.add("serialVersionUID");
		list.add("action");
		list.add("delFlag");
		list.add("delFlg");
		list.add("remarks");
		list.add("tenantName");
		list.add("partName");
		return list;
	}
}

