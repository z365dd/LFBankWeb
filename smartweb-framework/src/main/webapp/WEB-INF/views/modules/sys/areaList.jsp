<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>区域管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, rootId = "0";
			addRow("#treeTableList", tpl, data, rootId, true);
			$("#treeTable").treeTable({expandLevel : 2});
			
			$(".lb2").hover(function(){
				$(this).children("div").show();
			},function(){
				$(this).children("div").hide();
			}); 			
		});
		function addRow(list, tpl, data, pid, root){
			for (var i=0; i<data.length; i++){
				var row = data[i];
				if ((${fns:jsGetVal('row.parentId')}) == pid){
					$(list).append(Mustache.render(tpl, {
						dict: {
							dictTp: getDictLabel(${fns:toJson(fns:getDictList('REGION_TP'))}, row.regionTp)
						}, pid: (root?0:pid), row: row
					}));
					addRow(list, tpl, data, row.id);
				}
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/area/">区域列表</a></li>
		<shiro:hasPermission name="sys:area:edit"><li><a href="${ctx}/sys/area/form">区域添加</a></li></shiro:hasPermission>
	</ul>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>区域名称</th><th>区域编码</th><th>区域类型</th><th>备注</th><shiro:hasPermission name="sys:area:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td><a href="${ctx}/sys/area/form?id={{row.id}}">{{row.name}}</a></td>
			<td>{{row.regionCode}}</td>
			<td>{{dict.dictTp}}</td>
			<td>{{row.rmrk}}</td>
			<shiro:hasPermission name="sys:area:edit"><td>
				<button class="lb1">
					<a href="${ctx}/sys/area/form?id={{row.id}}">修改</a>
				</button>
				<div class="lb2">更多
					<button class="lb3">
						<img src="${ctxStatic}/mainframe/img/${ctxTheme}/lb4.png">
					</button>
					<div class="lb4" style="position: absolute; z-index: 999; display: none;">
						<ul>
							<li class="lb4li">
								<a href="${ctx}/sys/area/delete?id={{row.id}}" onclick="return confirmx('要删除该区域及所有子区域项吗？', this.href)">删除</a>
							</li>
							<li class="lb4li">
								<a href="${ctx}/sys/area/form?parent.id={{row.id}}">添加下级区域</a> 
							</li>
						</ul>
					</div>
				</div>
			</td>
		</shiro:hasPermission>
		</tr>
	</script>
</body>
</html>