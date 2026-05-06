package com.adtec.pay.dao;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.impl.share.caseStrategy.MBCCaseStrategy;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.pay.dto.recorded.RecQryReq;
import com.adtec.pay.dto.recorded.RecQryRes;
import com.adtec.pay.dto.recorded.RecQryResList;
import com.adtec.pay.entity.ReceiptPrint;
import com.adtec.sys.common.dao.IBaseDao;
import com.alibaba.excel.util.ListUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReceiptDao implements IBaseDao<T> {
    private final static Logger logger = LoggerFactory.getLogger(OfflineDao.class);

    private final static String TABLE_NAME = "T_MLPP_CHNL_COUNT";

    @Override
    public int insert(T objDO) {
        return 0;
    }

    @Override
    public int update(T objDO) {
        return 0;
    }

    @Override
    public int delete(T objDO) {
        return 0;
    }

    @Override
    public T get(T objDO) {
        return null;
    }

    @Override
    public List<T> list(T objDO) {
        return null;
    }

    @Override
    public List<T> list(T objDO, int start, int limit) {
        return null;
    }

    @Override
    public List<T> list(int start, int limit, Object... param) {
        return null;
    }

    /**
     * 获取回单打印excel数据
     * 0-现金
     * 1-转账
     * 2-微信
     * 3-支付宝
     * 4-pos
     * 5-本行卡支付（小惠发起）
     * 6-批量扣款
     * 其中2、3、4为跨行支付  其余均为本行支付
     *
     * @return
     */
    public List<ReceiptPrint> getExcelData(String busiNo, String strDate, String endDate, String chnlNo) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT tmcc.CLR_DATE ," +
                "( CASE " +
                "WHEN tmcc.FEE_AMT > 0 THEN '跨行交易' " +
                "ELSE '本行交易' " +
                "END ) PAY_TP," +
                "( CASE " +
                "WHEN RELAT_SYS = 'QQWT' THEN '水费' " +
                "END ) TYPE," +
                "tmcc.BUSI_NAME AS BUSI_NAME," +
                "( CASE " +
                "WHEN tmcc.CHNL_NO = '000' THEN '柜面' " +
                "WHEN tmcc.CHNL_NO = '008' THEN 'ATM' " +
                "WHEN tmcc.CHNL_NO = '030' THEN '手机银行' " +
                "WHEN tmcc.CHNL_NO = '047' THEN 'VTM' " +
                "WHEN tmcc.CHNL_NO = '160' THEN '生活缴费平台' " +
                "WHEN tmcc.CHNL_NO = '800' THEN '核心' " +
                "END ) REMARK, " +
                "tmcc.TOT_AMT AS TRAN_AMT, " +
                "tmcc.TOT_NUM AS TRAN_NUM, " +
                "tmcc.CLR_AMT AS CLR_AMT " +
                "FROM T_MLPP_CHNL_COUNT tmcc LEFT JOIN T_PIP_BUSI tpb ON tmcc.BUSI_NO = tpb.BUSI_NO " +
                "WHERE tmcc.BUSI_NO = ? AND tmcc.CLR_DATE >= ? AND tmcc.CLR_DATE <= ? ");
        ArrayList<Object> paramLists = ListUtils.newArrayList();
        paramLists.add(busiNo);
        paramLists.add(strDate);
        paramLists.add(endDate);
        if (StringUtils.isNotBlank(chnlNo)) {
            sb.append(" AND tmcc.CHNL_NO = ?");
            paramLists.add(chnlNo);
        }
        sb.append("ORDER BY tmcc.BUSI_NAME,tmcc.CLR_DATE,tmcc.FEE_AMT,tmcc.CHNL_NO,RELAT_SYS");
        List<ReceiptPrint> list = Lists.newArrayList();
        try {
            list = session.getObjectListByList(sb.toString(), ReceiptPrint.class, paramLists, new MBCCaseStrategy());
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

    public int checkPrint(String busiNo, String strDate, String endDate) {
        IDBSession session = DBSessionFactory.getSession();
        String sql = String.format("SELECT COUNT(*) FROM T_MLPP_CLR_CTRL " +
                "WHERE BUSI_NO = ? AND CLR_DATE >= ? AND CLR_DATE <= ? AND STAT != '90' ");
        int rs = 0;
        List<Object> paramLists = ListUtils.newArrayList();
        paramLists.add(busiNo);
        paramLists.add(strDate);
        paramLists.add(endDate);

        try {
            rs = session.accountByList(sql, paramLists);
        } catch (SQLException e) {
            throw new BaseException(SysErr.E_MESSAGE, "查询失败" + e.getMessage());
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return rs;
    }

    /**
     * 获取回单打印列表查询数据
     *
     * @param req
     * @return
     */
    public RecQryRes getSumData(RecQryReq req) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT SUM(TOT_NUM) AS TOT_NUM,SUM(TOT_AMT) AS TOT_AMT FROM ")
                .append(TABLE_NAME)
                .append(" WHERE BUSI_NO = ? AND CLR_DATE >= ? AND CLR_DATE <= ? ");
        List<Object> paramLists = ListUtils.newArrayList();
        paramLists.add(req.getBUSI_NO());
        paramLists.add(req.getSTR_DATE());
        paramLists.add(req.getEND_DATE());
        String chnlNo = req.getCHNL_NO();
        if (StringUtils.isNotBlank(chnlNo)) {
            sql.append("AND CHNL_NO = ? ");
            paramLists.add(chnlNo);
        }
        List<RecQryRes> list = ListUtils.newArrayList();
        try {
            list = session.getObjectListByList(sql.toString(), RecQryRes.class, paramLists, new MBCCaseStrategy());
        } catch (SQLException e) {
            throw new BaseException(SysErr.E_MESSAGE, "查询失败" + e.getMessage());
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return list.get(0);
    }

    public List<RecQryResList> getQryList(RecQryReq req, int start, int limit) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CLR_DATE,CHNL_NO,SUM(TOT_NUM) AS TOT_NUM,SUM(TOT_AMT) AS TOT_AMT,SUM(CLR_AMT) AS CLR_AMT,SUM(DCT_AMT) AS DCT_AMT,SUM(FEE_AMT) AS FEE_AMT ")
                .append(" FROM ").append(TABLE_NAME)
                .append(" WHERE BUSI_NO = ? AND CLR_DATE >= ? AND CLR_DATE <= ? ");
        List<Object> paramLists = ListUtils.newArrayList();
        paramLists.add(req.getBUSI_NO());
        paramLists.add(req.getSTR_DATE());
        paramLists.add(req.getEND_DATE());
        String chnlNo = req.getCHNL_NO();
        if (StringUtils.isNotBlank(chnlNo)) {
            sql.append("AND CHNL_NO = ? ");
            paramLists.add(chnlNo);
        }
        sql.append(" GROUP BY CLR_DATE,CHNL_NO").append(" ORDER BY CLR_DATE DESC").append(" LIMIT ?,?");
        paramLists.add(start);
        paramLists.add(limit);
        List<RecQryResList> list = ListUtils.newArrayList();
        try {
            list = session.getObjectListByList(sql.toString(), RecQryResList.class, paramLists, new MBCCaseStrategy());
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
     * 获取记录条数
     *
     * @param req
     * @return
     */
    public int countQryList(RecQryReq req) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(1) FROM ").append(TABLE_NAME).append(" WHERE BUSI_NO = ? AND CLR_DATE >= ? AND CLR_DATE <= ? ");
        String chnlNo = req.getCHNL_NO();
        List<Object> paramLists = ListUtils.newArrayList();
        int num = 0;
        paramLists.add(req.getBUSI_NO());
        paramLists.add(req.getSTR_DATE());
        paramLists.add(req.getEND_DATE());
        if (StringUtils.isNotBlank(chnlNo)) {
            sql.append("AND CHNL_NO = ? ");
            paramLists.add(chnlNo);
        }

        try {
            num = session.accountByList(sql.toString(), paramLists);
        } catch (SQLException e) {
            throw new BaseException(SysErr.E_MESSAGE, "查询失败" + e.getMessage());
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return num;
    }
}
