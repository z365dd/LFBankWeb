package com.adtec.pay.service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.framework.common.util.CollectionUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.common.util.StringUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.pay.dao.ReceiptDao;
import com.adtec.pay.dto.recorded.*;
import com.adtec.pay.entity.ReceiptPrint;
import com.adtec.pay.utils.CaculateUtils;
import com.adtec.pay.utils.MLppUtils;
import com.adtec.sys.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 入账查询服务类 回单打印和商户入账查询使用
 */
@Service
public class RecQryService {

    @Autowired
    private ReceiptDao receiptDao;

    /**
     * 获取汇总入账数 按照有无手续费
     *
     * @param req
     * @param start
     * @param limit
     * @return
     */
    public RecSumQryRes getTotList(IDataset reqDs, RecSumQryReq req, int start, int limit) {
        String busiNo = req.getBUSI_NO();
        req = setTime(reqDs, req);
        ReqDTO reqDTO = new ReqDTO(req);
        MLppUtils.setReqHead(busiNo, reqDTO, "MLppMerRecList", start, limit);
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        // 和Starring V6进行通信
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("MLPP_PARTID"), "MLppMerRecList",
                reqDTO, RecSumQryRes.class, null);
        RecSumQryRes recSumQryRes = (RecSumQryRes) resDTO.getBODY();
        return recSumQryRes;
    }

    /**
     * 请求前预处理  加工时间
     *
     * @param reqDs
     * @param req
     * @param req
     * @return
     */
    private RecSumQryReq setTime(IDataset reqDs, RecSumQryReq req) {
        String endTime = reqDs.getString("endTime");
        String END_TIME = "";
        //如果endTime为空 回单打印调用该接口进行校验
        if (StringUtil.isEmpty(endTime)) {
            String transDate = reqDs.getString("transDate");
            END_TIME = transDate.substring(0, 4) + transDate.substring(5, 7) + transDate.substring(8);
        } else {
            END_TIME = endTime.substring(0, 4) + endTime.substring(5, 7) + endTime.substring(8);
        }
        // 定制完成之后页面只查询一天 因为前端删除了开始日期 使用开始日期=结束日期=入账日期
        req.setSTR_DATE(END_TIME);
        req.setEND_DATE(END_TIME);
        return req;
    }


    /**
     * 获取回单打印excel数据
     *
     * @param req
     * @param start
     * @param limit
     * @return
     */
    public RecSumQryRes getTotListForPrint(IDataset reqDs, RecSumQryReq req, int start, int limit) {
        String busiNo = req.getBUSI_NO();
        req = setTime(reqDs, req);
        ReqDTO reqDTO = new ReqDTO(req);
        limit = limit == 0 ? 10 : limit;
        MLppUtils.setReqHead(busiNo, reqDTO, "MLppMerRecList", start, limit);
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        // 和Starring V6进行通信
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("MLPP_PARTID"), "MLppMerRecList",
                reqDTO, RecSumQryRes.class, null);
        RecSumQryRes recSumQryRes = (RecSumQryRes) resDTO.getBODY();
        if ("N".equals(recSumQryRes.getCLR_FLG())) {
            throw new BaseException(SysErr.E_MESSAGE, "该日期清算尚未完成，不可打印");
        }
        List<RecSumQryResList> list = recSumQryRes.getLIST();
        if (CollectionUtils.isEmpty(list)) {
            throw new BaseException(SysErr.E_MESSAGE, "该日期无清算明细，不可打印");
        }
        return recSumQryRes;
    }


    /**
     * 回单打印查询或商户入账明细查询
     *
     * @param req
     * @param start
     * @param limit
     * @return
     */
    public RecQryRes getRecListByPayType(RecQryReq req, int start, int limit) {
        String busiNo = req.getBUSI_NO();
        String clrDate = req.getCLR_DATE();
        String strDate = req.getSTR_DATE();
        String endDate = req.getEND_DATE();
        if ("1".equals(req.getType())) {
            strDate = strDate.substring(0, 4) + strDate.substring(5, 7) + strDate.substring(8);
            endDate = endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8);
        }
        req.setCLR_DATE(clrDate);
        req.setSTR_DATE(strDate);
        req.setEND_DATE(endDate);
        ReqDTO reqDTO = new ReqDTO(req);
        MLppUtils.setReqHead(busiNo, reqDTO, "MLppMerRecDtl", start, limit);
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        // 和Starring V6进行通信
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("MLPP_PARTID"), "MLppMerRecDtl",
                reqDTO, RecQryRes.class, null);
        RecQryRes recQryRes = (RecQryRes) resDTO.getBODY();
        //组成商户入账的树形下拉列表数据 进行回单打印和商户入账的区分 回单打印为1 商户入账为0
        String type = req.getType();
        if (!CollectionUtil.isEmpty(recQryRes.getLIST())) {
            List<RecQryResList> list = recQryRes.getLIST();
            //引用类型 形参的改变会影响实际参数
            list = assembleTreeData(list, type);
            //组装好的数据重新赋值
            long totNum = 0L;
            double totAmt = 0.00;
            //金额汇总
            for (RecQryResList recQryResList : list) {
                totNum += recQryResList.getTOT_NUM();
                totAmt = CaculateUtils.add(totAmt, recQryResList.getTOT_AMT());
            }
            String brchName = UserUtils.getUser().getOffice().getName();
            recQryRes.setBrchName(brchName);
            recQryRes.setTOT_NUM(totNum);
            recQryRes.setTOT_AMT(totAmt);
            recQryRes.setLIST(list);
        }
        return recQryRes;

    }


    private List<RecQryResList> assembleTreeData(List<RecQryResList> list, String type) {
        //作为id和pid
        int id = 0;
        int pid = 0;

        Set<String> set = new HashSet<>();
        for (RecQryResList recQryResList : list) {
            set.add(recQryResList.getCHNL_NO());
        }
        List<RecQryResList> addList = new ArrayList<>();
        for (String s : set) {
            Long totNum = 0L;
            double totAmt = 0.00;
            double clrAmt = 0.00;
            double dctMertAmt = 0.00;
            double dctBankAmt = 0.00;
            double feeAmt = 0.00;
            double dctAmt = 0.00;
            double batAmt = 0.00;
            RecQryResList addNode = null;
            for (RecQryResList node : list) {
                //这里特殊处理  生活缴费渠道的入账明细  支付方式为160-批量扣款
                if ("160".equals(node.getCHNL_NO())) {
                    node.setPAY_TP("160");
                }
                if (s.equals(node.getCHNL_NO())) {
                    //新增父节点-渠道相同记录金额的汇总
                    if (null == addNode) {
                        addNode = new RecQryResList(node);
                        addNode.setPid(0);
                        //双指针 pid记录新增父节点的id
                        addNode.setId(++id);
                        pid = id;
                        addList.add(addNode);
                    }
                    node.setPid(pid);
                    node.setId(++id);
                    //笔数、金额累加
                    totNum += node.getTOT_NUM();
                    addNode.setTOT_NUM(totNum);
                    totAmt = CaculateUtils.add(totAmt, node.getTOT_AMT());
                    addNode.setTOT_AMT(totAmt);
                    clrAmt = CaculateUtils.add(clrAmt, node.getCLR_AMT());
                    addNode.setCLR_AMT(clrAmt);
                    dctMertAmt = CaculateUtils.add(dctMertAmt, node.getDCT_MERT_AMT());
                    addNode.setDCT_MERT_AMT(dctMertAmt);
                    dctBankAmt = CaculateUtils.add(dctBankAmt, node.getDCT_BANK_AMT());
                    addNode.setDCT_BANK_AMT(dctBankAmt);
                    feeAmt = CaculateUtils.add(feeAmt, node.getFEE_AMT());
                    addNode.setFEE_AMT(feeAmt);
                    dctAmt = CaculateUtils.add(dctAmt, node.getDCT_AMT());
                    addNode.setDCT_AMT(dctAmt);
                    batAmt = CaculateUtils.add(batAmt, node.getBAT_AMT() == null ? 0.00 : node.getBAT_AMT());
                    addNode.setBAT_AMT(batAmt);
                }
            }
        }
        list.addAll(addList);
        return "0".equals(type) ? list : addList;
    }

    /**
     * 获取回单打印数据
     *
     * @param busiNo
     * @param strDate
     * @param endDate
     * @param chnlNo
     * @return
     */
    public List<ReceiptPrint> getExcelData(String busiNo, String strDate, String endDate, String chnlNo) {
        return receiptDao.getExcelData(busiNo, strDate, endDate, chnlNo);
    }

    /**
     * 打印前校验
     *
     * @param busiNo
     * @param strDate
     * @param endDate
     * @return
     */
    public int checkPrint(String busiNo, String strDate, String endDate) {
        return receiptDao.checkPrint(busiNo, strDate, endDate);
    }

    public RecQryRes getSumData(RecQryReq recQryReq) {
        return receiptDao.getSumData(recQryReq);
    }


    public List<RecQryResList> getQryList(RecQryReq recQryReq, int start, int limit) {
        return receiptDao.getQryList(recQryReq, start, limit);
    }

    public int count(RecQryReq recQryReq) {

        return receiptDao.countQryList(recQryReq);
    }
}
