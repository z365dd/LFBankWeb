package com.adtec.sys.modules.sys.entity;

import com.adtec.framework.common.util.CollectionUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.sys.common.persistence.BaseDO;
import com.adtec.sys.common.persistence.Page;
import com.adtec.sys.common.utils.excel.annotation.ExcelField;
import com.adtec.sys.common.utils.excel.fieldtype.RoleListType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;
import java.util.Map;

/**
 * 用户Entity
 *
 * @version 2016-03-01
 */
public class User extends BaseDO {

    private static final long serialVersionUID = 1L;
    /**
     * 自定义SQL（SQL标识，SQL内容）
     */
    protected Map<String, String> sqlMap;
    private Office office;    // 归属机构
    private String loginName;// 登录名
    private String pwd;// 密码
    private String userNo;        // 工号
    private String name;    // 姓名
    private String email;    // 邮箱
    private String telNo;    // 电话
    private String phoneNo;    // 手机
    private String roleTp;// 角色类型
    private String roleTpStr; // 角色类型描述
    private String userLvl;
    private String loginIp;    // 最后登陆IP
    private String loginDate;    // 最后登陆日期
    private String loginSwitchFlg;    // 是否允许登陆
    private String img;    // 头像
    private String oldName;// 原登录名
    private String oldLoginName;// 原登录名
    private String newPassword;    // 新密码
    private String oldLoginIp;    // 上次登陆IP
    private String oldLoginDate;    // 上次登陆日期
    private String busiName;//商户名称
    private User user;    //根據用戶查詢用戶條件
    private Role role;    // 根据角色查询用户条件
    private Rent rent;    // 根据租户查询用户条件
    private Corporation corporation;
    private List<User> userList = Lists.newArrayList();    //擁有用戶列表
    private List<Role> roleList = Lists.newArrayList(); // 拥有角色列表
    //显示域
    private String value;
    private String label;

    /**
     * 当前实体分页对象
     */
    protected Page<User> page;

    public User() {
        super();
        this.loginSwitchFlg = ParamUtil.YES;
    }

    public User(String id) {
        super(id);
    }

    public User(String id, String loginName) {
        super(id);
        this.loginName = loginName;
    }

    public User(Role role) {
        super();
        this.role = role;
    }

    public User(Rent rent) {
        super();
        this.rent = rent;
    }

    public static boolean isAdmin(String id) {
        return id != null && "1".equals(id);
    }

    @Override
    @ExcelField(title = "创建时间", type = 0, align = 1, sort = 90)
    public String getCrtTime() {
        return crtTime;
    }

    @Email(message = "邮箱格式不正确")
    @Length(min = 0, max = 200, message = "邮箱长度必须介于 1 和 200 之间")
    @ExcelField(title = "邮箱", align = 1, sort = 50)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelField(title = "最后登录日期", type = 1, align = 1, sort = 110)
    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    public String getLoginSwitchFlg() {
        return loginSwitchFlg;
    }

    public void setLoginSwitchFlg(String loginSwitchFlg) {
        this.loginSwitchFlg = loginSwitchFlg;
    }

    @ExcelField(title = "最后登录IP", type = 1, align = 1, sort = 100)
    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    @Length(min = 1, max = 100, message = "登录名长度必须介于 1 和 100 之间")
    @ExcelField(title = "登录名", align = 2, sort = 30)
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getBusiName() {
        return busiName;
    }

    public void setBusiName(String busiName) {
        this.busiName = busiName;
    }

    @Length(min = 0, max = 200, message = "手机长度必须介于 1 和 200 之间")
    @ExcelField(title = "手机", align = 2, sort = 70)
    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    @Length(min = 1, max = 100, message = "姓名长度必须介于 1 和 100 之间")
    @ExcelField(title = "姓名", align = 2, sort = 40)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Length(min = 1, max = 100, message = "工号长度必须介于 1 和 100 之间")
    @ExcelField(title = "工号", align = 2, sort = 45)
    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    @JsonIgnore
    @NotNull(message = "归属机构不能为空")
    @ExcelField(title = "归属机构", align = 2, sort = 25)
    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public String getOldLoginDate() {
        if (oldLoginDate == null) {
            return loginDate;
        }
        return oldLoginDate;
    }

    public void setOldLoginDate(String oldLoginDate) {
        this.oldLoginDate = oldLoginDate;
    }

    public String getOldLoginIp() {
        if (oldLoginIp == null) {
            return loginIp;
        }
        return oldLoginIp;
    }

    public void setOldLoginIp(String oldLoginIp) {
        this.oldLoginIp = oldLoginIp;
    }

    public String getOldLoginName() {
        return oldLoginName;
    }

    public void setOldLoginName(String oldLoginName) {
        this.oldLoginName = oldLoginName;
    }


    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    @JsonIgnore
    @Length(min = 1, max = 100, message = "密码长度必须介于 1 和 100 之间")
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Length(min = 0, max = 200, message = "电话长度必须介于 1 和 200 之间")
    @ExcelField(title = "电话", align = 2, sort = 60)
    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    @ExcelField(title = "备注", align = 1, sort = 900)
    public String getRmrk() {
        return rmrk;
    }


    @JsonIgnore
    @ExcelField(title = "归属租户", align = 2, sort = 26)
    public Rent getRent() {
        return rent;
    }

    /**
     * @param rent the rent to set
     */
    public void setRent(Rent rent) {
        this.rent = rent;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @JsonIgnore
    @ExcelField(title = "归属法人", align = 2, sort = 27)
    public Corporation getCorporation() {
        return corporation;
    }

    public void setCorporation(Corporation corporation) {
        this.corporation = corporation;
    }

    @JsonIgnore
    public List<String> getRoleIdList() {
        List<String> roleIdList = Lists.newArrayList();
        for (Role role : roleList) {
            roleIdList.add(role.getId());
        }
        return roleIdList;
    }

    public void setRoleIdList(List<String> roleIdList) {
        roleList = Lists.newArrayList();
        if (null == roleIdList) {
            return;
        }
        for (String roleId : roleIdList) {
            Role role = new Role();
            role.setId(roleId);
            roleList.add(role);
        }
    }

    /*add by chenyl 20170425 for 租户列表*/

    @JsonIgnore
    @ExcelField(title = "拥有角色", align = 1, sort = 800, fieldType = RoleListType.class)
    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    /**
     * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
     */
    public String getRoleNames() {
        return CollectionUtil.extractToString(roleList, "name", ",");
    }

    @JsonIgnore
    @XmlTransient
    public Map<String, String> getSqlMap() {
        if (sqlMap == null) {
            sqlMap = Maps.newHashMap();
        }
        return sqlMap;
    }

    public void setSqlMap(Map<String, String> sqlMap) {
        this.sqlMap = sqlMap;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonIgnore
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public String getRoleTp() {
        return roleTp;
    }

    public void setRoleTp(String roleTp) {
        this.roleTp = roleTp;
    }

    public String getRoleTpStr() {
        return roleTpStr;
    }

    public void setRoleTpStr(String roleTpStr) {
        this.roleTpStr = roleTpStr;
    }

    public String getUserLvl() {
        return userLvl;
    }

    public void setUserLvl(String userLvl) {
        this.userLvl = userLvl;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isAdmin() {
        return isAdmin(this.id);
    }

    /**
     * 是否属于开发角色
     *
     * @return
     */
    public boolean isDeveloper() {
        boolean result = false;
        for (Role role : roleList) {
            if (role.getId() != null && "0".equals(role.getId())) {
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean isManager() {
        return isAdmin(this.id) || "manager".equals(this.roleTp);
    }

    @JsonIgnore
    @XmlTransient
    public Page<User> getPage() {
        if (page == null) {
            page = new Page<User>();
        }
        return page;
    }

    public Page<User> setPage(Page<User> page) {
        this.page = page;
        return page;
    }

    @Override
    public String toString() {
        return id;
    }

    // 用户拥有菜单
    private List<Menu> menuList = Lists.newArrayList();
    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public String getMenuIds() {
        List<String> menuIdList = Lists.newArrayList();
        for (Menu menu : menuList) {
            menuIdList.add(menu.getId());
        }
        return StringUtils.join(menuIdList, ",");
    }

}