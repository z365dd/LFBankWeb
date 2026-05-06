/**
* 系统名称: SmartWeb平台
* 模块名称: sys-modules持久化模块
* 功能描述: 公告消息数据库操作
* 类 名 称  : SysNoticeDao.java
* 软件版权: 北京先进数通信息技术股份公司
* 开发人员: chenyl <br>
* 开发时间: 20190829<br>
* 系统版本: V1.0.0<br>
** 修改记录:
* 修改日期                            修改人员          修改说明 <br>
* ========     ======  ============================================
* 
* ========     ======  ============================================
*/
package com.adtec.sys.modules.sys.dao;

import static com.adtec.sys.common.persistence.BaseDO.DEL_FLAG_NORMAL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.common.dao.IBaseDao;
import com.adtec.sys.modules.sys.entity.SysNoticeDO;
import com.adtec.sys.modules.sys.entity.SysNoticeDataDO;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;

/**
 * 公告消息Dao接口
 * @author z'x
 * @version 20190829
 */
@Component
public class SysNoticeDao implements IBaseDao<SysNoticeDO>{
	/**
	 * 日志对象
	 */
	protected final static Logger logger = LoggerFactory.getLogger(SysNoticeDao.class);
	/*表名称*/
    public static final String TABLE_NAME = "T_SYS_NOTICE";
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public SysNoticeDO get(String id) {
		SysNoticeDO obj = new SysNoticeDO();
		obj.setId(id);
		return get(obj);
	}
	
	/**
	 * 获取单条数据
	 * @param obj
	 * @return
	 */
	@Override
    public SysNoticeDO get(SysNoticeDO obj) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where id=? AND del_flg='" + DEL_FLAG_NORMAL + "'");
        SysNoticeDO rs = null;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.getObject(sql.toString(), SysNoticeDO.class, obj.getId());
        } catch (Exception e) {
            logger.error("获取公告消息异常："+e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "获取公告消息失败！");
        }
        return rs;
    }
	
	/**
	 * 插入数据
	 * @param obj
	 * @return
	 */
	@Override
	public int insert(SysNoticeDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getNewSession();
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			rs = session.saveObject(TABLE_NAME, obj, ignoreFields);
			insertIdsData(obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("新增公告消息交易异常："+e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "新增公告消息交易失败！");
		}
		finally {
			try {
				DBSessionFactory.closeSession(session);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rs;
	}
	
	/**
	 * 更新数据
	 * @param obj
	 * @return
	 */
	@Override
	public int update(SysNoticeDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getNewSession();
		try {
			List<String> ignoreFields = obj.getIgnoreFields();
			List<String> matchFields = obj.getMatchFields();
			rs = session.updateObject(TABLE_NAME, obj, matchFields, ignoreFields);
			//先删除再插入id数据
			deleteData(obj.getId());
			insertIdsData(obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("修改公告消息异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "修改公告消息失败！");
		}
		finally {
			try {
				DBSessionFactory.closeSession(session);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rs;
	}
	
	/**
	 * 根据主键id删除数据（一般为逻辑删除，更新del_flg字段为1）
	 * @param id
	 * @see public int delete(T entity)
	 * @return
	 */
	public int delete(String id){
		SysNoticeDO obj = new SysNoticeDO();
		obj.setId(id);
		return delete(obj);
	}
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flg字段为1）
	 * @param obj
	 * @return
	 */
	@Override
	public int delete(SysNoticeDO obj){
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			String sql = "delete from T_SYS_NOTICE where id=?";
			rs = session.execute(sql, obj.getId());
			if(rs==0){
				throw new BaseException(SysErr.E_MESSAGE, "删除交易失败，影响记录数："+rs);
			}
			deleteData(obj.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("删除交易异常："+e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "删除交易失败！");
		}
		return rs;
	}
	
	/**
	 * 数据库多笔查询，不分页
	 * @param obj 数据对象DO
	 * @return List返回集合
	 */
	@Override
	public List<SysNoticeDO> list(SysNoticeDO obj) {
		// TODO Auto-generated method stub
		return list(obj, 0, 0);
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param obj 数据对象DO
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @return List返回集合
	 */
	@Override
	public List<SysNoticeDO> list(SysNoticeDO obj, int start, int limit) {
		logger.debug("SysNoticeDO=" + obj.toString());
		logger.debug("start=" + start);
		logger.debug("limit=" + limit);
		List<SysNoticeDO> list = null;
		/*20210111 mod by chenyl for SQL使用占位符*/
		List<Object> parameters = Lists.newArrayList();
		try {
			StringBuilder sql = new StringBuilder();
        	sql.append(getQuerySql(obj, parameters));
        	sql.append(" order by upt_time desc");
        	logger.debug("sql=" + sql.toString());
        	IDBSession session = DBSessionFactory.getSession();
			if (limit == 0) {
				list = session.getObjectListByList(sql.toString(), SysNoticeDO.class, parameters);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), SysNoticeDO.class, start, limit, parameters);
			}
		} catch (Exception e) {
			logger.error("列表查询异常：" + e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "列表查询失败！");
		}
		return list;
	}
	
	/**
	 * 数据库多笔查询，支持分页
	 * @param start 起始位置
	 * @param limit 每页数量
	 * @param param 查询参数
	 * @return List返回集合
	 */
	@Override
	public List<SysNoticeDO> list(int start, int limit, Object... param) {
		return null;
	}
	
	/**
	 * 根据数据对象产生对应的数据查询总记录
	 * @param obj 数据对象DO
	 * @return total
	 */
	public int getTotal(SysNoticeDO obj) {
		logger.debug("SysNoticeDO=" + obj);
        StringBuilder sql = new StringBuilder();
        /*20210111 mod by chenyl for SQL使用占位符*/
		List<Object> parameters = Lists.newArrayList();
        sql.append(getQuerySql(obj, parameters));
        String countSql = "select count(1) from (" + sql.toString() + ") ct";
        logger.debug("countSql=" + countSql);
        IDBSession session = DBSessionFactory.getSession();       
		int total = 0;
        try {
        	total = session.accountByList(countSql, parameters);
        } catch (Exception e) {
        	logger.error("总记录数查询T_SYS_NOTICE异常：" + e.getMessage());
        	throw new BaseException(SysErr.E_MESSAGE, "总记录数查询T_SYS_NOTICE失败！");
        }
        return total;
	}
	
	/**
	 * 根据数据对象产生对应的查询SQL
	 * @param obj 数据对象DO
	 * @return SQL
	 */
	public String getWhereSql(SysNoticeDO obj, List<Object> parameters) {
		StringBuffer sql = new StringBuffer();
			sql.append(" AND del_flg='").append(DEL_FLAG_NORMAL).append("' ");
		if (!DataUtil.isNullStr(obj.getNoteTitle())) {
			sql.append(" AND note_title LIKE ? ");
			parameters.add("%"+obj.getNoteTitle()+"%");
		}
		return sql.toString();
	}
	
	
	   /**
     * 插入数据
     * @param obj
     * @return
     */
    public void insertIdsData(SysNoticeDO obj){
        IDBSession session = DBSessionFactory.getSession();
        try {
            List<List<Object>> parameters = Lists.newArrayList();
            String[] arr = {};
            String sql = "insert into T_SYS_NOTICE_DATA (note_id,data_id) "
                    + "values (? ,?)";
            if(obj.getIds() != null && !"".equals(obj.getIds())){
                arr = obj.getIds().split(",");
                for (int i = 0; i < arr.length; i++) {
                	List<Object> parameter = Lists.newArrayList();
                    parameter.add(obj.getId());
                    parameter.add(arr[i]);
                    parameters.add(parameter);
                }
            }
            if(parameters.size() > 0){
                session.executeBatchByList(sql, parameters);
            }
        } catch (Exception e) {
            logger.error("新增公告消息交易异常："+e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "新增公告消息交易失败！");
        }
    }
    
    public List<SysNoticeDataDO> listData(String id) {
        List<SysNoticeDataDO> list = null;
        try {
            StringBuilder sql = new StringBuilder();
            /*20210111 mod by chenyl for sql使用占位符*/
            sql.append("select * from T_SYS_NOTICE_DATA where note_id = ?");
            logger.debug("sql=" + sql.toString());
            IDBSession session = DBSessionFactory.getSession();
            list = session.getObjectList(sql.toString(), SysNoticeDataDO.class, id);
        } catch (Exception e) {
            logger.error("列表查询异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "列表查询失败！");
        }
        return list;
    }
    
    public void deleteData(String id) {
        try {
            IDBSession session = DBSessionFactory.getSession();
            String sql = "delete from T_SYS_NOTICE_DATA where note_id=?";
            logger.debug("sql=" + sql.toString());
            session.execute(sql, id);
        } catch (Exception e) {
            logger.error("删除数据异常：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "列表查询失败！");
        }
    }
    
    public String getQuerySql(SysNoticeDO obj, List<Object> parameters) {
        User u = UserUtils.getUser();
        String userId = u.getId();
        String brchId = u.getOffice().getId();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from T_SYS_NOTICE t where ( t.NOTE_SCP = '0' or t.crtr= ? ")
        .append(" or t.id in ( select a.note_id from T_SYS_NOTICE_DATA a where a.data_id= ? ")
        .append(" or a.data_id = ? ")
        .append(" or a.data_id in (select b.role_id from T_SYS_USER_ROLE b where b.user_id= ? ")
        .append(")))").append(" and t.del_flg='").append(DEL_FLAG_NORMAL).append("' ");
        parameters.add(userId);
        parameters.add(userId);
        parameters.add(brchId);
        parameters.add(userId);
        if (!DataUtil.isNullStr(obj.getNoteTitle())) {
            sql.append(" and t.note_title LIKE ? ");
            parameters.add("%"+obj.getNoteTitle()+"%");
        }
        return sql.toString();
    }

}