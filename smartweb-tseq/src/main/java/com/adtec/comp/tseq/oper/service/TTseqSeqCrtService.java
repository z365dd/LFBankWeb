/**
* 系统名称: SmartWeb平台
* 模块名称: comp.tseq.oper服务模块
* 功能描述: 流水号生成器服务提供类
* 类 名 称  : TTseqSeqCrtService.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20200422<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.comp.tseq.oper.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adtec.comp.tseq.oper.dao.TTseqSeqCrtDao;
import com.adtec.comp.tseq.oper.dao.TTseqSeqCrtStatDao;
import com.adtec.comp.tseq.oper.dao.TseqTParaRelatSysDao;
import com.adtec.comp.tseq.oper.dao.TseqTPipCompParaDao;
import com.adtec.comp.tseq.oper.entity.TTseqSeqCrtDO;
import com.adtec.comp.tseq.oper.entity.TseqTParaRelatSysDO;
import com.adtec.comp.tseq.oper.entity.TseqTPipCompParaDO;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;


/**
 * TTseqSeqCrtService
 * 
 * @author zhengjt
 * @version 20200422
 */
@Service
@Transactional(readOnly = true)
public class TTseqSeqCrtService {
	private final static Logger log = LoggerFactory.getLogger(TTseqSeqCrtService.class);

	@Autowired
	private TTseqSeqCrtDao tTseqSeqCrtDao;
	@Autowired
	private TTseqSeqCrtStatDao tTseqSeqCrtStatDao;
	@Autowired
	private TseqTParaRelatSysDao tseqTParaRelatSysDao;
	@Autowired
	private TseqTPipCompParaDao tseqTPipCompParaDao;

	/**
	 * 获取单条数据
	 * 
	 * @param seqCrtId
	 * @return
	 */
	public TTseqSeqCrtDO get(String seqCrtId) {
		return tTseqSeqCrtDao.get(seqCrtId);
	}

	/**
	 * 获取单条数据
	 * 
	 * @param obj
	 * @return
	 */
	public TseqTPipCompParaDO get(TseqTPipCompParaDO obj) {
		return tseqTPipCompParaDao.get(obj);
	}
	
	/**
	 * 获取单条数据
	 * 
	 * @param obj
	 * @return
	 */
	public TTseqSeqCrtDO get(TTseqSeqCrtDO obj) {
		return tTseqSeqCrtDao.get(obj);
	}
	/**
	 * 插入数据
	 * 
	 * @param obj
	 * @return
	 */
	public boolean insert(TTseqSeqCrtDO obj) {
		int rs = 0;
		obj.preInsert();
		rs = tTseqSeqCrtDao.insert(obj);
		return rs > 0 ? true : false;
	}

	/**
	 * 更新数据
	 * 
	 * @param obj
	 * @return
	 */
	public boolean update(TTseqSeqCrtDO obj) {
		int rs = 0;
		if (null == obj) {
			throw new BaseException(SysErr.E_DEFAULT, "更新数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getSeqCrtId())) {
			throw new BaseException(SysErr.E_IN_NULL, "seqCrtId");
		}

		// 获取数据库保存的数据
		TTseqSeqCrtDO qryDO = tTseqSeqCrtDao.get(obj.getSeqCrtId());
		// 更新产品数据
		obj.setCrtr(qryDO.getCrtr());
		obj.setCrtTime(qryDO.getCrtTime());
		obj.preUpdate();
		rs = tTseqSeqCrtDao.update(obj);
		return rs > 0 ? true : false;
	}

	/**
	 * 更新数据
	 * 
	 * @param obj
	 * @return
	 */
	public boolean updateStat(String seqCrtId) {
		int rs = 0;
	
		rs = tTseqSeqCrtStatDao.updateModStat(seqCrtId); 
		return rs > 0 ? true : false;
	}

	/**
	 * 根据主键删除数据
	 * 
	 * @param seqCrtId
	 * @see public int delete(T entity)
	 * @return
	 */
	public boolean delete(String seqCrtId) {
		boolean rs = false;
		if (DataUtil.isNullStr(seqCrtId)) {
			throw new BaseException(SysErr.E_IN_NULL, "seqCrtId");
		}

		try {
			tTseqSeqCrtDao.delete(seqCrtId);
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
	public boolean delete(TTseqSeqCrtDO obj) {
		boolean rs = false;
		if (null == obj) {
			throw new BaseException(SysErr.E_DEFAULT, "删除数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getSeqCrtId())) {
			throw new BaseException(SysErr.E_IN_NULL, "seqCrtId");
		}

		try {
			tTseqSeqCrtDao.delete(obj);
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
	public List<TTseqSeqCrtDO> list(TTseqSeqCrtDO obj) {
		return tTseqSeqCrtDao.list(obj);
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
	public List<TTseqSeqCrtDO> list(TTseqSeqCrtDO obj, int start, int limit) {
		return tTseqSeqCrtDao.list(obj, start, limit);
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
	public List<TTseqSeqCrtDO> list(int start, int limit, Object... param) {
		return tTseqSeqCrtDao.list(start, limit, param);
	}

	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * 
	 * @param obj
	 *            数据对象DO
	 * @return total
	 */
	public int getTotal(TTseqSeqCrtDO obj) {
		return tTseqSeqCrtDao.getTotal(obj);
	}

	/**
	 * 数据库多笔查询
	 * 
	 * @param obj
	 *            数据对象DO
	 * @return List返回集合
	 */
	public List<TseqTParaRelatSysDO> relatSysList(TseqTParaRelatSysDO obj) {
		return tseqTParaRelatSysDao.list(obj);
	}
}