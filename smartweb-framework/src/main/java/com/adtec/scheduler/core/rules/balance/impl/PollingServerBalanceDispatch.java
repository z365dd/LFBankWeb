package com.adtec.scheduler.core.rules.balance.impl;

import com.adtec.scheduler.core.rules.balance.ServerBalanceDispatch;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lijunbin
 */
public class PollingServerBalanceDispatch implements ServerBalanceDispatch {
    /**
     * 当前轮询索引
     */
    private final AtomicInteger index = new AtomicInteger(0);

    @Override
    public String doSelect(List<String> servers) {
        if (index.get() >= servers.size()) {
            index.set(0);
        }
        return servers.get(index.getAndIncrement());
    }
}
