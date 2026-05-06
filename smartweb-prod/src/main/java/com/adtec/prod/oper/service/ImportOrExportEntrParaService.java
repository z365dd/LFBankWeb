package com.adtec.prod.oper.service;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.prod.oper.dao.ImportOrExportEntrParaDao;
import com.adtec.prod.oper.entity.BusiDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImportOrExportEntrParaService {
    private final static Logger log = LoggerFactory.getLogger(ProdAttrService.class);

    @Autowired
    private ImportOrExportEntrParaDao dao;

    public List<BusiDO> getBusiNo(BusiDO obj) {
        return dao.list(obj);
    }

    /**
     * 批量导入
     *
     * @param sqlList
     */
    public void importData(List<String> sqlList) {
        dao.importData(sqlList);

    }

    /**
     * 执行sql并返回插入sql
     *
     * @paramconn
     * @paramsm
     * @paramlistSQL *
     * @throwsSQLException
     */
    public void executeSQL(List<String> insertList, String busiNo) throws SQLException {

        List<String> listSQL = new ArrayList<String>();
        List<String> tablePub = new ArrayList<String>();
        List<String> dataSourList = new ArrayList<String>();

//    	String[] tablearr = {"T_PARA_BRCH","T_PARA_TLR","T_PARA_CHNL","T_PARA_TLR_LVL","T_PARA_TNT","T_PARA_LEGA","T_PARA_CARD_TYPE","T_UNION_BANK","T_ROUTE_STAT","T_ROUTE_ACCT"};//公共表 "T_PARA_RELAT_SYS",
// 		
// 		for(String str :tablearr){
// 			tablePub.add(str);
// 		}
//         //公共参数
//         for (int i = 0; i < tablePub.size(); i++) {
//             StringBuffer sb = new StringBuffer();
//             sb.append("SELECT * FROM").append(" ").append(tablePub.get(i));
//             listSQL.add(sb.toString());
//             dataSourList.add("local");
//         }

        //业务相关参数
        getBusiInserSqlByBusiNo(tablePub, listSQL, dataSourList, busiNo);

        getColumnNameAndColumeValue(insertList, listSQL, dataSourList, tablePub);
    }

    /**
     * 创建insertsql.txt并导出数据
     */
    public File createFile(List<String> insertList, File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("创建文件名失败！！");
            }
        }
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            if (insertList.size() > 0) {
                for (int i = 0; i < insertList.size(); i++) {
                    bw.append(insertList.get(i));
                    bw.append("\n");
                }
            }
        } catch (IOException e) {
        	log.error("读取失败");
        } finally {
            if (null != bw) {
                try {
                    bw.close();
                } catch (IOException e) {
					log.error("关闭失败");
                }
            }
            if (null != fw) {
                try {
                    fw.close();
                } catch (IOException e) {
					log.error("关闭失败");
                }
            }
        }
        return file;
    }

    /**
     * 获取列名和列值
     *
     * @return
     * @paramsm
     * @paramlistSQL
     * @paramrs
     * @throwsSQLException
     */
    public void getColumnNameAndColumeValue(List<String> insertList, List<String> listSQL, List<String> dataSourList, List<String> tablePub) throws SQLException {
        if (listSQL.size() > 0) {
            for (int j = 0; j < listSQL.size(); j++) {
                String sql = String.valueOf(listSQL.get(j));
                ResultSet rs = null;
                try {
                    if (("local").equals(dataSourList.get(j))) {
                        rs = DBSessionFactory.getSession().getResultSet(sql);
                    } else {
                        rs = DBSessionFactory.getSession(dataSourList.get(j)).getResultSet(sql);
                    }
                    if(rs==null){
                        throw new BaseException(SysErr.E_NULL_POINTER, "查询结果为空");
                    }
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int columnCount = rsmd.getColumnCount();
                    while (rs!=null && rs.next()) {
                        StringBuffer ColumnName = new StringBuffer();
                        StringBuffer ColumnValue = new StringBuffer();
                        for (int i = 1; i <= columnCount; i++) {
                            String value = rs.getString(i);
                            if (null == value || "".equals(value)) {
                                value = "";
                            }
                            if (i == 1 || i == columnCount) {
                                if (i == columnCount) {
                                    ColumnName.append(",");
                                }
                                ColumnName.append(rsmd.getColumnName(i));
                                if (i == 1) {
                                    if (Types.CHAR == rsmd.getColumnType(i) || Types.VARCHAR == rsmd.getColumnType(i) || Types.LONGVARCHAR == rsmd.getColumnType(i)) {
                                        ColumnValue.append("'").append(value).append("',");
                                    } else if (Types.SMALLINT == rsmd.getColumnType(i) || Types.INTEGER == rsmd.getColumnType(i) || Types.BIGINT == rsmd.getColumnType(i) || Types.FLOAT == rsmd.getColumnType(i) || Types.DOUBLE == rsmd.getColumnType(i) || Types.NUMERIC == rsmd.getColumnType(i) || Types.DECIMAL == rsmd.getColumnType(i) || Types.TINYINT == rsmd.getColumnType(i)) {
                                        ColumnValue.append(value).append(",");
                                    } else if (Types.DATE == rsmd.getColumnType(i) || Types.TIME == rsmd.getColumnType(i) || Types.TIMESTAMP == rsmd.getColumnType(i)) {
                                        ColumnValue.append("timestamp'").append(value).append("',");
                                    } else {
                                        ColumnValue.append(value).append(",");

                                    }
                                } else {
                                    if (Types.CHAR == rsmd.getColumnType(i) || Types.VARCHAR == rsmd.getColumnType(i) || Types.LONGVARCHAR == rsmd.getColumnType(i)) {
                                        ColumnValue.append("'").append(value).append("'");
                                    } else if (Types.SMALLINT == rsmd.getColumnType(i) || Types.INTEGER == rsmd.getColumnType(i) || Types.BIGINT == rsmd.getColumnType(i) || Types.FLOAT == rsmd.getColumnType(i) || Types.DOUBLE == rsmd.getColumnType(i) || Types.NUMERIC == rsmd.getColumnType(i) || Types.DECIMAL == rsmd.getColumnType(i) || Types.TINYINT == rsmd.getColumnType(i)) {
                                        ColumnValue.append(value);
                                    } else if (Types.DATE == rsmd.getColumnType(i) || Types.TIME == rsmd.getColumnType(i) || Types.TIMESTAMP == rsmd.getColumnType(i)) {
                                        ColumnValue.append("timestamp'").append(value);
                                    } else {
                                        ColumnValue.append(value);

                                    }
                                }

                            } else {
                                ColumnName.append("," + rsmd.getColumnName(i));
                                if (Types.CHAR == rsmd.getColumnType(i) || Types.VARCHAR == rsmd.getColumnType(i) || Types.LONGVARCHAR == rsmd.getColumnType(i)) {
                                    ColumnValue.append("'").append(value).append("'").append(",");
                                } else if (Types.SMALLINT == rsmd.getColumnType(i) || Types.INTEGER == rsmd.getColumnType(i) || Types.BIGINT == rsmd.getColumnType(i) || Types.FLOAT == rsmd.getColumnType(i) || Types.DOUBLE == rsmd.getColumnType(i) || Types.NUMERIC == rsmd.getColumnType(i) || Types.DECIMAL == rsmd.getColumnType(i) || Types.TINYINT == rsmd.getColumnType(i)) {
                                    ColumnValue.append(value).append(",");
                                } else if (Types.DATE == rsmd.getColumnType(i) || Types.TIME == rsmd.getColumnType(i) || Types.TIMESTAMP == rsmd.getColumnType(i)) {
                                    ColumnValue.append("timestamp'").append(value).append("',");
                                } else {
                                    ColumnValue.append(value).append(",");
                                }
                            }
                        }
                        insertSQL(insertList, ColumnName, ColumnValue, tablePub.get(j));
                    }
                } catch (Exception e) {
                    log.error("e:" + sql);
                    continue;
                } finally {
                    if (null != rs) {
                        try {
                            rs.close();
                        } catch (SQLException throwables) {
                            log.info("关闭失败");
                        }
                    }
                }


            }
        }
    }

    /**
     * 拼装insertsql放到全局list里面
     *
     * @return
     * @paramColumnName
     * @paramColumnValue
     */
    public List<String> insertSQL(List<String> insertList, StringBuffer ColumnName, StringBuffer ColumnValue, String tableName) {
        StringBuffer insertSQL = new StringBuffer();
        insertSQL.append("INSERT INTO ").append(tableName).append("(").append(ColumnName.toString()).append(")").append("VALUES").append("(").append(ColumnValue.toString()).append(");");
        insertList.add(insertSQL.toString());
        // System.out.println(insertSQL.toString());
        return insertList;
    }


    @SuppressWarnings("resource")
    public void getBusiInserSqlByBusiNo(List<String> tablePub, List<String> insertListSqlByBusiNo, List<String> dataSourList, String busiNo) throws SQLException {

        ResultSet rs = null;
        try {
            String entrNo = "";
            String saleProdCode = "";
            String prodLineCode = "";
            String relatSysNo = "";
            StringBuffer atomProdCode = new StringBuffer();
            StringBuffer compNo = new StringBuffer();
            String sql = "select * from  T_PIP_BUSI where busi_no='" + busiNo + "'";

            //业务表
            try {
                rs = DBSessionFactory.getSession().getResultSet(sql);
                if (null != rs && rs.next()) {
                    entrNo = rs.getString(2);
                    saleProdCode = rs.getString(4);
                    relatSysNo = rs.getString(12);
                } else {
                    return;
                }
            } catch (SQLException throwables) {
                log.info("查询数据异常");
            } finally {
                if (null != rs) {
                    try {
                        rs.close();
                    } catch (SQLException throwables) {
                        log.info("关闭失败");
                    }
                }
            }
            rs = null;

            insertListSqlByBusiNo.add(sql);
            tablePub.add("T_PIP_BUSI");
            dataSourList.add("local");

            insertListSqlByBusiNo.add("select * from  T_PIP_BUSI_DOC where busi_no='" + busiNo + "'");//业务文档表
            tablePub.add("T_PIP_BUSI_DOC");
            dataSourList.add("local");

            insertListSqlByBusiNo.add("select * from  T_PIP_BUSI_CHNL_OPEN where busi_no='" + busiNo + "'");//业务渠道开通表
            tablePub.add("T_PIP_BUSI_CHNL_OPEN");
            dataSourList.add("local");

            insertListSqlByBusiNo.add("select * from  T_PIP_BUSI_BRCH_OPEN where busi_no='" + busiNo + "'");//业务机构开通表
            tablePub.add("T_PIP_BUSI_BRCH_OPEN");
            dataSourList.add("local");

            insertListSqlByBusiNo.add("select * from  T_PIP_BUSI_PARA where busi_no='" + busiNo + "'");//业务表 获取key_no
            tablePub.add("T_PIP_BUSI_PARA");
            dataSourList.add("local");

            insertListSqlByBusiNo.add("select * from  T_PIP_ENTR where ENTR_NO='" + entrNo + "'");//单位表
            tablePub.add("T_PIP_ENTR");
            dataSourList.add("local");

            sql = "select * from T_PIP_SALE_PROD where sale_prod_code='" + saleProdCode + "'";//可售产品  图片,prod_line_code
            try {
                rs = DBSessionFactory.getSession().getResultSet(sql);
                if (rs != null && rs.next()) {
                    prodLineCode = rs.getString(3);
                }
            } catch (SQLException throwables) {
                log.info("查询数据异常");
            } finally {
                if (null != rs) {
                    try {
                        rs.close();
                    } catch (SQLException throwables) {
                        log.info("关闭失败");
                    }
                }
            }
            rs = null;

            insertListSqlByBusiNo.add(sql);
            tablePub.add("T_PIP_SALE_PROD");
            dataSourList.add("local");

            insertListSqlByBusiNo.add("select * from T_PIP_LINE_PROD where prod_line_code='" + prodLineCode + "'");//产品线表
            tablePub.add("T_PIP_LINE_PROD");
            dataSourList.add("local");

            sql = "select * from T_PIP_SALE_PROD_ADAPTER where sale_prod_code='" + saleProdCode + "'";//可售产品包装表
            insertListSqlByBusiNo.add(sql);
            tablePub.add("T_PIP_SALE_PROD_ADAPTER");
            dataSourList.add("local");

            sql = "select * from T_PIP_SALE_PROD_ATOM_PROD where  sale_prod_code='" + saleProdCode + "'";//可售产品原子关联表 atom_prod_code
            try {
                rs = DBSessionFactory.getSession().getResultSet(sql);
                while (rs.next()) {
                    atomProdCode.append("'").append(rs.getString(2)).append("',");//获取原子产品代码
                    compNo.append("'").append(rs.getString(4)).append("',");//获取组件号
                }
            } catch (SQLException throwables) {
                log.info("查询数据异常");
            } finally {
                if (null != rs) {
                    try {
                        rs.close();
                    } catch (SQLException throwables) {
                        log.info("关闭失败");
                    }
                }
            }
            rs = null;

            insertListSqlByBusiNo.add(sql);
            tablePub.add("T_PIP_SALE_PROD_ATOM_PROD");
            dataSourList.add("local");

            if (compNo.toString().length() > 0) {
                sql = "select * from T_PIP_COMP where  comp_no in(" + compNo.toString().substring(0, compNo.toString().length() - 1) + ")";//组件表
                insertListSqlByBusiNo.add(sql);
                tablePub.add("T_PIP_COMP");
                dataSourList.add("local");

                sql = "select * from T_PIP_SVC where  comp_no in(" + compNo.toString().substring(0, compNo.toString().length() - 1) + ")";//服务表
                insertListSqlByBusiNo.add(sql);
                tablePub.add("T_PIP_SVC");
                dataSourList.add("local");

                sql = "select * from T_PIP_SVC_COMP_SCEN where  comp_no in(" + compNo.toString().substring(0, compNo.toString().length() - 1) + ")";//服务场景表
                insertListSqlByBusiNo.add(sql);
                tablePub.add("T_PIP_SVC_COMP_SCEN");
                dataSourList.add("local");

                sql = "select * from T_PIP_COMP_PARA where  comp_no in(" + compNo.toString().substring(0, compNo.toString().length() - 1) + ")";//组件属性表
                insertListSqlByBusiNo.add(sql);
                tablePub.add("T_PIP_COMP_PARA");
                dataSourList.add("local");

                sql = "select * from T_PIP_COMP_SVC_PARA where  comp_no in(" + compNo.toString().substring(0, compNo.toString().length() - 1) + ")";//服务属性表
                insertListSqlByBusiNo.add(sql);
                tablePub.add("T_PIP_COMP_SVC_PARA");
                dataSourList.add("local");

                //属性暂定由组件和服务导出
                sql = "select c.* from T_PIP_KEY c where c.key_no in(select distinct(a.key_no) from T_PIP_KEY a, T_PIP_COMP_PARA b where  a.key_no=b.key_no and b.key_tp='02' and b.comp_no in(" + compNo.toString().substring(0, compNo.toString().length() - 1) + "))";//组件属性
                insertListSqlByBusiNo.add(sql);
                tablePub.add("T_PIP_KEY");
                dataSourList.add("local");

                sql = "select c.* from T_PIP_KEY c where c.key_no in(select distinct(a.key_no) from T_PIP_KEY a, T_PIP_COMP_SVC_PARA b where  a.key_no=b.key_no and b.comp_no in(" + compNo.toString().substring(0, compNo.toString().length() - 1) + "))";//服务属性
                insertListSqlByBusiNo.add(sql);
                tablePub.add("T_PIP_KEY");
                dataSourList.add("local");

                //属性控件
                sql = "select c.* from T_PIP_KEY_CTRL c where c.key_no in(select distinct(a.key_no) from T_PIP_KEY_CTRL a, T_PIP_COMP_PARA b where  a.key_no=b.key_no and b.key_tp='02' and b.comp_no in(" + compNo.toString().substring(0, compNo.toString().length() - 1) + "))";//组件属性
                insertListSqlByBusiNo.add(sql);
                tablePub.add("T_PIP_KEY_CTRL");
                dataSourList.add("local");

                sql = "select c.* from T_PIP_KEY_CTRL c where c.key_no in(select distinct(a.key_no) from T_PIP_KEY_CTRL a, T_PIP_COMP_SVC_PARA b where  a.key_no=b.key_no and b.comp_no in(" + compNo.toString().substring(0, compNo.toString().length() - 1) + "))";//服务属性
                insertListSqlByBusiNo.add(sql);
                tablePub.add("T_PIP_KEY_CTRL");
                dataSourList.add("local");
            }

            if (atomProdCode.toString().length() > 0) {
                sql = "select * from T_PIP_ATOM_PROD where ATOM_PROD_CODE in(" + atomProdCode.toString().substring(0, atomProdCode.toString().length() - 1) + ")";//原子产品表
                insertListSqlByBusiNo.add(sql);
                tablePub.add("T_PIP_ATOM_PROD");
                dataSourList.add("local");

                sql = "select * from T_PIP_ATOM_PROD_SVC where ATOM_PROD_CODE in(" + atomProdCode.toString().substring(0, atomProdCode.toString().length() - 1) + ")";//原子产品_服务关联表
                insertListSqlByBusiNo.add(sql);
                tablePub.add("T_PIP_ATOM_PROD_SVC");
                dataSourList.add("local");
            }


            sql = "select * from  T_PIP_ERR_PROC_PARA where busi_no='" + busiNo + "'";//差错处理表
            insertListSqlByBusiNo.add(sql);
            tablePub.add("T_PIP_ERR_PROC_PARA");
            dataSourList.add("local");

            String clrRuleNo = "";
            String chkRuleNo = "";
            String signRuleNo = "";
            String feeRuleNo = "";
            String chkRuleId = "";
            StringBuffer feeCode = new StringBuffer();
            sql = "select * from T_PIP_RULE_RELAT where  BUSI_NO='" + busiNo + "'";//规则关系表
            try {
                rs = DBSessionFactory.getSession().getResultSet(sql);
                while (null != rs && rs.next()) {
                    //101-对账动作
                    if ("101".equals(rs.getString("RULE_TP"))) {
                        chkRuleNo = rs.getString("RULE_ID");
                    }
                    //201-本金清算动作
                    if ("201".equals(rs.getString("RULE_TP"))) {
                        clrRuleNo = rs.getString("RULE_ID");
                    }
                    //202-本金+手续费清算动作
                    if ("202".equals(rs.getString("RULE_TP"))) {
                        clrRuleNo = rs.getString("RULE_ID");
                    }
                    //301-手续费清算动作
                    if ("301".equals(rs.getString("RULE_TP"))) {
                        feeRuleNo = rs.getString("RULE_ID");
                    }
                    //401-签约规则
                    if ("401".equals(rs.getString("RULE_TP"))) {
                        signRuleNo = rs.getString("RULE_ID");
                    }
                    //210-第三方清算规则
                    if ("210".equals(rs.getString("RULE_TP"))) {
                        clrRuleNo = rs.getString("RULE_ID");
                    }
                    //310-第三方手续费规则
                    if ("310".equals(rs.getString("RULE_TP"))) {
                        feeRuleNo = rs.getString("RULE_ID");
                    }
                }
            } catch (SQLException throwables) {
                log.info("查询数据异常");
            } finally {
                if (null != rs) {
                    try {
                        rs.close();
                    } catch (SQLException throwables) {
                        log.info("关闭失败");
                    }
                }
            }
            rs = null;

            insertListSqlByBusiNo.add(sql);
            tablePub.add("T_PIP_RULE_RELAT");
            dataSourList.add("local");

            sql = "select * from T_PIP_CLR_MERT_RULE where RULE_ID='" + clrRuleNo + "'";//清算规则表
            insertListSqlByBusiNo.add(sql);
            tablePub.add("T_PIP_CLR_MERT_RULE");
            dataSourList.add("local");

            sql = "select * from T_PIP_CLR_MERT_ACCT where RULE_ID='" + clrRuleNo + "'";//清算规则附属表
            insertListSqlByBusiNo.add(sql);
            tablePub.add("T_PIP_CLR_MERT_ACCT");
            dataSourList.add("local");

            sql = "select * from T_PIP_CLR_MERT_FEE_RULE where RULE_ID='" + feeRuleNo + "'";//手续费清算规则表
            insertListSqlByBusiNo.add(sql);
            tablePub.add("T_PIP_CLR_MERT_FEE_RULE");
            dataSourList.add("local");

//        	sql="select * from T_PIP_FEE_CALT where FEE_CLR_RULE_ID='"+feeRuleNo+"' and BUSI_NO='"+busiNo+"'";//手续费计算关系表
//        	insertListSqlByBusiNo.add(sql);
//        	tablePub.add("T_PIP_FEE_CALT");
//        	dataSourList.add("local");

            sql = "select * from T_PIP_CLR_MERT_FEE_CALT where RULE_ID='" + feeRuleNo + "'";//手续费计算规则表
            insertListSqlByBusiNo.add(sql);
            tablePub.add("T_PIP_CLR_MERT_FEE_CALT");
            dataSourList.add("local");

//        	rs=DBSessionFactory.getSession().getResultSet(sql);
//        	if(rs!=null){
//            	while(rs.next()){
//            		feeCode.append("'").append(rs.getString(4)).append("',");//获取组件号
//           	    }
//        	}
//        	rs=null;
//        	
//        	if(feeCode.toString().length()>0){
//        		sql="select * from T_PIP_FEE_CALT_CONF where FEE_CODE in("+feeCode.toString().substring(0, feeCode.toString().length()-1)+")";//手续费计算配置表
//            	insertListSqlByBusiNo.add(sql);
//            	tablePub.add("T_PIP_FEE_CALT_CONF");
//            	dataSourList.add("local");
//        	}

            sql = "select * from T_PIP_CHK_RULE where RULE_ID='" + chkRuleNo + "'";//对账规则表
            insertListSqlByBusiNo.add(sql);
            tablePub.add("T_PIP_CHK_RULE");
            dataSourList.add("local");
            try {
                rs = DBSessionFactory.getSession().getResultSet(sql);
                while (rs.next()) {
                    chkRuleId = rs.getString(19) == null ? "" : rs.getString(19);//对账规则id
                }
            } catch (SQLException throwables) {
                log.info("查询数据异常");
            } finally {
                if (null != rs) {
                    try {
                        rs.close();
                    } catch (SQLException throwables) {
                        log.info("关闭失败");
                    }
                }
            }
            rs = null;

            sql = "select * from T_PIP_SIGN_RULE where RULE_ID='" + signRuleNo + "'";//签约规则表
            insertListSqlByBusiNo.add(sql);
            tablePub.add("T_PIP_SIGN_RULE");
            dataSourList.add("local");

            sql = "select * from T_PIP_SIGN_RULE_LIM where RULE_ID='" + signRuleNo + "'";//签约规则限额表
            insertListSqlByBusiNo.add(sql);
            tablePub.add("T_PIP_SIGN_RULE_LIM");
            dataSourList.add("local");

            sql = "select * from T_TFSVR_SVR_PORT_PARA where comp_no in(" + compNo.toString().substring(0, compNo.toString().length() - 1) + ")";
            ;//文件传输端口
            insertListSqlByBusiNo.add(sql);
            tablePub.add("T_TFSVR_SVR_PORT_PARA");
            dataSourList.add("comp");

            if (null != relatSysNo && !"".equals(relatSysNo)) {
                sql = "select * from T_PARA_CHNL where RELAT_SYS='" + relatSysNo + "'";//渠道表
                insertListSqlByBusiNo.add(sql);
                tablePub.add("T_PARA_CHNL");
                dataSourList.add("local");

                StringBuffer seqCrtIds = new StringBuffer();
                StringBuffer fileSvrIds = new StringBuffer();
                sql = "select * from T_PARA_RELAT_SYS where RELAT_SYS='" + relatSysNo + "'";//查询关联系统表获取行内或者行外的 SEQ_CRT_ID 和FILE_SVR_ID
                insertListSqlByBusiNo.add(sql);
                tablePub.add("T_PARA_RELAT_SYS");
                dataSourList.add("local");

                try {
                    rs = DBSessionFactory.getSession().getResultSet(sql);
                    while (rs.next()) {
                        seqCrtIds.append("'").append(rs.getString(16) == null ? "" : rs.getString(16)).append("',");//获取流水生成标识号
                        fileSvrIds.append("'").append(rs.getString(17) == null ? "" : rs.getString(17)).append("',");//获取文件服务器标识号
                    }
                } catch (SQLException throwables) {
                    log.info("查询数据异常");
                } finally {
                    if (null != rs) {
                        try {
                            rs.close();
                        } catch (SQLException throwables) {
                            log.info("关闭失败");
                        }
                    }
                }
                rs = null;

                if (seqCrtIds.toString().length() > 0) {
                    //流水号中心
                    sql = "select * from T_TSEQ_SEQ_CRT where SEQ_CRT_ID in(" + seqCrtIds.toString().substring(0, seqCrtIds.toString().length() - 1) + ")";//流水号生成器表
                    insertListSqlByBusiNo.add(sql);
                    tablePub.add("T_TSEQ_SEQ_CRT");
                    dataSourList.add("comp");

                    sql = "select * from T_TSEQ_AUTH where SEQ_CRT_ID in(" + seqCrtIds.toString().substring(0, seqCrtIds.toString().length() - 1) + ")";//权限控制表
                    insertListSqlByBusiNo.add(sql);
                    tablePub.add("T_TSEQ_AUTH");
                    dataSourList.add("comp");
                }

                if (fileSvrIds.toString().length() > 0) {
                    //文件中心
                    sql = "select * from t_tfsvr_svr_para where FILE_SVR_ID in(" + fileSvrIds.toString().substring(0, fileSvrIds.toString().length() - 1) + ")";//文件服务器参数表
                    insertListSqlByBusiNo.add(sql);
                    tablePub.add("t_tfsvr_svr_para");
                    dataSourList.add("comp");
                }
            }

            //对账元件  根据对账规则抽数
            if (null != chkRuleId && !"".equals(chkRuleId)) {
                StringBuffer dataSrcCodes = new StringBuffer();

                sql = "select * from t_fchk_para ";//组件参数表  公共表
                insertListSqlByBusiNo.add(sql);
                tablePub.add("t_fchk_para");
                dataSourList.add("comp");

                sql = "select * from t_fchk_svc_code ";//服务码表  公共表
                insertListSqlByBusiNo.add(sql);
                tablePub.add("t_fchk_svc_code");
                dataSourList.add("comp");

                sql = "select * from t_fchk_busi_para where rule_id='" + chkRuleId + "'";//业务对账参数表
                insertListSqlByBusiNo.add(sql);
                tablePub.add("t_fchk_busi_para");
                dataSourList.add("comp");

                sql = "select * from t_fchk_data_src_relat where rule_id='" + chkRuleId + "'";//数据来源关系表
                insertListSqlByBusiNo.add(sql);
                tablePub.add("t_fchk_data_src_relat");
                dataSourList.add("comp");
                try {
                    rs = DBSessionFactory.getSession("comp").getResultSet(sql);
                    if (rs != null) {
                        while (rs.next()) {
                            if (null != rs.getString(6) && !"".equals(rs.getString(6))) {
                                dataSrcCodes.append("'").append(rs.getString(6)).append("',");//主方数据来源代码
                            }
                            if (null != rs.getString(8) && !"".equals(rs.getString(8))) {
                                dataSrcCodes.append("'").append(rs.getString(8)).append("',");//左方数据来源代码
                            }
                            if (null != rs.getString(10) && !"".equals(rs.getString(10))) {
                                dataSrcCodes.append("'").append(rs.getString(10)).append("',");//右方数据来源代码
                            }
                        }
                    }
                } catch (SQLException throwables) {
                    log.info("查询数据异常");
                } finally {
                    if (null != rs) {
                        try {
                            rs.close();
                        } catch (SQLException throwables) {
                            log.info("关闭失败");
                        }
                    }
                }
                rs = null;

                if (dataSrcCodes.toString().length() > 0) {
                    sql = "select * from t_fchk_file_conf where DATA_SRC_CODE in(" + dataSrcCodes.toString().substring(0, dataSrcCodes.toString().length() - 1) + ")";//对账文件解析汇总配置表
                    insertListSqlByBusiNo.add(sql);
                    tablePub.add("t_fchk_file_conf");
                    dataSourList.add("comp");

                    sql = "select * from t_fchk_file_elem_conf where DATA_SRC_CODE in(" + dataSrcCodes.toString().substring(0, dataSrcCodes.toString().length() - 1) + ")";//对账文件要素解析配置表
                    insertListSqlByBusiNo.add(sql);
                    tablePub.add("t_fchk_file_elem_conf");
                    dataSourList.add("comp");
                }

            }

            //清算元件
            if (compNo.toString().length() > 0) {
                sql = "select * from T_FCCS_CHK_SVC where  comp_no in(" + compNo.toString().substring(0, compNo.toString().length() - 1) + ")";
                ;//对账服务配置表
                insertListSqlByBusiNo.add(sql);
                tablePub.add("T_FCCS_CHK_SVC");
                dataSourList.add("comp");
            }

            //签约元件

            //调度中心

            //批量元件

        } catch (Exception e) {
            log.error("组织sql异常:" + e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException throwables) {
                    log.info("关闭失败");
                }
            }
        }

    }

    public File getSQLFile(File file, String busiNo) throws Exception {
        List<String> insertList = new ArrayList<String>();
        //busiNo="0300020005";
        executeSQL(insertList, busiNo);//根据业务编号获取导出语句
        File SQLfile = createFile(insertList, file);//创建文件
        return SQLfile;
    }

}
