/**
* 系统名称: SmartWeb平台
* 模块名称: comp-prod/oper/line服务模块
* 功能描述: 产品线管理服务提供类
* 类 名 称  : TPipLineProdService.java
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
package com.adtec.prod.oper.line.service;

import java.util.List;

import com.adtec.prod.oper.line.dao.TPipLineProdDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.SysErr;
import com.adtec.prod.oper.line.entity.TPipLineProdDO;

/**
 * TPipLineProdService
 * @author zengxj
 * @version 20200102
 */
@Service
@Transactional(readOnly = true)
public class TPipLineProdService {
	private final static Logger log = LoggerFactory.getLogger(TPipLineProdService.class);
	
	@Autowired
	private TPipLineProdDao tPipLineProdDao;
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public TPipLineProdDO get(String prodLineCode) {
		return tPipLineProdDao.get(prodLineCode);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	public TPipLineProdDO get(TPipLineProdDO obj) {
		return tPipLineProdDao.get(obj);
	}
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	public boolean insert(TPipLineProdDO obj){
		int rs = tPipLineProdDao.getLineCntByName(obj);
		if(rs>0){
			throw new BaseException(SysErr.E_DEFAULT, "此产品线已存在");
		}
		// 最大值+1
		String max = tPipLineProdDao.getMaxCode();
		int tmp = Integer.valueOf(max);
		tmp++;
		String code = String.format("%02d", tmp);
		obj.setProdLineCode(code);
		rs = tPipLineProdDao.insert(obj);
		return rs>0? true:false;
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	public boolean update(TPipLineProdDO obj){
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "更新数据对象不能空！");
		}
		int rs = 0;
		rs = tPipLineProdDao.update(obj);
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
		rs = tPipLineProdDao.delete(id);
		return rs>0? true:false;
	}
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param obj
	 * @return
	 */
	public boolean delete(TPipLineProdDO obj){
		int rs = 0;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "删除数据对象不能空！");
		}
		if(tPipLineProdDao.getSaleProdCount(obj) > 0) {
			throw new BaseException(SysErr.E_DEFAULT, "该产品线已关联可售产品，不能删除");
		}
		rs = tPipLineProdDao.delete(obj);
		return rs>0? true:false;
	}
	
	/**
	 * 数据库多笔查询
	 * @param obj 数据对象DO
	 * @return List返回集合
	 */
	public List<TPipLineProdDO> list(TPipLineProdDO obj) {
		return tPipLineProdDao.list(obj);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param obj 数据对象DO
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @return List返回集合
	 */
	public List<TPipLineProdDO> list(TPipLineProdDO obj, int start, int limit) {
		return tPipLineProdDao.list(obj, start, limit);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @param param 查询参数
	 * @return List返回集合
	 */
	public List<TPipLineProdDO> list(int start, int limit, Object... param) {
		return tPipLineProdDao.list(start, limit, param);
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(TPipLineProdDO obj) {
		return tPipLineProdDao.getTotal(obj);
	}
}