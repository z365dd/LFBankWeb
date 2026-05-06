let $table;
let $from;
let busiM = new Map();


$(function () {
    parent.window.$('#iframe_list').show();
    window.parent.document.getElementById('comDiv').innerHTML = '';
    $table = $('#yktTable');
    $from = $('#from');
    //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务 20-一卡通
    busiNo();
    initInstTable();
    chgHeight();
    $('#qryBtn').click(function () {
        refreshTable();
    });
});

//获取当前登录用户的可操作业务
function busiNo() {
    //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务 20-一卡通
    const busiInfo = busiSelect($("select[name='busiNo']"), '20');
    if (busiInfo != undefined && busiInfo != null) {
        busiInfo.forEach(item => {
            busiM.set(item.busiNo, item.busiName);
        });
    }
    // setI("busiName", busiM.get(getS("BUSI_NO")));
}

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
    const url = ctx + '/ykt/data/para_list';
    const columns = tableColumns();
    let config = SmartWeb.bootstrapTable.constructor('#yktTable', columns, url, queryParams);
    config = SmartWeb.bootstrapTable.extendConfig(config, {
        height: 500,
        pageSize: 10,
        responseHandler: res => {
            total = 0;
            rows = [];
            if (res.data != undefined && res.data != null) {
                const data = res.data;
                total = data.total;
                rows = data.data;
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

        {field: 'busi_NO', title: '业务编号'},
        {field: 'busi_NAME', title: '业务名称'},
        {field: 'tran_AMT', title: '单笔限额'},
        {field: 'day_AMT', title: '日累计限额'},
        {field: 'action', title: '操作', formatter: action}

    ];
}


// const btnInfos = [{text: '详情', act: 'info'},{text: '修改', act: 'update'}];
const btnInfos = [{text: '修改', act: 'update'}];

function action(value, row, index) {
    let btnhtml = '';
    btnInfos.forEach(btnInfo => {
        btnhtml += `<button class='unformatter lb1' type="button" onclick="${btnInfo.act}('${Base64.encode(JSON.stringify(row))}' )">${btnInfo.text}</button>`
    });
    return btnhtml;
}


function update(row) {
    window.parent.document.getElementById('comDiv').innerHTML = Base64.decode(row);
    parent.window.$('a[href^=\'#tab_update\']').attr('url', ctx + `/ykt_para_mng/page/update`);
    parent.window.$('a[href^=\'#tab_update\']').click();
}
