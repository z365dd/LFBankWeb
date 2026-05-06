package com.adtec.scheduler.core.util;

import com.adtec.framework.common.util.DateUtil;
import com.adtec.scheduler.entity.ScheduleJobDO;
import org.quartz.TriggerUtils;
import org.quartz.impl.triggers.CronTriggerImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.adtec.framework.common.constant.Constants.Scheduler.CronUnit;


/**
 * cron表达式工具类
 *
 * @author lijunbin
 */
public class CronUtil {

    /**
     * 冒号
     */
    private static final String COLON = ":";
    /**
     * 日期常量
     */
    private static final String DAY = "day";
    /**
     * 日期占位符，最终替换为执行日期或 ?
     */
    private static final String DAY_PATTERN = "$";
    /**
     * 星期常量
     */
    private static final String WEEK = "week";
    /**
     * 星期占位符，最终替换为执行日期或 ?
     */
    private static final String WEEK_PATTERN = "&";
    /**
     * 代表指定时间范围内触发
     */
    private static final String TO_PATTERN = "-";
    /**
     * 互斥占位符，日期与星期互斥，以日期执行时，星期应为 ? ，反之同理
     */
    private static final String QUESTION_MARK = "?";

    public static final String TRANS_DATE_FMT = "yyyy-MM-dd HH:mm:ss";

    private static final String TRANS_TIME_FMT = "HH:mm:ss";
    /**
     * 每天执行一次
     */
    private static final String ONCE_CRON = "ss mm HH $ * &";
    /**
     * 每秒钟任务，按月执行
     */
    private static final String PER_SECOND_CRON = "00/ * HH- $ * &";
    /**
     * 每分钟任务，按月执行
     */
    private static final String PER_MINUTE_CRON = "00 mm-59/ HH- $ * &";
    /**
     * 每小时任务，按月执行
     */
    private static final String PER_HOUR_CRON = "00 mm HH-/ $ * &";

    /**
     * ss mm HH dd MM ? yyyy
     */
    private static final SimpleDateFormat SDF = new SimpleDateFormat("ss mm HH dd MM ? yyyy");

    /**
     * 生成cron表达式
     *
     * @param scheduleJobDO scheduleJobDO
     * @return cron表达式
     */
    public static String generateCron(ScheduleJobDO scheduleJobDO) {
        scheduleJobDO.setStrTime(scheduleJobDO.getStrTime() + ":00");
        StringBuilder preCron = new StringBuilder();
        CronUnitEnum cronUnitEnum = CronUnitEnum.codeOf(scheduleJobDO.getTimeUnitTp());
        String format = cronUnitEnum.getValue();
        String preCronStr = getPreCron(scheduleJobDO.getStrTime(), format);
        if (WEEK.equals(scheduleJobDO.getPlanExecTp())) {
            // 星期占位符替换为执行日期，日期占位符替换为 ?
            preCron.append(preCronStr.replace(WEEK_PATTERN, scheduleJobDO.getProcDateParaVal()).replace(DAY_PATTERN, QUESTION_MARK));
        } else {
            // 日期占位符替换为执行日期，星期占位符替换为 ?
            preCron.append(preCronStr.replace(WEEK_PATTERN, QUESTION_MARK).replace(DAY_PATTERN, scheduleJobDO.getProcDateParaVal()));
        }
        if (!CronUnit.ONCE.equals(cronUnitEnum.getCode())) {
            scheduleJobDO.setEndTime(scheduleJobDO.getEndTime() + ":59");
            // 插入时间间隔
            preCron.insert(preCron.indexOf("/") + 1, scheduleJobDO.getIntvlTime());

            // 插入结束时间（小时）
            if (compareHour(scheduleJobDO)) {
                preCron.deleteCharAt(preCron.lastIndexOf(TO_PATTERN));
            } else {
                preCron.insert(preCron.lastIndexOf(TO_PATTERN) + 1, scheduleJobDO.getEndTime().split(COLON)[0]);
            }
        }
        return preCron.toString();
    }

    /**
     * 获取cron表达式接下来的numTimes次执行时间
     *
     * @param numTimes 次数
     * @return cron表达式接下来的limit次执行时间
     */
    public static List<String> getNextExecTimeStr(String cronExpression, Integer numTimes) {
        List<String> list = new ArrayList<>();
        CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();
        try {
            cronTriggerImpl.setCronExpression(cronExpression);
        } catch (ParseException e) {
            // e.printStackTrace();
            System.out.println("操作异常");
        }
        // 这个是重点，一行代码搞定
        List<Date> dates = TriggerUtils.computeFireTimes(cronTriggerImpl, null, numTimes);
        for (Date date : dates) {
            list.add(DateUtil.formatDate(date, TRANS_DATE_FMT));
        }
        return list;
    }

    private static void deleteCharWhenExist(StringBuilder stringBuilder, String pattern) {
        int index = stringBuilder.indexOf(pattern);
        if (index != -1) {
            stringBuilder.deleteCharAt(index);
        }
    }

    private static boolean compareHour(ScheduleJobDO scheduleJobDO) {
        return scheduleJobDO.getStrTime().split(COLON)[0].equals(scheduleJobDO.getEndTime().split(COLON)[0]);
    }

    private static String getPreCron(String dateStr, String format) {
        return DateUtil.Date2String(getFmtTime(dateStr), format);
    }

    private static Date getFmtTime(String dateStr) {
        return DateUtil.string2Date(dateStr, TRANS_TIME_FMT);
    }

    /***
     * 日期转换cron表达式
     * @param date
     * @return
     */
    public static String getDateCronExpr(Date date) {
        String formatTimeStr = null;
        if (null != date) {
            formatTimeStr = SDF.format(date);
        }
        return formatTimeStr;
    }

    public static String getDateCronExpr(String dateStr) {
        Date date = DateUtil.string2Date(dateStr, TRANS_DATE_FMT);
        return getDateCronExpr(date);
    }

    public enum CronUnitEnum {

        /**
         * 一次
         */
        ONCE(CronUnit.ONCE, ONCE_CRON),
        /**
         * 秒
         */
        SECOND(CronUnit.SECOND, PER_SECOND_CRON),
        /**
         * 分钟
         */
        MINUTE(CronUnit.MINUTE, PER_MINUTE_CRON),
        /**
         * 小时
         */
        HOUR(CronUnit.HOUR, PER_HOUR_CRON);

        private String code;
        private String value;

        CronUnitEnum(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public static CronUnitEnum codeOf(String code) {
            for (CronUnitEnum cronUnitEnum : values()) {
                if (cronUnitEnum.getCode().equals(code)) {
                    return cronUnitEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }
}
