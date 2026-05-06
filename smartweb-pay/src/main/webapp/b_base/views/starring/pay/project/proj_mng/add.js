let $from;
let fileExists;
let custNameChk;
let acctChk;
let custMngNameChk;

$(function () {
    parent.window.$('#iframe_add').show();
    $from = $('#from');

    $('#projTp').on("change", function (e) {
        const projTp = getS("projTp");
        console.info(projTp);
        if (projTp === '03') {
            $("#input1").show();
            $("#input2").show();
            $("#input3").show();
            setI('signDate', getCurrentday());
            setI('unsignDate', getCurrentday());
        }
    })


    //客户名称校验
    custNameChk = false;
    document.getElementById("custName").addEventListener("blur", function (event) {
        const custName = getI("custName");
        var validate = /^[a-zA-Z0-9\u4e00-\u9fa5-_]{2,30}$/;
        if (!validate.test(custName)) {
            showTip('请输入正确格式的客户名称', 'error', 1000, 0);
            custNameChk = false;
            return;
        }
        custNameChk = true;
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
    document.getElementById("custMngName").addEventListener("blur", function (event) {
        const custMngName = getI("custMngName");
        var validate = /^[a-zA-Z0-9\u4e00-\u9fa5-_]{2,30}$/;
        if (!validate.test(custMngName)) {
            showTip('请输入正确格式的拓展人', 'error', 1000, 0);
            custMngNameChk = false;
            return;
        }
        custMngNameChk = true;
    });

    document.querySelector("form").addEventListener('submit', function (event) {
        event.preventDefault(); // 阻止表单默认的提交行为
        const projTp = getS("projTp");
        if (custNameChk === true && custMngNameChk === true) {
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

    /*返回按钮*/
    $('#back').click(function () {
        back();
    });
});

//返回列表查询
function back() {
    parent.window.$('a[href^=\'#tab_list\']').click();
}


function save() {
    confirmx('是否保存', function () {
        var data = $("#from").serializeObject();
        //新增
        data.operTp = '1';
        //10-待分行审批
        data.stat = '10';

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







