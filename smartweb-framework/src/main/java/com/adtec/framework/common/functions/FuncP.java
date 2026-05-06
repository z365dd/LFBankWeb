package com.adtec.framework.common.functions;

import java.io.Serializable;

/**
 * 带参数的函数对象，此接口用于把一个函数包装成一个对象，从而传递函数
 *
 * @param <P> 参数类型
 * @author lijunbin
 */
public interface FuncP<P> extends Serializable {

    /**
     * 执行函数
     *
     * @param parameter 参数
     * @throws Exception 异常
     */
    void call(P parameter) throws Exception;
}
