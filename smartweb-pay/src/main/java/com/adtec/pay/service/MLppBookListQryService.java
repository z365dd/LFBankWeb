package com.adtec.pay.service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.pay.dao.BookDao;
import com.adtec.pay.dto.bookList.Book;
import com.adtec.pay.dto.bookList.BookList;
import com.adtec.pay.dto.bookList.MLppQryBookListReq;
import com.adtec.pay.dto.bookList.MLppQryBookListRes;
import com.adtec.pay.utils.MLppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MLppBookListQryService {

    @Autowired
    private BookDao bookDao;

    /**
     * 流水列表查询的公共请求
     *
     * @param req
     * @param start
     * @param limit
     * @return
     */
    public MLppQryBookListRes getBookList(IDataset reqDs, MLppQryBookListReq req, int start, int limit) {
        String busiNo = req.getBUSI_NO();
        String tranStat = req.getTRAN_STAT();
        req.setTRAN_STAT(null == tranStat ? "AA" : tranStat);
        //时间预处理
        req = setTime(reqDs, req);
        ReqDTO reqDTO = new ReqDTO(req);
        MLppUtils.setReqHead(busiNo, reqDTO, "MLppQryBookList", start, limit);
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        // 和Starring V6进行通信
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("MLPP_PARTID"), "MLppQryBookList",
                reqDTO, MLppQryBookListRes.class, null);
        MLppQryBookListRes mlppQryBookListRes = (MLppQryBookListRes) resDTO.getBODY();
        return mlppQryBookListRes;
    }

    /**
     * 请求前预处理  加工时间
     *
     * @param reqDs
     * @param req
     * @return
     */
    private MLppQryBookListReq setTime(IDataset reqDs, MLppQryBookListReq req) {
        String startTime = reqDs.getString("startTime");
        String STR_TIME = startTime.substring(0, 4) + startTime.substring(5, 7) + startTime.substring(8);
        String endTime = reqDs.getString("endTime");
        String END_TIME = endTime.substring(0, 4) + endTime.substring(5, 7) + endTime.substring(8);
        req.setSTR_DATE(STR_TIME);
        req.setEND_DATE(END_TIME);
        return req;
    }

    /**
     * 获取差错关联的原缴费流水
     *
     * @param req
     * @return 根据原流水表和流水日期查询到的缴费流水
     */
    public BookList getErrWithBookRecord(MLppQryBookListReq req) {
        String busiNo = req.getBUSI_NO();
        ReqDTO reqDTO = new ReqDTO(req);
        MLppUtils.setReqHead(busiNo, reqDTO, "MLppQryBookDtl");
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("MLPP_PARTID"), "MLppQryBookDtl",
                reqDTO, BookList.class, null);
        BookList bookList = (BookList) resDTO.getBODY();
        return bookList;
    }

    /**
     * 导出 记录的查询
     *
     * @param qryListReq
     * @return
     */
    public List<Book> getAllBookList(MLppQryBookListReq qryListReq) {
        List<Book> list = bookDao.findAll(qryListReq);
        // 设置分页参数
        return list;
    }
}
