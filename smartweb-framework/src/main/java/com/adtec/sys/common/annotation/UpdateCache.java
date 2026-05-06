package com.adtec.sys.common.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ METHOD })
@Retention(RUNTIME)
@Documented
public @interface UpdateCache {

	/**
     * 缓存类型
     * @return
     */
    String cacheType();
}
