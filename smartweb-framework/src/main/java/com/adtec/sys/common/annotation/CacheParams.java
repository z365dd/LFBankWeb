package com.adtec.sys.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 参数类型为 String时需要指定
 *
 * @author lijunbin
 */
@Target({PARAMETER})
@Retention(RUNTIME)
@Documented
public @interface CacheParams {
    String value();
}
