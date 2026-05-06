let $table;
let $from;
let busiM = new Map();
let dateValidate = true;
let dateValidate1 = true;
$(function () {

    parent.window.$('#iframe_list').show();
    //列表数据展示
    $table = $('#projTable');
    $from = $('#from');
    busiNo();
    initInstTable();
    chgHeight();
    $('#qryBtn').click(function () {
        refreshTable();
    });
    $("#busiNo").on("change", function (e) {
        if (busiM == undefined || busiM.size==0) {
            busiNo();
        }
    });

    //下载模板按钮点击事件
    $('#download').click(function () {
        top.$.jBox.confirm("是否要下载缴费项明细导入模板?", "系统提示", function (v, h, f) {
            if (v == "ok") {
                $("#from").attr("action", ctx + "/offline/data/download");
                $("#from").submit();
            }
        })
    });

    //收费周期验证
    $("#errTip").hide();
    document.getElementById("oweMonth").addEventListener("blur", function (event) {
        const oweMonth = getI("oweMonth");
        // var validate = /^[a-zA-Z0-9\u4e00-\u9f5a_-]{2,256}$/;
        if (oweMonth.trim().length == 0) {
            dateValidate = false;
            $("#errTip").show();
            return;
        }
        dateValidate = true;
        $("#errTip").hide();
    });

    //收费周期验证   按上次账单导入
    $("#errTip1").hide();
    document.getElementById("OWE_MONTH").addEventListener("blur", function (event) {
        const oweMonth = getI("OWE_MONTH");
        // var validate = /^[a-zA-Z0-9\u4e00-\u9f5a_-]{2,256}$/;
        if (oweMonth.trim().length == 0) {
            dateValidate1 = false;
            $("#errTip1").show();
            return;
        }
        dateValidate1 = true;
        $("#errTip1").hide();
    });

    //导入明细的拟态框
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
        $("#errTip").hide();
    })


});

//获取当前登录用户的可操作业务
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
    const url = ctx + '/offline/data/proj_list';
    const columns = tableColumns();
    let config = SmartWeb.bootstrapTable.constructor('#projTable', columns, url, queryParams);
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

        {field: 'busi_NAME', title: '业务名称'},

        {field: 'proj_NAME', title: '收费项目名称'},
        {
            field: 'crt_DATE', title: '创建日期', formatter: (value, row) => {
                const crtDate = row.crt_DATE;
                return crtDate.substr(0, 4) + '-' + crtDate.substr(4, 2) + '-' + crtDate.substr(6, 2);
            }
        },
        {field: 'proj_DESC', title: '项目描述'},
        {
            field: 'amt', title: '缴费金额', formatter: (value, row) => {
                const amt = row.amt;
                const tp = row.proj_TP;
                if (tp == '00') {
                    return '-';
                } else {
                    return amt;
                }
            }
        },
        {
            field: 'proj_TP', title: '项目类型', formatter: (value, row) => {
                const tp = row.proj_TP;
                if (tp == '00') {
                    return '非自主录入';
                }
                if (tp == '01') {
                    return '自主录入';
                }
            }
        },
        {field: 'owe_MONTH', title: '收费周期'},
        {field: 'action', title: '操作', formatter: action}

    ];
}

const btnInfos = [{text: '导入账单', act: 'fileImport'}, {text: '按上次账单导入', act: 'importAgain'}, {
    text: '修改',
    act: 'projMod'
}, {text: '删除', act: 'projDel'}];

// const btnInfos = [{text: '导入账单', act: 'fileImport'}, {text: '按上次账单导入', act: 'importAgain'}];

function action(value, row) {
    let btnhtml = '';
    //如果是非自主录入 才能导入账单
    const tp = row.proj_TP;
    if (tp == '00') {
        btnInfos.forEach(btnInfo => {
            btnhtml += `<button class='unformatter lb1' style="width: 90px" onclick="${btnInfo.act}('${Base64.encode(JSON.stringify(row))}' )">${btnInfo.text}</button>`
        });
    }
    return btnhtml;
}

//导入明细excel
function fileImport(row) {
    $("#importModal").modal({
        backdrop:"static",
        show:true
    });
    console.info(Base64.decode(row))
    var decodeRow = JSON.parse(Base64.decode(row));
    $("#importModal").css("display", "block");
    $("#importModal").modal('show');
    setI("BUSI_NO", decodeRow.busi_NO);
    setI('BUSI_NAME', busiM.get(decodeRow.busi_NO))
    setI("PROJ_NAME", decodeRow.proj_NAME);
    setI("PROJ_DESC", decodeRow.proj_DESC);

    $('#importFile').unbind('click').click(function () {
        if (!dateValidate) {
            $("#errTip").show();
            return;
        }
        //设置参数 将业务和批次数据传递到后端
        var files = document.getElementById("file").files;
        console.info(files);
        var file = files[0];
        var formData1 = new FormData();
        formData1.append("file", file);
        formData1.append('oweMonth', getI('oweMonth'));
        formData1.append("BUSI_NO", decodeRow.busi_NO);
        formData1.append('BUSI_NAME', busiM.get(decodeRow.busi_NO))
        formData1.append("PROJ_NAME", decodeRow.proj_NAME);
        formData1.append("PROJ_DESC", decodeRow.proj_DESC);
        formData1.append("STR_DATE", getI('strDate'));
        formData1.append("END_DATE", getI('endDate'));
        jsUpload(formData1);
    });
}


/**
 * 调用账单文件导入接口
 * @param file
 * @returns
 */
function jsUpload(formData) {
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
                setI('oweMonth', '');
                setI('strDate', '');
                setI('endDate', '');
                showContent(errMsg, "error");
            } else {
                showContent("文件导入成功,请稍后查询明细信息核对导入情况!");
                setI('oweMonth', '');
                setI('strDate', '');
                setI('endDate', '');
                $("#importModal").css("display", "none");
                $("#importModal").modal('hide');
                refreshTable();
                //设置一个隐藏  文件名称    传参
                // formData.append('FILE_NAME', resData.data);
                //调用方法
                //todo 暂时停止文件解析方法调用
                // ReadExcel(formData);
            }
        }
    });
    //清空文件input的值,防止点击就上传
    var obj = document.getElementById("file");
    obj.outerHTML = obj.outerHTML;
    dateValidate = true;
    $("#errTip").hide();
}

//读取解析文件入库  已弃用
function ReadExcel(formData) {

    $.ajaxSettings.async = false;
    $.ajax({
        url: ctx + "/offline/data/importDtl",
        type: "post",
        data: formData,
        contentType: false,
        processData: false
    });
    hideLoading();
    //表单请求 不加载页面的loadding
    // $("#importForm").attr("action", ctx + "/offline/data/importDtl")
    // $("#importForm").submit();
}


/**
 * 删除功能
 * @param row
 */
function projDel(row) {
    var decode = JSON.parse(Base64.decode(row));
    var data = {};
    //暂时这样用   busiNo没有回送
    data.busiNo = decode.busi_NO;
    data.busiName = decode.busi_NAME;
    data.projTp = decode.proj_TP;
    data.operTp = '3'
    data.projName = decode.proj_NAME;
    top.$.jBox.confirm("是否要删除该缴费项目?", "系统提示", function (v, h, f) {
        if (v == "ok") {
            sendPostOfAjax(ctx + '/offline/data/proj_modify', data, false, true, () => {
                showContent('删除成功!')
                refreshTable();
            })
        }
    });
}


/**
 * 修改功能
 * @param row
 */
function projMod(row) {
    //存放当前行内容
    window.parent.document.getElementById('comDiv').innerHTML = Base64.decode(row);
    parent.window.$('a[href^=\'#tab_update\']').attr('url', ctx + `/offline_proj/page/update`);
    parent.window.$('a[href^=\'#tab_update\']').click();
}

//按上次账单导入
function importAgain(row) {
    $("#lastModal").modal({
        backdrop:"static",
        show:true
    });
    //展示拟态框
    // $("#lastModal").css("display", "block");
    $("#lastModal").modal('show');
    var decodeRow = JSON.parse(Base64.decode(row));
    var data = {};
    data.busiNo = decodeRow.busi_NO;
    data.busiName = decodeRow.busi_NAME;
    data.projName = decodeRow.proj_NAME;

    //按照上次账单导入
    $('#repeat').unbind('click').click(function () {
        data.OWE_MONTH = getI('OWE_MONTH');
        data.STR_DATE = getI('STR_DATE');
        data.END_DATE = getI('END_DATE');
        if (dateValidate1) {
            sendPostOfAjax(ctx + '/offline/data/autoCtrl', data, false, true, () => {
                showContent('按照上次账单导入数据成功!');
                setI('OWE_MONTH', '');
                setI('STR_DATE', '');
                setI('END_DATE', '');
                // $("#lastModal").css("display", "none");
                $("#lastModal").modal('hide');
            })
        }
    });
    //按照上次账单导入的拟态框
    $("#cancelBtn1").click(function () {
        //将内容置空
        setI('OWE_MONTH', '');
        setI('STR_DATE', '');
        setI('END_DATE', '');
        // $("#lastModal").css("display", "none");
        $("#lastModal").modal('hide');
        $("#errTip1").hide();
    })
}
