/*
 * 系统名称: 
 * 模块名称: 
 * 文件名称: ProcedureParameter.java
 * 软件版权: 
 * 修改记录:
 * 修改日期      修改人员                     修改说明
 * ========    =======  ============================================

 * ========    =======  ============================================
*/
package com.adtec.framework.interfaces.db.storedProcedure;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能说明: 该类用于保存调用存储过程的参数说明，包括输入参数，输出参数，返回return code的说明<br>
 * 系统版本: v1.0<br>
 * 开发人员:  chenyl<br>
 * 开发时间: <br>
 * 功能描述: <br>
 */

public class ProcedureParameter {
	public static final int CURSOR=-10;//暂时只支持oracle游标类型
	//JAVA数据类型与JDBC数据类型对应表
	/** The type map. */
	private Map<String, Integer> typeMap = new HashMap<String, Integer>() {
		{
			put("string", java.sql.Types.VARCHAR);
			put("digdecimal", java.sql.Types.NUMERIC);
			put("boolean", java.sql.Types.BIT);
			put("byte", java.sql.Types.TINYINT);
			put("short", java.sql.Types.SMALLINT);
			put("int", java.sql.Types.INTEGER);
			put("long", java.sql.Types.BIGINT);
			put("float", java.sql.Types.REAL);
			put("double", java.sql.Types.DOUBLE);
			put("byte[]", java.sql.Types.VARBINARY);
			put("date",java.sql.Types.DATE);   
			put("time",java.sql.Types.TIME);  
			put("timestamp",java.sql.Types.TIMESTAMP);  
			put("cursor",CURSOR);// 增加游标类型映射
			put("other",java.sql.Types.OTHER);  
			put("longVarchar",java.sql.Types.LONGVARCHAR);  
			put("CLOB",java.sql.Types.CLOB);
			put("BLOB",java.sql.Types.BLOB);
		}
	};
	
	/** 目前存储过程参数主要有四类，各类参数的必填属性如下： IN参数：value,kind OUT参数：name,type,kind INOUT参数：<br>
	 * name,type,value,kind RETURN参数：type,kind  1）允许为空的参数，约定统一填null 2）对于返回的return值,只允许有一个，<br>
	 * 并且约定返回名称为returnCode 3) 对于返回游标的存储过程，目前只支持返回一个游标，且游标输出参数的位置必须定义在参数列表的最后，<br>
	 * 游标返回约定名称为dataSet. */
	
	
	//参数名称
	private String name;
	//参数数据类型，支持以java类型的字符串表示来构造
	/** The type. */
	private Integer type;
	//参数值
	/** The value. */
	private Object value;
	//参数类型，目前有IN OUT INOUT RETURN四种类型
	/** The kind. */
	private String kind;
	
	/**
	 * Instantiates a new procedure parameter.
	 * 
	 * @param name the name
	 * @param type the type
	 * @param value the value
	 * @param kind the kind
	 */
	public ProcedureParameter(String name, String type, Object value,
			String kind) {
		this.type = typeMap.get(type.toLowerCase());
		if (this.type==null)  this.type=java.sql.Types.OTHER;
		this.name = name;
		this.value = value;
		this.kind = kind;
	}

	/**
	 * Instantiates a new procedure parameter.
	 * 
	 * @param name the name
	 * @param type the type
	 * @param value the value
	 * @param kind the kind
	 */
	public ProcedureParameter(String name, Integer type, Object value,
			String kind) {
		this.type = type;
		this.name = name;
		this.value = value;
		this.kind = kind;
	}
	
	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * Gets the value.
	 * 
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Gets the kind.
	 * 
	 * @return the kind
	 */
	public String getKind() {
		return kind;
	}

}
