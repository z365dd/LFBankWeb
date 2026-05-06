/**
* 系统名称: Smartweb平台
* 模块名称: sys-modules服务模块
* 功能描述: 日志记录映射管理服务提供类
* 类 名 称  : LogMappingService.java
* 软件版权: XXX公司
* 开发人员: chenyl <br>
* 开发时间: 20210429<br>
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
import com.adtec.sys.modules.sys.dao.LogMappingDao;
import com.adtec.sys.modules.sys.entity.LogMappingDO;

/**
 * LogMappingService
 * @author 陈应龙
 * @version 20210429
 */
@Service
@Transactional(readOnly = true)
public class LogMappingService {
	private final static Logger log = LoggerFactory.getLogger(LogMappingService.class);
	
	@Autowired
	private LogMappingDao logMappingDao;
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public LogMappingDO get(String id) {
		return logMappingDao.get(id);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	public LogMappingDO get(LogMappingDO obj) {
		return logMappingDao.get(obj);
	}
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	public void insert(LogMappingDO obj){
		obj.preInsert();
		logMappingDao.insert(obj);
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	public void update(LogMappingDO obj){
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "更新数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getId())) {
			throw new BaseException(SysErr.E_IN_NULL, "id");
		}		

		// 获取数据库保存的数据
		LogMappingDO qryDO = logMappingDao.get(obj.getId());		
		// 更新产品数据
		obj.setCrtr(qryDO.getCrtr());
		obj.setCrtTime(qryDO.getCrtTime());
		obj.preUpdate();		
		logMappingDao.update(obj);		
	}
	
	/**
	 * 根据主键删除数据
	 * @param id
	 * @see public int delete(T entity)
	 * @return
	 */
	public void delete(String id){
		if (DataUtil.isNullStr(id)) {
			throw new BaseException(SysErr.E_IN_NULL, "id");
		}		
		
		try{
			logMappingDao.delete(id);
		}catch(Exception e){
			log.error("删除交易失败", e);
			throw new BaseException(SysErr.E_MESSAGE, "删除交易失败！"+e.getMessage());
		}		
	}
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param obj
	 * @return
	 */
	public void delete(LogMappingDO obj){
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "删除数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getId())) {
			throw new BaseException(SysErr.E_IN_NULL, "id");
		}		
		
		try{
			logMappingDao.delete(obj);
		}catch(Exception e){
			log.error("删除交易失败", e);
			throw new BaseException(SysErr.E_MESSAGE, "删除交易失败！"+e.getMessage());
		}
	}
	
	/**
	 * 数据库多笔查询
	 * @param obj 数据对象DO
	 * @return List返回集合
	 */
	public List<LogMappingDO> list(LogMappingDO obj) {
		return logMappingDao.list(obj);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param obj 数据对象DO
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @return List返回集合
	 */
	public List<LogMappingDO> list(LogMappingDO obj, int start, int limit) {
		return logMappingDao.list(obj, start, limit);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @param param 查询参数
	 * @return List返回集合
	 */
	public List<LogMappingDO> list(int start, int limit, Object... param) {
		return logMappingDao.list(start, limit, param);
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(LogMappingDO obj) {
		return logMappingDao.getTotal(obj);
	}
}