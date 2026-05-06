/**
* 系统名称: SmartWeb平台
* 模块名称: prod-oper服务模块
* 功能描述: T_PIP_SIGN_CHK_RULE服务提供类
* 类 名 称  : TPipSignChkRuleService.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200313<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.prod.oper.service;

import java.util.List;

import com.adtec.prod.oper.dao.TPipSignChkRuleDao;
import com.adtec.prod.oper.entity.TPipSignChkRuleDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.SysErr;

/**
 * TPipSignChkRuleService
 * @author linyx
 * @version 20200313
 */
@Service
@Transactional(readOnly = true)
public class TPipSignChkRuleService {
	private final static Logger log = LoggerFactory.getLogger(TPipSignChkRuleService.class);
	
	@Autowired
	private TPipSignChkRuleDao tPipSignChkRuleDao;
	
	/**
	 * 获取单条数据
	 * @param ruleId
	 * @return
	 */
	public TPipSignChkRuleDO get(
		String ruleId
	) {
		return tPipSignChkRuleDao.get(
		ruleId
);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	public TPipSignChkRuleDO get(TPipSignChkRuleDO obj) {
		return tPipSignChkRuleDao.get(obj);
	}
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	public boolean insert(TPipSignChkRuleDO obj){
		int rs = 0;
		obj.preInsert();
		rs = tPipSignChkRuleDao.insert(obj);
		return rs>0? true:false;
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	public boolean update(TPipSignChkRuleDO obj){
		int rs = 0;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "更新数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getRuleId())) {
			throw new BaseException(SysErr.E_IN_NULL, "ruleId");
		}		

		// 获取数据库保存的数据
		TPipSignChkRuleDO qryDO = tPipSignChkRuleDao.get(
		obj.getRuleId()
		);
		obj.preUpdate();		
		rs = tPipSignChkRuleDao.update(obj);
		return rs>0? true:false;
	}
	
	/**
	 * 根据主键删除数据
	 * @param ruleId
	 * @see public int delete(T entity)
	 * @return
	 */
	public boolean delete(
		String ruleId
	){
		boolean rs = false;
		if (DataUtil.isNullStr(ruleId)) {
			throw new BaseException(SysErr.E_IN_NULL, "ruleId");
		}		

		try{
			tPipSignChkRuleDao.delete(
				ruleId
			);
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
	public boolean delete(TPipSignChkRuleDO obj){
		boolean rs = false;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "删除数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getRuleId())) {
			throw new BaseException(SysErr.E_IN_NULL, "ruleId");
		}		
		
		try{
			tPipSignChkRuleDao.delete(obj);
			rs = true;
		}catch(Exception e){
			log.error("删除交易失败", e);
		}
		
		return rs;
	}
	
	/**
	 * 数据库多笔查询
	 * @param obj 数据对象DO
	 * @return List返回集合
	 */
	public List<TPipSignChkRuleDO> list(TPipSignChkRuleDO obj) {
		return tPipSignChkRuleDao.list(obj);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param obj 数据对象DO
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @return List返回集合
	 */
	public List<TPipSignChkRuleDO> list(TPipSignChkRuleDO obj, int start, int limit) {
		return tPipSignChkRuleDao.list(obj, start, limit);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @param param 查询参数
	 * @return List返回集合
	 */
	public List<TPipSignChkRuleDO> list(int start, int limit, Object... param) {
		return tPipSignChkRuleDao.list(start, limit, param);
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(TPipSignChkRuleDO obj) {
		return tPipSignChkRuleDao.getTotal(obj);
	}
}