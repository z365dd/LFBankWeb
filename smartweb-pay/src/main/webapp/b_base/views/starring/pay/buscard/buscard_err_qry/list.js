let $table;
let $from;
let payTypeMap = new Map();
let noErr;
let $querydate;
let errMap = new Map();

$(function () {
    //展示在iframe_list的iframe
    parent.window.$('#iframe_list').show();
    window.parent.document.getElementById('comDiv').innerHTML = '';
    $querydate = window.parent.document.getElementById('comQuery');
    //获取业务编号  00-联网缴费 01-非联网缴费
    busiSelect($("select[name='busiNo']"), '100');
    //获取表格
    $table = $('#errQueryTable');
    //获取输入参数
    $from = $('#from');
    payTypeMap = payTpMap();
    errMap = errStatMap();
    setI('startTime', getYesterday());
    setI('endTime', getCurrentday());

    if ($querydate.innerHTML.length > 0) {
        var queryData = JSON.parse($querydate.innerHTML);
        setI("startTime", queryData.startTime);
        setI("endTime", queryData.endTime);
        setI("busiNo", queryData.busiNo);
        setS("errStat", queryData.errStat);
    }
    initInstTable();
    chgHeight();
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
    //刷新表格
    $table.bootstrapTable('refresh');
    chgHeight();
}

const initInstTable = () => {
    //table动态指定请求地址
    const url = ctx + '/errQuery/data/list';
    //表格id和字段名映射
    const columns = tableColumns();
    //表格默认设置
    let config = SmartWeb.bootstrapTable.constructor('#errQueryTable', columns, url, queryParams);
    config = SmartWeb.bootstrapTable.extendConfig(config, {
        height: 500,
        pageSize: 10,
        responseHandler: res => {
            total = 0;
            rows = [];
            if (res.data != undefined && res.data != null) {
                const data = res.data;
                total = data.total;
                rows = data.data.list;
                if ((total == undefined && total == null) || total == 0 || rows == undefined || rows == null) {
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

const tableColumns = () => {
    return [
        {field: 'clr_DATE', title: '交易日期'},
        {field: 'busi_NAME', title: '业务名称'},
        {field: 'pay_NO', title: '应用标识号'},
        {field: 'acct', title: '账号'},
        {field: 'name', title: '账户名称'},
        {field: 'amt', title: '交易金额'},
        {field: 'orig_PLAT_SEQ', title: '交易流水'},
        {field: 'tran_TP', title: '交易类型', formatter(key, row) {
                const tranTp = row.tran_TP;
                if(tranTp == '01'){
                    return '激活';
                }
                if(tranTp == '02' ){
                    return '圈存';
                }
                if(tranTp == '03'){
                    return '退卡';
                }
            }
        },
        {field: 'err_DESC', title: '差错原因'},
        {
            field: 'stat', title: '差错状态', formatter(key, row) {
                const stat = row.stat;
                return getOrDefaltOfMap(errMap, stat);
            }
        },

        {field: 'action', title: '操作', formatter: action}
    ];
}

const btnInfos = [ {text: '处理', act: 'handle'}];

function action(value, row, index) {
    let btnhtml = '';
    btnInfos.forEach(btnInfo => {
        btnhtml += `<button class='unformatter lb1' style="display: ${btnInfo.act == 'handle' && row.stat == '01' ? 'none' : ''}" onclick="${btnInfo.act}('${Base64.encode(JSON.stringify(row))}')">${btnInfo.text}</button>`
    });
    return btnhtml;
}

function handle(row) {
    confirmx('是否已处理', function () {

        sendPostOfAjax(ctx + '/errQuery/data/manualHandle', JSON.parse(Base64.decode(row)), true, 'errQueryTable', () => {
            refreshTable();
        })

    })
}
