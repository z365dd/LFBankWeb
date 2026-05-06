let $table;
let $from;
let $querydate;
let busiM = new Map();
let tpMap = new Map();
let statMap = new Map();
let signStatM = new Map();
let currentDay;

/**
 * 项目状态
 * @returns {Map<any, any>}
 */
function projStatMap() {
    var map = new Map();
    map.set('00', '待提交审批');
    map.set('10', '待分行初审');
    map.set('20', '待总行审批');
    map.set('30', '审批成功');
    return map;
}

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
 * 签约状态
 * @returns {Map<any, any>}
 */
function signStatMap() {
    var map = new Map();
    map.set('00', '初始状态');
    map.set('10', '已签约');
    map.set('20', '已解约');
    return map;
}

$(function () {

    parent.window.$('#iframe_list').show();
    window.parent.document.getElementById('comDiv').innerHTML = '';
    $querydate = window.parent.document.getElementById('comQuery');
    $table = $('#projInspTable');
    $from = $('#from');

    //项目状态初始化
    statMap = projStatMap();
    //项目类型初始化
    tpMap = projTpMap();
    //签约状态初始化
    signStatM = signStatMap();

    //获取当前日期   yyyyMMdd
    currentDay = getCurrentday().replaceAll("-",'');

    initInstTable();
    chgHeight();


    $('#qryBtn').click(function () {
        initInstTable();
    });

});

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
    const url = ctx + '/proj/data/proj_chk_list';
    const columns = tableColumns();
    let config = SmartWeb.bootstrapTable.constructor('#projInspTable', columns, url, queryParams);
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


const tableColumns = () => {
    return [
        {field: 'brch_NAME', title: '所属机构'},
        {field: 'cust_NAME', title: '客户名称'},
        {field: 'acct', title: '客户账号'},
        {field: 'agt_BUSI_NO', title: '代理业务号'},
        {
            field: 'proj_TP', title: '项目类型', formatter: (value, row) => {
                const projTp = row.proj_TP;
                return getOrDefaltOfMap(tpMap, projTp);
            }
        },
        {
            field: 'sign_DATE', title: '协议日期', formatter: (value, row) => {
                if ('' === row.sign_DATE || '' === row.unsign_DATE) {
                    return '';
                }
                return row.sign_DATE + '-' + row.unsign_DATE;
            }
        },
        {field: 'lst_INSP_DATE', title: '最晚巡检日期',formatter:(value,row,index) =>{
            if(value === undefined ||value === ''){
                return '';
            }
            const isRed = getDaysBetweenDates(value, currentDay);
            if (isRed <= 10) {
                return '<span style="color: red;">' + value + '</span>';
            }
            return value;
            }},
        {
            field: 'insp_FILE_NAME', title: '巡检资料', formatter: (value, row) => {
                const inspFileName = row.insp_FILE_NAME;
                const lstInspDate = row.lst_INSP_DATE;

                if (inspFileName === '' || inspFileName === undefined) {
                    return '未上传';
                } else {
                    //巡检资料不为空 最晚巡检日期一定不为空
                    const isRed = getDaysBetweenDates(lstInspDate, currentDay);
                    if (isRed <= 10) {
                        return '<span style="color: red;">已上传</span>';
                    }else{
                        return '已上传';
                    }
                }

            }
        },
        {field: 'insp_DATE', title: '巡检日期',formatter:(value,row) =>{
                const lstInspDate = row.lst_INSP_DATE;
                if(undefined === value || '' === value){
                    return '';
                }
                const isRed = getDaysBetweenDates(lstInspDate, currentDay);
                if (isRed <= 10) {
                    return '<span style="color: red;">' + value + '</span>';
                }
                return value;
            }},
        {
            field: 'sign_STAT', title: '状态', formatter: (value, row) => {
                const stat = row.sign_STAT;
                return getOrDefaltOfMap(signStatM, stat);
            }
        },

        {field: 'action', title: '操作', formatter: action}
    ];
}

/**
 * 拼接操作列
 * @param value
 * @param row
 * @returns {string}
 */
function action(value, row) {
    let btnhtml = '';
    btnhtml += `<button class='unformatter lb1' onclick="info('${Base64.encode(JSON.stringify(row))}')">详情</button>`
    btnhtml += `<button class='unformatter lb1' style="width:72px;" onclick="inspMod('${Base64.encode(JSON.stringify(row))}')">巡检录入</button>`
    btnhtml += `<button class='unformatter lb1' style="width:80px;display:${'' === row.insp_FILE_NAME || undefined === row.insp_FILE_NAME ? 'none' : ''} " 
            onclick="downloadFile('${row.proj_TP}', '${row.cust_NAME}','${row.brch}')">巡检材料下载</button>`

    return btnhtml;
}

/**
 * 跳转详情页面
 * @param row
 */
function info(row) {
    var decodeRow = JSON.parse(Base64.decode(row));
    //保存该数据  详情页展示
    window.parent.document.getElementById('comDiv').innerHTML = JSON.stringify(decodeRow);
    //放置此次查询条件 详情页返回时使用
    parent.window.$('a[href^=\'#tab_info\']').attr('url', ctx + `/proj_chk/page/info`);
    parent.window.$('a[href^=\'#tab_info\']').click();
}


//修改
function inspMod(row) {
    var decodeRow = JSON.parse(Base64.decode(row));
    window.parent.document.getElementById('comDiv').innerHTML = JSON.stringify(decodeRow);

    parent.window.$('a[href^=\'#tab_update\']').attr('url', ctx + `/proj_chk/page/modify`);
    parent.window.$('a[href^=\'#tab_update\']').click();
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











