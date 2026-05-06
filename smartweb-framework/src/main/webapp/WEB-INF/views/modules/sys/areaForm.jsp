<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = path;
%>
<html>
<head>
	<title>区域管理</title>
	<meta name="decorator" content="default"/>
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
		$(document).ready(function() {
			$("#name").focus();
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
				},
		        highlight : function(element) {
		        	setElementBorder(element);
	            },
				success : function(element) {
					setElementPrevBorder(element);
		        }	
			});
			$('#regionTp').multiselect("select", ${area.regionTp}).multiselect('refresh');
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/area/">区域列表</a></li>
		<li class="active"><a href="form?id=${area.id}&parent.id=${area.parent.id}">区域<shiro:hasPermission name="sys:area:edit">${not empty area.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:area:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="area" action="${ctx}/sys/area/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">上级区域:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<sys:treeselect id="area" name="parent.id" value="${area.parent.id}" label_name="parent.name" label_value="${area.parent.name}"
					title="区域" url="/sys/area/treeData" ext_id="${area.id}" css_class="" allow_clear="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">区域名称:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">区域编码:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:input path="regionCode" htmlEscape="false" maxlength="20" oninput = "value=value.replace(/[\\u4e00-\\u9fa5/]+/g,'')" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">区域类型:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<%-- <form:select path="regionTp" class="input-medium">
					<form:options items="${fns:getDictList('REGION_TP')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select> --%>
				<select data-role="multiselect" id="regionTp" name="regionTp" class="" data-bv-notempty="true"
					data-async="true" blank-item="false" checkbtn="regionTp" blank-text="--请选择--" 
					check-empty="true"  data-max-height="300" data-url="${ctx}/sys/dict/selectData?type=REGION_TP">
				</select>				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:textarea path="rmrk" htmlEscape="false" rows="3" maxlength="80" class="input-xlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:area:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>