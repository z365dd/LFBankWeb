/**
 * 系统名称: 缓存中心
 * 模块名称: 缓存中心zk节点信息实体类
 * 类  名  称: CenterZkNodeDO.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl
 * 开发时间: 2018-11-13 11:47:37
 * 系统版本: V1.0.0
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================ *
 * ========     ======  ============================================
 */
package com.adtec.para.center.entity;

import com.adtec.sys.common.persistence.BaseDO;

import java.util.List;

public class CenterZkNodeDO extends BaseDO {
    private static final long serialVersionUID = -1L;
    /*所属缓存中心id*/
    private String cacheCentrId;
    /*节点序号*/
    private int zkId;
    /*节点地址*/
    private String ip;
    /*节点端口*/
    private int port;
    /*通讯端口*/
    private int commPort;
    /*选举端口*/
    private int electionPort;
    /*快照日志目录*/
    private String dataPath;
    /*事务日志目录*/
    private String dataLogPath;

    public String getCacheCentrId() {
        return cacheCentrId;
    }

    public void setCacheCentrId(String cacheCentrId) {
        this.cacheCentrId = cacheCentrId;
    }

    public int getZkId() {
        return zkId;
    }

    public void setZkId(int zkId) {
        this.zkId = zkId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getCommPort() {
        return commPort;
    }

    public void setCommPort(int commPort) {
        this.commPort = commPort;
    }

    public int getElectionPort() {
        return electionPort;
    }

    public void setElectionPort(int electionPort) {
        this.electionPort = electionPort;
    }

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public String getDataLogPath() {
        return dataLogPath;
    }

    public void setDataLogPath(String dataLogPath) {
        this.dataLogPath = dataLogPath;
    }

    public String getDataDirPath(String folder, int zkId){
        if (!folder.endsWith("/")){
            folder+="/";
        }
    	return String.format("%s/%s/%s", folder+"zkcluster", "z"+zkId, "data");
    }
    
    public String getDataLogDirPath(String folder, int myId){
        if (!folder.endsWith("/")){
            folder+="/";
        }
    	return String.format("%s/%s/%s", folder+"zkcluster", "z"+zkId, "log");
    }
	@Override
    public List<String> getIgnoreFields() {
		List<String> list = super.getIgnoreFields();
		list.add("serialVersionUID");
		list.add("delFlag");
		list.add("delFlg");
		list.add("remarks");
		return list;
	}
}
