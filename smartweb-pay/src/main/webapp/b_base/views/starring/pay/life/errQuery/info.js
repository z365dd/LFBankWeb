let $table;
let $from;
let payTypeMap = new Map();
let channelMap = new Map();
let errMap = new Map();


$(document).ready(function () {
    /*把修改页面内容显示出来*/
    parent.window.$('#iframe_detail').show();

    $table = $('#errInfoTable');
    $from = $('#from');
    var text = window.parent.document.getElementById('comDiv').innerText;
    var json = JSON.parse(text);
    const errDesc = json.err_DESC;
    const errStat = json.stat;
    payTypeMap = payTpMap();
    channelMap = chnlMap();
    errMap = errStatMap();
    const url = new URL(window.location.href);
    const busiNo = url.searchParams.get('busiNo');
    const origPlatDate = url.searchParams.get('origPlatDate');
    const origPlatSeq = url.searchParams.get('origPlatSeq');
    setI('busiNo', busiNo);
    setI('origPlatDate', origPlatDate);
    setI('origPlatSeq', origPlatSeq);
    //下方表格内容
    initInstTable();
    // document.getElementById("body").style.height = 900 + "px";
    setI('desc', errDesc);
    setI('stat', getOrDefaltOfMap(errMap, errStat));
    /*返回按钮*/
    $('#back').click(function () {
        back();
    });

});


// function chgHeight() {
//     const Height = $(document.body).height();
//     $(window.parent.document).find('#tab_detail').find('iframe').height(Height + 20);
// }

function back() {
    parent.window.$('a[href^=\'#tab_list\']').click();
}

/**
 * 表格内容初始化
 */
const initInstTable = () => {
    // const url = ctx + `/errQuery/data/info?busiNo=${busiNo}&origPlatDate=${origPlatDate}&origPlatSeq=${origPlatSeq}`;
    const url = ctx + '/errQuery/data/info';
    //主列表
    const columns = tableColumns();
    let config = SmartWeb.bootstrapTable.constructor('#errInfoTable', columns, url, queryParams);
    config = SmartWeb.bootstrapTable.extendConfig(config, {
        height: 10,
        pageSize: 10,
        responseHandler: res => {
            total = 0;
            rows = [];
            if (res.data != undefined && res.data != null) {
                const data = res.data;
                console.info("返回数据", data);
                setI('busiInfo', data.busi_NAME);
                setI('chnlInfo', getOrDefaltOfMap(channelMap, data.chnl_NO));
                setI('payNo', data.pay_NO);
                setI('payTime', data.req_TIME);
                setI('seq', data.plat_SEQ);
                setI('payType', getOrDefaltOfMap(payTypeMap, data.pay_TP));
                setI('acctBank', data.open_CUST_BRCH);
                setI('payAcct', data.pay_ACCT);
                setI('cstName', data.name);
                setI('addr', data.addr);
                setI('payAmt', data.tot_AMT);
                setI('realAmt', data.prctl_AMT);
                setI('discountAmt', data.dct_AMT);
                setI('feeAmt', data.fee_AMT);
                setI('errDesc', data.errDesc);
                setI('hostSeq', data.host_SEQ);
                setI('chnlSeq', data.req_SEQ);
                setI('othSeq', data.oth_SEQ);
                if (data.dct_FLG == 'Y') {
                    if (data.dct_LIST == undefined || data.dct_LIST == null || (data.dct_LIST.length == undefined && data.dct_LIST.length == null) || data.dct_LIST.length == 0) {
                        total = 0;
                        rows = [];
                    } else {
                        rows = data.dct_LIST;
                        total = data.dct_LIST.length;
                    }
                } else {
                    total = 0;
                    rows = [];
                }

            }
            return {
                total: total,
                rows: rows
            };
        }
    });
    SmartWeb.bootstrapTable.init(config);
}


function queryParams(params) {
    const formData = $from.serializeObject();
    return {
        ...formData,
        limit: params.limit,
        start: getPage(params)
    };
}

function getPage(params) {
    if (!isNaN(params.offset) || !isNaN(params.limit)) {
        return params.offset / params.limit + 1;
    }
}

const tableColumns = () => {
    return [
        {field: 'dct_ID', title: '优惠编号'},
        {field: 'dct_NAME', title: '优惠名称'},
        {
            field: 'dct_TP', title: '优惠类型', formatter: (value, row) => {
                const dctTp = row.dct_TP;
                if ('10' == dctTp) {
                    return '商户支付支付优惠活动';
                }
                if ('11' == dctTp) {
                    return '本行卡支付优惠活动';
                }
                if ('20' == dctTp) {
                    return '优惠券';
                }
                if ('30' == dctTp) {
                    return '通用体验金';
                }
                if ('40' == dctTp) {
                    return '专属体验金';
                }

            }
        },
        {
            field: 'dct_SUB_TP', title: '优惠子类型', formatter: (value, row) => {
                const dctTp = row.dct_TP;
                const dctSubTp = row.dct_SUB_TP;
                if ('10' == dctTp && '91' == dctSubTp) {
                    return '商户支付优惠';
                }
                if ('11' == dctTp && '92' == dctSubTp) {
                    return '本行卡支付优惠';
                }
                if ('20' == dctTp && '42' == dctSubTp) {
                    return '折扣券';
                }
                if ('20' == dctTp && '43' == dctSubTp) {
                    return '满减券';
                }
                if ('30' == dctTp && '1' == dctSubTp) {
                    return '通用体验金';
                }
                if ('40' == dctTp && '2' == dctSubTp) {
                    return '专属体验金';
                }

            }
        },
        // {field: 'dct_QTA', title: '优惠额度'},
        {field: 'dct_AMT', title: '优惠金额'},
        {field: 'dct_BANK_AMT', title: '本行承担金额'},
        {field: 'dct_MERT_AMT', title: '商户补贴金额'},
        {field: 'dct_FEE_TP', title: '手续费优惠方式'},
        {field: 'dct_BANK_PART', title: '本行占百分比'},
        {field: 'dct_MERT_PART', title: '商户占百分比'},
        {field: 'dct_DESC', title: '优惠描述'},
        {field: 'dct_BANK_ACCT', title: '机构承担核销账号'},
        {field: 'dct_BANK_BRCH', title: '银行承担机构号'},
        {field: 'dct_MERT_ACCT', title: '商户内部账号'},
        {field: 'dct_BANK_ACCT_NAME', title: '银行内部账号名称'},
        {field: 'dct_MERT_BRCH', title: '商户开户机构'},
        {field: 'dct_MERT_BRCH_NAME', title: '商户开户机构名称'},
        {
            field: 'dct_SHARE_TP', title: '分摊方式', formatter: (value, row) => {
                const dctShareTp = row.dct_SHARE_TP;
                if ('1' == dctShareTp) {
                    return '本行承担';
                }
                if ('2' == dctShareTp) {
                    return '商户承担';
                }
                if ('3' == dctShareTp) {
                    return '按比例分摊';
                }

            }
        },
    ];


}


/*查询明细*/
function getDetail(busiNo, origPlatDate, origPlatSeq) {

    const data = sendGetOfAjax(ctx + `/errQuery/data/info?busiNo=${busiNo}&origPlatDate=${origPlatDate}&origPlatSeq=${origPlatSeq}`, false);
    console.info(data)
    if (data != undefined && data != null) {

        setI('busiInfo', data.busi_NAME);
        setI('chnlInfo', getOrDefaltOfMap(channelMap, data.chnl_NO));
        setI('payNo', data.pay_NO);
        setI('payTime', data.req_TIME);
        setI('seq', data.plat_SEQ);
        setI('payType', getOrDefaltOfMap(payTypeMap, data.pay_TP));
        setI('acctBank', data.brch);
        setI('payAcct', data.pay_ACCT);
        setI('cstName', data.pay_ACCT_NAME);
        setI('addr', data.addr);
        setI('payAmt', data.amt);
        setI('realAmt', data.prctl_AMT);
        setI('discountAmt', data.dct_AMT);
        setI('feeAmt', data.fee_AMT);
        setI('errDesc', data.errDesc);
        setI('errStat', data.errStat);
        setI('hostSeq', data.req_HOST_SEQ);
        setI('chnlSeq', data.req_SEQ);
        setI('othSeq', data.oth_SEQ);
    }
}
