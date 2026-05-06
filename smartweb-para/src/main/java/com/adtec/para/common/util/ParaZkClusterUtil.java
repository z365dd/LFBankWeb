package com.adtec.para.common.util;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.apache.zookeeper.CreateMode;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class ParaZkClusterUtil {
	
	/**
	 * 获取节点数据
	 * @param node	节点路径
	 * @return	返回字符串
	 */
    public static String getNodeData(CuratorFramework curatorClient, String node) {
        String path = node;
        byte[] buf = null;
        if (path != null && !path.isEmpty()) {
            try {
                if (curatorClient.checkExists().forPath(path) != null) {
                    // System.out.println("节点存在："+path);
                    buf = curatorClient.getData().forPath(path);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        
        String json = "";
        try {
        	// 转换成utf-8，并检查是否缺失"，
        	json = new String(new String(buf != null ? buf : "{}".getBytes()).getBytes(), "utf-8");
        	json = json.replace("?,\"", "\",\"");
        	json = "".equals(json)?"{}":json;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
    }
    
    public static List<String> getChildNode(CuratorFramework curatorClient, String node) {
    	String path = node;
    	List<String> childrenList = Lists.newArrayList();
    	if (null!=path && !path.isEmpty()) {
    		try {
                if (curatorClient.checkExists().forPath(path) != null) {
                    // System.out.println("节点存在："+path);
                	childrenList = curatorClient.getChildren().forPath(path);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
    	}
		return childrenList;
    }
    
    /**
	 * 根据存储规则名称删除zk节点
	 */
	public static void zkDelNode(CuratorFramework curatorClient, String path) {
		try {
			if(null != curatorClient.checkExists().forPath(path)) {
				curatorClient.delete().forPath(path);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 配置中心添加缓存中心信息
	 * @param path
	 * @param nodeData
	 */
	public static void zkAddNode(CuratorFramework curatorClient, String path, String nodeData) {

		try {
			if (curatorClient.checkExists().forPath(path) == null) {
				curatorClient.create().creatingParentContainersIfNeeded()
				.withMode(CreateMode.PERSISTENT)
				.forPath(path, nodeData.getBytes("UTF-8"));
			}else {
				curatorClient.setData().forPath(path, nodeData.getBytes("UTF-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 /**
     * 修改节点数据
     * @param node	节点路径
     * @param data	设置的数据字符串
     * @return	true-成功、false-失败
     */
	public static boolean updateNodeData(CuratorFramework curatorClient, String node, String data) {
        String path = node;
        boolean ret = false;
        if (path != null && !path.isEmpty()) {
            try {
                if (curatorClient.checkExists().forPath(path) != null) {
                    // System.out.println("节点存在："+path);
                    curatorClient.setData().forPath(path, data.getBytes("UTF-8"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            ret = true;
        }
        return ret;
    }
}
