/**
 * 
 */
package com.adtec.framework.ide.service;

import java.util.List;

import com.adtec.framework.ide.po.form;

/**
 * @类名 StructsNameService
 * @描述:
     TODO
 * @版本 v1.0
 */
public interface StructsNameService {
	/**
	 * 通过文件名获取文件中的action 的name
	 * @param example
	 * @return
	 * @throws Exception
	 */
	public List<form> getActionName(String xmlName) throws Exception;
}
