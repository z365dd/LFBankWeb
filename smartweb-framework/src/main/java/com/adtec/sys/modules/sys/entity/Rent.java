package com.adtec.sys.modules.sys.entity;

import com.adtec.framework.common.util.ClassUtil;
import com.adtec.sys.common.persistence.BaseDO;
import com.adtec.sys.common.utils.excel.annotation.ExcelField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;
import org.hibernate.validator.constraints.Length;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;
import java.util.Map;

/**
 * 租户Entity
 *
 * @version 2017-04-25
 */
public class Rent extends BaseDO {

    //租户状态0-启用，1-关闭
    public static final String RENT_STAT_OPEN = "0";
    public static final String RENT_STAT_CLOSE = "1";
    private static final long serialVersionUID = 1L;

    protected Map<String, String> sqlMap;
    protected String parentIdList; // 所有父级编号
    private String name;    // 租户名称
    private String engName;    // 英文名称
    private String url;// 默认寻址地址
    private String stat;// 租户状态(0-启用,1-关闭),默认0-启用
    private String oneExtraRmrk;    // 备用字段1
    private String twoExtraRmrk;    // 备用字段2
    private String oldName;    // 原租户名称
    private String oldEngName;    // 原英文名称
    private User user;        // 根据用户ID查询租户列表
    protected Rent parent;	// 父级编号
    private String bgImg;     // 背景图
    private String tntDesc;   // 租户描述

    public Rent() {
        super();
        this.stat = "0";
    }

    public Rent(String id) {
        super(id);
    }

    public Rent(User user) {
        this();
        this.user = user;
    }

    @JsonIgnore
    @XmlTransient
    public Map<String, String> getSqlMap() {
        if (sqlMap == null) {
            sqlMap = Maps.newHashMap();
        }
        return sqlMap;
    }

    public void setSqlMap(Map<String, String> sqlMap) {
        this.sqlMap = sqlMap;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Length(min = 1, max = 100, message = "英文名称长度必须介于 1 和100之间")
    @ExcelField(title = "英文名称", align = 1, sort = 20)
    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    @Length(min = 1, max = 100, message = "租户名称长度必须介于 1 和100之间")
    @ExcelField(title = "租户名称", align = 1, sort = 10)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOldEngName() {
        return oldEngName;
    }

    public void setOldEngName(String oldEngName) {
        this.oldEngName = oldEngName;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public Rent getParent() {
        return parent;
    }

    public void setParent(Rent parent) {
        this.parent = parent;
    }

    public String getParentId() {
        String id = null;
        if (parent != null){
            id = (String) ClassUtil.getFieldValue(parent, "id");
        }
        return com.adtec.framework.common.util.StringUtil.isNotBlank(id) ? id : "0";
    }

    @Length(min = 1, max = 2000)
    public String getParentIdList() {
        return parentIdList;
    }

    public void setParentIdList(String parentIdList) {
        this.parentIdList = parentIdList;
    }

    public String getOneExtraRmrk() {
        return oneExtraRmrk;
    }

    public void setOneExtraRmrk(String oneExtraRmrk) {
        this.oneExtraRmrk = oneExtraRmrk;
    }

    public String getTwoExtraRmrk() {
        return twoExtraRmrk;
    }

    public void setTwoExtraRmrk(String twoExtraRmrk) {
        this.twoExtraRmrk = twoExtraRmrk;
    }

    @Override
    @Length(min = 0, max = 255)
    @ExcelField(title = "备注(选填)", align = 1, sort = 70)
    public String getRmrk() {
        return rmrk;
    }

    @Override
    public void setRmrk(String rmrk) {
        this.rmrk = rmrk;
    }

    /**
     * @return the stat
     */
//    @ExcelField(title = "租户状态", align = 1, sort = 50)
    public String getStat() {
        return stat;
    }

    /**
     * @param stat the stat to set
     */
    public void setStat(String stat) {
        this.stat = stat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public String getBgImg() {
		return bgImg;
	}

	public void setBgImg(String bgImg) {
		this.bgImg = bgImg;
	}

	public String getTntDesc() {
		return tntDesc;
	}

	public void setTntDesc(String tntDesc) {
		this.tntDesc = tntDesc;
	}

	@Override
    public String toString() {
        return name;
    }

	@Override
	public List<String> getIgnoreFields() {
		// TODO Auto-generated method stub
		List<String> list = super.getIgnoreFields();
		list.add("sqlMap");
		list.add("oldName");
		list.add("oldEngName");
		list.add("user");
		list.add("parent");
		return list;
	}
    
    
}
