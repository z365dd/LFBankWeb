package com.adtec.sys.modules.sys.entity;

import com.adtec.framework.common.util.ClassUtil;
import com.adtec.sys.common.persistence.BaseDO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;
import java.util.Map;

/**
 * 机构Entity
 *
 * @version 2013-05-15
 */
public class Office extends BaseDO {

    private static final long serialVersionUID = 1L;
    /**
     * 自定义SQL（SQL标识，SQL内容）
     */
    protected Map<String, String> sqlMap;
    protected String parentIdList; // 所有父级编号
    protected String name;    // 名称
    protected Integer sort;        // 排序
    private Area area;        // 归属区域
    private String brchCode;    // 机构编码
    private String brchTp;    // 机构类型
    private String ctctAddr; // 联系地址
    private String postEcd; // 邮政编码
    private String rspbtPer;    // 负责人
    private String telNo;    // 电话
    private String faxNo;    // 传真
    private String email;    // 邮箱
    private String validSwitchFlg;//是否可用
    private User oneRspbtPer;//主负责人
    private User twoRspbtPer;//副负责人
    private List<Role> roleList;
    protected Office parent;	// 父级编号

    public Office() {
        super();
        this.brchTp = "1";
        this.sort = 30;
    }

    public Office(String id) {
        super(id);
    }

    @Length(min = 0, max = 255)
    public String getCtctAddr() {
        return ctctAddr;
    }

    public void setCtctAddr(String ctctAddr) {
        this.ctctAddr = ctctAddr;
    }

    @NotNull
    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    @Length(min = 0, max = 100)
    public String getBrchCode() {
        return brchCode;
    }

    public void setBrchCode(String brchCode) {
        this.brchCode = brchCode;
    }

    public User getTwoRspbtPer() {
        return twoRspbtPer;
    }

    public void setTwoRspbtPer(User twoRspbtPer) {
        this.twoRspbtPer = twoRspbtPer;
    }

    @Length(min = 0, max = 200)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Length(min = 0, max = 200)
    public String getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    @Length(min = 0, max = 100)
    public String getRspbtPer() {
        return rspbtPer;
    }

    public void setRspbtPer(String rspbtPer) {
        this.rspbtPer = rspbtPer;
    }

    @Length(min = 1, max = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Office getParent() {
        return parent;
    }

    public void setParent(Office parent) {
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

    @Length(min = 0, max = 200)
    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public User getOneRspbtPer() {
        return oneRspbtPer;
    }

    public void setOneRspbtPer(User oneRspbtPer) {
        this.oneRspbtPer = oneRspbtPer;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

    @Length(min = 1, max = 3)
    public String getBrchTp() {
        return brchTp;
    }

    public void setBrchTp(String brchTp) {
        this.brchTp = brchTp;
    }

    public String getValidSwitchFlg() {
        return validSwitchFlg;
    }

    public void setValidSwitchFlg(String validSwitchFlg) {
        this.validSwitchFlg = validSwitchFlg;
    }

    @Length(min = 0, max = 100)
    public String getPostEcd() {
        return postEcd;
    }

    public void setPostEcd(String postEcd) {
        this.postEcd = postEcd;
    }

    @Override
    public String toString() {
        return name;
    }
}