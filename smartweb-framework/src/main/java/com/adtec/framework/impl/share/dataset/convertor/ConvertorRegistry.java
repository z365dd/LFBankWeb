/*
 * 系统名称: 
 * 模块名称: 
 * 文件名称: ConvertorRegistry.java
 * 软件版权: 
 * 修改记录:
 * 修改日期            修改人员                     修改说明 <br>
 * ========    =======  ============================================
 * 
 * ========    =======  ============================================
 */

package com.adtec.framework.impl.share.dataset.convertor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 转换器容器
 * <p>
 * 系统版本: v1.0<br>
 * 开发人员: chenyl <br>
 * 开发时间: 2011-9-20 <br>
 */
public class ConvertorRegistry
{
	private static Map<Class<?>, Convertor<?>>	innerConverts	= new HashMap<Class<?>, Convertor<?>>();

	static {
		// 整型
		innerConverts.put(int.class, new IntConvertor());
		innerConverts.put(Integer.class, new IntConvertor());
		innerConverts.put(long.class, new LongConvertor());
		innerConverts.put(Long.class, new LongConvertor());
		innerConverts.put(short.class, new ShortConvertor());
		innerConverts.put(Short.class, new ShortConvertor());
		innerConverts.put(byte.class, new ByteConvertor());
		innerConverts.put(Byte.class, new ByteConvertor());
		// Boolean 类型
		innerConverts.put(boolean.class, new BooleanConvertor());
		innerConverts.put(Boolean.class, new BooleanConvertor());
		// char 类型
		innerConverts.put(char.class, new CharConvertor());
		innerConverts.put(Character.class, new CharConvertor());
		// 浮点型
		innerConverts.put(double.class, new DoubleConvertor());
		innerConverts.put(Double.class, new DoubleConvertor());
		innerConverts.put(float.class, new FloatConvertor());
		innerConverts.put(Float.class, new FloatConvertor());
		// String 类型
		innerConverts.put(String.class, new StringConvertor());
		// byte[] 类型
		innerConverts.put(byte[].class, new ByteArrayConvertor());
		// String[] 类型
		innerConverts.put(String[].class, new StringArrayConvertor());
		// Date 类型
		innerConverts.put(Date.class, new DateConvertor());
		// BigDecimal 类型
		innerConverts.put(BigDecimal.class, new BigDecimalConvertor());
		// AtomicInteger 类型
		innerConverts.put(AtomicInteger.class, new AtomicIntegerConvertor());
		// AtomicLong 类型
		innerConverts.put(AtomicLong.class, new AtomicLongConvertor());
	}

	/** 内置转换器 */
	private Map<Class<?>, Convertor<?>>			convertors		= new HashMap<Class<?>, Convertor<?>>();

	public ConvertorRegistry()
	{
		convertors.putAll(innerConverts);
	}

	public ConvertorRegistry(ConvertorRegistry other)
	{
		this();
		convertors.putAll(other.convertors);
	}

	/**
	 * 注册一个转换器
	 * @param clz
	 *            类
	 * @param convertor
	 *            转换器
	 * @return 前一个与类相关的转换器，如果没有则返回null
	 */
	public <T> Convertor<?> registerConvertor(Class<T> clz, Convertor<?> convertor)
	{
		return convertors.put(clz, convertor);
	}

	/**
	 * 获取与类相关的转换器
	 * @param clz
	 *            类class
	 * @return 与类相关的转换器，如果不存在则返回null
	 */
	public <T> Convertor<?> getConvertor(Class<T> clz)
	{
		Convertor<?> convertor = convertors.get(clz);
		if (convertor == null) {
			return new DefaultConvertor<T>(clz);
		} else {
			return convertor;
		}
	}
}
