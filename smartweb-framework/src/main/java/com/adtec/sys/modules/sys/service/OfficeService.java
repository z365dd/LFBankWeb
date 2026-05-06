/**
 * 
 */
package com.adtec.sys.modules.sys.service;

import com.adtec.framework.common.util.CacheUtil;
import com.adtec.framework.common.util.StringUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.sys.common.service.BaseService;
import com.adtec.sys.modules.sys.dao.OfficeDao;
import com.adtec.sys.modules.sys.dao.PermissionDao;
import com.adtec.sys.modules.sys.dao.SysPermissionWeightDao;
import com.adtec.sys.modules.sys.dao.UserDao;
import com.adtec.sys.modules.sys.entity.Office;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.adtec.sys.modules.sys.utils.UserUtils.USER_CACHE;
import static com.adtec.sys.modules.sys.utils.UserUtils.USER_CACHE_LOGIN_NAME_;

/**
 * 机构Service
 * 
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class OfficeService {

	@Autowired
	private PermissionDao permissionDao;
	@Autowired
	private OfficeDao dao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private SysPermissionWeightDao sysPermissionWeightDao;

	public List<Office> findAll() {
		return UserUtils.getOfficeList();
	}

	public List<Office> findList(Boolean isAll) {
		if (isAll != null && isAll) {
			return UserUtils.getOfficeAllList();
		} else {
			return UserUtils.getOfficeList();
		}
	}

	public List<Office> findByParentIdsLike(Office office) {

		User user = UserUtils.getUser();
		if (user.isAdmin()) {
			return dao.findAllList();
		} else {
			List<Object> filterParams = Lists.newArrayList();
			office.setParentIdList(office.getParentIdList() + "%");
			office.getSqlMap().put("dsf", BaseService.dataScpFilterOffice(user, "a", "", filterParams));
			return dao.findByParentIdsLike(office, filterParams);
		}
	}

	public List<Office> findListByParent(Office office) {
		return dao.findByParentId(office);
	}
	
	public List<Office> findListByCode(Office office) {
		return dao.findByCode(office);
	}

	@Transactional(readOnly = false)
	public void save(Office office) {

		// 如果没有设置父节点，则代表为跟节点，有则获取父节点实体
		if (office.getParent() == null || StringUtil.isBlank(office.getParentId())
				|| "0".equals(office.getParentId())) {
			office.setParent(null);
		} else {
			office.setParent(dao.get(office.getParentId()));
		}
		if (office.getParent() == null) {
			Office parent = null;
			try {
				parent = new Office("0");
			} catch (Exception e) {
				throw new BaseException(SysErr.E_NO_MESSAGE, e, "保存机构失败");
			}
			office.setParent(parent);
			office.getParent().setParentIdList(StringUtil.EMPTY);
		}

		// 获取修改前的parentIds，用于更新子节点的parentIds
		String oldParentIds = office.getParentIdList();

		// 设置新的父节点串
		office.setParentIdList(office.getParent().getParentIdList() + office.getParent().getId() + ",");

		// 保存或更新实体
		if (StringUtil.isBlank(office.getId())) {
			office.preInsert();
			dao.insert(office);
			sysPermissionWeightDao.initMenuPermissionToOffice(office.getId());
		} else {
			office.preUpdate();
			dao.update(office);

			// 上级发生变化时，清空转授权和使用权
			if (!oldParentIds.equals(office.getParentIdList())) {
				permissionDao.deleteMenusByMenuIds("BRCH_ID", office.getId(), "transfer");
				permissionDao.deleteMenusByMenuIds("BRCH_ID", office.getId(), "use");
			}

			// 清除机构下的用户的缓存
			User user = new User();
			user.setOffice(office);
			List<User> list = userDao.findUserByBrchId(user);
			for (User u : list) {
				CacheUtil.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + u.getLoginName());
			}
		}

		// 更新子节点 parentIds
		Office o = new Office();
		o.setParentIdList("%," + office.getId() + ",%");
		List<Office> list = dao.findByParentIdsLike(o);
		for (Office e : list) {
			if (e.getParentIdList() != null && oldParentIds != null) {
				e.setParentIdList(e.getParentIdList().replace(oldParentIds, office.getParentIdList()));
				dao.updateParentIds(e);
				// 上级发生变化时，清空转授权和使用权
				if (!oldParentIds.equals(office.getParentIdList())) {
					permissionDao.deleteMenusByMenuIds("BRCH_ID", e.getId(), "transfer");
					permissionDao.deleteMenusByMenuIds("BRCH_ID", e.getId(), "use");
				}
			}
		}
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(Office office) {
		dao.delete(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}

	public String getNewOfficeCode(String brchId,String officeCode){
		int size = 0;
		List<Office> list = findAll();
		for (Office e : list) {
			if (e.getParent() != null && e.getParent().getId() != null
					&& e.getParent().getId().equals(brchId)) {
				size++;
			}
		}
		return officeCode + StringUtil.leftPad(String.valueOf(size > 0 ? size+1 : 1), 3, "0");

	}

	public Office getOffice(String brchId) {
		return dao.getOfficeById(brchId);
	}

	public Office getOfficeByCode(String code) {
		Office office = new Office();
		office.setBrchCode(code);
		return dao.getByCode(office);
	}

	public Office getOfficeByName(String name) {
		Office office = new Office();
		office.setName(name);
		return dao.getByName(office);
	}

	public Office get(String id) {
		return dao.get(id);
	}

	public Office getOfficeByRegionId(String regionId) {
		return dao.getOfficeByRegionId(regionId);
	}

	public Office get(Office office) {
		return dao.get(office);
	}
}
