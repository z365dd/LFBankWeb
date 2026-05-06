package com.adtec.pay.service;

import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.pay.dao.OfflineDao;
import com.adtec.pay.dao.YKTDao;
import com.adtec.pay.dto.offline.MerInfo;
import com.adtec.pay.entity.ykt.YKTPara;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class YKTOptService {


    @Autowired
    private YKTDao yktDao;

    @Autowired
    private OfflineDao offlineDao;

    /**
     * 计算符合条件的一卡通配置参数数量
     * @param req
     * @return
     */
    public int countPara(YKTPara req) {
        return yktDao.countPara(req);
    }

    /**
     * 分页查询
     * @param req
     * @param start
     * @param limit
     * @return
     */
    public List<YKTPara> paraList(YKTPara req, int start, int limit) {
        return yktDao.paraList(req,start,limit);
    }

    /**
     * 新增一卡通配置
     * @param req
     */
    public void paramAdd(YKTPara req) {
        yktDao.paramAdd(req);
    }

    /**
     * 修改一卡通配置
     * @param req
     */
    public void paramUpdate(YKTPara req) {
        yktDao.paramUpdate(req);
    }

    /**
     * 删除一卡通配置
     * @param req
     */
    public void paramDelete(YKTPara req) {
        yktDao.paramDelete(req);
    }

    /**
     * 查询一卡通商户个数
     * @param reqBody
     * @return
     */
    public int countMerList(Map<String, String> reqBody) {
        return yktDao.countMerList(reqBody);
    }

    /**
     * 根据商户类型查询商户列表
     * @param reqBody
     * @param start
     * @param limit
     * @return
     */
    public List<MerInfo> getMerList(Map<String, String> reqBody, int start, int limit) {
        return yktDao.getMerList(reqBody,start,limit);    }

    /**
     * 商户信息新增、修改
     * @param reqDs
     * @return
     */
    public int modifyMerchan(IDataset reqDs) {
        List<String> list = yktDao.getMaxSer();
        Map<String, Object> req = buildReqBody(reqDs, list);
        return yktDao.modifyMerchan(req);
    }

    private Map<String, Object> buildReqBody(IDataset reqDs, List<String> list) {

        HashMap<String, Object> req = new HashMap<>();
        String item = list.get(0);
        long entrNo = Long.parseLong(item);
        entrNo++;
        String item1 = list.get(1);
        long busiPara1 = Long.parseLong(item1);
        busiPara1++;
        long busiPara2 = Long.parseLong("2" + String.valueOf(busiPara1).substring(1));
        long busiPara3 = Long.parseLong("3" + String.valueOf(busiPara1).substring(1));
        long busiPara4 = Long.parseLong("4" + String.valueOf(busiPara1).substring(1));

        req.put("entrNo", String.valueOf(entrNo));
        req.put("busiPara1", String.valueOf(busiPara1));
        req.put("busiPara2", String.valueOf(busiPara2));
        req.put("busiPara3", String.valueOf(busiPara3));
        req.put("busiPara4", String.valueOf(busiPara4));


        String busiNo = reqDs.getString("busiNo");
        String busiName = reqDs.getString("busiName");
        String officeId = reqDs.getString("officeId");
        officeId = officeId.equals("1") ? "600001" : officeId;
        String officeName = reqDs.getString("officeName");
        String payAcct = reqDs.getString("payAcct");
        String payAcctName = reqDs.getString("payAcctName");
        // 是否为T1清算 | 是否支持拆分缴费
        String payInfo = reqDs.getString("clrCycle") + "|" + reqDs.getString("sepaFlg");
        String phoneNo = reqDs.getString("phoneNo");
        String name = reqDs.getString("name");
        String openStat = reqDs.getString("openStat");
        //操作状态 1-新增  2-修改
        String operStat = reqDs.getString("operStat");
        String addr = reqDs.getString("provinceName") + "-"
                + reqDs.getString("city") + "-"
                + reqDs.getString("detailedAddress");


        req.put("busiNo", busiNo);
        req.put("busiName", busiName);
        req.put("brchId", officeId);
        req.put("brchName", officeName);
        req.put("acct", payAcct);
        req.put("acctName", payAcctName);
        req.put("payInfo", payInfo);
        req.put("phoneNo", phoneNo);
        req.put("name", name);
        req.put("openStat", openStat);
        req.put("operStat", operStat);
        req.put("addr", addr);

        return req;
    }

}
