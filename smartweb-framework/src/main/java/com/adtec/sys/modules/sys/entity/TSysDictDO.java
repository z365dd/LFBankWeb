package com.adtec.sys.modules.sys.entity;

import org.hibernate.validator.constraints.Length;
import java.util.ArrayList;
import java.util.List;
import com.adtec.sys.common.persistence.BaseDO;

/**
 * 页面参数
 * @author zh
 * @version 20200630
 */
public class TSysDictDO extends BaseDO {
	
	private static final long serialVersionUID = 1L;
	private String dictTp;		// 页面参数类型
	private String dictInfo;		// 页面参数描述
	private String dictLabel;		// 标签名称
	private String dictVal;		// 标签值
	private Long sort;		// 排序值
	private String parentId;		// parent_id
	
	public TSysDictDO() {
		super();
	}

	public TSysDictDO(String id){
		super(id);
	}

	@Length(min=0, max=100, message="页面参数类型长度必须介于 0 和 100 之间")
	public String getDictTp() {
		return dictTp;
	}

	public void setDictTp(String dictTp) {
		this.dictTp = dictTp;
	}
	
	@Length(min=0, max=100, message="页面参数描述长度必须介于 0 和 100 之间")
	public String getDictInfo() {
		return dictInfo;
	}

	public void setDictInfo(String dictInfo) {
		this.dictInfo = dictInfo;
	}
	
	@Length(min=0, max=256, message="标签名称长度必须介于 0 和 256 之间")
	public String getDictLabel() {
		return dictLabel;
	}

	public void setDictLabel(String dictLabel) {
		this.dictLabel = dictLabel;
	}
	
	@Length(min=0, max=256, message="标签值长度必须介于 0 和 256 之间")
	public String getDictVal() {
		return dictVal;
	}

	public void setDictVal(String dictVal) {
		this.dictVal = dictVal;
	}
	
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=64, message="parent_id长度必须介于 0 和 64 之间")
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("TSysDictDO [ ");
		sb.append("id="+id+" , ");
		sb.append("dictTp="+dictTp+" , ");
		sb.append("dictInfo="+dictInfo+" , ");
		sb.append("dictLabel="+dictLabel+" , ");
		sb.append("dictVal="+dictVal+" , ");
		sb.append("sort="+sort+" , ");
		sb.append("parentId="+parentId+" , ");
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
		list.add("delFlg");
		return list;
	}
	
	@Override
	public List<String> getMatchFields() {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		list.add("dictTp");
		return list;
	}
}