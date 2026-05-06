package com.adtec.pay.web.data;

import com.adtec.framework.common.util.ServerResponse;
import com.adtec.framework.common.util.StringUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.pay.entity.BusiDo;
import com.adtec.pay.entity.LimitAmtDO;
import com.adtec.pay.service.ComQueryService;
import com.adtec.pay.utils.Constant;
import com.adtec.pay.webResp.ComRespBody;
import com.adtec.sys.modules.sys.utils.UserUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商户限额管理
 */
@RestController
@RequestMapping("${adminPath}/limitAmt/data/")
public class LimitAmtOpt {

    @Autowired
    private ComQueryService comQueryService;
    /**
     * 获取限额商户列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/list")
    public ServerResponse<ComRespBody<List<LimitAmtDO>>> merQry(HttpServletRequest request) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        int start = reqDs.getInt("start");
        int limit = 0 == reqDs.getInt("limit") ? 10 : reqDs.getInt("limit");
        start = (start - 1) * limit;
        String busiNo = reqDs.getString("busiNo");
        List<String> busiNoList = new ArrayList<>();
        if(StringUtil.isEmpty(busiNo)){
            String parentId = UserUtils.getUser().getOffice().getParentId();
            //如果是总行用户 查询全部
            if(parentId.equals(Constant.HEAD_BANK)){
                busiNo = "";
            }else{
                busiNo = "";
                //非总行用户 根据机构层级查询业务编号
                List<BusiDo> busiDos = comQueryService.busiList("");
                //如果没有查询到对应的合作商户
                if(CollectionUtils.isEmpty(busiDos)){
                    throw new BaseException(SysErr.E_MESSAGE, "该机构没有对应的合作商户，请使用合作商户对应的开户机构或上级机构用户进行查看");
                }
                for (BusiDo busiDo : busiDos) {
                    busiNoList.add(busiDo.getBusiNo());
                }
            }
        }
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("busiNo", busiNo);
        reqBody.put("busiNoList", busiNoList);
        int total = comQueryService.countLimAmtList(reqBody);
        List<LimitAmtDO> list = comQueryService.limitAmtList(reqBody, start, limit);
        Map<String, Object> res = new HashMap<>();
        res.put("total", total);
        res.put("list", list);
        ComRespBody<List<LimitAmtDO>> retData = new ComRespBody<>(start, limit, list, total);
        return ServerResponse.createBySuccess("查询成功", retData);
    }


    /**
     * 获取限额商户列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/modify")
    public  ServerResponse<String>  add(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String busiNo = reqDs.getString("busiNo");
        String busiName = reqDs.getString("busiName");
        double lmtAmt = reqDs.getDouble("lmtAmt");
        double dayAmt = reqDs.getDouble("dayAmt");
        String operTp = reqDs.getString("operTp");
        HashMap<String, Object> reqBody = new HashMap<>();
        reqBody.put("busiNo", busiNo);
        reqBody.put("busiName", busiName);
        reqBody.put("lmtAmt", lmtAmt);
        reqBody.put("dayAmt", dayAmt);
        //如果是新增
        if("add".equals(operTp)){
            int isExist = comQueryService.countLimAmtList(reqBody);
            if(isExist < 1){
                comQueryService.limitAmtAdd(reqBody);
            }else{
                return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "该限额商户已存在,不可重复添加!");
            }
            return ServerResponse.createBySuccessMessage("新增成功!");
        }else{
            comQueryService.limitAmtModify(reqBody);
            return ServerResponse.createBySuccessMessage("修改成功!");
        }
    }

}
