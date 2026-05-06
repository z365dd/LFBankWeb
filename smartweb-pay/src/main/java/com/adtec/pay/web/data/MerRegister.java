package com.adtec.pay.web.data;

import com.adtec.framework.common.util.ServerResponse;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.pay.dao.BusiDao;
import com.adtec.pay.dao.UserDao;
import com.adtec.pay.entity.UserDo;
import com.adtec.pay.utils.ComUtils;
import com.adtec.pay.utils.StrUtils;
import com.adtec.sys.common.utils.IdGen;
import com.adtec.sys.modules.sys.dao.RoleDao;
import com.adtec.sys.modules.sys.entity.Role;
import com.adtec.sys.modules.sys.service.SystemService;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
@RequestMapping("${adminPath}/merRegister/data/")
public class MerRegister {

    private BusiDao busiDao = BusiDao.getInstance();

    @Autowired
    private RoleDao roleDao;
    //生活缴费平台虚拟机构
    public static final String MER_TYPE_BRCH = "688071";
    //联机类型商户
    public static final String ONLINE_TYPE_ROLE = "merchans";
    //非联机类型商户
    public static final String OFFLINE_TYPE_ROLE = "merchans_offline";
    //公交卡类型商户
    public static final String BUSCARD_TYPE_ROLE = "merchans_buscard";


    @RequestMapping("/register")
    public ServerResponse<JSONObject> register(HttpServletRequest request) {
        try {
            IDataset reqDs = DatasetService.getInstace().getDataset(request);
            String busiNo = reqDs.getString("busiNo");
            String loginName = reqDs.getString("loginName");
            String pwd = reqDs.getString("pwd");
            String email = reqDs.getString("email");
            String phoneNo = reqDs.getString("phoneNo");
            String certNo = reqDs.getString("certNo");
            String realName = reqDs.getString("realName");
            String busiTp = reqDs.getString("busiTp");
            pwd = SystemService.entryptPassword(pwd);
            String id = IdGen.uuid();
            UserDao dao = UserDao.getInstance();
            Integer integer = dao.get(loginName);
            if (integer > 0) {
                return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "商户添加失败, 用户名重复");
            }
            UserDo user = new UserDo();
            user.setId(id);
            //默认选择默认机构号 生活缴费平台虚拟机构
            user.setBrchId(MER_TYPE_BRCH);
            user.setLoginName(loginName);
            //默认工号 在修改界面使用
            user.setUserNo(loginName);
            user.setPwd(pwd);
            user.setEmail(email);
            user.setPhoneNo(phoneNo);
            user.setCrtr(UserUtils.getUser().getId());
            user.setCrtTime(LocalDateTime.now().format(ComUtils.yyyyMMddHHmmss));
            user.setRmrk(certNo);
            user.setName(realName);
            user.setDelFlg("0");
            user.setTntId("1");
            //目前确定的测试商户的role_id 之后配置到配置文件中或者直接查询返回
            Role role = new Role();
            //根据上送的业务类型来创建不同类型的商户
            if(BusiDao.TYPE_ONLINE.equals(busiTp)){
                role.setEngName(ONLINE_TYPE_ROLE);
            } else if(BusiDao.TYPE_OFFLINE.equals(busiTp)){
                role.setEngName(OFFLINE_TYPE_ROLE);
            } else if(BusiDao.TYPE_BUSCARD.equals(busiTp)){
                role.setEngName(BUSCARD_TYPE_ROLE);
            }
            String role_id = roleDao.getByEngName(role).getId();
            //创建商户和用户的关系;配置商户角色;
            int insert = dao.insert(user, busiNo, role_id);

            if (insert == 1) {
                return ServerResponse.createBySuccessMessage("商户添加成功");
            }
            return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "商户添加失败");
        } catch (Exception e) {
            return ServerResponse.createByErrorCodeMessage(SysErr.E_MESSAGE, "商户添加失败：" + e.getMessage());
        }

    }

    @RequestMapping("/randomPwd")
    public ServerResponse<String> randomPwd() {
        String randomString = StrUtils.randomString(8);
        return ServerResponse.createBySuccess("查询成功", randomString);
    }


}

