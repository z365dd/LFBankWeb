const cancel = () => parent.window.$("a[href^='#tab_list']").click();

const submitForm = msg => confirmx(msg, () => save());

const weeks = () => [
    {
        label: "周一",
        value: "MON"
    },
    {
        label: "周二",
        value: "TUE"
    },
    {
        label: "周三",
        value: "WED"
    },
    {
        label: "周四",
        value: "THU"
    },
    {
        label: "周五",
        value: "FRI"
    },
    {
        label: "周六",
        value: "SAT"
    },
    {
        label: "周日",
        value: "SUN"
    }
];

const months = Object.keys(
    Array.apply(null, { length: 31 })
).map(item => {
    const day = parseInt(item) + 1;
    return {
        label: `${day}日`,
        value: day
    }
});

/**
 * 日期选择方式是否按周选择
 * @returns {boolean}
 */
const weekChoice = () => $("#planExecTp").val() === "week";

/**
 * 是否使用 cron表达式
 * @returns {boolean}
 */
const typeCron = () => $("#useCron").val() === "1";

const getNextExecTime = cron => {
    $.post(ctx + "/sys/schedule/getNextExecTime", { cronExpr: cron }, response => processResult({
        response,
        onError: msg => $('#nextExecTime').val(msg),
        onSuccess: (msg, data) => {
            let timeStr = [];
            if (data !== undefined) {
                $.each(data, (index, time) => {
                    timeStr.push(`第${index + 1}次：${time}`);
                });
                $('#nextExecTime').val(timeStr.join('\n'));
            }
        }
    }), "json");
}

const processResult = ({ response, onSuccess, onError, showSucMsg, showErrMsg }) => {
    const { returnCode, message, msg_type, data } = response;
    if (returnCode !== undefined && "0000" !== returnCode) {
        const errMsg = `错误信息[${message}]`;
        if (showErrMsg) {
            showContent(errMsg, "error");
        }
        if (typeof onError === "function") {
            onError(errMsg);
        }
    } else if (msg_type === "success") {
        const successMsg = `交易成功[${message}]`;
        if (showSucMsg) {
            showContent(successMsg, "success");
        }
        console.info(successMsg);
        if (typeof onSuccess === "function") {
            onSuccess(successMsg, data);
        }
    }
}

const initNodeEvent = () => {
    /* 是否使用 cron表达式 */
    const $useCron = $("#useCron");
    $useCron.on("change", () => useCronEffect($useCron.val()));

    /* 执行日期选择方式 */
    const $planExecTp = $("#planExecTp");
    $planExecTp.on("change", () => planExecTpEffect($planExecTp.val()));

    /* 任务执行时间单位 */
    const $timeUnitTp = $("#timeUnitTp");
    $timeUnitTp.on("change", () => timeUnitTpEffect($timeUnitTp.val()));

    const $planExecMeth = $("#planExecMeth");
    $planExecMeth.change(() => planExecMethEffect($planExecMeth.val()));

    $("#cronExpr").cronGen({
        direction: 'right',
        // 选择改变
        selectChange: cron => {
            getNextExecTime(cron);
        },
        // 输入改变
        inputChange: () => {
            getNextExecTime($('#cronExpr').val());
        }
    });
}

const useCronEffect = useCron => {
    if (useCron === "1") {
        formHelper.show(["cronExpr", "nextExecTime"])
        formHelper.hide(["timeUnitTp", "intvlTime", "strTime", "endTime", "planExecTp", "procDateParaVal"])
    } else {
        formHelper.hide(["cronExpr", "nextExecTime"])
        formHelper.show(["timeUnitTp", "intvlTime", "strTime", "endTime", "planExecTp", "procDateParaVal"])
    }
}

const timeUnitTpEffect = timeUnitTp => {
    if (timeUnitTp !== "O") {
        const timeUnit = $("select[name=timeUnitTp]").find('option').filter(':selected').text();
        $("#timeUnit").text(timeUnit);
        formHelper.show(["intvlTime", "endTime"]);
    } else {
        formHelper.hide(["intvlTime", "endTime"]);
    }
}

const planExecMethEffect = planExecMeth => {
    if (planExecMeth === "0") {
        formHelper.hide(["numKv"]);
    } else {
        formHelper.show(["numKv"]);
    }
}

const planExecTpEffect = planExecTp => {
    const $procDateParaVal = $("#procDateParaVal");
    const $execDateLabel = $("label[for='procDateParaVal']");
    if (planExecTp === "week") {
        $procDateParaVal.multiselect('dataprovider', weeks()).multiselect('rebuild');
        $execDateLabel.text("每周");
    } else {
        $procDateParaVal.multiselect('dataprovider', months).multiselect('rebuild');
        $execDateLabel.text("每月");
    }
}

const reloadIframe = iframeId => {
    const _body = window.parent;
    const _iframe1 = _body.parent.document.getElementById(iframeId);
    _iframe1.contentWindow.location.reload(true);
}