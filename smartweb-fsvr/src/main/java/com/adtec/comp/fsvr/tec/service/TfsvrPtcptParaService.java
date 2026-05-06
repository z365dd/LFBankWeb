/**
* 系统名称: SmartWeb平台
* 模块名称: comp.fsvr.tec服务模块
* 功能描述: 调用方信息服务提供类
* 类 名 称  : TfsvrPtcptParaService.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200622<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.comp.fsvr.tec.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adtec.comp.fsvr.tec.dao.TfsvrPtcptParaDao;
import com.adtec.comp.fsvr.tec.entity.TfsvrPtcptParaDO;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;

/**
 * TfsvrPtcptParaService
 * @author zhengjt
 * @version 20200622
 */
@Service
@Transactional(readOnly = true)
public class TfsvrPtcptParaService {
	private final static Logger log = LoggerFactory.getLogger(TfsvrPtcptParaService.class);
	
	@Autowired
	private TfsvrPtcptParaDao tfsvrPtcptParaDao;
	
	/**
	 * 获取单条数据
	 * @param callerId
	 * @return
	 */
	public TfsvrPtcptParaDO get(
		String callerId
	) {
		return tfsvrPtcptParaDao.get(
		callerId
);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	public TfsvrPtcptParaDO get(TfsvrPtcptParaDO obj) {
		return tfsvrPtcptParaDao.get(obj);
	}
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	public boolean insert(TfsvrPtcptParaDO obj){
		int rs = 0;
		obj.preInsert();
		rs = tfsvrPtcptParaDao.insert(obj);
		return rs>0? true:false;
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	public boolean update(TfsvrPtcptParaDO obj){
		int rs = 0;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "更新数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getCallerId())) {
			throw new BaseException(SysErr.E_IN_NULL, "callerId");
		}		

		// 获取数据库保存的数据
		TfsvrPtcptParaDO qryDO = tfsvrPtcptParaDao.get(
		obj.getCallerId()
		);
		// 更新产品数据
		obj.setCrtr(qryDO.getCrtr());
		obj.setCrtTime(qryDO.getCrtTime());
		obj.preUpdate();		
		rs = tfsvrPtcptParaDao.update(obj);
		return rs>0? true:false;
	}
	
	/**
	 * 根据主键删除数据
	 * @param callerId
	 * @see public int delete(T entity)
	 * @return
	 */
	public boolean delete(
		String callerId
	){
		boolean rs = false;
		if (DataUtil.isNullStr(callerId)) {
			throw new BaseException(SysErr.E_IN_NULL, "callerId");
		}		

		try{
			tfsvrPtcptParaDao.delete(
				callerId
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
	public boolean delete(TfsvrPtcptParaDO obj){
		boolean rs = false;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "删除数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getCallerId())) {
			throw new BaseException(SysErr.E_IN_NULL, "callerId");
		}		
		
		try{
			tfsvrPtcptParaDao.delete(obj);
			rs = true;
		}catch(Exception e){
			log.error("删除交易失败", e);
		}
		
		return rs;
	}
	
	/**
	 * 开通状态修改
	 * @param obj
	 * @return
	 */
	public boolean statChange(TfsvrPtcptParaDO obj){
		int rs = 0;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "状态修改数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getCallerId())) {
			throw new BaseException(SysErr.E_IN_NULL, "callerId");
		}		
		if (DataUtil.isNullStr(obj.getStat())) {
			throw new BaseException(SysErr.E_IN_NULL, "stat");
		}	
		try{
			rs = tfsvrPtcptParaDao.statChange(obj);
		}catch(Exception e){
			log.error("状态修改失败", e);
		}
		
		return rs>0? true:false;
	}
	
	/**
	 * 数据库多笔查询
	 * @param obj 数据对象DO
	 * @return List返回集合
	 */
	public List<TfsvrPtcptParaDO> list(TfsvrPtcptParaDO obj) {
		return tfsvrPtcptParaDao.list(obj);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param obj 数据对象DO
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @return List返回集合
	 */
	public List<TfsvrPtcptParaDO> list(TfsvrPtcptParaDO obj, int start, int limit) {
		return tfsvrPtcptParaDao.list(obj, start, limit);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @param param 查询参数
	 * @return List返回集合
	 */
	public List<TfsvrPtcptParaDO> list(int start, int limit, Object... param) {
		return tfsvrPtcptParaDao.list(start, limit, param);
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(TfsvrPtcptParaDO obj) {
		return tfsvrPtcptParaDao.getTotal(obj);
	}
}