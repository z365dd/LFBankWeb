package com.adtec.pay.web.data;

import com.adtec.framework.common.util.ServerResponse;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.framework.json.JSONObject;
import com.adtec.pay.entity.BusiDo;
import com.adtec.pay.entity.CtrlTParaChnlDO;
import com.adtec.pay.entity.OfficeDo;
import com.adtec.pay.service.ComQueryService;
import com.adtec.pay.utils.ComUtils;
import com.adtec.pay.utils.StrUtils;
import com.adtec.sys.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${adminPath}/comQuery/data/")
public class ComQuery {

    private ComQueryService comQueryService = new ComQueryService();

    @RequestMapping("/busiList")
    public ServerResponse<List<BusiDo>> busiList(String busiTp) {
        List<BusiDo> list = comQueryService.busiList(busiTp);
        return ServerResponse.createBySuccess("查询成功", list);
    }

    @RequestMapping("/chnlList")
    public ServerResponse<List<CtrlTParaChnlDO>> chnlList() {
        List<CtrlTParaChnlDO> list = comQueryService.chnlList();
        return ServerResponse.createBySuccess("查询成功", list);
    }

    @RequestMapping("/tranStat")
    public ServerResponse<List<Map<String, String>>> tranStatList() {
        List<Map<String, String>> list = ComUtils.TranStat.list();
        return ServerResponse.createBySuccess("查询成功", list);
    }

    @RequestMapping("/officeList")
    public ServerResponse<List<OfficeDo>> officeList() {
        List<OfficeDo> list = comQueryService.officeList();
        return ServerResponse.createBySuccess("查询成功", list);
    }

    /**
     * 获取默认查询日期范围
     *
     * @param request 请求参数
     * @return 当天和前一天的yyyy-MM-dd格式的日期
     */
    @RequestMapping("/getBeforeDate")
    public ServerResponse<Map<String, Object>> getBeforeDate(HttpServletRequest request) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String baseDate = reqDs.getString("baseDate");
        String offset = reqDs.getString("offset");
        LocalDate localDate;
        try {
            //默认为当天
            localDate = StringUtils.isBlank(baseDate) ? LocalDate.now() : LocalDate.parse(baseDate, ComUtils.yyyyMMdd);
        } catch (Exception e) {
            return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "时间格式不正确");
        }
        LocalDate offsetDate;
        try {
            offsetDate = localDate.plusDays(Long.parseLong(offset));
        } catch (Exception e) {
            return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "偏移量格式不正确");
        }
        JSONObject js = new JSONObject();
        //日期格式转换  yyyy-MM-dd
        js.put("curDate", localDate.format(ComUtils.yyyyMMdd));
        js.put("offset", offset);
        js.put("offsetDate", offsetDate.format(ComUtils.yyyyMMdd));
        return ServerResponse.createBySuccess("查询成功", js.toMap());
    }


    /**
     * 获取登录用户信息
     *
     * @return 登录用户用户id跟机构id
     */
    @RequestMapping("/getLoginUserInfo")
    public ServerResponse<Map<String, Object>> getLoginUserInfo(HttpServletRequest request) {

        JSONObject rs = new JSONObject();
        String id = UserUtils.getUser().getId();
        String brch = UserUtils.getUser().getOffice().getId();
        rs.put("id", id);
        rs.put("brch", brch);
        return ServerResponse.createBySuccess("查询成功", rs.toMap());
    }

    @RequestMapping("/sendSms")
    public ServerResponse<Map<String, String>> sendSms() {
        String phoneNo = UserUtils.getUser().getPhoneNo();
        if (StringUtils.isBlank(phoneNo)) {
            return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "该用户没有配置手机号，无法发送短信");
        }
        Map<String, String> map = new HashMap<>();
        map.put("phoneNo", StrUtils.strAddMask(phoneNo, 3, 4));
        map.put("key", "1234567");
        return ServerResponse.createBySuccess("查询成功", map);
    }


    /**
     * 获取渠道号以及渠道名称
     *
     * @return 渠道号以及渠道名称
     */
    public Map<String, String> chnlMap() {
        List<CtrlTParaChnlDO> chnlDos = comQueryService.chnlList();
        Map<String, String> map = chnlDos.stream().collect(Collectors.toMap(CtrlTParaChnlDO::getChnlNo, CtrlTParaChnlDO::getChnlName));
        return map;
    }

    /**
     * 获取机构号以及机构名称
     *
     * @return 机构号以及机构名称
     */
    public Map<String, String> officeMap() {
        List<OfficeDo> list = comQueryService.officeList();
        Map<String, String> map = list.stream().collect(Collectors.toMap(OfficeDo::getId, OfficeDo::getName));
        return map;
    }



}
