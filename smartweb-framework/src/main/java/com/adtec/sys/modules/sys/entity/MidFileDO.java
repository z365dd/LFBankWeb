/**
* 系统名称: SmartWeb平台
* 模块名称: msmall-appsystem的实体类模块
* 功能描述: 文件中间表数据定义
* 类 名 称  : MidFileDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200106<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.sys.modules.sys.entity;

import java.util.List;

import com.adtec.sys.common.persistence.BaseDO;

/**
 * 文件中间表
 * @author zx
 * @version 20200106
 */
public class MidFileDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String resId;		// source_id
	private String fileId;		// file_id
	
	public MidFileDO() {
		super();
	}

	public MidFileDO(String id){
		super();
	}

	public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("MidFileDO [ ");
		sb.append("id="+id+" , ");
		sb.append("resId="+resId+" , ");
		sb.append("fileId="+fileId+" , ");
		sb.append("crtr="+crtr+" , ");
		sb.append("crtTime="+crtTime+" , ");
		sb.append("uptr="+uptr+" , ");
		sb.append("uptTime="+uptTime+" , ");
		sb.append("rmrk="+rmrk+" , ");
		sb.append(" ] ");
		return sb.toString();
	}
	
	@Override
	public List<String> getIgnoreFields() {
		List<String> list = super.getIgnoreFields();
		list.add("delFlg");
		return list;
	}
}