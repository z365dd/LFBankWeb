package com.adtec.framework.ide.abstractutil;

/**
 * @类名 AbstractCstLoadingUtil.java
 * @描述: 关于cst编辑器的抽象
 * @作者 陈应龙
 * @创建时间 2016-02-26
 * @版本 v1.0
 */
public abstract class AbstractCstLoadingUtil extends AbstractCstGeneratorUtil {

	public abstract boolean setCstLoading(String content, String pathUrl);

	public abstract String getCstLoading(String pathUrl);

}
