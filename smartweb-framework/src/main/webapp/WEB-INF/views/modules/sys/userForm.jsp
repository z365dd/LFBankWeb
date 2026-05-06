<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%
    String path = request.getContextPath();
    String basePath = path;
%>
<html>
<head>
    <title>用户管理</title>
    <meta name="decorator" content="default"/>
    <!-- BOOTSTRAP -->
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/b_base/bootstrap-3.4.1/css/bootstrap.css"/>
    <!-- /BOOTSTRAP -->

    <!-- Multiselect -->
    <link type="text/css" rel="stylesheet"
          href="<%=basePath%>/b_ide/bootstrap/module/bootstrap-multiselect-master/dist/css/bootstrap-multiselect.css"/>
    <script type="text/javascript"
            src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-multiselect-master/dist/js/bootstrap-multiselect.js"></script>
    <script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/js/jquery.multiselectcus.js"></script>
    <!-- /Multiselect -->

    <!-- change skin -->
    <link href="${ctxStatic}/mainframe/css_${not empty cookie.theme.value ? cookie.theme.value : 'blue'}/index.css"
          type="text/css" rel="stylesheet"/>
    <script type="text/javascript">
        $(document).ready(function () {
            $.session.set('$HIDE_LOADING', 'true');
            $("#userNo").focus();
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
            $.validator.addMethod("phoneNo", function (value, element) {
                var v_regex = /^((13[0-9])|(14[579])|(15([0-3]|[5-9]]))|(16[56])|(17[0-8])|(18[0-9])|(19[1589]))+\d{8}$/;
                value = "" + value;
                if (value) {
                    if(value.length == 11 && v_regex.test(value)){
                        return true;
                    }else{
                        return false;
                    }
                } else {
                    return true;
                }
            }, "请输入正确的手机号码");
            $.validator.addMethod("telNo", function (value, element) {
                var v_regex = /^(\d{3,4}-?)?\d{7,9}$/g;
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
            }, "请输入正确的电话号码");
            $("#inputForm").validate({
                rules: {
                    loginName: {
                        required: true,
                        remote: "${ctx}/sys/user/checkLoginName?oldLoginName=" + encodeURIComponent('${user.loginName}')
                    },
                    name: {
                        required: true,
                        remote: "${ctx}/sys/user/checkName?oldName=" + encodeURIComponent('${user.name}')
                    },
                    userNo: {
                        required: true,
                        remote: "${ctx}/sys/user/checkUserNo?oldUserNo=" + encodeURIComponent('${user.userNo}')
                    }
                },
                messages: {
                    loginName: {required: "请输入登录名！", remote: "登录名已存在"},
                    name: {required: "请输入姓名！", remote: "姓名已存在"},
                    userNo: {required: "请输入工号！", remote: "工号已存在"}
                },
                submitHandler: function (form) {
                    $.session.set('$HIDE_LOADING', 'false');
                    loading('正在提交，请稍等...');
                    /*20181206 add by chenyl for 修复密码为空时的RSA解密异常*/
                    if ($('#newPassword').val() != "") {
                        $('#encyptNewPassword').val(encryptRSA($('#newPassword').val()));
                        $('#encyptConfirmNewPassword').val(encryptRSA($('#confirmNewPassword').val()));
                    }
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
                },
                highlight: function (element) {
                    setElementBorder(element);
                },
                success: function (element) {
                    setElementPrevBorder(element);
                }
            });
            $('#userLvl').multiselect("select", '${user.userLvl}').multiselect('refresh');
            $('#loginSwitchFlg').multiselect("select", '${user.loginSwitchFlg}').multiselect('refresh');
            $('#roleTp').multiselect("select", '${user.roleTp}').multiselect('refresh');
            // 根据角色类型刷新对应的角色选项
            typeChange();
            $('#roleTp').change(function () {
                typeChange();
            });

        });

        function typeChange() {
            var $sel = $('#roleTp').find("option:selected");
            var divId = 'roleDiv';
            var id = 'roleIdList';
            var name = 'roleIdList';
            var url = ctx + '/sys/role/getRoleList?roleTp=' + $sel.val() + '&userId=${user.id}';
            var required = false;
            var chkList = [];
            console.info('选中：' + $sel.val() + " - " + $sel.text());
            genCheckboxByUrl(divId, id, name, url, required, chkList);
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/sys/user/list">用户列表</a></li>
    <li class="active"><a href="${ctx}/sys/user/form?id=${user.id}">用户<shiro:hasPermission
            name="sys:user:edit">${not empty user.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
            name="sys:user:edit">查看</shiro:lacksPermission></a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/save" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">头像:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <sys:ckfinder input="nameImage" name="img" value="${user.img}" is_all_user="false" type="images"
                          upload_path="/photo" ckfinder_required="false" select_multiple="false" max_width="100"
                          max_height="100"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">归属机构:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <sys:treeselect id="office" name="office.id" value="${user.office.id}" label_name="office.name"
                            label_value="${user.office.name}"
                            title="部门" url="/sys/office/treeData?type=2" css_class="required"
                            treesearch_required="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">默认租户:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <sys:treeselect id="rent" name="rent.id" value="${user.rent.id}" label_name="rent.name"
                            label_value="${user.rent.name}"
                            title="租户" url="/sys/rent/treeData?type=2" css_class="required"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">归属法人:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <sys:treeselect id="corporation" name="corporation.id" value="${user.corporation.id}"
                            label_name="corporation.name" label_value="${user.corporation.name}"
                            title="法人" url="/sys/corporation/treeData?type=2" css_class="required"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">工号:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <form:input path="userNo" htmlEscape="false" maxlength="30" class="required"
                        oninput="value=value.replace(/[\\u4e00-\\u9fa5/]+/g,'')"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">姓名:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <input id="oldName" name="oldName" type="hidden" value="${user.name}">
            <form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">登录名:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <input id="oldLoginName" name="oldLoginName" type="hidden" value="${user.loginName}">
            <form:input path="loginName" htmlEscape="false" maxlength="100" class="required userName"
                        oninput="value=value.replace(/[\\u4e00-\\u9fa5/]+/g,'')"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">密码:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <form:input path="" id="newPassword" type="password" value="" pwd="true" minlength="6"
                        class="${empty user.id?'required':''}"
                        oninput="value=value.replace(/[\\u4e00-\\u9fa5/]+/g,'')" autocomplete="off"/>
            <input type="hidden" id="encyptNewPassword" name="newPassword"/>
            <c:if test="${empty user.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>
            <c:if test="${not empty user.id}"><span class="help-inline">若不修改密码，请留空。</span></c:if>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">确认密码:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <form:input path="newPassword" id="confirmNewPassword" type="password" value="" minlength="6"
                        equalTo="#newPassword" class="${empty user.id?'required':''}" autocomplete="off"/>
            <input type="hidden" id="encyptConfirmNewPassword" name="confirmNewPassword"/>
            <c:if test="${empty user.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">用户级别:</label>
        <div style="margin-left: 20px; display:inline-block;">
                <%-- <form:select path="userLvl">
                    <form:options items="${fns:getDictList('USER_LVL')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select> --%>
            <select data-role="multiselect" id="userLvl" name="userLvl" class="" data-bv-notempty="true"
                    data-async="true" blank-item="false" checkbtn="userLvl" blank-text="--请选择--"
                    check-empty="true" data-max-height="300" data-url="${ctx}/sys/dict/selectData?type=USER_LVL">
            </select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">邮箱:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <form:input path="email" htmlEscape="false" maxlength="200" class="email"
                        oninput="value=value.replace(/[\\u4e00-\\u9fa5/]+/g,'')"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">电话:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <form:input path="telNo" htmlEscape="false" telNo="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">手机:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <form:input path="phoneNo" htmlEscape="false" phoneNo="true"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">是否允许登录:</label>
        <div style="margin-left: 20px; display:inline-block;">
                <%-- <form:select path="loginSwitchFlg">
                    <form:options items="${fns:getDictList('LOGIN_SWITCH_FLG')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select> --%>
            <select data-role="multiselect" id="loginSwitchFlg" name="loginSwitchFlg" class="" data-bv-notempty="true"
                    data-async="true" blank-item="false" checkbtn="loginSwitchFlg" blank-text="--请选择--"
                    check-empty="true" data-max-height="300"
                    data-url="${ctx}/sys/dict/selectData?type=LOGIN_SWITCH_FLG">
            </select>
            <span class="help-inline"><font color="red">*</font> “是”代表此账号允许登录，“否”则表示此账号不允许登录</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">角色类型:</label>
        <div style="margin-left: 20px; display:inline-block;">
                <%-- <form:select path="roleTp">
                    <form:options items="${fns:getDictList('ROLE_TP')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select> --%>
            <select data-role="multiselect" id="roleTp" name="roleTp" class="" data-bv-notempty="true"
                    data-async="true" blank-item="false" checkbtn="roleTp" blank-text="--请选择--"
                    check-empty="true" data-max-height="300" data-url="${ctx}/sys/dict/selectData?type=ROLE_TP">
            </select>
        </div>
    </div>
    <div class="control-group" id="userRole">
        <label class="control-label">用户角色:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <div id="roleDiv">

            </div>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">备注:</label>
        <div style="margin-left: 20px; display:inline-block;">
            <form:textarea path="rmrk" htmlEscape="false" rows="3" maxlength="80" class="input-xlarge"/>
        </div>
    </div>
    <c:if test="${not empty user.id}">
        <div class="control-group">
            <label class="control-label">创建时间:</label>
            <div style="margin-left: 20px; display:inline-block;">
                <label class="lbl">${user.crtTime}</label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">最后登陆:</label>
            <div style="margin-left: 20px; display:inline-block;">
                <label class="lbl">IP: ${user.loginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：${user.loginDate}</label>
            </div>
        </div>
    </c:if>
    <div class="form-actions">
        <c:if test="${fns:getUser().manager || fns:getUser().admin}">
            <shiro:hasPermission name="sys:user:edit"><input id="btnSubmit" class="btn btn-primary" type="submit"
                                                             value="保 存"/>&nbsp;</shiro:hasPermission>
        </c:if>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>