package com.adtec.pay.dao;


import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.impl.share.caseStrategy.MBCCaseStrategy;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.pay.dto.offline.MerInfo;
import com.adtec.pay.entity.ykt.YKTPara;
import com.alibaba.excel.util.ListUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class YKTDao {

    private final static Logger logger = LoggerFactory.getLogger(YKTDao.class);

    //新增
    private final static String ADD = "1";

    /**
     * 查询符合条件的一卡通配置参数数量
     *
     * @param req
     * @return
     */
    public int countPara(YKTPara req) {
        IDBSession session = DBSessionFactory.getSession();
        String busiNo = req.getBUSI_NO();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT  COUNT(1)  FROM T_MLPP_LIM_PARA ");
        List<Object> paramList = ListUtils.newArrayList();
        if (StringUtils.isNotBlank(busiNo)) {
            sql.append(" WHERE BUSI_NO = ?");
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
     * 一卡通配置参数分页查询
     *
     * @param req
     * @param start
     * @param limit
     * @return
     */
    public List<YKTPara> paraList(YKTPara req, int start, int limit) {
        IDBSession session = DBSessionFactory.getSession();

        String busiNo = req.getBUSI_NO();
        StringBuilder sql = new StringBuilder();
        List<Object> paramList = Lists.newArrayList();

        sql.append("SELECT * FROM T_MLPP_LIM_PARA WHERE 1=1 ");
        if (StringUtils.isNotBlank(busiNo)) {
            sql.append(" AND BUSI_NO = ? ");
            paramList.add(busiNo);
        }
        sql.append(" LIMIT ").append(start).append(",").append(limit);

        List<YKTPara> list = Lists.newArrayList();

        try {
            list = session.getObjectListByList(sql.toString(), YKTPara.class, paramList, new MBCCaseStrategy());
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
     * 新增一卡通参数配置
     *
     * @param req
     */
    public void paramAdd(YKTPara req) {
        IDBSession session = DBSessionFactory.getSession();
        String busiNo = req.getBUSI_NO();
        String busiName = req.getBUSI_NAME();
        String tranAmt = req.getTRAN_AMT().toString();
        String dayAmt = req.getDAY_AMT().toString();

        String sql = "INSERT INTO T_MLPP_LIM_PARA(BUSI_NO,  BUSI_NAME,  TRAN_AMT,  DAY_AMT) " +
                " VALUES (?,?,?,?)";

        try {
            session.execute(sql, busiNo, busiName, tranAmt, dayAmt);
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
     * 修改一卡通参数配置
     *
     * @param req
     */
    public void paramUpdate(YKTPara req) {
        IDBSession session = DBSessionFactory.getSession();

        String busiNo = req.getBUSI_NO();
        String busiName = req.getBUSI_NAME();
        String tranAmt = req.getTRAN_AMT().toString();
        String dayAmt = req.getDAY_AMT().toString();
        StringBuilder sql = new StringBuilder();

        sql.append("UPDATE T_MLPP_LIM_PARA SET ");

        List<Object> paramList = Lists.newArrayList();

        if (StringUtils.isNotBlank(tranAmt)) {
            sql.append("TRAN_AMT = ?,");
            paramList.add(tranAmt);
        }
        if (StringUtils.isNotBlank(dayAmt)) {
            sql.append("DAY_AMT = ?,");
            paramList.add(dayAmt);
        }

        //删除最后的，
        sql.deleteCharAt(sql.length() - 1);

        sql.append(" WHERE BUSI_NO = ? ");
        paramList.add(busiNo);

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
     * 删除一卡通参数配置
     *
     * @param req
     */
    public void paramDelete(YKTPara req) {

    }

    /**
     * 查询一卡通商户个数
     *
     * @param reqBody
     * @return
     */
    public int countMerList(Map<String, String> reqBody) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        List<Object> parameters = Lists.newArrayList();
        sql.append("SELECT COUNT(*) FROM  T_PIP_ENTR A " +
                "LEFT JOIN T_PIP_BUSI B ON A.ENTR_NO = B.ENTR_NO " +
                "LEFT JOIN T_PIP_RULE_RELAT C ON B.BUSI_NO = C.BUSI_NO " +
                "LEFT JOIN T_PIP_CLR_MERT_ACCT D ON C.RULE_ID = D.RULE_ID " +
                "LEFT JOIN T_SYS_OFFICE E ON D.ENTR_ACCT_BANK = E.BRCH_CODE " +
                "WHERE A.SHORT_RMRK =? AND C.RULE_TP ='201' ");
        String busiNo1 = reqBody.get("busiNo");
        String busiNos = reqBody.get("busiNos");
        String busiName = reqBody.get("busiName");
        String officeId = reqBody.get("officeId");
        //商户类型
        String busiTp = reqBody.get("busiTp");
        parameters.add(busiTp);

        if (!StringUtils.isEmpty(busiNo1)) {
            sql.append("AND B.BUSI_NO = ? ");
            parameters.add(busiNo1);
        }

        if (!StringUtils.isEmpty(busiNos)) {
            sql.append("AND B.BUSI_NO IN ( ");
            String[] busiList = busiNos.split(";");
            for (String busiNo : busiList) {
                sql.append("?,");
                parameters.add(busiNo);
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(")");
        }

        if (!StringUtils.isEmpty(busiName)) {
            sql.append("AND BUSI_NAME LIKE ?");
            parameters.add("%" + busiName + "%");
        }

        if (!StringUtils.isEmpty(officeId)) {
            String[] officeIds = officeId.split(",");
            sql.append(" AND E.ID IN (");
            for (String item : officeIds) {
                sql.append("?,");
                parameters.add(item);
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(")");
        }

        int account = 0;
        try {
            account = session.accountByList(sql.toString(), parameters);
        } catch (SQLException e) {
            logger.info("sql语句为：" + sql.toString());
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
     * 根据商户类型查询商户列表
     *
     * @param reqBody
     * @param start
     * @param limit
     * @return
     */
    public List<MerInfo> getMerList(Map<String, String> reqBody, int start, int limit) {
        IDBSession session = DBSessionFactory.getSession();
        List<MerInfo> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        List<Object> parameters = Lists.newArrayList();
        sql.append("SELECT B.BUSI_NO,B.BUSI_NAME, E.BRCH_CODE AS BRCH_ID,E.NAME AS BRCH_NAME,D.ENTR_ACCT,D.ENTR_ACCT_NAME,A.LONG_RMRK AS PAY_INFO,A.ENTR_ADDR,A.ENTR_TEL_NO,A.CTCT_PER_NAME AS NAME,A.OPEN_STAT FROM T_PIP_ENTR A " +
                "LEFT JOIN T_PIP_BUSI B ON A.ENTR_NO = B.ENTR_NO " +
                "LEFT JOIN T_PIP_RULE_RELAT C ON B.BUSI_NO = C.BUSI_NO " +
                "LEFT JOIN T_PIP_CLR_MERT_ACCT D ON C.RULE_ID = D.RULE_ID " +
                "LEFT JOIN T_SYS_OFFICE E ON D.ENTR_ACCT_BANK = E.BRCH_CODE " +
                "WHERE A.SHORT_RMRK = ? AND C.RULE_TP ='201' ");
        String busiTp = reqBody.get("busiTp");
        parameters.add(busiTp);

        String busiNo1 = reqBody.get("busiNo");
        String busiNos = reqBody.get("busiNos");
        String busiName = reqBody.get("busiName");
        String officeId = reqBody.get("officeId");

        if (!StringUtils.isEmpty(busiNo1)) {
            sql.append(" AND B.BUSI_NO = ? ");
            parameters.add(busiNo1);
        }

        if (!StringUtils.isEmpty(officeId)) {
            String[] officeIds = officeId.split(",");
            sql.append(" AND E.ID IN (");
            for (String item : officeIds) {
                sql.append("?,");
                parameters.add(item);
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(")");
        }

        if (!StringUtils.isEmpty(busiNos)) {
            sql.append("AND B.BUSI_NO IN ( ");
            String[] busiList = busiNos.split(";");
            for (String busiNo : busiList) {
                sql.append("?,");
                parameters.add(busiNo);
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(")");
        }

        if (!StringUtils.isEmpty(busiName)) {
            sql.append("AND BUSI_NAME LIKE ?");
            parameters.add("%" + busiName + "%");
        }
        //按照修改日期倒序展示
        sql.append("ORDER BY A.UPT_TIME DESC ");
        sql.append("LIMIT ").append(start).append(",").append(limit);
        try {
            list = session.getObjectListByList(sql.toString(), MerInfo.class, parameters, new MBCCaseStrategy());
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
     * 获取最大单位编号以及最大
     */
    public List<String> getMaxSer() {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT MAX(A.ENTR_NO) AS ENTR_NO ,MAX(B.RULE_ID) AS RULE_ID FROM T_PIP_BUSI A LEFT JOIN T_PIP_RULE_RELAT B ON A.BUSI_NO =B.BUSI_NO  WHERE RULE_TP='101'");
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
     * 商户信息新增、修改
     * @param req
     * @return
     */
    public int modifyMerchan(Map<String, Object> req) {
        String operStat = (String) req.get("operStat");

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
        ExecuteTask<SmsSqlTask<Integer>, Integer> task = new ExecuteTask();
        if (operStat.equals(ADD)) {
            return task.sqlTaskNeedTransactional("新增商户信息", session -> {
                //企业单位表
                session.execute("INSERT INTO T_PIP_ENTR (ENTR_NO,OPEN_STAT,ENTR_NAME,ENTR_NATURE,ENTR_CERT_TP,ENTR_CERT_NO,LEGA_NAME,LEGA_CERT_TP,LEGA_CERT_NO,ENTR_ADDR,ENTR_TEL_NO,EMAIL,CTCT_PER_NAME,CTCT_PHONE_NO,SND_TRAN_DATE,SND_TRAN_CMPLE_TIME,REG_BRCH,REG_TLR_NO,MOD_DATE,MOD_TIME,MOD_BRCH,MOD_TLR_NO,URL,LAST_UPT_TIME,COMN_DESC,CRTR,CRT_TIME,UPTR,UPT_TIME,BELONG_LEGA_NO,SHORT_RMRK,MID_RMRK,LONG_RMRK,DAC) VALUES (?,?,?,'1','C001','',?,'P000','',?,?,'',?,'','','','','','','','',NULL,'',NULL,?,'1','2024-03-07 11:49:41','1','2024-05-17 16:52:17',NULL,'20','',?,'')", entrNo, openStat, busiName, busiName, addr, phoneNo, name, busiName, payInfo);
                //业务信息表
                session.execute("INSERT INTO T_PIP_BUSI (BUSI_NO,ENTR_NO,BUSI_NAME,SALE_PROD_CODE,SALE_PROD_TP,BUSI_TMPL,OPEN_STAT,BUSI_DESC,OPEN_GRP_CHNL_NO,CLR_TP,SIGN_PAT,CHK_PAT,FEE_TP,BUSI_TP,RELAT_SYS,CLR_DATE,BRCH_TP,CRTR,BG_IMG,UPT_TIME,LAST_UPT_TIME,CRT_TIME,MOD_STAT,UPTR,BRCH_ID,SHORT_RMRK,MID_RMRK,LONG_RMRK,DAC) VALUES (?,?,?,'010010',NULL,NULL,'Y',?,NULL,'00','01','01','00','00','YKT',NULL,'1','1','','2024-03-07 11:53:44',NULL,'2024-03-07 11:53:44',NULL,'1','1','20','','','')", busiNo, entrNo, busiName, busiName);
                //业务参数表
                session.execute("INSERT INTO T_PIP_BUSI_PARA (BUSI_NO,BUSI_NAME,KEY_TP,KEY_NO,KEY_NAME,KV,LAST_UPT_TIME,MOD_STAT,SHORT_RMRK,MID_RMRK,LONG_RMRK,DAC) VALUES (?,?,'',?,'记账摘要码','MB8082',NULL,NULL,'','','','')", busiNo, busiName,"SumCode");
                session.execute("INSERT INTO T_PIP_BUSI_PARA (BUSI_NO,BUSI_NAME,KEY_TP,KEY_NO,KEY_NAME,KV,LAST_UPT_TIME,MOD_STAT,SHORT_RMRK,MID_RMRK,LONG_RMRK,DAC) VALUES (?,?,'',?,'缴费业务支持缴费类型','01|02|03|04|06',NULL,NULL,'','','','')", busiNo, busiName,"PayType");
                session.execute("INSERT INTO T_PIP_BUSI_PARA (BUSI_NO,BUSI_NAME,KEY_TP,KEY_NO,KEY_NAME,KV,LAST_UPT_TIME,MOD_STAT,SHORT_RMRK,MID_RMRK,LONG_RMRK,DAC) VALUES (?,?,'',?,'核心业务产品参数','0090',NULL,NULL,'','','','')", busiNo, busiName,"BusiKdNo");
                session.execute("INSERT INTO T_PIP_BUSI_PARA (BUSI_NO,BUSI_NAME,KEY_TP,KEY_NO,KEY_NAME,KV,LAST_UPT_TIME,MOD_STAT,SHORT_RMRK,MID_RMRK,LONG_RMRK,DAC) VALUES (?,?,'',?,'记账摘要描述','充值',NULL,NULL,'','','','')", busiNo, busiName,"SumDesc");
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

                //清算规则表    NOTE_TP  2-对账后发送对账文件
                return session.execute("INSERT INTO T_MLPP_CLR_RUL (BUSI_NO,BUSI_NAME,CHK_TP,CLR_TP,RFND_TP,NOTE_TP,ENTR_ACCT,ENTR_ACCT_NAME,INTRM_ACCT,INTRM_ACCT_NAME,TEMP_ACCT,TEMP_ACCT_NAME,BRCH,TLR_NO,DAY_NOTE_TP,REG_TIME,SHORT_RMRK,MID_RMRK,LONG_RMRK,DAC) VALUES (?,?,'2','0','2','1',?,?,'600001012240100150069','生活缴费待划转款项',NULL,NULL,'600001','admin','2','20230923170251',NULL,NULL,NULL,NULL)", busiNo, busiName, acct, acctName);
            });
        } else {
            return task.sqlTaskNeedTransactional("修改商户信息", session -> {
                //修改商户名称
                session.execute("UPDATE T_PIP_BUSI SET BUSI_NAME=? WHERE BUSI_NO=?", busiName, busiNo);

                //修改  是否拆分支付、是否T1清算、地址、咨询电话、联系人姓名、商户状态
                session.execute("UPDATE T_PIP_ENTR A SET A.ENTR_NAME=?,A.ENTR_ADDR =?,A.ENTR_TEL_NO =?,A.CTCT_PER_NAME =?,A.OPEN_STAT =? WHERE EXISTS (SELECT 1 FROM T_PIP_BUSI B WHERE A.ENTR_NO=B.ENTR_NO AND B.BUSI_NO=?)", busiName, addr, phoneNo, name, openStat, busiNo);
                //修改 清算账户、清算账户名称、清算账户所属机构
                return session.execute("UPDATE T_PIP_CLR_MERT_ACCT A SET A.ENTR_ACCT =?,A.ENTR_ACCT_NAME =?,A.ENTR_ACCT_BANK =?,A.ENTR_ACCT_BANK_NAME=? WHERE EXISTS (SELECT 1 FROM T_PIP_RULE_RELAT B WHERE A.RULE_ID=B.RULE_ID AND B.RULE_TP='201' AND B.BUSI_NO=?)", acct, acctName, brchId, brchName, busiNo);
            });
        }
    }
}
