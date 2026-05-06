/**
* 系统名称: SmartWeb平台
* 模块名称: comp.fsvr.tec服务模块
* 功能描述: 文件格式总体参数配置表服务提供类
* 类 名 称  : TfsvrFileTotParaService.java
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

import com.adtec.comp.fsvr.tec.dao.TfsvrFileTotParaDao;
import com.adtec.comp.fsvr.tec.entity.TfsvrFileTotParaDO;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;

/**
 * TfsvrFileTotParaService
 * @author zhengjt
 * @version 20200630
 */
@Service
@Transactional(readOnly = true)
public class TfsvrFileTotParaService {
	private final static Logger log = LoggerFactory.getLogger(TfsvrFileTotParaService.class);
	
	@Autowired
	private TfsvrFileTotParaDao tfsvrFileTotParaDao;
	
	/**
	 * 获取单条数据
	 * @param fmtNo
	 * @return
	 */
	public TfsvrFileTotParaDO get(
		String fmtNo
	) {
		return tfsvrFileTotParaDao.get(
		fmtNo
);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	public TfsvrFileTotParaDO get(TfsvrFileTotParaDO obj) {
		return tfsvrFileTotParaDao.get(obj);
	}
	
	/**
	 * 获取最大转换号
	 * @param obj
	 * @return
	 */
	public String getMaxFmtNo() {
		return tfsvrFileTotParaDao.getMaxFmtNo();
	}
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	public boolean insert(TfsvrFileTotParaDO obj){
		int rs = 0;
		obj.preInsert();
		rs = tfsvrFileTotParaDao.insert(obj);
		return rs>0? true:false;
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	public boolean update(TfsvrFileTotParaDO obj){
		int rs = 0;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "更新数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getFmtNo())) {
			throw new BaseException(SysErr.E_IN_NULL, "fmtNo");
		}		

		// 获取数据库保存的数据
		TfsvrFileTotParaDO qryDO = tfsvrFileTotParaDao.get(
		obj.getFmtNo()
		);
		// 更新产品数据
		obj.setCrtr(qryDO.getCrtr());
		obj.setCrtTime(qryDO.getCrtTime());
		obj.preUpdate();		
		rs = tfsvrFileTotParaDao.update(obj);
		return rs>0? true:false;
	}
	
	/**
	 * 根据主键删除数据
	 * @param fmtNo
	 * @see public int delete(T entity)
	 * @return
	 */
	public boolean delete(
		String fmtNo
	){
		boolean rs = false;
		if (DataUtil.isNullStr(fmtNo)) {
			throw new BaseException(SysErr.E_IN_NULL, "fmtNo");
		}		

		try{
			tfsvrFileTotParaDao.delete(
				fmtNo
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
	public boolean delete(TfsvrFileTotParaDO obj){
		boolean rs = false;
		if(null==obj){
			throw new BaseException(SysErr.E_DEFAULT, "删除数据对象不能空！");
		}
		if (DataUtil.isNullStr(obj.getFmtNo())) {
			throw new BaseException(SysErr.E_IN_NULL, "fmtNo");
		}		
		
		try{
			tfsvrFileTotParaDao.delete(obj);
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
	public List<TfsvrFileTotParaDO> list(TfsvrFileTotParaDO obj) {
		return tfsvrFileTotParaDao.list(obj);
	}
	
	/**
	 * 模板格式号下拉框 数据库多笔查询，支持分页
	 * @param obj 数据对象DO
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @return List返回集合
	 */
	public List<TfsvrFileTotParaDO> selectList(TfsvrFileTotParaDO obj) {
		return tfsvrFileTotParaDao.selectList(obj);
	}
	
	/**
	 * 模板格式号下拉框--带衍生 数据库多笔查询，支持分页
	 * @param obj 数据对象DO
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @return List返回集合
	 */
	public List<TfsvrFileTotParaDO> selectListTwo(TfsvrFileTotParaDO obj) {
		return tfsvrFileTotParaDao.selectListTwo(obj);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param obj 数据对象DO
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @return List返回集合
	 */
	public List<TfsvrFileTotParaDO> list(TfsvrFileTotParaDO obj, int start, int limit) {
		return tfsvrFileTotParaDao.list(obj, start, limit);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @param param 查询参数
	 * @return List返回集合
	 */
	public List<TfsvrFileTotParaDO> list(int start, int limit, Object... param) {
		return tfsvrFileTotParaDao.list(start, limit, param);
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(TfsvrFileTotParaDO obj) {
		return tfsvrFileTotParaDao.getTotal(obj);
	}
}