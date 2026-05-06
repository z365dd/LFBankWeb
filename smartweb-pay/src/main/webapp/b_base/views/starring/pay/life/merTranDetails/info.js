$(document).ready(function () {
    /*把修改页面内容显示出来*/
    parent.window.$('#iframe_detail').show();
    // const url = new URL(window.location.href);
    var text = window.parent.document.getElementById('comDiv').innerText;
    var json = JSON.parse(text);
    var tranStarMap = tranStatSelect('', true);
    var payTypeMap = payTpMap();
    setI('busiInfo', json.busi_NO == '' ? '' : json.busi_NO + '-' + json.busi_NAME);
    const autoFlag = json.auto_FLG;
    //缴费号
    setI('cstNo', json.pay_NO);
    setI('payAcct', payAcctHide(json.pay_ACCT));
    setI('cstName', json.name);
    setI('addr', json.addr);
    var date = json.plat_DATE.substr(0, 4) + '-' + json.plat_DATE.substr(4, 2) + '-' + json.plat_DATE.substr(6, 2);
    setI('payDate', date);
    setI('payTime', json.req_TIME.substr(8, 6));
    setI('arrearsDates', json.owe_MONTH);
    setI('payAmt', json.tot_AMT);
    setI('realAmt', json.prctl_AMT);
    setI('discountAmt', json.dct_AMT);
    setI('bankDctAmt', json.amt);
    setI('mertDctAmt', accSub(json.dct_AMT, json.amt));
    setI('feeAmt', json.fee_AMT);
    setI('payType', autoFlag == '0' ? payTypeMap.get(json.pay_TP) : '批量扣款');
    var refundStat = json.rfnd_STAT;
    if (refundStat == '00') {
        refundStat = '未退费';
        $('#refund').show();
    } else if (refundStat == '01') {
        refundStat = '全额退费';
        $('#refund').hide();
    }
    setI('refundStat', autoFlag == '0' ? refundStat : '未退费');
    var txStat = json.tran_STAT;
    setI('txStat', tranStarMap.has(txStat) ? tranStarMap.get(txStat) : txStat);

    /*返回按钮*/
    $('#back').click(function () {
        back();
    });
    /*返回按钮*/
    $('#refund').click(function () {
        refund();
    });

});

function back() {
    parent.window.$('a[href^=\'#tab_list\']').click();
}

function payAcctHide(val) {
    if (val == undefined || val == '') {
        return '';
    }
    if (val.length <= 10) {
        return val;
    }
    const size = val.length;
    const head = val.substr(0, 6);
    const tail = val.substr(size - 4, size);
    return head + '****' + tail;
}

function refund() {

    $("#handleModal").css("display", "block");
    $("#handleModal").modal('show');

    $("#cancelBtn").click(function () {
        window.parent.document.getElementById('comDiv').innerText = '';
        $("#handleModal").css("display", "none");
        $("#handleModal").modal('hide');
    })

    // sendGetOfAjax(ctx + '/comQuery/data/sendSms', false, res => {
    //     $("#handleModal").css("display", "block");
    //     $("#handleModal").modal('show');
    //     document.getElementById('phoneNoTipMsg').innerHTML = `短信验证码已发送到${res.phoneNo}`;
    //     // document.getElementById('key').innerHTML = `短信验证码已发送到${res.key}`;
    //     $("#requireBtn").click(function () {
    //         var code = getI('validateCode');
    //         if (strIsBlank(code)) {
    //             showTip("请填写验证码", 'error', 2000, 100);
    //             return;
    //         }
    //         var lineInfo = window.parent.document.getElementById('comDiv').innerText;
    //         var data = JSON.parse(lineInfo)
    //         data.validateCode = getI('validateCode');
    //         data.key = res.key;
    //         sendPostOfAjax(ctx + '/merTranDetails/data/refund', data, false, '', (res) => {
    //             showTip(res, 'success', 2000, 100);
    //             window.parent.document.getElementById('comDiv').innerText = '';
    //             $("#handleModal").css("display", "none");
    //             $("#handleModal").modal('hide');
    //             // refreshTable();
    //             back();
    //         })
    //     })
    //     $("#cancelBtn").click(function () {
    //         window.parent.document.getElementById('comDiv').innerText = '';
    //         $("#handleModal").css("display", "none");
    //         $("#handleModal").modal('hide');
    //     })
    // })
}

/*查询明细*/
function getDetail(busiName, cstNo, payAcct) {

    const data = sendGetOfAjax(ctx + `/payDetails/data/info?busiName=${busiName}&cstNo=${cstNo}&payAcct=${payAcct}`, false);
    /*
     if (data != undefined && data != null) {
         $('#smsTitle').val(data.smsTitle);
         $('#platDate').val(data.platDate);
         $('#platTime').val(data.platTime);
         $('#dtlNo').val(data.dtlNo);
         $('#msgCntt').val(data.msgCntt);
         $('#phoneNo').val(data.phoneNo);
         $('#chnlNo').val(data.chnlNo);
         $('#sndStat').val(data.sndStat);
         $('#sndStatDesc').val(data.sndStatDesc);
         $('#devNo').val(data.devNo);
         $('#retryNum').val(data.retryNum);
         $('#validTime').val(data.validTime);
         $('#sndTime').val(data.sndTime);
         $('#fstId').val(data.fstId);
         $('#tranKd').val(tpMap && tpMap != null && tpMap.has(data.tranKd) ? tpMap.get(data.tranKd) : `未知渠道[${data.tranKd}]`);
         $('#tpName').val(data.tpName);
         $('#creTime').val(data.creTime);
         $('#lastSndTime').val(data.lastSndTime);
         $('#fountainTextG').hide();
     }
    */
}
