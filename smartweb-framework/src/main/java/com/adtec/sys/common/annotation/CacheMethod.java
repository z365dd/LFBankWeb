package com.adtec.sys.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 标记为缓存方法，方法的返回值会自动加入到缓存中.
 *
 * @author lijunbin
 */
@Target({METHOD})
@Retention(RUNTIME)
@Documented
public @interface CacheMethod {

    /**
     * 缓存类型
     *
     * @return 缓存类型
     */
    String cacheType();

    /**
     * 缓存的 key，可以使用 #{}表达式
     *
     * @return 缓存的key
     * Simplest usage is of form:
     * <pre>
     *      @CacheMethod(cacheType = LMP_DIM_CACHE, cacheKey = "#{id}")
     * 	    public DimDO get(String id) {
     *
     * 		}
     * </pre>
     */
    String cacheKey();

}
