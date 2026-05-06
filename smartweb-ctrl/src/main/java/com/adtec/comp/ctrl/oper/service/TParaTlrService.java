/**
* 系统名称: SmartWeb平台
* 模块名称: comp.ctrl.oper服务模块
* 功能描述: 柜员管理服务提供类
* 类 名 称  : TParaTlrService.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200310<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.comp.ctrl.oper.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.SysErr;
import com.adtec.comp.ctrl.oper.entity.CtrlTParaBrchDO;
import com.adtec.comp.ctrl.oper.entity.TParaTlrDO;
import com.adtec.comp.ctrl.oper.dao.CtrlTParaBrchDao;
import com.adtec.comp.ctrl.oper.dao.TParaTlrDao;

/**
 * TParaTlrService
 * @author zhengjt
 * @version 20200310
 */
@Service
@Transactional(readOnly = true)
public class TParaTlrService {
	private final static Logger log = LoggerFactory.getLogger(TParaTlrService.class);
	
	@Autowired
	private TParaTlrDao tParaTlrDao;
	@Autowired
	private CtrlTParaBrchDao tParaBrchDao;
	/**
	 * 获取单条数据
	 * @param brch
	 * @param tlrNo
	 * @return
	 */
	public TParaTlrDO get(
		String brch
				,
		String tlrNo
	) {
		return tParaTlrDao.get(
		brch
				,
		tlrNo
);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	public TParaTlrDO get(TParaTlrDO obj) {
		return tParaTlrDao.get(obj);
	}
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	public boolean insert(TParaTlrDO obj){
		int rs = 0;
		obj.preInsert();
		rs = tParaTlrDao.insert(obj);
		return rs>0? true:false;
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	public boolean update(TParaTlrDO obj){
		int rs = 0;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "更新数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getBrch())) {
			throw new BaseException(SysErr.E_IN_NULL, "brch");
		}		
		if (DataUtil.isNullStr(obj.getTlrNo())) {
			throw new BaseException(SysErr.E_IN_NULL, "tlrNo");
		}		

		// 获取数据库保存的数据
		TParaTlrDO qryDO = tParaTlrDao.get(
		obj.getBrch()
				,
		obj.getTlrNo()
		);
		// 更新产品数据
		obj.setCrtr(qryDO.getCrtr());
		obj.setCrtTime(qryDO.getCrtTime());
		obj.preUpdate();		
		rs = tParaTlrDao.update(obj);
		return rs>0? true:false;
	}
	
	/**
	 * 根据主键删除数据
	 * @param brch
	 * @param tlrNo
	 * @see public int delete(T entity)
	 * @return
	 */
	public boolean delete(
		String brch
				,
		String tlrNo
	){
		boolean rs = false;
		if (DataUtil.isNullStr(brch)) {
			throw new BaseException(SysErr.E_IN_NULL, "brch");
		}		
		if (DataUtil.isNullStr(tlrNo)) {
			throw new BaseException(SysErr.E_IN_NULL, "tlrNo");
		}		

		try{
			tParaTlrDao.delete(
				brch
				,
				tlrNo
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
	public boolean delete(TParaTlrDO obj){
		boolean rs = false;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "删除数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getBrch())) {
			throw new BaseException(SysErr.E_IN_NULL, "brch");
		}		
		if (DataUtil.isNullStr(obj.getTlrNo())) {
			throw new BaseException(SysErr.E_IN_NULL, "tlrNo");
		}		
		
		try{
			tParaTlrDao.delete(obj);
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
	public List<TParaTlrDO> list(TParaTlrDO obj) {
		return tParaTlrDao.list(obj);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param obj 数据对象DO
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @return List返回集合
	 */
	public List<TParaTlrDO> list(TParaTlrDO obj, int start, int limit) {
		return tParaTlrDao.list(obj, start, limit);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @param param 查询参数
	 * @return List返回集合
	 */
	public List<TParaTlrDO> list(int start, int limit, Object... param) {
		return tParaTlrDao.list(start, limit, param);
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(TParaTlrDO obj) {
		return tParaTlrDao.getTotal(obj);
	}
	
	/**
	 * 机构表多笔查询
	 * @param obj 数据对象DO
	 * @return List返回集合
	 */
	public List<CtrlTParaBrchDO> getBrch(CtrlTParaBrchDO tParaBrchDO) {
		return tParaBrchDao.list(tParaBrchDO);
	}
}