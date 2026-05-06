package com.adtec.pay.dao;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.impl.share.caseStrategy.MBCCaseStrategy;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.pay.dto.bus.BusListQryReq;
import com.adtec.pay.entity.buscard.BuscardAcctStatement;
import com.adtec.pay.entity.buscard.BuscardAcctStatementDtl;
import org.apache.axis.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 公交数据操作类
 */
@Repository
public class BuscardDao {

    private final static Logger logger = LoggerFactory.getLogger(BuscardDao.class);
    /**
     * 获取激活、充值、退卡数据
     * @param params
     * @return
     */
    public List<BuscardAcctStatement> getList(Map<String, String> params){
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT '廊坊银行' AS BUSI_NAME,CONCAT(SUBSTR(CLR_DATE,1,4),'-',SUBSTR(CLR_DATE,5,2),'-',SUBSTR(CLR_DATE,7,2)) AS TRAN_DATE,FEE_TOT_NUM AS ACTIVE_NUM,FEE_TOT_AMT AS ACTIVE_AMT,CRFLD_TOT_NUM AS RECHARGE_NUM,CRFLD_TOT_AMT AS RECHARGE_AMT,RFND_TOT_NUM AS RTN_NUM,(0-RFND_TOT_AMT) AS RTN_AMT,NETG_AMT AS TOT_AMT,0 AS ELE_RECHARGE_NUM,0 AS ELE_RECHARGE_AMT,CLR_AMT,(CLR_AMT- NETG_AMT) AS PC_AMT " +
                "FROM T_BUSCARD_CLR_COUNT WHERE BUSI_NO = ? AND CLR_DATE >= ? AND CLR_DATE <= ? ORDER BY CLR_DATE");
        List<Object> parameters = new ArrayList<>();
        parameters.add(params.get("busiNo"));
        parameters.add(params.get("strDate"));
        parameters.add(params.get("endDate"));
        List<BuscardAcctStatement> list = new ArrayList<>();
        try {
            list = session.getObjectListByList(sql.toString(), BuscardAcctStatement.class, parameters, new MBCCaseStrategy());
        } catch (SQLException e) {
            throw new BaseException(SysErr.E_MESSAGE, "查询失败" + e.getMessage());
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return list;
    }

    /**
     * 获取激活、充值、退卡的明细数据
     * @param params
     * @return
     */
    public List<BuscardAcctStatementDtl> getDtlList(Map<String, String> params){
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT PLAT_DATE AS TRAN_DATE,BRCH,TLR_NO,APP_ID,TRAN_AMT,CASE WHEN TRAN_TP = '01' THEN '激活' WHEN TRAN_TP = '02' THEN '圈存' WHEN TRAN_TP = '03' THEN '退卡' END TRAN_TP,CASE WHEN TRAN_TP = '03' THEN PAYEE_ACCT ELSE PAY_ACCT END  ACCT,OTH_SEQ,CASE WHEN TRAN_STAT = '01' THEN '成功' WHEN TRAN_STAT = '02' THEN '失败' WHEN TRAN_STAT = '03' THEN '超时' END TRAN_STAT,CASE WHEN CONFM_STAT = '00' THEN '未确认' WHEN CONFM_STAT = '10' THEN '确认成功' WHEN CONFM_STAT = '20' THEN '确认失败' END CONFM_STAT FROM T_BUSCARD_JRNL ")
        .append("WHERE PLAT_DATE >= ? AND PLAT_DATE <= ? ");
        List<Object> parameters = new ArrayList<>();
        parameters.add(params.get("strDate"));
        parameters.add(params.get("endDate"));
        String tranTp = params.get("tranTp");
        if(!StringUtils.isEmpty(tranTp)){
            sql.append("AND TRAN_TP = ? ");
            parameters.add(tranTp);
        }
        String stat = params.get("stat");
        if(!StringUtils.isEmpty(stat)){
            sql.append("AND TRAN_STAT = ? ");
            parameters.add(stat);
        }
        String acct = params.get("acct");
        if(!StringUtils.isEmpty(acct)){
            sql.append("AND ( PAY_ACCT = ? OR PAYEE_ACCT = ? )");
            parameters.add(acct);
            parameters.add(acct);
        }
        String appId = params.get("appId");
        if(!StringUtils.isEmpty(appId)){
            sql.append("AND APP_ID = ? ");
            parameters.add(appId);
        }
        String confmStat = params.get("confmStat");
        if(!StringUtils.isEmpty(confmStat)){
            sql.append("AND CONFM_STAT = ? ");
            parameters.add(confmStat);
        }
        sql.append("ORDER BY PLAT_DATE");
        logger.info("sql:{}", sql.toString());
        List<BuscardAcctStatementDtl> list = new ArrayList<>();
        try {
            list = session.getObjectListByList(sql.toString(), BuscardAcctStatementDtl.class, parameters, new MBCCaseStrategy());
        } catch (SQLException e) {
            throw new BaseException(SysErr.E_MESSAGE, "查询失败" + e.getMessage());
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return list;
    }

    /**
     * 获取合计数据
     * @param params
     * @return
     */
    public List<BuscardAcctStatement> getTotData(Map<String, String> params) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT SUM(FEE_TOT_NUM) AS ACTIVE_NUM,SUM(FEE_TOT_AMT) AS ACTIVE_AMT,0 AS ELE_RECHARGE_AMT,0 AS ELE_RECHARGE_NUM,SUM(CRFLD_TOT_NUM) AS RECHARGE_NUM,SUM(CRFLD_TOT_AMT) AS RECHARGE_AMT,SUM(RFND_TOT_NUM) AS RTN_NUM,(0-SUM(RFND_TOT_AMT)) AS RTN_AMT,SUM(NETG_AMT) AS TOT_AMT,SUM(CLR_AMT) AS CLR_AMT,SUM(CLR_AMT-NETG_AMT) AS PC_AMT FROM T_BUSCARD_CLR_COUNT ")
        .append("WHERE BUSI_NO = ? AND CLR_DATE >= ? AND CLR_DATE <= ? ");
        List<Object> parameters = new ArrayList<>();
        parameters.add(params.get("busiNo"));
        parameters.add(params.get("strDate"));
        parameters.add(params.get("endDate"));
        List<BuscardAcctStatement> list = new ArrayList<>();
        try {
            list = session.getObjectListByList(sql.toString(), BuscardAcctStatement.class, parameters, new MBCCaseStrategy());
        } catch (SQLException e) {
            throw new BaseException(SysErr.E_MESSAGE, "查询失败" + e.getMessage());
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return list;
    }


}
