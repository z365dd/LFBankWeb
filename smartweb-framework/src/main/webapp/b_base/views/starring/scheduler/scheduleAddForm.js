$(document).ready(function () {
    /*把新增页面内容显示出来*/
    parent.window.$("#iframe_add").show();

    /*保存按钮*/
    $('#saveBtn').on("click", () => {
        if (proof()) {
            submitForm("是否新增自动任务");
        }
    });

    /*取消按钮*/
    $('#cancelBtn').on("click", () => cancel());

    initNodeEvent();

    initForm();

});

const initForm = () => {
    formHelper.hide([
        "cronExpr", "nextExecTime", "numKv"
    ]);
    const selectedWeek = weeks().map(week => {
        week["selected"] = true;
        return week;
    });
    const $procDateParaVal = $("#procDateParaVal");
    $procDateParaVal.multiselect('dataprovider', selectedWeek).multiselect('rebuild');
}

/**
 * 保存函数--保存自动任务新增
 * @returns
 */
const save = () => {
    const $useCron = $("#useCron");
    const formData = $("#addForm").serializeObject();
    let procDateParaVal = $("#procDateParaVal").val();
    if (procDateParaVal !== "" && procDateParaVal != null) {
        formData["procDateParaVal"] = procDateParaVal.join(',');
    }
    if ($useCron.val() === "0") {
        formData["cronExpr"] = "";
    }
    console.info(formData);

    /*向后台发送参数*/
    $.post(`${ctx}/sys/schedule/insert`, formData,
        response => processResult({
            response,
            onSuccess: cancel(),
            showSucMsg: true,
            showErrMsg: true
        }), "json");
}