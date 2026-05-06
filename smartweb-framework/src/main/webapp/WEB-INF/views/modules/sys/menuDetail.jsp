<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = path;
%>
<html>
<head>
	<title>菜单管理</title>
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
			// $("#name").focus();
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
			setRadioVal("dpyFlg", ${menu.dpyFlg});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/menu/">菜单列表</a></li>
		<li class="active"><a href="${ctx}/sys/menu/detail?id=${menu.id}">菜单查看</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="menu" action="${ctx}/sys/menu/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group" id="up_menu">
			<label class="control-label">上级菜单:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<input type="text" id="menu" value="${menu.parent.name}" readonly/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">菜单名称:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<input type="text" id="name" value="${menu.name}" readonly/>
				<span class="help-inline"><!-- <font color="red">*</font> --> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">链接:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<input type="text" id="menuLink" value="${menu.menuLink}" readonly/>
				<span class="help-inline">点击菜单跳转的页面</span>
			</div>
		</div>
		<div class="control-group hide">
			<label class="control-label">目标:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<input type="text" id="windowVal" value="${menu.windowVal}" readonly/>
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
		<div class="control-group">
			<label class="control-label">排序值:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<input type="text" id="sort" value="${menu.sort}" readonly/>
				<span class="help-inline">使用升序排列</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否可见:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<div class="radio-inline">
					<label style="visibility:visible">
						<input id="dpyFlg_0" type="radio" value="0" name="dpyFlg" disabled>
						隐藏
						<div></div>
					</label>
				</div>
				<div class="radio-inline">
					<label style="visibility:visible">
						<input id="dpyFlg_1" type="radio" value="1" name="dpyFlg" disabled checked>
						显示
						<div></div>
					</label>
				</div>
				<span class="help-inline">该菜单是否显示到菜单列表中</span>
			</div>
		</div>
		<div class="control-group hide">
			<label class="control-label">权限标识:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<input type="text" id="auth" value="${menu.auth}" readonly/>
				<span class="help-inline">控制器中定义的权限标识，如：@RequiresPermissions("权限标识")</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:textarea path="rmrk" htmlEscape="false" rows="3" maxlength="100" disabled="true" class="input-xxlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>