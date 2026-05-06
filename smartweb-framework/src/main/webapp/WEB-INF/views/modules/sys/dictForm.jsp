<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>字典管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			setDisable();
			$("#dictTp").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
		function setDisable() {
			if ($("#id").val() == "") {
				$("#dictTp").removeAttr("disabled");
			} else {
				$("#dictTp").attr("disabled", "disabled");
			}
		};
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/dict/">字典列表</a></li>
		<li class="active"><a href="${ctx}/sys/dict/form?id=${dict.id}">字典<shiro:hasPermission name="sys:dict:edit">${not empty dict.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:dict:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="dict" action="${ctx}/sys/dict/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">字典类型:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:input path="dictTp" htmlEscape="false" maxlength="100" class="required abc" oninput = "value=value.replace(/[\\u4e00-\\u9fa5/]+/g,'')"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标签值:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:input path="value" htmlEscape="false" maxlength="80" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标签名称:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:input path="label" htmlEscape="false" maxlength="80" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标签描述:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:input path="dictInfo" htmlEscape="false" maxlength="30" style="width:450px;" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序值:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:input path="sort" htmlEscape="false" maxlength="11" class="required digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:textarea path="rmrk" htmlEscape="false" rows="3" maxlength="80" class="input-xlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:dict:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>