package com.adtec.framework.interfaces.db.handler;

import com.adtec.framework.common.functions.FuncP;
import com.adtec.framework.interfaces.db.session.IDBSession;

/**
 * 事务函数
 *
 * @author lijunbin
 */
public interface TxHandler extends FuncP<IDBSession> {

    /**
     * 执行函数
     *
     * @param session DBSession
     * @throws Exception 自定义异常
     */
    @Override
    void call(IDBSession session) throws Exception;
}
