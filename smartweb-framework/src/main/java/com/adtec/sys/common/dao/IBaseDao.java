package com.adtec.sys.common.dao;

import java.util.List;

public interface IBaseDao<T> {

	/**
	 * 数据库插入
	 * @param objDO 数据对象DO
	 * @return 返回数量
	 */
	public int insert(T objDO);
	
	/**
	 * 数据库更新
	 * @param objDO 数据对象DO
	 * @return 返回数量
	 */
	public int update(T objDO);
	
	/**
	 * 数据库删除
	 * @param objDO
	 * @return 返回数量
	 */
	public int delete(T objDO);
	
	/**
	 * 数据库单笔查询
	 * @param objDO 数据对象DO
	 * @return DO对象
	 */
	public T get(T objDO);
	
	/**
	 * 数据库多笔查询
	 * @param objDO 数据对象DO
	 * @return List返回集合
	 */
	public List<T> list(T objDO);
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param objDO 数据对象DO
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @return List返回集合
	 */
	public List<T> list(T objDO, int start, int limit);
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @param param 查询参数
	 * @return List返回集合
	 */
	public List<T> list(int start, int limit, Object... param);
	
}
