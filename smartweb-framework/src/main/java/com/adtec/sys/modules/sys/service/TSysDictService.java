package com.adtec.sys.modules.sys.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.sys.modules.sys.entity.TSysDictDO;
import com.adtec.sys.modules.sys.dao.TSysDictDao;

/**
 * TSysDictService
 * @author zh
 * @version 20200630
 */
@Service
@Transactional(readOnly = true)
public class TSysDictService {
	private final static Logger log = LoggerFactory.getLogger(TSysDictService.class);
	
	@Autowired
	private TSysDictDao tSysDictDao;

	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	public TSysDictDO get(TSysDictDO obj) {
		return tSysDictDao.get(obj);
	}

	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	public TSysDictDO getVal(TSysDictDO obj) {
		return tSysDictDao.getVal(obj);
	}

	public TSysDictDO getSort(TSysDictDO obj) {
		return tSysDictDao.getSort(obj);
	}

	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	public boolean insert(TSysDictDO obj){
		int rs = 0;
		obj.preInsert();
		rs = tSysDictDao.insert(obj);
		return rs>0? true:false;
	}

	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	public boolean insertVal(TSysDictDO obj){
		int rs = 0;
		obj.preInsert();
		rs = tSysDictDao.insertVal(obj);
		return true;
	}

	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	public boolean update(TSysDictDO obj){
		int rs = 0;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "更新数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getDictTp())) {
			throw new BaseException(SysErr.E_IN_NULL, "dictTp");
		}

		TSysDictDO temp = new TSysDictDO();
		temp.setDictTp(obj.getDictTp());
		// 获取数据库保存的数据
		TSysDictDO qryDO = tSysDictDao.get(temp);
		// 更新产品数据
		obj.setCrtr(qryDO.getCrtr());
		obj.setCrtTime(qryDO.getCrtTime());
		obj.preUpdate();		
		rs = tSysDictDao.update(obj);
		return rs>0? true:false;
	}

	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	public boolean updateVal(TSysDictDO obj){
		int rs = 0;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "更新数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getDictTp())) {
			throw new BaseException(SysErr.E_IN_NULL, "dictTp");
		}
		if (DataUtil.isNullStr(obj.getDictVal())) {
			throw new BaseException(SysErr.E_IN_NULL, "dictVal");
		}

		TSysDictDO temp = new TSysDictDO();
		temp.setDictTp(obj.getDictTp());
		temp.setDictVal(obj.getDictVal());
		// 获取数据库保存的数据
		TSysDictDO qryDO = tSysDictDao.getVal(temp);
		// 更新产品数据
		obj.setCrtr(qryDO.getCrtr());
		obj.setCrtTime(qryDO.getCrtTime());
		obj.preUpdate();
		rs = tSysDictDao.updateVal(obj);
		return rs>0? true:false;
	}

	/**
	 * 删除数据（一般为逻辑删除，更新del_flg字段为1）
	 * @param obj
	 * @return
	 */
	public boolean delete(TSysDictDO obj){
		boolean rs = false;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "删除数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getDictTp())) {
			throw new BaseException(SysErr.E_IN_NULL, "dictTp");
		}		
		
		try{
			tSysDictDao.delete(obj);
			rs = true;
		}catch(Exception e){
			log.error("删除交易失败", e);
		}
		
		return rs;
	}

	/**
	 * 删除数据（一般为逻辑删除，更新del_flg字段为1）
	 * @param obj
	 * @return
	 */
	public boolean deleteVal(TSysDictDO obj){
		boolean rs = false;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "删除数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getDictTp())) {
			throw new BaseException(SysErr.E_IN_NULL, "dictTp");
		}

		try{
			tSysDictDao.deleteVal(obj);
			rs = true;
		}catch(Exception e){
			log.error("删除交易失败", e);
		}

		return rs;
	}

	/**
	 * 数据库多笔查询，支持分页
	 * @param obj 数据对象DO
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @return List返回集合
	 */
	public List<TSysDictDO> list(TSysDictDO obj, int start, int limit) {
		return tSysDictDao.list(obj, start, limit);
	}

	/**
	 * 数据库多笔查询，支持分页
	 * @param obj 数据对象DO
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @return List返回集合
	 */
	public List<TSysDictDO> listVal(TSysDictDO obj, int start, int limit) {
		return tSysDictDao.listVal(obj, start, limit);
	}

	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(TSysDictDO obj) {
		return tSysDictDao.getTotal(obj);
	}

	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotalVal(TSysDictDO obj) {
		return tSysDictDao.getTotalVal(obj);
	}

}