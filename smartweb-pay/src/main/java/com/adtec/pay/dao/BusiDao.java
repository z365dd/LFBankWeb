package com.adtec.pay.dao;

import com.adtec.pay.entity.BusiDo;
import com.adtec.sys.common.dao.IBaseDao;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.table.TableRowSorter;
import java.util.List;

import static dm.jdbc.util.DriverUtil.log;

public class BusiDao implements IBaseDao<BusiDo> {

    private final static Logger logger = LoggerFactory.getLogger(BusiDao.class);

    private static final BusiDao busiDao;
    //业务表
    private static final String TABLE_NAME = "T_PIP_BUSI";
    //商户清算规则表
    private static final String MERT_ACCT_TABLE_NAME = "T_PIP_CLR_MERT_ACCT";
    //规则关系表
    private static final String RULE_TABLE_NAME = "T_PIP_RULE_RELAT";

    //联网缴费
    public final static String TYPE_ONLINE = "00";

    //非联网缴费
    public final static String TYPE_OFFLINE = "01";

    //公交卡
    public final static String TYPE_BUSCARD = "100";

    //一卡通
    public final static String TYPE_YKT = "20";


    static {
        synchronized (BusiDao.class) {
            busiDao = new BusiDao();
        }
    }

    public static BusiDao getInstance() {
        return busiDao;
    }

    private BusiDao() {
    }

    /**
     * 数据库插入
     *
     * @param objDO 数据对象DO
     * @return 返回数量
     */
    @Override
    public int insert(BusiDo objDO) {
        return 0;
    }

    /**
     * 数据库更新
     *
     * @param objDO 数据对象DO
     * @return 返回数量
     */
    @Override
    public int update(BusiDo objDO) {
        return 0;
    }

    /**
     * 数据库删除
     *
     * @param objDO
     * @return 返回数量
     */
    @Override
    public int delete(BusiDo objDO) {
        return 0;
    }

    /**
     * 数据库单笔查询
     *
     * @param objDO 数据对象DO
     * @return DO对象
     */
    @Override
    public BusiDo get(BusiDo objDO) {
        return null;
    }

    public BusiDo get(String busiNo) {
        ExecuteTask<SmsSqlTask<BusiDo>, BusiDo> task = new ExecuteTask();
        return task.sqlTaskNotNeedTransactional("获取数据", session -> {
            String sql = String.format("SELECT BUSI_NO, BUSI_NAME FROM %S WHERE BUSI_NO = ?", TABLE_NAME);
            return session.getObject(sql, BusiDo.class, busiNo);
        });
    }

    /**
     * 查询所有记录
     *
     * @param objDO 数据对象DO
     * @return List返回集合
     */
    @Override
    public List<BusiDo> list(BusiDo objDO) {
        return null;
    }

    public List<BusiDo> getAllBusi(String busiTp) {
        ExecuteTask<SmsSqlTask<List<BusiDo>>, List<BusiDo>> task = new ExecuteTask();

        return task.sqlTaskNotNeedTransactional("获取数据", session -> {
            String sql = "";
            //如果busiTp为空 查询所有  给商户注册使用
            if (StringUtils.isBlank(busiTp)) {
                sql = String.format("SELECT BUSI_NO, BUSI_NAME FROM %s ORDER BY BUSI_NO", TABLE_NAME);
                return session.getObjectList(sql, BusiDo.class);

            } else if(TYPE_ONLINE.equals(busiTp)){ //清泉自来水
                sql = String.format("SELECT BUSI_NO, BUSI_NAME " +
                        "FROM %s tpb LEFT JOIN T_PIP_ENTR tpe ON tpb.ENTR_NO = tpe.ENTR_NO " +
                        "WHERE BUSI_TP = ? AND tpe.SHORT_RMRK = 1 " +
                        "ORDER BY BUSI_NO", TABLE_NAME);
                return session.getObjectList(sql, BusiDo.class, busiTp);
            } else if(TYPE_OFFLINE.equals(busiTp)){ //非联网缴费
                sql = String.format("SELECT BUSI_NO, BUSI_NAME " +
                        "FROM %s tpb LEFT JOIN T_PIP_ENTR tpe ON tpb.ENTR_NO = tpe.ENTR_NO " +
                        "WHERE BUSI_TP = ? AND LENGTH(tpe.SHORT_RMRK) < 3 " +
                        "ORDER BY BUSI_NO", TABLE_NAME);
                return session.getObjectList(sql, BusiDo.class, busiTp);
            }else{
                //公交卡、一卡通
                sql = String.format("SELECT BUSI_NO, BUSI_NAME " +
                        "FROM %s tpb LEFT JOIN T_PIP_ENTR tpe ON tpb.ENTR_NO = tpe.ENTR_NO " +
                        "WHERE tpe.SHORT_RMRK = ? " +
                        "ORDER BY BUSI_NO", TABLE_NAME);
                return session.getObjectList(sql, BusiDo.class,busiTp);
            }
        });
    }

    //根据用户所属机构返回所有业务
    public List<BusiDo> getBusi(String brchCode, String busiTp) {
        String like = "%," + brchCode + ",%";
        logger.error("机构id"+brchCode);
        logger.error("busiTp"+busiTp);
        logger.error("like"+like);
        ExecuteTask<SmsSqlTask<List<BusiDo>>, List<BusiDo>> task = new ExecuteTask();
        return task.sqlTaskNotNeedTransactional("获取数据", session -> {
            String sql = "";
            //如果busiTp为空 查询所有  给商户注册使用
            if (StringUtils.isBlank(busiTp)) {
                sql = String.format("SELECT BUSI_NO,BUSI_NAME FROM %s WHERE BUSI_NO IN (" +
                        "SELECT BUSI_NO FROM T_PIP_RULE_RELAT WHERE RULE_ID IN (" +
                        "SELECT RULE_ID FROM T_PIP_CLR_MERT_ACCT WHERE ENTR_ACCT_BANK  IN ( " +
                        "SELECT BRCH_CODE FROM T_SYS_OFFICE WHERE PARENT_ID_LIST  LIKE ? " +
                        "union SELECT BRCH_CODE FROM T_SYS_OFFICE WHERE BRCH_CODE = ?))) ORDER BY BUSI_NO", TABLE_NAME);
                return session.getObjectList(sql, BusiDo.class, like, brchCode);
            } else if(TYPE_ONLINE.equals(busiTp)){//清泉自来水-00  SHORT_RMRK = 1

                sql = String.format("SELECT BUSI_NO,BUSI_NAME FROM %s tpb LEFT JOIN T_PIP_ENTR tpe ON tpb.ENTR_NO = tpe.ENTR_NO " +
                        "WHERE BUSI_NO IN (" +
                        "SELECT BUSI_NO FROM T_PIP_RULE_RELAT WHERE RULE_ID IN (" +
                        "SELECT RULE_ID FROM T_PIP_CLR_MERT_ACCT WHERE ENTR_ACCT_BANK  IN ( " +
                        "SELECT BRCH_CODE FROM T_SYS_OFFICE WHERE PARENT_ID_LIST  LIKE ? " +
                        "union SELECT BRCH_CODE FROM T_SYS_OFFICE WHERE BRCH_CODE = ?))) " +
                        "AND BUSI_TP = ? AND  tpe.SHORT_RMRK = ?  ORDER BY BUSI_NO", TABLE_NAME);
                return session.getObjectList(sql, BusiDo.class, like, brchCode, busiTp,'1');
            } else if(TYPE_OFFLINE.equals(busiTp)){//非联网缴费-01  SHORT_RMRK = 10
                sql = String.format("SELECT BUSI_NO,BUSI_NAME FROM %s tpb LEFT JOIN T_PIP_ENTR tpe ON tpb.ENTR_NO = tpe.ENTR_NO " +
                        " "+
                        "WHERE BUSI_NO IN (" +
                        "SELECT BUSI_NO FROM T_PIP_RULE_RELAT WHERE RULE_ID IN (" +
                        "SELECT RULE_ID FROM T_PIP_CLR_MERT_ACCT WHERE ENTR_ACCT_BANK  IN ( " +
                        "SELECT BRCH_CODE FROM T_SYS_OFFICE WHERE PARENT_ID_LIST  LIKE ? " +
                        "union SELECT BRCH_CODE FROM T_SYS_OFFICE WHERE BRCH_CODE = ?))) " +
                        "AND BUSI_TP = ?  ORDER BY BUSI_NO", TABLE_NAME);
                logger.info("一卡通执行SQL"+sql);
                return session.getObjectList(sql, BusiDo.class, like, brchCode, busiTp);
            }else{
                //100-公交卡 20 -一卡通
                sql = String.format("SELECT BUSI_NO,BUSI_NAME FROM %s tpb LEFT JOIN T_PIP_ENTR tpe ON tpb.ENTR_NO = tpe.ENTR_NO" +
                        " " +
                        "WHERE BUSI_NO IN (" +
                        "SELECT BUSI_NO FROM T_PIP_RULE_RELAT WHERE RULE_ID IN (" +
                        "SELECT RULE_ID FROM T_PIP_CLR_MERT_ACCT WHERE ENTR_ACCT_BANK  IN ( " +
                        "SELECT BRCH_CODE FROM T_SYS_OFFICE WHERE PARENT_ID_LIST  LIKE ? " +
                        "union SELECT BRCH_CODE FROM T_SYS_OFFICE WHERE BRCH_CODE = ?))) " +
                        "AND  tpe.SHORT_RMRK = ? ORDER BY BUSI_NO", TABLE_NAME);
                logger.error("sql：：：："+sql);
                return session.getObjectList(sql, BusiDo.class, like, brchCode, busiTp);
            }
        });
    }

    /**
     * 数据库多笔查询，支持分页
     *
     * @param objDO 数据对象DO
     * @param start 起始位置
     * @param limit 每页数量
     * @return List返回集合
     */
    @Override
    public List<BusiDo> list(BusiDo objDO, int start, int limit) {
        return null;
    }

    /**
     * 数据库多笔查询，支持分页
     *
     * @param start 起始位置
     * @param limit 每页数量
     * @param param 查询参数
     * @return List返回集合
     */
    @Override
    public List<BusiDo> list(int start, int limit, Object... param) {
        return null;
    }

    /**
     * 数据库商户用户关联商户查询
     * @param id
     * @return
     */
    public List<BusiDo> getMerRelateBusi(String id) {
        ExecuteTask<SmsSqlTask<List<BusiDo>>, List<BusiDo>> task = new ExecuteTask();
        return task.sqlTaskNotNeedTransactional("获取数据", session -> {
            String sql = String.format("SELECT BUSI_NO,BUSI_NAME FROM %s A " +
                    "WHERE EXISTS (SELECT 1 FROM T_SYS_BUSI_USER B WHERE USER_ID = ? " +
                    "AND A.BUSI_NO = B.BUSI_ID)", TABLE_NAME);
            return session.getObjectList(sql, BusiDo.class, id);
        });
    }
}
