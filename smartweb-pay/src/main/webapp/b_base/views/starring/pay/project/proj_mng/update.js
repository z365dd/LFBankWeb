let $from;
let projTpM = new Map();
let projTp;
let fileName;
let acctChk;

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
    parent.window.$('#iframe_update').show();
    $from = $('#from');
    var encodeData = window.parent.document.getElementById('comDiv').innerText;
    const data = JSON.parse(encodeData);
    projTpM = projTpMap();

    projTp = data.proj_TP;
    if (projTp === '03') {
        $("#input1").show();
        $("#input2").show();
        $("#input3").show();
    }
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
    fileName = data.mng_FILE_NAME;
    document.getElementById('fileName').textContent = fileName;
    setI('mngFileName', fileName);
    setS('inspCyc', data.insp_CYC);
    setI('stat', data.stat);


    //点击a标签下载已有文件
    document.getElementById('downloadLink').addEventListener('click', function (event) {
        event.preventDefault(); // 阻止默认行为
        const custName = getI('custName');
        const brch = getI('brch');
        downloadFile(projTp, custName, brch);
    });


    //客户账号校验
    acctChk = false;
    document.getElementById("acct").addEventListener("blur", function (event) {
        const acct = getI("acct");
        var validate = /^[a-zA-Z0-9-_]{2,30}$/;
        if (!validate.test(acct)) {
            showTip('请输入正确格式的客户名称', 'error', 1000, 0);
            acctChk = false;
            return;
        }
        acctChk = true;
    });



    //拓展人校验
    custMngNameChk = false;
    document.getElementById('custMngName').addEventListener("blur", function (event) {
        const custMngName = getI("custMngName");
        var validate = /^[a-zA-Z0-9\u4e00-\u9fa5-_]{2,30}$/;
        if (!validate.test(custMngName)) {
            // this.placeholder = '请输入正确格式的拓展人';
            // this.style.border = '1px solid #ffcccc';
            showTip('请输入正确格式的拓展人', 'error', 1000, 0);
            custMngNameChk = false;
            return;
        }else{
            this.style.border = '';
        }
        custMngNameChk = true;
    });
    // // 当用户开始输入时，恢复原始placeholder
    document.getElementById('custMngName').addEventListener('focus', function() {
        this.placeholder = '';
    });

    /*返回按钮*/
    $('#back').click(function () {
        back();
    });

    /**
     * 更换文件事件
     */
    document.getElementById('file').addEventListener('change', function (e) {
        const file = e.target.files[0];
        if (file) {
            // 更新显示信息
            document.getElementById('fileName').textContent = file.name;

            // 这里可以添加文件验证逻辑
            if (file.size > 5 * 1024 * 1024) {
                alert('文件大小不能超过5MB');
                this.value = ''; // 清空选择
            }
        }
    });


    /**
     * 表单提交
     */
    document.querySelector("form").addEventListener('submit', function (event) {
        event.preventDefault(); // 阻止表单默认的提交行为
        if (custMngNameChk === true) {
            if (projTp === '03') {
                if (acctChk === true) {
                    save();
                } else {
                    showTip("请检查必填项是否已填写", "error", 2000, 100);
                }
            } else {
                save();
            }
        } else {
            showTip("请检查必填项是否已填写", "error", 2000, 100);
        }

    });


});

function showFilePicker() {
    document.getElementById('file').click();
}

//返回列表查询
function back() {
    parent.window.$('a[href^=\'#tab_list\']').click();
}


/**
 * 保存
 */
function save() {
    confirmx('是否保存', function () {
        var data = $("#from").serializeObject();
        //修改
        data.operTp = '2'
        //将
        let stat = getI('stat');
        if (stat === '00') {
            stat = '10';
        }
        data.stat = stat;
        var files = document.getElementById('file').files;
        var file = files[0];
        if (file === undefined || files.length === 0) {
            showContent("请上传文件", "error");
            return;
        }
        //获取保存在服务器上文件名称
        data.mngFileName = file.name;


        // 创建FormData对象
        /*const formData = new FormData();
        formData.append('file', file);
        formData.append("projTp", getS('projTp'));
        formData.append("custName", getI('custName'));
        formData.append("brch", getI('brch'));
        const info = sendPostOfAjax(ctx + '/proj/data/uploadFile', formData);
        data.id = info.id;
        console.info('上传影像文件到影像平台成功');*/

        sendPostOfAjax(ctx + '/proj/data/proj_mng_modify', data, false, true, () => {
            //添加成功
            parent.window.$('a[href^=\'#tab_list\']').click();
        });
    })
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
        // 可以在这里添加网络错误提示
        console.error('网络请求失败');
    };

    xhr.send();
}

