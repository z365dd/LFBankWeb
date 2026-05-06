/*
 * 系统名称: 
 * 模块名称: 
 * 文件名称: DBSession.java
 * 软件版权:
 * 修改记录:
 * 修改日期      修改人员                     修改说明
 * ========    =======  ============================================
 *  
 * ========    =======  ============================================
 */
package com.adtec.framework.interfaces.db.sqlstatictics;

import java.util.Map;

/**
 * 
 * 功能说明: 存放待统计的列信息，调用addStatColumn(String column,String type)方法增加统计列信息。
 * 调用getStatColumns()方法获得所有列统计信息。
 * 系统版本: v1.0
 * 开发人员: 
 * 开发时间: 
 * 功能描述: 写明作用，调用方式，使用场景，以及特殊情况
 */
public interface IStatHolder {
	
	
	/**
	 * 
	 * 增加统计操作信息 如果对指定列进行多个统计操作，后者会覆盖前者。
	 * @param column 统计操作类名
	 * @param type 统计操作类型 目前支持sum、avg、count、max、min、stddev、variance大小写无关的
	 */
	public void addStatColumn(String column,String type);
	
	
	/**
     * 
     * 返回所有进行统计操作的列信息。
     * key:进行统计操作列名称
     * value：进行统计操作的名称 如sum、avg等
     * @return 以map结构保存所有的统计列信息
     */
    public Map<String, String> getStatColumns();
   
 
}
