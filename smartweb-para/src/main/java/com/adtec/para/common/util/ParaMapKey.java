package com.adtec.para.common.util;

public class ParaMapKey {
	/**
	 * 所属缓存中心
	 */
	public static final String CACHE_CENTER = "CacheCenter";
	/**
	 * 状态
	 */
	public static final String STATUS = "Status";
	/**
	 * 返回码
	 */
	public final static String RETCODE = "RetCode";
	/**
	 * 返回信息
	 */
	public final static String MSG = "Msg";
	/**
	 * 英文名称
	 */
	public static final String EN_NAME = "EnName";
	/**
	 * 中文名称
	 */
	public static final String NAME = "Name";
	/**
	 * 缓存方式
	 */
	public static final String CACHE_MODE = "CacheMode";
	/**
	 * 缓存中心zk节点
	 */
	public static final String ZK_CLUSTER = "ZkCluster";
	/**
	 * 缓存中心rs节点
	 */
	public static final String REDIS_CLUSTER = "RedisCluster";
	/**
	 * 节点ip地址
	 */
	public static final String IP = "Ip";
	/**
	 * 节点端口
	 */
	public static final String PORT = "Port";
	/**
	 * agent节点CacheHost
	 */
	public static final String CACHE_HOST = "CacheHost";
	/**
	 * agent节点CachePort端口
	 */
	public static final String CACHE_PORT = "CachePort";
	/**
	 * 从节点数
	 */
	public static final String SLAVE_COUNT = "SlaveCount";
	/**
	 * 从节点数据
	 */
	public static final String SLAVE_DATA = "SlaveData";
	/**
	 * zk序号
	 */
	public static final String MYID = "MyId";
	/**
	 * 节点数
	 */
	public static final String NODE_COUNT = "NodeCount";
	/**
	 * 节点数据
	 */
	public static final String SERVERS = "Servers";
	/**
	 * 唯一索引组合
	 */
	public static final String UNIQUE_KEY = "UniqueKey";
	/**
	 * 租户
	 */
	public static final String TENANT = "Tenant";
	/**
	 * 读取权限等级
	 */
	public static final String READ_AUTH_LEVEL = "ReadAuthLevel";
	/**
	 * 读取权限列表
	 */
	public static final String READ_AUTH_LIST = "ReadAuthList";
	/**
	 * 写入权限列表
	 */
	public static final String WRITE_AUTH_LIST = "WriteAuthList";
	/**
	 * 存储规则键项
	 */
	public static final String COLUMN = "Column";
	/**
	 * 字段名
	 */
	public static final String COLID = "ColId";
	/**
	 * 中文描述
	 */
	public static final String COLNAME = "ColName";
	/**
	 * 数据类型
	 */
	public static final String TYPE = "Type";
	/**
	 * 字段长度
	 */
	public static final String LENGTH = "Length";
	/**
	 * 是否非空
	 */
	public static final String ISNOTNULL = "IsNotNull";
	/**
	 * 唯一索引
	 */
	public static final String ISUNIQUE = "IsUnique";
	/**
	 * 存储规则
	 */
	public static final String RULES_STG = "RulesStg";
	/**
	 * Agent唯一ID
	 */
	public final static String AGENT_SEQ = "AgentSeq";
	/**
	 * 参与者ID
	 */
	public final static String PARTID = "PartId";
	/**
	 * 参与者实例
	 */
	public final static String PART_INST = "PartInst";
	/**
	 * 参与者版本号
	 */
	public final static String PARTVERSION = "PartVersion";
	/**
	 * 交易令牌
	 */
	public final static String TOKEN = "Token";
	/**
	 * zk-认证登陆名
	 */
	public static final String ZK_LOGIN_NAME = "ZkLoginName";
	/**
	 * zk-认证密码
	 */
	public static final String ZK_LOGIN_PWD = "ZkLoginPassword";
	/**
     * redis-认证登陆名
     */
	public static final String REDIS_LOGIN_NAME = "RedisLoginName";
	/**
     * redis-认证密码
     */
	public static final String REDIS_LOGIN_PWD = "RedisLoginPassword";
	/**
	 * TCP查询AGENT本地缓存返回结果集
	 */
	public static final String DATA = "DATA";
	/**
	 * Agent对应参与者实例信息
	 */
	public static final String AGENT_INFO = "AgentInfo";
	/**
	 * ParamType
	 */
	public static final String PARAMTYPE = "ParamType";
	/**
	 * TCP访问Agent缓存数据返回结果
	 */
	public static final String PARAMS = "Params";
	/**
	 * TCP访问Agent缓存数据操作符集合
	 */
	public static final String PARAMS_OPERS = "ParamsOpers";
	/**
	 * 默认类型
	 */
	public static final String DEFAULT = "Default";
	
	/**
	 * AgentList信息-用于检查zk中的Agent节点变更
	 */
	public static final String AGENT_LIST = "AgentList";
	/**
	 * HostName
	 */
	public static final String HOSTNAME = "HostName";
	/**
	 * StorgRuleTp
	 */
    public static final String STORG_RULE_TP = "StorgRuleTp";
    public static final String COL_NO = "colNo";
    public static final String RMRK = "rmrk";
    public static final String VALUE = "value";
	public static final String OPER = "oper";
}
