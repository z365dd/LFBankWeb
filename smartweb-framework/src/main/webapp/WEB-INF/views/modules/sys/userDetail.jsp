<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = path;
%>
<html>
<head>
	<title>用户详情</title>
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
			$('#userLvl').multiselect("select", '${user.userLvl}').multiselect('disable');
			$('#roleTp').multiselect("select", '${user.roleTp}').multiselect('disable');
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/user/list">用户列表</a></li>
		<li class="active"><a href="${ctx}/sys/user/detail?id=${user.id}">用户详情</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label" for="img">头像:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<img id="img" src="${user.img}" url="${user.img}" style="max-width:100px;max-height:100px;_height:100px;border:0;padding:3px;">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="parent">归属机构:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<input type="text" id="parent" value="${user.office.name}" readonly/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="rent">默认租户:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<input type="text" id="rent" value="${user.rent.name}" readonly/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="rent">归属法人:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<input type="text" id="legaId" value="${user.corporation.name}" readonly/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="userNo">工号:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<input type="text" id="userNo" value="${user.userNo}" readonly/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="name">姓名:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<input type="text" id="name" value="${user.name}" readonly/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="loginName">登录名:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<input type="text" id="loginName" value="${user.loginName}" readonly/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户级别:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<%-- <form:select path="userLvl">
					<form:options items="${fns:getDictList('USER_LVL')}" itemLabel="label" itemValue="value" htmlEscape="false" disabled="true"/>
				</form:select> --%>
				<select data-role="multiselect" id="userLvl" name="userLvl" class="" data-bv-notempty="true"
					data-async="true" blank-item="false" checkbtn="userLvl" blank-text="--请选择--" 
					check-empty="true"  data-max-height="300" data-url="${ctx}/sys/dict/selectData?type=USER_LVL">
				</select>	
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="email">邮箱:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<input type="text" id="email" value="${user.email}" readonly/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="telNo">电话:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<input type="text" id="telNo" value="${user.telNo}" readonly/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="phoneNo">手机:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<input type="text" id="phoneNo" value="${user.phoneNo}" readonly/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="loginSwitchFlg">是否允许登录:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<input id="loginSwitchFlg" type="text" value="${user.loginSwitchFlg eq "1"?"是":"否"}" readonly>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="roleTp">角色类型:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<%-- <form:select path="roleTp">
					<form:options items="${fns:getDictList('ROLE_TP')}" itemLabel="label" itemValue="value" htmlEscape="false" disabled="true"/>
				</form:select> --%>
				<select data-role="multiselect" id="roleTp" name="roleTp" class="" data-bv-notempty="true"
					data-async="true" blank-item="false" checkbtn="roleTp" blank-text="--请选择--" 
					check-empty="true"  data-max-height="300" data-url="${ctx}/sys/dict/selectData?type=ROLE_TP">
				</select>					
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户角色:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<label class="lbl">${user.roleNames}</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:textarea path="rmrk" htmlEscape="false" rows="3" maxlength="100" class="input-xlarge" disabled="true"/>
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
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>