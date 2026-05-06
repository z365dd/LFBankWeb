let $table;
let $from;
let busiM = new Map();
let channelMap = new Map();
let businessNo;
let BUSINAME;

$(function () {
    parent.window.$('#iframe_list').show();
    setI('strDate', getYesterday());
    setI('endDate', getCurrentday());
    //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务
    const busiInfo = busiSelect($("select[name='busiNo']"), '00');
    businessNo = getI("busiNo")
    chnlSelectPrint($("select[name='chnlNo']"));
    //回单查询机构号下拉框
    if (busiInfo != undefined || busiInfo != null) {
        busiInfo.forEach(item => {
            busiM.set(item.busiNo, item.busiName);
        });
    }
    BUSINAME = busiM.get(busiNo);
    $table = $('#payInfoTable');
    $from = $('#from');
    channelMap = chnlMap();
    initInstTable();
    chgHeight();
    $('#qryBtn').click(function () {
        refreshTable();
    });
    busiNo();
    $("#busiNo").on("change", function (e) {
        busiNo();
    });
    //不在页面初始化进行查询
    // var data = $table.data('bootstrap.table');
    // data.options.url = ctx + '/pay/data/list';
    $('#print').click(function () {
        top.$.jBox.confirm("是否要进行回单查询打印?", "系统提示", function (v, h, f) {
            if (v == "ok") {
                print();
            }
        })

    });
});

function busiNo() {
    if (busiM == undefined || busiM.size==0) {
        //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务
        const busiInfo = busiSelect($("select[name='busiNo']"), '00');
        busiInfo.forEach(item => {
            busiM.set(item.busiNo, item.busiName);
        });
    }
    setI("busiName", busiM.get(getS("busiNo")));
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
    const url = ctx + '/pay/data/list';
    const columns = tableColumns();
    let config = SmartWeb.bootstrapTable.constructor('#payInfoTable', columns, url, queryParams);
    config = SmartWeb.bootstrapTable.extendConfig(config, {
        height: 500,
        pageSize: 10,
        responseHandler: res => {
            total = 0;
            rows = [];
            if (res.data != undefined && res.data != null) {
                const data = res.data;
                this.sendData = data;
                console.info(data);
                const sumRows = data.data;
                total = data.total;
                if (total != undefined && total != null && total != 0) {
                    setI('busiNameInfo', busiM.get(getI("busiNo")));
                    setI('instName', sumRows.brchName);
                    // setI('txDateInfo', getI("transDate"));
                    setI('totalNum', sumRows.tot_NUM);
                    setI('totalAmt', sumRows.tot_AMT);
                }
                if (total == 0) {
                    setI('busiNameInfo', '');
                    setI('instName', '');
                    // setI('txDateInfo', '');
                    setI('totalNum', '');
                    setI('totalAmt', '');
                }
                rows = data.data.list;
                if ((total == undefined && total == null) || total == 0 || rows == undefined || rows == null) {
                    total = 0;
                    rows = [];
                    $('#print').hide();
                } else {
                    $('#print').show();
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
        // {field: 'busiNo', title: '业务编号'},
        // {field: 'brchNo', title: '机构号'},
        // {field: 'totalNum', title: '总笔数'},

        {field: 'clr_DATE', title: '清算日期'},
        {field: 'chnl_NO', title: '渠道号'},
        {
            field: 'chnl_NO', title: '渠道名称', formatter: (value, row) => {
                const chnlNo = row.chnl_NO;
                return getOrDefaltOfMap(channelMap, chnlNo);
            }
        },
        {field: 'tot_NUM', title: '缴费总笔数'},
        {field: 'tot_AMT', title: '缴费总金额'},
        {field: 'clr_AMT', title: '清算总金额'},
        {field: 'dct_AMT', title: '优惠总金额'},
        {field: 'fee_AMT', title: '手续费总金额'}
    ];
}

const btnInfos = [{text: '详情', act: 'info'}];

function action(value, row, index) {
    let btnhtml = '';
    btnInfos.forEach(btnInfo => {
        btnhtml += `<button class='unformatter lb1' onclick="${btnInfo.act}('${row.platDate}', '${row.platSeq}', '${row.dtlNo}')">${btnInfo.text}</button>`
    });
    return btnhtml;
}

function info(platDate, platSeq, dtlNo) {
    parent.window.$('a[href^=\'#tab_detail\']').attr('url', ctx + `/comp/sms/stat/page/info?platDate=${platDate}&platSeq=${platSeq}&dtlNo=${dtlNo}`);

    parent.window.$('a[href^=\'#tab_detail\']').click();
}

function print() {
    $.ajax({
        url: ctx + '/merAccount/data/checkPrint',
        type: 'POST',
        data: $from.serializeObject(),
        success: function (data, textStatus) {
            console.info(data);
            if (data && data.returnCode && '0000' == data.returnCode) {
                $("#from").attr("action", ctx + "/pay/data/print");
                $("#from").submit();
                return;
            }
            showContent('操作失败:[' + data && data.message ? data.message : '' + ']', 'error');
        },

    });
}


