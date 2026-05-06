<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = path;
%>
<html>
<head>
	<title>租户管理</title>
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
			$("#name").focus();
			$("#inputForm").validate({
				rules: {
					name: {
						required: true,
						remote: "${ctx}/sys/rent/checkName?oldName=" + encodeURIComponent("${rent.name}")
					},
					engName: {
						required: true,
						remote: "${ctx}/sys/rent/checkEngName?oldEngName=" + encodeURIComponent("${rent.engName}")
					}
				},
				messages: {
					name: {
						required	: "租户名不能为空！",
						remote: "租户名已存在！"
					},
					engName: {
						required	: "英文名不能为空！",
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

			console.info("rent.id="+"${rent.id}");

			var hasMsMall = '${hasMsMall}';
			if(hasMsMall=='YES'&&$("#id").val()!=""){
				var ue = UE.getEditor('description');
				$("#bgImg").val('${imgUrl}');
				bgImgPreview();
				/* 实例后在ready方(组件渲染后事件)中设置内容 */
				ue.ready(function() {
					ue.setContent('${description}');
				});
			}
			if (${isUpdate}) {
				$("#engName").attr("disabled", true);
			}
        	$('#stat').multiselect("select", '${rent.stat}').multiselect('refresh');			
			if ('${allowMultiLevelRent}'=="N") {
				$('#rentName').attr('not_allow_select_child', true);
			}
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/rent/list?id=${fns:getUser().rent.id}&parentIdList=${fns:getUser().rent.parentIdList}">租户列表</a></li>
		<li class="active"><a href="${ctx}/sys/rent/form?id=${rent.id}&parent.id=${rent.parent.id}">租户<shiro:hasPermission name="sys:rent:edit">${not empty rent.id?'修改':'添加'}</shiro:hasPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="rent" action="${ctx}/sys/rent/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">上级租户:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<sys:treeselect id="rent" name="parent.id" value="${rent.parent.id}" label_name="parent.name" label_value="${rent.parent.name}"
								ext_id="${rent.id}"  
								title="租户" url="/sys/rent/treeData" css_class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">租户名称:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<input id="oldName" name="oldName" type="hidden" value="${rent.name}">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">租户编码:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<input id="oldEngName" name="oldEngName" type="hidden" value="${rent.engName}">
				<form:input path="engName" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font> 登录云平台使用</span>
			</div>
		</div>
		<c:if test="${hasMsMall=='YES' }">
			<div class="control-group">
				<label class="control-label">
					背景图:
				</label>
				<div id="ckfinder" style="margin-left: 180px">
					<sys:ckfinder input="bgImg" name="bgImg" value="" type="images" upload_path="/msmall/rent"
					is_all_user="false" ckfinder_required="false" select_multiple="false" readonly="readonly"
					max_width="200" max_height="200">
					</sys:ckfinder>
					<!-- <span class="help-inline"><font color="red">*</font> </span> -->
				</div>
			</div>
		</c:if>
		<div class="control-group hide">
			<label class="control-label">默认寻址地址:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:input path="url" htmlEscape="false" maxlength="128" class="input-xlarge"/>
				<span class="help-inline">如果为空则通过调用服务目录进行寻址，否则以该URL请求路径进行外部请求</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">租户状态:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<%-- <form:select path="stat">
					<form:options items="${fns:getDictList('OPEN_STAT')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select> --%>
				<select data-role="multiselect" id="stat" name="stat" class="" data-bv-notempty="true"
					data-async="true" blank-item="false" checkbtn="stat" blank-text="--请选择--" 
					check-empty="true"  data-max-height="300" data-url="${ctx}/sys/dict/selectData?type=OPEN_STAT">
				</select>				
				<span class="help-inline">租户状态(Y-启用,N-关闭),默认Y-启用</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div style="margin-left: 20px; display:inline-block;">
				<form:textarea path="rmrk" htmlEscape="false" rows="3" maxlength="80" class="input-xlarge"/>
			</div>
		</div>
		<c:if test="${hasMsMall=='YES' }">
			<div class="control-group">
				<label class=" control-label">
					描述信息:
				</label>
				<div style="margin-left: 20px; display:inline-block;" id="ueditor">
					<script type="text/plain" id="description" name="description" maxlength="84" style="height:300px;width: 600px">
				</script>
				</div>
			</div>
		</c:if>
		<div class="form-actions">
			<shiro:hasPermission name="sys:rent:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>