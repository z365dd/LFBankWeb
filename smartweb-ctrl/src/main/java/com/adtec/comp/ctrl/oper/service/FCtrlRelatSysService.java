package com.adtec.comp.ctrl.oper.service;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adtec.comp.ctrl.oper.dao.FCtrlRelatSysDao;
import com.adtec.comp.ctrl.oper.entity.FCtrlRelatSysDO;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.SysErr;

@Service
@Transactional(readOnly = true)
public class FCtrlRelatSysService {
	private final static Logger log = LoggerFactory.getLogger(FCtrlRelatSysService.class);

	@Autowired
	private FCtrlRelatSysDao relatSysDao;
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public FCtrlRelatSysDO get(String id) {
		return relatSysDao.get(id);
	}

	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	public FCtrlRelatSysDO get(FCtrlRelatSysDO obj) {
		return relatSysDao.get(obj);
	}

	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	public boolean insert(FCtrlRelatSysDO obj){
		int rs = 0;
		obj.preInsert();
		rs = relatSysDao.insert(obj);
		return rs>0? true:false;
	}

	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	public boolean update(FCtrlRelatSysDO obj){
		int rs = 0;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "更新数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getRelatSys())) {
			throw new BaseException(SysErr.E_IN_NULL, "RelatSys");
		}
		// 获取数据库保存的数据
		FCtrlRelatSysDO qryDO = relatSysDao.get(obj.getRelatSys());
		// 更新产品数据
		obj.setCrtr(qryDO.getCrtr());
		obj.setCrtTime(qryDO.getCrtTime());
		obj.preUpdate();
		rs = relatSysDao.update(obj);
		return rs>0? true:false;
	}

	/**
	 * 根据主键id删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param id
	 * @see public int delete(T entity)
	 * @return
	 */
	public boolean delete(String id){
		int rs = 0;
		if (DataUtil.isNullStr(id)) {
			throw new BaseException(SysErr.E_IN_NULL, "id");
		}
		rs = relatSysDao.delete(id);
		return rs>0? true:false;
	}

	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param obj
	 * @return
	 */
	public boolean delete(FCtrlRelatSysDO obj){
		int rs = 0;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "删除数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getRelatSys())) {
			throw new BaseException(SysErr.E_IN_NULL, "id");
		}
		rs = relatSysDao.delete(obj);
		return rs>0? true:false;
	}

	/**
	 * 数据库多笔查询
	 * @param obj 数据对象DO
	 * @return List返回集合
	 */
	public List<FCtrlRelatSysDO> list(FCtrlRelatSysDO obj) {
		return relatSysDao.list(obj);
	}

	/**
	 * 数据库多笔查询，支持分页
	 * @param obj 数据对象DO
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @return List返回集合
	 */
	public List<FCtrlRelatSysDO> list(FCtrlRelatSysDO obj, int start, int limit) {
		return relatSysDao.list(obj, start, limit);
	}

	/**
	 * 数据库多笔查询，支持分页
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @param param 查询参数
	 * @return List返回集合
	 */
	public List<FCtrlRelatSysDO> list(int start, int limit, Object... param) {
		return relatSysDao.list(start, limit, param);
	}
	
	/**
	 * 根据关联系统号查询
	 * @param obj
	 * @return
	 */
	public List<FCtrlRelatSysDO> qryRelatSys(FCtrlRelatSysDO obj) {
		return relatSysDao.qryRelatSys(obj);
	}

	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(FCtrlRelatSysDO obj) {
		return relatSysDao.getTotal(obj);
	}


	public void add(FCtrlRelatSysDO reqBody) {
		relatSysDao.insert(reqBody);
	}
	
	
	/**
	 * 获取详细数据 after
	 * @param DO
	 * @return
	 */
	public FCtrlRelatSysDO getDetail(FCtrlRelatSysDO DO){
		return relatSysDao.get(DO) ;
	}
	/**
	 * 修改状态
	 * @param FCtrlRelatSysDO
	 */
	public int UpdateStat(FCtrlRelatSysDO FCtrlRelatSysDO) {
		return relatSysDao.updateStat(FCtrlRelatSysDO);
		
	}


	
}
