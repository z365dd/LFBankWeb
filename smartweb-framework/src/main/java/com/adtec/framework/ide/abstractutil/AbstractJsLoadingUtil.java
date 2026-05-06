package com.adtec.framework.ide.abstractutil;

import com.adtec.framework.ide.abstractutil.AbstractJsGeneratorUtil;

/**
 * @类名 JsLoadingUtil.java
 * @描述: 关于js编辑器的抽象
 * @版本 v1.0
 */
public abstract class AbstractJsLoadingUtil extends AbstractJsGeneratorUtil {

	public abstract boolean setJsLoading(String content, String pathUrl);

	public abstract String getJsLoading(String pathUrl);

}
