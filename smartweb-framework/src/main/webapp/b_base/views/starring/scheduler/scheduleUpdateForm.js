$(function () {
    console.info("scheduleUpdatePage");
    parent.window.$("#iframe_update").show();

    $('#saveBtn').on('click', () => {
        if (proof()) {
            submitForm("是否修改自动任务");
        }
    });

    $("#cancelBtn").on('click', () => cancel());

    initNodeEvent();

    get($('#beanName').val());
});

const save = () => {

    const $useCron = $("#useCron");
    const formData = $("#scheduleForm").serializeObject();
    let procDateParaVal = $("#procDateParaVal").val();
    if (procDateParaVal !== "" && procDateParaVal != null) {
        formData["procDateParaVal"] = procDateParaVal.join(',');
    }
    if ($useCron.val() === "0") {
        formData["cronExpr"] = "";
    }
    console.info(scheduleForm);
    /*向后台发送参数*/
    $.post(`${ctx}/sys/schedule/update`, formData,
        response => processResult({
            response,
            onSuccess: cancel(),
            showSucMsg: true,
            showErrMsg: true
        }), "json");
}

const getWeekOptions = execDate => {
    const options = [];
    const execDateArr = execDate ? execDate.split(",") : [];
    for (let i = 0; i < 7; i++) {
        const weekUnit = ["周日", "周一", "周二", "周三", "周四", "周五", "周六"];
        const weekNum = ["SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"];
        const index = execDateArr.indexOf(weekNum[i]);
        if (index >= 0) {
            options.push({
                label: weekUnit[i],
                value: weekNum[i],
                selected: true
            });
        } else {
            options.push({
                label: weekUnit[i],
                value: weekNum[i]
            });
        }
    }
    return options;
}

const getMonthOptions = procDateParaVal => {
    const options = [];
    const execDateArr = procDateParaVal ? procDateParaVal.split(",") : [];
    for (let i = 1; i <= 31; i++) {
        const index = execDateArr.indexOf(i.toString());
        if (index >= 0) {
            options.push({
                label: i + "日",
                value: i.toString(),
                selected: true
            });
        } else {
            options.push({
                label: i + "日",
                value: i,
            });
        }
    }
    return options;
}

function get(beanName) {
    $.post(`${ctx}/sys/schedule/getDetail`, { "beanName": beanName },
        response => processResult({
            response,
            onSuccess: (successMsg, schedule) => {
                console.info("查询交易成功");
                const timeUnitTp = $("#timeUnitTp");

                useCronEffect(schedule["useCron"]);

                if (schedule["cronExpr"] !== undefined && schedule["cronExpr"].length > 0) {
                    $("#useCron").multiselect('select', '1').multiselect('rebuild');

                    $('#cronExpr').val(schedule["cronExpr"]);
                    $("input[name='cronGen_display']").val(schedule["cronExpr"]);
                    getNextExecTime(schedule["cronExpr"]);
                } else {
                    $("#useCron").multiselect('select', '0').multiselect('rebuild');
                    timeUnitTp.multiselect("select", schedule["timeUnitTp"]).multiselect("rebuild");

                    if (timeUnitTp.val() !== "O" && timeUnitTp.val() !== "") {
                        const timeUnit = $("select[name=timeUnitTp]").find('option').filter(':selected').text();
                        $("#timeUnit").text(timeUnit);
                        formHelper.show(["intvlTime", "endTime"]);
                    } else {
                        formHelper.hide(["intvlTime", "endTime"]);
                    }

                    $("#strTime").val(schedule["strTime"].substring(0, 5));
                    $("#val_strTime").val(schedule["strTime"].substring(0, 5));
                    $("#endTime").val(schedule["endTime"].substring(0, 5));
                    $("#val_endTime").val(schedule["endTime"].substring(0, 5));

                    let options;
                    const procDateParaVal = schedule["procDateParaVal"];
                    if (schedule["planExecTp"] === "week") {
                        options = getWeekOptions(procDateParaVal);
                    } else {
                        options = getMonthOptions(procDateParaVal);
                    }
                    $("#procDateParaVal").multiselect("dataprovider", options);
                }

                planExecMethEffect(schedule["planExecMeth"]);
                $("#numKv").val(schedule["numKv"]);
                $("#rmrk").val(schedule["rmrk"]);
            },
            showErrMsg: true
        }), "json");
}
