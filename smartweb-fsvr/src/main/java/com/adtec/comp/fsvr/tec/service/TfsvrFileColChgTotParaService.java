/**
* 系统名称: SmartWeb平台
* 模块名称: comp.fsvr.tec服务模块
* 功能描述: 文件格式字段转换汇总参数表服务提供类
* 类 名 称  : TfsvrFileColChgTotParaService.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200630<br>
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

import com.adtec.comp.fsvr.tec.dao.TfsvrFileColChgTotParaDao;
import com.adtec.comp.fsvr.tec.entity.TfsvrFileColChgTotParaDO;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;

/**
 * TfsvrFileColChgTotParaService
 * @author zhengjt
 * @version 20200630
 */
@Service
@Transactional(readOnly = true)
public class TfsvrFileColChgTotParaService {
	private final static Logger log = LoggerFactory.getLogger(TfsvrFileColChgTotParaService.class);
	
	@Autowired
	private TfsvrFileColChgTotParaDao tfsvrFileColChgTotParaDao;
	
	/**
	 * 获取单条数据
	 * @param chgNo
	 * @return
	 */
	public TfsvrFileColChgTotParaDO get(
		String chgNo
	) {
		return tfsvrFileColChgTotParaDao.get(
		chgNo
);
	}
	
	/**
	 * 获取最大流水号
	 * @param chgNo
	 * @return
	 */
	public String getMaxChgNo() {
		return tfsvrFileColChgTotParaDao.getMaxChgNo();
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	public TfsvrFileColChgTotParaDO get(TfsvrFileColChgTotParaDO obj) {
		return tfsvrFileColChgTotParaDao.get(obj);
	}
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	public boolean insert(TfsvrFileColChgTotParaDO obj){
		int rs = 0;
		obj.preInsert();
		rs = tfsvrFileColChgTotParaDao.insert(obj);
		return rs>0? true:false;
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	public boolean update(TfsvrFileColChgTotParaDO obj){
		int rs = 0;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "更新数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getChgNo())) {
			throw new BaseException(SysErr.E_IN_NULL, "chgNo");
		}		

		// 获取数据库保存的数据
		TfsvrFileColChgTotParaDO qryDO = tfsvrFileColChgTotParaDao.get(
		obj.getChgNo()
		);
		// 更新产品数据
		obj.setCrtr(qryDO.getCrtr());
		obj.setCrtTime(qryDO.getCrtTime());
		obj.preUpdate();		
		rs = tfsvrFileColChgTotParaDao.update(obj);
		return rs>0? true:false;
	}
	
	/**
	 * 根据主键删除数据
	 * @param chgNo
	 * @see public int delete(T entity)
	 * @return
	 */
	public boolean delete(
		String chgNo
	){
		boolean rs = false;
		if (DataUtil.isNullStr(chgNo)) {
			throw new BaseException(SysErr.E_IN_NULL, "chgNo");
		}		

		try{
			tfsvrFileColChgTotParaDao.delete(
				chgNo
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
	public boolean delete(TfsvrFileColChgTotParaDO obj){
		boolean rs = false;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "删除数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getChgNo())) {
			throw new BaseException(SysErr.E_IN_NULL, "chgNo");
		}		
		
		try{
			tfsvrFileColChgTotParaDao.delete(obj);
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
	public List<TfsvrFileColChgTotParaDO> list(TfsvrFileColChgTotParaDO obj) {
		return tfsvrFileColChgTotParaDao.list(obj);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param obj 数据对象DO
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @return List返回集合
	 */
	public List<TfsvrFileColChgTotParaDO> list(TfsvrFileColChgTotParaDO obj, int start, int limit) {
		return tfsvrFileColChgTotParaDao.list(obj, start, limit);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @param param 查询参数
	 * @return List返回集合
	 */
	public List<TfsvrFileColChgTotParaDO> list(int start, int limit, Object... param) {
		return tfsvrFileColChgTotParaDao.list(start, limit, param);
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(TfsvrFileColChgTotParaDO obj) {
		return tfsvrFileColChgTotParaDao.getTotal(obj);
	}
}