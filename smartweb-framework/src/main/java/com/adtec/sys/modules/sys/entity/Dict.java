package com.adtec.sys.modules.sys.entity;

import com.adtec.sys.common.persistence.BaseDO;
import com.adtec.sys.common.persistence.Page;
import com.adtec.sys.common.utils.excel.annotation.ExcelField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 字典Entity
 *
 * @version 2013-05-15
 */
public class Dict extends BaseDO {

    private static final long serialVersionUID = 1L;
    private String value;    // 数据值
    private String label;    // 标签名
    private String dictTp;    // 类型
    private String dictInfo;// 描述
    private Integer sort;    // 排序
    private String parentId;//父Id

    /**
     * 当前实体分页对象
     */
    protected Page<Dict> page;

    public Dict() {
        super();
    }

    public Dict(String id) {
        super(id);
    }

    public Dict(String value, String label) {
        this.value = value;
        this.label = label;
    }

    @XmlAttribute
    @Length(min = 0, max = 100)
    @ExcelField(title = "描述", align = 2, sort = 45)
    public String getDictInfo() {
        return dictInfo;
    }

    public void setDictInfo(String dictInfo) {
        this.dictInfo = dictInfo;
    }

    @XmlAttribute
    @Length(min = 1, max = 120)
    @ExcelField(title = "标签", align = 2, sort = 35)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @NotNull
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Length(min = 1, max = 100)
    @ExcelField(title = "类型", align = 2, sort = 40)
    public String getDictTp() {
        return dictTp;
    }

    public void setDictTp(String dictTp) {
        this.dictTp = dictTp;
    }

    @XmlAttribute
    @Length(min = 1, max = 120)
    @ExcelField(title = "键值", align = 2, sort = 30)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @JsonIgnore
    @XmlTransient
    public Page<Dict> getPage() {
        if (page == null) {
            page = new Page<Dict>();
        }
        return page;
    }

    public Page<Dict> setPage(Page<Dict> page) {
        this.page = page;
        return page;
    }

    @Override
    public String toString() {
        return label;
    }
}