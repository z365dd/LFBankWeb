/**
* 系统名称: SmartWeb平台
* 模块名称: comp.ctrl.oper服务模块
* 功能描述: 法人管理服务提供类
* 类 名 称  : CtrlTParaLegaService.java
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
import com.adtec.comp.ctrl.oper.entity.CtrlTParaLegaAndTntDO;
import com.adtec.comp.ctrl.oper.entity.CtrlTParaLegaDO;
import com.adtec.comp.ctrl.oper.dao.CtrlTParaLegaDao;

/**
 * CtrlTParaLegaService
 * @author zhengjt
 * @version 20200310
 */
@Service
@Transactional(readOnly = true)
public class CtrlTParaLegaService {
	private final static Logger log = LoggerFactory.getLogger(CtrlTParaLegaService.class);
	
	@Autowired
	private CtrlTParaLegaDao ctrlTParaLegaDao;
	
	/**
	 * 获取单条数据
	 * @param legaNo
	 * @return
	 */
	public CtrlTParaLegaDO get(
		String legaNo
	) {
		return ctrlTParaLegaDao.get(
		legaNo
);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	public CtrlTParaLegaDO get(CtrlTParaLegaDO obj) {
		return ctrlTParaLegaDao.get(obj);
	}
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	public boolean insert(CtrlTParaLegaDO obj){
		int rs = 0;
		obj.preInsert();
		rs = ctrlTParaLegaDao.insert(obj);
		return rs>0? true:false;
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	public boolean update(CtrlTParaLegaDO obj){
		int rs = 0;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "更新数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getLegaNo())) {
			throw new BaseException(SysErr.E_IN_NULL, "legaNo");
		}		

		// 获取数据库保存的数据
		CtrlTParaLegaDO qryDO = ctrlTParaLegaDao.get(
		obj.getLegaNo()
		);
		// 更新产品数据
		obj.setCrtr(qryDO.getCrtr());
		obj.setCrtTime(qryDO.getCrtTime());
		obj.preUpdate();		
		rs = ctrlTParaLegaDao.update(obj);
		return rs>0? true:false;
	}
	
	/**
	 * 根据主键删除数据
	 * @param legaNo
	 * @see public int delete(T entity)
	 * @return
	 */
	public boolean delete(
		String legaNo
	){
		boolean rs = false;
		if (DataUtil.isNullStr(legaNo)) {
			throw new BaseException(SysErr.E_IN_NULL, "legaNo");
		}		

		try{
			ctrlTParaLegaDao.delete(
				legaNo
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
	public boolean delete(CtrlTParaLegaDO obj){
		boolean rs = false;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "删除数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getLegaNo())) {
			throw new BaseException(SysErr.E_IN_NULL, "legaNo");
		}		
		
		try{
			ctrlTParaLegaDao.delete(obj);
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
	public List<CtrlTParaLegaDO> list(CtrlTParaLegaDO obj) {
		return ctrlTParaLegaDao.list(obj);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param obj 数据对象DO
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @return List返回集合
	 */
	public List<CtrlTParaLegaDO> list(CtrlTParaLegaDO obj, int start, int limit) {
		return ctrlTParaLegaDao.list(obj, start, limit);
	}
	
	public List<CtrlTParaLegaAndTntDO> listAndTnt(CtrlTParaLegaAndTntDO obj, int start, int limit) {
		return ctrlTParaLegaDao.listAndTnt(obj, start, limit);
	}
	/**
	 * 数据库多笔查询，支持分页
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @param param 查询参数
	 * @return List返回集合
	 */
	public List<CtrlTParaLegaDO> list(int start, int limit, Object... param) {
		return ctrlTParaLegaDao.list(start, limit, param);
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(CtrlTParaLegaDO obj) {
		return ctrlTParaLegaDao.getTotal(obj);
	}
}