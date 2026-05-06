<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>法人管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, rootId = "${not empty corporation.id ? corporation.id : '1'}";
			console.info('data='+data.length);
			addRow("#treeTableList", tpl, data, rootId, true);
			$("#treeTable").treeTable({expandLevel : 2});
			if(${!fns:getUser().admin && fns:getUser().manager}) {
				$("#treeTableList").find("tr:first").find("td:last").html("<div class=\"lb2\">更多<button class=\"lb3\"><img src=\"${ctxStatic}/mainframe/img/${ctxTheme}/lb4.png\"></button><div class=\"lb4\" style=\"position: absolute; z-index: 999; display: none;\"><ul><li class=\"lb4li\"><a href=\"${ctx}/sys/corporation/seePermissionToCorporation?id=${fns:getUser().corporation.id}\">查看权限组</a></li><li class=\"lb4li\"><a href=\"${ctx}/sys/corporation/assignPermissionToCorporation?id=${fns:getUser().corporation.id}&permissionType=use\">分配使用权</a></li></ul></div></div>");
			}
			if(${!fns:getUser().admin && !fns:getUser().manager}) {
				$("#treeTableList").find("tr:first").find("td:last").html("<div class=\"lb2\">更多<button class=\"lb3\"><img src=\"${ctxStatic}/mainframe/img/${ctxTheme}/lb4.png\"></button><div class=\"lb4\" style=\"position: absolute; z-index: 999; display: none;\"><ul><li class=\"lb4li\"><a href=\"${ctx}/sys/corporation/seePermissionToCorporation?id=${fns:getUser().corporation.id}\">查看权限组</a></li></ul></div></div>");
			}

			$(".lb2").hover(function(){
				$(this).children("div").show();
			},function(){
				$(this).children("div").hide();
			});
		});
		function addRow(list, tpl, data, pid, root){
			for (var i=0; i<data.length; i++){
				var row = data[i];
				if(root){
					if ((${fns:jsGetVal('row.id')}) == pid){
						$(list).append(Mustache.render(tpl, {
							dict: {
								validSwitchFlg: getDictLabel(${fns:toJson(fns:getDictList('VALID_SWITCH_FLG'))}, row.validSwitchFlg)
							}, pid: (root?0:pid), row: row
						}));
						addRow(list, tpl, data, row.id);
					}
				}
				else{
					if ((${fns:jsGetVal('row.parentId')}) == pid){
						$(list).append(Mustache.render(tpl, {
							dict: {
								validSwitchFlg: getDictLabel(${fns:toJson(fns:getDictList('VALID_SWITCH_FLG'))}, row.validSwitchFlg)
							}, pid: (root?0:pid), row: row
						}));
						addRow(list, tpl, data, row.id);
					}
				}
			}
		}
		function corporationQry(){
			$("#searchForm").attr("action","${ctx}/sys/corporation/list?id="+$("#corporationId").attr("value")+"&parentIdList=");
		}
	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li class="active"><a href="${ctx}/sys/corporation/">法人列表</a></li>
	<c:if test="${fns:getUser().manager}">
		<shiro:hasPermission name="sys:rent:edit"><li><a href="${ctx}/sys/corporation/form?parent.id=${corporation.id}">法人添加</a></li></shiro:hasPermission>
	</c:if>
</ul>
<form:form id="searchForm" modelAttribute="corporation" action="${ctx}/sys/corporation/list" method="post" class="breadcrumb form-search ">
	<ul class="ul-form">
		<li><label>归属法人：</label><sys:treeselect id="corporation" name="corporation.id" value="${user.corporation.id}" label_name="corporation.name" label_value="${user.corporation.name}"
												title="法人" url="/sys/corporation/treeData?type=2&isAll=false" css_class="input-medium" allow_clear="true"/></li>
		<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="corporationQry()"/>
	</ul>
</form:form>
<sys:message content="${message}"/>
<table id="treeTable" class="table table-striped table-bordered table-condensed">
	<tr><th>法人编号</th><th>法人名称</th><th>是否可用</th><shiro:hasAnyPermissions name="sys:corporation:view,sys:corporation:edit"><th>操作</th></shiro:hasAnyPermissions></tr>
	<tbody id="treeTableList"></tbody>
</table>
<script type="text/template" id="treeTableTpl">
	<tr id="{{row.id}}" pId="{{pid}}">
		<td>{{row.legaNo}}</td>
		<td>{{row.name}}</td>
		<td>{{dict.validSwitchFlg}}</td>
		<shiro:hasAnyPermissions name="sys:corporation:view,sys:corporation:edit"><td>
			<c:if test="${fns:getUser().manager}">
				<shiro:hasPermission name="sys:corporation:edit">
				<button class="lb1">
				<a href="${ctx}/sys/corporation/form?id={{row.id}}">修改</a>
				</button>
				</shiro:hasPermission>
			</c:if>
			<div class="lb2">更多
				<button class="lb3">
					<img src="${ctxStatic}/mainframe/img/${ctxTheme}/lb4.png">
				</button>
				<div class="lb4" style="position: absolute; z-index: 999; display: none;">
					<ul>
						<c:if test="${fns:getUser().manager}">
							<shiro:hasPermission name="sys:corporation:edit">
							<li class="lb4li">
								<a href="${ctx}/sys/corporation/delete?id={{row.id}}" onclick="return confirmx('要删除该法人及所有子法人吗？', this.href)">删除</a>
							</li>
							<li class="lb4li">
								<a href="${ctx}/sys/corporation/form?parent.id={{row.id}}">添加下级法人</a>
							</li>
							</shiro:hasPermission>
						</c:if>
						<li class="lb4li">
							<a href="${ctx}/sys/corporation/seePermissionToCorporation?id={{row.id}}">查看权限组</a>
						</li>
						<c:if test="${fns:getUser().manager}">
						<shiro:hasPermission name="sys:corporation:edit">
						<li class="lb4li">
							<a href="${ctx}/sys/corporation/assignPermissionToCorporation?id={{row.id}}&permissionType=transfer">分配转授权</a>
						</li>
						<li class="lb4li">
							<a href="${ctx}/sys/corporation/assignPermissionToCorporation?id={{row.id}}&permissionType=use">分配使用权</a>
						</li>
						</shiro:hasPermission>
						</c:if>
					</ul>
				</div>
			</div>
		</td></shiro:hasAnyPermissions>
	</tr>
</script>
</body>