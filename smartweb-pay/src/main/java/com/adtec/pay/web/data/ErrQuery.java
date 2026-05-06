package com.adtec.pay.web.data;

import com.adtec.framework.common.util.ServerResponse;
import com.adtec.framework.common.util.StringUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.pay.dto.bookList.BookList;
import com.adtec.pay.dto.bookList.MLppQryBookListReq;
import com.adtec.pay.dto.error.ErrHandleReq;
import com.adtec.pay.dto.error.ErrQryReq;
import com.adtec.pay.dto.error.ErrQryRes;
import com.adtec.pay.entity.BusiDo;
import com.adtec.pay.service.ComQueryService;
import com.adtec.pay.service.ErrQryService;
import com.adtec.pay.service.MLppBookListQryService;
import com.adtec.pay.utils.MLppUtils;
import com.adtec.pay.webResp.ComRespBody;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.modules.sys.utils.UserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping("${adminPath}/errQuery/data/")
public class ErrQuery extends BaseController {

    @Autowired
    private ErrQryService errQryService;
    @Autowired
    private ComQueryService comQueryService;

    @Autowired
    private MLppBookListQryService mLppBookListQryService;

    /**
     * 差错查询
     *
     * @param req
     * @return
     */
    @RequestMapping("/list")
    public ServerResponse<ComRespBody<ErrQryRes>> query(HttpServletRequest req) {
        IDataset reqDs = DatasetService.getInstace().getDataset(req);
        int limit = reqDs.getInt("limit");
        int start = reqDs.getInt("start");
        String startTime = reqDs.getString("startTime");
        String endTime = reqDs.getString("endTime");
        String busiTp = reqDs.getString("busiTp");

        ErrQryReq errQryReq = new ErrQryReq();
        if (MLppUtils.dateCheck(startTime, endTime)) {
            String STR_TIME = startTime.substring(0, 4) + startTime.substring(5, 7) + startTime.substring(8);
            String END_TIME = endTime.substring(0, 4) + endTime.substring(5, 7) + endTime.substring(8);
            errQryReq.setSTR_DATE(STR_TIME);
            errQryReq.setEND_DATE(END_TIME);
        }
        String busiNo = reqDs.getString("busiNo");
        String stat = reqDs.getString("errStat");
        if (StringUtil.isEmpty(busiNo)) {
            busiNo = "";
            //非总行用户 根据机构层级查询业务编号
            List<BusiDo> busiDos = comQueryService.busiList(busiTp);
            //如果没有查询到对应的合作商户
            if (CollectionUtils.isEmpty(busiDos)) {
                throw new BaseException(SysErr.E_MESSAGE, "该机构没有对应的合作商户，请使用合作商户对应的开户机构或上级机构用户进行查看");
            }
            StringBuilder busiNos = new StringBuilder();
            for (BusiDo busiDo : busiDos) {
                busiNos.append(busiDo.getBusiNo()).append(";");
            }
            errQryReq.setBUSI_NO_LIST(busiNos.toString());
        }

            errQryReq.setBUSI_NO(busiNo);

            //stat为空时  AA-查询全部
            errQryReq.setSTAT(StringUtil.isNotBlank(stat) ? stat : "AA");
            ErrQryRes errQryRes = errQryService.getErrList(errQryReq, start, limit);
            int total = errQryRes.getTOT_NUM().intValue();
            ComRespBody<ErrQryRes> retData = new ComRespBody<ErrQryRes>(start, limit, errQryRes, total);
            return ServerResponse.createBySuccess("查询成功", retData);
        }







        /**
         * 查询差错流水详情
         *
         * @param req
         * @return
         */
        @RequestMapping("/info")
        public ServerResponse<BookList> info (HttpServletRequest req){
            IDataset reqDs = DatasetService.getInstace().getDataset(req);
            String origPlatDate = reqDs.getString("origPlatDate");
            String origPlatSeq = reqDs.getString("origPlatSeq");
            String busiNo = reqDs.getString("busiNo");
            MLppQryBookListReq mLppQryBookListReqDTO = new MLppQryBookListReq();
            mLppQryBookListReqDTO.setORIG_REQ_DATE(origPlatDate);
            mLppQryBookListReqDTO.setORIG_REQ_SEQ(origPlatSeq);
            mLppQryBookListReqDTO.setBUSI_NO(busiNo);
            BookList errWithBookRecord = mLppBookListQryService.getErrWithBookRecord(mLppQryBookListReqDTO);
            return ServerResponse.createBySuccess("成功", errWithBookRecord);
        }

        /**
         * 差错处理
         *
         * @param req
         * @param res
         */
        @RequestMapping("/manualHandle")
        public void errHandle (HttpServletRequest req, HttpServletResponse res){
            IDataset reqDs = DatasetService.getInstace().getDataset(req);
            String busiNo = reqDs.getString("busi_NO");
            String clrDate = reqDs.getString("clr_DATE");
            String origPlatSeq = reqDs.getString("orig_PLAT_SEQ");
            String origPlatDate = reqDs.getString("orig_PLAT_DATE");
            //1-人工已处理 2-冲正 3-补账
            String procTp = "1";
            ErrHandleReq errHandleReq = new ErrHandleReq();
            errHandleReq.setBUSI_NO(busiNo);
            errHandleReq.setCLR_DATE(clrDate);
            errHandleReq.setORIG_PLAT_DATE(origPlatDate);
            errHandleReq.setORIG_PLAT_SEQ(origPlatSeq);
            errHandleReq.setPROC_TP(procTp);
            IDataset resDs = errQryService.errHandle(errHandleReq);
            setResponseDataset(req, res, resDs, SysErr.E_SUCCESS, "交易成功");
        }
    }

