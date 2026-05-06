package com.adtec.para.cachetest.service;

import com.adtec.cache.cacheagent.service.UserDataService;
import com.adtec.framework.common.util.*;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.para.cfgcenter.service.ParaCfgCenterService;
import com.adtec.para.chk.entity.ParaRedisDataDO;
import com.adtec.para.common.constants.ParaConst;
import com.adtec.para.common.util.ParaMapKey;
import com.adtec.para.rules.dao.ParaRulesStgAuthDao;
import com.adtec.para.rules.dao.ParaRulesStgColDao;
import com.adtec.para.rules.dao.ParaRulesStgDao;
import com.adtec.para.rules.entity.ParaRulesStgAuthDO;
import com.adtec.para.rules.entity.ParaRulesStgColDO;
import com.adtec.para.rules.entity.ParaRulesStgDO;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.google.common.collect.Maps;
import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.apache.curator.shaded.com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

@Service
public class ParaTestService {
	
	private static final Logger logger = LoggerFactory.getLogger(ParaTestService.class);
	@Autowired
	private ParaRulesStgDao rulesStgDao;
	
	@Autowired
	private ParaRulesStgColDao rulesStgColDao;
	
	@Autowired
	private ParaCfgCenterService cfgCenterService;
	
	@Autowired
	private ParaRulesStgAuthDao rulesStgAuthDao;
	
	@Value("${JAVAWORKDIR}")
	private String javaWorkDir;

	/**
	 * 获取本地缓存数据列表
	 * @param reqDs
	 * @return
	 */
	public IDataset getLocalList(IDataset reqDs) {
		String colJson = reqDs.getString("colJson");
		String custom = reqDs.getString("custom");
		// 存储规则名称
		String rulesUnixKey = reqDs.getString("rules");
		String paramType = rulesUnixKey.split("&@&")[0];
		String unixKey = rulesUnixKey.split("&@&")[1];
		String[] uKeys = unixKey.split(",");
		HashMap<String, Object> params = Maps.newHashMap();
		HashMap<String, String> operMap = Maps.newHashMap();
		List<Map<String, Object>> colList = JsonUtil.string2Obj(colJson, List.class);
		for (Map<String, Object> map : colList) {
			params.put((String)map.get(ParaMapKey.COL_NO), map.get(ParaMapKey.VALUE));
			operMap.put((String)map.get(ParaMapKey.COL_NO), (String) map.get(ParaMapKey.OPER));
		}

		String token = getToken();
		UserDataService userDataService = SpringContextHolder.getBean("cacheUserDataService");
		HashMap<String, Object> retMap = Maps.newHashMap();
		if (ParaConst.CustomType.AND.equals(custom)) {
			retMap = userDataService.getRedisParamData(token, paramType, params);
		} else if (ParaConst.CustomType.OR.equals(custom)) {
			retMap = userDataService.getRedisParamData(token, paramType, params, operMap, custom);
		}
		// ParamType=STUDENT, Params=[{HEIGHT=180, SID=3333, CLASSID=999777, SNAME=XX组件
		List<ParaRedisDataDO> list = Lists.newArrayList();
		if(null!=retMap && "SUCCESS".equals((String)retMap.get("RetCode"))){
			List<Map<String, Object>> paramsList = (List<Map<String,Object>>)retMap.get("Params");
			for(Map<String,Object> map : paramsList){
				ParaRedisDataDO item = new ParaRedisDataDO();
				StringBuffer uniqueKey = new StringBuffer();
				for (int i=0;i<uKeys.length;i++) {
					String uKey = uKeys[i];
					if (map.containsKey(uKey)){
						uniqueKey.append(map.get(uKey));
					}else {
						continue;
					}
					if (i!=uKeys.length-1){
						uniqueKey.append("_");
					}
				}
				item.setUniqueKey(unixKey);
				item.setUnixKey(uniqueKey.toString());
				item.setParamType(paramType);
				list.add(item);
			}
		} else {
			String msg =  (""+retMap.get("Msg")).substring((""+retMap.get("Msg")).indexOf(":")+1,(""+retMap.get("Msg")).length());
			logger.debug(msg);
			throw new BaseException(SysErr.E_MESSAGE, msg);
		}
		IDataset resDs = DatasetService.getInstace().getDataset(list, ParaRedisDataDO.class);
		chgLocalCache(resDs);
		return resDs;
	}

	/**
	 * 设置数据
	 * @param reqDs
	 */
	public HashMap<String, Object> setData(IDataset reqDs) {
		String rule = reqDs.getString("rules");
		String[] rules = rule.split("&@&");
		String paramType = rules[0];
		List<HashMap<String, Object>> params = Lists.newArrayList();
		String json = reqDs.getString("colJson");
		List<Map<String, Object>> colList = JsonUtil.string2Obj(json, List.class);
		HashMap<String, Object> pMap = Maps.newHashMap();
		params.add(pMap);
		for (Map<String, Object> col : colList) {
			pMap.put((String) col.get(ParaMapKey.COL_NO), col.get(ParaMapKey.RMRK));
		}
		String token = getToken();
		UserDataService userDataService = SpringContextHolder.getBean("cacheUserDataService");
		HashMap<String, Object> retMap = userDataService.setRedisParamData(token, paramType, params);
		return retMap;
	}

	/**
	 * 删除数据
	 * @param reqDs
	 */
	public HashMap<String, Object> delData(IDataset reqDs) {
		String rulesUnixKey = reqDs.getString("dataKey");
		String paramType = rulesUnixKey.split("&@&")[0];
		String uniqueKey = rulesUnixKey.split("&@&")[1];
		String unixValue = rulesUnixKey.split("&@&")[2];
		String[] keys = uniqueKey.split(",");
		String[] values = unixValue.split("_");
		HashMap<String, Object> params = Maps.newHashMap();
		for (int i=0;i<keys.length;i++) {
			params.put(keys[i], values[i]);
		}
		String token = getToken();
		UserDataService userDataService = SpringContextHolder.getBean("cacheUserDataService");
		HashMap<String, Object> retMap = userDataService.deleteRedisParamData(token, paramType, params);
		return retMap;
	}
	
	/**
	 * 本地缓存数据-详情
	 * @param reqDs
	 * @return
	 */
	public IDataset getlocalData(IDataset reqDs) {
		String rulesUnixKey = reqDs.getString("dataKey");
		String paramType = rulesUnixKey.split("&@&")[0];
		String uniqueKey = rulesUnixKey.split("&@&")[1];
		String unixValue = rulesUnixKey.split("&@&")[2];
		String[] keys = uniqueKey.split(",");
		String[] values = unixValue.split("_");
		HashMap<String, Object> params = Maps.newHashMap();
		for (int i=0;i<keys.length;i++) {
			params.put(keys[i], values[i]);
		}
		String token = getToken();
		UserDataService userDataService = SpringContextHolder.getBean("cacheUserDataService");
		HashMap<String, Object> retMap = userDataService.getRedisParamData(token, paramType, params);
		List<ParaRedisDataDO> list = Lists.newArrayList();
		if(null!=retMap && "SUCCESS".equals((String)retMap.get("RetCode"))){
			List<Map<String, Object>> paramsList = (List<Map<String,Object>>)retMap.get("Params");
			for(Map<String,Object> map : paramsList){
				for(Entry<String,Object> entry : map.entrySet()){
					ParaRedisDataDO item = new ParaRedisDataDO();
					item.setKey(entry.getKey());
					if (null !=entry.getValue()) {
						if(entry.getValue().toString().indexOf("{") != -1) {
//							String json = JSON.toJSONString(entry.getValue().toString());
							String json = JsonUtil.obj2String(entry.getValue().toString());
							item.setObject(json);
						}else {
							item.setObject(entry.getValue().toString());
						}
					} else {
						item.setObject("null");
					}
					list.add(item);
				}
			}
		}else {
			logger.debug(""+retMap.get("RetCode")+":"+retMap.get("Msg"));
			throw new BaseException(SysErr.E_MESSAGE, retMap.get("Msg"));
		}
		IDataset resDs = DatasetService.getInstace().getDataset(list, ParaRedisDataDO.class);
		return resDs;
	}
	
	/**
	 * 获取数据详情-更新页面
	 * @param reqDs
	 * @return
	 */
	public List<ParaRulesStgColDO> getDataForUpt(IDataset reqDs) {
		String rulesUnixKey = reqDs.getString("dataKey");
		String paramType = rulesUnixKey.split("&@&")[0];
		String uniqueKey = rulesUnixKey.split("&@&")[1];
		String unixValue = rulesUnixKey.split("&@&")[2];
		String[] keys = uniqueKey.split(",");
		String[] values = unixValue.split("_");
		HashMap<String, Object> params = Maps.newHashMap();
		for (int i=0;i<keys.length;i++) {
			params.put(keys[i], values[i]);
		}
		List<ParaRulesStgColDO> colList = Lists.newArrayList();
		List<ParaRulesStgColDO> colDO = rulesStgColDao.getColByRulesName(paramType);
		String token = getToken();
		UserDataService userDataService = SpringContextHolder.getBean("cacheUserDataService");
		HashMap<String, Object> retMap = userDataService.getRedisParamData(token, paramType, params);
		ParaRedisDataDO item = new ParaRedisDataDO();

		if (null!=retMap && "SUCCESS".equals((String)retMap.get("RetCode"))) {
			List<Map<String, Object>> paramsList = (List<Map<String,Object>>)retMap.get("Params");
			Map<String, Object> map = paramsList.get(0);
			Set<String> keySet = Sets.newHashSet();
			keySet.addAll(map.keySet());

			for (ParaRulesStgColDO col : colDO) {
				col.setRmrk(""+map.get(col.getColNo()));
				colList.add(col);
				keySet.remove(col.getColNo());
			}

				for (String key : keySet) {
					ParaRulesStgColDO col = new ParaRulesStgColDO(key, key, "Y", "N", ""+map.get(key));
					colList.add(col);
				}
		} else {
			String msg =  (""+retMap.get("Msg")).substring((""+retMap.get("Msg")).indexOf(":")+1,(""+retMap.get("Msg")).length());
			logger.debug(msg);
			throw new BaseException(SysErr.E_MESSAGE, msg);
		}
		return colList;
	}
	

	/**
	 * 获取默认类型
	 * @return
	 */
	private Map<String, String> getDefaultType() {
		Map<String, String> type = Maps.newHashMap();
		
		String defaultType = ParamUtil.getString("defaultType");
		String defaultLength = ParamUtil.getString("defaultLength");
		type.put(ParaMapKey.TYPE, defaultType);
		type.put(ParaMapKey.LENGTH, defaultLength);
		return type;
	}

	/**
	 * 预先授权给smartweb
	 * @param stgId
	 */
	public void setRulesAuth(String stgId) {
		String tenant = com.adtec.framework.common.util.ParamUtil.getString("ReqTenant");
		String partId = com.adtec.framework.common.util.ParamUtil.getString("ReqPartId");
		
		ParaRulesStgAuthDO stgWirteAuthDO = new ParaRulesStgAuthDO(stgId, "1", tenant, partId);
		ParaRulesStgAuthDO temp = new ParaRulesStgAuthDO(stgId, "0", tenant, partId);
		ParaRulesStgAuthDO stgReadAuthDO = rulesStgAuthDao.getAuthByUK(temp);
		if (stgReadAuthDO == null) {
			temp.preInsert();
			rulesStgAuthDao.insert(temp);
		}
		stgWirteAuthDO.preInsert();
		rulesStgAuthDao.insert(stgWirteAuthDO);
	}
	
	
	/**
	 * 获取存储规则，生成下拉选项
	 * @return
	 */
	public IDataset getRules() {
		String tenant = ParamUtil.getString("ReqTenant");
		ParaRulesStgDO rulesStgDO = new ParaRulesStgDO();
		List<ParaRulesStgDO> rulesList = rulesStgDao.getRulesByAuth(rulesStgDO, tenant, "");
		IDataset resDs = DatasetService.getInstace().getDataset(rulesList, ParaRulesStgDO.class);
		chgRulesDict(resDs);
		return resDs;
	}
	
	/**
	 * 根据存储规则英文名获取字段信息
	 * @param reqDs
	 * @return
	 */
	public List<ParaRulesStgColDO> getRulesCol(IDataset reqDs) {
		String engName = reqDs.getString("engName");
		List<ParaRulesStgColDO> colDO = rulesStgColDao.getColByRulesName(engName);
		IDataset resDs = DatasetService.getInstace().getDataset(colDO, ParaRulesStgColDO.class);
		return colDO;
	}

	private String getToken() {
		HashMap<String, Object> cacheMap = (HashMap<String, Object>) CacheUtil.get("CacheAgent");
		String token = "";
		if (cacheMap != null) {
			token = (String) cacheMap.get(ParaMapKey.TOKEN);
		}
		return token;
	}

	/**
	 * 字典转换
	 * @param dataset
	 */
	private void chgLocalCache(IDataset dataset) {
		if (null == dataset) {
			return;
		}
		dataset.addColumn("action");
		dataset.beforeFirst();
		while(dataset.hasNext()){
			dataset.next();
			String paramType = dataset.getString("paramType");
			String uniqueKey = dataset.getString("uniqueKey"); // 组合
			String unixValue = dataset.getString("unixKey"); // 值
			String dataKey = paramType+"&@&"+uniqueKey+"&@&"+unixValue;
			StringBuffer action = new StringBuffer();
			// 前端分页
			action.append("<a href=\\\"JavaScript:void(0);\\\" onClick=\\\"getData('" + dataKey + "')\\\" >详细</a>");
			action.append("<a href=\\\"JavaScript:void(0);\\\" onClick=\\\"setData('" + dataKey + "')\\\" >修改</a>");
			action.append("<a href=\\\"JavaScript:void(0);\\\" onClick=\\\"delData('" + dataKey + "')\\\" >删除</a>");
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
		dataset.addColumn("action");
		dataset.addColumn("readAuthLevel_str");
		dataset.addColumn("rulesUnixKey");
		dataset.addColumn("syncConfig");
		dataset.beforeFirst();
		while(dataset.hasNext()){
			dataset.next();
			String sync = dataset.getString("syncFlg");
			String level = dataset.getString("readAuthLvl");
			if (ParaConst.ReadAuthLvl.AUTH_PUBLIC.equals(level)) {
				dataset.updateString("readAuthLevel_str", "公共");
			}else if (ParaConst.ReadAuthLvl.AUTH_TENANT.equals(level)) {
				dataset.updateString("readAuthLevel_str", "租户");
			}else if (ParaConst.ReadAuthLvl.AUTH_PART.equals(level)) {
				dataset.updateString("readAuthLevel_str", "参与者");
			}else if (ParaConst.ReadAuthLvl.AUTH_USER_TENANT.equals(level)) {
				dataset.updateString("readAuthLevel_str", "当前租户可用");
			}
			StringBuilder action = new StringBuilder();
			if (ParaConst.SyncFlg.SYNC.equals(sync)){
				dataset.updateString("syncConfig", "已同步");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"detail('" + dataset.getString("engName") + "')\" >查询数据</a>");
			}else {
				dataset.updateString("syncConfig", "未同步");
			}
			dataset.updateString("action", action.toString());
			String rules = dataset.getString("engName");
			String unixKey = dataset.getString("uniqKey");
			dataset.updateString("rulesUnixKey", rules +"&@&"+ unixKey);
		}
	}


	/**
	 * 导入存储规则
	 * @param reqDs
	 * @param mfile
	 * @return
	 */
	public HashMap<String, Object> importRules(IDataset reqDs, MultipartFile mfile) {
		HashMap<String, Object> retMap = Maps.newHashMap();
		String centerId = reqDs.getString("cacheCentrId");
		String tenant = reqDs.getString("tntNo");
		String storgRuleTp = reqDs.getString("storgRuleTp");
		String tabName = reqDs.getString("tabName");
		String sameDbFlg = reqDs.getString("sameDbFlg");
		String srcDataSrc = reqDs.getString("srcDataSrc");
		String busiNoFlg = reqDs.getString("busiNoFlg");
		List<Map<String, String>> retList = Lists.newArrayList();
		try {
			List<ParaRulesStgDO> list = Lists.newArrayList();
			Map<String, String> ruleMap = Maps.newHashMap();
			if (null != mfile) {
				File file = new File(mfile.getOriginalFilename());
				if (!file.getName().endsWith(".properties")) {
					throw new Exception("文件后缀名必须为properties");
				}
				FileUtil.copyInputStreamToFile(mfile.getInputStream(), file);
				Properties properties = new Properties();
				try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file.getName()))){
					properties.load(bufferedReader);
					for (Object key : properties.keySet()) {

						String keyStr = key.toString();
						String value = properties.getProperty(keyStr);
						ParaRulesStgDO rulesStgDO = new ParaRulesStgDO(keyStr, keyStr, centerId, tenant, value, ParaConst.ReadAuthLvl.AUTH_PUBLIC, storgRuleTp, ParaConst.SyncFlg.UNSYNC, ParaConst.DelFlg.NODEL,tabName, sameDbFlg, srcDataSrc, busiNoFlg);
						list.add(rulesStgDO);
						ruleMap.put(keyStr, value);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					System.out.println("出现异常");
				}
				int rs = 0;
				IDBSession session = DBSessionFactory.getSession();
				String ruleName = "";
				String msg = "";
				for (ParaRulesStgDO stgDO : list) {
					ruleName = stgDO.getEngName();
					try {
						session.beginTransaction();
						ParaRulesStgDO item = rulesStgDao.getRulesByEngName(stgDO);
						if (item!=null) {
							rulesStgColDao.deleteByStgId(item.getId());
							rulesStgAuthDao.deleteByStgId(item.getId());
							rulesStgDao.delete(item);
						}
						stgDO.preInsert();
						rulesStgDao.insert(stgDO);
						String ruleId = stgDO.getId();
						String uniqKey = ruleMap.get(stgDO.getEngName());
						String[] keys = uniqKey.split(",");
						for (int i=0;i<keys.length;i++) {
							String key = keys[i];
							ParaRulesStgColDO rulesStgColDO = new ParaRulesStgColDO(ruleId, key, key, "String", "128", "N", "Y", String.valueOf(i));
							rulesStgColDO.preInsert();
							rulesStgColDao.insert(rulesStgColDO);
						}
//						setRulesAuth(ruleIddddd);
						session.endTransaction();
					} catch (Exception e) {
						session.rollback();
						Map<String, String> map = Maps.newHashMap();
						map.put(ruleName, e.getMessage());
						retList.add(map);
					}
				}
//				cfgCenterService.infoRelease(centerId);
			} else {
				throw new Exception("文件不能为空");
			}
		} catch (Exception e) {
			throw new BaseException(SysErr.E_MESSAGE, "导入存储规则失败！"+e.getMessage());
		}
		retMap.put(ParaMapKey.DATA, retList);
		return retMap;
	}

	/**
	 * 多表查询
	 * @param reqDs
	 * @return
	 */
	public IDataset getLocalTables(IDataset reqDs) {
		List<String> pTypeList = Lists.newArrayList();
		Map<String, String[]> uKeyMap = Maps.newHashMap();
		String custom = reqDs.getString("custom");
		// 存储规则名称
		String rulesUnixKey = reqDs.getString("rules");
		String paramType = rulesUnixKey.split("&@&")[0];
		String unixKey = rulesUnixKey.split("&@&")[1];
		String[] uKeys = unixKey.split(",");
		HashMap<String, Object> params = Maps.newHashMap();
		HashMap<String, String> operMap = Maps.newHashMap();
		String fRules = reqDs.getString("fRules");
		String[] arr = fRules.split(";");
		uKeyMap.put(paramType, uKeys);
		for (String s : arr) {
			String sParamType = s.split("&@&")[0];
			String sUnixKey = s.split("&@&")[1];
			String[] sUKeys = sUnixKey.split(",");
			pTypeList.add(sParamType);
			uKeyMap.put(sParamType, sUKeys);
		}
		String fKey = reqDs.getString("fKey");
		String token = getToken();
		List<ParaRedisDataDO> list = Lists.newArrayList();
		UserDataService userDataService = SpringContextHolder.getBean("cacheUserDataService");
		List<HashMap<String, Object>> mapList = userDataService.getRedisParamData(token, paramType, params, pTypeList, fKey);
		for (HashMap<String, Object> retMap : mapList) {
			logger.info(""+retMap);
			// ParamType=STUDENT, Params=[{HEIGHT=180, SID=3333, CLASSID=999777, SNAME=XX组件
			if(null!=retMap && "SUCCESS".equals((String)retMap.get("RetCode"))){
				String dataType = (String) retMap.get("ParamType");
				String[] sUKeys = uKeyMap.get(dataType);
				List<Map<String, Object>> paramsList = (List<Map<String,Object>>)retMap.get("Params");
				for(Map<String,Object> map : paramsList){
					ParaRedisDataDO item = new ParaRedisDataDO();
					StringBuffer uniqueKey = new StringBuffer();
					String uKeysStr = "";
					for (int i=0;i<sUKeys.length;i++) {
						String uKey = sUKeys[i];
						uKeysStr += uKey+",";
						if (map.containsKey(uKey)){
							uniqueKey.append(map.get(uKey));
						}else {
							continue;
						}
						if (i!=sUKeys.length-1){
							uniqueKey.append("_");
						}
					}
					uKeysStr = uKeysStr.substring(0, uKeysStr.lastIndexOf(","));
					item.setUniqueKey(uKeysStr);
					item.setUnixKey(uniqueKey.toString());
					item.setParamType(dataType);
					list.add(item);
				}
			} else {
				String msg =  (""+retMap.get("Msg")).substring((""+retMap.get("Msg")).indexOf(":")+1,(""+retMap.get("Msg")).length());
				logger.debug(msg);
//				throw new BaseException(SysErr.E_MESSAGE, msg);
			}

		}
		IDataset resDs = DatasetService.getInstace().getDataset(list, ParaRedisDataDO.class);
		chgLocalCache(resDs);
		return resDs;
	}
}
