let $table;
let $from;
let $querydate;
let statM = new Map();
let apprStatM = new Map();

/**
 * 用户状态
 * @returns {Map<any, any>}
 */
function statMap() {
    var map = new Map();
    map.set('1', '在职');
    map.set('2', '离职');
    map.set('3', '退休');
    return map;
}

/**
 * 审批状态
 * @returns {Map<any, any>}
 */
function apprStatMap() {
    var map = new Map();
    map.set('0', '待审批');
    map.set('1', '已审批');
    return map;
}


$(function () {

    parent.window.$('#iframe_list').show();
    window.parent.document.getElementById('comDiv').innerHTML = '';
    $querydate = window.parent.document.getElementById('comQuery');
    $table = $('#apprUserTable');
    $from = $('#from');

    //用户状态
    statM = statMap();
    //审核状态
    apprStatM = apprStatMap();


    initInstTable();
    chgHeight();


    $('#qryBtn').click(function () {
        initInstTable();
    });

    //进入人员列表
    $('#enterBtn').click(function () {
        parent.window.$('a[href^=\'#tab_list\']').attr('url', ctx + `/union_user/page/list`);
        parent.window.$('a[href^=\'#tab_list\']').click();    });

});

function chgHeight() {
    const Height = $(document.body).height();
    $(window.parent.document).find('#tab_list').find('iframe').height(Height + 80);
}


function queryParams(params) {

    let formData = $from.serializeObject();
    //仅查询需要审核的数据
    formData.validFlg = '0';
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
    const url = ctx + '/union/data/user_list';
    const columns = tableColumns();
    let config = SmartWeb.bootstrapTable.constructor('#apprUserTable', columns, url, queryParams);
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
        {field: 'busi_NAME', title: '单位名称'},
        {field: 'name', title: '员工姓名'},
        {field: 'phone_NO', title: '手机号码'},
        {field: 'cert_NO', title: '证件号码'},
        {
            field: 'stat', title: '状态', formatter: (value, row) => {
                const stat = row.stat;
                return getOrDefaltOfMap(statM, stat);
            }
        },
        {field: 'action', title: '操作', formatter: action}
    ];
}


function action(value, row) {
    let btnhtml = '';
    btnhtml += `<button class='unformatter lb1' style="width:72px;" onclick="userApprPass('${Base64.encode(JSON.stringify(row))}')">审批通过</button>`
    btnhtml += `<button class='unformatter lb1' style="width:72px;" onclick="userApprRefuse('${Base64.encode(JSON.stringify(row))}')">审批驳回</button>`
    return btnhtml;
}

/**
 * 审批通过
 * @param row
 */
function userApprPass(row,) {
    var decodeRow = JSON.parse(Base64.decode(row));
    let data = {};

    data.operTp = '2';
    //审批状态 为1-通过
    data.validFlg = '1';
    //用户状态为1-在职
    data.stat = '1';
    data.busiNo = decodeRow.busi_NO;
    data.certNo = decodeRow.cert_NO;
    sendPostOfAjax(ctx + '/union/data/user_modify', data);
    refreshTable();
}


/**
 * 审批驳回
 * @param row
 */
function userApprRefuse(row) {
    var decodeRow = JSON.parse(Base64.decode(row));
    let data = {};

    //如果审批驳回 直接删除该数据
    data.operTp = '3';
    data.busiNo = decodeRow.busi_NO;
    data.certNo = decodeRow.cert_NO;
    sendPostOfAjax(ctx + '/union/data/user_modify', data);
    refreshTable();
}













