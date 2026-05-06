let $from;
let busiM = new Map();
let rowData = {};


$(function () {
    parent.window.$('#iframe_info').show();
    $from = $('#from');
    var encodeData = window.parent.document.getElementById('comDiv').innerText;
    rowData = JSON.parse(encodeData);
    console.info(rowData);
    setI('busiNo', rowData.busi_NO);
    setI('busiName', rowData.busi_NAME);
    setI('platDate', rowData.plat_DATE);
    setI('platSeq', rowData.plat_SEQ);
    setI('projName', rowData.proj_NAME);
    setI('oweMonth', rowData.owe_MONTH);
    setI('name', rowData.name);
    setI('phoneNo', rowData.phone_NO);
    setI('totAmt', rowData.tot_AMT);
    setI('certNo', rowData.cert_NO);
    setI('major', rowData.major);
    setI('stuClass', rowData.stu_CLASS);
    setI('stuId', rowData.stu_ID);
    setI('dctAmt', rowData.dct_AMT);
    setI('feeAmt', rowData.fee_AMT);
    setI('lateFeeAmt', rowData.late_FEE_AMT);
    setS('operStat', rowData.oper_STAT);
    setI('prctlAmt', rowData.prctl_AMT);
    setI('strDate', rowData.str_DATE);
    setI('endDate', rowData.end_DATE);
    $('#refund').hide();
    //只有已缴费且正常缴费的才能够退费
    if (rowData.stat == '01' && rowData.oper_STAT == '00') {
        $('#refund').show();
    }

    //限制短信验证码输入最多10位
    var input = document.getElementById('vrfyNo');
    input.addEventListener('input', function (event) {
        if (this.value.length > 10) {
            input.value = input.value.substring(0, 10); // 截断超过限制的字符
        }
    });

    /*退费按钮*/
    $('#refund').click(function () {
        var data = {};
        data.busiNo = getI('busiNo');
        data.platDate = getI('platDate');
        data.platSeq = getI('platSeq');
        sendPostOfAjax1(ctx + '/offline/data/beforeRefund', data, false, true, (data) => {
            let retMSG = data.data.ret_MSG;
            // let split = retMSG.split('\n');
            $('#relateInfo').html(retMSG);
            $("#refundDtlModal").css("display", "block");
            $("#refundDtlModal").modal('show');
        })
    });


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
        $("#refundDtlModal").css("display", "none");
        $("#refundDtlModal").modal('hide');
        var data = {};
        data.busiNo = getI('busiNo');
        data.busiName = getI('busiName');
        data.phoneNo = getI('phoneNo');
        sendPostOfAjax(ctx + '/offline/data/sendMsg', data, false, true, (data) => {
            showContent('发送短信验证码成功');
            setI('vrfyNoCrtId', data.data.vrfy_NO_CRT_ID);
            $("#refundModal").css("display", "block");
            $("#refundModal").modal('show');
        })
    })

    /**
     * 短信验证码填入后退费操作
     */
    $('#refundMoney').click(function () {
        var data = rowData;
        data.busiNo = getI('busiNo');
        data.vrfyNoCrtId = getI('vrfyNoCrtId');
        data.vrfyNo = getI('vrfyNo');
        //退费 该接口包含短信验证功能
        refundConfm(data);
    });


    //短信验证码输入界面 返回按钮
    $("#cancelBtn").click(function () {
        //清空input中的文件
        var obj = document.getElementById("vrfyNo");
        obj.outerHTML = obj.outerHTML;
        $("#refundModal").css("display", "none");
        $("#refundModal").modal('hide');
    })


    //退费关联明细页面
    $("#cancelBtn1").click(function () {
        $("#refundDtlModal").css("display", "none");
        $("#refundDtlModal").modal('hide');
    })

    /*返回按钮*/
    $('#back').click(function () {
        back();
    });
});

function refundConfm(data) {
    showLoading();
    sendPostOfAjax(ctx + '/offline/data/refund', data, false, true, () => {
        back();
    })
}

//返回列表查询
function back() {
    parent.window.$('a[href^=\'#tab_list\']').click();
}




