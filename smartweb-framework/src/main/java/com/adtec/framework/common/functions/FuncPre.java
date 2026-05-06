package com.adtec.framework.common.functions;

import java.io.Serializable;

/**
 * 有参数有返回值且支持自定义异常类型的函数对象，此接口用于把一个函数包装成一个对象，从而传递函数
 *
 * @param <P> 参数类型
 * @param <R> 返回值
 * @param <E> 自定义异常
 * @author lijunbin
 */
public interface FuncPre<P, R, E extends Exception> extends Serializable {

    /**
     * 执行函数
     *
     * @param parameter 参数
     * @return 函数执行结果
     * @throws E 自定义异常
     */
    R call(P parameter) throws E;
}
