<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机构管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, rootId = "${not empty office.id ? office.id : '1'}";
			addRow("#treeTableList", tpl, data, rootId, true);
			$("#treeTable").treeTable({expandLevel : 2});
			if(${!fns:getUser().admin && fns:getUser().manager}) {
				$("#treeTableList").find("tr:first").find("td:last").html("<button class=\"lb1\"><a href=\"${ctx}/sys/office/detail?id=${fns:getUser().office.id}\">详情</a></button><div class=\"lb2\">更多<button class=\"lb3\"><img src=\"${ctxStatic}/mainframe/img/${ctxTheme}/lb4.png\"></button><div class=\"lb4\" style=\"position: absolute; z-index: 999; display: none;\"><ul><li class=\"lb4li\"><a href=\"${ctx}/sys/office/seePermissionToOffice?id=${fns:getUser().office.id}\">查看权限组</a></li><li class=\"lb4li\"><a href=\"${ctx}/sys/office/assignPermissionToOffice?id=${fns:getUser().office.id}&permissionType=use\">分配使用权</a></li></ul></div></div>");
			}
			if(${!fns:getUser().admin && !fns:getUser().manager}) {
				$("#treeTableList").find("tr:first").find("td:last").html("<button class=\"lb1\"><a href=\"${ctx}/sys/office/detail?id=${fns:getUser().office.id}\">详情</a></button><div class=\"lb2\">更多<button class=\"lb3\"><img src=\"${ctxStatic}/mainframe/img/${ctxTheme}/lb4.png\"></button><div class=\"lb4\" style=\"position: absolute; z-index: 999; display: none;\"><ul><li class=\"lb4li\"><a href=\"${ctx}/sys/office/seePermissionToOffice?id=${fns:getUser().office.id}\">查看权限组</a></li></ul></div></div>");
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
								dictTp: getDictLabel(${fns:toJson(fns:getDictList('BRCH_TP'))}, row.brchTp),
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
								dictTp: getDictLabel(${fns:toJson(fns:getDictList('BRCH_TP'))}, row.brchTp),
								validSwitchFlg: getDictLabel(${fns:toJson(fns:getDictList('VALID_SWITCH_FLG'))}, row.validSwitchFlg)
							}, pid: (root?0:pid), row: row
						}));
						addRow(list, tpl, data, row.id);
					}
				}
			}
		}
		function officeQry(){
			$("#searchForm").attr("action","${ctx}/sys/office/list?id="+$("#officeId").attr("value")+"&parentIdList=");
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/office/list?id=${fns: getUser().office.id}&parentIdList=${fns: getUser().office.parentIdList}">机构列表</a></li>
		<c:if test="${fns:getUser().manager}">
		<shiro:hasPermission name="sys:office:edit"><li><a href="${ctx}/sys/office/form?parent.id=${office.id}">机构添加</a></li></shiro:hasPermission>
		</c:if>
	</ul>
	<form:form id="searchForm" modelAttribute="office" action="${ctx}/sys/office/list" method="post" class="breadcrumb form-search ">
		<ul class="ul-form">
			<li><label>归属机构：</label><sys:treeselect id="office" name="office.id" value="${user.office.id}" label_name="office.name" label_value="${user.office.name}" 
				title="机构" url="/sys/office/treeData?type=2&isAll=false" css_class="input-medium" allow_clear="true"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="officeQry()"/>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>机构名称</th><th>归属区域</th><th>机构编码</th><th>机构类型</th><th>是否可用</th><shiro:hasAnyPermissions name="sys:office:view,sys:office:edit"><th>操作</th></shiro:hasAnyPermissions></tr></thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td><a href="${ctx}/sys/office/detail?id={{row.id}}">{{row.name}}</a></td>
			<td>{{row.area.name}}</td>
			<td>{{row.brchCode}}</td>
			<td>{{dict.dictTp}}</td>
			<td>{{dict.validSwitchFlg}}</td>
			<shiro:hasAnyPermissions name="sys:office:view,sys:office:edit">
			<td>
					<button class="lb1">
					<a href="${ctx}/sys/office/detail?id={{row.id}}">详情</a>
					</button>
				<div class="lb2">更多
					<button class="lb3">
						<img src="${ctxStatic}/mainframe/img/${ctxTheme}/lb4.png">
					</button>
					<div class="lb4" style="position: absolute; z-index: 999; display: none;">
						<ul>
							<c:if test="${fns:getUser().manager}">
								<shiro:hasPermission name="sys:office:edit">
								<li class="lb4li">
									<a href="${ctx}/sys/office/delete?id={{row.id}}" onclick="return confirmx('要删除该机构及所有子机构项吗？', this.href)">删除</a>
								</li>
								<li class="lb4li">
									<a href="${ctx}/sys/office/form?parent.id={{row.id}}">添加下级机构</a>
								</li>
								<li class="lb4li">
									<a href="${ctx}/sys/office/form?id={{row.id}}">修改</a>
								</li>
								</shiro:hasPermission>
							</c:if>
							<li class="lb4li">
								<a href="${ctx}/sys/office/seePermissionToOffice?id={{row.id}}">查看权限组</a>
							</li>
							<c:if test="${fns:getUser().manager}">
							<shiro:hasPermission name="sys:office:edit">
							<li class="lb4li">
								<a href="${ctx}/sys/office/assignPermissionToOffice?id={{row.id}}&permissionType=transfer">分配转授权</a>
							</li>
							<li class="lb4li">
								<a href="${ctx}/sys/office/assignPermissionToOffice?id={{row.id}}&permissionType=use">分配使用权</a>
							</li>
							</shiro:hasPermission>
							</c:if>
						</ul>
					</div>
				</div>
			</td>
			</shiro:hasAnyPermissions>
		</tr>
	</script>
</body>
</html>