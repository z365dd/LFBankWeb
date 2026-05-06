package com.adtec.pay.service;


import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.pay.dao.ProjectDao;
import com.adtec.pay.dto.NoReturnRes;
import com.adtec.pay.dto.proj.chk.ProjInspModReq;
import com.adtec.pay.dto.proj.chk.ProjInspQryReq;
import com.adtec.pay.dto.proj.chk.ProjInspQryRes;
import com.adtec.pay.dto.proj.mng.MngProjModReq;
import com.adtec.pay.dto.proj.mng.MngProjQryReq;
import com.adtec.pay.dto.proj.mng.MngProjQryRes;
import com.adtec.pay.dto.proj.rec.ProjInspRecReq;
import com.adtec.pay.dto.proj.rec.ProjInspRecResList;
import com.adtec.pay.utils.MLppUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectOptService {
    private final static Logger logger = LoggerFactory.getLogger(ProjectOptService.class);

    @Autowired
    private ProjectDao projectDao;

    /**
     * 管理项目查询
     * @param req
     * @param start
     * @param limit
     * @return
     */
    public MngProjQryRes listMngProj(MngProjQryReq req, int start, int limit) {

        ReqDTO reqDTO = new ReqDTO(req);
        MLppUtils.setReqHead(reqDTO, "MLppMngProjQry", start, limit);
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        // 和Starring V6进行通信
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("MLPP_PARTID"), "MLppMngProjQry",
                reqDTO, MngProjQryRes.class, null);
        MngProjQryRes mngProjQryRes = (MngProjQryRes) resDTO.getBODY();
        return mngProjQryRes;
    }

    /**
     * 管理项目维护
     * @param req
     * @return
     */
    public IDataset callProjMngModify(MngProjModReq req) {

        ReqDTO reqDTO = new ReqDTO(req);
        MLppUtils.setReqHead(reqDTO, "MLppMngProjMod",0,0);
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("MLPP_PARTID"), "MLppMngProjMod", reqDTO, NoReturnRes.class, null);
        NoReturnRes res = new NoReturnRes();
        if (null != resDTO.getBODY()) {
            res = (NoReturnRes) resDTO.getBODY();
        }
        IDataset responseData = DatasetService.getInstace().getDataset(res, NoReturnRes.class);
        return responseData;
    }




    /**
     * 获取项目巡检列表
     * @param req
     * @param start
     * @param limit
     * @return
     */
    public ProjInspQryRes listProjChk(ProjInspQryReq req, int start, int limit) {
        ReqDTO reqDTO = new ReqDTO(req);
        MLppUtils.setReqHead(reqDTO, "MLppProjInspQry", start, limit);
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        // 和Starring V6进行通信
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("MLPP_PARTID"), "MLppProjInspQry",
                reqDTO, ProjInspQryRes.class, null);
        ProjInspQryRes  projInspQryRes= (ProjInspQryRes) resDTO.getBODY();
        return projInspQryRes;
    }

    /**
     * 项目巡检维护
     * @param req
     * @return
     */
    public IDataset callProjInspModify(ProjInspModReq req) {
        ReqDTO reqDTO = new ReqDTO(req);
        MLppUtils.setReqHead(reqDTO, "MLppProjInspMod",0,0);
        HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
        ResDTO resDTO = (ResDTO) httpJsonFactory.callService("", ParamUtil.getConfig("MLPP_PARTID"), "MLppProjInspMod", reqDTO, NoReturnRes.class, null);
        NoReturnRes res = new NoReturnRes();
        if (null != resDTO.getBODY()) {
            res = (NoReturnRes) resDTO.getBODY();
        }
        IDataset responseData = DatasetService.getInstace().getDataset(res, NoReturnRes.class);
        return responseData;
    }

    /**
     * 巡检记录查询
     * @param projInspRecReq
     * @param start
     * @param limit
     * @return
     */
    public List<ProjInspRecResList> listInspRec(ProjInspRecReq projInspRecReq, int start, int limit) {
        return projectDao.listInspRec(projInspRecReq,start,limit);
    }

    public int countInspRec(ProjInspRecReq projInspRecReq) {
        return projectDao.countInspRec(projInspRecReq);
    }
}

