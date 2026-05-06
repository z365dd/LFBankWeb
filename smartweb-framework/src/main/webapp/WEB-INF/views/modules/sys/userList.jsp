<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/sys/user/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
			
			$(".lb2").hover(function(){
				$(this).children("div").show();
			},function(){
				$(this).children("div").hide();
			}); 
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/user/list");
			$("#searchForm").submit();
	    	return false;
	    }
		
		function shouTntModal(userId){
			$("#tntModal").modal('show');
			$("#userId").val(userId);
		}
		
		function saveTntBtn(){
			console.log("--saveTntBtn--");
	    	var rentId = $("#rentIdId").val();
	    	if(rentId==""){
	    		showTip("请选择租户","error");
	    		return;
	    	}
		}
	</script>
	<link
	href="${ctxStatic}/mainframe/css_${not empty cookie.theme.value ? cookie.theme.value : 'blue'}/index.css"
	type="text/css" rel="stylesheet" />
</head>
<body>
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/sys/user/import" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
			<a href="${ctx}/sys/user/import/template">下载模板</a>
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/user/list">用户列表</a></li>
        <c:if test="${fns:getUser().manager}">
		    <shiro:hasPermission name="sys:user:edit"><li><a href="${ctx}/sys/user/form">用户添加</a></li></shiro:hasPermission>
        </c:if>
	</ul>
	<form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/user/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label style="margin-left: 15px">归属机构：</label><sys:treeselect id="office" name="office.id" value="${user.office.id}" label_name="office.name" label_value="${user.office.name}"
				title="机构" url="/sys/office/treeData?type=2&isAll=false" css_class="input-medium" allow_clear="true"/></li>
			<li><label style="margin-left: 15px">默认租户：</label><sys:treeselect id="rent" name="rent.id" value="${user.rent.id}" label_name="rent.name" label_value="${user.rent.name}"
				title="租户" url="/sys/rent/treeData?isAll=false" css_class="input-medium" allow_clear="true"/></li>
			<li class="clearfix"></li>
			<li><label>登录名：</label><div style="margin-left:4px; display:inline-block;"><form:input path="loginName" htmlEscape="false" class="input-medium"/></div></li>
			<li><label>姓&nbsp;&nbsp;&nbsp;名：</label><div style="margin-left:4px; display:inline-block;"><form:input path="name" htmlEscape="false" class="input-medium"/></div></li>
			<li><label>商户名称：</label><div style="margin-left:4px; display:inline-block;"><form:input path="busiName" htmlEscape="false" class="input-medium"/></div></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
				<c:if test="${fns:getUser().manager}">
				<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
				<input id="btnImport" class="btn btn-primary" type="button" value="导入"/>
				</c:if>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th width="13%">归属机构</th><th width="13%">默认租户</th><th width="13%">归属法人</th><th class="login_name" width="10%">登录名</th><th class="name" width="10%">姓名</th><th width="8%">角色类型</th><th width="8%">手机</th><th width="10%">商户名称</th><shiro:hasAnyPermissions name="sys:user:view,sys:user:edit"><th>操作</th></shiro:hasAnyPermissions></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="user">
			<tr>
				<td>${user.office.name}</td>
				<td>${user.rent.name}</td>
				<td>${user.corporation.name}</td>
				<td><a href="${ctx}/sys/user/detail?id=${user.id}">${user.loginName}</a></td>
				<td>${user.name}</td>
				<td>${user.roleTpStr}</td>
				<td>${user.phoneNo}</td>
                <td>${user.busiName}</td>
				<shiro:hasAnyPermissions name="sys:user:view,sys:user:edit"><td>
					<button class="lb1">
						<a href="${ctx}/sys/user/detail?id=${user.id}">详情</a>
					</button>
					<div class="lb2">更多
						<button class="lb3">
							<img src="${ctxStatic}/mainframe/img/${ctxTheme}/lb4.png">
						</button>
						<div class="lb4" style="position: absolute; z-index: 999; display: none;">
							<ul>
								<li class="lb4li">
									<a href="${ctx}/sys/user/userMenu?id=${user.id}">用户菜单</a>
								</li>
<%--								<c:if test="${(fns:getUser().manager && !(user.office.id eq fns:getUser().office.id)) || fns:getUser().admin}">--%>
								<c:if test="${fns:getUser().manager}">
									<li class="lb4li">
										<a href="${ctx}/sys/user/form?id=${user.id}">修改</a>
									</li>
									<li class="lb4li">
										<a href="${ctx}/sys/user/delete?id=${user.id}" onclick="return confirmx('确认要删除该用户吗？', this.href)">删除</a>
									</li>
									<li class="lb4li">
										<a href="${ctx}/sys/user/updateOfficeForm?userId=${user.id}">变更机构</a>
									</li>
									<li class="lb4li">
										<a href="${ctx}/sys/user/userAcct?id=${user.id}">其它账号</a>
									</li>
									<li class="lb4li">
										<a href="${ctx}/sys/user/userTnt?id=${user.id}">租户授权</a>
									</li>
								</c:if>
							</ul>
						</div>
					</div>
				</td></shiro:hasAnyPermissions>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>