package com.adtec.pay.web.data;

import com.adtec.framework.common.util.ServerResponse;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.pay.dto.offline.MerInfo;
import com.adtec.pay.service.OfflineOptService;
import com.adtec.pay.webResp.ComRespBody;
import com.alibaba.fastjson.JSONObject;
import com.adtec.sys.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${adminPath}/school/register/data/")
public class SchoolRegisterOpt extends BaseController {

    @Autowired
    private OfflineOptService offlineOptService;

    @RequestMapping("/list")
    public ServerResponse<ComRespBody<List<MerInfo>>> list(HttpServletRequest request) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        int start = reqDs.getInt("start");
        int limit = 0 == reqDs.getInt("limit") ? 10 : reqDs.getInt("limit");
        start = (start - 1) * limit;
        String busiNo = reqDs.getString("busiNo");
        String busiName = reqDs.getString("busiName");
        String officeId = reqDs.getString("officeId");
        Map<String, String> reqBody = new HashMap<>();
        reqBody.put("busiNo", busiNo);
        reqBody.put("busiName", busiName);
        reqBody.put("officeId", officeId);
        int total = offlineOptService.countMerList(reqBody);
        List<MerInfo> list = offlineOptService.getMerList(reqBody, start, limit);
        ComRespBody<List<MerInfo>> retData = new ComRespBody<>(start, limit, list, total);
        return ServerResponse.createBySuccess("查询成功", retData);
    }

    @RequestMapping("/modify")
    public ServerResponse<JSONObject> modify(HttpServletRequest request) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        int start = reqDs.getInt("start");
        int limit = 0 == reqDs.getInt("limit") ? 10 : reqDs.getInt("limit");
        String operStat = reqDs.getString("operStat");
        String acct = reqDs.getString("payAcct");
        boolean flg = offlineOptService.checkAcct(acct);
        if (!flg) {
            if ("1".equals(operStat)) {
                return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "清算账户不存在,新增失败");
            } else {
                return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "清算账户不存在,修改失败");
            }
        }
        int rec;
        try {
            rec = offlineOptService.modifyMerchan(reqDs, start, limit);
        } catch (Exception e) {
            if (e.getMessage().contains("唯一性约束")) {
                return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "商户号已存在,新增失败");
            }
            if ("1".equals(operStat)) {
                return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "新增商户失败" + e.getMessage());
            } else {
                return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "修改商户失败" + e.getMessage());
            }
        }
        if (rec == 0) {
            if ("1".equals(operStat)) {
                return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "新增商户失败");
            } else {
                return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "修改商户失败");
            }
        }
        if ("1".equals(operStat)) {
            return ServerResponse.createBySuccessMessage("新增商户成功");
        } else {
            return ServerResponse.createBySuccessMessage("修改商户成功");
        }
    }

    @RequestMapping("/delete")
    public ServerResponse<JSONObject> delete(HttpServletRequest request) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String busiNo = reqDs.getString("busiNo");
        try {
            int rec = offlineOptService.offShelfMerchan(busiNo);
            if (rec < 1) {
                return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "删除失败,未找到对应商户");
            }
        } catch (Exception e) {
            return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "删除失败：" + e.getMessage());
        }
        return ServerResponse.createBySuccessMessage("删除成功");
    }

    @RequestMapping("/countDtl")
    public ServerResponse<ComRespBody<Integer>> countDtl(HttpServletRequest request) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String busiNo = reqDs.getString("busiNo");
        int rec = offlineOptService.countDtl(busiNo);
        ComRespBody<Integer> retData = new ComRespBody<>(0, 10, rec, 10);
        return ServerResponse.createBySuccess("查询成功", retData);
    }

    @RequestMapping("/export")
    public void export(HttpServletRequest request, HttpServletResponse response) throws Exception {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String busiNo = reqDs.getString("busiNo");
        String busiName = reqDs.getString("busiName");
        String officeId = reqDs.getString("officeId");

        Map<String, String> reqBody = new HashMap<>();
        reqBody.put("busiNo", busiNo);
        reqBody.put("busiName", busiName);
        reqBody.put("officeId", officeId);

        List<MerInfo> list = offlineOptService.getMerList(reqBody, 0, 10000);
        for (MerInfo merInfo : list) {
            String payInfo = merInfo.getPAY_INFO();
            String[] split = payInfo.split("\\|");
            merInfo.setPAY_INFO("Y".equals(split[0]) ? "是" : "否");
            merInfo.setCLR_TP("Y".equals(split[0]) ? "T1清算" : "D1清算");
            merInfo.setOPEN_STAT("Y".equals(merInfo.getOPEN_STAT()) ? "已上架" : "已下架");
        }
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = java.net.URLEncoder.encode("学校商户信息" + System.currentTimeMillis(), "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        com.alibaba.excel.EasyExcel.write(response.getOutputStream(), MerInfo.class).sheet("学校商户信息").doWrite(list);
    }
}
