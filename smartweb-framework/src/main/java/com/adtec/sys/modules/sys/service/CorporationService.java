package com.adtec.sys.modules.sys.service;

import static com.adtec.sys.modules.sys.utils.UserUtils.CACHE_CORPORATION_LIST;

import java.util.List;

import com.adtec.sys.modules.sys.dao.PermissionDao;
import com.adtec.sys.modules.sys.dao.SysPermissionWeightDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adtec.framework.common.util.StringUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.sys.modules.sys.dao.CorporationDao;
import com.adtec.sys.modules.sys.dao.MenuDao;
import com.adtec.sys.modules.sys.entity.Corporation;
import com.adtec.sys.modules.sys.entity.Menu;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.adtec.sys.modules.sys.vo.PermissionDTO;
import com.google.common.collect.Lists;

@Service
@Transactional(readOnly = true)
public class CorporationService {

    private final PermissionDao permissionDao;
    private final CorporationDao dao;
    private final MenuDao menuDao;
    private final SysPermissionWeightDao sysPermissionWeightDao;

    public CorporationService(PermissionDao permissionDao, CorporationDao dao, MenuDao menuDao, SysPermissionWeightDao sysPermissionWeightDao) {
        this.permissionDao = permissionDao;
        this.dao = dao;
        this.menuDao = menuDao;
        this.sysPermissionWeightDao = sysPermissionWeightDao;
    }

    public int delete(Corporation corporation) {
        int i = dao.delete(corporation);
        UserUtils.removeCache(CACHE_CORPORATION_LIST);
        return i;
    }

    public Corporation getByNumber(String number) {
        return dao.getByNumber(number);
    }

    public Corporation getByEngName(String engName) {
        return dao.getByEngName(engName);
    }

    public void save(Corporation corporation) {
        // 如果没有设置父节点，则代表为跟节点，有则获取父节点实体
        if (corporation.getParent() == null || StringUtil.isBlank(corporation.getParentId())
                || "0".equals(corporation.getParentId())) {
            corporation.setParent(null);
        } else {
            corporation.setParent(dao.get(corporation.getParentId()));
        }
        if (corporation.getParent() == null) {
            Corporation parentEntity = null;
            try {
                parentEntity = new Corporation("0");
            } catch (Exception e) {
                throw new BaseException(SysErr.E_NO_MESSAGE, e, "设置权限失败");
            }
            corporation.setParent(parentEntity);
            corporation.getParent().setParentIdList(StringUtil.EMPTY);
        }

        // 获取修改前的parentIds，用于更新子节点的parentIds
        String oldParentIds = corporation.getParentIdList();
        if (oldParentIds == null && !StringUtil.isBlank(corporation.getId())) {
            oldParentIds = dao.get(corporation.getId()).getParentIdList();
        }

        // 设置新的父节点串
        corporation.setParentIdList(corporation.getParent().getParentIdList() + corporation.getParent().getId() + ",");

        // 保存或更新实体
        if (StringUtil.isBlank(corporation.getId())) {
            corporation.preInsert();
            dao.insert(corporation);
            sysPermissionWeightDao.initMenuPermissionToCorporation(corporation.getId());
        } else {
            corporation.preUpdate();
            dao.update(corporation);
            // 上级发生变化时，清空转授权和使用权
            if (!oldParentIds.equals(corporation.getParentIdList())) {
                permissionDao.deleteMenusByMenuIds("LEGA_ID", corporation.getId(), "transfer");
                permissionDao.deleteMenusByMenuIds("LEGA_ID", corporation.getId(), "use");
            }
        }

        // 更新子节点 parentIds
        Corporation o = new Corporation();
        o.setParentIdList("%," + corporation.getId() + ",%");
        List<Corporation> list = dao.findByParentIdsLike(o);
        for (Corporation e : list) {
            if (e.getParentIdList() != null && oldParentIds != null) {
                e.setParentIdList(e.getParentIdList().replace(oldParentIds, corporation.getParentIdList()));
                dao.updateParentIds(e);
                // 上级发生变化时，清空转授权和使用权
                if (!oldParentIds.equals(corporation.getParentIdList())) {
                    permissionDao.deleteMenusByMenuIds("LEGA_ID", e.getId(), "transfer");
                    permissionDao.deleteMenusByMenuIds("LEGA_ID", e.getId(), "use");
                }
            }
        }
        UserUtils.removeCache(UserUtils.CACHE_CORPORATION_LIST);
    }

    public List<Corporation> findAllCorporation() {
        return UserUtils.getCorporationList();
    }

    public Corporation get(String id) {
        return dao.get(id);
    }
}
