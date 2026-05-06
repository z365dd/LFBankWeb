package com.adtec.sys.modules.sys.entity;

import com.adtec.framework.common.util.StringUtil;
import com.adtec.sys.common.persistence.BaseDO;
import com.adtec.sys.common.persistence.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.xml.bind.annotation.XmlTransient;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

/**
 * 日志Entity
 *
 * @version 2014-8-19
 */
public class Log extends BaseDO {

    // 日志类型（1：接入日志；2：错误日志）
    public static final String TYPE_ACCESS = "1";
    public static final String TYPE_EXCEPTION = "2";
    private static final long serialVersionUID = 1L;
    /**
     * 自定义SQL（SQL标识，SQL内容）
     */
    protected Map<String, String> sqlMap;
    private String logTp;        // 日志类型（1：接入日志；2：错误日志）
    private String infoTitle;        // 日志标题
    private String termIp;    // 操作用户的IP地址
    private String reqUrl;    // 操作的URI
    private String reqMeth;        // 操作的方式
    private String userAgentMsg;    // 操作用户代理信息
    private String seq;		// 日志流水号
    /**
     * oracle下使用byte[]类型
     */
    private byte[] paraDescByteData;        // 操作提交的数据
    private byte[] javaExctByteData;    // 异常信息
    private Date beginDate;        // 开始日期
    private Date endDate;        // 结束日期

    private String createByName;//创建者名称
    private String createByBrchName;//创建者所属部门名称
    private String createByTntName;//创建者所属租户名称

    /**
     * 当前实体分页对象
     */
    protected Page<Log> page;

    public Log() {
        super();
    }

    public Log(String id) {
        super(id);
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getLogTp() {
        return logTp;
    }

    public void setLogTp(String logTp) {
        this.logTp = logTp;
    }

    public String getInfoTitle() {
        return infoTitle;
    }

    public void setInfoTitle(String infoTitle) {
        this.infoTitle = infoTitle;
    }

    public String getTermIp() {
        return termIp;
    }

    public void setTermIp(String termIp) {
        this.termIp = termIp;
    }

    public String getReqUrl() {
        return reqUrl;
    }

    public void setReqUrl(String reqUrl) {
        this.reqUrl = reqUrl;
    }

    public String getReqMeth() {
        return reqMeth;
    }

    public void setReqMeth(String reqMeth) {
        this.reqMeth = reqMeth;
    }

    public String getUserAgentMsg() {
        return userAgentMsg;
    }

    public void setUserAgentMsg(String userAgentMsg) {
        this.userAgentMsg = userAgentMsg;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public byte[] getParaDescByteData() {
        return paraDescByteData;
    }

    public void setParaDescByteData(byte[] paraDescByteData) {
        this.paraDescByteData = paraDescByteData;
    }

    public byte[] getJavaExctByteData() {
        return javaExctByteData;
    }

    public void setJavaExctByteData(byte[] javaExctByteData) {
        this.javaExctByteData = javaExctByteData;
    }

    /**
     * 设置请求参数
     *
     * @param paramMap
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void setParaDescByteData(Map paramMap) {
        if (paramMap == null) {
            return;
        }
        StringBuilder params = new StringBuilder();
        for (Map.Entry<String, String[]> param : ((Map<String, String[]>) paramMap).entrySet()) {
            params.append(("".equals(params.toString()) ? "" : "&") + param.getKey() + "=");
            String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
            params.append(StringUtil.abbr(StringUtil.endsWithIgnoreCase(param.getKey(), "password") ? "" : paramValue, 100));
        }
        byte[] pBytes = null;
        try {
            pBytes = params.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.paraDescByteData = pBytes;
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

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }

    public String getCreateByBrchName() {
        return createByBrchName;
    }

    public void setCreateByBrchName(String createByBrchName) {
        this.createByBrchName = createByBrchName;
    }

    public String getCreateByTntName() {
        return createByTntName;
    }

    public void setCreateByTntName(String createByTntName) {
        this.createByTntName = createByTntName;
    }

    @JsonIgnore
    @XmlTransient
    public Page<Log> getPage() {
        if (page == null) {
            page = new Page<Log>();
        }
        return page;
    }

    public Page<Log> setPage(Page<Log> page) {
        this.page = page;
        return page;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}