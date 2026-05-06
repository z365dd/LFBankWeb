package com.adtec.framework.common.functions;

import java.io.Serializable;

/**
 * 无参数有返回值的函数对象，此接口用于把一个函数包装成一个对象，从而传递函数
 *
 * @param <R> 返回值类型
 * @author lijunbin
 */
public interface FuncR<R> extends Serializable {
    /**
     * 执行函数
     *
     * @return 函数执行结果
     * @throws Exception 自定义异常
     */
    R call() throws Exception;
}
