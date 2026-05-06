package com.adtec.pay.dao;

import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.impl.share.caseStrategy.MBCCaseStrategy;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.pay.entity.BatDtl;
import com.adtec.sys.common.dao.IBaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 批量缴费明细数据层操作类
 */
@Repository
public class BatDao implements IBaseDao<BatDtl> {

    private final static String TABLE_NAME = "T_MLPP_OFFLINE_DTL";
    private final static String STAT_INIT = "00";

    private final static Logger logger = LoggerFactory.getLogger(BatDao.class);

    @Override
    public int insert(BatDtl objDO) {
        return 0;
    }

    @Override
    public int update(BatDtl objDO) {
        return 0;
    }

    @Override
    public int delete(BatDtl objDO) {
        return 0;
    }

    @Override
    public BatDtl get(BatDtl objDO) {
        return null;
    }

    @Override
    public List<BatDtl> list(BatDtl objDO) {
        return null;
    }

    @Override
    public List<BatDtl> list(BatDtl objDO, int start, int limit) {
        return null;
    }

    @Override
    public List<BatDtl> list(int start, int limit, Object... param) {
        return null;
    }

    public int save(BatDtl obj, String busiNo, String busiName, String batNo, String batName, Long ser) {
//        ExecuteTask<SmsSqlTask<Integer>, Integer> task = new ExecuteTask();
//        String batDate = DateUtil.getDate();
//        return task.sqlTaskNeedTransactional("批量数据解析入库", session -> {
//            String sql = String.format("INSERT INTO %s (BUSI_NO,BUSI_NAME,BAT_DATE,BAT_NO,BAT_NAME,SUB_SER,STAT,TRAN_TP,PAY_NO,NAME,PHONE_NO,OWE_MONTH,TOT_AREA,UNIT_PRICE,PRCTL_AMT,TOT_AMT,DCT_AMT,DCT_QTA,LATE_FEE_AMT,PROJ_NAME,ADDR,COMMUNITY,BUID_NO,UNIT_NO,ROOM_NO,CERT_NO) " +
//                    "VALUES(?, ?, ?, ?, ?, ?, ?,'01', ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?)", TABLE_NAME);
//            return session.execute(sql,busiNo,busiName,batDate,batNo,batName,ser,STAT_INIT,
//                    obj.getPAY_NO(),obj.getNAME(),obj.getPHONE_NO(),obj.getOWE_MONTH(),
//                    obj.getTOT_AREA(),obj.getUNIT_PRICE(),obj.getPRCTL_AMT(),obj.getTOT_AMT(),
//                    obj.getDCT_AMT(),obj.getDCT_QTA(),obj.getLATE_FEE_AMT(),obj.getPROJ_NAME(),
//                    obj.getADDR(), obj.getCOMMUNITY(),obj.getBUID_NO(),obj.getUNIT_NO(),
//                    obj.getROOM_NO(), obj.getCERT_NO());
//        });
        String batDate = DateUtil.getDate();
        IDBSession session = DBSessionFactory.getSession();
        String sql = String.format("INSERT INTO %s (BUSI_NO,BUSI_NAME,BAT_DATE,BAT_NO,BAT_NAME,SUB_SER,STAT,TRAN_TP,PAY_NO,NAME,PHONE_NO,OWE_MONTH,TOT_AREA,UNIT_PRICE,PRCTL_AMT,TOT_AMT,DCT_AMT,DCT_QTA,LATE_FEE_AMT,PROJ_NAME,ADDR,COMMUNITY,BUID_NO,UNIT_NO,ROOM_NO,CERT_NO) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?,'01', ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?)", TABLE_NAME);
        int result = 0;
        try {
            result = session.execute(sql, busiNo, busiName, batDate, batNo, batName, ser, STAT_INIT,
                    obj.getPAY_NO(), obj.getNAME(), obj.getPHONE_NO(), obj.getOWE_MONTH(),
                    obj.getTOT_AREA(), obj.getUNIT_PRICE(), obj.getPRCTL_AMT(), obj.getTOT_AMT(),
                    obj.getDCT_AMT(), obj.getDCT_QTA(), obj.getLATE_FEE_AMT(), obj.getPROJ_NAME(),
                    obj.getADDR(), obj.getCOMMUNITY(), obj.getBUID_NO(), obj.getUNIT_NO(),
                    obj.getROOM_NO(), obj.getCERT_NO());
        } catch (SQLException throwables) {
            throw new BaseException(SysErr.E_MESSAGE, "第[" + ser + "]条数据插入失败");
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return result;
    }

    public List<BatDtl> findAll(String busiNo, String batNo) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ").append(TABLE_NAME).append(" WHERE BUSI_NO= '").append(busiNo).append("' ");
        sql.append("AND BAT_NO = '").append(batNo).append("'");
        List<BatDtl> list = new ArrayList<>();
        try {
            list = session.getObjectList(sql.toString(), BatDtl.class, new MBCCaseStrategy());
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
