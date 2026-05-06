package com.adtec.sys.modules.sys.service;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adtec.sys.modules.sys.dao.MenuDao;
import com.adtec.sys.modules.sys.dao.UserDao;
import com.adtec.sys.modules.sys.entity.Menu;
import com.adtec.sys.modules.sys.entity.User;

import java.util.List;

/**
 * @author chenyl
 */
@Service
@Transactional(readOnly = true)
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private MenuDao menuDao;

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updateOffice(User user) {
        //取消该用户的所有角色关联
//        userDao.deleteUserRole(user, null);
        return userDao.updateOffice(user.getId(), user.getOffice().getId());
    }

    public User getUser(String id) {
        return userDao.get(id);
    }
    
    public User getUserByLoginName(String username) {
    	User user = new User();
    	user.setLoginName(username);
        return userDao.getByLoginName(user);
    }

    public User userMenu(User user) {
        List<Menu> menuList;
        if (user.isAdmin()){
            menuList = menuDao.findAllList();
        }else{
            menuList = menuDao.findByUser(user);
        }

        // 只有根节点菜单时，去掉根节点的显示
        if (menuList.size() == 1) {
            for (Menu menu : menuList) {
                if ("1".equals(menu.getId())) {
                    menuList = Lists.newArrayList();
                }
            }
        }
        user.setMenuList(menuList);
        return user;
    }

    public void updateUserLoginInfo(User user){
         userDao.updateLoginInfo(user);
    }
}
