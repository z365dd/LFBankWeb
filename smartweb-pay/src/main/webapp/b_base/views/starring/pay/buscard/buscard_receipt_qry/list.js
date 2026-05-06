let $table;
let $from;
let busiM = new Map();


$(function () {
    parent.window.$('#iframe_list').show();
    $table = $('#busReceiptTable');
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
    initInstTable();
    chgHeight();
    $('#qryBtn').click(function () {
        refreshTable();
    });
    //打印按钮
    $('#print').click(function () {
        top.$.jBox.confirm("是否要进行回单查询打印?", "系统提示", function (v, h, f) {
            if (v == "ok") {
                showLoading();
                $("#from").attr("action", ctx + "/bus/data/print");
                $("#from").submit();
                //axios请求在产品中有loading页面的前处理和后处理 通过调用该方法实现loading页面的关闭
                exportExcel();
            }
        })
    });
    //下载对账报表
    $('#getBill').click(function () {
        top.$.jBox.confirm("是否要下载对账报表?", "系统提示", function (v, h, f) {
            if (v == "ok") {
                showLoading();
                $("#from").attr("action", ctx + "/bus/data/getAcctStatement");
                $("#from").submit();
                //axios请求在产品中有loading页面的前处理和后处理 通过调用该方法实现loading页面的关闭
                getBill();
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
    const url = ctx + '/bus/data/clrList';
    const columns = tableColumns();
    let config = SmartWeb.bootstrapTable.constructor('#busReceiptTable', columns, url, queryParams);
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
        {field: 'clr_DATE', title: '清算日期'},
        {field: 'busi_NAME', title: '业务名称'},
        {field: 'fee_TOT_AMT', title: '激活手续费金额'},
        {field: 'fee_TOT_NUM', title: '激活手续费笔数'},
        {field: 'crfld_TOT_AMT', title: '圈存金额'},
        {field: 'crfld_TOT_NUM', title: '圈存笔数'},
        {field: 'rfnd_TOT_AMT', title: '退卡金额'},
        {field: 'rfnd_TOT_NUM', title: '退卡笔数'},
        {field: 'netg_AMT', title: '轧差金额'},
        {field: 'clr_AMT', title: '清算金额'}
    ];
}

function exportExcel() {
    $.ajax({
        url: ctx + '/bus/data/print',
        type: 'POST',
        data: $from.serializeObject()
    });
}

function getBill() {
    $.ajax({
        url: ctx + '/bus/data/getAcctStatement',
        type: 'POST',
        data: $from.serializeObject()
    });
}
