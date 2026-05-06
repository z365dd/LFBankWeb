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
    // //给busiMap赋值 00-联网缴费
    // busiMap = busiMap('00');
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
    const url = ctx + '/merAccount/data/offList';
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
    //00-联网缴费 01-非联网缴费
    const busiInfo = busiSelect($("select[name='busiNo']"), '01');
    busiInfo.forEach(item => {
        busiM.set(item.busiNo, item.busiName);
    });


}

const tableColumns = () => {
    const formData = $from.serializeObject();
    var busiNo = formData.busiNo;
    console.info('当前状态为' + busiNo);
    let colunms = [
        {
            field: 'busi_NO', title: '业务编号', formatter: (value, row) => {
                const busNo = row.busi_NO;
                return getOrDefaltOfMap(busiMap, busNo);
            }
        },
        {
            field: 'busi_NAME', title: '业务名称', formatter: (value, row) => {
                const busiName = row.busi_NAME;
                return getOrDefaltOfMap(busiMap, busiName);
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
        {field: 'oth_AMT', title: '码牌金额'},
        {field: 'mual_AMT', title: '差错补录'},
        {field: 'str_DATE', title: '交易开始日期'},
        {field: 'end_DATE', title: '交易结束日期'}];
        if(busiNo == ''){
            let stat =  {
                field: 'stat', title: '清算状态', formatter: (value, row) => {
                    var stat = row.stat;
                    if(stat == '90'){
                        return '清算完成';
                    }
                    if(stat[1] == '0'){
                        return '清算处理中';
                    }
                    if(stat[1] == '2'){
                        return '清算异常';
                    }
                }
            };
            colunms.push(stat);
        }
    return colunms;
}

// const btnInfos = [{text: '详情', act: 'info'}];

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