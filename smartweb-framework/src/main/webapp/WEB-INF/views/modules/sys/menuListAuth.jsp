<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>按钮权限</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#treeTable").treeTable({expandLevel : 4}).show();
			
			$(".lb2").hover(function(){
				$(this).children("div").show();
			},function(){
				$(this).children("div").hide();
			}); 
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/menu/listAuth">按钮列表</a></li>
		<c:if test="${fns:getUser().manager}">
			<li><a href="${ctx}/sys/menu/formAuth">按钮添加</a></li>
		</c:if>
	</ul>
	<sys:message content="${message}"/>
	<form id="listForm" method="post">
		<table id="treeTable" class="table table-striped table-bordered table-condensed hide">
			<thead><tr><th>按钮名称</th><th>后端拦截路径</th><th>权限标识</th><th>所属权限组 (机构|法人|租户|角色)</th><th>操作</th></tr></thead>
			<tbody><c:forEach items="${listAuth}" var="menu">
				<tr id="${menu.id}" pId="${menu.parent.id ne '1'?menu.parent.id:'0'}">
					<td nowrap>${menu.name}</td>
					<td title="${menu.menuLink}">${fns:abbr(menu.menuLink,30)}</td>
					<td nowrap>${menu.auth}</td>
					<td>${menu.brchName}|${menu.legaName}|${menu.tntName}|${menu.roleName}</td>
					<td nowrap>
						<c:if test="${menu.isOwn eq '1' && fn:length(fn:split(menu.parentIdList, ',')) == 4}">
						<button class="lb1">
							<a href="${ctx}/sys/menu/formAuth?parent.id=${menu.id}">添加</a>
						</button>
						</c:if>
						<c:if test="${menu.isOwn eq '1' && fn:length(fn:split(menu.parentIdList, ',')) == 5}">
							<button class="lb1">
								<a href="${ctx}/sys/menu/formAuth?id=${menu.id}">修改</a>
							</button>
							<div class="lb2">更多
								<button class="lb3">
									<img src="${ctxStatic}/mainframe/img/${ctxTheme}/lb4.png">
								</button>
								<div class="lb4" style="position: absolute; z-index: 999; display: none;">
									<ul>
										<li class="lb4li">
											<a href="${ctx}/sys/menu/deleteAuth?id=${menu.id}" onclick="return confirmx('要删除该按钮权限吗？', this.href)">删除</a>
										</li>
									</ul>
								</div>
							</div>
						</c:if>
					</td>
				</tr>
			</c:forEach></tbody>
		</table>
	 </form>
</body>
</html>