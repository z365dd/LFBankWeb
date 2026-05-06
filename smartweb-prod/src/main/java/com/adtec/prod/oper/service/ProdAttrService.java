package com.adtec.prod.oper.service;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.prod.oper.dao.ProdAttrDao;
import com.adtec.prod.oper.dao.ProdAttrParaDao;
import com.adtec.prod.oper.dao.TPipAttrSvcDao;
import com.adtec.prod.oper.entity.ProdAttrDO;
import com.adtec.prod.oper.entity.ProdAttrParaDO;
import com.adtec.prod.oper.entity.TPipAttrSvcDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProdAttrService {
    private final static Logger log = LoggerFactory.getLogger(ProdAttrService.class);

    @Autowired
    private ProdAttrDao prodAttrDao;
    @Autowired
    private ProdAttrParaDao prodAttrParaDao;
    @Autowired
    private TPipAttrSvcDao tPipAttrSvcDao;

    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    public ProdAttrDO get(String id) {
        return prodAttrDao.get(id);
    }

    /**
     * 获取单条数据
     *
     * @param obj
     * @return
     */
    public ProdAttrDO get(ProdAttrDO obj) {
        return prodAttrDao.get(obj);
    }

    public ProdAttrDO getByName(ProdAttrDO obj) {
        return prodAttrDao.getByName(obj);
    }

    /**
     * 插入数据
     *
     * @param obj
     * @return
     */
    public boolean insert(ProdAttrDO obj) {
        int rs = 0;
        obj.preInsert();
        rs = prodAttrDao.insert(obj);
        return rs > 0 ? true : false;
    }
    
    /**
     * 插入数据svc
     *
     * @param obj
     * @return
     */
    public boolean insertSvc(ProdAttrParaDO obj) {
        int rs = 0;
        obj.preInsert();
        rs = prodAttrParaDao.insert(obj);
        return rs > 0 ? true : false;
    }
    

    /**
     * 更新数据
     *
     * @param obj
     * @return
     */
    public boolean update(ProdAttrDO obj) {
        int rs = 0;
        if (null == obj) {
            throw new BaseException(SysErr.E_DEFAULT, "更新数据对象不能空！");
        }
        if (DataUtil.isNullStr(obj.getCOMP_NO())) {
            throw new BaseException(SysErr.E_IN_NULL, "COMP_NO");
        }
        rs = prodAttrDao.update(obj);
        return rs > 0 ? true : false;
    }

    /**
     * 删除数据（直接删除数据）
     *
     * @param obj
     * @return
     */
    public boolean delete(ProdAttrDO obj) {
        int rs = 0;
        if (null == obj) {
            throw new BaseException(SysErr.E_DEFAULT, "删除数据对象不能空！");
        }
        if (DataUtil.isNullStr(obj.getCOMP_NO())) {
            throw new BaseException(SysErr.E_IN_NULL, "COMP_NO");
        }
        ProdAttrParaDO paraDO = new ProdAttrParaDO();
        TPipAttrSvcDO svcDO = new TPipAttrSvcDO();
        //删除组件属性表对应数据
        paraDO.setCOMP_NO(obj.getCOMP_NO());
        svcDO.setCompNo(obj.getCOMP_NO());
        int svcCount = tPipAttrSvcDao.list(svcDO).size();
        if(svcCount!=0){
        	throw new BaseException(SysErr.E_DEFAULT,"该组件已存在服务，不能删除");
        }
        rs = prodAttrDao.delete(obj);
    	prodAttrParaDao.delete(paraDO);
        return rs > 0 ? true : false;
    }

    /**
     * 数据库多笔查询
     *
     * @param obj 数据对象DO
     * @return List返回集合
     */
    public List<ProdAttrDO> list(ProdAttrDO obj) {
        return prodAttrDao.list(obj);
    }

    /**
     * 数据库多笔查询，支持分页
     *
     * @param obj   数据对象DO
     * @param start 起始位置
     * @param limit 每页数量
     * @return List返回集合
     */
    public List<ProdAttrDO> list(ProdAttrDO obj, int start, int limit) {
        return prodAttrDao.list(obj, start, limit);
    }

    /**
     * 数据库多笔查询，支持分页
     *
     * @param start 起始位置
     * @param limit 每页数量
     * @param param 查询参数
     * @return List返回集合
     */
    public List<ProdAttrDO> list(int start, int limit, Object... param) {
        return prodAttrDao.list(start, limit, param);
    }

    /**
     * 根据数据对象产生对应的数据查询总记录
     *
     * @param obj 数据对象DO
     * @return total
     */
    public int getTotal(ProdAttrDO obj) {
        return prodAttrDao.getTotal(obj);
    }

    /**
     * 列表查询after
     *
     * @param reqDO
     * @param start
     * @param limit
     * @return
     */
    public List<ProdAttrDO> qry(ProdAttrDO reqDO,int start,int limit){
        return prodAttrDao.list(reqDO,start,limit);
    }

    /**
     * 修改状态
     *
     * @param prodAttrDO
     * @return
     */
	public void updateStat(ProdAttrDO prodAttrDO) {
		prodAttrDao.updateStat(prodAttrDO);
		
	}


}
