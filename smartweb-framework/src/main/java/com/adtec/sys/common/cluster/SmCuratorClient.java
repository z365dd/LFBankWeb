package com.adtec.sys.common.cluster;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;

public class SmCuratorClient {
	public static CuratorFramework initCuratorClient() {
		CuratorFramework curatorClient = null;
		String flag = ParamUtil.getClusterFlag();
		if (check(flag)) {
			String interval = ParamUtil.getZKRetryInterval();
			String times = ParamUtil.getZKRetryTimes();
			String zkServerList = ParamUtil.getZKServerList();
			String sessionTimeout = ParamUtil.getZKSessionTimeOut();
			String connectionTimeout = ParamUtil.getZKConnectTimeOut();
			if (DataUtil.isNullStr(interval) || DataUtil.isNullStr(times) || DataUtil.isNullStr(zkServerList)
					|| DataUtil.isNullStr(sessionTimeout) || DataUtil.isNullStr(connectionTimeout)) {
				throw new BaseException(SysErr.E_MESSAGE, "smartweb的zookeeper参数缺失！");
			}
			int baseSleepTimeMs = 0;
			int maxRetries = 0;
			try {
				baseSleepTimeMs = Integer.parseInt(interval);
				maxRetries = Integer.parseInt(times);
			} catch (Exception e) {
				throw new BaseException(SysErr.E_MESSAGE, e, "smartweb的zookeeper重连配置参数格式异常！");
			}
			ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries);

			int sessionTimeoutMs = 0;
			int connectionTimeoutMs = 0;
			try {
				sessionTimeoutMs = Integer.parseInt(sessionTimeout);
				connectionTimeoutMs = Integer.parseInt(connectionTimeout);
			} catch (Exception e) {
				throw new BaseException(SysErr.E_MESSAGE, e, "smartweb的zookeeper连接配置参数格式异常！");
			}

			curatorClient = CuratorFrameworkFactory.newClient(zkServerList, sessionTimeoutMs, connectionTimeoutMs,
					retryPolicy);
			curatorClient.start();
		}
		return curatorClient;
	}

	public static boolean check(String taskRun) {
		if (taskRun != null || (null != taskRun && !taskRun.isEmpty())) {
			if (taskRun.toUpperCase().equals("Y")) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
