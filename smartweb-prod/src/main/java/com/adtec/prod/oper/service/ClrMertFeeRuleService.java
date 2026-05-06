package com.adtec.prod.oper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adtec.prod.oper.dao.BusiSignDao;
import com.adtec.prod.oper.dao.TPipClrMertAcctDao;
import com.adtec.prod.oper.dao.TPipClrMertFeeCaltDao;
import com.adtec.prod.oper.dao.TPipClrMertFeeRuleDao;
import com.adtec.prod.oper.entity.TPipClrMertAcctDO;
import com.adtec.prod.oper.entity.TPipClrMertFeeCaltDO;
import com.adtec.prod.oper.entity.TPipClrMertFeeRuleDO;

@Service
@Transactional(readOnly = true)
public class ClrMertFeeRuleService {
	@Autowired
	private TPipClrMertFeeRuleDao TPipClrMertFeeRuleDao;
	@Autowired
	private BusiSignDao busiSignDao;
	@Autowired
	private TPipClrMertAcctDao clrMertAcctDao;
	@Autowired
	private TPipClrMertFeeCaltDao clrMertFeeCaltDao;

	/**
	 * 列表查询
	 * @param dO
	 * @return
	 */
	public List<TPipClrMertFeeRuleDO> list(TPipClrMertFeeRuleDO dO) {
		return TPipClrMertFeeRuleDao.list(dO);
	}

	/**
	 * 修改配置
	 * @param dO
	 */
	public int update(TPipClrMertFeeRuleDO tpipClrMertFeeRuleDO,List<TPipClrMertAcctDO> tpipClrMertAcctDOList,TPipClrMertFeeCaltDO tpipClrMertFeeCaltDO) {
		int ret=0;
		//更新T_PIP_BUSI表标志
		String flgVal="";
		if("00".equals(tpipClrMertFeeRuleDO.getClrMeth())) {//不清算
			flgVal = "00";
		}else if("01".equals(tpipClrMertFeeRuleDO.getClrMeth())) {//手续费清算
			flgVal = "01";
		}else {//其他
			flgVal = "01";
		}
		busiSignDao.updateByRule("FEE_CLR", tpipClrMertFeeRuleDO.getRuleId(), flgVal);
		
		//商户账号更新
		clrMertAcctDao.delete(tpipClrMertFeeRuleDO.getRuleId());
		for(TPipClrMertAcctDO reqTPipClrMertAcctDO:tpipClrMertAcctDOList) {
			reqTPipClrMertAcctDO.setRuleId(tpipClrMertFeeRuleDO.getRuleId());
			clrMertAcctDao.insert(reqTPipClrMertAcctDO);
		}
		
		//计算表更新
		clrMertFeeCaltDao.update(tpipClrMertFeeCaltDO);
		
		//清算规则修改
		ret=TPipClrMertFeeRuleDao.update(tpipClrMertFeeRuleDO);
		
		return ret;
		
	}

	/**
	 * 获取详情
	 * @param dO
	 * @return
	 */
	public TPipClrMertFeeRuleDO getDetail(TPipClrMertFeeRuleDO dO) {
		return TPipClrMertFeeRuleDao.get(dO);
	}

}
