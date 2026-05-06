let $table;
let $preRefundTable;
let $from;
let $querydate;
let busiM = new Map();


$(function () {

    parent.window.$('#iframe_list').show();
    window.parent.document.getElementById('comDiv').innerHTML = '';
    $querydate = window.parent.document.getElementById('comQuery');
    $table = $('#batRefundDtlTable');

    $from = $('#from');
    //选择框内容查询
    busiNo();

    setI('startTime', getYesterday());
    setI('endTime', getCurrentday());

    //批量退款明细查询
    initInstTable();
    chgHeight();

    $('#qryBtn').click(function () {
        refreshTable();
    });

    if (busiM == undefined && busiM.size == 0) {
        busiNo();
    }

    $("#busiNo").on("change", function (e) {
        if (busiM == undefined && busiM.size == 0) {
            busiNo();
        }
    });

    $('#export').click(function () {
        top.$.jBox.confirm("是否要导出账单明细查询数据?", "系统提示", function (v, h, f) {
            if (v == "ok") {
                showLoading();
                $("#from").attr("action", ctx + "/offline/data/export");
                $("#from").submit();
                //axios请求在产品中有loading页面的前处理和后处理 通过调用该方法实现loading页面的关闭
                exportDtl();
            }
        })
    });


});





function busiNo() {
    //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务
    const busiInfo = busiSelect($("select[name='busiNo']"), '01');
    if (busiInfo != undefined && busiInfo != null) {
        busiInfo.forEach(item => {
            busiM.set(item.busiNo, item.busiName);
        });
    }
}


function chgHeight() {
    const Height = $(document.body).height();
    $(window.parent.document).find('#tab_list').find('iframe').height(Height + 80);
}



const tableColumns = () => {
    //返回初始化
    return [
        {field: 'busi_NO', title: '业务编号'},
        {field: 'owe_MONTH', title: '收费周期'},
        {field: 'plat_DATE', title: '退款日期'},
        {field: 'plat_SEQ', title: '退款流水号'},
        {field: 'payee_ACCT', title: '退款卡号'},
        {field: 'payee_ACCT_NAME', title: '客户名称'},
        {field: 'prctl_AMT', title: '退款金额'},
        {
            field: 'tran_STAT', title: '退款结果' ,formatter: function (value, row) {
                const tranStat = row.tran_STAT;
                if('01' === tranStat){
                    return '成功';
                }
                if('02' === tranStat){
                    return '失败';
                }
                if('03' === tranStat){
                    return '异常';
                }
            }
        },
       /* {field: 'action', title: '操作', formatter: action}*/

    ];
}


const initInstTable = () => {
    const url = ctx + '/payDetails/data/list';
    const columns = tableColumns();
    let config = SmartWeb.bootstrapTable.constructor('#batRefundDtlTable', columns, url, queryParams);
    config = SmartWeb.bootstrapTable.extendConfig(config, {
        height: 550,
        pageSize: 10,
        responseHandler: res => {
            total = 0;
            rows = [];
            if (res.data != undefined && res.data != null) {
                const data = res.data;
                total = data.total;
                rows = data.data.book_LIST;
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


function queryParams(params) {

    let formData = $from.serializeObject();
    //默认查询已缴费的缴费账单明细
    formData.stat = '01';
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


function action(value, row) {
    let btnhtml = '';
    btnhtml += `<button class='unformatter lb1' style="display:${accSub(row.prctl_AMT, row.rfnd_AMT) > 0 && (row.pay_TP === '1' || row.pay_TP === '5') && row.oper_STAT === '00' ? '' : 'none'} " onclick="preRefund('${Base64.encode(JSON.stringify(row))}' )">预退款</button>`
    return btnhtml;
}






