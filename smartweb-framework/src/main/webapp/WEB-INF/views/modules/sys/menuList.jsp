<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>菜单管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#treeTable").treeTable({expandLevel : 1}).show();
			
			$(".lb2").hover(function(){
				$(this).children("div").show();
			},function(){
				$(this).children("div").hide();
			}); 
		});
    	function updateSort() {
			loading('正在提交，请稍等...');
	    	$("#listForm").attr("action", "${ctx}/sys/menu/updateSort");
	    	$("#listForm").submit();
    	}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/menu/">菜单列表</a></li>
		<c:if test="${fns:getUser().manager}">
			<li><a href="${ctx}/sys/menu/form">菜单添加</a></li>
			<li><a href="${ctx}/sys/menu/importForm">批量导入</a></li>
		</c:if>
	</ul>
	<sys:message content="${message}"/>
	<form id="listForm" method="post">
		<table id="treeTable" class="table table-striped table-bordered table-condensed hide">
			<thead><tr><th>菜单名称</th><th>链接</th><th>排序值</th><th>是否可见</th><th>所属权限组 (机构|法人|租户|角色)</th><th>操作</th></tr></thead>
			<tbody><c:forEach items="${list}" var="menu">
				<tr id="${menu.id}" pId="${menu.parent.id ne '1'?menu.parent.id:'0'}">
					<td nowrap><i class="icon-${not empty menu.appIcon?menu.appIcon:' hide'}"></i>
						<c:if test="${menu.isOwn eq '1'}"><a href="${ctx}/sys/menu/detail?id=${menu.id}">${menu.name}</a></c:if>
						<c:if test="${menu.isOwn ne '1'}">${menu.name}</c:if>
					</td>
					<c:if test="${menu.isOwn eq '1'}">
						<td title="${menu.menuLink}">${fns:abbr(menu.menuLink,30)}</td>
					</c:if>
					<c:if test="${menu.isOwn ne '1'}">
						<td></td>
					</c:if>
					<c:if test="${menu.parentIdList eq '0,1,'}">
						<td style="text-align:left;">
							<shiro:hasPermission name="sys:menu:edit">
								<input type="hidden" name="ids" value="${menu.id}"/>
								<input name="sorts" type="text" value="${menu.sort}" style="width:50px;margin:0;padding:0;text-align:center;">
							</shiro:hasPermission><shiro:lacksPermission name="sys:menu:edit">
								${menu.sort}
							</shiro:lacksPermission>
						</td>
					</c:if>
					<c:if test="${menu.parentIdList ne '0,1,' && fn:length(fn:split(menu.parentIdList, ',')) == 3}">
						<td style="text-align:center;">
							<shiro:hasPermission name="sys:menu:edit">
								<input type="hidden" name="ids" value="${menu.id}"/>
								<input name="sorts" type="text" value="${menu.sort}" style="width:50px;margin:0;padding:0;text-align:center;">
							</shiro:hasPermission><shiro:lacksPermission name="sys:menu:edit">
							${menu.sort}
						</shiro:lacksPermission>
						</td>
					</c:if>
					<c:if test="${menu.parentIdList ne '0,1,' && fn:length(fn:split(menu.parentIdList, ',')) != 3}">
						<td style="text-align:right;">
							<shiro:hasPermission name="sys:menu:edit">
								<input type="hidden" name="ids" value="${menu.id}"/>
								<input name="sorts" type="text" value="${menu.sort}" style="width:50px;margin:0;padding:0;text-align:center;">
							</shiro:hasPermission><shiro:lacksPermission name="sys:menu:edit">
							${menu.sort}
						</shiro:lacksPermission>
						</td>
					</c:if>
					<td>${menu.dpyFlg eq '1'?'显示':'隐藏'}</td>
					<td>${menu.brchName}|${menu.legaName}|${menu.tntName}|${menu.roleName}</td>
					<td nowrap>
						<c:if test="${menu.isOwn eq '1'}">
						<button class="lb1">
							<a href="${ctx}/sys/menu/form?id=${menu.id}">修改</a>
						</button>
						<div class="lb2">更多
							<button class="lb3">
								<img src="${ctxStatic}/mainframe/img/${ctxTheme}/lb4.png">
							</button>
							<div class="lb4" style="position: absolute; z-index: 999; display: none;">
								<ul>
									<li class="lb4li">
										<a href="${ctx}/sys/menu/delete?id=${menu.id}" onclick="return confirmx('要删除该菜单及所有子菜单项吗？', this.href)">删除</a>
									</li>
									<c:if test="${fn:length(fn:split(menu.parentIdList, ',')) != 4}">
									<li class="lb4li">
										<a href="${ctx}/sys/menu/form?parent.id=${menu.id}">添加下级菜单</a> 
									</li>
									</c:if>
								</ul>
							</div>
						</div>
						</c:if>
					</td>
				</tr>
			</c:forEach></tbody>
		</table>
		<shiro:hasRole name="globle"><div class="form-actions pagination-left">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保存排序" onclick="updateSort();"/>
		</div></shiro:hasRole>
	 </form>
</body>
</html>