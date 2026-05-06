let $from;
let busiM = new Map();
let lmtCheck = false;
let lmtAmtCheck = false;


$(function () {
    parent.window.$('#iframe_add').show();
    $from = $('#from');

    busiNo();
    if (busiM == undefined || busiM.size==0) {
        busiNo();
    }
    $("#busiNo").on("change", function (e) {
        if (busiM == undefined || busiM.size==0) {
            busiNo();
        }
        setI('busiName', busiM.get(getS('busiNo')))
    });

    checkInput();


    document.querySelector("form").addEventListener('submit', function (event) {
        event.preventDefault(); // 阻止表单默认的提交行为
        if (lmtAmtCheck && dayAmtCheck) {
            save();
        }  else {
            showTip("请检查必填项是否已填写", "error", 2000, 100);
        }
    });

    /*返回按钮*/
    $('#back').click(function () {
        back();
    });
});

//返回列表查询
function back() {
    parent.window.$('a[href^=\'#tab_list\']').click();
}

//获取当前登录用户的可操作业务
function busiNo() {
    //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务
    const busiInfo = busiSelect($("select[name='busiNo']"), '');
    if (busiInfo != undefined && busiInfo != null) {
        busiInfo.forEach(item => {
            busiM.set(item.busiNo, item.busiName);
        });
    }
    setI('busiName', busiM.get(getS('busiNo')));
}

function save() {
    confirmx('是否保存', function () {
        var data = $("#from").serializeObject();
        data.operTp = 'add';
        sendPostOfAjax(ctx + '/limitAmt/data/modify', data, false, true, () => {
            //添加成功
            parent.window.$('a[href^=\'#tab_list\']').click();
        });
    })
}

function checkInput() {

    //收费周期校验
    document.getElementById("lmtAmt").addEventListener("blur", function (event) {
        const lmtAmt = getI("lmtAmt");
        var validate = /(^[1-9](\d+)?(\.\d{1,2})?$)|(^0$)|(^\d\.\d{1,2}$)/;
        if (lmtAmt == 0 || lmtAmt == 0.00 || lmtAmt == 0.0) {
            lmtAmtCheck = false;
            $("#lmtAmtError").text('输入的金额必须大于0');
            $("#lmtAmtError").show();
            return;
        }
        if (!validate.test(lmtAmt)) {
            lmtAmtCheck = false;
            $("#lmtAmtError").text('请输入正确格式的单笔限额');
            $("#lmtAmtError").show();
            return;
        }
        lmtAmtCheck = true;
        $("#lmtAmtError").hide();
    });

    //金额校验
    document.getElementById("dayAmt").addEventListener("blur", function (event) {
        const dayAmt = getI("dayAmt");
        var validate = /(^[1-9](\d+)?(\.\d{1,2})?$)|(^0$)|(^\d\.\d{1,2}$)/;
        if (dayAmt == 0 || dayAmt == 0.00 || dayAmt == 0.0) {
            dayAmtCheck = false;
            $("#dayAmtError").text('输入的金额必须大于0');
            $("#dayAmtError").show();
            return;
        }
        if (!validate.test(dayAmt)) {
            dayAmtCheck = false;
            $("#dayAmtError").text('请输入正确格式的日累计限额');
            $("#dayAmtError").show();
            return;
        }
        dayAmtCheck = true;
        $("#dayAmtError").hide();
    });
}


