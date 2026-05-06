package com.adtec.prod.oper.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.adtec.framework.common.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.ms.msagent.util.Encodes;
import com.adtec.prod.dto.FActQryAcctInfoReqDTO;
import com.adtec.prod.dto.FActQryAcctInfoResDTO;
import com.adtec.prod.dto.FPRodEntrRegRedisParaReqDTO;
import com.adtec.prod.oper.dao.BrchDao;
import com.adtec.prod.oper.dao.BusiDemoDao;
import com.adtec.prod.oper.dao.ChnlDao;
import com.adtec.prod.oper.dao.EntrDemoDao;
import com.adtec.prod.oper.dao.TPipBusiBrchOpenDao;
import com.adtec.prod.oper.dao.TPipBusiChnlOpenDao;
import com.adtec.prod.oper.dao.TPipBusiDao;
import com.adtec.prod.oper.dao.TPipBusiDocDao;
import com.adtec.prod.oper.dao.TPipBusiParaDao;
import com.adtec.prod.oper.dao.TPipChkRuleDao;
import com.adtec.prod.oper.dao.TPipClrMertAcctDao;
import com.adtec.prod.oper.dao.TPipClrMertFeeCaltDao;
import com.adtec.prod.oper.dao.TPipClrMertFeeRuleDao;
import com.adtec.prod.oper.dao.TPipClrMertRuleDao;
import com.adtec.prod.oper.dao.TPipRuleRelatDao;
import com.adtec.prod.oper.dao.TPipSignRuleDao;
import com.adtec.prod.oper.definition.saleprod.dao.TPipSaleProdDao;
import com.adtec.prod.oper.definition.saleprod.entity.TPipSaleProdDO;
import com.adtec.prod.oper.entity.BrchDO;
import com.adtec.prod.oper.entity.ChnlDO;
import com.adtec.prod.oper.entity.DictDO;
import com.adtec.prod.oper.entity.EntrDemoDO;
import com.adtec.prod.oper.entity.FPRodEntrQryBusiEntrNoListListDTO;
import com.adtec.prod.oper.entity.TPipBusiBrchOpenDO;
import com.adtec.prod.oper.entity.TPipBusiChnlOpenDO;
import com.adtec.prod.oper.entity.TPipBusiDO;
import com.adtec.prod.oper.entity.TPipBusiDocDO;
import com.adtec.prod.oper.entity.TPipBusiParaDO;
import com.adtec.prod.oper.entity.TPipBusiParaDOTemp;
import com.adtec.prod.oper.entity.TPipChkRuleDO;
import com.adtec.prod.oper.entity.TPipClrMertAcctDO;
import com.adtec.prod.oper.entity.TPipClrMertFeeCaltDO;
import com.adtec.prod.oper.entity.TPipClrMertFeeRuleDO;
import com.adtec.prod.oper.entity.TPipClrMertRuleDO;
import com.adtec.prod.oper.entity.TPipCompSvcParaDOTemp;
import com.adtec.prod.oper.entity.TPipRuleRelatDO;
import com.adtec.prod.oper.entity.TPipSignRuleDO;
import com.adtec.prod.util.ProdUtil;
import com.adtec.sys.modules.sys.dao.OfficeDao;
import com.adtec.sys.modules.sys.dao.UserDao;
import com.adtec.sys.modules.sys.entity.Office;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.utils.DictUtils;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
@Service
public class BusiDemoService {
	@Autowired
	private BusiDemoDao busiDemoDao;
	@Autowired
	private TPipBusiDao tPipBusiDao;
	@Autowired
	private TPipBusiDocDao tPipBusiDocDao;
	@Autowired
	private TPipBusiParaDao tPipBusiParaDao;
	@Autowired
	private EntrDemoDao entrDemoDao;
	@Autowired
	private TPipRuleRelatDao tPipRuleRelatDao;
	@Autowired
	private TPipChkRuleDao tPipChkRuleDao;
	@Autowired
	private TPipSignRuleDao tPipSignRuleDao;
	@Autowired
	private TPipClrMertRuleDao tPipClrRuleDao;
	@Autowired
	private TPipClrMertAcctDao tPipClrMertAcctDao;
	@Autowired
	private TPipClrMertFeeRuleDao tPipClrMertFeeRuleDao;
	@Autowired
	private TPipClrMertFeeCaltDao tPipClrMertFeeCaltDao;
	@Autowired
	private TPipSaleProdDao tPipSaleProdDao;
	@Autowired
	private TPipBusiBrchOpenDao tPipBusiBrchOpenDao;
	@Autowired
	private TPipBusiChnlOpenDao tPipBusiChnlOpenDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private OfficeDao officeDao;

	/*以下为20200117代码*/
	/**
	 *查询操作
	 * */
	public IDataset qry(TPipBusiDO tPipBusiDO, int start, int limit) {
		List<TPipBusiDO> busiList = tPipBusiDao.list(tPipBusiDO, start, limit);
		int total = tPipBusiDao.getTotal(tPipBusiDO);
		actionAndStat(busiList);
		IDataset responseData = DatasetService.getInstace().getDataset(busiList,TPipBusiDO.class);
		responseData.setTotalCount(total);
		return responseData;
	}
	
	/**
	 *查询操作
	 * */
	public List<TPipBusiDO> qryHaveFeeClr(TPipBusiDO tPipBusiDO, int start, int limit) {
		List<TPipBusiDO> busiList = tPipBusiDao.listHaveFeeClr(tPipBusiDO, start, limit);
		return busiList;
	}

	/**
	 *查询操作
	 * */
	public List<TPipBusiDO> qryForMsmall(TPipBusiDO tPipBusiDO, int start, int limit) {
		List<TPipBusiDO> busiList = tPipBusiDao.listForMsmall(tPipBusiDO, start, limit);
		List<User> userList = userDao.findAllList();
		List<Office> officeList = officeDao.findAllList();
		List<TPipSaleProdDO> tPipSaleProdDOList = tPipSaleProdDao.list(new TPipSaleProdDO());
		for (TPipBusiDO temp : busiList) {
			for (User user : userList) {
				if (user.getId().equals(temp.getCrtr())) {
					temp.setOfficeCode(user.getOffice().getBrchCode());
					temp.setOfficeName(user.getOffice().getName());
					break;
				}
			}

			for (TPipSaleProdDO tPipSaleProdDO : tPipSaleProdDOList) {
				if (tPipSaleProdDO.getSaleProdCode().equals(temp.getSaleProdCode())) {
					for (Office office : officeList) {
						if (tPipSaleProdDO.getBrchId() != null && office.getId().equals(tPipSaleProdDO.getBrchId())) {
							// 业务对应的可售产品的机构类型和机构名称（主管机构）
							temp.setBrchId(office.getId());
							temp.setBrchTp(office.getBrchTp());
							temp.setBrchName(office.getName());
							break;
						}
					}
					break;
				}
			}

		}
		return busiList;
	}

	/**
	 *查询操作
	 * */
	public List<TPipBusiDO> qryCompSvcForMsmall(TPipBusiDO tPipBusiDO, int start, int limit) {
		List<TPipBusiDO> busiList = tPipBusiDao.qryCompSvcForMsmall(tPipBusiDO, start, limit);
		return busiList;
	}

	/**
	 *查询操作
	 * */
	public List<TPipBusiDO> qryHasFileForMsmall(TPipBusiDO tPipBusiDO, int start, int limit) {
		List<TPipBusiDO> busiList = tPipBusiDao.listHasFileForMsmall(tPipBusiDO, start, limit);
		List<User> userList = userDao.findAllList();
		for (TPipBusiDO temp : busiList) {
			String brchName = DictUtils.getDictLabel(temp.getBrchTp(), "BRCH_TP", "");
			temp.setBrchName(brchName);

			for (User user : userList) {
				if (user.getId().equals(temp.getCrtr())) {
					temp.setOfficeCode(user.getOffice().getBrchCode());
					temp.setOfficeName(user.getOffice().getName());
					break;
				}
			}
		}
		return busiList;
	}

	public int getTotalForMsmall(TPipBusiDO tPipBusiDO) {
		return tPipBusiDao.getTotalForMsmall(tPipBusiDO);
	}

	public int getTotalCompSvcForMsmall(TPipBusiDO tPipBusiDO) {
		return tPipBusiDao.getTotalCompSvcForMsmall(tPipBusiDO);
	}

	public int getTotalHasFileForMsmall(TPipBusiDO tPipBusiDO) {
		return tPipBusiDao.getTotalHasFileForMsmall(tPipBusiDO);
	}

	public int getTotalAssetsForMsmall(String areaId, String type) {
		return tPipBusiDao.getTotalAssetsForMsmall(areaId, type);
	}

	/**
	 *查询操作
	 * */
	public IDataset qryParaForMsmall(TPipBusiParaDO tPipBusiParaDO, int start, int limit) {
		List<TPipBusiParaDO> paraList = tPipBusiParaDao.list(tPipBusiParaDO, start, limit);

		// 把属性名称显示出来
		for (TPipBusiParaDO temp : paraList) {
			if (StringUtil.isEmpty(temp.getElemKv()) || StringUtil.isEmpty(temp.getKv())) {
				continue;
			}
			StringBuffer kvNew = new StringBuffer();
			String[] kvs = temp.getKv().split("\\|");
 			String[] strs = temp.getElemKv().split(";@;");
			for (String str : strs) {
				String[] valKey = str.split("!@#");
				for (String kv : kvs) {
					if (valKey[1].equals(kv)) {
						kvNew.append("|").append(valKey[1]).append("-").append(valKey[0]);
					}
				}
			}
			temp.setKv(kvNew.substring(1).toString());
		}
		int total = tPipBusiParaDao.getTotal(tPipBusiParaDO);
		IDataset responseData = DatasetService.getInstace().getDataset(paraList,TPipBusiParaDO.class);
		responseData.setTotalCount(total);
		return responseData;
	}

	/**
	 *查询操作
	 * */
	public List<TPipBusiDO> qryFileForMsmall(TPipBusiDO tPipBusiDO, int start, int limit) {
		List<TPipBusiDO> busiList = tPipBusiDao.listFile(tPipBusiDO, start, limit);
		for (TPipBusiDO temp : busiList) {
			temp.setFileSuffix(FileUtil.getFileType(temp.getFileName()));
			temp.setPrevPath(FileUtil.getPreviewFilePath(ParamUtil.getUploadFile()+temp.getUrl().substring(9), false));
		}
		return busiList;
	}

	public int getFileTotal(TPipBusiDO tPipBusiDO) {
		return tPipBusiDao.getFileTotal(tPipBusiDO);
	}

	private List<TPipBusiDO> actionAndStat(List<TPipBusiDO> busiList){
		if(null!=busiList && busiList.size()>0){
			for(TPipBusiDO tPipBusiDO:busiList){
				StringBuilder action = new StringBuilder();
				action.append("<a href=\"#JavaScript:void(0);\" onClick=\"Detail('" + tPipBusiDO.getBusiNo() + "')\">详细</a> ")
						.append("<a href=\"#JavaScript:void(0);\" onClick=\"Revice('" + tPipBusiDO.getBusiNo() + "')\">修改</a> ")
						.append("<a href=\"#JavaScript:void(0);\" onClick=\"Delete('" + tPipBusiDO.getBusiNo() + "', '" + tPipBusiDO.getOpenStat() + "')\">删除</a> ");
				if("Y".equals(tPipBusiDO.getOpenStat())){
					action.append("<a href=\"#JavaScript:void(0);\" onClick=\"Stopping('" + tPipBusiDO.getBusiNo() + "')\">关闭</a> ");
					tPipBusiDO.setOpenStat("启用");
				}else{
					action.append("<a href=\"#JavaScript:void(0);\" onClick=\"Enable('" + tPipBusiDO.getBusiNo() + "')\">启用</a> ");
					tPipBusiDO.setOpenStat("关闭");
				}
				tPipBusiDO.setAction(action.toString());
				EntrDemoDO entr = new EntrDemoDO();
				entr = entrDemoDao.get(tPipBusiDO.getEntrNo());
				if(entr!=null){
					tPipBusiDO.setEntrName(entr.getEntrName());
				}
			}
		}
		
		return busiList;
	}

	/**
	 *获取详细数据
	 * */
	public IDataset getDetail(TPipBusiDO tPipBusiDO) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		
		//查询文件信息
		TPipBusiDO rsDO = tPipBusiDao.get(tPipBusiDO);
		// 获取清算机构名称
		if(officeDao.get(rsDO.getBrchId())!=null) {
			rsDO.setBrchIdName(officeDao.get(rsDO.getBrchId()).getName());
		}
		//获取单位名称
		String entrNo = rsDO.getEntrNo();
		if (!DataUtil.isNullStr(entrNo)) {
			EntrDemoDO entrDemoDO = entrDemoDao.get(entrNo);
			if(entrDemoDO != null){
				rsDO.setEntrName(entrDemoDO.getEntrName());
			}
		}
		rsDO.setSignChkFlg(rsDO.getSignPat());
		rsDO.setClrFeeTp(rsDO.getFeeTp());
		rsDO.setRelatSysNo(rsDO.getRelatSys());
		
		List<TPipSaleProdDO> list = tPipSaleProdDao.list(new TPipSaleProdDO());
		rsDO.setBUSI_NO(rsDO.getBusiNo());
		rsDO.setBUSI_NAME(rsDO.getBusiName());
		rsDO.setENTR_NO(rsDO.getEntrNo());
		rsDO.setENTR_NAME(rsDO.getEntrName());
		for (TPipSaleProdDO tPipSaleProdDO : list) {
			if (rsDO.getSaleProdCode().equals(tPipSaleProdDO.getSaleProdCode())) {
				rsDO.setPROD_LINE_CODE(tPipSaleProdDO.getProdLineCode());
				if(officeDao.get(tPipSaleProdDO.getBrchId())!=null) {
					rsDO.setBrchName(officeDao.get(tPipSaleProdDO.getBrchId()).getName());
				}
			}
		}

		TPipBusiParaDO tPipBusiParaDO = new TPipBusiParaDO();
		tPipBusiParaDO.setBusiNo(tPipBusiDO.getBusiNo());
		List<TPipBusiParaDO> paraList = tPipBusiParaDao.list(tPipBusiParaDO);
//		rsDO.setKeyList(paraList);

		List<TPipCompSvcParaDOTemp> KEY_LIST = Lists.newArrayList();
		for(TPipBusiParaDO para:paraList){
			TPipCompSvcParaDOTemp key = new TPipCompSvcParaDOTemp();
			key.setKEY_NO(para.getKeyNo());
			key.setKEY_NAME(para.getKeyName());
			key.setKV(para.getKv());
			KEY_LIST.add(key);
		}
		rsDO.setSALEPROD(KEY_LIST);

		//开通机构
		TPipBusiBrchOpenDO tPipBusiBrchOpenDO=new TPipBusiBrchOpenDO();
		tPipBusiBrchOpenDO.setBusiNo(rsDO.getBusiNo());
		List<TPipBusiBrchOpenDO> openGrpBrchList = tPipBusiBrchOpenDao.list(tPipBusiBrchOpenDO);
		rsDO.setOpenGrpBrch(openGrpBrchList);
		
		//开通渠道
		TPipBusiChnlOpenDO tPipBusiChnlOpenDO=new TPipBusiChnlOpenDO();
		tPipBusiChnlOpenDO.setBusiNo(rsDO.getBusiNo());
		List<TPipBusiChnlOpenDO> openGrpChnlNoList = tPipBusiChnlOpenDao.list(tPipBusiChnlOpenDO);
		rsDO.setOpenGrpChnlNo(openGrpChnlNoList);
		
		// 清算
		String ruleIdClrTp;
		// 签约检查
		String ruleIdSignPat;
		// 对账
		String ruleIdChkPat;
		// 手续费清算
		String ruleIdClrFeeTp;
		TPipRuleRelatDO tPipRuleRelatDO = new TPipRuleRelatDO();
		tPipRuleRelatDO.setBusiNo(tPipBusiDO.getBusiNo());
		List<TPipRuleRelatDO> list1 = tPipRuleRelatDao.list(tPipRuleRelatDO);
		for (TPipRuleRelatDO t : list1) {
			if ("201".equals(t.getRuleTp())) {
				ruleIdClrTp = t.getRuleId();
//				TPipClrRuleDO tPipClrRuleDO = new TPipClrRuleDO();
//				tPipClrRuleDO.setRuleId(ruleIdClrTp);
//				tPipClrRuleDO = tPipClrRuleDao.get(tPipClrRuleDO);
				
				TPipClrMertAcctDO tPipClrMertAcctDO = new TPipClrMertAcctDO();
				tPipClrMertAcctDO.setRuleId(ruleIdClrTp);
				tPipClrMertAcctDO.setMertNo("0");
				tPipClrMertAcctDO = tPipClrMertAcctDao.get(tPipClrMertAcctDO);
				if(tPipClrMertAcctDO!=null) {
					rsDO.setIntrmAcct(tPipClrMertAcctDO.getIntrmAcct());
					rsDO.setEntrAcct(tPipClrMertAcctDO.getEntrAcct());
					rsDO.setEntrAcctBank(tPipClrMertAcctDO.getEntrAcctBank());
					rsDO.setIntrmAcctName(tPipClrMertAcctDO.getIntrmAcctName());
					rsDO.setEntrAcctName(tPipClrMertAcctDO.getEntrAcctName());
					rsDO.setInOutBankFlg(tPipClrMertAcctDO.getEntrAcctBankFlg());
					rsDO.setShortRmrk(tPipClrMertAcctDO.getShortRmrk());//获取过渡账户开户机构
				}
			} else if ("401".equals(t.getRuleTp())) {
				ruleIdSignPat = t.getRuleId();
			} else if ("101".equals(t.getRuleTp())) {
				ruleIdChkPat = t.getRuleId();
			} else if ("301".equals(t.getRuleTp())) {
				ruleIdClrFeeTp = t.getRuleId();
				TPipClrMertAcctDO tPipClrMertAcctDO = new TPipClrMertAcctDO();
				tPipClrMertAcctDO.setRuleId(ruleIdClrFeeTp);
				tPipClrMertAcctDO.setMertNo("0");
				tPipClrMertAcctDO = tPipClrMertAcctDao.get(tPipClrMertAcctDO);
				if (tPipClrMertAcctDO != null) {
					rsDO.setFeeTfOutAcct(tPipClrMertAcctDO.getFeeTfOutAcct());
					rsDO.setFeeTfInAcct(tPipClrMertAcctDO.getFeeTfInAcct());
				}
				
				TPipClrMertFeeCaltDO tPipClrMertFeeCaltDO = new TPipClrMertFeeCaltDO();
				tPipClrMertFeeCaltDO.setRuleId(ruleIdClrFeeTp);
				tPipClrMertFeeCaltDO = tPipClrMertFeeCaltDao.get(tPipClrMertFeeCaltDO);
				if(tPipClrMertFeeCaltDO!=null) {
					String feeRule = (tPipClrMertFeeCaltDO.getFeeRuleExpr().split("@#@"))[0];
					String feeRuleColArray[] = feeRule.split("\\|");
					if (feeRuleColArray.length == 4) {
						if ("1".equals(tPipClrMertFeeCaltDO.getFeeCaltMeth())) {//成功笔数
							rsDO.setAmt(Double.parseDouble(feeRule.split("\\|")[2]));
						} else if ("3".equals(tPipClrMertFeeCaltDO.getFeeCaltMeth())) {//成功金额比例
							rsDO.setDefFrt(Integer.parseInt(feeRule.split("\\|")[3]));
						}
					}
				}

			}
		}

		TPipBusiDocDO tPipBusiDocDO = new TPipBusiDocDO();
		tPipBusiDocDO.setBusiNo(tPipBusiDO.getBusiNo());
		List<TPipBusiDocDO> docList = tPipBusiDocDao.list(tPipBusiDocDO);
		StringBuilder str1 = new StringBuilder();
		StringBuilder str2 = new StringBuilder();
		StringBuilder str3 = new StringBuilder();
		for(TPipBusiDocDO doc:docList){
			if("demand".equals(doc.getFileTp())){
				str1.append("|").append(doc.getUrl());
			}else if("interface".equals(doc.getFileTp())){
				str2.append("|").append(doc.getUrl());
			}else if("other".equals(doc.getFileTp())){
				str3.append("|").append(doc.getUrl());
			}
		}
		com.adtec.framework.json.JSONObject resBody = new com.adtec.framework.json.JSONObject(rsDO);
		resBody.put("FILE_PATH1", str1.toString());
		resBody.put("FILE_PATH2", str2.toString());
		resBody.put("FILE_PATH3", str3.toString());
		
		responseData.addColumn("tPipBusiDO");
		responseData.beforeFirst();
		if(!responseData.hasNext()){
			responseData.appendRow();
		}
		responseData.next();
		responseData.updateValue("tPipBusiDO", resBody);
		return responseData;
	}

	/**
	 *获取详细数据
	 * */
	public TPipBusiDO getDetailForMsmall(TPipBusiDO tPipBusiDO) {

		//查询文件信息
		TPipBusiDO rsDO = tPipBusiDao.get(tPipBusiDO);

		String entrName = entrDemoDao.get(rsDO.getEntrNo()).getEntrName();
		rsDO.setSignChkFlg(rsDO.getSignPat());
		rsDO.setClrFeeTp(rsDO.getFeeTp());
		rsDO.setRelatSysNo(rsDO.getRelatSys());

		List<TPipSaleProdDO> list = tPipSaleProdDao.list(new TPipSaleProdDO());
		rsDO.setBUSI_NO(rsDO.getBusiNo());
		rsDO.setBUSI_NAME(rsDO.getBusiName());
		rsDO.setENTR_NO(rsDO.getEntrNo());
		rsDO.setENTR_NAME(entrName);
		rsDO.setEntrName(entrName);
		for (TPipSaleProdDO tPipSaleProdDO : list) {
			if (rsDO.getSaleProdCode().equals(tPipSaleProdDO.getSaleProdCode())) {
				rsDO.setPROD_LINE_CODE(tPipSaleProdDO.getProdLineCode());
			}
		}

		TPipBusiParaDO tPipBusiParaDO = new TPipBusiParaDO();
		tPipBusiParaDO.setBusiNo(tPipBusiDO.getBusiNo());
		List<TPipBusiParaDO> paraList = tPipBusiParaDao.list(tPipBusiParaDO);

		List<TPipCompSvcParaDOTemp> KEY_LIST = Lists.newArrayList();
		for(TPipBusiParaDO para:paraList){
			TPipCompSvcParaDOTemp key = new TPipCompSvcParaDOTemp();
			key.setKEY_NO(para.getKeyNo());
			key.setKEY_NAME(para.getKeyName());
			key.setKV(para.getKv());
			KEY_LIST.add(key);
		}
		rsDO.setSALEPROD(KEY_LIST);

		// 清算
		String ruleIdClrTp;
		// 签约检查
		String ruleIdSignPat;
		// 对账
		String ruleIdChkPat;
		// 手续费清算
		String ruleIdClrFeeTp;
		TPipRuleRelatDO tPipRuleRelatDO = new TPipRuleRelatDO();
		tPipRuleRelatDO.setBusiNo(tPipBusiDO.getBusiNo());
		List<TPipRuleRelatDO> list1 = tPipRuleRelatDao.list(tPipRuleRelatDO);
		for (TPipRuleRelatDO t : list1) {
			if ("201".equals(t.getRuleTp())) {
				ruleIdClrTp = t.getRuleId();
//				TPipClrRuleDO tPipClrRuleDO = new TPipClrRuleDO();
//				tPipClrRuleDO.setRuleId(ruleIdClrTp);
//				tPipClrRuleDO = tPipClrRuleDao.get(tPipClrRuleDO);
//				if (tPipClrRuleDO != null) {
//					rsDO.setIntrmAcct(tPipClrRuleDO.getIntrmAcct());
//					rsDO.setEntrAcct(tPipClrRuleDO.getEntrAcct());
//					rsDO.setEntrAcctBank(tPipClrRuleDO.getEntrAcctBank());
//				}

				TPipClrMertAcctDO tPipClrMertAcctDO = new TPipClrMertAcctDO();
				tPipClrMertAcctDO.setRuleId(ruleIdClrTp);
				tPipClrMertAcctDO.setMertNo("0");
				tPipClrMertAcctDO = tPipClrMertAcctDao.get(tPipClrMertAcctDO);
				if(tPipClrMertAcctDO != null) {
					rsDO.setIntrmAcct(tPipClrMertAcctDO.getIntrmAcct());
					rsDO.setEntrAcct(tPipClrMertAcctDO.getEntrAcct());
//					rsDO.setShortRmrk();
					rsDO.setEntrAcctBank(tPipClrMertAcctDO.getEntrAcctBank());
					rsDO.setIntrmAcctName(tPipClrMertAcctDO.getIntrmAcctName());
					rsDO.setEntrAcctName(tPipClrMertAcctDO.getEntrAcctName());
					rsDO.setInOutBankFlg(tPipClrMertAcctDO.getEntrAcctBankFlg());
				}
			} else if ("401".equals(t.getRuleTp())) {
				ruleIdSignPat = t.getRuleId();
			} else if ("101".equals(t.getRuleTp())) {
				ruleIdChkPat = t.getRuleId();
			} else if ("301".equals(t.getRuleTp())) {
				ruleIdClrFeeTp = t.getRuleId();
			}
		}

		TPipBusiDocDO tPipBusiDocDO = new TPipBusiDocDO();
		tPipBusiDocDO.setBusiNo(tPipBusiDO.getBusiNo());
		List<TPipBusiDocDO> docList = tPipBusiDocDao.list(tPipBusiDocDO);
		StringBuilder str1 = new StringBuilder();
		StringBuilder str2 = new StringBuilder();
		StringBuilder str3 = new StringBuilder();
		for(TPipBusiDocDO doc:docList){
			if("demand".equals(doc.getFileTp())){
				str1.append("|").append(doc.getUrl());
			}else if("interface".equals(doc.getFileTp())){
				str2.append("|").append(doc.getUrl());
			}else if("other".equals(doc.getFileTp())){
				str3.append("|").append(doc.getUrl());
			}
		}

		rsDO.setFILE_PATH1(str1.toString());
		rsDO.setFILE_PATH2(str2.toString());
		rsDO.setFILE_PATH3(str3.toString());
		return rsDO;
	}

	/**
	 * 提交
	 * @return
	 */
	public IDataset add(TPipBusiDO tPipBusiDO,String FILE_PATH1,String FILE_PATH2,String FILE_PATH3,String operTy,List<TPipBusiParaDOTemp> KEY_LIST,IDataset reqDs) {
		IDBSession session = DBSessionFactory.getSession();
		try {
			int ret = 0;
			// 判断业务编号是否存在
			TPipBusiDO tPipBusiDoOrig = tPipBusiDao.get(tPipBusiDO);
			if ("1".equals(operTy) && tPipBusiDoOrig != null) {
				throw new BaseException(SysErr.E_MESSAGE, "业务编号已存在，新增失败");
			}

			session.beginTransaction();
			//新插入，先删除数据,插入文档数据
			tPipBusiDocDao.delete(tPipBusiDO.getBusiNo());
			FILE_PATH1 = Encodes.urlDecode(FILE_PATH1);
			FILE_PATH2 = Encodes.urlDecode(FILE_PATH2);
			FILE_PATH3 = Encodes.urlDecode(FILE_PATH3);
			String[] FILE_PATH_ARR1 = FILE_PATH1.split("\\|");
			String[] FILE_PATH_ARR2 = FILE_PATH2.split("\\|");
			String[] FILE_PATH_ARR3 = FILE_PATH3.split("\\|");
			List<String[]> list = new ArrayList<>();
			list.add(FILE_PATH_ARR1);
			list.add(FILE_PATH_ARR2);
			list.add(FILE_PATH_ARR3);
			TPipBusiDocDO rsDoc = tPipBusiDocDao.getMaxSer();
			for(int a=0;a<list.size();a++){
				String[] fileArr = list.get(a);
				int fileKind = a+1;
				for(String filePath:fileArr){
					if("".equals(filePath)){
						continue;
					}
					// 获取文件名
					String fileName = FileUtil.getFileName(filePath);
					// 真实路径
					String realPath = filePath;
					TPipBusiDocDO tPipBusiDocDO = new TPipBusiDocDO();
					tPipBusiDocDO.setBusiNo(tPipBusiDO.getBusiNo());
					tPipBusiDocDO.setFileName(fileName);
					if (fileKind == 1) {
						tPipBusiDocDO.setFileTp("demand");
					} else if (fileKind == 2) {
						tPipBusiDocDO.setFileTp("interface");
					} else if (fileKind == 3) {
						tPipBusiDocDO.setFileTp("other");
					}
					tPipBusiDocDO.setUrl(realPath);
					if(null!=rsDoc){
						tPipBusiDocDO.setSer(Integer.parseInt(rsDoc.getSer())+fileKind+"");
					}else{
						tPipBusiDocDO.setSer(fileKind+"");
					}
					
					String previewPath = FileUtil.getPreviewFilePath(realPath, false);
					//插入数据
					tPipBusiDocDao.insert(tPipBusiDocDO);
				}
			}

			tPipBusiDO.setBrchTp(UserUtils.getUser().getOffice().getBrchTp());
			tPipBusiDO.setCrtr(UserUtils.getUser().getId());
			if("1".equals(operTy)){
				//插入业务信息
				tPipBusiDO.setCrtr(UserUtils.getUser().getId());
				tPipBusiDO.setCrtTime(DateUtil.getDateTime());
				tPipBusiDO.setUptr(UserUtils.getUser().getId());
				tPipBusiDO.setUptTime(DateUtil.getDateTime());
				tPipBusiDao.insert(tPipBusiDO);
			}else{
				tPipBusiDO.setCrtr(tPipBusiDoOrig.getCrtr());
				tPipBusiDO.setCrtTime(tPipBusiDoOrig.getCrtTime());
				tPipBusiDO.setUptr(UserUtils.getUser().getId());
				tPipBusiDO.setUptTime(DateUtil.getDateTime());
				tPipBusiDao.update(tPipBusiDO);
				
			}

			tPipBusiParaDao.delete(tPipBusiDO.getBusiNo());
			for (TPipBusiParaDOTemp tPipBusiParaDOTemp : KEY_LIST) {
				TPipBusiParaDO tPipBusiParaDO = new TPipBusiParaDO();
				tPipBusiParaDO.setBusiNo(tPipBusiDO.getBusiNo());
				tPipBusiParaDO.setBusiName(tPipBusiDO.getBusiName());
				tPipBusiParaDO.setKeyNo(tPipBusiParaDOTemp.getKEY_NO());
				tPipBusiParaDO.setKeyName(tPipBusiParaDOTemp.getKEY_NAME());
				tPipBusiParaDO.setKv(tPipBusiParaDOTemp.getKV());
				tPipBusiParaDao.insert(tPipBusiParaDO);
			}

			// 清算
			String ruleIdClrTp = null;
			// 签约检查
			String ruleIdSignPat = null;
			// 对账
			String ruleIdChkPat = null;
			// 手续费清算
			String ruleIdClrFeeTp = null;

			// 获取规则表相关数据
			TPipRuleRelatDO tPipRuleRelatDO1 = new TPipRuleRelatDO();
			tPipRuleRelatDO1.setBusiNo(tPipBusiDO.getBusiNo());
			List<TPipRuleRelatDO> list1 = tPipRuleRelatDao.list(tPipRuleRelatDO1);
			for (TPipRuleRelatDO t : list1) {
				if ("201".equals(t.getRuleTp())) {
					ruleIdClrTp = t.getRuleId();
					//tPipClrRuleDao.delete(ruleIdClrTp);
				} else if ("401".equals(t.getRuleTp())) {
					ruleIdSignPat = t.getRuleId();
					//tPipSignRuleDao.delete(ruleIdSignPat);
				} else if ("101".equals(t.getRuleTp())) {
					ruleIdChkPat = t.getRuleId();
					//tPipChkRuleDao.delete(ruleIdChkPat);
				} else if ("301".equals(t.getRuleTp())) {
					ruleIdClrFeeTp = t.getRuleId();
				}
			}
			//tPipRuleRelatDao.delete(tPipBusiDO.getBusiNo());

			// 生成规则编号
			if (null == ruleIdClrTp) {
				ruleIdClrTp = getRuleId("201");
			}
			if (null == ruleIdSignPat) {
				ruleIdSignPat = getRuleId("401");
			}
			if (null == ruleIdChkPat) {
				ruleIdChkPat = getRuleId("101");
			}
			if (null == ruleIdClrFeeTp) {
				ruleIdClrFeeTp = getRuleId("301");
			}

			

			// 签约规则：不检验 00 检查 01 其他模式检查 02
			String signPat = tPipBusiDO.getSignPat();
			TPipRuleRelatDO tPipRuleRelatDO = new TPipRuleRelatDO();
			tPipRuleRelatDO.setBusiNo(tPipBusiDO.getBusiNo());
			tPipRuleRelatDO.setSvcCode("0");
			tPipRuleRelatDO.setSceneNo("0");
			
			tPipRuleRelatDO.setRuleId(ruleIdSignPat);
			tPipRuleRelatDO.setRuleTp("401");
			if("1".equals(operTy)){
				tPipRuleRelatDao.insert(tPipRuleRelatDO);
				tPipSignRuleDao.insert(buildTPipSignRuleDO(ruleIdSignPat, reqDs, signPat));
			}else{
				if(tPipBusiDoOrig!=null&&!tPipBusiDoOrig.getSignPat().equals(signPat)){
					tPipSignRuleDao.update(buildTPipSignRuleDO(ruleIdSignPat, reqDs, signPat));
				}
				
			}
			

			// 对账规则：不对账00 两方对账01 三方对账02
			String chkPat = tPipBusiDO.getChkPat();
			tPipRuleRelatDO.setRuleId(ruleIdChkPat);
			tPipRuleRelatDO.setRuleTp("101");
			if("1".equals(operTy)){
				tPipRuleRelatDao.insert(tPipRuleRelatDO);
				tPipChkRuleDao.insert(buildTPipChkRuleDO(ruleIdChkPat, reqDs,chkPat));
			}else{
				if(tPipBusiDoOrig!=null&&!tPipBusiDoOrig.getChkPat().equals(chkPat)){
					//修改赋值  
					tPipChkRuleDao.update(buildTPipChkRuleDO(ruleIdChkPat, reqDs,chkPat));
				}
			}


			// 清算规则：无需清算  使用过渡户 01 其他模式 02
			String clrTp = tPipBusiDO.getClrTp();
			tPipRuleRelatDO.setRuleId(ruleIdClrTp);
			tPipRuleRelatDO.setRuleTp("201");
			if("1".equals(operTy)){
				if(!"02".equals(clrTp)){//商户清算模式
					tPipRuleRelatDao.insert(tPipRuleRelatDO);
					tPipClrRuleDao.insert(buildTPipClrMertRuleDO(ruleIdClrTp, reqDs,clrTp));
					tPipClrMertAcctDao.insert(buildTPipClrMertAcctDO(ruleIdClrTp, reqDs,clrTp));
				}
			}else{
				if(!"02".equals(clrTp)){//商户清算模式
					if (tPipRuleRelatDao.getByRuleIdAndType(tPipRuleRelatDO) == null) {
						tPipRuleRelatDao.insert(tPipRuleRelatDO);
					}
					TPipClrMertRuleDO tPipClrMertRuleDO = buildTPipClrMertRuleDO(ruleIdClrTp, reqDs,clrTp);
					ret = tPipClrRuleDao.update(tPipClrMertRuleDO);
					if (ret == 0) {
						tPipClrRuleDao.insert(tPipClrMertRuleDO);
					}

					TPipClrMertAcctDO tPipClrMertAcctDO = buildTPipClrMertAcctDO(ruleIdClrTp, reqDs,clrTp);
					ret = tPipClrMertAcctDao.update(tPipClrMertAcctDO);
					if (ret == 0) {
						tPipClrMertAcctDao.insert(tPipClrMertAcctDO);
					}
					
				}
			}

			// 手续费规则：不收取手续费 00 按成功金额比例收取 01 按成功笔数收取 02 其他模式 03
			String clrFeeTp = tPipBusiDO.getFeeTp();
			tPipRuleRelatDO.setRuleId(ruleIdClrFeeTp);
			tPipRuleRelatDO.setRuleTp("301");
			if("1".equals(operTy)){
				if(!"03".equals(clrFeeTp)){//商户清算模式
					tPipRuleRelatDao.insert(tPipRuleRelatDO);
					tPipClrMertFeeCaltDao.insert(buildTPipClrMertFeeCaltDO(ruleIdClrFeeTp, reqDs,clrFeeTp));
					
					tPipClrMertFeeRuleDao.insert(buildTPipClrMertFeeRuleDO(ruleIdClrFeeTp, reqDs,clrFeeTp));
					if(!"00".equals(clrFeeTp)) {//收取
						tPipClrMertAcctDao.insert(buildTPipClrMertAcctDOForFeeClr(ruleIdClrFeeTp, reqDs,clrFeeTp));
					}
				}
			}else {
				if(!"03".equals(clrFeeTp)){//商户清算模式
					if(tPipBusiDoOrig!=null&&!tPipBusiDoOrig.getFeeTp().equals("03")){
						tPipClrMertFeeRuleDao.update(buildTPipClrMertFeeRuleDO(ruleIdClrFeeTp, reqDs,clrFeeTp));
						if("00".equals(clrFeeTp)) {//不收取
							tPipClrMertFeeCaltDao.delete(ruleIdClrFeeTp);
							
							TPipClrMertAcctDO tPipClrMertAcctDO = new TPipClrMertAcctDO();
							tPipClrMertAcctDO.setRuleId(ruleIdClrFeeTp);
							tPipClrMertAcctDO.setMertNo("0");
							tPipClrMertAcctDao.delete(tPipClrMertAcctDO);
						}else {
							TPipClrMertFeeCaltDO tPipClrMertFeeCaltDO = buildTPipClrMertFeeCaltDO(ruleIdClrFeeTp, reqDs,clrFeeTp);
							ret = tPipClrMertFeeCaltDao.update(tPipClrMertFeeCaltDO);
							if(ret == 0) {
								tPipClrMertFeeCaltDao.insert(tPipClrMertFeeCaltDO);
							}
							TPipClrMertAcctDO tPipClrMertAcctDO = buildTPipClrMertAcctDOForFeeClr(ruleIdClrFeeTp, reqDs,clrFeeTp);
							ret = tPipClrMertAcctDao.update(tPipClrMertAcctDO);
							if(ret == 0) {
								tPipClrMertAcctDao.insert(tPipClrMertAcctDO);
							}
						}
					} else {
						if (tPipRuleRelatDao.getByRuleIdAndType(tPipRuleRelatDO) == null) {
							tPipRuleRelatDao.insert(tPipRuleRelatDO);
						}

						TPipClrMertFeeCaltDO tPipClrMertFeeCaltDO = buildTPipClrMertFeeCaltDO(ruleIdClrFeeTp, reqDs,clrFeeTp);
						ret = tPipClrMertFeeCaltDao.update(tPipClrMertFeeCaltDO);
						if(ret == 0) {
							tPipClrMertFeeCaltDao.insert(tPipClrMertFeeCaltDO);
						}

						if(!"00".equals(clrFeeTp)) {//收取
							TPipClrMertAcctDO tPipClrMertAcctDO = buildTPipClrMertAcctDOForFeeClr(ruleIdClrFeeTp, reqDs, clrFeeTp);
							ret = tPipClrMertAcctDao.update(tPipClrMertAcctDO);
							if (ret == 0) {
								tPipClrMertAcctDao.insert(tPipClrMertAcctDO);
							}
						}

						TPipClrMertFeeRuleDO tPipClrMertFeeRuleDO = buildTPipClrMertFeeRuleDO(ruleIdClrFeeTp, reqDs,clrFeeTp);
						ret = tPipClrMertFeeRuleDao.update(tPipClrMertFeeRuleDO);
						if(ret == 0) {
							tPipClrMertFeeRuleDao.insert(tPipClrMertFeeRuleDO);
						}
					}
					
				}
			}

			//插入业务渠道开通表
			String openGrpChnlNo =reqDs.getString("openGrpChnlNo");//业务开通渠道
			if("1".equals(operTy)){
				if(""!=openGrpChnlNo){
					String[] arrs=openGrpChnlNo.split(",");
					for(int i=0;i<arrs.length;i++){
						TPipBusiChnlOpenDO tPipBusiChnlOpenDO=new TPipBusiChnlOpenDO();
						tPipBusiChnlOpenDO.setBusiNo(tPipBusiDO.getBusiNo());
						tPipBusiChnlOpenDO.setChnlNo(arrs[i]);
						tPipBusiChnlOpenDO.setFlg("Y");
						
						tPipBusiChnlOpenDao.insert(tPipBusiChnlOpenDO);
					}
				}
			}else{
				//先删除后插入
				TPipBusiChnlOpenDO tPipBusiChnlOpenDO=new TPipBusiChnlOpenDO();
				tPipBusiChnlOpenDO.setBusiNo(tPipBusiDO.getBusiNo());
				tPipBusiChnlOpenDao.delete(tPipBusiChnlOpenDO);
				if(""!=openGrpChnlNo){
					String[] arrs=openGrpChnlNo.split(",");
					for(int i=0;i<arrs.length;i++){
						TPipBusiChnlOpenDO tPipBusiChnlOpenDOSour=new TPipBusiChnlOpenDO();
						tPipBusiChnlOpenDOSour.setBusiNo(tPipBusiDO.getBusiNo());
						tPipBusiChnlOpenDOSour.setChnlNo(arrs[i]);
						tPipBusiChnlOpenDOSour.setFlg("Y");
						
						tPipBusiChnlOpenDao.insert(tPipBusiChnlOpenDOSour);
					}
				}
			}
			
			//插入业务机构开通表
			String openGrpBrch =reqDs.getString("openGrpBrch");//业务开通机构
			if("1".equals(operTy)){
				if(""!=openGrpBrch){
					String[] arrs=openGrpBrch.split(",");
					for(int i=0;i<arrs.length;i++){
						TPipBusiBrchOpenDO tPipBusiBrchOpenDO=new TPipBusiBrchOpenDO();
						tPipBusiBrchOpenDO.setBusiNo(tPipBusiDO.getBusiNo());
						tPipBusiBrchOpenDO.setBrch(arrs[i]);
						tPipBusiBrchOpenDO.setFlg("Y");
						
						tPipBusiBrchOpenDao.insert(tPipBusiBrchOpenDO);
					}
				}
			}else{
				//先删除后插入
				TPipBusiBrchOpenDO tPipBusiBrchOpenDO=new TPipBusiBrchOpenDO();
				tPipBusiBrchOpenDO.setBusiNo(tPipBusiDO.getBusiNo());
				tPipBusiBrchOpenDao.delete(tPipBusiBrchOpenDO);
				if(""!=openGrpBrch){
					String[] arrs=openGrpBrch.split(",");
					for(int i=0;i<arrs.length;i++){
						TPipBusiBrchOpenDO tPipBusiBrchOpenDOSour=new TPipBusiBrchOpenDO();
						tPipBusiBrchOpenDOSour.setBusiNo(tPipBusiDO.getBusiNo());
						tPipBusiBrchOpenDOSour.setBrch(arrs[i]);
						tPipBusiBrchOpenDOSour.setFlg("Y");
						
						tPipBusiBrchOpenDao.insert(tPipBusiBrchOpenDOSour);
					}
				}
			}

		}catch (Exception e) {
			e.printStackTrace();
			try {
				session.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new BaseException(SysErr.E_MESSAGE, e, e.getMessage());
		}finally {
			try {
				session.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		String effect = reqDs.getString("effect");
		if (null!=effect && "1".equals(effect)) {
			FPRodEntrRegRedisParaReqDTO reqBody = new FPRodEntrRegRedisParaReqDTO();
			reqBody.setOPER_TP("2");
			ReqDTO req = new ReqDTO(reqBody);
			ProdUtil.setReqHead(tPipBusiDO.getBusiNo(),req, "FCtrlTranRedisRefresh");
			HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();//FPRodEntrRegRedisPara //ParamUtil.getConfig("PROD_PARTID")
			httpJsonFactory.callService("","999103","FCtrlTranRedisRefresh", req, FPRodEntrRegRedisParaReqDTO.class,null);
		}

		return DatasetService.getInstace().getDataset();
	}

	//获取下拉框的渠道编号
	public IDataset getgetChnl() {
		ChnlDao chnlDao = SpringContextHolder.getBean("chnlDao");
		List<ChnlDO> list = chnlDao.list(new ChnlDO());
		IDataset resBody = DatasetService.getInstace().getDataset(list,ChnlDO.class);
		return resBody;
		
	}

	public IDataset getInOutBankFlg() {
		List<DictDO> list = busiDemoDao.getInOutBankFlg();
		IDataset resBody = DatasetService.getInstace().getDataset(list,DictDO.class);
		return resBody;
	}

	public IDataset getgetBrch() {
		BrchDao brchDao = SpringContextHolder.getBean("brchDao");
		List<BrchDO> list = brchDao.list(new BrchDO());
		IDataset resBody = DatasetService.getInstace().getDataset(list,BrchDO.class);
		return resBody;

	}

	public void Enable(String busiNo) {
		if(StringUtil.isEmpty(busiNo)){
			throw new BaseException("业务标号不能为空");
		}
		TPipBusiDO bs = new TPipBusiDO();
		bs.setBusiNo(busiNo);
		bs = tPipBusiDao.get(bs);
		if(bs.getOpenStat().equals("Y")){
			throw new BaseException("业务已经启用");
		}
		tPipBusiDao.update(busiNo,"Y");
	}
	public void Stopping(String busiNo) {
		if(StringUtil.isEmpty(busiNo)){
			throw new BaseException("业务标号不能为空");
		}
		TPipBusiDO bs = new TPipBusiDO();
		bs.setBusiNo(busiNo);
		bs = tPipBusiDao.get(bs);
		if(bs.getOpenStat().equals("N")){
			throw new BaseException("业务已经停用");
		}
		tPipBusiDao.update(busiNo,"N");
	}
	
	public void delete(String busiNo) {
		if(StringUtil.isEmpty(busiNo)){
			throw new BaseException("业务标号不能为空");
		}
		IDBSession session = DBSessionFactory.getSession();
		try {
			session.beginTransaction();
			tPipBusiDocDao.delete(busiNo);
			tPipBusiParaDao.delete(busiNo);
			tPipBusiDao.delete(busiNo);

			// 清算
			String ruleIdClrTp;
			// 签约检查
			String ruleIdSignPat;
			// 对账
			String ruleIdChkPat;
			// 手续费清算
			String ruleIdClrFeeTp;
			TPipRuleRelatDO tPipRuleRelatDO = new TPipRuleRelatDO();
			tPipRuleRelatDO.setBusiNo(busiNo);
			List<TPipRuleRelatDO> list1 = tPipRuleRelatDao.list(tPipRuleRelatDO);
			for (TPipRuleRelatDO t : list1) {
				if ("201".equals(t.getRuleTp())) {
					ruleIdClrTp = t.getRuleId();
					tPipClrRuleDao.delete(ruleIdClrTp);
					tPipClrMertAcctDao.delete(ruleIdClrTp);
				} else if ("401".equals(t.getRuleTp())) {
					ruleIdSignPat = t.getRuleId();
					tPipSignRuleDao.delete(ruleIdSignPat);
				} else if ("101".equals(t.getRuleTp())) {
					ruleIdChkPat = t.getRuleId();
					tPipChkRuleDao.delete(ruleIdChkPat);
				} else if ("301".equals(t.getRuleTp())) {
					ruleIdClrFeeTp = t.getRuleId();
					tPipClrMertAcctDao.delete(ruleIdClrFeeTp);
					tPipClrMertFeeCaltDao.delete(ruleIdClrFeeTp);
					tPipClrMertFeeRuleDao.delete(ruleIdClrFeeTp);
				}
			}
			tPipRuleRelatDao.delete(busiNo);

			TPipBusiBrchOpenDO tPipBusiBrchOpenDO=new TPipBusiBrchOpenDO();
			tPipBusiBrchOpenDO.setBusiNo(busiNo);
			tPipBusiBrchOpenDao.delete(tPipBusiBrchOpenDO);
			
			TPipBusiChnlOpenDO tPipBusiChnlOpenDO=new TPipBusiChnlOpenDO();
			tPipBusiChnlOpenDO.setBusiNo(busiNo);
			tPipBusiChnlOpenDao.delete(tPipBusiChnlOpenDO);
			
		}catch (Exception e) {
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

	public String getBusiNo(String SALE_PROD_CODE) {
		return tPipBusiDao.getBusiNo(SALE_PROD_CODE);
	}

	public String getRuleId(String type) {
		return tPipBusiDao.getRuleId(type);
	}

	private TPipChkRuleDO buildTPipChkRuleDO(String ruleId, IDataset reqDs,String chkPat) {
		TPipChkRuleDO tPipChkRuleDO = new TPipChkRuleDO();

		tPipChkRuleDO.setRuleId(ruleId);		// 规则编号
		tPipChkRuleDO.setRuleName(reqDs.getString("BUSI_NAME")+"业务对账规则");		// 规则名称
		if("00".equals(chkPat)){//不对账
			tPipChkRuleDO.setChkTp("0");
		}else {
			if("01".equals(chkPat)){//两方
				tPipChkRuleDO.setChkTp("1");
			}else{//三方
				tPipChkRuleDO.setChkTp("2");
			}
			tPipChkRuleDO.setChkSndGrpFlg("11000000000000000000");//第1位-自动对账、第2位-柜面发起对账、第3位-第三方发起
			tPipChkRuleDO.setChkCycTp("T");		// 对账周期类型
			tPipChkRuleDO.setChkCyc(1);		// 对账周期
			tPipChkRuleDO.setStrTime("000000");		// 开始时间
			tPipChkRuleDO.setEndTime("235959");		// 结束时间
			tPipChkRuleDO.setChkRsltPushFlg("1");		// 对账结果推送标志
			tPipChkRuleDO.setChkRsltFileName("");		// 对账结果文件名称
			tPipChkRuleDO.setChkFileDownloadFlg("");		// 对账文件下载标志
			tPipChkRuleDO.setOthChkFileName("");
			tPipChkRuleDO.setChkSwitchFlg("0");//0-否（上一日对账失败，是否进行下一日的对账）
			tPipChkRuleDO.setErrSwitchFlg("0");//0-否（是否自动差错处理）
			if("01".equals(chkPat)){
				tPipChkRuleDO.setBean("ChkBeanComn");
			}
			tPipChkRuleDO.setChkNo(reqDs.getString("BUSI_NO")+"-"+ruleId);//对账分类编号
			if("01".equals(chkPat)) {
				tPipChkRuleDO.setChkRuleId("1010000000000");//对账组件对账勾兑规则ID
			} else {
				tPipChkRuleDO.setChkRuleId(ruleId);//对账组件对账勾兑规则ID
			}
			tPipChkRuleDO.setUpRuleId("");
		}
		
		
		return tPipChkRuleDO;
	}

	private TPipClrMertRuleDO buildTPipClrMertRuleDO(String ruleId, IDataset reqDs,String clrTp) {
		TPipClrMertRuleDO tPipClrRuleDO = new TPipClrMertRuleDO();
		tPipClrRuleDO.setRuleId(ruleId);		// 规则编号
		tPipClrRuleDO.setRuleName(reqDs.getString("BUSI_NAME")+"业务本金清算规则");		// 规则名称
		if("01".equals(clrTp)){//过渡户清算
			tPipClrRuleDO.setClrMeth("01");// 清算方式
			tPipClrRuleDO.setClrDimTp("01"); //清算维度类型
			tPipClrRuleDO.setBatProcFlg("1"); //批次处理标志
			tPipClrRuleDO.setClrCycTp("T");		// 清算周期类型
			tPipClrRuleDO.setClrCyc(1);		// 清算周期
			tPipClrRuleDO.setClrSndGrpFlg("11000000000000000000");		// 清算发送组标志
			tPipClrRuleDO.setStrTime("000000");		// 开始时间
			tPipClrRuleDO.setEndTime("235959");		// 结束时间
			tPipClrRuleDO.setBean("MertClrBeanComm");		// BEAN
		}else {//不清算或其他
			tPipClrRuleDO.setClrMeth("00");
			tPipClrRuleDO.setClrSndGrpFlg("00000000000000000000");		// 清算发送组标志
		}
		return tPipClrRuleDO;
	}

	private TPipClrMertAcctDO buildTPipClrMertAcctDO(String ruleId, IDataset reqDs,String clrTp) {
		TPipClrMertAcctDO tPipClrMertAcctDO = new TPipClrMertAcctDO();
		tPipClrMertAcctDO.setRuleId(ruleId);		// 规则编号
		tPipClrMertAcctDO.setMertNo("0");
		tPipClrMertAcctDO.setIntrmAcctFlg("N");		// 过渡账户标志
		if("01".equals(clrTp)){
			tPipClrMertAcctDO.setIntrmAcctFlg("Y");		// 过渡账户标志
		}
		tPipClrMertAcctDO.setIntrmAcct(reqDs.getString("intrmAcct"));		// 过渡账户
		tPipClrMertAcctDO.setIntrmAcctName(reqDs.getString("intrmAcctName"));		// 过渡账户名称
		tPipClrMertAcctDO.setEntrAcctBankFlg(reqDs.getString("inOutBankFlg"));//内外部行号标志
		tPipClrMertAcctDO.setEntrAcct(reqDs.getString("entrAcct"));		// 单位账号
		tPipClrMertAcctDO.setEntrAcctName(reqDs.getString("entrAcctName"));		// 单位账户名称
		tPipClrMertAcctDO.setEntrAcctBank(reqDs.getString("entrAcctBank"));		// 单位账户行号
		tPipClrMertAcctDO.setShortRmrk(reqDs.getString("shortRmrk")); //过渡账号开户机构
		tPipClrMertAcctDO.setEntrAcctBankName("");	// 单位账户银行名称
		tPipClrMertAcctDO.setPostingSumCode("");		// 过账摘要代码
		tPipClrMertAcctDO.setPostingSumDesc("");		// 过账摘要描述
		
		return tPipClrMertAcctDO;
	}

	private TPipSignRuleDO buildTPipSignRuleDO(String ruleId, IDataset reqDs,String signPat) {
		TPipSignRuleDO tPipClrRuleDO = new TPipSignRuleDO();
		tPipClrRuleDO.setRuleId(ruleId);		// 规则编号
		tPipClrRuleDO.setRuleDesc(reqDs.getString("BUSI_NAME")+"业务签约规则");		// 规则描述
		if("00".equals(signPat)){
			tPipClrRuleDO.setSignFlg("N");		// 签约标志
		}else{
			tPipClrRuleDO.setSignFlg("Y");		// 签约标志
			tPipClrRuleDO.setSignTp("01");		// 签约类型
			tPipClrRuleDO.setNoteTp("01");		// 通知类型
			tPipClrRuleDO.setAcctStatList("");		// 账户状态列表
			tPipClrRuleDO.setVrfyAcctNameFlg("N");		// 核对账号名称标志
			tPipClrRuleDO.setVrfyCertFlg("N");		// 验证证件标志
			tPipClrRuleDO.setVrfyPhoneFlg("N");		// 验证手机标志
			tPipClrRuleDO.setVrfyModBrchFlg("N");		// 核对修改机构标志
			tPipClrRuleDO.setVrfyCanclBrchFlg("N");		// 核对撤销机构标志
			tPipClrRuleDO.setCustDefLimFlg("N");		// 客户自定义限额标志
		}
		return tPipClrRuleDO;
	}

	private TPipClrMertFeeRuleDO buildTPipClrMertFeeRuleDO(String ruleId, IDataset reqDs,String clrTp) {
		TPipClrMertFeeRuleDO tPipClrRuleDO = new TPipClrMertFeeRuleDO();
		tPipClrRuleDO.setRuleId(ruleId);		// 规则编号
		tPipClrRuleDO.setRuleName(reqDs.getString("BUSI_NAME")+"业务手续费清算规则");		// 规则名称
		if("01".equals(clrTp) || "02".equals(clrTp)){//手续费清算
			tPipClrRuleDO.setClrMeth("01");// 清算方式
			tPipClrRuleDO.setClrDimTp("01"); //清算维度类型
			tPipClrRuleDO.setBatProcFlg("1"); //批次处理标志
			tPipClrRuleDO.setClrCycTp("T");		// 清算周期类型
			tPipClrRuleDO.setClrCyc(1);		// 清算周期
			tPipClrRuleDO.setClrSndGrpFlg("11000000000000000000");		// 清算发送组标志
			tPipClrRuleDO.setStrTime("000000");		// 开始时间
			tPipClrRuleDO.setEndTime("235959");		// 结束时间
			tPipClrRuleDO.setBean("MertClrBeanComm");		// BEAN
		}else {//不清算或其他
			tPipClrRuleDO.setClrMeth("00");
			tPipClrRuleDO.setClrSndGrpFlg("00000000000000000000");		// 清算发送组标志
		}
		return tPipClrRuleDO;
	}
	

	private TPipClrMertFeeCaltDO buildTPipClrMertFeeCaltDO(String ruleId, IDataset reqDs,String clrTp) {
		TPipClrMertFeeCaltDO tPipClrMertFeeCaltDO = new TPipClrMertFeeCaltDO();
		tPipClrMertFeeCaltDO.setRuleId(ruleId);		// 规则编号
		tPipClrMertFeeCaltDO.setRuleName(reqDs.getString("BUSI_NAME")+"业务手续费清算规则");		// 规则名称
		if("01".equals(clrTp)) {//01-按成功金额比例收取（商户清算）
			tPipClrMertFeeCaltDO.setMinFee(0.00);
			tPipClrMertFeeCaltDO.setFeeCaltMeth("3");
			tPipClrMertFeeCaltDO.setFeeRuleExpr("0.00|0.00|0.0|"+reqDs.getInt("defFrt"));//起始笔数(金额)|结束笔数（金额）|元/每笔|收费万分比
		}else if("02".equals(clrTp)){//02-按成功笔数收取（商户清算）
			tPipClrMertFeeCaltDO.setMinFee(0.00);
			tPipClrMertFeeCaltDO.setFeeCaltMeth("1");
			tPipClrMertFeeCaltDO.setFeeRuleExpr("0.00|0.00|"+reqDs.getDouble("amt")+"|0");//起始笔数(金额)|结束笔数（金额）|元/每笔|收费万分比
		}
		return tPipClrMertFeeCaltDO;
	}
	private TPipClrMertAcctDO buildTPipClrMertAcctDOForFeeClr(String ruleId, IDataset reqDs,String clrTp) {
		TPipClrMertAcctDO tPipClrMertAcctDO = new TPipClrMertAcctDO();
		tPipClrMertAcctDO.setRuleId(ruleId);		// 规则编号
		tPipClrMertAcctDO.setMertNo("0");
		tPipClrMertAcctDO.setFeeTfOutAcct(reqDs.getString("feeTfOutAcct"));
		tPipClrMertAcctDO.setFeeTfInAcct(reqDs.getString("feeTfInAcct"));
		
		return tPipClrMertAcctDO;
	}

	public IDataset listEntrData(String BUSI_NO) {
		List<FPRodEntrQryBusiEntrNoListListDTO> busiList = new ArrayList();
		TPipBusiDO tPipBusiDO = tPipBusiDao.get(BUSI_NO);
		if (tPipBusiDO != null) {
			FPRodEntrQryBusiEntrNoListListDTO temp = new FPRodEntrQryBusiEntrNoListListDTO();
			temp.setENTR_NO(tPipBusiDO.getENTR_NO());
			EntrDemoDO entrDemoDO = entrDemoDao.get(tPipBusiDO.getEntrNo());
			if (entrDemoDO != null) {
				temp.setENTR_NAME(entrDemoDO.getEntrName());
			}
			busiList.add(temp);
		}

		IDataset responseData = DatasetService.getInstace().getDataset(busiList, FPRodEntrQryBusiEntrNoListListDTO.class);
		return responseData;
	}
	
	/**
	 * 账户基本信息查询
	 * @param reqBody
	 * @return
	 */
	public IDataset factQryAcctInfo(FActQryAcctInfoReqDTO reqBody) {
		IDataset responseData = DatasetService.getInstace().getDataset();
		try {
			ReqDTO req = new ReqDTO(reqBody);
			ProdUtil.setReqHead(req, "FActQryAcctInfo");
			HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
			ResDTO res = (ResDTO) httpJsonFactory.callServiceNoException("", "999102", "FActQryAcctInfo", req,
					FActQryAcctInfoResDTO.class, null);
			FActQryAcctInfoResDTO resBody = (FActQryAcctInfoResDTO) res.getBODY();
			responseData = DatasetService.getInstace().getDataset(resBody, FActQryAcctInfoResDTO.class);
		} catch (Exception e) {
			throw e;
		}
		return responseData;
	}

}
