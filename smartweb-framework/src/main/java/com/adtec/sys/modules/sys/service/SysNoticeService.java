/**
 * 系统名称: SmartWeb平台
 * 模块名称: sys-modules服务模块
 * 功能描述: 公告消息服务提供类
 * 类 名 称  : SysNoticeService.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 20190829<br>
 * 系统版本: V1.0.0<br>
 * * 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * <p>
 * ========     ======  ============================================
 */
package com.adtec.sys.modules.sys.service;

import com.adtec.framework.common.functions.Func;
import com.adtec.framework.common.functions.FuncP;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.sys.modules.sys.dao.SysNoticeDao;
import com.adtec.sys.modules.sys.entity.*;
import com.adtec.sys.modules.sys.utils.NotificationUtil;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * SysNoticeService
 *
 * @author z'x
 * @version 20190829
 */
@Service
@Transactional(readOnly = true)
public class SysNoticeService {

    private final static Logger log = LoggerFactory.getLogger(SysNoticeService.class);

    @Autowired
    private SysNoticeDao sysNoticeDao;
    @Autowired
    private SystemService systemService;

    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    public SysNoticeDO get(String id) {
        return sysNoticeDao.get(id);
    }

    /**
     * 获取单条数据
     *
     * @param obj
     * @return
     */
    public SysNoticeDO get(SysNoticeDO obj) {
        return sysNoticeDao.get(obj);
    }

    /**
     * 插入数据到 t_sys_notice
     *
     * @param obj
     * @return
     */
    public boolean insert(SysNoticeDO obj) {
        int rs = 0;
        obj.preInsert();
        notify(obj);
        rs = sysNoticeDao.insert(obj);
        return rs > 0;
    }

    private void notify(final SysNoticeDO obj) {
        DBSessionFactory.async(new Func() {
            @Override
            public void call() throws Exception {
                if ("Y".equals(obj.getPopupFlg())) {
                    String ids = obj.getIds();
                    String noteScp = obj.getNoteScp();
                    Set<String> notifiedUser = Sets.newHashSet();
                    switch (noteScp) {
                        case NoteScp.ALL:
                            NotificationUtil.notifyEveryone(obj);
                            break;
                        case NoteScp.USER:
                            if (DataUtil.isNullStr(ids)) {
                                break;
                            }
                            for (String userId : ids.split(",")) {
                                User user = UserUtils.get(userId);
                                if (user != null) {
                                    NotificationUtil.notifyUser(user.getLoginName(), obj);
                                }
                            }
                            break;
                        case NoteScp.OFFICE:
                            if (DataUtil.isNullStr(ids)) {
                                break;
                            }
                            for (String officeId : ids.split(",")) {
                                Office office = new Office(officeId);
                                User user = new User();
                                user.setOffice(office);
                                List<User> users = systemService.findUser(user);
                                for (User u : users) {
                                    if (notifiedUser.contains(u.getLoginName())) {
                                        continue;
                                    }
                                    NotificationUtil.notifyUser(u.getLoginName(), obj);
                                    notifiedUser.add(u.getLoginName());
                                }
                            }
                            break;
                        case NoteScp.ROLE:
                            if (DataUtil.isNullStr(ids)) {
                                break;
                            }
                            for (String roleId : ids.split(",")) {
                                Role role = new Role(roleId);
                                User user = new User();
                                user.setRole(role);
                                List<User> users = systemService.findUser(user);
                                for (User u : users) {
                                    if (notifiedUser.contains(u.getLoginName())) {
                                        continue;
                                    }
                                    NotificationUtil.notifyUser(u.getLoginName(), obj);
                                    notifiedUser.add(u.getLoginName());
                                }

                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }, new FuncP<Exception>() {
            @Override
            public void call(Exception e) throws Exception {

            }
        });
    }


    /**
     * 更新数据
     *
     * @param obj
     * @return
     */
    public boolean update(SysNoticeDO obj) {
        int rs = 0;
        if (null == obj) {
            throw new BaseException(SysErr.E_DEFAULT, "更新数据对象不能空！");
        }
        if (DataUtil.isNullStr(obj.getId())) {
            throw new BaseException(SysErr.E_IN_NULL, "id");
        }
        // 获取数据库保存的数据
        SysNoticeDO qryDO = sysNoticeDao.get(obj.getId());
        // 更新产品数据
        obj.setCrtr(qryDO.getCrtr());
        obj.setCrtTime(qryDO.getCrtTime());
        obj.preUpdate();
        notify(obj);
        rs = sysNoticeDao.update(obj);
        return rs > 0;
    }

    /**
     * 根据主键id删除数据（一般为逻辑删除，更新del_flg字段为1）
     *
     * @param id
     * @return
     * @see public int delete(T entity)
     */
    public boolean delete(String id) {
        int rs = 0;
        if (DataUtil.isNullStr(id)) {
            throw new BaseException(SysErr.E_IN_NULL, "id");
        }
        rs = sysNoticeDao.delete(id);
        return rs > 0 ? true : false;
    }

    /**
     * 删除数据（一般为逻辑删除，更新del_flg字段为1）
     *
     * @param obj
     * @return
     */
    public boolean delete(SysNoticeDO obj) {
        int rs = 0;
        if (null == obj) {
            throw new BaseException(SysErr.E_DEFAULT, "删除数据对象不能空！");
        }
        if (DataUtil.isNullStr(obj.getId())) {
            throw new BaseException(SysErr.E_IN_NULL, "id");
        }
        rs = sysNoticeDao.delete(obj);
        return rs > 0 ? true : false;
    }

    public void deleteData(String id) {
        sysNoticeDao.deleteData(id);
    }

    /**
     * 数据库多笔查询
     *
     * @param obj 数据对象DO
     * @return List返回集合
     */
    public List<SysNoticeDO> list(SysNoticeDO obj) {
        return sysNoticeDao.list(obj);
    }

    public List<SysNoticeDataDO> listData(String id) {
        return sysNoticeDao.listData(id);
    }

    /**
     * 数据库多笔查询，支持分页
     *
     * @param obj   数据对象DO
     * @param start 起始位置
     * @param limit 每页数量
     * @return List返回集合
     */
    public List<SysNoticeDO> list(SysNoticeDO obj, int start, int limit) {
        return sysNoticeDao.list(obj, start, limit);
    }

    /**
     * 数据库多笔查询，支持分页
     *
     * @param start 起始位置
     * @param limit 每页数量
     * @param param 查询参数
     * @return List返回集合
     */
    public List<SysNoticeDO> list(int start, int limit, Object... param) {
        return sysNoticeDao.list(start, limit, param);
    }

    /**
     * 根据数据对象产生对应的数据查询总记录
     *
     * @param obj 数据对象DO
     * @return total
     */
    public int getTotal(SysNoticeDO obj) {
        return sysNoticeDao.getTotal(obj);
    }

    public List<Map<String, Object>> getTreeData(String type) {
        List<Map<String, Object>> maps = Lists.newArrayList();
        //公告类型：0-所有，1-用户，2-机构，3-角色
        if ("0".equals(type)) {

        } else if ("1".equals(type)) {
            List<User> list = UserUtils.getUserList();
            for (User user : list) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", user.getId());
                map.put("pId", "0");
                map.put("name", user.getName());
                maps.add(map);
            }
            //因为用户没有层级关系，为方便用户选择，虚构一个全选的顶级节点
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", "0");
            map.put("pId", "0");
            map.put("name", "全选/取消");
            maps.add(map);
        } else if ("2".equals(type)) {
            List<Office> list = UserUtils.getOfficeList();
            for (Office office : list) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", office.getId());
                map.put("pId", office.getParentId());
                map.put("name", office.getName());
                maps.add(map);
            }
        } else if ("3".equals(type)) {
            List<Role> list = UserUtils.getRoleList();
            for (Role role : list) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", role.getId());
                map.put("pId", "0");
                map.put("name", role.getName());
                maps.add(map);
            }
            //因为角色没有层级关系，为方便用户选择，虚构一个全选的顶级节点
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", "0");
            map.put("pId", "0");
            map.put("name", "全选/取消");
            maps.add(map);
        }
        return maps;
    }

    public List<Map<String, Object>> getNoticeList() {
        List<Map<String, Object>> maps = Lists.newArrayList();
        List<SysNoticeDO> list = sysNoticeDao.list(new SysNoticeDO(), 0, 10);
        for (SysNoticeDO s : list) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", s.getId());
            map.put("title", s.getNoteTitle());
            map.put("date", s.getUptTime());
            maps.add(map);
        }
        return maps;
    }

    public List<Map<String, Object>> getUserInfo() {
        List<Map<String, Object>> maps = Lists.newArrayList();
        User user = UserUtils.getUser();
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", user.getId());
        map.put("name", user.getLoginName());
        map.put("roleName", user.getRoleNames());
        map.put("userPhoto", user.getImg());
        map.put("brchName", user.getOffice().getName());
        map.put("legaName", user.getCorporation().getName());
        map.put("loginIp", user.getLoginIp());
        map.put("loginDate", user.getLoginDate());
		//加了租户切换功能后，用户的租户信息不能从缓存中取
		RentService rentService = SpringContextHolder.getBean("rentService");
		UserService userService = SpringContextHolder.getBean("userService");
		String userId = user.getId();
		if(!DataUtil.isNullStr(userId)){
			User  u = userService.getUser(userId);
			if(u != null){
				Rent rent = rentService.get(u.getRent().getId());
				if(rent != null){
					map.put("tntName", rent.getName());
				}
			}
		}
        maps.add(map);
        return maps;
    }

    public interface NoteScp {
        String ALL = "0";
        String USER = "1";
        String OFFICE = "2";
        String ROLE = "3";
    }
}
