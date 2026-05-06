let $from;
let projTp;
let custName;
let brch;
let projTpM;
let inspCycM;


/**
 * 项目类型
 * @returns {Map<any, any>}
 */
function projTpMap() {
    var map = new Map();
    map.set('01', '代收');
    map.set('02', '代付');
    map.set('03', '缴费');
    return map;
}

/**
 * 项目类型
 * @returns {Map<any, any>}
 */
function inspCycMap() {
    var map = new Map();
    map.set('01', '季度');
    map.set('02', '半年');
    map.set('03', '一年');
    return map;
}

$(function () {
    parent.window.$('#iframe_info').show();
    $from = $('#from');
    var encodeData = window.parent.document.getElementById('comDiv').innerText;
    rowData = JSON.parse(encodeData);
    $table = $('#projInspRecTable');


    projTpM = projTpMap();
    inspCycM = inspCycMap();

    projTp = rowData.proj_TP;
    custName = rowData.cust_NAME;
    brch = rowData.brch;

    setI('projTp', getOrDefaltOfMap(projTpM, projTp));
    setI('custName', custName);
    setI('acct', rowData.acct);
    setI('brchName', rowData.brch_NAME);
    setI('inspCyc', getOrDefaltOfMap(inspCycM, rowData.insp_CYC));
    setI('spclAcct', rowData.spcl_ACCT);
    setI('spclAcctName', rowData.spcl_ACCT_NAME);
    setI('agtBusiNo', rowData.agt_BUSI_NO);
    setI('inspFileName', rowData.insp_FILE_NAME);
    setI('inspDate', rowData.insp_DATE);
    setI('inspUserName', rowData.insp_USER_NAME);


    initInstTable();
    chgHeight();

    /*返回按钮*/
    $('#back').click(function () {
        back();
    });

    $('#downloadFile').click(function () {
        downloadFile(projTp, custName, brch);
    });

});


function chgHeight() {
    const Height = $(document.body).height();
    $(window.parent.document).find('#tab_list').find('iframe').height(Height + 80);
}


function queryParams(params) {

    let formData = $from.serializeObject();
    formData.projTp = projTp;
    formData.brch = brch;
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

const tableColumns = () => {
    return [
        {field: 'insp_DATE', title: '巡检日期'},
        {field: 'insp_USER_NAME', title: '巡检人员'}
    ];
}


const initInstTable = () => {
    const url = ctx + '/proj/data/insp_rec_list';
    const columns = tableColumns();
    let config = SmartWeb.bootstrapTable.constructor('#projInspRecTable', columns, url, queryParams);
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


//返回列表查询
function back() {
    parent.window.$('a[href^=\'#tab_list\']').click();
}

/**
 * 流式传输
 * @param projTp
 * @param custName
 * @param brch
 */
function downloadFile(projTp, custName, brch) {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', ctx + `/proj/data/getFileStream?projTp=${projTp}&custName=${custName}&brch=${brch}`);
    xhr.responseType = 'blob';

    xhr.onload = function () {
        if (xhr.status === 200) {
            // 从响应头解析文件名
            const contentDisposition = xhr.getResponseHeader('Content-Disposition');
            let fileName = 'download_file'; // 默认文件名

            if (contentDisposition) {
                // 处理两种文件名格式（RFC 5987编码优先）
                const utf8FilenameMatch = contentDisposition.match(/filename\*=UTF-8''(.+?)(;|$)/i);
                if (utf8FilenameMatch) {
                    fileName = decodeURIComponent(utf8FilenameMatch[1]);
                } else {
                    const basicFilenameMatch = contentDisposition.match(/filename="?(.+?)"?(;|$)/i);
                    if (basicFilenameMatch) {
                        fileName = basicFilenameMatch[1].replace(/["']/g, '');
                    }
                }
            }

            const blob = xhr.response;
            const url = window.URL.createObjectURL(blob);
            const link = document.createElement('a');
            link.href = url;
            link.download = fileName;
            link.style.display = 'none';
            document.body.appendChild(link);
            link.click();
            // 清理资源
            setTimeout(() => {
                document.body.removeChild(link);
                window.URL.revokeObjectURL(url);
            }, 100);
        } else {
            console.error('下载失败，状态码：', xhr.status);
            // 可以在这里添加错误提示逻辑
        }
    };

    xhr.onerror = function () {
        console.error('网络请求失败');
        // 可以在这里添加网络错误提示
    };

    xhr.send();
}


