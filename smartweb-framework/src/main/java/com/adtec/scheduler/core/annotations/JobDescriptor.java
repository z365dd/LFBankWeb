package com.adtec.scheduler.core.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * <p> </p>
 *
 * @author lijb
 * @since 2020/12/24
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface JobDescriptor {

    /**
     * 中文名
     */
    String name();

    /**
     * CRON表达式
     */
    String cronExpr();

    /**
     * 模块代码
     */
    String modlCode() default "";

    /**
     * 模块描述
     */
    String modlDesc() default "";

    /**
     * 执行日期选择方式
     *
     * @return /
     */
    PlanExecTp planExecTp() default PlanExecTp.WEEK;

    /**
     * 任务执行方式
     */
    PlanExecMeth planExecMeth() default PlanExecMeth.SERIAL;

    /**
     * 并行度
     */
    int numKv() default 1;

    /**
     * 是否启用
     */
    Whether openSwitchFlg() default Whether.YES;

    /**
     * 启动时执行
     */
    Whether runSwitchFlg() default Whether.NO;

    /**
     * 任务说明
     */
    String remark() default "";

    enum Whether {
        /**
         * 是
         */
        YES("1"),
        /**
         * 否
         */
        NO("0");
        private final String code;

        Whether(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    enum PlanExecTp {
        /**
         * 周
         */
        WEEK,
        /**
         * 月
         */
        MONTH;
    }

    enum PlanExecMeth {
        /**
         * 串行
         */
        SERIAL("0"),
        /**
         * 并行
         */
        PARALLEL("1");

        private final String code;

        PlanExecMeth(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
}
