/**
 *
 */
package com.adtec.sys.modules.gen.entity;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.StringUtil;
import com.adtec.sys.common.persistence.BaseDO;
import com.adtec.sys.common.persistence.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.Length;

import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

/**
 * 业务表Entity
 *
 * @version 2013-10-15
 */
public class GenTable extends BaseDO {

    private static final long serialVersionUID = 1L;
    private String name;    // 名称
    private String tabDesc;        // 描述
    private String procClssName;        // 实体类名称
    private String parentTabName;        // 关联父表
    private String parentTabOutKey;        // 关联父表外键

    private List<GenTableColumn> columnList = Lists.newArrayList();    // 表列

    private String nameLike;    // 按名称模糊查询

    private List<String> pkList; // 当前表主键列表

    private GenTable parent;    // 父表对象
    private List<GenTable> childList = Lists.newArrayList();    // 子表列表

    /**
     * 当前实体分页对象
     */
    protected Page<GenTable> page;

    public GenTable() {
        super();
    }

    public GenTable(String id) {
        super(id);
    }

    public List<GenTable> getChildList() {
        return childList;
    }

    public void setChildList(List<GenTable> childList) {
        this.childList = childList;
    }

    public String getProcClssName() {
        return procClssName;
    }

    public void setProcClssName(String procClssName) {
        this.procClssName = procClssName;
    }

    public List<GenTableColumn> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<GenTableColumn> columnList) {
        this.columnList = columnList;
    }

    public String getTabDesc() {
        return tabDesc;
    }

    public void setTabDesc(String tabDesc) {
        this.tabDesc = tabDesc;
    }

    /**
     * 是否存在id列
     * @return
     */
    public Boolean getIdExists() {
        for (GenTableColumn c : columnList) {
            if ("id".equalsIgnoreCase(c.getName())) {
                return true;
            }
        }
        return false;
    }
    /**
     * 是否存在crtr列
     * @return
     */
    public Boolean getCrtrExists() {
        for (GenTableColumn c : columnList) {
            if ("crtr".equalsIgnoreCase(c.getName())) {
                return true;
            }
        }
        return false;
    }
    /**
     * 是否存在crt_time列
     * @return
     */
    public Boolean getCrtTimeExists() {
        for (GenTableColumn c : columnList) {
            if ("crt_time".equalsIgnoreCase(c.getName())) {
                return true;
            }
        }
        return false;
    }
    /**
     * 是否存在uptr列
     * @return
     */
    public Boolean getUptrExists() {
        for (GenTableColumn c : columnList) {
            if ("uptr".equalsIgnoreCase(c.getName())) {
                return true;
            }
        }
        return false;
    }
    /**
     * 是否存在upt_time列
     * @return
     */
    public Boolean getUptTimeExists() {
        for (GenTableColumn c : columnList) {
            if ("upt_time".equalsIgnoreCase(c.getName())) {
                return true;
            }
        }
        return false;
    }
    /**
     * 是否存在rmrk列
     * @return
     */
    public Boolean getRmrkExists() {
        for (GenTableColumn c : columnList) {
            if ("rmrk".equalsIgnoreCase(c.getName())) {
                return true;
            }
        }
        return false;
    }
    /**
     * 是否存在del_flg列
     * @return
     */
    public Boolean getDelFlgExists() {
        for (GenTableColumn c : columnList) {
            if ("del_flg".equalsIgnoreCase(c.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取导入依赖包字符串
     * @return
     */
    public List<String> getImportList() {
        List<String> importList = Lists.newArrayList(); // 引用列表
        for (GenTableColumn column : getColumnList()) {
            if (column.getIsNotBaseField() || ("1".equals(column.getQryFlg()) && "between".equals(column.getQryTp())
                    && ("crtTime".equals(column.getSimpleJavaField()) || "uptTime".equals(column.getSimpleJavaField())))) {
                // 导入类型依赖包， 如果类型中包含“.”，则需要导入引用。
                if (StringUtil.indexOf(column.getJavaTp(), ".") != -1 && !importList.contains(column.getJavaTp())) {
                    importList.add(column.getJavaTp());
                }
            }
            if (column.getIsNotBaseField()) {
                // 导入JSR303、Json等依赖包
                for (String ann : column.getAnnotationList()) {
                	/* 20200207 mod by chenyl for 判断截取后的字符串不能包含( */
                	String impStr = StringUtil.substringBeforeLast(ann, "(");
                    if (!DataUtil.isNullStr(impStr) && -1==impStr.indexOf("(") && !importList.contains(impStr)) {
                        importList.add(impStr);
                    }
                }
            }
        }
        // 如果有子表，则需要导入List相关引用
        if (getChildList() != null && getChildList().size() > 0) {
            if (!importList.contains("java.util.List")) {
                importList.add("java.util.List");
            }
            if (!importList.contains("com.google.common.collect.Lists")) {
                importList.add("com.google.common.collect.Lists");
            }
        }
        return importList;
    }

    @Length(min = 1, max = 200)
    public String getName() {
        return StringUtil.lowerCase(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取列名和说明
     * @return
     */
    public String getNameAndComments() {
        return getName() + (tabDesc == null ? "" : "  :  " + tabDesc);
    }

    public String getNameLike() {
        return nameLike;
    }

    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }

    public GenTable getParent() {
        return parent;
    }

    public void setParent(GenTable parent) {
        this.parent = parent;
    }

    /**
     * 是否存在父类
     * @return
     */
    public Boolean getParentExists() {
        return parent != null && StringUtil.isNotBlank(parentTabName) && StringUtil.isNotBlank(parentTabOutKey);
    }

    public String getParentTabName() {
        return StringUtil.lowerCase(parentTabName);
    }

    public void setParentTabName(String parentTabName) {
        this.parentTabName = parentTabName;
    }

    public String getParentTabOutKey() {
        return StringUtil.lowerCase(parentTabOutKey);
    }

    public void setParentTabOutKey(String parentTabOutKey) {
        this.parentTabOutKey = parentTabOutKey;
    }

    public List<String> getPkList() {
        return pkList;
    }

    public void setPkList(List<String> pkList) {
        this.pkList = pkList;
    }

    @JsonIgnore
    @XmlTransient
    public Page<GenTable> getPage() {
        if (page == null) {
            page = new Page<GenTable>();
        }
        return page;
    }

    public Page<GenTable> setPage(Page<GenTable> page) {
        this.page = page;
        return page;
    }

}


