package com.adtec.sys.modules.sys.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adtec.sys.modules.sys.dao.*;
import com.adtec.sys.modules.sys.entity.*;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.adtec.sys.modules.sys.vo.PermissionDTO;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PermissionService {
    private final static Logger logger = LoggerFactory.getLogger(PermissionService.class);

    private final PermissionDao permissionDao;
    private final OfficeDao officeDao;
    private final RentDao rentDao;
    private final CorporationDao corporationDao;
    private final RoleDao roleDao;
    private final SysPermissionWeightDao sysPermissionWeightDao;

    public PermissionService(PermissionDao permissionDao, OfficeDao officeDao, RentDao rentDao, CorporationDao corporationDao, RoleDao roleDao, SysPermissionWeightDao sysPermissionWeightDao) {
        this.permissionDao = permissionDao;
        this.officeDao = officeDao;
        this.rentDao = rentDao;
        this.corporationDao = corporationDao;
        this.roleDao = roleDao;
        this.sysPermissionWeightDao = sysPermissionWeightDao;
    }

    public PermissionDTO assembleCorporationVo(String legaId, String authTp) {
        String dbFieldName = "LEGA_ID";
        List<Menu> menuList;
        List<Menu> assignableMenuList = null;
        List<Menu> list1 = null;
        List<Menu> list2 = null;
        PermissionDTO permissionDTO = new PermissionDTO();
        Corporation corporation = corporationDao.get(legaId);
        Corporation parent = corporationDao.findParentCorporationById(legaId);
        if (null != parent) {
            // 当前用户所在法人，只能分配使用权，不能分配转授权
            if ("use".equals(authTp) && legaId.equals(UserUtils.getUser().getCorporation().getId())) {
                assignableMenuList = permissionDao.findMenusById(UserUtils.getUser().getCorporation().getId(), authTp, true, dbFieldName);
            } else {
                // 查询权限分配是否可跨越下级
                SysPermissionWeightDO sysPermissionWeightDO = new SysPermissionWeightDO();
                List<SysPermissionWeightDO> list = sysPermissionWeightDao.list(sysPermissionWeightDO);
                for (SysPermissionWeightDO temp : list) {
                    if ("corporation".equals(temp.getWghtName())) {
                        sysPermissionWeightDO = temp;
                        break;
                    }
                }

                // 可跨越下级分配
                if ("1".equals(sysPermissionWeightDO.getAuthLvlSwitchFlg())) {
                    // 分配转授权时，可分配转授权 = 用户所在权限组拥有所有权 ∪ 用户所在权限组拥有转授权
                    // 分配使用权时，可分配使用权 = 用户所在权限组拥有转授权
                    if ("transfer".equals(authTp)) {
                        assignableMenuList = permissionDao.findMenusById(UserUtils.getUser().getCorporation().getId(), authTp, true, dbFieldName);
                        list1 = permissionDao.findMenusById(UserUtils.getUser().getCorporation().getId(), authTp, false, dbFieldName);
                        // 并集
                        assignableMenuList.removeAll(list1);
                        assignableMenuList.addAll(list1);
                    } else if ("use".equals(authTp)) {
                        assignableMenuList = permissionDao.findMenusById(UserUtils.getUser().getCorporation().getId(), authTp, true, dbFieldName);
                    }
                } else {
                    // 不可跨越下级分配

                    // 分配转授权时，可分配转授权 = 上级拥有所有权 ∪ （上级拥有转授权 ∩ 用户所在权限组拥有转授权）
                    // 分配使用权时，可分配使用权 = 上级拥有转授权 ∪ （上级拥有使用权 ∩ 用户所在权限组拥有转授权）
                    assignableMenuList = permissionDao.findMenusById(parent.getId(), authTp, true, dbFieldName);
                    list1 = permissionDao.findMenusById(parent.getId(), authTp, false, dbFieldName);
                    if ("transfer".equals(authTp)) {
                        list2 = permissionDao.findMenusById(UserUtils.getUser().getCorporation().getId(), authTp, false, dbFieldName);
                    } else if ("use".equals(authTp)) {
                        list2 = permissionDao.findMenusById(UserUtils.getUser().getCorporation().getId(), authTp, true, dbFieldName);
                    }
                    // 交集
                    list1.retainAll(list2);
                    // 并集
                    assignableMenuList.removeAll(list1);
                    assignableMenuList.addAll(list1);
                }
            }
        }

        if (null != assignableMenuList) {
            permissionDTO.setParentMenuList(assignableMenuList);
            permissionDTO.setTempMenuIdListUseMenu(assignableMenuList);
        }

        menuList = permissionDao.findMenusById(legaId, authTp, false, dbFieldName);
        if (null != menuList) {
            permissionDTO.setMenuList(menuList);
        }
        permissionDTO.setId(legaId);
        permissionDTO.setName(corporation.getName());
        permissionDTO.setAuthTp(authTp);
        return permissionDTO;
    }

    public PermissionDTO assembleCorporationVoByAuthTp(String legaId, String authTp) {
        String dbFieldName = "LEGA_ID";
        PermissionDTO permissionDTO = new PermissionDTO();
        Corporation corporation = corporationDao.get(legaId);
        List<Menu> menuList = permissionDao.findMenusByIdAndAuthTp(legaId, authTp, dbFieldName);
        if (null != menuList) {
            // 只剩下一个功能菜单"1"时，不显示
            if (menuList.size() == 1 && "1".equals(menuList.get(0).getId())) {
                menuList.remove(0);
            }
            permissionDTO.setMenuList(menuList);
            permissionDTO.setParentMenuList(menuList);
        }
        permissionDTO.setId(legaId);
        permissionDTO.setName(corporation.getName());
        return permissionDTO;
    }

    public PermissionDTO assembleOfficeVoByAuthTp(String brchId, String authTp) {
        String dbFieldName = "BRCH_ID";
        PermissionDTO officeVo = new PermissionDTO();
        Office office = officeDao.get(brchId);
        List<Menu> menuList = permissionDao.findMenusByIdAndAuthTp(brchId, authTp, dbFieldName);
        if (null != menuList) {
            // 只剩下一个功能菜单"1"时，不显示
            if (menuList.size() == 1 && "1".equals(menuList.get(0).getId())) {
                menuList.remove(0);
            }
            officeVo.setMenuList(menuList);
            officeVo.setParentMenuList(menuList);
        }
        officeVo.setId(brchId);
        officeVo.setName(office.getName());
        return officeVo;
    }

    public PermissionDTO assembleRentVoByAuthTp(String tntId, String authTp) {
        String dbFieldName = "TNT_ID";
        PermissionDTO rentVo = new PermissionDTO();
        Rent rent = rentDao.get(tntId);
        List<Menu> menuList = permissionDao.findMenusByIdAndAuthTp(tntId, authTp, dbFieldName);
        if (null != menuList) {
            // 只剩下一个功能菜单"1"时，不显示
            if (menuList.size() == 1 && "1".equals(menuList.get(0).getId())) {
                menuList.remove(0);
            }
            rentVo.setMenuList(menuList);
            rentVo.setParentMenuList(menuList);
        }
        rentVo.setId(tntId);
        rentVo.setName(rent.getName());
        return rentVo;
    }

    public PermissionDTO assembleRoleVoByAuthTp(String roleId, String authTp) {
        PermissionDTO roleVo = new PermissionDTO();
        Role role = roleDao.get(roleId);
        List<Menu> menuList = permissionDao.findMenuListByRoleIdByAuthTp(roleId, authTp);
        if (null != menuList) {
            // 只剩下一个功能菜单"1"时，不显示
            if (menuList.size() == 1 && "1".equals(menuList.get(0).getId())) {
                menuList.remove(0);
            }
            roleVo.setMenuList(menuList);
            roleVo.setParentMenuList(menuList);
        }
        roleVo.setId(roleId);
        roleVo.setName(role.getName());
        return roleVo;
    }

    public PermissionDTO assembleOfficeVo(String brchId, String authTp) {
        String dbFieldName = "BRCH_ID";
        List<Menu> menuList;
        List<Menu> assignableMenuList = null;
        List<Menu> list1 = null;
        List<Menu> list2 = null;
        PermissionDTO officeVo = new PermissionDTO();
        Office office = officeDao.get(brchId);
        Office parent = officeDao.findParentOfficeById(brchId);
        if (null != parent) {
            // 当前用户所在机构，只能分配使用权，不能分配转授权
            if ("use".equals(authTp) && brchId.equals(UserUtils.getUser().getOffice().getId())) {
                assignableMenuList = permissionDao.findMenusById(UserUtils.getUser().getOffice().getId(), authTp, true, dbFieldName);
            } else {
                // 查询权限分配是否可跨越下级
                SysPermissionWeightDO sysPermissionWeightDO = new SysPermissionWeightDO();
                List<SysPermissionWeightDO> list = sysPermissionWeightDao.list(sysPermissionWeightDO);
                for (SysPermissionWeightDO temp : list) {
                    if ("office".equals(temp.getWghtName())) {
                        sysPermissionWeightDO = temp;
                        break;
                    }
                }

                // 可跨越下级分配
                if ("1".equals(sysPermissionWeightDO.getAuthLvlSwitchFlg())) {
                    // 分配转授权时，可分配转授权 = 用户所在权限组拥有所有权 ∪ 用户所在权限组拥有转授权
                    // 分配使用权时，可分配使用权 = 用户所在权限组拥有转授权
                    if ("transfer".equals(authTp)) {
                        assignableMenuList = permissionDao.findMenusById(UserUtils.getUser().getOffice().getId(), authTp, true, dbFieldName);
                        list1 = permissionDao.findMenusById(UserUtils.getUser().getOffice().getId(), authTp, false, dbFieldName);
                        // 并集
                        assignableMenuList.removeAll(list1);
                        assignableMenuList.addAll(list1);
                    } else if ("use".equals(authTp)) {
                        assignableMenuList = permissionDao.findMenusById(UserUtils.getUser().getOffice().getId(), authTp, true, dbFieldName);
                    }
                } else {
                    // 不可跨越下级分配

                    // 分配转授权时，可分配转授权 = 上级拥有所有权 ∪ （上级拥有转授权 ∩ 用户所在权限组拥有转授权）
                    // 分配使用权时，可分配使用权 = 上级拥有转授权 ∪ （上级拥有使用权 ∩ 用户所在权限组拥有转授权）
                    assignableMenuList = permissionDao.findMenusById(parent.getId(), authTp, true, dbFieldName);
                    list1 = permissionDao.findMenusById(parent.getId(), authTp, false, dbFieldName);
                    if ("transfer".equals(authTp)) {
                        list2 = permissionDao.findMenusById(UserUtils.getUser().getOffice().getId(), authTp, false, dbFieldName);
                    } else if ("use".equals(authTp)) {
                        list2 = permissionDao.findMenusById(UserUtils.getUser().getOffice().getId(), authTp, true, dbFieldName);
                    }
                    // 交集
                    list1.retainAll(list2);
                    // 并集
                    assignableMenuList.removeAll(list1);
                    assignableMenuList.addAll(list1);
                }
            }
        }

        if (null != assignableMenuList) {
            officeVo.setParentMenuList(assignableMenuList);
            officeVo.setTempMenuIdListUseMenu(assignableMenuList);
        }

        menuList = permissionDao.findMenusById(brchId, authTp, false, dbFieldName);
        if (null != menuList) {
            officeVo.setMenuList(menuList);
        }
        officeVo.setId(brchId);
        officeVo.setName(office.getName());
        officeVo.setAuthTp(authTp);
        return officeVo;
    }

    public PermissionDTO assembleRentVo(String tntId, String authTp) {
        String dbFieldName = "TNT_ID";
        List<Menu> menuList;
        List<Menu> assignableMenuList = null;
        List<Menu> list1 = null;
        List<Menu> list2 = null;
        PermissionDTO rentVo = new PermissionDTO();
        Rent rent = rentDao.get(tntId);
        Rent parent = rentDao.findParentRentById(tntId);
        if (null != parent) {
            // 当前用户所在租户，只能分配使用权，不能分配转授权
            if ("use".equals(authTp) && tntId.equals(UserUtils.getUser().getRent().getId())) {
                assignableMenuList = permissionDao.findMenusById(UserUtils.getUser().getRent().getId(), authTp, true, dbFieldName);
            } else {
                // 查询权限分配是否可跨越下级
                SysPermissionWeightDO sysPermissionWeightDO = new SysPermissionWeightDO();
                List<SysPermissionWeightDO> list = sysPermissionWeightDao.list(sysPermissionWeightDO);
                for (SysPermissionWeightDO temp : list) {
                    if ("rent".equals(temp.getWghtName())) {
                        sysPermissionWeightDO = temp;
                        break;
                    }
                }

                // 可跨越下级分配
                if ("1".equals(sysPermissionWeightDO.getAuthLvlSwitchFlg())) {
                    // 分配转授权时，可分配转授权 = 用户所在权限组拥有所有权 ∪ 用户所在权限组拥有转授权
                    // 分配使用权时，可分配使用权 = 用户所在权限组拥有转授权
                    if ("transfer".equals(authTp)) {
                        assignableMenuList = permissionDao.findMenusById(UserUtils.getUser().getRent().getId(), authTp, true, dbFieldName);
                        list1 = permissionDao.findMenusById(UserUtils.getUser().getRent().getId(), authTp, false, dbFieldName);
                        // 并集
                        assignableMenuList.removeAll(list1);
                        assignableMenuList.addAll(list1);
                    } else if ("use".equals(authTp)) {
                        assignableMenuList = permissionDao.findMenusById(UserUtils.getUser().getRent().getId(), authTp, true, dbFieldName);
                    }
                } else {
                    // 不可跨越下级分配

                    // 分配转授权时，可分配转授权 = 上级拥有所有权 ∪ （上级拥有转授权 ∩ 用户所在权限组拥有转授权）
                    // 分配使用权时，可分配使用权 = 上级拥有转授权 ∪ （上级拥有使用权 ∩ 用户所在权限组拥有转授权）
                    assignableMenuList = permissionDao.findMenusById(parent.getId(), authTp, true, dbFieldName);
                    list1 = permissionDao.findMenusById(parent.getId(), authTp, false, dbFieldName);
                    if ("transfer".equals(authTp)) {
                        list2 = permissionDao.findMenusById(UserUtils.getUser().getRent().getId(), authTp, false, dbFieldName);
                    } else if ("use".equals(authTp)) {
                        list2 = permissionDao.findMenusById(UserUtils.getUser().getRent().getId(), authTp, true, dbFieldName);
                    }
                    // 交集
                    list1.retainAll(list2);
                    // 并集
                    assignableMenuList.removeAll(list1);
                    assignableMenuList.addAll(list1);
                }
            }
        }

        if (null != assignableMenuList) {
            rentVo.setParentMenuList(assignableMenuList);
            rentVo.setTempMenuIdListUseMenu(assignableMenuList);
        }

        menuList = permissionDao.findMenusById(tntId, authTp, false, dbFieldName);
        if (null != menuList) {
            rentVo.setMenuList(menuList);
        }
        rentVo.setId(tntId);
        rentVo.setName(rent.getName());
        rentVo.setAuthTp(authTp);
        return rentVo;
    }

    public PermissionDTO assembleRoleVo(String roleId, String authTp) {
        String dbFieldName = "ROLE_ID";
        PermissionDTO roleVo = new PermissionDTO();
        Role role = roleDao.get(roleId);
        List<Menu> assignableMenuList = permissionDao.findMenuListCanAllotte(authTp, roleId);
        if (null != assignableMenuList) {
            roleVo.setParentMenuList(assignableMenuList);
            roleVo.setTempMenuIdListUseMenu(assignableMenuList);
        }
        List<Menu> menuList = permissionDao.findMenuListByRoleId(roleId, authTp, dbFieldName);
        if (null != menuList) {
            roleVo.setMenuList(menuList);
        }
        roleVo.setId(roleId);
        roleVo.setName(role.getName());
        roleVo.setAuthTp(authTp);
        return roleVo;
    }

    public int savePermissionToCorporation(PermissionDTO permissionDTO) {
        String dbFieldName = "LEGA_ID";

        List<String> legaIds = new ArrayList<>();
        // 查询权限分配是否可跨越下级
        SysPermissionWeightDO sysPermissionWeightDO = new SysPermissionWeightDO();
        List<SysPermissionWeightDO> list = sysPermissionWeightDao.list(sysPermissionWeightDO);
        for (SysPermissionWeightDO temp : list) {
            // 可跨越下级时，不清空下级已分配的权限
            if ("corporation".equals(temp.getWghtName()) && "1".equals(temp.getAuthLvlSwitchFlg())) {
                legaIds.add(permissionDTO.getId());
            }
        }

        if (legaIds.size() == 0) {
            Corporation corporation = new Corporation();
            corporation.setId(permissionDTO.getId());
            legaIds = corporationDao.getCorporationsByLegaId(corporation);
        }

        List<String> menuIds = permissionDTO.getMenuIdList();
        // 当分配菜单数量为0时，不删除根目录功能菜单"1"
        if (menuIds != null && menuIds.size() == 0) {
            permissionDTO.getTempMenuIdList().remove("1");
        }

        // 求差集，上级移除的权限，下级也移除
        List<String> diff = new ArrayList<>();
        diff.addAll(permissionDTO.getTempMenuIdList());
        diff.removeAll(menuIds);
for (String s : permissionDTO.getTempMenuIdList()) {
    logger.error("原有菜单ID：" + s);
}
for (String s : menuIds) {
    logger.error("新分配菜单ID：" + s);
}
for (String s : diff) {
    logger.error("差异菜单ID：" + s);
}
        //更新当前和下属法人权限组
        if (legaIds.size() > 0) {
            for (String legaId : legaIds) {
                if (legaId.equals(permissionDTO.getId())) {
                    permissionDao.deleteMenusByMenuIds(dbFieldName, legaId, permissionDTO.getAuthTp(), permissionDTO.getTempMenuIdList(), false);
                } else {
                    permissionDao.deleteMenusByMenuIds(dbFieldName, legaId, permissionDTO.getAuthTp(), diff, false);
                }
            }
        }
        if (menuIds != null && menuIds.size() > 0) {
            return permissionDao.insertPermissionMenu(permissionDTO, dbFieldName);
        } else {
            return 1;
        }
    }

    public int savePermissionToOffice(PermissionDTO officeVo) {
        String dbFieldName = "BRCH_ID";

        List<String> brchIds = new ArrayList<>();
        // 查询权限分配是否可跨越下级
        SysPermissionWeightDO sysPermissionWeightDO = new SysPermissionWeightDO();
        List<SysPermissionWeightDO> list = sysPermissionWeightDao.list(sysPermissionWeightDO);
        for (SysPermissionWeightDO temp : list) {
            // 可跨越下级时，不清空下级已分配的权限
            if ("office".equals(temp.getWghtName()) && "1".equals(temp.getAuthLvlSwitchFlg())) {
                brchIds.add(officeVo.getId());
            }
        }

        if (brchIds.size() == 0) {
            Office office = new Office();
            office.setId(officeVo.getId());
            brchIds = officeDao.getOfficesByBrchId(office);
        }

        List<String> menuIds = officeVo.getMenuIdList();
        // 当分配菜单数量为0时，不删除根目录功能菜单"1"
        if (menuIds != null && menuIds.size() == 0) {
            officeVo.getTempMenuIdList().remove("1");
        }

        // 求差集，上级移除的权限，下级也移除
        List<String> diff = new ArrayList<>();
        diff.addAll(officeVo.getTempMenuIdList());
        diff.removeAll(menuIds);

for (String s : officeVo.getTempMenuIdList()) {
    logger.error("原有菜单ID：" + s);
}
for (String s : menuIds) {
    logger.error("新分配菜单ID：" + s);
}
for (String s : diff) {
    logger.error("差异菜单ID：" + s);
}
        //更新当前和下属机构权限组
        if (brchIds.size() > 0) {
            for (String brchId : brchIds) {
                if (brchId.equals(officeVo.getId())) {
                    permissionDao.deleteMenusByMenuIds(dbFieldName, brchId, officeVo.getAuthTp(), officeVo.getTempMenuIdList(), false);
                } else {
                    permissionDao.deleteMenusByMenuIds(dbFieldName, brchId, officeVo.getAuthTp(), diff, false);
                }
            }
        }
        if (menuIds != null && menuIds.size() > 0) {
            return permissionDao.insertPermissionMenu(officeVo, dbFieldName);
        } else {
            return 1;
        }
    }

    public int savePermissionToRent(PermissionDTO rentVo) {
        String dbFieldName = "TNT_ID";

        List<String> tntIds = new ArrayList<>();
        // 查询权限分配是否可跨越下级
        SysPermissionWeightDO sysPermissionWeightDO = new SysPermissionWeightDO();
        List<SysPermissionWeightDO> list = sysPermissionWeightDao.list(sysPermissionWeightDO);
        for (SysPermissionWeightDO temp : list) {
            // 可跨越下级时，不清空下级已分配的权限
            if ("rent".equals(temp.getWghtName()) && "1".equals(temp.getAuthLvlSwitchFlg())) {
                tntIds.add(rentVo.getId());
            }
        }

        if (tntIds.size() == 0) {
            Rent rent = new Rent();
            rent.setId(rentVo.getId());
            tntIds = rentDao.getRentsByTntId(rent);
        }

        List<String> menuIds = rentVo.getMenuIdList();
        if (menuIds != null && menuIds.size() == 0) {
            rentVo.getTempMenuIdList().remove("1");
        }

        // 求差集，上级移除的权限，下级也移除
        List<String> diff = new ArrayList<>();
        diff.addAll(rentVo.getTempMenuIdList());
        diff.removeAll(menuIds);

        //更新当前和下属租户权限组
        if (tntIds.size() > 0) {
            for (String tntId : tntIds) {
                if (tntId.equals(rentVo.getId())) {
                    permissionDao.deleteMenusByMenuIds(dbFieldName, tntId, rentVo.getAuthTp(), rentVo.getTempMenuIdList(), false);
                } else {
                    permissionDao.deleteMenusByMenuIds(dbFieldName, tntId, rentVo.getAuthTp(), diff, false);
                }
            }
        }
        if (menuIds != null && menuIds.size() > 0) {
            return permissionDao.insertPermissionMenu(rentVo, dbFieldName);
        } else {
            return 1;
        }
    }

    public int savePermissionToRole(PermissionDTO roleVo) {
        String dbFieldName = "ROLE_ID";
        Role role = new Role();
        role.setId(roleVo.getId());
        List<String> menuIds = roleVo.getMenuIdList();
        permissionDao.deleteMenusByMenuIds(dbFieldName, roleVo.getId(), roleVo.getAuthTp(), roleVo.getTempMenuIdList(), true);
        if (menuIds != null && menuIds.size() > 0) {
            return permissionDao.insertPermissionMenu(roleVo, dbFieldName);
        } else {
            return 1;
        }
    }

}
