let $from;
let payTypeMap = new Map();
let chnlM = new Map();
let nameValidate = true;
let acctValidate = true;
let custMngNameChk;
//全局变量 存储行数据
let rowData = {};

$(function () {
    parent.window.$('#iframe_add').show();
    $from = $('#from');
    $table = $('#apprTable');

    initInstTable();
    chgHeight();

    //支付方式
    payTypeMap = payTpMap();
    chnlM = chnlMap();

    /*返回按钮*/
    $('#back').click(function () {
        back();
    });


    $('#checkBtn').click(function () {
        const acct = getI('acct');
        const name = getI('name');
        if (name.length === 0) {
            showTip('请输入报销人姓名', 'error', 1000, 0);
            return;
        }
        if (acct.length === 0) {
            showTip('请输入报销人卡号', 'error', 1000, 0);
            return;
        }
        if (!nameValidate) {
            showTip('请输入正确格式的姓名', 'error', 1000, 0);
            return;
        }
        if (!acctValidate) {
            showTip('请输入正确的报销卡号', 'error', 1000, 0);
            return;
        }
        let data = {};
        data.operTp = '1';
        data.platDate = rowData.plat_DATE;
        data.platSeq = rowData.plat_SEQ;
        data.busiNo = rowData.busi_NO;
        data.busiName = rowData.busi_NAME;
        data.certNo = rowData.cert_NO;
        data.phoneNo = rowData.ctct_PHONE_NO;
        data.busiName = rowData.busi_NAME;
        data.stat = '02';
        data.name = name;
        data.payAcct = acct;
        sendPostOfAjax(ctx + '/union/data/appr_modify', data, false, true, () => {
            //关闭退款信息modal
            $("#checkModal").modal('hide');
            setI('acct', '');
            setI('name', '');
            //清空变量
            rowData = {};
            refreshTable();
        });

    });

    //关闭报销人账户信息填写窗口
    $("#cancelBtn").click(function () {
        //关闭退款信息modal
        $("#checkModal").modal('hide');
        setI('name', '');
        setI('acct', '');
        //清空变量
        rowData = {};
    });


    //姓名校验
    document.getElementById("name").addEventListener("blur", function (event) {
        const name = getI("name");
        var pattern = new RegExp("[`!@#$%^&*()_+~！￥（）—<>…《》/；;\":、\']");
        if (name.length === 0) {
            nameValidate = false;
            showTip("请输入报销人姓名", "error", 4000, 10);
            return;
        }
        if (name !== '') {
            if (name.length > 0 && pattern.test(name)) {
                nameValidate = false;
                showTip("请输入正确格式的姓名", "error", 4000, 10);
                return;
            }
        }
        nameValidate = true;
    });


    //账号校验
    document.getElementById("acct").addEventListener("blur", function (event) {
        const acct = getI("acct");
        console.info(acct);
        var validate = /^[0-9]{16,19}$/;
        if (!validate.test(acct)) {
            acctValidate = false;
            showTip("请输入正确格式的报销卡号", "error", 4000, 10);
            return;
        }

        acctValidate = true;
    });


});

//返回列表查询
function back() {
    parent.window.$('a[href^=\'#tab_list\']').click();
}


function chgHeight() {
    const Height = $(document.body).height();
    $(window.parent.document).find('#tab_list').find('iframe').height(Height + 80);
}


function queryParams(params) {

    let formData = $from.serializeObject();
    formData.autoDeduct = '0';
    //获取近两年的数据（按照整年计算  比如当前20250301 获取2023-01-01~2025-03-01
    let now = getCurrentday();
    const year = now.substr(0, 4);
    formData.startTime = year + "-01-01";
    formData.endTime = now;
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
    const url = ctx + '/union/data/mock_data';
    const columns = tableColumns();
    let config = SmartWeb.bootstrapTable.constructor('#apprTable', columns, url, queryParams);
    config = SmartWeb.bootstrapTable.extendConfig(config, {
        height: 550,
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
        {
            field: 'plat_DATE', title: '缴费日期', formatter: (value, row) => {
                const platDate = row.plat_DATE;
                return platDate.substr(0, 4) + '-' + platDate.substr(4, 2) + '-' + platDate.substr(6, 2);
            }
        },
        {field: 'busi_NAME', title: '单位名称'},
        {field: 'prctl_AMT', title: '缴费金额'},
        {field: 'name', title: '客户名称'},
        {field: 'cert_NO', title: '身份证号码'},
        {field: 'pay_ACCT', title: '账号'},
        {field: 'ctct_PHONE_NO', title: '手机号'},
        {
            field: 'chnl_NO', title: '缴费渠道', formatter: (value, row) => {
                const chnlNo = row.chnl_NO;
                return getOrDefaltOfMap(chnlM, chnlNo);
            }
        },
        {
            field: 'pay_TP', title: '缴费方式', formatter: (value, row) => {
                const tp = row.pay_TP;
                return getOrDefaltOfMap(payTypeMap, tp);
            }
        },
        {field: 'action', title: '操作', formatter: action}
    ];
}


function action(value, row) {
    let btnhtml = '';
    btnhtml += `<button class='unformatter lb1'  style="width:72px" onclick="preExpense('${Base64.encode(JSON.stringify(row))}' )">提交报销</button>`
    return btnhtml;
}

function preExpense(row) {
    const decodeRow = JSON.parse(Base64.decode(row));
    let data = {};
    data.platDate = decodeRow.plat_DATE;
    data.platSeq = decodeRow.plat_SEQ;
    //检查该缴费记录是否已申请报销
    sendPostOfAjaxNoShowContent(ctx + '/union/data/appr_add_check', data);
    $("#checkModal").modal({
        backdrop: "static",
        show: true
    });
    $("#checkModal").modal('show');
    rowData = decodeRow;

}




