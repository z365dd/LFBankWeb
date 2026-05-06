let $table;
let $from;
let $querydate;
let busiM = new Map();
let projM = new Map();
let extraM = new Map();
let statMap = new Map();
let typeMap = new Map();


$(function () {

    parent.window.$('#iframe_list').show();
    window.parent.document.getElementById('comDiv').innerHTML = '';
    $querydate = window.parent.document.getElementById('comQuery');
    $table = $('#limitAmtTable');
    $from = $('#from');
    //选择框内容查询
    busiNo();
    initInstTable();
    chgHeight();
    $('#qryBtn').click(function () {
        initInstTable();
    });


});

function busiNo() {
    if (busiM == undefined || busiM.size==0) {
        const busiInfo = busiSelect($("select[name='busiNo']"), '');
        if (busiInfo != undefined && busiInfo != null) {
            busiInfo.forEach(item => {
                busiM.set(item.busiNo, item.busiName);
            });
        }
    }
}



function chgHeight() {
    const Height = $(document.body).height();
    $(window.parent.document).find('#tab_list').find('iframe').height(Height + 80);
}





function queryParams(params) {

    let formData = $from.serializeObject();
    formData.extraFields = getS('extraField') + ',$' + getI('extraField');
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
    const url = ctx + '/limitAmt/data/list';
    const columns = tableColumns();
    let config = SmartWeb.bootstrapTable.constructor('#limitAmtTable', columns, url, queryParams);
    config = SmartWeb.bootstrapTable.extendConfig(config, {
        height: 550,
        pageSize: 10,
        responseHandler: res => {
            total = 0;
            rows = [];
            if (res.data != undefined && res.data != null && res.data.total>0) {
                const data = res.data;
                console.info('内容为' + data);
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
        {field: 'lim_AMT', title: '单笔限额'},
        {field: 'day_AMT', title: '日累计限额'},
        // {field: 'month_AMT', title: '月累计限额'},
        // {field: 'year_AMT', title: '年累计限额'},
        // {field: 'day_NUM', title: '日累计笔数'},
        // {field: 'month_NUM', title: '月累计笔数'},
        // {field: 'year_NUM', title: '年累计笔数'},
        {field: 'action', title: '操作', formatter: action}

    ];
}

// const btnInfos = [{text: '修改', act: 'modify'}, {text: '删除', act: 'del'}];
const btnInfos = [{text: '修改', act: 'modify'}];

function action(value, row) {
    let btnhtml = '';
    btnInfos.forEach(btnInfo => {
        btnhtml += `<button class='unformatter lb1' stule="display:${row.stat == '00' && row.stat == '01' ? 'none' : ''} " onclick="${btnInfo.act}('${Base64.encode(JSON.stringify(row))}' )">${btnInfo.text}</button>`
    });
    return btnhtml;
}



//修改
function modify(row) {
    window.parent.document.getElementById('comDiv').innerHTML = Base64.decode(row);
    //放置此次查询条件 新增页面返回时使用
    // $querydate.innerHTML = JSON.stringify($from.serializeObject());
    parent.window.$('a[href^=\'#tab_update\']').attr('url', ctx + `/limitMng/page/modify`);
    parent.window.$('a[href^=\'#tab_update\']').click();
}

//删除功能
function del(row) {
    var decode = JSON.parse(Base64.decode(row));
    var data = {};
    //暂时这样用   busiNo没有回送
    data.busiNo = decode.busi_NO;
    data.operTp = '3'
    data.projName = decode.proj_NAME;
    data.subSer = decode.sub_SER;
    data.oweMonth = decode.owe_MONTH;
    top.$.jBox.confirm("是否要删除该账单明细?", "系统提示", function (v, h, f) {
        if (v == "ok") {
            sendDeReq(ctx + '/offline/data/dtl_modify', data, false, true, () => {
                showTip("删除成功", "success", 1000, 10);
                refreshTable();
            })
        }
    });

}





