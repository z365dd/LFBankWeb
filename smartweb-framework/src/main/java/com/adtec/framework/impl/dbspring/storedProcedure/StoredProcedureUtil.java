
package com.adtec.framework.impl.dbspring.storedProcedure;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.adtec.framework.impl.dbspring.dataset.DataSetUtil;
import com.adtec.framework.interfaces.db.storedProcedure.ProcedureParameter;
/**
 * 功能说明: 处理存储过程。目前支持ORC,MYSQL存储过程，自定义函数及DB2存储过程<br>
 * 系统版本: v1.0<br>
 * 开发人员: chenyl<br>
 * 开发时间: 2016-9-20<br>
 * 功能描述: <br>
 */
public class StoredProcedureUtil {

	/** The Constant DEFAULT_CURSOR_TYPE. */
	//默认游标类型号
	public final static int DEFAULT_CURSOR_TYPE = -99999;
	//返回的游标结果集在map中的名称
	public final static String DEFAULT_RESULTSET_NAME = "dataSet";
	//返回的returnCode名称
	public final static String DEFAULT_RETURNCODE_NAME = "returnCode";

	/**
	 * Gets the call procedure sql.
	 * 
	 * @param procedureName
	 *            the procedure name
	 * @param num
	 *            the num
	 * @param returnType
	 *            the return type
	 * @return the call procedure sql
	 */
	public static String getCallProcedureSql(String procedureName, int num,
			Integer returnType) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		if (returnType != -1) {
			sb.append("?=");
			num--;
		}
		sb.append("call ");
		sb.append(procedureName);
		sb.append("(");
		for (int i = 0; i < num; i++)
			if (i == num - 1)
				sb.append("?");
			else
				sb.append("?,");
		sb.append(")}");
		return sb.toString();
	}

	/**
	 * Sets the call procedure in parameter by position.
	 * 
	 * @param cstmt
	 *            the cstmt
	 * @param inParameters
	 *            the in parameters
	 * @throws SQLException
	 *             the sQL exception
	 */
	public static void setCallProcedureInParameterByPosition(
			CallableStatement cstmt, Map<Integer, Object> inParameters)
			throws SQLException {
		for (Iterator it = inParameters.entrySet().iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			Integer paramPosition = (Integer) entry.getKey();
			Object paramValue = entry.getValue();
			cstmt.setObject(paramPosition, paramValue);
		}
	}

	/**
	 * Register out parameter by position.
	 * 
	 * @param cstmt
	 *            the cstmt
	 * @param outParameters
	 *            the out parameters
	 * @throws SQLException
	 *             the sQL exception
	 */
	public static void registerOutParameterByPosition(CallableStatement cstmt,
			Map<Integer, ProcedureParameter> outParameters) throws SQLException {
		for (Iterator it = outParameters.entrySet().iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			Integer paramPosition = (Integer) entry.getKey();
			int paramType = ((ProcedureParameter) entry.getValue()).getType();
			if (paramType != DEFAULT_CURSOR_TYPE)
				cstmt.registerOutParameter(paramPosition, paramType);
		}
	}

	/**
	 * Pares in parameters and out parameters.
	 * 
	 * @param start
	 *            the start
	 * @param returnType
	 *            the return type
	 * @param parameterList
	 *            the parameter list
	 * @param inParameters
	 *            the in parameters
	 * @param outParameters
	 *            the out parameters
	 * @return the integer
	 */
	public static Integer paresInParametersAndOutParameters(Integer start,
			Integer returnType, List<ProcedureParameter> parameterList,
			Map<Integer, Object> inParameters,
			Map<Integer, ProcedureParameter> outParameters) {
		if (returnType == -1)
			start = 0;
		else {
			start = 1;
			outParameters.put(start, new ProcedureParameter(
					DEFAULT_RETURNCODE_NAME, returnType, null, "out"));
		}
		for (ProcedureParameter parameter : parameterList) {
			String kind = parameter.getKind().toLowerCase();
			if (!kind.equals("return"))
				start++;
			if (kind.equals("in")) {
				inParameters.put(start, parameter.getValue());
			} else if (kind.equals("out")) {
				outParameters.put(start, parameter);
			} else if (kind.equals("inout")) {
				inParameters.put(start, parameter.getValue());
				outParameters.put(start, parameter);
			}
		}
		return start;
	}

	/**
	 * Pares return type.
	 * 
	 * @param returnType
	 *            the return type
	 * @param parameterList
	 *            the parameter list
	 * @return the integer
	 */
	public static Integer paresReturnType(Integer returnType,
			List<ProcedureParameter> parameterList) {
		for (ProcedureParameter parameter : parameterList) {
			String kind = parameter.getKind().toLowerCase();
			if (kind.equals("return")) {
				returnType = parameter.getType();
			}
		}
		return returnType;
	}

	/**
	 * Execute call procedure by position.
	 * 
	 * @param cstmt
	 *            the cstmt
	 * @param outParametersMap
	 *            the out parameters map
	 * @return the map
	 * @throws SQLException
	 *             the SQL exception
	 */
	public static Map executeCallProcedureByPosition(CallableStatement cstmt,
			Map<Integer, ProcedureParameter> outParametersMap)
			throws SQLException {
		Map resultMap = new HashMap<String, Object>();
		cstmt.execute();
		for (Iterator it = outParametersMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			Integer paramPosition = (Integer) entry.getKey();
			String paramnName = ((ProcedureParameter) entry.getValue())
					.getName();
			int paramnType = ((ProcedureParameter) entry.getValue())
					.getType();

			if (paramnType == oracle.jdbc.OracleTypes.CURSOR)
				resultMap.put(paramnName, DataSetUtil
						.getCopyDataset((ResultSet) cstmt
								.getObject(paramPosition)));
			else if (paramnType == DEFAULT_CURSOR_TYPE)
				resultMap.put(paramnName, DataSetUtil.getCopyDataset(cstmt
						.getResultSet()));
			else
				resultMap.put(paramnName, cstmt.getObject(paramPosition));

		}
		return resultMap;
	}

}
