let $from;
let busiM = new Map();

$(function () {
    parent.window.$('#iframe_add').show();
    $from = $('#from');
    var encodeData = window.parent.document.getElementById('comDiv').innerText;
    var data = JSON.parse(encodeData);
    console.info(data);
    setI('busiNo', data.busi_NO);
    //todo 记录中暂无数据 从列表参数中获取
    // setI('busiNo', getI('busiNo'));
    setI('busiName', data.busi_NAME);
    // setI('busiName', '111');
    console.info('=================================>', getI('busiName'));
    setI('projName', data.proj_NAME);
    setI('oweMonth', data.owe_MONTH);
    document.querySelector("form").addEventListener('submit', function (event) {
        event.preventDefault(); // 阻止表单默认的提交行为
        // if (projNameCheck == true && projDescCheck == true) {
        save();
        // } else {
        //     showTip("请检查必填项是否已填写", "error", 2000, 100);
        // }
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
        data.operTp = '1';
        console.info(data);
        sendPostOfAjax(ctx + '/offline/data/dtl_modify', data, false, true, () => {
            //添加成功
            parent.window.$('a[href^=\'#tab_list\']').click();
        });
    })
}

