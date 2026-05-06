package com.adtec.sys.modules.sys.entity;

import com.adtec.sys.common.persistence.BaseDO;

import java.util.List;

public class FileDO extends BaseDO {
    private static final long serialVersionUID = -1L;
    /* 文件名 */
    private String fileName;
    /* 保存名 */
    private String saveName;
    /*
     * 资源类型前2位表示大类，后2位表示小类
     * 0101-资产库框架
     * 0200-filebeat,0201-kafka,0202-logstash,0203-ES,0204-metricbeat
     */
    private String resTp;
    /* 文件类型 */
    private String fileTp;
    /* 保存路径 */
    private String savePath;
    /* 预览路径 */
    private String prevPath;
    /* 下载次数 */
    private int downloadNum;
    /* 是否引用 */
    private String quoteFlg;
    /* dac校验 */
    private String dac;
    /* 引用常量:0-否 */
    public final static String IS_USED_N = "0";
    /* 引用常量:1-是 */
    public final static String IS_USED_Y = "1";
    private String projName;

    // 文件归属
    private String ownerShip;
    private String fileIds;
    private String docTp;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public int getDownloadNum() {
        return downloadNum;
    }

    public void setDownloadNum(int downloadNum) {
        this.downloadNum = downloadNum;
    }

    public String getResTp() {
        return resTp;
    }

    public void setResTp(String resTp) {
        this.resTp = resTp;
    }

    public String getFileTp() {
        return fileTp;
    }

    public void setFileTp(String fileTp) {
        this.fileTp = fileTp;
    }

    public String getPrevPath() {
        return prevPath;
    }

    public void setPrevPath(String prevPath) {
        this.prevPath = prevPath;
    }

    public String getQuoteFlg() {
        return quoteFlg;
    }

    public void setQuoteFlg(String quoteFlg) {
        this.quoteFlg = quoteFlg;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getDac() {
        return dac;
    }

    public void setDac(String dac) {
        this.dac = dac;
    }

    public String getOwnerShip() {
        return ownerShip;
    }

    public void setOwnerShip(String ownerShip) {
        this.ownerShip = ownerShip;
    }

    public String getFileIds() {
        return fileIds;
    }

    public void setFileIds(String fileIds) {
        this.fileIds = fileIds;
    }
    
    public String getDocTp() {
        return docTp;
    }

    public void setDocTp(String docTp) {
        this.docTp = docTp;
    }

    @Override
    public List<String> getIgnoreFields() {
        List<String> ignoreList = super.getIgnoreFields();
        ignoreList.add("IS_USED_N");
        ignoreList.add("IS_USED_Y");
        ignoreList.add("delFlg");
        ignoreList.add("ownerShip");
        ignoreList.add("fileIds");
        ignoreList.add("projName");
        return ignoreList;
    }

    @Override
    public List<String> getMatchFields() {
        // TODO Auto-generated method stub
        return super.getMatchFields();
    }

}
