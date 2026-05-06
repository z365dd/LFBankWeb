let $from;
let nameChk;
let phoneNoChk;

$(function () {
    parent.window.$('#iframe_update').show();
    $from = $('#from');
    var encodeData = window.parent.document.getElementById('comDiv').innerText;
    const data = JSON.parse(encodeData);


    setI('busiNo', data.busi_NO);
    setI('busiName', data.busi_NAME);
    setI('name', data.name);
    setI('certNo', data.cert_NO);
    setI('phoneNo', data.phone_NO);
    setS('stat', data.stat);


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


    /*返回按钮*/
    $('#back').click(function () {
        back();
    });




    /**
     * 表单提交
     */
    document.querySelector("form").addEventListener('submit', function (event) {
        event.preventDefault(); // 阻止表单默认的提交行为
        if (nameChk && phoneNoChk) {
            save();
        } else {
            const name = getI('name');
            const phoneNo = getI('phoneNo');
            if(name.length === 0 || phoneNo.length === 0 ){
                showTip("请检查必填项是否已填写", "error", 2000, 100);
            }else{
                showTip("请检查必填项填写格式是否正确", "error", 2000, 100);
            }
        }

    });


});

//返回列表查询
function back() {
    parent.window.$('a[href^=\'#tab_list\']').click();
}


/**
 * 保存修改
 */
function save() {
    confirmx('是否保存', function () {
        let data = $("#from").serializeObject();
        //修改
        data.operTp = '2'

        sendPostOfAjax(ctx + '/union/data/user_modify', data, false, true, () => {
            //添加成功
            parent.window.$('a[href^=\'#tab_list\']').click();
        });
    })
}




