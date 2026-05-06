package com.adtec.sys.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 缓存注解：标注在查询的方法上，返回值自动加入到缓存，查询时优先从缓存拿数据
 *
 * @author chenyl
 * @version 1.0
 * @date 2019-10-17 16:37
 */
@Target({ METHOD })
@Retention(RUNTIME)
@Documented
public @interface GetCache {

    /**
     * 缓存类型
     * @return
     */
    String cacheType();

    /**
     * 缓存名称
     * @return
     */
    String cacheName();
    
    boolean useArgsAsCacheName() default false;

}
