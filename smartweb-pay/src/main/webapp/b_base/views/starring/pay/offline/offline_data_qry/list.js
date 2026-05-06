let $table;
let $from;
let busiM = new Map();


$(function () {

    parent.window.$('#iframe_list').show();

    $table = $('#table');
    $from = $('#from');

    //选择框内容查询
    busiNo();

    initInstTable();
    chgHeight();
    $('#qryBtn').click(function () {
        initInstTable();
    });


    $('#export').click(function () {
        top.$.jBox.confirm("是否要导出数据查询数据?", "系统提示", function (v, h, f) {
            if (v == "ok") {
                showLoading();
                $("#from").attr("action", ctx + "/offline/data/exportTotData");
                $("#from").submit();
                //axios请求在产品中有loading页面的前处理和后处理 通过调用该方法实现loading页面的关闭
                exportDtl();
            }
        })
    });


});

function busiNo() {
    if (busiM == undefined || busiM.size == 0) {
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

    let formData = $from.serializeObject();
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
    const url = ctx + '/offline/data/dataQry';
    const columns = tableColumns();
    let config = SmartWeb.bootstrapTable.constructor('#table', columns, url, queryParams);
    config = SmartWeb.bootstrapTable.extendConfig(config, {
        height: 550,
        pageSize: 10,
        responseHandler: res => {
            total = 0;
            rows = [];
            if (res.data != undefined && res.data != null) {
                const data = res.data;
                console.info(data);
                total = data.total;
                rows = data.data.list;
                setI('succTotNum', data.data.totNum);
                setI('succTotAmt', data.data.totAmt);
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
        {field: 'name', title: '所属机构'},
        {field: 'busi_NO', title: '业务编号'},
        {field: 'busi_NAME', title: '业务名称'},
        {field: 'bankcard_NUM', title: '本行卡笔数'},
        {field: 'bankcard_AMT', title: '本行卡金额'},
        {field: 'tot_NUM', title: '成功缴费笔数'},
        {field: 'tot_AMT', title: '成功缴费金额'},
    ];
}


function exportDtl() {
    $.ajax({
        url: ctx + '/offline/data/exportTotData',
        type: 'POST',
        data: $from.serializeObject()
    });

}


