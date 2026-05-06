<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人信息</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
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
			$.validator.addMethod("email", function (value, element) {
				var v_regex = /(^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$)/;
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
			}, "请输入正确格式的邮箱");
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					// $("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/user/info">个人信息</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/info" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">头像:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<sys:ckfinder input="nameImage" name="img" value="${user.img}" type="images" upload_path="/photo" select_multiple="false" max_width="100" max_height="100"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">归属部门:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<label class="lbl">${user.office.name}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">默认租户:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<label class="lbl">${user.rent.name}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">归属法人:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<label class="lbl">${user.corporation.name}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:input path="email" htmlEscape="false" email="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:input path="telNo" htmlEscape="false" telNo="true" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:input path="phoneNo" htmlEscape="false" phoneNo="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:textarea path="rmrk" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">角色类型:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<label class="lbl">${fns:getDictLabel(user.roleTp, 'ROLE_TP', '无')}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户角色:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<label class="lbl">${user.roleNames}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上次登录:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<label class="lbl">IP: ${user.oldLoginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：${user.oldLoginDate}</label>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
		</div>
	</form:form>
</body>
</html>