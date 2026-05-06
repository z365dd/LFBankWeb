let $table;
let $from;
let $querydate;
let busiM = new Map();
let tpMap = new Map();
let statMap = new Map();


$(function () {

    parent.window.$('#iframe_list').show();
    window.parent.document.getElementById('comDiv').innerHTML = '';
    $querydate = window.parent.document.getElementById('comQuery');
    $table = $('#totTable');
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
    const url = ctx + '/unio/data/tot_qry';
    const columns = tableColumns();
    let config = SmartWeb.bootstrapTable.constructor('#totTable', columns, url, queryParams);
    config = SmartWeb.bootstrapTable.extendConfig(config, {
        height: 550,
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
        {field: 'brch_NAME', title: '单位名称'},
        {field: 'cust_NAME', title: '应缴费笔数'},
        {field: 'cust_NAME', title: '应缴费金额'},
        {field: 'cust_NAME', title: '已缴费笔数'},
        {field: 'cust_NAME', title: '已缴费金额'},
        {field: 'cust_NAME', title: '未缴费笔数'},
        {field: 'cust_NAME', title: '未缴费金额'}
    ];
}


