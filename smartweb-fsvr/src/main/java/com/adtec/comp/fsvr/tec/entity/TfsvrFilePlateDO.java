package com.adtec.comp.fsvr.tec.entity;

import java.util.List;


public class TfsvrFilePlateDO {
	
	private List<TfsvrFilePlateDelDO> headList;//头尾模板列表
	
	private List<TfsvrFilePlateDelDO> bodyList;//体模板列表


	public List<TfsvrFilePlateDelDO> getHeadList() {
		return headList;
	}

	public void setHeadList(List<TfsvrFilePlateDelDO> headList) {
		this.headList = headList;
	}

	public List<TfsvrFilePlateDelDO> getBodyList() {
		return bodyList;
	}

	public void setBodyList(List<TfsvrFilePlateDelDO> bodyList) {
		this.bodyList = bodyList;
	}
	
}