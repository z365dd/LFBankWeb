package com.adtec.para.rules.service;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.impl.share.dataset.CommonDatasets;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.framework.interfaces.share.IDatasets;
import com.adtec.para.cfgcenter.service.ParaCfgCenterService;
import com.adtec.para.common.constants.ParaConst;
import com.adtec.para.common.util.ParaZkClusterUtil;
import com.adtec.para.common.util.ParaZkNode;
import com.adtec.para.rules.dao.ParaRulesStgAuthDao;
import com.adtec.para.rules.dao.ParaRulesStgColDao;
import com.adtec.para.rules.dao.ParaRulesStgDao;
import com.adtec.para.rules.entity.ParaRulesStgAuthDO;
import com.adtec.para.rules.entity.ParaRulesStgColDO;
import com.adtec.para.rules.entity.ParaRulesStgDO;
import com.adtec.sys.modules.sys.dao.RentDao;
import com.adtec.sys.modules.sys.entity.Rent;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ParaRulesStgService {
	
	@Autowired
	private ParaRulesStgDao rulesStgDao;
	@Autowired
	private ParaRulesStgColDao rulesStgColDao;
	@Autowired
	private ParaRulesStgAuthDao rulesStgAuthDao;
	@Autowired
	private RentDao rentDao;
	@Autowired
	private CuratorFramework configClient;
	@Autowired
	private ParaCfgCenterService cfgCenterService;
	
	/**
	 * 缓存中心列表查询
	 * @param reqDs
	 * @return
	 */
	public IDataset listByPage(IDataset reqDs) {
		int start = reqDs.getInt("start");
		int limit = reqDs.getInt("pageSize");
		ParaRulesStgDO rulesStgDO = DatasetService.getInstace().getObject(reqDs, ParaRulesStgDO.class);
		List<ParaRulesStgDO> list = rulesStgDao.listByPage(rulesStgDO, start, limit);
		int total = rulesStgDao.getTotal(rulesStgDO);
		IDataset dataset = DatasetService.getInstace().getDataset(list, ParaRulesStgDO.class);
		dataset.setTotalCount(total);
		chgDict(dataset);
		return dataset;
	}
	
	/**
	 * 存储规则新增
	 * @param reqDs
	 * @return
	 */
	public void insert(IDataset reqDs) {
		ParaRulesStgDO rulesStgDO = DatasetService.getInstace().getObject(reqDs, ParaRulesStgDO.class);
		String tenant = UserUtils.getUser().getRent().getEngName();
		rulesStgDO.setUseTntNo(tenant);
		if (ParaConst.StorgRuleTp.STORG_RULE_TP_PARAM.equals(rulesStgDO.getStorgRuleTp())) {
			rulesStgDO.setReadAuthLvl(ParaConst.ReadAuthLvl.AUTH_PUBLIC);
		} else if (ParaConst.StorgRuleTp.STORG_RULE_TP_CACHE.equals(rulesStgDO.getStorgRuleTp())) {
			rulesStgDO.setReadAuthLvl("");
		}
		rulesStgDO.setDelFlg(ParaConst.DelFlg.NODEL);
		rulesStgDO.setSyncFlg(ParaConst.SyncFlg.UNSYNC);
		String colJson = reqDs.getString("colJson");
		List<ParaRulesStgColDO> stgColDOList = Lists.newArrayList();
		stgColDOList = JSON.parseArray(colJson, ParaRulesStgColDO.class);
		IDBSession session = DBSessionFactory.getSession();
		try {
			session.beginTransaction();
			rulesStgDO.preInsert();

			int rs = rulesStgDao.insert(rulesStgDO);
			if (rs == 1) {
				String stgId = rulesStgDO.getId();
				if (stgColDOList != null && stgColDOList.size() != 0) {
					for (ParaRulesStgColDO stgColDO : stgColDOList) {
//						stgColDO.setStgId(stgId);
						stgColDO.setRuleId(stgId);
						if (ParaConst.DataTp.DATA_TP_OBJECT.equals(stgColDO.getDataTp())) {
							stgColDO.setColLen(" ");
						}
						stgColDO.preInsert();
						rs = rulesStgColDao.insert(stgColDO);
						if (rs != 1) {
							session.rollback();
							throw new BaseException(SysErr.E_MESSAGE, "存储规则字段参新增异常");
						}
					}
				} else {
					session.rollback();
					throw new BaseException(SysErr.E_MESSAGE, "存储规则字段参数为空");
				}
				
			} else {
				session.rollback();
				throw new BaseException(SysErr.E_MESSAGE, "新增存储规则失败，rs="+rs);
			}
		} catch (Exception e) {
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_MESSAGE, e.getMessage());
		}finally {
			try {
				session.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 存储规则详情
	 * @param reqDs
	 * @return
	 */
	public IDatasets get(String id) {
		IDatasets resDss = new CommonDatasets();
		ParaRulesStgDO stgDO = rulesStgDao.get(id);
		List<ParaRulesStgColDO> stgColList = rulesStgColDao.getListByStgId(id);
		List<ParaRulesStgAuthDO> stgAuthList = rulesStgAuthDao.getListByStgId(id);
		StringBuffer readListStr = new StringBuffer();
		StringBuffer writeListStr = new StringBuffer();
		for (ParaRulesStgAuthDO item : stgAuthList) {
			if (ParaConst.ReadAuthTp.AUTH_READ.equals(item.getRuleAuthTp())) {
				readListStr.append(item.getUseTntNo());
				if (!DataUtil.isNullStr(item.getMembNo())){
					readListStr.append("$"+item.getMembNo());
				}
				readListStr.append(";");
			}else if (ParaConst.ReadAuthTp.AUTH_WRITE.equals(item.getRuleAuthTp())) {
				writeListStr.append(item.getUseTntNo()+"_"+item.getMembNo());
				writeListStr.append(";");
			}
		}
		stgDO.setReadListStr(readListStr.toString());
		stgDO.setWriteListStr(writeListStr.toString());
		IDataset stgDs = DatasetService.getInstace().getDataset(stgDO, ParaRulesStgDO.class);
		stgDs.setDatasetName("stgDs");
		IDataset stgColDs = DatasetService.getInstace().getDataset(stgColList, ParaRulesStgColDO.class);
		stgColDs.setDatasetName("stgColDs");
		stgColDs.addColumn("action");
		resDss.putDataset(stgDs);
		resDss.putDataset(stgColDs);
		return resDss;
	}
	
	/**
	 * 存储规则更新
	 * @param reqDs
	 */
	public void update(IDataset reqDs) {
		ParaRulesStgDO rulesStgDO = DatasetService.getInstace().getObject(reqDs, ParaRulesStgDO.class);
		String colJson = reqDs.getString("colJson");
		List<ParaRulesStgColDO> stgColDOList = Lists.newArrayList();
		stgColDOList = JSON.parseArray(colJson, ParaRulesStgColDO.class);
		IDBSession session = DBSessionFactory.getSession();
		try {
			session.beginTransaction();
			rulesStgDO.preUpdate();
//			rulesStgDO.setRemark1(NOSYNC);
			rulesStgDO.setSyncFlg(ParaConst.SyncFlg.UNSYNC);
			rulesStgDO.setDelFlg(ParaConst.DelFlg.NODEL);
			int rs = rulesStgDao.update(rulesStgDO);
			if (rs == 1) {
				String stgId = rulesStgDO.getId();
				rulesStgColDao.deleteByStgId(stgId);
				if (stgColDOList != null && stgColDOList.size() != 0) {
					for (ParaRulesStgColDO stgColDO : stgColDOList) {
						stgColDO.setRuleId(stgId);
						if (ParaConst.DataTp.DATA_TP_OBJECT.equals(stgColDO.getDataTp())) {
							stgColDO.setColLen(" ");
						}
						stgColDO.preInsert();
						rs = rulesStgColDao.insert(stgColDO);
						if (rs != 1) {
							session.rollback();
							throw new BaseException(SysErr.E_MESSAGE, "存储规则字段参修改异常");
						}
					}
				} else {
					session.rollback();
					throw new BaseException(SysErr.E_MESSAGE, "存储规则字段参数为空");
				}
				
			} else {
				session.rollback();
				throw new BaseException(SysErr.E_MESSAGE, "新增存储规则失败，rs="+rs);
			}
		} catch (Exception e) {
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_MESSAGE, e.getMessage());
		}finally {
			try {
				session.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 存储规则授权
	 * @param reqDs
	 */
	public void grant(IDataset reqDs) {
		ParaRulesStgDO rulesStgDO = DatasetService.getInstace().getObject(reqDs, ParaRulesStgDO.class);
		String readListStr = reqDs.getString("readListStr");
		String writeListStr = reqDs.getString("writeListStr");
		String ruleTp = rulesStgDO.getStorgRuleTp();
		String stgId = rulesStgDO.getId();
		String level = rulesStgDO.getReadAuthLvl();
		rulesStgDO.setDelFlg("N");
		rulesStgDO.setSyncFlg("N");
		if (ParaConst.StorgRuleTp.STORG_RULE_TP_PARAM.equals(ruleTp)) {
			rulesStgDO.setReadAuthLvl(level);
		} else if (ParaConst.StorgRuleTp.STORG_RULE_TP_CACHE.equals(ruleTp)) {
			rulesStgDO.setReadAuthLvl("");
		}

		IDBSession session = DBSessionFactory.getSession();
		try {
			session.beginTransaction();
			rulesStgDO.preUpdate();
			int rs = rulesStgDao.update(rulesStgDO);
			if (rs == 1) {
				rulesStgAuthDao.deleteByStgId(stgId);
				// 如果读取权限为公共，则跳过读取权限的分配
				if (!ParaConst.ReadAuthLvl.AUTH_PUBLIC.equals(rulesStgDO.getReadAuthLvl())) {
//					if (AUTH_USER_TENANT.equals(rulesStgDO.getReadAuthLvl())) {
//						String auth = UserUtils.getUser().getRent().getEngName();
//						RulesStgAuthDO stgAuthDO = new RulesStgAuthDO(stgId, "0", auth);
//						stgAuthDO.preInsert();
//						rulesStgAuthDao.insert(stgAuthDO);
//					}
					if (null != readListStr && !DataUtil.isNullStr(readListStr)){
						String[] readAuths = readListStr.split(";");
						for (String auth : readAuths) {
							ParaRulesStgAuthDO stgAuthDO = new ParaRulesStgAuthDO(stgId, ParaConst.ReadAuthTp.AUTH_READ, auth, " "); // 租户读取
							if (ParaConst.StorgRuleTp.STORG_RULE_TP_CACHE.equals(ruleTp)){
//								String[] tmp = auth.split("$");
								String tntNo = reqDs.getString("tntNolist");
								String membNo = reqDs.getString("partList");
								stgAuthDO = new ParaRulesStgAuthDO(stgId, ParaConst.ReadAuthTp.AUTH_READ, tntNo, membNo); // 参与者读取
							}
							stgAuthDO.preInsert();
							rulesStgAuthDao.insert(stgAuthDO);
						}
					}
				}
				if (null != writeListStr && !DataUtil.isNullStr(writeListStr)) {
					String[] writeAuths = writeListStr.split(";");
					for (String auth : writeAuths) {
						String[] tmp = auth.split("_");
						ParaRulesStgAuthDO stgWirteAuthDO = new ParaRulesStgAuthDO(stgId, ParaConst.ReadAuthTp.AUTH_WRITE, tmp[0], tmp[1]); // 参与者写入
						ParaRulesStgAuthDO temp = new ParaRulesStgAuthDO(stgId, ParaConst.ReadAuthTp.AUTH_READ, tmp[0], tmp[1]); // 参与者读取
						ParaRulesStgAuthDO stgReadAuthDO = rulesStgAuthDao.getAuthByUK(temp);
						if (stgReadAuthDO == null) {
							temp.preInsert();
							rulesStgAuthDao.insert(temp);
						}
						stgWirteAuthDO.preInsert();
						rulesStgAuthDao.insert(stgWirteAuthDO);
					}
				}
			} else {
				throw new BaseException(SysErr.E_MESSAGE, "存储规则授权失败，rs="+rs);
			}
			rulesStgDao.updateSyncFlgByStgId(stgId, ParaConst.SyncFlg.UNSYNC);
			session.endTransaction();
		} catch (Exception e) {
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_MESSAGE, e.getMessage());
		}
	}
	
	/**
	 * 根据存储规则英文名称判断是否已存在
	 * @param reqDs
	 * @return
	 */
	public int getStg(IDataset reqDs) {
		ParaRulesStgDO rulesStgDO = DatasetService.getInstace().getObject(reqDs, ParaRulesStgDO.class);
		ParaRulesStgDO rs = rulesStgDao.getRulesByEngName(rulesStgDO);
		int num = 0;
		if (rs != null) {
			num = 1;
		}
		return num;
	}
	
	/**
	 * 停用存储规则
	 * @param reqDs
	 */
	public void stop(IDataset reqDs) {
		String stgId = reqDs.getString("id");
//		RulesStgDO rulesStgDO = rulesStgDao.get(stgId);
//		rulesStgDO.preUpdate();
//		rulesStgDO.setSyncFlg(Const.SyncFlg.UNSYNC);
//		rulesStgDao.update(rulesStgDO);
		cfgCenterService.syncStgs(stgId, "stop", ParaConst.SyncFlg.UNSYNC);
	}
	
	/**
	 * 删除存储规则
	 * @param reqDs
	 */
	public void delStg(IDataset reqDs) {
		ParaRulesStgDO rulesStgDO = DatasetService.getInstace().getObject(reqDs, ParaRulesStgDO.class);
		String stgId = rulesStgDO.getId();
		String enname = rulesStgDO.getEngName();
		String path = ParaZkNode.STORAGE+"/"+ enname;
		String stgData = ParaZkClusterUtil.getNodeData(configClient, path);
		IDBSession session = DBSessionFactory.getSession();
		try {
			session.beginTransaction();
			ParaZkClusterUtil.zkDelNode(configClient, path);
			rulesStgColDao.deleteByStgId(stgId);
			rulesStgAuthDao.deleteByStgId(stgId);
			rulesStgDao.deleteStgById(stgId);
		} catch (SQLException e) {
			try {
				session.rollback();
				ParaZkClusterUtil.zkAddNode(configClient, path, stgData);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_MESSAGE, e.getMessage());
		} finally {
			try {
				session.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 从zk获取类型
	 * @return
	 */
//	public IDataset getTypes() {
////		List<String> childList = ZkClusterUtil.getChildNode(configClient, ZkNode.COL_TYPE);
//		String colType = ParamUtil.getString("colType");
//		String[] types = colType.split(",");
//		List<RulesStgColDO> list = Lists.newArrayList();
//		for (String type : types) {
//			RulesStgColDO rulesStgColDO = new RulesStgColDO();
//			rulesStgColDO.setType(type);
//			list.add(rulesStgColDO);
//		}
//		IDataset dataset = DatasetService.getInstace().getDataset(list, RulesStgColDO.class);
//		return dataset;
//	}
	
	/**
	 * 列表字段转换
	 * @param dataset
	 */
	private void chgDict(IDataset dataset) {
		if (null == dataset) {
			return;
		}
		List<String> rents = Lists.newArrayList();
		User user = UserUtils.getUser();
		rents.add(user.getRent().getEngName());
		rents.addAll(findChildRentsByRentEnname(rents, user.getRent().getEngName()));
		
		dataset.addColumn("action");
		dataset.addColumn("readAuthLvl_str");
		dataset.addColumn("syncFlg_str");
		dataset.addColumn("storgRuleTp_str");
		dataset.beforeFirst();
		while(dataset.hasNext()){
			dataset.next();
			String level = dataset.getString("readAuthLvl");
			if (ParaConst.ReadAuthLvl.AUTH_PUBLIC.equals(level)) {
				dataset.updateString("readAuthLvl_str", "公共");
//			}else if (Const.ReadAuthLvl.AUTH_USER_TENANT.equals(level)){
//				dataset.updateString("ReadAuthLvl_str", "本租户可用");
			}else if (ParaConst.ReadAuthLvl.AUTH_TENANT.equals(level)) {
				dataset.updateString("readAuthLvl_str", "租户");
			}else if (ParaConst.ReadAuthLvl.AUTH_PART.equals(level)) {
				dataset.updateString("readAuthLvl_str", "参与者");
			}
			
			StringBuilder action = new StringBuilder();
			action.append("	<a href=\"JavaScript:void(0);\" onClick=\"detail('" + dataset.getString("id") + "')\" >详情</a>");
			String sync = dataset.getString("syncFlg");
			String tenant = dataset.getString("tenant");
			if (ParaConst.SyncFlg.UNSYNC.equals(sync)){
				dataset.updateString("syncFlg_str", "未同步");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"update('" + dataset.getString("id") + "')\" >修改</a>");
//				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"grant('" + dataset.getString("id") + "')\" >授权</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"delStg(this)\" >删除</a>");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"syncStgs('" + dataset.getString("id") + "')\" >同步</a>");
			}else if (ParaConst.SyncFlg.SYNC.equals(sync)){
				dataset.updateString("syncFlg_str", "已同步");
				action.append("	<a href=\"JavaScript:void(0);\" onClick=\"stop('" + dataset.getString("id") + "')\" >停用</a>");
			}
			String storgRuleTp = dataset.getString("storgRuleTp");
			if (storgRuleTp.equals(ParaConst.StorgRuleTp.STORG_RULE_TP_PARAM)) {
				dataset.updateString("storgRuleTp_str", "参数规则");
			} else if (storgRuleTp.equals(ParaConst.StorgRuleTp.STORG_RULE_TP_CACHE)) {
				dataset.updateString("storgRuleTp_str", "缓存规则");
				dataset.updateString("readAuthLvl_str", "无");
			}
			dataset.updateString("action", action.toString());
		}
	}
	
	/**
	 * 查询租户下所有下级租户list 递归算法
	 * @param rents 租户英文名集合，用于传递结果递归
	 * @param rentEnname 租户英文名
	 * @return
	 */
	public List<String> findChildRentsByRentEnname(List<String> rents, String rentEnname) {
		List<String> list = getChildRents(rentEnname);
		if(list.size() > 0) {
			rents.addAll(list);
		}
		for (String rent : list) {
			findChildRentsByRentEnname(rents, rent);
		}
		return rents;
	}
	
	/**
	 * 查询当前租户下的子租户
	 * 
	 * @param rentEnname
	 *            租户英文名称
	 * @return
	 */
	private List<String> getChildRents(String rentEnname) {
		Rent rent = new Rent();
		rent.setEngName(rentEnname);
		rent = rentDao.getByEngName(rent);
		List<String> list = Lists.newArrayList();
		List<Rent> childRentList = rentDao.findChildRentList(rent);
		for (Rent r : childRentList) {
			list.add(r.getEngName());
		}
		return list;
	}

}
