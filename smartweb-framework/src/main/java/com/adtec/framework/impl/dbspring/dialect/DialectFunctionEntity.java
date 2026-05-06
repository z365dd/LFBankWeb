package com.adtec.framework.impl.dbspring.dialect;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DialectFunctionEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String functionName = null;
	
	private String format = null;
	
	private Map<String, String> dialectFuncMap = new HashMap<String, String>();

	private String expression = null;
	
	private int paramCount = 0;

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public void setDialectFunc(String dbType, String function){
		dialectFuncMap.put(dbType, function);
	}
	
	public String getDialectFunc(String dbType){
		
		if(dialectFuncMap.containsKey(dbType))
			return dialectFuncMap.get(dbType);
		
		return null;
	}

	public int getParamCount() {
		return paramCount;
	}

	public void setParamCount(int paramCount) {
		this.paramCount = paramCount;
	}

}
