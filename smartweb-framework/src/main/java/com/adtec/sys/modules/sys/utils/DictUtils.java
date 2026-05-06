package com.adtec.sys.modules.sys.utils;

import com.adtec.framework.common.util.CacheUtil;
import com.adtec.framework.common.util.GVarContainer;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.sys.modules.sys.dao.DictDao;
import com.adtec.sys.modules.sys.dao.RentDao;
import com.adtec.sys.modules.sys.entity.Dict;
import com.adtec.sys.modules.sys.entity.Rent;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import java.util.*;

/**
 * 字典工具类
 *
 * @version 2013-5-29
 */
public class DictUtils {
	private static DictDao dictDao = SpringContextHolder.getBean(DictDao.class);
	private static RentDao rentDao = SpringContextHolder.getBean(RentDao.class);

	public static final String CACHE_DICT_MAP = "dictMap";
	public static final String RENT_DICT = "__smartweb_rent";
	public static final String DAYEND_RENT_DICT = "__smartweb_dayend_rent"; // 日终参数租户字典
	public static final String DAYEND_MODL_DICT = "__smartweb_dayend_modl"; // 日终参数模型字典

	public static String getDictLabel(String value, String type, String defaultValue) {
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)) {
			for (Dict dict : getDictList(type)) {
				if (type.equals(dict.getDictTp()) && value.equals(dict.getValue())) {
					return dict.getLabel();
				}
			}
		}
		return defaultValue;
	}

	public static String getDictLabels(String values, String type, String defaultValue) {
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(values)) {
			List<String> valueList = Lists.newArrayList();
			for (String value : StringUtils.split(values, ",")) {
				valueList.add(getDictLabel(value, type, defaultValue));
			}
			return StringUtils.join(valueList, ",");
		}
		return defaultValue;
	}

	public static String getDictValue(String label, String type, String defaultLabel) {
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)) {
			for (Dict dict : getDictList(type)) {
				if (type.equals(dict.getDictTp()) && label.equals(dict.getLabel())) {
					return dict.getValue();
				}
			}
		}
		return defaultLabel;
	}

	/**
	 * 初始化缓存数据字典
	 */
	public static void initDictInCache(){
		Map<String, List<Dict>> dictMap = Maps.newHashMap();
		for (Dict dict : dictDao.findAllList()) {
			List<Dict> dictList = dictMap.get(dict.getDictTp());
			if (dictList != null) {
				dictList.add(dict);
			} else {
				dictMap.put(dict.getDictTp(), Lists.newArrayList(dict));
			}
		}
		CacheUtil.put(CACHE_DICT_MAP, dictMap);
	}

	/**
	 * 根据参数类型获取参数列表
	 * @param type
	 * @return
	 */
	public static List<Dict> getDictList(String type) {
		if (RENT_DICT.equals(type)) {
			// 系统内部使用的租信息列表
			return getRentDictList(type);
		}
		/*20220219 mod by chenyl for 当集群部署时优先从线程变量中取*/
		List<Dict> dictList = null;
		if(ParamUtil.CONF_Y.equals(ParamUtil.getClusterFlag())){
			if(null!=GVarContainer.getVar(type)){
				dictList = (List<Dict>) GVarContainer.getVar(type);
				return dictList;
			}
		}
		// 从缓存中加载
		@SuppressWarnings("unchecked")
		Map<String, List<Dict>> dictMap = (Map<String, List<Dict>>) CacheUtil.get(CACHE_DICT_MAP);
		if (dictMap == null) {
			initDictInCache();
		}
		dictList = dictMap.get(type);
		if (dictList == null) {
			dictList = Lists.newArrayList();
		}
		// 集群模式时添加到线程变量中
		if(ParamUtil.CONF_Y.equals(ParamUtil.getClusterFlag())){
			GVarContainer.setVar(type, dictList);
		}
		return dictList;
	}

	/**
	 * 从本地缓存中通过id获取对应参数
	 * @param id
	 * @return
	 */
	public static Dict getDictById(String id){
		Dict d = null;
		Map<String, List<Dict>> dictMap = (Map<String, List<Dict>>) CacheUtil.get(CACHE_DICT_MAP);
		if (dictMap == null) {
			boolean isFound = false;
			Set<String> keys = dictMap.keySet();
			for(String key:keys){
				List<Dict> dictList = dictMap.get(key);
				for(Dict dict:dictList){
					if(null!=dict && dict.getId().equals(id)){
						d = dict;
						isFound = true;
						break;
					}
				}
				if(isFound){
					break;
				}
			}
		}
		return d;
	}

	public static void insertDictInCache(Dict dict) {
		Map<String, List<Dict>> dictMap = (Map<String, List<Dict>>) CacheUtil.get(CACHE_DICT_MAP);
		if (dictMap == null) {
			dictMap = Maps.newHashMap();
		}
		List<Dict> list = dictMap.get(dict.getDictTp());
		if (list != null && list.size() > 0) {
			list.add(dict);
			sort(list);
		} else {
			dictMap.put(dict.getDictTp(), Lists.newArrayList(dict));
		}
		CacheUtil.put(CACHE_DICT_MAP, dictMap);
	}

	private static void sort(List<Dict> list) {
		// update by 2022-02-28 新增后需要重新进行排序,不然获取数据排序不对
		// jdk1.8
//			list.sort((d1, d2) -> {
//				int ds1 = d1.getSort() != null ? d1.getSort() : 0;
//				int ds2 = d2.getSort() != null ? d2.getSort() : 0;
//				return Integer.compare(ds1, ds2);
//			});
		Collections.sort(list, new Comparator<Dict>() {
			@Override
			public int compare(Dict d1, Dict d2) {
				int ds1 = d1.getSort() != null ? d1.getSort() : 0;
				int ds2 = d2.getSort() != null ? d2.getSort() : 0;
				return ds1-ds2;
			}
		});
	}

	public static void updateDictInCache(Dict dict) {
		Map<String, List<Dict>> dictMap = (Map<String, List<Dict>>) CacheUtil.get(CACHE_DICT_MAP);
		if (dictMap != null && dictMap.size() >0) {
			Iterator it = dictMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry)it.next();
				if (dict.getDictTp().equals(entry.getKey())) {
					List<Dict> list = (List<Dict>) entry.getValue();
					for (Dict temp : list) {
						if (temp.getDictTp().equals(dict.getDictTp()) && temp.getValue().equals(dict.getValue())) {
							list.remove(temp);
							list.add(dict);
							break;
						}
					}
					sort(list);
					break;
				}
			}
		}
		/*20220110 add by chenyl for 更新缓存数据*/
		CacheUtil.put(CACHE_DICT_MAP, dictMap);
	}

	public static void deleteDictInCache(Dict dict) {
		Map<String, List<Dict>> dictMap = (Map<String, List<Dict>>) CacheUtil.get(CACHE_DICT_MAP);
		if (dictMap != null && dictMap.size() >0) {
			Iterator it = dictMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry)it.next();
				if (dict.getDictTp().equals(entry.getKey())) {
					List<Dict> list = (List<Dict>) entry.getValue();
					if (list.size() == 1 || dict.getValue() == null) {
						// 最后一个的时候，删除
						it.remove();
					} else {
						for (Dict temp : list) {
							if (temp.getDictTp().equals(dict.getDictTp()) && temp.getValue().equals(dict.getValue())) {
								list.remove(temp);
								break;
							}
						}
					}
					break;
				}
			}
		}
	}

	/**
	 * 返回系统中0-启用状态的租户信息，value为租户id，label为租户name
	 *
	 * @return
	 */
	public static List<Dict> getRentDictList(String type) {
		@SuppressWarnings("unchecked")
		Map<String, List<Dict>> dictMap = (Map<String, List<Dict>>) CacheUtil.get(CACHE_DICT_MAP);
		if (dictMap == null) {
			dictMap = Maps.newHashMap();
			for (Dict dict : dictDao.findAllList()) {
				List<Dict> dictList = dictMap.get(dict.getDictTp());
				if (dictList != null) {
					dictList.add(dict);
				} else {
					dictMap.put(dict.getDictTp(), Lists.newArrayList(dict));
				}
			}
			CacheUtil.put(CACHE_DICT_MAP, dictMap);
		}
		List<Dict> dictList = dictMap.get(type);
		if (dictList == null) {
			dictList = Lists.newArrayList();
			int sNo = 1;
			// 查询租户列表
			for (Rent rent : rentDao.findAllList()) {
				if (Rent.RENT_STAT_OPEN.equals(rent.getStat())) {
					Dict d = new Dict();
					d.setParentId("0");
					d.setDictTp(type);
					d.setLabel(rent.getName());
					d.setValue(rent.getId());
					d.setSort(sNo);
					dictList.add(d);
					sNo++;
				}
			}
			dictMap.put(type, dictList);
			CacheUtil.put(CACHE_DICT_MAP, dictMap);
		}
		return dictList;
	}
}
