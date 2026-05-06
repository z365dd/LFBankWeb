<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = path;
%>
<html>
<head>
	<title>法人管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<!-- UEditor -->
	<script src="${ctxStatic}/ueditor/ueditor.config.js" type="text/javascript"></script>
	<script src="${ctxStatic}/ueditor/ueditor.all.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/ueditor/ueditor.parse.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/ueditor/lang/zh-cn/zh-cn.js" type="text/javascript"></script>
	<!-- BOOTSTRAP -->
	<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_base/bootstrap-3.4.1/css/bootstrap.css"/>
	<!-- /BOOTSTRAP -->	
	
	<!-- Multiselect -->
	<link type="text/css" rel="stylesheet"  href="<%=basePath%>/b_ide/bootstrap/module/bootstrap-multiselect-master/dist/css/bootstrap-multiselect.css"/>
	<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/module/bootstrap-multiselect-master/dist/js/bootstrap-multiselect.js"></script>
	<script type="text/javascript" src="<%=basePath%>/b_ide/bootstrap/js/jquery.multiselectcus.js"></script>
	<!-- /Multiselect -->
	
	<!-- change skin -->
	<link href="${ctxStatic}/mainframe/css_${not empty cookie.theme.value ? cookie.theme.value : 'blue'}/index.css" type="text/css" rel="stylesheet" />	
	
	<script type="text/javascript">
		$(document).ready(function(){
			$("#id").focus();
			$("#inputForm").validate({
				rules: {
					legaNo: {
						required : true,
						remote: "${ctx}/sys/corporation/checkLegaNo?oldLegaNo=" + encodeURIComponent("${corporation.legaNo}")
					},
					name: {
						required : true
					},
					engName: {
						required : true,
						remote: "${ctx}/sys/corporation/checkEngName?oldEngName=" + encodeURIComponent("${corporation.engName}")
					}
				},
				messages: {
					legaNo: {
						required : "法人编号不能为空",
						remote: "法人编号不正确或者法人编号已存在！"
					},
					name: {
						required : "法人名称不能为空"
					},
					engName: {
						required : "英文名不能为空",
						remote: "英文名不正确或者英文名称已存在！"
					}
				},

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
				},
		        highlight : function(element) {
		        	setElementBorder(element);
	            },
				success : function(element) {
					setElementPrevBorder(element);
		        }				
			});

			console.info("corporation.id="+"${corporation.id}");

			if (${isUpdate}) {
				$("#engName").attr("disabled", true);
			}
			$('#validSwitchFlg').multiselect("select", '${corporation.validSwitchFlg}').multiselect('refresh');
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/corporation/list?id=${fns:getUser().corporation.id}">法人列表</a></li>
		<li class="active"><a href="${ctx}/sys/corporation/form?id=${corporation.id}&parent.id=${corporation.parent.id}">法人<shiro:hasPermission name="sys:corporation:edit">${not empty corporation.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:corporation:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="corporation" action="${ctx}/sys/corporation/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">上级法人:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<sys:treeselect id="corporation" name="parent.id" value="${corporation.parent.id}" label_name="parent.name" label_value="${corporation.parent.name}"
								ext_id="${corporation.id}"
								title="法人" url="/sys/corporation/treeData" css_class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">法人编号:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<input id="oldLegaNo" name="oldLegaNo" type="hidden" value="${corporation.legaNo}">
				<form:input path="legaNo" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">法人名称:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">英文名称:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<input id="oldEngName" name="oldEngName" type="hidden" value="${corporation.engName}">
				<form:input path="engName" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否可用:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<%-- <form:select path="validSwitchFlg">
					<form:options items="${fns:getDictList('VALID_SWITCH_FLG')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select> --%>
				<select data-role="multiselect" id="validSwitchFlg" name="validSwitchFlg" class="" data-bv-notempty="true"
					data-async="true" blank-item="false" checkbtn="validSwitchFlg" blank-text="--请选择--" 
					check-empty="true"  data-max-height="300" data-url="${ctx}/sys/dict/selectData?type=VALID_SWITCH_FLG">
				</select>				
				<span class="help-inline">“是”代表归属此法人的账号允许登陆，“否”则表示不允许登陆</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">法人地址:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:input path="ctctAddr" htmlEscape="false" maxlength="80" class="input-xlarge"/>
				<span class="help-inline">法人地址</span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:corporation:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>