package com.adtec.pay.dao;

import com.adtec.pay.entity.BatDtl;
import com.adtec.sys.common.dao.IBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UploadDAO implements IBaseDao<BatDtl> {


    @Override
    public int insert(BatDtl objDO) {
        return 0;
    }

    @Override
    public int update(BatDtl objDO) {
        return 0;
    }

    @Override
    public int delete(BatDtl objDO) {
        return 0;
    }

    @Override
    public BatDtl get(BatDtl objDO) {
        return null;
    }

    @Override
    public List<BatDtl> list(BatDtl objDO) {
        return null;
    }

    @Override
    public List<BatDtl> list(BatDtl objDO, int start, int limit) {
        return null;
    }

    @Override
    public List<BatDtl> list(int start, int limit, Object... param) {
        return null;
    }

    public void save(List<BatDtl> cacheDataList) {
        String name = "time";
        System.out.println("-------------------------->看剑！" + name);
    }
}
