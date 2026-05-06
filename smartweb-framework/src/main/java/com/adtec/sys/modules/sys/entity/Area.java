package com.adtec.sys.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.adtec.framework.common.util.ClassUtil;
import com.adtec.sys.common.persistence.BaseDO;

/**
 * 区域Entity
 *
 * @version 2013-05-15
 */
public class Area extends BaseDO {

    private static final long serialVersionUID = 1L;
    protected String parentIdList; // 所有父级编号
    protected String name;    // 名称
    protected Integer sort;        // 排序
    private String regionCode;    // 区域编码
    private String regionTp;    // 区域类型（1：国家；2：省份、直辖市；3：地市；4：区县）
    protected Area parent;	// 父级编号

    public Area() {
        super();
        this.sort = 30;
    }

    public Area(String id) {
        super(id);
    }

    @Length(min = 0, max = 100)
    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }


    @Length(min = 1, max = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Area getParent() {
        return parent;
    }

    public void setParent(Area parent) {
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Length(min = 1, max = 1)
    public String getRegionTp() {
        return regionTp;
    }

    public void setRegionTp(String regionTp) {
        this.regionTp = regionTp;
    }

    @Override
    public String toString() {
        return name;
    }
}