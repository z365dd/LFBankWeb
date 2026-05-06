let $table;
let $from;
let busiM = new Map();


$(function () {
    parent.window.$('#iframe_list').show();
    //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务
    // const busiInfo = busiSelect($("select[name='BUSI_NO']"), '00');
    $table = $('#cleanRuleTable');
    $from = $('#from');
    // busiInfo.forEach(item => {
    //     busiM.set(item.busiNo, item.busiName);
    // });
    busiNo();
    initInstTable();
    chgHeight();

    $('#qryBtn').click(function () {
        refreshTable();
    });

    $("#BUSI_NO").on("change", function (e) {
        if(busiM == undefined || busiM.size == 0){
            busiNo();
        }
    });
    // var data = $table.data('bootstrap.table');
    // data.options.url = ctx + '/clearRule/data/list';
});

function busiNo() {
        //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务
        const busiInfo = busiSelect($("select[name='BUSI_NO']"), '00');
        busiInfo.forEach(item => {
            busiM.set(item.busiNo, item.busiName);
        });
    setI("busiName", busiM.get(getS("BUSI_NO")));
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
    const url = ctx + '/clearRule/data/list';
    const columns = tableColumns();
    let config = SmartWeb.bootstrapTable.constructor('#cleanRuleTable', columns, url, queryParams);
    config = SmartWeb.bootstrapTable.extendConfig(config, {
        height: 500,
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
        {field: 'busi_NO', title: '业务编号'},
        {
            field: 'clr_TP', title: '清算类型', formatter: (value, row) => {
                const tp = row.clr_TP;
                if (tp == 0) {
                    return "汇总清算一笔";
                }
                if (tp == 2) {
                    return "按手续费清算";
                }
                if (tp == 3) {
                    return "按渠道分别清算";
                }
            }
        },
        {
            field: 'note_TP', title: '通知类型', formatter: (value, row) => {
                const tp = row.note_TP;
                if (tp == 1) {
                    return "对账完成通知三方";
                }
                if (tp == 2) {
                    return "对账完成不通知三方";
                }

            }
        },
        {
            field: 'chk_TP', title: '对账类型', formatter: (value, row) => {
                const tp = row.chk_TP;
                if (tp == 1) {
                    return "行内对账";
                }
                if (tp == 2) {
                    return "三方对账";
                }
            }
        },
        {
            field: 'rfnd_TP', title: '退款类型', formatter: (value, row) => {
                const tp = row.rfnd_TP;
                if (tp == 1) {
                    return "单位户直接退费";
                }
                if (tp == 2) {
                    return "内部户退费";
                }
                if (tp == 3) {
                    return "垫款户退费";
                }
                if (tp == 4) {
                    return "不允许退费";
                }
            }
        },
        // {
        //     field: 'day_NOTE_TP', title: '日切通知类型', formatter: (value, row) => {
        //         const dayNoteTp = row.day_NOTE_TP;
        //         if (dayNoteTp == 1) {
        //             return "通知三方";
        //         }
        //         if (dayNoteTp == 2) {
        //             return "不通知三方";
        //         }
        //     }
        // },
        {field: 'entr_ACCT', title: '单位账号'},
        {field: 'temp_ACCT', title: '垫款账号'},
        {field: 'temp_ACCT_NAME', title: '垫款账户名称'},
        // {field: 'action', title: '操作', formatter: action}

    ];
}


// const btnInfos = [{text: '删除', act: 'deleteInfo'}, {text: '修改', act: 'update'}];
const btnInfos = [{text: '修改', act: 'update'}];

function action(value, row, index) {
    let btnhtml = '';
    btnInfos.forEach(btnInfo => {
        btnhtml += `<button class='unformatter lb1' type="button" onclick="${btnInfo.act}(
    '${row.busi_NO}','${row.rule_NO}','${row.clr_TP}','${row.chk_TP}','${row.rfnd_TP}','${row.temp_ACCT}','${row.temp_ACCT_NAME}','${row.note_TP}','${row.day_NOTE_TP}')">${btnInfo.text}</button>`
    });
    return btnhtml;
}

function deleteInfo(BUSI_NO, RULE_NO) {
    confirmx('是否删除该记录', function () {
        del(BUSI_NO, RULE_NO);
        refreshTable();
    })
}

function del(BUSI_NO, RULE_NO) {
    sendGetOfAjax(ctx + `/clearRule/data/delete?BUSI_NO=${BUSI_NO}&RULE_NO=${RULE_NO}`, true);
}

function update(busiNo, ruleNo, clrTp, chkTp, rfndTp, tempAcct, tempAcctName, noteTp, dayNoteTp) {
    parent.window.$('a[href^=\'#tab_update\']').attr('url', ctx + `/clearRule/page/update?busi_no=${busiNo}&rule_no=${ruleNo}&clr_tp=${clrTp}&chk_tp=${chkTp}&rfnd_tp=${rfndTp}&temp_acct=${tempAcct}&temp_acct_name=${tempAcctName}&note_tp=${noteTp}&day_note_tp=${dayNoteTp}`);
    parent.window.$('a[href^=\'#tab_update\']').click();
}
