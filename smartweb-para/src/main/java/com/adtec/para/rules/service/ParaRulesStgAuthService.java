package com.adtec.para.rules.service;

import com.adtec.framework.common.util.ClassUtil;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.para.rules.dao.ParaRulesStgAuthDao;
import com.adtec.para.rules.entity.ParaRulesStgAuthDO;
import com.adtec.sys.modules.sys.dao.RentDao;
import com.adtec.sys.modules.sys.entity.Rent;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.service.SystemService;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParaRulesStgAuthService {
	
	@Autowired
	private ParaRulesStgAuthDao rulesStgAuthDao;
	@Autowired
	private RentDao rentDao;
	@Autowired
	private SystemService systemService;
	
	private static final String CACHE_RENT_LIST = "rentList";

	/**
	 * 获取租户信息
	 * @param reqDs
	 * @return
	 */
	public IDataset getTenanices(IDataset reqDs) {
		List<Rent> rentList = (List<Rent>)UserUtils.getCache(CACHE_RENT_LIST);
		if (rentList == null){
			rentList = rentDao.findAllList();
			UserUtils.putCache(CACHE_RENT_LIST, rentList);
		}
		IDataset dataset = DatasetService.getInstace().getDataset(rentList, Rent.class);
		chgDict(dataset);
		return dataset;
	}
	
	/**
	 * 获取参与者信息
	 * @param reqDs
	 * @return
	 */
	public <T> IDataset getParts(IDataset reqDs) {
		List<ParaRulesStgAuthDO> list = Lists.newArrayList();
		String tenantEnname = reqDs.getString("tenant");
		User user = UserUtils.getUser();
		List<Rent> rents = Lists.newArrayList();
		if (tenantEnname != null && tenantEnname.isEmpty()) {
			list = rulesStgAuthDao.getAllPart();
		}else {
			rents.addAll(findChildRentsByRentEnname(rents, tenantEnname));
			rents.add(systemService.getRentByEngName(tenantEnname));
		}
		
		Class<T> clazz = null;
		try {
			clazz = (Class<T>) Class.forName("com.adtec.ms.service.part.entity.PartDO");
			getPartListByRentEnname(clazz, rents, list);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IDataset dataset = DatasetService.getInstace().getDataset(list, ParaRulesStgAuthDO.class);
		chgDict(dataset);
		return dataset;
	}

	/**
	 * 获取租户参与者信息
	 * @param clazz
	 * @param rents
	 * @param authlist
	 */
	private <T> void getPartListByRentEnname(Class<T> clazz, List<Rent> rents, List<ParaRulesStgAuthDO> authlist) {
//		List<T> partlist = Lists.newArrayList();
		for (Rent rent : rents) {
			if(SpringContextHolder.containsBean("partService")) {
				Object partService = SpringContextHolder.getBean("partService");
				List<T> partlist = (List<T>) ClassUtil.invokeMethodByName(partService, "listAllPart", new Object[] {rent.getEngName()});
				for (T part : partlist) {
					ParaRulesStgAuthDO auth = new ParaRulesStgAuthDO();
					auth.setTenantName(rent.getName());
					auth.setPartName(ClassUtil.getFieldValue(part, "chName").toString());
					auth.setUseTntNo(rent.getEngName());
					auth.setMembNo(ClassUtil.getFieldValue(part, "membNo").toString());
					authlist.add(auth);
				}
			}else {
				authlist.addAll(rulesStgAuthDao.getPartByRent(rent));
			}
		}
	}
	
	
	/**
	 * 查询租户下所有下级租户list 递归算法
	 * @param rents 租户英文名集合，用于传递结果递归
	 * @param rentEnname 租户英文名
	 * @return
	 */
	private List<Rent> findChildRentsByRentEnname(List<Rent> rents, String rentEnname) {
		List<Rent> list = systemService.getLowerRentsByEnName(rentEnname);
		if(list.size() > 0) {
			rents.addAll(list);
		}
		for (Rent rent : list) {
			findChildRentsByRentEnname(rents, rent.getEngName());
		}
		return rents;
	}
	
	/**
	 * 查询当前租户下的子租户
	 * 
	 * @param reqDs
	 *            租户英文名称
	 * @return
	 */
	public IDataset getChildRents(IDataset reqDs) {
		User user = UserUtils.getUser();
		List<Rent> rentList = Lists.newArrayList();
		if (user.isAdmin()) {
			rentList = (List<Rent>)UserUtils.getCache(CACHE_RENT_LIST);
			if (rentList == null){
				rentList = rentDao.findAllList();
				UserUtils.putCache(CACHE_RENT_LIST, rentList);
			}
		}else {
			rentList = systemService.getLowerRentsByEnName(user.getRent().getEngName());
			rentList.add(user.getRent());
		}
		IDataset dataset = DatasetService.getInstace().getDataset(rentList, Rent.class);
		return dataset;
		
	}
	
	/**
	 * 列表字段转换
	 * @param dataset
	 */
	private void chgDict(IDataset dataset) {
		if (null == dataset) {
			return;
		}
		dataset.addColumn("tenantPart");
		dataset.addColumn("tenantPartName");
		dataset.addColumn("tntNoStr");
		dataset.beforeFirst();
		while(dataset.hasNext()){
			dataset.next();
			String authTenant = dataset.getString("tntNo");
			String authPartId = dataset.getString("membNo");
			dataset.updateString("tenantPart", authTenant+"_"+authPartId);
			String tenantName = dataset.getString("tenantName");
			String partName = dataset.getString("partName");
			dataset.updateString("tenantPartName", tenantName+"_"+partName);
			String engName = dataset.getString("engName");
			String name = dataset.getString("name");
			dataset.updateString("tntNoStr", engName+"-"+name);
		}
	}

	public <T> IDataset getPartList() {
		List<Rent> rents = Lists.newArrayList();
		rents.add(systemService.getRentByEngName("head"));
		List<ParaRulesStgAuthDO> list = Lists.newArrayList();
		Class<T> clazz = null;
		try {
			clazz = (Class<T>) Class.forName("com.adtec.ms.service.part.entity.PartDO");
			getPartListByRentEnname(clazz, rents, list);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IDataset dataset = DatasetService.getInstace().getDataset(list, ParaRulesStgAuthDO.class);
		chgDict(dataset);
		return dataset;
	}
}
