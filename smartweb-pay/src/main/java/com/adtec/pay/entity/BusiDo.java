package com.adtec.pay.entity;

import com.adtec.sys.common.persistence.BaseDO;

import java.util.List;

/**
 * t_pip_busi 业务表映射类
 */
public class BusiDo extends BaseDO {
    private static final long serialVersionUID = 4524922918310436925L;
    //业务编号
    private String busiNo;
    //单位编号
    private String entrNo;
    //业务名称
    private String busiName;
    //可售产品代码
    private String saleProdCode;
    //开通状态
    private String stat;
    //业务描述
    private String busiDesc;
    //公司id
    private String compNo;
    //机构id
    private String brchId;

    public String getBrchId() {
        return brchId;
    }

    public void setBrchId(String brchId) {
        this.brchId = brchId;
    }

    public String getCompNo() {
        return compNo;
    }


    public void setCompNo(String compNo) {
        this.compNo = compNo;
    }


    public String getBusiNo() {
        return busiNo;
    }


    public void setBusiNo(String busiNo) {
        this.busiNo = busiNo;
    }


    public String getEntrNo() {
        return entrNo;
    }


    public void setEntrNo(String entrNo) {
        this.entrNo = entrNo;
    }


    public String getBusiName() {
        return busiName;
    }


    public void setBusiName(String busiName) {
        this.busiName = busiName;
    }


    public String getSaleProdCode() {
        return saleProdCode;
    }


    public void setSaleProdCode(String saleProdCode) {
        this.saleProdCode = saleProdCode;
    }


    public String getStat() {
        return stat;
    }


    public void setStat(String stat) {
        this.stat = stat;
    }


    public String getBusiDesc() {
        return busiDesc;
    }


    public void setBusiDesc(String busiDesc) {
        this.busiDesc = busiDesc;
    }


    /**
     * 覆盖 设置忽略字段
     */
    @Override
    public List<String> getIgnoreFields() {
        List<String> ignoreFields = super.getIgnoreFields();
        ignoreFields.add("serialVersionUID");
        ignoreFields.add("DEL_FLAG_NORMAL");
        ignoreFields.add("DEL_FLAG_DELETE");
        ignoreFields.add("id");
        ignoreFields.add("createBy");
        ignoreFields.add("createDate");
        ignoreFields.add("updateBy");
        ignoreFields.add("updateDate");
        ignoreFields.add("remarks");
        ignoreFields.add("delFlag");
        ignoreFields.add("LIST");
        ignoreFields.add("ELEM_KEY");
        ignoreFields.add("ELEM_NAME");
        ignoreFields.add("ELEM_KV");
        return ignoreFields;
    }

}
