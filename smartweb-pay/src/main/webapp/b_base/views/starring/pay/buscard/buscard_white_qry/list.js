let $table;
let $from;
let busiM = new Map();

$(function () {
    parent.window.$('#iframe_list').show();

    //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务
    const busiInfo = busiSelect($("select[name='busiNo']"), '100');

    $table = $('#whiteListTable');
    $from = $('#from');

    if (busiInfo != undefined || busiInfo != null) {
        busiInfo.forEach(item => {
            busiM.set(item.busiNo, item.busiName);
        });
    }
    busiNo();
    $('#qryBtn').click(function () {
        refreshTable();
    });
    initInstTable();
    chgHeight();
});

function busiNo() {
    if (busiM == undefined || busiM.size==0) {
        //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务
        const busiInfo = busiSelect($("select[name='busiNo']"), '100');
        busiInfo.forEach(item => {
            busiM.set(item.busiNo, item.busiName);
        });
    }
    setI("busiName", busiM.get(getS("MERT_NO")));
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
    const url = ctx + '/bus/data/whiteList';
    const columns = tableColumns();
    let config = SmartWeb.bootstrapTable.constructor('#whiteListTable', columns, url, queryParams);
    config = SmartWeb.bootstrapTable.extendConfig(config, {
        height: 350,
        pageSize: 10,
        responseHandler: res => {
            total = 0;
            rows = [];
            if (res.data != undefined && res.data != null) {
                const data = res.data;
                console.info(data);
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
        {field: 'cust_NO', title: '客户号'},
        {field: 'acct', title: '账号'},
        {field: 'acct_NAME', title: '账户名称'},
        {field: 'app_ID', title: '应用标识号'},
        {field: 'cert_TP', title: '证件类型', formatter: (value, row) => {
                const certTp = row.cert_TP;
                if(certTp == '10'){
                    return '身份证';
                }
                if(certTp == '11'){
                    return '户口簿';
                }
                if(certTp == '12'){
                    return '护照';
                }
                if(certTp == '13'){
                    return '军官证';
                }
                if(certTp == '14'){
                    return '士兵证';
                }
                if(certTp == '15'){
                    return '港澳居民来往内地通行证';
                }
                if(certTp == '16'){
                    return '台湾同胞来往内地通行证';
                }
                if(certTp == '17'){
                    return '临时身份证';
                }
                if(certTp == '18'){
                    return '外国人居留证';
                }
                if(certTp == '19'){
                    return '警官证';
                }
                if(certTp == '1A'){
                    return '香港身份证';
                }
                if(certTp == '1B'){
                    return '澳门身份证';
                }
                if(certTp == '1C'){
                    return '台湾身份证';
                }
                if(certTp == '1D'){
                    return '边民出入境通行证';
                }
                if(certTp == '1E'){
                    return '中华人民共和国港澳居民居住证';
                }
                if(certTp == '1F'){
                    return '中华人民共和国台湾居民居住证';
                }
                if(certTp == '1X'){
                    return '个人其他证件';
                }
                if(certTp == '21'){
                    return '组织机构代码';
                }
                if(certTp == '22'){
                    return '营业执照';
                }
                if(certTp == '23'){
                    return '登记证书';
                }
                if(certTp == '24'){
                    return '批文或批复';
                }
                if(certTp == '25'){
                    return '开户证明';
                }
                if(certTp == '26'){
                    return '企业名称预先核准通知书或批文';
                }
                if(certTp == '27'){
                    return '外汇许可证号码';
                }
                if(certTp == '28'){
                    return '国税税务登记证号';
                }
                if(certTp == '29'){
                    return '地税税务登记证号';
                }
                if(certTp == '2A'){
                    return '基本账户开户许可证核准号';
                }
                if(certTp == '2B'){
                    return '对外贸易备案登记证';
                }
                if(certTp == '2C'){
                    return '台港澳侨企业批准证书';
                }
                if(certTp == '2D'){
                    return '进出口企业资格证书';
                }
                if(certTp == '2E'){
                    return '外资企业外汇登记证';
                }
                if(certTp == '2F'){
                    return '金融许可证机构编码';
                }
                if(certTp == '2G'){
                    return '金融机构代码证号';
                }
                if(certTp == '2X'){
                    return '对公其他';
                }

            }},
        {field: 'cert_NO', title: '证件号码'},
        {field: 'stat', title: '状态', formatter: (value, row) => {
                const stat = row.stat;
                if (stat == 'Y') {
                    return '有效';
                }
                if (stat == 'N') {
                    return '无效';
                }
            }},
        // {field: 'action', title: '操作', formatter: action}

    ];
}

const btnInfos = [{text: '删除', act: 'deleteInfo'}, {text: '修改', act: 'update'}];

function action(value, row, index) {
    let btnhtml = '';
    btnInfos.forEach(btnInfo => {
        btnhtml += `<button class='unformatter lb1' type="button" onclick="${btnInfo.act}('${row.mert_NO}','${row.rule_NO}','${row.rule_TP}','${row.str_TIME}','${row.end_TIME}', '${row.end_TIME}')">${btnInfo.text}</button>`
    });
    return btnhtml;
}

function deleteInfo(mertNo, ruleNo, ruleTp) {
    console.info(ruleTp);
    confirmx('是否删除该记录', function () {
        del(mertNo, ruleNo, ruleTp);
        console.info("进入页面");
        refreshTable();
    })
}

function del(mertNo, ruleNo, ruleTp) {
    sendGetOfAjax(ctx + `/payTime/data/delete?MERT_NO=${mertNo}&RULE_NO=${ruleNo}&RULE_TP=${ruleTp}`, true);
}

function update(mertNo, ruleNo, ruleTp, strTime, endTime) {
    parent.window.$('a[href^=\'#tab_update\']').attr('url', ctx + `/payTime/page/update?mert_no=${mertNo}&rule_no=${ruleNo}&rule_tp=${ruleTp}&str_time=${strTime}&end_time=${endTime}`);

    parent.window.$('a[href^=\'#tab_update\']').click();
}
