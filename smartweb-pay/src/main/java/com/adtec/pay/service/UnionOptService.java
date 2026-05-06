
package com.adtec.pay.service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.pay.dao.UnionDao;
import com.adtec.pay.dto.NoReturnRes;
import com.adtec.pay.dto.union.AcctCheckReq;
import com.adtec.pay.dto.union.BatDisuburseReq;
import com.adtec.pay.dto.union.TotDataListRes;
import com.adtec.pay.entity.union.UnionExpense;
import com.adtec.pay.entity.union.UnionInst;
import com.adtec.pay.entity.union.UnionParam;
import com.adtec.pay.entity.union.UnionUser;
import com.adtec.pay.utils.MLppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UnionOptService {

    @Autowired
    private UnionDao unionDao;


/**
     * 新增工会缴费参数
     * @param req
     */

    public void paramAdd(UnionParam req) {
        unionDao.paramAdd(req);
    }


/**
     * 修改工会缴费参数
     * @param req
    */

    public void paramUpdate(UnionParam req) {
        unionDao.paramUpdate(req);
    }

/**
     * 删除工会缴费参数
     * @param req
    */

    public void paramDelete(UnionParam req) {
        unionDao.paramDelete(req);
    }


/**
     * 工会缴费参数个数统计
     * @param req
     * @return
    */

    public int countPara(UnionParam req) {
        return unionDao.countPara(req);
    }


/**
     * 获取工会缴费参数（分页）
     * @param start
     * @param limit
     * @return
    */

    public List<UnionParam> paraList(int start, int limit) {
        return unionDao.paraList(start,limit);
    }


/**
     * 报销审批表列表查询（分页）
     * @param req
     * @param strDate
     * @param endDate
     * @param start
     * @param limit
     * @return
    */

    public List<UnionExpense> apprList(UnionExpense req, String strDate, String endDate, int start, int limit) {
        return unionDao.apprList(req,strDate,endDate,start,limit);
    }



/**
     * 检查缴费记录是否已提交报销
     * @param req
     * @return
    */

    public int checkExpAppr(UnionExpense req) {
        return unionDao.checkExpAppr(req);
    }


/**
     * 根据条件查询审批表个数
     * @param req
     * @param strDate
     * @param endDate
     * @return
    */

    public int countAppr(UnionExpense req, String strDate, String endDate) {
        return unionDao.countAppr(req,strDate,endDate);
    }


/**
     * 新增工会报销
     * @param req
    */

    public void expApprAdd(UnionExpense req) {
       unionDao.expApprAdd(req);
    }


/**
     * 工会报销修改
     * @param req
    */

    public void expApprUpdate(UnionExpense req) {
        unionDao.expApprUpdate(req);
    }



/**
     * 批量出账
     * @param req
     * @return
    */

    public IDataset callBatDisburse(BatDisuburseReq req) {
        ReqDTO reqDto = new ReqDTO(req);
        String busiNo = req.getBUSI_NO();
        MLppUtils.setReqHead(busiNo, reqDto, "MLppBatDisburse");
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("MLPP_PARTID"), "MLppBatDisburse", reqDto, NoReturnRes.class, null);
        NoReturnRes res = new NoReturnRes();
        if (null != resDTO.getBODY()) {
            res = (NoReturnRes) resDTO.getBODY();
        }
        IDataset responseData = DatasetService.getInstace().getDataset(res, NoReturnRes.class);
        return responseData;
    }


/**
     * 校验账号是否合法
     * @param req
     * @return
    */

    public IDataset callAcctCheck(AcctCheckReq req) {
        ReqDTO reqDto = new ReqDTO(req);
        MLppUtils.setReqHead(reqDto, "MLppAcctCheck",0,0);
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("MLPP_PARTID"), "MLppAcctCheck", reqDto, NoReturnRes.class, null);
        NoReturnRes res = new NoReturnRes();
        if (null != resDTO.getBODY()) {
            res = (NoReturnRes) resDTO.getBODY();
        }
        IDataset responseData = DatasetService.getInstace().getDataset(res, NoReturnRes.class);
        return responseData;
    }


/**
     * 根据登录用户获取所属工会
     * @param userId
     * @return
    */

    public String getBusiNoByUserId(String userId) {
        return unionDao.getBusiNoByUserId(userId);
    }


/**
     * 根据条件统计人员个数
     * @param unionUser
     * @return
    */

    public int countApprUser(UnionUser unionUser) {
        return unionDao.countApprUser(unionUser);
    }


/**
     * 获取工会用户列表 （分页）
     * @param unionUser
     * @param start
     * @param limit
     * @return
    */

    public List<UnionUser> userList(UnionUser unionUser, int start, int limit) {
        return unionDao.userList(unionUser,start,limit);
    }


/**
     * 工会用户新增
     * @param req
    */

    public void unionUserAdd(UnionUser req) {
        unionDao.unionUserAdd(req);
    }

/**
     * 工会用户修改
     * @param req
    */

    public void unionUserUpdate(UnionUser req) {
        unionDao.unionUserUpdate(req);

    }

/**
     * 工会用户删除
     * @param req
    */

    public void unionUserDelete(UnionUser req) {
        unionDao.unionUserDelete(req);

    }


/**
     * 机构列表查询（分页）
     * @param unionInst
     * @param addr
     * @param start
     * @param limit
     * @return
    */

    public List<UnionInst> unionInstList(UnionInst unionInst, String addr, int start, int limit) {
        return unionDao.unionInstList(unionInst,addr,start,limit);
    }


/**
     * 获取机构列表个数
     * @param unionInst
     * @param addr
     * @return
    */

    public int countUnionInst(UnionInst unionInst, String addr) {
        return unionDao.countUnionInst(unionInst,addr);
    }




/**
     * 账户有效性校验
     *
     * @param acct
     * @return
    */


  /*  public boolean checkAcct(String acct) {
        CheckAcctReq req = new CheckAcctReq();
        req.setACCT(acct);
        ReqDTO reqDTO = new ReqDTO(req);
        MLppUtils.setReqHead(reqDTO, "MLppAcctConfm");
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        // 和Starring V6进行通信
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("MLPP_PARTID"), "MLppAcctConfm",
                reqDTO, CheckAcctRes.class, null);
        CheckAcctRes res = (CheckAcctRes) resDTO.getBODY();
        return res.getFLG().equals("Y");
    }*/




/**
     * 工会修改
     * @param reqDs
     * @return
    */

    public int unionInstUpdate(IDataset reqDs) {
        HashMap<String, Object> req = new HashMap<>();
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
        return unionDao.unionInstUpdate(req);
    }


/**
     * 构建工会新增请求体
     * @param reqDs
     * @param list
     * @return
    */

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


/**
     * 工会新增
     * @param reqDs
     * @return
    */

    public int unionInstAdd(IDataset reqDs) {
        List<String> list = unionDao.getMaxSer();
        Map<String, Object> req = buildReqBody(reqDs, list);
        return unionDao.unionInstAdd(req);
    }

/**
     * 工会删除
     * @param reqDs
     * @return
    */

    public void unionDelete(IDataset reqDs) {
        //todo 待定工会是否是商户的概念 如果是商户的概念 是否需要删除
    }

    /**
     * 工会汇总分页
     * @param reqBody
     * @return
     */
    public TotDataListRes totDataList(Map<String, String> reqBody) {
        return null;
    }
}

