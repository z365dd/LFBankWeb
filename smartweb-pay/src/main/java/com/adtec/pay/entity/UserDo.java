package com.adtec.pay.entity;

import com.adtec.sys.common.persistence.BaseDO;

public class UserDo extends BaseDO {

    private static final long serialVersionUID = 2937530196546095748L;
    private String brchId;
    private String loginName;
    private String pwd;
    private String email;
    private String telNo;
    private String phoneNo;
    private String tntId;
    private String crtr;
    private String crtTime;
    private String uptr;
    private String uptTime;
    private String name;
    private String userNo;

    public String getBrchId() {
        return brchId;
    }

    public void setBrchId(String brchId) {
        this.brchId = brchId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    @Override
    public String getCrtr() {
        return crtr;
    }

    @Override
    public void setCrtr(String crtr) {
        this.crtr = crtr;
    }

    @Override
    public String getCrtTime() {
        return crtTime;
    }

    @Override
    public void setCrtTime(String crtTime) {
        this.crtTime = crtTime;
    }

    @Override
    public String getUptr() {
        return uptr;
    }

    @Override
    public void setUptr(String uptr) {
        this.uptr = uptr;
    }

    @Override
    public String getUptTime() {
        return uptTime;
    }

    @Override
    public void setUptTime(String uptTime) {
        this.uptTime = uptTime;
    }

    public String getTntId() {
        return tntId;
    }

    public void setTntId(String tntId) {
        this.tntId = tntId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
