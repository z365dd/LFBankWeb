let $table;
let $from;
let $querydate;
let busiM = new Map();
let payTpM = new Map();
let chnlTpM = new Map();

$(function () {

    parent.window.$('#iframe_list').show();
    window.parent.document.getElementById('comDiv').innerHTML = '';
    $querydate = window.parent.document.getElementById('comQuery');
    $table = $('#dtlTable');
    $from = $('#from');

    //缴费方式
    payTpM = payTpMap();
    //缴费渠道
    chnlTpM = chnlMap();

    initInstTable();
    chgHeight();

    if (busiM == undefined && busiM.size == 0) {
        busiNo();
    }

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
    const url = ctx + '/offline/data/dtl_list';
    const columns = tableColumns();
    let config = SmartWeb.bootstrapTable.constructor('#dtlTable', columns, url, queryParams);
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
        {field: 'plat_DATE', title: '缴费日期'},
        {field: 'busi_NAME', title: '单位名称'},
        {field: 'name', title: '客户名称'},
        {field: 'amt', title: '缴费金额'},
        {field: 'cert_NO', title: '身份证号码'},
        {field: 'phone_NO', title: '手机号码'},
        {
            field: 'chnl_NO', title: '缴费渠道', formatter: (value, row) => {
                const chnlNo = row.chnl_NO;
                return getOrDefaltOfMap(chnlTpM, chnlNo);
            }
        },
        {
            field: 'pay_TP', title: '缴费方式', formatter: (value, row) => {
                const payTp = row.pay_TP;
                return getOrDefaltOfMap(payTpM, payTp);
            }
        },
        {
            field: 'stat', title: '缴费状态', formatter: (value, row) => {
                const stat = row.stat;
                if('00' === stat){
                    return '待缴费';
                }
                if('00' === stat){
                    return '已缴费';
                }
            }
        },
        {field: 'action', title: '操作', formatter: action}
    ];
}


function action(value, row) {
    let btnhtml = '';
    btnhtml += `<button class='unformatter lb1' onclick="info('${Base64.encode(JSON.stringify(row))}')">详情</button>`
    return btnhtml;
}

/**
 * 跳转详情页面
 * @param row
 */
function info(row) {
    var decodeRow = JSON.parse(Base64.decode(row));
    //保存该数据  详情页展示
    window.parent.document.getElementById('comDiv').innerHTML = JSON.stringify(decodeRow);
    //放置此次查询条件 详情页返回时使用
    $querydate.innerHTML = JSON.stringify($from.serializeObject());
    parent.window.$('a[href^=\'#tab_info\']').attr('url', ctx + `/proj_mng/page/info`);
    parent.window.$('a[href^=\'#tab_info\']').click();
}

/**
 * 获取单位编号以及单位名称
 */
function busiNo() {
    //00-联网缴费  01-非联网 缴费缴费  02-工会 100-公交卡业务
    const busiInfo = busiSelect($("select[name='busiNo']"), '02');
    if (busiInfo != undefined && busiInfo != null) {
        busiInfo.forEach(item => {
            busiM.set(item.busiNo, item.busiName);
        });
    }
}









