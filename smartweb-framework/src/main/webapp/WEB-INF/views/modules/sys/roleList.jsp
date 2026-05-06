<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>角色管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
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
		<li class="active"><a href="${ctx}/sys/role/">角色列表</a></li>
        <c:if test="${fns:getUser().manager}">
		<shiro:hasPermission name="sys:role:edit"><li><a href="${ctx}/sys/role/form">角色添加</a></li></shiro:hasPermission>
        </c:if>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<tr><th>角色名称</th><th>英文名称</th><th>角色类型</th><th>是否可用</th><shiro:hasAnyPermissions name="sys:role:view,sys:role:edit"><th>操作</th></shiro:hasAnyPermissions></tr>
        <c:forEach items="${list}" var="role">
			<tr>
				<td><a href="detail?id=${role.id}">${role.name}</a></td>
				<td><a href="detail?id=${role.id}">${role.engName}</a></td>
				<td>${fns:getDictLabel(role.roleTp, 'ROLE_TP', '未知')}</td>
				<td>${fns:getDictLabel(role.validSwitchFlg, 'VALID_SWITCH_FLG', '未知')}</td>
				<shiro:hasAnyPermissions name="sys:role:view,sys:role:edit"><td>
<%--系统管理员   管理用户 并且 角色不是系统数据  角色不是管理类 并且 不是系统数据--%>
<%--						<c:if test="${fns:getUser().admin || (fns:getUser().manager && '1' eq role.dataSwitchFlg)}">--%>
<%--							<button class="lb1">--%>
<%--								<a href="${ctx}/sys/role/assign?id=${role.id}">分配</a>--%>
<%--							</button>--%>
<%--                    	</c:if>--%>
							<c:if test="${fns:getUser().admin || (fns:getUser().manager && '1' eq role.dataSwitchFlg)}">
								<button class="lb1">
									<a href="${ctx}/sys/role/form?id=${role.id}">修改</a>
								</button>
<%--								<button class="lb1">--%>
<%--									<a href="${ctx}/sys/role/delete?id=${role.id}" onclick="return confirmx('确认要删除该角色吗？', this.href)">删除</a>--%>
<%--								</button>--%>
							</c:if>
						<div class="lb2">更多
							<button class="lb3">
								<img src="${ctxStatic}/mainframe/img/${ctxTheme}/lb4.png">
							</button>
							<div class="lb4" style="position: absolute; z-index: 999; display: none;">
								<ul>
									<c:if test="${fns:getUser().admin || (fns:getUser().manager && '1' eq role.dataSwitchFlg)}">
<%--										<li class="lb4li">--%>
<%--											<a href="${ctx}/sys/role/form?id=${role.id}">修改</a>--%>
<%--										</li>--%>
										<li class="lb4li">
											<a href="${ctx}/sys/role/delete?id=${role.id}" onclick="return confirmx('确认要删除该角色吗？', this.href)">删除</a>
										</li>
									</c:if>
									<li class="lb4li">
										<a href="${ctx}/sys/role/seePermissionToRole?id=${role.id}">查看权限组</a>
									</li>
									<c:if test="${fns:getUser().manager}">
										<shiro:hasPermission name="sys:role:edit">
											<li class="lb4li">
												<a href="${ctx}/sys/role/assignPermissionToRole?id=${role.id}&permissionType=transfer">分配转授权</a>
											</li>
											<li class="lb4li">
												<a href="${ctx}/sys/role/assignPermissionToRole?id=${role.id}&permissionType=use">分配使用权</a>
											</li>
										</shiro:hasPermission>
									</c:if>
								</ul>
							</div>
						</div>                    
                </shiro:hasAnyPermissions>
			</tr>
		</c:forEach>
	</table>
</body>
</html>