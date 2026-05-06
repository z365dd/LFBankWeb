package com.adtec.pay.dao;

import com.adtec.framework.interfaces.db.session.IDBSession;

public interface SmsSqlTask<R> {
    R task(IDBSession session) throws Exception;
}
