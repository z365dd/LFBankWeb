<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>权限组授权</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript" charset="utf-8">
        $(document).ready(function(){
            $("#name").val("${permissionOwn.name}");
            $("#name").attr("disabled","disabled");
            
            var setting = {check:{enable:true,nocheckInherit:true},view:{selectedMulti:false},
                data:{simpleData:{enable:true}},callback:{beforeClick:function(id, node){
                        tree.checkNode(node, !node.checked, true, true);
                        return false;
                    }}};

            // 机构-菜单
            var zNodes=[
                    <c:forEach items="${permissionOwn.parentMenuList}" var="menu">{id:"${menu.id}", pId:"${not empty menu.parent.id?menu.parent.id:0}", name:"${not empty menu.parent.id?menu.name:'权限列表'}"},
                </c:forEach>];
            // 初始化树结构
            var tree = $.fn.zTree.init($("#menuTreeOwn"), setting, zNodes);
            // 不选择父节点
            tree.setting.check.chkboxType = { "Y" : "ps", "N" : "s" };

            // 默认选择节点
            var ids = "${permissionOwn.menuIds}".split(",");
            for(var i=0; i<ids.length; i++) {
                var node = tree.getNodeByParam("id", ids[i]);
                try{tree.checkNode(node, true, false);}catch(e){}
            }

            // 默认展开全部节点
            // tree.expandAll(true);

			// 机构-菜单
			var zNodes=[
					<c:forEach items="${permissionTransfer.parentMenuList}" var="menu">{id:"${menu.id}", pId:"${not empty menu.parent.id?menu.parent.id:0}", name:"${not empty menu.parent.id?menu.name:'权限列表'}"},
				</c:forEach>];
			// 初始化树结构
			var tree = $.fn.zTree.init($("#menuTreeTransfer"), setting, zNodes);
			// 不选择父节点
			tree.setting.check.chkboxType = { "Y" : "ps", "N" : "s" };

			// 默认选择节点
			var ids = "${permissionTransfer.menuIds}".split(",");
			for(var i=0; i<ids.length; i++) {
				var node = tree.getNodeByParam("id", ids[i]);
				try{tree.checkNode(node, true, false);}catch(e){}
			}

			// 默认展开全部节点
			// tree.expandAll(true);

			// 机构-菜单
			var zNodes=[
					<c:forEach items="${permissionUse.parentMenuList}" var="menu">{id:"${menu.id}", pId:"${not empty menu.parent.id?menu.parent.id:0}", name:"${not empty menu.parent.id?menu.name:'权限列表'}"},
				</c:forEach>];
			// 初始化树结构
			var tree = $.fn.zTree.init($("#menuTreeUse"), setting, zNodes);
			// 不选择父节点
			tree.setting.check.chkboxType = { "Y" : "ps", "N" : "s" };

			// 默认选择节点
			var ids = "${permissionUse.menuIds}".split(",");
			for(var i=0; i<ids.length; i++) {
				var node = tree.getNodeByParam("id", ids[i]);
				try{tree.checkNode(node, true, false);}catch(e){}
			}

			// 默认展开全部节点
			// tree.expandAll(true);

			var zTree1 = $.fn.zTree.getZTreeObj("menuTreeOwn");
			var zTree2 = $.fn.zTree.getZTreeObj("menuTreeTransfer");
			var zTree3 = $.fn.zTree.getZTreeObj("menuTreeUse");
			showZtreeNum1(true, zTree1, 2);
			showZtreeNum2(true, zTree2, 2);
			showZtreeNum3(true, zTree3, 2);

			function showZtreeNum1(status,childnodes,level){
				// 调用时首次调用是showZtreeNum(true,zTree,classLevel) 获取总的结点
				// 然后递归调用其孩子结点
				if(status){
					var rootnodes = zTree1.getNodes();
					showZtreeNum1(false,rootnodes,level);//递归
				}else{
					if(childnodes){
						var len=childnodes.length;
						if(level<childnodes[0].level+1){
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

			function showZtreeNum2(status,childnodes,level){
				// 调用时首次调用是showZtreeNum(true,zTree,classLevel) 获取总的结点
				// 然后递归调用其孩子结点
				if(status){
					var rootnodes = zTree2.getNodes();
					showZtreeNum2(false,rootnodes,level);//递归
				}else{
					if(childnodes){
						var len=childnodes.length;
						if(level<childnodes[0].level+1){
							return;
						}
						for (var i = 0; i < len; i++) {
							zTree2.expandNode(childnodes[i], true, false, false, true);
							var child=childnodes[i].children;
							showZtreeNum2(false,child,level);//递归
						}
					}
				}
			}

			function showZtreeNum3(status,childnodes,level){
				// 调用时首次调用是showZtreeNum(true,zTree,classLevel) 获取总的结点
				// 然后递归调用其孩子结点
				if(status){
					var rootnodes = zTree3.getNodes();
					showZtreeNum3(false,rootnodes,level);//递归
				}else{
					if(childnodes){
						var len=childnodes.length;
						if(level<childnodes[0].level+1){
							return;
						}
						for (var i = 0; i < len; i++) {
							zTree3.expandNode(childnodes[i], true, false, false, true);
							var child=childnodes[i].children;
							showZtreeNum3(false,child,level);//递归
						}
					}
				}
			}

        });
	</script>
</head>
<body>
<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/role/list">角色列表</a></li>
		<c:if test="${fns:getUser().manager}">
		<shiro:hasPermission name="sys:role:edit"><li class="active"><a href="${ctx}/sys/role/seePermissionToRole?id=${permissionOwn.id}">查看权限组</a></li></shiro:hasPermission>
		</c:if>
</ul>
<form:form id="inputForm" modelAttribute="permissionOwn" action="" method="post" class="form-horizontal">
	<div class="control-group">
		<label class="control-label">权限组名称:</label>
		<div style="margin-left: 180px;">
			<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">拥有所有权:</label>
		<div style="margin-left: 180px;">
			<div id="menuTreeOwn" class="ztree" style="margin-top:3px;float:left;"></div>
		</div>
		<label class="control-label">拥有转授权:</label>
		<div style="margin-left: 180px;">
			<div id="menuTreeTransfer" class="ztree" style="margin-top:3px;float:left;"></div>
		</div>
		<label class="control-label">拥有使用权:</label>
		<div style="margin-left: 180px;">
			<div id="menuTreeUse" class="ztree" style="margin-top:3px;float:left;"></div>
		</div>
	</div>
	<div class="form-actions">
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
</form:form>
</body>
</html>