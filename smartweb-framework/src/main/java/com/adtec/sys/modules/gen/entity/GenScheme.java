/**
 *
 */
package com.adtec.sys.modules.gen.entity;

import com.adtec.sys.common.persistence.BaseDO;
import com.adtec.sys.common.persistence.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

/**
 * 生成方案Entity
 *
 * @version 2013-10-15
 */
public class GenScheme extends BaseDO {

    private static final long serialVersionUID = 1L;
    private String name;    // 名称
    private String clssCode;        // 分类
    private String packName;        // 生成包路径
    private String modlName;        // 生成模块名
    private String subModlName;        // 生成子模块名
    private String funcName;        // 生成功能名
    private String funcNameAbbr;        // 生成功能名（简写）
    private String funcCrtr;        // 生成功能作者
    private GenTable genTable;        // 业务表名
    private String tabId;	// 查询表id

    private String flag;    // 0：保存方案； 1：保存方案并生成代码

    private Boolean replaceFile;    // 是否替换现有文件    0：不替换；1：替换文件

    /**
     * 当前实体分页对象
     */
    protected Page<GenScheme> page;

    public GenScheme() {
        super();
    }

    public GenScheme(String id) {
        super(id);
    }

    public String getClssCode() {
        return clssCode;
    }

    public void setClssCode(String clssCode) {
        this.clssCode = clssCode;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public String getModlName() {
        return modlName;
    }

    public void setModlName(String modlName) {
        this.modlName = modlName;
    }

    public String getSubModlName() {
        return subModlName;
    }

    public void setSubModlName(String subModlName) {
        this.subModlName = subModlName;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public String getFuncNameAbbr() {
        return funcNameAbbr;
    }

    public void setFuncNameAbbr(String funcNameAbbr) {
        this.funcNameAbbr = funcNameAbbr;
    }

    public String getFuncCrtr() {
        return funcCrtr;
    }

    public void setFuncCrtr(String funcCrtr) {
        this.funcCrtr = funcCrtr;
    }

    public String getTabId() {
        return tabId;
    }

    public void setTabId(String tabId) {
        this.tabId = tabId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public GenTable getGenTable() {
        return genTable;
    }

    public void setGenTable(GenTable genTable) {
        this.genTable = genTable;
    }

    @Length(min = 1, max = 200)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getReplaceFile() {
        return replaceFile;
    }

    public void setReplaceFile(Boolean replaceFile) {
        this.replaceFile = replaceFile;
    }

    @JsonIgnore
    @XmlTransient
    public Page<GenScheme> getPage() {
        if (page == null) {
            page = new Page<GenScheme>();
        }
        return page;
    }

    public Page<GenScheme> setPage(Page<GenScheme> page) {
        this.page = page;
        return page;
    }

	/* (non-Javadoc)
	 * @see com.adtec.sys.common.persistence.BaseDO#getIgnoreFields()
	 */
	@Override
	public List<String> getIgnoreFields() {
		// TODO Auto-generated method stub
		List<String> list = super.getIgnoreFields();
		list.add("page");
		list.add("genTable");
		return list;
	}

}


