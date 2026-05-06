package com.adtec.pay.service;

import com.adtec.pay.dao.BusiDao;
import com.adtec.pay.dao.ChnlDao;
import com.adtec.pay.dao.ComDao;
import com.adtec.pay.dao.OfficeDao;
import com.adtec.pay.entity.BusiDo;
import com.adtec.pay.entity.CtrlTParaChnlDO;
import com.adtec.pay.entity.LimitAmtDO;
import com.adtec.pay.entity.OfficeDo;
import com.adtec.pay.web.data.MerRegister;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ComQueryService {

    public static final String HEAD_BANK = "0";
    /**
     * 获取对应业务类型的业务编号和业务名称  00-联网缴费   01-非联网缴费 20-一卡通业务
     *
     * @return 所有业务编号和业务名称
     */
    public List<BusiDo> busiList(String busiTp) {
        BusiDao busiDao = BusiDao.getInstance();
        //获取当前登录用户
        User user = UserUtils.getUser();
        //获取上级机构号 parentId=0:总行;parentId=1:分行;其他为支行
        String parentId = user.getOffice().getParentId();
        //获取所有所属机构的唯一id
        String brchCode = user.getOffice().getBrchCode();
        //商户用户默认返回创建商户时关联的商户号
        if(MerRegister.MER_TYPE_BRCH.equals(brchCode)){
            String id = user.getId();
            return busiDao.getMerRelateBusi(id);
        }
        //如是总行 直接查询业务表 获取所有数据
        if (HEAD_BANK.equals(parentId)) {
            return busiDao.getAllBusi(busiTp);
        } else {
            //如果不是总行 根据获得的机构号进行查询
            return busiDao.getBusi(brchCode, busiTp);
        }
    }

    /**
     * 获取渠道号和渠道名称
     *
     * @return 所有的渠道号和渠道名称
     */
    public List<CtrlTParaChnlDO> chnlList() {
        ChnlDao dao = ChnlDao.getInstance();
        return dao.getChnlInfo();
    }

    /**
     * 获取机构层级、机构名称、机构代码
     *
     * @return 获取所有机构层级、机构名称、机构代码
     */
    public List<OfficeDo> officeList() {
        OfficeDao dao = OfficeDao.getInstance();
        return dao.list();
    }


    /**
     * 获取限额管理商户列表
     *
     * @return 获取限额管理商户列表
     * @param reqBody
     * @param start
     * @param limit
     */
    public List<LimitAmtDO> limitAmtList(Map<String, Object> reqBody, int start, int limit) {
        ComDao dao = ComDao.getInstance();
        return dao.getLimAmtList(reqBody,start,limit);
    }

    /**
     * 获取限额商户数量
     * @param reqBody
     * @return
     */
    public int countLimAmtList(Map<String, Object> reqBody) {
        ComDao dao = ComDao.getInstance();
        return dao.countLimAmtList(reqBody);
    }

    /**
     * 限额商户新增
     * @param reqBody
     */
    public void limitAmtAdd(HashMap<String, Object> reqBody) {
        ComDao dao = ComDao.getInstance();
        dao.limitAmtAdd(reqBody);
    }

    /**
     * 限额商户信息修改
     * @param reqBody
     */
    public void limitAmtModify(HashMap<String, Object> reqBody) {
        ComDao dao = ComDao.getInstance();
        dao.limitAmtModify(reqBody);
    }

    /**
     *
     * @param busiType  业务类型
     * @param officeId
     * @return
     */
    public List<String> getBusiNoByOfficeId(String busiType,String officeId){
        ComDao dao = ComDao.getInstance();
        return dao.getBusiNoByOfficeId(busiType,officeId);
    }
}
