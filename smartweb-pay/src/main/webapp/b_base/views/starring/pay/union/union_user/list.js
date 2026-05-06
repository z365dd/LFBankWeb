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
    $table = $('#userTable');
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

});

function chgHeight() {
    const Height = $(document.body).height();
    $(window.parent.document).find('#tab_list').find('iframe').height(Height + 80);
}


function queryParams(params) {

    let formData = $from.serializeObject();
    //仅查询已通过审核的数据
    formData.validFlg = '1';
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
    let config = SmartWeb.bootstrapTable.constructor('#userTable', columns, url, queryParams);
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
/*
    btnhtml += `<button class='unformatter lb1' onclick="info('${Base64.encode(JSON.stringify(row))}')">详情</button>`
*/
    btnhtml += `<button class='unformatter lb1' onclick="userModify('${Base64.encode(JSON.stringify(row))}' )">修改</button>`
    return btnhtml;
}

/**
 * 跳转详情页面
 * @param row
 */
function info(row) {
    var decodeRow = JSON.parse(Base64.decode(row));
    decodeRow.isChk = isChk;
    //保存该数据  详情页展示
    window.parent.document.getElementById('comDiv').innerHTML = JSON.stringify(decodeRow);
    //放置此次查询条件 详情页返回时使用
    $querydate.innerHTML = JSON.stringify($from.serializeObject());
    parent.window.$('a[href^=\'#tab_info\']').attr('url', ctx + `/union_user/page/info`);
    parent.window.$('a[href^=\'#tab_info\']').click();
}


//修改
function userModify(row) {
    window.parent.document.getElementById('comDiv').innerHTML = Base64.decode(row);
    //放置此次查询条件 新增页面返回时使用
    $querydate.innerHTML = JSON.stringify($from.serializeObject());
    parent.window.$('a[href^=\'#tab_update\']').attr('url', ctx + `/union_user/page/update`);
    parent.window.$('a[href^=\'#tab_update\']').click();
}






