let $table;
let $from;
let $querydate;
let busiM = new Map();
let tpMap = new Map();
let statMap = new Map();
let userId;
let brchId;

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






$(function () {

    parent.window.$('#iframe_list').show();
    window.parent.document.getElementById('comDiv').innerHTML = '';
    $querydate = window.parent.document.getElementById('comQuery');
    $table = $('#projTable');
    $from = $('#from');

    //项目状态初始化
    statMap = projStatMap();
    //项目类型初始化
    tpMap = projTpMap();

    const dataInfo = sendGetOfAjax(ctx + '/comQuery/data/getLoginUserInfo', false);

    userId = dataInfo.id;
    brchId = dataInfo.brch;
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
    const url = ctx + '/proj/data/proj_mng_list';
    const columns = tableColumns();
    let config = SmartWeb.bootstrapTable.constructor('#projTable', columns, url, queryParams);
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
        {
            field: 'proj_TP', title: '项目类型', formatter: (value, row) => {
                const projTp = row.proj_TP;
                return getOrDefaltOfMap(tpMap, projTp);
            }
        },
        {field: 'cust_MNG_NAME', title: '拓展人'},
        {
            field: 'mng_FILE_NAME', title: '资料', formatter: (value, row) => {
                return `<button class='unformatter lb1' onclick="preview(${row.proj_TP},'${row.cust_NAME}',${row.brch})">预览</button> 
                        <button class='unformatter lb1' onclick="downloadFile(${row.proj_TP},'${row.cust_NAME}',${row.brch})">下载</button>`
            }
        },
        {
            field: 'stat', title: '状态', formatter: (value, row) => {
                const stat = row.stat;
                return getOrDefaltOfMap(statMap, stat);
            }
        },
        {field: 'action', title: '操作', formatter: action}
    ];
}

const btnInfos = [{text: '修改', act: 'info'}, {text: '分行审批', act: 'dtlModify'},
    {text: '总行审批', act: 'dtlDelete'}, {text: '详情', act: 'refund'}];
3
function action(value, row) {
    let btnhtml = '';
    btnhtml += `<button class='unformatter lb1' onclick="info('${Base64.encode(JSON.stringify(row))}',false)">详情</button>`
    btnhtml += `<button class='unformatter lb1' style="display:${row.stat === '00' && userId === row.appr_NAME ? '' : 'none'} " onclick="projMod('${Base64.encode(JSON.stringify(row))}' )">修改</button>`
    btnhtml += `<button class='unformatter lb1' style="width:72px;display:${row.stat === '10' && brchId !== '1'? '' : 'none'} " onclick="info('${Base64.encode(JSON.stringify(row))}',true )">分行审批</button>`
    btnhtml += `<button class='unformatter lb1' style="width:72px;display:${row.stat === '20' && brchId === '1'? '' : 'none'} " onclick="info('${Base64.encode(JSON.stringify(row))}',true)">总行审批</button>`

    return btnhtml;
}

/**
 * 跳转详情页面
 * @param row
 * @param isChk  是否展示审批按钮
 */
function info(row, isChk) {
    var decodeRow = JSON.parse(Base64.decode(row));
    decodeRow.isChk = isChk;
    //保存该数据  详情页展示
    window.parent.document.getElementById('comDiv').innerHTML = JSON.stringify(decodeRow);
    //放置此次查询条件 详情页返回时使用
    $querydate.innerHTML = JSON.stringify($from.serializeObject());
    parent.window.$('a[href^=\'#tab_info\']').attr('url', ctx + `/proj_mng/page/info`);
    parent.window.$('a[href^=\'#tab_info\']').click();
}


//修改
function projMod(row) {
    window.parent.document.getElementById('comDiv').innerHTML = Base64.decode(row);
    //放置此次查询条件 新增页面返回时使用
    $querydate.innerHTML = JSON.stringify($from.serializeObject());
    parent.window.$('a[href^=\'#tab_update\']').attr('url', ctx + `/proj_mng/page/modify`);
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

function preview(projTp, custName, brch) {

    // 调用方式保持不变
    $.ajax({
        url: ctx + `/proj/data/getFileStream?projTp=${projTp}&custName=${custName}&brch=${brch}`,
        method: 'GET',
        xhrFields: {
            responseType: 'blob'
        },
        success: previewImage,  // 直接使用函数引用
        error: function (xhr) {
            console.error('请求失败:', xhr.statusText);
        }
    });
}


function previewImage(blob) {
    // 创建 FileReader 对象
    const reader = new FileReader();

    // 读取完成回调
    reader.onload = function (e) {
        // 生成 Data URL
        const dataUrl = e.target.result;

        // 在新标签页打开图片
        const newWindow = window.open();
        if (newWindow) {
            newWindow.document.write(`
                <html>
                    <head>
                        <title>图片预览</title>
                        <style>
                            body { margin: 0; display: flex; justify-content: center; align-items: center; min-height: 100vh; }
                            img { max-width: 100%; max-height: 100vh; box-shadow: 0 2px 10px rgba(0,0,0,0.2); }
                        </style>
                    </head>
                    <body>
                        <img src="${dataUrl}">
                    </body>
                </html>
            `);
            newWindow.document.close();
        }
    };

    // 错误处理
    reader.onerror = function () {
        console.error('图片读取失败');
        if (window.DEBUG_MODE) {
            alert('图片加载失败，请检查文件格式');
        }
    };

    // 开始读取文件
    reader.readAsDataURL(blob);
}










