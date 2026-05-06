let $table;
let $from;
let $querydate;
let busiM = new Map();
let signStatMap = new Map();
let chnkMap = new Map();

$(function () {

    parent.window.$('#iframe_list').show();
    $querydate = window.parent.document.getElementById('comQuery');
    //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务
    const busiInfo = busiSelect($("select[name='busiNo']"), '00');
    $table = $('#signBatTable');
    $from = $('#from');
    if (busiInfo != undefined && busiInfo != null) {
        busiInfo.forEach(item => {
            busiM.set(item.busiNo, item.busiName);
        });
    }
    // setI("strDate", getYesterday());
    setI("strDate", '');
    setI("endDate", '');
    signStatMap = signStatSelect($("select[name='signStat']"));
    chnkMap = chnlMap();
    initInstTable();
    chgHeight();
    $('#qryBtn').click(function () {
        refreshTable();
    });
    $("#busiNo").on("change", function (e) {
        busiNo();
    });
    $('#export').click(function () {
        top.$.jBox.confirm("是否要导出签约查询数据?", "系统提示", function (v, h, f) {
            if (v == "ok") {
                showLoading();
                $("#from").attr("action", ctx + "/signBatQry/data/export");
                $("#from").submit();
                //axios请求在产品中有loading页面的前处理和后处理 通过调用该方法实现loading页面的关闭
                exportExcel();
            }
        })

    });
});

function busiNo() {
    if (busiM == undefined || busiM.size==0) {
        //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务
        const busiInfo = busiSelect($("select[name='busiNo']"), '00');
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
    const url = ctx + '/signBatQry/data/list';
    const columns = tableColumns();
    let config = SmartWeb.bootstrapTable.constructor('#signBatTable', columns, url, queryParams);
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
        {field: 'sign_BRCH', title: '签约机构'},
        {field: 'sign_PROT_NO', title: '协议编号'},
        {field: 'busi_NAME', title: '缴费单位'},
        {
            field: 'chnl_NO', title: '渠道名称', formatter: (value, row) => {
                const chnlNo = row.chnl_NO;
                return getOrDefaltOfMap(chnkMap, chnlNo);
            }
        },
        {field: 'oth_CUST_NO', title: '用户号'},
        {field: 'oth_CUST_NAME', title: '用户名称'},
        {field: 'addr', title: '住户地'},
        {
            field: 'acct', title: '签约卡号', formatter: (value, row) => {
                return row.acct_NODE[0].acct;
            }
        },
        {
            field: 'sign_DATE', title: '签约日期', formatter: (value, row) => {
                var signDate = row.sign_DATE;
                if ('undefined' == signDate || null == signDate || '' == signDate) {
                    return '';
                } else {
                    return signDate.substr(0, 4) + '-' + signDate.substr(4, 2) + '-' + signDate.substr(6, 2);
                }
            }
        },
        {
            field: 'sign_STAT', title: '签约状态', formatter: (value, row) => {
                const stat = row.sign_STAT;
                return getOrDefaltOfMap(signStatMap, stat);
            }
        },
        {
            field: 'cancl_SIGN_DATE', title: '解约日期', formatter: (value, row) => {
                var canclSignDate = row.cancl_SIGN_DATE;
                if ('undefined' == canclSignDate || null == canclSignDate || '' == canclSignDate) {
                    return '';
                } else {
                    return canclSignDate.substr(0, 4) + '-' + canclSignDate.substr(4, 2) + '-' + canclSignDate.substr(6, 2);
                }

            }
        },
    ];
}
function exportExcel() {
    $.ajax({
        url: ctx + '/signBatQry/data/export',
        type: 'POST',
        data: $from.serializeObject()
    });
}


