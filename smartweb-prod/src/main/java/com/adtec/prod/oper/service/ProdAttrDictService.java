package com.adtec.prod.oper.service;

import com.adtec.comm.dto.ReqDTO;
import com.adtec.comm.dto.ResDTO;
import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.prod.oper.dao.ProdAttrDictDao;
import com.adtec.prod.oper.entity.ProdAttrDictDO;
import com.adtec.prod.util.ProdUtil;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.prod.dto.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class ProdAttrDictService {
    private final static Logger log = LoggerFactory.getLogger(ProdAttrDictService.class);

    @Autowired
    private ProdAttrDictDao prodAttrDictDao;

    /**
     * 获取单条数据
     *
     * @param obj
     * @return
     */
    public ProdAttrDictDO get(ProdAttrDictDO obj) {
        return prodAttrDictDao.get(obj);
    }

    /**
     * 获取单条数据
     *
     * @param obj
     * @return
     */
    public ProdAttrDictDO getByName(ProdAttrDictDO obj) {
        return prodAttrDictDao.getByName(obj);
    }

    /**
     * 插入数据
     *
     * @param obj
     * @return
     */
    public boolean insert(ProdAttrDictDO obj) {
        int rs = 0;
//        obj.preInsert();
        rs = prodAttrDictDao.insert(obj);
        return rs > 0 ? true : false;
    }

    /**
     * 更新数据
     *
     * @param obj
     * @return
     */
    public boolean update(ProdAttrDictDO obj) {
        int rs = 0;
        if (null == obj) {
            throw new BaseException(SysErr.E_DEFAULT, "更新数据对象不能空！");
        }
//        if (DataUtil.isNullStr(obj.getId())) {
//            throw new BaseException(SysErr.E_IN_NULL, "id");
//        }
        // 获取数据库保存的数据
//        ProdAttrDictDO qryDO = prodAttrDictDao.get(obj.getId());
        // 更新产品数据
//        obj.setCreateBy(qryDO.getCreateBy());
//        obj.setCreateDate(qryDO.getCreateDate());
//        obj.preUpdate();
        rs = prodAttrDictDao.update(obj);
        return rs > 0 ? true : false;
    }

    /**
     * 删除数据（一般为逻辑删除，更新del_flag字段为1）
     *
     * @param obj
     * @return
     */
    public boolean delete(ProdAttrDictDO obj) {
        int rs = 0;
        if (null == obj) {
            throw new BaseException(SysErr.E_DEFAULT, "删除数据对象不能空！");
        }
//        if (DataUtil.isNullStr(obj.getId())) {
//            throw new BaseException(SysErr.E_IN_NULL, "id");
//        }
        rs = prodAttrDictDao.delete(obj);
        return rs > 0 ? true : false;
    }

    /**
     * 数据库多笔查询
     *
     * @param obj 数据对象DO
     * @return List返回集合
     */
    public List<ProdAttrDictDO> list(ProdAttrDictDO obj) {
        return prodAttrDictDao.list(obj);
    }

    /**
     * 数据库多笔查询，支持分页
     *
     * @param obj   数据对象DO
     * @param start 起始位置
     * @param limit 每页数量
     * @return List返回集合
     */
    public List<ProdAttrDictDO> list(ProdAttrDictDO obj, int start, int limit) {
        return prodAttrDictDao.list(obj, start, limit);
    }

    /**
     * 数据库多笔查询，支持分页
     *
     * @param start 起始位置
     * @param limit 每页数量
     * @param param 查询参数
     * @return List返回集合
     */
    public List<ProdAttrDictDO> list(int start, int limit, Object... param) {
        return prodAttrDictDao.list(start, limit, param);
    }

    /**
     * 根据数据对象产生对应的数据查询总记录
     *
     * @param obj 数据对象DO
     * @return total
     */
    public int getTotal(ProdAttrDictDO obj) {
        return prodAttrDictDao.getTotal(obj);
    }

    public Map<String, String> getCompParaDistinct() {
        return prodAttrDictDao.getCompParaDistinct();
    }

    public Map<String, String> getSvcParaDistinct() {
        return prodAttrDictDao.getSvcParaDistinct();
    }

}
