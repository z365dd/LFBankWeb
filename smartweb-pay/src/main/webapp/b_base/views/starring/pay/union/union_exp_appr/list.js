let $table;
let $from;
let $querydate;
let busiM = new Map();
let apprStatM = new Map();
let amtCheck = true;
let rowData = {};
let platDate = '';
let platSeq = '';
/**
 * 审批状态
 * @returns {Map<any, any>}
 */
function apprStatMap() {
    var map = new Map();
    map.set('00', '待提交');
    map.set('01', '审批中');
    map.set('02', '审批通过');
    map.set('03', '待补充材料');
    map.set('04', '拒收');
    return map;
}

/**
 * 跳转新增报销页面
 */
function addApproveRecord() {
    parent.window.$('a[href^=\'#tab_add\']').attr('url', ctx + `/union_exp_appr/page/add`);
    parent.window.$('a[href^=\'#tab_add\']').click();
}

$(function () {

    parent.window.$('#iframe_list').show();
    window.parent.document.getElementById('comDiv').innerHTML = '';
    $querydate = window.parent.document.getElementById('comQuery');
    $table = $('#apprTable');
    $from = $('#from');

    //审批状态
    apprStatM = apprStatMap();

    initInstTable();
    chgHeight();


    $('#qryBtn').click(function () {
        initInstTable();
    });
    $('#addBtn').click(function () {
        addApproveRecord();
    });

    $('#save').click(function () {
        const amt = getI('amt');
        if(!amtCheck){
            showTip('请检查输入的报销金额是否正确', 'error', 1000, 0);
            return;
        }
        let data = {};
        data.operTp = '2';``
        data.platDate = rowData.plat_DATE;
        data.platSeq = rowData.plat_SEQ;
        data.stat = '02';
        data.amt = amt;
        sendPostOfAjax(ctx + '/union/data/appr_modify', data, false, true, () => {
            //关闭退款信息modal
            $("#expenseModal").modal('hide');
            setI('amt', '');
            //清空变量
            rowData = {};
            refreshTable();
        });
    });

    //关闭退款信息
    $("#cancelBtn").click(function () {
        //关闭退款信息modal
        $("#expenseModal").modal('hide');
        setI('amt', '');
        //清空变量
        rowData = {};
    });



    //收费项目名称校验
    document.getElementById("amt").addEventListener("blur", function (event) {
        const amt = getI("amt");
        if (amt < 0.01) {
            showTip('报销金额不能为0', 'error', 1000, 0);
            amtCheck = false;
            return;
        }
        var validate = /^\d+(\.\d{1,2})?$/;
        if (!validate.test(amt)) {
            showTip('请输入正确的金额格式', 'error', 1000, 0);
            amtCheck = false;
            return;
        }
        amtCheck = true;
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
    const url = ctx + '/union/data/appr_list';
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
        {field: 'reg_DATE', title: '申请日期'},
        {field: 'busi_NAME', title: '单位名称'},
        {field: 'name', title: '客户名称'},
        {field: 'cert_NO', title: '身份证号码'},
        {field: 'phone_NO', title: '手机号码'},
        {field: 'pay_ACCT', title: '报销卡号'},
        {
            field: 'cert_URL', title: '身份证', formatter: (value, row) => {
                const fileType = row.cert_URL.slice(-3);
                const url = Base64.encode(row.cert_URL);

                return `<button class='unformatter lb1' onclick="preview('${url}','${fileType}')">预览</button> 
                        <button class='unformatter lb1' style="display:${row.stat === '03' || row.cert_URL === undefined || row.cert_URL === '' ? '' : 'none'} " onclick="uploadFile1('${row.plat_DATE}','${row.plat_SEQ}')">上传</button>`
            }
        },
        {
            field: 'pay_ACCT_URL', title: '银行卡', formatter: (value, row) => {
                const fileType = row.pay_ACCT_URL.slice(-3);
                const url = Base64.encode(row.pay_ACCT_URL);

                return `<button class='unformatter lb1' onclick="preview('${url}','${fileType}')">预览</button> 
                        <button class='unformatter lb1' style="display:${row.stat === '03' ||row.pay_ACCT_URL === undefined || row.pay_ACCT_URL === '' ? '' : 'none'} " onclick="uploadFile2()">上传</button>`
            }
        },
        {
            field: 'case_HIS_URL', title: '病历', formatter: (value, row) => {
                const url = Base64.encode(row.case_HIS_URL);

                return `<button class='unformatter lb1' onclick="downloadFile('${url}')">预览</button> 
                        <button class='unformatter lb1' style="display:${ row.stat === '03' || row.case_HIS_URL === undefined || row.case_HIS_URL === '' ? '' : 'none'} " onclick="uploadFile3()">上传</button>`
            }
        },
        {
            field: 'bill_URL', title: '票据', formatter: (value, row) => {
                const url = Base64.encode(row.bill_URL);

                return `<button class='unformatter lb1' onclick="downloadFile('${url}')">预览</button> 
                        <button class='unformatter lb1' style="display:${row.stat === '03' ||row.bill_URL === undefined || row.bill_URL === '' ? '' : 'none'} " onclick="uploadFile4()">上传</button>`
            }
        },
        {
            field: 'stat', title: '审批状态', formatter: (value, row) => {
                const stat = row.stat;
                return getOrDefaltOfMap(apprStatM, stat);
            }
        },
        {field: 'amt', title: '报销金额'},
        {field: 'action', title: '操作', formatter: action}

    ];
}


function action(value, row) {
    let btnhtml = '';
    btnhtml += `<button class='unformatter lb1' style="width:72px;display:${row.stat === '00' || row.stat === '03' +
    '' ? '' : 'none'}" onclick="approve('${Base64.encode(JSON.stringify(row))}')">提交申请</button>`
    btnhtml += `<button class='unformatter lb1' style="display:${row.stat === '01' ? '' : 'none'} " onclick="pass('${Base64.encode(JSON.stringify(row))}')">通过</button>`
    btnhtml += `<button class='unformatter lb1' style="width:72px;display:${row.stat === '01' ? '' : 'none'} " onclick="reSubmit('${Base64.encode(JSON.stringify(row))}',true )">补充材料</button>`
    btnhtml += `<button class='unformatter lb1' style="display:${row.stat === '01' ? '' : 'none'} " onclick="refuse('${Base64.encode(JSON.stringify(row))}',true)">拒收</button>`

    return btnhtml;
}


/**
 * 提交审批
 * @param row
 */
function approve(row) {
    var decodeRow = JSON.parse(Base64.decode(row));
    if(platDate !== decodeRow.plat_DATE || platSeq !== decodeRow.plat_SEQ){
        showContent("上传报销材料与当前提交选择的报销人不一致，请重新上传材料后提交申请", "error");
        platDate = '';
        platSeq = '';
        clearFile();
        return;
    }

    var files1 = document.getElementById('file1').files;
    var file1 = files1[0];
    var files2 = document.getElementById('file2').files;
    var file2 = files2[0];
    var files3 = document.getElementById('file3').files;
    var file3 = files3[0];
    var files4 = document.getElementById('file4').files;
    var file4 = files4[0];

    if (undefined === file1) {
        showContent("请上传身份证", "error");
        return;
    }
    if (undefined === file2) {
        showContent("请上传银行卡", "error");
        return;
    }
    if (undefined === file3) {
        showContent("请上传病历", "error");
        return;
    }
    if (undefined === file4) {
        showContent("请上传票据", "error");
        return;
    }
    if(file1.name.slice(-3) !== 'pdf' && file1.name.slice(-3) !== 'jpg' && file1.name.slice(-3) !== 'png'){
        showContent("身份证只能上传图片或者pdf格式的文件", "error");
        clearFile();
        return;
    }
    if(file2.name.slice(-3) !== 'pdf' && file2.name.slice(-3) !== 'jpg' && file2.name.slice(-3) !== 'png'){
        showContent("银行卡只能上传图片或者pdf格式的文件", "error");
        return;
    }
    if(file3.name.slice(-3) !== 'pdf'){
        showContent("请上传pdf格式的病历", "error");
        return;
    }
    if(file4.name.slice(-3) !== 'pdf'){
        showContent("请上传pdf格式的票据", "error");
        return;
    }

    var formData = new FormData();
    formData.append("certFile", file1);
    formData.append("payAcctFile", file2);
    formData.append("caseHisFile", file3);
    formData.append("billFile", file4);
    formData.append("platDate", decodeRow.plat_DATE);
    formData.append("busiNo", decodeRow.busi_NO);
    formData.append("certNo", decodeRow.cert_NO);

    flg = false;
    $.ajax({
        url: ctx + "/union/data/uploadFile",
        type: "post",
        data: formData,
        contentType: false,
        processData: false,
        success: function (data) {
            console.info(data);
            if (data.returnCode !== undefined && "0000" != data.returnCode) {
                var errMsg = data.message;
                showContent(errMsg, "error");
            } else {
                const fileInfos = data.data.data;
                var data = {};
                data.operTp = '2';
                data.platDate = decodeRow.plat_DATE;
                data.platSeq = decodeRow.plat_SEQ;
                data.stat = '01';
                data.certUrl = Base64.encode(fileInfos.certFileName);
                data.payAcctUrl = Base64.encode(fileInfos.payAcctFileName);
                data.caseHisUrl = Base64.encode(fileInfos.caseHisFileName);
                data.billUrl = Base64.encode(fileInfos.billFileName);

                sendPostOfAjax(ctx + '/union/data/appr_modify', data, false, true, () => {
                    refreshTable();
                });
            }
        }
    });

    //清空文件input的值,防止点击就上传
    clearFile();

}

/**
 * 清空文件
 */
function clearFile(){
    var obj1 = document.getElementById("file1");
    obj1.outerHTML = obj1.outerHTML;
    var obj2 = document.getElementById("file2");
    obj2.outerHTML = obj2.outerHTML;
    var obj3 = document.getElementById("file3");
    obj3.outerHTML = obj3.outerHTML;
    var obj4 = document.getElementById("file4");
    obj4.outerHTML = obj4.outerHTML;
}

/**
 * 通过
 * @param row
 */
function pass(row) {
    $("#expenseModal").modal({
        backdrop: "static",
        show: true
    });
    $("#expenseModal").modal('show');
    var decodeRow = JSON.parse(Base64.decode(row));
    //全局变量赋值
    rowData = decodeRow;
}


/**
 * 补充材料
 * @param row
 */
function reSubmit(row) {
    var decodeRow = JSON.parse(Base64.decode(row));
    var data = {};
    data.operTp = '2';
    data.platDate = decodeRow.plat_DATE;
    data.platSeq = decodeRow.plat_SEQ;
    data.stat = '03';
    sendPostOfAjax(ctx + '/union/data/appr_modify', data, false, true, () => {
        refreshTable();
    });
}

/**
 * 拒收
 * @param row
 */
function refuse(row) {
    var decodeRow = JSON.parse(Base64.decode(row));
    var data = {};
    data.operTp = '2';
    data.platDate = decodeRow.plat_DATE;
    data.platSeq = decodeRow.plat_SEQ;
    data.stat = '04';
    sendPostOfAjax(ctx + '/union/data/appr_modify', data, false, true, () => {
        refreshTable();
    });

}

/**
 * 图片预览
 * @param url
 * @param fileTpye
 */
function preview(url,fileTpye) {
    let data = {};
    data.url = url;
    //如果是pdf类型 直接进入下载方法
    if(fileTpye === 'pdf'){
        downloadFile(url)
    }else{
        // 调用方式保持不变
        $.ajax({
            url: ctx + `/union/data/getFileStream`,
            method: 'POST',
            data: data,
            xhrFields: {
                responseType: 'blob'
            },
            success: previewImage,  // 直接使用函数引用
            error: function (xhr) {
                console.error('请求失败:', xhr.statusText);
            }
        });
    }

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


function uploadFile1(date,seq) {
    document.getElementById('file1').click();
    platDate = date;
    platSeq = seq;
}

function uploadFile2() {
    document.getElementById('file2').click();
}

function uploadFile3() {
    document.getElementById('file3').click();
}

function uploadFile4() {
    document.getElementById('file4').click();
}


/**
 * 流式传输
 * @param projTp
 * @param custName
 * @param brch
 */
function downloadFile(url) {
    const xhr = new XMLHttpRequest();
    xhr.open('POST', ctx + '/union/data/getFileStream');
    xhr.responseType = 'blob';
    // 设置表单编码的请求头
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    // 构造请求参数
    const params = new URLSearchParams();
    params.append('url', url);

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

    xhr.send(params.toString());
}




