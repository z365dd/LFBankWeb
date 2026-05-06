<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = path;
%>
<html>
<head>
	<title>角色管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	
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
	
	<script type="text/javascript" charset="utf-8">
        $(document).ready(function(){
        	$.session.set('$HIDE_LOADING', 'true');
            // $("#name").focus();
            console.info('roleForm ready');
            $("#inputForm").validate({
            	rules: {
            		name: {
            			required: true,
            			remote: "${ctx}/sys/role/checkName?oldName=" + encodeURIComponent("${role.name}")
            		},
            		engName: {
            			required: true,
            			remote: "${ctx}/sys/role/checkEngName?oldEngName=" + encodeURIComponent("${role.engName}")
            		}
            	},
                messages: {
                    name: {
                    	required: "角色名不能为空！",
                    	remote: "角色名已存在"
                   	},
                    engName: {
                    	required: "英文名不能为空！",
            			remote: "英文名应为数字、英文、下划线或者英文名称已存在"
                    }
                },
                submitHandler: function(form){
                    var ids = [], nodes = tree.getCheckedNodes(true);
                    for(var i=0; i<nodes.length; i++) {
                        ids.push(nodes[i].id);
                    }
                    $("#menuIds").val(ids);
                    var ids2 = [], nodes2 = tree2.getCheckedNodes(true);
                    for(var i=0; i<nodes2.length; i++) {
                        ids2.push(nodes2[i].id);
                    }
                    $("#brchIds").val(ids2);
                    $("#brchName").attr("disabled",false);
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
                }
            });

            if (${isUpdate}) {
                $("#brchName").attr("disabled",true);
            }
			$('#roleTp').multiselect("select", '${role.roleTp}').multiselect('refresh');
			$('#dataSwitchFlg').multiselect("select", '${role.dataSwitchFlg}').multiselect('refresh');
			$('#validSwitchFlg').multiselect("select", '${role.validSwitchFlg}').multiselect('refresh');
			$('#dataScp').multiselect("select", '${role.dataScp}').multiselect('refresh');
        });

	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li><a href="${ctx}/sys/role/">角色列表</a></li>
	<li class="active"><a href="${ctx}/sys/role/detail?id=${role.id}">角色查看</a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="role" action="${ctx}/sys/role/save" method="post" class="form-horizontal">
	<form:hidden path="id"/>
	<sys:message content="${message}"/>
	<input type="hidden" value="${isUpdate}" name="isUpdate" id="isUpdate"/>
	<div class="control-group">
		<label class="control-label">角色名称:</label>
		<div style="margin-left: 20px; display:inline-block;">
			<input id="oldName" name="oldName" type="hidden" value="${role.name} readonly">
			<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">英文名称:</label>
		<div style="margin-left: 20px; display:inline-block;">
			<input id="oldEngName" name="oldEngName" type="hidden" value="${role.engName} readonly">
			<form:input path="engName" htmlEscape="false" maxlength="50" class="required"/>
			<span class="help-inline"><font color="red">*</font></span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">角色类型:</label>
		<div style="margin-left: 20px; display:inline-block;">
			<%-- <form:select path="roleTp" class="input-medium">
				<form:options items="${fns:getDictList('ROLE_TP')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select> --%>
			<select data-role="multiselect" id="roleTp" name="roleTp" class="" data-bv-notempty="true"
				data-async="true" blank-item="false" checkbtn="roleTp" blank-text="--请选择--" 
				check-empty="true"  data-max-height="300" data-url="${ctx}/sys/dict/selectData?type=ROLE_TP">
			</select>				
		</div>
	</div>
	<div class="control-group hide">
		<label class="control-label">是否系统数据:</label>
		<div style="margin-left: 20px; display:inline-block;">
			<%-- <form:select path="dataSwitchFlg">
				<form:options items="${fns:getDictList('DATA_SWITCH_FLG')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select> --%>
			<select data-role="multiselect" id="dataSwitchFlg" name="dataSwitchFlg" class="" data-bv-notempty="true"
				data-async="true" blank-item="false" checkbtn="dataSwitchFlg" blank-text="--请选择--" 
				check-empty="true"  data-max-height="300" data-url="${ctx}/sys/dict/selectData?type=DATA_SWITCH_FLG">
			</select>				
			<span class="help-inline">“是”代表此数据只有超级管理员能进行修改，“否”则表示拥有角色修改人员的权限都能进行修改</span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">是否可用</label>
		<div style="margin-left: 20px; display:inline-block;">
			<%-- <form:select path="validSwitchFlg">
				<form:options items="${fns:getDictList('VALID_SWITCH_FLG')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select> --%>
			<select data-role="multiselect" id="validSwitchFlg" name="validSwitchFlg" class="" data-bv-notempty="true"
				data-async="true" blank-item="false" checkbtn="validSwitchFlg" blank-text="--请选择--" 
				check-empty="true"  data-max-height="300" data-url="${ctx}/sys/dict/selectData?type=VALID_SWITCH_FLG">
			</select>				
			<span class="help-inline">代表此角色的菜单权限是否可用</span>
		</div>
	</div>
	<div class="control-group hide" >
		<label class="control-label">数据范围:</label>
		<div style="margin-left: 20px; display:inline-block;">
			<%-- <form:select path="dataScp" class="input-medium">
				<form:options items="${fns:getDictList('DATA_SCP')}" itemLabel="label" itemValue="value" htmlEscape="false" id="scope_"/>
			</form:select> --%>
			<select data-role="multiselect" id="dataScp" name="dataScp" class="" data-bv-notempty="true"
				data-async="true" blank-item="false" checkbtn="dataScp" blank-text="--请选择--" 
				check-empty="true"  data-max-height="300" data-url="${ctx}/sys/dict/selectData?type=DATA_SCP">
			</select>				
			<span class="help-inline">特殊情况下，设置为“按明细设置”，可进行跨机构授权</span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">备注:</label>
		<div style="margin-left: 20px; display:inline-block;">
			<form:textarea path="rmrk" htmlEscape="false" rows="3" maxlength="100" class="input-xlarge"/>
		</div>
	</div>
	<div class="form-actions">
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
</form:form>
</body>
</html>