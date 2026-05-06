let $table;
let $from;
let $querydate;
let busiM = new Map();
let projM = new Map();
let extraM = new Map();
let statMap = new Map();
let typeMap = new Map();


$(function () {

    parent.window.$('#iframe_list').show();
    window.parent.document.getElementById('comDiv').innerHTML = '';
    $querydate = window.parent.document.getElementById('comQuery');
    $table = $('#dtlTable');
    $from = $('#from');
    statMap = offStatMap();
    typeMap = offTypeMap();
    //初始化汇总信息
    initTotal();
    //选择框内容查询
    busiNo();
    projName();
    extraField();
    //详情页返回后展示最近一次查询的记录
    if ($querydate.innerHTML.length > 0) {
        var queryData = JSON.parse($querydate.innerHTML);
        console.info(queryData);
        setS('busiNo', queryData.busiNo);
        setS('projName', queryData.projName);
        setS('stat', queryData.stat);
        setI('oweMoth', queryData.oweMoth);
        setI('payNo', queryData.payNo);
        setI('name', queryData.name);
        setI('phoneNo', queryData.phoneNo);
        setI('strDate', queryData.strDate);
        setI('endDate', queryData.endDate);
    }
    initInstTable();
    chgHeight();
    $('#qryBtn').click(function () {
        initInstTable();
    });
    if (busiM == undefined || busiM.size==0) {
        busiNo();
        projName();
        extraField();
    }
    $("#busiNo").on("change", function (e) {
        if (busiM == undefined || busiM.size==0) {
            busiNo();
        }
        projName();
        extraField();
    });
    //缴费状态列表加载
    buildStatSelect($("select[name='stat']"));

    //缴费状态列表加载
    // buildTypeSelect($("select[name='projTp']"));

    //项目名称变更字段获取项目名称列表
    $("#projName").on("change", function (e) {
        if (projM == undefined || projM == undefined) {
            projName();
        }
    });
    //拓展字段变更
    $("#extraField").on("change", function (e) {
        if (extraM == undefined || extraM == undefined) {
            extraField();
        }
    });
    $('#export').click(function () {
        top.$.jBox.confirm("是否要导出账单明细查询数据?", "系统提示", function (v, h, f) {
            if (v == "ok") {
                showLoading();
                //字段名/字段值
                setI('extraField1', getS('extraField') + ',$' + getI('extraField'));
                console.info(getI('extraField1'));
                $("#from").attr("action", ctx + "/offline/data/export");
                $("#from").submit();
                //axios请求在产品中有loading页面的前处理和后处理 通过调用该方法实现loading页面的关闭
                exportDtl();
            }
        })
    });
    $('#mergeExport').click(function () {
        top.$.jBox.confirm("是否要导出账单明细合并数据?", "系统提示", function (v, h, f) {
            if (v == "ok") {
                showLoading();
                $("#from").attr("action", ctx + "/offline/data/exportByPayNo");
                $("#from").submit();
                //axios请求在产品中有loading页面的前处理和后处理 通过调用该方法实现loading页面的关闭
                exportMergeDtl();
            }
        })
    });

});

function busiNo() {
    if (busiM == undefined || busiM.size==0) {
        //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务
        const busiInfo = busiSelect($("select[name='busiNo']"), '01');
        if (busiInfo != undefined && busiInfo != null) {
            busiInfo.forEach(item => {
                busiM.set(item.busiNo, item.busiName);
            });
        }
    }
}

function initTotal() {
    setI('totNum', '0');
    setI('succTotNum', '0');
    setI('failTotNum', '0');
    setI('totAmt', '0');
    setI('succTotAmt', '0');
    setI('failTotAmt', '0');
}

function chgHeight() {
    const Height = $(document.body).height();
    $(window.parent.document).find('#tab_list').find('iframe').height(Height + 80);
}


function projName() {
    const dom = $("select[name='projName']")
    dom.empty();
    var tempMap = new Map();
    tempMap.set('', '请选择');
    tempMap.forEach((v, k) => {
        dom.append(`<option value = '${k}'>${v}</option>`);
    })
    dom.multiselect('rebuild').multiselect('refresh');
    const proInfo = projSelect(dom, getI('busiNo'));

    if (proInfo != undefined && proInfo != null) {
        proInfo.forEach(item => {
            projM.set(item.busiNo, item.projName);
        });
    }
}


function extraField() {
    const dom = $("select[name='extraField']")
    dom.empty();
    var tempMap = new Map();
    tempMap.set('', '请选择');
    tempMap.forEach((v, k) => {
        dom.append(`<option value = '${k}'>${v}</option>`);
    })
    dom.multiselect('rebuild').multiselect('refresh');
    const extraInfo = extraFieldSelect(dom, getI('busiNo'));

    if (extraInfo != undefined && extraInfo != null) {
        extraInfo.forEach(item => {
            extraM.set(item.key_NO, item.key_DESC);
        });
    }
}

//当前业务的缴费项目名称查询
function projSelect(projDom, busiNo) {
    const resp = sendGetOfAjax(ctx + `/offline/data/projNameList?busiNo=${busiNo}`, false);
    if (resp == undefined || resp == null) {
        return null;
    }
    if (resp != undefined && resp != null) {
        resp.forEach((tp, index) => {
            const optStr = `<option value = '${tp.projName}'>${tp.projName}</option>`;
            projDom.append(optStr);
        });
    }
    projDom.multiselect('rebuild').multiselect('refresh');
    return resp;
}

//当前业务的展示字段查询
function extraFieldSelect(dom, busiNo) {
    const resp = sendGetOfAjax(ctx + `/offline/data/getAllTmplField?busiNo=${busiNo}`, false);
    if (resp == undefined || resp == null) {
        return null;
    }
    console.info(resp);
    if (resp != undefined && resp != null) {
        resp.forEach((item, index) => {
            if (item.key_NO != 'NAME' && item.key_NO != 'PLAT_DATE' && item.key_NO != 'PHONE_NO' && item.key_NO != 'STU_ID') {
                const optStr = `<option value = '${item.key_NO}'>${item.key_DESC}</option>`;
                dom.append(optStr);
            }
        });
    }
    dom.multiselect('rebuild').multiselect('refresh');
    return resp;
}


function queryParams(params) {

    let formData = $from.serializeObject();
    formData.extraFields = getS('extraField') + ',$' + getI('extraField');
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
                setI('totNum', data.data.tot_NUM);
                setI('succTotNum', data.data.succ_TOT_NUM);
                setI('failTotNum', data.data.fail_TOT_NUM);
                setI('totAmt', data.data.tot_AMT);
                setI('totPrctlAmt', data.data.tot_PRCTL_AMT);
                setI('succTotAmt', data.data.succ_TOT_AMT);
                setI('succPrctlAmt', data.data.succ_PRCTL_AMT);
                setI('failTotAmt', data.data.fail_TOT_AMT);
                setI('failPrctlAmt', data.data.fail_PRCTL_AMT);
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

//缴费状态枚举
function offStatMap() {
    var map = new Map();
    map.set('00', '待缴费');
    map.set('01', '已缴费');
    map.set('99', '已作废');
    // map.set('02', '缴费异常');
    // map.set('03', '已退费');
    return map;
}

//缴费状态枚举
function offTypeMap() {
    var map = new Map();
    map.set('00', '非自主录入');
    // map.set('01', '自主录入');
    return map;
}

//构建缴费状态选择下拉框
function buildStatSelect(statDom) {
    var temMap = offStatMap();
    temMap.forEach((v, k) => {
        statDom.append(`<option value = '${k}' ${k == '0' ? 'selected' : ''}>${v}</option>`);
    })
    statDom.multiselect('rebuild').multiselect('refresh');
}

function buildTypeSelect(dom) {
    var temMap = offTypeMap();
    temMap.forEach((v, k) => {
        dom.append(`<option value = '${k}' ${k == '0' ? 'selected' : ''}>${v}</option>`);
    })
    dom.multiselect('rebuild').multiselect('refresh');
}


const tableColumns = () => {
    const formData = $from.serializeObject();
    var busiNo = formData.busiNo;
    //返回初始化
    let columns = [
        // {field: 'checked', align: 'center', checkbox: true },
        {field: 'proj_NAME', title: '收费项目'},
        {
            field: 'proj_TP', title: '项目类型', formatter: (value, row) => {
                const tp = row.proj_TP;
                return getOrDefaltOfMap(typeMap, tp);
            }
        },
        {field: 'owe_MONTH', title: '收费周期'},
    ];
    //查询所有明细查询启用状态为Y的展示字段  使用get方法不弹出成功方法
    const resp = sendGetOfAjax(ctx + `/offline/data/getAllTmplField?busiNo=${busiNo}`, false);
    let tranAmt = {field: 'prctl_AMT', title: '应缴金额'};
    let tranDate = {field: 'plat_DATE', title: '缴费日期'};
    let stat = {
        field: 'stat', title: '缴费状态', formatter: (value, row) => {
            const stat = row.stat;
            return getOrDefaltOfMap(statMap, stat);
        }
    };
    let data = {field: 'action', title: '操作', align: 'center', formatter: action};
    if (resp == null) {
        columns.push(tranAmt);
        columns.push(tranDate);
        columns.push(stat);
        columns.push(data);
        return columns;
    }
    //遍历循环添加  明细展示列名
    if (resp != undefined && resp != null) {
        resp.forEach((item, index) => {
            console.info(item);
            var name = item.key_NO;
            if (name.indexOf('_') > 0) {
                var nameArray = name.split('_');
                name = nameArray[0].toLowerCase() + '_' + nameArray[1];
            } else {
                name = name.toLowerCase();
            }
            const column = {field: name, align: 'center', title: item.key_DESC};
            columns.push(column);
        });
    }
    columns.push(tranAmt);
    columns.push(tranDate);
    columns.push(stat);
    columns.push(data);
    return columns;
}

const btnInfos = [{text: '详情', act: 'info'}, {text: '修改', act: 'dtlModify'}, {
    text: '删除',
    act: 'dtlDelete'
}, {text: '退费', act: 'refund'}];

function action(value, row) {
    let btnhtml = '';
    // btnInfos.forEach(btnInfo => {
    //     btnhtml += `<button class='unformatter lb1' stule="display:${row.stat == '00' && row.stat == '01' ? 'none' : ''} " onclick="${btnInfo.act}('${Base64.encode(JSON.stringify(row))}' )">删除</button>`
    // });
    btnhtml += `<button class='unformatter lb1' onclick="info('${Base64.encode(JSON.stringify(row))}' )">详情</button>`
    // btnhtml += `<button class='unformatter lb1' style="display:${row.stat == '01' ? 'none' : ''} " onclick="dtlModify('${Base64.encode(JSON.stringify(row))}' )">修改</button>`
    btnhtml += `<button class='unformatter lb1' style="display:${row.stat == '00' ? '' : 'none'} " onclick="dtlDelete('${Base64.encode(JSON.stringify(row))}' )">删除</button>`
    // btnhtml += `<button class='unformatter lb1' style="display:${row.stat == '01' ? '' : 'none'} " onclick="refund('${Base64.encode(JSON.stringify(row))}' )">退费</button>`

    return btnhtml;
}

//详情按钮 跳转详情页
function info(row) {
    var decodeRow = JSON.parse(Base64.decode(row));
    decodeRow.busiNo = getI('busiNo');
    //保存该数据  详情页展示
    window.parent.document.getElementById('comDiv').innerHTML = JSON.stringify(decodeRow);
    //放置此次查询条件 详情页返回时使用
    $querydate.innerHTML = JSON.stringify($from.serializeObject());
    parent.window.$('a[href^=\'#tab_info\']').attr('url', ctx + `/offline_detail/page/info`);
    parent.window.$('a[href^=\'#tab_info\']').click();
}

//新增
function dtlAdd(row) {
    //保存该数据  详情页展示  当前明细中没有返回busiNo 先这样处理
    window.parent.document.getElementById('comDiv').innerHTML = Base64.decode(row);

    //放置此次查询条件 新增页面返回时使用
    $querydate.innerHTML = JSON.stringify($from.serializeObject());
    parent.window.$('a[href^=\'#tab_add\']').attr('url', ctx + `/offline_detail/page/add`);
    parent.window.$('a[href^=\'#tab_add\']').click();
}

//修改
function dtlModify(row) {
    // var decodeRow = JSON.parse(Base64.decode(row));
    // decodeRow.busiNo = getI('busiNo');
    window.parent.document.getElementById('comDiv').innerHTML = Base64.decode(row);
    // window.parent.document.getElementById('comDiv').innerHTML = JSON.stringify(decodeRow);
    //放置此次查询条件 新增页面返回时使用
    $querydate.innerHTML = JSON.stringify($from.serializeObject());
    parent.window.$('a[href^=\'#tab_update\']').attr('url', ctx + `/offline_detail/page/modify`);
    parent.window.$('a[href^=\'#tab_update\']').click();
}

//删除功能
function dtlDelete(row) {
    var decode = JSON.parse(Base64.decode(row));
    var data = {};
    //暂时这样用   busiNo没有回送
    data.busiNo = decode.busi_NO;
    data.operTp = '3'
    data.projName = decode.proj_NAME;
    data.subSer = decode.sub_SER;
    data.oweMonth = decode.owe_MONTH;
    top.$.jBox.confirm("是否要删除该账单明细?", "系统提示", function (v, h, f) {
        if (v == "ok") {
            sendDeReq(ctx + '/offline/data/dtl_modify', data, false, true, () => {
                showTip("删除成功", "success", 1000, 10);
                refreshTable();
            })
        }
    });

}

//退费
function refund(row) {
    //保存该数据  详情页展示
    window.parent.document.getElementById('comDiv').innerHTML = Base64.decode(row);
    //放置此次查询条件 详情页返回时使用
    $querydate.innerHTML = JSON.stringify($from.serializeObject());
    parent.window.$('a[href^=\'#tab_info\']').attr('url', ctx + `/offline_detail/page/info`);
    parent.window.$('a[href^=\'#tab_info\']').click();
}

function exportDtl() {
    $.ajax({
        url: ctx + '/offline/data/export',
        type: 'POST',
        data: $from.serializeObject()
    });

}

// 发送请求
function sendDeReq(url, data, isRefresh, tableId, successMethod, errorMthod) {
    let result;
    $.ajax({
        url: url,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        dataType: 'json',
        async: false,
        success: function (data, textStatus) {
            if (data && data.returnCode && '0000' == data.returnCode) {
                if (isRefresh == undefined ? true : typeof isRefresh != 'boolean' ? true : isRefresh) {
                    $('#' + tableId ? tableId : 'smsIndexTable').bootstrapTable('refreshOptions', queryParams);
                }
                if (typeof data == 'string') {
                    data = JSON.parse(data);
                }
                result = data.data;
                if (successMethod) {
                    successMethod(result);
                }
                return;
            }
            showContent('操作失败:[' + data && data.message ? data.message : '' + ']', 'error');
            if (errorMthod) {
                errorMthod();
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            showContent('请求发生错误,未能成功获取响应结果', 'error');
            if (errorMthod) {
                errorMthod();
            }
        }
    });
    return result;
}
function exportMergeDtl() {
    $.ajax({
        url: ctx + '/offline/data/exportByPayNo',
        type: 'POST',
        data: $from.serializeObject()
    });
}



