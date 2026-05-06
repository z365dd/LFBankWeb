/**
* 系统名称: SmartWeb平台
* 模块名称: comp-prod/oper/definition/saleprod服务模块
* 功能描述: 可售产品管理服务提供类
* 类 名 称  : TPipSaleProdService.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200104<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.prod.oper.definition.saleprod.service;

import java.sql.SQLException;
import java.util.List;

import com.adtec.prod.oper.definition.saleprod.entity.TPipSaleProdDOForMsmall;
import com.adtec.prod.oper.line.dao.TPipLineProdDao;
import com.adtec.prod.oper.line.entity.TPipLineProdDO;
import com.adtec.framework.common.util.DateUtil;
import com.adtec.sys.modules.sys.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.common.util.DataUtil;
import com.alibaba.fastjson.JSON;
import com.adtec.prod.oper.definition.saleprod.entity.TPipSaleProdAtomProdDO;
import com.adtec.prod.oper.definition.saleprod.entity.TPipSaleProdDO;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.prod.oper.definition.saleprod.dao.TPipSaleProdAtomProdDao;
import com.adtec.prod.oper.definition.saleprod.dao.TPipSaleProdDao;

/**
 * TPipSaleProdService
 * @author zengxj
 * @version 20200104
 */
@Service
@Transactional(readOnly = true)
public class TPipSaleProdService {
	private final static Logger log = LoggerFactory.getLogger(TPipSaleProdService.class);
	
	@Autowired
	private TPipSaleProdDao tPipSaleProdDao;
	
	@Autowired
	private TPipSaleProdAtomProdDao tPipSaleProdAtomProdDao;
	@Autowired
	private TPipLineProdDao tPipLineProdDao;
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public TPipSaleProdDO get(String id) {
		return tPipSaleProdDao.get(id);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	public TPipSaleProdDO get(TPipSaleProdDO obj) {
		TPipSaleProdDO tPipSaleProdDO = tPipSaleProdDao.get(obj);
		TPipLineProdDO tPipLineProdDO = new TPipLineProdDO();
		List<TPipLineProdDO> list = tPipLineProdDao.list(tPipLineProdDO);
		for (TPipLineProdDO temp : list) {
			if (temp.getProdLineCode().equals(tPipSaleProdDO.getProdLineCode())) {
				tPipSaleProdDO.setProdLineName(temp.getProdLineName());
				break;
			}
		}
		return tPipSaleProdDO;
	}
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	public boolean insert(TPipSaleProdDO obj, String atomProdJson){
//		obj.preInsert();
		int rs = tPipSaleProdDao.getSaleProdCount(obj);
		if (rs > 0) {
			throw new BaseException(SysErr.E_MESSAGE, "可售产品["+obj.getSaleProdDesc()+"]已存在");
		}
		String prodLineCode = obj.getProdLineCode();
		String tmp = "0001";
		String maxCode = tPipSaleProdDao.getMaxCode(prodLineCode);
		if (!"".equals(maxCode)){
			maxCode = maxCode.substring(prodLineCode.length(), maxCode.length());
			int max = Integer.valueOf(maxCode);
			max++;
			tmp = String.format("%04d", max);
		}
		String saleProdCode = prodLineCode + tmp;
		obj.setSaleProdCode(saleProdCode);
		IDBSession session = DBSessionFactory.getSession();
		try {
			session.beginTransaction();
			obj.setCrtr(UserUtils.getUser().getId());
			obj.setCrtTime(DateUtil.getDateTime());
			obj.setUptr(UserUtils.getUser().getId());
			obj.setUptTime(DateUtil.getDateTime());
			rs = tPipSaleProdDao.insert(obj);
			List<TPipSaleProdAtomProdDO> list = JSON.parseArray(atomProdJson, TPipSaleProdAtomProdDO.class);
			
			for (TPipSaleProdAtomProdDO item : list) {
				item.setSaleProdCode(saleProdCode);
				tPipSaleProdAtomProdDao.insert(item);
			} 
			session.endTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				session.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_MESSAGE, e.getMessage());
		}
		return rs>0? true:false;
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	public boolean update(TPipSaleProdDO obj, String atomProdJson){
		int rs = 0;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "更新数据对象不能空！");
		}
		IDBSession session = DBSessionFactory.getSession();
		try {
			session.beginTransaction();
			if(tPipSaleProdDao.getAdapterCount(obj) >0 ) {
				throw new BaseException(SysErr.E_DEFAULT, "该可售产品已经包装，不能修改");
			} else {
				obj.setUptr(UserUtils.getUser().getId());
				obj.setUptTime(DateUtil.getDateTime());
				rs = tPipSaleProdDao.update(obj);
				String saleProdCode = obj.getSaleProdCode();
				tPipSaleProdAtomProdDao.delete(saleProdCode);
				List<TPipSaleProdAtomProdDO> list = JSON.parseArray(atomProdJson, TPipSaleProdAtomProdDO.class);
				for (TPipSaleProdAtomProdDO item : list) {
					item.setSaleProdCode(saleProdCode);
					tPipSaleProdAtomProdDao.insert(item);
				} 
			}
			session.endTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				session.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_MESSAGE, e.getMessage());
		}
		
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
		rs = tPipSaleProdDao.delete(id);
		return rs>0? true:false;
	}
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param obj
	 * @return
	 */
	public boolean delete(TPipSaleProdDO obj){
		int rs = 0;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "删除数据对象不能空！");
		}
//		if (DataUtil.isNullStr(obj.getId())) {
//			throw new BaseException(SysErr.E_IN_NULL, "id");
//		}
		if(tPipSaleProdDao.getAdapterCount(obj) >0 ) {
			throw new BaseException(SysErr.E_DEFAULT, "该可售产品已关联可售产品包装，不能删除");
		}
		rs = tPipSaleProdDao.delete(obj);
		return rs>0? true:false;
	}
	
	/**
	 * 数据库多笔查询
	 * @param obj 数据对象DO
	 * @return List返回集合
	 */
	public List<TPipSaleProdDO> list(TPipSaleProdDO obj) {
		return tPipSaleProdDao.list(obj);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param obj 数据对象DO
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @return List返回集合
	 */
	public List<TPipSaleProdDO> list(TPipSaleProdDO obj, int start, int limit) {
		return tPipSaleProdDao.list(obj, start, limit);
	}

	public List<TPipSaleProdDOForMsmall> listForMsmall(TPipSaleProdDOForMsmall obj, int start, int limit) {
		return tPipSaleProdDao.listForMsmall(obj, start, limit);
	}

	public List<TPipSaleProdAtomProdDO> listCompSvcForMsmall(TPipSaleProdAtomProdDO obj, int start, int limit) {
		return tPipSaleProdAtomProdDao.listCompSvcForMsmall(obj, start, limit);
	}

	/**
	 * 数据库多笔查询，支持分页
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @param param 查询参数
	 * @return List返回集合
	 */
	public List<TPipSaleProdDO> list(int start, int limit, Object... param) {
		return tPipSaleProdDao.list(start, limit, param);
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(TPipSaleProdDO obj) {
		return tPipSaleProdDao.getTotal(obj);
	}

	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotalForMsmall(TPipSaleProdDOForMsmall obj) {
		return tPipSaleProdDao.getTotalForMsmall(obj);
	}

	/**
	 * 根据可售产品编号获取原子产品关联信息
	 * @param saleProdCode
	 * @return
	 */
	public List<TPipSaleProdAtomProdDO> getSaleAtomList(String saleProdCode) {
		// TODO Auto-generated method stub
		return tPipSaleProdDao.getSaleAtomList(saleProdCode);
	}

	/**
	 * 根据可售产品编号获取原子产品关联信息
	 * @param tPipSaleProdAtomProdDO
	 * @return
	 */
	public List<TPipSaleProdAtomProdDO> getSaleAtomList(TPipSaleProdAtomProdDO tPipSaleProdAtomProdDO, int start, int limit) {
		return tPipSaleProdDao.getSaleAtomList(tPipSaleProdAtomProdDO, start, limit);
	}

	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotalAtom(TPipSaleProdAtomProdDO obj) {
		return tPipSaleProdDao.getTotalAtom(obj);
	}

}