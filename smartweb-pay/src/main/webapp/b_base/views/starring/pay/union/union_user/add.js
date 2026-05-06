let $from;
let nameChk;
let certNoChk;
let phoneNoChk;

$(function () {
    parent.window.$('#iframe_add').show();
    $from = $('#from');

    //客户名称校验
    nameChk = false;
    document.getElementById("name").addEventListener("blur", function (event) {
        const name = getI("name");
        const validate = /^[a-zA-Z0-9\u4e00-\u9fa5-_]{2,30}$/;
        if (!validate.test(name)) {
            showTip('请输入正确格式的客户名称', 'error', 1000, 0);
            nameChk = false;
            return;
        }
        nameChk = true;
    });


    //证件号码校验
    certNoChk = false;
    document.getElementById("certNo").addEventListener("blur", function (event) {
        const certNo = getI("certNo");
        const validate = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
        if (!validate.test(certNo)) {
            showTip('请输入正确格式的身份证号码', 'error', 1000, 0);
            certNoChk = false;
            return;
        }
        certNoChk = true;
    });


    //手机号校验
    phoneNoChk = false;
    document.getElementById("phoneNo").addEventListener("blur", function (event) {
        const phoneNo = getI("phoneNo");
        const validate = /^1[3-9]\d{9}$/;
        if (!validate.test(phoneNo)) {
            showTip('请输入正确格式的手机号', 'error', 1000, 0);
            phoneNoChk = false;
            return;
        }
        phoneNoChk = true;
    });

    document.querySelector("form").addEventListener('submit', function (event) {
        event.preventDefault(); // 阻止表单默认的提交行为
        if (nameChk === true && phoneNoChk === true && certNoChk === true) {
            save();
        } else {
            const name = getI('name');
            const certNo = getI('certNo');
            const phoneNo = getI('phoneNo');
            if(name.length === 0 || certNo.length === 0 ||phoneNo.length === 0 ){
                showTip("请检查必填项是否已填写", "error", 2000, 100);
            }else{
                showTip("请检查必填项填写格式是否正确", "error", 2000, 100);
            }
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
        let data = $("#from").serializeObject();
        //新增
        data.operTp = '1';
        data.validFlg = '1';

        sendPostOfAjax(ctx + '/union/data/user_modify', data, false, true, () => {
            //添加成功
            parent.window.$('a[href^=\'#tab_list\']').click();
        });
    })
}







