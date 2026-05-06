let $table;
let $from;
let $querydate;
let busiM = new Map();
let brchTpM = new Map();


/**
 * 审批状态
 * @returns {Map<any, any>}
 */
function brchTpMap() {
    var map = new Map();
    map.set('1', ' 一级工会');
    map.set('2', '市直工会');
    return map;
}

$(function () {

    parent.window.$('#iframe_list').show();
    window.parent.document.getElementById('comDiv').innerHTML = '';
    $querydate = window.parent.document.getElementById('comQuery');
    $table = $('#instTable');
    $from = $('#from');

    //项目状态初始化
    brchTpM = brchTpMap();

    initInstTable();
    chgHeight();

    $(".lb2").click(function(){
        $(this).children("div").show();
    },function(){
        $(this).children("div").hide();
    });


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
    const url = ctx + '/union/data/inst_list';
    const columns = tableColumns();
    let config = SmartWeb.bootstrapTable.constructor('#instTable', columns, url, queryParams);
    config = SmartWeb.bootstrapTable.extendConfig(config, {
        height: 550,
        pageSize: 10,
        responseHandler: res => {
            total = 0;
            rows = [];
            if (res.data !== undefined) {
                const data = res.data;
                total = data.total;
                rows = data.data;
                if ((total === undefined) || total === 0 || rows === undefined) {
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
        {field: 'brch_NAME', title: '机构名称'},
        {
            field: 'brch_TP', title: '机构类型', formatter: (value, row) => {
                const brchTp = row.brch_TP;
                return getOrDefaltOfMap(brchTpM, brchTp);
            }
        },
        {field: 'addr', title: '所属区域'},
        {field: 'ctct_PER_NAME', title: '联系人'},
        {field: 'entr_TEL_NO', title: '联系电话'},
        {field: 'open_STAT', title: '是否可用'},
        {field: 'action', title: '操作', formatter: action}
    ];
}


function action(value, row,index) {
    let btnhtml = '';
    btnhtml += `<button class='unformatter lb1' onclick="info('${Base64.encode(JSON.stringify(row))}',false)">详情</button>`
    btnhtml += `<button class='unformatter lb1' onclick="instUpdate('${Base64.encode(JSON.stringify(row))}',false)">修改</button>`
    btnhtml += `<button class='unformatter lb1' onclick="instDelete('${Base64.encode(JSON.stringify(row))}',false)">删除</button>`

    return btnhtml;
}

/**
 * 跳转详情页面
 * @param row
 */
function info(row) {
    let decodeRow = JSON.parse(Base64.decode(row));
    //保存该数据  详情页展示
    window.parent.document.getElementById('comDiv').innerHTML = JSON.stringify(decodeRow);
    //放置此次查询条件 详情页返回时使用
    $querydate.innerHTML = JSON.stringify($from.serializeObject());
    parent.window.$('a[href^=\'#tab_info\']').attr('url', ctx + `/union_inst/page/info`);
    parent.window.$('a[href^=\'#tab_info\']').click();
}


//修改
function instUpdate(row) {
    let decodeRow = JSON.parse(Base64.decode(row));
    window.parent.document.getElementById('comDiv').innerHTML = Base64.decode(row);
    //放置此次查询条件 新增页面返回时使用
    $querydate.innerHTML = JSON.stringify($from.serializeObject());
    parent.window.$('a[href^=\'#tab_update\']').attr('url', ctx + `/union_inst/page/update`);
    parent.window.$('a[href^=\'#tab_update\']').click();
}


//删除
function instDelete(row) {
    let decodeRow = JSON.parse(Base64.decode(row));
    let data= {};
    data.brchNo = decodeRow.brch_NO;
    confirmx('要删除该工会以及所有下级工会吗', function () {

      sendPostOfAjax(ctx + '/union/data/inst_list',)
    });

}


