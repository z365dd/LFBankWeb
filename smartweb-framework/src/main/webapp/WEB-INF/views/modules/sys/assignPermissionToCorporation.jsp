<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>权限组授权</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript" charset="utf-8">
        $(document).ready(function(){
            $("#name").focus();
            console.info('准备分配权限');
            $("#inputForm").validate({
            	submitHandler: function(form){
                    var ids = [], nodes = tree.getCheckedNodes(true);
                    for(var i=0; i<nodes.length; i++) {
                        ids.push(nodes[i].id);
                    }
                    $("#menuIds").val(ids);
                    form.submit();
                }
            });
            $("#name").val("${permissionDTO.name}");
            $("#name").attr("disabled","disabled");

            var setting = {check:{enable:true,nocheckInherit:true},view:{selectedMulti:false},
                data:{simpleData:{enable:true}},callback:{beforeClick:function(id, node){
                        tree.checkNode(node, !node.checked, true, true);
                        return false;
                    }}};

            // 机构-菜单
            var zNodes=[
                    <c:forEach items="${permissionDTO.parentMenuList}" var="menu">{id:"${menu.id}", pId:"${not empty menu.parent.id?menu.parent.id:0}", name:"${not empty menu.parent.id?menu.name:'权限列表'}"},
                </c:forEach>];
            // 初始化树结构
            var tree = $.fn.zTree.init($("#menuTree"), setting, zNodes);
            // 不选择父节点
            tree.setting.check.chkboxType = { "Y" : "ps", "N" : "s" };

            // 默认选择节点
            var ids = "${permissionDTO.menuIds}".split(",");
            for(var i=0; i<ids.length; i++) {
                var node = tree.getNodeByParam("id", ids[i]);
                try{tree.checkNode(node, true, false);}catch(e){}
            }

            // 默认展开全部节点
            // tree.expandAll(true);

			var zTree1 = $.fn.zTree.getZTreeObj("menuTree");
			showZtreeNum1(true, zTree1, 2);

			function showZtreeNum1(status,childnodes,level){
				// 调用时首次调用是showZtreeNum(true,zTree,classLevel) 获取总的结点
				// 然后递归调用其孩子结点
				if(status){
					var rootnodes = zTree1.getNodes();
					showZtreeNum1(false,rootnodes,level);//递归
				}else{
					if(childnodes){
						var len=childnodes.length;
						if(len<=0 || level<childnodes[0].level+1){
							return;
						}
						for (var i = 0; i < len; i++) {
							zTree1.expandNode(childnodes[i], true, false, false, true);
							var child=childnodes[i].children;
							showZtreeNum1(false,child,level);//递归
						}
					}
				}
			}

        });
	</script>
</head>
<body>
<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/corporation/list?id=${fns: getUser().corporation.id}&parentIdList=${fns: getUser().corporation.parentIdList}">法人列表</a></li>
		<c:if test="${fns:getUser().manager}">
		<shiro:hasPermission name="sys:corporation:edit"><li class="active"><a href="${ctx}/sys/corporation/assignPermissionToCorporation?id=${permissionDTO.id}&authTp=${permissionDTO.authTp}">权限组授权</a></li></shiro:hasPermission>
		</c:if>
</ul>
<form:form id="inputForm" modelAttribute="permissionDTO" action="${ctx}/sys/corporation/savePermissionToCorporation" method="post" class="form-horizontal">
	<form:hidden path="id"/>
	<form:hidden path="authTp"/>
	<form:hidden path="tempMenuIdList"/>
	<sys:message content="${message}"/>
	<div class="control-group">
		<label class="control-label">权限组名称:</label>
		<div style="margin-left: 20px; display:inline-block;">
			<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group">
		<c:if test="${permissionDTO.authTp == 'transfer' || permissionDTO.authTp == 'TRANSFER'}">
			<label class="control-label">可被分配转授权:</label>
		</c:if>
		<c:if test="${permissionDTO.authTp == 'use' || permissionDTO.authTp == 'USE'}">
			<label class="control-label">可被分配使用权:</label>
		</c:if>
		<div style="margin-left: 20px; display:inline-block;">
			<div id="menuTree" class="ztree" style="margin-top:3px;float:left;"></div>
			<form:hidden path="menuIds"/>
		</div>
	</div>
	<div class="form-actions">
		<c:if test="${permissionDTO.parentMenuList.size() < 1}">
			<shiro:hasPermission name="sys:corporation:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" disabled="disabled"/>&nbsp;</shiro:hasPermission>
		</c:if>
		<c:if test="${permissionDTO.parentMenuList.size() > 0}">
			<shiro:hasPermission name="sys:corporation:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
		</c:if>
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
</form:form>
</body>
</html>