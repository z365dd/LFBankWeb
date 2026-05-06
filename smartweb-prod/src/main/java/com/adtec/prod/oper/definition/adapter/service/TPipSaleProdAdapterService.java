/**
* 系统名称: SmartWeb平台
* 模块名称: comp-prod/oper/definition/adapter服务模块
* 功能描述: 可售产品包装服务提供类
* 类 名 称  : TPipSaleProdAdapterService.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200106<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.prod.oper.definition.adapter.service;

import com.adtec.prod.oper.definition.adapter.dao.TPipSaleProdAdapterDao;
import com.adtec.prod.oper.definition.adapter.dto.SaleProdAdapterDTO;
import com.adtec.prod.oper.definition.adapter.entity.TPipSaleProdAdapterCtrlDO;
import com.adtec.prod.oper.definition.adapter.entity.TPipSaleProdAdapterDO;
import com.adtec.prod.oper.definition.saleprod.dao.TPipSaleProdDao;
import com.adtec.prod.oper.definition.saleprod.entity.TPipSaleProdDO;
import com.adtec.prod.util.ProdStrEnum;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.sys.modules.sys.utils.DictUtils;
import com.alibaba.fastjson.JSON;
import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

/**
 * TPipSaleProdAdapterService
 * @author zengxj
 * @version 20200106
 */
@Service
@Transactional(readOnly = true)
public class TPipSaleProdAdapterService {
	private final static Logger log = LoggerFactory.getLogger(TPipSaleProdAdapterService.class);
	
	@Autowired
	private TPipSaleProdAdapterDao tPipSaleProdAdapterDao;
	@Autowired
	private TPipSaleProdDao tPipSaleProdDao;
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public TPipSaleProdAdapterDO get(String id) {
		return tPipSaleProdAdapterDao.get(id);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	public TPipSaleProdAdapterDO get(TPipSaleProdAdapterDO obj) {
		return tPipSaleProdAdapterDao.get(obj);
	}
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	public boolean insert(TPipSaleProdAdapterDO obj, String keyJson){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			session.beginTransaction();
			tPipSaleProdAdapterDao.delete(obj.getSaleProdCode());
			List<TPipSaleProdAdapterDO> list = JSON.parseArray(keyJson, TPipSaleProdAdapterDO.class);
			for (TPipSaleProdAdapterDO adapterDO : list) {
				adapterDO.setSaleProdCode(obj.getSaleProdCode());
				checkProdAttr(adapterDO);
				rs = tPipSaleProdAdapterDao.insert(adapterDO);
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
			String msg = e.getMessage();
			// 10000:错误信息[产品属性[响应码转换方式]数据类型信息有误，请确认后重试]
			if (msg.indexOf("[") != -1) {
				msg = msg.substring(msg.indexOf("[")+1, msg.lastIndexOf("]"));
			}
			throw new BaseException(SysErr.E_MESSAGE, msg);
		} 
		return rs>0? true:false;
	}
	
	/**
	 * 更新数据
	 */
	public boolean update(String saleProdCode, String keyJson){
		int rs = 0;
		// 获取数据库保存的数据
		IDBSession session = DBSessionFactory.getSession();
		try {
			session.beginTransaction();
			if (tPipSaleProdAdapterDao.getBusiCount(saleProdCode)) {
				throw new BaseException(SysErr.E_DEFAULT, "该可售产品已使用，不能修改");
			} else {
				tPipSaleProdAdapterDao.delete(saleProdCode);
				List<TPipSaleProdAdapterDO> list = JSON.parseArray(keyJson, TPipSaleProdAdapterDO.class);
				for (TPipSaleProdAdapterDO adapterDO : list) {
					adapterDO.setSaleProdCode(saleProdCode);
					checkProdAttr(adapterDO);
					rs = tPipSaleProdAdapterDao.insert(adapterDO);
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
			String msg = e.getMessage();
			if (msg.indexOf("[") != -1) {
				msg = msg.substring(msg.indexOf("[")+1, msg.lastIndexOf("]"));
			}
			throw new BaseException(SysErr.E_MESSAGE, msg);
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
		rs = tPipSaleProdAdapterDao.delete(id);
		return rs>0? true:false;
	}
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param obj
	 * @return
	 */
	public boolean delete(TPipSaleProdAdapterDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			session.beginTransaction();
			if (tPipSaleProdAdapterDao.getBusiCount(obj.getSaleProdCode())) {
				throw new BaseException(SysErr.E_DEFAULT, "该可售产品已使用，不能删除");
			} else {
				rs = tPipSaleProdAdapterDao.delete(obj);
//				tPipSaleProdAdapterCtrlDao.delete(obj.getSaleProdCode());
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
	 * 数据库多笔查询
	 * @param obj 数据对象DO
	 * @return List返回集合
	 */
	public List<TPipSaleProdAdapterDO> list(TPipSaleProdAdapterDO obj) {
		return tPipSaleProdAdapterDao.list(obj);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param obj 数据对象DO
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @return List返回集合
	 */
	public List<TPipSaleProdDO> list(TPipSaleProdDO obj, int start, int limit) {
		return tPipSaleProdAdapterDao.saleProdList(obj, start, limit);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @param param 查询参数
	 * @return List返回集合
	 */
	public List<TPipSaleProdAdapterDO> list(int start, int limit, Object... param) {
		return tPipSaleProdAdapterDao.list(start, limit, param);
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(TPipSaleProdDO obj) {
		return tPipSaleProdAdapterDao.getTotal(obj);
	}
	
	/**
	 * 根据可售产品编号获取对应原子产品服务属性列表
	 * @param saleProdCode
	 * @return
	 */
	public List<TPipSaleProdAdapterDO> getKeyList(String saleProdCode, String detailFlag) {
		List<TPipSaleProdAdapterDO> adapterList = Lists.newArrayList();
//		List<TPipSaleProdAtomProdDO> atomList = tPipSaleProdDao.getSaleAtomList(saleProdCode);
//		String atomProdCode="";
//		if(adapterList!=null&&adapterList.size()>0){
//			for(int i=0;i<adapterList.size();i++){
//				atomProdCode+=adapterList.get(i).getAtomProdCode()+",";
//			}
		List<TPipSaleProdAdapterDO> list = tPipSaleProdAdapterDao.getKeyList(saleProdCode, detailFlag);
		adapterList.addAll(list);
//		}
		
//		for (TPipSaleProdAtomProdDO item : atomList) {
//			List<TPipSaleProdAdapterDO> list = tPipSaleProdAdapterDao.getKeyList(item, detailFlag);
//			adapterList.removeAll(list);
//			adapterList.addAll(list);
//		}
		return adapterList;
	}
	
	/**
	 * 根据产品属性编号获取包装字段列表
	 * @param keyNo
	 * @return
	 */
	public List<SaleProdAdapterDTO> getKeyCtrlList(String keyNo) {
		List<SaleProdAdapterDTO> list = Lists.newArrayList();
		List<TPipSaleProdAdapterCtrlDO> keyCtrlList = tPipSaleProdAdapterDao.getKeyCtrlList(keyNo);
		for (TPipSaleProdAdapterCtrlDO ctrlDO : keyCtrlList) {
			String elemKv = ctrlDO.getElemKv();
			String[] kv = elemKv.split(";@;");
			for (String item : kv) {
				String[] tmp = item.split("!@#");
				SaleProdAdapterDTO dto = new SaleProdAdapterDTO();
				dto.setKeyType(tmp[0]);
				dto.setKeyValue(tmp[1]);
				list.add(dto);
			}
		}
		return list;
	}
	
	/**
	 * 检查产品属性各项信息
	 * @param adapterDO
	 */
	private void checkProdAttr(TPipSaleProdAdapterDO adapterDO) {
		boolean chk = false;
		String message = "产品属性["+adapterDO.getKeyNo()+adapterDO.getKeyName()+"]";
		if (DataUtil.isNullStr(adapterDO.getKeyNo())) { // KeyNo 
			chk = true;
			message += "属性,";
		}
		if (DataUtil.isNullStr(adapterDO.getValLen())) {
			chk = true;
			message += "最大长度,";
		}
		
		if ("".equals(DictUtils.getDictLabel(adapterDO.getValTp(), "VAL_TP", ""))){ // ValTp
			chk = true;
			message += "数据类型,";
		}
		
		if ("".equals(DictUtils.getDictLabel(adapterDO.getEnterTp(), "ENTER_TP", ""))) { // EnterTp
			chk = true;
			message += "输入方式,";
		}
		
		message = message.substring(0, message.length()-1) + "信息有误，请确认后重试";
		if (chk) {
			throw new BaseException(SysErr.E_MESSAGE, message);
		}
	}
}