/**
* 系统名称: SmartWeb平台
* 模块名称: sys-modules.sys服务模块
* 功能描述: 用户租户服务提供类
* 类 名 称  : UserTntService.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20210714<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.sys.modules.sys.service;

import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.modules.sys.entity.UserTntDO;
import com.adtec.sys.modules.sys.dao.UserTntDao;

/**
 * UserTntService
 * @author zx
 * @version 20210714
 */
@Service
@Transactional(readOnly = true)
public class UserTntService {
	private final static Logger log = LoggerFactory.getLogger(UserTntService.class);
	
	@Autowired
	private UserTntDao userTntDao;
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public UserTntDO get(String id) {
		return userTntDao.get(id);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	public UserTntDO get(UserTntDO obj) {
		return userTntDao.get(obj);
	}
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	public void insert(UserTntDO obj){
		obj.preInsert();
		userTntDao.insert(obj);
	}
	
	public void saveUserTnt(UserTntDO obj){
		String tntId = obj.getTntId();
		if(!DataUtil.isNullStr(tntId)){
			IDBSession session = DBSessionFactory.getSession();
			try {
				session.beginTransaction();
				String userId = obj.getUserId();
				userTntDao.delByUserId(userId);
				String[] tntArr = tntId.split(",");
				for (String tnt : tntArr) {
					UserTntDO ut = new UserTntDO();
					ut.preInsert();
					ut.setUserId(userId);
					ut.setTntId(tnt);
					userTntDao.insert(ut);
				}
				session.endTransaction();
			} catch (SQLException e) {
				log.error("授权交易异常", e);
				try {
	                session.rollback();
	            } catch (SQLException e1) {
	                e1.printStackTrace();
	            }
	            throw new BaseException(SysErr.E_MESSAGE, "授权交易失败！");
			}

		}
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	public void update(UserTntDO obj){
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "更新数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getId())) {
			throw new BaseException(SysErr.E_IN_NULL, "id");
		}		

		// 获取数据库保存的数据
		UserTntDO qryDO = userTntDao.get(obj.getId());		
		// 更新产品数据
		obj.setCrtr(qryDO.getCrtr());
		obj.setCrtTime(qryDO.getCrtTime());
		obj.preUpdate();		
		userTntDao.update(obj);		
	}
	
	/**
	 * 根据主键删除数据
	 * @param id
	 * @see public int delete(T entity)
	 * @return
	 */
	public void delete(String id){
		if (DataUtil.isNullStr(id)) {
			throw new BaseException(SysErr.E_IN_NULL, "id");
		}		
		
		try{
			userTntDao.delete(id);
		}catch(Exception e){
			log.error("删除交易失败", e);
			throw new BaseException(SysErr.E_MESSAGE, "删除交易失败！"+e.getMessage());
		}		
	}
	
	public void delByUserId(String userId){
		UserTntDO obj =  new UserTntDO();
		obj.setUserId(userId);
		userTntDao.delete(obj);
	}
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flg字段为1）
	 * @param obj
	 * @return
	 */
	public void delete(UserTntDO obj){
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "删除数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getId())) {
			throw new BaseException(SysErr.E_IN_NULL, "id");
		}		
		
		try{
			userTntDao.delete(obj);
		}catch(Exception e){
			log.error("删除交易失败", e);
			throw new BaseException(SysErr.E_MESSAGE, "删除交易失败！"+e.getMessage());
		}
	}
	
	/**
	 * 数据库多笔查询
	 * @param obj 数据对象DO
	 * @return List返回集合
	 */
	public List<UserTntDO> list(UserTntDO obj) {
		return userTntDao.list(obj);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param obj 数据对象DO
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @return List返回集合
	 */
	public List<UserTntDO> list(UserTntDO obj, int start, int limit) {
		return userTntDao.list(obj, start, limit);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @param param 查询参数
	 * @return List返回集合
	 */
	public List<UserTntDO> list(int start, int limit, Object... param) {
		return userTntDao.list(start, limit, param);
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(UserTntDO obj) {
		return userTntDao.getTotal(obj);
	}
}