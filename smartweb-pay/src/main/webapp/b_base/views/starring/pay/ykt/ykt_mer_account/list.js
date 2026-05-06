let $table;
let $from;
let $querydate;
let busiM = new Map();
$(function () {
    parent.window.$('#iframe_list').show();
    window.parent.document.getElementById('comDiv').innerHTML = '';
    $querydate = window.parent.document.getElementById('comQuery');
    $table = $('#merAccountTable');
    $from = $('#from');
    busiNo();
    setI('endTime', getCurrentday());
    if ($querydate.innerHTML.length > 0) {
        var queryData = JSON.parse($querydate.innerHTML);
        setI('endTime', queryData.endTime);
    }
    initInstTable();
    chgHeight();

    $("#busiNo").on("change", function (e) {
        if (!busiM == undefined && !busiM.size == 0) {
            busiNo();
        }
    });
    $('#qryBtn').click(function () {
        refreshTable();
    });
    $('#export').click(function () {
        expExcel();
    });
});

function chgHeight() {
    const Height = $(document.body).height();
    $(window.parent.document).find('#tab_list').find('iframe').height(Height + 80);
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

const refreshTable = () => {
    const $preClick = $table.parent().parent().find('.page-pre');
    if ($preClick.siblings().length > 1) {
        $preClick.next().click();
    }
    $table.bootstrapTable('refresh');
    chgHeight();
}

const initInstTable = () => {
    const url = ctx + '/merAccount/data/list';
    //主列表
    const columns = tableColumns();
    let config = SmartWeb.bootstrapTable.constructor('#merAccountTable', columns, url, queryParams);
    config = SmartWeb.bootstrapTable.extendConfig(config, {
        height: 500,
        pageSize: 10,
        responseHandler: res => {
            total = 0;
            rows = [];
            const data = res.data;
            if (res.data != undefined && data.total > 0 && (data.data.list && data.data.list.length >0)) {
                // setI('sumNum', data.data.tot_NUM);
                // setI('sumTranAmt', data.data.tot_AMT);
                // setI('sumRealAmt', data.data.clr_AMT);
                // setI('sumFeeAmt', data.data.fee_AMT);
                // setI('sumBankSubsidy', data.data.dct_BANK_AMT);
                // setI('sumMerSubsidy', data.data.dct_MERT_AMT);
                // setI('sumMualAmt', data.data.mual_AMT);
                // setI('sumOthAmt', data.data.oth_AMT);
                // setI('sumRfndAmt', data.data.rfnd_AMT);
                // if (data.data.bat_AMT == undefined || data.data.bat_AMT == null) {
                //     setI('sumBatAmt', 0);
                // } else {
                //     setI('sumBatAmt', data.data.bat_AMT);
                // }
                total = data.total;
                rows = data.data.list;
                rows[0].str_DATE = data.data.str_DATE;
                rows[0].end_DATE = data.data.end_DATE;
                // rows[0].mual_AMT = data.data.mual_AMT;
                if ((total == undefined && total == null) || total == 0 || rows == undefined || rows == null) {
                    total = 0;
                    rows = [];
                }
            } else {
                // setI('sumNum', 0);
                // setI('sumTranAmt', 0);
                // setI('sumRealAmt', 0);
                // setI('sumFeeAmt', 0);
                // setI('sumBankSubsidy', 0);
                // setI('sumMerSubsidy', 0);
                // setI('sumBatAmt', 0);
                // setI('sumMualAmt', 0);
                // setI('sumOthAmt', 0);
                // setI('sumRfndAmt', 0);
                total = 0;
                rows = [];
            }
            return {
                total: total,
                rows: rows
            };
        }
    });
    SmartWeb.bootstrapTable.init(config);
}


function busiNo() {
    //00-联网缴费 01-非联网缴费 10-一卡通 20-一卡通
    const busiInfo = busiSelect($("select[name='busiNo']"), '20');
    if(null !== busiInfo){
        busiInfo.forEach(item => {
            busiM.set(item.busiNo, item.busiName);
        });
    }

}

const tableColumns = () => {
    return [
        {
            field: 'busi_NO', title: '业务编号', formatter: (value, row) => {
                const busNo = row.busi_NO;
                return getOrDefaltOfMap(busiMap, busNo);
            }
        },
        {field: 'clr_DATE', title: '入账日期'},
        {field: 'tot_NUM', title: '缴费笔数'},
        {field: 'tot_AMT', title: '缴费金额'},
        {field: 'rfnd_AMT', title: '退费金额'},
        {field: 'clr_AMT', title: '清算金额'},
        {field: 'dct_MERT_AMT', title: '商户补贴'},
        {field: 'dct_BANK_AMT', title: '银行补贴'},
        {field: 'fee_AMT', title: '手续费'},
        {field: 'oth_AMT', title: '差错金额'},
        {field: 'str_DATE', title: '交易开始日期'},
        {field: 'end_DATE', title: '交易结束日期'},
        // {field: 'action', title: '入账明细', formatter: action}
    ];
}


function action(value, row, index) {
    let btnhtml = '';
    btnInfos.forEach(btnInfo => {
        btnhtml += `<button class='unformatter lb1' onclick="${btnInfo.act}('${Base64.encode(JSON.stringify(row))}')">${btnInfo.text}</button>`
    });
    return btnhtml;
}

function info(row) {
    const data = Base64.decode(row);
    var jsonData = JSON.parse(data);
    const treeData = sendGetOfAjax1(ctx + `/merAccount/data/info?ser=${jsonData.ser}&clrDate=${jsonData.clr_DATE}&busiNo=${jsonData.busi_NO}`, false);
    const sendData = treeData.data.list;
    if (sendData == null || sendData == undefined) {

    }
    window.parent.document.getElementById('comDiv').innerHTML = JSON.stringify(sendData);
    window.parent.document.getElementById('comQuery').innerHTML = JSON.stringify($from.serializeObject());
    parent.window.$('a[href^=\'#tab_detail\']').attr('url', ctx + `/merAccount/page/info`);
    parent.window.$('a[href^=\'#tab_detail\']').click();
}

function expExcel() {
    const data = $from.serializeObject();
    data.start = 1;
    data.limit = 10;
    top.$.jBox.confirm("是否要导出当前清算日期的入账明细?", "系统提示", function (v, h, f) {
        if (v == "ok") {
            $("#from").attr("action", ctx + "/offline/data/expExcel");
            $("#from").submit();
        }
    });
}
