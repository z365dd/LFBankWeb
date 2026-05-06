let $table;
let $from;
let $querydate;
let busiM = new Map();
let tranStatMap = new Map();
let autoDeductMap = new Map();
let payTypeMap = new Map();

$(function () {

    parent.window.$('#iframe_list').show();
    window.parent.document.getElementById('comDiv').innerHTML = '';
    $querydate = window.parent.document.getElementById('comQuery');
    //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务
    busiNo();
    $table = $('#payDetailsTable');
    $from = $('#from');
    tranStatMap = tranStatSelect($("select[name='txStat']"), true);
    // chnkMap = chnlMap();
    // payTypeMap = payTpMap();
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
        setS("autoDeduct", queryData.autoDeduct);
    }
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

    $('#getPdf').click(function () {
        let currentRows = $("#payDetailsTable").bootstrapTable('getSelections');
        console.info(currentRows);
        top.$.jBox.confirm("是否要生成选中数据的收据?", "系统提示", function (v, h, f) {
            if (v == "ok") {
                showLoading();
                //字段名/字段值
                setI('selectedData', JSON.stringify(currentRows));
                $("#from").attr("action", ctx + "/offline/data/exportPdf");
                $("#from").submit();
                //axios请求在产品中有loading页面的前处理和后处理 通过调用该方法实现loading页面的关闭
                exportPdf();
            }
        })
    });
    $("#payDetailsTable").on("checkbox")

    $('#exportExcel').click(function () {
        top.$.jBox.confirm("是否要导出excel?", "系统提示", function (v, h, f) {
            if (v == "ok") {
                showLoading();
                //字段名/字段值
                $("#from").attr("action", ctx + "/offline/data/exportExcel");
                $("#from").submit();
                //axios请求在产品中有loading页面的前处理和后处理 通过调用该方法实现loading页面的关闭
                exportExcel();
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
    return [
        {field: 'checked', checkbox: true},
        {
            field: 'plat_DATE', title: '缴费日期', formatter: (value, row) => {
                const platDate = row.plat_DATE;
                return platDate.substr(0, 4) + '-' + platDate.substr(4, 2) + '-' + platDate.substr(6, 2);
            }
        },
        {field: 'stu_CLASS', title: '班级'},
        {field: 'pay_NO', title: '学号'},
        {field: 'name', title: '学生姓名'},
        {field: 'tot_AMT', title: '缴费金额'},
        {field: 'prctl_AMT', title: '实付金额'},
        {field: 'amt', title: '银行优惠'},
        {field: 'dct_AMT', title: '商户补贴',formatter: (value, row) => {
                const dctAMT = row.dct_AMT;
                const amt = row.amt;
                return accSub(dctAMT, amt);
            }
        },
        {
            field: 'tran_TP', title: '缴费类型', formatter: (value, row) => {
                const tranTp = row.tran_TP;
                if (tranTp == '02') {
                    return '缴费';
                }
                if (tranTp == '03') {
                    return '退款';
                }
            }
        },
        {
            field: 'tran_STAT', title: '交易状态', formatter: (value, row) => {
                const stat = row.tran_STAT;
                return getOrDefaltOfMap(tranStatMap, stat);
            }
        }
    ];
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
    parent.window.$('a[href^=\'#tab_detail\']').attr('url', ctx + `/payDetails/page/info`);
    parent.window.$('a[href^=\'#tab_detail\']').click();
}

function clearNoNum(obj) {
    obj.value = obj.value.replace(/[^\d.]/g, ""); //清除“数字”和“.”以外的字符
    obj.value = obj.value.replace(/\.{2,}/g, "."); //只保留第一个. 清除多余的
    obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3');//只能输入两个小数
    if (obj.value.indexOf(".") < 0 && obj.value != "") {//以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额
        obj.value = parseFloat(obj.value);
    }
}

function exportPdf() {
    $.ajax({
        url: ctx + '/offline/data/exportPdf',
        type: 'POST',
        data: $from.serializeObject()
    });
}

function exportExcel() {
    $.ajax({
        url: ctx + '/offline/data/exportExcel',
        type: 'POST',
        data: $from.serializeObject()
    });
}
