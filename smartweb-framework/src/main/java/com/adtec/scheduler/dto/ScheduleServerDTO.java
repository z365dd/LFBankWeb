package com.adtec.scheduler.dto;

/**
 * @author lijunbin
 */
public class ScheduleServerDTO {

    private String nodeName;

    private String masterFlag;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getMasterFlag() {
        return masterFlag;
    }

    public void setMasterFlag(String masterFlag) {
        this.masterFlag = masterFlag;
    }

    @Override
    public String toString() {
        return "ScheduleServerDTO{" +
                "nodeName='" + nodeName + '\'' +
                ", masterFlag=" + masterFlag +
                '}';
    }
}
