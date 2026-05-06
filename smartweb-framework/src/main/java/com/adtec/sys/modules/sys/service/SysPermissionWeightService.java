/**
* 系统名称: SmartWeb平台
* 模块名称: sys服务模块
* 功能描述: 权限维度服务提供类
* 类 名 称  : SysPermissionWeightService.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20190901<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.sys.modules.sys.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.sys.modules.sys.dao.SysPermissionWeightDao;
import com.adtec.sys.modules.sys.entity.SysPermissionWeightDO;

/**
 * SysPermissionWeightService
 * @author 权限维度
 * @version 20190901
 */
@Service
@Transactional(readOnly = true)
public class SysPermissionWeightService {
	private final static Logger log = LoggerFactory.getLogger(SysPermissionWeightService.class);
	
	@Autowired
	private SysPermissionWeightDao sysPermissionWeightDao;
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public SysPermissionWeightDO get(String id) {
		return sysPermissionWeightDao.get(id);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	public SysPermissionWeightDO get(SysPermissionWeightDO obj) {
		return sysPermissionWeightDao.get(obj);
	}

	public boolean initPermission(String isAll) {
		return sysPermissionWeightDao.initPermission(isAll);
	}

	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	public boolean insert(SysPermissionWeightDO obj){
		int rs = 0;
		obj.preInsert();
		rs = sysPermissionWeightDao.insert(obj);
		return rs>0? true:false;
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	public boolean update(SysPermissionWeightDO obj){
		// 角色维度的权限分配跨越下级，只能1
		if ("role".equals(obj.getWghtName()) && "0".equals(obj.getAuthLvlSwitchFlg())) {
			throw new BaseException(SysErr.E_DEFAULT, "所有角色之间是平级关系，无需修改跨级标志！");
		}
		int rs = 0;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "更新数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getId())) {
			throw new BaseException(SysErr.E_IN_NULL, "id");
		}
		// 获取数据库保存的数据
		SysPermissionWeightDO qryDO = sysPermissionWeightDao.get(obj.getId());
		// 更新产品数据
		obj.setCrtr(qryDO.getCrtr());
		obj.setCrtTime(qryDO.getCrtTime());
		obj.preUpdate();		
		rs = sysPermissionWeightDao.update(obj);
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
		rs = sysPermissionWeightDao.delete(id);
		return rs>0? true:false;
	}
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flg字段为1）
	 * @param obj
	 * @return
	 */
	public boolean delete(SysPermissionWeightDO obj){
		int rs = 0;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "删除数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getId())) {
			throw new BaseException(SysErr.E_IN_NULL, "id");
		}
		rs = sysPermissionWeightDao.delete(obj);
		return rs>0? true:false;
	}
	
	/**
	 * 数据库多笔查询
	 * @param obj 数据对象DO
	 * @return List返回集合
	 */
	public List<SysPermissionWeightDO> list(SysPermissionWeightDO obj) {
		return sysPermissionWeightDao.list(obj);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param obj 数据对象DO
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @return List返回集合
	 */
	public List<SysPermissionWeightDO> list(SysPermissionWeightDO obj, int start, int limit) {
		return sysPermissionWeightDao.list(obj, start, limit);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @param param 查询参数
	 * @return List返回集合
	 */
	public List<SysPermissionWeightDO> list(int start, int limit, Object... param) {
		return sysPermissionWeightDao.list(start, limit, param);
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(SysPermissionWeightDO obj) {
		return sysPermissionWeightDao.getTotal(obj);
	}
}