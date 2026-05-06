<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@page isELIgnored="false"%>
<html>
<head>
	<title>租户管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, rootId = "${not empty rent.id ? rent.id : '1'}";
			console.info('data='+data);
			console.info('fns:getUser().manager======'+${fns:getUser().manager});
			addRow("#treeTableList", tpl, data, rootId, true);
			$("#treeTable").treeTable({expandLevel : 2});
			if(${!fns:getUser().admin && fns:getUser().manager}) {
				//$("#treeTableList").find("tr:first").find("td:last").html("<button class=\"lb1\"><a href=\"${ctx}/sys/rent/detail?id=${fns:getUser().rent.id}\">详情</a></button><div class=\"lb2\">更多<button class=\"lb3\"><img src=\"${ctxStatic}/mainframe/img/${ctxTheme}/lb4.png\"></button><div class=\"lb4\" style=\"position: absolute; z-index: 999; display: none;\"><ul><li class=\"lb4li\"><a href=\"${ctx}/sys/rent/seePermissionToRent?id=${fns:getUser().rent.id}\">查看权限组</a></li><li class=\"lb4li\"><a href=\"${ctx}/sys/rent/assignPermissionToRent?id=${fns:getUser().rent.id}&permissionType=use\">分配使用权</a></li><li class=\"lb4li\"><a href=\"JavaScript:void(0);\" onclick=\"zkinfo('${fns:getUser().rent.engName}')\">查看ZK节点</a></li></ul></div></div>");
				$("#treeTableList").find("tr:first").find("td:last").html("<button class=\"lb1\"><a href=\"${ctx}/sys/rent/detail?id=${fns:getUser().rent.id}\">详情</a></button><div class=\"lb2\">更多<button class=\"lb3\"><img src=\"${ctxStatic}/mainframe/img/${ctxTheme}/lb4.png\"></button><div class=\"lb4\" style=\"position: absolute; z-index: 999; display: none;\"><ul><li class=\"lb4li\"><a href=\"JavaScript:void(0);\" onclick=\"zkinfo('${fns:getUser().rent.engName}')\">查看ZK节点</a></li></ul></div></div>");
			}
			if(${!fns:getUser().admin && !fns:getUser().manager}) {
				//$("#treeTableList").find("tr:first").find("td:last").html("<button class=\"lb1\"><a href=\"${ctx}/sys/rent/detail?id=${fns:getUser().rent.id}\">详情</a></button><div class=\"lb2\">更多<button class=\"lb3\"><img src=\"${ctxStatic}/mainframe/img/${ctxTheme}/lb4.png\"></button><div class=\"lb4\" style=\"position: absolute; z-index: 999; display: none;\"><ul><li class=\"lb4li\"><a href=\"${ctx}/sys/rent/seePermissionToRent?id=${fns:getUser().rent.id}\">查看权限组</a></li><li class=\"lb4li\"><a href=\"JavaScript:void(0);\" onclick=\"zkinfo('${fns:getUser().rent.engName}')\">查看ZK节点</a></li></ul></div></div>");
				$("#treeTableList").find("tr:first").find("td:last").html("<button class=\"lb1\"><a href=\"${ctx}/sys/rent/detail?id=${fns:getUser().rent.id}\">详情</a></button><div class=\"lb2\">更多<button class=\"lb3\"><img src=\"${ctxStatic}/mainframe/img/${ctxTheme}/lb4.png\"></button><div class=\"lb4\" style=\"position: absolute; z-index: 999; display: none;\"><ul><li class=\"lb4li\"><a href=\"JavaScript:void(0);\" onclick=\"zkinfo('${fns:getUser().rent.engName}')\">查看ZK节点</a></li></ul></div></div>");
			}

			$(".lb2").hover(function(){
				$(this).children("div").show();
			},function(){
				$(this).children("div").hide();
			});
			$('#BtnZkinfoSubmit').click(function(){
				$('#zkinfoModal').modal('hide');
			});
			//根据是否配置允许多层级租户来控制按钮显示
			if("${fns:getConfig('allowMultiLevelRent')}"=="N"){
				$("#li_1").show();
			}else{
				$("li[id^='li_']").show();
			}
		});	
		function addRow(list, tpl, data, pid, root){
			for (var i=0; i<data.length; i++){
				var row = data[i];
				if(root){
					if ((${fns:jsGetVal('row.id')}) == pid){
						$(list).append(Mustache.render(tpl, {
							dict: {
								dictTp: getDictLabel(${fns:toJson(fns:getDictList('OPEN_STAT'))}, row.stat)
							}, pid: (root?0:pid), row: row
						}));
						addRow(list, tpl, data, row.id);
					}
				}
				else{
					if ((${fns:jsGetVal('row.parentId')}) == pid){
						$(list).append(Mustache.render(tpl, {
							dict: {
								dictTp: getDictLabel(${fns:toJson(fns:getDictList('OPEN_STAT'))}, row.stat)
							}, pid: (root?0:pid), row: row
						}));
						addRow(list, tpl, data, row.id);
					}
				}
			}
		}
		function rentQry(){
			$("#searchForm").attr("action","${ctx}/sys/rent/list?id="+$("#rentId").attr("value")+"&parentIdList=");
		}
		function zkinfo(ename){
			/*向后台发送参数*/
			$.post(ctx + "/sys/rent/zkinfo", {ename:ename},
					function(data){
						if(data.returnCode!==undefined && "0000"!=data.returnCode){
							var errMsg = "错误信息["+data.message+"]";
							showContent(errMsg,"error");
							return '0';
						}else if(data.msg_type == "success"){
							$('#zkinfoModal').modal('show');
							for(var i = 0 ; i < data.dataSetResult.length; i++){
								for(var j = 0 ; j < data.dataSetResult[i].data.length; j++){
									var jsonObj = data.dataSetResult[i].data[j];
									console.info(jsonObj);
									$('#Status').val(jsonObj.status);
									$('#Name').val(jsonObj.name);
								}
							}
						}
					}, "json");
		}
	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li class="active"><a href="${ctx}/sys/rent/">租户列表</a></li>
	<c:if test="${fns:getUser().manager}">
		<shiro:hasPermission name="sys:rent:edit"><li><a href="${ctx}/sys/rent/form?parent.id=${rent.id}">租户添加</a></li></shiro:hasPermission>
		<shiro:hasRole name="globle"><li><a href="${ctx}/sys/rent/importForm">批量导入</a></li></shiro:hasRole>
	</c:if>
</ul>
<form:form id="searchForm" modelAttribute="rent" action="${ctx}/sys/rent/list" method="post" class="breadcrumb form-search ">
	<ul class="ul-form">
		<li><label>归属租户：</label><sys:treeselect id="rent" name="rent.id" value="${user.rent.id}" label_name="rent.name" label_value="${user.rent.name}"
												title="租户" url="/sys/rent/treeData?type=2&isAll=false" css_class="input-medium" allow_clear="true"/></li>
		<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="rentQry()"/>
	</ul>
</form:form>
<sys:message content="${message}"/>
<table id="treeTable" class="table table-striped table-bordered table-condensed">
	<tr><th>租户名称</th><th>租户编码</th><th>租户状态</th><shiro:hasAnyPermissions name="sys:rent:view,sys:rent:edit"><th>操作</th></shiro:hasAnyPermissions></tr>
	<tbody id="treeTableList"></tbody>
</table>
<script type="text/template" id="treeTableTpl">
	<tr id="{{row.id}}" pId="{{pid}}">
		<td><a href="${ctx}/sys/rent/detail?id={{row.id}}">{{row.name}}</a></td>
		<td>{{row.engName}}</td>
		<td>{{dict.dictTp}}</td>
		<shiro:hasAnyPermissions name="sys:rent:view,sys:rent:edit"><td>
			<button class="lb1">
				<a href="${ctx}/sys/rent/detail?id={{row.id}}">详情</a>
			</button>
			<div class="lb2">更多
				<button class="lb3">
					<img src="${ctxStatic}/mainframe/img/${ctxTheme}/lb4.png">
				</button>
				<div class="lb4" style="position: absolute; z-index: 999; display: none;">
					<ul>
						<c:if test="${fns:getUser().manager}">
							<shiro:hasPermission name="sys:rent:edit">
							<li class="lb4li">
								<a href="${ctx}/sys/rent/delete?id={{row.id}}" onclick="return confirmx('要删除该租户及所有子租户吗？', this.href)">删除</a>
							</li>
								<li class="lb4li" id="li_{{row.id}}" style="display:none;">
									<a href="${ctx}/sys/rent/form?parent.id={{row.id}}">添加下级租户</a>
								</li>
							<li class="lb4li">
								<a href="${ctx}/sys/rent/form?id={{row.id}}">修改</a>
							</li>
							</shiro:hasPermission>
						</c:if>
						<!-- 
						<li class="lb4li">
							<a href="${ctx}/sys/rent/seePermissionToRent?id={{row.id}}">查看权限组</a>
						</li>
						<c:if test="${fns:getUser().manager}">
						<shiro:hasPermission name="sys:rent:edit">
						<li class="lb4li">
							<a href="${ctx}/sys/rent/assignPermissionToRent?id={{row.id}}&permissionType=transfer">分配转授权</a>
						</li>
						<li class="lb4li">
							<a href="${ctx}/sys/rent/assignPermissionToRent?id={{row.id}}&permissionType=use">分配使用权</a>
						</li>
						-->
						</shiro:hasPermission>
						</c:if>
						<li class="lb4li">
							<a href="JavaScript:void(0);" onclick="zkinfo('{{row.engName}}')">查看ZK节点</a>
						</li>
					</ul>
				</div>
			</div>
		</td></shiro:hasAnyPermissions>
	</tr>
</script>

<div class="modal fade hide" id="zkinfoModal" tabindex="-1" role="dialog"
	 aria-labelledby="zkinfoModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="zkinfoModalLabel">节点信息</h4>
			</div>
			<div class="modal-body">
				<form ravo="rainbow_fx_layout_bd" class="form-horizontal" pourl=""
					  id="zkinfoForm">
					<div ravo="rainbow_fx_radio" class="form-group">
						<div>
							<input id="Name" name="Name" class="form-control" readonly="readonly"/>
						</div>
						<div>
							<textarea id="Status" name="Status" rows="3" class="form-control" style="height:200px" readonly="readonly">
							</textarea>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-calcle" id="BtnZkinfoSubmit">关闭</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal -->
</div>

</body>
</html>