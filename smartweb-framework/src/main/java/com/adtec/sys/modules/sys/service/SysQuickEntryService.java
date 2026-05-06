/**
* 系统名称: SmartWeb平台
* 模块名称: sys-modules服务模块
* 功能描述: 快捷菜单入口服务提供类
* 类 名 称  : SysQuickEntryService.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20190904<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.sys.modules.sys.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.sys.modules.sys.dao.SysQuickEntryDao;
import com.adtec.sys.modules.sys.entity.Menu;
import com.adtec.sys.modules.sys.entity.SysQuickEntryDO;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * SysQuickEntryService
 * @author zx
 * @version 20190904
 */
@Service
@Transactional(readOnly = true)
public class SysQuickEntryService {
	private final static Logger log = LoggerFactory.getLogger(SysQuickEntryService.class);
	
	@Autowired
	private SysQuickEntryDao sysQuickEntryDao;
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public SysQuickEntryDO get(String id) {
		return sysQuickEntryDao.get(id);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	public SysQuickEntryDO get(SysQuickEntryDO obj) {
		return sysQuickEntryDao.get(obj);
	}
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	public boolean insert(SysQuickEntryDO obj){
		int rs = 0;
		obj.preInsert();
		rs = sysQuickEntryDao.insert(obj);
		return rs>0? true:false;
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	public boolean update(SysQuickEntryDO obj){
		int rs = 0;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "更新数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getId())) {
			throw new BaseException(SysErr.E_IN_NULL, "id");
		}
		// 获取数据库保存的数据
		SysQuickEntryDO qryDO = sysQuickEntryDao.get(obj.getId());
		// 更新产品数据
		obj.setCrtr(qryDO.getCrtr());
		obj.setCrtTime(qryDO.getCrtTime());
		obj.preUpdate();		
		rs = sysQuickEntryDao.update(obj);
		return rs>0? true:false;
	}
	
	/**
	 * 根据主键id删除数据（一般为逻辑删除，更新del_flg字段为1）
	 * @param id
	 * @see public int delete(T entity)
	 * @return
	 */
	public boolean delete(String id){
		int rs = 0;
		if (DataUtil.isNullStr(id)) {
			throw new BaseException(SysErr.E_IN_NULL, "id");
		}
		rs = sysQuickEntryDao.delete(id);
		return rs>0? true:false;
	}
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flg字段为1）
	 * @param obj
	 * @return
	 */
	public boolean delete(SysQuickEntryDO obj){
		int rs = 0;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "删除数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getId())) {
			throw new BaseException(SysErr.E_IN_NULL, "id");
		}
		rs = sysQuickEntryDao.delete(obj);
		return rs>0? true:false;
	}
	
	/**
	 * 数据库多笔查询
	 * @param obj 数据对象DO
	 * @return List返回集合
	 */
	public List<SysQuickEntryDO> list(SysQuickEntryDO obj) {
		return sysQuickEntryDao.list(obj);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param obj 数据对象DO
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @return List返回集合
	 */
	public List<SysQuickEntryDO> list(SysQuickEntryDO obj, int start, int limit) {
		return sysQuickEntryDao.list(obj, start, limit);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @param param 查询参数
	 * @return List返回集合
	 */
	public List<SysQuickEntryDO> list(int start, int limit, Object... param) {
		return sysQuickEntryDao.list(start, limit, param);
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(SysQuickEntryDO obj) {
		return sysQuickEntryDao.getTotal(obj);
	}
	
	
    public List<Map<String, Object>> getQuickEntry() {
        List<Map<String, Object>> maps = Lists.newArrayList();
        SysQuickEntryDO obj = new SysQuickEntryDO();
        obj.setUserId(UserUtils.getUser().getId());
        List<SysQuickEntryDO> list = sysQuickEntryDao.list(obj);
        for (SysQuickEntryDO s : list) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", s.getId());
            map.put("menuId", s.getMenuId());
            Menu menu = UserUtils.getMenuById(s.getMenuId());
            String menuName = "";
            String pMenuName = "";
            String gMenuName = "";
            String path = "";
            if(menu!=null){
                String pids = menu.getParentIdList();
                String[] arr = pids.split(",");
                if(arr.length > 3){
                    String gid = arr[2];
                    String pid = arr[3];
                    Menu pMenu = UserUtils.getMenuById(pid);
                    Menu gMenu = UserUtils.getMenuById(gid);
                    menuName = menu.getName();
                    pMenuName = pMenu.getName();
                    gMenuName = gMenu.getName();
                    path = gMenuName+"->"+pMenuName+"->"+menuName;
                }
                map.put("menuName", menuName);
                map.put("pmenuName", pMenuName);
                map.put("path", path);
                map.put("img", s.getImg());
                //由于界面排版，最多只显示9个快捷入口
                if(maps.size() < 9){
                	maps.add(map);
                }
            }
        }
        return maps;
    }
}