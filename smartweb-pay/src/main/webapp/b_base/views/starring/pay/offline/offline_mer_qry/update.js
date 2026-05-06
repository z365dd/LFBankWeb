let $from;
//区域
let areas = new Map();
let areasReverse = new Map();

let busiNameFlg = true;
let acctFlg = true;
let acctNameFlg = true;
let phoneNoFlg = true;
let nameFlg = true;

$(function () {
    parent.window.$('#iframe_update').show();
    $from = $('#from');
    var encodeData = window.parent.document.getElementById('comDiv').innerText;
    var data = JSON.parse(encodeData);
    console.info(data);

    setI('busiNo', data.busi_NO);
    data.busiNo = data.busi_NO;
    //查询导入的商户是否已经导入过明细   返回的res.data是明细的条数
    const res = getNum(ctx + '/offline/data/countDtl', data, false, true);
    if(res.data > 0){
        document.getElementById('busiName').readOnly = true;
    }else{
        document.getElementById('busiName').readOnly = false;
    }

    setI('busiName', data.busi_NAME);
    $("#officeId").val(data.brch_ID);
    $("#officeName").val(data.brch_NAME);
    setI('payAcct', data.entr_ACCT);
    setI('payAcctName', data.entr_ACCT_NAME);
    let payInfos = data.pay_INFO.split('|');
    setI('clrCycle', payInfos[0] == 'Y' ? 'T1清算' : 'D1清算');
    setI('sepaFlg', payInfos[1] == 'Y' ? '是' : '否');
    setS('openStat', data.open_STAT);

    setI('phoneNo', data.entr_TEL_NO);
    setI('name', data.name);


    const info = getProvince(ctx + "/prod/oper/entrDemo/area?parentId=1");
    info.forEach(item => {
        areasReverse.set(item.name, item.id);
        areas.set(item.id, item.name);
    });
    let addrList = data.entr_ADDR.split('-');



    setSelect1("province", "/prod/oper/entrDemo/area?parentId=1", "id", "name", undefined, false);
    //设置默认河北省
    setS("province", areasReverse.get(addrList[0]));
    setSelect1("city", `/prod/oper/entrDemo/area?parentId=${getS('province')}`, "name", "name", undefined, false);
    setS("city", addrList[1]);
    //码值转换
    setI('provinceName', addrList[0]);
    $("#province").on("change", function (e) {
        setSelect1("city", `/prod/oper/entrDemo/area?parentId=${getS('province')}`, "name", "name", undefined, false);
        setI('provinceName', areas.get(getS('province')));
    });
    setI('detailedAddress',addrList[2]);
    /*返回按钮*/
    $('#back').click(function () {
        back();
    });

    //业务名称校验
    $("#busiNameErrTip").hide();
    $("#busiName").on("blur", function (event) {
        var validate = new RegExp("[`!@#$%^&*_+~！￥—<>…《》/；;\":、\']");
        const busiName = getI("busiName");
        if (busiName == undefined || busiName.length == 0) {
            busiNameFlg = false;
            $("#busiNameErrTip").text("业务名称不能为空");
            $("#busiNameErrTip").show();
            return;
        }
        if (busiName.length > 0) {
            if (validate.test(busiName)) {
                busiNameFlg = false;
                $("#busiNameErrTip").text("请输入正确格式的业务名称");
                $("#busiNameErrTip").show();
                return;
            }
        }
        busiNameFlg = true;
        $("#busiNameErrTip").hide();
    });


    //清算账户校验
    $("#acctErrTip").hide();
    $("#payAcct").on("blur", function (event) {
        var validate = /^[\d-]{8,32}$/;
        const payAcct = getI("payAcct");
        if (payAcct == undefined || payAcct.length == 0) {
            acctFlg = false;
            $("#acctErrTip").text("清算账户不能为空");
            $("#acctErrTip").show();
            return;
        }
        if (payAcct.length > 0) {
            if (!validate.test(payAcct)) {
                acctFlg = false;
                $("#acctErrTip").text("请输入正确格式的清算账户");
                $("#acctErrTip").show();
                return;
            }
        }
        acctFlg = true;
        $("#acctErrTip").hide();
    });

    //清算账户名称校验
    $("#acctNameErrTip").hide();
    $("#payAcctName").on("blur", function (event) {
        var validate = new RegExp("[`!@#$%^&*_+~！￥—<>…《》/；;\":、\']");
        const payAcctName = getI("payAcctName");
        if (payAcctName == undefined || payAcctName.length == 0) {
            acctNameFlg = false;
            $("#acctNameErrTip").text("清算账户不能为空");
            $("#acctNameErrTip").show();
            return;
        }
        if (payAcctName.length > 0) {
            if (validate.test(payAcctName)) {
                acctNameFlg = false;
                $("#acctNameErrTip").text("请输入正确格式的清算账户");
                $("#acctNameErrTip").show();
                return;
            }
        }
        acctNameFlg = true;
        $("#acctNameErrTip").hide();
    });

    //所属地区校验

    //咨询电话校验
    $("#phoneNoErrTip").hide();
    $("#phoneNo").on("blur", function (event) {
        var mobileReg = /^1[3-9]\d{9}$/;
        var telReg = /^0\d{2,3}-\d{7,8}$/;
        var regExp = /^[\u4e00-\u9fff]{1,10}$/;

        const phoneNo = getI("phoneNo");
        if (phoneNo == undefined || phoneNo.length == 0) {
            phoneNoFlg = false;
            $("#phoneNoErrTip").text("咨询电话不能为空");
            $("#phoneNoErrTip").show();
            return;
        }
        if (phoneNo.length > 0) {
            if (!mobileReg.test(phoneNo) && !telReg.test(phoneNo) && !regExp.test(phoneNo)) {
                phoneNoFlg = false;
                $("#phoneNoErrTip").text("请输入正确格式的咨询电话");
                $("#phoneNoErrTip").show();
                return;
            }
        }
        phoneNoFlg = true;
        $("#phoneNoErrTip").hide();
    });

    //联系人校验
    $("#nameErrTip").hide();
    $("#name").on("blur", function (event) {
        var validate = new RegExp("[`!@#$%^&*_+~()！￥—<>…（）《》/；;\":、\']");
        const name = getI("name");
        if (name == undefined || name.length == 0) {
            nameFlg = false;
            $("#nameErrTip").text("联系人不能为空");
            $("#nameErrTip").show();
            return;
        }
        if (name.length > 0) {
            if (validate.test(name)) {
                nameFlg = false;
                $("#nameErrTip").text("请输入正确格式的联系人");
                $("#nameErrTip").show();
                return;
            }
        }
        nameFlg = true;
        $("#nameErrTip").hide();
    });

    document.querySelector("form").addEventListener('submit', function (event) {
        event.preventDefault(); // 阻止表单默认的提交行为
        if (busiNameFlg && acctFlg && acctNameFlg && phoneNoFlg && nameFlg) {
            save();
        } else {
            showTip("请检查必填项是否已正确填写", "error", 2000, 100);
        }
    });
});


function save() {
    confirmx('是否保存修改', function () {
        const data = $("#from").serializeObject();
        //保存
        data.operStat = '2';
        sendPostOfAjax(ctx + '/offline/data/merModify', data, false, true, () => {
            //添加成功
            parent.window.$('a[href^=\'#tab_list\']').click();
        });
    })
}

//返回列表查询
function back() {
    parent.window.$('a[href^=\'#tab_list\']').click();
}

function getNum(url, data, isRefresh, tableId, successMethod, errorMthod) {
    let result;
    $.ajax({
        url: url,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        dataType: 'json',
        async: false,
        success: function (data, textStatus) {
            if (data && data.returnCode && '0000' == data.returnCode) {
                if (isRefresh == undefined ? true : typeof isRefresh != 'boolean' ? true : isRefresh) {
                    $('#' + tableId ? tableId : 'smsIndexTable').bootstrapTable('refreshOptions', queryParams);
                }
                if (typeof data == 'string') {
                    data = JSON.parse(data);
                }
                result = data.data;
                if (successMethod) {
                    successMethod(result);
                }
                return;
            }
            showContent('操作失败:[' + data && data.message ? data.message : '' + ']', 'error');
            if (errorMthod) {
                errorMthod();
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            showContent('请求发生错误,未能成功获取响应结果', 'error');
            if (errorMthod) {
                errorMthod();
            }
        }
    });
    return result;

};