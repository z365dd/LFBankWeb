package com.adtec.para.chk.service;

import com.adtec.cache.cacheagent.util.SpringContextHolder;
import com.adtec.comm.protocol.tcp.TCPJsonServer;
import com.adtec.framework.common.util.CacheUtil;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.JsonUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.para.chk.entity.ParaAgentDO;
import com.adtec.para.chk.entity.ParaRedisDataDO;
import com.adtec.para.common.constants.ParaConst;
import com.adtec.para.common.util.*;
import com.adtec.para.rules.dao.ParaRulesStgAuthDao;
import com.adtec.para.rules.dao.ParaRulesStgDao;
import com.adtec.para.rules.entity.ParaRulesStgDO;
import com.adtec.para.rules.service.ParaRulesStgService;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.*;
import java.util.Map.Entry;

@Service
public class ParaChkAgentService {
	private final static Log log = LogFactory.getLog(ParaChkAgentService.class);
	@Autowired
	private CuratorFramework configClient;
	@Autowired
	private ParaRulesStgAuthDao rulesStgAuthDao;
	@Autowired
	private ParaRulesStgDao rulesStgDao;
	@Autowired
	private JedisCluster paraJedisCluster;
	@Autowired
	private ParaRulesStgService ruleStgService;
	
	public static final String AUTH_PUBLIC = "0";
	public static final String AUTH_USER_TENANT = "1";
	public static final String AUTH_TENANT = "2";
	public static final String AUTH_PART = "3";
	
	public static final String NOSYNC = "0";
	public static final String SYNC = "1";
	
	public static final String TRAN = "cacheUserDataService";
	public static final String SUBTRAN = "getRedisParamData";
	
	/**
	 * 获取agent列表
	 * @param reqDs
	 * @return
	 */
	public IDataset agentList(IDataset reqDs) {
		ParaAgentDO agentDO =  DatasetService.getInstace().getObject(reqDs, ParaAgentDO.class);
		String reqTenant = agentDO.getTenant();
		String reqPartId = agentDO.getPartId();
		String zkPath = ParaZkNode.PART_INST;
		// 20190827 modify by zengxj 修改agent列表查询bug
//		Map<String, List<String>> jsonMap = (Map<String, List<String>>) CacheUtil.get(MapKey.AGENT_INFO);
//		if (jsonMap == null) {
//			jsonMap = getAgentJson(zkPath);
//			CacheUtil.put(MapKey.AGENT_INFO, jsonMap);
//		}
		
		IDataset dataset = DatasetService.getInstace().getDataset();
//		Map<String, List<String>> jsonMap = (Map<String, List<String>>) CacheUtil.get(MapKey.CACHE_CENTER, MapKey.AGENT_INFO);
		Map<String, List<String>> jsonMap = Maps.newHashMap();
		try {
//			List<String> cacheAgentList = (List<String>) CacheUtil.get(MapKey.AGENT_LIST);
			List<String> cacheAgentList = Lists.newArrayList();
			List<String> children = configClient.getChildren().forPath(ParaZkNode.AGENT);
			if (null == cacheAgentList || cacheAgentList.size() == 0) {
				jsonMap = getAgentJson(ParaZkNode.PART_INST);
				CacheUtil.put(ParaMapKey.CACHE_CENTER, ParaMapKey.AGENT_INFO, jsonMap);
				CacheUtil.put(ParaMapKey.CACHE_CENTER, ParaMapKey.AGENT_LIST, children);
				log.debug("获取参与者列表成功");	
			}
			if (null != cacheAgentList && (!cacheAgentList.containsAll(children) || !children.containsAll(cacheAgentList))) {
				jsonMap = getAgentJson(zkPath);
				CacheUtil.put(ParaMapKey.CACHE_CENTER, ParaMapKey.AGENT_INFO, jsonMap);
				CacheUtil.put(ParaMapKey.CACHE_CENTER, ParaMapKey.AGENT_LIST, children);
				log.debug("获取参与者列表成功");
			}
			if (null == jsonMap || jsonMap.size() == 0) {
				jsonMap = getAgentJson(zkPath);
				CacheUtil.put(ParaMapKey.CACHE_CENTER, ParaMapKey.AGENT_INFO, jsonMap);
			}
			List<ParaAgentDO> agentList = Lists.newArrayList();
			if (null != reqTenant && !reqTenant.isEmpty()) {
				zkPath += "/"+reqTenant;
				if (null != reqPartId && !reqPartId.isEmpty()) {
					zkPath += "/"+reqPartId;
				}
			}
			for (Entry<String, List<String>> entry : jsonMap.entrySet()) {
				String key = entry.getKey();
				List<String> value = entry.getValue();
				String tenant = key.split("_")[0];
				String partId = key.split("_")[1];
				String partVersion = key.split("_")[2];
				String agentSeq = key.split("_")[3];
				String token = key.split("_")[4];
				String partInstSeq = key.split("_")[5];
				String access = key.split("_")[6];
				// 筛选租户
				if (null!=reqTenant && !reqTenant.isEmpty()) {
					if (null!=tenant && !tenant.equals(reqTenant)) {
						if (null!=reqPartId && !reqPartId.isEmpty()) {
							if (null!=partId && !partId.equals(reqPartId)) {
								continue;
							}
						}else {
							continue;
						}
					}
				}
				// 筛选参与者
				if (null!=reqPartId && !reqPartId.isEmpty()) {
					if (null!=partId && !partId.equals(reqPartId)) {
						continue;
					}
				}
				String tenantPart = rulesStgAuthDao.getTenantPartByEnname(tenant, partId);
				for (String json : value) {
					ParaAgentDO agent = JSON.parseObject(json, ParaAgentDO.class);
					if (DataUtil.isNullStr(agent.getIp())) {
						continue;
					}
					agent.setAgentSeq(agentSeq);
					agent.setTenant(tenant);
					agent.setPartId(partId);
					agent.setPartVersion(partVersion);
					agent.setTenantPart(tenantPart);
					agent.setToken(token);
					agent.setPartInstSeq(partInstSeq);
					agent.setAccess(access);
					agentList.add(agent);
				}
			}
			dataset = DatasetService.getInstace().getDataset(agentList, ParaAgentDO.class);
			chgDict(dataset);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		CacheUtil.remove(MapKey.CACHE_CENTER, MapKey.AGENT_INFO);
		return dataset;
	}
	
	/**
	 * 获取Agent本地缓存
	 * @param reqDs
	 * @return
	 */
	public IDataset agentDataDetail(IDataset reqDs) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		String agentKey = reqDs.getString("agentKey");
		String token = reqDs.getString("token");
		
		/*agentKey!@#rules!@#unixKey!@#uniqueVal*/
		String[] agentKeys = agentKey.split("!@#");
		String paramType = agentKeys[1];
		String uniqueKey = agentKeys[2];
		String[] unixKeys = uniqueKey.split(",");
		String uniqueVal = agentKeys[3];
		String[] ks = agentKeys[0].split("_");
//		String tenant = ks[0];
//		String partId = ks[1];
//		String partVersion = ks[2];
//		String agentSeq = ks[3];
		// 20191206 modify by zengxj ip和port以_分割
//		String ipAndPort = ks[4];
		String host = ks[4];
		int port = DataUtil.formatInt(ks[5]);
//		if (ipAndPort.indexOf(":") != -1){
//			host = ipAndPort.split(":")[0];
//			port = DataUtil.formatInt(ipAndPort.split(":")[1]);
//		}
		HashMap<String, Object> reqData = Maps.newHashMap();
		reqData.put(ParaMapKey.TOKEN, token);
		reqData.put(ParaMapKey.PARAMTYPE, paramType);
		Map<String, Object> map = Maps.newHashMap();
		for (int i=0;i<unixKeys.length;i++) {
			map.put(unixKeys[i], uniqueVal.split("_")[i]);
		}
		reqData.put(ParaMapKey.PARAMS, map);
		HashMap<String, Object> retMap = Maps.newHashMap();
		try {
			retMap = TCPJsonServer.sendTcpJson(host, port, "", TRAN, SUBTRAN, reqData);
		}catch (Exception e){
			String error = "访问缓存代理数据失败：IP["+host+"], PORT["+port+"]";
			throw new BaseException(SysErr.E_MESSAGE, error);
		}
		List<ParaRedisDataDO> list = Lists.newArrayList();
		if(null!=retMap && "SUCCESS".equals((String)retMap.get(ParaMapKey.RETCODE))){
			List<Map<String, Object>> paramsList = (List<Map<String,Object>>)retMap.get(ParaMapKey.PARAMS);
			for(Map<String,Object> pMap : paramsList){
				for(Entry<String,Object> entry : pMap.entrySet()){
					ParaRedisDataDO item = new ParaRedisDataDO();
					item.setKey(entry.getKey());
					if (null!=entry.getValue()) {
						if(entry.getValue().toString().indexOf("{") != -1) {
							String json = JSON.toJSONString(entry.getValue().toString());
							item.setObject(json);
						} else {
							item.setObject(entry.getValue().toString());
						}
					} else {
						item.setObject("null");
					}
					list.add(item);
				}
			}
			resDs = DatasetService.getInstace().getDataset(list, ParaRedisDataDO.class);	
		}else {
			throw new BaseException(""+retMap.get(ParaMapKey.RETCODE)+":"+retMap.get(ParaMapKey.MSG));
		}
//		getAgentDataDetail(tenant, partId, partVersion, agentSeq, unixKeys, uniqueVal, paramType);
		
		return resDs;
	}
	
	/**
	 * 
	 * @param tenant
	 * @param partId
	 * @param partVersion
	 * @param agentSeq
	 * @param unixKeys
	 * @param uniqueVal
	 * @param paramType
	 */
	private void getAgentDataDetail(String tenant, String partId, String partVersion, String agentSeq, String[] unixKeys, String uniqueVal, String paramType) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		String path = ParaZkNode.PART_INST +"/" + tenant + "/" + partId + "/" + partVersion;
		Map<String, Object> partInstInfo = Maps.newHashMap();
		List<String> partInstList = Lists.newArrayList();
		try {
			partInstList = configClient.getChildren().forPath(path);
		} catch (Exception e) {
			throw new BaseException(SysErr.E_DEFAULT);
		}
		for (String partInst : partInstList) {
			String partInstJson = ParaZkClusterUtil.getNodeData(configClient, path + "/" + partInst);
			partInstInfo = JSON.parseObject(partInstJson, new TypeReference<Map<String, Object>>() {
	        });
			if (null != agentSeq && agentSeq.equals(partInstInfo.get(ParaMapKey.AGENT_SEQ))){
				String token = ParaEntryptUtil.aesDecrypt((String) partInstInfo.get(ParaMapKey.TOKEN));
				String agentJson = ParaZkClusterUtil.getNodeData(configClient, ParaZkNode.AGENT+"/"+agentSeq);
				Map<String, Object> agentInfo = JSON.parseObject(agentJson, new TypeReference<Map<String, Object>>() {
		        });
				String host = (String) agentInfo.get(ParaMapKey.IP);
				int port = DataUtil.formatInt(agentInfo.get(ParaMapKey.PORT));
				String tran = "cacheUserDataService";
				String subTran = "getRedisParamData";
				HashMap<String, Object> reqData = Maps.newHashMap();
				reqData.put(ParaMapKey.TOKEN, token);
				reqData.put(ParaMapKey.PARAMTYPE, paramType);
				Map<String, Object> map = Maps.newHashMap();
				for (int i=0;i<unixKeys.length;i++) {
					map.put(unixKeys[i], uniqueVal.split("_")[i]);
				}
				reqData.put(ParaMapKey.PARAMS, map);
				HashMap<String, Object> retMap = Maps.newHashMap();
				try {
					retMap = TCPJsonServer.sendTcpJson(host, port, "", tran, subTran, reqData);
				}catch (Exception e){
					String error = "访问缓存代理数据失败：IP["+host+"], PORT["+port+"]";
					throw new BaseException(SysErr.E_MESSAGE, error);
				}
				List<ParaRedisDataDO> list = Lists.newArrayList();
				if(null!=retMap && "SUCCESS".equals((String)retMap.get(ParaMapKey.RETCODE))){
					List<Map<String, Object>> paramsList = (List<Map<String,Object>>)retMap.get(ParaMapKey.PARAMS);
					for(Map<String,Object> pMap : paramsList){
						StringBuffer retKey = new StringBuffer();
						StringBuffer retValue = new StringBuffer();
						for(Entry<String,Object> entry : pMap.entrySet()){
							ParaRedisDataDO item = new ParaRedisDataDO();
							item.setKey(entry.getKey());
							if (null!=entry.getValue()) {
								if(entry.getValue().toString().indexOf("{") != -1) {
									String json = JSON.toJSONString(entry.getValue().toString());
									item.setObject(json);
								} else {
									item.setObject(entry.getValue().toString());
								}
							} else {
								item.setObject("null");
							}
							
							list.add(item);
						}
					}
					resDs = DatasetService.getInstace().getDataset(list, ParaRedisDataDO.class);	
				}else {
					throw new BaseException(""+retMap.get(ParaMapKey.RETCODE)+":"+retMap.get(ParaMapKey.MSG));
				}
			}
		}
	}

	/**
	 * 获取存储规则列表
	 * @param reqDs
	 * @return
	 */
	public IDataset ruleListByPage(IDataset reqDs) {
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		ParaRulesStgDO rulesStgDO = DatasetService.getInstace().getObject(reqDs, ParaRulesStgDO.class);
		rulesStgDO.setSyncFlg("Y");
		List<ParaRulesStgDO> list = rulesStgDao.listByPage(rulesStgDO, start, limit);
		int total = rulesStgDao.getTotal(rulesStgDO);
		IDataset dataset = DatasetService.getInstace().getDataset(list, ParaRulesStgDO.class);
		dataset.setTotalCount(total);
		chgRulesDict(dataset);
		return dataset;
	}
	
	/**
	 * 获取现在运行的Agent信息
	 * @param path
	 * @return
	 */
	public Map<String, List<String>> getAgentJson(String path) {
		Map<String, List<String>> jsonMap = Maps.newHashMap();
		Map<String, List<String>> jsonMap2 = Maps.newHashMap();
		List<String> jsonList = Lists.newArrayList();
		try {
			if (configClient == null) {
				configClient = SpringContextHolder.getBean("configClient");
			}
			List<String> children = configClient.getChildren().forPath(path);
			if (children.size() > 0) {
				for (String child : children) {
					if ("HealthTest".equals(child)) {
						continue;
					}
					String childPath = path + "/" + child;
					jsonMap2 = getAgentJson(childPath);
					jsonMap.putAll(jsonMap2);
				}
			}else {
				// 未接入
				String access = "0";
				String[] str = path.split("/");
				String tenant = str[3];
				String partId = str[4];
				String version = "$";
				String partInstSeq = "$";
				if (str.length > 5) {
					version = str[5];
					if (str.length>6) {
						partInstSeq = str[6];
					}
				}
				String tenantPart = tenant+"_"+partId;
				String partInstJson = ParaZkClusterUtil.getNodeData(configClient, path);
				//System.out.println(partInstJson);
				Map<String, Object> partInstInfo = JSON.parseObject(partInstJson, new TypeReference<Map<String, Object>>() {
                });
				if (null!=partInstInfo.get(ParaMapKey.AGENT_SEQ) && !((String) partInstInfo.get(ParaMapKey.AGENT_SEQ)).isEmpty()) {
					if (null != configClient.checkExists().forPath(ParaZkNode.AGENT+"/"+partInstInfo.get(ParaMapKey.AGENT_SEQ))) {
						String token = ParaEntryptUtil.aesDecrypt((String) partInstInfo.get(ParaMapKey.TOKEN));
						//System.out.println(tenant+"_"+partId+"_"+version+"_"+partInstInfo.get(MapKey.AGENT_SEQ));
						String agentJson = ParaZkClusterUtil.getNodeData(configClient, ParaZkNode.AGENT+"/"+partInstInfo.get(ParaMapKey.AGENT_SEQ));
						if (-1 == agentJson.indexOf("CacheHost")) {
							String tmp = agentJson.substring(0,agentJson.lastIndexOf("}"));
							String ip = (String) partInstInfo.get(ParaMapKey.HOSTNAME);
							String port = (String) partInstInfo.get(ParaMapKey.PORT);
							tmp += ",\"Ip\":\""+ip+"\",\"Port\":\""+port+"\"}";
							agentJson = tmp;
						} else {
							access = "1";
							agentJson = agentJson.replace("CacheHost", "Ip");
							agentJson = agentJson.replace("CachePort", "Port");
						}
						jsonList.add(agentJson);
						jsonMap.put(tenantPart+"_"+version+"_"+partInstInfo.get(ParaMapKey.AGENT_SEQ)+"_"+token+"_"+partInstSeq+"_"+access, jsonList);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("获取参与者列表失败");
		}
		return jsonMap;
	}
	
	/**
	 * 根据存储规则英文名称获取数据
	 * @param reqDs
	 * @return
	 */
	public IDataset dataList(IDataset reqDs) {
		String engName = reqDs.getString("engName");
		String unixKey = reqDs.getString("unixKey");
		List<ParaRedisDataDO> redisDataList = Lists.newArrayList();
		Set<String> paramKeys = keys("/PARAM_DATA/*/"+engName+"/*" + unixKey + "*");
		Set<String> cacheKeys = keys("/CACHE_DATA/*/"+engName+"/*" + unixKey + "*");
		// /PARAM_DATA/PUBLIC/ParaPipChkRule/999302/0000000041
		for (String rsKey : paramKeys) {
			String key = rsKey.substring(0, rsKey.lastIndexOf("/"));
			String uKey = rsKey.substring(rsKey.indexOf(engName)+engName.length()+1, rsKey.lastIndexOf("/") );
			String cacheSeq = rsKey.substring(rsKey.lastIndexOf("/")+1);
			// {999777_3333={HEIGHT=180, SID=3333, CLASSID=999777, SNAME=XX组件}, 999333_7777={HEIGHT=185, SID=7777, CLASSID=999333, SNAME=YY组件}}
			ParaRedisDataDO redisData = new ParaRedisDataDO();
			redisData.setRedisKey(key);
			redisData.setUnixKey(uKey);
			redisData.setCacheSeq(cacheSeq);
			redisDataList.add(redisData);
		}
		for (String rsKey : cacheKeys) {
			String key = rsKey.substring(0, rsKey.lastIndexOf("/"));
			String uKey = rsKey.substring(rsKey.indexOf(engName)+engName.length()+1, rsKey.lastIndexOf("/") );
			String cacheSeq = rsKey.substring(rsKey.lastIndexOf("/")+1);
			// {999777_3333={HEIGHT=180, SID=3333, CLASSID=999777, SNAME=XX组件}, 999333_7777={HEIGHT=185, SID=7777, CLASSID=999333, SNAME=YY组件}}
			ParaRedisDataDO redisData = new ParaRedisDataDO();
			redisData.setRedisKey(key);
			redisData.setUnixKey(uKey);
			redisData.setCacheSeq(cacheSeq);
			redisDataList.add(redisData);
		}
		IDataset resDs = DatasetService.getInstace().getDataset(redisDataList, ParaRedisDataDO.class);
		chgRedisDataDict(resDs);
		return resDs;
	}
	
	/**
	 * 根据Key获取reids中的数据
	 * @param reqDs
	 * @return
	 */
	public IDataset getData(IDataset reqDs) {
		String redisKey = reqDs.getString("redisKey");
		List<ParaRedisDataDO> redisDataList = Lists.newArrayList();
		Set<String> keys = keys(redisKey+"*");
		for (String rsKey : keys) {
			Map<String, Object> pMap = getMap(rsKey);
			for (Entry<String, Object> entry : pMap.entrySet()) {
				String key = entry.getKey();
				ParaRedisDataDO redisData = new ParaRedisDataDO();
				redisData.setKey(key);
				if (null != pMap.get(key)) {
					if (pMap.get(key).toString().indexOf("{") != -1) {
						String json = JSON.toJSONString(pMap.get(key));
						redisData.setObject(json);
					}else {
						redisData.setObject(pMap.get(key).toString());
					}
				}else {
					redisData.setObject("null");
				}
				redisDataList.add(redisData);
			}
		}
		IDataset resDs = DatasetService.getInstace().getDataset(redisDataList, ParaRedisDataDO.class);
		return resDs;
	}

	/**
	 * 根据pattern 获取所有的keys
	 * @param pattern
	 * @return
	 */
	public TreeSet<String> keys(String pattern) {
		// TODO Auto-generated method stub
		TreeSet<String> keys = new TreeSet<String>();
		Map<String, JedisPool> clusterNodes = paraJedisCluster.getClusterNodes();
		for (String k : clusterNodes.keySet()) {
			// log.info("Getting keys from: " + k);
			JedisPool jp = clusterNodes.get(k);
			Jedis connection = jp.getResource();
			try {
				keys.addAll(connection.keys(pattern));
			} catch (Exception e) {
				System.out.println("操作失败");
			} finally {
				// log.info("Connection closed.");
				connection.close();// 用完一定要close这个链接！！！
			}
		}
		return keys;
	}
	
	/**
	 * 根据key获取存储在redis中的自定义Map对象
	 * @param key
	 * @return
	 */
	public <T> Map<String, T> getMap(String key) {
		if (null == key || (null != key && key.isEmpty())) {
			return null;
		}
		byte[] in = paraJedisCluster.get(key.getBytes());
		Map<String, T> map = (Map<String, T>) ParaObjectTranscoder.deserialize(in);
		return map;
	}
	
	/**
	 * 转换列表字典
	 * @param dataset
	 */
	private void chgDict(IDataset dataset) {
		if (null == dataset) {
			return;
		}
		// 加入租户判断可读存储规则
		List<String> rents = Lists.newArrayList();
		User user = UserUtils.getUser();
		rents.add(user.getRent().getEngName());
		rents.addAll(ruleStgService.findChildRentsByRentEnname(rents, user.getRent().getEngName()));
		dataset.addColumn("status_str");
		dataset.addColumn("tenant_str");
		dataset.addColumn("partId_str");
		dataset.addColumn("access_str");
		dataset.addColumn("addr");
		dataset.addColumn("agentKey");
		dataset.addColumn("action");
		dataset.beforeFirst();
		while(dataset.hasNext()){
			boolean existIp = false;
			dataset.next();
			String token = dataset.getString("token");
			String status = dataset.getString("status");
			String access = dataset.getString("access");
			String tenantPart = dataset.getString("tenantPart");
			
			String tenant = dataset.getString("tenant");
			String tenantStr = tenantPart.split("_")[0];
			String partIdStr = tenantPart.split("_")[1];
			
			String addr = "";
			if (!dataset.getString("ip").isEmpty() && !dataset.getString("port").isEmpty()) {
				addr = dataset.getString("ip")+"_"+dataset.getString("port");
				dataset.updateString("addr", addr);
				existIp = true;
			}else {
				dataset.updateString("addr", addr);
			}
			dataset.updateString("status_str", "run".equals(status)?"运行中":"已停止");
			dataset.updateString("access_str", "1".equals(access)?"是":"否");
			dataset.updateString("tenant_str", tenantStr);
			dataset.updateString("partId_str", partIdStr);
			String agentKey = dataset.getString("tenant") + "_" + dataset.getString("partId") + "_" + dataset.getString("partVersion") + "_" + dataset.getString("agentSeq") + "_" + addr;
			dataset.updateString("agentKey", agentKey);
			StringBuffer action = new StringBuffer();
			
			if (existIp) {
//				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"getRules('" + agentKey + "', '"+token+"')\" >查询</a>");
//				if (!user.isAdmin() && tenant!=null && !rents.contains(tenant)) {
//					continue;
//				} else {
//					action.append("<a href=\\\"javascript:;\\\" onClick=\\\"getRules('"+agentKey+"','"+token+"')\\\">详细数据</a>");
//				}
				if ("1".equals(access)) {
					action.append("<a href=\\\"javascript:;\\\" onClick=\\\"getRules('"+agentKey+"','"+token+"')\\\">详细数据</a>");
				}
			}
			dataset.updateString("action", action.toString());
		}
	}

	/**
	 * 转换存储规则列表字段
	 * @param dataset
	 */
	private void chgRulesDict(IDataset dataset) {
		if (null == dataset) {
			return;
		}

		boolean agentChk = false;
		if (dataset.getString("agentKey") != null && !"".equals(dataset.getString("agentKey"))) {
			agentChk = true;
		}
		dataset.addColumn("action");
		dataset.addColumn("readAuthLvl_str");
		dataset.addColumn("rulesUnixKey");
		dataset.addColumn("syncFlg_str");
		dataset.addColumn("storgRuleTp_str");
		dataset.beforeFirst();
		while(dataset.hasNext()){
			dataset.next();
			String level = dataset.getString("readAuthLvl");
			boolean readAuth = true;
			String tenant = dataset.getString("tenant");
			if (ParaConst.ReadAuthLvl.AUTH_PUBLIC.equals(level)) {
				dataset.updateString("readAuthLvl_str", "公共");
			}else if (ParaConst.ReadAuthLvl.AUTH_TENANT.equals(level)) {
				dataset.updateString("readAuthLvl_str", "租户");
			}else if (ParaConst.ReadAuthLvl.AUTH_PART.equals(level)) {
				dataset.updateString("readAuthLvl_str", "参与者");
			}else if (AUTH_USER_TENANT.equals(level)) {
				dataset.updateString("readAuthLvl_str", "当前租户可用");
			}
			StringBuilder action = new StringBuilder();
			
			String sync = dataset.getString("syncFlg");
			if (ParaConst.SyncFlg.SYNC.equals(sync)){
				dataset.updateString("syncFlg_str", "已同步");
				if (agentChk) {
					action.append("	<a href=\"JavaScript:void(0);\" onClick=\"detail('" + dataset.getString("agentKey") +"&@&" + dataset.getString("engName") + "')\" >查询</a>");
				}else {
					action.append("	<a href=\"JavaScript:void(0);\" onClick=\"detail('" + dataset.getString("engName") + "')\" >数据</a>");
				}
			}else {
				dataset.updateString("syncFlg_str", "未同步");
			}
			
			dataset.updateString("action", action.toString());
			
			String rules = dataset.getString("engName");
			String unixKey = dataset.getString("uniqKey");
			dataset.updateString("rulesUnixKey", rules +"&@&"+ unixKey);

			String storgRuleTp = dataset.getString("storgRuleTp");
			if (storgRuleTp.equals(ParaConst.StorgRuleTp.STORG_RULE_TP_PARAM)) {
				dataset.updateString("storgRuleTp_str", "参数规则");
			} else if (storgRuleTp.equals(ParaConst.StorgRuleTp.STORG_RULE_TP_CACHE)) {
				dataset.updateString("storgRuleTp_str", "缓存规则");
				dataset.updateString("readAuthLvl_str", "无");
			}
		}
	}
	
	/**
	 * 转换列表字典
	 * @param dataset
	 */
	private void chgRedisDataDict(IDataset dataset) {
		if (null == dataset) {
			return;
		}
		dataset.addColumn("action");
		dataset.beforeFirst();
		while(dataset.hasNext()){
			dataset.next();
			StringBuffer action = new StringBuffer();
			action.append("'<a href=\'JavaScript:void(0);\' onClick=\'getData('" + dataset.getString("redisKey") + "')\'>数据详情</a>'");
			dataset.updateString("action", action.toString());
		}
	}


	/**
	 * Agent所属租户_参与者有读取权限的存储规则
	 * @param reqDs
	 * @return
	 */
	public IDataset agentRulesList(IDataset reqDs) {
		String agentKey = reqDs.getString("agentKey");
		String[] agtKeys = agentKey.split("_");
		String tenant = agtKeys[0];
		String partId = agtKeys[1];
		ParaRulesStgDO rulesStgDO = DatasetService.getInstace().getObject(reqDs, ParaRulesStgDO.class);
		List<ParaRulesStgDO> rulesList = rulesStgDao.getRulesByAuth(rulesStgDO, tenant, partId);
		IDataset dataset = DatasetService.getInstace().getDataset(rulesList, ParaRulesStgDO.class);
		if (dataset.hasNext()) {
			dataset.addColumn("agentKey");
			dataset.updateString("agentKey", agentKey);
		}
		chgRulesDict(dataset);
		return dataset;
	}

	/**
	 * Agent可读的数据
	 * @param reqDs
	 * @return
	 */
	public IDataset agentDataList(IDataset reqDs) {
		IDataset resDs = DatasetService.getInstace().getDataset();
		String unique = reqDs.getString("uniqueKey");
		String uniqueValue = reqDs.getString("uniqueValue");
		// 查询参数
		HashMap<String, Object> params = Maps.newHashMap();
		// 操作符参数 < > = like
		HashMap<String, String> operMap = Maps.newHashMap();
		if (unique!=null && !"".equals(unique)) {
			params.put(unique, uniqueValue);
			operMap.put(unique, "like");
		}
		// EntrRuleParam&@&ENTR_NO,RULE_NO
		String rulesUnixKey = reqDs.getString("rules");
		String token = reqDs.getString("token");
		String rules = rulesUnixKey.split("&@&")[0];
		String unixKey = rulesUnixKey.split("&@&")[1];
		String[] unixKeys = unixKey.split(",");
		// tenant_partId_partVersion_agentSeq_ip_Port
		String agentKey = reqDs.getString("agentKey");
		String[] agtKeys = agentKey.split("_");
//		String tenant = agtKeys[0];
//		String partId = agtKeys[1];
//		String partVersion = agtKeys[2];
//		String agentSeq = agtKeys[3];
//		String ipAndPort = agtKeys[4];
		
		String host = agtKeys[4];
		int port = Integer.valueOf(agtKeys[5]);
//		if (ipAndPort.indexOf(":") != -1){
//			host = ipAndPort.split(":")[0];
//			port = DataUtil.formatInt(ipAndPort.split(":")[1]);
//		}
		// 接收数据结果列表
		List<ParaRedisDataDO> redisDataList = Lists.newArrayList();
		// 请求参数
		HashMap<String, Object> reqData = Maps.newHashMap();
		reqData.put(ParaMapKey.TOKEN, token);
		reqData.put(ParaMapKey.PARAMTYPE, rules);
		reqData.put(ParaMapKey.PARAMS, params); // Params
		reqData.put(ParaMapKey.PARAMS_OPERS, operMap); // ParamsOpers
		HashMap<String, Object> retMap = Maps.newHashMap();
		try {
			retMap = TCPJsonServer.sendTcpJson(host, port, "", TRAN, SUBTRAN, reqData);
		}catch (Exception e){
			String error = "访问Agent数据失败：["+e.getMessage()+"]";
			throw new BaseException(SysErr.E_MESSAGE, error);
		}
		String retCode = (String)retMap.get("RetCode");
		if (null != retMap && !"SUCCESS".equals(retCode) && !"00000000".equals(retCode)) {
			String msg =  (""+retMap.get("Msg")).substring((""+retMap.get("Msg")).indexOf(":")+1,(""+retMap.get("Msg")).length());
			throw new BaseException(SysErr.E_MESSAGE, msg==null || "null".equals(msg)?"该实例未启动缓存代理监听功能":msg);
		}
		Object retObj = retMap.get(ParaMapKey.PARAMS);
		String paramJson = JSON.toJSONString(retObj);
		List<Map<String, Object>> list = JsonUtil.string2Obj(paramJson, List.class);
		for (Map<String, Object> paramMap : list) {
			String uniqueVal = "";
			ParaRedisDataDO redisData = new ParaRedisDataDO();
			for (int i=0;i<unixKeys.length;i++) {
				uniqueVal += paramMap.get(unixKeys[i]);
				if (i<unixKeys.length-1) {
					uniqueVal += "_";
				}
			}
			Set<String> paramKeys = keys("/PARAM_DATA/*/"+rules+"/*" + uniqueVal + "*");
			String rsKey = paramKeys.iterator().next();
			String redisSeq = "";
			if ( rsKey!=null && rsKey.length()!=0 ) {
				redisSeq = rsKey.substring(rsKey.lastIndexOf("/")+1);
			}
			redisData.setParamType(rules);
			redisData.setUnixKey(uniqueVal);
			redisData.setCacheSeq(paramMap.get("CACHE_SEQ")+"");
			redisData.setRedisSeq(redisSeq);
			// agentKey!@#rules!@#unixKey!@#uniqueVal
			String actionKey = agentKey+"!@#"+rules+"!@#"+ unixKey +"!@#"+uniqueVal;
			StringBuffer action = new StringBuffer();
			//20191206 modify by zengxj 修改为前端分页，需要转义\"
//			action.append("	<a href=\"JavaScript:void(0);\" onClick=\"getData('" + actionKey + "', '"+token+"')\" >详细</a>");
			action.append("<a href=\\\"JavaScript:void(0);\\\" onClick=\\\"getData('" + actionKey + "', '"+token+"')\\\" >详细</a>");
			redisData.setKey(action.toString());
			redisDataList.add(redisData);
		}
//		redisDataList = getAgentDataList(tenant, partId, partVersion, rules, agentSeq, ipAndPort, unixKeys, agentKey, unixKey);
		resDs = DatasetService.getInstace().getDataset(redisDataList, ParaRedisDataDO.class);
		chgDataDict(resDs);
		return resDs;
	}
	
	/**
	 * 获取AgentDataList
	 * @return
	 */
//	private List<RedisDataDO> getAgentDataList(String tenant, String partId, String partVersion, String rules, String agentSeq, String ipAndPort, String[] unixKeys, String agentKey, String unixKey) {
//		List<RedisDataDO> redisDataList = Lists.newArrayList();
//		HashMap<String, Object> params = Maps.newHashMap();
//		HashMap<String, String> operMap = Maps.newHashMap();
//		String path = ZkNode.PART_INST +"/" + tenant + "/" + partId + "/" + partVersion;
//		List<String> partInstList = Lists.newArrayList();
//		Map<String, Object> partInstInfo = Maps.newHashMap();
//		try {
//			partInstList = configClient.getChildren().forPath(path);
//		} catch (Exception e) {
//			throw new BaseException(IErrMsg.ERR_DEFAULT, "查询参与者列表错误");
//		}
//		for (String partInst : partInstList) {
//			String partInstJson = ZkClusterUtil.getNodeData(configClient, path + "/" + partInst);
//			partInstInfo = JSON.parseObject(partInstJson, new TypeReference<Map<String, Object>>() {
//	        });
//			if (null != agentSeq && agentSeq.equals(partInstInfo.get(MapKey.AGENT_SEQ))){
//				String token = EntryptUtil.aesDecrypt((String) partInstInfo.get(MapKey.TOKEN));
//				String agentJson = ZkClusterUtil.getNodeData(configClient, ZkNode.AGENT+"/"+agentSeq);
//				Map<String, String> agentInfo = JSON.parseObject(agentJson, new TypeReference<Map<String, String>>() {
//		        });
//				String host = agentInfo.get(MapKey.IP);
//				int port = DataUtil.formatInt(agentInfo.get(MapKey.PORT));
//				String tran = "cacheUserDataService";
//				String subTran = "getRedisParamData";
//				HashMap<String, Object> reqData = Maps.newHashMap();
//				reqData.put(MapKey.TOKEN, token);
//				reqData.put(MapKey.PARAMTYPE, rules);
//				reqData.put(MapKey.PARAMS, params); // Params
//				reqData.put(MapKey.PARAMS_OPERS, operMap); // ParamsOpers
//				HashMap<String, Object> retMap = Maps.newHashMap();
//				try {
//					if (host == null || port == 0) {
//						host = ipAndPort.split(":")[0];
//						port = DataUtil.formatInt(ipAndPort.split(":")[1]);
//					}
//					retMap = TCPJsonServer.sendTcpJson(host, port, "", tran, subTran, reqData);
//				}catch (Exception e){
//					String error = "访问Agent数据失败：IP["+host+"], PORT["+port+"]";
//					throw new BaseException(SysErr.E_MESSAGE, error);
//				}
//				String retCode = (String)retMap.get("RetCode");
//				if (null != retMap && !"SUCCESS".equals(retCode) && !"00000000".equals(retCode)) {
//					String msg =  (""+retMap.get("Msg")).substring((""+retMap.get("Msg")).indexOf(":")+1,(""+retMap.get("Msg")).length());
//					throw new BaseException(SysErr.E_MESSAGE, msg==null || "null".equals(msg)?"该参与者实例未启动缓存中心功能":msg);
//				}
//				Object retObj = retMap.get(MapKey.PARAMS);
//				String paramJson = JSON.toJSONString(retObj);
//				List<Map<String, Object>> list = JsonUtil.string2Obj(paramJson, List.class);
//				for (Map<String, Object> paramMap : list) {
//					String uniqueVal = "";
//					RedisDataDO redisData = new RedisDataDO();
//					for (int i=0;i<unixKeys.length;i++) {
//						uniqueVal += paramMap.get(unixKeys[i]);
//						if (i<unixKeys.length-1) {
//							uniqueVal += "_";
//						}
//					}
//					redisData.setParamType(rules);
//					redisData.setUnixKey(uniqueVal);
//					// agentKey%&%rules%&%unixKey%&%uniqueVal
//					String actionKey = agentKey+"%&%"+rules+"%&%"+ unixKey +"%&%"+uniqueVal;
//					StringBuffer action = new StringBuffer();
//					action.append("	<a href=\"JavaScript:void(0);\" onClick=\"getData('" + actionKey + "')\" >详细</a>");
//					redisData.setKey(action.toString());
//					redisDataList.add(redisData);
//				}
//			}
//		}
//		return redisDataList;
//	}

	private void chgDataDict(IDataset dataset) {
		if (null == dataset) {
			return;
		}
		dataset.addColumn("action");
		dataset.beforeFirst();
		while(dataset.hasNext()){
			dataset.next();
			String action = dataset.getString("key");
			dataset.updateString("action", action.toString());
		}
	
	}
	
}
