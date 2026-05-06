package com.adtec.framework.ide.abstractutil;

import java.io.File;
import java.util.Map;

/**
 * @类名 AbstractCstNameChangeUtil.java
 * @描述: 关于cst文件更改名字的抽象
 * @作者 陈应龙
 * @创建时间 2016-02-26
 * @版本 v1.0
 */
public abstract class AbstractCstNameChangeUtil {

	public abstract Map<String,File> CstNameChangeUtil(String oldPathUrl,String newPathUrlName,String jspPath);

}
