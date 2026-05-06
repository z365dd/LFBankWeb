let busiM = new Map();
let $from;
$(function () {
    const busiInfo = busiSelect($("select[name='busiNo']"), '100');
    if (busiInfo == undefined || busiInfo == null) {

        showTip('该机构没有对应的合作商户，请使用合作商户对应的开户机构或上级机构用户进行查看', 'error', 1000, 0);
        showContent('错误信息[10000:错误信息[该机构没有对应的合作商户，请使用合作商户对应的开户机构或上级机构用户进行查看]]', 'error');
    }
    $("#changePasswordBtn").click(function () {
        $("#NomertInfo").css("hide", "block");
    });
    if (busiInfo != undefined || busiInfo != null) {
        busiInfo.forEach(item => {
            busiM.set(item.busiNo, item.busiName);
        });
    }
    $from = $('#inputForm')
    $("#busiNo").on("change", function (e) {
        busiNo();
    });
//邮箱校验
    var loginNameVail = true;
    $("#loginNameErrTip").hide();
    document.getElementById("loginName").addEventListener("blur", function (event) {
        const loginName = getI("loginName");
        var validate = /^[A-Za-z0-9]{4,20}$/;
        if ((loginName != undefined || loginName != null) && loginName.length > 0) {
            if (!validate.test(loginName)) {
                loginNameVail = false;
                $("#loginNameErrTip").text("只能输入英文、数字，长度为4-20位");
                $("#loginNameErrTip").show();
                return;
            }
        }
        loginNameVail = true;
        $("#loginNameErrTip").hide();
    });

    //邮箱校验
    var emailVail = true;
    $("#emailErrTip").hide();
    document.getElementById("email").addEventListener("blur", function (event) {
        //getI() 获取input框中的内容
        const email = getI("email");
        if (email != undefined || email != null) {
            // var validate = /^[a-zA-Z0-9_-]+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-]+)/;
            var validate = /(^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$)/;
            if (email.length > 0 && !validate.test(email)) {
                emailVail = false;
                $("#emailErrTip").text("请输入正确的邮箱格式");
                $("#emailErrTip").show();
                return;
            }
        }
        emailVail = true;
        $("#emailErrTip").hide();
    });
    //手机号码校验
    var phoneNoVail = true;
    $("#phoneNoErrTip").hide();
    document.getElementById("phoneNo").addEventListener("blur", function (event) {
        const phoneNo = getI("phoneNo");
        if (phoneNo != undefined || phoneNo != null) {
            if (phoneNo.length > 0 && !/^1[3456789]\d{9}$/.test(phoneNo)) {
                phoneNoVail = false;
                $("#phoneNoErrTip").text("请输入正确的手机号");
                $("#phoneNoErrTip").show();
                return;
            }
        }
        phoneNoVail = true;
        $("#phoneNoErrTip").hide();
    });
    var nameVail = true;
    $("#nameNoErrTip").hide();
    document.getElementById("realName").addEventListener("blur", function (event) {
        const realName = getI("realName");
        if (realName != undefined || realName != null) {
            if (!/^[\u4E00-\u9FA5]{2,20}(·[\u4E00-\u9FA5]{2,20}){0,2}$/.test(realName)) {
                nameVail = false;
                $("#nameNoErrTip").text("请输入正确的真实姓名");
                $("#nameNoErrTip").show();
                return;
            }
        }
        nameVail = true;
        $("#nameNoErrTip").hide();
    });


    //密码长度校验
    var pwdVail = true;
    $("#pwdErrTip").hide();
    document.getElementById("pwd").addEventListener("blur", function (event) {
        var validate = /[\u4e00-\u9fa5]/;
        if (pwd != undefined || pwd != null) {
            const pwd = getI("pwd");
            if (pwd.length > 0 && (pwd.length < 6 || pwd.length > 20)) {
                pwdVail = false;
                $("#pwdErrTip").text("密码长度应为6-20位");
                $("#pwdErrTip").show();
                return;
            }
            if (validate.test(pwd) || /\s/.test(pwd)) {
                pwdVail = false;
                $("#pwdErrTip").text("密码不支持汉字以及空格");
                $("#pwdErrTip").show();
                return;
            }
            const requirePwd = getI("requirePwd");
            if (requirePwd.length > 0 && pwd != requirePwd) {
                requirePwdVail = false;
                $("#requirePwdErrTip").text("两次密码必须一致");
                $("#requirePwdErrTip").show();
                return;
            }
        }
        pwdVail = true;
        $("#pwdErrTip").hide();
    });


    //二次密码校验
    var requirePwdVail = true;
    $("#requirePwdErrTip").hide();
    document.getElementById("requirePwd").addEventListener("blur", function (event) {
        var validate = /[^\u4e00-\u9fa5][^\uFE30-\uFEA0]{8,20}/;
        const requirePwd = getI("requirePwd");
        const pwd = getI("pwd");
        if (pwd != requirePwd) {
            requirePwdVail = false;
            $("#requirePwdErrTip").text("两次密码必须一致");
            $("#requirePwdErrTip").show();
            return;
        }
        requirePwdVail = true;
        $("#requirePwdErrTip").hide();
    });
    //身份证号码校验
    var certNoVail = true;
    $("#certNoErrTip").hide();
    document.getElementById("certNo").addEventListener("blur", function (event) {
        const certNo = getI("certNo");
        if (certNo != undefined || certNo != null) {
            if (certNo.length > 0 && !/^\d{17}[\dxX]$/.test(certNo)) {
                certNoVail = false;
                $("#certNoErrTip").text("请输入正确的身份证号");
                $("#certNoErrTip").show();
                return;
            }
        }
        certNoVail = true;
        $("#certNoErrTip").hide();
    });
    document.getElementById("pwd").addEventListener("blur", function (event) {
        const requirePwd = getI("requirePwd");
        const pwd = getI("pwd");
        if (pwd == requirePwd) {
            pwdVail = true;
            $("#requirePwdErrTip").hide();
        }
    });
    document.querySelector("form").addEventListener('submit', function (event) {
        event.preventDefault(); // 阻止表单默认的提交行为
        if (emailVail && phoneNoVail && pwdVail && requirePwdVail && certNoVail && nameVail && loginNameVail) {
            submitInfo();
        } else {
            showTip("请确保输入项已正确填写", "error", 1000, 10);
        }
    });
    document.getElementById("pwd").addEventListener('copy', async (event) => {
        event.preventDefault();
        // event.clipboardData.setData('text/plain', `账号为：${getI('loginName')}\n密码为：${getI('pwd')}\n姓名为：${getI('realName')}\n所属业务：${getI('busiName')}\n邮箱：${getI('email')}\n手机号为：${getI('phoneNo')}`)
        event.clipboardData.setData('text/plain', `${getI('pwd')}`)
    })
});

function busiNo() {
    if (busiM == undefined || busiM.size==0) {
        //00-联网缴费  01-非联网 缴费缴费  100-公交卡业务
        const busiInfo = busiSelect($("select[name='busiNo']"), '100');
        busiInfo.forEach(item => {
            busiM.set(item.busiNo, item.busiName);
        });
    }
    setI("busiName", busiM.get(getS("busiNo")));
}

function submitInfo() {
    let data = $from.serializeObject();
    //上传busiTp区分商户
    data.busiTp = '100';
    sendPostOfAjax(ctx + '/merRegister/data/register', data, false, true, () => {
        $("#pwd").select();
        document.execCommand("Copy");
        showTip("注册成功，密码已复制，可直接粘贴", "success", 2000, 100);
        setS("busiNo", "");
        setI("busiName", "");
        setI("loginName", "");
        setI("pwd", "");
        setI("requirePwd", "");
        setI("realName", "");
        setI("certNo", "");
        setI("email", "");
        setI("phoneNo", "");
    });
}

$('.alert').css({
    'position': 'fixed',
    'top': '50%',
    'left': '50%',
    'transform': 'translate(-50%,-50%)'
})
