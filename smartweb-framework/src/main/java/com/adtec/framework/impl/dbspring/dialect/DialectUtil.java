package com.adtec.framework.impl.dbspring.dialect;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.adtec.framework.impl.dbspring.session.DBSessionFactory4Spring;

public class DialectUtil implements Serializable {

	private static final long serialVersionUID = -109062182857957461L;

	/* 数据库类型 */
	public static final String DB_TYPE_ORACLE = "oracle";
	public static final String DB_TYPE_DB2 = "db2";
	public static final String DB_TYPE_MYSQL = "mysql";
	public static final String DB_TYPE_SQLSERVER = "sqlserver";
	public static final String DB_TYPE_INFORMIX = "informix";
	public static final String DB_TYPE_SYBASE = "sybase";
	public static final String DB_TYPE_KINGBASE8 = "kingbase8";
	public static final String DB_TYPE_OPENGAUSS = "opengauss";

	/* 数据库方言函数 */
	public static final String FUN_HS_TRIM = "hs_trim";
	public static final String FUN_HS_CAST = "hs_cast";
	public static final String FUN_HS_DATE = "hs_date";
	public static final String FUN_HS_DATETIME = "hs_timestamp";
	public static final String FUN_HS_CONCAT = "hs_concat";
	public static final String FUN_HS_NVL = "hs_nvl";
	public static final String FUN_HS_LEN = "hs_len";
	public static final String FUN_HS_SUBSTR = "hs_substr";
	
	private static final String TMP_CONSTANT = "#_TMP_CONSTANT";

	private static Map<String, Integer> dataTypeMap = new HashMap<String, Integer>();

	private static Map<String, DatabaseMetaData> dbmMap = new HashMap<String, DatabaseMetaData>();

	public static void clean() {
		dataTypeMap.clear();
		dbmMap.clear();
		dataTypeMap = null;
		dbmMap = null;
	}

	/**
	 * 生成克隆sql.
	 * 
	 * @param sql
	 *            the sql
	 * @return the clone sql
	 * @return
	 */
	public static String getCloneSql(String sql) {
		byte[] tempBytes = new byte[sql.getBytes().length];
		System
				.arraycopy(sql.getBytes(), 0, tempBytes, 0,
						sql.getBytes().length);
		String tempSql = new String(tempBytes);
		return tempSql;
	}

	/**
	 * 对insert 语句和 update语句取表名
	 * 
	 * @param sql
	 * @return
	 */
	private static String getTabName(String sql) {
		String tempSql = getCloneSql(sql).toLowerCase();
		int index = -1;

		index = tempSql.indexOf("insert into");

		if (index != -1) {
			return sql.substring(index + 12, tempSql.indexOf("("));
		}
		index = -1;
		index = tempSql.indexOf("update ");

		if (index != -1) {
			String tstr = sql.substring(7).trim();
			return tstr.substring(0, tstr.indexOf(" "));
		}

		return null;
	}

	/**
	 * 对insert 语句和 update语句取插入字段
	 * 
	 * @param sql
	 * @return
	 */

	private static List<String> getColumnName(String sql) {
		String tempSql = getCloneSql(sql).toLowerCase();
		List<String> coluList = new ArrayList<String>();

		int index = -1;

		index = tempSql.indexOf("insert into");

		if (index != -1) {
			String tstr = tempSql.substring(tempSql.indexOf("(") + 1, tempSql
					.indexOf(")"));

			if (tstr.length() > 0) {
				String[] clos = tstr.split(",");
				for (String column : clos) {
					coluList.add(column.trim());
				}
			}

			return coluList;
		}

		index = -1;
		index = tempSql.indexOf("update ");

		if (index != -1) {
			int set = -1;
			set = tempSql.indexOf(" set ");

			int where = -1;
			where = tempSql.indexOf(" where ");

			String tstr = null;
			if (where != -1)
				tstr = tempSql.substring(set + 5, where).trim();
			else
				tstr = tempSql.substring(set + 5).trim();

			if (tstr.length() > 0) {
				String[] cols = tstr.split(",");
				for (String col : cols) {
					if (col.indexOf("=") != -1 && col.indexOf("?") != -1)
						coluList.add(col.substring(0, col.indexOf("=")));
				}
			}
		}

		return coluList;
	}

	/**
	 * 查询数据表字段类型
	 * 
	 * @param conn
	 * @param schema
	 * @param tabName
	 * @param colName
	 * @return
	 * @throws SQLException
	 */
	private static Integer getColumnType(Connection conn, String schema,
			String tabName, String colName, String dataSourceName)
			throws SQLException {

		java.sql.ResultSet set = null;
		try {

			set = getDBMetaData(conn, dataSourceName).getColumns(null, schema,
					tabName.trim(), colName.trim());

			if (set.next()) {
				return new Integer(set.getInt("DATA_TYPE"));
			}
		} catch (SQLException e) {
			throw new SQLException("取表 " + tabName + " 字段  " + colName
					+ " 数据类型失败 :"+e.getMessage());
		} finally {
			if(null != set){
				try {
					set.close();
				} catch (SQLException throwables) {
					System.out.println("sql异常");
				}
			}

		}

		return null;
	}

	private static java.sql.DatabaseMetaData getDBMetaData(Connection conn,
			String dataSourceName) throws SQLException {

		String key = dataSourceName.toLowerCase();
		if (dbmMap.containsKey(key)) {
			if (dbmMap.get(key).getConnection().isClosed())
				dbmMap.remove(key);
			else
				return dbmMap.get(key);
		}

		DatabaseMetaData dbm = conn.getMetaData();
		dbmMap.put(key, dbm);
		return dbm;
	}

	/**
	 * 分析sql语句，如果是做插入或更新操作则查询被操作字段的数据类型，以列表返回
	 * 
	 * @param conn
	 * @param sql
	 * @param dataSourceName
	 * @return
	 * @throws SQLException
	 */
	public static List<Integer> getDataType(Connection conn, String sql,
			String dataSourceName) throws SQLException {

		List<Integer> dataTypes = new ArrayList<Integer>();

		if (sql.trim().toLowerCase().indexOf("select") == 0) {
			return dataTypes;
		}

		String tabName = getTabName(sql.trim());
		List<String> colums = getColumnName(sql.trim());

		if (tabName != null && colums.size() > 0) {

			for (String col : colums) {
				StringBuilder key = new StringBuilder();
				key.append(dataSourceName.toLowerCase()).append(".").append(
						tabName.toLowerCase()).append(".").append(
						col.toLowerCase());
				if (dataTypeMap.containsKey(key.toString())) {
					dataTypes.add(dataTypeMap.get(key));
					continue;
				}
				DBSessionFactory4Spring.getLog().debug(
						"Get data type for column:" + col.trim()
								+ " from table " + tabName);
				Integer type = getColumnType(conn, null, tabName, col,
						dataSourceName);

				if (type == null)
					type = getColumnType(conn, null, tabName.toUpperCase(), col
							.toUpperCase(), dataSourceName);
				if (type == null)
					type = getColumnType(conn, null, tabName.toLowerCase(), col
							.toLowerCase(), dataSourceName);

				dataTypeMap.put(key.toString(), type);
				dataTypes.add(type);
			}
		}

		return dataTypes;
	}

	/*
	 * public static String transTrim(String sql){
	 * 
	 * StringBuilder sb = new StringBuilder(); String temsql =
	 * DialectUtil.getCloneSql(sql).toLowerCase();
	 * 
	 * // 转换TRIM函数为ltrim(rtrim()) if (temsql.indexOf(" trim(") != -1) { int
	 * start = temsql.indexOf(" trim(");
	 * 
	 * sb.append(sql.substring(0, start)); String tsql = sql.substring(start +
	 * 6); int end = tsql.indexOf(")"); if (end > 0) { String column =
	 * tsql.substring(0, end - 1);
	 * sb.append(" ltrim( rtrim(").append(transTrim(column)).append(")) ");
	 * sb.append(transTrim(tsql.substring(end + 1))); } sql = sb.toString(); }
	 * temsql = null; return sql; }
	 */

	public static String transCastFunction(String sql) {

		return transFunction(sql, FUN_HS_CAST, "cast(", ") ", " as ");
	}

	public static String transConcatFunction(String sql, String join) {
		return transFunction(sql, FUN_HS_CONCAT, "(", ") ", join);
	}

	public static String transFunction(String sql, String srcName,
			String targetBegin, String targetEnd, String join) {

		StringBuilder sb = new StringBuilder();
		String temsql = DialectUtil.getCloneSql(sql).toLowerCase();

		if (temsql.indexOf(srcName) != -1) {
			int start = temsql.indexOf(srcName);

			sb.append(sql.substring(0, start));
			String tsql = sql.substring(start + srcName.length());
			int end = getEndPosition(tsql, '(', ')');
			if (end > 0) {
				String column = tsql.substring(tsql.indexOf("(") + 1, end);
				if (column.indexOf(",") != -1) {
					String[] cArray = column.split(",");
					StringBuilder tsb = new StringBuilder();
					for (int i = 0; i < cArray.length; i++) {
						if (i > 0)
							tsb.append(join);
						tsb.append(cArray[i].trim());
					}
					column = tsb.toString();
				}
				sb.append(targetBegin).append(
						transFunction(column, srcName, targetBegin, targetEnd,
								join)).append(targetEnd);
				sb.append(transFunction(tsql.substring(end + 1), srcName,
						targetBegin, targetEnd, join));
			}
			sql = sb.toString();
		}
		temsql = null;
		return sql;
	}

	public static String transFunctionName(String sql, String srcName,
			String targetBegin, String targetEnd) {

		StringBuilder sb = new StringBuilder();
		String temsql = DialectUtil.getCloneSql(sql).toLowerCase();

		if (temsql.indexOf(srcName) != -1) {
			int start = temsql.indexOf(srcName);

			sb.append(sql.substring(0, start));
			String tsql = sql.substring(start + srcName.length());
			// int end = tsql.indexOf(srcEnd);
			int end = getEndPosition(tsql, '(', ')');
			if (end > 0) {
				String column = tsql.substring(tsql.indexOf("(") + 1, end);
				sb.append(targetBegin).append(
						transFunctionName(column, srcName, targetBegin,
								targetEnd)).append(targetEnd);
				sb.append(transFunctionName(tsql.substring(end + 1), srcName,
						targetBegin, targetEnd));
			}
			sql = sb.toString();
		}
		temsql = null;
		return sql;
	}

	public static int getEndPosition(String sql, char begin, char end) {
		int index = 0;
		boolean beginning = false;
		char[] sqlCharArray = sql.toCharArray();
		for (int i = 0; index < sqlCharArray.length; i++) {
			if (begin == sqlCharArray[i]) {
				index++;
				beginning = true;
				continue;
			}
			if (end == sqlCharArray[i])
				index--;

			if (beginning && index == 0)
				return i;
		}

		return 0;
	}

	public static String replaceSchema(String sql, String sche) {
		StringBuilder _param = new StringBuilder();
		if (sql != null && sql.trim().length() > 0) {

			String[] paramArray = sql.split(",");
			for (String param : paramArray) {
				int i = param.indexOf(".");
				if (sche != null && sche.trim().length() > 0) {
					_param.append(sche).append(".");
				}
				if (i > 0) {
					_param.append(param.substring(i + 1));
				} else {
					_param.append(param.trim());
				}

				_param.append(",");
				i = -1;
			}

			if (_param.length() > 1)
				return _param.substring(0, _param.length() - 1);
		}
		return _param.toString();
	}

	public static boolean hasDistinct(String sql) {
		return sql.indexOf("select distinct") >= 0;
	}

	/**
	 * 如果分页查询的sql有排序条件，需要将排序字段也作为查询的输出字段，以便于在sql的OVER函数中调用
	 * 
	 * @param sql
	 * @return
	 */
	public static String addOrderByToField(String sql) {
		String tempSql = getCloneSql(sql).toLowerCase();
		if (tempSql.indexOf("union") != -1)
			return sql;

		int orberBy = tempSql.lastIndexOf("order by");
		if (orberBy > 0) {
			int select = tempSql.indexOf("select");
			int from = tempSql.indexOf("from");

			String fields = sql.substring(select + 6, from - 1);

			if (fields.trim().equals(""))
				return sql;
			String fieldArray[] = fields.split(",");
			for (String field : fieldArray) {
				if (field.trim().equals("*"))
					return sql;
			}

			StringBuilder fileStr = new StringBuilder(fields);

			String param = sql.substring(orberBy + 9);
			int desc = param.toLowerCase().indexOf("desc");
			int asc = param.toLowerCase().indexOf("asc");
			if (desc > 0)
				param = param.substring(0, desc - 1);
			if (asc > 0)
				param = param.substring(0, asc - 1);

			if (param != null && param.trim().length() > 0) {
				String[] paraArray = param.split(",");
				for (String para : paraArray) {

					int schemaIdx = para.indexOf(".");
					if (schemaIdx > 0) {
						String schema = para.substring(0, schemaIdx);
						if (fields.indexOf(schema + ".*") != -1)
							continue;
					}

					if (fields.indexOf(para.trim()) == -1)
						fileStr.append(", ").append(para);
				}
			}

			if (fileStr.length() > fields.length()) {
				StringBuilder sqlBuilder = new StringBuilder();
				sqlBuilder.append(sql.substring(0, select)).append("select ")
						.append(fileStr.toString()).append(" ").append(
								sql.substring(from));
				return sqlBuilder.toString();
			}
		}

		return sql;
	}

	/*
	 * public static String buildSqlFuction(String sql, String dbType) { String
	 * tempSql = getCloneSql(sql).toLowerCase(); List<DialectFunctionEntity>
	 * dialectList = DialectFactory.getDialect(); Pattern p = null; Matcher m =
	 * null;
	 * 
	 * for (DialectFunctionEntity dfe : dialectList) { String expression =
	 * dfe.getExpression(); String dialectFunction = dfe.getDialectFunc(dbType);
	 * String functionName = dfe.getFunctionName();
	 * 
	 * if (tempSql.indexOf(functionName.toLowerCase()) != -1) { p =
	 * Pattern.compile(expression); m = p.matcher(sql); StringBuffer sb = new
	 * StringBuffer(); boolean flg = false; while (m.find()) {
	 * if(dfe.getFormat().indexOf("...")!=-1 && m.groupCount()==1){ String
	 * content = m.group(1); sb.append(sql.substring(0, m.start()));
	 * sb.append(dialectFunction.substring(0, dialectFunction.indexOf("(")+1));
	 * String separator =
	 * dialectFunction.substring(dialectFunction.indexOf("$1")+3,
	 * dialectFunction.indexOf(")"));
	 * sb.append(content.replaceAll(String.valueOf(DialectFactory.SEPARATOR),
	 * separator));
	 * sb.append(dialectFunction.substring(dialectFunction.indexOf(")")));
	 * sb.append(sql.substring(m.end())); sql = sb.toString(); continue; }else{
	 * m.appendReplacement(sb, dialectFunction); flg = true; } }
	 * 
	 * if(flg){ m.appendTail(sb); sql = sb.toString(); } m = null; p = null; } }
	 * return sql; }
	 */

	public static String buildSqlFuction(String sql, String dbType) {

		if (sql.indexOf(DialectFactory.FUNCHOLDER) != -1) {
			long time1 = System.currentTimeMillis();
			
			List<String> constant = new ArrayList<String>();
			sql = beforeTransSqlFunc(sql, constant);
			
			Map<String,DialectFunctionEntity> dialectMap = DialectFactory
					.getDialect();
			for (DialectFunctionEntity dfe : dialectMap.values()) {
				// String expression = dfe.getExpression();
				String functionName = dfe.getFunctionName();
				String format = dfe.getFormat();
				String dialectFunction = dfe.getDialectFunc(dbType);
                
				if (sql.toLowerCase().indexOf(functionName.toLowerCase()) != -1) {
					sql = transSqlFunc(sql.toLowerCase(), functionName.toLowerCase(), format,
							dialectFunction);
				}
			}
			sql = afterTransSqlFunc(sql.toLowerCase(), constant);
			constant.clear();
			constant = null;
			
			long time2 = System.currentTimeMillis();
			DBSessionFactory4Spring.getLog().info("解析SQL方言函数耗时" + (time2-time1) + " 毫秒; sql=" + sql);
		}
		return sql;
	}

	/**
	 * 按规则组合序列名称。序列命名要符合"seq_" + tableName的规范
	 * 
	 * @param param
	 * @return
	 */
	public static String getSeqName(String param) {
		return "seq_" + param;
	}

	private static int analyzeFunc(String srcSql, List<String> paraList) {
		int index = 0;
		int count = 0;
		boolean beginning = false;

		StringBuilder sb = new StringBuilder();
		char[] sqlCharArray = srcSql.toCharArray();		
		for (int i = 0; index < sqlCharArray.length; i++) {

			if (sqlCharArray[i] == '(') {
				count++;
				beginning = true;

				if (count == 1) {
					continue;
				}
			} else if (!beginning) {
				continue;
			}

			if (beginning && ')' == sqlCharArray[i]) {
				count--;

				if (beginning && count == 0) {
					paraList.add(sb.toString().trim());
					index = i;
					break;
				}
			}

			if (beginning) {
				if (sqlCharArray[i] == DialectFactory.SEPARATOR && count == 1) {
					paraList.add(sb.toString().trim());
					sb = new StringBuilder();
				} else
					sb.append(sqlCharArray[i]);
			}

		}
		return ++index;
	}

	private static Map<String, Object> builderFunction(String sql, String funcName,
			String format, String dialectFunction) {

		List<String> paraList = new ArrayList<String>();
		Map<String, Object> funMap = new HashMap<String, Object>();
		int index = analyzeFunc(sql, paraList);

		for (int i = 1; i <= paraList.size(); i++) {
			String space = "\\" + String.valueOf(DialectFactory.PLACEHOLDER)
					+ i;
			String tmpsql = transSqlFunc(paraList.get(i - 1), funcName, format, dialectFunction);
			dialectFunction = dialectFunction.replaceAll(space, tmpsql);
		}
		funMap.put("LEN", index);
		funMap.put("SQL", dialectFunction);
		return funMap;
	}

	private static Map<String, Object> builderMultiParamFunc(String sql, String funcName,
			String format, String dialectFunction) {
		List<String> paraList = new ArrayList<String>();
		int index = analyzeFunc(sql, paraList);
		Map<String, Object> funMap = new HashMap<String, Object>();

		funMap.put("LEN", index);
		String separator = dialectFunction.substring(dialectFunction.indexOf("$1") + 3, dialectFunction
				.indexOf(")"));
		StringBuilder sb = new StringBuilder();
		sb.append(dialectFunction.substring(0, dialectFunction.indexOf("(") + 1));
		for (int i = 0; i < paraList.size(); i++) {
			String tmpsql = transSqlFunc(paraList.get(i), funcName, format, dialectFunction);
			sb.append(tmpsql);
			if (i < paraList.size() - 1)
				sb.append(separator);
		}
		sb.append(dialectFunction.substring(dialectFunction.indexOf(")")));

		funMap.put("SQL", sb.toString());

		return funMap;
	}

	private static String transSqlFunc(String sql, String funcName,
			String format, String dialectFunction) {
		
		if(sql.indexOf(funcName)==-1)
			return sql;
		
		Pattern p = null;
		Matcher m = null;
		//StringBuilder sbp = new StringBuilder("([\'][^\']*").append(funcName).append("[^\']*[\'])|(").append(funcName).append(")");
		p = Pattern.compile(funcName);
		m = p.matcher(sql);
		StringBuilder sb = new StringBuilder();
		int index = 0;
		
		while (m.find()) {
			/*if(sql.charAt(m.start()) == '\'')
				continue;*/
			if(m.start() < index)
				continue;
			
			sb.append(sql.substring(index, m.start()));			
			String tempsql = sql.substring(m.end());

			Map<String, Object> funMap;
			if (format.indexOf("...") != -1)
				funMap = builderMultiParamFunc(tempsql, funcName, format, dialectFunction);
			else
				funMap = builderFunction(tempsql, funcName, format, dialectFunction);
			sb.append(funMap.get("SQL"));
			index = m.end() + ((Integer) funMap.get("LEN")).intValue();
			funMap.clear();
		}
		if (index < sql.length())
			sb.append(sql.substring(index));
		return sb.toString();
	}
	
	/**
	 * 将sql语句中单引号注释的常量提取出来并替换成临时变量
	 * @param sql
	 * @param constats
	 * @return
	 */
	private static String beforeTransSqlFunc(String sql, List<String> constats){
		String sqlPattern = "([\'][^\']*[\'])";
		
		if(sql.indexOf("'")!=-1){
			Pattern p = Pattern.compile(sqlPattern);
			Matcher m = p.matcher(sql);
			StringBuffer sb = new StringBuffer();
			//int i = 0;
			
			while(m.find()){
				constats.add(process$(m.group()));//20110819 修改引号中带有$引起的bug，需要对$进行特殊处理
				m.appendReplacement(sb, TMP_CONSTANT);
			}
			
			m.appendTail(sb);
			sql = sb.toString();
		}
		
		return sql;
	}
	
	/**
	 * 处理引号中的$
	 * @param group
	 * @return
	 */
	private static String process$(String group) {
      
		String[] arrays=group.split("\\$");
		StringBuffer sb=new StringBuffer();
		for (int i = 0; i < arrays.length; i++) {
			 sb.append(arrays[i]);
			if(i!=arrays.length-1){
			  sb.append("\\$");
		    }	
		}
        return sb.toString();
	}
	/**
	 * 还原sql语句中被替换成临时变量的字符串
	 * @param sql
	 * @param constats
	 * @return
	 */
	private static String afterTransSqlFunc(String sql, List<String> constats){
		if(sql.indexOf(TMP_CONSTANT.toLowerCase())==-1 || constats.size()==0)
			return sql;
		
		Pattern p = Pattern.compile(TMP_CONSTANT.toLowerCase());
		Matcher m = p.matcher(sql);
		StringBuffer sb = new StringBuffer();
		int i = 0;
		while(m.find()){
			if(constats.size()>i && constats.get(i)!=null)
				m.appendReplacement(sb, constats.get(i++));
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
}
