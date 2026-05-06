/*
 * 系统名称: 
 * 模块名称: 
 * 文件名称: Convertor.java
 * 软件版权: 
 * 修改记录:
 * 修改日期            修改人员                     修改说明 <br>
 * ========    =======  ============================================
 * 
 * ========    =======  ============================================
 */

package com.adtec.framework.impl.share.dataset.convertor;

import com.adtec.framework.interfaces.share.IDataset;

/**
 * Dataset值转换器
 * <p>
 * 系统版本: v1.0<br>
 * 开发人员: chenyl <br>
 * 开发时间: 2016-9-20 <br>
 */
public interface Convertor<T>
{
	T convert(IDataset dataset, String name);
}
