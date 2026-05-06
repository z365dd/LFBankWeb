package com.adtec.scheduler.core.rules.balance;

import java.util.List;

/**
 * 服务器调度接口.
 *
 * @author lijunbin
 */
public interface ServerBalanceDispatch {

    /**
     * 根据 server 列表选择 server
     *
     * @param servers server列表
     * @return 目标server
     */
    public String doSelect(List<String> servers);

}
