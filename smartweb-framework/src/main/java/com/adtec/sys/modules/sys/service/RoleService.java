/**
 *
 */
package com.adtec.sys.modules.sys.service;

import com.adtec.sys.modules.sys.dao.MenuDao;
import com.adtec.sys.modules.sys.dao.RoleDao;
import com.adtec.sys.modules.sys.entity.Menu;
import com.adtec.sys.modules.sys.entity.Role;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author Leize
 * @date: 2018年11月12日上午1:15:28
 */
@Service
@Transactional(readOnly = true)
public class RoleService {

    private final RoleDao roleDao;
    private final MenuDao menuDao;

    @Autowired
    public RoleService(RoleDao roleDao, MenuDao menuDao) {
        this.roleDao = roleDao;
        this.menuDao = menuDao;
    }

    public Role get(Role entity) {
        return roleDao.get(entity);
    }

    public Role get(String id) {
        return roleDao.get(id);
    }

    public List<Role> getRolesWithUser(Role role) {
        return roleDao.findListWithUser(role);
    }

}
