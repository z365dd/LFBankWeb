let $table;
let $from;
let busiM = new Map();

$(function () {
    parent.window.$('#iframe_list').show();
    //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务
    const busiInfo = busiSelect($("select[name='MERT_NO']"), '00');
    $table = $('#payTimeTable');
    $from = $('#from');
    if (busiInfo != undefined || busiInfo != null) {
        busiInfo.forEach(item => {
            busiM.set(item.busiNo, item.busiName);
        });
    }


    $('#qryBtn').click(function () {
        refreshTable();
    });
    busiNo();
    $("#MERT_NO").on("change", function (e) {
        busiNo();
    });
    initInstTable();
    chgHeight();
    // var data = $table.data('bootstrap.table');
    // data.options.url = ctx + '/payTime/data/list';
});

function busiNo() {
    if (busiM == undefined || busiM.size==0) {
        //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务
        const busiInfo = busiSelect($("select[name='MERT_NO']"), '00');
        busiInfo.forEach(item => {
            busiM.set(item.busiNo, item.busiName);
        });
    }
    setI("busiName", busiM.get(getS("MERT_NO")));
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
    const url = ctx + '/payTime/data/list';
    const columns = tableColumns();
    let config = SmartWeb.bootstrapTable.constructor('#payTimeTable', columns, url, queryParams);
    config = SmartWeb.bootstrapTable.extendConfig(config, {
        height: 500,
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
        {field: 'mert_NAME', title: '业务名称'},
        {
            field: 'rule_TP', title: '规则类型', formatter: (value, row) => {
                const tp = row.rule_TP;
                if (tp == 1) {
                    return "固定时间";
                }
                if (tp == 2) {
                    return "固定日期";
                }
                if (tp == 3) {
                    return "指定日期时间";
                }
            }
        },
        {field: 'str_TIME', title: '不允许缴费开始日期时间'},
        {field: 'end_TIME', title: '不允许缴费结束日期时间'},
        {field: 'rule_NO', title: '规则编号'},
        {field: 'action', title: '操作', formatter: action}

    ];
}

const btnInfos = [{text: '删除', act: 'deleteInfo'}, {text: '修改', act: 'update'}];

function action(value, row, index) {
    let btnhtml = '';
    btnInfos.forEach(btnInfo => {
        btnhtml += `<button class='unformatter lb1' type="button" onclick="${btnInfo.act}('${row.mert_NO}','${row.rule_NO}','${row.rule_TP}','${row.str_TIME}','${row.end_TIME}', '${row.end_TIME}')">${btnInfo.text}</button>`
    });
    return btnhtml;
}

function deleteInfo(mertNo, ruleNo, ruleTp) {
    console.info(ruleTp);
    confirmx('是否删除该记录', function () {
        del(mertNo, ruleNo, ruleTp);
        console.info("进入页面");
        refreshTable();
    })
}

function del(mertNo, ruleNo, ruleTp) {
    sendGetOfAjax(ctx + `/payTime/data/delete?MERT_NO=${mertNo}&RULE_NO=${ruleNo}&RULE_TP=${ruleTp}`, true);
}

function update(mertNo, ruleNo, ruleTp, strTime, endTime) {
    parent.window.$('a[href^=\'#tab_update\']').attr('url', ctx + `/payTime/page/update?mert_no=${mertNo}&rule_no=${ruleNo}&rule_tp=${ruleTp}&str_time=${strTime}&end_time=${endTime}`);

    parent.window.$('a[href^=\'#tab_update\']').click();
}
