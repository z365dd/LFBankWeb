let $from;
let busiM = new Map();
let tpMap = new Map();
let phoneValidate = true;
let certValidate = true;

$(function () {
    parent.window.$('#iframe_update').show();
    $from = $('#from');
    var encodeData = window.parent.document.getElementById('comDiv').innerText;
    var data = JSON.parse(encodeData);
    console.info(data);
    setI('busiNo', data.busi_NO);
    setI('busiName', data.busi_NAME);
    setI('projName', data.proj_NAME);
    setI('oweMonth', data.owe_MONTH);
    setI('name', data.name);
    setI('phoneNo', data.phone_NO);
    setI('totAmt', data.tot_AMT);
    setI('subSer', data.sub_SER);
    setI('certNo', data.cert_NO);
    setI('major', data.major);
    setI('stuClass', data.stu_CLASS);
    setI('stuId', data.stu_ID);
    setI('dctAmt', data.dct_AMT);
    setI('feeAmt', data.fee_AMT);
    setI('lateFeeAmt', data.late_FEE_AMT);
    let oldOperStat = data.oper_STAT;
    setS('operStat', oldOperStat);

    //限制短信验证码输入最多10位
    var input = document.getElementById('vrfyNo');
    input.addEventListener('input', function (event) {
        if (this.value.length > 10) {
            input.value = input.value.substring(0, 10); // 截断超过限制的字符
        }
    });
    //收单差错补录 展示差错补录收单流水
    $("#operStat").on("change", function (e) {
        const operStat = getS("operStat");
        console.info("操作状态" + operStat);
        if (operStat == '04') {
            $("#addSeqLab").show();
            $("#posSeqDiv").show();
        } else {
            $("#addSeqLab").hide();
            $("#posSeqDiv").hide();
            setI('posSeq',"")
        }
    })

    /**
     * 短信验证
     */
    $('#confmSave').click(function () {
        //输入的验证码
        let vrfyNo = getI('vrfyNo');
        //短信返回的验证码
        let vrfyNoCrtId = getI('vrfyNoCrtId');
        var data = $("#from").serializeObject();
        data.operTp = '2';
        if(getS('operStat') == '04'){
            data.shortRmrk = getI('posSeq');
        }
        data.vrfyNo = vrfyNo;
        data.vrfyNoCrtId = vrfyNoCrtId;
        sendPostOfAjax(ctx + '/offline/data/verifyCode', data, false, true, () => {
            $("#modifyModal").css("display", "none");
            $("#modifyModal").modal('hide');
            setI('vrfyNo', '');
            parent.window.$('a[href^=\'#tab_list\']').click();
        });
    })

    //手机号码验证
    $("#phoneErrTip").hide();
    document.getElementById("phoneNo").addEventListener("blur", function (event) {
        const phoneNo = getI("phoneNo");
        if (phoneNo != undefined || phoneNo != null) {
            if (phoneNo.length > 0 && !/^1[3456789]\d{9}$/.test(phoneNo)) {
                phoneValidate = false;
                $("#phoneErrTip").show();
                return;
            }
        }
        phoneValidate = true;
        $("#phoneErrTip").hide();
    });

    //身份证号码校验
    $("#certNoErrTip").hide();
    document.getElementById("certNo").addEventListener("blur", function (event) {
        const certNo = getI("certNo");
        if (certNo != undefined || certNo != null) {
            if (certNo.length > 0 && !/^\d{17}[\dxX]$/.test(certNo)) {
                certValidate = false;
                $("#certNoErrTip").show();
                return;
            }
        }
        certValidate = true;
        $("#certNoErrTip").hide();
    });

    //姓名校验
    document.getElementById("name").addEventListener("blur", function (event) {
        const name = getI("name");
        var pattern = new RegExp("[`!@#$%^&*()_+~！￥（）—<>…《》/；;\":、\']");
        if (name != undefined || name != null) {
            if (name.length > 0 && pattern.test(name)) {
                nameValidate = false;
                showTip("请输入正确格式的姓名", "error", 4000, 10);
                return;
            }
        }
        nameValidate = true;
    });

    /**
     * 表单提交
     */
    document.querySelector("form").addEventListener('submit', function (event) {
        event.preventDefault(); // 阻止表单默认的提交行为
        let operStat = getS("operStat");
        if ('01' == operStat || '02' == operStat) {
            //发送短信验证码
            sendMsg();
        } else if ('04' == operStat) {
            confmSeq();
        } else {
            if(phoneValidate && certValidate){
                save();
            }else {
                showTip("请确保输入项已正确填写", "error", 1000, 10);
            }
        }
    });

    /**
     * 返回按钮
     */
    $("#cancelBtn").click(function () {
        $("#modifyModal").css("display", "none");
        $("#modifyModal").modal('hide');
        setI('vrfyNo', '');
    })

    /**
     * 返回按钮
     */
    $("#cancelBtn1").click(function () {
        $("#errAddModal").css("display", "none");
        $("#errAddModal").modal('hide');
        setI('vrfyNo', '');
    })

    /*返回按钮*/
    $('#back').click(function () {
        back();
    });

    //差错补录缴费明细页面确认
    $('#confirm').click(function () {
         $("#errAddModal").css("display", "none");
         $("#errAddModal").modal('hide');
        sendMsg();
    });

});

//返回列表查询
function back() {
    parent.window.$('a[href^=\'#tab_list\']').click();
}

/**
 * 发送短信
 */
function sendMsg() {
    $("#modifyModal").css("display", "block");
    $("#modifyModal").modal('show');
    var data = {};
    data.busiNo = getI('busiNo');
    data.busiName = getI('busiName');
    sendPostOfAjax(ctx + '/offline/data/sendMsg', data, false, true, (data) => {
        showContent('发送短信验证码成功');
        setI('vrfyNoCrtId', data.data.vrfy_NO_CRT_ID);
    })
}


//差错补录
function confmSeq() {
    console.info("开始执行差错补录");
    var data = {};
    data.busiNo = getI('busiNo');
    data.platSeq = getI('posSeq');
    //上送platDate不为空
    data.platDate = 'platDate';
    data.msgClob = '04';
    sendPostOfAjax1(ctx + '/offline/data/beforeRefund', data, false, true, (data) => {
        // showContent('查询补录缴费明细成功!');
        let retMSG = data.data.ret_MSG;
        $('#payDtlInfo').html(retMSG);
        $("#errAddModal").css("display", "block");
        $("#errAddModal").modal('show');
    })
}
function sendPostOfAjax1(url, data, isRefresh, tableId, successMethod, errorMthod) {
    let result;
    $.ajax({
        url: url,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        dataType: 'json',
        async: false,
        success: function (data, textStatus) {
            if (data && data.returnCode && '0000' == data.returnCode) {
                if (isRefresh == undefined ? true : typeof isRefresh != 'boolean' ? true : isRefresh) {
                    $('#' + tableId ? tableId : 'smsIndexTable').bootstrapTable('refreshOptions', queryParams);
                }
                if (typeof data == 'string') {
                    data = JSON.parse(data);
                }
                result = data.data;
                if (successMethod) {
                    successMethod(result);
                }
                return;
            }
            showContent('操作失败:[' + data && data.message ? data.message : '' + ']', 'error');
            if (errorMthod) {
                errorMthod();
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            showContent('请求发生错误,未能成功获取响应结果', 'error');
            if (errorMthod) {
                errorMthod();
            }
        }
    });
    return result;
}

/**
 * 发送短信验证码
 */
$('#confirm').click(function () {
    $("#errAddModal").css("display", "none");
    $("#errAddModal").modal('hide');
    var data = {};
    data.busiNo = getI('busiNo');
    data.busiName = getI('busiName');
    data.phoneNo = getI('phoneNo');
    sendPostOfAjax(ctx + '/offline/data/sendMsg', data, false, true, (data) => {
        showContent('发送短信验证码成功');
        setI('vrfyNoCrtId', data.data.vrfy_NO_CRT_ID);
    })
})

/**
 * 保存
 */
function save() {
    var data = $("#from").serializeObject();
    data.operTp = '2';
    if(getS('operStat') == '04'){
        data.shortRmrk = getI('posSeq');
    }
    console.info(data);
    sendPostOfAjax(ctx + '/offline/data/dtl_modify', data, false, true, () => {
        //添加成功
        parent.window.$('a[href^=\'#tab_list\']').click();
    });
}

