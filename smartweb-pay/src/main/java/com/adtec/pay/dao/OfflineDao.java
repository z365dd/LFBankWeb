package com.adtec.pay.dao;

import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.impl.share.caseStrategy.MBCCaseStrategy;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.pay.dto.bookList.MLppQryBookListReq;
import com.adtec.pay.dto.bookList.OfflineBook;
import com.adtec.pay.dto.offline.DataInfo;
import com.adtec.pay.dto.offline.MerInfo;
import com.adtec.pay.dto.offline.MergeOfflineDtl;
import com.adtec.pay.dto.template.PdfTemplate;
import com.adtec.pay.entity.OfflineDO;
import com.adtec.pay.entity.ProjDo;
import com.adtec.pay.entity.Template;
import com.adtec.pay.web.data.OfflineOpt;
import com.adtec.sys.common.dao.IBaseDao;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class OfflineDao implements IBaseDao<T> {

    private final static Logger logger = LoggerFactory.getLogger(OfflineDao.class);
    //脱机缴费项目表
    private static final String PROJ_TABLE = "T_MLPP_OFFLINE_PROJ";
    //脱机模板字段明细表
    private static final String TMPL_FIELD_TABLE = "T_MLPP_OFFLINE_TMPL_DTL";
    //脱机业务pdf模板映射表
    private static final String TMPL_BUSI_TABLE = "T_MLPP_OFFLINE_BUSI_TMPL";
    //脱机业务模板关系表
    private static final String T_MLPP_BOOK = "T_MLPP_BOOK";
    //脱机模板字段明细表
    private static final String TMPL_TABLE = "T_MLPP_OFFLINE_TMPL";
    //脱机缴费明细表
    private static final String DTL_TABLE = "T_MLPP_OFFLINE_DTL";
    //脱机缴费任务表
    private static final String CTRL_TABLE = "T_MLPP_OFFLINE_CTRL";
    //  初始化状态
    private final static String STAT_INIT = "00";

    private final static String NO = "N";
    private final static String YES = "Y";

    private final static String ADD = "1";
    private final static String UPT = "2";


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
     * 导入明细数据入库
     *
     * @param obj
     * @param busiNo
     * @param busiName
     * @param projectName
     * @param projTp
     * @param oweMonth
     * @param ser
     * @return
     */
    public int save(OfflineDO obj, String busiNo, String busiName, String projectName, String projTp, String oweMonth, long ser) {
        //获取创建日期
        String createDate = DateUtil.getDate();
        IDBSession session = DBSessionFactory.getSession();
        String sql = String.format("INSERT INTO %s (BUSI_NO,BUSI_NAME,CRT_DATE,PROJ_NAME,PROJ_TP,SUB_SER,STAT,NAME,PHONE_NO,TOT_AMT,PRCTL_AMT,CERT_NO,STU_CLASS,STU_ID,MAJOR,OWE_MONTH,CLR_FLG,LOCK_STAT) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?)", DTL_TABLE);
        int result = 0;
        try {
            result = session.execute(sql, busiNo, busiName, createDate, projectName,
                    projTp, ser, STAT_INIT, obj.getNAME(), obj.getPHONE_NO(),
                    obj.getTOT_AMT(), obj.getTOT_AMT(), obj.getCERT_NO(), obj.getSTU_CLASS(),
                    obj.getSTU_ID(), obj.getMAJOR(), oweMonth, NO, NO);
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

    public int insertMission(String busiNo, String busiName, String projectName, String projTp, String oweMonth, String projDesc) {
        IDBSession session = DBSessionFactory.getSession();
        String createDate = DateUtil.getDate();
        String sql = String.format("INSERT INTO %s (BUSI_NO,BUSI_NAME,CRT_DATE,PROJ_NAME,PROJ_DESC,PROJ_TP,OWE_MONTH,STAT,LOCK_STAT) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)", CTRL_TABLE);
        int result = 0;
        try {
            result = session.execute(sql, busiNo, busiName, createDate, projectName,
                    projDesc, projTp, oweMonth, STAT_INIT, YES);
        } catch (SQLException throwables) {
            throw new BaseException(SysErr.E_MESSAGE, "明细控制表添加数据失败");
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return result;
    }

    public int updateMission(String busiNo, String projectName, String oweMonth, String stat) {
        IDBSession session = DBSessionFactory.getSession();
        String createDate = DateUtil.getDate();
        String sql = String.format("UPDATE %s SET STAT = ?,LOCK_STAT = ? " +
                "WHERE BUSI_NO = ? AND PROJ_NAME = ? AND OWE_MONTH = ?", CTRL_TABLE);
        int result = 0;
        try {
            result = session.execute(sql, stat, NO, busiNo, projectName, oweMonth);
        } catch (SQLException throwables) {
            throw new BaseException(SysErr.E_MESSAGE, "数据更新失败");
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        return result;
    }

    public List<ProjDo> getProjList(String busiNo) {
        ExecuteTask<SmsSqlTask<List<ProjDo>>, List<ProjDo>> task = new ExecuteTask();
        return task.sqlTaskNotNeedTransactional("获取缴费项数据", session -> {
            String sql = String.format("SELECT BUSI_NO,PROJ_NAME FROM %s WHERE BUSI_NO = ? ORDER BY CRT_DATE DESC", PROJ_TABLE);
            return session.getObjectList(sql, ProjDo.class, busiNo);
        });
    }


    /**
     * 获取模板
     *
     * @return
     */
    public List<Template> getTemplate() {

        IDBSession session = DBSessionFactory.getSession();
        String sql = String.format("SELECT * FROM %s ", TMPL_TABLE);
        List<Template> list = Lists.newArrayList();
        try {
            list = session.getObjectList(sql, Template.class, new MBCCaseStrategy());
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
     * 统计某模板的字段总数
     *
     * @param busiNo
     * @param keyNo
     * @param keyDesc
     * @return
     */
    public int countTmplField(String busiNo, String keyNo, String keyDesc) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT  COUNT(1)  FROM T_MLPP_OFFLINE_TMPL_DTL WHERE BUSI_NO = ? ");
        List<Object> paramList = ListUtils.newArrayList();
        paramList.add(busiNo);
        if (StringUtils.isNotBlank(keyNo)) {
            sql.append("AND KEY_NO = ? ");
            paramList.add(keyNo);
        }
        if (StringUtils.isNotBlank(keyDesc)) {
            sql.append("AND KEY_DESC = ? ");
            paramList.add(keyDesc);
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


    public List<List<Object>> getAllData(String busiNo, String projName, String stat, String payNo, String name,
                                         String phoneNo, String strDate, String endDate, String oweMonth, String projTp,
                                         List<List<String>> listKey, int page, String fieldName, String fieldVal) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        for (int i = 0; i < listKey.size() - 2; i++) {
            List<String> strings = listKey.get(i);
            sql.append(strings.get(0)).append(",");
        }
        sql.append("CASE WHEN PROJ_TP = '00' THEN '非自主录入'" +
                "WHEN PROJ_TP = '01' THEN '自主录入' END PROJ_TP, ");
        sql.append("CASE " +
                "WHEN STAT = '00' THEN '未缴费' " +
                "WHEN STAT = '01' THEN '已缴费' " +
                "WHEN STAT = '02' THEN '缴费异常' " +
                "WHEN STAT = '03' THEN '已退费' " +
                "END STAT FROM ").append(DTL_TABLE).append(" WHERE BUSI_NO= ? ");
        List<Object> parameters = Lists.newArrayList();
        parameters.add(busiNo);
        if (StringUtils.isNotBlank(projName)) {
            sql.append("AND PROJ_NAME = ? ");
            parameters.add(projName);
        }
        if (StringUtils.isNotBlank(stat)) {
            sql.append("AND STAT = ? ");
            parameters.add(stat);
        }
        if (StringUtils.isNotBlank(payNo)) {
            sql.append("AND PAY_NO = ? ");
            parameters.add(payNo);
        }
        if (StringUtils.isNotBlank(name)) {
            sql.append("AND NAME = ? ");
            parameters.add(name);
        }
        if (StringUtils.isNotBlank(phoneNo)) {
            sql.append("AND PHONE_NO = ? ");
            parameters.add(phoneNo);
        }
        if (StringUtils.isNotBlank(strDate)) {
            sql.append("AND PLAT_DATE >= ? ");
            parameters.add(strDate);
        }
        if (StringUtils.isNotBlank(endDate)) {
            sql.append("AND PLAT_DATE <= ? ");
            parameters.add(endDate);
        }
        if (StringUtils.isNotBlank(oweMonth)) {
            sql.append("AND OWE_MONTH = ? ");
            parameters.add(oweMonth);
        }
        if (StringUtils.isNotBlank(projTp)) {
            sql.append("AND PROJ_TP = ? ");
            parameters.add(projTp);
        }
        if (StringUtils.isNotBlank(fieldName) && StringUtils.isNotBlank(fieldVal)) {
            sql.append("AND ").append(fieldName).append(" = ? ");
            parameters.add(fieldVal);
        }
        sql.append("ORDER BY BUSI_NO,PROJ_NAME,OWE_MONTH,SUB_SER ").append("LIMIT ").append(page).append(",10000");
        List<List<Object>> list = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = session.getResultSetByList(sql.toString(), parameters);
            while (rs.next()) {
                List<Object> inList = new ArrayList<>();
                for (int i = 1; i <= listKey.size(); i++) {
                    Object object = rs.getObject(i);
                    inList.add(object);
                }
                list.add(inList);
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


    public int updateTmplStat(String busiNo, String keyNo, String queryStat, String excelStat) {
        IDBSession session = DBSessionFactory.getSession();
        String sql = String.format("UPDATE %s SET QUERY_STAT = ? ,EXCEL_STAT = ? WHERE BUSI_NO = ? AND KEY_NO = ?", TMPL_FIELD_TABLE);
        int rs = 0;
        try {
            rs = session.execute(sql, queryStat, excelStat, busiNo, keyNo);
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

    public List<Template> getAllTmplField(String busiNo, String type) {
        IDBSession session = DBSessionFactory.getSession();
        List<Object> parameters = Lists.newArrayList();
        String sql = "";
        if (OfflineOpt.EXCEL_EXPORT.equals(type)) {
            sql = String.format("SELECT KEY_NO,KEY_DESC FROM %s WHERE QUERY_STAT = 'Y' AND BUSI_NO = ? ORDER BY SER", TMPL_FIELD_TABLE);
        } else {
            sql = String.format("SELECT KEY_NO,KEY_DESC,DEFA_VAL FROM %s WHERE EXCEL_STAT = 'Y' AND BUSI_NO = ? ORDER BY SER", TMPL_FIELD_TABLE);
        }
        parameters.add(busiNo);
        List<Template> list = Lists.newArrayList();
        try {
            list = session.getObjectListByList(sql, Template.class, parameters, new MBCCaseStrategy());
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
     * 获取模板字段详情
     *
     * @param busiNo
     * @param start
     * @param limit
     * @return
     */
    public List<Template> getTmplField(String busiNo, int start, int limit) {
        IDBSession session = DBSessionFactory.getSession();
        List<Object> parameters = Lists.newArrayList();
        String sql = String.format("SELECT * FROM %s WHERE BUSI_NO = ? ORDER BY SER LIMIT ?,?", TMPL_FIELD_TABLE);
        parameters.add(busiNo);
        parameters.add(start);
        parameters.add(limit);
        List<Template> list = Lists.newArrayList();
        try {
            list = session.getObjectListByList(sql, Template.class, parameters, new MBCCaseStrategy());
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
//        ExecuteTask<SmsSqlTask<List<Template>>, List<Template>> task = new ExecuteTask();
//        return task.sqlTaskNotNeedTransactional("获取模板字段数据项", session -> {
//            String sql = String.format("SELECT  TMOTD.*,TMOBT.TMPL_DESC FROM %s TMOTD INNER JOIN %s TMOBT ON  TMOTD.TMPL_ID = TMOBT.TMPL_ID  WHERE TMOTD.TMPL_ID = ? ORDER BY SER", TMPL_FIELD_TABLE,BUSI_TMPL_TABLE);
//            return session.getObjectList(sql, Template.class, tmplId);
//        });
    }

    /**
     * 汇总统计符合条件的明细总数
     *
     * @param busiNo
     * @param projName
     * @param stat
     * @param payNo
     * @param name
     * @param phoneNo
     * @param strDate
     * @param endDate
     * @param fieldName
     * @param fieldVal
     * @return
     */
    public int countExportData(String busiNo, String projName, String stat, String payNo, String name, String phoneNo,
                               String strDate, String endDate, String oweMonth, String projTp, String fieldName, String fieldVal) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT  COUNT(1)  FROM T_MLPP_OFFLINE_DTL WHERE BUSI_NO = ? ");
        int account = 0;
        List<Object> parameters = Lists.newArrayList();
        parameters.add(busiNo);
        if (StringUtils.isNotBlank(projName)) {
            sql.append("AND PROJ_NAME = ? ");
            parameters.add(projName);
        }
        if (StringUtils.isNotBlank(stat)) {
            sql.append("AND STAT = ? ");
            parameters.add(stat);
        }
        if (StringUtils.isNotBlank(payNo)) {
            sql.append("AND PAY_NO = ? ");
            parameters.add(payNo);
        }
        if (StringUtils.isNotBlank(name)) {
            sql.append("AND NAME = ? ");
            parameters.add(name);
        }
        if (StringUtils.isNotBlank(phoneNo)) {
            sql.append("AND PHONE_NO = ? ");
            parameters.add(phoneNo);
        }
        if (StringUtils.isNotBlank(strDate)) {
            sql.append("AND PLAT_DATE >= ? ");
            parameters.add(strDate);
        }
        if (StringUtils.isNotBlank(endDate)) {
            sql.append("AND PLAT_DATE <= ? ");
            parameters.add(endDate);
        }
        if (StringUtils.isNotBlank(oweMonth)) {
            sql.append("AND OWE_MONTH = ? ");
            parameters.add(oweMonth);
        }
        if (StringUtils.isNotBlank(projTp)) {
            sql.append("AND PROJ_TP = ? ");
            parameters.add(projTp);
        }
        if (StringUtils.isNotBlank(fieldName) && StringUtils.isNotBlank(fieldVal)) {
            sql.append("AND ").append(fieldName).append(" = ? ");
            parameters.add(fieldVal);
        }
        try {
            account = session.accountByList(sql.toString(), parameters);
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
     * 初始化指定业务的模板内容
     *
     * @param busiNo
     * @param busiName
     */
    public void addTmplFields(String busiNo, String busiName) {
        IDBSession session = DBSessionFactory.getSession();
        String sql = String.format("INSERT INTO %s(BUSI_NO,BUSI_NAME,SER,KEY_NO,KEY_DESC,DEFA_VAL,EXCEL_STAT,QUERY_STAT)" +
                "SELECT ?,?,SER,KEY_NO,KEY_DESC,DEFA_VAL,EXCEL_STAT,QUERY_STAT FROM %s WHERE BUSI_NO = 'TMPL' ", TMPL_FIELD_TABLE, TMPL_FIELD_TABLE);
        try {
            session.execute(sql, busiNo, busiName);
        } catch (SQLException e) {
            throw new BaseException(SysErr.E_MESSAGE, "业务模板字段初始化失败" + e.getMessage());
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
    }

    /**
     * 新增模板字段
     *
     * @param busiNo
     * @param busiName
     * @param ser
     * @param keyNo
     * @param keyDesc
     * @param queryStat
     * @param excelStat
     */
    public void addTmplField(String busiNo, String busiName, int ser, String keyNo, String keyDesc, String queryStat, String excelStat) {
        IDBSession session = DBSessionFactory.getSession();
        String sql = String.format("INSERT INTO %s(BUSI_NO,BUSI_NAME,SER,KEY_NO,KEY_DESC,QUERY_STAT,EXCEL_STAT) " +
                "VALUES(?,?,?,?,?,?,?)", TMPL_FIELD_TABLE);

        try {
            session.execute(sql, busiNo, busiName, ser, keyNo, keyDesc, queryStat, excelStat);
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
     * @param qryListReq
     * @return
     */
    public List<OfflineBook> findAllBook(MLppQryBookListReq qryListReq, int start) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        //存放输入的查询条件 防止sql注入
        List<Object> params = org.apache.commons.compress.utils.Lists.newArrayList();
        String auto_flg = qryListReq.getAUTO_FLG();

        sql.append("SELECT (TOT_AMT + LEGA_NO) AS REC_AMT,LEGA_NO,CONCAT( SUBSTRING(PLAT_DATE,0,4),'-',SUBSTRING(PLAT_DATE,5,2),'-',SUBSTRING(PLAT_DATE,7)) AS PLAT_DATE,BUSI_NO,BUSI_NAME,(CASE CHNL_NO WHEN '030' THEN '手机银行' WHEN '000' THEN '柜面系统' WHEN '800' THEN '核心' WHEN '047' THEN 'VTM' WHEN '057' THEN '收单系统' END)CHNL_NO,PAY_NO,NAME,SUBSTRING(REQ_TIME,9) REQ_TIME,(CASE PAY_TP WHEN '0' THEN '现金' WHEN '1' THEN '转账' WHEN '2' THEN '微信' WHEN '3' THEN '支付宝' WHEN '4' THEN 'POS' WHEN '5' THEN '本行卡支付' END)PAY_TP,TOT_AMT ,PRCTL_AMT ,DCT_AMT,AMT,FEE_AMT ,(CASE TRAN_STAT WHEN '01' THEN '成功' WHEN '02' THEN '失败' WHEN '03' THEN '异常' ELSE '已冲正' END )TRAN_STAT ,HOST_SEQ ,REQ_SEQ,(DCT_AMT - AMT) AS SHORT_RMRK,(CASE TRAN_TP WHEN '03' THEN '退费' ELSE '缴费' END) TRAN_TP,(CASE TRAN_TP WHEN '03' THEN PAYEE_ACCT ELSE PAY_ACCT END) PAY_ACCT");
        sql.append(" FROM ").append(T_MLPP_BOOK);
        sql.append(" WHERE BUSI_NO = '").append(qryListReq.getBUSI_NO()).append("' ")
                .append("AND　PLAT_DATE >= '").append(qryListReq.getSTR_DATE()).append("' ")
                .append("AND　PLAT_DATE <= '").append(qryListReq.getEND_DATE()).append("' ");
        String tran_stat = qryListReq.getTRAN_STAT();
        if (org.apache.commons.lang.StringUtils.isNotEmpty(tran_stat) && !"AA".equals(tran_stat)) {
            sql.append("AND TRAN_STAT = ? ");
            params.add(tran_stat);
        }
        String pay_no = qryListReq.getPAY_NO();
        if (org.apache.commons.lang.StringUtils.isNotEmpty(pay_no)) {
            sql.append("AND PAY_NO = ? ");
            params.add(pay_no);
        }
        sql.append("LIMIT ").append(start).append(",10000");
        List<OfflineBook> list = new ArrayList<>();
        try {
            list = session.getObjectListByList(sql.toString(), OfflineBook.class, params, new MBCCaseStrategy());
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
     * 统计导出数据
     *
     * @param qryListReq
     * @return
     */
    public int countExcelData(MLppQryBookListReq qryListReq) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT  COUNT(1)  FROM T_MLPP_BOOK WHERE BUSI_NO = ? ");
        int account = 0;
        List<Object> parameters = Lists.newArrayList();
        parameters.add(qryListReq.getBUSI_NO());
        if (StringUtils.isNotBlank(qryListReq.getSTR_DATE())) {
            sql.append("AND PLAT_DATE >= ? ");
            parameters.add(qryListReq.getSTR_DATE());
        }

        if (StringUtils.isNotBlank(qryListReq.getEND_DATE())) {
            sql.append("AND PLAT_DATE <= ? ");
            parameters.add(qryListReq.getEND_DATE());
        }

        if (StringUtils.isNotBlank(qryListReq.getTRAN_STAT())) {
            sql.append("AND TRAN_STAT >= ? ");
            parameters.add(qryListReq.getTRAN_STAT());
        }

        if (StringUtils.isNotBlank(qryListReq.getTRAN_TP())) {
            sql.append("AND TRAN_TP >= ? ");
            parameters.add(qryListReq.getTRAN_TP());
        }

        if (StringUtils.isNotBlank(qryListReq.getPAY_NO())) {
            sql.append("AND  PAY_NO>= ? ");
            parameters.add(qryListReq.getPAY_NO());
        }
        try {
            account = session.accountByList(sql.toString(), parameters);
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
     * 根据业务编号获取pdf模板
     *
     * @param busiNo
     * @return
     */
    public PdfTemplate getPdfTemplate(String busiNo) {
        IDBSession session = DBSessionFactory.getSession();
        List<Object> parameters = Lists.newArrayList();
        String sql = String.format("SELECT * FROM %s WHERE BUSI_NO = ? ", TMPL_BUSI_TABLE);
        parameters.add(busiNo);
        List<PdfTemplate> list = Lists.newArrayList();
        try {
            list = session.getObjectListByList(sql, PdfTemplate.class, parameters, new MBCCaseStrategy());
        } catch (SQLException e) {
            throw new BaseException(SysErr.E_MESSAGE, "查询失败" + e.getMessage());
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return new PdfTemplate();
    }

    /**
     * 获取合并缴费明细条数
     *
     * @param qryList
     * @param strDate
     * @param endDate
     * @return
     */
    public int countMergeData(OfflineDO qryList, String strDate, String endDate) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        StringBuilder sql2 = new StringBuilder();
        sql.append("SELECT COUNT(1) FROM ( " +
                "SELECT A.* , ROW_NUMBER()  OVER (PARTITION BY PAY_NO,OWE_MONTH ORDER BY PAY_NO,OWE_MONTH) AS RN FROM T_MLPP_OFFLINE_DTL a WHERE BUSI_NO=? ");
        sql2.append(" ) B LEFT JOIN (SELECT PAY_NO,OWE_MONTH, LISTAGG( PROJ_NAME || ','||TOT_AMT || ',' ||DCT_AMT || ',' || STAT  || '|') WITHIN GROUP (ORDER BY PROJ_NAME) AS data FROM T_MLPP_OFFLINE_DTL WHERE BUSI_NO=? ");
        int account = 0;
        List<Object> parameters = Lists.newArrayList();
        List<Object> parameters1 = Lists.newArrayList();
        parameters.add(qryList.getBUSI_NO());
        parameters1.add(qryList.getBUSI_NO());
        if (StringUtils.isNotBlank(qryList.getOWE_MONTH())) {
            sql.append("AND OWE_MONTH = ? ");
            sql2.append("AND OWE_MONTH = ? ");
            parameters.add(qryList.getOWE_MONTH());
            parameters1.add(qryList.getOWE_MONTH());
        }
        if (StringUtils.isNotBlank(qryList.getPROJ_TP())) {
            sql.append("AND PROJ_TP = ? ");
            sql2.append("AND PROJ_TP = ? ");
            parameters.add(qryList.getPROJ_TP());
            parameters1.add(qryList.getPROJ_TP());
        }
        if (StringUtils.isNotBlank(qryList.getPROJ_NAME())) {
            sql.append("AND PROJ_NAME = ? ");
            sql2.append("AND PROJ_NAME = ? ");
            parameters.add(qryList.getPROJ_NAME());
            parameters1.add(qryList.getPROJ_NAME());
        }
        if (StringUtils.isNotBlank(qryList.getSTAT())) {
            sql.append("AND STAT = ? ");
            sql2.append("AND STAT = ? ");
            parameters.add(qryList.getSTAT());
            parameters1.add(qryList.getSTAT());
        }
        if (StringUtils.isNotBlank(qryList.getPAY_NO())) {
            sql.append("AND PAY_NO = ? ");
            sql2.append("AND PAY_NO = ? ");
            parameters.add(qryList.getPAY_NO());
            parameters1.add(qryList.getPAY_NO());
        }
        if (StringUtils.isNotBlank(qryList.getNAME())) {
            sql.append("AND NAME = ? ");
            sql2.append("AND NAME = ? ");
            parameters.add(qryList.getNAME());
            parameters1.add(qryList.getNAME());
        }
        if (StringUtils.isNotBlank(qryList.getPHONE_NO())) {
            sql.append("AND PHONE_NO = ? ");
            sql2.append("AND PHONE_NO = ? ");
            parameters.add(qryList.getPHONE_NO());
            parameters1.add(qryList.getPHONE_NO());
        }
        if (StringUtils.isNotBlank(strDate)) {
            sql.append("AND PLAT_DATE >= ? ");
            sql2.append("AND PLAT_DATE >= ? ");
            parameters.add(strDate);
            parameters1.add(strDate);
        }
        if (StringUtils.isNotBlank(endDate)) {
            sql.append("AND PLAT_DATE <= ? ");
            sql2.append("AND PLAT_DATE <= ? ");
            parameters.add(endDate);
            parameters1.add(endDate);
        }
        sql.append(sql2.toString()).append(" GROUP BY PAY_NO,OWE_MONTH) c ON B.PAY_NO =C.PAY_NO AND B.OWE_MONTH = C.OWE_MONTH WHERE B.RN=1 ");
        parameters.addAll(parameters1);
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
     * 获取合并缴费明细数据
     *
     * @param qryList
     * @param strDate
     * @param endDate
     * @param page
     * @return
     */
    public List<MergeOfflineDtl> getMergeDataList(OfflineDO qryList, String strDate, String endDate, int page) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        StringBuilder sql2 = new StringBuilder();
        sql.append("SELECT B.*, C.DATA,C.DATA as DATA1,C.DATA as DATA2,C.DATA as DATA3,C.DATA AS DATA4,C.DATA AS DATA5,C.DATA AS DATA6 FROM ( " +
                "SELECT A.* , ROW_NUMBER()  OVER (PARTITION BY PAY_NO,OWE_MONTH ORDER BY PAY_NO,OWE_MONTH) AS RN FROM T_MLPP_OFFLINE_DTL a WHERE BUSI_NO=? ");
        sql2.append(" ) B LEFT JOIN (SELECT PAY_NO,OWE_MONTH, LISTAGG( PROJ_NAME || ','||TOT_AMT || ',' ||DCT_AMT || ',' || STAT  || '|') WITHIN GROUP (ORDER BY PROJ_NAME) AS data FROM T_MLPP_OFFLINE_DTL WHERE BUSI_NO=? ");
        int account = 0;
        List<Object> parameters = Lists.newArrayList();
        List<Object> parameters1 = Lists.newArrayList();
        List<MergeOfflineDtl> list = Lists.newArrayList();
        parameters.add(qryList.getBUSI_NO());
        parameters1.add(qryList.getBUSI_NO());
        if (StringUtils.isNotBlank(qryList.getOWE_MONTH())) {
            sql.append("AND OWE_MONTH = ? ");
            sql2.append("AND OWE_MONTH = ? ");
            parameters.add(qryList.getOWE_MONTH());
            parameters1.add(qryList.getOWE_MONTH());
        }
        if (StringUtils.isNotBlank(qryList.getPROJ_TP())) {
            sql.append("AND PROJ_TP = ? ");
            sql2.append("AND PROJ_TP = ? ");
            parameters.add(qryList.getPROJ_TP());
            parameters1.add(qryList.getPROJ_TP());
        }
        if (StringUtils.isNotBlank(qryList.getPROJ_NAME())) {
            sql.append("AND PROJ_NAME = ? ");
            sql2.append("AND PROJ_NAME = ? ");
            parameters.add(qryList.getPROJ_NAME());
            parameters1.add(qryList.getPROJ_NAME());
        }
        if (StringUtils.isNotBlank(qryList.getSTAT())) {
            sql.append("AND STAT = ? ");
            sql2.append("AND STAT = ? ");
            parameters.add(qryList.getSTAT());
            parameters1.add(qryList.getSTAT());
        }
        if (StringUtils.isNotBlank(qryList.getPAY_NO())) {
            sql.append("AND PAY_NO = ? ");
            sql2.append("AND PAY_NO = ? ");
            parameters.add(qryList.getPAY_NO());
            parameters1.add(qryList.getPAY_NO());
        }
        if (StringUtils.isNotBlank(qryList.getNAME())) {
            sql.append("AND NAME = ? ");
            sql2.append("AND NAME = ? ");
            parameters.add(qryList.getNAME());
            parameters1.add(qryList.getNAME());
        }
        if (StringUtils.isNotBlank(qryList.getPHONE_NO())) {
            sql.append("AND PHONE_NO = ? ");
            sql2.append("AND PHONE_NO = ? ");
            parameters.add(qryList.getPHONE_NO());
            parameters1.add(qryList.getPHONE_NO());
        }
        if (StringUtils.isNotBlank(strDate)) {
            sql.append("AND PLAT_DATE >= ? ");
            sql2.append("AND PLAT_DATE >= ? ");
            parameters.add(strDate);
            parameters1.add(strDate);
        }
        if (StringUtils.isNotBlank(endDate)) {
            sql.append("AND PLAT_DATE <= ? ");
            sql2.append("AND PLAT_DATE <= ? ");
            parameters.add(endDate);
            parameters1.add(endDate);
        }
        sql.append(sql2.toString()).append(" GROUP BY PAY_NO,OWE_MONTH) c ON B.PAY_NO =C.PAY_NO AND B.OWE_MONTH = C.OWE_MONTH WHERE B.RN=1 ");
        parameters.addAll(parameters1);
        sql.append("LIMIT ").append(page).append(",10000");
        try {
            list = session.getObjectListByList(sql.toString(), MergeOfflineDtl.class, parameters, new MBCCaseStrategy());
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
     * 获取商户列表（非联网缴费）
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
                "WHERE A.SHORT_RMRK ='10' AND C.RULE_TP ='201' ");
        String busiNo = reqBody.get("busiNo");
        String busiName = reqBody.get("busiName");
        String officeId = reqBody.get("officeId");
        if (!StringUtils.isEmpty(officeId)) {
//            sql.append("AND ( E.ID = ? OR E.PARENT_ID_LIST  LIKE ? )");
//            parameters.add(officeId);
//            officeId = "%," + officeId + ",%";
//            parameters.add(officeId);
            String[] officeIds = officeId.split(",");
            sql.append(" AND E.ID IN (");
            for (String item : officeIds) {
                sql.append("?,");
                parameters.add(item);
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(")");
        }
        if (!StringUtils.isEmpty(busiNo)) {
            sql.append("AND B.BUSI_NO=? ");
            parameters.add(busiNo);
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
     * 返回商户数量（非联网缴费）
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
                "WHERE A.SHORT_RMRK ='10' AND C.RULE_TP ='201' ");
        String busiNo = reqBody.get("busiNo");
        String busiName = reqBody.get("busiName");
        String officeId = reqBody.get("officeId");

        if (!StringUtils.isEmpty(busiNo)) {
            sql.append("AND B.BUSI_NO=? ");
            parameters.add(busiNo);
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
     * 新增、修改商户
     *
     * @param req
     * @param start
     * @param limit
     * @return
     */
    public int modifyMerchan(Map<String, Object> req, int start, int limit) {
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
                session.execute("INSERT INTO T_PIP_ENTR (ENTR_NO,OPEN_STAT,ENTR_NAME,ENTR_NATURE,ENTR_CERT_TP,ENTR_CERT_NO,LEGA_NAME,LEGA_CERT_TP,LEGA_CERT_NO,ENTR_ADDR,ENTR_TEL_NO,EMAIL,CTCT_PER_NAME,CTCT_PHONE_NO,SND_TRAN_DATE,SND_TRAN_CMPLE_TIME,REG_BRCH,REG_TLR_NO,MOD_DATE,MOD_TIME,MOD_BRCH,MOD_TLR_NO,URL,LAST_UPT_TIME,COMN_DESC,CRTR,CRT_TIME,UPTR,UPT_TIME,BELONG_LEGA_NO,SHORT_RMRK,MID_RMRK,LONG_RMRK,DAC) VALUES (?,?,?,'1','C001','',?,'P000','',?,?,'',?,'','','','','','','','',NULL,'',NULL,?,'1','2024-03-07 11:49:41','1','2024-05-17 16:52:17',NULL,'10','',?,'')", entrNo, openStat, busiName, busiName, addr, phoneNo, name, busiName, payInfo);
                //业务信息表
                session.execute("INSERT INTO T_PIP_BUSI (BUSI_NO,ENTR_NO,BUSI_NAME,SALE_PROD_CODE,SALE_PROD_TP,BUSI_TMPL,OPEN_STAT,BUSI_DESC,OPEN_GRP_CHNL_NO,CLR_TP,SIGN_PAT,CHK_PAT,FEE_TP,BUSI_TP,RELAT_SYS,CLR_DATE,BRCH_TP,CRTR,BG_IMG,UPT_TIME,LAST_UPT_TIME,CRT_TIME,MOD_STAT,UPTR,BRCH_ID,SHORT_RMRK,MID_RMRK,LONG_RMRK,DAC) VALUES (?,?,?,'010008',NULL,NULL,'Y',?,NULL,'00','01','01','00','01','OFFLINE',NULL,'1','1','','2024-03-07 11:53:44',NULL,'2024-03-07 11:53:44',NULL,'1','1','','','','')", busiNo, entrNo, busiName, busiName);
                //业务参数表
                session.execute("INSERT INTO T_PIP_BUSI_PARA (BUSI_NO,BUSI_NAME,KEY_TP,KEY_NO,KEY_NAME,KV,LAST_UPT_TIME,MOD_STAT,SHORT_RMRK,MID_RMRK,LONG_RMRK,DAC) VALUES (?,?,'',?,'记账摘要码','MB8078',NULL,NULL,'','','','')", busiNo, busiName,"SumCode");
                session.execute("INSERT INTO T_PIP_BUSI_PARA (BUSI_NO,BUSI_NAME,KEY_TP,KEY_NO,KEY_NAME,KV,LAST_UPT_TIME,MOD_STAT,SHORT_RMRK,MID_RMRK,LONG_RMRK,DAC) VALUES (?,?,'',?,'缴费业务支持缴费类型','01|02|03|04|06',NULL,NULL,'','','','')", busiNo, busiName,"PayType");
                session.execute("INSERT INTO T_PIP_BUSI_PARA (BUSI_NO,BUSI_NAME,KEY_TP,KEY_NO,KEY_NAME,KV,LAST_UPT_TIME,MOD_STAT,SHORT_RMRK,MID_RMRK,LONG_RMRK,DAC) VALUES (?,?,'',?,'核心业务产品参数','0063',NULL,NULL,'','','','')", busiNo, busiName,"BusiKdNo");
                session.execute("INSERT INTO T_PIP_BUSI_PARA (BUSI_NO,BUSI_NAME,KEY_TP,KEY_NO,KEY_NAME,KV,LAST_UPT_TIME,MOD_STAT,SHORT_RMRK,MID_RMRK,LONG_RMRK,DAC) VALUES (?,?,'',?,'记账摘要描述','教育缴费',NULL,NULL,'','','','')", busiNo, busiName,"SumDesc");
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
                //商户模板配置表
                session.execute("INSERT INTO T_MLPP_OFFLINE_TMPL_DTL (BUSI_NO,BUSI_NAME,SER,KEY_NO,KEY_DESC,DEFA_VAL,SHORT_RMRK,MID_RMRK,LONG_RMRK,EXCEL_STAT,QUERY_STAT) VALUES (?,?,1,'SUB_SER','序号','1',NULL,NULL,NULL,'Y','Y')", busiNo, busiName);
                session.execute("INSERT INTO T_MLPP_OFFLINE_TMPL_DTL (BUSI_NO,BUSI_NAME,SER,KEY_NO,KEY_DESC,DEFA_VAL,SHORT_RMRK,MID_RMRK,LONG_RMRK,EXCEL_STAT,QUERY_STAT) VALUES (?,?,2,'NAME','姓名','张三',NULL,NULL,NULL,'Y','Y')", busiNo, busiName);
                session.execute("INSERT INTO T_MLPP_OFFLINE_TMPL_DTL (BUSI_NO,BUSI_NAME,SER,KEY_NO,KEY_DESC,DEFA_VAL,SHORT_RMRK,MID_RMRK,LONG_RMRK,EXCEL_STAT,QUERY_STAT) VALUES (?,?,3,'PHONE_NO','手机号码','15906227740',NULL,NULL,NULL,'Y','Y')", busiNo, busiName);
                session.execute("INSERT INTO T_MLPP_OFFLINE_TMPL_DTL (BUSI_NO,BUSI_NAME,SER,KEY_NO,KEY_DESC,DEFA_VAL,SHORT_RMRK,MID_RMRK,LONG_RMRK,EXCEL_STAT,QUERY_STAT) VALUES (?,?,4,'TOT_AMT','缴费金额','17.38',NULL,NULL,NULL,'Y','Y')", busiNo, busiName);
                session.execute("INSERT INTO T_MLPP_OFFLINE_TMPL_DTL (BUSI_NO,BUSI_NAME,SER,KEY_NO,KEY_DESC,DEFA_VAL,SHORT_RMRK,MID_RMRK,LONG_RMRK,EXCEL_STAT,QUERY_STAT) VALUES (?,?,5,'ADDR','地址','河北省廊坊市xxxx',NULL,NULL,NULL,'N','N')", busiNo, busiName);
                session.execute("INSERT INTO T_MLPP_OFFLINE_TMPL_DTL (BUSI_NO,BUSI_NAME,SER,KEY_NO,KEY_DESC,DEFA_VAL,SHORT_RMRK,MID_RMRK,LONG_RMRK,EXCEL_STAT,QUERY_STAT) VALUES (?,?,6,'CERT_NO','证件号码','4102198907124430',NULL,NULL,NULL,'Y','Y')", busiNo, busiName);
                session.execute("INSERT INTO T_MLPP_OFFLINE_TMPL_DTL (BUSI_NO,BUSI_NAME,SER,KEY_NO,KEY_DESC,DEFA_VAL,SHORT_RMRK,MID_RMRK,LONG_RMRK,EXCEL_STAT,QUERY_STAT) VALUES (?,?,7,'STU_ID','学号','1002',NULL,NULL,NULL,'Y','Y')", busiNo, busiName);
                session.execute("INSERT INTO T_MLPP_OFFLINE_TMPL_DTL (BUSI_NO,BUSI_NAME,SER,KEY_NO,KEY_DESC,DEFA_VAL,SHORT_RMRK,MID_RMRK,LONG_RMRK,EXCEL_STAT,QUERY_STAT) VALUES (?,?,8,'STU_CLASS','班级','1',NULL,NULL,NULL,'Y','Y')", busiNo, busiName);
                session.execute("INSERT INTO T_MLPP_OFFLINE_TMPL_DTL (BUSI_NO,BUSI_NAME,SER,KEY_NO,KEY_DESC,DEFA_VAL,SHORT_RMRK,MID_RMRK,LONG_RMRK,EXCEL_STAT,QUERY_STAT) VALUES (?,?,9,'COLLEGE','学院','经济学院',NULL,NULL,NULL,'N','N')", busiNo, busiName);
                session.execute("INSERT INTO T_MLPP_OFFLINE_TMPL_DTL (BUSI_NO,BUSI_NAME,SER,KEY_NO,KEY_DESC,DEFA_VAL,SHORT_RMRK,MID_RMRK,LONG_RMRK,EXCEL_STAT,QUERY_STAT) VALUES (?,?,10,'SCHOOL','学校','河北xxx大学',NULL,NULL,NULL,'N','N')", busiNo, busiName);
                session.execute("INSERT INTO T_MLPP_OFFLINE_TMPL_DTL (BUSI_NO,BUSI_NAME,SER,KEY_NO,KEY_DESC,DEFA_VAL,SHORT_RMRK,MID_RMRK,LONG_RMRK,EXCEL_STAT,QUERY_STAT) VALUES (?,?,11,'MAJOR','专业','数学',NULL,NULL,NULL,'N','N')", busiNo, busiName);
                session.execute("INSERT INTO T_MLPP_OFFLINE_TMPL_DTL (BUSI_NO,BUSI_NAME,SER,KEY_NO,KEY_DESC,DEFA_VAL,SHORT_RMRK,MID_RMRK,LONG_RMRK,EXCEL_STAT,QUERY_STAT) VALUES (?,?,12,'GRADE','年级','大一',NULL,NULL,NULL,'N','N')", busiNo, busiName);
                session.execute("INSERT INTO T_MLPP_OFFLINE_TMPL_DTL (BUSI_NO,BUSI_NAME,SER,KEY_NO,KEY_DESC,DEFA_VAL,SHORT_RMRK,MID_RMRK,LONG_RMRK,EXCEL_STAT,QUERY_STAT) VALUES (?,?,13,'ENROLL_YEAR','入学年度','河北xxx大学',NULL,NULL,NULL,'N','N')", busiNo, busiName);
                session.execute("INSERT INTO T_MLPP_OFFLINE_TMPL_DTL (BUSI_NO,BUSI_NAME,SER,KEY_NO,KEY_DESC,DEFA_VAL,SHORT_RMRK,MID_RMRK,LONG_RMRK,EXCEL_STAT,QUERY_STAT) VALUES (?,?,14,'CAR_NO','车牌号','河北省廊坊市xxxx',NULL,NULL,NULL,'N','N')", busiNo, busiName);
                session.execute("INSERT INTO T_MLPP_OFFLINE_TMPL_DTL (BUSI_NO,BUSI_NAME,SER,KEY_NO,KEY_DESC,DEFA_VAL,SHORT_RMRK,MID_RMRK,LONG_RMRK,EXCEL_STAT,QUERY_STAT) VALUES (?,?,15,'DCT_AMT','优惠金额',NULL,NULL,NULL,NULL,'Y','Y')", busiNo, busiName);
                //pdf模板业务映射表
                return session.execute("INSERT INTO T_MLPP_OFFLINE_BUSI_TMPL (BUSI_NO,BUSI_NAME,BUSI_TP,TMPL_ID,TMPL_DESC,STAT,RMRK) VALUES (?,?,NULL,?,NULL,NULL,NULL)", busiNo, busiName,"schoolTemplate.pdf");
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
     * 获取已缴费明细数量
     *
     * @param reqDs
     * @return
     */
    public int countSuccDtlList(IDataset reqDs) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        List<Object> parameters = Lists.newArrayList();
        sql.append("SELECT COUNT(*) FROM T_MLPP_OFFLINE_DTL  WHERE BUSI_NO=? AND PROJ_NAME=? AND OWE_MONTH=? AND STAT='01' ");
        parameters.add(reqDs.getString("busiNo"));
        parameters.add(reqDs.getString("projName"));
        parameters.add(reqDs.getString("oweMonth"));
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
     * 缴费账单明细批量删除
     * @param reqDs
     * @return
     */
    public int batDelDtl(IDataset reqDs) {
        String busiNo = reqDs.getString("busiNo");
        String projName = reqDs.getString("projName");
        String oweMonth = reqDs.getString("oweMonth");
        ExecuteTask<SmsSqlTask<Integer>, Integer> task = new ExecuteTask();
        return task.sqlTaskNeedTransactional("缴费账单明细批量删除", session -> {
            //修改商户名称
            session.execute("DELETE FROM T_MLPP_OFFLINE_DTL WHERE  BUSI_NO=? AND PROJ_NAME=? AND OWE_MONTH=? AND STAT='00' ", busiNo, projName, oweMonth);

            return session.execute("DELETE FROM T_MLPP_OFFLINE_CTRL WHERE BUSI_NO=? AND PROJ_NAME=? AND OWE_MONTH=?", busiNo, projName, oweMonth);
        });
    }

    /**
     * 获取缴费单位咨询电话
     * @param busiNo
     * @return
     */
    public String getPhoneNo(String busiNo) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.ENTR_TEL_NO FROM T_PIP_ENTR A WHERE EXISTS (SELECT 1 FROM T_PIP_BUSI B WHERE A.ENTR_NO=B.ENTR_NO AND B.BUSI_NO= ?)");
        String result = "";
        ResultSet rs = null;
        try {
            rs = session.getResultSet(sql.toString(),busiNo);
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
     * 获取明细金额
     * @param busiNo
     * @return
     */
    public int countDtl(String busiNo) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        List<Object> parameters = Lists.newArrayList();
        sql.append("SELECT COUNT(*) FROM T_MLPP_OFFLINE_DTL  WHERE BUSI_NO=? ");
        parameters.add(busiNo);
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
     * 学校商户删除（逻辑下架）
     *
     * @param busiNo
     * @return
     */
    public int offShelfMerchan(String busiNo) {
        ExecuteTask<SmsSqlTask<Integer>, Integer> task = new ExecuteTask();
        return task.sqlTaskNeedTransactional("学校商户下架", session -> session.execute(
                "UPDATE T_PIP_ENTR A SET A.OPEN_STAT='N' WHERE EXISTS (SELECT 1 FROM T_PIP_BUSI B WHERE A.ENTR_NO=B.ENTR_NO AND B.BUSI_NO=?)",
                busiNo
        ));
    }

    /**
     *  获取数据查询列表总数量
     * @param reqBody
     * @return list 第一个是记录条数，第二个是合计缴费笔数，第三个是合计缴费金额
     */
    public List<String> countDataInfo(Map<String, Object> reqBody) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        List<Object> parameters = Lists.newArrayList();
        sql.append("SELECT COUNT(DISTINCT BUSI_NO),COUNT(1) AS TOT_NUM,SUM(TOT_AMT) AS TOT_AMT FROM T_MLPP_BOOK WHERE TRAN_STAT='01' AND TRAN_TP!='03' AND DAC='10' ");
        List<String> busiNoList = (List<String>)reqBody.get("busiNoList");
        String strDate = (String)reqBody.get("strDate");
        String endDate = (String)reqBody.get("endDate");

        if (!CollectionUtils.isEmpty(busiNoList)) {
            sql.append("AND BUSI_NO IN (");
            for (String busiNo : busiNoList) {
                sql.append("?,");
                parameters.add(busiNo);
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(") ");
        }
        if (!StringUtils.isEmpty(strDate) ) {
            sql.append("AND CLR_DATE >= ? ");
            parameters.add(strDate);
        }
        if (!StringUtils.isEmpty(endDate) ) {
            sql.append("AND CLR_DATE <= ? ");
            parameters.add(endDate);
        }
        List<String> list = new ArrayList<String>();
        ResultSet rs = null;
        try {
            rs = session.getResultSetByList(sql.toString(),parameters);
            while (rs.next()) {
                list.add(String.valueOf(rs.getObject(1)));
                list.add(String.valueOf(rs.getObject(2)));
                list.add(String.valueOf(rs.getObject(3)));
            }
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
        return list;
    }

    /**
     * 获取数据查询分页数据
     * @param reqBody
     * @param start
     * @param limit
     * @return
     */
    public List<DataInfo> getDataList(Map<String, Object> reqBody, int start, int limit) {
        IDBSession session = DBSessionFactory.getSession();
        List<DataInfo> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        List<Object> parameters = Lists.newArrayList();
        sql.append("SELECT COUNT(*) AS TOT_NUM,SUM(TOT_AMT) AS TOT_AMT,SUM(CASE WHEN PAY_TP ='1' THEN 1 WHEN PAY_TP ='5' THEN 1 ELSE 0 END) AS BANKCARD_NUM,SUM(CASE WHEN PAY_TP ='1' THEN TOT_AMT WHEN PAY_TP ='5' THEN TOT_AMT ELSE 0 END) AS BANKCARD_AMT,A.BUSI_NO,A.BUSI_NAME,A.BELONG_BRCH,B.NAME FROM T_MLPP_BOOK A  LEFT JOIN T_SYS_OFFICE B ON A.BELONG_BRCH =B.BRCH_CODE WHERE TRAN_STAT='01' AND TRAN_TP!='03' ");
        List<String> busiNoList = (List<String>)reqBody.get("busiNoList");
        String strDate = (String)reqBody.get("strDate");
        String endDate = (String)reqBody.get("endDate");

        if (!CollectionUtils.isEmpty(busiNoList)) {
            sql.append("AND BUSI_NO IN (");
            for (String busiNo : busiNoList) {
                sql.append("?,");
                parameters.add(busiNo);
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(") ");
        }
        if (!StringUtils.isEmpty(strDate) ) {
            sql.append("AND CLR_DATE >= ? ");
            parameters.add(strDate);
        }
        if (!StringUtils.isEmpty(endDate) ) {
            sql.append("AND CLR_DATE <= ? ");
            parameters.add(endDate);
        }

        sql.append("GROUP BY A.BUSI_NO,A.BUSI_NAME,A.BELONG_BRCH,B.NAME ");
        sql.append("LIMIT ").append(start).append(",").append(limit);
        try {
            list = session.getObjectListByList(sql.toString(), DataInfo.class, parameters, new MBCCaseStrategy());
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
     * 获取数据查询数据
     * @param reqBody
     * @param start
     * @param limit
     * @return
     */
    public List<DataInfo> getDataList(Map<String, Object> reqBody) {
        IDBSession session = DBSessionFactory.getSession();
        List<DataInfo> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        List<Object> parameters = Lists.newArrayList();
        sql.append("SELECT COUNT(*) AS TOT_NUM,SUM(TOT_AMT) AS TOT_AMT,SUM(CASE WHEN PAY_TP ='1' THEN 1 WHEN PAY_TP ='5' THEN 1 ELSE 0 END) AS BANKCARD_NUM,SUM(CASE WHEN PAY_TP ='1' THEN TOT_AMT WHEN PAY_TP ='5' THEN TOT_AMT ELSE 0 END) AS BANKCARD_AMT,A.BUSI_NO,A.BUSI_NAME,A.BELONG_BRCH,B.NAME FROM T_MLPP_BOOK A  LEFT JOIN T_SYS_OFFICE B ON A.BELONG_BRCH =B.BRCH_CODE WHERE TRAN_STAT='01' AND TRAN_TP!='03' ");
        List<String> busiNoList = (List<String>)reqBody.get("busiNoList");
        String strDate = (String)reqBody.get("strDate");
        String endDate = (String)reqBody.get("endDate");

        if (!CollectionUtils.isEmpty(busiNoList)) {
            sql.append("AND BUSI_NO IN (");
            for (String busiNo : busiNoList) {
                sql.append("?,");
                parameters.add(busiNo);
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(") ");
        }
        if (!StringUtils.isEmpty(strDate) ) {
            sql.append("AND CLR_DATE >= ? ");
            parameters.add(strDate);
        }
        if (!StringUtils.isEmpty(endDate) ) {
            sql.append("AND CLR_DATE <= ? ");
            parameters.add(endDate);
        }

        sql.append("GROUP BY A.BUSI_NO,A.BUSI_NAME,A.BELONG_BRCH,B.NAME ");
        try {
            list = session.getObjectListByList(sql.toString(), DataInfo.class, parameters, new MBCCaseStrategy());
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
