let $from;
let busiM = new Map();
let oweMonthCheck = false;
let amtCheck = false;


$(function () {
    parent.window.$('#iframe_update').show();
    $from = $('#from');
    var encodeData = window.parent.document.getElementById('comDiv').innerText;
    var data = JSON.parse(encodeData);
    setI('busiNo', data.busi_NO);
    setI('busiName', data.busi_NAME);
    setI('projName', data.proj_NAME);
    setI('origProjName', data.proj_NAME);
    setI('projDesc', data.proj_DESC);
    setI('projTp', data.proj_TP == '00' ? '非自主录入' : '自主录入');

    //收费项目名称校验
    var projNameCheck = true;
    $("#projNameError").hide();
    document.getElementById("projName").addEventListener("blur", function (event) {
        const projName = getI("projName");
        var validate = /^[a-zA-Z0-9\u4e00-\u9fa5-_]{2,20}$/;
        if (!validate.test(projName)) {
            projNameCheck = false;
            $("#projNameError").show();
            return;
        }
        projNameCheck = true;
        $("#projNameError").hide();
    });
    //收费描述验证
    var projDescCheck = true;
    $("#projDescError").hide();
    document.getElementById("projDesc").addEventListener("blur", function (event) {
        const projDesc = getI("projDesc");
        var validate = /^[a-zA-Z0-9\u4e00-\u9fa5_-]{2,20}$/;
        if (!validate.test(projDesc)) {
            projDescCheck = false;
            $("#projDescError").show();
            return;
        }
        projDescCheck = true;
        $("#projDescError").hide();
    });


    document.querySelector("form").addEventListener('submit', function (event) {
        event.preventDefault(); // 阻止表单默认的提交行为
        const tp = data.proj_TP;
        if (tp == '01' && projNameCheck == true && projDescCheck == true && oweMonthCheck == true && amtCheck == true) {
            save();
        } else if (tp == '00' && projNameCheck == true && projDescCheck == true) {
            save();
        } else {
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


/**
 * 修改保存
 */
function save() {
    var data = {};
    data.busiNo = getI('busiNo');
    data.busiName = getI('busiName');
    data.projName = getI('projName');
    data.projDesc = getI('projDesc');
    data.origProjName = getI('origProjName');
    data.projTp = '00';
    data.operTp = '2';
    sendPostOfAjax(ctx + '/offline/data/proj_modify', data, false, true, () => {
        //添加成功
        parent.window.$('a[href^=\'#tab_list\']').click();
    });
}

function checkInput() {
    //展示块
    $('#oweMonthDiv').show();
    $('#amtDiv').show();

    //收费周期校验
    document.getElementById("oweMonth").addEventListener("blur", function (event) {
        const oweMonth = getI("oweMonth");
        var validate = /^[0-9_-]{4,10}$/;
        if (!validate.test(oweMonth)) {
            oweMonthCheck = false;
            $("#oweMonthError").show();
            return;
        }
        oweMonthCheck = true;
        $("#oweMonthError").hide();
    });

    //金额校验
    document.getElementById("amt").addEventListener("blur", function (event) {
        const amt = getI("amt");
        var validate = /(^[1-9](\d+)?(\.\d{1,2})?$)|(^0$)|(^\d\.\d{1,2}$)/;
        if (amt == 0 || amt == 0.00 || amt == 0.0) {
            amtCheck = false;
            $("#amtError").text('输入的金额必须大于0');
            $("#amtError").show();
            return;
        }
        if (!validate.test(amt)) {
            amtCheck = false;
            $("#amtError").show();
            return;
        }
        amtCheck = true;
        $("#amtError").hide();
    });
}


