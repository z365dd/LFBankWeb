/**
 * 系统名称: 缓存中心
 * 模块名称:存储规则信息表实体类
 * 类  名  称: RulesStgDO.java
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

public class ParaRulesStgDO extends BaseDO {
    private static final long serialVersionUID = -1L;
    /*所属缓存中心*/
    private String cacheCentrId;
    /*英文名称*/
    private String engName;
    /*中文名称*/
    private String chName;
    /*唯一主键组合*/
    private String uniqKey;
    /*所属租户*/
    private String useTntNo;
    /*可读等级*/
    private String readAuthLvl;
    /*同步标志*/
    private String syncFlg;
	/*存储规则类型*/
	private String storgRuleTp;

    private String tenantName;
    private String centerName;
    private String readListStr;
    private String writeListStr;
    private String tabName;
    private String sameDbFlg;
    private String srcDataSrc;
    private String busiNoFlg;
    
	public ParaRulesStgDO() {
		super();
	}

	public ParaRulesStgDO(String engName, String chName, String cacheCentrId, String useTntNo, String uniqKey, String readAuthLvl, String storgRuleTp, String syncFlg, String delFlg, String tabName, String sameDbFlg, String srcDataSrc, String busiNoFlg) {
		this.cacheCentrId = cacheCentrId;
		this.engName = engName;
		this.chName = chName;
		this.uniqKey = uniqKey;
		this.useTntNo = useTntNo;
		this.readAuthLvl = readAuthLvl;
		this.syncFlg = syncFlg;
		this.storgRuleTp = storgRuleTp;
		super.delFlg = delFlg;
		this.tabName = tabName;
		this.sameDbFlg = sameDbFlg;
		this.srcDataSrc = srcDataSrc;
		this.busiNoFlg = busiNoFlg;
	}


	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public String getSameDbFlg() {
		return sameDbFlg;
	}

	public void setSameDbFlg(String sameDbFlg) {
		this.sameDbFlg = sameDbFlg;
	}

	public String getSrcDataSrc() {
		return srcDataSrc;
	}

	public void setSrcDataSrc(String srcDataSrc) {
		this.srcDataSrc = srcDataSrc;
	}

	public String getBusiNoFlg() {
		return busiNoFlg;
	}

	public void setBusiNoFlg(String busiNoFlg) {
		this.busiNoFlg = busiNoFlg;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getStorgRuleTp() {
		return storgRuleTp;
	}

	public void setStorgRuleTp(String storgRuleTp) {
		this.storgRuleTp = storgRuleTp;
	}

	public ParaRulesStgDO(String id) {
		super(id);
	}

    public String getCacheCentrId() {
        return cacheCentrId;
    }

    public void setCacheCentrId(String cacheCentrId) {
        this.cacheCentrId = cacheCentrId;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getChName() {
        return chName;
    }

    public void setChName(String chName) {
        this.chName = chName;
    }

    public String getUniqKey() {
        return uniqKey;
    }

    public void setUniqKey(String uniqKey) {
        this.uniqKey = uniqKey;
    }

    public String getUseTntNo() {
        return useTntNo;
    }

    public void setUseTntNo(String useTntNo) {
        this.useTntNo = useTntNo;
    }

    public String getReadAuthLvl() {
        return readAuthLvl;
    }

    public void setReadAuthLvl(String readAuthLvl) {
        this.readAuthLvl = readAuthLvl;
    }

    public String getSyncFlg() {
        return syncFlg;
    }

    public void setSyncFlg(String syncFlg) {
        this.syncFlg = syncFlg;
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
	 * @return the centerName
	 */
	public String getCenterName() {
		return centerName;
	}
	/**
	 * @param centerName the centerName to set
	 */
	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}
	/**
	 * @return the readListStr
	 */
	public String getReadListStr() {
		return readListStr;
	}
	/**
	 * @param readListStr the readListStr to set
	 */
	public void setReadListStr(String readListStr) {
		this.readListStr = readListStr;
	}
	/**
	 * @return the writeListStr
	 */
	public String getWriteListStr() {
		return writeListStr;
	}
	/**
	 * @param writeListStr the writeListStr to set
	 */
	public void setWriteListStr(String writeListStr) {
		this.writeListStr = writeListStr;
	}
	@Override
	public List<String> getIgnoreFields() {
		List<String> list = super.getIgnoreFields();
		list.add("serialVersionUID");
		list.add("action");
		list.add("delFlag");
		list.add("remarks");
		list.add("tenantName");
		list.add("centerName");
		list.add("readListStr");
		list.add("writeListStr");
		list.add("colList");
		return list;
	}
}

