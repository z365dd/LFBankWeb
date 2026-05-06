let $from;
let projStatM = new Map();
let projTpM = new Map();
let inspCycM = new Map();
let projTp;
let userId;
let stat;


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
    data = JSON.parse(encodeData);
    console.info(data);


    //获取用户名
    userId = sendGetReqOfUserId();
    if(userId === data.appr_NAME && data.isChk){
        const passBtn = document.getElementById('pass');
        const refuseBtn = document.getElementById('refuse');
        if (passBtn.style.display === 'none') {
            passBtn.style.display = 'block'; // 显示按钮
        }
        if (refuseBtn.style.display === 'none') {
            refuseBtn.style.display = 'block'; // 显示按钮
        }
    }


    //枚举值初始化
    projStatM = projStatMap();
    projTpM = projTpMap();
    inspCycM = inspCycMap();

    //项目信息初始化
    projTp = data.proj_TP;
    setI('projTp', getOrDefaltOfMap(projTpM, projTp));
    setI('custName', data.cust_NAME);
    setI('brch', data.brch);
    setI('brchName', data.brch_NAME);
    setI('acct', data.acct);
    setI('custMngName', data.cust_MNG_NAME);
    let signDate = data.sign_DATE;
    setI('signDate', signDate === '' ? '' : signDate.substr(0, 4) + '-' + signDate.substr(4, 2) + '-' + signDate.substr(6, 2));
    let unsignDate = data.unsign_DATE
    setI('unsignDate', unsignDate === '' ? '' :unsignDate.substr(0, 4) + '-' + unsignDate.substr(4, 2) + '-' + unsignDate.substr(6, 2));
    document.getElementById('fileName').textContent = data.mng_FILE_NAME;
    fileName = data.mng_FILE_NAME;
    setI('mngFileName', fileName);
    let inspCyc = data.insp_CYC;
    setI('inspCyc', getOrDefaltOfMap(inspCycM, inspCyc));
    //状态设置全局变量
    stat = data.stat;
    setI('stat', getOrDefaltOfMap(projStatM,stat));


    //点击a标签下载已有文件
    document.getElementById('downloadLink').addEventListener('click', function (event) {
        event.preventDefault(); // 阻止默认行为
        const custName = getI('custName');
        const brch = getI('brch');
        downloadFile(projTp, custName, brch);
    });



    //通过按钮触发事件 审批状态修改到下一状态
    document.getElementById('pass').addEventListener('click', function (event) {
        event.preventDefault(); // 阻止默认行为
        var data = {
            operTp:'2',
            projTp : projTp,
            custName : getI('custName'),
            brch : getI('brch'),
        };
        //通过后审批状态修改到下一状态
        stat = stat === '10' ? '20' : '30';
        data.stat = stat;
        sendPostOfAjax(ctx + '/proj/data/proj_mng_modify', data, false, true, () => {
            //添加成功
            parent.window.$('a[href^=\'#tab_list\']').click();
        });
    });


    //拒绝按钮触发事件 审批状态修改到00-待提交审批
    document.getElementById('refuse').addEventListener('click', function (event) {
        event.preventDefault(); // 阻止默认行为
        var data = {
            operTp:'2',
            projTp : projTp,
            custName : getI('custName'),
            brch : getI('brch'),
            stat : '00'
        };
        sendPostOfAjax(ctx + '/proj/data/proj_mng_modify', data, false, true, () => {
            //添加成功
            parent.window.$('a[href^=\'#tab_list\']').click();
        });
    });

    /*返回按钮*/
    $('#back').click(function () {
        back();
    });
});


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




