/**
 * 系统名称: SmartWeb平台
 * 模块名称: cache-center的实体类模块
 * 功能描述: 缓存中心信息数据定义
 * 类 名 称  : CenterDO.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 20200716<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 *
 * ========     ======  ============================================
 */
package com.adtec.para.center.entity;

import com.adtec.sys.common.persistence.BaseDO;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 缓存中心信息
 * @author zengxj
 * @version 20200716
 */
public class ParaCacheDO extends BaseDO {

    private static final long serialVersionUID = 1L;
    private String engName;		// 英文名称
    private String chName;		// 中文名称
    private String runStat;		// 运行状态
    private String syncFlg;		// 同步状态
    private String cacheMode;		// 缓存方式
    private String basePath;		// 基础路径
    private String zkTickTime;		// zk心跳时间
    private String zkInitLimVal;		// zk初始限制数值
    private String zkSyncLimVal;		// zk同步限制数值
    private Long zkSnaResrvNum;		// zk快照保留数量
    private Long zkClnFreq;		// zk清理频率
    private Long zkConnMaxNum;		// zk连接最大数
    private String zkLoginName;		// zk登陆名
    private String zkPwd;		// zk密码
    private String rsTimeOutTime;		// rs超时时间
    private String rsPrtctSwitchFlg;		// rs保护开关标志
    private String rsClstrSwitchFlg;		// rs集群开关标志
    private String rsLoginName;		// rs登陆名
    private String rsPwd;		// rs密码

    public ParaCacheDO() {
        super();
    }

    public ParaCacheDO(String id) {
        super(id);
    }

    @Length(min=1, max=128, message="英文名称长度必须介于 1 和 128 之间")
    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    @Length(min=1, max=128, message="中文名称长度必须介于 1 和 128 之间")
    public String getChName() {
        return chName;
    }

    public void setChName(String chName) {
        this.chName = chName;
    }

    @Length(min=1, max=5, message="运行状态长度必须介于 1 和 5 之间")
    public String getRunStat() {
        return runStat;
    }

    public void setRunStat(String runStat) {
        this.runStat = runStat;
    }

    @Length(min=0, max=2, message="同步状态长度必须介于 0 和 2 之间")
    public String getSyncFlg() {
        return syncFlg;
    }

    public void setSyncFlg(String syncFlg) {
        this.syncFlg = syncFlg;
    }

    @Length(min=1, max=2, message="缓存方式长度必须介于 1 和 2 之间")
    public String getCacheMode() {
        return cacheMode;
    }

    public void setCacheMode(String cacheMode) {
        this.cacheMode = cacheMode;
    }

    @Length(min=1, max=255, message="基础路径长度必须介于 1 和 255 之间")
    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    @Length(min=1, max=8, message="zk心跳时间长度必须介于 1 和 8 之间")
    public String getZkTickTime() {
        return zkTickTime;
    }

    public void setZkTickTime(String zkTickTime) {
        this.zkTickTime = zkTickTime;
    }

    @Length(min=1, max=8, message="zk初始限制数值长度必须介于 1 和 8 之间")
    public String getZkInitLimVal() {
        return zkInitLimVal;
    }

    public void setZkInitLimVal(String zkInitLimVal) {
        this.zkInitLimVal = zkInitLimVal;
    }

    @Length(min=1, max=8, message="zk同步限制数值长度必须介于 1 和 8 之间")
    public String getZkSyncLimVal() {
        return zkSyncLimVal;
    }

    public void setZkSyncLimVal(String zkSyncLimVal) {
        this.zkSyncLimVal = zkSyncLimVal;
    }

    @NotNull(message="zk快照保留数量不能为空")
    public Long getZkSnaResrvNum() {
        return zkSnaResrvNum;
    }

    public void setZkSnaResrvNum(Long zkSnaResrvNum) {
        this.zkSnaResrvNum = zkSnaResrvNum;
    }

    @Length(min=1, max=10, message="zk清理频率长度必须介于 1 和 10 之间")
    public Long getZkClnFreq() {
        return zkClnFreq;
    }

    public void setZkClnFreq(Long zkClnFreq) {
        this.zkClnFreq = zkClnFreq;
    }

    @NotNull(message="zk连接最大数不能为空")
    public Long getZkConnMaxNum() {
        return zkConnMaxNum;
    }

    public void setZkConnMaxNum(Long zkConnMaxNum) {
        this.zkConnMaxNum = zkConnMaxNum;
    }

    @Length(min=0, max=255, message="zk登陆名长度必须介于 0 和 255 之间")
    public String getZkLoginName() {
        return zkLoginName;
    }

    public void setZkLoginName(String zkLoginName) {
        this.zkLoginName = zkLoginName;
    }

    @Length(min=0, max=128, message="zk密码长度必须介于 0 和 128 之间")
    public String getZkPwd() {
        return zkPwd;
    }

    public void setZkPwd(String zkPwd) {
        this.zkPwd = zkPwd;
    }

    @Length(min=1, max=8, message="rs超时时间长度必须介于 1 和 8 之间")
    public String getRsTimeOutTime() {
        return rsTimeOutTime;
    }

    public void setRsTimeOutTime(String rsTimeOutTime) {
        this.rsTimeOutTime = rsTimeOutTime;
    }

    @Length(min=1, max=2, message="rs保护开关标志长度必须介于 1 和 2 之间")
    public String getRsPrtctSwitchFlg() {
        return rsPrtctSwitchFlg;
    }

    public void setRsPrtctSwitchFlg(String rsPrtctSwitchFlg) {
        this.rsPrtctSwitchFlg = rsPrtctSwitchFlg;
    }

    @Length(min=1, max=2, message="rs集群开关标志长度必须介于 1 和 2 之间")
    public String getRsClstrSwitchFlg() {
        return rsClstrSwitchFlg;
    }

    public void setRsClstrSwitchFlg(String rsClstrSwitchFlg) {
        this.rsClstrSwitchFlg = rsClstrSwitchFlg;
    }

    @Length(min=0, max=255, message="rs登陆名长度必须介于 0 和 255 之间")
    public String getRsLoginName() {
        return rsLoginName;
    }

    public void setRsLoginName(String rsLoginName) {
        this.rsLoginName = rsLoginName;
    }

    @Length(min=0, max=128, message="rs密码长度必须介于 0 和 128 之间")
    public String getRsPwd() {
        return rsPwd;
    }

    public void setRsPwd(String rsPwd) {
        this.rsPwd = rsPwd;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("CenterDO [ ");
        sb.append("id="+id+" , ");
        sb.append("engName="+engName+" , ");
        sb.append("chName="+chName+" , ");
        sb.append("runStat="+runStat+" , ");
        sb.append("syncFlg="+syncFlg+" , ");
        sb.append("cacheMode="+cacheMode+" , ");
        sb.append("basePath="+basePath+" , ");
        sb.append("zkTickTime="+zkTickTime+" , ");
        sb.append("zkInitLimVal="+zkInitLimVal+" , ");
        sb.append("zkSyncLimVal="+zkSyncLimVal+" , ");
        sb.append("zkSnaResrvNum="+zkSnaResrvNum+" , ");
        sb.append("zkClnFreq="+zkClnFreq+" , ");
        sb.append("zkConnMaxNum="+zkConnMaxNum+" , ");
        sb.append("zkLoginName="+zkLoginName+" , ");
        sb.append("zkPwd="+zkPwd+" , ");
        sb.append("rsTimeOutTime="+rsTimeOutTime+" , ");
        sb.append("rsPrtctSwitchFlg="+rsPrtctSwitchFlg+" , ");
        sb.append("rsClstrSwitchFlg="+rsClstrSwitchFlg+" , ");
        sb.append("rsLoginName="+rsLoginName+" , ");
        sb.append("rsPwd="+rsPwd+" , ");
        sb.append("crtTime="+crtTime+" , ");
        sb.append("uptr="+uptr+" , ");
        sb.append("uptTime="+uptTime+" , ");
        sb.append("rmrk="+rmrk+" , ");
        sb.append(" ] ");
        return sb.toString();
    }

    @Override
    public List<String> getIgnoreFields() {
        // TODO Auto-generated method stub
        List<String> list = super.getIgnoreFields();
//        list.add("delFlg");
        list.add("crtr");
        list.add("crtTime");
        list.add("uptr");
        list.add("uptTime");
        return list;
    }

    @Override
    public List<String> getMatchFields() {
        // TODO Auto-generated method stub
        List<String> list = new ArrayList<String>();
        list.add("id");
        return list;
    }
}