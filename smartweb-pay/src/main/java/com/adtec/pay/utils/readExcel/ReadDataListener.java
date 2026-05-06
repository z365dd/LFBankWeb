package com.adtec.pay.utils.readExcel;

import com.adtec.pay.dao.OfflineDao;
import com.adtec.pay.entity.OfflineDO;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;

public class ReadDataListener implements ReadListener<OfflineDO> {

    private OfflineDao offlineDao;
    private String busiNo;
    private String busiName;
    private String projectName;
    private String projTp;
    private String oweMonth;
    private long ser = 0;

    public ReadDataListener(OfflineDao offlineDao) {
        this.offlineDao = offlineDao;
    }

    public ReadDataListener(OfflineDao OfflineDao, String busiNo, String busiName, String projectName, String projTp, String oweMonth) {
        this.offlineDao = OfflineDao;
        this.busiNo = busiNo;
        this.busiName = busiName;
        this.projectName = projectName;
        this.projTp = projTp;
        this.oweMonth = oweMonth;
    }

    @Override
    public void invoke(OfflineDO obj, AnalysisContext analysisContext) {
        offlineDao.save(obj, busiNo, busiName, projectName, projTp, oweMonth, ++this.ser);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        this.ser = 0;
    }
}
