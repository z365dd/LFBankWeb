let $from;
let projTpM = new Map();
let projTp;
let fileName;
let amtCheck;


$(function () {
    parent.window.$('#iframe_update').show();
    $from = $('#from');
    var encodeData = window.parent.document.getElementById('comDiv').innerText;
    const data = JSON.parse(encodeData);


    setI('year', data.year);
    strDate = data.str_DATE;
    strDate = strDate.substr(0, 4) + '-' + strDate.substr(4, 2) + '-' + strDate.substr(6, 2);
    setI('strDate', strDate);
    endDate = data.end_DATE;
    endDate = endDate.substr(0, 4) + '-' + endDate.substr(4, 2) + '-' + endDate.substr(6, 2);
    setI('endDate', endDate);
    setI('amt', data.amt);


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


    /*返回按钮*/
    $('#back').click(function () {
        back();
    });


    /**
     * 表单提交
     */
    document.querySelector("form").addEventListener('submit', function (event) {
        event.preventDefault(); // 阻止表单默认的提交行为
        if (amtCheck === true) {
            save();
        }
    });


});



