package com.adtec.framework.ide.abstractutil;

import java.io.File;
import java.util.Map;

/**
 * @类名 JsLoadingUtil.java
 * @描述: 关于js编辑器的抽象
 * @版本 v1.0
 */
public abstract class AbstractJsNameChangeUtil extends AbstractJsGeneratorUtil {

	public abstract Map<String,File> JsNameChangeUtil(String oldPathUrl,String newPathUrlName,String jspPath);

}
