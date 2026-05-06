let $table;
let $from;
let busiM = new Map();
let $strTime;
let $endTime;

$(function () {
    parent.window.$('#iframe_update').show();
    //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务
    const busiInfo = busiSelect($("select[name='MERT_NO']"), '00');
    $table = $('#payTimeTable');
    $from = $('#from');
    busiInfo.forEach(item => {
        busiM.set(item.busiNo, item.busiName);
    });
    $strTime = $('label[for="STR_TIME"]');
    $endTime = $('label[for="END_TIME"]');
    const url = new URL(window.location.href);
    const MERT_NO = url.searchParams.get('mert_no');
    console.log(MERT_NO);
    const RULE_NO = url.searchParams.get('rule_no');
    console.log(RULE_NO);
    const RULE_TP = url.searchParams.get('rule_tp');
    const STR_TIME = url.searchParams.get('str_time');
    const END_TIME = url.searchParams.get('end_time');
    $("#MERT_NO").on("change", function (e) {
        busiNo();
    });
    $("#RULE_TP").on("change", function (e) {
        ruleTp();
    });
    setI("RULE_NO", RULE_NO);
    setI("MERT_NO", MERT_NO);
    setS("RULE_TP", RULE_TP);
    ruleTp();
    busiNo();
    $('#STR_TIME').val(STR_TIME);
    $('#END_TIME').val(END_TIME);

    document.querySelector("form").addEventListener('submit', function (event) {
        event.preventDefault(); // 阻止表单默认的提交行为
        save();
    });
});

function busiNo() {
    if (busiM == undefined || busiM.size==0) {
        //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务
        const busiInfo = busiSelect($("select[name='MERT_NO']"), '00');
        busiInfo.forEach(item => {
            busiM.set(item.busiNo, item.busiName);
        });
    }
    setI("busiName", busiM.get(getI("MERT_NO")));
}

function ruleTp() {
    var index = getS("RULE_TP");
    if (index > 3 || index < 1) {
        index = 1
    }
    console.info(index)
    setI("STR_TIME", "");
    setI("END_TIME", "");
    var lable = "";
    var dateFmt = "";
    var isShowClear = false;
    var quickSel = [];
    var qsEnabled = false;
    if (index == 1) {
        lable = "时间";
        dateFmt = "HH:mm";
        quickSel = ['00:00', '12:00', '20:00', '23:00', '23:59']
        qsEnabled = true;
    } else if (index == 2) {
        lable = "日期";
        dateFmt = "dd";
    } else if (index == 3) {
        lable = "日期时间";
        dateFmt = "yyyy-MM-dd HH:mm";
    }
    $strTime.text("限制开始" + lable + "：");
    $endTime.text("限制结束" + lable + "：");
    $('#STR_TIME').unbind('click').bind('click', function () {
        WdatePicker({
            dateFmt: dateFmt,
            isShowClear: isShowClear,
            quickSel: quickSel,
            qsEnabled: qsEnabled
        });
    });
    $('#END_TIME').unbind('click').bind('click', function () {
        WdatePicker({
            dateFmt: dateFmt,
            isShowClear: isShowClear,
            quickSel: quickSel,
            qsEnabled: qsEnabled
        });
    });
}

function save() {
    confirmx('是否保存', function () {
        const data = $("#from").serializeObject();
        sendPostOfAjax(ctx + '/payTime/data/update', data, false, true, () => {
            parent.window.$("a[href^='#tab_list']").click();
        });

    })
}
