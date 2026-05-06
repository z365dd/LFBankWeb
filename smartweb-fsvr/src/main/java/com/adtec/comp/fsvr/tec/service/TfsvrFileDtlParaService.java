/**
* 系统名称: SmartWeb平台
* 模块名称: comp.fsvr.tec服务模块
* 功能描述: 文件格式明细参数配置表服务提供类
* 类 名 称  : TfsvrFileDtlParaService.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200630<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.comp.fsvr.tec.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adtec.comp.fsvr.tec.dao.TfsvrFileDtlParaDao;
import com.adtec.comp.fsvr.tec.entity.TfsvrFileDtlParaDO;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;

/**
 * TfsvrFileDtlParaService
 * 
 * @author zhengjt
 * @version 20200630
 */
@Service
@Transactional(readOnly = true)
public class TfsvrFileDtlParaService {
	private final static Logger log = LoggerFactory.getLogger(TfsvrFileDtlParaService.class);

	@Autowired
	private TfsvrFileDtlParaDao tfsvrFileDtlParaDao;

	/**
	 * 获取单条数据
	 * 
	 * @param fmtNo
	 * @param flg
	 * @param ser
	 * @return
	 */
	public TfsvrFileDtlParaDO get(String fmtNo, String flg, Long ser) {
		return tfsvrFileDtlParaDao.get(fmtNo, flg, ser);
	}

	/**
	 * 获取单条数据
	 * 
	 * @param obj
	 * @return
	 */
	public TfsvrFileDtlParaDO get(TfsvrFileDtlParaDO obj) {
		return tfsvrFileDtlParaDao.get(obj);
	}

	/**
	 * 插入数据
	 * 
	 * @param obj
	 * @return
	 */
	public boolean insert(TfsvrFileDtlParaDO obj) {
		int rs = 0;
		obj.preInsert();
		rs = tfsvrFileDtlParaDao.insert(obj);
		return rs > 0 ? true : false;
	}

	/**
	 * 更新数据
	 * 
	 * @param obj
	 * @return
	 */
	public boolean update(TfsvrFileDtlParaDO obj) {
		int rs = 0;
		if (null == obj) {
			throw new BaseException(SysErr.E_DEFAULT, "更新数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getFmtNo())) {
			throw new BaseException(SysErr.E_IN_NULL, "fmtNo");
		}
		if (DataUtil.isNullStr(obj.getFileFlg())) {
			throw new BaseException(SysErr.E_IN_NULL, "flg");
		}
		if (null == obj.getSer()) {
			throw new BaseException(SysErr.E_IN_NULL, "ser");
		}

		// 获取数据库保存的数据
		TfsvrFileDtlParaDO qryDO = tfsvrFileDtlParaDao.get(obj.getFmtNo(), obj.getFileFlg(), obj.getSer());
		// 更新产品数据
		obj.setCrtr(qryDO.getCrtr());
		obj.setCrtTime(qryDO.getCrtTime());
		obj.preUpdate();
		rs = tfsvrFileDtlParaDao.update(obj);
		return rs > 0 ? true : false;
	}

	/**
	 * 根据主键删除数据
	 * 
	 * @param fmtNo
	 * @param flg
	 * @param ser
	 * @see public int delete(T entity)
	 * @return
	 */
	public boolean delete(String fmtNo) {
		boolean rs = false;
		if (DataUtil.isNullStr(fmtNo)) {
			throw new BaseException(SysErr.E_IN_NULL, "fmtNo");
		}
		try {
			tfsvrFileDtlParaDao.delete(fmtNo);
			rs = true;
		} catch (Exception e) {
			log.error("删除交易失败", e);
		}

		return rs;
	}

	/**
	 * 根据主键删除数据
	 * 
	 * @param fmtNo
	 * @param flg
	 * @param ser
	 * @see public int delete(T entity)
	 * @return
	 */
	public boolean delete(String fmtNo, String flg, Long ser) {
		boolean rs = false;
		if (DataUtil.isNullStr(fmtNo)) {
			throw new BaseException(SysErr.E_IN_NULL, "fmtNo");
		}
		if (DataUtil.isNullStr(flg)) {
			throw new BaseException(SysErr.E_IN_NULL, "flg");
		}
		if (null == ser) {
			throw new BaseException(SysErr.E_IN_NULL, "ser");
		}

		try {
			tfsvrFileDtlParaDao.delete(fmtNo, flg, ser);
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
	public boolean delete(TfsvrFileDtlParaDO obj) {
		boolean rs = false;
		if (null == obj) {
			throw new BaseException(SysErr.E_DEFAULT, "删除数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getFmtNo())) {
			throw new BaseException(SysErr.E_IN_NULL, "fmtNo");
		}
		if (DataUtil.isNullStr(obj.getFileFlg())) {
			throw new BaseException(SysErr.E_IN_NULL, "flg");
		}
		if (null == obj.getSer()) {
			throw new BaseException(SysErr.E_IN_NULL, "ser");
		}

		try {
			tfsvrFileDtlParaDao.delete(obj);
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
	public List<TfsvrFileDtlParaDO> list(TfsvrFileDtlParaDO obj) {
		return tfsvrFileDtlParaDao.list(obj);
	}

	/**
	 * 数据库多笔查询
	 * 
	 * @param obj
	 *            数据对象DO
	 * @return List返回集合
	 */
	public List<TfsvrFileDtlParaDO> list(String fmtNo) {
		TfsvrFileDtlParaDO obj = new TfsvrFileDtlParaDO();
		obj.setFmtNo(fmtNo);
		return tfsvrFileDtlParaDao.getHeadList(obj, 0, 0);
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
	public List<TfsvrFileDtlParaDO> list(TfsvrFileDtlParaDO obj, int start, int limit) {
		return tfsvrFileDtlParaDao.list(obj, start, limit);
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
	public List<TfsvrFileDtlParaDO> list(int start, int limit, Object... param) {
		return tfsvrFileDtlParaDao.list(start, limit, param);
	}

	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * 
	 * @param obj
	 *            数据对象DO
	 * @return total
	 */
	public int getTotal(TfsvrFileDtlParaDO obj) {
		return tfsvrFileDtlParaDao.getTotal(obj);
	}
}