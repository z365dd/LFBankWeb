package com.adtec.prod.oper.service;

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
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImportOrExportCompParaService {
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
    public  void executeSQL(List<String> insertList,String compNo) throws SQLException {
    	List<String> listSQL = new ArrayList<String>();
    	List<String> tablePub = new ArrayList<String>();
    	List<String> dataSourList = new ArrayList<String>();
    	
    	getBusiInserSqlByCompNo(tablePub,listSQL,dataSourList,compNo);
    	
    	getColumnNameAndColumeValue(insertList,listSQL,dataSourList,tablePub);
    }
    /**
     * 创建insertsql.txt并导出数据
     */
    public  File createFile(List<String> insertList,File file) {
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
                System.out.println("出现异常");
            } finally {
            if (null != bw) {
                try {
                    bw.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
            if (null != fw) {
                try {
                    fw.close();
                } catch (IOException e) {
                System.out.println("出现异常");
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
    public  void getColumnNameAndColumeValue(List<String> insertList, List<String> listSQL,List<String> dataSourList,List<String> tablePub) throws SQLException {
        if (listSQL.size() > 0) {
            for (int j = 0; j < listSQL.size(); j++) {
                String sql = String.valueOf(listSQL.get(j));
                System.out.println(sql);
                ResultSet rs = null;
                try{
                	if(("local").equals(dataSourList.get(j))){
                		rs =DBSessionFactory.getSession().getResultSet(sql);
                	}else{
                		rs =DBSessionFactory.getSession(dataSourList.get(j)).getResultSet(sql);
                	}
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int columnCount = rsmd.getColumnCount();
                    while (rs.next()) {
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
                                }else{
                                    if (Types.CHAR == rsmd.getColumnType(i) || Types.VARCHAR == rsmd.getColumnType(i) || Types.LONGVARCHAR == rsmd.getColumnType(i)) {
                                        ColumnValue.append("'").append(value).append("'");
                                    } else if (Types.SMALLINT == rsmd.getColumnType(i) || Types.INTEGER == rsmd.getColumnType(i) || Types.BIGINT == rsmd.getColumnType(i) || Types.FLOAT == rsmd.getColumnType(i) || Types.DOUBLE == rsmd.getColumnType(i) || Types.NUMERIC == rsmd.getColumnType(i) || Types.DECIMAL == rsmd.getColumnType(i)|| Types.TINYINT == rsmd.getColumnType(i)) {
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
                                } else if (Types.SMALLINT == rsmd.getColumnType(i) || Types.INTEGER == rsmd.getColumnType(i) || Types.BIGINT == rsmd.getColumnType(i) || Types.FLOAT == rsmd.getColumnType(i) || Types.DOUBLE == rsmd.getColumnType(i) || Types.NUMERIC == rsmd.getColumnType(i) || Types.DECIMAL == rsmd.getColumnType(i)|| Types.TINYINT == rsmd.getColumnType(i)) {
                                    ColumnValue.append(value).append(",");
                                } else if (Types.DATE == rsmd.getColumnType(i) || Types.TIME == rsmd.getColumnType(i) || Types.TIMESTAMP == rsmd.getColumnType(i)) {
                                    ColumnValue.append("timestamp'").append(value).append("',");
                                } else {
                                    ColumnValue.append(value).append(",");
                                }
                            }
                        }
                        System.out.println(ColumnName.toString());
                        System.out.println(ColumnValue.toString());
                        insertSQL(insertList,ColumnName, ColumnValue,tablePub.get(j));
                    }
                }catch(Exception e){
                	log.error("e:"+sql);
                	continue;
                } finally {
                    if(null != rs){
                        try {
                            rs.close();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }

                }

            }
        }
    }

    /**
     * 拼装insertsql放到全局list里面
     * @return 
     * @paramColumnName
     * @paramColumnValue
     */
    public List<String> insertSQL(List<String> insertList,StringBuffer ColumnName, StringBuffer ColumnValue,String tableName) {
        StringBuffer insertSQL = new StringBuffer();
        insertSQL.append("INSERT INTO ").append(tableName).append("(").append(ColumnName.toString()).append(")").append("VALUES").append("(").append(ColumnValue.toString()).append(");");
        insertList.add(insertSQL.toString());
        System.out.println(insertSQL.toString());
        return insertList;
    }

   
    public  void getBusiInserSqlByCompNo(List<String> tablePub,List<String> insertListSqlByBusiNo,List<String> dataSourList,String compNo) throws SQLException{	//替换
    	
		String sql="select * from T_PIP_COMP where  comp_no ="+compNo;//组件表
    	insertListSqlByBusiNo.add(sql);
    	tablePub.add("T_PIP_COMP");
    	dataSourList.add("local");
    	
    	sql="select * from T_PIP_SVC where  comp_no ="+compNo;//服务表
    	insertListSqlByBusiNo.add(sql);
    	tablePub.add("T_PIP_SVC");
    	dataSourList.add("local");
    	
    	sql="select * from T_PIP_SVC_COMP_SCEN where  comp_no ="+compNo;//服务场景表
    	insertListSqlByBusiNo.add(sql);
    	tablePub.add("T_PIP_SVC_COMP_SCEN");
    	dataSourList.add("local");
    	
    	sql="select * from T_PIP_COMP_PARA where  comp_no ="+compNo;//组件属性表
    	insertListSqlByBusiNo.add(sql);
    	tablePub.add("T_PIP_COMP_PARA");
    	dataSourList.add("local");
    	
    	sql="select * from T_PIP_COMP_SVC_PARA where  comp_no ="+compNo;//服务属性表
    	insertListSqlByBusiNo.add(sql);
    	tablePub.add("T_PIP_COMP_SVC_PARA");
    	dataSourList.add("local");
    	
		//属性暂定由组件和服务导出
		sql="select c.* from T_PIP_KEY c where c.key_no in(select distinct(a.key_no) from T_PIP_KEY a, T_PIP_COMP_PARA b where  a.key_no=b.key_no and b.key_tp='02' and b.comp_no ="+compNo+")";//组件属性
    	insertListSqlByBusiNo.add(sql);
    	tablePub.add("T_PIP_KEY");
    	dataSourList.add("local");
    	
    	sql="select c.* from T_PIP_KEY c where c.key_no in(select distinct(a.key_no) from T_PIP_KEY a, T_PIP_COMP_SVC_PARA b where  a.key_no=b.key_no and b.comp_no ="+compNo+")";//服务属性
    	insertListSqlByBusiNo.add(sql);
    	tablePub.add("T_PIP_KEY");
    	dataSourList.add("local");
		
    	//属性控件 
    	sql="select c.* from T_PIP_KEY_CTRL c where c.key_no in(select distinct(a.key_no) from T_PIP_KEY_CTRL a, T_PIP_COMP_PARA b where  a.key_no=b.key_no and b.key_tp='02' and b.comp_no ="+compNo+")";//组件属性
    	insertListSqlByBusiNo.add(sql);
    	tablePub.add("T_PIP_KEY_CTRL");
    	dataSourList.add("local");
    	
    	sql="select c.* from T_PIP_KEY_CTRL c where c.key_no in(select distinct(a.key_no) from T_PIP_KEY_CTRL a, T_PIP_COMP_SVC_PARA b where  a.key_no=b.key_no and b.comp_no ="+compNo+")";//服务属性
    	insertListSqlByBusiNo.add(sql);
    	tablePub.add("T_PIP_KEY_CTRL");
    	dataSourList.add("local");
    	
    }
    

    public File getSQLFile(File file,String compNo) throws Exception{
    	List<String> insertList = new ArrayList<String>();
        executeSQL(insertList, compNo);//根据组件编号获取导出语句
        File SQLfile =  createFile(insertList,file);//创建文件
    	return SQLfile;
    }
    
	/**
     * 连接数据库创建statement对象
     * *@paramdriver
     * *@paramurl
     * *@paramUserName
     * *@paramPassword
     * @throws Exception
     */
    public  Connection connectSQL(String driver, String url, String UserName, String Password) throws Exception {
    	Connection conn=null;
        try {
            Class.forName(driver);
            conn=DriverManager.getConnection(url, UserName, Password);
            System.out.println("数据库连接成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("数据库连接失败");
            throw e;
        }
        return conn;
    }
    
    
    }
