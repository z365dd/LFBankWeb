package com.adtec.pay.web.data;

import com.adtec.framework.common.util.ServerResponse;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.pay.dao.BusiUserDao;
import com.adtec.pay.dto.recorded.RecQryReq;
import com.adtec.pay.dto.recorded.RecQryRes;
import com.adtec.pay.dto.recorded.RecSumQryReq;
import com.adtec.pay.dto.recorded.RecSumQryRes;
import com.adtec.pay.entity.BusiDo;
import com.adtec.pay.entity.BusiUserDo;
import com.adtec.pay.service.ComQueryService;
import com.adtec.pay.service.RecQryService;
import com.adtec.pay.utils.Constant;
import com.adtec.pay.webResp.ComRespBody;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.utils.UserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 商户入账查询
 */
@RestController
@RequestMapping("${adminPath}/merAccount/data/")
public class MerAccount {

    @Autowired
    private RecQryService recQryService;

    @Autowired
    private ComQueryService comQueryService;
    /*
     * String startTime = reqDs.getString("startTime");
     * String endTime = reqDs.getString("endTime");
     * LocalDate startLocalDate = null;
     * LocalDate endLocalDate;
     * try{
     *     startLocalDate = LocalDate.parse(startTime, ComUtils.yyyyMMdd);
     *     endLocalDate = LocalDate.parse(endTime, ComUtils.yyyyMMdd);
     * } catch(Exception e){
     *     throw new BaseException(SysErr.E_MESSAGE, "日期格式错误");
     * }if(endLocalDate.compareTo(startLocalDate) < 0){
     *     throw new BaseException(SysErr.E_MESSAGE, "结束日期 必须大于或等于 开始日期");
     * }
     */

    /**
     * 目前根据有无手续费进行入账的查询
     *
     * @return
     */
    @RequestMapping("/list")
    public ServerResponse<ComRespBody<RecSumQryRes>> callChkQrySum(HttpServletRequest request) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        int start = reqDs.getInt("start");
        int limit = reqDs.getInt("limit");
        //业务编号
        String busiNo = reqDs.getString("busiNo");
        //判空处理 如果busiNo为空
        //busiNo为空有两种情况
        // 一种是 从合作商户-商户入账查询进来的  可能是 开发人员、管理员或者商户
        // 还有一种可能是从生活缴费-商户入账查询进入的 这种是没有合作商户的行方用户
        if (StringUtils.isEmpty(busiNo)) {
            User user = UserUtils.getUser();
            //如果是开发或者管理员 总行下的可以查询到合作商户
            if ("manager".equals(user.getRoleTp()) || "tech".equals(user.getRoleTp())) {
                throw new BaseException(SysErr.E_MESSAGE, "商户专属，请管理员或开发人员使用生活缴费中的商户入账查询功能");
            }
            if ("teller".equals(user.getRoleTp())) {
                throw new BaseException(SysErr.E_MESSAGE, "该机构没有对应的合作商户，请使用合作商户对应的开户机构或上级机构用户进行查看");
            }
            if (!"manager".equals(user.getRoleTp()) && !"tech".equals(user.getRoleTp())
                    && !"teller".equals(user.getRoleTp())
                    && ("busi".equals(user.getRoleTp()) && !"merchans".equals(user.getRoleList().get(0).getEngName()))) {
                throw new BaseException(SysErr.E_MESSAGE, "该用户不属于商户也不属于合作商户的归属机构，无法进行查询");
            }
            BusiUserDo busiUserDo = null;
            try {
                busiUserDo = BusiUserDao.getInstance().get(user.getId());
            } catch (Exception e) {
                throw new BaseException(SysErr.E_MESSAGE, "该用户不属于已合作商户，无法进行查询");
            }
            busiNo = busiUserDo.getBusiId();
            if (StringUtils.isEmpty(busiNo)) {
                throw new BaseException(SysErr.E_MESSAGE, "该用户不属于已合作商户，无法进行查询");
            }
        }
        RecSumQryReq recSumQryReq = new RecSumQryReq();
        recSumQryReq.setBUSI_NO(busiNo);
        RecSumQryRes recSumQryRes = recQryService.getTotList(reqDs, recSumQryReq, start, limit);
        int total = recSumQryRes.getREC_NUM().intValue();
        ComRespBody<RecSumQryRes> retData = new ComRespBody<RecSumQryRes>(start, limit, recSumQryRes, total);
        return ServerResponse.createBySuccess("查询成功", retData);
    }


    /**
     * 非联机商户入账查询
     *
     * @return
     */
    @RequestMapping("/offList")
    public ServerResponse<ComRespBody<RecSumQryRes>> callOffChkQrySum(HttpServletRequest request) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        int start = reqDs.getInt("start");
        int limit = reqDs.getInt("limit");
        //业务编号
        String busiNo = reqDs.getString("busiNo");
        RecSumQryReq recSumQryReq = new RecSumQryReq();
        //判空处理 如果busiNo为空 查询当前登录用户下的所有机构
        if (StringUtils.isEmpty(busiNo)) {
            //根据机构层级查询业务编号
            List<BusiDo> busiDos = comQueryService.busiList(Constant.BUSI_TP_OFFLINE);
            //如果没有查询到对应的合作商户
            if (CollectionUtils.isEmpty(busiDos)) {
                throw new BaseException(SysErr.E_MESSAGE, "该机构没有对应的合作商户，请使用合作商户对应的开户机构或上级机构用户进行查看");
            }
            StringBuilder busiNos = new StringBuilder();
            for (BusiDo busiDo : busiDos) {
                busiNos.append(busiDo.getBusiNo()).append(";");
            }
            recSumQryReq.setBUSI_NO_LIST(busiNos.toString());
        }

        recSumQryReq.setBUSI_NO(busiNo);
        RecSumQryRes recSumQryRes = recQryService.getTotList(reqDs, recSumQryReq, start, limit);
        int total = recSumQryRes.getREC_NUM().intValue();
        ComRespBody<RecSumQryRes> retData = new ComRespBody<RecSumQryRes>(start, limit, recSumQryRes, total);
        return ServerResponse.createBySuccess("查询成功", retData);
    }



    /**
     * 非联机商户入账查询
     *
     * @return
     */
    @RequestMapping("/yktList")
    public ServerResponse<ComRespBody<RecSumQryRes>> callYktChkQrySum(HttpServletRequest request) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        int start = reqDs.getInt("start");
        int limit = reqDs.getInt("limit");
        //业务编号
        String busiNo = reqDs.getString("busiNo");
        RecSumQryReq recSumQryReq = new RecSumQryReq();
        //判空处理 如果busiNo为空 查询当前登录用户下的所有机构
        if (StringUtils.isEmpty(busiNo)) {
            //根据机构层级查询业务编号
            List<BusiDo> busiDos = comQueryService.busiList(Constant.SHORT_RMRK_YKT);
            //如果没有查询到对应的合作商户
            if (CollectionUtils.isEmpty(busiDos)) {
                throw new BaseException(SysErr.E_MESSAGE, "该机构没有对应的合作商户，请使用合作商户对应的开户机构或上级机构用户进行查看");
            }
            StringBuilder busiNos = new StringBuilder();
            for (BusiDo busiDo : busiDos) {
                busiNos.append(busiDo.getBusiNo()).append(";");
            }
            recSumQryReq.setBUSI_NO_LIST(busiNos.toString());
        }

        recSumQryReq.setBUSI_NO(busiNo);
        RecSumQryRes recSumQryRes = recQryService.getTotList(reqDs, recSumQryReq, start, limit);
        int total = recSumQryRes.getREC_NUM().intValue();
        ComRespBody<RecSumQryRes> retData = new ComRespBody<RecSumQryRes>(start, limit, recSumQryRes, total);
        return ServerResponse.createBySuccess("查询成功", retData);
    }

    /**
     * 回单打印前校验
     *
     * @return
     */
    @RequestMapping("/checkPrint")
    public ServerResponse<String> isPrintable(HttpServletRequest request) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        //业务编号
        String busiNo = reqDs.getString("busiNo");
        String strDate = reqDs.getString("strDate");
        String endDate = reqDs.getString("endDate");
        int num = recQryService.checkPrint(busiNo, strDate, endDate);
        if (num > 0) {
            throw new BaseException(SysErr.E_MESSAGE, "所选时间段包含尚未清算日期,不可打印");
        }
        return ServerResponse.createBySuccessMessage("查询成功");
    }

    @RequestMapping("/info")
    public ServerResponse<ComRespBody<RecQryRes>> getRecList(HttpServletRequest request) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        int start = reqDs.getInt("start");
        int limit = reqDs.getInt("limit");
        //获取序号
        String busiNo = reqDs.getString("busiNo");
        Long ser = reqDs.getLong("ser");
        String clrDate = reqDs.getString("clrDate");
        //回单打印传值 商户入账查询不传值
        String chnlNo = reqDs.getString("chnlNo");
        RecQryReq recQryReq = new RecQryReq();
        recQryReq.setBUSI_NO(busiNo);
        recQryReq.setSER(ser);
        recQryReq.setCLR_DATE(clrDate);
        recQryReq.setCHNL_NO(chnlNo);
        //入账明细 type为0  回单打印为1
        recQryReq.setType("0");
        RecQryRes recQryRes = recQryService.getRecListByPayType(recQryReq, start, limit);
        int total = recQryRes.getREC_NUM().intValue();
        if (0 == total) {
            throw new BaseException(SysErr.E_MESSAGE, "没有匹配的数据！");
        }
        ComRespBody<RecQryRes> retData = new ComRespBody<RecQryRes>(start, limit, recQryRes, total);
        return ServerResponse.createBySuccess("查询成功", retData);
    }

}
