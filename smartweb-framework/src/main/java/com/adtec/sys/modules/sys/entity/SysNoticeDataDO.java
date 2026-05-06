/**
* 系统名称: SmartWeb平台
* 模块名称: sys-modules的实体类模块
* 功能描述: 公告数据定义
* 类 名 称  : SysNoticeDataDO.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20190829<br>
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
 * 公告
 * @author zx
 * @version 20190829
 */
public class SysNoticeDataDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String noteId;		// notice_id
	private String dataId;		// data_id
	
	public SysNoticeDataDO() {
		super();
	}

	public SysNoticeDataDO(String id){
		super(id);
	}

	@Length(min=0, max=64, message="notice_id长度必须介于 0 和 64 之间")
	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

	@Length(min=0, max=64, message="data_id长度必须介于 0 和 64 之间")
	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("SysNoticeDataDO [ ");
		sb.append("noteId="+noteId+" , ");
		sb.append("dataId="+dataId+" , ");
		sb.append(" ] ");
		return sb.toString();
	}
	
	@Override
	public List<String> getIgnoreFields() {
		// TODO Auto-generated method stub
		List<String> list = super.getIgnoreFields();
		return list;
	}
}