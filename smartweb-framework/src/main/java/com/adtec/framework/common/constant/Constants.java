/**
 *
 */
package com.adtec.framework.common.constant;

/**
 * @类名 Constants
 * @描述:
 *     常量接口类
 * @version v1.0
 */
public interface Constants {
	/**
	 * 日志是否启用DEBUG模式，线程变量IS_DEBUG
	 */
	public static final String IS_DEBUG = "IS_DEBUG";
	
    /*文件表资产类型*/
	public static final String PUB_RES = "0000";			 //0000-平台公共资源
    public static final String MSMALL_FRAMEWORK = "0101";    //0101-资产库框架
    public static final String MSMALL_APPSYSTEM = "0102";    //0102-资产库应用
    public static final String MSMALL_FIELD = "0103";        //0103-资产库领域
    public static final String MSMALL_TEC_STANDART = "0104"; //0104-资产库技术规范
    public static final String MSMALL_RENT = "0105";         //0105-资产库租户
    public static final String MSMALL_MSG = "0106";          //0106-资产库样例报文
    public static final String MSMALL_SVC = "0107";          //0107-资产库服务
    public static final String MSMALL_COMP = "0108";		 //0108-资产库组件
    public static final String MSMALL_DICT = "0109";		 //0109-资产库字典

	/**
	 * 操作标识
	 */
	public static final String OP_A="1"; //新增
	public static final String OP_D="2"; //删除
	public static final String OP_U="3"; //修改

	/**
	 * 用户标识
	 */
	public static final String USER="user";
	/**
	 * 资源标识
	 */
	public static final String RESOURCE="resource";

	/**
	 * 调度管理常量接口
	 */
	interface Scheduler {

		String INSERT = "insert";

		String UPDATE = "update";

		String DELETE = "delete";

		String LAUNCH = "launch";

		String STOP = "stop";

		interface CronUnit {

			String SECOND = "S";

			String MINUTE = "M";

			String HOUR = "H";

			String ONCE = "O";

		}

		interface Status {

			String RUN = "1";

			String STOP = "0";
		}

		/**
		 * 是否串行
		 */
		interface Serial {

			String YES = "0";

			String NO = "1";
		}
	}
}
