let $table;
let $from;
let busiM = new Map();


$(function () {
    parent.window.$('#iframe_list').show();
    window.parent.document.getElementById('comDiv').innerHTML = '';
    //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务 20-一卡通
    $table = $('#merTable');
    $from = $('#from');
    initInstTable();
    chgHeight();
    $('#qryBtn').click(function () {
        refreshTable();
    });

    $('#export').click(function () {
        expExcel();
    });

});

// //获取当前登录用户的可操作业务
// function busiNo() {
//     //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务
//     const busiInfo = busiSelect($("select[name='busiNo']"), '01');
//     if (busiInfo != undefined && busiInfo != null) {
//         busiInfo.forEach(item => {
//             busiM.set(item.busiNo, item.busiName);
//         });
//     }
//     // setI("busiName", busiM.get(getS("BUSI_NO")));
// }

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
    const url = ctx + '/ykt/data/merQry';
    const columns = tableColumns();
    let config = SmartWeb.bootstrapTable.constructor('#merTable', columns, url, queryParams);
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

        {field: 'brch_NAME', title: '所属机构'},
        {field: 'busi_NO', title: '业务编号'},
        {field: 'busi_NAME', title: '业务名称'},
        {field: 'entr_ACCT', title: '清算账户'},
        {field: 'entr_ACCT_NAME', title: '清算账户名称'},
        {field: 'entr_ADDR', title: '所属地区',formatter: (value, row) => {
                const addr = row.entr_ADDR;
                return addr.replaceAll('-','');
            }
        },
        {field: 'entr_TEL_NO', title: '咨询电话'},
        {field: 'pay_INFO', title: '清算周期',formatter: (value, row) => {
                const payInfo = row.pay_INFO;
                let payInfos = payInfo.split('|');
                if(payInfos[0] == 'Y'){
                    return 'T1清算'
                }
                if(payInfos[0] == 'N'){
                    return 'D1清算'
                }
            }
        },
        {field: 'open_STAT', title: '商户状态',formatter: (value, row) => {
                const operStat = row.open_STAT;
                if(operStat == 'Y'){
                    return '已上架';
                }
                if(operStat == 'N'){
                    return '已下架';
                }
            }
        },
        {field: 'action', title: '操作', formatter: action}

    ];
}


// const btnInfos = [{text: '详情', act: 'info'},{text: '修改', act: 'update'}];
const btnInfos = [{text: '修改', act: 'update'}];

function action(value, row, index) {
    let btnhtml = '';
    btnInfos.forEach(btnInfo => {
        btnhtml += `<button class='unformatter lb1' type="button" onclick="${btnInfo.act}('${Base64.encode(JSON.stringify(row))}' )">${btnInfo.text}</button>`
    });
    return btnhtml;
}


function update(row) {
    window.parent.document.getElementById('comDiv').innerHTML = Base64.decode(row);
    parent.window.$('a[href^=\'#tab_update\']').attr('url', ctx + `/ykt_mer_mng//page/update`);
    parent.window.$('a[href^=\'#tab_update\']').click();
}


function expExcel() {
    const data = $from.serializeObject();
    data.start = 1;
    data.limit = 10;
    top.$.jBox.confirm("是否要导出商户信息?", "系统提示", function (v, h, f) {
        if (v == "ok") {
            showLoading();
            $("#from").attr("action", ctx + "/ykt/data/exportMerData");
            $("#from").submit();
            $.ajax({
                url: ctx + '/ykt/data/exportMerData',
                type: 'POST',
                data: $from.serializeObject()
            });
        }
    });
}
