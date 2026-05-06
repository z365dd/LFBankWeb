let $from;
let areas = new Map();

let busiNoFlg;
let busiNameFlg;
let acctFlg;
let acctNameFlg;
let phoneNoFlg;
let nameFlg;


$(function () {
    parent.window.$('#iframe_add').show();
    $from = $('#from');
    //获取省id和省名称
    const info = getProvince(ctx + "/prod/oper/entrDemo/area?parentId=1");
    info.forEach(item => {
        areas.set(item.id, item.name);
    });

    setSelect1("province", "/prod/oper/entrDemo/area?parentId=1", "id", "name", undefined, false);
    //设置默认河北省
    setS("province", '38');
    setSelect1("city", `/prod/oper/entrDemo/area?parentId=${getS('province')}`, "name", "name", undefined, false);
    setS("city", '廊坊市');
    setI('provinceName', areas.get(getS('province')));
    $("#province").on("change", function (e) {
        setSelect1("city", `/prod/oper/entrDemo/area?parentId=${getS('province')}`, "name", "name", undefined, false);
        setI('provinceName', areas.get(getS('province')));
    });
    /* $("#officeId").val("600001");
     $("#officeName").val("廊坊银行总行");*/


    //默认机构归属于总行
    setI('officeId', '600001');
    setI('officeName', '廊坊银行总行');


    /*返回按钮*/
    $('#back').click(function () {
        back();
    });

    // 工会编号校验
    busiNoFlg = false;
    $("#busiNoErrTip").hide();
    document.getElementById("busiNo").addEventListener("blur", function (event) {
        var validate = /^[A-Za-z0-9]{8,20}$/;
        const busiNo = getI("busiNo");
        if (busiNo == undefined || busiNo.length == 0) {
            busiNoFlg = false;
            $("#busiNoErrTip").text(" 工会编号不能为空");
            $("#busiNoErrTip").show();
            return;
        }
        if (busiNo.length > 0) {
            if (!validate.test(busiNo)) {
                busiNoFlg = false;
                $("#busiNoErrTip").text("只能输入英文、数字，长度为8-20位");
                $("#busiNoErrTip").show();
                return;
            }
        }
        busiNoFlg = true;
        $("#busiNoErrTip").hide();
    });


    // 工会名称校验
    busiNameFlg = false;
    $("#busiNameErrTip").hide();
    document.getElementById("busiName").addEventListener("blur", function (event) {
         var validate = new RegExp("[`!@#$%^&*_+~！￥—<>…《》/；;\":、\']");
            const busiName = getI("busiName");
            if (busiName == undefined || busiName.length == 0) {
                busiNameFlg = false;
                $("#busiNameErrTip").text(" 工会名称不能为空");
                $("#busiNameErrTip").show();
                return;
            }
            if (busiName.length > 0) {
                if (validate.test(busiName)) {
                    busiNameFlg = false;
                    $("#busiNameErrTip").text("请输入正确格式的 工会名称");
                    $("#busiNameErrTip").show();
                    return;
                }
            }
            busiNameFlg = true;
            $("#busiNameErrTip").hide();
        });


        //清算账户校验
        acctFlg = false;
        $("#acctErrTip").hide();
        document.getElementById("payAcct").addEventListener("blur", function (event) {
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
            acctNameFlg = false;
            $("#acctNameErrTip").hide();
            document.getElementById("payAcctName").addEventListener("blur", function (event) {

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
            document.getElementById("phoneNo").addEventListener("blur", function (event) {
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
            document.getElementById("name").addEventListener("blur", function (event) {
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
                if (busiNoFlg && busiNameFlg && acctFlg && acctNameFlg && phoneNoFlg && nameFlg) {
                    save();
                } else {
                    showTip("请检查必填项是否已正确填写", "error", 2000, 100);
                }
            });
        });


        function save() {
            confirmx('是否保存', function () {
                const data = $("#from").serializeObject();
                //保存
                data.operStat = '1';
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

