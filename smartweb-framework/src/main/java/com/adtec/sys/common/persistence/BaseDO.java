package com.adtec.sys.common.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.DateUtil;
import com.adtec.sys.common.utils.IdGen;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.utils.UserUtils;

/**
 * @类名 BaseDO.java
 * @描述:
                    公共DO        
 * @版本 v1.0
 * @author ruanyh
 */
public class BaseDO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6618237489843468322L;
	/**
	 * 删除标记（0：正常；1：删除；2：审核；）
	 */
	public static final String DEL_FLAG_NORMAL = "0";
	public static final String DEL_FLAG_DELETE = "1";
	
	protected String id;	/*唯一标识ID*/
	protected String crtr;	/*创建者，默认当前操作用户，格式：yyyyMMddHHmmss*/
	protected String crtTime;	/*创建时间*/
	protected String uptr;	/*修改者*/
	protected String uptTime;	/*修改时间，默认当前操作用户，格式：yyyyMMddHHmmss*/
	protected String rmrk;	/*备注信息*/
	protected String delFlg; 	// 删除标记（0：正常；1：删除）
	protected int start = 1;	/*查询起始记录数，从1开始*/
	protected int limit = 0;	/*查询最大记录数，当为0时不分页*/
	
	public BaseDO(){
		this.delFlg = DEL_FLAG_NORMAL;
	}
	
	public BaseDO(String id) {
		super();
		this.id = id;
	}

	public String getCrtr() {
		return crtr;
	}

	public void setCrtr(String crtr) {
		this.crtr = crtr;
	}

	public String getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(String crtTime) {
		this.crtTime = crtTime;
	}

	public String getUptr() {
		return uptr;
	}

	public void setUptr(String uptr) {
		this.uptr = uptr;
	}

	public String getUptTime() {
		return uptTime;
	}

	public void setUptTime(String uptTime) {
		this.uptTime = uptTime;
	}

	public String getRmrk() {
		return rmrk;
	}

	public void setRmrk(String rmrk) {
		this.rmrk = rmrk;
	}

	public String getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}	
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public static String getDelFlagNormal() {
		return DEL_FLAG_NORMAL;
	}
	public static String getDelFlagDelete() {
		return DEL_FLAG_DELETE;
	}
	
	/**
	 * 插入之前执行方法，需要手动调用
	 */
	public void preInsert(){
		// 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
		if( DataUtil.isNullStr(this.id)){
			//如果id为空则默认使用uuid产生唯一主键
			setId(IdGen.uuid());
		}
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			this.crtr = user.getId();
			this.uptr = user.getId();
		}
		this.crtTime = DateUtil.getDateTime();
		this.uptTime = DateUtil.getDateTime();
	}
	
	/**
	 * 更新之前执行方法，需要手动调用
	 */
	public void preUpdate(){
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			this.uptr = user.getId();
		}
		this.uptTime = DateUtil.getDateTime();
	}
	
	/**
	 * 返回固定过滤的域列表
	 * @return
	 */
	public List<String> getIgnoreFields(){
		//设置过滤域
		List<String> ignoreFields = new ArrayList<String>();
		ignoreFields.add("serialVersionUID");
		ignoreFields.add("DEL_FLAG_NORMAL");
		ignoreFields.add("DEL_FLAG_DELETE");
		ignoreFields.add("start");
		ignoreFields.add("limit");
		return ignoreFields;
	}
	
	/**
	 * 返回固定的匹配域ID
	 * @return
	 */
	public List<String> getMatchFields(){
		//设置更新匹配条件
		List<String> matchField = new ArrayList<String>();
		matchField.add("id");
		return matchField;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseDO other = (BaseDO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
