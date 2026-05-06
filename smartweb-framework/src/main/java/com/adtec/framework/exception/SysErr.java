/**
 * 系统名称: SmartWeb平台
 * 模块名称: 异常模块
 * 功能描述: 错误码常量
 * 类 名 称  : SysErr.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2017年4月18日 上午11:21:18<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.framework.exception;

/**
 * @author chenyl
 *
 */
public class SysErr {
	/**
	 * 平台内置错误码
	 * 1、固定0开头
	 * 2、外部定义错误码信息在ErrorMessage.properties文件中，请使用以非0开头，并通过在此或客户自定义类添加对应错误码常量
	 * */
	/**成功*/
	public static final String E_SUCCESS                = "0000";
	/**部分成功*/
	public static final String E_PARTSUCC               = "0001";
	/**全部失败*/
	public static final String E_ALLFAIL                = "0002";
	/**字段[%s]输入为空*/
	public static final String E_IN_NULL                = "0003";
	/**[%s]数据不存在*/
	public static final String E_DATA_NOEXSIT               = "0004";	
	/**结果集转换为树型结构异常 */
	public static final String E_TABLE_TO_TREE 		= "0005";
	/**json转换失败异常*/
	public static final String  E_JSON_RESULT 		= "0006";
	/**空指针异常*/
	public static final String  E_NULL_POINTER 		= "0007";
	/**不支持的错误*/
	public static final String  E_NOT_SUPPORTED 		= "0008";
	/*JSON服务转换异常码*/
	/**
	 * io读取错误异常
	 */
	public static final String E_IO_ERROR = "0009";
	
	/**
	 * 没有设置TemplateLoader异常
	 */
	public static final String E_CAN_NOT_SET_TEMPLATELOADER = "0010";
	
	/**
	 * 公共窗体name属性为空异常
	 */
	public static final String  E_PWINDOW_PROPERTY_NAME = "0011";
	
	/**通用系统错误[%s]*/
	public static final String E_DEFAULT                = "2000";
	/** %s */
	public static final String E_NO_MESSAGE                = "2999";
	/** 未知错误 */
	public static final String E_UNKNOWN            	  = "9999";
	
	public static final String EXIT_CODE = "EXIT_CODE";
	/**错误信息[%s]*/
	public static final String E_MESSAGE = "10000";
	
	/*Dataset数据集中的错误*/
	/** 数据集错误号个数 */
	public static final int E_SIZE_DATASET_ERROR = 8;
	/** 行索引超出范围 */
	public static final String E_LINE_INDEX_OUT_OF_BOUND = "0065";
	/** 列索引超出范围 */
	public static final String E_COLUMN_INDEX_OUT_OF_BOUND = "0066";
	/** 指定的行列位置无效 */
	public static final String E_LOCATION_OUT_OF_BOUND = "0067";
	/** 列名不存在 */
	public static final String E_COLUMN_NAME_NOT_EXSITED = "0068";
	/** 列类型无效 */
	public static final String E_COLUMN_TYPE_INVALID = "0069";
	/** IDataset中尽力的类型转换失败 */
	public static final String E_TYPE_CONVERT_FAILURE = "0070";
}
