let $table;
let $from;
let $flg;
let tranStatMap = new Map();
let payTypeMap = new Map();

$(function () {
    parent.window.$('#iframe_list').show();
    $table = $('#merTranDetailsTable');
    $from = $('#from');
    setI("startTime", getCurrentday());
    setI("endTime", getCurrentday());
    tranStatMap = tranStatSelect($("select[name='txStat']"), true);
    autoDeductMap = autoDeductSelect($("select[name='autoFlag']"));
    $('#autoFlag').on("change", function (e) {
        linkChange();
    })
    payTypeMap = payTpMap();
    initInstTable();
    chgHeight();
    $('#qryBtn').click(function () {
        refreshTable();
    });
    // var data = $table.data('bootstrap.table');
    // data.options.url = ctx + '/merTranDetails/data/list';
    $flg = window.parent.document.getElementById('flg');
    if ($flg.innerText.length > 0) {
        try {
            const query = JSON.parse($flg.innerText);
            setI('cstNo', query.cstNo);
            setI('startTime', query.startTime);
            setI('endTime', query.endTime);
            setS('refundStat', query.refundStat);
            setS('autoFlag', query.autoFlag);
            linkChange();
            refreshTable();
        } catch (e) {
            console.info('原json条件解析失败');
        } finally {
            $flg.innerText = '';
        }
    }
});

function chgHeight() {
    const Height = $(document.body).height();
    $(window.parent.document).find('#tab_list').find('iframe').height(Height + 80);
}

//自动标志和退费状态选择框的关联
function linkChange() {
    const autoFlg = getS("autoFlag");
    const dom = $("select[name='txStat']");
    console.info("当前自动标志属性", autoFlg);
    rebuildStatSelect(dom, autoFlg);
    if (autoFlg == '1') {
        $('#refund').hide();
        $('#auto').attr('class', 'col-md-3 column');
        $('#blank').show();
    }
    if (autoFlg == '0') {
        $('#refund').show();
        $('#auto').attr('class', 'col-md-4 column');
        $('#blank').hide();
    }
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

function rebuildStatSelect(dom, autoFlg) {
    if (autoFlg == '1') {
        var statMap = new Map();
        statMap.set('', '请选择');
        statMap.set('01', '成功');
        statMap.set('02', '失败');
        dom.empty();
        statMap.forEach((v, k) => {
            dom.append(`<option value = '${k}'>${v}</option>`);
        })
        dom.multiselect('rebuild').multiselect('refresh');
        return statMap;
    } else {
        dom.empty();
        var tempMap = new Map();
        tempMap.set('', '请选择');
        tempMap.forEach((v, k) => {
            dom.append(`<option value = '${k}'>${v}</option>`);
        })
        return tranStatSelect(dom, false);
    }

}

const initInstTable = () => {
    const url = ctx + '/merTranDetails/data/list';
    const columns = tableColumns();
    let config = SmartWeb.bootstrapTable.constructor('#merTranDetailsTable', columns, url, queryParams);
    config = SmartWeb.bootstrapTable.extendConfig(config, {
        height: 400,
        pageSize: 10,
        responseHandler: res => {
            total = 0;
            rows = [];
            if (res.data != undefined && res.data != null) {
                const data = res.data;
                setI('totalNotSettleNum', data.data.wait_CLR_NUM);
                setI('totalNotSettleAmt', data.data.wait_CLR_AMT);
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

const tableColumns = () => {
    return [
        {field: 'busi_NAME', title: '业务名称'},
        {field: 'plat_DATE', title: '交易日期'},
        {field: 'pay_NO', title: '缴费号'},
        {field: 'name', title: '客户名称'},
        {field: 'tot_AMT', title: '缴费金额'},
        {field: 'prctl_AMT', title: '实付金额'},
        {field: 'dct_AMT', title: '优惠金额'},
        {field: 'amt', title: '银行优惠'},
        {
            field: 'dct_AMT', title: '商户优惠', formatter: (value, row) => {
                var amt = row.amt;
                var dctAmt = row.dct_AMT;
                return accSub(dctAmt, amt);
            }
        },
        {field: 'fee_AMT', title: '手续费'},
        {
            field: 'pay_TP', title: '缴费方式', formatter: (value, row) => {
                const autoFLG = row.auto_FLG;
                //自动标志为1时，缴费方式为批量扣款
                if (autoFLG == '1') {
                    return '批量扣款';
                }
                const tp = row.pay_TP;
                return getOrDefaltOfMap(payTypeMap, tp);
            }
        },
        {
            field: 'tran_STAT', title: '交易状态', formatter: (value, row) => {
                const stat = row.tran_STAT;
                return getOrDefaltOfMap(tranStatMap, stat);
            }
        },
        {
            field: 'rfnd_STAT', title: '退费状态', formatter: (value, row) => {
                const auto = getS('autoFlag');
                if (auto == '1' || row.rfnd_STAT == '00') {
                    return '未退费';
                } else if (row.rfnd_STAT == '01') {
                    return '全额退费';
                }
            }
        },
        {
            field: 'ret_MSG', title: '失败原因', formatter: (value, row) => {
                const stat = row.tran_STAT;
                if (stat == '01' || stat == '04') {
                    return '';
                }
                if (stat == '02' || stat == '03') {
                    return row.ret_MSG;
                }

            }
        },
        {field: 'action', title: '操作', formatter: action}
    ];
}

const btnInfos = [{text: '详情', act: 'info'}];

function action(value, row, index) {
    let btnhtml = '';
    btnInfos.forEach(btnInfo => {
        // btnhtml += `<button class='unformatter lb1' onclick="${btnInfo.act}('${Base64.encode(JSON.stringify(row))}')">${btnInfo.text}</button>`
        // btnhtml += `<button class='unformatter lb1' onclick="${btnInfo.act}($(row))">${btnInfo.text}</button>`
        btnhtml += `<button class='unformatter lb1' onclick="${btnInfo.act}('${Base64.encode(JSON.stringify(row))}')">${btnInfo.text}</button>`
    });
    return btnhtml;
}

function info(rowJson) {
    $flg.innerText = JSON.stringify($from.serializeObject());
    //存放当前行内容
    window.parent.document.getElementById('comDiv').innerHTML = Base64.decode(rowJson);
    parent.window.$('a[href^=\'#tab_detail\']').attr('url', ctx + `/merTranDetails/page/info`);
    parent.window.$('a[href^=\'#tab_detail\']').click();
}
