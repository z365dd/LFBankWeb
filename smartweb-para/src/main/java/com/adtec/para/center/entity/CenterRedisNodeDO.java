/**
 * 系统名称: 缓存中心
 * 模块名称: 缓存中心redis节点信息实体类
 * 类  名  称: CenterRedisNodeDO.java
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

public class CenterRedisNodeDO extends BaseDO {
    private static final long serialVersionUID = -1L;
    /*所属缓存中心id*/
    private String cacheCentrId;
    /*所属主节点*/
    private String mainNodeId;
    /*节点地址*/
    private String ip;
    /*节点端口*/
    private int port;
    /*起始HASH槽*/
    private int strSlotNum;
    /*结束HASH槽*/
    private int endSlotNum;
    /*进程保存文件*/
    private String progFilePath;
    /*日志目录*/
    private String logFilePath;
    /*集群内部配置文件*/
    private String clstrCfgFilePath;

    private String slaveCount;
    private List<CenterRedisNodeDO> slave;

    public String getCacheCentrId() {
        return cacheCentrId;
    }

    public void setCacheCentrId(String cacheCentrId) {
        this.cacheCentrId = cacheCentrId;
    }

    public String getMainNodeId() {
        return mainNodeId;
    }

    public void setMainNodeId(String mainNodeId) {
        this.mainNodeId = mainNodeId;
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

    public int getStrSlotNum() {
        return strSlotNum;
    }

    public void setStrSlotNum(int strSlotNum) {
        this.strSlotNum = strSlotNum;
    }

    public int getEndSlotNum() {
        return endSlotNum;
    }

    public void setEndSlotNum(int endSlotNum) {
        this.endSlotNum = endSlotNum;
    }

    public String getProgFilePath() {
        return progFilePath;
    }

    public void setProgFilePath(String progFilePath) {
        this.progFilePath = progFilePath;
    }

    public String getLogFilePath() {
        return logFilePath;
    }

    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    public String getClstrCfgFilePath() {
        return clstrCfgFilePath;
    }

    public void setClstrCfgFilePath(String clstrCfgFilePath) {
        this.clstrCfgFilePath = clstrCfgFilePath;
    }

    /**
	 * @return the slaveCount
	 */
	public String getSlaveCount() {
		return slaveCount;
	}
	/**
	 * @param slaveCount the slaveCount to set
	 */
	public void setSlaveCount(String slaveCount) {
		this.slaveCount = slaveCount;
	}
	/**
	 * @return the slave
	 */
	public List<CenterRedisNodeDO> getSlave() {
		return slave;
	}
	/**
	 * @param slave the slave to set
	 */
	public void setSlave(List<CenterRedisNodeDO> slave) {
		this.slave = slave;
	}
    
	public String getPidFile(int port){
		return String.format("/%s/%s", "var/run", "redis-"+port+".pid");
	}
    
	public String getLogFile(String folder, int port){
		return String.format("\"%s/%s\"", folder+"redis-cluster/log", "redis-"+port+".log");
	}
	                   
	public String getClusterConfigFile(int port){
		return String.format("%s", "nodes-"+port+".conf");
	}
	@Override
    public List<String> getIgnoreFields() {
		List<String> list = super.getIgnoreFields();
		list.add("serialVersionUID");
		list.add("delFlag");
		list.add("delFlg");
		list.add("remarks");
		list.add("slaveCount");
		list.add("slave");
		return list;
	}
}
