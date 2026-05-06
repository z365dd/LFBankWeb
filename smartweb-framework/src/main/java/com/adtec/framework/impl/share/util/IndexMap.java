/*
 * 系统名称: 
 * 模块名称: 
 * 类 名 称: IndexMap.java
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

package com.adtec.framework.impl.share.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Map的方式写，可以按照Map的方式取，也可以按照索引的方式取。此类的所有方法都不是线程不安全的，在使用的时候注意挑选。
 * <p>
 * 系统版本: v1.0 <br>
 * 开发人员: chenyl <br>
 * 开发时间: 2016-9-20 <br>
 * <br>
 */
public class IndexMap<T>
{
	private List<T>					values	= new ArrayList<T>();
	private Map<String, Integer>	mapping	= new HashMap<String, Integer>();

	/**
	 * 将一个名字与一个值相关联，如果名字已经存在，则替换值
	 * 
	 * @param name
	 *            名字
	 * @param t
	 *            值
	 */
	public T putAndReturn(String name, T t)
	{
		Integer index = mapping.get(name);
		if (index != null) {
			return values.set(index, t);
		} else {
			values.add(t);
			mapping.put(name, values.size() - 1);
			return null;
		}
	}

	public void put(String name, T t)
	{
		Integer index = mapping.get(name);
		if (index != null) {
			values.set(index, t);
		} else {
			values.add(t);
			mapping.put(name, values.size() - 1);
		}
	}

	public T remove(String name)
	{
		Integer index = mapping.remove(name);
		if (index != null) {
			T v = values.get(index);
			values.remove(index);
			return v;
		}
		return null;
	}

	/**
	 * 按照名字获取
	 * 
	 * @param name
	 *            字段名
	 * @return 值
	 */
	public T get(String name)
	{
		Integer index = mapping.get(name);
		if (index != null) {
			return values.get(index);
		} else {
			return null;
		}
	}

	/**
	 * 按照索引获取
	 * 
	 * @param index
	 *            索引
	 * @return 值
	 */
	public T get(int index)
	{
		return values.get(index);
	}

	/**
	 * 返回名字在集合中的索引，如果该名字不存在则返回 -1
	 * @param name
	 *            名字
	 * @return 索引
	 */
	public int getNameIndex(String name)
	{
		Integer index = mapping.get(name);
		if (index != null) {
			return index;
		} else {
			return -1;
		}
	}

	/**
	 * 获取容器的大小
	 * 
	 * @return 元素的大小
	 */
	public int size()
	{
		return mapping.size();
	}

	/**
	 * 清空
	 */
	public void clear()
	{
		values.clear();
		mapping.clear();
	}

	/**
	 * @return the values
	 */
	public List<T> getValues()
	{
		return values;
	}

	/**
	 * @return the mapping
	 */
	public Map<String, Integer> getMapping()
	{
		return mapping;
	}
}
