let $table;
let $from;
let busiM = new Map();
let $strTime;
let $endTime;

$(function () {
    parent.window.$('#iframe_add').show();
    //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务
    const busiInfo = busiSelect($("select[name='MERT_NO']"), '00');
    $table = $('#payTimeTable');
    $from = $('#from');
    busiInfo.forEach(item => {
        busiM.set(item.busiNo, item.busiName);
    });
    $strTime = $('label[for="STR_TIME"]');
    $endTime = $('label[for="END_TIME"]');
    $("#MERT_NO").on("change", function (e) {
        busiNo();
    });
    console.info("进入方法")
    // setI('END_TIME',getYesterday())
    ruleTp();
    console.info("进入方法2")
    $("#RULE_TP").on("change", function (e) {
        ruleTp();
    });
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
    setI("busiName", busiM.get(getS("MERT_NO")));
}

function ruleTp() {
    var index = getS("RULE_TP");
    if (index > 3 || index < 1) {
        index = 1
    }
    console.info(index);
    var defaultStartTime = "";
    var defaultEndTime = "";
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
        defaultStartTime = "00:00";
        defaultEndTime = "23:59";
    } else if (index == 2) {
        lable = "日期";
        dateFmt = "dd";
        defaultStartTime = "01";
        defaultEndTime = "31";
    } else if (index == 3) {
        lable = "日期时间";
        dateFmt = "yyyy-MM-dd HH:mm";
        const curDate = getCurDate();
        defaultStartTime = curDate + " 00:00";
        defaultEndTime = curDate + " 23:59";
    }
    $strTime.text("限制开始" + lable);
    $endTime.text("限制结束" + lable);
    console.info(lable);
    console.info(dateFmt);
    console.info(defaultStartTime);
    console.info(defaultEndTime);
    console.info("配置开始时间开始")
    $('#STR_TIME').unbind('click').bind('click', function () {
        WdatePicker({
            dateFmt: dateFmt,
            isShowClear: isShowClear,
            quickSel: quickSel,
            qsEnabled: qsEnabled
        });
    });
    console.info("配置开始时间结束")
    console.info("配置结束时间开始")
    $('#END_TIME').unbind('click').bind('click', function () {
        WdatePicker({
            dateFmt: dateFmt,
            isShowClear: isShowClear,
            quickSel: quickSel,
            qsEnabled: qsEnabled
        });
    });
    console.info("配置结束时间结束")
    setI("STR_TIME", defaultStartTime);
    setI("END_TIME", defaultEndTime);
}

function save() {
    confirmx('是否保存', function () {
        const data = $("#from").serializeObject();
        sendPostOfAjax(ctx + '/payTime/data/add', data, false, true, () => {
            setS("MERT_NO", "");
            setI("busiName", "");
        });
    })
}

function getCurDate() {
    var myDate = new Date();	//创建Date对象
    var Y = myDate.getFullYear();   //获取当前完整年份
    var M = myDate.getMonth() + 1;  //获取当前月份
    var D = myDate.getDate();   //获取当前日1-31
    // 月份不足10补0
    if (M < 10) {
        M = '0' + M;
    }
    // 日不足10补0
    if (D < 10) {
        D = '0' + D;
    }
    // 拼接日期分隔符根据自己的需要来修改
    return Y + '-' + M + '-' + D;
}
