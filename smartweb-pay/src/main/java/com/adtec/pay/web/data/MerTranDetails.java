package com.adtec.pay.web.data;

import com.adtec.framework.common.util.ServerResponse;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.pay.dao.BusiUserDao;
import com.adtec.pay.dto.bookList.MLppQryBookListReq;
import com.adtec.pay.dto.bookList.MLppQryBookListRes;
import com.adtec.pay.entity.BusiUserDo;
import com.adtec.pay.service.MLppBookListQryService;
import com.adtec.pay.webResp.ComRespBody;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("${adminPath}/merTranDetails/data/")
public class MerTranDetails {

    @Autowired
    private MLppBookListQryService mlppBookListQryService;

    @RequestMapping("/refund")
    public ServerResponse<String> refund(HttpServletRequest req) {
        IDataset reqDs = DatasetService.getInstace().getDataset(req);
        return ServerResponse.createBySuccess("退款成功", "退款成功");
    }

    /**
     * 商户交易明细查询
     *
     * @param req
     * @return
     */
    @RequestMapping("/list")
    public ServerResponse<ComRespBody<MLppQryBookListRes>> query(HttpServletRequest req) {
        IDataset reqDs = DatasetService.getInstace().getDataset(req);
        int start = reqDs.getInt("start");
        int limit = reqDs.getInt("limit");
        String RFND_STAT = reqDs.getString("refundStat");
        // 默认为否 必须要有的字段
        String autoDeduct = reqDs.getString("autoFlag");
        //当自动标志为1-是，退费状态查询全部，因为批量明细表中没有退费状态字段，会导致报错
        if ("1".equals(autoDeduct)) {
            RFND_STAT = "";
        }
        String payNo = reqDs.getString("cstNo");
        String tranStat = reqDs.getString("txStat");
        MLppQryBookListReq qryListReq = new MLppQryBookListReq();
        //获取商户的业务编号
        User user = UserUtils.getUser();
        if (!"merchans".equals(user.getRoleList().get(0).getEngName())) {
            throw new BaseException(SysErr.E_MESSAGE, "商户专属，请管理员或开发人员使用生活缴费中的商户入账查询功能");
        }
        BusiUserDo busiUserDo = null;
        try {
            busiUserDo = BusiUserDao.getInstance().get(user.getId());
        } catch (Exception e) {
            throw new BaseException(SysErr.E_MESSAGE, "该用户不属于已合作商户，无法进行查询");
        }
        if (StringUtils.isEmpty(busiUserDo.getBusiId())) {
            throw new BaseException(SysErr.E_MESSAGE, "该用户不属于已合作商户，无法进行查询");
        }

        qryListReq.setBUSI_NO(busiUserDo.getBusiId());
        qryListReq.setAUTO_FLG(autoDeduct);
        //只有商户交易明细查询这个接口使用 必传
        qryListReq.setQRY_TP("1");
        qryListReq.setPAY_NO(payNo);
        qryListReq.setRFND_STAT(StringUtils.isBlank(RFND_STAT) ? "AA" : RFND_STAT);
        qryListReq.setTRAN_STAT(StringUtils.isBlank(tranStat) ? "AA" : tranStat);
        MLppQryBookListRes respDto = mlppBookListQryService.getBookList(reqDs, qryListReq, start, limit);
        int total = respDto.getTOT_NUM().intValue();
        ComRespBody<MLppQryBookListRes> retData = new ComRespBody<MLppQryBookListRes>(start, limit, respDto,
                total);
        return ServerResponse.createBySuccess("查询成功", retData);
    }

}
