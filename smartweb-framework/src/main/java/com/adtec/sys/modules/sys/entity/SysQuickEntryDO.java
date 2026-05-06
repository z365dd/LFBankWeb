/**
* 系统名称: SmartWeb平台
* 模块名称: sys-modules的实体类模块
* 功能描述: 快捷菜单入口数据定义
* 类 名 称  : SysQuickEntryDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20190904<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.sys.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.adtec.sys.common.persistence.BaseDO;

import java.util.List;

/**
 * 快捷菜单入口
 * @author zx
 * @version 20190904
 */
public class SysQuickEntryDO extends BaseDO {
    
    private static final long serialVersionUID = 1L;
    private String userId;      // user_id
    private String menuId;      // menu_id
    private String menuName;        // menu_name
    private String pmenuId;     // pmenu_id
    private String pmenuName;       // pmenu_name
    private String path;        // path
    private String img;        // img
    
    public SysQuickEntryDO() {
        super();
    }

    public SysQuickEntryDO(String id){
        super(id);
    }

    @Length(min=1, max=64, message="user_id长度必须介于 1 和 64 之间")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    @Length(min=1, max=64, message="menu_id长度必须介于 1 和 64 之间")
    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
    
    @Length(min=1, max=64, message="menu_name长度必须介于 1 和 64 之间")
    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    
    @Length(min=1, max=64, message="pmenu_id长度必须介于 1 和 64 之间")
    public String getPmenuId() {
        return pmenuId;
    }

    public void setPmenuId(String pmenuId) {
        this.pmenuId = pmenuId;
    }
    
    @Length(min=1, max=64, message="pmenu_name长度必须介于 1 和 64 之间")
    public String getPmenuName() {
        return pmenuName;
    }

    public void setPmenuName(String pmenuName) {
        this.pmenuName = pmenuName;
    }
    
    @Length(min=0, max=100, message="path长度必须介于 0 和 100 之间")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    @Length(min=0, max=10, message="img长度必须介于 0 和 10 之间")
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("SysQuickEntryDO [ ");
        sb.append("id="+id+" , ");
        sb.append("userId="+userId+" , ");
        sb.append("menuId="+menuId+" , ");
        sb.append("menuName="+menuName+" , ");
        sb.append("pmenuId="+pmenuId+" , ");
        sb.append("pmenuName="+pmenuName+" , ");
        sb.append("path="+path+" , ");
        sb.append("crtr="+crtr+" , ");
        sb.append("crtTime="+crtTime+" , ");
        sb.append("uptr="+uptr+" , ");
        sb.append("uptTime="+uptTime+" , ");
        sb.append("rmrk="+rmrk+" , ");
        sb.append("delFlg="+delFlg+" , ");
        sb.append(" ] ");
        return sb.toString();
    }
    
    @Override
    public List<String> getIgnoreFields() {
        // TODO Auto-generated method stub
        List<String> list = super.getIgnoreFields();
        list.add("menuName");
        list.add("pmenuId");
        list.add("pmenuName");
        list.add("path");
        return list;
    }
}