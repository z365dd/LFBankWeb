/**
 * 系统名称: SmartWeb平台
 * 模块名称: 流程实例服务类
 * 类  名  称: FlowService.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2018年8月7日 上午9:39:26<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.sys.modules.flow.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.modules.flow.dao.FlowDao;
import com.adtec.sys.modules.flow.dao.FlowStepDao;
import com.adtec.sys.modules.flow.entity.FlowDO;
import com.adtec.sys.modules.flow.entity.FlowStepDO;
import com.adtec.sys.modules.flow.entity.FlowStepTemplateDO;
import com.adtec.sys.modules.flow.entity.FlowTemplateDO;
import com.adtec.sys.modules.flow.event.IFlowEvent;
import com.adtec.sys.modules.sys.entity.Office;
import com.adtec.sys.modules.sys.entity.Role;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.service.SystemService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author chenyl
 *
 */
@Service
@Transactional(readOnly = true)
public class FlowService {
	private final static Logger log = LoggerFactory.getLogger(FlowService.class);
	@Autowired
	private FlowDao flowDao;
	@Autowired
	FlowTemplateService flowTemplateService;
	@Autowired
	FlowStepTemplateService flowStepTemplateService;
	@Autowired
	FlowStepService flowStepService;
	@Autowired
	private SystemService systemService;
    @Autowired
    private FlowStepDao flowStepDao;	
    /**
     * 存储外部自定义每个步骤待处理审批人员列表,key:全局业务流水号，value：<步骤号，用户列表>
     */
    public final static ConcurrentHashMap<String , Map<String, List<User>>> STEP_DEAL_MAP = new ConcurrentHashMap<String , Map<String, List<User>>>();
	
	/**
	 * 根据流程ID获取流程信息
	 * @param id
	 * @return
	 */
	public FlowDO get(String id) {
		// TODO Auto-generated method stub
		return flowDao.get(id);
	}

	/**
	 * 根据业务流水号获取流程信息
	 * @param globalSeq		业务流水号
	 * @return
	 */
	public FlowDO getByGlobalSeq(String globalSeq) {
		// TODO Auto-generated method stub
		return flowDao.getByGlobalSeq(globalSeq);
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(FlowDO obj) {
		return flowDao.getTotal(obj);
	}
	
	/**
	 * 根据流程模板ID查询关联的流程实例数
	 * @param flowTmplId 流程模板id
	 * @return total
	 */
	public int getTotal(String flowTmplId) {
		return flowDao.getInstByTemplateId(flowTmplId);
	}
	
	/**
	 * 根据流程模板ID检查是否存在未结束的流程实例
	 * @param flowTmplId	流程模板ID
	 * @return
	 */
	public boolean chkFlowByTemplate(String flowTmplId){
		FlowDO qryDO = new FlowDO();
		qryDO.setFlowTmplId(flowTmplId);
		return flowDao.chkFlowByTemplate(qryDO);
	}

	/**
	 * @param flowDO
	 * @param start
	 * @param limit
	 * @return
	 */
	public HashMap<String, Object> listByAuth(FlowDO flowDO, int start, int limit) {
		// TODO Auto-generated method stub
		return flowDao.listByAuth(flowDO, start, limit);
	}
	
	/**
	 * 删除流程实例
	 * @param obj
	 * @return
	 */
	public boolean delete(FlowDO obj) {
		// TODO Auto-generated method stub
		int rs = 0;
		// 获取数据库保存的数据
		FlowDO qryDO = flowDao.get(obj.getId());
		if(null==qryDO){
			throw new BaseException(SysErr.E_DEFAULT, "流程实例[" + obj.getId() + "]不存在");
		}
		String globalSeq = qryDO.getGlobalSeq();
		String flowTmplId = qryDO.getFlowTmplId();
		
		// 开启事务
		IDBSession session = DBSessionFactory.getSession();
		try {
			session.beginTransaction();
			// 清理掉所有相关的流程步骤实例
			flowStepService.deleteByFlowTmplId(globalSeq, flowTmplId);

			// 删除流程实例
			rs = flowDao.delete(obj);
			
			session.endTransaction();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			try {
				session.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_DEFAULT, e, "删除流程实例[" + obj.getId() + "]数据库事务失败");
		}
		return rs > 0 ? true : false;
	}
	
	/**
	 * 删除流程实例
	 * @param globalSeq
	 * @return
	 */
	public boolean delByGlobalSeq(String globalSeq) {
		int rs = 0;
		// 开启事务
		IDBSession session = DBSessionFactory.getSession();
		try {
			session.beginTransaction();
			// 清理掉所有相关的流程步骤实例
			flowStepService.deleteByFlowTmplId(globalSeq, "");
			// 删除流程实例
			rs = flowDao.delByGlobalSeq(globalSeq);
			session.endTransaction();
		} catch (Exception e) {
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return rs > 0 ? true : false;
	}
	
	/**
	 * 修改流程信息
	 * @param obj
	 * @return
	 */
	public boolean update(FlowDO obj) {
		// TODO Auto-generated method stub
		int rs = 0;
		// 获取数据库保存的数据
		FlowDO qryDO = flowDao.get(obj.getId());
		String globalSeq = obj.getGlobalSeq();
		String flowTmplId = obj.getFlowTmplId();
		// 更新流程模板数据
		obj.setCrtr(qryDO.getCrtr());
		obj.setCrtTime(qryDO.getCrtTime());
		obj.setGlobalSeq(qryDO.getGlobalSeq());
		obj.setFlowStat(qryDO.getFlowStat());
		obj.setStepSer(qryDO.getStepSer());
		obj.setStrTime(DateUtil.getDateTime());
		// 生成更新前相关要素
		obj.preUpdate();
		
		// 开启事务
		IDBSession session = DBSessionFactory.getSession();
		try {
			session.beginTransaction();
			// 清理掉所有相关的原流程步骤实例
			flowStepService.deleteByFlowTmplId(globalSeq, qryDO.getFlowTmplId());
			
			// 通过步骤模板添加第一步审批流程实例
			int stepCount = flowStepService.generateStepInst(flowTmplId, obj.getStepSer() + 1, obj.getGlobalSeq(),
					obj.getInfoTitle(), false, false);
			log.info("重新生成步骤[" + (obj.getStepSer() + 1) + "]的实例数[" + stepCount + "]");

			// 更新流程实例信息
			rs = flowDao.update(obj);
			session.endTransaction();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			try {
				session.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_DEFAULT, e, "更新流程实例[" + obj.getId() + "]数据库事务失败");
		}

		return rs > 0 ? true : false;
	}

	/**
	 * @param obj
	 * @return
	 */
	public boolean insert(FlowDO obj) {
		// TODO Auto-generated method stub
		int rs = 0;
		
		// 获取流程模板信息
		String flowTmplId = obj.getFlowTmplId();
		FlowTemplateDO flowTemplate = flowTemplateService.get(flowTmplId);
		if(null==flowTemplate){
			throw new BaseException(SysErr.E_DEFAULT, "流程模板["+flowTmplId+"]不存在");
		}
		if(!FlowTemplateDO.STAT_ON.equals(flowTemplate.getFlowTmplStat())){
			throw new BaseException(SysErr.E_DEFAULT, "flowTemplateEnname["+flowTemplate.getEngName()+"] , flowTp["+flowTemplate.getFlowTp()+"] , flowTemplateVersion["+flowTemplate.getVerNo()+"]流程模板非启用状态，发起流程实例失败");
		}
		String sndFlowUserTp = flowTemplate.getSndFlowUserTp();
		if(!DataUtil.isNullStr(sndFlowUserTp)){
			// 发起用户类型有指定的，则检查当前发起用户不能为空且为允许范围内
			if(DataUtil.isNullStr(obj.getSndUserId())){
				throw new BaseException(SysErr.E_DEFAULT, "流程发起用户类型["+sndFlowUserTp+"],发起用户不能空");
			}else{
				boolean hasAuth = false;
				String sndUserId = obj.getSndUserId();
				// 如果发起用户名为空的，则通过查询当前用户信息补填
				SystemService systemService = SpringContextHolder.getBean("systemService");
				User user = systemService.getUser(sndUserId);
				if(null==user){
					throw new BaseException(SysErr.E_DEFAULT, "流程发起用户["+sndUserId+"]不存在");
				}
				
				if("user".equals(sndFlowUserTp)){
					// 指定用户发起
					if(sndUserId.equals(flowTemplate.getSndUserId())){
						hasAuth = true;
					}			
				}else if("office".equals(sndFlowUserTp)){
					// 指定机构发起
					Office office = user.getOffice();
					String officeList = office.getParentIdList()+office.getId();
					if(officeList.contains(flowTemplate.getSndUserId())){
						hasAuth = true;
					}			
				}else if("role".equals(sndFlowUserTp)){
					// 指定角色发起
					Role role = new Role();
					role.setUser(user);
					List<Role> roleList = systemService.findAllRoleWithUser(role);
					for(Role r:roleList){
						if(null!=r && r.getId().equals(flowTemplate.getSndUserId())){
							hasAuth = true;
							break;
						}
					}			
				}else{
					throw new BaseException(SysErr.E_DEFAULT, "不支持该发起用户类型["+sndFlowUserTp+"]");
				}
								
				if(!hasAuth){
					throw new BaseException(SysErr.E_DEFAULT, "流程发起用户类型["+sndFlowUserTp+"],当前用户["+sndUserId+"]无权限发起流程");
				}
				if(DataUtil.isNullStr(obj.getSndUserName())){
					obj.setSndUserName(user.getName());
				}
			}			
		}else{
			// 不选择发起用户类型，如果上送的发起用户为空的，则设置流程发起用户为：系统自动发起
			if(DataUtil.isNullStr(obj.getSndUserId())){
				obj.setSndUserId(FlowDO.FLOW_INIT_USER);
				obj.setSndUserName(FlowDO.FLOW_INIT_USER_NAME);
			}
		}
		// 生成保存的ID
		obj.preInsert();
		// 新增的流程默认状态为：00-开始
		obj.setFlowStat(FlowDO.STAT_START);
		// 设置当前流程步骤号:0-总流程开始
		obj.setStepSer(FlowStepTemplateDO.STEP_START);
		// 设置流程开始时间
		obj.setStrTime(DateUtil.getDateTime());
		
		// 开启事务
		IDBSession session = DBSessionFactory.getSession();
		try {
			session.beginTransaction();
			// 通过步骤模板添加第一步审批流程实例
			int stepCount = flowStepService.generateStepInst(flowTmplId, obj.getStepSer()+1, obj.getGlobalSeq(), obj.getInfoTitle(), false, false);
			log.info("生成步骤["+(obj.getStepSer()+1)+"]的实例数["+stepCount+"]");

			// 插入数据库
			rs = flowDao.insert(obj);
			session.endTransaction();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			try {
				session.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_DEFAULT, e, "新增流程实例[" + obj.getId() + "]数据库事务失败");
		}

		return rs > 0 ? true : false;
	}
	
	/**
	 * @param obj
	 * @return
	 */
	public boolean insertNoTransaction(FlowDO obj) {
		// TODO Auto-generated method stub
		int rs = 0;
		
		// 获取流程模板信息
		String flowTmplId = obj.getFlowTmplId();
		FlowTemplateDO flowTemplate = flowTemplateService.get(flowTmplId);
		if(null==flowTemplate){
			throw new BaseException(SysErr.E_DEFAULT, "流程模板["+flowTmplId+"]不存在");
		}
		if(!FlowTemplateDO.STAT_ON.equals(flowTemplate.getFlowTmplStat())){
			throw new BaseException(SysErr.E_DEFAULT, "flowTemplateEnname["+flowTemplate.getEngName()+"] , flowTp["+flowTemplate.getFlowTp()+"] , flowTemplateVersion["+flowTemplate.getVerNo()+"]流程模板非启用状态，发起流程实例失败");
		}
		String sndFlowUserTp = flowTemplate.getSndFlowUserTp();
		if(!DataUtil.isNullStr(sndFlowUserTp)){
			// 发起用户类型有指定的，则检查当前发起用户不能为空且为允许范围内
			if(DataUtil.isNullStr(obj.getSndUserId())){
				throw new BaseException(SysErr.E_DEFAULT, "流程发起用户类型["+sndFlowUserTp+"],发起用户不能空");
			}else{
				boolean hasAuth = false;
				String sndUserId = obj.getSndUserId();
				// 如果发起用户名为空的，则通过查询当前用户信息补填
				SystemService systemService = SpringContextHolder.getBean("systemService");
				User user = systemService.getUser(sndUserId);
				if(null==user){
					throw new BaseException(SysErr.E_DEFAULT, "流程发起用户["+sndUserId+"]不存在");
				}
				
				if("user".equals(sndFlowUserTp)){
					// 指定用户发起
					if(sndUserId.equals(flowTemplate.getSndUserId())){
						hasAuth = true;
					}			
				}else if("office".equals(sndFlowUserTp)){
					// 指定机构发起
					Office office = user.getOffice();
					String officeList = office.getParentIdList()+office.getId();
					if(officeList.contains(flowTemplate.getSndUserId())){
						hasAuth = true;
					}			
				}else if("role".equals(sndFlowUserTp)){
					// 指定角色发起
					Role role = new Role();
					role.setUser(user);
					List<Role> roleList = systemService.findAllRoleWithUser(role);
					for(Role r:roleList){
						if(null!=r && r.getId().equals(flowTemplate.getSndUserId())){
							hasAuth = true;
							break;
						}
					}			
				}else{
					throw new BaseException(SysErr.E_DEFAULT, "不支持该发起用户类型["+sndFlowUserTp+"]");
				}
				
				if(!hasAuth){
					throw new BaseException(SysErr.E_DEFAULT, "流程发起用户类型["+sndFlowUserTp+"],当前用户["+sndUserId+"]无权限发起流程");
				}
				if(DataUtil.isNullStr(obj.getSndUserName())){
					obj.setSndUserName(user.getName());
				}
			}			
		}else{
			// 不选择发起用户类型，如果上送的发起用户为空的，则设置流程发起用户为：系统自动发起
			if(DataUtil.isNullStr(obj.getSndUserId())){
				obj.setSndUserId(FlowDO.FLOW_INIT_USER);
				obj.setSndUserName(FlowDO.FLOW_INIT_USER_NAME);
			}
		}
		// 生成保存的ID
		obj.preInsert();
		// 新增的流程默认状态为：00-开始
		obj.setFlowStat(FlowDO.STAT_START);
		// 设置当前流程步骤号:0-总流程开始
		obj.setStepSer(FlowStepTemplateDO.STEP_START);
		// 设置流程开始时间
		obj.setStrTime(DateUtil.getDateTime());
		
		// 开启事务
		//IDBSession session = DBSessionFactory.getSession();
		try {
			//session.beginTransaction();
			// 通过步骤模板添加第一步审批流程实例
			int stepCount = flowStepService.generateStepInst(flowTmplId, obj.getStepSer()+1, obj.getGlobalSeq(), obj.getInfoTitle(), false, false);
			log.info("生成步骤["+(obj.getStepSer()+1)+"]的实例数["+stepCount+"]");
			
			// 插入数据库
			rs = flowDao.insert(obj);
			//session.endTransaction();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			/*try {
				session.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
			throw new BaseException(SysErr.E_DEFAULT, e, "新增流程实例[" + obj.getId() + "]数据库事务失败");
		}
		
		return rs > 0 ? true : false;
	}
	
	/**
	 * 根据流程模板ID获取发起用户或超时处理用来列表
	 * @param flowStepTmplId
	 * @param isTimeoutUser			true-获取超时处理用户，false-获取发起用户
	 * @return
	 */
	public List<User> getUserListByFlowTmplId(String flowStepTmplId, boolean isTimeoutUser){
		List<User> userList = Lists.newArrayList();
		// 获取对应的步骤模板信息
		FlowTemplateDO flowTemplateDO = flowTemplateService.get(flowStepTmplId);
		String userType = flowTemplateDO.getSndFlowUserTp();
		String userId = flowTemplateDO.getSndUserId();
		if(isTimeoutUser){
			// 获取超时处理用户
			userType = flowTemplateDO.getTimeOutFlowUserTp();
			userId = flowTemplateDO.getTimeOutProcUserId();
		}
		if("user".equals(userType)){
			// 指定用户
			User user = systemService.getUser(userId);
			if(null!=user){
				userList.add(user);
			}
		}else if("office".equals(userType)){
			// 指定机构
			User qryUser = new User();
			qryUser.setOffice(new Office(userId));
			// 获取当前机构以及所有的下级机构下的用户列表
			List<User> uList = systemService.findUser(qryUser);
			if(null!=uList){
				userList.addAll(uList);
			}
			if ("1".equals(userId)) {
				// 加入admin用户
				userList.add(systemService.getUser("1"));
			}
		}else if("role".equals(userType)){
			// 指定角色
			User qryUser = new User();
			qryUser.setRole(new Role(userId));
			List<User> uList = systemService.findUser(qryUser);
			if(null!=uList){
				userList.addAll(uList);
			}
			if ("1".equals(userId)) {
				// 加入admin用户
				userList.add(systemService.getUser("1"));
			}
		}		
		return userList;
	}
	
	/**
	 * 发起流程
	 * @param globalSeq			全局流水号，必输
	 * @param flowTemplateEnname	流程模板英文名称，必输
	 * @param flowTp				流程类型，必输
	 * @param flowTemplateVersion	流程模板版本，必输
	 * @param sndUserId				发起用户ID，默认使用当前登录用户，非必输
	 * @param sndUserName			发起用户名称，默认使用当前登录用户，非必输
	 * @param title					流程名称，必输
	 * @param flowDesc				流程描述，非必输
	 */
	public FlowDO startupFlow(String globalSeq, String flowTemplateEnname, String flowTp, String flowTemplateVersion, String sndUserId, String sndUserName, String title, String flowDesc){
		return startupFlow(globalSeq, flowTemplateEnname, flowTp, flowTemplateVersion, sndUserId, sndUserName, title, flowDesc, null);
	}
	
	/**
	 * 发起流程
	 * @param globalSeq			全局流水号，必输
	 * @param flowTemplateEnname	流程模板英文名称，必输
	 * @param flowTp				流程类型，必输
	 * @param flowTemplateVersion	流程模板版本，必输
	 * @param sndUserId				发起用户ID，默认使用当前登录用户，非必输
	 * @param sndUserName			发起用户名称，默认使用当前登录用户，非必输
	 * @param title					流程名称，必输
	 * @param flowDesc				流程描述，非必输
	 * @param stepCurUserMap		指定审批步骤处理用户列表
	 */
	public FlowDO startupFlow(String globalSeq, String flowTemplateEnname, String flowTp, String flowTemplateVersion, String sndUserId, String sndUserName, String title, String flowDesc, Map<String, List<User>> stepCurUserMap){
		// 检查必输项
		if(DataUtil.isNullStr(globalSeq)){
			throw new BaseException(SysErr.E_DEFAULT, "全局流水号不能空");
		}
		if(DataUtil.isNullStr(flowTemplateEnname)){
			throw new BaseException(SysErr.E_DEFAULT, "流程模板英文名称不能空");
		}
		if(DataUtil.isNullStr(flowTp)){
			throw new BaseException(SysErr.E_DEFAULT, "流程类型不能空");
		}
		if(DataUtil.isNullStr(flowTemplateVersion)){
			throw new BaseException(SysErr.E_DEFAULT, "流程模板版本不能空");
		}
		if(DataUtil.isNullStr(title)){
			throw new BaseException(SysErr.E_DEFAULT, "流程名称不能空");
		}
		log.info("发起流程输入参数：globalSeq["+globalSeq+"] , flowTemplateEnname["+flowTemplateEnname+"] , flowTp["+flowTp+"] , flowTemplateVersion["+flowTemplateVersion+"] , sndUserId["+sndUserId+"] , sndUserName["+sndUserName+"] , title["+title+"] , flowDesc["+flowDesc+"]");
		// 检查对应的流程模板是否存在
		FlowTemplateDO flowTemplateDO = flowTemplateService.get(flowTemplateEnname, flowTp, flowTemplateVersion);
		if(null==flowTemplateDO){
			throw new BaseException(SysErr.E_DEFAULT, "flowTemplateEnname["+flowTemplateEnname+"] , flowTp["+flowTp+"] , flowTemplateVersion["+flowTemplateVersion+"]流程模板不存在");
		}
		FlowDO flowDO = new FlowDO();
		// 设置流程信息
		flowDO.setGlobalSeq(globalSeq);
		flowDO.setFlowTmplId(flowTemplateDO.getId());
		flowDO.setInfoTitle(title);
		flowDO.setFlowDesc(flowDesc);
		flowDO.setFlowStat(FlowDO.STAT_START);
		flowDO.setStepSer(FlowStepTemplateDO.STEP_START);
		flowDO.setSndUserId(sndUserId);
		flowDO.setSndUserName(sndUserName);
		if(null!=stepCurUserMap && !stepCurUserMap.isEmpty()){
			STEP_DEAL_MAP.put(globalSeq, stepCurUserMap);		// 设置程序外部指定步骤处理人员列表，用户生成处理任务列表时所使用
		}
		if(!insert(flowDO)){
			throw new BaseException(SysErr.E_DEFAULT, "发起流程失败");
		}		
		return flowDO;
	}

	/**
	 * 发起流程
	 * @param globalSeq			全局流水号，必输
	 * @param flowTemplateEnname	流程模板英文名称，必输
	 * @param flowTp				流程类型，必输
	 * @param flowTemplateVersion	流程模板版本，必输
	 * @param sndUserId				发起用户ID，默认使用当前登录用户，非必输
	 * @param sndUserName			发起用户名称，默认使用当前登录用户，非必输
	 * @param title					流程名称，必输
	 * @param flowDesc				流程描述，非必输
	 * @param stepCurUserMap		指定审批步骤处理用户列表
	 */
	public FlowDO startupFlowNoTransaction(String globalSeq, String flowTemplateEnname, String flowTp, String flowTemplateVersion, String sndUserId, String sndUserName, String title, String flowDesc, Map<String, List<User>> stepCurUserMap){
		// 检查必输项
		if(DataUtil.isNullStr(globalSeq)){
			throw new BaseException(SysErr.E_DEFAULT, "全局流水号不能空");
		}
		if(DataUtil.isNullStr(flowTemplateEnname)){
			throw new BaseException(SysErr.E_DEFAULT, "流程模板英文名称不能空");
		}
		if(DataUtil.isNullStr(flowTp)){
			throw new BaseException(SysErr.E_DEFAULT, "流程类型不能空");
		}
		if(DataUtil.isNullStr(flowTemplateVersion)){
			throw new BaseException(SysErr.E_DEFAULT, "流程模板版本不能空");
		}
		if(DataUtil.isNullStr(title)){
			throw new BaseException(SysErr.E_DEFAULT, "流程名称不能空");
		}
		log.info("发起流程输入参数：globalSeq["+globalSeq+"] , flowTemplateEnname["+flowTemplateEnname+"] , flowTp["+flowTp+"] , flowTemplateVersion["+flowTemplateVersion+"] , sndUserId["+sndUserId+"] , sndUserName["+sndUserName+"] , title["+title+"] , flowDesc["+flowDesc+"]");
		// 检查对应的流程模板是否存在
		FlowTemplateDO flowTemplateDO = flowTemplateService.get(flowTemplateEnname, flowTp, flowTemplateVersion);
		if(null==flowTemplateDO){
			throw new BaseException(SysErr.E_DEFAULT, "flowTemplateEnname["+flowTemplateEnname+"] , flowTp["+flowTp+"] , flowTemplateVersion["+flowTemplateVersion+"]流程模板不存在");
		}
		FlowDO flowDO = new FlowDO();
		// 设置流程信息
		flowDO.setGlobalSeq(globalSeq);
		flowDO.setFlowTmplId(flowTemplateDO.getId());
		flowDO.setInfoTitle(title);
		flowDO.setFlowDesc(flowDesc);
		flowDO.setFlowStat(FlowDO.STAT_START);
		flowDO.setStepSer(FlowStepTemplateDO.STEP_START);
		flowDO.setSndUserId(sndUserId);
		flowDO.setSndUserName(sndUserName);
		if(null!=stepCurUserMap && !stepCurUserMap.isEmpty()){
			STEP_DEAL_MAP.put(globalSeq, stepCurUserMap);		// 设置程序外部指定步骤处理人员列表，用户生成处理任务列表时所使用
		}
		if(!insertNoTransaction(flowDO)){
			throw new BaseException(SysErr.E_DEFAULT, "发起流程失败");
		}		
		return flowDO;
	}	

	/**
	 * 流程人工审批
	 * @param stepId		当前步骤实例ID
	 * @param stat		审批结果(01-审批通过、02-审批拒绝)
	 * @param appMsg		审批信息
	 */
	public void apply(String stepId, String flowStat, String appMsg) {
		// TODO Auto-generated method stub
		log.info("审批信息: stepId["+stepId+"],flowStat["+flowStat+"],appMsg["+appMsg+"]");
		if(DataUtil.isNullStr(stepId)){
			throw new BaseException(SysErr.E_DEFAULT, "步骤实例ID不能空");
		}
		if(DataUtil.isNullStr(flowStat)){
			throw new BaseException(SysErr.E_DEFAULT, "审批结果不能空[01-审批通过、02-审批拒绝]");
		}
		if(FlowStepDO.STAT_REFUSE_END.equals(flowStat) && DataUtil.isNullStr(appMsg)){
			throw new BaseException(SysErr.E_DEFAULT, "审批拒绝时审批信息不能空");
		}
		
		// 获取对应的步骤实例
		FlowStepDO step = flowStepService.get(stepId);
		if(null==step){
			throw new BaseException(SysErr.E_DEFAULT, "步骤实例["+stepId+"]不存在");
		}
		int stepSer = step.getStepSer();
		// 获取对应的步骤模板
		FlowStepTemplateDO stepTemplate = flowStepTemplateService.get(step.getStepTmplId());
		if(null==stepTemplate){
			throw new BaseException(SysErr.E_DEFAULT, "步骤模板["+step.getStepTmplId()+"]不存在");
		}
		String flowTimeOutFlg = stepTemplate.getFlowTimeOutFlg();
		int nextStepSer = stepTemplate.getNextStepSer();
		String stepEventClass = stepTemplate.getFlowProcClssTp();
		
		// 获取对应的流程实例
		String globalSeq = step.getGlobalSeq();
		String flowTmplId = step.getFlowTmplId();
		FlowDO flow = getByGlobalSeq(globalSeq);
		if(null==flow){
			throw new BaseException(SysErr.E_DEFAULT, "业务流水["+globalSeq+"]对应的流程实例不存在");
		}
		
		// 获取对应的流程模板
		FlowTemplateDO flowTemplate = flowTemplateService.get(flowTmplId);
		if(null==flowTemplate){
			throw new BaseException(SysErr.E_DEFAULT, "业务流水["+globalSeq+"]对应的流程模板不存在");
		}
		String flowEventClass = flowTemplate.getFlowProcClssTp();
		
		// 获取对应的审核步骤状态
		String stepStat =  flowStat;
		if(!DataUtil.isNullStr(step.getTimeOutProcUserId())){
			if(FlowDO.FLOW_INIT_USER.equals(flowTimeOutFlg)){
				// 超时自动审批处理
				if(FlowStepDO.STAT_PASS_END.equals(flowStat)){
					// 04-自动审批通过
					stepStat = FlowStepDO.STAT_AUTO_PASS_END;
				}else{
					// 05-自动审批拒绝
					stepStat = FlowStepDO.STAT_AUTO_REFUSE_END;
				}
			}else{
				// 此审批流程为超时人工处理流程
				if(FlowStepDO.STAT_PASS_END.equals(flowStat)){
					// 06-超时人工处理通过
					stepStat = FlowStepDO.STAT_TIMEOUT_PASS_END;
				}else{
					// 07-超时人工处理拒绝
					stepStat = FlowStepDO.STAT_TIMEOUT_REFUSE_END;
				}
			}
		}
		
		// 获取对应的流程状态
		String stat = FlowDO.STAT_DEAL; // 默认设置为02-处理中
		boolean isFlowEnd = false;
		boolean isStepEnd = flowStepService.chkFlowStepPass(step, stepStat); //检查当前步骤是否符合通过标准
		if((FlowStepTemplateDO.STEP_END==nextStepSer && isStepEnd) || FlowStepDO.STAT_REFUSE_END.equals(flowStat)){
			// 总流程符合结束或审批拒绝则整个流程结束
			if(!DataUtil.isNullStr(step.getTimeOutProcUserId())){
				if(FlowDO.FLOW_INIT_USER.equals(flowTimeOutFlg)){
					// 超时自动审批处理
					if(FlowStepDO.STAT_PASS_END.equals(flowStat)){
						// 04-自动审批通过
						stat = FlowStepDO.STAT_AUTO_PASS_END;
					}else{
						// 05-自动审批拒绝
						stat = FlowStepDO.STAT_AUTO_REFUSE_END;
					}
				}else{
					// 超时人工审批处理
					if(FlowStepDO.STAT_PASS_END.equals(flowStat)){
						// 06-超时人工通过
						stat = FlowStepDO.STAT_TIMEOUT_PASS_END;
					}else{
						// 07-超时人工处理拒绝
						stat = FlowStepDO.STAT_TIMEOUT_REFUSE_END;
					}
				}
			}else{
				// 正常审批处理：02-审批通过、03-审批拒绝
				stat = flowStat;
			}
			isFlowEnd = true;
			if(FlowStepDO.STAT_REFUSE_END.equals(flowStat)){
				// 审批拒绝时下一步骤号还是当前步骤号
				nextStepSer = stepSer;
			}
		}
		// 获取当前日期时间
		String dateTime = DateUtil.getDateTime();
		
		// 开启事务
		IDBSession session = DBSessionFactory.getSession();
		try {
			session.beginTransaction();			
			// 更新流程实例信息状态
			flow.preUpdate();
			flow.setFlowStat(stat);
			// 总流程结束则更新结束时间
			if(isFlowEnd){
				flow.setEndTime(dateTime);
			}
			// 根据当前步骤是否结束更新对应的当前步骤号
			if(isStepEnd){
				flow.setStepSer(nextStepSer);
			}else{
				flow.setStepSer(stepSer);
			}
			flowDao.update(flow);

			// 更新流程步骤信息
			step.setEndTime(dateTime);
			step.setFlowStat(stepStat);
			step.setAppMsg(appMsg);
			flowStepService.update(step);
			
			// 生成下一步待审批流程实例
			if(!isFlowEnd && isStepEnd){
				int stepCount = flowStepService.generateStepInst(flowTmplId, nextStepSer, globalSeq, flow.getInfoTitle(), false, isFlowEnd);
				log.info("生成下一步骤["+nextStepSer+"]的实例数["+stepCount+"]");
			}			
			session.endTransaction();
			
			// 设置入参
			HashMap<String, Object> dataMap = Maps.newHashMap();
			dataMap.put(IFlowEvent.KEY_GLOBAL_SEQ_NO, globalSeq); // 全局流水号
			dataMap.put(IFlowEvent.KEY_FLOW_TEMPLATE_ID, flowTmplId); // 流程模板ID
			dataMap.put(IFlowEvent.KEY_STEP_NO, stepSer); // 当前步骤号
			dataMap.put(IFlowEvent.KEY_FLOW_TITLE, flow.getInfoTitle()); // 流程标题
			dataMap.put(IFlowEvent.KEY_STEP_TITLE, step.getInfoTitle()); // 当前步骤标题
			dataMap.put(IFlowEvent.KEY_APPLY_STAT, flowStat); // 最后步骤处理结果
			dataMap.put(IFlowEvent.KEY_APPLY_MSG, appMsg); // 最后步骤审批信息
			dataMap.put(IFlowEvent.KEY_UP_USER_ID, flow.getSndUserId()); // 流程发起用户ID
			dataMap.put(IFlowEvent.KEY_UP_USER_NAME, flow.getSndUserName()); // 流程发起用户名称
			dataMap.put(IFlowEvent.KEY_DEAL_USER_ID, step.getCurProcUserId()); // 最后审批人ID
			dataMap.put(IFlowEvent.KEY_DEAL_USER_NAME, step.getCurProcUserName()); // 最后审批人中文名
			
			// 执行步骤审批通过结束事件
			IFlowEvent stepEvent = null;
			if(!DataUtil.isNullStr(stepEventClass)){
				try{
					stepEvent = (IFlowEvent)Class.forName(stepEventClass).newInstance();
				}catch(Exception e){
					log.warn("步骤处理事件类["+stepEventClass+"]不存在");
				}
			}
			/*20200406 add by chenyl for 新增步骤审批结束且审批通过的才执行对应的通过后事件*/
			if(isStepEnd && FlowStepDO.STAT_PASS_END.equals(flowStat) && null!=stepEvent){
				try{
					stepEvent.passEvent(dataMap);
				}catch(Exception e){
					log.error("业务流水["+globalSeq+"],执行步骤审批通过后事件失败");
				}
			}			
			// 执行步骤审批拒绝结束事件
			if(FlowStepDO.STAT_REFUSE_END.equals(flowStat) && null!=stepEvent){
				try{
					stepEvent.refuseEvent(dataMap);
				}catch(Exception e){
					log.error("业务流水["+globalSeq+"],执行步骤审批拒绝后事件失败");
				}
			}
			
			// 执行流程通过后事件
			IFlowEvent flowEvent = null;
			if(!DataUtil.isNullStr(flowEventClass)){
				try{
					flowEvent = (IFlowEvent)Class.forName(flowEventClass).newInstance();
				}catch(Exception e){
					log.warn("流程处理事件类["+flowEventClass+"]不存在");
				}
			}
			/*20200406 add by chenyl for 新增整个流程结束且审批通过的才执行对应的通过后事件*/
			if(isFlowEnd && FlowStepDO.STAT_PASS_END.equals(flowStat) && null!=flowEvent ){
				try{
					flowEvent.passEvent(dataMap);
				}catch(Exception e){
					log.error("业务流水["+globalSeq+"],执行流程通过后事件失败");
				}
			}
			
			// 执行流程拒绝后事件
			if(FlowStepDO.STAT_REFUSE_END.equals(flowStat) && null!=flowEvent){
				try{
					flowEvent.refuseEvent(dataMap);
				}catch(Exception e){
					log.error("业务流水["+globalSeq+"],执行流程拒绝结束事件失败");
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			try {
				session.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_DEFAULT, e, "审批流程[" + stepId + "]数据库事务失败");
		}
	}

	/**
	 * 根据业务流水更新流程实例的步骤号
	 * @param globalSeq
	 * @param stepSer
	 */
	public void updateStepSerByGlobalSeq(String globalSeq, int stepSer) {
		// TODO Auto-generated method stub
		flowDao.updateStepSerByGlobalSeq(globalSeq, stepSer);
	}

	/**
	 * 更新流程超时
	 * @param updFlowObj
	 */
	public void updateTimeOut(FlowDO updFlowObj) {
		// TODO Auto-generated method stub
		flowDao.updateTimeOut(updFlowObj);
	}

    public List<Map<String, Object>> getLatestFlowInfo() {
        List<Map<String, Object>> maps = Lists.newArrayList();
        HashMap<String, Object> retMap = flowDao.listByAuth(new FlowDO(), 1, 1);
        List<FlowDO> list = (List<FlowDO>)retMap.get("LIST");
        if(list.size() > 0){
            //step1-流程发起步骤信息
            Map<String, Object> map = Maps.newHashMap();
            map.put("name", list.get(0).getSndUserName());
            map.put("begTime", list.get(0).getStrTime());
            map.put("endTime", list.get(0).getEndTime());
            map.put("stat", "04");
            maps.add(map);
            //step2-当前流程处理人步骤信息
            FlowStepDO flowStep = new FlowStepDO();
            flowStep.setGlobalSeq(list.get(0).getGlobalSeq());
            flowStep.setFlowTmplId(list.get(0).getFlowTmplId());
            if(list.get(0).getStepSer() == 0){
                flowStep.setStepSer(1);
            }else{
                flowStep.setStepSer(list.get(0).getStepSer());
            }
            List<FlowStepDO> currentList = flowStepDao.getFlowStep(flowStep);
            if(currentList.size() > 0){
				String name = "";
				for(int i=0;i<currentList.size();i++){
					name += currentList.get(i).getCurProcUserName()+",";
				}
                map = Maps.newHashMap();
                map.put("name", name);
                map.put("begTime", currentList.get(0).getStrTime());
                map.put("endTime", currentList.get(0).getEndTime());
                map.put("stat", currentList.get(0).getFlowStat());
                maps.add(map);
            }
            ////step3-当前流程结束步骤信息
            FlowStepDO endFlowStep = new FlowStepDO();
            endFlowStep.setGlobalSeq(list.get(0).getGlobalSeq());
            endFlowStep.setFlowTmplId(list.get(0).getFlowTmplId());
            List<FlowStepDO> endtList = flowStepDao.getEndFlowStep(endFlowStep);
            if(endtList.size() > 0){
				String name = "";
				for(int i=0;i<endtList.size();i++){
					name += endtList.get(i).getCurProcUserName()+",";
				}
            	if(currentList.size() == 0){
            		//流程完成，没有当前处理人，把最后处理人作为当前处理人节点数据
                    map = Maps.newHashMap();
                    map.put("name", name);
                    map.put("begTime", list.get(0).getStrTime());
                    map.put("endTime", list.get(0).getEndTime());
                    map.put("stat", list.get(0).getFlowStat());
                    maps.add(map);
            	}
                map = Maps.newHashMap();
                map.put("name", name);
                map.put("begTime", list.get(0).getStrTime());
                map.put("endTime", list.get(0).getEndTime());
                map.put("stat", list.get(0).getFlowStat());
                maps.add(map);
            }
        }
        return maps;
    }

	public int updateGlobalSeqById(String globalSeq, FlowDO flowDO) {
		flowDO.setGlobalSeq(globalSeq);
		return flowDao.update(flowDO);
	}

	public void appendDesc(FlowDO obj) {
		// TODO Auto-generated method stub
		String id = obj.getId();
		if(DataUtil.isNullStr(id)){
			throw new BaseException(SysErr.E_MESSAGE, "流程实例ID为空");
		}
		String flowDesc = obj.getFlowDesc();
		flowDao.appendDesc(id, flowDesc);
	}
}
