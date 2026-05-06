package com.adtec.framework.interfaces.uiengine;

import com.adtec.framework.interfaces.share.IDataset;


/**
 * 功能说明: 对象解析转化为json字符串<br>
 * 系统版本: v1.0<br>
 * 开发人员: chenyl <br>
 * 开发时间: 2016-9-22<br>
 * 功能描述: 提供将不同类型的对象转化为json结构字符串<br>
 */

public interface JsonService {
	
	/**
	 * 树数据类型
	 */
	public static final String TREE_DATA_TYPE = "treeOutput";
	
	/**
	 * 二维集合数据类型
	 */
	public static final String COLLECTION_DATA_TYPE = "collectionOutput";
	/**
	 * 
	 * 将对象解析转化为普通json字符串
	 * @param object 被解析的对象
	 *               如：{'data':{id:'1',name:'tome'}},data为key值
	 * @return 转化后的json串
	 */
	public String parse(Object object);
	/**
	 * 
	 * 将对象解析转化为树结构json字符串
	 * @param object  被解析的对象
	 * @param mapping  用于映射树的(id、text、checked等属性)
	 * @return 转化后的json串
	 */
	public String parseTree(Object object, String mapping);
	
	/**
	 * 将IDataset转化为json字符串
	 * @param dataset 被解析的IDataset对象
	 * @param isMap 是否需要将IDataset对象转换为{}map(默认值为false)
	 *                如果某个IDataset中有一行数据,isMap为true则转化为{}结构，否则转换为[{}]结构,
	 *                当IDataset中有多行数据，isMap为true时，取第一行数据转为{}结构
	 * @return  转化后的json串
	 */
	public String parseDataset(IDataset dataset,boolean isMap);
}
