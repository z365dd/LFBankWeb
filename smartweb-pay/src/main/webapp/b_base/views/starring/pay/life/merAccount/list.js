let $table;
let $from;
let $querydate;
$(function () {
    parent.window.$('#iframe_list').show();
    window.parent.document.getElementById('comDiv').innerHTML = '';
    $querydate = window.parent.document.getElementById('comQuery');
    if (document.getElementById("busiNo")) {
        //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务
        const busiInfo = busiSelectByName($("select[name='busiNo']"), '00');
    }
    $table = $('#merAccountTable');
    $from = $('#from');
    const dataInfo = sendGetOfAjax(ctx + '/comQuery/data/getBeforeDate?offset=-1', false);
    setI('endTime', dataInfo.offsetDate);
    //给busiMap赋值 00-联网缴费
    busiMap = busiMap('00');
    // busiNo();
    if ($querydate.innerHTML.length > 0) {
        var queryData = JSON.parse($querydate.innerHTML);
        setI('endTime', queryData.endTime);
    }
    initInstTable();
    chgHeight();

    $("#busiNo").on("change", function (e) {
        busiSelectByName($("select[name='busiNo']"), '00');
    });
    $('#qryBtn').click(function () {
        refreshTable();
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
            if (res.data != undefined && data.total > 0) {
                setI('sumNum', data.data.tot_NUM);
                setI('sumTranAmt', data.data.tot_AMT);
                setI('sumRealAmt', data.data.clr_AMT);
                setI('sumFeeAmt', data.data.fee_AMT);
                setI('sumBankSubsidy', data.data.dct_BANK_AMT);
                setI('sumMerSubsidy', data.data.dct_MERT_AMT);
                if (data.data.bat_AMT == undefined || data.data.bat_AMT == null) {
                    setI('sumBatAmt', 0);
                } else {
                    setI('sumBatAmt', data.data.bat_AMT);
                }
                total = data.total;
                rows = data.data.list;
                if ((total == undefined && total == null) || total == 0 || rows == undefined || rows == null) {
                    total = 0;
                    rows = [];
                }
            } else {
                setI('sumNum', 0);
                setI('sumTranAmt', 0);
                setI('sumRealAmt', 0);
                setI('sumFeeAmt', 0);
                setI('sumBankSubsidy', 0);
                setI('sumMerSubsidy', 0);
                setI('sumBatAmt', 0);
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
    if (busiM == undefined || busiM.size==0) {
        //00-联网缴费
        const busiInfo = busiSelectByName($("select[name='busiNo']"), '00');
        busiInfo.forEach(item => {
            busiM.set(item.busiNo, item.busiName);
        });
    }
}

const tableColumns = () => {
    return [
        {
            field: 'busi_NAME', title: '业务名称', formatter: (value, row) => {
                const busNo = row.busi_NO;
                return getOrDefaltOfMap(busiMap, busNo);
            }
        },
        // {
        //     field: 'chnlName', title: '渠道名称', formatter: (value, row) => {
        //         console.info("渠道名称value:", value);
        //         return chnlM.get(row.chnlNo);
        //     }
        // },
        {field: 'clr_DATE', title: '入账日期'},
        {field: 'tot_NUM', title: '缴费笔数'},
        {field: 'tot_AMT', title: '缴费金额'},
        {field: 'clr_AMT', title: '清算金额'},
        {field: 'dct_MERT_AMT', title: '商户补贴'},
        {field: 'dct_BANK_AMT', title: '银行补贴'},
        {field: 'fee_AMT', title: '手续费'},
        {
            field: 'bat_AMT', title: '批量金额', formatter: (value, row) => {
                if (row.bat_AMT == undefined || row.bat_AMT == null) {
                    return 0
                } else {
                    return row.bat_AMT;
                }
            }
        },
        {field: 'action', title: '入账明细', formatter: action}
    ];
}

const btnInfos = [{text: '详情', act: 'info'}];

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
    console.info("打印时间", $from.serializeObject());
    parent.window.$('a[href^=\'#tab_detail\']').attr('url', ctx + `/merAccount/page/info`);
    parent.window.$('a[href^=\'#tab_detail\']').click();
}
