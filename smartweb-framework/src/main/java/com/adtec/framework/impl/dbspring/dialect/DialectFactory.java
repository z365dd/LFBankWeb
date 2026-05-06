
package com.adtec.framework.impl.dbspring.dialect;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.adtec.framework.interfaces.db.dialect.IDialect;

/**
 * <P>DialectFactory</P>
 * <P>类的详细说明</P>
 * <P>Copyright: Copyright (c) 2015</P>
 * <P>Company: 北京先进数通信息技术股份公司</P>
 * @author  chenyl
 * @version 1.0 2016年9月20日 
 * <P>          修改者姓名 修改内容说明</P>
 * @see     参考类1
 */
public class DialectFactory {	
	private static Map<String, IDialect> dialects = new HashMap<String, IDialect>();

	private static Map<String,DialectFunctionEntity> dialectFunctions = new HashMap<String, DialectFunctionEntity>();

	private static final String DEFAULT_DIALECT_CONFIG = "/defaultDialectConfig.xml";

	private static final String DIALECT_CONFIG = "/dialectConfig.xml";

	public static final char SEPARATOR = ',';

	public static final char PLACEHOLDER = '$';

	public static final String FUNCHOLDER = "#";

	static {
		dialects.put(new Oracle9Dialect().getDialectName(),
				new Oracle9Dialect());
		dialects.put(new MySQLDialect().getDialectName(), new MySQLDialect());
		dialects.put(new SQLServerDialect().getDialectName(),
				new SQLServerDialect());
		dialects.put(new OracleDialect().getDialectName(), new OracleDialect());
		dialects.put(new DB2Dialect().getDialectName(), new DB2Dialect());
		dialects.put(new InformixDialect().getDialectName(),
				new InformixDialect());
		dialects.put(new SybaseDialect().getDialectName(), new SybaseDialect());
		dialects.put(new Kingbase8Dialect().getDialectName(), new Kingbase8Dialect());
		dialects.put(new OpenGaussDialect().getDialectName(), new OpenGaussDialect());
		buildDialectFunctions();
	}

	public static IDialect getDialect(String name) {
		return dialects.get(name.toLowerCase());
	}

	public static Map<String,DialectFunctionEntity> getDialect() {
		return dialectFunctions;
	}

	private static void processCustomDialectConfig(String configFile) {

		URL url = null;

	}
	
	private static void processDefaultDialectConfig(String defaultConfigFile) {

		URL url = null;

	}
	


	public static void buildDialectFunctions() {

		processDefaultDialectConfig(DEFAULT_DIALECT_CONFIG);
		processCustomDialectConfig(DIALECT_CONFIG);
	}


	private static String buildExpression(String funcName, String format) {
		String pb = ".*\\(\\s*(.+?)\\s*\\).*";

		Pattern p = Pattern.compile(pb);
		Pattern p2 = Pattern.compile(funcName.toLowerCase() + "_sep");
		Matcher m = p.matcher(format);

		if (m.find()) {
			StringBuilder sb = new StringBuilder();

			sb.append(funcName.toLowerCase()).append("_begin")
					.append("\\(\\s*");
			sb.append("(.+?)");

			Matcher m2 = p2.matcher(m.group(1).toLowerCase());
			while (m2.find()) {
				sb.append("\\s?");
				sb.append(funcName.toLowerCase()).append("_sep");
				sb.append("\\s?(.+?)");
			}
			sb.append("\\s*\\)");
			sb.append(funcName.toLowerCase()).append("_end");

			return sb.toString();
		}

		return format; 
	}

	private static int getParamCount(String sql) {

		int count = 0;

		String[] param = sql.split(String.valueOf(PLACEHOLDER));
		count = param.length;

		return count;
	}
}
