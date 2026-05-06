package com.adtec.prod.oper.service;

import java.util.List;

import com.adtec.prod.oper.dao.BusiSignDao;
import com.adtec.prod.oper.dao.TPipChkRuleDao;
import com.adtec.prod.oper.entity.TPipChkRuleDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChkRuleService {
	@Autowired
	private TPipChkRuleDao tPipChkRuleDao;
	@Autowired
	private BusiSignDao busiSignDao;

	/**
	 * 查询列表
	 * @param dO
	 * @return
	 */
	public List<TPipChkRuleDO> list(TPipChkRuleDO dO) {
		return tPipChkRuleDao.list(dO);
	}

	/**
	 * 获取总条数
	 * @param dO
	 * @return
	 */
	public int getTotal(TPipChkRuleDO dO) {
		return tPipChkRuleDao.getTotal(dO);
	}

	public int update(TPipChkRuleDO dO) {
		//更新T_PIP_BUSI表标志
		String flgVal="";
		if("1".equals(dO.getChkTp())) {//两方对账
			flgVal = "01";
		}else if("2".equals(dO.getChkTp())) {//三方对账
			flgVal = "02";
		}else {//不对账
			flgVal = "00";
		}
		busiSignDao.updateByRule("CHK", dO.getRuleId(), flgVal);
		
		//对账规则更新
		return tPipChkRuleDao.update(dO);
	}

	public TPipChkRuleDO getDetail(TPipChkRuleDO dO) {
		return tPipChkRuleDao.get(dO);

	}
	
}
