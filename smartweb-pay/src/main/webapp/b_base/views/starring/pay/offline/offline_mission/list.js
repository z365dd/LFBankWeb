let $table;
let $from;
let $querydate;
let busiM = new Map();
let projM = new Map();


$(function () {

    parent.window.$('#iframe_list').show();
    $querydate = window.parent.document.getElementById('comQuery');
    $table = $('#missionTable');
    $from = $('#from');
    busiNo();
    projName();
    //详情页返回后展示最近一次查询的记录
    //详情页返回后展示最近一次查询的记录
    if ($querydate.innerHTML.length > 0) {
        var queryData = JSON.parse($querydate.innerHTML);
        setS('busiNo', queryData.busiNo);
        setS('projName', queryData.projName);
        setI('strDate', queryData.strDate);
        setI('endDate', queryData.endDate);
    }
    initInstTable();
    chgHeight();
    $('#qryBtn').click(function () {
        refreshTable();
    });
    if (busiM == undefined || busiM.size==0) {
        busiNo();
        projName();
    }
    $("#busiNo").on("change", function (e) {
        if (busiM == undefined || busiM.size==0) {
            busiNo();
        }
        projName();
    });
    //项目名称变更字段获取项目名称列表
    $("#projName").on("change", function (e) {
        if (projM == undefined || projM == undefined) {
            projName();
        }
    });

    //账单文件导入  取消按钮点击事件
    $("#cancelBtn").click(function () {
        //清空input中的文件
        var obj = document.getElementById("file");
        obj.outerHTML = obj.outerHTML;
        //将内容置空
        setI('oweMonth', '');
        setI('strDate', '');
        setI('endDate', '');
        $("#importModal").css("display", "none");
        $("#importModal").modal('hide');
    })
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

/**
 * 获取当前业务的
 */
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
    const url = ctx + '/offline/data/mission_list';
    const columns = tableColumns();
    let config = SmartWeb.bootstrapTable.constructor('#missionTable', columns, url, queryParams);
    config = SmartWeb.bootstrapTable.extendConfig(config, {
        height: 400,
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

        {field: 'proj_NAME', title: '收费项目名称'},
        {field: 'owe_MONTH', title: '收费周期'},
        {field: 'tot_AMT', title: '明细总金额'},
        {field: 'tot_NUM', title: '明细总笔数'},
        {field: 'succ_TOT_AMT', title: '已缴金额'},
        {field: 'succ_TOT_NUM', title: '已缴笔数'},
        {field: 'fail_TOT_AMT', title: '未缴金额'},
        {field: 'fail_TOT_NUM', title: '未缴笔数'},
        {field: 'str_DATE', title: '缴费开始日期'},
        {field: 'end_DATE', title: '缴费截止日期'},
        {field: 'action', title: '操作', formatter: action}
    ];
}

const btnInfos = [{text: '新增账单', act: 'addDtl'},{text: '一键通知', act: 'batNotice'},{text: '批量删除', act: 'deleteDtls'}];

function action(value, row) {
    let btnhtml = '';
    btnInfos.forEach(btnInfo => {
        btnhtml += `<button class='unformatter lb1' style="display:${row.proj_TP == '01' ? 'none' : ''};width:80px" onclick="${btnInfo.act}('${Base64.encode(JSON.stringify(row))}' )">${btnInfo.text}</button>`
    });
    return btnhtml;
}

function addDtl(row) {
    $("#importModal").modal({
        backdrop:"static",
        show:true
    });
    var decodeRow = JSON.parse(Base64.decode(row));
    $("#importModal").css("display", "block");
    $("#importModal").modal('show');
    $('#importFile').unbind('click').click(function () {
        //设置参数 将业务和批次数据传递到后端
        var files = document.getElementById("file").files;
        var file = files[0];
        var formData = new FormData();
        formData.append("file", file);
        formData.append('oweMonth', decodeRow.owe_MONTH);
        formData.append("BUSI_NO", decodeRow.busi_NO);
        formData.append('BUSI_NAME', busiM.get(decodeRow.busi_NO))
        formData.append("PROJ_NAME", decodeRow.proj_NAME);
        formData.append("STR_DATE", decodeRow.str_DATE);
        formData.append("END_DATE", decodeRow.end_DATE);

        $.ajax({
            url: ctx + "/offline/data/fileImport",
            type: "post",
            data: formData,
            contentType: false,
            processData: false,
            success: function (data) {
                console.info(data);
                if (data.returnCode !== undefined && "0000" != data.returnCode) {
                    var errMsg = data.message;
                    $("#importModal").css("display", "none");
                    $("#importModal").modal('hide');
                    showContent(errMsg, "error");
                } else {
                    showContent("文件导入成功,请稍后查询明细信息核对导入情况!");
                    $("#importModal").css("display", "none");
                    $("#importModal").modal('hide');
                    refreshTable();
                }
            }
        });
        //清空文件input的值,防止点击就上传
        var obj = document.getElementById("file");
        obj.outerHTML = obj.outerHTML;
    });
}

function deleteDtls(row) {
    var decodeRow = JSON.parse(Base64.decode(row));
    var data = {};
    data.busiNo = decodeRow.busi_NO;
    data.projName = decodeRow.proj_NAME;
    data.oweMonth = decodeRow.owe_MONTH;

    top.$.jBox.confirm("是否要进行批量删除?", "系统提示", function (v, h, f) {
        if (v == "ok") {
            sendPostOfAjax(ctx + '/offline/data/batDel', data, false, true, () => {
                showTip("删除成功", "success", 1000, 10);
                refreshTable();
            })
        }
    });
}

function batNotice(row) {
    var decodeRow = JSON.parse(Base64.decode(row));
    if(decodeRow.succ_TOT_NUM == decodeRow.tot_NUM){
        showTip("当前批次不存在未缴费明细,无法通知", "error", 3000, 10);
        return;
    }
    let date = getCurrentday();
    date = date.replaceAll('-', '');
    if(date.localeCompare(decodeRow.str_DATE)<0 || date.localeCompare(decodeRow.end_DATE)>0){
        showTip("当前日期不在该批次缴费时间段内,无法通知", "error", 3000, 10);
        return;
    }
    var data = {};
    data.busiNo = decodeRow.busi_NO;
    data.projName = decodeRow.proj_NAME;
    data.oweMonth = decodeRow.owe_MONTH;
    top.$.jBox.confirm("将对本账单的所有未缴费人员发送短信通知,请确认是否发送?", "系统提示", function (v, h, f) {
        if (v == "ok") {
            sendPostOfAjax(ctx + '/offline/data/smsBatNotice', data, false, true, () => {
                showTip("一键通知成功", "success", 1000, 10);
                refreshTable();
            })
        }
    });
}




