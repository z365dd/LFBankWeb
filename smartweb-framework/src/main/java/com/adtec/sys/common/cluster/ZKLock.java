package com.adtec.sys.common.cluster;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.DateUtil;
import com.adtec.quartz.TaskParam;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class ZKLock {
	private final static Logger logger = LoggerFactory.getLogger(ZKLock.class);
	@SuppressWarnings("unchecked")
	public static String getLock(String TaskName,String taskInterval, CuratorFramework curatorClient) {
		if(null==curatorClient){
			logger.error("ZKClient为空！");
			return null;
		}
		try {
			if (null == curatorClient.checkExists().forPath(TaskParam.BASE_TASK_PATH + "/" + TaskName)) {
				// 自动任务根节点不存在时创建
				curatorClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
						.forPath(TaskParam.BASE_TASK_PATH + "/" + TaskName);
			}else{
				byte[] b = curatorClient.getData().forPath(TaskParam.BASE_TASK_PATH + "/" + TaskName);
				String data = new String(b);
				Map<String, Object> map = (Map<String, Object>) JSON.parse(data);
				String lastDate = (String) map.get(TaskParam.LAST_DATE);
				long diff = DateUtil.diffDateTimeByMsecond(lastDate, DateUtil.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
				//String interval = ParamUtil.getInterval();
				if(Integer.parseInt(taskInterval)>Math.abs(diff)){
					//两次执行任务的间隔需要大于任务周期
					return null;
				}
			}
			List<String> taskList = curatorClient.getChildren().forPath(TaskParam.BASE_TASK_PATH + "/" + TaskName);
			if(null!=taskList&&!taskList.isEmpty()){
				//该自动任务已经有节点在执行
				return null;
			}else{
				String taskPath = curatorClient.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
				.forPath(TaskParam.BASE_TASK_PATH + "/" + TaskName+"/"+TaskName);
				return taskPath;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("ZKClient创建节点异常！");
			return null;
		}
	}
	
	public static void releaseLock(CuratorFramework curatorClient,String taskPath){
		try {
			if (!DataUtil.isNullStr(taskPath)) {
				if (null!=curatorClient.checkExists().forPath(taskPath)) {
					//删除该任务节点表示这次任务结束了
					curatorClient.delete().forPath(taskPath);
					String parent = taskPath.substring(0, taskPath.lastIndexOf("/"));
					JSONObject obj = new JSONObject();
					obj.put(TaskParam.LAST_DATE, DateUtil.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
					curatorClient.setData().forPath(parent, JSON.toJSONString(obj).getBytes());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("ZKClient删除节点异常！");
		}
	}
}
