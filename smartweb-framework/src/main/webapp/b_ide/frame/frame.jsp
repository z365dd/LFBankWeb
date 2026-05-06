<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String webapp = request.getContextPath();
	String basePath =  webapp;
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>SmartWeb</title>
<!-- jQuery引入 -->

	<script type="text/javascript" src="<%=basePath%>/b_base/jquery-3.6.1.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=basePath%>/b_base/jquery-migrate-3.4.0.min.js" charset="utf-8"></script>

	
<!--Ztree引入-->
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/b_base/ztree/css/zTreeStyle/zTreeStyle.css" />
<script type="text/javascript"
	src="<%=basePath%>/b_base/ztree/js/jquery.ztree.all-3.5.min.js"></script>

<!-- easyUI引入 -->

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/b_base/jquery-easyui-1.4.2/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/b_base/jquery-easyui-1.4.2/themes/icon.css" />
<script type="text/javascript"
	src="<%=basePath%>/b_base/jquery-easyui-1.4.2/jquery.easyui.min.js"></script>

<!-- messenger -->
<script type="text/javascript"
	src="<%=basePath%>/b_ide/bootstrap/js/messenger.js" charset="utf-8"></script>

<!-- backspace key handler -->
<script type="text/javascript"
	src="<%=basePath%>/b_ide/bootstrap/js/backspace.js" charset="utf-8"> </script>

<!-- jbox -->
<link href="<%=basePath%>/b_base/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
<script src="<%=basePath%>/b_base/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>

<script type="text/javascript" src="<%=basePath%>/b_base/util.js" charset="utf-8"> </script>
<script type="text/javascript" src="./frame.js" charset="utf-8"> </script>

<style type="">
body {
	background-image:
		url("<%=basePath%>/b_base/jquery-easyui-1.4.2/themes/frame/images/bc15.jpg");
	/*主框架背景图*/
}

.nav_tools {
	height: 26px;
	background-color: #F4F4F4;
}

#tran50 {
	background-image:
		url("<%=basePath%>/b_base/jquery-easyui-1.4.2/themes/frame/images/50.png");
	/**/
}

#tran80 {
	background-image:
		url("<%=basePath%>/b_base/jquery-easyui-1.4.2/themes/frame/images/80.png");
	/**/
}

#westid {
	background-image:
		url("<%=basePath%>/b_base/jquery-easyui-1.4.2/themes/frame/images/80.png");
	/**/
}

#tran90 {
	background-image:
		url("<%=basePath%>/b_base/jquery-easyui-1.4.2/themes/frame/images/90.png");
	/**/
}
</style>
</head>

<body id="layout" class="">
	<div id="tran80" data-options="region:'north',border:false"
		style="width: 100%; height: 50px;">
		<iframe id="model" name="model" src="<%=basePath%>/b_ide/bootstrap/model.jsp"
			style="border: 0; width: 100%; height: 100%;"></iframe>
	</div>
	<div data-options="region:'west',title:'页面列表',split:true,border:false"
		style="width: 200px; border-bottom-left-radius: 6px; border-bottom-right-radius: 6px; border-top-left-radius: 6px; border-top-right-radius: 6px;"
		id="tran80">
		<!--  
		<a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">页面配置</a>  
		<a id="btn2" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">页面编辑</a> 
		-->
		<ul id="treeDemo" class="ztree" style="over-flow: auto;"></ul>
	</div>
	<div id="centerID" data-options="region:'center',border:false"
		style="padding: 0px; border-bottom-left-radius: 6px; border-bottom-right-radius: 6px; border-top-left-radius: 6px; border-top-right-radius: 6px;">
		<iframe id="ide" name="hello" src="<%=basePath%>/b_ide/bootstrap/ide.jsp"  style="border: 0; width: 100%; height: 100%;"></iframe>
		<iframe id="idee" name="hello" src="<%=basePath%>/b_ide/generate/autoFrame.jsp" style="border: 0; width: 0%; height: 0%;"></iframe>
	</div>

	<div id="westid" data-options="region:'east',title:'组件属性',border:false,split:true "
		style="width: 250px; border-bottom-left-radius: 6px; border-bottom-right-radius: 6px; border-top-left-radius: 6px; border-top-right-radius: 6px;">
		<table id="dataGrid" class="easyui-propertygrid">
		</table>
	</div>

	<div id="dialog" title="SmartWeb PREVIEW">
		<iframe id="preview" scrolling="yes" frameborder="0"
			src="<%=basePath%>/b_ide/frame/preview/preview.jsp"
			style="width: 100%; height: 98%;"></iframe>
	</div>

	<div id="dialogJSEditer" class="easyui-dialog" title="JS编辑">
		<iframe id="JSwindow" scrolling="yes" frameborder="0"
			src="<%=webapp%>/b_ide/bootstrap/module/ace/JSwindow.jsp"
			style="width: 100%; height: 100%;"></iframe>
	</div>
	
	<div id="dialogCSTEditer" class="easyui-dialog" title="CST编辑">
		<iframe id="CSTwindow" scrolling="yes" frameborder="0"
			src="<%=webapp%>/b_ide/bootstrap/module/ace/CSTwindow.jsp"
			style="width: 100%; height: 100%;"></iframe>
	</div>
	<script type="text/javascript">
        $( "#dialog" ).dialog({
          width : 1300,  
          height : 580,  
          modal :  true,  
          minimizable : false,  
          maximizable : true,
          maximized : true,
          shadow : false,  
          cache : false,
          closed : true,  
          collapsible : false,  
          resizable : false, 

        });
    </script>

	<script type="text/javascript">

	var messenger;
	var savePath = '';
	var previewJspPath = '';
	
	$(function(){
	
		/*
		$('#btn').click(function () {
			
			$('#layout').layout('collapse','east');
			
			$('#ide').css('width','0%');
			$('#ide').css('height','0%');
			$('#idee').css('width','100%');
			$('#idee').css('height','100%');
			
		});
		$('#btn2').click(function () {
			
			$('#layout').layout('expand','east');
			
			$('#ide').css('width','100%');
			$('#ide').css('height','100%');
			$('#idee').css('width','0%');
			$('#idee').css('height','0%');
		});
		*/
		
		$("#dialogJSEditer").dialog('close');
		$("#dialogCSTEditer").dialog('close');
	    messenger = new Messenger('parent', 'faceui');
	    var ide = document.getElementById('ide');

	    var model = document.getElementById('model');
	    
	    var preview = document.getElementById('preview');
	    
	    var jsEditer= document.getElementById('JSwindow');
	
	    var cstEditer= document.getElementById('CSTwindow');
	
	    messenger.listen(function (msg) {
	    	console.info('--------------frame messenger listen-------------');
	    	console.info(msg);
	    	if (msg == "bigpc")
	    	{
	    		$("#centerID").css("width","100%"); 
	    	}else if (msg == "pc")
	    	{
	    		$("#centerID").css("width","970px"); 
	    	}else if (msg == "pad")
	    	{
	    		$("#centerID").css("width","750px");
	    	}else if (msg == "phone")
	    	{
	    		$("#centerID").css("width","580px");
	    	}else if(msg=="download")
	    	{
	    		sendMessage('ide',msg);
	    	}else if(msg=="save")
	    	{
	    		var m = new Object();
	    		m.name = 'save';
	    		m.value = savePath;
	    		sendMessage('ide',m);
	    	}else if(msg=="modal")
	    	{
	    		sendMessage('ide',msg);
	    	}else if(msg=="clear")
	    	{
	    		sendMessage('ide',msg);
	    	}else if(msg=="edit")
	    	{
	    		sendMessage('ide',msg);
	    	}else if(msg=="devpreview")
	    	{
	    		sendMessage('ide',msg);
	    	}else if(msg.act==="requestJSET"){
	    		
	    		editerstart(msg);
	    		
	    	}else if(msg==="savescs"){
	    		$("#tool_save").linkbutton("disable");
	    		$("#btn_save").linkbutton("disable");
	    	} else if(msg==="saverr"){
	    		alert("保存失败");
	    	}else if(msg==="jschange"){
	    		$("#tool_save").linkbutton("enable");
	    		$("#btn_save").linkbutton("enable");
	    	}else if(msg=="JSEditer")
	    	{
	    		
	    		editerstart(msg);
	    		
	    	}else if(msg=="CSTEditer")
	    	{
	    		
	    		cstEditerstart(msg);
	    		
	    	}else if(msg==="cstchange"){
	    		$("#cst_tool_save").linkbutton("enable");
	    		$("#cst_btn_save").linkbutton("enable");
	    	}else if(msg=="browserPreview")
	    	{
	    		var m = new Object();
	    		m.name = 'browserPreview';
	    		m.value = savePath;
	    		sendMessage('ide',m);
	    		
	    	}else if(msg=='open'){
	    		sendMessage('preview',msg);
	    		$('#dialog').panel('refresh').panel('open');
	    		
	    	}else if(msg=='savePath'){
	    		sendMessage('ide',savePath);
	    		
	    	}else{
	    		mesHandler(msg);
	    	}
	    });
	    //
	    messenger.addTarget(ide.contentWindow, 'ide');
	    messenger.addTarget(model.contentWindow, 'model');
	    messenger.addTarget(preview.contentWindow, 'preview');
	    messenger.addTarget(jsEditer.contentWindow, 'jsEditer');
	    messenger.addTarget(cstEditer.contentWindow, 'cstEditer');
	    
	    $('#westid').panel({
	    	onResize:function(width, height){
				setTimeout( "$('#dataGrid').datagrid('resize', { })", 200 );
				setTimeout( "$('#dataGrid2').datagrid('resize', { })", 200 );
				setTimeout( "$('#dataGrid3').datagrid('resize', { })", 200 );
	    	}
	    });
	    
	});
	 function editerstart(msg){
		//$('#dialogJSEditer').panel('refresh');
 	   	$("#dialogJSEditer").dialog('open').dialog({
 	          minimizable : false,  
 	          maximizable : true,
 	          maximized : true,
 	          shadow : false,  
 	          cache : false,
 	          collapsible : false,  
 	          resizable : true,
 			  width : 1300,  
 	          height : 580,
 	          toolbar:[{
 	        	    id:'tool_save',
 					text:'保存',
 					iconCls:'icon-save',
 					handler:function(){
 						var mag={};
 			    		mag.value=savePath;;
 			    		mag.message="JSAVE";
 			    		sendMessage('jsEditer',mag);
 						 
 						}
 				},{
 					text:'帮助',
 					iconCls:'icon-help',
 					handler:function(){
 						alert("help");
 					
 						}
 				}],
 				buttons:[{
 					id:'btn_save',
 					text:'保存',
 					handler:function(){
 						var mag={};
 						mag.value=savePath;
 			    		mag.message="JSAVE";
 			    		sendMessage('jsEditer',mag);
 						
 						
 						}
 				},
 				{
 					text:'关闭',
 					handler:function(){$("#dialogJSEditer").dialog('close')}
 				}]
 	          
 		        
 		});
 		var mag ={};
 		mag.value = savePath;;
 		mag.message = "JSURL";
 		mag.numark = msg.numark;
 		console.info(msg.quesbtn+" btn事件和id名称 "+msg.idname);
 		mag.quesbtn = msg.quesbtn;
    	mag.idname = msg.idname;
 		console.info(msg.numark + " 值message.numark  可能是undefined");
 		sendMessage('jsEditer',mag);
	 }
	 function cstEditerstart(msg){
 	   	$("#dialogCSTEditer").dialog('open').dialog({
 	          minimizable : false,  
 	          maximizable : true,
 	          maximized : true,
 	          shadow : false,  
 	          cache : false,
 	          collapsible : false,  
 	          resizable : true,
 			  width : 1300,  
 	          height : 580,
 	          toolbar:[{
 	        	    id:'cst_tool_save',
 					text:'保存',
 					iconCls:'icon-save',
 					handler:function(){
 						var mag={};
 			    		mag.value=savePath;;
 			    		mag.message="CSTSAVE";
 			    		sendMessage('cstEditer',mag);
 						 
 						}
 				},{
 					text:'帮助',
 					iconCls:'icon-help',
 					handler:function(){
 						alert("help");
 					
 						}
 				}],
 				buttons:[{
 					id:'cst_btn_save',
 					text:'保存',
 					handler:function(){
 						var mag={};
 						mag.value=savePath;
 			    		mag.message="CSTSAVE";
 			    		sendMessage('cstEditer',mag);
 						
 						
 						}
 				},
 				{
 					text:'关闭',
 					handler:function(){$("#dialogCSTEditer").dialog('close')}
 				}]
 	          
 		        
 		});
 		var mag ={};
 		mag.value = savePath;
 		mag.message = "CSTURL";
 		console.info("savePath "+savePath);
 		sendMessage('cstEditer',mag);
	 }
	// 向单个子页面发送消息
    function sendMessage(name,changes) {
        messenger.targets[name].send(changes);
    }
    // 向全部子页面发送‘message from parent: to all’
    function sendAll() {
        messenger.send("message from parent: to all");
    }
    
    $('#layout').addClass("easyui-layout");
	$('#idcenter').addClass("easyui-layout");
	
    
	 var setting = {
			view: {
				addHoverDom: addHoverDom,
				removeHoverDom: removeHoverDom,
				selectedMulti: false,
				dblClickExpand: false
			},
			edit: {
				enable: true,
				editNameSelectAll: true
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeDrag: beforeDrag,
				beforeRemove: beforeRemove,
				beforeRename: beforeRename,
				onRemove : zTreeOnRemove,
				onRename: zTreeOnRename,
				onDblClick: zTreeOnDblClick
			}
		}; 
	
		function beforeDrag(treeId, treeNodes) {
			return false;
		}
	
	 	function beforeRemove(treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.selectNode(treeNode);
			if(treeNode.name.indexOf(".jsp")>0){
				return confirm("确认删除 节点 -- " + treeNode.name + " 吗？");
			}else{
				 if(treeNode.children.length>0){
		           alert("该节点有子节点，不能删除，亲！");
		           return false;
				 }else{
					 return confirm("确认删除 节点 -- " + treeNode.name + " 吗？");
				}
			 } 
		}
		var oldNodeName="";
		function beforeRename(treeId, treeNode, newName, isCancel) {
			var path=getNodePath(treeNode);
			oldNodeName=path;
			if (newName.length == 0) {
				alert("节点名称不能为空,亲");
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				setTimeout(function(){zTree.editName(treeNode)}, 10);
				return false;
			}
			if(newName.indexOf(".jsp")>0){
				if(typeof(treeNode.children)=="undefined"){
					var zTree = $.fn.zTree.getZTreeObj("treeDemo");
					var nodes = zTree.getSelectedNodes();
					<%-- nodes.icon="<%=basePath%>/ztree/css/zTreeStyle/img/diy/aa.png"; --%>
					zTree.updateNode(nodes);
					return true;
				}
				if(treeNode.children.length>0){
					alert("该节点有子节点，不能修改为页面，亲！");
					return false;
				}
			}
			if(newName.indexOf(".jsp")<0){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var nodes = zTree.getSelectedNodes();
				<%-- nodes.iconSkin="<%=basePath%>/ztree/css/zTreeStyle/img/diy/aa.png";
				nodes.icon="<%=basePath%>/ztree/css/zTreeStyle/img/diy/bb.png";  --%>
				zTree.updateNode(nodes);
				return true;
			}
			return false;
		}
		
		function getPath(treeNode){
			var _path = '';
			var parentNode = treeNode.getParentNode();
			if(parentNode!==null){
			  _path = getPath(parentNode);
			}
			if(parentNode===null){
				return '';
			}
			return _path+'/'+treeNode.name;
		}
		
		function zTreeOnDblClick(event, treeId, treeNode){
			try{
				$('#layout').layout('expand','east');
				
				$('#ide').css('width','100%');
				$('#ide').css('height','100%');
				$('#idee').css('width','0%');
				$('#idee').css('height','0%');
			}catch(e){}
			
			if(treeNode.name.indexOf('.jsp')>0){
				$.ajax({ 
					type: "POST",
					url:ctxIde+'/ide/jspGet',
					data: {"path":getPath(treeNode)},
					dataType : "json",
					success : function(data) {
						//传送到IDE.jsp
						var msg = new Object();
						msg.eleType = 'jspCon';
						msg.value = data.obj.jspFile;
						//下拉列表反显设置
						msg.selectinfo=1;
						savePath = getPath(treeNode);
						previewJspPath = data.obj.jspPath;
						msg.filePath = savePath;
						sendMessage('ide',msg);
						
					}
				});
			}else{
				getAllJspName();
			}
		}
		
		
		var newCount = 0;
		function addHoverDom(treeId, treeNode) {
			var sObj = $("#" + treeNode.tId + "_span");
			if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0||$("#navBtn_"+treeNode.tId).length>0) return;
			
			if(treeNode.name.indexOf(".jsp")<0){
				var $addStr = $('<span>',{
					'class':'button add',
					'id':'addBtn_' + treeNode.tId,
					'title':'增加节点'
				});
				sObj.after($addStr);
				$addStr.bind("click", function(){
					var zTree = $.fn.zTree.getZTreeObj("treeDemo");
					var newNode={id:(100 + newCount), pId:treeNode.id, name:"new Node" +(newCount++)+".jsp"<%-- ,icon:"<%=basePath%>/ztree/css/zTreeStyle/img/diy/bb.png" --%>};
					newNode.icon = "<%=basePath%>/b_base/ztree/css/zTreeStyle/img/diy/right.png";
					zTree.addNodes(treeNode,newNode);
					var path=getNodePath(treeNode);
					addJsp(path,newNode.name);
				});
			}else{
				var $navStr = $('<span>',{
					'class':'button ico_open',
					'id':'navBtn_' + treeNode.tId,
					'title':''
				});
				sObj.before($navStr);
				$navStr.bind("click", function(){
					$('#layout').layout('collapse','east');
					
					$('#ide').css('width','0%');
					$('#ide').css('height','0%');
					$('#idee').css('width','100%');
					$('#idee').css('height','100%');
					$('#idee').attr('src', $('#idee').attr('src'));
					savePath = getPath(treeNode);
					console.info('+++++++++++++++++');
					console.info(savePath);
				});
			}
		};
		
		function removeHoverDom(treeId, treeNode) {
			$("#addBtn_"+treeNode.tId).unbind().remove();
			$("#navBtn_"+treeNode.tId).unbind().remove();
	};
		
		function selectAll() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
		}
		
		function getNodePath(treeNode) {
				var path = "";
				p = treeNode.getParentNode();
			while (p != null){
				path = p.name + "//" + path;
				p = p.getParentNode();
			}
				return path+treeNode.name;
		};
		
		function addJsp(path,name){
			$.ajax({ 
				type: "POST",
				data:{path:path,name:name},
				url:ctxIde+'/ide/addJspName',
				dataType : "json",
				success : function(data) {
					addJScrpt(path,name);
					addCstFile(path,name);
				}
			});
		}
		//增加JS文件
		function addJScrpt(path,name){
			if(!path.endsWith(".jsp")&&!name.endsWith(".jsp")){
				return false;
			}
			name=name.split(".")[0]+".js";
			console.info(name);
			$.ajax({ 
				type: "POST",
				data:{path:path,name:name},
				url:ctxIde+'/ide/addJScrptName',
				dataType : "json",
				success : function(data) {
					var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
					treeObj.reAsyncChildNodes(null, "refresh");
				}
			});
		}
		
		//增加客户号文件cst
		function addCstFile(path,name){
			if(!path.endsWith(".jsp")&&!name.endsWith(".jsp")){
				return false;
			}
			name=name.split(".")[0]+".cst";
			console.info(name);
			$.ajax({ 
				type: "POST",
				data:{path:path,name:name},
				url:ctxIde+'/ide/addCstName',
				dataType : "json",
				success : function(data) {
					var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
					treeObj.reAsyncChildNodes(null, "refresh");
				}
			});
		}
		
		function zTreeOnRemove(event, treeId, treeNode) {
			var path=getNodePath(treeNode);
			removeJsp(path);
		}
		
		function removeJsp(path){
			$.ajax({ 
				type: "POST",
				data:{path:path},
				url:ctxIde+'/ide/removeJspName',
				dataType : "json",
				success : function(data) {
					removeJScript(path);
					removeCstFile(path);
				}
			});
		}
		
		function removeJScript(path){
			if(!path.endsWith(".jsp")){
				return false;
			}
			path=path.split(".")[0]+".js";
			$.ajax({ 
				type: "POST",
				data:{path:path},
				url:ctxIde+'/ide/removeJScript',
				dataType : "json",
				success : function(data) {
					var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
					treeObj.reAsyncChildNodes(null, "refresh");
				}
			});
		}
		
		//移除客户化文件
		function removeCstFile(path){
			if(!path.endsWith(".jsp")){
				return false;
			}
			path=path.split(".")[0]+".cst";
			$.ajax({ 
				type: "POST",
				data:{path:path},
				url:ctxIde+'/ide/removeCst',
				dataType : "json",
				success : function(data) {
					var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
					treeObj.reAsyncChildNodes(null, "refresh");
				}
			});
		}
		
		function zTreeOnRename(event, treeId, treeNode, isCancel) {
			var name=getNodePath(treeNode);
			console.info(oldNodeName+"===="+name);
			renameJsp(oldNodeName,name);
		}
		
		function renameJsp(path,name){
			$.ajax({ 
				type: "POST",
				data:{path:path,name:name},
				url:ctxIde+'/ide/renameJspName',
				dataType : "json",
				success : function(data) {
                    renameJScript(path,name);
                    renameCst(path,name);
				}
			});
		}
		
		function renameJScript(path,name){
			if(!path.endsWith(".jsp")&&!name.endsWith(".jsp")){
				return false;
			}
			var jsEditUrl="";
			var nameArrary=name.split("//");
			$.each(nameArrary,function(i,v){
				if(i!=0){
				   jsEditUrl=jsEditUrl+"/"+v;
				}
			});
			console.info(jsEditUrl+" jedit");
			path=path.split(".")[0]+".js";
			name=name.split(".")[0]+".js";
			$.ajax({ 
				type: "POST",
				data:{path:path,name:name},
				url:ctxIde+'/ide/renameJScriptName',
				dataType : "json",
				success : function(data) {
					savePath=jsEditUrl;
					//var mag={};
		    		//mag.value=jsEditUrl;
		    		//mag.message="SETURL";
		    		//sendMessage('jsEditer',mag);
					var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
					treeObj.reAsyncChildNodes(null, "refresh");
				}
			});
		}
		
		//重命名客户化文件
		function renameCst(path,name){
			if(!path.endsWith(".jsp")&&!name.endsWith(".jsp")){
				return false;
			}
			var cstEditUrl="";
			var nameArrary=name.split("//");
			$.each(nameArrary,function(i,v){
				if(i!=0){
				   cstEditUrl=cstEditUrl+"/"+v;
				}
			});
			console.info(cstEditUrl+" cstedit");
			path=path.split(".")[0]+".cst";
			name=name.split(".")[0]+".cst";
			$.ajax({ 
				type: "POST",
				data:{path:path,name:name},
				url:ctxIde+'/ide/renameCstName',
				dataType : "json",
				success : function(data) {
					savePath=cstEditUrl;
					var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
					treeObj.reAsyncChildNodes(null, "refresh");
				}
			});
		}
		
		function getAllJspName(){
			 $.ajax({ 
					type: "POST",
					//data:data,
					url:ctxIde+'/ide/getAllJspName',
					dataType : "json",
					success : function(data) {
						var nodes=data.obj;
						nodes=eval("(" + nodes + ")");  
						$.fn.zTree.init($("#treeDemo"), setting,nodes);
						
						var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
						
						
						var nodes = treeObj.transformToArray(treeObj.getNodes());
						for(var i=0;i<nodes.length;i++){
							var node = nodes[i];
							if(node.name.endsWith(".jsp")||node.name.endsWith('.css')||node.name.endsWith('.js')){
								node.icon = "<%=basePath%>/b_base/ztree/css/zTreeStyle/img/diy/right.png";
							}else{
								node.icon = "<%=basePath%>/b_base/ztree/css/zTreeStyle/img/diy/file.png";
							}
						}
						treeObj.refresh();
						
						treeObj.expandAll(false);
					}
				});
		}
		$(document).ready(function(){
			console.info('frame...');
			getAllJspName();
			$("#selectAll").bind("click", selectAll);
		});
    </script>
    
    
</body>
</html>
