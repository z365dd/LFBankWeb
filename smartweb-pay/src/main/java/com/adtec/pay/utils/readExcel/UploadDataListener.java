package com.adtec.pay.utils.readExcel;

import com.adtec.pay.entity.BatDtl;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;

import java.util.List;

public class UploadDataListener implements ReadListener<BatDtl> {

    //批量入库的数据条数
    private static final int BATCH_COUNT = 5;

    private List<BatDtl> cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

//    private UploadDAO uploadDAO;
//    private String busiNo;
//    private String busiName;
//    private String batNo;
//    private String batName;
//
//    public UploadDataListener(UploadDAO uploadDAO) {
//        this.uploadDAO = uploadDAO;
//    }
//
//    public UploadDataListener(UploadDAO uploadDAO, String busiNo, String busiName, String batNo, String batName) {
//        this.uploadDAO = uploadDAO;
//        this.busiNo = busiNo;
//        this.busiName = busiName;
//        this.batNo = batNo;
//        this.batName = batName;
//    }

    //数据解析调用
    @Override
    public void invoke(BatDtl data, AnalysisContext analysisContext) {
        cacheDataList.add(data);
        if (cacheDataList.size() >= BATCH_COUNT) {
//            saveData();
            //储存完成清理list
            cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    //数据解析完之后调用该方法
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //这里也要保存 防止数据遗留
//        saveData();
    }

//    private void saveData() {
//        uploadDAO.save(cacheDataList);
//    }
}
