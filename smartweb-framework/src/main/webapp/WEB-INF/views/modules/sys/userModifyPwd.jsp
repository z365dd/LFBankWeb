<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>修改密码</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            // 禁止浏览器记住密码
            const userAgent = navigator.userAgent
            if (userAgent.indexOf("Chrome") !== -1 && userAgent.indexOf("Safari") !== -1 && userAgent.indexOf("Edg") === -1) {
                $("#oldPassword").addClass("txtPassword")
                $("#newPassword").addClass("txtPassword")
                $("#confirmNewPassword").addClass("txtPassword")
            } else {
                $("#oldPassword").focus(() => {
                    $("#oldPassword").attr("type", "password")
                })
                $("#newPassword").focus(() => {
                    $("#newPassword").attr("type", "password")
                })
                $("#confirmNewPassword").focus(() => {
                    $("#confirmNewPassword").attr("type", "password")
                })
            }
            $("#oldPassword").focus();
            $.validator.addMethod("pwd", function (value, element) {
                var v_regex = /^(?!.*[！·（）【】“”：；，》￥、。‘’——……\n\t\s\v\r])(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[~`!@#$%^&*)(?,.></_|+=}{;:'"\\\]\[\-])[^\u4e00-\u9fa5]{8,20}$/;
                value = "" + value;
                if (value) {
                    if (!v_regex.test(value)) {
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    return true;
                }
            }, "8-20位，其中必须包含数字、小写字母、大写字母及特殊字符，不支持空格及中文");
            $("#inputForm").validate({
                rules: {},
                messages: {
                    confirmNewPassword: {equalTo: "输入与上面相同的密码"}
                },
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    $('#encyptOldPassword').val(encryptRSA($('#oldPassword').val()));
                    $('#encyptNewPassword').val(encryptRSA($('#newPassword').val()));
                    $('#encyptConfirmNewPassword').val(encryptRSA($('#confirmNewPassword').val()));
                    $('#oldPassword').attr('disabled', true)
                    $('#confirmNewPassword').attr('disabled', true)
                    $('#newPassword').attr('disabled', true)
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
            // $('#newPassword').keyup(function(e) {
            // 	var strongRegex = new RegExp("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g");
            // 	var mediumRegex = new RegExp("^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g");
            // 	var enoughRegex = new RegExp("(?=.{6,}).*", "g");
            // 	if (false == enoughRegex.test($(this).val())) {
            // 		$('#passstrength').className = 'less';
            // 		// $('#passstrength').html('');
            // 	} else if (strongRegex.test($(this).val())) {
            // 		$('#passstrength').className = 'ok';
            // 		$('#passstrength').html('强');
            // 	} else if (mediumRegex.test($(this).val())) {
            // 		$('#passstrength').className = 'alert';
            // 		$('#passstrength').html('中');
            // 	} else {
            // 		$('#passstrength').className = 'error';
            // 		$('#passstrength').html('弱');
            // 	}
            // 	return true;
            // });
        });

        function validateSame() {
            var oldPassword = $('#oldPassword').val();
            var newPassword = $('#newPassword').val();
            if (oldPassword != undefined && newPassword != undefined) {
                if (oldPassword == newPassword) {
                    $('#passsame').html('新旧不能密码一致!').css("color", "red");
                    $("#btnSubmit").attr("disabled", "disabled");
                } else {
                    $('#passsame').html('');
                    $("#btnSubmit").removeAttr("disabled");
                }
            }
        }
    </script>
    <style>
        .txtPassword {
            -webkit-text-security: disc;
        }
    </style>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/sys/user/modifyPwd">修改密码</a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/modifyPwd" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">旧密码:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <form:input path="oldName" id="oldPassword" type="text" value="" autocomplete="off" class="required"
                        onkeyup="validateSame()"/>
            <input type="hidden" id="encyptOldPassword" name="oldPassword"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">新密码:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <form:input path="oldLoginName" id="newPassword" type="text" value="" autocomplete="off" pwd="true"
                        class="required" onkeyup="validateSame()"
                        oninput="value=value.replace(/[\\u4e00-\\u9fa5/]+/g,'')"/>
            <input type="hidden" id="encyptNewPassword" name="newPassword"/>
            <span class="help-inline"><font color="red">*</font> </span><span id="passstrength"></span><span
                id="passsame"></span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">确认新密码:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <form:input path="newPassword" id="confirmNewPassword" type="text" value="" autocomplete="off"
                        class="required" equalTo="#newPassword"/>
            <input type="hidden" id="encyptConfirmNewPassword" name="confirmNewPassword"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="form-actions">
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
    </div>
</form:form>
</body>
</html>