package com.adtec.prod.oper.service;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.prod.oper.dao.AreaDao;
import com.adtec.prod.oper.dao.BusiSignDao;
import com.adtec.prod.oper.dao.EntrDemoDao;
import com.adtec.prod.oper.entity.AreaDo;
import com.adtec.prod.oper.entity.BusiSignDO;
import com.adtec.prod.oper.entity.BusiTypeDo;
import com.adtec.prod.oper.entity.EntrDemoDO;
import com.adtec.sys.modules.sys.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class EntrDemoService {
    private final static Logger log = LoggerFactory.getLogger(EntrDemoService.class);

    @Autowired
    private EntrDemoDao entrDemoDao;
    @Autowired
    private BusiSignDao busiSignDao;

    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    public EntrDemoDO get(String id) {
        return entrDemoDao.get(id);
    }

    /**
     * 获取单条数据
     *
     * @param obj
     * @return
     */
    public EntrDemoDO get(EntrDemoDO obj) {
        return entrDemoDao.get(obj);
    }

    /**
     * 插入数据
     *
     * @param obj
     * @return
     */
    public boolean insert(EntrDemoDO obj) {
        int rs = 0;
        obj.preInsert();
        obj.setCrtr(UserUtils.getUser().getId());
        obj.setCrtTime(DateUtil.getDateTime());
        obj.setUptr(UserUtils.getUser().getId());
        obj.setUptTime(DateUtil.getDateTime());
        rs = entrDemoDao.insert(obj);
        return rs > 0 ? true : false;
    }

    /**
     * 更新数据
     *
     * @param obj
     * @return
     */
    public boolean update(EntrDemoDO obj) {
        int rs = 0;
        if (null == obj) {
            throw new BaseException(SysErr.E_DEFAULT, "更新数据对象不能空！");
        }
        if (DataUtil.isNullStr(obj.getEntrNo())) {
            throw new BaseException(SysErr.E_IN_NULL, "EntrNO");
        }
        // 获取数据库保存的数据
        EntrDemoDO qryDO = entrDemoDao.get(obj.getEntrNo());
        obj.preUpdate();
        obj.setUptr(UserUtils.getUser().getId());
        obj.setUptTime(DateUtil.getDateTime());
        rs = entrDemoDao.update(obj);
        return rs > 0 ? true : false;
    }

    /**
     * 根据主键id删除数据（一般为逻辑删除，更新del_flag字段为1）
     *
     * @param id
     * @return
     * @see public int delete(T entity)
     */
    public boolean delete(String id) {
        int rs = 0;
        if (DataUtil.isNullStr(id)) {
            throw new BaseException(SysErr.E_IN_NULL, "id");
        }
        rs = entrDemoDao.delete(id);
        return rs > 0 ? true : false;
    }

    /**
     * 删除数据（一般为逻辑删除，更新del_flag字段为1）
     *
     * @param obj
     * @return
     */
    public boolean delete(EntrDemoDO obj) {
        int rs = 0;
        if (null == obj) {
            throw new BaseException(SysErr.E_DEFAULT, "删除数据对象不能空！");
        }
        if (DataUtil.isNullStr(obj.getEntrNo())) {
            throw new BaseException(SysErr.E_IN_NULL, "id");
        }
        BusiSignDO busiSignDO = new BusiSignDO();
        busiSignDO.setEntrNo(obj.getEntrNo());
        int count = busiSignDao.getTotal(busiSignDO);
        if (count != 0) {
            throw new BaseException(SysErr.E_DEFAULT, "该单位已存在业务，不能删除");
        }
        rs = entrDemoDao.delete(obj);
        return rs > 0 ? true : false;
    }

    /**
     * 数据库多笔查询
     *
     * @param obj 数据对象DO
     * @return List返回集合
     */
    public List<EntrDemoDO> list(EntrDemoDO obj) {
        return entrDemoDao.list(obj);
    }

    /**
     * 数据库多笔查询，支持分页
     *
     * @param obj   数据对象DO
     * @param start 起始位置
     * @param limit 每页数量
     * @return List返回集合
     */
    public List<EntrDemoDO> list(EntrDemoDO obj, int start, int limit) {
        return entrDemoDao.list(obj, start, limit);
    }

    /**
     * 数据库多笔查询，支持分页
     *
     * @param start 起始位置
     * @param limit 每页数量
     * @param param 查询参数
     * @return List返回集合
     */
    public List<EntrDemoDO> list(int start, int limit, Object... param) {
        return entrDemoDao.list(start, limit, param);
    }

    /**
     * 根据单位名称查询
     *
     * @param obj
     * @return
     */
    public List<EntrDemoDO> qryName(EntrDemoDO obj) {
        return entrDemoDao.qryName(obj);
    }

    /**
     * 根据数据对象产生对应的数据查询总记录
     *
     * @param obj 数据对象DO
     * @return total
     */
    public int getTotal(EntrDemoDO obj) {
        return entrDemoDao.getTotal(obj);
    }

    public void add(EntrDemoDO reqBody) {
        reqBody.setCrtr(UserUtils.getUser().getId());
        reqBody.setCrtTime(DateUtil.getDateTime());
        reqBody.setUptr(UserUtils.getUser().getId());
        reqBody.setUptTime(DateUtil.getDateTime());
        entrDemoDao.insert(reqBody);
    }

    /**
     * 获取详细数据 after
     *
     * @param DO
     * @return
     */
    public EntrDemoDO getDetail(EntrDemoDO DO) {
        return entrDemoDao.get(DO);
    }

    /**
     * 修改状态
     *
     * @param entrDemoDO
     */
    public int UpdateStat(EntrDemoDO entrDemoDO) {
        return entrDemoDao.updateStat(entrDemoDO);

    }

    /**
     * 获取最新的单位编号
     *
     * @return
     */
    public String getNewEntrNO() {
        // TODO Auto-generated method stub

        Date date = new Date();
        SimpleDateFormat formart = new SimpleDateFormat("YYMMdd");
        String dateStr = formart.format(date);
        List<EntrDemoDO> list = entrDemoDao.getNewEntrNo(dateStr);
        StringBuffer ENTR_NO = new StringBuffer();
        if (list != null && list.size() > 0) {
            String ENTR_NO_MAX = list.get(0).getEntrNo();
            String temp = String.valueOf(Integer.valueOf(ENTR_NO_MAX.substring(dateStr.length())) + 1);
            ENTR_NO.append(dateStr);
            for (int i = 1; i <= 4; i++) {
                if (i > temp.length()) {
                    ENTR_NO.append("0");
                }
            }
            ENTR_NO.append(temp);
        } else {
            ENTR_NO.append(dateStr).append("0001");
        }
        return ENTR_NO.toString();
    }

    public List<AreaDo> getEntrDict(String parentId) {
        List<AreaDo> list = new AreaDao().list(parentId);

        return list;
    }

	public List<BusiTypeDo> getBusiType() {
		List<BusiTypeDo> list = new BusiTypeDao().list();
        System.out.println("list ------->" + list);
		return list;
	}

}
