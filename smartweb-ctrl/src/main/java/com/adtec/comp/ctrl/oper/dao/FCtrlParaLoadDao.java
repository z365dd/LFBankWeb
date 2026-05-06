package com.adtec.comp.ctrl.oper.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.adtec.comp.ctrl.oper.entity.FCtrlParaLoadDO;
import com.adtec.comp.ctrl.oper.entity.FCtrlParaLoadDO;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.common.dao.IBaseDao;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;

@Component
public class FCtrlParaLoadDao implements IBaseDao<FCtrlParaLoadDO>{
	 /**
     * 日志对象
     */
    protected final static Logger logger = LoggerFactory.getLogger(FCtrlParaLoadDao.class);
    /*表名称*/
    public static final String TABLE_NAME = "T_PARA_RULES_STG";
	 /**
     * 数据库多笔查询，不分页
     *
     * @param obj 数据对象DO
     * @return List返回集合
     */
    @Override
    public List<FCtrlParaLoadDO> list(FCtrlParaLoadDO obj) {
        return list(obj, 0, 0);
    }

    /**
     * 数据库多笔查询，支持分页
     *
     * @param obj   数据对象DO
     * @param start 起始位置
     * @param limit 每页数量
     * @return List返回集合
     */
    @Override
    public List<FCtrlParaLoadDO> list(FCtrlParaLoadDO obj, int start, int limit) {
        logger.debug("FCtrlParaLoadDO=" + obj.toString());
        logger.debug("start=" + start);
        logger.debug("limit=" + limit);
        List<FCtrlParaLoadDO> list = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select * from ").append(TABLE_NAME).append(" where 1 = 1 ");
            if(obj.getEngName()!=null &&!"".equals(obj.getEngName())){
            	sql.append(" and ENG_NAME ="+ obj.getEngName());
            }
            
            if(obj.getTabName()!=null &&!"".equals(obj.getTabName())){
            	sql.append(" and TAB_NAME ="+ obj.getTabName());
            }
           
            logger.debug("sql=" + sql.toString());
            IDBSession session = DBSessionFactory.getSession("fctrl");
          
            if (limit == 0) {
                list = session.getObjectList(sql.toString(), FCtrlParaLoadDO.class);
            } else {
                list = session.getObjectListForPage(sql.toString(), FCtrlParaLoadDO.class, start, limit);
            }
        } catch (Exception e) {
            logger.error("列表查询异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "列表查询失败！");
        }
        return list;
    }
    
	@Override
	public int insert(FCtrlParaLoadDO objDO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(FCtrlParaLoadDO objDO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(FCtrlParaLoadDO objDO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public FCtrlParaLoadDO get(FCtrlParaLoadDO objDO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FCtrlParaLoadDO> list(int start, int limit, Object... param) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
