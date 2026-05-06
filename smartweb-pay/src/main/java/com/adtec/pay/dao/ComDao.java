package com.adtec.pay.dao;

import com.adtec.framework.common.util.StringUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.impl.share.caseStrategy.MBCCaseStrategy;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.pay.entity.LimitAmtDO;
import com.alibaba.excel.util.ListUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComDao {
    private final static Logger logger = LoggerFactory.getLogger(ComDao.class);

    private static final ComDao comDao;

    private static final String LIMIT_PARA_TABLE = "T_MLPP_LIMIT_PARA";

    static {
        synchronized (ComDao.class) {
            comDao = new ComDao();
        }
    }

    public static ComDao getInstance() {
        return comDao;
    }

    /**
     * 统计限额商户列表
     *
     * @param reqBody

     * @param start
     * @param limit
     * @return
     */
    public List<LimitAmtDO> getLimAmtList(Map<String, Object> reqBody, int start, int limit) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        List<Object> paramList = ListUtils.newArrayList();
        sql.append("SELECT  *  FROM T_MLPP_LIMIT_PARA ");
        String busiNo = (String) reqBody.get("busiNo");
        //根据业务编号查询数据
        if (StringUtils.isBlank(busiNo)) {
            List<String> list = (List<String>) reqBody.get("busiNoList");
            if(!CollectionUtils.isEmpty(list)){
                sql.append(" BUSI_NO IN (");
                for (String item : list) {
                    sql.append("?,");
                }
                sql.deleteCharAt(sql.length() - 1);
                sql.append(")");
                paramList.addAll(list);
            }

        }else{
            sql.append("WHERE BUSI_NO = ?");
            paramList.add(busiNo);
        }

        sql.append(" LIMIT ?,? ");
        paramList.add(start);
        paramList.add(limit);
        List<LimitAmtDO> list = ListUtils.newArrayList();
        try {
            list = session.getObjectListByList(sql.toString(), LimitAmtDO.class, paramList, new MBCCaseStrategy());
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
     * 统计限额商户个数
     * @param reqBody
     * @return
     */
    public int countLimAmtList(Map<String, Object> reqBody) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        List<Object> paramList = ListUtils.newArrayList();
        sql.append("SELECT  COUNT(1)  FROM T_MLPP_LIMIT_PARA ");
        String busiNo = (String) reqBody.get("busiNo");
        //根据业务编号查询数据
        if (StringUtils.isBlank(busiNo)) {
            List<String> list = (List<String>) reqBody.get("busiNoList");
            if(!CollectionUtils.isEmpty(list)){
                sql.append(" BUSI_NO IN (");
                for (String item : list) {
                    sql.append("?,");
                }
                sql.deleteCharAt(sql.length() - 1);
                sql.append(")");
                paramList.addAll(list);
            }
        }else{
            sql.append("WHERE BUSI_NO = ?");
            paramList.add(busiNo);
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
     * 限额商户新增
     * @param reqBody
     */
    public void limitAmtAdd(HashMap<String, Object> reqBody) {
        IDBSession session = DBSessionFactory.getSession();
        String sql = String.format("INSERT INTO %s(BUSI_NO,BUSI_NAME,LIM_AMT,DAY_AMT) " +
                "VALUES(?,?,?,?)", LIMIT_PARA_TABLE);

        try {
            session.execute(sql, reqBody.get("busiNo"), reqBody.get("busiName"), reqBody.get("lmtAmt"), reqBody.get("dayAmt"));
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
     * 限额商户信息修改
     * @param reqBody
     */
    public int limitAmtModify(HashMap<String, Object> reqBody) {
        IDBSession session = DBSessionFactory.getSession();

        String sql = String.format("UPDATE %s SET LIM_AMT = ?,DAY_AMT = ? WHERE BUSI_NO = ?", LIMIT_PARA_TABLE);
        int rs = 0;
        try {
            rs = session.execute(sql, reqBody.get("lmtAmt"), reqBody.get("dayAmt"), reqBody.get("busiNo"));
        } catch (SQLException e) {
            throw new BaseException(SysErr.E_MESSAGE, "修改失败" + e.getMessage());
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
     *
     * @param busiType 业务类型
     * @param officeId 机构号  多个机构号用,分隔
     * @return
     */
    public List<String> getBusiNoByOfficeId(String busiType,String officeId) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        List<Object> paramList = ListUtils.newArrayList();
        sql.append("SELECT B.BUSI_NO FROM  T_PIP_ENTR A  " +
                "LEFT JOIN T_PIP_BUSI B ON A.ENTR_NO = B.ENTR_NO  " +
                "LEFT JOIN T_PIP_RULE_RELAT C ON B.BUSI_NO = C.BUSI_NO  " +
                "LEFT JOIN T_PIP_CLR_MERT_ACCT D ON C.RULE_ID = D.RULE_ID  " +
                "LEFT JOIN T_SYS_OFFICE E ON D.ENTR_ACCT_BANK = E.BRCH_CODE  " +
                "WHERE A.SHORT_RMRK = ? AND C.RULE_TP ='201' ");
        paramList.add(busiType);
        if(officeId.contains(",")){
            sql.append("AND E.ID IN (");
            String[] officeIds = officeId.split(",");
            for (String id : officeIds) {
                sql.append("?,");
                paramList.add(id);
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(")");
        }else{
            if(!StringUtil.isBlank(officeId)){
                sql.append("AND E.ID = ?");
                paramList.add(officeId);
            }
        }
        List<String> list = ListUtils.newArrayList();
        ResultSet rs = null;
        try {
            rs = session.getResultSetByList(sql.toString(),paramList);
            while (rs.next()) {
                list.add((String) rs.getObject(1));
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
}
