package com.adtec.framework.interfaces.db.handler;

import com.adtec.framework.interfaces.db.session.IDBSession;

/**
 * 错误处理接口
 *
 * @author lijunbin
 */
public interface ExceptionHandler {
    void apply(IDBSession session, Exception ex);
}
