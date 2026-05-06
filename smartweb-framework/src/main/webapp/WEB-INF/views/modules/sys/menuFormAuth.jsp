<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = path;
%>
<html>
<head>
	<title>按钮权限</title>
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
			console.info($("#up_menu").html());
			var $menuName = $("#menuName");
			console.info($menuName.attr('name'));
			$menuName.on('change', function(){
				console.info('11111');
			});
			
			var $menuName = $.session.get('$MENU_NAME');
			var $menuHref = $.session.get('$MENU_HREF');
			if($menuName != undefined && $menuName != ""){
				$("#name").val($menuName);
				$.session.remove('$MENU_NAME');
			}
			if($menuHref != undefined && $menuHref != ""){
				$("#menuLink").val($menuHref);
				$.session.remove('$MENU_HREF');
			}
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/menu/listAuth">按钮列表</a></li>
		<li class="active"><a href="${ctx}/sys/menu/formAuth?id=${menu.id}&parent.id=${menu.parent.id}">按钮<shiro:hasPermission name="sys:menu:edit">${not empty menu.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:menu:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="menuAuth" action="${ctx}/sys/menu/saveAuth" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group" id="up_menu">
			<label class="control-label">上级菜单:</label>
			<div style="margin-left: 20px; display:inline-block;">
                <sys:treeselect id="menuAuth" name="parent.id" value="${menu.parent.id}" label_name="parent.name" label_value="${menu.parent.name}"
					title="菜单" url="/sys/menu/treeData" ext_id="${menu.id}" css_class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">按钮名称:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required input-xlarge"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">后端拦截路径:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:input path="menuLink" htmlEscape="false" maxlength="2000" class="input-xxlarge"/>
			</div>
		</div>
		<div class="control-group hide">
			<label class="control-label">目标:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:input path="windowVal" htmlEscape="false" maxlength="10" class="input-small"/>
				<span class="help-inline">链接地址打开的目标窗口，默认：mainFrame</span>
			</div>
		</div>
		<div class="control-group hide">
			<label class="control-label">一级图标:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<sys:imgselect id="menuIcon" name="menuIcon" value="${menu.menuIcon}"/>
			</div>
		</div>
		<div class="control-group hide">
			<label class="control-label">图标:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<sys:iconselect id="appIcon" name="appIcon" value="${menu.appIcon}"/>
			</div>
		</div>
		<div class="control-group hide">
			<label class="control-label">排序值:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:input path="sort" htmlEscape="false" maxlength="50" class="required digits input-small"/>
				<span class="help-inline">使用升序排列</span>
			</div>
		</div>
		<div class="control-group hide">
			<label class="control-label">是否可见:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:radiobuttons path="dpyFlg" items="${fns:getDictList('DPY_FLG')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">权限标识:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:input path="auth" htmlEscape="false" maxlength="100" class="input-xxlarge"/>
				<span class="help-inline">控制器中定义的权限标识，如：@RequiresPermissions("权限标识")</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:textarea path="rmrk" htmlEscape="false" rows="3" maxlength="100" class="input-xxlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>