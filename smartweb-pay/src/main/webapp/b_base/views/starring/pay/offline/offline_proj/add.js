let $from;
let busiM = new Map();
let tpMap = new Map();
let oweMonthCheck = false;
let amtCheck = false;


$(function () {
    parent.window.$('#iframe_add').show();
    $from = $('#from');
    tpMap = getTpMap($("select[name='projTp']"));

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
    //收费项目名称校验
    var projNameCheck = false;
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
    var projDescCheck = false;
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

    $("#projTp").on("change", function (e) {
        if (getS('projTp') == '01') {
            checkInput();
        } else {
            $('#oweMonthDiv').hide();
            $('#amtDiv').hide();
            $('#oweMonthError').hide();
            $('#amtError').hide();
            setI('oweMonth', '');
            setI('amt', '');
        }
    });


    document.querySelector("form").addEventListener('submit', function (event) {
        event.preventDefault(); // 阻止表单默认的提交行为
        const tp = getS('projTp');
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

function getTpMap(tpDom) {
    var tempMap = new Map();
    tempMap.set('00', '非自主录入');
    // tempMap.set('01', '自主录入');
    if (tpDom) {
        tempMap.forEach((v, k) => {
            tpDom.append(`<option value = '${k}' ${k == '0' ? 'selected' : ''}>${v}</option>`);
        })
        tpDom.multiselect('rebuild').multiselect('refresh');
    }

    return tempMap;
}

//获取当前登录用户的可操作业务
function busiNo() {
    //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务
    const busiInfo = busiSelect($("select[name='busiNo']"), '01');
    if (busiInfo != undefined && busiInfo != null) {
        busiInfo.forEach(item => {
            busiM.set(item.busiNo, item.busiName);
        });
    }
    setI('busiName', busiM.get(getS('busiNo')))
}

function save() {
    confirmx('是否保存', function () {
        var data = $("#from").serializeObject();
        data.operTp = '1';
        console.info(data);
        sendPostOfAjax(ctx + '/offline/data/proj_modify', data, false, true, () => {
            //添加成功
            parent.window.$('a[href^=\'#tab_list\']').click();
        });
    })
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


