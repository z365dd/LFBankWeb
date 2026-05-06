/**
* 系统名称: SmartWeb平台
* 模块名称: comp.ctrl.oper服务模块
* 功能描述: 业务渠道开通服务提供类
* 类 名 称  : CtrlTPipBusiChnlOpenService.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200324<br>
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

import com.adtec.comp.ctrl.oper.dao.CtrlTParaChnlDao;
import com.adtec.comp.ctrl.oper.dao.CtrlTPipBusiChnlOpenDao;
import com.adtec.comp.ctrl.oper.dao.CtrlTPipBusiDao;
import com.adtec.comp.ctrl.oper.entity.CtrlTParaChnlDO;
import com.adtec.comp.ctrl.oper.entity.CtrlTPipBusiChnlOpenDO;
import com.adtec.comp.ctrl.oper.entity.CtrlTPipBusiDO;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.SysErr;

/**
 * CtrlTPipBusiChnlOpenService
 * 
 * @author zhengjt
 * @version 20200324
 */
@Service
@Transactional(readOnly = true)
public class CtrlTPipBusiChnlOpenService {
	private final static Logger log = LoggerFactory.getLogger(CtrlTPipBusiChnlOpenService.class);

	@Autowired
	private CtrlTPipBusiChnlOpenDao ctrlTPipBusiChnlOpenDao;

	@Autowired
	private CtrlTParaChnlDao ctrlTParaChnlDao;

	@Autowired
	private CtrlTPipBusiDao ctrlTPipBusiDao;

	/**
	 * 获取单条数据
	 * 
	 * @param busiNo
	 * @param chnlNo
	 * @return
	 */
	public CtrlTPipBusiChnlOpenDO get(String busiNo, String chnlNo) {
		return ctrlTPipBusiChnlOpenDao.get(busiNo, chnlNo);
	}

	/**
	 * 获取单条数据
	 * 
	 * @param obj
	 * @return
	 */
	public CtrlTPipBusiChnlOpenDO get(CtrlTPipBusiChnlOpenDO obj) {
		return ctrlTPipBusiChnlOpenDao.get(obj);
	}

	/**
	 * 插入数据
	 * 
	 * @param obj
	 * @return
	 */
	public boolean insert(CtrlTPipBusiChnlOpenDO obj) {
		int rs = 0;
		obj.preInsert();
		rs = ctrlTPipBusiChnlOpenDao.insert(obj);
		return rs > 0 ? true : false;
	}

	/**
	 * FLG修改
	 * 
	 * @param obj
	 * @return
	 */
	public boolean update(CtrlTPipBusiChnlOpenDO obj) {
		int rs = 0;
		if (null == obj) {
			throw new BaseException(SysErr.E_DEFAULT, "更新数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getBusiNo())) {
			throw new BaseException(SysErr.E_IN_NULL, "busiNo");
		}
		if (DataUtil.isNullStr(obj.getChnlNo())) {
			throw new BaseException(SysErr.E_IN_NULL, "chnlNo");
		}
		if (DataUtil.isNullStr(obj.getFlg())) {
			throw new BaseException(SysErr.E_IN_NULL, "flg");
		}
		// 获取数据库保存的数据
		CtrlTPipBusiChnlOpenDO qryDO = ctrlTPipBusiChnlOpenDao.get(obj.getBusiNo(), obj.getChnlNo());
		// 更新产品数据
		obj.setCrtr(qryDO.getCrtr());
		obj.setCrtTime(qryDO.getCrtTime());
		obj.preUpdate();
		rs = ctrlTPipBusiChnlOpenDao.update(obj);
		return rs > 0 ? true : false;
	}
	
	/**
	 * 更新数据
	 * 
	 * @param obj
	 * @return
	 */
	public boolean update(CtrlTPipBusiChnlOpenDO obj, String chnlNo) {
		int rs = 0;
		if (null == obj) {
			throw new BaseException(SysErr.E_DEFAULT, "更新数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getBusiNo())) {
			throw new BaseException(SysErr.E_IN_NULL, "busiNo");
		}
		if (DataUtil.isNullStr(obj.getChnlNo())) {
			throw new BaseException(SysErr.E_IN_NULL, "chnlNo");
		}
		if (DataUtil.isNullStr(obj.getFlg())) {
			throw new BaseException(SysErr.E_IN_NULL, "flg");
		}
		// 获取数据库保存的数据
		CtrlTPipBusiChnlOpenDO qryDO = ctrlTPipBusiChnlOpenDao.get(obj.getBusiNo(), chnlNo);
		// 更新产品数据
		obj.setCrtr(qryDO.getCrtr());
		obj.setCrtTime(qryDO.getCrtTime());
		obj.preUpdate();
		rs = ctrlTPipBusiChnlOpenDao.update(obj,chnlNo);
		return rs > 0 ? true : false;
	}

	/**
	 * 根据主键删除数据
	 * 
	 * @param busiNo
	 * @param chnlNo
	 * @see public int delete(T entity)
	 * @return
	 */
	public boolean delete(String busiNo, String chnlNo) {
		boolean rs = false;
		if (DataUtil.isNullStr(busiNo)) {
			throw new BaseException(SysErr.E_IN_NULL, "busiNo");
		}
		if (DataUtil.isNullStr(chnlNo)) {
			throw new BaseException(SysErr.E_IN_NULL, "chnlNo");
		}

		try {
			ctrlTPipBusiChnlOpenDao.delete(busiNo, chnlNo);
			rs = true;
		} catch (Exception e) {
			log.error("删除交易失败", e);
		}

		return rs;
	}

	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * 
	 * @param obj
	 * @return
	 */
	public boolean delete(CtrlTPipBusiChnlOpenDO obj) {
		boolean rs = false;
		if (null == obj) {
			throw new BaseException(SysErr.E_DEFAULT, "删除数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getBusiNo())) {
			throw new BaseException(SysErr.E_IN_NULL, "busiNo");
		}
		if (DataUtil.isNullStr(obj.getChnlNo())) {
			throw new BaseException(SysErr.E_IN_NULL, "chnlNo");
		}

		try {
			ctrlTPipBusiChnlOpenDao.delete(obj);
			rs = true;
		} catch (Exception e) {
			log.error("删除交易失败", e);
		}

		return rs;
	}

	/**
	 * 数据库多笔查询
	 * 
	 * @param obj
	 *            数据对象DO
	 * @return List返回集合
	 */
	public List<CtrlTPipBusiChnlOpenDO> list(CtrlTPipBusiChnlOpenDO obj) {
		return ctrlTPipBusiChnlOpenDao.list(obj);
	}

	/**
	 * 数据库多笔查询，支持分页
	 * 
	 * @param obj
	 *            数据对象DO
	 * @param start
	 *            起始位置
	 * @param limit
	 *            每页数量
	 * @return List返回集合
	 */
	public List<CtrlTPipBusiChnlOpenDO> list(CtrlTPipBusiChnlOpenDO obj, int start, int limit) {
		return ctrlTPipBusiChnlOpenDao.list(obj, start, limit);
	}

	/**
	 * 数据库多笔查询，支持分页
	 * 
	 * @param start
	 *            起始位置
	 * @param limit
	 *            每页数量
	 * @param param
	 *            查询参数
	 * @return List返回集合
	 */
	public List<CtrlTPipBusiChnlOpenDO> list(int start, int limit, Object... param) {
		return ctrlTPipBusiChnlOpenDao.list(start, limit, param);
	}

	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * 
	 * @param obj
	 *            数据对象DO
	 * @return total
	 */
	public int getTotal(CtrlTPipBusiChnlOpenDO obj) {
		return ctrlTPipBusiChnlOpenDao.getTotal(obj);
	}

	/**
	 * 渠道表多笔查询
	 * 
	 * @param obj
	 *            数据对象DO
	 * @return List返回集合
	 */
	public List<CtrlTParaChnlDO> getChnlNo(CtrlTParaChnlDO obj) {
		return ctrlTParaChnlDao.list(obj);
	}

	/**
	 * 业务表多笔查询
	 * 
	 * @param obj
	 *            数据对象DO
	 * @return List返回集合
	 */
	public List<CtrlTPipBusiDO> getBusiNo(CtrlTPipBusiDO obj) {
		return ctrlTPipBusiDao.list(obj);
	}
	/**
	 * 业务表单笔查询
	 * 
	 * @param busiNo
	 *           
	 * @return 返回 数据对象DO
	 */
	public CtrlTPipBusiDO getBusiName(String busiNo) {
		return ctrlTPipBusiDao.get(busiNo);
	}
	/**
	 * 业务表单笔查询
	 * 
	 * @param obj
	 *            数据对象DO
	 * @return List返回集合
	 */
	public CtrlTParaChnlDO getChnlName(String chnlNo) {
		return ctrlTParaChnlDao.get(chnlNo);
	}
}