let $table;
let $from;
let $querydate;
let busiM = new Map();
let tranStatMap = new Map();
let payTypeMap = new Map();

let tranTp;

$(function () {

    parent.window.$('#iframe_list').show();
    window.parent.document.getElementById('comDiv').innerHTML = '';
    $querydate = window.parent.document.getElementById('comQuery');
    //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务 10-公交卡
    const busiInfo = busiSelect($("select[name='busiNo']"), '20');
    $table = $('#payDetailsTable');
    $from = $('#from');
    if (busiInfo != undefined && busiInfo != null) {
        busiInfo.forEach(item => {
            busiM.set(item.busiNo, item.busiName);
        });
    }
    tranStatMap = tranStatSelect($("select[name='txStat']"), true);
    payTypeMap = payTpMap();
    //详情页返回后展示最近一次查询的记录
    setI("startTime", getCurrentday());
    setI("endTime", getCurrentday());
    if ($querydate.innerHTML.length > 0) {
        var queryData = JSON.parse($querydate.innerHTML);
        setI("startTime", queryData.startTime);
        setI("endTime", queryData.endTime);
        setI("busiNo", queryData.busiNo);
        setI("payNo", queryData.payNo);
        setS("txStat", queryData.txStat);
        setS("tranTp", queryData.tranTp);
    }
    tranTp = getS('tranTp');
    initInstTable();
    chgHeight();
    $('#qryBtn').click(function () {
        refreshTable();
    });
    busiNo();
    $("#busiNo").on("change", function (e) {
        busiNo();
    });

    $("#tranTp").on("change", function (e) {
        tranTp = getS('tranTp');
        // 重新生成 columns 配置
        const newColumns = tableColumns();
        // 更新表格列
        $table.bootstrapTable('refreshOptions', {columns: newColumns});
        // 刷新数据
        $table.bootstrapTable('refresh');
    });

    $('#export').click(function () {
        top.$.jBox.confirm("是否要导出缴费明细查询数据?", "系统提示", function (v, h, f) {
            if (v == "ok") {
                showLoading();
                $("#from").attr("action", ctx + "/payDetails/data/export");
                $("#from").submit();
                //axios请求在产品中有loading页面的前处理和后处理 通过调用该方法实现loading页面的关闭
                exportExcel();
            }
        })
    });
    // var data = $table.data('bootstrap.table');
    // data.options.url = ctx + '/payDetails/data/list';
});

function busiNo() {
    if (busiM == undefined || busiM.size == 0) {
        //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务 10-公交卡 20-一卡通
        const busiInfo = busiSelect($("select[name='busiNo']"), '20');
        if (busiInfo != undefined && busiInfo != null) {
            busiInfo.forEach(item => {
                busiM.set(item.busiNo, item.busiName);
            });
        }

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


function rebuildStatSelect(dom) {
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
}

const refreshTable = () => {
    const $preClick = $table.parent().parent().find('.page-pre');
    if ($preClick.siblings().length > 1) {
        $preClick.next().click();
    }
    // $table.bootstrapTable('destroy');
    // initInstTable();
    $table.bootstrapTable('refresh');
    chgHeight();
}

const initInstTable = () => {
    const url = ctx + '/payDetails/data/list';
    const columns = tableColumns();
    let config = SmartWeb.bootstrapTable.constructor('#payDetailsTable', columns, url, queryParams);
    config = SmartWeb.bootstrapTable.extendConfig(config, {
        height: 500,
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

const tableColumns = () => {

    console.info('=======================' + tranTp);
    const columns = [
        {
            field: 'plat_DATE', title: tranTp === '01' ? '充值日期' : '退费日期', formatter: (value, row) => {
                const platDate = row.plat_DATE;
                return platDate.substr(0, 4) + '-' + platDate.substr(4, 2) + '-' + platDate.substr(6, 2);
            }
        },
        {field: 'pay_NO', title:'一卡通账号'},
        {field: 'name', title: '客户名称'}
    ];

    if(tranTp === '01'){
        columns.push( {field: 'pay_ACCT', title: '充值银行卡号'});
        columns.push({field: 'tot_AMT', title: '充值金额'});
        columns.push( {field: 'prctl_AMT', title: '实付金额'});
    }else{
        columns.push( {field: 'payee_ACCT', title: '接收退款账号'});
        columns.push( {field: 'payee_ACCT_NAME', title: '接收退款账户名称'});
        columns.push( {field: 'tot_AMT', title: '退费金额'});
    }


    if(tranTp === '01'){
        columns.push({field: 'fee_AMT', title: '手续费'});
        columns.push({
            field: 'pay_TP', title: '充值方式', formatter: (value, row) => {
                const autoFLG = row.auto_FLG;
                //自动标志为1时，缴费方式为批量扣款
                if (autoFLG == '1') {
                    return '批量扣款';
                }
                const tp = row.pay_TP;
                return getOrDefaltOfMap(payTypeMap, tp);
            }
        });
    }

    columns.push({
        field: 'tran_STAT', title: '交易状态', formatter: (value, row) => {
            const stat = row.tran_STAT;
            return getOrDefaltOfMap(tranStatMap, stat);
        }
    });
    columns.push( {field: 'chnl_NO', title: '渠道',formatter: (value, row) => {
            const chnl_No = row.chnl_NO;
            if (chnl_No =='00'){
                return '柜面';
            }else if (chnl_No =='030'){
                return '手机银行';
            }else if (chnl_No =='057') {
                return '廊银小惠';
            }
        }
    });
    columns.push({
        field: 'ret_MSG', title: '失败原因', formatter: (value, row) => {
            const stat = row.tran_STAT;
            if (stat == '01' || stat == '04') {
                return '';
            }
            if (stat == '02' || stat == '03') {
                return row.ret_MSG;
            }

        }
    });

    columns.push({field: 'action', title: '操作', formatter: action});
    return columns;

    /*return [
        {
            field: 'plat_DATE', title: tranTp === '01' ? '缴费日期' : '退费日期', formatter: (value, row) => {
                const platDate = row.plat_DATE;
                return platDate.substr(0, 4) + '-' + platDate.substr(4, 2) + '-' + platDate.substr(6, 2);
            }
        },
        {field: 'pay_NO', title: '缴费号'},
        {field: 'name', title: '客户名称'},
        {field: 'pay_ACCT', title: '缴费账号'},
        {field: 'tot_AMT', title: '缴费金额'},
        {field: 'prctl_AMT', title: '实付金额'},
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

    ];*/

    // try{
    //     const payColums = [
    //         {
    //             field: 'plat_DATE', title: '缴费日期', formatter: (value, row) => {
    //                 const platDate = row.plat_DATE;
    //                 return platDate.substr(0, 4) + '-' + platDate.substr(4, 2) + '-' + platDate.substr(6, 2);
    //             }
    //         },
    //         {field: 'pay_NO', title: '缴费号'},
    //         {field: 'name', title: '客户名称'},
    //         {field: 'pay_ACCT', title: '缴费账号'},
    //         {field: 'tot_AMT', title: '缴费金额',},
    //         {field: 'prctl_AMT', title: '实付金额'},
    //         {field: 'fee_AMT', title: '手续费'},
    //         {
    //             field: 'pay_TP', title: '缴费方式', formatter: (value, row) => {
    //                 const autoFLG = row.auto_FLG;
    //                 //自动标志为1时，缴费方式为批量扣款
    //                 if (autoFLG == '1') {
    //                     return '批量扣款';
    //                 }
    //                 const tp = row.pay_TP;
    //                 return getOrDefaltOfMap(payTypeMap, tp);
    //             }
    //         },
    //         {
    //             field: 'tran_STAT', title: '交易状态', formatter: (value, row) => {
    //                 const stat = row.tran_STAT;
    //                 return getOrDefaltOfMap(tranStatMap, stat);
    //             }
    //         },
    //         {
    //             field: 'ret_MSG', title: '失败原因', formatter: (value, row) => {
    //                 const stat = row.tran_STAT;
    //                 if (stat == '01' || stat == '04') {
    //                     return '';
    //                 }
    //                 if (stat == '02' || stat == '03') {
    //                     return row.ret_MSG;
    //                 }
    //
    //             }
    //         },
    //         {field: 'action', title: '操作', formatter: action}];
    //
    //     const refunddColums = [
    //         {
    //             field: 'plat_DATE', title: '退费日期', formatter: (value, row) => {
    //                 const platDate = row.plat_DATE;
    //                 return platDate.substr(0, 4) + '-' + platDate.substr(4, 2) + '-' + platDate.substr(6, 2);
    //             }
    //         },
    //         {field: 'pay_NO', title: '一卡通账号'},
    //         {field: 'name', title: '一卡通户名'},
    //         {field: 'payee_ACCT', title: '接收退款账号'},
    //         {field: 'payee_ACCT_NAME', title: '接收退款账户名称'},
    //         {field: 'tot_AMT', title: '退费金额',},
    //         {field: 'prctl_AMT', title: '实退金额'},
    //         {
    //             field: 'tran_STAT', title: '交易状态', formatter: (value, row) => {
    //                 const stat = row.tran_STAT;
    //                 return getOrDefaltOfMap(tranStatMap, stat);
    //             }
    //         },
    //         {
    //             field: 'ret_MSG', title: '失败原因', formatter: (value, row) => {
    //                 const stat = row.tran_STAT;
    //                 if (stat == '01') {
    //                     return '';
    //                 }
    //                 if (stat == '02' || stat == '03') {
    //                     return row.ret_MSG;
    //                 }
    //
    //             }
    //         },
    //         {field: 'action', title: '操作', formatter: action}
    //     ];
    //
    //     if (tranTp === '01') {  return payColums;}
    //     if (tranTp === '03') { return refunddColums; }
    //     return [];
    // }catch (e){
    //     console.error("列配置生成失败:", e);
    //     return [{
    //         field: 'plat_DATE', title: '缴费日期', formatter: (value, row) => {
    //             const platDate = row.plat_DATE;
    //             return platDate.substr(0, 4) + '-' + platDate.substr(4, 2) + '-' + platDate.substr(6, 2);
    //         }
    //     },
    //         {field: 'pay_NO', title: '缴费号'},
    //         {field: 'name', title: '客户名称'},
    //         {field: 'pay_ACCT', title: '缴费账号'},
    //         {field: 'tot_AMT', title: '缴费金额',},
    //         {field: 'prctl_AMT', title: '实付金额'},
    //         {field: 'fee_AMT', title: '手续费'},
    //         {
    //             field: 'pay_TP', title: '缴费方式', formatter: (value, row) => {
    //                 const autoFLG = row.auto_FLG;
    //                 //自动标志为1时，缴费方式为批量扣款
    //                 if (autoFLG == '1') {
    //                     return '批量扣款';
    //                 }
    //                 const tp = row.pay_TP;
    //                 return getOrDefaltOfMap(payTypeMap, tp);
    //             }
    //         },
    //         {
    //             field: 'tran_STAT', title: '交易状态', formatter: (value, row) => {
    //                 const stat = row.tran_STAT;
    //                 return getOrDefaltOfMap(tranStatMap, stat);
    //             }
    //         },
    //         {
    //             field: 'ret_MSG', title: '失败原因', formatter: (value, row) => {
    //                 const stat = row.tran_STAT;
    //                 if (stat == '01' || stat == '04') {
    //                     return '';
    //                 }
    //                 if (stat == '02' || stat == '03') {
    //                     return row.ret_MSG;
    //                 }
    //
    //             }
    //         },
    //         {field: 'action', title: '操作', formatter: action}]; // 异常时返回空列
    // }

}

const btnInfos = [{text: '详情', act: 'info'}];

function action(value, row, index) {
    let btnhtml = '';
    btnInfos.forEach(btnInfo => {
        btnhtml += `<button class='unformatter lb1' onclick="${btnInfo.act}('${Base64.encode(JSON.stringify(row))}' )">${btnInfo.text}</button>`
    });
    return btnhtml;
}

function info(rowJson) {
    window.parent.document.getElementById('comDiv').innerHTML = Base64.decode(rowJson);
    //放置本次查询的日期条件 详情页返回时使用
    window.parent.document.getElementById('comQuery').innerHTML = JSON.stringify($from.serializeObject());
    parent.window.$('a[href^=\'#tab_detail\']').attr('url', ctx + `/ykt_dtl_mng/page/info`);
    parent.window.$('a[href^=\'#tab_detail\']').click();
}


function exportExcel() {
    $.ajax({
        url: ctx + '/payDetails/data/export',
        type: 'POST',
        data: $from.serializeObject()
    });
}
