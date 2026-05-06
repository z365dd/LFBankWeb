/**
 *
 */
package com.adtec.sys.modules.sys.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adtec.sys.common.persistence.Page;
import com.adtec.sys.modules.sys.dao.DictDao;
import com.adtec.sys.modules.sys.entity.Dict;
import com.adtec.sys.modules.sys.utils.DictUtils;

/**
 * 字典Service
 *
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class DictService {
	protected final static Logger log = LoggerFactory.getLogger(DictService.class);
    @Autowired
    private DictDao dao;

    public Dict get(String id) {
    	/*20200322 add by chenyl for 先从缓存中获取，如果不存在再从数据库中获取*/
    	Dict d = DictUtils.getDictById(id);
    	if(null==d){
    		d = dao.get(id);
    		DictUtils.insertDictInCache(d);
    	}
        return d;
    }

    public Dict get(String dictTp, String dictVal) {
        return dao.get(dictTp, dictVal);
    }

    /**
     * 查询字段类型列表
     * @return
     */
    public List<String> findTypeList() {
        return dao.findTypeList();
    }

    public Page<Dict> findPage(Page<Dict> page, Dict Dict) {
        Dict.setPage(page);

        List<Dict> list = dao.findList(Dict);
        page.setCount(list.size());
        int fromIndex = (page.getPageNo() - 1) * page.getPageSize();
        int toIndex = page.getPageNo() * page.getPageSize();
        if (-1 == page.getPageSize()) {
            toIndex = (int) page.getCount();
        }
        List<Dict> dictList = list.subList(fromIndex, toIndex > (int) page.getCount() ? (int) page.getCount() : toIndex);

        page.setList(dictList);
        return page;
    }

    public List<Dict> findList(Dict entity) {
        return dao.findList(entity);
    }

    @Transactional(readOnly = false)
    public void save(Dict dict) {
        if (StringUtils.isBlank(dict.getId())){
            dict.preInsert();
            dao.insert(dict);
            DictUtils.insertDictInCache(dict);
        }else{
            dict.preUpdate();
            dao.update(dict);
            DictUtils.updateDictInCache(dict);
        }
    }

    @Transactional(readOnly = false)
    public void delete(Dict dict) {
        dao.delete(dict);
        DictUtils.deleteDictInCache(dict);
    }

}
