package com.adtec.sys.modules.sys.dao;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.modules.sys.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class BusiUserDao {
    private final static Logger logger = LoggerFactory.getLogger(BusiUserDao.class);

    public int delete(User user){
        int i;
        IDBSession session = DBSessionFactory.getSession();
        String sql = "delete from T_SYS_BUSI_USER WHERE user_id=?";
        Object[] params = {user.getId()};
        try {
            i = session.execute(sql, params);
        } catch (SQLException e) {
            logger.error("更新数据失败：" + e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "更新数据失败！");
        }
        return i;
    }
}
