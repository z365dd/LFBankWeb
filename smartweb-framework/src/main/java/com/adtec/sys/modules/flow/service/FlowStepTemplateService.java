/**
 * 系统名称: SmartWeb平台
 * 模块名称: 流程步骤模板服务类
 * 类  名  称: FlowStepTemplateService.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2018年8月7日 上午10:40:23<br>
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.modules.flow.dao.FlowStepTemplateDao;
import com.adtec.sys.modules.flow.entity.FlowStepDO;
import com.adtec.sys.modules.flow.entity.FlowStepTemplateDO;
import com.adtec.sys.modules.flow.entity.FlowTemplateDO;

/**
 * @author chenyl
 *
 */
@Service
@Transactional(readOnly = true)
public class FlowStepTemplateService {
	private final static Logger log = LoggerFactory.getLogger(FlowStepTemplateService.class);
	@Autowired
	private FlowStepTemplateDao flowStepTemplateDao;
	@Autowired
	private FlowTemplateService flowTemplateService;
	@Autowired
	private FlowStepService flowStepService;
	
	
	/**ni ji
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public FlowStepTemplateDO get(String id) {
		return flowStepTemplateDao.get(id);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	public FlowStepTemplateDO get(FlowStepTemplateDO obj) {
		return flowStepTemplateDao.get(obj);
	}
	
	/**
     * 根据流程模板ID+步骤号获取对应的流程步骤模板
     * @param flowTmplId	流程模板ID
     * @param stepSer			步骤号
     * @return
     */
    public FlowStepTemplateDO getByStepSer(String flowTmplId, int stepSer) {
    	return flowStepTemplateDao.getByStepSer(flowTmplId, stepSer);
    }

	public FlowStepTemplateDO getByTmpl(String flowTmplId) {
		return flowStepTemplateDao.getByTmpl(flowTmplId);
	}

	/**
	 * 数据库多笔查询，支持分页
	 * @param obj 数据对象DO
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @return List返回集合
	 */
	public List<FlowStepTemplateDO> list(FlowStepTemplateDO obj, int start, int limit){
		return flowStepTemplateDao.list(obj, start, limit);
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(FlowStepTemplateDO obj) {
		return flowStepTemplateDao.getTotal(obj);
	}
	   
    /**
     * 根据流程模板id删除对应的流程步骤模板
     * @param flowTmplId
     * @return
     */
    public boolean deleteByFlowTmplId(String flowTmplId) {
    	int rs = 0;
    	rs = flowStepTemplateDao.deleteByFlowTmplId(flowTmplId);
        return rs>0?true:false;
    }
    
    /**
	 * 删除流程步骤模板
	 * @param obj
	 * @return
	 */
	public boolean delete(FlowStepTemplateDO obj){
		int rs = 0;
		// 获取数据库保存的数据
		FlowStepTemplateDO qryDO = flowStepTemplateDao.get(obj.getId());
		String flowTmplId = qryDO.getFlowTmplId();
		// 检查流程模板状态是否为挂起
		if(!flowTemplateService.chkFlowTemplateStat(flowTmplId, FlowTemplateDO.STAT_OFF)){
			throw new BaseException(SysErr.E_DEFAULT, "流程模板非挂起，流程步骤模板删除失败");
		}
		// 检查是否存在对应的步骤实例
		FlowStepDO qryStep = new FlowStepDO();
		qryStep.setStepTmplId(qryDO.getId());
		if(flowStepService.getTotal(qryStep)>0){
			throw new BaseException(SysErr.E_DEFAULT, "关联步骤实例，流程步骤模板删除失败");
		}
		
		int stepSer = qryDO.getStepSer();	// 获取当前节点号
		int prvStepSer = qryDO.getPrvStepSer(); // 获取上一步骤号
		int nextStepSer = qryDO.getNextStepSer(); // 获取下一步骤号
		
		boolean nextStepSub = false; // 后续步骤是否-1操作
		if(FlowStepTemplateDO.STEP_END==nextStepSer){
			// 当前步骤为最后一个步骤节点，则只需要更新上一步骤节点的下一不步骤号为当前节点步骤号
			nextStepSub = false;
		}else{
			// 当前步骤节点后续步骤的前一步骤号、当前步骤号、下一步骤号(除去该步骤号为999-总流程结束)，进行-1操作
			nextStepSub = true;
		}

		// 开启事务
		IDBSession session = DBSessionFactory.getSession();
		try {
			session.beginTransaction();
			// 删除当前步骤节点
			rs = flowStepTemplateDao.delete(obj);
			
			if (nextStepSub) {
				// 更新后续所有步骤节点，步骤号-1
				flowStepTemplateDao.moveStepSer(flowTmplId, stepSer, -1);
			} else {
				// 更新上一个节点号的下一步骤号为最后一步骤号999-总流程结束
				flowStepTemplateDao.updateNextStepSer(flowTmplId, prvStepSer, FlowStepTemplateDO.STEP_END);
			}
			session.endTransaction();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			try {
				session.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_DEFAULT, e, "新增流程步骤模板[" + obj.getEngName() + "]数据库事务失败");
		}
		return rs > 0 ? true : false;
	}
	
    
    /**
	 * 修改流程步骤模板
	 * @param obj
	 * @return
	 */
	public boolean update(FlowStepTemplateDO obj) {
		int rs = 0;
		String flowTmplId = obj.getFlowTmplId();
		// 检查流程模板状态是否为挂起
		if(!flowTemplateService.chkFlowTemplateStat(flowTmplId, FlowTemplateDO.STAT_OFF)){
			throw new BaseException(SysErr.E_DEFAULT, "流程模板非挂起，流程步骤模板修改失败");
		}
		// 获取数据库保存的数据
		FlowStepTemplateDO qryDO = flowStepTemplateDao.get(obj.getId());
		if(null==qryDO){
			throw new BaseException(SysErr.E_DEFAULT, "步骤模板["+obj.getId()+"]不存在");
		}
		// 更新流程步骤模板数据
		obj.setFlowTmplId(qryDO.getFlowTmplId());
		obj.setPrvStepSer(qryDO.getPrvStepSer());
		obj.setStepSer(qryDO.getStepSer());
		obj.setNextStepSer(qryDO.getNextStepSer());
		obj.setCrtr(qryDO.getCrtr());
		obj.setCrtTime(qryDO.getCrtTime());
		obj.preUpdate();
		rs = flowStepTemplateDao.update(obj);
		return rs > 0 ? true : false;
	}

	
    /**
	 * 新增流程步骤模板
	 * @param obj
	 * @return
	 */
	public boolean insert(FlowStepTemplateDO obj){
		int rs = 0;
		String flowTmplId = obj.getFlowTmplId();
		// 检查流程模板状态是否为挂起
		if(!flowTemplateService.chkFlowTemplateStat(flowTmplId, FlowTemplateDO.STAT_OFF)){
			throw new BaseException(SysErr.E_DEFAULT, "流程模板非挂起，流程步骤模板新增失败");
		}
		
		// 获取当前流程模板下存在的步骤数量
		FlowStepTemplateDO qryDO = new FlowStepTemplateDO();
		qryDO.setmQry(FlowStepTemplateDO.MQRY_Y);
		qryDO.setFlowTmplId(flowTmplId);
		int stepCount = flowStepTemplateDao.getTotal(qryDO);
		// 获取上一步骤信息
		int prvStepSer = obj.getPrvStepSer();
		FlowStepTemplateDO preFlowStep = getByStepSer(flowTmplId, prvStepSer);
		
		// 更新其他步骤的sql
		boolean nextStepAdd = false; 
		int stepSer = prvStepSer+1;	// 当前节点号为prvStepSer+1
		int nextStepSer = stepSer + 1; // 默认下一步为stepSer+1
		if((null!=preFlowStep && FlowStepTemplateDO.STEP_END==preFlowStep.getNextStepSer()) || stepCount<=0){
			// 首个步骤添加或上一步骤为最后一个步骤节点，则只需要更新上一步骤节点的下一不步骤号为当前节点步骤号
			nextStepAdd = false;
			nextStepSer = FlowStepTemplateDO.STEP_END; // 当前步骤的下一步骤为999-总流程结束
		}else{
			// 当前步骤节点后续步骤的前一步骤号、当前步骤号、下一步骤号(除去该步骤号为999-总流程结束)，进行+1操作
			nextStepAdd = true;
		}
		obj.setStepSer(stepSer);
		obj.setNextStepSer(nextStepSer);
		// 生成保存的ID
		obj.preInsert();
				
		// 开启事务
		IDBSession session = DBSessionFactory.getSession();
		try {
			session.beginTransaction();
			if(nextStepAdd){
				// 更新后续所有步骤节点，步骤号+1
				flowStepTemplateDao.moveStepSer(flowTmplId, prvStepSer, 1);
			}else{
				// 更新上一个节点号的下一步骤号为当前步骤号
				flowStepTemplateDao.updateNextStepSer(flowTmplId, prvStepSer, stepSer);
			}
			// 新增新的步骤节点
			rs = flowStepTemplateDao.insert(obj);
			session.endTransaction();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			try {
				session.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_DEFAULT, e, "新增流程步骤模板[" + obj.getEngName() + "]数据库事务失败");
		}
		return rs>0? true:false;
	}

	/**
	 * 获取最后一个步骤模板
	 * @param flowTmplId
	 * @return
	 */
	public FlowStepTemplateDO getLastStep(String flowTmplId) {
		// TODO Auto-generated method stub
		return flowStepTemplateDao.getLastStep(flowTmplId);
	}
	
}
