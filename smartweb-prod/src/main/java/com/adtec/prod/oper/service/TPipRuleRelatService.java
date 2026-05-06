/**
* 系统名称: SmartWeb平台
* 模块名称: comp.prod-oper服务模块
* 功能描述: 签约规则参数服务提供类
* 类 名 称  : TPipRuleRelatService.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200309<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.prod.oper.service;

import java.util.List;

import com.adtec.prod.oper.dao.TPipRuleRelatDao;
import com.adtec.prod.oper.entity.TPipRuleRelatDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * TPipRuleRelatService
 * @author linyx
 * @version 20200309
 */
@Service
@Transactional(readOnly = true)
public class TPipRuleRelatService {
	private final static Logger log = LoggerFactory.getLogger(TPipRuleRelatService.class);
	
	@Autowired
	private TPipRuleRelatDao tPipRuleRelatDao;
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	public TPipRuleRelatDO get(TPipRuleRelatDO obj) {
		return tPipRuleRelatDao.get(obj);
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	public int update(TPipRuleRelatDO obj){
		return tPipRuleRelatDao.update(obj);
	}
	
	/**
	 * 数据库多笔查询
	 * @param obj 数据对象DO
	 * @return List返回集合
	 */
	public List<TPipRuleRelatDO> list(TPipRuleRelatDO obj) {
		return tPipRuleRelatDao.list(obj);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param obj 数据对象DO
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @return List返回集合
	 */
	public List<TPipRuleRelatDO> list(TPipRuleRelatDO obj, int start, int limit) {
		return tPipRuleRelatDao.list(obj, start, limit);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @param param 查询参数
	 * @return List返回集合
	 */
	public List<TPipRuleRelatDO> list(int start, int limit, Object... param) {
		return tPipRuleRelatDao.list(start, limit, param);
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(TPipRuleRelatDO obj) {
		return tPipRuleRelatDao.getTotal(obj);
	}
}