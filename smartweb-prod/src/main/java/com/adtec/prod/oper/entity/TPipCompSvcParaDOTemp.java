/**
 * 系统名称: SmartWeb平台
 * 模块名称: comp.prod.oper的实体类模块
 * 功能描述: 服务属性表数据定义
 * 类 名 称  : TPipCompSvcParaDO.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 20200109<br>
 * 系统版本: V1.0.0<br>
 * * 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * <p>
 * ========     ======  ============================================
 */
package com.adtec.prod.oper.entity;

import com.adtec.sys.common.persistence.BaseDO;

/**
 * 服务属性表
 * @author zh
 * @version 20200109
 */
public class TPipCompSvcParaDOTemp extends BaseDO {

    private static final long serialVersionUID = 1L;
    private String KEY_NO;        // 键编号
    private String KEY_NAME;        // 键名称
    private String KV;        // 键名称

    public TPipCompSvcParaDOTemp() {
        super();
    }

    public String getKEY_NO() {
        return KEY_NO;
    }

    public void setKEY_NO(String KEY_NO) {
        this.KEY_NO = KEY_NO;
    }

    public String getKEY_NAME() {
        return KEY_NAME;
    }

    public void setKEY_NAME(String KEY_NAME) {
        this.KEY_NAME = KEY_NAME;
    }

    public String getKV() {
        return KV;
    }

    public void setKV(String KV) {
        this.KV = KV;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("TPipCompSvcParaDOTemp [ ");
        sb.append("KEY_NO=" + KEY_NO + " , ");
        sb.append("KEY_NAME=" + KEY_NAME + " , ");
        sb.append("KV=" + KV + " , ");
        sb.append(" ] ");
        return sb.toString();
    }

}