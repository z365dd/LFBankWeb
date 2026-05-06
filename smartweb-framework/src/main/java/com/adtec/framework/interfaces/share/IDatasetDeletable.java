/*
 * 系统名称:
 * 模块名称: 
 * 类 名 称: IDatasetDeletable.java
 * 软件版权:
 * 相关文档:
 * 修改记录:
 * 修改日期      修改人员                     修改说明 <br>
 * ========     ======  ============================================
 *   
 * ========     ======  ============================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */

package com.adtec.framework.interfaces.share;

/**
 * 是否允许删除行或者列 <br>
 * 系统版本: v1.0 <br>
 * 开发人员: chenyl <br>
 * 开发时间: 2016-6-25 <br>
 * <br>
 */
public interface IDatasetDeletable
{
	void deleteLine(int rowIndex);
}
