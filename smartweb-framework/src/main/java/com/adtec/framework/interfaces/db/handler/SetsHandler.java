package com.adtec.framework.interfaces.db.handler;

import com.adtec.framework.common.util.StringUtil;
import com.adtec.framework.ide.action.ResourceNavController;
import com.google.common.collect.Sets;
import org.apache.axis.utils.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

/**
 * ResultSet处理接口，返回结果转换成Set<String>
 *
 * @author lijunbin
 */
public class SetsHandler implements ResultSetHandler<Set<String>> {

    @Override
    public Set<String> apply(ResultSet rs) {
        Set<String> sets = Sets.newHashSet();
        try {
            while (rs.next()) {
                if(!StringUtils.isEmpty(rs.getString(1)))
                sets.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sets;
    }
}
