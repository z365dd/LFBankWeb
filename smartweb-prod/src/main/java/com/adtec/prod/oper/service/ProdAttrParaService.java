package com.adtec.prod.oper.service;

import java.util.List;

import com.adtec.prod.oper.dao.ProdAttrParaDao;
import com.adtec.prod.oper.entity.ProdAttrParaDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adtec.framework.interfaces.db.session.IDBSession;

@Service
@Transactional(readOnly = true)
public class ProdAttrParaService {
	private final static Logger log = LoggerFactory.getLogger(ProdAttrParaService.class);
	
	 @Autowired
	 private ProdAttrParaDao prodAttrParaDao;

	 	/**
	     * 数据库多笔 不分页查询
	     *
	     * @param obj 数据对象DO
	     * @return List返回集合
	     */
		 public List<ProdAttrParaDO> list(ProdAttrParaDO obj) {
	    	 return prodAttrParaDao.list(obj,0,0);
	    }
	 
	    /**
	     * 数据库多笔查询
	     *
	     * @param obj 数据对象DO
	     * @return List返回集合
	     */
	    public List<ProdAttrParaDO> list(ProdAttrParaDO obj,int start,int limit) {
	        return prodAttrParaDao.list(obj,start,limit);
	    }
	    
	    
	    /**
	     * 总记录数查询
	     *
	     * @param obj 数据对象DO
	     * @return List返回集合
	     */
	    public int getTotal(ProdAttrParaDO obj) {
	        return prodAttrParaDao.getTotal(obj);
	    }

	    /**
	     * 删除
	     * @param prodAttrParaDO
	     */
		public int delete(ProdAttrParaDO prodAttrParaDO,IDBSession session) {
			int rs =prodAttrParaDao.delete(prodAttrParaDO,session);
			log.debug("删除"+rs+"条组件属性");
			return	rs;
		}

	    /**
	     * 删除
	     * @param prodAttrParaDO
	     */
		public int delete(ProdAttrParaDO prodAttrParaDO) {
			int rs =prodAttrParaDao.delete(prodAttrParaDO);
			log.debug("删除"+rs+"条组件属性");
			return	rs;
		}
		
		/**
		 * 新增
		 * @param dO
		 */
		public void add(ProdAttrParaDO dO) {
			prodAttrParaDao.insert(dO);
		}

		/**
		 * 新增
		 * @param dO
		 */
		public void add(ProdAttrParaDO dO,IDBSession session) {
			prodAttrParaDao.insert(dO,session);
		}
		
		/**
		 * 获取表中最大的SER
		 * @param dO
		 */
		public String getSER() {
			List<ProdAttrParaDO> list = prodAttrParaDao.getListSerDesc("SER","DESC");
			if(list!=null&&list.size()>0){
				return list.get(0).getSER();
			}else{
				return "";
			}
		}
		
		/**
		 * 获取表中KEY_TYPE为03 的最大KEY_NO
		 * @param dO
		 */
		public String get03TP_KEY_NO() {
			String term  = "and KEY_TP = '03' ";
			List<ProdAttrParaDO> list = prodAttrParaDao.getListSerDesc("KEY_NO","DESC",term);
			if(list!=null&&list.size()>0){
				return list.get(0).getKEY_NO();
			}else{
				return "";
			}
		}
}
