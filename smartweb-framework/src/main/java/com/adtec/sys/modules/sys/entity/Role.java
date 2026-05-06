package com.adtec.sys.modules.sys.entity;

import com.adtec.framework.common.util.ParamUtil;
import com.adtec.sys.common.persistence.BaseDO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.xml.bind.annotation.XmlTransient;
import java.util.List;
import java.util.Map;

/**
 * 角色Entity
 *
 * @version 2013-12-05
 */
public class Role extends BaseDO {

    // 数据范围（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在部门及以下数据；5：所在部门数据；8：仅本人数据；9：按明细设置）
    public static final String DATA_SCOPE_ALL = "1";
    public static final String DATA_SCOPE_COMPANY_AND_CHILD = "2";
    public static final String DATA_SCOPE_COMPANY = "3";
    public static final String DATA_SCOPE_OFFICE_AND_CHILD = "4";
    public static final String DATA_SCOPE_OFFICE = "5";
    public static final String DATA_SCOPE_SELF = "8";
    public static final String DATA_SCOPE_CUSTOM = "9";
    private static final long serialVersionUID = 1L;

    private String name;    // 角色名称
    private String engName;    // 英文名称
    private String roleTp;// 角色类型
    private String dataScp;// 数据范围
    private String oldName;    // 原角色名称
    private String oldEngName;    // 原英文名称
    private String dataSwitchFlg;        //是否是系统数据
    private String validSwitchFlg;        //是否是可用
    private User user;        // 根据用户ID查询角色列表
    private List<Menu> menuList = Lists.newArrayList(); // 拥有菜单列表
    private List<Office> officeList = Lists.newArrayList(); // 按明细设置数据范围

    public Role() {
        super();
        this.dataScp = DATA_SCOPE_ALL;
        this.validSwitchFlg = ParamUtil.YES;
    }

    public Role(String id) {
        super(id);
    }

    public Role(User user) {
        this();
        this.user = user;
    }

    public String getDataScp() {
        return dataScp;
    }

    public void setDataScp(String dataScp) {
        this.dataScp = dataScp;
    }

    @Length(min = 1, max = 100)
    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public List<String> getMenuIdList() {
        List<String> menuIdList = Lists.newArrayList();
        for (Menu menu : menuList) {
            menuIdList.add(menu.getId());
        }
        return menuIdList;
    }

    public void setMenuIdList(List<String> menuIdList) {
        menuList = Lists.newArrayList();
        for (String menuId : menuIdList) {
            Menu menu = new Menu();
            menu.setId(menuId);
            menuList.add(menu);
        }
    }

    public String getMenuIds() {
        return StringUtils.join(getMenuIdList(), ",");
    }

    public void setMenuIds(String menuIds) {
        menuList = Lists.newArrayList();
        if (menuIds != null) {
            String[] ids = StringUtils.split(menuIds, ",");
            setMenuIdList(Lists.newArrayList(ids));
        }
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    @Length(min = 1, max = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getBrchIdList() {
        List<String> brchIdList = Lists.newArrayList();
        for (Office office : officeList) {
            brchIdList.add(office.getId());
        }
        return brchIdList;
    }

    public void setBrchIdList(List<String> brchIdList) {
        officeList = Lists.newArrayList();
        for (String brchId : brchIdList) {
            Office office = new Office();
            office.setId(brchId);
            officeList.add(office);
        }
    }

    public String getBrchIds() {
        return StringUtils.join(getBrchIdList(), ",");
    }

    public void setBrchIds(String brchIds) {
        officeList = Lists.newArrayList();
        if (brchIds != null) {
            String[] ids = StringUtils.split(brchIds, ",");
            setBrchIdList(Lists.newArrayList(ids));
        }
    }

    public List<Office> getOfficeList() {
        return officeList;
    }

    public void setOfficeList(List<Office> officeList) {
        this.officeList = officeList;
    }

    public String getOldEngName() {
        return oldEngName;
    }

    public void setOldEngName(String oldEngName) {
        this.oldEngName = oldEngName;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    /**
     * 获取权限字符串列表
     */
    public List<String> getPermissions() {
        List<String> permissions = Lists.newArrayList();
        for (Menu menu : menuList) {
            if (menu.getAuth() != null && !"".equals(menu.getAuth())) {
                permissions.add(menu.getAuth());
            }
        }
        return permissions;
    }

    @Length(min = 1, max = 100)
    public String getRoleTp() {
        return roleTp;
    }

    public void setRoleTp(String roleTp) {
        this.roleTp = roleTp;
    }

    public String getDataSwitchFlg() {
        return dataSwitchFlg;
    }

    public void setDataSwitchFlg(String dataSwitchFlg) {
        this.dataSwitchFlg = dataSwitchFlg;
    }

    public String getValidSwitchFlg() {
        return validSwitchFlg;
    }

    public void setValidSwitchFlg(String validSwitchFlg) {
        this.validSwitchFlg = validSwitchFlg;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
