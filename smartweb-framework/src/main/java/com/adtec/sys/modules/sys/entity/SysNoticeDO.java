/**
 * 系统名称: SmartWeb平台
 * 模块名称: sys-modules的实体类模块
 * 功能描述: 公告消息数据定义
 * 类 名 称  : SysNoticeDO.java
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
package com.adtec.sys.modules.sys.entity;

import com.adtec.sys.common.persistence.BaseDO;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 公告消息
 *
 * @author z'x
 * @version 20190829
 */
public class SysNoticeDO extends BaseDO {

    private static final long serialVersionUID = 1L;
    private String noteTitle;        // title
    private String noteCntt;        // content
    private String noteScp;        // type
    private String ids;        // type
    private String url; // url
    private String popupFlg; // POP_FLG
    private String linkDesc; // LINK_DESC

    private Integer timeout;

    public SysNoticeDO() {
        super();
    }

    public SysNoticeDO(String id) {
        super(id);
    }

    public String getPopupFlg() {
        return popupFlg;
    }

    public void setPopupFlg(String popFlg) {
        this.popupFlg = popFlg;
    }

    public String getLinkDesc() {
        return linkDesc;
    }

    public void setLinkDesc(String linkDesc) {
        this.linkDesc = linkDesc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Length(min = 0, max = 100, message = "title长度必须介于 0 和 100 之间")
    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    @Length(min = 0, max = 2000, message = "content长度必须介于 0 和 2000 之间")
    public String getNoteCntt() {
        return noteCntt;
    }

    public void setNoteCntt(String noteCntt) {
        this.noteCntt = noteCntt;
    }

    @Length(min = 0, max = 1, message = "type长度必须介于 0 和 1 之间")
    public String getNoteScp() {
        return noteScp;
    }

    public void setNoteScp(String noteScp) {
        this.noteScp = noteScp;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("SysNoticeDO [ ");
        sb.append("id=" + id + " , ");
        sb.append("title=" + noteTitle + " , ");
        sb.append("noteCntt=" + noteCntt + " , ");
        sb.append("noteScp=" + noteScp + " , ");
        sb.append("crtr=" + crtr + " , ");
        sb.append("crtTime=" + crtTime + " , ");
        sb.append("uptr=" + uptr + " , ");
        sb.append("uptTime=" + uptTime + " , ");
        sb.append("rmrk=" + rmrk + " , ");
        sb.append("delFlg=" + delFlg + " , ");
        sb.append("url=" + url + " , ");
        sb.append("POPUP_FLG=" + popupFlg + " , ");
        sb.append("LINK_DESC=" + linkDesc + " , ");
        sb.append(" ] ");
        return sb.toString();
    }

    @Override
    public List<String> getIgnoreFields() {
        List<String> list = super.getIgnoreFields();
        list.add("ids");
        list.add("timeout");
        return list;
    }
}