/**
 *
 */
package com.adtec.sys.modules.gen.entity;

import com.adtec.framework.common.util.StringUtil;
import com.adtec.sys.common.persistence.BaseDO;
import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.Length;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

/**
 * 生成方案Entity
 *
 * @version 2013-10-15
 */
@XmlRootElement(name = "template")
public class GenTemplate extends BaseDO {

    private static final long serialVersionUID = 1L;
    private String name;    // 名称
    private String category;        // 分类
    private String filePath;        // 生成文件路径
    private String fileName;        // 文件名
    private String content;        // 内容

    public GenTemplate() {
        super();
    }

    public GenTemplate(String id) {
        super(id);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @XmlTransient
    public List<String> getCategoryList() {
        if (category == null) {
            return Lists.newArrayList();
        } else {
            return Lists.newArrayList(StringUtil.split(category, ","));
        }
    }

    public void setCategoryList(List<String> categoryList) {
        if (categoryList == null) {
            this.category = "";
        } else {
            this.category = "," + StringUtil.join(categoryList, ",") + ",";
        }
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Length(min = 1, max = 200)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}


