/**
* 系统名称: SmartWeb平台
* 模块名称: sys-modules服务模块
* 功能描述: 请求IP信息表服务提供类
* 类 名 称  : SysReqIpMsgService.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20211021<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.sys.modules.sys.service;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.sys.modules.sys.dao.SysReqIpMsgDao;
import com.adtec.sys.modules.sys.entity.SysReqIpMsgDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * SysReqIpMsgService
 * @author zengxj
 * @version 20211021
 */
@Service
@Transactional(readOnly = true)
public class SysReqIpMsgService {
	private final static Logger log = LoggerFactory.getLogger(SysReqIpMsgService.class);
	
	@Autowired
	private SysReqIpMsgDao sysReqIpMsgDao;
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public SysReqIpMsgDO get(String id) {
		return sysReqIpMsgDao.get(id);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	public SysReqIpMsgDO get(SysReqIpMsgDO obj) {
		return sysReqIpMsgDao.get(obj);
	}
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	public void insert(SysReqIpMsgDO obj){
		obj.preInsert();
		sysReqIpMsgDao.insert(obj);
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	public void update(SysReqIpMsgDO obj){
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "更新数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getId())) {
			throw new BaseException(SysErr.E_IN_NULL, "id");
		}		

		// 获取数据库保存的数据
		SysReqIpMsgDO qryDO = sysReqIpMsgDao.get(obj.getId());		
		// 更新产品数据
		obj.setCrtr(qryDO.getCrtr());
		obj.setCrtTime(qryDO.getCrtTime());
		obj.preUpdate();		
		sysReqIpMsgDao.update(obj);		
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
			sysReqIpMsgDao.delete(id);
		}catch(Exception e){
			log.error("删除交易失败", e);
			throw new BaseException(SysErr.E_MESSAGE, "删除交易失败！"+e.getMessage());
		}		
	}
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flg字段为1）
	 * @param obj
	 * @return
	 */
	public void delete(SysReqIpMsgDO obj){
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "删除数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getId())) {
			throw new BaseException(SysErr.E_IN_NULL, "id");
		}		
		
		try{
			sysReqIpMsgDao.delete(obj);
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
	public List<SysReqIpMsgDO> list(SysReqIpMsgDO obj) {
		return sysReqIpMsgDao.list(obj);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param obj 数据对象DO
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @return List返回集合
	 */
	public List<SysReqIpMsgDO> list(SysReqIpMsgDO obj, int start, int limit) {
		return sysReqIpMsgDao.list(obj, start, limit);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @param param 查询参数
	 * @return List返回集合
	 */
	public List<SysReqIpMsgDO> list(int start, int limit, Object... param) {
		return sysReqIpMsgDao.list(start, limit, param);
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(SysReqIpMsgDO obj) {
		return sysReqIpMsgDao.getTotal(obj);
	}
}