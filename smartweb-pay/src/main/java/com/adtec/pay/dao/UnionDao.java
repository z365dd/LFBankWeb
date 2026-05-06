package com.adtec.pay.dao;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.impl.share.caseStrategy.MBCCaseStrategy;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.pay.entity.union.UnionExpense;
import com.adtec.pay.entity.union.UnionInst;
import com.adtec.pay.entity.union.UnionParam;
import com.adtec.pay.entity.union.UnionUser;
import com.alibaba.excel.util.ListUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class UnionDao {
    private final static Logger logger = LoggerFactory.getLogger(UnionDao.class);

    /**
     * 新增工会缴费参数
     *
     * @param req
     */
    public void paramAdd(UnionParam req) {
        IDBSession session = DBSessionFactory.getSession();
        String year = req.getYEAR();
        String strDate = req.getSTR_DATE();
        String end_date = req.getEND_DATE();
        String amt = req.getAMT();
        String sql = "INSERT INTO T_MLPP_UNION_PARA(BUSI_NO,BUSI_NAME,SER,KEY_NO,KEY_DESC,QUERY_STAT,EXCEL_STAT) " + "VALUES(?,?,?,?)";

        try {
            session.execute(sql, year, strDate, end_date, amt);
        } catch (SQLException e) {
            throw new BaseException(SysErr.E_MESSAGE, "新增失败" + e.getMessage());
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
    }

    /**
     * 修改工会缴费参数
     *
     * @param req
     */
    public void paramUpdate(UnionParam req) {
        IDBSession session = DBSessionFactory.getSession();
        String year = req.getYEAR();
        String strDate = req.getSTR_DATE();
        String end_date = req.getEND_DATE();
        String amt = req.getAMT();
        String sql = "UPDATE T_MLPP_UNION_PARA SET STR_DATE=?,END_DATE=?,AMT=? WHERE YEAR = ?";

        try {
            session.execute(sql, strDate, end_date, amt, year);
        } catch (SQLException e) {
            throw new BaseException(SysErr.E_MESSAGE, "修改失败" + e.getMessage());
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
    }

    /**
     * 新增工会缴费参数
     *
     * @param req
     */
    public void paramDelete(UnionParam req) {
        IDBSession session = DBSessionFactory.getSession();
        String year = req.getYEAR();
        String sql = "DELETE FROM T_MLPP_UNION_PARA WHERE YAER = ?";

        try {
            session.execute(sql, year);
        } catch (SQLException e) {
            throw new BaseException(SysErr.E_MESSAGE, "删除失败" + e.getMessage());
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
    }

    /**
     * 工会缴费参数统计
     *
     * @param req
     * @return
     */
    public int countPara(UnionParam req) {
        IDBSession session = DBSessionFactory.getSession();
        String year = req.getYEAR();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT  COUNT(1)  FROM T_MLPP_UNION_PARA ");
        List<Object> paramList = ListUtils.newArrayList();
        if (StringUtils.isNotBlank(year)) {
            sql.append(" WHERE YEAR = ?");
            paramList.add(year);
        }

        int account = 0;
        try {
            account = session.accountByList(sql.toString(), paramList);
        } catch (SQLException e) {
            throw new BaseException(SysErr.E_MESSAGE, "查询失败" + e.getMessage());
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return account;
    }

    /**
     * 获取参数列表（分页）
     *
     * @param start
     * @param limit
     * @return
     */
    public List<UnionParam> paraList(int start, int limit) {
        IDBSession session = DBSessionFactory.getSession();
        List<Object> parameters = Lists.newArrayList();
        parameters.add(start);
        parameters.add(limit);
        String sql = "SELECT * FROM T_MLPP_UNION_PARA ORDER BY YEAR LIMIT ?,?";
        List<UnionParam> list = Lists.newArrayList();

        try {
            list = session.getObjectListByList(sql, UnionParam.class, parameters, new MBCCaseStrategy());
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
     * 检查缴费记录是否已提交报销
     *
     * @param req
     * @return
     */
    public int checkExpAppr(UnionExpense req) {
        IDBSession session = DBSessionFactory.getSession();
        String platDate = req.getPLAT_DATE();
        String platSeq = req.getPLAT_SEQ();
        StringBuilder sql = new StringBuilder();
        //查询状态不是拒收的报销记录
        sql.append("SELECT  COUNT(1)  FROM T_MLPP_UNION_EXPENSE WHERE PLAT_DATE=? AND PLAT_SEQ=? AND STAT!='04' ");
        List<Object> paramList = ListUtils.newArrayList();
        paramList.add(platDate);
        paramList.add(platSeq);

        int account = 0;
        try {
            account = session.accountByList(sql.toString(), paramList);
        } catch (SQLException e) {
            throw new BaseException(SysErr.E_MESSAGE, "查询失败" + e.getMessage());
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return account;
    }

    /**
     * 根据条件查询工会审批表数据
     *
     * @param req
     * @param strDate
     * @param endDate
     * @return
     */
    public int countAppr(UnionExpense req, String strDate, String endDate) {
        IDBSession session = DBSessionFactory.getSession();
        String busiName = req.getBUSI_NAME();
        String stat = req.getSTAT();
        String expStat = req.getEXP_STAT();
        String name = req.getNAME();
        String certNo = req.getCERT_NO();
        String platDate = req.getPLAT_DATE();
        String platSeq = req.getPLAT_SEQ();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT  COUNT(1)  FROM T_MLPP_UNION_EXPENSE WHERE 1=1 ");
        List<Object> paramList = ListUtils.newArrayList();

        if (StringUtils.isNotBlank(platDate)) {
            sql.append(" AND PLAT_DATE = ?");
            paramList.add(platDate);
        }
        if (StringUtils.isNotBlank(platSeq)) {
            sql.append(" AND PLAT_SEQ = ?");
            paramList.add(platSeq);
        }

        if (StringUtils.isNotBlank(busiName)) {
            sql.append(" AND BUSI_NAME LIKE ?");
            paramList.add("%" + busiName + "%");
        }
        if (StringUtils.isNotBlank(name)) {
            sql.append(" AND NAME = ?");
            paramList.add(name);
        }
        if (StringUtils.isNotBlank(certNo)) {
            sql.append(" AND CERT_NO = ?");
            paramList.add(certNo);
        }
        if (StringUtils.isNotBlank(stat)) {
            sql.append(" AND STAT = ?");
            paramList.add(stat);
        }
        if (StringUtils.isNotBlank(expStat)) {
            sql.append(" AND EXP_STAT = ?");
            paramList.add(expStat);
        }

        if (StringUtils.isNotBlank(strDate)) {
            sql.append(" AND REG_DATE >= ?");
            paramList.add(strDate);
        }
        if (StringUtils.isNotBlank(endDate)) {
            sql.append(" AND REG_DATE <= ?");
            paramList.add(endDate);
        }

        int account = 0;
        try {
            account = session.accountByList(sql.toString(), paramList);
        } catch (SQLException e) {
            throw new BaseException(SysErr.E_MESSAGE, "查询失败" + e.getMessage());
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return account;
    }


    /**
     * 列表查询
     *
     * @param req
     * @param strDate
     * @param endDate
     * @param start
     * @param limit
     * @return
     */
    public List<UnionExpense> apprList(UnionExpense req, String strDate, String endDate, int start, int limit) {
        IDBSession session = DBSessionFactory.getSession();
        String busiName = req.getBUSI_NAME();
        String stat = req.getSTAT();
        String expStat = req.getEXP_STAT();
        String name = req.getNAME();
        String certNo = req.getCERT_NO();
        List<Object> paramList = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM T_MLPP_UNION_EXPENSE WHERE 1=1 ");
        List<UnionExpense> list = Lists.newArrayList();
        if (StringUtils.isNotBlank(busiName)) {
            sql.append(" AND BUSI_NAME LIKE ?");
            paramList.add("%" + busiName + "%");
        }
        if (StringUtils.isNotBlank(name)) {
            sql.append(" AND NAME = ?");
            paramList.add(name);
        }
        if (StringUtils.isNotBlank(certNo)) {
            sql.append(" AND CERT_NO = ?");
            paramList.add(certNo);
        }
        if (StringUtils.isNotBlank(stat)) {
            sql.append(" AND STAT = ?");
            paramList.add(stat);
        }
        if (StringUtils.isNotBlank(expStat)) {
            sql.append(" AND EXP_STAT = ?");
            paramList.add(expStat);
        }

        if (StringUtils.isNotBlank(strDate)) {
            sql.append(" AND REG_DATE >= ?");
            paramList.add(strDate);
        }
        if (StringUtils.isNotBlank(endDate)) {
            sql.append(" AND REG_DATE <= ?");
            paramList.add(endDate);
        }

        sql.append(" LIMIT ").append(start).append(",").append(limit);

        try {
            list = session.getObjectListByList(sql.toString(), UnionExpense.class, paramList, new MBCCaseStrategy());
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
     * 新增工会报销
     *
     * @param req
     */
    public void expApprAdd(UnionExpense req) {
        IDBSession session = DBSessionFactory.getSession();
        String platDate = req.getPLAT_DATE();
        String platSeq = req.getPLAT_SEQ();
        String regDate = req.getREG_DATE();
        String busiNo = req.getBUSI_NO();
        String busiName = req.getBUSI_NAME();
        String name = req.getNAME();
        String certNo = req.getCERT_NO();
        String phoneNo = req.getPHONE_NO();
        String payAcct = req.getPAY_ACCT();

        String stat = req.getSTAT();
        String expStat = req.getEXP_STAT();
        String sql = "INSERT INTO T_MLPP_UNION_EXPENSE(PLAT_DATE,  PLAT_SEQ,  REG_DATE,  BUSI_NO,  BUSI_NAME,  NAME,  CERT_NO,  PHONE_NO,  PAY_ACCT, STAT,  EXP_STAT) " +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        try {
            session.execute(sql, platDate, platSeq, regDate, busiNo, busiName, name, certNo, phoneNo, payAcct, stat, expStat);
        } catch (SQLException e) {
            throw new BaseException(SysErr.E_MESSAGE, "新增失败" + e.getMessage());
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
    }

    /**
     * 工会报销修改
     *
     * @param req
     */
    public void expApprUpdate(UnionExpense req) {
        IDBSession session = DBSessionFactory.getSession();

        String platDate = req.getPLAT_DATE();
        String platSeq = req.getPLAT_SEQ();
        String certUrl = req.getCERT_URL();
        String payAcctUrl = req.getPAY_ACCT_URL();
        String caseHisUrl = req.getCASE_HIS_URL();
        String billUrl = req.getBILL_URL();
        String stat = req.getSTAT();
        Double amt = req.getAMT();
        StringBuilder sql = new StringBuilder();

        sql.append("UPDATE T_MLPP_UNION_EXPENSE SET ");

        List<Object> paramList = Lists.newArrayList();


        if (StringUtils.isNotBlank(certUrl)) {
            sql.append("CERT_URL = ?,");
            paramList.add(certUrl);
        }
        if (StringUtils.isNotBlank(payAcctUrl)) {
            sql.append("PAY_ACCT_URL = ?,");
            paramList.add(payAcctUrl);
        }
        if (StringUtils.isNotBlank(caseHisUrl)) {
            sql.append("CASE_HIS_URL = ?,");
            paramList.add(caseHisUrl);
        }
        if (StringUtils.isNotBlank(billUrl)) {
            sql.append("BILL_URL = ?,");
            paramList.add(billUrl);
        }
        if (new BigDecimal(amt).setScale(2, BigDecimal.ROUND_HALF_UP).compareTo(BigDecimal.ZERO) > 0) {
            sql.append("AMT = ?,");
            paramList.add(amt);
        }
        if (StringUtils.isNotBlank(stat)) {
            sql.append("STAT = ?,");
            paramList.add(stat);
        }

        //删除最后的，
        sql.deleteCharAt(sql.length() - 1);

        sql.append(" WHERE PLAT_DATE = ? AND PLAT_SEQ=? ");
        paramList.add(platDate);
        paramList.add(platSeq);

        try {
            session.executeByList(sql.toString(), paramList);

        } catch (SQLException e) {
            throw new BaseException(SysErr.E_MESSAGE, "修改失败" + e.getMessage());
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }

    }

    /**
     * 根据当前登录用户获取
     *
     * @param userId
     * @return
     */
    public String getBusiNoByUserId(String userId) {

        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT BUSI_ID FROM T_SYS_BUSI_USER WHERE USER_ID=?");
        String result = "";
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(), userId);
            while (rs.next()) {
                result = (String) rs.getObject(1);
            }
        } catch (SQLException e) {
            throw new BaseException(SysErr.E_MESSAGE, "查询失败" + e.getMessage());
        } finally {
            try {
                session.closeResultSetAndStatement(rs);
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return result;
    }

    /**
     * 统计当前登录用户所属市直工会的待审批用户个数
     *
     * @param unionUser
     * @return
     */
    public int countApprUser(UnionUser unionUser) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();

        String busiNo = unionUser.getBUSI_NO();
        String busiName = unionUser.getBUSI_NAME();
        String name = unionUser.getNAME();
        String certNo = unionUser.getCERT_NO();
        String validFlg = unionUser.getVALID_FLG();
        String stat = unionUser.getSTAT();

        //查询状态不是拒收的报销记录
        sql.append("SELECT  COUNT(1)  FROM T_MLPP_UNION_USER WHERE 1=1 ");
        List<Object> paramList = ListUtils.newArrayList();
        if (StringUtils.isNotBlank(busiNo)) {
            sql.append(" AND BUSI_NO = ?");
            paramList.add(busiNo);
        }
        if (StringUtils.isNotBlank(busiName)) {
            sql.append("AND BUSI_NAME LIKE ?");
            paramList.add("%" + busiName + "%");
        }
        if (StringUtils.isNotBlank(name)) {
            sql.append(" AND NAME = ?");
            paramList.add(name);
        }
        if (StringUtils.isNotBlank(certNo)) {
            sql.append(" AND CERT_NO = ?");
            paramList.add(certNo);
        }
        if (StringUtils.isNotBlank(stat)) {
            sql.append(" AND STAT = ?");
            paramList.add(stat);
        }
        if (StringUtils.isNotBlank(validFlg)) {
            sql.append(" AND VALID_FLG = ?");
            paramList.add(validFlg);
        }

        int account = 0;
        try {
            account = session.accountByList(sql.toString(), paramList);
        } catch (SQLException e) {
            throw new BaseException(SysErr.E_MESSAGE, "查询失败" + e.getMessage());
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return account;
    }

    /**
     * 获取工会用户列表 （分页）
     *
     * @param unionUser
     * @param start
     * @param limit
     * @return
     */
    public List<UnionUser> userList(UnionUser unionUser, int start, int limit) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();

        String busiNo = unionUser.getBUSI_NO();
        String busiName = unionUser.getBUSI_NAME();
        String name = unionUser.getNAME();
        String certNo = unionUser.getCERT_NO();
        String validFlg = unionUser.getVALID_FLG();
        String stat = unionUser.getSTAT();

        //查询状态不是拒收的报销记录
        sql.append("SELECT *  FROM T_MLPP_UNION_USER WHERE 1=1 ");
        List<Object> paramList = ListUtils.newArrayList();
        if (StringUtils.isNotBlank(busiNo)) {
            sql.append(" AND BUSI_NO = ?");
            paramList.add(busiNo);
        }
        if (StringUtils.isNotBlank(busiName)) {
            sql.append("AND BUSI_NAME LIKE ?");
            paramList.add("%" + busiName + "%");
        }
        if (StringUtils.isNotBlank(name)) {
            sql.append(" AND NAME = ?");
            paramList.add(name);
        }
        if (StringUtils.isNotBlank(certNo)) {
            sql.append(" AND CERT_NO = ?");
            paramList.add(certNo);
        }
        if (StringUtils.isNotBlank(stat)) {
            sql.append(" AND STAT = ?");
            paramList.add(stat);
        }
        if (StringUtils.isNotBlank(validFlg)) {
            sql.append(" AND VALID_FLG = ?");
            paramList.add(validFlg);
        }
        sql.append(" LIMIT ").append(start).append(",").append(limit);

        List<UnionUser> list = Lists.newArrayList();
        try {
            list = session.getObjectListByList(sql.toString(), UnionUser.class, paramList, new MBCCaseStrategy());
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
     * 工会用户新增
     *
     * @param req
     */
    public void unionUserAdd(UnionUser req) {
        IDBSession session = DBSessionFactory.getSession();

        String busiNo = req.getBUSI_NO();
        String busiName = req.getBUSI_NAME();
        String name = req.getNAME();
        String certNo = req.getCERT_NO();
        String phoneNo = req.getPHONE_NO();
        String validFlg = req.getVALID_FLG();
        String stat = req.getSTAT();


        String sql = "INSERT INTO T_MLPP_UNION_USER(BUSI_NO,  BUSI_NAME,  NAME,  CERT_NO,  PHONE_NO, STAT,  VALID_FLG) " +
                " VALUES (?,?,?,?,?,?,?)";

        try {
            session.execute(sql, busiNo, busiName, name, certNo, phoneNo, stat, validFlg);
        } catch (SQLException e) {
            throw new BaseException(SysErr.E_MESSAGE, "新增失败" + e.getMessage());
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
    }

    /**
     * 修改工会用户
     *
     * @param req
     */
    public void unionUserUpdate(UnionUser req) {
        IDBSession session = DBSessionFactory.getSession();

        String busiNo = req.getBUSI_NO();
        String certNo = req.getCERT_NO();

        String name = req.getNAME();
        String phoneNo = req.getPHONE_NO();
        String stat = req.getSTAT();

        String validFlg = req.getVALID_FLG();

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE T_MLPP_UNION_USER SET ");

        List<Object> paramList = Lists.newArrayList();

        if (StringUtils.isNotBlank(name)) {
            sql.append("NAME = ?,");
            paramList.add(name);
        }
        if (StringUtils.isNotBlank(phoneNo)) {
            sql.append("PHONE_NO = ?,");
            paramList.add(phoneNo);
        }

        if (StringUtils.isNotBlank(stat)) {
            sql.append("STAT = ?,");
            paramList.add(stat);
        }

        if (StringUtils.isNotBlank(validFlg)) {
            sql.append("VALID_FLG = ?,");
            paramList.add(validFlg);
        }

        //删除最后的，
        sql.deleteCharAt(sql.length() - 1);

        sql.append(" WHERE BUSI_NO = ? AND CERT_NO=? ");
        paramList.add(busiNo);
        paramList.add(certNo);

        try {
            session.executeByList(sql.toString(), paramList);

        } catch (SQLException e) {
            throw new BaseException(SysErr.E_MESSAGE, "修改失败" + e.getMessage());
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
    }


    /**
     * 工会用户删除
     *
     * @param req
     */
    public void unionUserDelete(UnionUser req) {
        IDBSession session = DBSessionFactory.getSession();

        String busiNo = req.getBUSI_NO();
        String certNo = req.getCERT_NO();

        String sql = "DELETE FROM T_MLPP_UNION_USER WHERE BUSI_NO=? AND CERT_NO=?";

        try {
            session.execute(sql, busiNo, certNo);
        } catch (SQLException e) {
            throw new BaseException(SysErr.E_MESSAGE, "新增失败" + e.getMessage());
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
    }

    /**
     * 机构列表查询（分页）
     *
     * @param unionInst
     * @param addr
     * @param start
     * @param limit
     * @return
     */
    public List<UnionInst> unionInstList(UnionInst unionInst, String addr, int start, int limit) {
        IDBSession session = DBSessionFactory.getSession();
        String brchNo = unionInst.getBRCH_NO();
        String brchName = unionInst.getBRCH_NAME();
        String brchTp = unionInst.getBRCH_TP();

        List<Object> paramList = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT F.BRCH_NO,F.BRCH_NAME,F.ADDR,D.ENTR_ACCT,D.ENTR_ACCT_NAME,A.LONG_RMRK AS PAY_INFO,A.ENTR_ADDR,A.ENTR_TEL_NO,A.CTCT_PER_NAME,A.OPEN_STAT " +
                "FROM  T_PIP_ENTR A " +
                "LEFT JOIN T_PIP_BUSI B ON A.ENTR_NO = B.ENTR_NO " +
                "LEFT JOIN T_PIP_RULE_RELAT C ON B.BUSI_NO = C.BUSI_NO " +
                "LEFT JOIN T_PIP_CLR_MERT_ACCT D ON C.RULE_ID = D.RULE_ID " +
                "LEFT JOIN T_SYS_OFFICE E ON D.ENTR_ACCT_BANK = E.BRCH_CODE " +
                "LEFT JOIN T_MLPP_UNION_INST F ON B.BUSI_NO = F.BRCH_NO " +
                "WHERE A.SHORT_RMRK ='10' AND C.RULE_TP ='201' WHERE 1=1");
        List<UnionInst> list = Lists.newArrayList();

        if (StringUtils.isNotBlank(brchNo)) {
            sql.append(" AND BRCH_NO = ?");
            paramList.add(brchNo);
        }
        if (StringUtils.isNotBlank(brchName)) {
            sql.append(" AND BRCH_NAME LIKE ?");
            paramList.add("%" + brchName + "%");
        }

        if (StringUtils.isNotBlank(brchTp)) {
            sql.append(" AND BRCH_TP = ?");
            paramList.add(brchTp);
        }

        if (StringUtils.isNotBlank(addr)) {
            sql.append(" AND F.ADDR = ?");
            paramList.add("%" + addr + "%");
        }

        sql.append(" LIMIT ").append(start).append(",").append(limit);

        try {
            list = session.getObjectListByList(sql.toString(), UnionInst.class, paramList, new MBCCaseStrategy());
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
     * 获取机构列表个数
     *
     * @param unionInst
     * @param addr
     * @return
     */
    public int countUnionInst(UnionInst unionInst, String addr) {
        IDBSession session = DBSessionFactory.getSession();
        String brchNo = unionInst.getBRCH_NO();
        String brchName = unionInst.getBRCH_NAME();
        String brchTp = unionInst.getBRCH_TP();

        List<Object> paramList = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();


        sql.append("SELECT COUNT(*) FROM  T_PIP_ENTR A " +
                "LEFT JOIN T_PIP_BUSI B ON A.ENTR_NO = B.ENTR_NO " +
                "LEFT JOIN T_PIP_RULE_RELAT C ON B.BUSI_NO = C.BUSI_NO " +
                "LEFT JOIN T_PIP_CLR_MERT_ACCT D ON C.RULE_ID = D.RULE_ID " +
                "LEFT JOIN T_SYS_OFFICE E ON D.ENTR_ACCT_BANK = E.BRCH_CODE " +
                "LEFT JOIN T_MLPP_UNION_INST F ON B.BUSI_NO = F.BRCH_NO " +
                "WHERE A.SHORT_RMRK ='10' AND C.RULE_TP ='201' WHERE 1=1");

        List<UnionInst> list = Lists.newArrayList();

        if (StringUtils.isNotBlank(brchNo)) {
            sql.append(" AND F.BRCH_NO = ?");
            paramList.add(brchNo);
        }
        if (StringUtils.isNotBlank(brchName)) {
            sql.append(" AND F.BRCH_NAME LIKE ?");
            paramList.add("%" + brchName + "%");
        }

        if (StringUtils.isNotBlank(brchTp)) {
            sql.append(" AND F.BRCH_TP = ?");
            paramList.add(brchTp);
        }

        if (StringUtils.isNotBlank(addr)) {
            sql.append(" AND F.ADDR = ?");
            paramList.add("%" + addr + "%");
        }

        int account = 0;

        try {
            account = session.accountByList(sql.toString(), paramList);
        } catch (SQLException e) {
            throw new BaseException(SysErr.E_MESSAGE, "查询失败" + e.getMessage());
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return account;
    }


    /**
     * 获取最大单位编号以及最大
     */
    public List<String> getMaxSer() {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT MAX(A.ENTR_NO) AS ENTR_NO ,MAX(B.RULE_ID) AS RULE_ID FROM T_PIP_BUSI A LEFT JOIN T_PIP_RULE_RELAT B ON A.BUSI_NO =B.BUSI_NO  WHERE SALE_PROD_CODE = '010008' AND RULE_TP='101'");
        List<String> list = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString());
            while (rs.next()) {
                list.add((String) rs.getObject(1));
                list.add((String) rs.getObject(2));
            }
        } catch (SQLException e) {
            throw new BaseException(SysErr.E_MESSAGE, "查询失败" + e.getMessage());
        } finally {
            try {
                session.closeResultSetAndStatement(rs);
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return list;
    }


    /**
     * 新增、修改商户
     *
     * @param req
     * @return
     */
    public int unionInstAdd(Map<String, Object> req) {
        String entrNo = (String) req.get("entrNo");
        String busiPara1 = (String) req.get("busiPara1");
        String busiPara2 = (String) req.get("busiPara2");
        String busiPara3 = (String) req.get("busiPara3");
        String busiPara4 = (String) req.get("busiPara4");

        String busiNo = (String) req.get("busiNo");
        String busiName = (String) req.get("busiName");
        String brchId = (String) req.get("brchId");
        String brchName = (String) req.get("brchName");
        String acct = (String) req.get("acct");
        String acctName = (String) req.get("acctName");
        String payInfo = (String) req.get("payInfo");
        String phoneNo = (String) req.get("phoneNo");
        String name = (String) req.get("name");
        String openStat = (String) req.get("openStat");
        String addr = (String) req.get("addr");
        String brchTp = (String) req.get("brchTp");


        ExecuteTask<SmsSqlTask<Integer>, Integer> task = new ExecuteTask();

        return task.sqlTaskNeedTransactional("新增工会信息", session -> {
            //工会
            session.execute("INSERT INTO T_MLPP_UNION_BRCH (BRCH_NO,BRCH_NAME,BRCH_TP,UP_BRCH,ADDR) VALUES (?,?,?,?,?,?)",busiNo,busiName,brchTp,'1',addr);
            //企业单位表
            session.execute("INSERT INTO T_PIP_ENTR (ENTR_NO,OPEN_STAT,ENTR_NAME,ENTR_NATURE,ENTR_CERT_TP,ENTR_CERT_NO,LEGA_NAME,LEGA_CERT_TP,LEGA_CERT_NO,ENTR_ADDR,ENTR_TEL_NO,EMAIL,CTCT_PER_NAME,CTCT_PHONE_NO,SND_TRAN_DATE,SND_TRAN_CMPLE_TIME,REG_BRCH,REG_TLR_NO,MOD_DATE,MOD_TIME,MOD_BRCH,MOD_TLR_NO,URL,LAST_UPT_TIME,COMN_DESC,CRTR,CRT_TIME,UPTR,UPT_TIME,BELONG_LEGA_NO,SHORT_RMRK,MID_RMRK,LONG_RMRK,DAC) VALUES (?,?,?,'1','C001','',?,'P000','',?,?,'',?,'','','','','','','','',NULL,'',NULL,?,'1','2024-03-07 11:49:41','1','2024-05-17 16:52:17',NULL,'10','',?,'')", entrNo, openStat, busiName, busiName, addr, phoneNo, name, busiName, payInfo);
            //业务信息表
            session.execute("INSERT INTO T_PIP_BUSI (BUSI_NO,ENTR_NO,BUSI_NAME,SALE_PROD_CODE,SALE_PROD_TP,BUSI_TMPL,OPEN_STAT,BUSI_DESC,OPEN_GRP_CHNL_NO,CLR_TP,SIGN_PAT,CHK_PAT,FEE_TP,BUSI_TP,RELAT_SYS,CLR_DATE,BRCH_TP,CRTR,BG_IMG,UPT_TIME,LAST_UPT_TIME,CRT_TIME,MOD_STAT,UPTR,BRCH_ID,SHORT_RMRK,MID_RMRK,LONG_RMRK,DAC) VALUES (?,?,?,'010008',NULL,NULL,'Y',?,NULL,'00','01','01','00','01','OFFLINE',NULL,'1','1','','2024-03-07 11:53:44',NULL,'2024-03-07 11:53:44',NULL,'1','1','','','','')", busiNo, entrNo, busiName, busiName);
            //业务参数表
            session.execute("INSERT INTO T_PIP_BUSI_PARA (BUSI_NO,BUSI_NAME,KEY_TP,KEY_NO,KEY_NAME,KV,LAST_UPT_TIME,MOD_STAT,SHORT_RMRK,MID_RMRK,LONG_RMRK,DAC) VALUES (?,?,'',?,'记账摘要码','MB8078',NULL,NULL,'','','','')", busiNo, busiName, "SumCode");
            session.execute("INSERT INTO T_PIP_BUSI_PARA (BUSI_NO,BUSI_NAME,KEY_TP,KEY_NO,KEY_NAME,KV,LAST_UPT_TIME,MOD_STAT,SHORT_RMRK,MID_RMRK,LONG_RMRK,DAC) VALUES (?,?,'',?,'缴费业务支持缴费类型','01|02|03|04|06',NULL,NULL,'','','','')", busiNo, busiName, "PayType");
            session.execute("INSERT INTO T_PIP_BUSI_PARA (BUSI_NO,BUSI_NAME,KEY_TP,KEY_NO,KEY_NAME,KV,LAST_UPT_TIME,MOD_STAT,SHORT_RMRK,MID_RMRK,LONG_RMRK,DAC) VALUES (?,?,'',?,'核心业务产品参数','0063',NULL,NULL,'','','','')", busiNo, busiName, "BusiKdNo");
            session.execute("INSERT INTO T_PIP_BUSI_PARA (BUSI_NO,BUSI_NAME,KEY_TP,KEY_NO,KEY_NAME,KV,LAST_UPT_TIME,MOD_STAT,SHORT_RMRK,MID_RMRK,LONG_RMRK,DAC) VALUES (?,?,'',?,'记账摘要描述','教育缴费',NULL,NULL,'','','','')", busiNo, busiName, "SumDesc");
            //业务机构开通表
            session.execute("INSERT INTO T_PIP_BUSI_BRCH_OPEN (BUSI_NO,BRCH,FLG,SHORT_RMRK,MID_RMRK,LONG_RMRK,DAC) VALUES (?,'','Y','','','','')", busiNo);
            //业务渠道开通表
            session.execute("INSERT INTO T_PIP_BUSI_CHNL_OPEN (BUSI_NO,CHNL_NO,FLG,SHORT_RMRK,MID_RMRK,LONG_RMRK,DAC) VALUES (?,'030','Y',NULL,NULL,NULL,NULL)", busiNo);
            session.execute("INSERT INTO T_PIP_BUSI_CHNL_OPEN (BUSI_NO,CHNL_NO,FLG,SHORT_RMRK,MID_RMRK,LONG_RMRK,DAC) VALUES (?,'057','Y',NULL,NULL,NULL,NULL)", busiNo);
            session.execute("INSERT INTO T_PIP_BUSI_CHNL_OPEN (BUSI_NO,CHNL_NO,FLG,SHORT_RMRK,MID_RMRK,LONG_RMRK,DAC) VALUES (?,'125','Y',NULL,NULL,NULL,NULL)", busiNo);
            session.execute("INSERT INTO T_PIP_BUSI_CHNL_OPEN (BUSI_NO,CHNL_NO,FLG,SHORT_RMRK,MID_RMRK,LONG_RMRK,DAC) VALUES (?,'160','Y',NULL,NULL,NULL,NULL)", busiNo);

            //规则关系表
            session.execute("INSERT INTO T_PIP_RULE_RELAT (BUSI_NO,SVC_CODE,SCENE_NO,RULE_TP,RULE_ID,LAST_UPT_TIME,SHORT_RMRK,MID_RMRK,LONG_RMRK,DAC) VALUES (?,'0','0','101',?,NULL,'','','','')", busiNo, busiPara1);
            session.execute("INSERT INTO T_PIP_RULE_RELAT (BUSI_NO,SVC_CODE,SCENE_NO,RULE_TP,RULE_ID,LAST_UPT_TIME,SHORT_RMRK,MID_RMRK,LONG_RMRK,DAC) VALUES (?,'0','0','201',?,NULL,'','','','')", busiNo, busiPara2);
            session.execute("INSERT INTO T_PIP_RULE_RELAT (BUSI_NO,SVC_CODE,SCENE_NO,RULE_TP,RULE_ID,LAST_UPT_TIME,SHORT_RMRK,MID_RMRK,LONG_RMRK,DAC) VALUES (?,'0','0','301',?,NULL,'','','','')", busiNo, busiPara3);
            session.execute("INSERT INTO T_PIP_RULE_RELAT (BUSI_NO,SVC_CODE,SCENE_NO,RULE_TP,RULE_ID,LAST_UPT_TIME,SHORT_RMRK,MID_RMRK,LONG_RMRK,DAC) VALUES (?,'0','0','401',?,NULL,'','','','')", busiNo, busiPara4);
            //商户清算表
            session.execute("INSERT INTO T_PIP_CLR_MERT_ACCT (RULE_ID,MERT_NO,MERT_NAME,UP_MERT_NO,INTRM_ACCT_FLG,INTRM_ACCT,INTRM_ACCT_NAME,ENTR_ACCT,ENTR_ACCT_NAME,ENTR_ACCT_BANK,ENTR_ACCT_BANK_NAME,ENTR_ACCT_BANK_FLG,SUM_CODE,SUM_DESC,POSTING_SUM_CODE,POSTING_SUM_DESC,FEE_TF_OUT_ACCT,FEE_TF_OUT_ACCT_NAME,FEE_TF_IN_ACCT,FEE_TF_IN_ACCT_NAME,FEE_SUM_CODE,FEE_SUM_DESC,SHORT_RMRK,MID_RMRK,LONG_RMRK,DAC) VALUES (?,'0','','','Y','600001012240100150069','生活缴费待划转款项',?,?,?,?,'00','','','','','','','','','','','','','','')", busiPara2, acct, acctName, brchId, brchName);
            //商户清算规则表
            session.execute("INSERT INTO T_PIP_CLR_MERT_RULE (RULE_ID,RULE_NAME,CLR_METH,CLR_DIM_TP,BAT_PROC_FLG,CLR_SND_GRP_FLG,CLR_CYC_TP,CLR_CYC,STR_TIME,END_TIME,BEAN,AUTO_CLR_CHNL_NO,SHORT_RMRK,MID_RMRK,LONG_RMRK,DAC) VALUES (?,?,'00','','','00000000000000000000','',0,'','','','','','','','')", busiPara2, busiName + "业务本金清算规则");

            //对账规则表
            session.execute("INSERT INTO T_PIP_CHK_RULE (RULE_ID,RULE_NAME,CHK_TP,CHK_SND_GRP_FLG,CHK_CYC_TP,CHK_CYC,STR_TIME,END_TIME,CHK_RSLT_PUSH_FLG,CHK_RSLT_FILE_NAME,CHK_FILE_DOWNLOAD_FLG,OTH_CHK_FILE_NAME,CHK_SWITCH_FLG,ERR_SWITCH_FLG,BEAN,UP_RULE_ID,CHK_NO,CHK_RULE_ID,SHORT_RMRK,MID_RMRK,LONG_RMRK,DAC) VALUES (?,?,'1','11000000000000000000','T',1,'000000','235959','1','','','','0','0',?,'',?,'1010000000000','','','','')", busiPara1, busiName + "业务对账规则", "ChkBeanComn", busiNo + "-" + busiPara1);
            //清算规则表
            session.execute("INSERT INTO T_MLPP_CLR_RUL (BUSI_NO,BUSI_NAME,CHK_TP,CLR_TP,RFND_TP,NOTE_TP,ENTR_ACCT,ENTR_ACCT_NAME,INTRM_ACCT,INTRM_ACCT_NAME,TEMP_ACCT,TEMP_ACCT_NAME,BRCH,TLR_NO,DAY_NOTE_TP,REG_TIME,SHORT_RMRK,MID_RMRK,LONG_RMRK,DAC) VALUES (?,?,'2','0','2','2',?,?,'600001012240100150069','生活缴费待划转款项',NULL,NULL,'600001','admin','2','20230923170251',NULL,NULL,NULL,NULL)", busiNo, busiName, acct, acctName);
            //pdf模板业务映射表
            return session.execute("INSERT INTO T_MLPP_OFFLINE_BUSI_TMPL (BUSI_NO,BUSI_NAME,BUSI_TP,TMPL_ID,TMPL_DESC,STAT,RMRK) VALUES (?,?,NULL,?,NULL,NULL,NULL)", busiNo, busiName, "schoolTemplate.pdf");
        });

    }

    /**
     * 新增、修改商户
     *
     * @param req
     * @return
     */
    public int unionInstUpdate(Map<String, Object> req) {
        String busiNo = (String) req.get("busiNo");
        String busiName = (String) req.get("busiName");
        String brchId = (String) req.get("brchId");
        String brchName = (String) req.get("brchName");
        String acct = (String) req.get("acct");
        String acctName = (String) req.get("acctName");
        String phoneNo = (String) req.get("phoneNo");
        String name = (String) req.get("name");
        String openStat = (String) req.get("openStat");
        String addr = (String) req.get("addr");
        String brchTp = (String) req.get("brchTp");

        ExecuteTask<SmsSqlTask<Integer>, Integer> task = new ExecuteTask();

        return task.sqlTaskNeedTransactional("修改工会信息", session -> {
            //修改工会机构表内容
            session.execute("UPDATE T_MLPP_UNION_BRCH SET BRCH_NAME=?,BRCH_TP,ADDR WHERE BRCH_NO=?", busiName,brchTp,addr, busiNo);
            //修改商户名称
            session.execute("UPDATE T_PIP_BUSI SET BUSI_NAME=? WHERE BUSI_NO=?", busiName, busiNo);

            //修改  是否拆分支付、是否T1清算、地址、咨询电话、联系人姓名、商户状态
            session.execute("UPDATE T_PIP_ENTR A SET A.ENTR_NAME=?,A.ENTR_ADDR =?,A.ENTR_TEL_NO =?,A.CTCT_PER_NAME =?,A.OPEN_STAT =? WHERE EXISTS (SELECT 1 FROM T_PIP_BUSI B WHERE A.ENTR_NO=B.ENTR_NO AND B.BUSI_NO=?)", busiName, addr, phoneNo, name, openStat, busiNo);
            //修改 清算账户、清算账户名称、清算账户所属机构
            return session.execute("UPDATE T_PIP_CLR_MERT_ACCT A SET A.ENTR_ACCT =?,A.ENTR_ACCT_NAME =?,A.ENTR_ACCT_BANK =?,A.ENTR_ACCT_BANK_NAME=? WHERE EXISTS (SELECT 1 FROM T_PIP_RULE_RELAT B WHERE A.RULE_ID=B.RULE_ID AND B.RULE_TP='201' AND B.BUSI_NO=?)", acct, acctName, brchId, brchName, busiNo);
        });

    }
}
