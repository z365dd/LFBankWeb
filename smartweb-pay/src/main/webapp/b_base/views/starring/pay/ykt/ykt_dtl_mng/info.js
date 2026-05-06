$(document).ready(function () {
    /*把修改页面内容显示出来*/
    parent.window.$('#iframe_detail').show();
    // const url = new URL(window.location.href);
    var text = window.parent.document.getElementById('comDiv').innerText;
    var json = JSON.parse(text);
    var tranStarMap = tranStatSelect('', true);
    var autoDeductMap = autoDeductSelect('');
    chnlMap = chnlMap();
    var payTypeMap = payTpMap();
    const autoFlag = json.auto_FLG;


    setI('busiInfo', json.busi_NO === '' ? '' : json.busi_NO + '-' + json.busi_NAME);

    //如果是自动扣款 渠道为生活缴费平台 支付方式为批量扣款
    if (autoFlag == 1) {
        setI('chnlInfo', '生活缴费平台');
    } else {
        setI('chnlInfo', json.chnl_NO === '' ? '' : json.chnl_NO + '-' + chnlMap.get(json.chnl_NO));
    }
    setI('payNo', json.pay_NO);
    setI('payAcct', json.pay_ACCT);
    var date = json.plat_DATE.substr(0, 4) + '-' + json.plat_DATE.substr(4, 2) + '-' + json.plat_DATE.substr(6, 2);
    setI('payDate', date);
    setI('arrearsDates', json.owe_MONTH);
    setI('payType', autoFlag == '0' ? payTypeMap.get(json.pay_TP) : '批量扣款');
    setI('cstName', json.name);
    setI('addr', json.addr);
    setI('payAmt', json.tot_AMT);
    setI('realAmt', json.prctl_AMT);
    setI('discountAmt', json.dct_AMT);
    setI('feeAmt', json.fee_AMT);
    setI('payTime', json.req_TIME.substr(8, 6));
    var txStat = json.tran_STAT;
    setI('txStat', tranStarMap.has(txStat) ? tranStarMap.get(txStat) : txStat);
    setI('hostSeq', json.host_SEQ);
    setI('chnlSeq', autoFlag == '0' ? json.req_SEQ : json.plat_SEQ);
    setI('othSeq', autoFlag == '0' ? json.oth_SEQ : '');
    setI('acctBank', autoFlag == '0' ? json.open_CUST_BRCH : '');
    setI('bankDctAmt', json.amt);
    setI('mertDctAmt', accSub(json.dct_AMT, json.amt));


    // var autoDeduct = json.auto_FLAG;
    setI('autoDeduct', autoFlag == '0' ? '否' : '是');

    /*返回按钮*/
    $('#back').click(function () {
        back();
    });

});

function back() {
    parent.window.$('a[href^=\'#tab_list\']').click();
}

/*查询明细*/
function getDetail(busiName, cstNo, payAcct) {
    sendGetOfAjax(ctx + `/payDetails/data/info?busiName=${busiName}&cstNo=${cstNo}&payAcct=${payAcct}`, false);
}
