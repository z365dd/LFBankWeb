package com.adtec.para.center.service;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.FileUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.impl.share.dataset.CommonDatasets;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.framework.interfaces.share.IDatasets;
import com.adtec.para.center.dao.ParaCacheDao;
import com.adtec.para.center.dao.ParaRedisNodeDao;
import com.adtec.para.center.dao.ParaZkNodeDao;
import com.adtec.para.center.entity.CenterRedisNodeDO;
import com.adtec.para.center.entity.CenterZkNodeDO;
import com.adtec.para.center.entity.ParaCacheDO;
import com.adtec.para.common.constants.ParaConst;
import com.adtec.para.common.util.ParaMapKey;
import com.adtec.para.common.util.ParaZkClusterUtil;
import com.adtec.para.common.util.ParaZkNode;
import com.adtec.para.rules.dao.ParaRulesStgDao;
import com.adtec.para.rules.entity.ParaRulesStgDO;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.apache.curator.shaded.com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class ParaCacheService {
	
	private final ParaCacheDao centerDao;
	private final ParaRedisNodeDao centerRedisNodeDao;
	private final ParaZkNodeDao centerZkNodeDao;
	private final ParaRulesStgDao rulesStgDao;
	private final CuratorFramework configClient;

	public ParaCacheService(ParaCacheDao centerDao, ParaRedisNodeDao centerRedisNodeDao, ParaZkNodeDao centerZkNodeDao, ParaRulesStgDao rulesStgDao, CuratorFramework configClient) {
		this.centerDao = centerDao;
		this.centerRedisNodeDao = centerRedisNodeDao;
		this.centerZkNodeDao = centerZkNodeDao;
		this.rulesStgDao = rulesStgDao;
		this.configClient = configClient;
	}

	private final static String STATUS_STOP = "1";
	private final static String YES = "yes";
	private final static String NO = "no";
	
	private final static String NOSYNC = "0";
	private final static String SYNC = "1";
	
	/**
	 * 缓存中心列表查询
	 * @param reqDs
	 * @return
	 */
	public IDataset listByPage(IDataset reqDs) {
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		ParaCacheDO centerDO = DatasetService.getInstace().getObject(reqDs, ParaCacheDO.class);
		List<ParaCacheDO> list = centerDao.listByPage(centerDO, start, limit);
		int total = centerDao.getTotal(centerDO);
		IDataset dataset = DatasetService.getInstace().getDataset(list, ParaCacheDO.class);
		dataset.setTotalCount(total);
		chgDict(dataset);
		return  dataset;
	}
	
	/**
	 * 新增缓存中心
	 * @param reqDs
	 */
	public void insert(IDataset reqDs) {
		ParaCacheDO centerDO = DatasetService.getInstace().getObject(reqDs, ParaCacheDO.class);
		centerDO.setRunStat("00"); // 默认状态：00-stop
		centerDO.setSyncFlg("N"); // 默认状态：00-stop
		centerDO.setDelFlg("N");
		centerDO.setZkTickTime("2000");
		centerDO.setZkInitLimVal("10");
		centerDO.setZkSyncLimVal("5");
		centerDO.setZkSnaResrvNum(3L);
		centerDO.setZkClnFreq(1L);
		centerDO.setZkConnMaxNum(60L);
		centerDO.setRsClstrSwitchFlg("Y");
		centerDO.setRsPrtctSwitchFlg("Y");
		centerDO.setRsTimeOutTime("10000");
		String folder = centerDO.getBasePath();
		String zkNode = reqDs.getString("zkJson");
		String rsNode = reqDs.getString("rsJson");
		Map<String, Object> zkMap = Maps.newHashMap();
		Map<String, Object> rsMap = Maps.newHashMap();
		try {
			zkMap = JSON.parseObject(zkNode, Map.class);
			rsMap = JSON.parseObject(rsNode, Map.class);
		} catch (Exception e) {
			throw new BaseException(SysErr.E_MESSAGE, "缓存中心信息填写有误，请检查");
		}
		
		IDBSession session = DBSessionFactory.getSession();
		try {
			session.beginTransaction();
			centerDO.preInsert();
			int rs = centerDao.insert(centerDO);
			if (rs == 1) {
				String centerId = centerDO.getId();
				if (zkMap != null && zkMap.size() != 0) {
					insertZkNote(zkMap, centerId, folder, session);
				} else {
					throw new BaseException(SysErr.E_MESSAGE, "ZooKeeper节点参数为空");
				}
				if (rsMap != null && rsMap.size() != 0) {
					insertRsNode(rsMap, centerId, folder, session);
				} else {
					throw new BaseException(SysErr.E_MESSAGE, "Redis节点参数为空");
				}
			}
			session.endTransaction();
		} catch (Exception e) {
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if (e instanceof SQLException){
				throw new BaseException(SysErr.E_MESSAGE, "新增缓存中心失败，请检查zookeeper与redis节点信息是否填写正确");
			}else {
				throw new BaseException(SysErr.E_MESSAGE, e.getMessage());
			}
			
		}
	}

	/**
	 * 插入redis节点
	 * @param rsMap
	 * @param centerId
	 * @param folder
	 * @param session
	 */
	private void insertRsNode(Map<String, Object> rsMap, String centerId, String folder, IDBSession session) {
		int num = rsMap.values().size();
		int i = 0;
		int totSlot = 16383;
		BigDecimal size = new BigDecimal(num);
		BigDecimal slot = new BigDecimal("16383");
		BigDecimal slotSize = slot.divide(size, 0, BigDecimal.ROUND_UP);
		for (Object obj : rsMap.values()) { // Redis主节点
			BigDecimal startSlot = slotSize.multiply(new BigDecimal(i));
			BigDecimal endSlot = startSlot.add(slotSize);
			int strSlotNum = Integer.valueOf(String.valueOf(startSlot));
			int endSlotNum = Integer.valueOf(String.valueOf(endSlot));
			if (i+1 == num) {
				endSlotNum = totSlot;
			}
			i++;
			String str = JSON.toJSONString(obj);
			CenterRedisNodeDO redisNodeDO = JSON.parseObject(str, CenterRedisNodeDO.class);
			if (redisNodeDO.getIp().isEmpty() || redisNodeDO.getPort() == 0){
				throw new BaseException(SysErr.E_MESSAGE, "Redis主节点地址，端口不能为空");
			}
			redisNodeDO.setMainNodeId("0");
			redisNodeDO.setStrSlotNum(strSlotNum);
			redisNodeDO.setEndSlotNum(endSlotNum);
			redisNodeDO.setCacheCentrId(centerId);
			redisNodeDO.setProgFilePath(redisNodeDO.getPidFile(redisNodeDO.getPort()));
			redisNodeDO.setLogFilePath(redisNodeDO.getLogFile(folder, redisNodeDO.getPort()));
			redisNodeDO.setClstrCfgFilePath(redisNodeDO.getClusterConfigFile(redisNodeDO.getPort()));
			redisNodeDO.preInsert();
			centerRedisNodeDao.insert(redisNodeDO);
			for (CenterRedisNodeDO slaveNodeDO : redisNodeDO.getSlave()) { // Redis从节点
				if (slaveNodeDO.getIp().isEmpty() || slaveNodeDO.getPort() == 0){
					throw new BaseException(SysErr.E_MESSAGE, "Redis从节点地址，端口不能为空");
				}
				if (slaveNodeDO.getPort() == redisNodeDO.getPort()) {
					throw new BaseException(SysErr.E_MESSAGE, "Redis主从节点端口不能相等");
				}
				slaveNodeDO.setCacheCentrId(centerId);
				slaveNodeDO.setMainNodeId(redisNodeDO.getId());
				slaveNodeDO.setProgFilePath(slaveNodeDO.getPidFile(slaveNodeDO.getPort()));
				slaveNodeDO.setLogFilePath(slaveNodeDO.getLogFile(folder, slaveNodeDO.getPort()));
				slaveNodeDO.setClstrCfgFilePath(slaveNodeDO.getClusterConfigFile(slaveNodeDO.getPort()));
				slaveNodeDO.preInsert();
				centerRedisNodeDao.insert(slaveNodeDO);
			}
		}
	}

	/**
	 * 插入zk节点
	 * @param zkMap
	 * @param centerId
	 * @param folder
	 * @param session
	 */
	private void insertZkNote(Map<String, Object> zkMap, String centerId, String folder, IDBSession session) {
		for (Object obj : zkMap.values()) { // ZooKeeper节点
			String str = JSON.toJSONString(obj);
			CenterZkNodeDO temp = JSON.parseObject(str, CenterZkNodeDO.class);
			if (temp.getIp().isEmpty() || temp.getPort()==0) {
				throw new BaseException(SysErr.E_MESSAGE, "ZooKeeper地址，各端口不能为空");
			}
			int electionPort = Integer.valueOf(String.valueOf(temp.getPort()) + "1");
			int commPort = Integer.valueOf(String.valueOf(temp.getPort()) + "8");
//			if (temp.getPort() ==  temp.getElectionPort() || temp.getElectionPort() == temp.getCommPort() || temp.getPort() == temp.getCommPort()) {
//				throw new BaseException(SysErr.E_MESSAGE, "ZooKeeper各端口不能相等");
//			}
			temp.setElectionPort(electionPort);
			temp.setCommPort(commPort);
			temp.setCacheCentrId(centerId);
			temp.setDataPath(temp.getDataDirPath(folder, temp.getZkId()));
			temp.setDataLogPath(temp.getDataLogDirPath(folder, temp.getZkId()));
			temp.preInsert();
			centerZkNodeDao.insert(temp);
		}
	}

	/**
	 * 获取缓存中心详情
	 * @param id
	 * @return
	 */
	public IDatasets get(String id) {
		IDatasets resDss = new CommonDatasets();
		// 获取缓存中心集群信息
		ParaCacheDO rs = centerDao.get(id);
		// 获取缓存中心Zookeeper节点信息
		List<CenterZkNodeDO> zkNodeList = centerZkNodeDao.getNodeByCenterId(id);
		// 获取缓存中心Redis节点信息
		List<CenterRedisNodeDO> rsNodeList = centerRedisNodeDao.getRsNodeByCenterId(id, "");
		List<CenterRedisNodeDO> slaveNodeList = Lists.newArrayList();
		for (CenterRedisNodeDO rsNode : rsNodeList){
//			slaveNodeList.addAll(centerRedisNodeDao.getRsNodeByCenterId(id, rsNode.getId()));
			if (!DataUtil.isNullStr(rsNode.getMainNodeId()) && !"0".equals(rsNode.getMainNodeId())) {
				slaveNodeList.add(rsNode);
			}
		}
		IDataset centerDs = DatasetService.getInstace().getDataset(rs, ParaCacheDO.class);
		centerDs.setDatasetName("centerDs");
		IDataset zkDs = DatasetService.getInstace().getDataset(zkNodeList, CenterZkNodeDO.class);
		zkDs.setDatasetName("zkDs");
		IDataset rsDs = DatasetService.getInstace().getDataset(rsNodeList, CenterRedisNodeDO.class);
		rsDs.setDatasetName("rsDs");
		IDataset rsSlaveDs = DatasetService.getInstace().getDataset(slaveNodeList, CenterRedisNodeDO.class);
		rsSlaveDs.setDatasetName("rsSlaveDs");
		
		resDss.putDataset(centerDs);
		resDss.putDataset(zkDs);
		resDss.putDataset(rsDs);
		resDss.putDataset(rsSlaveDs);
		return resDss;
	}
	

	/**
	 * 缓存中心更新
	 * @param reqDs
	 * @return
	 */
	public void update(IDataset reqDs) {
		ParaCacheDO centerDO = DatasetService.getInstace().getObject(reqDs, ParaCacheDO.class);
		centerDO.setId(reqDs.getString("centerId"));
		centerDO.setRunStat("00"); // 默认状态：00-stop
		centerDO.setSyncFlg("N"); // 默认状态：00-stop
		centerDO.setDelFlg("N");
		centerDO.setZkTickTime("2000");
		centerDO.setZkInitLimVal("10");
		centerDO.setZkSyncLimVal("5");
		centerDO.setZkSnaResrvNum(3L);
		centerDO.setZkClnFreq(1L);
		centerDO.setZkConnMaxNum(60L);
		centerDO.setRsClstrSwitchFlg("Y");
		centerDO.setRsPrtctSwitchFlg("Y");
		centerDO.setRsTimeOutTime("10000");
		String folder = centerDO.getBasePath();
		String zkNode = reqDs.getString("zkJson");
		String rsNode = reqDs.getString("rsJson");
		Map<String, Object> zkMap = Maps.newHashMap();
		Map<String, Object> rsMap = Maps.newHashMap();
		try {
			zkMap = JSON.parseObject(zkNode, Map.class);
			rsMap = JSON.parseObject(rsNode, Map.class);
		} catch (Exception e) {
			throw new BaseException(SysErr.E_MESSAGE, "缓存中心信息填写有误，请检查");
		}
		
		IDBSession session = DBSessionFactory.getSession();
		try {
			session.beginTransaction();
			centerDO.preUpdate();
			int rs = centerDao.update(centerDO);
			if (rs == 1) {
				String centerId = centerDO.getId();
				centerZkNodeDao.deleteByCenterId(centerId);
				centerRedisNodeDao.deleteByCenterId(centerId);
				if (zkMap != null && zkMap.size() != 0) {
					insertZkNote(zkMap, centerId, folder, session);
				} else {
					throw new BaseException(SysErr.E_MESSAGE, "ZooKeeper节点参数为空");
				}
				if (rsMap != null && rsMap.size() != 0) {
					insertRsNode(rsMap, centerId, folder, session);
				} else {
					throw new BaseException(SysErr.E_MESSAGE, "Redis节点参数为空");
				}
			}else {
				throw new BaseException(SysErr.E_MESSAGE, "修改缓存中心失败");
			}
			session.endTransaction();
		} catch (Exception e) {
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if (e instanceof SQLException){
				throw new BaseException(SysErr.E_MESSAGE, "新增缓存中心失败，请检查zookeeper与redis节点信息是否填写正确");
			}else {
				throw new BaseException(SysErr.E_MESSAGE, e.getMessage());
			}
		}
	}

	/**
	 * 停用缓存中心
	 * @param reqDs
	 */
	public void stop(IDataset reqDs) {
		String id = reqDs.getString("id");
		IDBSession session = DBSessionFactory.getSession();
		try {
			session.beginTransaction();
			centerDao.updateRunStat(id, ParaConst.RunStat.STOP);
			rulesStgDao.updateSyncFlgByCenterId(id, ParaConst.SyncFlg.UNSYNC);
			ParaCacheDO centerDO = centerDao.get(id);
			String path = ParaZkNode.CACHE_CENTERS + "/" + centerDO.getEngName();
			String json = ParaZkClusterUtil.getNodeData(configClient, path);
			Map<String, Object> centerMap = JSON.parseObject(json, new TypeReference<Map<String, Object>>() {
            });
			if (null!=centerMap && !centerMap.isEmpty()) {
				centerMap.put(ParaMapKey.STATUS, "stop");
			}
			String centerJson = JSON.toJSONString(centerMap);
			ParaZkClusterUtil.updateNodeData(configClient, path, centerJson);
		} catch (Exception e) {
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_MESSAGE, e.getMessage());
		} finally {
			try {
				session.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 列表字段转换
	 * @param dataset
	 */
	private void chgDict(IDataset dataset) {
		if (null == dataset) {
			return;
		}
		User user = UserUtils.getUser();
		String userId = user.getId();
		dataset.addColumn("runStat_str");
		dataset.addColumn("cacheMode_str");
		dataset.addColumn("action");
		dataset.beforeFirst();
		while(dataset.hasNext()){
			dataset.next();
			String runStat = dataset.getString("runStat");
			dataset.updateString("runStat_str", "00".equals(runStat)?"停止":"启用");
			
			String cacheMode = dataset.getString("cacheMode");
			dataset.updateString("cacheMode_str", "00".equals(cacheMode)?"ZooKeeper+Redis集群":"其他");
			
			StringBuilder action = new StringBuilder();
			action.append("	<a href=\"JavaScript:void(0);\" onClick=\"detail('" + dataset.getString("id") + "')\" >详情</a>");
			
			if ("00".equals(runStat)) {
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"update('" + dataset.getString("id") + "')\" >修改</a>");
			}else if ("01".equals(runStat)) {
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"stop('" + dataset.getString("id") + "')\" >停用</a>");
			}
			action.append("	<a href=\"JavaScript:void(0);\" onClick=\"expCfgFile('" + dataset.getString("id") + "')\" >导出配置文件</a>");
			action.append("	<a href=\"JavaScript:void(0);\" onClick=\"infoRelease('" + dataset.getString("id") + "')\" >同步配置信息</a>");

			dataset.updateString("action", action.toString());
		}
	}
	
	/**
	 * 生成配置文件
	 * @param reqDs
	 */
	public String genCfgFile(IDataset reqDs) {
		String redisPath = ParamUtil.getUploadFile()+"/cachecenter/cfg/redis.conf";
		String redisContent = getCfg(redisPath);
		String centerPath = ParamUtil.getUploadFile()+"/cachecenter/center/";
		FileUtil.delFile(centerPath);
		String id = reqDs.getString("id");
		ParaCacheDO center = centerDao.get(id);
		genZkCfgFile(center, centerPath);
		genRedisCfgFile(center, redisContent, centerPath);
		String filePath = centerPath + center.getEngName();
		String zipFilePath = centerPath + center.getEngName() + ".zip";
		FileUtil.zipFiles(filePath, "*", zipFilePath);
		return zipFilePath;
	}
	
	/**
	 * 生成Redis配置文件
	 * @param center
	 * @param redisContent
	 * @param centerPath
	 */
	private void genRedisCfgFile(ParaCacheDO center, String redisContent, String centerPath) {
		// 获取缓存中心Redis节点信息
		List<CenterRedisNodeDO> rsNodeList = centerRedisNodeDao.getNodeByCenterId(center.getId());
		StringBuffer redisNode = new StringBuffer();
		redisNode.append("cluster-node-timeout ").append(center.getRsTimeOutTime()).append("\r\n\r\n");
		redisNode.append("cluster-enabled ").append("Y".equals(center.getRsClstrSwitchFlg())?YES:NO).append("\r\n\r\n");
		redisNode.append("protected-mode ").append("Y".equals(center.getRsPrtctSwitchFlg())?YES:NO).append("\r\n\r\n");
		for (CenterRedisNodeDO rsNode : rsNodeList){
			StringBuffer rsNodeStr = new StringBuffer();
			rsNodeStr.append("bind ").append(rsNode.getIp()).append("\r\n\r\n");
			rsNodeStr.append("port ").append(rsNode.getPort()).append("\r\n\r\n");
			rsNodeStr.append("pidfile ").append(rsNode.getProgFilePath()).append("\r\n\r\n");
			rsNodeStr.append("logfile ").append(rsNode.getLogFilePath()).append("\r\n\r\n");
			rsNodeStr.append("cluster-config-file ").append(rsNode.getClstrCfgFilePath()).append("\r\n\r\n");
			rsNodeStr.append(redisNode);
			rsNodeStr.append(redisContent);
			String rsPath = centerPath + center.getEngName() + "/redis-cluster/" + rsNode.getIp() +"_"+ rsNode.getPort() + "/" + "redis.conf";
			FileUtil.writeToFile(rsPath, rsNodeStr.toString(), false);
		}
	}

	/**
	 * 生成Zookeeper配置文件
	 * @param center
	 * @param centerPath
	 */
	private void genZkCfgFile(ParaCacheDO center, String centerPath) {
		// 获取缓存中心Zookeeper节点信息
		List<CenterZkNodeDO> zkNodeList = centerZkNodeDao.getNodeByCenterId(center.getId());
		StringBuffer zkInfo = new StringBuffer();
		zkInfo.append("tickTime=").append(center.getZkTickTime()).append("\r\n");
		zkInfo.append("initLimit=").append(center.getZkInitLimVal()).append("\r\n");
		zkInfo.append("syncLimit=").append(center.getZkSyncLimVal()).append("\r\n");
		zkInfo.append("autopurge.purgeInterval=").append(center.getZkClnFreq()).append("\r\n");
		if (center.getZkSnaResrvNum() != 0) {
			zkInfo.append("autopurge.snapRetainCount=").append(center.getZkSnaResrvNum()).append("\r\n");
		}
		StringBuilder sb = new StringBuilder();
		for(CenterZkNodeDO item : zkNodeList) {
			sb.append("server.").append(item.getZkId()).append("=").append(item.getIp()).append(":").append(item.getCommPort()).append(":").append(item.getElectionPort()).append("\r\n");
		}
		zkInfo.append("maxClientCnxns=").append(center.getZkConnMaxNum()).append("\r\n");
		for (CenterZkNodeDO zkNodeDO : zkNodeList) {
			StringBuffer zkString = new StringBuffer();
			zkString.append(zkInfo).append("\r\n");
			zkString.append("dataDir=").append(zkNodeDO.getDataPath()).append("\r\n");
			zkString.append("dataLogDir=").append(zkNodeDO.getDataLogPath()).append("\r\n");
			zkString.append("clientPort=").append(zkNodeDO.getPort()).append("\r\n");
			zkString.append(sb.toString());
			String zkPath = centerPath + center.getEngName() + "/zkcluster/z" + zkNodeDO.getZkId() + "/" + "zoo.cfg";
			FileUtil.writeToFile(zkPath, zkString.toString(), false);
		}
	}

	/**
	 * 获取文件内容
	 * @param path
	 * @return
	 */
	private String getCfg(String path) {
        System.out.println("文件保存路径："+path);
		//开始解析文件
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            File file  = new File(path);
        	if (file.exists()) {//文件存在
				reader = new BufferedReader(new FileReader(file));
				String tempString = null;
				// 一次读入一行，直到读入null为文件结束
				while ((tempString = reader.readLine()) != null) {
					content.append(tempString + "\r\n");
				}
			}
        } catch (IOException e) {
                System.out.println("出现异常");
            } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
        }
        
        return content.toString();
	}
	
	/**
	 * 根据英文名称获取缓存中心数量
	 * @param engName
	 * @return
	 */
	public int getCenterByEngName(String engName) {
		ParaCacheDO centerDO = new ParaCacheDO();
		centerDO.setEngName(engName);
		return centerDao.getCenterSum(centerDO);
	}

	/**
	 * 根据状态获取缓存中心数量
	 * @return
	 */
	public int getCenterByStatus(String runStat, String id) {
		ParaCacheDO centerDO = new ParaCacheDO();
		centerDO.setId(id);
		centerDO.setRunStat(runStat);
		return centerDao.getCenterSum(centerDO);
	}

	/**
	 * 获取缓存中心总数
	 * @return
	 */
	public int getCenterSum(ParaCacheDO centerDO) {
//		CenterDO centerDO = new CenterDO();
		return centerDao.getCenterSum(centerDO);
	}

	public int getCenterByStgId(String stgId) {
		return centerDao.getCenterSumByStgId(stgId);
	}
	
	/**
	 * 删除缓存中心，先判断是否有存储规则，有则提示信息，无则删除
	 * @param reqDs
	 */
	public void del(IDataset reqDs) {
		String id = reqDs.getString("id");
		List<ParaRulesStgDO> list = rulesStgDao.getListByCenterId(id);
		if (list.size()>0) {
			throw new BaseException(SysErr.E_MESSAGE, "请先删除此缓存中心的存储规则后操作");
		}
		ParaCacheDO centerDO = new ParaCacheDO(id);
		ParaCacheDO qryDO = centerDao.get(id);
		centerDao.delete(centerDO);
		ParaZkClusterUtil.zkDelNode(configClient, ParaZkNode.CACHE_CENTERS +"/" + qryDO.getEngName());
	}
	
}
