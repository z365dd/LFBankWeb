/**
 * 系统名称: SmartWeb平台
 * 模块名称: 流程模板服务类
 * 类  名  称: FlowTemplateService.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2018年8月6日 下午3:53:25<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.sys.modules.flow.service;

import java.sql.SQLException;
import java.util.List;

import com.adtec.sys.modules.flow.dao.FlowDao;
import com.adtec.sys.modules.flow.dao.FlowStepTemplateDao;
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
import com.adtec.sys.modules.flow.dao.FlowTemplateDao;
import com.adtec.sys.modules.flow.entity.FlowTemplateDO;
import com.adtec.sys.modules.sys.utils.DictUtils;

/**
 * @author chenyl
 *
 */
@Service
@Transactional(readOnly = true)
public class FlowTemplateService {
	private final static Logger log = LoggerFactory.getLogger(FlowTemplateService.class);
	@Autowired
	private FlowTemplateDao flowTemplateDao;
	@Autowired
	private FlowStepTemplateDao flowStepTemplateDao;
	@Autowired
	private FlowService flowService;
	@Autowired
	private FlowStepTemplateService flowStepTemplateService;
	@Autowired
	private FlowDao flowDao;
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public FlowTemplateDO get(String id) {
		return flowTemplateDao.get(id);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	public FlowTemplateDO get(FlowTemplateDO obj) {
		return flowTemplateDao.get(obj);
	}
	
	/**
	 * 根据业务唯一索引：英文名称+流程类型+流程版本，获取流程模板
	 * @param enname		英文名称
	 * @param flowTp		流程类型
	 * @param version		流程模板版本
	 * @return
	 */
	public FlowTemplateDO get(String enname, String flowTp, String version){
		return flowTemplateDao.get(enname, flowTp, version);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param obj 数据对象DO
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @return List返回集合
	 */
	public List<FlowTemplateDO> list(FlowTemplateDO obj, int start, int limit){
		return flowTemplateDao.list(obj, start, limit);
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(FlowTemplateDO obj) {
		return flowTemplateDao.getTotal(obj);
	}
	
	/**
	 * 根据业务唯一属性：流程英文名+流程类型+流程版本，检查流程模板是否存在
	 * @param enname
	 * @param flowTp
	 * @param version
	 * @return
	 */
	public boolean flowTemplateExist(String enname, String flowTp, String version){
		boolean exist = false;
		// 检查是否已存在对应的数据
		FlowTemplateDO qryDO = new FlowTemplateDO();
		qryDO.setEngName(enname);
		qryDO.setFlowTp(flowTp);
		qryDO.setVerNo(version);
		qryDO.setmQry(FlowTemplateDO.MQRY_Y);
		if(flowTemplateDao.getTotal(qryDO)>0){
			exist = true;
		}
		return exist;
	}
	
	/**
	 * 根据流程模板id，检查状态是否与给定的状态一致
	 * @param flowTmplId	流程模板ID
	 * @param stat				流程模板状态：1-启用、2-挂起
	 * @return
	 */
	public boolean chkFlowTemplateStat(String flowTmplId, String stat){
		boolean equals = false;
		// 获取流程模板信息
		FlowTemplateDO qryDO = flowTemplateDao.get(flowTmplId);
		if(null!=qryDO && qryDO.getFlowTmplStat().equals(stat)){
			equals = true;
		}
		return equals;
	}
	
	/**
	 * 新增流程模板
	 * @param obj
	 * @return
	 */
	public boolean insert(FlowTemplateDO obj){
		int rs = 0;
		// 检查是否已存在对应的数据
		if(flowTemplateExist(obj.getEngName(), obj.getFlowTp(), obj.getVerNo())){
			throw new BaseException(SysErr.E_DEFAULT, "流程类型["+DictUtils.getDictLabel(obj.getFlowTp(), "FLOW_TP", obj.getFlowTp())+"],英文名称["+obj.getEngName()+"],版本["+obj.getVerNo()+"]流程模板已存在");
		}
		// 生成保存的ID
		obj.preInsert();
		// 新增的流程模板默认状态为：2-挂起
		obj.setFlowTmplStat(FlowTemplateDO.STAT_OFF);
		// 插入数据库
		rs = flowTemplateDao.insert(obj);
		return rs>0? true:false;
	}
	
	/**
	 * 修改流程模板
	 * @param obj
	 * @return
	 */
	public boolean update(FlowTemplateDO obj) {
		int rs = 0;
		// 获取数据库保存的数据
		FlowTemplateDO qryDO = flowTemplateDao.get(obj.getId());
		// 更新流程模板数据
		obj.setCrtr(qryDO.getCrtr());
		obj.setCrtTime(qryDO.getCrtTime());
		obj.setFlowTmplStat(qryDO.getFlowTmplStat());
		obj.setFlowTp(qryDO.getFlowTp());
		obj.preUpdate();
		rs = flowTemplateDao.update(obj);
		return rs > 0 ? true : false;
	}

	/**
	 * 删除流程模板
	 * @param obj
	 * @return
	 */
	public boolean delete(FlowTemplateDO obj){
		int rs = 0;
		if(null!=obj && DataUtil.isNullStr(obj.getId())){
			throw new BaseException(SysErr.E_DEFAULT, "删除的流程模板ID不能空");
		}
		String flowTmplId = obj.getId();
		// 检查流程模板状态是否为挂起
		if(!chkFlowTemplateStat(flowTmplId, FlowTemplateDO.STAT_OFF)){
			throw new BaseException(SysErr.E_DEFAULT, "流程模板非挂起，删除失败");
		}
		
		// 存在对应的流程实例，删除失败
		if(flowService.getTotal(flowTmplId)>0){
			throw new BaseException(SysErr.E_DEFAULT, "流程模板存在流程实例，删除失败");
		}

		// 存在对应的流程步骤模板，删除失败
		if(flowStepTemplateDao.getByTmpl(flowTmplId) != null){
			throw new BaseException(SysErr.E_DEFAULT, "流程模板存在步骤模板，删除失败");
		}

		// 开启事务
		IDBSession session = DBSessionFactory.getSession();
		try {
			session.beginTransaction();
			// 删除对应的流程步骤模板
			flowStepTemplateService.deleteByFlowTmplId(flowTmplId);
			// 删除流程模板
			rs = flowTemplateDao.delete(obj);			
			session.endTransaction();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			try {
				session.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_DEFAULT, e, "删除流程模板["+obj.getId()+"]数据库事务失败");
		}
		
		return rs>0? true:false;
	}
	
	/**
	 * 更新流程模板状态
	 * @param obj
	 * @return
	 */
	public boolean updateStatus(String flowStepTmplId, String status) {
		log.info("更新流程模板["+flowStepTmplId+"]状态["+status+"]");

		if("2".equals(status) && flowDao.getUndoneByTemplateId(flowStepTmplId) != null){
			throw new BaseException(SysErr.E_DEFAULT, "流程模板有未结束的流程，不能挂起");
		}

		int rs = 0;
		rs = flowTemplateDao.updateStatus(flowStepTmplId, status);
		return rs > 0 ? true : false;
	}

	/**
	 * 获取当前用户有权使用的流程模板
	 * @param qryDO
	 * @return
	 */
	public List<FlowTemplateDO> listBySndUser(FlowTemplateDO qryDO) {
		// TODO Auto-generated method stub
		return flowTemplateDao.listBySndUser(qryDO);
	}
}
