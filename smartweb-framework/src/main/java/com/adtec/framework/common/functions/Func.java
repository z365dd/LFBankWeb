package com.adtec.framework.common.functions;

import java.io.Serializable;

/**
 * 无参数无返回值的函数对象，此接口用于把一个函数包装成一个对象，从而传递函数
 *
 * @author lijunbin
 */
public interface Func extends Serializable {

    /**
     * 执行函数
     *
     * @throws Exception 自定义异常
     */
    void call() throws Exception;
}
