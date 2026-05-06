package com.adtec.framework.interfaces.db.handler;

import com.adtec.framework.common.functions.FuncPr;
import com.adtec.framework.interfaces.db.session.IDBSession;

/**
 * @param <R> 返回值类型
 * @author lijunbin
 */
public interface TxHandlerR<R> extends FuncPr<IDBSession, R> {

    /**
     * 执行函数
     *
     * @param session DBSession
     * @return 函数执行结果
     * @throws Exception 自定义异常
     */
    @Override
    R call(IDBSession session) throws Exception;
}
