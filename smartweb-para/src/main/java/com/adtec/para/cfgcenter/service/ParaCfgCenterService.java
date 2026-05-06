package com.adtec.para.cfgcenter.service;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.para.center.dao.ParaCacheDao;
import com.adtec.para.center.dao.ParaRedisNodeDao;
import com.adtec.para.center.dao.ParaZkNodeDao;
import com.adtec.para.cfgcenter.entity.*;
import com.adtec.para.common.constants.ParaConst;
import com.adtec.para.common.util.ParaZkClusterUtil;
import com.adtec.para.common.util.ParaZkNode;
import com.adtec.para.rules.dao.ParaRulesStgAuthDao;
import com.adtec.para.rules.dao.ParaRulesStgColDao;
import com.adtec.para.rules.dao.ParaRulesStgDao;
import com.adtec.para.rules.entity.ParaRulesStgAuthDO;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ParaCfgCenterService {
	
	private final static Log log = LogFactory.getLog(ParaCfgCenterService.class);
	private ParaCacheDao centerDao;
	private ParaRedisNodeDao centerRedisNodeDao;
	private ParaZkNodeDao centerZkNodeDao;
	private ParaRulesStgDao rulesStgDao;
	private ParaRulesStgColDao rulesStgColDao;
	private ParaRulesStgAuthDao rulesStgAuthDao;
	private CuratorFramework configClient;

	public ParaCfgCenterService(ParaCacheDao centerDao, ParaRedisNodeDao centerRedisNodeDao, ParaZkNodeDao centerZkNodeDao, ParaRulesStgDao rulesStgDao, ParaRulesStgColDao rulesStgColDao, ParaRulesStgAuthDao rulesStgAuthDao, CuratorFramework configClient) {
		this.centerDao = centerDao;
		this.centerRedisNodeDao = centerRedisNodeDao;
		this.centerZkNodeDao = centerZkNodeDao;
		this.rulesStgDao = rulesStgDao;
		this.rulesStgColDao = rulesStgColDao;
		this.rulesStgAuthDao = rulesStgAuthDao;
		this.configClient = configClient;
	}

	private final static String STATUS_RUN = "0";
	private final static String STATUS_STOP = "1";
	
	private final static String NOSYNC = "0";
	private final static String SYNC = "1";
	
	/**
	 * 同步配置信息
	 * @param reqDs
	 */
	public void infoRelease(String centerId) {
		try {
			// 获取缓存中心
			ParaCenterData center = centerDao.getCenterDataById(centerId);
			center.setRunStat("run");
			// 获取缓存中心存储规则
			List<ParaRulesStgData> list = rulesStgDao.getColDataByCenterId(centerId);
//			if (list == null || list.size() == 0) {
//				throw new BaseException(SysErr.E_MESSAGE, "缓存中心未分配存储规则");
//			}
			// 获取缓存中心Zookeeper节点信息
			List<ParaZkNodeData> zkNodeList = centerZkNodeDao.getZkDataByCenterId(centerId);
			// 获取缓存中心Redis节点信息
			List<ParaRedisNodeData> rsNodeList = centerRedisNodeDao.getRsDataByCenterId(centerId);
			List<ParaRedisNodeData> rsMainNodeList = Lists.newArrayList();
			Map<String, List<ParaRedisNodeData>> map = Maps.newHashMap();
			for (ParaRedisNodeData rsNode : rsNodeList) {
				if (DataUtil.isNullStr(rsNode.getMainNodeId()) || "0".equals(rsNode.getMainNodeId())) {
					rsMainNodeList.add(rsNode);
					continue;
				}
				List<ParaRedisNodeData> slaveNodeList = map.get(rsNode.getMainNodeId());
				if (slaveNodeList == null) {
					slaveNodeList = Lists.newArrayList();
				}
				slaveNodeList.add(rsNode);
				map.put(rsNode.getMainNodeId(), slaveNodeList);
			}
			for (ParaRedisNodeData rsNode : rsMainNodeList) {
				if (!DataUtil.isNullStr(rsNode.getMainNodeId()) && !"0".equals(rsNode.getMainNodeId())) {
					continue;
				}
				List<ParaRedisNodeData> slaveNodeList = map.get(rsNode.getId());
				if (slaveNodeList == null) {
					slaveNodeList = Lists.newArrayList();
				}
				rsNode.setSlaveCount(String.valueOf(slaveNodeList.size()));
				rsNode.setSlaveData(slaveNodeList);
			}
			center.setZkCluster(String.valueOf(zkNodeList.size()), zkNodeList);
			center.setRedisCluster(String.valueOf(rsMainNodeList.size()), rsMainNodeList);
			String centerNodeData = JSON.toJSONString(center);
			ParaZkClusterUtil.zkAddNode(configClient, ParaZkNode.CACHE_CENTERS + "/" + center.getEngName(), centerNodeData);

			for (ParaRulesStgData item : list) {
				String stgRuleTp = item.getStorgRuleTp();
				item.setStatus("run");
				List<ParaRulesStgColData> colList = rulesStgColDao.getColDataByStgId(item.getId());
				List<ParaRulesStgAuthDO> authList = rulesStgAuthDao.getListByStgId(item.getId());
				item.setColumn(colList);
				StringBuffer readAuthStr = new StringBuffer();
				StringBuffer writeAuthStr = new StringBuffer();
				for (ParaRulesStgAuthDO auth : authList) {
					readAuthStr.append(getAuthStr(stgRuleTp, auth));
				}
				item.setReadAuthList(readAuthStr.toString());
				item.setWriteAuthList(writeAuthStr.toString());
				String stgData = JSON.toJSONString(item);
				ParaZkClusterUtil.zkAddNode(configClient, ParaZkNode.STORAGE + "/" + center.getEngName() + "/" + item.getEngName(), stgData);
			}
//			centerDao.updateRunStat(centerId, Const.RunStat);
			centerDao.updateStat(centerId, ParaConst.RunStat.RUN, ParaConst.SyncFlg.SYNC);
			rulesStgDao.updateSyncFlgByCenterId(centerId, ParaConst.SyncFlg.SYNC);
		}catch (Exception e) {
			e.printStackTrace();
			throw new BaseException(SysErr.E_MESSAGE, e.getMessage());
		}
	}
	
	/**
	 * 同步存储规则
	 */
	public void syncStgs(String stgId, String zkStatus, String syncFlg) {
		ParaRulesStgData rulesStgData = rulesStgDao.getStgDataByStgId(stgId);
		String stgRuleTp = rulesStgData.getStorgRuleTp();
		rulesStgData.setStatus(zkStatus);
		List<ParaRulesStgColData> colList = rulesStgColDao.getColDataByStgId(stgId);
		List<ParaRulesStgAuthDO> authList = rulesStgAuthDao.getListByStgId(stgId);
		rulesStgData.setColumn(colList);
		StringBuffer readAuthStr = new StringBuffer();
		StringBuffer writeAuthStr = new StringBuffer(); 
		for (ParaRulesStgAuthDO auth : authList) {
			readAuthStr.append(getAuthStr(stgRuleTp, auth));
		}
		rulesStgData.setReadAuthList(readAuthStr.toString());
		rulesStgData.setWriteAuthList(writeAuthStr.toString());
		String stgData = JSON.toJSONString(rulesStgData);
//		ZkClusterUtil.zkAddNode(configClient, ZkNode.STORAGE+"/"+rulesStgData.getEngName(), stgData);
		ParaZkClusterUtil.zkAddNode(configClient, ParaZkNode.STORAGE + "/" + rulesStgData.getCacheCenter() + "/" + rulesStgData.getEngName(), stgData);
		rulesStgDao.updateSyncFlgByStgId(stgId, syncFlg);
	}

	private String getAuthStr(String stgRuleTp, ParaRulesStgAuthDO auth) {
		String str = "";
		if (ParaConst.ReadAuthTp.AUTH_READ.equals(auth.getRuleAuthTp())) {
			str = auth.getUseTntNo();
			if(!DataUtil.isNullStr(auth.getMembNo())){
				str += "_" + auth.getMembNo() + ",";
			}
		}
		return str;
	}

}
