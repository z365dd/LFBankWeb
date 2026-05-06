package com.adtec.pay.web.data;

import com.adtec.framework.common.util.ServerResponse;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.pay.dto.offline.MerInfo;
import com.adtec.pay.entity.BusiDo;
import com.adtec.pay.entity.ykt.YKTPara;
import com.adtec.pay.service.ComQueryService;
import com.adtec.pay.service.OfflineOptService;
import com.adtec.pay.service.YKTOptService;
import com.adtec.pay.utils.Constant;
import com.adtec.pay.webResp.ComRespBody;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${adminPath}/ykt/data/")
public class YKTOpt {
    @Autowired
    private YKTOptService yktService;

    @Autowired
    private OfflineOptService offlineOptService;

    @Autowired
    private ComQueryService comQueryService;

    //增加
    private static String OPT_ADD = "1";
    //修改
    private static String OPT_UPT = "2";
    //一卡通商户类型
    private static String BUSI_YKT = "20";


    /**
     * 一卡通参数列表查询
     * @param request
     * @return
     */
    @RequestMapping("/para_list")
    public ServerResponse<ComRespBody<List<YKTPara>>> paraList(HttpServletRequest request) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        int start = reqDs.getInt("start");
        int limit = reqDs.getInt("limit");
        String busiNo = reqDs.getString("busiNo");
        start = start == 0 ? start : (start - 1) * limit;
        YKTPara req = new YKTPara();
        req.setBUSI_NO(busiNo);
        int total = yktService.countPara(req);
        List<YKTPara> list = yktService.paraList(req,start, limit);
        ComRespBody<List<YKTPara>> retData = new ComRespBody<List<YKTPara>>(start, limit, list, total);
        return ServerResponse.createBySuccess("查询成功", retData);
    }


    /**
     * 一卡通参数更新
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/para_modify")
    public ServerResponse<String> paraModify(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String busiNo = reqDs.getString("busiNo");
        String busiName = reqDs.getString("busiName");
        String tranAmt = reqDs.getString("tranAmt");
        String dayAmt = reqDs.getString("dayAmt");
        String operStat = reqDs.getString("operStat");
        YKTPara req = new YKTPara();
        req.setBUSI_NO(busiNo);
        req.setBUSI_NAME(busiName);
        req.setTRAN_AMT(Double.valueOf(tranAmt));
        req.setDAY_AMT(Double.valueOf(dayAmt));

        if (OPT_ADD.equals(operStat)) {
            if(Double.compare(Double.parseDouble(tranAmt),Double.parseDouble(dayAmt)) > 0){
                return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "日累计限额不能小于单笔限额!");
            }
            int rec = yktService.countPara(req);
            if (rec > 0) {
                return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "该学校限额参数已配置,不可重复添加!");
            }
            yktService.paramAdd(req);
            return ServerResponse.createBySuccessMessage("新增成功!");
        } else if (OPT_UPT.equals(operStat)) {
            if(Double.compare(Double.parseDouble(tranAmt),Double.parseDouble(dayAmt)) > 0){
                return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "日累计限额不能小于单笔限额!");
            }
            yktService.paramUpdate(req);
            return ServerResponse.createBySuccessMessage("修改成功!");
        } else {
            yktService.paramDelete(req);
            return ServerResponse.createBySuccessMessage("删除成功!");
        }

    }


    /**
     * 获取商户列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/merQry")
    public ServerResponse<ComRespBody<List<MerInfo>>> merQry(HttpServletRequest request) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        int start = reqDs.getInt("start");
        int limit = 0 == reqDs.getInt("limit") ? 10 : reqDs.getInt("limit");
        start = (start - 1) * limit;
        String busiNo = reqDs.getString("busiNo");
        String busiName = reqDs.getString("busiName");
        String officeId = reqDs.getString("officeId");
        Map<String, String> reqBody = new HashMap<>();

        List<BusiDo> busiDos = comQueryService.busiList(BUSI_YKT);
        if (CollectionUtils.isEmpty(busiDos)) {
            throw new BaseException(SysErr.E_MESSAGE, "该机构没有对应的合作商户，请使用合作商户对应的开户机构或上级机构用户进行查看");
        }
        StringBuilder busiNos = new StringBuilder();
        for (BusiDo busiDo : busiDos) {
            busiNos.append(busiDo.getBusiNo()).append(";");
        }
        reqBody.put("busiNo",busiNo);
        reqBody.put("busiNos", busiNos.toString());
        reqBody.put("busiName", busiName);
        reqBody.put("officeId", officeId);
        reqBody.put("busiTp", Constant.SHORT_RMRK_YKT);
        int total = yktService.countMerList(reqBody);
        List<MerInfo> list = yktService.getMerList(reqBody, start, limit);
        ComRespBody<List<MerInfo>> retData = new ComRespBody<>(start, limit, list, total);
        return ServerResponse.createBySuccess("查询成功", retData);
    }
	
    /**
     *
     * 商户信息导出
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("exportMerData")
    private void exportMerData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String busiNo = reqDs.getString("busiNo");
        String busiName = reqDs.getString("busiName");
        String officeId = reqDs.getString("officeId");

        Map<String, String> reqBody = new HashMap<>();
        if(!StringUtils.isEmpty(busiNo)){
            reqBody.put("busiNo", busiNo);
        }
        reqBody.put("busiName", busiName);
        reqBody.put("officeId", officeId);
        reqBody.put("busiTp", BUSI_YKT);
        List<MerInfo> list = yktService.getMerList(reqBody, 0, 10000);
        for (MerInfo merInfo : list) {
            String payInfo = merInfo.getPAY_INFO();
            String[] split = payInfo.split("\\|");
            merInfo.setPAY_INFO(split[0].equals("Y") ? "是" :"否");
            merInfo.setCLR_TP(split[0].equals("Y") ? "T1清算" :"D1清算");
            merInfo.setOPEN_STAT(merInfo.getOPEN_STAT().equals("Y") ? "已上架" : "已下架");
        }
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("商户信息" + System.currentTimeMillis(), "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(),MerInfo.class).sheet("商户信息").doWrite(list);

    }


    /**
     * 商户修改
     *
     * @param request
     * @return
     */
    @RequestMapping("/merModify")
    public ServerResponse<JSONObject> merModify(HttpServletRequest request) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        int start = reqDs.getInt("start");
        int limit = 0 == reqDs.getInt("limit") ? 10 : reqDs.getInt("limit");
        //操作种类  1-新增 2-修改
        String operStat = reqDs.getString("operStat");
        String acct = reqDs.getString("payAcct");
        //判断输入的清算账户是否合法  如果合法返回true
        boolean flg = offlineOptService.checkAcct(acct);
        if(!flg){
            if (operStat.equals("1")) {
                return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "清算账户不存在,新增失败");
            }else{
                return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "清算账户不存在,修改失败");
            }
        }
//        boolean rightAcctFlg = offlineOptService.validateAcct(payAcct);
        int rec = 0;
        try{
            rec = yktService.modifyMerchan(reqDs);
        }catch (Exception e){
            if(e.getMessage().contains("唯一性约束")){
                return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "商户号已存在,新增失败");
            }
            if(operStat.equals("1")){
                return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "新增商户失败" + e.getMessage());
            }else{
                return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "修改商户失败" + e.getMessage());
            }
        }
        if (rec == 0) {
            if(operStat.equals("1")){
                return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE,"新增商户失败");
            }else{
                return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE,"修改商户失败");
            }
        }
        if(operStat.equals("1")){
            return ServerResponse.createBySuccessMessage("新增商户成功");
        }else{
            return ServerResponse.createBySuccessMessage("修改商户成功");
        }
    }





}
