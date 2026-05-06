/**
* 系统名称: SmartWeb平台
* 模块名称: comp-prod/oper/definition/atom服务模块
* 功能描述: 原子产品服务提供类
* 类 名 称  : TPipAtomProdService.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200102<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.prod.oper.definition.atom.service;

import java.sql.SQLException;
import java.util.List;

import com.adtec.prod.oper.definition.atom.dao.TPipAtomProdDao;
import com.adtec.prod.oper.definition.atom.dao.TPipAtomProdSvcDao;
import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.prod.oper.definition.atom.entity.TPipAtomProdDO;
import com.adtec.prod.oper.definition.atom.entity.TPipAtomProdSvcDO;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;

/**
 * TPipAtomProdService
 * @author zengxj
 * @version 20200102
 */
@Service
@Transactional(readOnly = true)
public class TPipAtomProdService {
	private final static Logger log = LoggerFactory.getLogger(TPipAtomProdService.class);
	
	@Autowired
	private TPipAtomProdDao tPipAtomProdDao;
	@Autowired
	private TPipAtomProdSvcDao tPipAtomProdSvcDao;
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public TPipAtomProdDO get(String id) {
		TPipAtomProdDO prodDO = tPipAtomProdDao.get(id);
		TPipAtomProdSvcDO tmp = new TPipAtomProdSvcDO();
		tmp.setAtomProdCode(prodDO.getAtomProdCode());
		List<TPipAtomProdSvcDO> list = tPipAtomProdSvcDao.list(tmp);
		String svcCode = "";
		for (TPipAtomProdSvcDO item : list) {
			String code = item.getSvcCode();
			if (!"00".equals(item.getSceneNo())){
				code += "_"+item.getSceneNo();
			}
			svcCode += code+";";
		}
		prodDO.setLongRmrk(svcCode);
		return prodDO;
		
		
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	public TPipAtomProdDO get(TPipAtomProdDO obj) {
		TPipAtomProdDO prodDO = tPipAtomProdDao.get(obj);
		TPipAtomProdSvcDO tmp = new TPipAtomProdSvcDO();
		tmp.setAtomProdCode(prodDO.getAtomProdCode());
		List<TPipAtomProdSvcDO> list = tPipAtomProdSvcDao.list(tmp);
		String svcCode = "";
		for (TPipAtomProdSvcDO item : list) {
			String code = item.getSceneNo();
			if (!DataUtil.isNullStr(item.getSceneNo())){
				code += "_"+item.getSceneNo();
			}
			svcCode += code+";";
		}
		tmp.setLongRmrk(svcCode);
		return prodDO;
	}
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	public boolean insert(TPipAtomProdDO obj, String data){
		int count = tPipAtomProdDao.getAtomCount(obj);
		if (count > 0) {
			throw new BaseException(SysErr.E_MESSAGE, "原子产品["+obj.getAtomProdDesc()+"]已存在");
		}
		String maxCode = tPipAtomProdDao.getMaxAtomProdCode(obj.getCompNo());
		String atomProdCode = "";
		if (!DataUtil.isNullStr(maxCode)) {
			String code = maxCode.replace(obj.getCompNo(), "");
			int x = Integer.valueOf(code);
			x++;
			String tmp = String.valueOf(x);
			String prodCode = String.format("%04d",Integer.valueOf(tmp));
			atomProdCode = obj.getCompNo()+prodCode;
		} else {
			atomProdCode = obj.getCompNo()+"0001";
		}
		obj.setAtomProdCode(atomProdCode);
		
		List<TPipAtomProdSvcDO> svcList = Lists.newArrayList();
		String[] codeList = data.split(";");
		for (String item : codeList) {
			TPipAtomProdSvcDO svcDO = new TPipAtomProdSvcDO();
			svcDO.setAtomProdCode(atomProdCode);
			svcDO.setSceneNo("00");
			String svcCode = item.split("_")[0];
			svcDO.setSvcCode(svcCode);
			if (item.indexOf("_") != -1) {
				String subSvcCode = item.split("_")[1];
				svcDO.setSceneNo(subSvcCode);
			}
			svcList.add(svcDO);
		}
		
		int rs = 0;
//		obj.preInsert();
		IDBSession session = DBSessionFactory.getSession();
		try {
			session.beginTransaction();
			rs = tPipAtomProdDao.insert(obj);
			for (TPipAtomProdSvcDO svcDO : svcList) {
				rs = tPipAtomProdSvcDao.insert(svcDO);
			}
//			rs = tPipAtomProdDao.insertSvc();
			session.endTransaction();
		}  catch (Exception e) {
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
	public boolean update(TPipAtomProdDO obj, String data){
		int rs = 0;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "更新数据对象不能空！");
		}
		List<TPipAtomProdSvcDO> svcList = Lists.newArrayList();
		String[] codeList = data.split(";");
		for (String item : codeList) {
			TPipAtomProdSvcDO svcDO = new TPipAtomProdSvcDO();
			svcDO.setAtomProdCode(obj.getAtomProdCode());
			svcDO.setSceneNo("00");
			String svcCode = item.split("_")[0];
			svcDO.setSvcCode(svcCode);
			if (item.indexOf("_") != -1) {
				String subSvcCode = item.split("_")[1];
				svcDO.setSceneNo(subSvcCode);
			}
			svcList.add(svcDO);
		}
		
		IDBSession session = DBSessionFactory.getSession();
		try {
			session.beginTransaction();
//			rs = tPipAtomProdDao.insert(obj);
			
			if (tPipAtomProdDao.getSaleProdCount(obj.getAtomProdCode()) > 0) {
				throw new BaseException(SysErr.E_MESSAGE, "该原子产品已关联可售产品，不能修改");
			}else {
				rs = tPipAtomProdDao.update(obj);
				tPipAtomProdSvcDao.delete(obj.getAtomProdCode());
				for (TPipAtomProdSvcDO svcDO : svcList) {
					rs = tPipAtomProdSvcDao.insert(svcDO);
				}
			}
			
//			rs = tPipAtomProdDao.insertSvc();
			session.endTransaction();
		}  catch (Exception e) {
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
	public boolean delete(String atomProdCode){
		int rs = 0;
		if (DataUtil.isNullStr(atomProdCode)) {
			throw new BaseException(SysErr.E_IN_NULL, "atomProdCode");
		}
		IDBSession session = DBSessionFactory.getSession();
		try {
			session.beginTransaction();
			if (tPipAtomProdDao.getSaleProdCount(atomProdCode) > 0) {
				throw new BaseException(SysErr.E_MESSAGE, "该原子产品已关联可售产品，不能删除");
			} else {
				rs = tPipAtomProdDao.delete(atomProdCode);
				tPipAtomProdSvcDao.delete(atomProdCode);
			}
			session.endTransaction();
		}  catch (Exception e) {
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
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param obj
	 * @return
	 */
	public boolean delete(TPipAtomProdDO obj){
		int rs = 0;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "删除数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getId())) {
			throw new BaseException(SysErr.E_IN_NULL, "id");
		}
		rs = tPipAtomProdDao.delete(obj);
		return rs>0? true:false;
	}
	
	/**
	 * 数据库多笔查询
	 * @param obj 数据对象DO
	 * @return List返回集合
	 */
	public List<TPipAtomProdDO> list(TPipAtomProdDO obj) {
		return tPipAtomProdDao.list(obj);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param obj 数据对象DO
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @return List返回集合
	 */
	public List<TPipAtomProdDO> list(TPipAtomProdDO obj, int start, int limit) {
		return tPipAtomProdDao.list(obj, start, limit);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @param param 查询参数
	 * @return List返回集合
	 */
	public List<TPipAtomProdDO> list(int start, int limit, Object... param) {
		return tPipAtomProdDao.list(start, limit, param);
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(TPipAtomProdDO obj) {
		return tPipAtomProdDao.getTotal(obj);
	}
}