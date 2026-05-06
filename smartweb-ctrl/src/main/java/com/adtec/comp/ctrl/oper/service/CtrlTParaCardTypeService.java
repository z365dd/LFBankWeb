/**
* 系统名称: SmartWeb平台
* 模块名称: comp.ctrl.oper服务模块
* 功能描述: 卡种类管理服务提供类
* 类 名 称  : CtrlTParaCardTypeService.java
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
import com.adtec.comp.ctrl.oper.entity.CtrlTParaCardTypeDO;
import com.adtec.comp.ctrl.oper.dao.CtrlTParaCardTypeDao;

/**
 * CtrlTParaCardTypeService
 * @author zhengjt
 * @version 20200310
 */
@Service
@Transactional(readOnly = true)
public class CtrlTParaCardTypeService {
	private final static Logger log = LoggerFactory.getLogger(CtrlTParaCardTypeService.class);
	
	@Autowired
	private CtrlTParaCardTypeDao ctrlTParaCardTypeDao;
	
	/**
	 * 获取单条数据
	 * @param cardBinNo
	 * @return
	 */
	public CtrlTParaCardTypeDO get(
		String cardBinNo
	) {
		return ctrlTParaCardTypeDao.get(
		cardBinNo
);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	public CtrlTParaCardTypeDO get(CtrlTParaCardTypeDO obj) {
		return ctrlTParaCardTypeDao.get(obj);
	}
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	public boolean insert(CtrlTParaCardTypeDO obj){
		int rs = 0;
		obj.preInsert();
		rs = ctrlTParaCardTypeDao.insert(obj);
		return rs>0? true:false;
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	public boolean update(CtrlTParaCardTypeDO obj){
		int rs = 0;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "更新数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getCardBinNo())) {
			throw new BaseException(SysErr.E_IN_NULL, "cardBinNo");
		}		

		// 获取数据库保存的数据
		CtrlTParaCardTypeDO qryDO = ctrlTParaCardTypeDao.get(
		obj.getCardBinNo()
		);
		// 更新产品数据
		obj.setCrtr(qryDO.getCrtr());
		obj.setCrtTime(qryDO.getCrtTime());
		obj.preUpdate();		
		rs = ctrlTParaCardTypeDao.update(obj);
		return rs>0? true:false;
	}
	
	/**
	 * 根据主键删除数据
	 * @param cardBinNo
	 * @see public int delete(T entity)
	 * @return
	 */
	public boolean delete(
		String cardBinNo
	){
		boolean rs = false;
		if (DataUtil.isNullStr(cardBinNo)) {
			throw new BaseException(SysErr.E_IN_NULL, "cardBinNo");
		}		

		try{
			ctrlTParaCardTypeDao.delete(
				cardBinNo
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
	public boolean delete(CtrlTParaCardTypeDO obj){
		boolean rs = false;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "删除数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getCardBinNo())) {
			throw new BaseException(SysErr.E_IN_NULL, "cardBinNo");
		}		
		
		try{
			ctrlTParaCardTypeDao.delete(obj);
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
	public List<CtrlTParaCardTypeDO> list(CtrlTParaCardTypeDO obj) {
		return ctrlTParaCardTypeDao.list(obj);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param obj 数据对象DO
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @return List返回集合
	 */
	public List<CtrlTParaCardTypeDO> list(CtrlTParaCardTypeDO obj, int start, int limit) {
		return ctrlTParaCardTypeDao.list(obj, start, limit);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @param param 查询参数
	 * @return List返回集合
	 */
	public List<CtrlTParaCardTypeDO> list(int start, int limit, Object... param) {
		return ctrlTParaCardTypeDao.list(start, limit, param);
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(CtrlTParaCardTypeDO obj) {
		return ctrlTParaCardTypeDao.getTotal(obj);
	}
}