/**
 *
 */
package com.adtec.sys.modules.gen.entity;

import com.adtec.framework.common.util.StringUtil;
import com.adtec.sys.common.persistence.BaseDO;
import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 业务表字段Entity
 *
 * @version 2013-10-15
 */
public class GenTableColumn extends BaseDO {

    private static final long serialVersionUID = 1L;
    private GenTable genTable;    // 归属表
    private String name;        // 列名
    private String tabDesc;    // 描述
    private String dbFieldTp;    // JDBC类型
    private String javaTp;    // JAVA类型
    private String javaField;    // JAVA字段名
    private String mainKeyFlg;        // 是否主键（1：主键）
    private String nullFlg;        // 是否可为空（1：可为空；0：不为空）
    private String insertFlg;    // 是否为插入字段（1：插入字段）
    private String editFlg;        // 是否编辑字段（1：编辑字段）
    private String listFlg;        // 是否列表字段（1：列表字段）
    private String qryFlg;        // 是否查询字段（1：查询字段）
    private String qryTp;    // 查询方式（等于、不等于、大于、小于、范围、左LIKE、右LIKE、左右LIKE）
    private String dpyTp;    // 字段生成方案（文本框、文本域、下拉框、复选框、单选框、字典选择、人员选择、部门选择、区域选择）
    private String dictTp;    // 字典类型
    private Integer sort;        // 排序（升序）

    /**
     * 选项：1-是
     */
    public static final String OPTION_Y = "1";
    /**
     * 选项：0-否
     */
    public static final String OPTION_N = "0";

    public GenTableColumn() {
        super();
    }

    public GenTableColumn(String id) {
        super(id);
    }

    public GenTableColumn(GenTable genTable) {
        this.genTable = genTable;
    }

    /**
     * 获取列注解列表
     * @return
     */
    public List<String> getAnnotationList() {
        List<String> list = Lists.newArrayList();
        // 导入Jackson注解
        if ("This".equals(getJavaTp())) {
            list.add("com.fasterxml.jackson.annotation.JsonBackReference");
        }
        if ("java.util.Date".equals(getJavaTp())) {
            list.add("com.fasterxml.jackson.annotation.JsonFormat(pattern = \"yyyy-MM-dd HH:mm:ss\")");
        }
        // 导入JSR303验证依赖包
        if (!"1".equals(getNullFlg()) && !"String".equals(getJavaTp())) {
            list.add("javax.validation.constraints.NotNull(message=\"" + getTabDesc() + "不能为空\")");
        } else if (!"1".equals(getNullFlg()) && "String".equals(getJavaTp()) && !"0".equals(getDataLength())) {
            list.add("org.hibernate.validator.constraints.Length(min=1, max=" + getDataLength()
                    + ", message=\"" + getTabDesc() + "长度必须介于 1 和 " + getDataLength() + " 之间\")");
        } else if ("String".equals(getJavaTp()) && !"0".equals(getDataLength())) {
            list.add("org.hibernate.validator.constraints.Length(min=0, max=" + getDataLength()
                    + ", message=\"" + getTabDesc() + "长度必须介于 0 和 " + getDataLength() + " 之间\")");
        }
        return list;
    }

    public String getTabDesc() {
        return tabDesc;
    }

    public void setTabDesc(String tabDesc) {
        this.tabDesc = tabDesc;
    }

    /**
     * 获取字符串长度
     * @return
     */
    public String getDataLength() {
        String[] ss = StringUtil.split(StringUtil.substringBetween(getDbFieldTp(), "(", ")"), ",");
        /* 20200207 add by chenyl 对于小数类型的，最大长度为“整数位”+“.”+“小数位” */
        if (ss.length == 1 && ss[0] != null) {// && "String".equals(getJavaType())){
            return ss[0].trim();
        } else if (ss.length == 2 && ss[0] != null && ss[1] != null) {
            String len = "0";
            try {
                len = "" + (Integer.parseInt(ss[0].trim()) + 1 + Integer.parseInt(ss[1].trim()));
            } catch (Exception e) {
                System.out.println("操作失败");
            }
            return len;
        }
        return "0";
    }

    public String getDictTp() {
        return dictTp;
    }

    public void setDictTp(String dictTp) {
        this.dictTp = dictTp;
    }

    public GenTable getGenTable() {
        return genTable;
    }

    public void setGenTable(GenTable genTable) {
        this.genTable = genTable;
    }

    public String getInsertFlg() {
        return insertFlg;
    }

    public void setInsertFlg(String insertFlg) {
        this.insertFlg = insertFlg;
    }

    public String getEditFlg() {
        return editFlg;
    }

    public void setEditFlg(String editFlg) {
        this.editFlg = editFlg;
    }

    public String getListFlg() {
        return listFlg;
    }

    public void setListFlg(String listFlg) {
        this.listFlg = listFlg;
    }

    /**
     * 是否是基类字段
     * @return
     */
    public Boolean getIsNotBaseField() {
        return !StringUtil.equals(getSimpleJavaField(), "id")
                && !StringUtil.equals(getSimpleJavaField(), "rmrk")
                && !StringUtil.equals(getSimpleJavaField(), "crtr")
                && !StringUtil.equals(getSimpleJavaField(), "crtTime")
                && !StringUtil.equals(getSimpleJavaField(), "uptr")
                && !StringUtil.equals(getSimpleJavaField(), "uptTime")
                && !StringUtil.equals(getSimpleJavaField(), "delFlg");
    }

    /**
     * 是否是基类字段(用于Dataset的非驼峰型模板)
     * @return
     */
    public Boolean getIsNotBaseFieldForDataset() {
        return !StringUtil.equals(getSimpleJavaField(), "id")
                && !StringUtil.equals(getSimpleJavaField(), "rmrk")
                && !StringUtil.equals(getSimpleJavaField(), "crtr")
                && !StringUtil.equals(getSimpleJavaField(), "crt_time")
                && !StringUtil.equals(getSimpleJavaField(), "uptr")
                && !StringUtil.equals(getSimpleJavaField(), "upt_time")
                && !StringUtil.equals(getSimpleJavaField(), "del_flg");
    }

    public String getMainKeyFlg() {
        return mainKeyFlg;
    }

    public void setMainKeyFlg(String mainKeyFlg) {
        this.mainKeyFlg = mainKeyFlg;
    }

    public String getNullFlg() {
        return nullFlg;
    }

    public void setNullFlg(String nullFlg) {
        this.nullFlg = nullFlg;
    }

    public String getQryFlg() {
        return qryFlg;
    }

    public void setQryFlg(String qryFlg) {
        this.qryFlg = qryFlg;
    }

    public String getJavaField() {
        return javaField;
    }

    public void setJavaField(String javaField) {
        this.javaField = javaField;
    }

    /**
     * 获取Java字段，所有属性名
     * @return
     */
    public String[][] getJavaFieldAttrs() {
        String[] ss = StringUtil.split(StringUtil.substringAfter(getJavaField(), "|"), "|");
        String[][] sss = new String[ss.length][2];
        if (ss != null) {
            for (int i = 0; i < ss.length; i++) {
                sss[i][0] = ss[i];
                sss[i][1] = StringUtil.toUnderScoreCase(ss[i]);
            }
        }
        return sss;
    }

    /**
     * 获取Java字段，如果是对象，则获取对象.附加属性1
     * @return
     */
    public String getJavaFieldId() {
        return StringUtil.substringBefore(getJavaField(), "|");
    }

    /**
     * 获取Java字段，如果是对象，则获取对象.附加属性2
     * @return
     */
    public String getJavaFieldName() {
        String[][] ss = getJavaFieldAttrs();
        return ss.length > 0 ? getSimpleJavaField() + "." + ss[0][0] : "";
    }

    public String getDbFieldTp() {
        return dbFieldTp;
    }

    public void setDbFieldTp(String dbFieldTp) {
        this.dbFieldTp = dbFieldTp;
    }

    public String getJavaTp() {
        return javaTp;
    }

    public void setJavaTp(String javaTp) {
        this.javaTp = javaTp;
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

    public String getQryTp() {
        return qryTp;
    }

    public void setQryTp(String qryTp) {
        this.qryTp = qryTp;
    }

    public String getDpyTp() {
        return dpyTp;
    }

    public void setDpyTp(String dpyTp) {
        this.dpyTp = dpyTp;
    }

    /**
     * 获取简写列注解列表
     * @return
     */
    public List<String> getSimpleAnnotationList() {
        List<String> list = Lists.newArrayList();
        for (String ann : getAnnotationList()) {
            list.add(StringUtil.substringAfterLast(ann, "."));
        }
        return list;
    }

    /**
     * 获取简写Java字段
     * @return
     */
    public String getSimpleJavaField() {
        return StringUtil.substringBefore(getJavaField(), ".");
    }

    /**
     * 获取简写Java类型
     * @return
     */
    public String getSimpleJavaTp() {
        if ("This".equals(getJavaTp())) {
            return StringUtil.capitalize(genTable.getProcClssName());
        }
        return StringUtil.indexOf(getJavaTp(), ".") != -1
                ? StringUtil.substringAfterLast(getJavaTp(), ".")
                : getJavaTp();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /* (non-Javadoc)
     * @see com.adtec.sys.common.persistence.BaseDO#getIgnoreFields()
     */
    @Override
    public List<String> getIgnoreFields() {
        // TODO Auto-generated method stub
        List<String> list = super.getIgnoreFields();
        list.add("OPTION_Y");
        list.add("OPTION_N");
        return list;
    }
}


