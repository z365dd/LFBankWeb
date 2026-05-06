package com.adtec.pay.dao;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.impl.share.caseStrategy.MBCCaseStrategy;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.pay.dto.bookList.Book;
import com.adtec.pay.dto.bookList.MLppQryBookListReq;
import com.adtec.pay.entity.CtrlTParaChnlDO;
import com.adtec.sys.common.dao.IBaseDao;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * BookDao查询类
 */
@Component
public class BookDao implements IBaseDao<BookDao> {

    private final static Logger logger = LoggerFactory.getLogger(BookDao.class);

    public static final String table1 = "T_MLPP_BOOK";
    public static final String table2 = "T_MLPP_BAT_DTL";

    public List<Book> findAll(MLppQryBookListReq qryListReq) {
        IDBSession session = DBSessionFactory.getSession();
        StringBuilder sql = new StringBuilder();
        //存放输入的查询条件 防止sql注入
        List<Object> params = Lists.newArrayList();
        String auto_flg = qryListReq.getAUTO_FLG();
        //联机交易
        if ("0".equals(auto_flg)) {
            sql.append("SELECT CONCAT( SUBSTRING(PLAT_DATE,0,4),'-',SUBSTRING(PLAT_DATE,5,2),'-',SUBSTRING(PLAT_DATE,7)) AS PLAT_DATE,BUSI_NO,BUSI_NAME,(CASE CHNL_NO WHEN '030' THEN '手机银行' WHEN '000' THEN '柜面系统' WHEN '800' THEN '核心' WHEN '047' THEN 'VTM' WHEN '057' THEN '廊银小惠' END) CHNL_NO,PAY_NO,PAY_ACCT,NAME,SUBSTRING(REQ_TIME,9) REQ_TIME,OWE_MONTH,(CASE PAY_TP WHEN '0' THEN '现金' WHEN '1' THEN '转账' WHEN '2' THEN '微信' WHEN '3' THEN '支付宝' WHEN '4' THEN 'POS' WHEN '5' THEN '本行卡支付' END)PAY_TP,ADDR,TOT_AMT ,PRCTL_AMT ,DCT_AMT,AMT,FEE_AMT ,(CASE TRAN_STAT WHEN '01' THEN '成功' WHEN '02' THEN '失败' WHEN '03' THEN '异常' ELSE '已冲正' END )TRAN_STAT ,HOST_SEQ ,REQ_SEQ,OTH_SEQ ,OPEN_CUST_BRCH,(DCT_AMT - AMT) AS SHORT_RMRK,'否' AS AUTO_FLG");
            sql.append(" FROM ").append(table1);
            sql.append(" WHERE BUSI_NO = '").append(qryListReq.getBUSI_NO()).append("' ")
                    .append("AND　PLAT_DATE >= '").append(qryListReq.getSTR_DATE()).append("' ")
                    .append("AND　PLAT_DATE <= '").append(qryListReq.getEND_DATE()).append("' ");
            String tran_stat = qryListReq.getTRAN_STAT();
            if (StringUtils.isNotEmpty(tran_stat) && !"AA".equals(tran_stat)) {
                sql.append("AND TRAN_STAT = ? ");
                params.add(tran_stat);
            }
            String pay_no = qryListReq.getPAY_NO();
            if (StringUtils.isNotEmpty(pay_no)) {
                sql.append("AND PAY_NO = ? ");
                params.add(pay_no);
            }
            String acct = qryListReq.getACCT();
            if (StringUtils.isNotEmpty(acct)) {
                sql.append("AND PAY_ACCT = ? ");
                params.add(acct);
            }
            String tranTp = qryListReq.getTRAN_TP();
            if (StringUtils.isNotEmpty(tranTp)) {
                sql.append("AND TRAN_TP = ? ");
                params.add(tranTp);
            }
        } else {
            //批量扣款
            sql.append("SELECT CONCAT( SUBSTRING(PLAT_DATE,0,4),'-',SUBSTRING(PLAT_DATE,5,2),'-',SUBSTRING(PLAT_DATE,7)) AS PLAT_DATE,BUSI_NO,BUSI_NAME,'生活缴费平台' AS CHNL_NO,PROT_NO AS PAY_NO,PAY_ACCT,CUST_NAME AS NAME,SUBSTRING(LAST_UPT_TIME ,9) AS REQ_TIME,'' AS OWE_MONTH,'批量扣款' AS PAY_TP,ADDR,TRAN_AMT AS TOT_AMT,TRAN_AMT AS PRCTL_AMT,0 AS DCT_AMT,0 AS AMT,0 AS FEE_AMT,(CASE STAT WHEN '10' THEN '成功' ELSE '失败' END)TRAN_STAT,HOST_SEQ,BAT_NO AS REQ_SEQ,'' AS OTH_SEQ,'' AS OPEN_CUST_BRCH,0 AS SHORT_RMRK,'是' AS AUTO_FLG ");
            sql.append(" FROM ").append(table2);
            sql.append(" WHERE BUSI_NO = '").append(qryListReq.getBUSI_NO()).append("' ")
                    .append("AND　PLAT_DATE != '00' ")
                    .append("AND　PLAT_DATE >= '").append(qryListReq.getSTR_DATE()).append("' ")
                    .append("AND　PLAT_DATE <= '").append(qryListReq.getEND_DATE()).append("' ");
            String tran_stat = qryListReq.getTRAN_STAT();
            if (StringUtils.isNotEmpty(tran_stat) && !"AA".equals(tran_stat)) {
                if ("01".equals(tran_stat)) {
                    sql.append("AND STAT = '10' ");
                } else {
                    sql.append("AND STAT != '10' ");
                }
            }
            //缴费号
            String pay_no = qryListReq.getPAY_NO();
            if (StringUtils.isNotEmpty(pay_no)) {
                sql.append("AND PROT_NO = ? ");
                params.add(pay_no);
            }
            String acct = qryListReq.getACCT();
            if (StringUtils.isNotEmpty(pay_no)) {
                sql.append("AND PAY_ACCT = ? ");
                params.add(acct);
            }
        }
        List<Book> list = new ArrayList<>();
        try {
            list = session.getObjectListByList(sql.toString(), Book.class, params, new MBCCaseStrategy());
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

    private String getChnel(String channl) {
        List<CtrlTParaChnlDO> chnlInfo = ChnlDao.getInstance().getChnlInfo();
        for (CtrlTParaChnlDO ctrlTParaChnlDO : chnlInfo) {
            if (channl.equals(ctrlTParaChnlDO.getChnlNo())) {
                return ctrlTParaChnlDO.getChnlName();
            }
        }
        return channl;
    }


    @Override
    public int insert(BookDao objDO) {
        return 0;
    }

    @Override
    public int update(BookDao objDO) {
        return 0;
    }

    @Override
    public int delete(BookDao objDO) {
        return 0;
    }

    @Override
    public BookDao get(BookDao objDO) {
        return null;
    }

    @Override
    public List<BookDao> list(BookDao objDO) {
        return null;
    }

    @Override
    public List<BookDao> list(BookDao objDO, int start, int limit) {
        return null;
    }

    @Override
    public List<BookDao> list(int start, int limit, Object... param) {
        return null;
    }
}
