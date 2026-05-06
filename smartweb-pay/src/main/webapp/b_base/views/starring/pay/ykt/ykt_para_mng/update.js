let $from;
let tranAmtFlg = true;
let dayAmtFlg = true;

$(function () {
    parent.window.$('#iframe_update').show();
    $from = $('#from');
    var encodeData = window.parent.document.getElementById('comDiv').innerText;
    var data = JSON.parse(encodeData);
    console.info(data);

    setI('busiNo', data.busi_NO);
    setI('busiName',data.busi_NAME)
    setI('tranAmt',data.tran_AMT)
    setI('dayAmt',data.day_AMT)
    data.busiNo = data.busi_NO;
    data.busiName = data.busi_NAME;

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
        console.info(dayAmtFlg);
        console.info(tranAmtFlg);
        if (dayAmtFlg && tranAmtFlg) {
            save();
        } else {
            showTip("请检查必填项是否已正确填写", "error", 2000, 100);
        }
    });
});


function save() {
    confirmx('是否保存修改', function () {
        const data = $("#from").serializeObject();
        //保存
        data.operStat = '2';
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
