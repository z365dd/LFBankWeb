let $from;
let amtCheck;

$(function () {
    parent.window.$('#iframe_add').show();
    $from = $('#from');

    const currentDay = getCurrentday();
    setI('year', currentDay.substr(0, 4));
    setI('strDate', currentDay);
    setI('endDate', currentDay);



    amtCheck = false;
    //金额校验
    document.getElementById("amt").addEventListener("blur", function (event) {
        const amt = getI("amt");
        var validate = /(^[1-9](\d+)?(\.\d{1,2})?$)|(^0$)|(^\d\.\d{1,2}$)/;
        if (amt == 0 || amt == 0.00 || amt == 0.0) {
            amtCheck = false;
            $("#amtError").text('输入的金额必须大于0');
            $("#amtError").show();
            return;
        }
        if (!validate.test(amt)) {
            amtCheck = false;
            $("#amtError").text('请输入正确的金额格式');
            $("#amtError").show();
            return;
        }
        amtCheck = true;
        $("#amtError").hide();
    });

    document.querySelector("form").addEventListener('submit', function (event) {
        event.preventDefault(); // 阻止表单默认的提交行为
        if (amtCheck === true) {
            save();
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
        if (file === 'undefied' || files.length === 0) {
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
        const info = sendPostOfAjax(ctx + '/proj/data/proj_mng_modify', formData);
        data.id = info.id;
        console.info('上传影像文件到影像平台成功');*/
        sendPostOfAjax(ctx + '/proj/data/proj_mng_modify', data, false, true, () => {
            //添加成功
            parent.window.$('a[href^=\'#tab_list\']').click();
        });
    })
}







