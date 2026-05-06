package com.adtec.pay.web.data;

import com.adtec.framework.common.util.ServerResponse;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.pay.dto.clrrule.ClearRuleModifyReq;
import com.adtec.pay.dto.clrrule.ClearRuleQryRes;
import com.adtec.pay.service.ClrRuleService;
import com.adtec.pay.webResp.ComRespBody;
import com.adtec.sys.common.web.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("${adminPath}/clearRule/data/")
public class ClearRule extends BaseController {

    @Autowired
    private ClrRuleService clrRuleService;

    // 测试环境
    @RequestMapping("/list")
    public ServerResponse<ComRespBody<ClearRuleQryRes>> query(HttpServletRequest req) {
        IDataset reqDs = DatasetService.getInstace().getDataset(req);
        int start = reqDs.getInt("start");
        int limit = reqDs.getInt("limit");
        String busiNo = reqDs.getString("BUSI_NO");
        if(StringUtils.isEmpty(busiNo)){
            throw new BaseException(SysErr.E_MESSAGE, "该机构没有对应的合作商户，请使用合作商户对应的开户机构或上级机构用户进行查看");
        }
        ComRespBody<ClearRuleQryRes> body = clrRuleService.getClearRule(busiNo, start, limit);
        return ServerResponse.createBySuccess("查询成功", body);
    }

    @RequestMapping("/add")
    public void add(HttpServletRequest req, HttpServletResponse res) {
        String OPER_TP = "1";
        moddify(req, res, OPER_TP);

    }

    @RequestMapping("/update")
    public void update(HttpServletRequest req, HttpServletResponse res) {
        String OPER_TP = "3";
        moddify(req, res, OPER_TP);

    }

    @RequestMapping("/delete")
    public void delete(HttpServletRequest req, HttpServletResponse res) {
        IDataset reqDs = DatasetService.getInstace().getDataset(req);
        String OPER_TP = "2";
        moddify(req, res, OPER_TP);

    }


    public void moddify(HttpServletRequest req, HttpServletResponse res, String OPER_TP) {

        IDataset reqDs = DatasetService.getInstace().getDataset(req);
        String busi_no = reqDs.getString("BUSI_NO");
        String busi_Name = reqDs.getString("busiName");
        //操作类型
        String clr_tp = reqDs.getString("CLR_TP");
        String chk_tp = reqDs.getString("CHK_TP");
        String rfnd_tp = reqDs.getString("RFND_TP");
        String temp_acct = reqDs.getString("TEMP_ACCT");
        String temp_acct_name = reqDs.getString("TEMP_ACCT_NAME");
        String note_tp = reqDs.getString("NOTE_TP");
        String day_note_tp = reqDs.getString("DAY_NOTE_TP");
        ClearRuleModifyReq clearRuleModifyReqDTO = new ClearRuleModifyReq();
        clearRuleModifyReqDTO.setBUSI_NO(busi_no);
        clearRuleModifyReqDTO.setBUSI_NAME(busi_Name);
        clearRuleModifyReqDTO.setOPER_TP(OPER_TP);
        clearRuleModifyReqDTO.setCLR_TP(clr_tp);
        clearRuleModifyReqDTO.setCHK_TP(chk_tp);
        clearRuleModifyReqDTO.setRFND_TP(rfnd_tp);
        clearRuleModifyReqDTO.setTEMP_ACCT(temp_acct);
        clearRuleModifyReqDTO.setTEMP_ACCT_NAME(temp_acct_name);
        clearRuleModifyReqDTO.setNOTE_TP(note_tp);
        clearRuleModifyReqDTO.setDAY_NOTE_TP(day_note_tp);
        IDataset resDs = clrRuleService.modifyClearRule(req, res, clearRuleModifyReqDTO);
        setResponseDataset(req, res, resDs, SysErr.E_SUCCESS, "交易成功");
    }

}
