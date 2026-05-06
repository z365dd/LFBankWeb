package com.adtec.pay.dao;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.impl.share.caseStrategy.MBCCaseStrategy;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.pay.dto.proj.chk.ProjInspQryResList;
import com.adtec.pay.dto.proj.mng.MngProjQryRes;
import com.adtec.pay.dto.proj.mng.MngProjQryResList;
import com.adtec.pay.dto.proj.rec.ProjInspRecReq;
import com.adtec.pay.dto.proj.rec.ProjInspRecResList;
import com.adtec.pay.entity.ProjDo;
import com.adtec.pay.entity.Template;
import com.adtec.pay.entity.proj.ProjMngDO;
import com.adtec.sys.common.dao.IBaseDao;
import com.alibaba.excel.util.ListUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class ProjectDao implements IBaseDao<T> {


    private final static String TABLE_NAME = "T_MLPP_PROJ_INSP_REC";

    private final static Logger logger = LoggerFactory.getLogger(ProjectDao.class);

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
     * 巡检记录查询
     * @return
     */
    public List<ProjInspRecResList> listInspRec(ProjInspRecReq projInspRecReq, int start, int limit) {
        IDBSession session = DBSessionFactory.getSession();
        String projTp = projInspRecReq.getPROJ_TP();
        String brch = projInspRecReq.getBRCH();
        String custName = projInspRecReq.getCUST_NAME();
        List<Object> parameters = Lists.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM T_MLPP_PROJ_INSP_REC WHERE PROJ_TP = ? AND CUST_NAME = ? AND BRCH = ?");
        parameters.add(projTp);
        parameters.add(custName);
        parameters.add(brch);
        List<ProjInspRecResList> list = Lists.newArrayList();
        sql.append(" LIMIT ").append(start).append(",").append(limit);
        try {
            list = session.getObjectListByList(sql.toString(), ProjInspRecResList.class, parameters, new MBCCaseStrategy());
        } catch (SQLException e) {
            throw new BaseException(SysErr.E_MESSAGE, "查询失败" + e.getMessage());
        } finally {
            try {
                DBSessionFactory.closeSession(session);
            } catch (SQLException e) {
                logger.error("关闭删除数据异常：" + e.getMessage());
            }
        }
        MngProjQryRes mngProjQryRes = new MngProjQryRes();
        return list;
    }

    /**
     * 统计项目巡检记录条数
     * @param projInspRecReq
     * @return
     */
    public int countInspRec(ProjInspRecReq projInspRecReq) {
        IDBSession session = DBSessionFactory.getSession();
        String projTp = projInspRecReq.getPROJ_TP();
        String brch = projInspRecReq.getBRCH();
        String custName = projInspRecReq.getCUST_NAME();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT  COUNT(1)  FROM T_MLPP_PROJ_INSP_REC WHERE PROJ_TP = ? AND CUST_NAME = ? AND BRCH = ?");
        List<Object> paramList = ListUtils.newArrayList();
        paramList.add(projTp);
        paramList.add(custName);
        paramList.add(brch);
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
}
