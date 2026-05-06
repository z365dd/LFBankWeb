let $table;
let $from;
let busiM = new Map();

$(function () {
    parent.window.$('#iframe_list').show();
    //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务
    const busiInfo = busiSelect($("select[name='busiNo']"), '01');
    $table = $('#chkQueryTable');
    $from = $('#from');
    if (busiInfo != undefined && busiInfo != null) {
        busiInfo.forEach(item => {
            busiM.set(item.busiNo, item.busiName);
        });
    }
    busiNo();
    $("#busiNo").on("change", function (e) {
        busiNo();
    });
    setI('startTime', getYesterday());
    setI('endTime', getYesterday());
    initInstTable();
    chgHeight();
    $('#qryBtn').click(function () {
        refreshTable();
    });
});

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

function busiNo() {
    if (busiM == undefined || busiM.size==0) {
        //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务
        const busiInfo = busiSelect($("select[name='busiNo']"), '100');
        if (busiInfo != undefined && busiInfo != null) {
            busiInfo.forEach(item => {
                busiM.set(item.busiNo, item.busiName);
            });
        }

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
    const url = ctx + '/chkQuery/data/list';
    const columns = tableColumns();
    let config = SmartWeb.bootstrapTable.constructor('#chkQueryTable', columns, url, queryParams);
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
        {field: 'chk_DATE', title: '对账日期'},
        {field: 'busi_NO', title: '业务编号'},
        {
            field: 'busi_NO', title: '业务名称', formatter: (value, row) => {
                const busiNo = row.busi_NO;
                return getOrDefaltOfMap(busiM, busiNo);
            }
        },
        {
            field: 'task_NO', title: '对账步骤', formatter: (value, row) => {
                const taskNo = row.task_NO;
                if (taskNo == 'LOAD_HOST_FILE') {
                    return '核心对账';
                }
                if (taskNo == 'LOAD_DCT_FILE') {
                    return '营销对账';
                }
                if (taskNo == 'LOAD_POS_FILE') {
                    return 'pos对账';
                }
                if (taskNo == 'CHK_DTL') {
                    return '流水勾对';
                }
                if (taskNo == 'CHK_ALL') {
                    return '对账总控';
                }
            }
        },
        {field: 'tot_NUM', title: '总笔数'},
        {field: 'tot_AMT', title: '总金额'},
        {
            field: 'chk_STAT', title: '对账状态', formatter: (value, row) => {
                const chkStat = row.chk_STAT;
                if (chkStat == '00') {
                    return '待处理';
                }
                if (chkStat == '01') {
                    return '对账成功';
                }
                if (chkStat == '02') {
                    return '对账失败';
                }
                if (chkStat == '03') {
                    return '正在处理';
                }
            }
        },
        {field: 'err_NUM', title: '差错笔数'}
    ];
}

const btnInfos = [{text: '详情', act: 'info'}];

function action(value, row, index) {
    let btnhtml = '';
    btnInfos.forEach(btnInfo => {
        btnhtml += `<button class='unformatter lb1' onclick="${btnInfo.act}($(row))">${btnInfo.text}</button>`
    });
    return btnhtml;
}

function info(row) {
    parent.window.$('a[href^=\'#tab_detail\']').attr('url', ctx + `/payDetails/data/info`);

    parent.window.$('a[href^=\'#tab_detail\']').click();
}
