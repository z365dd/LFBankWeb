/**
* 系统名称: SmartWeb平台
* 模块名称: comp.ctrl.oper服务模块
* 功能描述: 渠道管理服务提供类
* 类 名 称  : CtrlTParaChnlService.java
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
import com.adtec.comp.ctrl.oper.entity.CtrlTParaChnlDO;
import com.adtec.comp.ctrl.oper.dao.CtrlTParaChnlDao;

/**
 * CtrlTParaChnlService
 * @author zhengjt
 * @version 20200310
 */
@Service
@Transactional(readOnly = true)
public class CtrlTParaChnlService {
	private final static Logger log = LoggerFactory.getLogger(CtrlTParaChnlService.class);
	
	@Autowired
	private CtrlTParaChnlDao ctrlTParaChnlDao;
	
	/**
	 * 获取单条数据
	 * @param chnlNo
	 * @return
	 */
	public CtrlTParaChnlDO get(
		String chnlNo
	) {
		return ctrlTParaChnlDao.get(
		chnlNo
);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	public CtrlTParaChnlDO get(CtrlTParaChnlDO obj) {
		return ctrlTParaChnlDao.get(obj);
	}
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	public boolean insert(CtrlTParaChnlDO obj){
		int rs = 0;
		obj.preInsert();
		rs = ctrlTParaChnlDao.insert(obj);
		return rs>0? true:false;
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	public boolean update(CtrlTParaChnlDO obj){
		int rs = 0;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "更新数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getChnlNo())) {
			throw new BaseException(SysErr.E_IN_NULL, "chnlNo");
		}
		if (DataUtil.isNullStr(obj.getChnlTp())) {
			throw new BaseException(SysErr.E_IN_NULL, "chnlTp");
		}
		// 获取数据库保存的数据
		CtrlTParaChnlDO chnlDO=new CtrlTParaChnlDO();
		chnlDO.setChnlNo(obj.getChnlNo());
		chnlDO.setChnlTp(obj.getChnlTp());
		CtrlTParaChnlDO qryDO = ctrlTParaChnlDao.get(
				chnlDO
		);
		// 更新产品数据
		obj.setCrtr(qryDO.getCrtr());
		obj.setCrtTime(qryDO.getCrtTime());
		obj.preUpdate();		
		rs = ctrlTParaChnlDao.update(obj);
		return rs>0? true:false;
	}
	
	/**
	 * 根据主键删除数据
	 * @param chnlNo
	 * @see public int delete(T entity)
	 * @return
	 */
	public boolean delete(
		String chnlNo
	){
		boolean rs = false;
		if (DataUtil.isNullStr(chnlNo)) {
			throw new BaseException(SysErr.E_IN_NULL, "chnlNo");
		}		

		try{
			ctrlTParaChnlDao.delete(
				chnlNo
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
	public boolean delete(CtrlTParaChnlDO obj){
		boolean rs = false;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "删除数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getChnlNo())) {
			throw new BaseException(SysErr.E_IN_NULL, "chnlNo");
		}		
		
		try{
			ctrlTParaChnlDao.delete(obj);
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
	public List<CtrlTParaChnlDO> list(CtrlTParaChnlDO obj) {
		return ctrlTParaChnlDao.list(obj);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param obj 数据对象DO
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @return List返回集合
	 */
	public List<CtrlTParaChnlDO> list(CtrlTParaChnlDO obj, int start, int limit) {
		return ctrlTParaChnlDao.list(obj, start, limit);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @param param 查询参数
	 * @return List返回集合
	 */
	public List<CtrlTParaChnlDO> list(int start, int limit, Object... param) {
		return ctrlTParaChnlDao.list(start, limit, param);
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(CtrlTParaChnlDO obj) {
		return ctrlTParaChnlDao.getTotal(obj);
	}
}