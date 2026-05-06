let $table;
let $from;
let $querydate;
let busiM = new Map();
let tpMap = new Map();
let statMap = new Map();
let userId;
let brchId;




$(function () {

    parent.window.$('#iframe_list').show();
    window.parent.document.getElementById('comDiv').innerHTML = '';
    $querydate = window.parent.document.getElementById('comQuery');
    $table = $('#paraTable');
    $from = $('#from');

    initInstTable();
    chgHeight();


    $('#qryBtn').click(function () {
        initInstTable();
    });

});

function chgHeight() {
    const Height = $(document.body).height();
    $(window.parent.document).find('#tab_list').find('iframe').height(Height + 80);
}


function queryParams(params) {

    let formData = $from.serializeObject();
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
    const url = ctx + '/union/data/para_list';
    const columns = tableColumns();
    let config = SmartWeb.bootstrapTable.constructor('#paraTable', columns, url, queryParams);
    config = SmartWeb.bootstrapTable.extendConfig(config, {
        height: 550,
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
        {field: 'year', title: '年度'},
        {field: 'str_DATE', title: '缴费开始日期'},
        {field: 'end_DATE', title: '缴费截止日期'},
        {field: 'amt', title: '缴费金额'},
        {field: 'action', title: '操作', formatter: action}
    ];
}

function action(value, row) {
    let btnhtml = '';
    btnhtml += `<button class='unformatter lb1' onclick="paraModify('${Base64.encode(JSON.stringify(row))}',false)">修改</button>`
    btnhtml += `<button class='unformatter lb1' onclick="paraDelete('${Base64.encode(JSON.stringify(row))}',false)">删除</button>`

    return btnhtml;
}

//修改
function paraModify(row) {
    window.parent.document.getElementById('comDiv').innerHTML = Base64.decode(row);
    //放置此次查询条件 新增页面返回时使用
    $querydate.innerHTML = JSON.stringify($from.serializeObject());
    parent.window.$('a[href^=\'#tab_update\']').attr('url', ctx + `/union_para/page/update`);
    parent.window.$('a[href^=\'#tab_update\']').click();
}


//删除参数
function paraDelete(row) {
    const data = JSON.parse(Base64.decode(row));
    const req = {};
    req.operTp = '3';
    req.year = data.year;
    top.$.jBox.confirm("是否要删除该缴费参数?", "系统提示", function (v, h, f) {
        if (v == "ok") {
            sendPostOfAjax(ctx + '/union/data/para_modify', data, false, true, () => {
                showTip("删除成功", "success", 1000, 10);
                refreshTable();
            })
        }
    });
}












