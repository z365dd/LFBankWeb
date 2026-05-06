let $from;
let tranAmtFlg = false;
let dayAmtFlg = false;
let busiM = new Map();


$(function () {
    parent.window.$('#iframe_add').show();
    $from = $('#from');

    //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务 20-一卡通
    busiNo();

    $("#busiNo").on("change", function (e) {
        if (busiM == undefined || busiM.size==0) {
            busiNo();
        }
        setI('busiName', busiM.get(getS('busiNo')))
    });


    /*返回按钮*/
    $('#back').click(function () {
        back();
    });

    $("#tranAmtErrTip").hide();
    $("#tranAmt").on("blur", function (event) {
        const tranAmt = getI("tranAmt");
        var validate = /(^[1-9](\d+)?(\.\d{1,2})?$)|(^0$)|(^\d\.\d{1,2}$)/;
        if (tranAmt == 0 || tranAmt == 0.00 || tranAmt == 0.0) {
            tranAmtFlg = false;
            $("#tranAmtErrTip").text('输入的单笔限额必须大于0');
            $("#tranAmtErrTip").show();
            return;
        }
        if (!validate.test(tranAmt)) {
            tranAmtFlg = false;
            $("#tranAmtErrTip").text('请输入正确的单笔限额');
            $("#tranAmtErrTip").show();
            return;
        }
        if (tranAmt.toString().length > 16){
            tranAmtFlg = false;
            $("#tranAmtErrTip").text('输入的单笔限额长度不能超过16位');
            $("#tranAmtErrTip").show();
            return;
        }
        tranAmtFlg = true;
        $("#tranAmtErrTip").hide();
    });


    $("#dayAmtErrTip").hide();
    $("#dayAmt").on("blur", function (event) {
        const dayAmt = getI("dayAmt");
        var validate = /(^[1-9](\d+)?(\.\d{1,2})?$)|(^0$)|(^\d\.\d{1,2}$)/;
        if (dayAmt == 0 || dayAmt == 0.00 || dayAmt == 0.0) {
            dayAmtFlg = false;
            $("#dayAmtErrTip").text('输入的日累计限额必须大于0');
            $("#dayAmtErrTip").show();
            return;
        }
        if (!validate.test(dayAmt)) {
            dayAmtFlg = false;
            $("#dayAmtErrTip").text('请输入正确的日累计限额');
            $("#dayAmtErrTip").show();
            return;
        }
        if (dayAmt.toString().length > 16){
            dayAmtFlg = false;
            $("#dayAmtErrTip").text('输入的日累计限额长度不能超过16位');
            $("#dayAmtErrTip").show();
            return;
        }
        dayAmtFlg = true;
        $("#dayAmtErrTip").hide();
    });


    document.querySelector("form").addEventListener('submit', function (event) {
        event.preventDefault(); // 阻止表单默认的提交行为
        if (dayAmtFlg && tranAmtFlg) {
            save();
        } else {
            showTip("请检查必填项是否已正确填写", "error", 2000, 100);
        }
    });
});


//获取当前登录用户的可操作业务
function busiNo() {
    //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务 20-一卡通
    const busiInfo = busiSelect($("select[name='busiNo']"), '20');
    if (busiInfo != undefined && busiInfo != null) {
        busiInfo.forEach(item => {
            busiM.set(item.busiNo, item.busiName);
        });
    }
    setI('busiName', busiM.get(getS('busiNo')))
}


function save() {
    confirmx('是否保存', function () {
        const data = $("#from").serializeObject();
        //保存
        data.operStat = '1';
        sendPostOfAjax(ctx + '/ykt/data/para_modify', data, false, true, () => {
            //添加成功
            parent.window.$('a[href^=\'#tab_list\']').click();
        });
    })
}

//返回列表查询
function back() {
    parent.window.$('a[href^=\'#tab_list\']').click();
}

