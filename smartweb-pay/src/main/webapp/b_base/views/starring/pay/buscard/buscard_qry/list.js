let $table;
let $from;
let tranTpMap = new Map();
let statMap = new Map();
let confmStatMap = new Map();
let busiM = new Map();


$(function () {
    parent.window.$('#iframe_list').show();
    $table = $('#busListTable');
    $from = $('#from');
    //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务
    const busiInfo = busiSelect($("select[name='busiNo']"), '100');
    if (busiInfo != undefined || busiInfo != null) {
        busiInfo.forEach(item => {
            busiM.set(item.busiNo, item.busiName);
        });
    }
    busiNo();
    setI('strDate', getYesterday());
    setI('endDate', getCurrentday());
    buildTranTp($("select[name='tranTp']"));
    buildTranStat($("select[name='stat']"));
    buildConfmStat($("select[name='confmStat']"));
    initInstTable();
    chgHeight();
    $('#qryBtn').click(function () {
        refreshTable();
    });
    $('#printDtl').click(function () {
        top.$.jBox.confirm("是否要下载交易明细?", "系统提示", function (v, h, f) {
            if (v == "ok") {
                showLoading();
                $("#from").attr("action", ctx + "/bus/data/printDtl");
                $("#from").submit();
                //axios请求在产品中有loading页面的前处理和后处理 通过调用该方法实现loading页面的关闭
                printDtl();
            }
        })
    });
});
function busiNo() {
    if (busiM == undefined || busiM.size==0) {
        //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务
        const busiInfo = busiSelect($("select[name='busiNo']"), '100');
        busiInfo.forEach(item => {
            busiM.set(item.busiNo, item.busiName);
        });
    }
}


//状态
function buildTranStat(dom) {
    statMap.set('01', '成功');
    statMap.set('02', '失败');
    statMap.set('03', '超时');
    statMap.forEach((value, key) => {
        const optStr = `<option value = '${key}'>${key + '-' + value}</option>`;
        dom.append(optStr);
    });
    dom.multiselect('rebuild').multiselect('refresh');
}


//确认状态
function buildConfmStat(dom) {
    confmStatMap.set('00', '未确认');
    confmStatMap.set('10', '确认成功');
    confmStatMap.set('20', '确认失败');
    confmStatMap.forEach((value, key) => {
        const optStr = `<option value = '${key}'>${key + '-' + value}</option>`;
        dom.append(optStr);
    });
    dom.multiselect('rebuild').multiselect('refresh');
}

//交易类型
function buildTranTp(dom) {
    tranTpMap.set('01', '激活');
    tranTpMap.set('02', '圈存');
    tranTpMap.set('03', '退卡');
    tranTpMap.forEach((value, key) => {
        const optStr = `<option value = '${key}'>${key + '-' + value}</option>`;
        dom.append(optStr);
    });
    dom.multiselect('rebuild').multiselect('refresh');
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
    const url = ctx + '/bus/data/busList';
    const columns = tableColumns();
    let config = SmartWeb.bootstrapTable.constructor('#busListTable', columns, url, queryParams);
    config = SmartWeb.bootstrapTable.extendConfig(config, {
        height: 350,
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
        {field: 'plat_DATE', title: '交易日期'},
        {field: 'app_ID', title: '应用标识号'},
        {field: 'acct', title: '账号'},
        {field: 'acct_NAME', title: '账号名称'},
        {field: 'tran_AMT', title: '交易金额'},
        {
            field: 'tran_TP', title: '交易类型', formatter: (value, row) => {
                const tp = row.tran_TP;
                return getOrDefaltOfMap(tranTpMap, tp);
            }
        },
        {
            field: 'stat', title: '交易状态', formatter: (value, row) => {
                const stat = row.stat;
                return getOrDefaltOfMap(statMap, stat);
            }
        },
        {
            field: 'confm_STAT', title: '确认状态', formatter: (value, row) => {
                const confmSTAT = row.confm_STAT;
                return getOrDefaltOfMap(confmStatMap, confmSTAT);
            }
        },
        {field: 'plat_SEQ', title: '交易流水'},
        {field: 'host_DATE', title: '核心日期'},
        {field: 'host_SEQ', title: '核心流水'}
    ];
}

/**
 * 获取对账报表
 */
function printDtl() {
    $.ajax({
        url: ctx + '/bus/data/printDtl',
        type: 'POST',
        data: $from.serializeObject()
    });
}
