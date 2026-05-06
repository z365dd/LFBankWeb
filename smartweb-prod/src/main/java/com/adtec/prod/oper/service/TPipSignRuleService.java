/**
* 系统名称: SmartWeb平台
* 模块名称: comp.prod-oper服务模块
* 功能描述: 签约规则参数服务提供类
* 类 名 称  : TPipSignRuleService.java
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

import com.adtec.prod.oper.dao.BusiSignDao;
import com.adtec.prod.oper.dao.TPipSignRuleDao;
import com.adtec.prod.oper.dao.TPipSignRuleLimDao;
import com.adtec.prod.oper.entity.TPipSignRuleDO;
import com.adtec.prod.oper.entity.TPipSignRuleLimDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.SysErr;

/**
 * TPipSignRuleService
 * @author linyx
 * @version 20200309
 */
@Service
@Transactional(readOnly = true)
public class TPipSignRuleService {
	private final static Logger log = LoggerFactory.getLogger(TPipSignRuleService.class);
	
	@Autowired
	private TPipSignRuleDao tPipSignRuleDao;
	@Autowired
	private TPipSignRuleLimDao tPipSignRuleLimDao;
	@Autowired
	private BusiSignDao busiSignDao;
	
	/**
	 * 获取单条数据
	 * @param ruleId
	 * @return
	 */
	public TPipSignRuleDO get(
		String ruleId
	) {
		return tPipSignRuleDao.get(
		ruleId
);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	public TPipSignRuleDO get(TPipSignRuleDO obj) {
		return tPipSignRuleDao.get(obj);
	}
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	public boolean insert(TPipSignRuleDO obj){
		int rs = 0;
		obj.preInsert();
		rs = tPipSignRuleDao.insert(obj);
		return rs>0? true:false;
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	public int update(TPipSignRuleDO tPipSignRuleDO,List<TPipSignRuleLimDO> tPipSignRuleLimDOList){
		int ret=0;
		//更新T_PIP_BUSI表标志
		String flgVal="";
		if("Y".equals(tPipSignRuleDO.getSignFlg())) {//需要签约
			flgVal = "01";
		}else {
			flgVal = "00";
		}
		busiSignDao.updateByRule("SIGN", tPipSignRuleDO.getRuleId(), flgVal);
		
		//限额参数处理
		tPipSignRuleLimDao.delete(tPipSignRuleDO.getRuleId(), null,null);
		for (TPipSignRuleLimDO tPipSignRuleLimDO : tPipSignRuleLimDOList) {
			tPipSignRuleLimDO.setRuleId(tPipSignRuleDO.getRuleId());
			tPipSignRuleLimDao.insert(tPipSignRuleLimDO);
		}
		//签约规则处理
		ret=tPipSignRuleDao.update(tPipSignRuleDO);
		
		return ret;
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
			tPipSignRuleDao.delete(
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
	public boolean delete(TPipSignRuleDO obj){
		boolean rs = false;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "删除数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getRuleId())) {
			throw new BaseException(SysErr.E_IN_NULL, "ruleId");
		}		
		
		try{
			tPipSignRuleDao.delete(obj);
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
	public List<TPipSignRuleDO> list(TPipSignRuleDO obj) {
		return tPipSignRuleDao.list(obj);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param obj 数据对象DO
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @return List返回集合
	 */
	public List<TPipSignRuleDO> list(TPipSignRuleDO obj, int start, int limit) {
		return tPipSignRuleDao.list(obj, start, limit);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @param param 查询参数
	 * @return List返回集合
	 */
	public List<TPipSignRuleDO> list(int start, int limit, Object... param) {
		return tPipSignRuleDao.list(start, limit, param);
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(TPipSignRuleDO obj) {
		return tPipSignRuleDao.getTotal(obj);
	}
}