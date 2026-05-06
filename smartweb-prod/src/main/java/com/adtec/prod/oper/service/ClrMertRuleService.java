package com.adtec.prod.oper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adtec.prod.oper.dao.BusiSignDao;
import com.adtec.prod.oper.dao.TPipClrMertAcctDao;
import com.adtec.prod.oper.dao.TPipClrMertRuleDao;
import com.adtec.prod.oper.entity.TPipClrMertAcctDO;
import com.adtec.prod.oper.entity.TPipClrMertRuleDO;

@Service
@Transactional(readOnly = true)
public class ClrMertRuleService {
	@Autowired
	private TPipClrMertRuleDao TPipClrMertRuleDao;
	@Autowired
	private BusiSignDao busiSignDao;
	@Autowired
	private TPipClrMertAcctDao clrMertAcctDao;

	/**
	 * 列表查询
	 * @param dO
	 * @return
	 */
	public List<TPipClrMertRuleDO> list(TPipClrMertRuleDO dO) {
		return TPipClrMertRuleDao.list(dO);
	}

	/**
	 * 修改配置
	 * @param dO
	 */
	public int update(TPipClrMertRuleDO tpipClrMertRuleDO,List<TPipClrMertAcctDO> tpipClrMertAcctDOList) {
		int ret=0;
		//更新T_PIP_BUSI表标志
		String flgVal="";
		if("00".equals(tpipClrMertRuleDO.getClrMeth())) {//不清算
			flgVal = "00";
		}else if("01".equals(tpipClrMertRuleDO.getClrMeth())) {//本金清算
			flgVal = "01";
		}else {//其他
			flgVal = "02";
		}
		busiSignDao.updateByRule("CLR", tpipClrMertRuleDO.getRuleId(), flgVal);
		
		//商户账号更新
		clrMertAcctDao.delete(tpipClrMertRuleDO.getRuleId());
		for(TPipClrMertAcctDO reqTPipClrMertAcctDO:tpipClrMertAcctDOList) {
			reqTPipClrMertAcctDO.setRuleId(tpipClrMertRuleDO.getRuleId());
			clrMertAcctDao.insert(reqTPipClrMertAcctDO);
		}
		
		//清算规则修改
		ret=TPipClrMertRuleDao.update(tpipClrMertRuleDO);
		
		return ret;
		
	}

	/**
	 * 获取总条数
	 * @param dO
	 * @return
	 */
	public int getTotal(TPipClrMertRuleDO dO) {
		return TPipClrMertRuleDao.getTotal(dO);
		
	}

	/**
	 * 获取详情
	 * @param dO
	 * @return
	 */
	public TPipClrMertRuleDO getDetail(TPipClrMertRuleDO dO) {
		return TPipClrMertRuleDao.get(dO);
	}

}
