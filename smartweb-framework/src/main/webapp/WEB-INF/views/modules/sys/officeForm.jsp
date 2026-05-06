<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%> <!-- JSTL  -->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = path;
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>机构管理</title>
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
			console.info("officeForm");
			$.session.set('$HIDE_LOADING', 'true');
			$("#name").focus();
			$("#inputForm").validate({
                rules: {
					name: {
						required	:true,
						remote		:"${ctx}/sys/office/checkName?oldName=" + encodeURIComponent("${office.name}")
					},
					brchCode: {
						required	:true,
						remote		:"${ctx}/sys/office/checkCode?oldCode=" + encodeURIComponent("${office.brchCode}")
					}
                },
                messages: {
					name: {
						required	: "机构名不能为空！",
						remote		: "机构名已存在！"
					},
					brchCode: {
						required	: "机构编码不能为空！",
						remote		: "机构编码不正确或者机构编码已存在！"
					}
                },
				submitHandler: function(form){
					$.session.set('$HIDE_LOADING', 'false');
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
        	$('#brchTp').multiselect("select", ${office.brchTp}).multiselect('refresh');
        	$('#validSwitchFlg').multiselect("select", ${office.validSwitchFlg}).multiselect('refresh');
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/office/list?id=${fns:getUser().office.id}&parentIdList=${fns:getUser().office.parentIdList}">机构列表</a></li>
		<li class="active"><a href="${ctx}/sys/office/form?id=${office.id}&parent.id=${office.parent.id}">机构<shiro:hasPermission name="sys:office:edit">${not empty office.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:office:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="office" action="${ctx}/sys/office/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">上级机构:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<c:if test="${office.id ne '1'}">
	                <sys:treeselect id="office" name="parent.id" value="${office.parent.id}" label_name="parent.name" label_value="${office.parent.name}"
	                	ext_id="${office.id}"
						title="机构" url="/sys/office/treeData" css_class="required"/>
				</c:if>
				<c:if test="${office.id eq '1'}">
	                <sys:treeselect id="office" name="parent.id" value="${office.parent.id}" label_name="parent.name" label_value="${office.parent.name}"
	                	ext_id="${office.id}"
						title="机构" url="/sys/office/treeData"/>
				</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">归属区域:</label>
			<div style="margin-left: 20px; display:inline-block;">
                <sys:treeselect id="area" name="area.id" value="${office.area.id}" label_name="area.name" label_value="${office.area.name}"
					title="区域" url="/sys/area/treeData" css_class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机构名称:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:input path="name" htmlEscape="false" class="required" maxlength="50"/>
				<span class="help-inline"><font color="red" id="nameErrMsg">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机构编码:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:input path="brchCode" htmlEscape="false" class="required" maxlength="50"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机构类型:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<%-- <form:select path="brchTp" class="input-medium">
					<form:options items="${fns:getDictList('BRCH_TP')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select> --%>
				<select data-role="multiselect" id="brchTp" name="brchTp" class="" data-bv-notempty="true"
					data-async="true" blank-item="false" checkbtn="brchTp" blank-text="--请选择--" 
					check-empty="true"  data-max-height="300" data-url="${ctx}/sys/dict/selectData?type=BRCH_TP">
				</select>
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
				<span class="help-inline">“是”代表归属此机构的账号允许登陆，“否”则表示不允许登陆</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主负责人:</label>
			<div style="margin-left: 20px; display:inline-block;">
				 <sys:treeselect id="oneRspbtPer" name="oneRspbtPer.id" value="${office.oneRspbtPer.id}" label_name="office.oneRspbtPer.name" label_value="${office.oneRspbtPer.name}"
					title="用户" url="/sys/office/treeData?type=3" allow_clear="true" not_allow_select_parent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">副负责人:</label>
			<div style="margin-left: 20px; display:inline-block;">
				 <sys:treeselect id="twoRspbtPer" name="twoRspbtPer.id" value="${office.twoRspbtPer.id}" label_name="office.twoRspbtPer.name" label_value="${office.twoRspbtPer.name}"
					title="用户" url="/sys/office/treeData?type=3" allow_clear="true" not_allow_select_parent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系地址:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:input path="ctctAddr" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮政编码:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:input path="postEcd" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">负责人:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:input path="rspbtPer" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:input path="telNo" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">传真:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:input path="faxNo" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:input path="email" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:textarea path="rmrk" htmlEscape="false" rows="3" maxlength="80" class="input-xlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<c:if test="${!(fns:getUser().office.id eq office.id)||fns:getUser().admin}">
			<shiro:hasPermission name="sys:office:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>