/**
 *
 */
package com.adtec.sys.modules.sys.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adtec.framework.common.util.CacheUtil;
import com.adtec.framework.common.util.StringUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.sys.modules.sys.dao.AreaDao;
import com.adtec.sys.modules.sys.entity.Area;
import com.adtec.sys.modules.sys.utils.UserUtils;

/**
 * 区域Service
 *
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class AreaService {
    @Autowired
    private AreaDao dao;

    public Area get(String id) {
    	/*20200322 add by chenyl for 先从缓存中获取，如果不存在再从数据库中获取*/
    	Area area = UserUtils.getAreaByIdInCache(id);
    	if(null==area){
    		area = dao.get(id);
    	}
        return area;
    }

    public Area getByCode(String regionCode) {
        return dao.getByCode(regionCode);
    }

    public List<Area> findAll() {
        return UserUtils.getAreaList();
    }

    @Transactional(readOnly = false)
    public void save(Area area) {

        // 如果没有设置父节点，则代表为跟节点，有则获取父节点实体
        if (area.getParent() == null || StringUtil.isBlank(area.getParentId())
                || "0".equals(area.getParentId())) {
            area.setParent(null);
        } else {
            area.setParent(dao.get(area.getParentId()));
        }
        if (area.getParent() == null) {
            Area parentEntity = null;
            try {
                parentEntity = new Area("0");
            } catch (Exception e) {
                throw new BaseException(SysErr.E_NO_MESSAGE, e, "保存区域失败");
            }
            area.setParent(parentEntity);
            area.getParent().setParentIdList(StringUtil.EMPTY);
        }

        // 获取修改前的parentIds，用于更新子节点的parentIds
        String oldParentIds = area.getParentIdList();

        // 设置新的父节点串
        area.setParentIdList(area.getParent().getParentIdList() + area.getParent().getId() + ",");

        // 保存或更新实体
        if (StringUtil.isBlank(area.getId())) {
            area.preInsert();
            dao.insert(area);
        } else {
            area.preUpdate();
            dao.update(area);
        }

        // 更新子节点 parentIds
        Area o = new Area();
        o.setParentIdList("%," + area.getId() + ",%");
        List<Area> list = dao.findByParentIdsLike(o);
        for (Area e : list) {
            if (e.getParentIdList() != null && oldParentIds != null) {
                e.setParentIdList(e.getParentIdList().replace(oldParentIds, area.getParentIdList()));
                dao.updateParentIds(e);
            }
        }
        CacheUtil.remove(UserUtils.CACHE_AREA_LIST);
        CacheUtil.remove(UserUtils.CACHE_AREA_TREE);
    }

    @Transactional(readOnly = false)
    public void delete(Area area) {
        dao.delete(area);
        CacheUtil.remove(UserUtils.CACHE_AREA_LIST);
        CacheUtil.remove(UserUtils.CACHE_AREA_TREE);
    }

}
