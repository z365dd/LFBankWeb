let $table;
let $from;
let busiM = new Map();


$(function () {

    parent.window.$('#iframe_list').show();
    //列表数据展示
    $table = $('#tmplTable');
    $from = $('#from');
    busiNo();
    initInstTable();
    chgHeight();
    if (busiM == undefined || busiM.size==0) {
        busiNo();
    }
    $("#busiNo").on("change", function (e) {
        setI('busiName', busiM.get(getS('busiNo')));
        if (busiM == undefined || busiM.size==0) {
            busiNo();
        }
    });
    $('#qryBtn').click(function () {
        refreshTable();
    });


    $('#switch').click(function () {
        var left = $('#switch').css('left');
        left = parseInt(left);
        console.info(left);
        if (left == 0) {
            $(this).css('left', '30px'),
                $(this).css('background-color', '#FF0000'),
                $('.Ellipse1').css('background-color', '#FF0000');
        } else {
            $(this).css('left', '0px'),
                $(this).css('background-color', '#fff'),
                $('.Ellipse1').css('background-color', '#ccc');
        }
    });

});


//获取当前登录用户的可操作业务
function busiNo() {
    //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务
    const busiInfo = busiSelect($("select[name='busiNo']"), '01');
    if (busiInfo != undefined && busiInfo != null) {
        busiInfo.forEach(item => {
            busiM.set(item.busiNo, item.busiName);
        });
    }
    setI('busiName', busiM.get(getS('busiNo')));
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
    const url = ctx + '/offline/data/getTmplField';
    const columns = tableColumns();
    let config = SmartWeb.bootstrapTable.constructor('#tmplTable', columns, url, queryParams);
    config = SmartWeb.bootstrapTable.extendConfig(config, {
        height: 400,
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
        {field: 'ser', title: '序号'},
        {field: 'key_NO', title: '字段名称'},
        {field: 'key_DESC', title: '中文描述'},
        {field: 'excel_STAT', title: '模板下载启用状态', formatter: action},
        {field: 'query_STAT', title: '明细查询启用状态', formatter: action1}

    ];
}

// const btnInfos = [{text: '启用', act: 'importDtl'},{text: '禁用', act: 'importAgain'}];

function action(value, row) {
    var stat = row.excel_STAT == 'Y' ? '启用' : '停用';
    let btnhtml = '';
    btnhtml = `<button class='unformatter lb1' style="width: 90px;color:${row.excel_STAT == 'Y' ? 'green' : 'red'};font-weight: bold" onclick="dtlModify('${Base64.encode(JSON.stringify(row))}',value)">${stat}</button>`
    // btnhtml = `<div class="Ell ipse1"><div id="switch" class="circle1"></div></div>`
    return btnhtml;
}

function action1(value, row) {
    var stat = row.query_STAT == 'Y' ? '启用' : '停用';
    let btnhtml1 = '';
    btnhtml1 = `<button class='unformatter lb1' style="width: 90px;color:${row.query_STAT == 'Y' ? 'green' : 'red'};font-weight: bold" onclick="dtlModify1('${Base64.encode(JSON.stringify(row))}',value)">${stat}</button>`
    return btnhtml1;
}

function dtlModify(row, value) {

    var decode = JSON.parse(Base64.decode(row));
    var data = {};
    data.busiNo = decode.busi_NO;
    data.keyNo = decode.key_NO;
    data.excelStat = decode.excel_STAT == 'Y' ? 'N' : 'Y';
    data.queryStat = decode.query_STAT;
    data.operTp = '2';
    if (decode.key_NO == 'TOT_AMT' || decode.key_NO == 'SUB_SER') {
        showContent('该项为必选项,不可停用!', 'error');
        return;
    }
    sendPostOfAjax(ctx + '/offline/data/tmpl_modify', data, false, true, () => {
        showContent('状态修改成功!');
        refreshTable();
    })
}

function dtlModify1(row, value) {
    var decode = JSON.parse(Base64.decode(row));
    console.info(decode);
    var data = {};
    data.busiNo = decode.busi_NO;
    data.keyNo = decode.key_NO;
    data.queryStat = decode.query_STAT == 'Y' ? 'N' : 'Y';
    data.excelStat = decode.excel_STAT;
    //1-新增  2-修改
    data.operTp = '2';
    if (decode.key_NO == 'TOT_AMT' || decode.key_NO == 'SUB_SER') {
        showContent('该项为必选项,不可停用!', 'error');
        return;
    }
    sendPostOfAjax(ctx + '/offline/data/tmpl_modify', data, false, true, () => {
        showContent('状态修改成功!');
        refreshTable();
    })
}
