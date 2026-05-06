<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>字典管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
		$(document).ready(function() {
			$('select').multiselect({
				includeSelectAllOption:true,
				enableFiltering:true,
				filterPlaceholder: '搜索'
			});
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
		<li class="active"><a href="${ctx}/sys/dict/">字典列表</a></li>
		<shiro:hasPermission name="sys:dict:edit"><li><a href="${ctx}/sys/dict/form?sort=10">字典添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="dict" action="${ctx}/sys/dict/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>类型：</label><form:select id="dictTp" path="dictTp" class="input-medium"><form:option value="" label="--全部--"/><form:options items="${typeList}" htmlEscape="false"/></form:select>
		&nbsp;&nbsp;<label>描述 ：</label><div style="margin-left:4px; display:inline-block;"><form:input path="dictInfo" htmlEscape="false" class="input-medium"/></div>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>字典类型</th><th>标签值</th><th>标签名称</th><th>标签描述</th><th>排序值</th><shiro:hasPermission name="sys:dict:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="dict">
			<tr>
				<td><a href="javascript:" onclick="$('#dictTp').val('${dict.dictTp}');$('#searchForm').submit();return false;">${dict.dictTp}</a></td>
				<td>${dict.value}</td>
				<td><a href="${ctx}/sys/dict/form?id=${dict.id}">${dict.label}</a></td>
				<td>${dict.dictInfo}</td>
				<td>${dict.sort}</td>
				<shiro:hasPermission name="sys:dict:edit"><td>
    				<button class="lb1"><a href="${ctx}/sys/dict/form?id=${dict.id}">修改</a></button>
    				<div class="lb2">更多
    					<button class="lb3">
    						<img src="${ctxStatic}/mainframe/img/${ctxTheme}/lb4.png">
    					</button>
    					<div class="lb4" style="position: absolute; z-index: 999; display: none;">
    						<ul>
    							<li class="lb4li">
									<a href="${ctx}/sys/dict/delete?id=${dict.id}&dictTp=${dict.dictTp}" onclick="return confirmx('确认要删除该字典吗？', this.href)">删除</a>
								</li>
    							<li class="lb4li">
    								<a href="<c:url value='${fns:getAdminPath()}/sys/dict/form?dictTp=${dict.dictTp}&sort=${dict.sort+10}'><c:param name='dictInfo' value='${dict.dictInfo}'/></c:url>">添加键值</a>
								</li>
							</ul>
						</div>
					</div>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>