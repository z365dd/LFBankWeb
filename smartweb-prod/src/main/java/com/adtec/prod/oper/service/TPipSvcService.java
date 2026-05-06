/**
* 系统名称: SmartWeb平台
* 模块名称: comp.prod.oper服务模块
* 功能描述: 服务服务提供类
* 类 名 称  : TPipSvcService.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200108<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.prod.oper.service;

import java.util.List;

import com.adtec.prod.oper.dao.TPipSvcDao;
import com.adtec.prod.oper.entity.TPipSvcDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.SysErr;

/**
 * TPipSvcService
 * @author zh
 * @version 20200108
 */
@Service
@Transactional(readOnly = true)
public class TPipSvcService {
	private final static Logger log = LoggerFactory.getLogger(TPipSvcService.class);
	
	@Autowired
	private TPipSvcDao tPipSvcDao;
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public TPipSvcDO get(String id) {
		return tPipSvcDao.get(id);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	public TPipSvcDO get(TPipSvcDO obj) {
		return tPipSvcDao.get(obj);
	}
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	public boolean insert(TPipSvcDO obj){
		int rs = 0;
//		obj.preInsert();
		rs = tPipSvcDao.insert(obj);
		return rs>0? true:false;
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	public boolean update(TPipSvcDO obj){
		int rs = 0;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "更新数据对象不能空！");
		}
//		if (DataUtil.isNullStr(obj.getId())) {
//			throw new BaseException(SysErr.E_IN_NULL, "id");
//		}
		// 获取数据库保存的数据
//		TPipSvcDO qryDO = tPipSvcDao.get(obj.getId());
//		// 更新产品数据
//		obj.setCreateBy(qryDO.getCreateBy());
//		obj.setCreateDate(qryDO.getCreateDate());
//		obj.preUpdate();
		rs = tPipSvcDao.update(obj);
		return rs>0? true:false;
	}
	
	/**
	 * 根据主键id删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param id
	 * @see public int delete(T entity)
	 * @return
	 */
	public boolean delete(String id){
		boolean rs = false;
		if (DataUtil.isNullStr(id)) {
			throw new BaseException(SysErr.E_IN_NULL, "id");
		}
		try{
			tPipSvcDao.delete(id);
			rs = true;
		}catch(Exception e){
			log.error("删除交易失败", e);
		}
		
		return rs;
	}
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param obj
	 * @return
	 */
	public boolean delete(TPipSvcDO obj){
		boolean rs = false;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "删除数据对象不能空！");
		}
//		if (DataUtil.isNullStr(obj.getId())) {
//			throw new BaseException(SysErr.E_IN_NULL, "id");
//		}
		try{
			tPipSvcDao.delete(obj);
			rs = true;
		}catch(Exception e){
			log.error("删除交易失败", e);
			throw e;
		}
		
		return rs;
	}
	
	/**
	 * 数据库多笔查询
	 * @param obj 数据对象DO
	 * @return List返回集合
	 */
	public List<TPipSvcDO> list(TPipSvcDO obj) {
		return tPipSvcDao.list(obj);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param obj 数据对象DO
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @return List返回集合
	 */
	public List<TPipSvcDO> list(TPipSvcDO obj, int start, int limit) {
		return tPipSvcDao.list(obj, start, limit);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @param param 查询参数
	 * @return List返回集合
	 */
	public List<TPipSvcDO> list(int start, int limit, Object... param) {
		return tPipSvcDao.list(start, limit, param);
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(TPipSvcDO obj) {
		return tPipSvcDao.getTotal(obj);
	}

	/**
	 * 启用
	 * @param obj
	 */
	public void Enable(TPipSvcDO obj) {
		obj = tPipSvcDao.get(obj);
		obj.setOpenStat("Y");
		tPipSvcDao.update(obj);
	}

	/**
	 * 停用
	 * @param obj
	 */
	public void Stopping(TPipSvcDO obj) {
		obj = tPipSvcDao.get(obj);
		obj.setOpenStat("N");
		tPipSvcDao.update(obj);
	}
}